package de.ulfbiallas.imagedatabase.entities;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;



@Entity
public class Feature {

	@Id
	@Column
	private String id;

	@OneToOne(mappedBy="feature")
	private ImageRecord imageRecord;

	@ElementCollection
	@CollectionTable(name="hueHistogram", joinColumns=@JoinColumn(name="id"))
	@Column(name="value")
	private Map<Integer, Double> hueHistogram;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ImageRecord getImageRecord() {
		return imageRecord;
	}

	public void setImageRecord(ImageRecord imageRecord) {
		this.imageRecord = imageRecord;
	}

	public Map<Integer, Double> getHueHistogram() {
		return hueHistogram;
	}

	public void setHueHistogram(Map<Integer, Double> hueHistogram) {
		this.hueHistogram = hueHistogram;
	}

}
