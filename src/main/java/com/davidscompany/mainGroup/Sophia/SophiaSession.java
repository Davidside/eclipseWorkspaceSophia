package com.davidscompany.mainGroup.Sophia;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class SophiaSession extends WebSession {

	private static final long serialVersionUID = 1L;
	private User user = new User();
	private ManageBooksDao manageBooksDao = new ManageBooksDaoHibernate();
	private ManageUsersDao manageUsersDao = new ManageUsersDaoHibernate();
	private String lastSelectedUserName;
	
	protected SophiaSession(Application application, Request request) {
		super(request);
	}
	
	public User getUser () {
		return user;
	}
	
	public ManageBooksDao getManageBooksDao() {
		return manageBooksDao;
	}
	public ManageUsersDao getManageUsersDao() {
		return manageUsersDao;
	}

	public String getLastSelectedUserName() {
		return lastSelectedUserName;
	}

	public void setLastSelectedUserName(String lastSelectedUserName) {
		this.lastSelectedUserName = lastSelectedUserName;
	}
}
