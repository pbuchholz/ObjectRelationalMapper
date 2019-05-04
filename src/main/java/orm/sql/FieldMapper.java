package orm.sql;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Maps the fields of a defined type T.
 * 
 * @author Philipp Buchholz
 */
public interface FieldMapper<T, I> {

	/**
	 * Type which is mapped.
	 * 
	 * @return
	 */
	Class<T> mappedType();

	/**
	 * Type of identifier which is supported.
	 * 
	 * @return
	 */
	Class<I> identifierType();

	/**
	 * Returns the {@link FieldMapping} by the passed in Getter.
	 * 
	 * @param getter
	 * @return
	 */
	FieldMapping<?> byGetter(Method getter);

	/**
	 * Returns the {@link FieldMapping} by the passed in Column.
	 * 
	 * @param columnName
	 * @return
	 */
	FieldMapping<?> byColumn(String columnName);

	/**
	 * Returns the {@link List} of all available {@link FieldMapping}s.
	 * 
	 * @return
	 */
	List<FieldMapping<?>> fieldMappings();

	/**
	 * Returns the {@link List} of {@link FieldMapping}s which map id fields.
	 * 
	 * @return
	 */
	List<FieldMapping<?>> idFieldMappings();
}
