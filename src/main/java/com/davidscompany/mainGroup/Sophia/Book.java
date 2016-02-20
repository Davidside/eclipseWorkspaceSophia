package com.davidscompany.mainGroup.Sophia;

import java.io.Serializable;

public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	private int bookID;
	private String name;
	private String author;
	private String category;
	private String description;
	private String imageURL;
	private int userID;
	
	public Book () {
	}
	
	public Book(String name, String author, String category, String description, String imageURL) {
		this.name = name;
		this.author = author;
		this.category = category;
		this.description = description;
		this.imageURL = imageURL;
	}
	
	public Book(int bookID, String name, String author, String category, String description, String imageURL) {
		this.bookID = bookID;
		this.name = name;
		this.author = author;
		this.category = category;
		this.description = description;
		this.imageURL = imageURL;
	}
	
	public Book(int bookID, String name, String author, String category, String description, String imageURL, int userID) {
		this.bookID = bookID;
		this.name = name;
		this.author = author;
		this.category = category;
		this.description = description;
		this.imageURL = imageURL;
		this.userID = userID;
	}
	
	
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthor() {
		return author;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory() {
		return category;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getUserID() {
		return userID;
	}
	
	@Override
	public String toString() {
		

		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("[");
		stringBuilder.append("Name: " + name);
		
		if(author!=null && (!author.equals(""))) {
			stringBuilder.append(", Author: " + author);
		}
		
		
		if(category!=null && (!category.equals(""))) {
			stringBuilder.append(", Category: " + category);
		}
		
		if(description!=null && (!description.equals(""))) {
			stringBuilder.append(", Description: " + description);
		}
		
		if(imageURL!=null && (!imageURL.equals(""))) {
			stringBuilder.append(", ImageURL: " + imageURL);
		}
		
		stringBuilder.append(", UserID: " + userID);
		
		stringBuilder.append("]");
		String result = stringBuilder.toString();
		return result;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + bookID;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((imageURL == null) ? 0 : imageURL.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + userID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (bookID != other.bookID)
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (imageURL == null) {
			if (other.imageURL != null)
				return false;
		} else if (!imageURL.equals(other.imageURL))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}
}
