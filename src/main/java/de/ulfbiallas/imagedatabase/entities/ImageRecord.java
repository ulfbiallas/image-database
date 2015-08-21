package de.ulfbiallas.imagedatabase.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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

	@OneToOne
	private Image thumbnail;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Tag> tags = new ArrayList<Tag>();

	@OneToOne
	private Feature feature;

    private Double score;

    @OneToMany
    private List<Comment> comments;


    
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

	public Image getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
		metaInfo.setScore(getScore());
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