package de.ulfbiallas.imagedatabase.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Tag {

	@JsonIgnore
	@Id
	@Column
	private String id;
	
	@Column(unique=true)
	private String name;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date time;

	@JsonIgnore
	@ManyToMany(mappedBy="tags")
	private List<ImageRecord> imageRecords;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<ImageRecord> getImageRecords() {
		return imageRecords;
	}

	public void setImageRecords(List<ImageRecord> imageRecords) {
		this.imageRecords = imageRecords;
	}

	public Integer getPopularity() {
		return getImageRecords().size();
	}

}