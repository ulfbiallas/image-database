package de.ulfbiallas.imagedatabase.test.integration;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import de.ulfbiallas.imagedatabase.controller.TagController;
import de.ulfbiallas.imagedatabase.entities.Tag;
import de.ulfbiallas.imagedatabase.repository.ImageRecordRepository;
import de.ulfbiallas.imagedatabase.repository.TagRepository;
import de.ulfbiallas.imagedatabase.service.MetaInfoService;

public class TagControllerTest extends JerseyTest  {

    @Override
    protected Application configure() {
        TagRepository tagRepo = Mockito.mock(TagRepository.class);
        ImageRecordRepository imageRepo = Mockito.mock(ImageRecordRepository.class);
        MetaInfoService metaInfoService = Mockito.mock(MetaInfoService.class);
        TagController tagController = new TagController(tagRepo, imageRepo, metaInfoService);

        Mockito.when(tagRepo.findAll()).thenReturn(createListOfTags());
//        Mockito.when(tagRepo.findByName("testTag")).thenReturn(new Tag("testTag"));

        ResourceConfig config = new ResourceConfig();
        config.register(JacksonFeature.class);
        config.register(tagController);
        config.property("contextConfigLocation", "classpath:testContext.xml");
        return config;
    }



    @Test
    public void test_getTags() {
        List<Tag> expectedList = createListOfTags();
        Response response = target("tag").request().get();
        String list = response.readEntity(String.class);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(convertToJson(expectedList), list);
    }


/*
    @Test
    public void getImageRecordsByTag() {
        Response response = target("tag/testTag").request().get();
    }
*/


    private List<Tag> createListOfTags() {
        List<Tag> list = new ArrayList<Tag>();
        list.add(new Tag("tag1"));
        list.add(new Tag("tag2"));
        list.add(new Tag("tag3"));
        return list;
    }

    private String convertToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        try {
            return writer.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
