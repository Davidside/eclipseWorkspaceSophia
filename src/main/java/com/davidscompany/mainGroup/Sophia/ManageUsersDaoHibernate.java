package com.davidscompany.mainGroup.Sophia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

public class ManageUsersDaoHibernate implements ManageUsersDao, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ManageUsersDaoHibernate.class);
	private static SessionFactory factory = SessionFactoryBuilder.getSessionFactoryBuilder().getSessionFactory();
	// private List<User> usersList = new ArrayList<User>();
	
	public ManageUsersDaoHibernate() {		
	}
	
	// Method to READ existing User's password
	public String readUsersPassword(String userName) {
		Session session = factory.openSession();
		Transaction tx = null;
		String password = "";
		
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("SELECT U.userPassword FROM User U WHERE U.userName = :userName");
			query.setParameter("userName", userName);
			password = (String) query.uniqueResult();
			tx.commit();
			
			logger.info("Got password of the user [" + userName + "] from database.");
		} catch (HibernateException e) {
			logger.error("Cannot read password of the user [" + userName + "] from database.");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return password;
	}
	
	// Method to READ existing User's id
	public Integer readUsersID(String userName) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer userID = 0;
		
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("SELECT U.userID FROM User U WHERE U.userName = :userName");
			query.setParameter("userName", userName);
			userID = (Integer) query.uniqueResult();
			tx.commit();
			
			logger.info("Got ID of the user [" + userName + "] from database: [" + userID + "]");
		} catch (HibernateException e) {
			logger.error("Cannot read ID of the user [" + userName + "] from database.");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return userID;
	}
	
	// Method to CREATE user
	public boolean createUser(User user){
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();	
			
			logger.info("User with name [" + user.getUserName() + "] has been created.");
			return true;
			
		} catch (ConstraintViolationException e){
			logger.info("Cannot create user with name: [" + user.getUserName() +"], because this name already exists.");
		}
		catch (HibernateException e) {
			logger.error("Cannot create user with name: [" + user.getUserName() + "]");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}
	
	// Method to READ all users
	public List<User> readAllUsers() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<User> usersList = new ArrayList<User>();
		
		try {
			tx = session.beginTransaction();
			List<?> list = session.createQuery("FROM User").list();
			for (Object object : list) {
				User user = (User) object;
				usersList.add(user);
			}
			tx.commit();
			
			logger.info("Read all the users from database.");
		} catch (HibernateException e) {
			logger.error("Cannot read all the users from database.");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return usersList;
	}
	
	// Method to GET user's names list without name of the current user
	public List<String> getUserNames (String currentUser) {
		List<User> usersList = readAllUsers();
		List<String> userNames = new ArrayList<String>();
		
		userNames.add("Vyberte uživatele:");
		
		for(User user : usersList) {
			 String userName = user.getUserName();
			 if(!userName.equals(currentUser)) {
				 userNames.add(userName);
			 }
		}
		
		logger.info("Get user's names without name of the current user: [" + currentUser + "]");
		return userNames;
	}
}
