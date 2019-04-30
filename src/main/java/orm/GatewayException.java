package de.bu.governance.healthmetrics.storage;

/**
 * {@link Exception} which is thrown if an exception occurs in a {@link Gateway}
 * operation.
 * 
 * @author Philipp Buchholz
 */
public class GatewayException extends RuntimeException {

	private static final long serialVersionUID = 8014187798978383206L;

	public GatewayException() {

	}

	public GatewayException(Exception cause) {
		super(cause);
	}

	public GatewayException(String message) {
		super(message);
	}

}
