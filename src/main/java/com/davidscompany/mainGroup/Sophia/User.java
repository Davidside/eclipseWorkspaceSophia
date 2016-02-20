package com.davidscompany.mainGroup.Sophia;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private int userID;
	private String userName;
	private String userPassword;
	
	public User() {	
	}
	
	public User(int userID, String userName){
		this.userID = userID;
		this.userName = userName;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getUserID () {
		return userID;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserPassword() {
		return userPassword;
	}
}
