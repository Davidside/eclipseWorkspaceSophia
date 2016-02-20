package com.davidscompany.mainGroup.Sophia;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ManageBooksDaoHibernate implements ManageBooksDao, Serializable{

	private static final Logger logger = LogManager.getLogger(ManageBooksDaoHibernate.class);
	private static final long serialVersionUID = 1L;
	private static SessionFactory factory = SessionFactoryBuilder.getSessionFactoryBuilder().getSessionFactory();
	private List<String> categories = Arrays.asList("", "Beletrie", "Biografie", "Fantasy", "Filozofie",
			"Odborná literatura", "Psychologie",  "Sci-fi");
	
	public ManageBooksDaoHibernate() {
	}
	
	// Method to READ Books list of the specific user from database
	public List<Book> getBooks(Integer userID) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Book> booksList = new ArrayList<Book>();
		
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM Book B WHERE B.userID = :userID");
			query.setParameter("userID", userID);
			List<?> list = query.list();
			for(Object object : list) {
				Book book = (Book) object;
				booksList.add(book);
			}
			tx.commit();
			
			logger.info("Got books list from database of the user with id: [" + userID + "]");
		} catch (HibernateException e) {
			logger.error("Getting books list from database of the user with id [" + userID + "] has failed.");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return booksList;
	}
	
	// Method to CREATE an book in the database
	public boolean addBook(Book book, Integer userID) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			book.setUserID(userID);
			session.save(book);
			tx.commit();
			
			logger.info("The Book has been created: " + book);
			return true;
			
		} catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			logger.error("The Book creation has failed: " + book);
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}
	
	// Method to DELETE an book in the database
	public boolean deleteBook(Integer bookID) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			
			String hql = "DELETE FROM Book WHERE bookID = :bookID";
			Query query = session.createQuery(hql);
			query.setParameter("bookID", bookID);
			query.executeUpdate();	
			tx.commit();
			
			logger.info("The Book with BookID [" + bookID + "] has been deleted.");
			return true;
			
		} catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			logger.error("Deletion of the Book with BookID [" + bookID + "] has failed.");
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}
	
	// Method to UPDATE an book in the database
	public boolean updateBook(Book book) {
		Session session = factory.openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.update(book);
			tx.commit();
			
			logger.info("The Book has been updated: " + book);
			return true;
		} catch(HibernateException e) {
			if(tx!=null) tx.rollback();
			logger.error("Updating Book has failed: " + book);
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}
	
	// Method to READ categories of the book
	public List<String> getCategories () {
		return categories;
	}
}
