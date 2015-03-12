package de.ulfbiallas.imagedatabase.entities;

import java.util.List;

public interface ImageRecordDAO {

	public ImageRecord getById(String id);
	public List<ImageRecord> getImageRecords();
	public String save(ImageRecord imageRecord);
	public void update(ImageRecord imageRecord);
	public void delete(ImageRecord imageRecord);

}
