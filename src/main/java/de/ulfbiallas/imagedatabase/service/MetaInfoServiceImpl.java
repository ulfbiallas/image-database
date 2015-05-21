package de.ulfbiallas.imagedatabase.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.tools.ImageMetaInfo;



@Component
public class MetaInfoServiceImpl implements MetaInfoService {

    @Override
    public List<ImageMetaInfo> getMetaInfosForImageRecords(List<ImageRecord> imageRecords) {
        List<ImageMetaInfo> imageMetaInfos = new ArrayList<ImageMetaInfo>();
        Iterator<ImageRecord> imageRecordIterator = imageRecords.iterator();
        while(imageRecordIterator.hasNext()) {
            imageMetaInfos.add(imageRecordIterator.next().getMetaInfo());
        }
        return imageMetaInfos;
    }

}
