package org.cg.services.core.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cg.services.core.exception.DuplicateResourceException;
import org.cg.services.core.exception.ServiceExceptionMessage;

/**
 * A ExceptionMapper that maps DuplicateResourceException to 409 CONFLICT
 * @author WZ
 *
 */
@Provider
public class DuplicateResourceExceptionMapper implements ExceptionMapper<DuplicateResourceException> {
    
	private static final Logger LOG = LoggerFactory.getLogger(DuplicateResourceExceptionMapper.class);

    @Override
    public Response toResponse(DuplicateResourceException exception) {
    	LOG.info("Service Exception:", exception);

        ServiceExceptionMessage serviceExceptionDetails = new ServiceExceptionMessage(
        		Response.Status.CONFLICT.getStatusCode(),
        		Response.Status.CONFLICT.toString(),
        		exception.getCause() !=null ? exception.getCause().getMessage() : "Duplicate Resource Found"
        		);
      
       return Response.status(Response.Status.CONFLICT).entity(serviceExceptionDetails).type(MediaType.APPLICATION_JSON).build();
    }

}