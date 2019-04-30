package de.bu.governance.healthmetrics.storage.sql;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Stream;

import de.bu.governance.healthmetrics.storage.Reflections;

/**
 * Abstract base class of {@link FieldMapper}s.
 * 
 * @author Philipp Buchholz
 *
 * @param <T>
 */
public abstract class AbstractFieldMapper<T, I> implements FieldMapper<T, I> {

	@Override
	public FieldMapping<?> byGetter(Method getter) {
		Stream<FieldMapping<?>> fieldMappings = FieldMapperSupport.unionFieldMappings(this.fieldMappings(),
				this.idFieldMappings());
		Optional<FieldMapping<?>> fieldMapping = fieldMappings.filter((entry) -> entry.getter().equals(getter))
				.findFirst();
		if (fieldMapping.isPresent()) {
			return fieldMapping.get();
		} else {
			return null;
		}
	}

	@Override
	public FieldMapping<?> byColumn(String columnName) {
		Stream<FieldMapping<?>> fieldMappings = FieldMapperSupport.unionFieldMappings(this.fieldMappings(),
				this.idFieldMappings());
		Optional<FieldMapping<?>> fieldMapping = fieldMappings.filter(entry -> entry.columnName().equals(columnName))
				.findFirst();
		if (fieldMapping.isPresent()) {
			return fieldMapping.get();
		} else {
			return null;
		}
	}

	protected Method findGetter(String propertyName) {
		return Reflections.findGetterOn(this.mappedType(), propertyName);
	}

	protected Method findSetter(String propertyName) {
		return Reflections.findSetterOn(this.mappedType(), propertyName);
	}

	protected Method findIdentifierGetter(String propertyName) {
		return Reflections.findGetterOn(this.identifierType(), propertyName);
	}

	protected Method findIdentifierSetter(String propertyName) {
		return Reflections.findSetterOn(this.identifierType(), propertyName);
	}

}
