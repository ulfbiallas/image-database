package de.ulfbiallas.imagedatabase.entities;

import java.util.List;

public interface TagDAO {

	public Tag getById(String id);
	public Tag getByName(String name);
	public List<Tag> getTags();
	public String save(Tag tag);
	public void update(Tag tag);
	public void delete(Tag tag);

}
