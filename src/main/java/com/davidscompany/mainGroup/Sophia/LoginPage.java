package com.davidscompany.mainGroup.Sophia;

import java.security.NoSuchAlgorithmException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class LoginPage extends SophiaPage {

	private static final long serialVersionUID = 1L;
	
	private static final JavaScriptResourceReference sjcl = new JavaScriptResourceReference(LoginPage.class, "javaScript/sjcl.js");
	private static final JavaScriptResourceReference sha256 = new JavaScriptResourceReference(LoginPage.class, "javaScript/sha256.js");
	private static final JavaScriptResourceReference codecBase64 = new JavaScriptResourceReference(LoginPage.class, "javaScript/codecBase64.js");
	private static final JavaScriptResourceReference bitArray = new JavaScriptResourceReference(LoginPage.class, "javaScript/bitArray.js");
	private static final JavaScriptResourceReference codecString = new JavaScriptResourceReference(LoginPage.class, "javaScript/codecString.js");
	private static final JavaScriptResourceReference hashPass = new JavaScriptResourceReference(LoginPage.class, "javaScript/hashPass.js");
	
	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(JavaScriptReferenceHeaderItem.forReference(sjcl));
		response.render(JavaScriptReferenceHeaderItem.forReference(sha256));
		response.render(JavaScriptReferenceHeaderItem.forReference(codecBase64));
		response.render(JavaScriptReferenceHeaderItem.forReference(bitArray));
		response.render(JavaScriptReferenceHeaderItem.forReference(codecString));
	    response.render(JavaScriptReferenceHeaderItem.forReference(hashPass));
	}
	
	private TextField<String> nameField;
	private PasswordTextField passwordField;
	private Button loginButton;
	private Label registerMessage;
	private PasswordTextField passwordAgainField;
	private PasswordTextField hashedPassword;
	private PasswordTextField hashedPasswordAgain;
	private Link<Object> registerButton;
	private Label message;
	private boolean isMessageVisible = false;
	private ManageUsersDao manageUsersDao = getManageUsersDao();

	
	public LoginPage() {
		Form<Object> form = new Form<Object>("form");
		add(form);
		nameField = new TextField<String>("nameField", new Model<String>(""));
		passwordField = new PasswordTextField("passwordField", new Model<String>(""));
		hashedPassword = new PasswordTextField("hashedPassword", new Model<String>(""));
		loginButton = new Button("loginButton") {

			private static final long serialVersionUID = 1L;

			@Override
			// BUTTON 
			public void onSubmit() {
				// find out if user want to login or register
				if(registerMessage.isVisible()) {
					// REGISTER of THE USER:
					// get userName and user hashed password
					// and user hashed password again
					// after submit
					String userName = (String) nameField.getModelObject();
					String userHashedPassword = (String) hashedPassword.getModelObject();
					hashedPassword.setModelObject("");
					String userHashedPasswordAgain = (String) hashedPasswordAgain.getModelObject();
					hashedPasswordAgain.setModelObject("");
					
					if (userHashedPassword.equals(userHashedPasswordAgain)) {
						getUser().setUserName(userName);
						
						// Hashing the password to Sha256 digest 
						// and encode it to BASE64 to read it as a 
						// String 
						Sha256 sha256 = new Sha256();
						try {
							userHashedPassword = sha256.hash(userHashedPassword);
						} catch (NoSuchAlgorithmException e) {
							System.out.println("Chyba pri hashovani" + e);
						}
						getUser().setUserPassword(userHashedPassword);
						
						// write the user to database
						if(manageUsersDao.createUser(getUser())) {
							setLastSelectedUserName("Vyberte uživatele:");
							setResponsePage(new SophiaIndex());
						}
						else {
							message.setDefaultModelObject("Zadané jméno je již používané, vyberte prosím jiné.");
							isMessageVisible = true;
						}
					}
					else {
						message.setDefaultModelObject("Zadané heslo se neshoduje.");
						isMessageVisible = true;
					}
				}
				else {
					// USER LOGIN: get userName and user hashed password
					// after submit
					String userName = (String) nameField.getModelObject();
					String userHashedPassword = (String) hashedPassword.getModelObject();
					hashedPassword.setModelObject("");
					
					getUser().setUserName(userName);
					
					// Hashing the password to Sha256 digest 
					// and encode it to BASE64 to read it as a 
					// String 
					Sha256 sha256 = new Sha256();
					try {
						userHashedPassword = sha256.hash(userHashedPassword);
					} catch (NoSuchAlgorithmException e) {
						System.out.println("Chyba pri kryptovani" + e);
					}
					getUser().setUserPassword(userHashedPassword);
					
					// get the user hashed password from database
					// String userPasswordFromDatabase = getUser().getFromDatabase();
					String userPasswordFromDatabase = manageUsersDao.readUsersPassword(getUser().getUserName());
	
					// compare hashed password from TextField with
					// hashed password from database
					if(userPasswordFromDatabase.equals(getUser().getUserPassword())) {
						getUser().setUserID(manageUsersDao.readUsersID(getUser().getUserName()));
						setLastSelectedUserName("Vyberte uživatele:");
						setResponsePage(new SophiaIndex());
					}
					else {
						message.setDefaultModelObject("Nesprávné jméno nebo heslo.");
						isMessageVisible = true;
					}
				}
			}
		};
		
		registerMessage = new Label("registerMessage", "Heslo znovu:");
		registerButton = new Link<Object>("registerButton") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				if(!registerMessage.isVisible()) {
					registerMessage.setVisible(true);
					passwordAgainField.setVisible(true);
					registerButton.add(new AttributeModifier("value", "Zpět"));
					loginButton.add(new AttributeModifier("value", "Potvrdit"));
					loginButton.add(new AttributeModifier("onClick", "retrieveRegisterPassword()"));
				}
				else {
					registerMessage.setVisible(false);
					passwordAgainField.setVisible(false);
					registerButton.add(new AttributeModifier("value", "Registrovat se"));
					loginButton.add(new AttributeModifier("value", "Přihlásit se"));
					loginButton.add(new AttributeModifier("onClick", "retrievePassword()"));
				}
			}
		};
		
		passwordAgainField = new PasswordTextField("passwordAgainField", new Model<String>(""));
		hashedPasswordAgain = new PasswordTextField("hashedPasswordAgain", new Model<String>(""));
		
		form.add(nameField.setRequired(true));
		form.add(passwordField.setRequired(true));
		form.add(loginButton);
			loginButton.add(new AttributeModifier("onClick", "retrievePassword()"));
		form.add(registerMessage);
			registerMessage.setVisible(false);
		form.add(passwordAgainField.setRequired(true));
			passwordAgainField.setVisible(false);
		form.add(registerButton);
		form.add(hashedPassword.setRequired(false));
		form.add(hashedPasswordAgain.setRequired(false));
	
		message = new Label("message", new Model<String>("")) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return isMessageVisible;
			}
		};
		add(message);
		
		add(new FeedbackPanel("feedback"));
		
		
	}
	
	@Override
	protected void onAfterRender() {
		if(isMessageVisible) {
			isMessageVisible = false;
		}

		super.onAfterRender();
	}
	
}
