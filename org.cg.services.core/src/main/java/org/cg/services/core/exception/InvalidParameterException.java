package org.cg.services.core.exception;

import javax.ws.rs.BadRequestException;

/**
 * A BadRequestException indicates invalid parameter
 * @author WZ
 *
 */
public class InvalidParameterException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7876121164556246896L;

	/**
	 * Constructor
	 */
	public InvalidParameterException() {
		super(new Throwable("Invalid parameter."));
	}

	/**
	 * Constructor
	 * @param msg The error message
	 */
	public InvalidParameterException( String msg ) {
		super(new Throwable(msg));
	}
}