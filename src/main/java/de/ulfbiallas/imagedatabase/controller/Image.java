package de.ulfbiallas.imagedatabase.controller;

import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import de.ulfbiallas.imagedatabase.entities.ImageDAO;



@Path("image")
public class Image {

	private final ImageDAO imageDAO;



	@Inject
	public Image(final ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}



	@OPTIONS
	public Response createUserOptions() {
		ResponseBuilder responseBuilder = Response.ok();
		responseBuilder.header("Access-Control-Allow-Origin", "*");
		responseBuilder.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		responseBuilder.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
		return responseBuilder.build();		
	}



	@GET
	public Response test() {
		ResponseBuilder responseBuilder = Response.ok();
		responseBuilder.header("Access-Control-Allow-Origin", "*");
		responseBuilder.entity("Test");
		return responseBuilder.build();			
	}



	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(
			@FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition fileDetails,
			@FormDataParam("caption") String caption,
			@FormDataParam("description") String description,
			@HeaderParam("Content-Length") Integer contentLength
			) {

		System.out.println("upload image");
		System.out.println("fileName: " + fileDetails.getFileName());
		System.out.println("fileType: " + fileDetails.getType());
		System.out.println("fileSize: " + fileDetails.getSize());
		System.out.println("Content-Length: " + contentLength);
		System.out.println("caption: " + caption);
		System.out.println("description: " + description);

		ResponseBuilder responseBuilder = Response.status(Status.OK);
		responseBuilder.header("Access-Control-Allow-Origin", "*");
		return responseBuilder.build();
	}
	
}
