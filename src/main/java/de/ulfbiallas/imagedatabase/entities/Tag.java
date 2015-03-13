package de.ulfbiallas.imagedatabase.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.ulfbiallas.imagedatabase.tools.ImageMetaInfo;



@Entity
public class Tag {

	@Id
	@Column
	private String id;
	
	@Column
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date time;

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

}