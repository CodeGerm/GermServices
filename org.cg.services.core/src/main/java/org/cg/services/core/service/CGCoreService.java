/**
 * 
 */
package org.cg.services.core.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * A example service resource
 * @author WZ
 * 
 */
public class CGCoreService {

	@GET
	@Path("/ping/{message}")
	@Produces(MediaType.TEXT_PLAIN)
	public String ping (@PathParam("message") String message) {
		return message;
	}
}
