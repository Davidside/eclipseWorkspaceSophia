package com.davidscompany.mainGroup.Sophia;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryBuilder implements Serializable {

	private static final long serialVersionUID = 1L;
	private static SessionFactoryBuilder sessionFactoryBuilder = new SessionFactoryBuilder();
	private static SessionFactory sessionFactory;
	
	private SessionFactoryBuilder() {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Failed to create sessionFactory object. " + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactoryBuilder getSessionFactoryBuilder() {
		return sessionFactoryBuilder;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	

}
