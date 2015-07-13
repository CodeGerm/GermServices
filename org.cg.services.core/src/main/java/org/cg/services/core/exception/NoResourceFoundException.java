package org.cg.services.core.exception;

import javax.ws.rs.NotFoundException;

/**
 * A NotFoundException indicate no resource found
 * @author WZ
 *
 */
public class NoResourceFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6882071913049906604L;

	/**
	 * Constructor
	 */
    public NoResourceFoundException() {
        super(new Throwable("No resource found."));
    }
    
    /**
     * Constructor
     * @param msg The error message
     */
    public NoResourceFoundException( String msg) {
        super(new Throwable(msg));
    }
}