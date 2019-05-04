package orm.sql;

/**
 * Exception thrown in case fields mappings could not be made.
 * 
 * @author Philipp Buchholz
 */
public class FieldMappingException extends RuntimeException {

	private static final long serialVersionUID = 7047443838448607760L;

	public FieldMappingException(Exception cause) {
		super(cause);
	}

}
