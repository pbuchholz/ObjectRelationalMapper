package orm;

public class StorageSystemException extends RuntimeException {

	private static final long serialVersionUID = 2604091918482116551L;

	public StorageSystemException(Exception cause) {
		super(cause);
	}

}
