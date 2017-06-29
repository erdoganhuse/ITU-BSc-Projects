package wicket.quickstart;

import java.awt.Button;
import java.awt.Checkbox;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class AddBookForm extends Form {
	Common common = new Common();
	Book book = new Book();
	private String selected = "Bilim";

	@SuppressWarnings("unchecked")
	public AddBookForm(String id) {
		super(id);
		CompoundPropertyModel model = new CompoundPropertyModel(book);
		this.setDefaultModel(model);

		this.add(new TextField("_name"));
		this.add(new TextField("_author_name"));
		this.add(new NumberTextField("_publish_year"));
		this.add(new TextField("_ISBN"));

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
				"_category_id", new PropertyModel<String>(this, "selected"),
				categoryList);
		this.add(categories);

	}

	public final void onSubmit() {
		Book book = (Book) this.getModelObject();

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
							selected);
			result = dbc.GetResult(query);
			result.next();
			Integer cetagory_id = result.getInt(1);
			book.set_category_id(cetagory_id);

			dbc = new DatabaseConnection();
			query = String
					.format("INSERT INTO books (book_name, author_id, book_publication_year, book_ISBN, book_category_id) VALUES ('%s', '%d', '%d', '%s', '%d');",
							book.get_name(), author_id,
							book.get_publish_year(), book.get_ISBN(),
							book.get_category_id());

			dbc.Insert(query);

		} catch (Exception DuplicateKeyException) {
			; // zaten varsa ekleme
		} finally {
			try {
				dbc = new DatabaseConnection();
				query = String.format("SELECT book_id FROM books WHERE book_ISBN = '%s';",book.get_ISBN());
				ResultSet result = dbc.GetResult(query);
				result.next();
				book.set_book_id(result.getInt(1));	// her türlü book_id bul
				dbc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			common.set_type_id(book.get_type_id());
			common.set_x_id(book.get_book_id());
			common.set_name(book.get_name());
			this.setResponsePage(new SourcePlacedPage(common,false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}