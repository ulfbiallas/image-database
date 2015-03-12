package de.ulfbiallas.imagedatabase.entities;

import java.util.List;

public interface FileDAO {

	public File getById(String id);
	public List<File> getFiles();
	public String save(File file);
	public void update(File file);
	public void delete(File file);

}
