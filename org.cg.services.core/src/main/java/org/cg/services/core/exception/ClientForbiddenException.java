package org.cg.services.core.exception;

import javax.ws.rs.ForbiddenException;

/**
 * A client access/action forbidden exception
 * @author liang.li
 *
 */
public class ClientForbiddenException extends ForbiddenException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2026310525306636062L;
	
	public ClientForbiddenException() {
		super(new Throwable("access/action forbidden."));
	}
	
	public ClientForbiddenException(String msg) {
		super(new Throwable(msg));
	}

}
