package de.ulfbiallas.imagedatabase.service;

import java.util.List;

import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.tools.ImageMetaInfo;

public interface MetaInfoService {

    List<ImageMetaInfo> getMetaInfosForImageRecords(List<ImageRecord> imageRecords);

}
