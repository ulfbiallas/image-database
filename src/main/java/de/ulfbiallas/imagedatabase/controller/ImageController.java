package de.ulfbiallas.imagedatabase.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import de.ulfbiallas.imagedatabase.entities.Image;
import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.entities.ImageRecordDAO;
import de.ulfbiallas.imagedatabase.tools.ImageMetaInfo;
import de.ulfbiallas.imagedatabase.tools.ImageProcessor;



@Path("image")
public class ImageController {

	private final ImageRecordDAO imageRecordDAO;
	private final ImageProcessor imageProcessor;



	@Inject
	public ImageController(final ImageRecordDAO imageRecordDAO, final ImageProcessor imageProcessor) {
		this.imageRecordDAO = imageRecordDAO;
		this.imageProcessor = imageProcessor;
	}



	@OPTIONS
	public Response options() {
		ResponseBuilder responseBuilder = Response.ok();
		responseBuilder.header("Access-Control-Allow-Origin", "*");
		responseBuilder.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		responseBuilder.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
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
		System.out.println("");

		String id = imageProcessor.processImage(file, caption, description, fileDetails.getFileName());

		ResponseBuilder responseBuilder;
		if(id != null) {
			responseBuilder = Response.status(Status.OK);
		} else {
			responseBuilder = Response.status(Status.INTERNAL_SERVER_ERROR);
		}

		responseBuilder.header("Access-Control-Allow-Origin", "*");
		return responseBuilder.build();
	}



	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
	public Response getMetaInfosForAllImages() {
		
		List<ImageRecord> imageRecords = imageRecordDAO.getImageRecords();
		
		List<ImageMetaInfo> imageMetaInfos = new ArrayList<ImageMetaInfo>();
		for(int k=0; k<imageRecords.size(); ++k) {
			imageMetaInfos.add(imageRecords.get(k).getMetaInfo());
		}
				
		ResponseBuilder responseBuilder = Response.status(Status.OK);
		responseBuilder.header("Access-Control-Allow-Origin", "*");
		responseBuilder.entity(imageMetaInfos);
		return responseBuilder.build();		
	}



	@GET
	@Path("/{id}")
	@Produces("image/png")
	public Response showImage(@PathParam("id") String id) {
		ImageRecord imageRecord = imageRecordDAO.getById(id);
		Image imageFile = imageRecord.getImage();
		InputStream inputStream = new ByteArrayInputStream(imageFile.getData());

		ResponseBuilder responseBuilder = Response.status(Status.OK);
		responseBuilder.header("Access-Control-Allow-Origin", "*");
		responseBuilder.header("Content-Type", "image/"+imageFile.getType());
		responseBuilder.entity(inputStream);
		return responseBuilder.build();		
	}
}
