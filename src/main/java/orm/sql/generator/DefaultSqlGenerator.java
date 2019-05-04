package orm.sql.generator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import orm.CriteriasBuilder.Criterias;
import orm.sql.FieldMapping;
import orm.sql.FieldMappingException;
import orm.sql.interpreter.SqlCriteriaInterpreter;

@ApplicationScoped
public class DefaultSqlGenerator implements SqlGenerator {

	private static final String EQUALS = "=";
	private static final String OPEN_BRACKET = "(";
	private static final String CLOSE_BRACKET = ")";
	private static final String AND = " AND ";
	private static final String COLUMN_SEPERATOR = ",";
	private static final String VALUE_SEPERATOR = "'";

	@Inject
	private SqlCriteriaInterpreter sqlCriteriaInterpreter;

	@Override
	public String generateSelectStatement(String tableName, List<FieldMapping<?>> columns,
			FieldMappingTargetBag idBag) {
		String sql = this.generateSelectStatement(tableName, columns);
		return sql.concat(SqlClause.WHERE.clause()) //
				.concat(this.createWhereClause(idBag));
	}

	@Override
	public String generateSelectStatement(String tableName, List<FieldMapping<?>> columns, Criterias criterias) {
		String sql = this.generateSelectStatement(tableName, columns);
		return sql.concat(SqlClause.WHERE.clause()) //
				.concat(this.createWhereClause(criterias));
	}

	@Override
	public String generateSelectStatement(String tableName, List<FieldMapping<?>> columns) {
		return DmlStatement.SELECT.statement().concat(createColumnList(columns)) //
				.concat(SqlClause.FROM.clause()) //
				.concat(tableName);
	}

	@Override
	public String generateInsertStatement(String tableName, FieldMappingTargetBag... fieldMappingTargetBags) {
		return DmlStatement.INSERT.statement() //
				.concat(tableName) //
				.concat(createColumnAndValueList(fieldMappingTargetBags));
	}

	private String createWhereClause(Criterias criterias) {
		return this.sqlCriteriaInterpreter.interpret(criterias);
	}

	private String createWhereClause(FieldMappingTargetBag idBag) {
		return idBag.getFieldMappings().stream() //
				.map((fieldMapping) -> fieldMapping.columnName() //
						.concat(EQUALS) //
						.concat(extractValue(fieldMapping, idBag.getTarget()))) //
				.reduce((left, right) -> left.concat(AND)) //
				.get();
	}

	private String extractValue(FieldMapping<?> fieldMapping, Object target) {
		try {
			return (fieldMapping.needsConversion()) ? fieldMapping.typeConverter() //
					.convertTo(fieldMapping.getter().invoke(target))
					: String.valueOf(fieldMapping.getter().invoke(target));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new FieldMappingException(e);
		}
	}

	private String escapeChars(FieldMapping<?> fieldMapping, String value) {
		return (fieldMapping.isNumericField()) ? value : VALUE_SEPERATOR.concat(value).concat(VALUE_SEPERATOR);
	}

	private String createColumnAndValueList(FieldMappingTargetBag... fieldMappingTargetBags) {
		StringBuilder columnList = new StringBuilder(OPEN_BRACKET);
		StringBuilder valueList = new StringBuilder(SqlClause.VALUES.clause()) //
				.append(OPEN_BRACKET);

		Stream.of(fieldMappingTargetBags) //
				.forEach(bag -> {
					bag.getFieldMappings().forEach(fieldMapping -> {
						columnList //
								.append(fieldMapping.columnName()) //
								.append(COLUMN_SEPERATOR);
						valueList //
								.append(this.escapeChars(fieldMapping,
										this.extractValue(fieldMapping, bag.getTarget()))) //
								.append(COLUMN_SEPERATOR);
					});
				});

		return this.replaceLastColumnSeperator(CLOSE_BRACKET, columnList) //
				.append(this.replaceLastColumnSeperator(CLOSE_BRACKET, valueList)).toString();
	}

	private StringBuilder replaceLastColumnSeperator(String replacement, StringBuilder source) {
		return source.replace(source.length() - 1, source.length(), replacement);
	}

	private String createColumnList(List<FieldMapping<?>> columns) {
		return columns.stream() //
				.map(FieldMapping::columnName) //
				.reduce((left, right) -> left //
						.concat(COLUMN_SEPERATOR) //
						.concat(right)) //
				.get();
	}

}
