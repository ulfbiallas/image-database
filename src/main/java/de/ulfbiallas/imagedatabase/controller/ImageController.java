package de.ulfbiallas.imagedatabase.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import de.ulfbiallas.imagedatabase.entities.Image;
import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.repository.ImageRecordRepository;
import de.ulfbiallas.imagedatabase.service.MetaInfoService;
import de.ulfbiallas.imagedatabase.tools.ImageMetaInfo;
import de.ulfbiallas.imagedatabase.tools.ImageProcessor;



@Path("image")
public class ImageController {

    private static final int PAGE_SIZE = 5;

    private final ImageRecordRepository imageRecordRepository;
    private final ImageProcessor imageProcessor;
    private final MetaInfoService metaInfoService;



    @Inject
    public ImageController(
        final ImageRecordRepository imageRecordRepository,
        final ImageProcessor imageProcessor,
        final MetaInfoService metaInfoService) {
        this.imageRecordRepository = imageRecordRepository;
        this.imageProcessor = imageProcessor;
        this.metaInfoService = metaInfoService;
    }



	@OPTIONS
	public Response options() {
		ResponseBuilder responseBuilder = Response.ok();
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
			@FormDataParam("tags") String tags,
			@HeaderParam("Content-Length") Integer contentLength
			) {

		System.out.println("upload image");
		System.out.println("fileName: " + fileDetails.getFileName());
		System.out.println("fileType: " + fileDetails.getType());
		System.out.println("fileSize: " + fileDetails.getSize());
		System.out.println("Content-Length: " + contentLength);
		System.out.println("caption: " + caption);
		System.out.println("description: " + description);
		System.out.println("tags: " + tags);
		System.out.println("");

		String id = imageProcessor.processImage(file, caption, description, tags, fileDetails.getFileName());

		ResponseBuilder responseBuilder;
		if(id != null) {
			responseBuilder = Response.status(Status.OK);
		} else {
			responseBuilder = Response.status(Status.INTERNAL_SERVER_ERROR);
		}

		return responseBuilder.build();
	}



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
    public Response getMetaInfosForAllImages(@QueryParam("page") int page) {
        List<ImageRecord> imageRecords = imageRecordRepository.findAll(new PageRequest(page, PAGE_SIZE)).getContent();
        List<ImageMetaInfo> imageMetaInfos = metaInfoService.getMetaInfosForImageRecords(imageRecords);
        ResponseBuilder responseBuilder = Response.status(Status.OK);
        responseBuilder.entity(imageMetaInfos);
        return responseBuilder.build();
    }



    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
    public Response getImage(@PathParam("id") String id) {
        ImageRecord imageRecord = imageRecordRepository.findById(id);
        ResponseBuilder responseBuilder = Response.status(Status.OK);
        responseBuilder.entity(imageRecord.getMetaInfo());
        return responseBuilder.build();     
    }



    @GET
    @Path("/{id}/view")
    @Produces("image/png")
    public Response viewImage(@PathParam("id") String id) {
        ImageRecord imageRecord = imageRecordRepository.findById(id);
        Image imageFile = imageRecord.getImage();
        InputStream inputStream = new ByteArrayInputStream(imageFile.getData());

        ResponseBuilder responseBuilder = Response.status(Status.OK);
        responseBuilder.header("Content-Type", "image/"+imageFile.getType());
        responseBuilder.entity(inputStream);
        return responseBuilder.build();		
    }



    @GET
    @Path("/{id}/view/thumbnail")
    @Produces("image/png")
    public Response viewImageAsThumbnail(@PathParam("id") String id) {
        ImageRecord imageRecord = imageRecordRepository.findById(id);
        Image imageFile = imageRecord.getThumbnail();
        InputStream inputStream = new ByteArrayInputStream(imageFile.getData());

        ResponseBuilder responseBuilder = Response.status(Status.OK);
        responseBuilder.header("Content-Type", "image/"+imageFile.getType());
        responseBuilder.entity(inputStream);
        return responseBuilder.build();		
    }



    @GET
    @Path("/{id}/similar")
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
    public Response getSimilarImages(@PathParam("id") String id) {
        List<ImageRecord> imageRecords = imageRecordRepository.findSimilarImages(id);
        List<ImageMetaInfo> imageMetaInfos = metaInfoService.getMetaInfosForImageRecords(imageRecords);
        ResponseBuilder responseBuilder = Response.status(Status.OK);
        responseBuilder.entity(imageMetaInfos);
        return responseBuilder.build();		
    }

}
