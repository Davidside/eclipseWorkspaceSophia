package com.davidscompany.mainGroup.Sophia;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

public class SophiaIndex extends SophiaPage {

	private static final long serialVersionUID = 1L;
	private Label message;
	private boolean isMessageVisible = false;
	private TextField<String> name;
	private TextField<String> author;
	private ListChoice<String> category;
	private TextField<String> imageURL;
	private TextArea<String> description;
	private Button submit;
	private Book book = new Book();
	private int editingBookID;
	private AttributeModifier addBookCaption = new AttributeModifier("value", "Vložit knihu");
	private AttributeModifier saveChangesCaption = new AttributeModifier("value", "Uložit změny");
	private AttributeModifier submitCaption;
	private ManageBooksDao manageBooksDao = getManageBooksDao();
	
	public SophiaIndex() {
		Form<String> form = new Form<String>("form");
		add(form);
		setDefaultModel (new CompoundPropertyModel<Book>(book));
		name = new TextField<String>("name");
		author = new TextField<String>("author");
		category = new ListChoice<String>("category", manageBooksDao.getCategories());
		imageURL = new TextField<String>("imageURL");
		description = new TextArea<String>("description");
		submit = new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			// Add book button to create a new book
			public void onSubmit() {
				if(submitCaption.equals(addBookCaption)) {
			
					boolean isBookCreated = manageBooksDao.addBook(book, getUser().getUserID());
								
					name.setModelObject("");
					author.setModelObject("");
					category.setModelObject("");
					imageURL.setModelObject("");
					description.setModelObject("");
					if(isBookCreated) {
						message.setDefaultModelObject("Kniha byla úspěšně vložena.");
						isMessageVisible = true;
					}
					else {
						message.setDefaultModelObject("Knihu se nepodařilo úspěšně vložit.");
						isMessageVisible = true;
					}
				}
				// Edit book button to update a book
				else {
					
					boolean isBookEdited = manageBooksDao.updateBook(book);
					
					name.setModelObject("");
					author.setModelObject("");
					category.setModelObject("");
					imageURL.setModelObject("");
					description.setModelObject("");
					
					submitCaption = addBookCaption;
					submit.add(submitCaption);
					editingBookID = 0;
					
					if(isBookEdited) {
						message.setDefaultModelObject("Kniha byla úspěšně editována.");
						isMessageVisible = true;
					}
					else {
						message.setDefaultModelObject("Knihu se nepodařilo úspěšně editovat.");
						isMessageVisible = true;
					}

				}
				
				
			}
		};
		
		form.add(new Link<Object>("friendsLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new FriendsPage());
			}
		});
		form.add(name.setRequired(true));
		form.add(author);
		form.add(category);
		category.setModelObject("");
		form.add(imageURL);
		form.add(description);
		form.add(submit);
		submitCaption = addBookCaption;
		submit.add(submitCaption);
		
		message = new Label("message", new Model<String>("")) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return isMessageVisible;
			}
		};
		
		// Message panel and feedback panel
		add(message);
		add(new FeedbackPanel("feedback"));
		
		// DetachableModel for bookslist
		final IModel<List<Book>> booksList = new LoadableDetachableModel<List<Book>>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Book> load() {
				return manageBooksDao.getBooks(getUser().getUserID());
			}
		};
		
		
		// List of the books
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
				
				// REMOVE Button
				item.add(new Link<Book>("remove", item.getModel()) {
					
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						
						Book selected = (Book) getModelObject();
						
						if(editingBookID != selected.getBookID()) {
							boolean isBookRemoved = manageBooksDao.deleteBook(selected.getBookID());
							booksList.detach();
							
							if(isBookRemoved) {
								message.setDefaultModelObject("Kniha byla vymazána.");
								isMessageVisible = true;
							}
							else {
								message.setDefaultModelObject("Knihu se nepodařilo vymazat.");
								isMessageVisible = true;
							}
						}
						else {
							message.setDefaultModelObject("Nemůžete vymazat knihu, kterou právě editujete. Nejdřive uložte změny.");
							isMessageVisible = true;
						}
					}
				});
				
				// Edit Button
				item.add(new Link<Book>("edit", item.getModel()) {
					
					private static final long serialVersionUID = 1L;

						@Override
						public void onClick() {
							Book selected = (Book) getModelObject();
							editingBookID = selected.getBookID();
							name.setModelObject(selected.getName());
							author.setModelObject(selected.getAuthor());
							category.setModelObject(selected.getCategory());
							imageURL.setModelObject(selected.getImageURL());
							description.setModelObject(selected.getDescription());
							submitCaption = saveChangesCaption;
							submit.add(submitCaption);
						}
				});
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
