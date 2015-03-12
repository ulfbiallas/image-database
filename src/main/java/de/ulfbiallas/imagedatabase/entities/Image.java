package de.ulfbiallas.imagedatabase.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import de.ulfbiallas.imagedatabase.tools.ImageMetaInfo;



@Entity
public class Image extends File {

	@Column
	private int width;
	
	@Column
	private int height;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ImageMetaInfo getMetaInfo() {
		ImageMetaInfo metaInfo = new ImageMetaInfo();
		metaInfo.setId(getId());
		metaInfo.setTime(getTime());
		metaInfo.setWidth(getWidth());
		metaInfo.setHeight(getHeight());
		metaInfo.setType(getType());
		return metaInfo;
	}

}