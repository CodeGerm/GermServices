package org.cg.services.core.exception;

import javax.ws.rs.WebApplicationException;

/**
 * A WebApplicationException indicates internal error
 * @author WZ
 *
 */
public class InternalServiceException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1280568639064043800L;

	/**
	 * Constructor
	 */
	public InternalServiceException() {
		super(new Throwable("Internal error."));
	}

	/**
	 * Constructor
	 * @param throwable The cause 
	 */
	public InternalServiceException(Throwable throwable) {
		super(throwable);
	}
	
	/**
	 * Constructor
	 * @param msg The error message
	 */
	public InternalServiceException( String msg ) {
		super(new Throwable(msg));
	}
}