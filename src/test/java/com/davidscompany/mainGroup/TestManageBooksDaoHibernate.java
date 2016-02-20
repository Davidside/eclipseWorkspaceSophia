package com.davidscompany.mainGroup;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.davidscompany.mainGroup.Sophia.Book;
import com.davidscompany.mainGroup.Sophia.ManageBooksDao;
import com.davidscompany.mainGroup.Sophia.ManageBooksDaoHibernate;
import com.davidscompany.mainGroup.Sophia.SessionFactoryBuilder;

public class TestManageBooksDaoHibernate {

	private static SessionFactory factory;
	private ManageBooksDao manageBooksDao;
	private Book book;
	private List<Book> expectedBooksList;
	
	@BeforeClass
	public static void BeforeClass() {
		// Create new SessionFactory
		factory = SessionFactoryBuilder.getSessionFactoryBuilder().getSessionFactory();
		
		// CREATE Testing Table in database
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String SQL = "CREATE TABLE books_test LIKE books";
			SQLQuery query = session.createSQLQuery(SQL);
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	@Before
	public void setUp() {
		// Create new ManageBooks Object
		manageBooksDao = new ManageBooksDaoHibernate();
		
		// Create new instance of the book and add it to the list
		book = new Book(1 ,"Navstivila ji smrt", "John McKain", "Beletrie", 
				"Uzasna kniha", "http://book.jpg", 2);
		expectedBooksList = new ArrayList<Book>();
		expectedBooksList.add(book);
		
		// Create some data in Table
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(book);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	// Test to CREATE a book in testing table
	@Test
	public void shouldAbleToCreateNewBook() {
		Book book = new Book("Nova kniha", "autor knihy", "Beletrie", "Popisek", "Obrazek");
		Integer userID = 2;
		
		boolean isBookCreated = manageBooksDao.addBook(book, userID);
		assertTrue(isBookCreated);
	}
	
	// Test to DELETE a book in testing table
	@Test
	public void shouldAbleToDeleteBook() {
		boolean isBookDeleted = manageBooksDao.deleteBook(1);
		assertTrue(isBookDeleted);
	}
	
	// Test to UPDATE a book in testing table
	@Test
	public void shouldAbleToUpdateBook() {
		Book book = new Book(1, "Zmena nazvu", "Jiny Autor", "Fantasy", "Bez popisku", "Jine");
		
		boolean isBookUpdated = manageBooksDao.updateBook(book);
		assertTrue(isBookUpdated);
	}
	
	// Test to READ books from the testing table
	@Test
	public void shouldAbleToReadBooks() {
		List<Book> actualBooksList = manageBooksDao.getBooks(2);
		
		for (int i = 0; i < actualBooksList.size(); i++) {
			Book actualBook = (Book) actualBooksList.get(i);
			Book expectedBook = (Book) expectedBooksList.get(i);
			assertEquals(expectedBook, actualBook);
		}
		
		
	}
	
	@After
	public void tearDown() {
		
		// Clear book reference and books list
		book = null;
		expectedBooksList = null;
		
		// Reset table
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String SQL = "TRUNCATE TABLE books_test";
			SQLQuery query = session.createSQLQuery(SQL);
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		// Clear manageBooks
		manageBooksDao = null;
	}
	
	@AfterClass
	public static void AfterClass() {
		
		// DROP Testing Table in database
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String SQL = "DROP TABLE books_test";
			SQLQuery query = session.createSQLQuery(SQL);
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		// Clear Factory
		factory = null;
	}
}
