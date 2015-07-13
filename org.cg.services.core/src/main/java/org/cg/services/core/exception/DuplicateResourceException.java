package org.cg.services.core.exception;

import javax.ws.rs.BadRequestException;

/**
 * A BadRequestException indicates duplicate resource
 * @author WZ
 *
 */
public class DuplicateResourceException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2023321811788010561L;

	/**
	 * Constructor
	 */
    public DuplicateResourceException() {
        super(new Throwable("Duplicate resource."));
    }
    
    /**
     * Constructor
     * @param msg The error message
     */
    public DuplicateResourceException( String msg) {
        super(new Throwable(msg));
    }
}