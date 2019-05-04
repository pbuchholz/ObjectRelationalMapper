package orm.sql;

import java.util.List;
import java.util.stream.Stream;

/**
 * Implemented by objects which support a {@link FieldMapper}.
 * 
 * @author Philipp Buchholz
 */
public interface FieldMapperSupport<T, I> {

	/**
	 * Returns the {@link FieldMapper} which is supported.
	 * 
	 * @return
	 */
	FieldMapper<T, I> getFieldMapper();

	public static Stream<FieldMapping<?>> unionFieldMappings(List<FieldMapping<?>> left, List<FieldMapping<?>> right) {
		return Stream.concat(left.stream(), right.stream());
	}

}
