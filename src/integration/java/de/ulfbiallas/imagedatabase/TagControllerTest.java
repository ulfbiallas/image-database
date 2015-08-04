package de.ulfbiallas.imagedatabase;

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
import de.ulfbiallas.imagedatabase.tools.ImageMetaInfo;



public class TagControllerTest extends JerseyTest  {

    private TagRepository tagRepo;

    private ImageRecordRepository imageRepo;



    @Override
    protected Application configure() {
        tagRepo = Mockito.mock(TagRepository.class);
        imageRepo = Mockito.mock(ImageRecordRepository.class);
        MetaInfoService metaInfoService = Mockito.mock(MetaInfoService.class);
        TagController tagController = new TagController(tagRepo, imageRepo, metaInfoService);

        Mockito.when(tagRepo.findAll()).thenReturn(createListOfTags());

        Tag testTag = createTag();
        Mockito.when(tagRepo.findByName("test tag")).thenReturn(testTag);

        Mockito.when(metaInfoService.getMetaInfosForImageRecords(Mockito.anyList())).thenReturn(createListOfImageMetaInfos());

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



    @Test
    public void test_getImageRecordsByTag() {
        String tagString = "test+tag";
        List<ImageMetaInfo> expectedList = createListOfImageMetaInfos();

        Response response = target("tag/"+tagString).request().get();
        String list = response.readEntity(String.class);

        Mockito.verify(tagRepo).findByName("test tag");
        Mockito.verify(imageRepo).findImagesByTagId("myId");
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(convertToJson(expectedList), list);
    }



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

    private Tag createTag() {
        Tag tag = new Tag("test tag");
        tag.setId("myId");
        return tag;
    }

    private List<ImageMetaInfo> createListOfImageMetaInfos() {
        List<ImageMetaInfo> list = new ArrayList<ImageMetaInfo>();
        ImageMetaInfo imageMetaInfo1 = new ImageMetaInfo();
        imageMetaInfo1.setId("imi1");
        imageMetaInfo1.setDescription("test description");
        list.add(imageMetaInfo1);
        ImageMetaInfo imageMetaInfo2 = new ImageMetaInfo();
        imageMetaInfo2.setId("imi2");
        imageMetaInfo2.setDescription("another text");
        list.add(imageMetaInfo2);
        return list;
    }

}
