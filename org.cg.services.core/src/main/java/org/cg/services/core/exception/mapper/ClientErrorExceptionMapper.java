package org.cg.services.core.exception.mapper;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cg.services.core.exception.ServiceExceptionMessage;

/**
 * A ExceptionMapper that maps ClientErrorException to 400 BAD_REQUEST
 * @author WZ
 *
 */
public class ClientErrorExceptionMapper implements ExceptionMapper<ClientErrorException> {
	
    private static final Logger LOG = LoggerFactory.getLogger(ClientErrorExceptionMapper.class);

    @Override
    public Response toResponse(ClientErrorException exception) {
    	LOG.error("Service Exception", exception);

        ServiceExceptionMessage serviceExceptionDetails = new ServiceExceptionMessage(
        		Response.Status.BAD_REQUEST.getStatusCode(),
        		Response.Status.BAD_REQUEST.toString(),
        		exception.getCause() !=null ? exception.getCause().getMessage() : "Bad Path");
        
       return Response.status(Response.Status.BAD_REQUEST).entity(serviceExceptionDetails).type(MediaType.APPLICATION_JSON).build();
    }
}
