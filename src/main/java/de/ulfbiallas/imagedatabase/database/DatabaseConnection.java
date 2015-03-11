package de.ulfbiallas.imagedatabase.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.stereotype.Component;



@Component
public class DatabaseConnection {

	private SessionFactory sf;
	private Session session;

	public DatabaseConnection() {
		sf = new AnnotationConfiguration().configure("hibernate.cfg.xml").buildSessionFactory();			
		session = sf.openSession();		
	}
	
	public Session getSession() {
		return session;
	}
	
	public void destroy() {
		if(session.isOpen()) session.close();
		if(!sf.isClosed()) sf.close();
	}

}