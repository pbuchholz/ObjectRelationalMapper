package de.bu.governance.healthmetrics.storage.sql;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.sql.DataSource;

import de.bu.governance.healthmetrics.storage.CriteriasBuilder.Criterias;
import de.bu.governance.healthmetrics.storage.Gateway;
import de.bu.governance.healthmetrics.storage.GatewayException;
import de.bu.governance.healthmetrics.storage.Reflections;
import de.bu.governance.healthmetrics.storage.sql.generator.FieldMappingTargetBag;
import de.bu.governance.healthmetrics.storage.sql.generator.SqlGenerator;

/**
 * Abstract {@link Gateway} which uses a underlying relational database to
 * provide access to data.
 * 
 * @author Philipp.Buchholz
 *
 * @param <I>
 * @param <T>
 */
public abstract class AbstractSqlGateway<I, T> implements Gateway<I, T>, FieldMapperSupport<T, I> {

	@Inject
	private SqlGenerator sqlGenerator;

	/**
	 * Returns the {@link DataSource} which is used to access the underlying
	 * relational data store.
	 * 
	 * @return
	 */
	protected abstract DataSource getDataSource();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> findAll() {
		List<FieldMapping<?>> fieldMappings = FieldMapperSupport
				.unionFieldMappings(this.getFieldMapper().fieldMappings(), this.getFieldMapper().idFieldMappings()) //
				.collect(Collectors.toList());
		String select = this.sqlGenerator.generateSelectStatement(this.tableName(), fieldMappings);
		return this.findMany(select);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> findManyByCriterias(Criterias criterias) {
		List<FieldMapping<?>> fieldMappings = FieldMapperSupport
				.unionFieldMappings(this.getFieldMapper().fieldMappings(), this.getFieldMapper().idFieldMappings()) //
				.collect(Collectors.toList());
		String select = this.sqlGenerator.generateSelectStatement(this.tableName(), fieldMappings, criterias);
		return this.findMany(select);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T findOneByCriterias(Criterias criterias) {
		List<T> found = this.findManyByCriterias(criterias);
		if (found.isEmpty()) {
			return null;
		} else if (found.size() > 1) {
			throw new GatewayException("Criterias are ambiguous.");
		}

		return found.get(0);
	}

	private List<T> findMany(String sqlStatement) {
		try {
			Connection connection = this.getDataSource().getConnection();
			try (PreparedStatement select = connection.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)) {
				List<T> foundObjects = new ArrayList<>();
				ResultSet resultSet = select.executeQuery();
				if (!resultSet.first()) {
					return Collections.emptyList();
				}

				do {
					foundObjects.add(this.mapSingleObject(resultSet));
				} while (resultSet.next());

				return foundObjects;
			}
		} catch (SQLException e) {
			throw new GatewayException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T findOneByIdentifier(I identifier) {
		try {
			List<FieldMapping<?>> fieldMappings = FieldMapperSupport
					.unionFieldMappings(this.getFieldMapper().fieldMappings(), this.getFieldMapper().idFieldMappings()) //
					.collect(Collectors.toList());
			String select = this.sqlGenerator.generateSelectStatement(this.tableName(), fieldMappings,
					new FieldMappingTargetBag(identifier, this.getFieldMapper().idFieldMappings()));

			Connection connection = this.getDataSource().getConnection();
			try (PreparedStatement preparedStatement = connection.prepareStatement(select,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
				ResultSet resultSet = preparedStatement.executeQuery();
				if (!resultSet.first()) {
					return null;
				}

				return this.mapSingleObject(resultSet);
			}
		} catch (SQLException e) {
			throw new GatewayException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(I identifier, T add) {
		Connection connection = null;
		try {
			// Does not work because add needs to be an identifyable object here!
			FieldMappingTargetBag idFieldMappingTargetBag = new FieldMappingTargetBag(identifier,
					this.getFieldMapper().idFieldMappings());
			FieldMappingTargetBag fieldMappingTargetBag = new FieldMappingTargetBag(add,
					this.getFieldMapper().fieldMappings());
			String insert = this.sqlGenerator.generateInsertStatement(this.tableName(), idFieldMappingTargetBag,
					fieldMappingTargetBag);

			connection = this.getDataSource().getConnection();
			try (PreparedStatement insertPreparedStatement = connection.prepareStatement(insert)) {
				insertPreparedStatement.executeUpdate();
			}
			connection.commit();
		} catch (SQLException e) {
			throw new GatewayException(e);
		} finally {
			this.closeIf(connection);
		}
	}

	private void closeIf(Connection connection) {
		try {
			if (null != connection) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new GatewayException(e);
		}
	}

	/**l
	 * Returns the name of the database table the gateway is operating on.
	 * 
	 * @return
	 */
	protected abstract String tableName();

	/**
	 * Maps the row to which the {@link ResultSet} currently points to an new object
	 * of type T.
	 * 
	 * @param resultSet
	 * @return @
	 */
	private T mapSingleObject(ResultSet resultSet) {
		return mapUsingFieldMapper(resultSet);
	}

	/**
	 * Maps a single instance of type T using the {@link FieldMapper} supported by
	 * the {@link AbstractSqlGateway} implementation.
	 * 
	 * @param resultSet
	 * @return @
	 */
	private T mapUsingFieldMapper(ResultSet resultSet) {
		try {
			/* Instantiate instance and map base class fields. */
			T instance = this.supportedType().newInstance();

			for (Method method : instance.getClass().getMethods()) {
				if (Reflections.isGetter(method)) {
					FieldMapping<?> fieldMapper = this.getFieldMapper().byGetter(method);
					if (null != fieldMapper) {

						Object value = (fieldMapper.needsConversion())
								? fieldMapper.typeConverter().convertFrom(resultSet.getString(fieldMapper.columnName()))
								: resultSet.getObject(fieldMapper.columnName());
						fieldMapper.setter().invoke(instance, value);
					}
				}
			}

			return instance;
		} catch (Exception e) {
			throw new GatewayException(e);
		}
	}

}
