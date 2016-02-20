package com.davidscompany.mainGroup.Sophia;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

public class SophiaApplication extends WebApplication {
	public SophiaApplication() {
	}
	
	@Override
	public void init(){  
	}
	
	public Class<LoginPage> getHomePage() {
		return LoginPage.class;
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new SophiaSession(SophiaApplication.this, request);
	}
}