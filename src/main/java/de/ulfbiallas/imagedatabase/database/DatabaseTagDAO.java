package de.ulfbiallas.imagedatabase.database;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.Tag;
import de.ulfbiallas.imagedatabase.entities.TagDAO;



@Component
public class DatabaseTagDAO implements TagDAO {

	private final DatabaseConnection databaseConnection;



	@Inject
	public DatabaseTagDAO(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}



	public Tag getById(String id) {
		Session session = databaseConnection.getSession();
		return (Tag) session.get(Tag.class, id);
	}



	public Tag getByName(String name) {
		Session session = databaseConnection.getSession();
		return (Tag) session.get(Tag.class, name);
	}



	public List<Tag> getTags() {
		Session session = databaseConnection.getSession();
		Criteria criteria = session.createCriteria(Tag.class);
		List<Tag> list = criteria.list();
		return list;
	}



	public String save(Tag tag) {
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(tag);
			tx.commit();
			return tag.getId();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		   return null;
		}
	}



	public void update(Tag tag) {
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(tag);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}



	public void delete(Tag tag) {
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(tag);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}

}
