package org.cg.services.core.exception;

/**
 * A exception message that can be returned in error response
 * @author WZ
 *
 */
public class ServiceExceptionMessage {

	private final int errorCode;
	private final String errorType;
	private final String error;
	
	public ServiceExceptionMessage(int errorCode, String errorType, String error) {
		super();
		this.errorCode = errorCode;
		this.errorType = errorType;
		this.error = error;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorType() {
		return errorType;
	}

	public String getErrorMessage() {
		return error;
	}

}