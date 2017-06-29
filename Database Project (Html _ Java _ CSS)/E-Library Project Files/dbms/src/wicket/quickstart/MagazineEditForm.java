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
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class MagazineEditForm extends Form {

	private String category = null;
	TextField<String> name = null;
	TextField<Integer> issue_number = null;
	NumberTextField<Integer> publish_year = null;
	Magazine inputmagazine = null;
	
	public MagazineEditForm(String id,Magazine inmagazine) {
		super(id);
		inputmagazine = inmagazine;
		category = inputmagazine.get_category_name();

		name = new TextField<String>("_name", Model.of(inputmagazine.get_name()));
		issue_number = new NumberTextField<Integer>("_issue_number", Model.of(inputmagazine
				.get_issue_number()));
		publish_year = new NumberTextField<Integer>("_publish_year",
				Model.of(inputmagazine.get_publish_year()));

		name.setRequired(true);
		issue_number.setRequired(true);
		publish_year.setRequired(true);

		this.add(name);
		this.add(issue_number);
		this.add(publish_year);

		final List categoryList = new ArrayList();
		try {
			DatabaseConnection dbc = new DatabaseConnection();
			String query = "SELECT magazine_category_name FROM magazine_categories ORDER BY magazine_category_name;";
			ResultSet cat = dbc.GetResult(query);

			while (cat.next()) {
				categoryList.add(cat.getString("magazine_category_name"));
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

	public void onSubmit() {
		Magazine magazine = new Magazine();
		magazine.set_magazine_id(inputmagazine.get_magazine_id());
		magazine.set_name(name.getModelObject());
		magazine.set_category_name(category);
		magazine.set_issue_number(issue_number.getModelObject());
		magazine.set_publish_year(publish_year.getModelObject());

		DatabaseConnection dbc = new DatabaseConnection();
		String query = "";

		try {
			dbc = new DatabaseConnection();
			query = String
					.format("SELECT magazine_category_id FROM magazine_categories WHERE magazine_category_name = '%s';",
							magazine.get_category_name());
			ResultSet result = dbc.GetResult(query);
			result.next();
			Integer cetagory_id = result.getInt(1);
			magazine.set_category_id(cetagory_id);

			dbc = new DatabaseConnection();
			query = String
					.format("UPDATE magazines SET magazine_name = '%s', magazine_issue_number = %d, "
							+ " magazine_publication_date = '%d-01-01', magazine_category_id = %d WHERE magazine_id = %d;",
							magazine.get_name(), magazine.get_issue_number(),
							magazine.get_publish_year(), magazine.get_category_id(), magazine.get_magazine_id());

			dbc.Insert(query);

		} catch (Exception DuplicateKeyException) {
			; // zaten varsa ekleme
		} finally {
			try {
				dbc.close();
				this.setResponsePage(new MagazinePage(magazine,""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}