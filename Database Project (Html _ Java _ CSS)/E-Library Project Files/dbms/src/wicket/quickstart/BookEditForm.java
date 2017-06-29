package wicket.quickstart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class BookEditForm extends Form {

	private String category = null;

	TextField<String> name = null;
	TextField<String> author = null;
	TextField<String> ISBN = null;
	NumberTextField<Integer> publish_year = null;
	Book inputbook = null;
	

	@SuppressWarnings("unchecked")
	public BookEditForm(String id, Book inbook) {
		super(id);
		inputbook = inbook;
		category = inbook.get_category_name();
		
		name = new TextField<String>("_name",
				Model.of(inbook.get_name()));
		author = new TextField<String>("_author_name",
				Model.of(inbook.get_author_name()));
		ISBN = new TextField<String>("_ISBN",
				Model.of(inbook.get_ISBN()));
		publish_year = new NumberTextField<Integer>(
				"_publish_year",
				Model.of(inbook.get_publish_year()));	
		
		name.setRequired(true);
		author.setRequired(true);
		ISBN.setRequired(true);
		publish_year.setRequired(true);

		this.add(name);
		this.add(author);
		this.add(ISBN);
		this.add(publish_year);

		final List categoryList = new ArrayList();
		try {
			DatabaseConnection dbc = new DatabaseConnection();
			String query = "SELECT book_category_name FROM book_categories ORDER BY book_category_name;";
			ResultSet cat = dbc.GetResult(query);

			while (cat.next()) {
				categoryList.add(cat.getString("book_category_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		DropDownChoice<String> categories = new DropDownChoice<String>(
				"_category_id", new PropertyModel<String>(this, "category"),
				categoryList);
		this.add(categories);
	}

	public final void onSubmit() {
		Book book = new Book();
		book.set_book_id(inputbook.get_book_id());
		book.set_name(name.getModelObject());
		book.set_category_name(category);
		book.set_author_name(author.getModelObject());
		book.set_ISBN(ISBN.getModelObject());
		book.set_publish_year(publish_year.getModelObject());	
		
		DatabaseConnection dbc = new DatabaseConnection();
		String query = "";
		try {
			query = String.format(
					"INSERT INTO authors (author_name) VALUES ('%s');",
					book.get_author_name());
			dbc.Insert(query);
		} catch (Exception DuplicateKeyException) {
			; // yazar varsa bişey yapma
		}

		try {
			dbc = new DatabaseConnection();
			query = String.format(
					"SELECT author_id FROM authors WHERE author_name = '%s';",
					book.get_author_name());
			ResultSet result = dbc.GetResult(query);
			result.next();
			Integer author_id = result.getInt(1);

			dbc = new DatabaseConnection();
			query = String
					.format("SELECT book_category_id FROM book_categories WHERE book_category_name = '%s';",
							book.get_category_name());
			result = dbc.GetResult(query);
			result.next();
			Integer cetagory_id = result.getInt(1);
			book.set_category_id(cetagory_id);
		
			dbc = new DatabaseConnection();
			query = String
					.format("UPDATE books SET book_name = '%s', author_id = '%d', book_publication_year = '%d', " + 
							" book_ISBN = '%s', book_category_id = %d WHERE book_id = %d;",
							book.get_name(), author_id,
							book.get_publish_year(), book.get_ISBN(),
							book.get_category_id(), book.get_book_id());

			dbc.Insert(query);
		
		} catch (Exception DuplicateKeyException) {
			; // zaten varsa ekleme
		} finally {
			try {
				dbc.close();
				this.setResponsePage(new BookPage(book, ""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}