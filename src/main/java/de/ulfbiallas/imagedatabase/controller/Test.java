package de.ulfbiallas.imagedatabase.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;



@Path("test")
public class Test {

	@GET
	public Response test() {
		ResponseBuilder responseBuilder = Response.ok();
		responseBuilder.entity("Test");
		return responseBuilder.build();			
	}
	
}
