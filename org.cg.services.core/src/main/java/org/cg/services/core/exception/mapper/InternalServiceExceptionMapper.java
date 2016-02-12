package org.cg.services.core.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cg.services.core.exception.InternalServiceException;
import org.cg.services.core.exception.ServiceExceptionMessage;

/**
 * A ExceptionMapper that maps InternalServiceException to 500 INTERNAL_SERVER_ERROR
 * @author WZ
 *
 */
@Provider
public class InternalServiceExceptionMapper implements ExceptionMapper<InternalServiceException> {
 
	private static final Log LOG = LogFactory.getLog(InternalServiceExceptionMapper.class);

    @Override
    public Response toResponse(InternalServiceException exception) {
    	LOG.error("Service Exception:", exception);

        ServiceExceptionMessage serviceExceptionDetails = new ServiceExceptionMessage(
        		Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
        		Response.Status.INTERNAL_SERVER_ERROR.toString(),
        		exception.getCause() !=null ? exception.getCause().getMessage() : "Internal Error");
        
       return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(serviceExceptionDetails).type(MediaType.APPLICATION_JSON).build();
    }
}