package org.cg.services.core.exception.mapper;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cg.services.core.exception.ServiceExceptionMessage;

/**
 * A ExceptionMapper that maps BadRequestException to 400 BAD_REQUEST
 * @author WZ
 *
 */
@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    
	private static final Logger LOG = LoggerFactory.getLogger(BadRequestExceptionMapper.class);

    @Override
    public Response toResponse(BadRequestException exception) {
    	LOG.error("Service Exception:", exception);

        ServiceExceptionMessage serviceExceptionDetails = new ServiceExceptionMessage(
        		Response.Status.BAD_REQUEST.getStatusCode(),
        		Response.Status.BAD_REQUEST.toString(),
        		exception.getCause()!=null ? exception.getCause().getMessage() : "Bad Request");
        
       return Response.status(Response.Status.BAD_REQUEST).entity(serviceExceptionDetails).type(MediaType.APPLICATION_JSON).build();
    }
}