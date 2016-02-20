package com.davidscompany.mainGroup.Sophia;

import java.util.List;

public interface ManageBooksDao  {

	/**
	 * This is the method to be used to list down
	 * all  the records from user books table
	 * @param userID
	 * @return
	 */
	public List<Book> getBooks(Integer userID);
	
	/**
	 * This method to be used to create
	 * a record of the book in the user books table
	 * @param userID
	 * @param book
	 * @return
	 */
	public boolean addBook(Book book, Integer userID);
	
	/**
	 * This method to be used to delete
	 * a record of the book in the user books table
	 * @param userID
	 * @param bookID
	 * @return
	 */
	public boolean deleteBook(Integer bookID);
	
	/**
	 * This method to be used to update
	 * a record of the book in the user books table
	 * @param userID
	 * @param book
	 * @param bookID
	 * @return
	 */
	public boolean updateBook(Book book);
	
	/**
	 * This method to be used to get
	 * list of book's categories
	 * @return
	 */
	public List<String> getCategories();
}
