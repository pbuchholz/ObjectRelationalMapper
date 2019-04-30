package de.bu.governance.healthmetrics.storage.finder;

/**
 * {@link Exception} which is thrown if a finder does not return any result.
 * 
 * @author Philipp Buchholz
 */
public class NoResultFoundException extends RuntimeException {

	private static final long serialVersionUID = -3928690125811752493L;

	public NoResultFoundException() {

	}

	public NoResultFoundException(String message) {
		super(message);
	}

	public NoResultFoundException(Exception cause) {
		super(cause);
	}

}
