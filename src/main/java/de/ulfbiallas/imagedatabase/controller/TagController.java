package de.ulfbiallas.imagedatabase.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.entities.Tag;
import de.ulfbiallas.imagedatabase.repository.ImageRecordRepository;
import de.ulfbiallas.imagedatabase.repository.TagRepository;
import de.ulfbiallas.imagedatabase.tools.ImageMetaInfo;


@Path("tag")
public class TagController {


	private final TagRepository tagRepository;

	private final ImageRecordRepository imageRecordRepository;

	@Inject
	public TagController(final TagRepository tagRepository, final ImageRecordRepository imageRecordRepository) {
		this.tagRepository = tagRepository;
		this.imageRecordRepository = imageRecordRepository;
	}



	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@JacksonFeatures(serializationEnable = { SerializationFeature.INDENT_OUTPUT })
	public Response getTags() {
		List<Tag> tags = tagRepository.findAll();
		ResponseBuilder responseBuilder = Response.status(Status.OK);
		responseBuilder.entity(tags);
		return responseBuilder.build();
	}



	@GET
	@Path("/{tag}")
	@Produces(MediaType.APPLICATION_JSON)
	@JacksonFeatures(serializationEnable = { SerializationFeature.INDENT_OUTPUT })
	public Response getImageRecordsByTag(@PathParam("tag") String tagString) {

		tagString = tagString.replaceAll("\\+", " ");
		Tag tag = tagRepository.findByName(tagString);

		List<ImageRecord> imageRecords = imageRecordRepository.findImagesByTagId(tag.getId());
		List<ImageMetaInfo> imageMetaInfos = ImageMetaInfo.getMetaInfosForImageRecords(imageRecords);

		ResponseBuilder responseBuilder = Response.status(Status.OK);
		responseBuilder.entity(imageMetaInfos);
		return responseBuilder.build();		
	}

}
