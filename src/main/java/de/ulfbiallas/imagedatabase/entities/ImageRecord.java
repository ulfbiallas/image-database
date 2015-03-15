package de.ulfbiallas.imagedatabase.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.ulfbiallas.imagedatabase.tools.ImageMetaInfo;



@Entity
public class ImageRecord {

	@Id
	@Column
	private String id;
	
	@Column
	private String caption;

	@Column
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date time;

	@OneToOne
	private Image image;

	@ManyToMany
	private List<Tag> tags;



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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public ImageMetaInfo getMetaInfo() {
		ImageMetaInfo metaInfo = new ImageMetaInfo();
		metaInfo.setId(getId());
		metaInfo.setCaption(getCaption());
		metaInfo.setDescription(getDescription());
		metaInfo.setTime(getTime());
		metaInfo.setWidth(getImage().getWidth());
		metaInfo.setHeight(getImage().getHeight());
		metaInfo.setType(getImage().getType());
		metaInfo.setTags(convertTagsToStrings(getTags()));
		return metaInfo;
	}

	private List<String> convertTagsToStrings(List<Tag> tags) {
		List<String> tagsAsString = new ArrayList<String>();
		for (Tag tag: tags) {
			tagsAsString.add(tag.getName());
		}
		return tagsAsString;
	}
}