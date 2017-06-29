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

public class DVDEditForm extends Form {

	private String category = null;
	TextField<String> name = null;
	TextField<Integer> duration = null;
	NumberTextField<Integer> publish_year = null;
	Dvd inputdvd = null;

	public DVDEditForm(String id, Dvd indvd) {
		super(id);
		inputdvd = indvd;
		category = inputdvd.get_category_name();

		name = new TextField<String>("_name", Model.of(inputdvd.get_name()));
		duration = new NumberTextField<Integer>("_duration", Model.of(inputdvd
				.get_duration()));
		publish_year = new NumberTextField<Integer>("_publish_year",
				Model.of(inputdvd.get_publish_year()));

		name.setRequired(true);
		duration.setRequired(true);
		publish_year.setRequired(true);

		this.add(name);
		this.add(duration);
		this.add(publish_year);

		final List categoryList = new ArrayList();
		try {
			DatabaseConnection dbc = new DatabaseConnection();
			String query = "SELECT DVD_category_name FROM DVD_categories ORDER BY DVD_category_name;";
			ResultSet cat = dbc.GetResult(query);

			while (cat.next()) {
				categoryList.add(cat.getString("DVD_category_name"));
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
		Dvd dvd = new Dvd();
		dvd.set_DVD_id(inputdvd.get_DVD_id());
		dvd.set_name(name.getModelObject());
		dvd.set_category_name(category);
		dvd.set_duration(duration.getModelObject());
		dvd.set_publish_year(publish_year.getModelObject());

		DatabaseConnection dbc = new DatabaseConnection();
		String query = "";

		try {
			dbc = new DatabaseConnection();
			query = String
					.format("SELECT DVD_category_id FROM DVD_categories WHERE DVD_category_name = '%s';",
							dvd.get_category_name());
			ResultSet result = dbc.GetResult(query);
			result.next();
			Integer cetagory_id = result.getInt(1);
			dvd.set_category_id(cetagory_id);

			dbc = new DatabaseConnection();
			query = String
					.format("UPDATE DVDs SET DVD_name = '%s', DVD_duration = %d, "
							+ " DVD_publication_date = '%d-01-01', DVD_category_id = %d WHERE DVD_id = %d;",
							dvd.get_name(), dvd.get_duration(),
							dvd.get_publish_year(), dvd.get_category_id(), dvd.get_DVD_id());

			dbc.Insert(query);

		} catch (Exception DuplicateKeyException) {
			; // zaten varsa ekleme
		} finally {
			try {
				dbc.close();
				this.setResponsePage(new DvdPage(dvd,""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}