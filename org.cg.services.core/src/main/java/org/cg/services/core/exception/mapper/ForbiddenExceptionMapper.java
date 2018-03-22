package org.cg.services.core.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.cg.services.core.exception.ClientForbiddenException;
import org.cg.services.core.exception.ServiceExceptionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A exception mapper that maps ForbiddenException to 403 FORBIDDEN
 * @author liang.li
 *
 */
@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ClientForbiddenException>{

	private static final Logger LOG = LoggerFactory.getLogger(ForbiddenExceptionMapper.class);

	@Override
	public Response toResponse(ClientForbiddenException exception) {
		
		LOG.info("Service exception: ", exception);
		
		ServiceExceptionMessage serviceExceptionDetails = new ServiceExceptionMessage(
				Response.Status.FORBIDDEN.getStatusCode(), 
				Response.Status.FORBIDDEN.toString(), 
				exception.getCause() != null ? exception.getCause().getMessage() : "Action forbidden.");
		
		return Response.status(Response.Status.FORBIDDEN).entity(serviceExceptionDetails).type(MediaType.APPLICATION_JSON).build();
	}
	
}
