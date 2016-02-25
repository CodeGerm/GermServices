package org.cg.services.core.exception;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A exception message that can be returned in error response
 * @author WZ
 *
 */
public class ServiceExceptionMessage {

	private final int errorCode;
	private final String errorType;
	private final String error;
	private final static Gson gson = new GsonBuilder()
									.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
									.create();
	
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

	public String toJson() {
		return gson.toJson(this);
	}
}