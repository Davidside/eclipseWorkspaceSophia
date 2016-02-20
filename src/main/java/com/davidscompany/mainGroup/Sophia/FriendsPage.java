package com.davidscompany.mainGroup.Sophia;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

public class FriendsPage extends SophiaPage {

	private static final long serialVersionUID = 1L;
	private ListChoice<String> friendsChoice;
	private Label message;
	private boolean isMessageVisible = false;
	private ManageUsersDao manageUsersDao = getManageUsersDao();
	private ManageBooksDao manageBooksDao = getManageBooksDao();
	
	public FriendsPage() {
		// DetachableModel for booksList
		final IModel<List<Book>> booksList = new LoadableDetachableModel<List<Book>>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Book> load() {
				return manageBooksDao.getBooks(manageUsersDao.readUsersID(getLastSelectedUserName()));
			}
		};
		
		// List of users choices in the field
		friendsChoice = new ListChoice<String>("friendsChoice", new Model<String>(getLastSelectedUserName()), 
												manageUsersDao.getUserNames(getUser().getUserName()));
		message = new Label("message", new Model<String>("")) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return isMessageVisible;
			}
		};
		
		
		Form<Object> form = new Form<Object>("formSelectFriend");
		add(form);
		
		// BACK buton
		form.add(new Link<Object>("backLink") {
	
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(SophiaIndex.class);
			}
		});
		
		form.add(friendsChoice);
		form.add(new Button("submit"){

			private static final long serialVersionUID = 1L;

			@Override
			// Selection Button
			public void onSubmit() {
				String selectedUserName = (String)friendsChoice.getModelObject();
				
				// User selected name in the choice field
				if(!selectedUserName.equals("Vyberte uživatele:")) {
					setLastSelectedUserName(selectedUserName);
					
					if(booksList.getObject().isEmpty()) {
						message.setDefaultModelObject("Uživatelův seznam knih je prázdný.");
						isMessageVisible = true;
					}
				} 
				
				// User selected choice "Vyberte uživatele:"
				else {
					message.setDefaultModelObject("Vyberte ze seznamu uživatele.");
					isMessageVisible = true;
					friendsChoice.setModelObject(getLastSelectedUserName());
				}
			}
		});
		
		add(message);
		
		// Books list of the selected user
		add(new ListView<Book>("booklist", booksList) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Book> item) {
				Book book = (Book) item.getModelObject();
				
				item.add(new Label("nameBook", book.getName()));
				item.add(new Label("authorBook", book.getAuthor()));
				item.add(new Label("categoryBook", book.getCategory()));
				item.add(new MultiLineLabel("descriptionBook", book.getDescription()));
				
				WebMarkupContainer markup = new WebMarkupContainer("bookImage");
				markup.add(new AttributeModifier("src", new Model<String>(book.getImageURL())));
				item.add(markup);
			}
		});
	}
	
	@Override
	protected void onAfterRender() {
		if(isMessageVisible) {
			isMessageVisible = false;
		}

		super.onAfterRender();
	}

}
