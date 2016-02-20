package com.davidscompany.mainGroup.Sophia;

import java.util.List;

public interface ManageUsersDao {

	/**
	 * This method to be used to 
	 * READ existing User's password
	 * @param userName
	 * @return
	 */
	public String readUsersPassword(String userName);
	
	/**
	 * This method to be used to 
	 * READ existing User's id
	 * @param userName
	 * @return
	 */
	public Integer readUsersID(String userName);
	
	/**
	 * This method to be used to
	 * create user
	 * @param user
	 * @return
	 */
	public boolean createUser(User user);
	
	/**
	 * This method to be used to
	 * READ all users
	 * @return
	 */
	public List<User> readAllUsers();
	
	/**
	 * This method to be used to
	 * GET user's names list without name
	 * of the current user
	 * @param currentUser
	 * @return
	 */
	public List<String> getUserNames (String currentUser);
}
