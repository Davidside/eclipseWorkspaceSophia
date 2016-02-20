package com.davidscompany.mainGroup.Sophia;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersReader implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<User> usersList = new ArrayList<User>();
	private List<Book> friendBooklist = new ArrayList<Book>();
	private String userName = "Vyberte uživatele:";
	
	public void setUsers() {
		usersList.clear();
		
		String url = "jdbc:mysql://localhost/mojedatabaze?useSSL=false";
		String queryLine = "SELECT userID, userName FROM users";
		
		try (Connection conn = DriverManager.getConnection(url, "root", "1234");
			 PreparedStatement query = conn.prepareStatement(queryLine);
			 ResultSet result = query.executeQuery();) {
			
			while(result.next()) {
				int userID = result.getInt(1);
				String userName = result.getString("userName");
				usersList.add(new User(userID, userName));
			}
		}
		catch (SQLException ex) {
	        System.out.println("Chyba při komunikaci s databází při načítání usersListu.");
		}
	}
	
	public void setFriendBooklist(String userName) {
		if(userName.equals("Vyberte uživatele:")) {
			return;
		}
		this.userName = userName;
		int userID = 0;
		for(User user : usersList) {
			 String userNameInList = user.getUserName();
			 if(userNameInList.equals(userName)) {
				 userID = user.getUserID();
				 break;
			 }
		}
		
		String url = "jdbc:mysql://localhost/mojedatabaze?useSSL=false";
		String queryLine = "SELECT * FROM `books-" + userID + "`";
		
		try (Connection conn = DriverManager.getConnection(url, "root", "1234");
			 PreparedStatement query = conn.prepareStatement(queryLine);
			 ResultSet result = query.executeQuery();) {
			
			
			while(result.next()) {
				int bookID = result.getInt(1);
				String name = result.getString("name");
				String author = result.getString("author");
				String category = result.getString("category");
				String description = result.getString("description");
				String imageURL = result.getString("imageURL");
				friendBooklist.add(new Book(bookID, name, author, category, description, imageURL));
			}
		}
		catch (SQLException ex) {
	        System.out.println("Chyba při komunikaci s databází při načítání friend booklistu. " + ex);
		}
	}
	
	public List<String> getUsersNames (String activeUser) {
		List<String> usersNames = new ArrayList<String>();
		usersNames.add("Vyberte uživatele:");
		for(User user : usersList) {
			 String userName = user.getUserName();
			 if(!userName.equals(activeUser)) {
				 usersNames.add(userName);
			 }
		}
		return usersNames;
	}
	
	public List<User> getUsersList() {
		return usersList;
	}
	
	public List<Book> getFriendBooklist() {
		return friendBooklist;
	}
	
	public String getUserName() {
		return userName;
	}
}
