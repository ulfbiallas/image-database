package de.ulfbiallas.imagedatabase.controller;

import java.util.List;
import java.util.Set;

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
import de.ulfbiallas.imagedatabase.repository.TagRepository;
import de.ulfbiallas.imagedatabase.tools.ImageMetaInfo;


@Path("tag")
public class TagController {


	private final TagRepository tagRepository;



	@Inject
	public TagController(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
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

		Set<ImageRecord> imageRecords = tag.getImageRecords();
		List<ImageMetaInfo> imageMetaInfos = ImageMetaInfo.getMetaInforsForImageRecords(imageRecords);

		ResponseBuilder responseBuilder = Response.status(Status.OK);
		responseBuilder.entity(imageMetaInfos);
		return responseBuilder.build();		
	}

}
