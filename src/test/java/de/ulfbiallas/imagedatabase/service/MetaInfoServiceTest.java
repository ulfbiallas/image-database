package de.ulfbiallas.imagedatabase.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.tools.ImageMetaInfo;

public class MetaInfoServiceTest {

    private MetaInfoService SUT;

    @Before
    public void init() {
        SUT = new MetaInfoServiceImpl();
    }

    @Test
    public void testGetMetaInfosForImageRecords() {
        List<ImageRecord> imageRecords = createImageRecords();

        List<ImageMetaInfo> imageMetaInfos = SUT.getMetaInfosForImageRecords(imageRecords);

        ImageMetaInfo imagaMetaInfo0 = imageMetaInfos.get(0);
        assertEquals("test if id of first meta info is as expected", "42345-35", imagaMetaInfo0.getId());
        assertEquals("test if caption of first meta info is as expected", "testRecord", imagaMetaInfo0.getCaption());
        assertEquals("test if description of first meta info is as expected", "this is a test record", imagaMetaInfo0.getDescription());

        ImageMetaInfo imagaMetaInfo1 = imageMetaInfos.get(1);
        assertEquals("test if id of second meta info is as expected", "wrgerg4523", imagaMetaInfo1.getId());
        assertEquals("test if caption of second meta info is as expected", "anotherEntry", imagaMetaInfo1.getCaption());
        assertEquals("test if description of second meta info is as expected", "yet another record for testing", imagaMetaInfo1.getDescription());
    }

    private List<ImageRecord> createImageRecords() {
        List<ImageRecord> imageRecords = new ArrayList<ImageRecord>();
        imageRecords.add(createImageRecord("42345-35", "testRecord", "this is a test record"));
        imageRecords.add(createImageRecord("wrgerg4523", "anotherEntry", "yet another record for testing"));
        return imageRecords;
    }

    private ImageRecord createImageRecord(String id, String caption, String description) {
        ImageMetaInfo imageMetaInfo = new ImageMetaInfo();
        imageMetaInfo.setId(id);
        imageMetaInfo.setCaption(caption);
        imageMetaInfo.setDescription(description);

        ImageRecord imageRecord = mock(ImageRecord.class);
        Mockito.when(imageRecord.getMetaInfo()).thenReturn(imageMetaInfo);
        return imageRecord;
    }

}
