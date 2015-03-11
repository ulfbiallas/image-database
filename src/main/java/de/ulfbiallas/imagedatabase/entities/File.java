package de.ulfbiallas.imagedatabase.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="class")
public class File {

	@Id
	@Column
	private String id;
	
	@Lob
	@Column
	private byte[] data;

	@Column
	private Integer size;
	
	@Column
	private String type;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}