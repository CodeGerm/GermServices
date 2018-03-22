package org.cg.services.core.exception.mapper;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cg.services.core.exception.ServiceExceptionMessage;

/**
 * A ExceptionMapper that maps NotAuthorizedException to 401 UNAUTHORIZED
 * @author WZ
 *
 */
@Provider
public class AuthenticationFailureExceptionMapper implements ExceptionMapper<NotAuthorizedException> {
   
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFailureExceptionMapper.class);

    @Override
    public Response toResponse(NotAuthorizedException exception) {
    	LOG.info("Service Exception:", exception);

        ServiceExceptionMessage serviceExceptionDetails = new ServiceExceptionMessage(
        		Response.Status.UNAUTHORIZED.getStatusCode(),
        		Response.Status.UNAUTHORIZED.toString(),
        		exception.getChallenges().get(0)!=null ? exception.getChallenges().get(0).toString() : "Authentication Failure");
 
       return Response.status(Response.Status.UNAUTHORIZED).entity(serviceExceptionDetails).type(MediaType.APPLICATION_JSON).build();
    }

}