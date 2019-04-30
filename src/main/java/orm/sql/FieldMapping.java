package de.bu.governance.healthmetrics.storage.sql;

import java.lang.reflect.Method;

/**
 * Represents the mapping of a single field of type T.
 * 
 * @author Philipp Buchholz
 *
 * @param <T>
 */
public class FieldMapping<T> {
	private String columnName;
	private int insertIndex;
	private Method getter;
	private Method setter;
	private Class<T> fieldType;
	private TypeConverter<T> typeConverter;

	public String columnName() {
		return columnName;
	}

	public int insertIndex() {
		return insertIndex;
	}

	public Method getter() {
		return this.getter;
	}

	public Method setter() {
		return this.setter;
	}

	public Class<T> fieldType() {
		return this.fieldType;
	}

	public TypeConverter<T> typeConverter() {
		return this.typeConverter;
	}

	/**
	 * Returns <code>true</code> if the value of the mapped field must by converted
	 * by a {@link TypeConverter}.
	 * 
	 * @return
	 */
	public boolean needsConversion() {
		return null != this.typeConverter;
	}

	public boolean isInsertable() {
		return this.insertIndex > 0;
	}

	public boolean isNumericField() {
		return this.fieldType.isAssignableFrom(Number.class);
	}

	public static <T> Builder<T> builder() {
		return new Builder<>();
	}

	public static final class Builder<T> {

		private FieldMapping<T> fieldMapping;

		public Builder() {
			this.fieldMapping = new FieldMapping<>();
		}

		public Builder<T> columnName(String columnName) {
			this.fieldMapping.columnName = columnName;
			return this;
		}

		public Builder<T> insertIndex(int insertIndex) {
			this.fieldMapping.insertIndex = insertIndex;
			return this;
		}

		public Builder<T> getter(Method getter) {
			this.fieldMapping.getter = getter;
			return this;
		}

		public Builder<T> setter(Method setter) {
			this.fieldMapping.setter = setter;
			return this;
		}

		public Builder<T> fieldType(Class<T> fieldType) {
			this.fieldMapping.fieldType = fieldType;
			return this;
		}

		public Builder<T> typeConverter(TypeConverter<T> typeConverter) {
			this.fieldMapping.typeConverter = typeConverter;
			return this;
		}

		public FieldMapping<T> build() {
			return this.fieldMapping;
		}

	}

}