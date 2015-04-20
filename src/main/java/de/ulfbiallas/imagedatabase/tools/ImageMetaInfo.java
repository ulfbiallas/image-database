package de.ulfbiallas.imagedatabase.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.ulfbiallas.imagedatabase.entities.ImageRecord;

public class ImageMetaInfo {

	private String id;
	private String caption;
	private String description;
	private Date time;
	private Integer width;
	private Integer height;
	private String type;
	private List<String> tags;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public static List<ImageMetaInfo> getMetaInforsForImageRecords(Set<ImageRecord> imageRecords) {
		List<ImageMetaInfo> imageMetaInfos = new ArrayList<ImageMetaInfo>();
		Iterator<ImageRecord> imageRecordIterator = imageRecords.iterator();
		while(imageRecordIterator.hasNext()) {
			imageMetaInfos.add(imageRecordIterator.next().getMetaInfo());
		}
		return imageMetaInfos;
	}

}
