package com.davidscompany.mainGroup.Sophia;

import org.apache.wicket.markup.html.WebPage;

public class SophiaPage extends WebPage {
	
	private static final long serialVersionUID = 1L;

	public SophiaPage() {
    }
	
	public SophiaSession getSophiaSession() {
		return (SophiaSession) getSession();
	}
	
	public User getUser() {
		return getSophiaSession().getUser();
	}
	
	public ManageBooksDao getManageBooksDao() {
		return getSophiaSession().getManageBooksDao();
	}
	
	public ManageUsersDao getManageUsersDao() {
		return getSophiaSession().getManageUsersDao();
	}
	
	public String getLastSelectedUserName() {
		return getSophiaSession().getLastSelectedUserName();
	}
	
	public void setLastSelectedUserName(String selectedUserName) {
		getSophiaSession().setLastSelectedUserName(selectedUserName);
	}
	
}
