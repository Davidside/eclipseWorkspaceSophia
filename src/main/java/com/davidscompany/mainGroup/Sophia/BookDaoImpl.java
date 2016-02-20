package com.davidscompany.mainGroup.Sophia;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookDaoImpl implements BookDao, Serializable {

	private static final long serialVersionUID = 1L;
	private String url = "jdbc:mysql://localhost/mojedatabaze?useSSL=false";
	private String login = "root";
	private String password = "1234";
	
	private List<Book> bookList  = new ArrayList<Book>();
	private List<String> categories = Arrays.asList("", "Beletrie", "Biografie", "Fantasy", "Filozofie",
			"Odborná literatura", "Psychologie",  "Sci-fi");	
	
	// JDBC Methods
	@Override
	public List<Book> getBooks(Integer userID) {
		bookList.clear();
		String queryLine = "SELECT * FROM `books-" + userID + "`";
		
		try (Connection conn = DriverManager.getConnection(url, login, password);
			 PreparedStatement query = conn.prepareStatement(queryLine);
			 ResultSet result = query.executeQuery();) {
			
			while(result.next()) {
				int bookID = result.getInt(1);
				String name = result.getString("name");
				String author = result.getString("author");
				String category = result.getString("category");
				String description = result.getString("description");
				String imageURL = result.getString("imageURL");
				bookList.add(new Book(bookID, name, author, category, description, imageURL));
			}
		}
		catch (SQLException ex) {
	        System.out.println("Chyba při komunikaci s databází při načítání user booklistu.");
		}
		return bookList;
	}

	@Override
	public boolean createBook(Integer userID, Book book) {
		String queryLine = "INSERT INTO `books-" + userID + "` (name, author, category, description, imageURL) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(url, login, password);
		     PreparedStatement query = conn.prepareStatement(queryLine)) {
			query.setString(1, book.getName());
			query.setString(2, book.getAuthor());
			query.setString(3, book.getCategory());
			query.setString(4, book.getDescription());
			query.setString(5, book.getImageURL());
	        query.executeUpdate();
	        return true;
		}
		catch (SQLException ex) {
	        System.out.println("Chyba při komunikaci s databází při ukládání knihy do databáze. " + ex);
		}
		return false;
	}

	@Override
	public boolean deleteBook(Integer userID, Integer bookID) {
		String queryLine = "DELETE FROM `books-" + userID + "` WHERE BookID=?";
		
		try (Connection conn = DriverManager.getConnection(url, login, password);
		     PreparedStatement query = conn.prepareStatement(queryLine)) {
			query.setInt(1, bookID);
	        query.executeUpdate();
	        return true;
		}
		catch (SQLException ex) {
	        System.out.println("Chyba při komunikaci s databází při mazání knihy z databáze. " + ex);
		}
		return false;
	}

	@Override
	public boolean updateBook(Integer userID, Book book, Integer bookID) {
		String queryLine = "UPDATE `books-" + userID + "` SET name=?, author=?, category=?, description=?, imageURL=? WHERE BookID=?";
		
		try (Connection conn = DriverManager.getConnection(url, login, password);
			     PreparedStatement query = conn.prepareStatement(queryLine)) {
				query.setString(1, book.getName());
				query.setString(2, book.getAuthor());
				query.setString(3, book.getCategory());
				query.setString(4, book.getDescription());
				query.setString(5, book.getImageURL());
				query.setInt(6, bookID);
		        query.executeUpdate();
		        return true;
			}
			catch (SQLException ex) {
		        System.out.println("Chyba při komunikaci s databází při editaci knihy v databázi. " + ex);
			}
			return false;
	}
	
	public List<String> getCategories () {
		return categories;
	}
	
}
