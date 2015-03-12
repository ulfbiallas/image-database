package de.ulfbiallas.imagedatabase.entities;

import java.util.List;

public interface ImageDAO {

	public Image getById(String id);
	public List<Image> getImages();
	public String save(Image image);
	public void update(Image image);
	public void delete(Image image);

}
