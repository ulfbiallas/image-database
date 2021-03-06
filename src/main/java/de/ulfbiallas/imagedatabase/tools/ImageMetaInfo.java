package de.ulfbiallas.imagedatabase.tools;

import java.util.Date;
import java.util.List;



public class ImageMetaInfo {

	private String id;
	private String caption;
	private String description;
	private Date time;
	private Integer width;
	private Integer height;
	private String type;
	private List<String> tags;
	private Double score;
    private String imageUrl;
    private String thumbnailUrl;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		setImageUrl("image/"+id+"/view");
		setThumbnailUrl("image/"+id+"/view/thumbnail");
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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
