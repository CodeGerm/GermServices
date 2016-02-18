package org.cg.services.core.swagger.example;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.cg.services.core.exception.ServiceExceptionMessage;

/**
 * A Facad for CGCoreService
 * @author WZ
 *
 */
@Api(value = "CG Core Service: Nothing useful here")
@Path("/v1.0")
public interface CGCoreService {

	@POST
	@Path("/greetings")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Greetings", 
				  notes = "Not very usefull")
	@ApiResponses(value = {
			@ApiResponse(code = 400, response = ServiceExceptionMessage.class, message = "Invalid parameter supplied"),
			@ApiResponse(code = 404, response = ServiceExceptionMessage.class, message = "No resources found"),
			@ApiResponse(code = 200, response = GreetingMessage.class, message = "OK")  
	})
	GreetingMessage greetings(
			@ApiParam(value = "The greeting message", required = true)
			@QueryParam("message")
			String message,
			@ApiParam(value = "Pass true to format the timestamp", required = false)
			@QueryParam("format_time") 
			Boolean formatTime);
}
