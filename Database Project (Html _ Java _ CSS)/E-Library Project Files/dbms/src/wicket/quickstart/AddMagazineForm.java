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
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class AddMagazineForm extends Form {
	Magazine magazine = new Magazine();
	Common common = new Common();
	private String selected = "Bilim";

	@SuppressWarnings("unchecked")
	public AddMagazineForm(String id) {
		super(id);
		CompoundPropertyModel model = new CompoundPropertyModel(magazine);
		this.setDefaultModel(model);

		this.add(new TextField("_name"));
		this.add(new NumberTextField("_publish_year"));
		this.add(new NumberTextField("_issue_number"));

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
				"_category_id", new PropertyModel<String>(this, "selected"),
				categoryList);
		this.add(categories);

	}

	public final void onSubmit() {
		Magazine magazine = (Magazine) this.getModelObject();

		DatabaseConnection dbc = new DatabaseConnection();
		String query = "";

		try {
			query = String
					.format("SELECT magazine_category_id FROM magazine_categories WHERE magazine_category_name = '%s';",
							selected);
			ResultSet result = dbc.GetResult(query);
			result.next();
			Integer cetagory_id = result.getInt(1);
			magazine.set_category_id(cetagory_id);

			dbc = new DatabaseConnection();
			query = String
					.format("INSERT INTO magazines (magazine_name, magazine_issue_number, magazine_publication_date, magazine_category_id) VALUES ('%s', '%d', '%d-01-01', '%d');",
							magazine.get_name(), magazine.get_issue_number(),
							magazine.get_publish_year(),
							magazine.get_category_id());

			dbc.Insert(query);

		} catch (Exception DuplicateKeyException) {
			; // zaten varsa ekleme
		} finally {
			try {
				dbc = new DatabaseConnection();
				query = String
						.format("SELECT magazine_id FROM magazines WHERE ((magazine_name = '%s') AND (magazine_publication_date = '%d-01-01') AND (magazine_issue_number = '%d'));",
								magazine.get_name(),
								magazine.get_publish_year(),
								magazine.get_issue_number());
				ResultSet result = dbc.GetResult(query);
				result.next();
				magazine.set_magazine_id(result.getInt(1)); // her türlü  magazine_id bul
				dbc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			common.set_type_id(magazine.get_type_id());
			common.set_x_id(magazine.get_magazine_id());
			common.set_name(magazine.get_name());
			this.setResponsePage(new SourcePlacedPage(common,false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
