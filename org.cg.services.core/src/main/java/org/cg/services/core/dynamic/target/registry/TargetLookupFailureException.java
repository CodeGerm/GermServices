package org.cg.services.core.dynamic.target.registry;

import org.springframework.core.NestedRuntimeException;

public class TargetLookupFailureException extends NestedRuntimeException {

	private static final long serialVersionUID = 5544643713610702772L;

	public TargetLookupFailureException(final String message) {
        super(message);
    }

    public TargetLookupFailureException(final String message, final Throwable cause) {
        super(message, cause);
    }

}