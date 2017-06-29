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

public class AddDVDForm extends Form {
	Dvd dvd = new Dvd();
	Common common = new Common();
	private String selected = "Film";

	@SuppressWarnings("unchecked")
	public AddDVDForm(String id) {
		super(id);
		CompoundPropertyModel model = new CompoundPropertyModel(dvd);
		this.setDefaultModel(model);

		this.add(new TextField("_name"));
		this.add(new NumberTextField("_publish_year"));
		this.add(new NumberTextField("_duration"));

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
				"_category_id", new PropertyModel<String>(this, "selected"),
				categoryList);
		this.add(categories);

	}

	public final void onSubmit() {
		Dvd dvd = (Dvd) this.getModelObject();

		DatabaseConnection dbc = new DatabaseConnection();
		String query = "";

		try {
			query = String
					.format("SELECT DVD_category_id FROM DVD_categories WHERE DVD_category_name = '%s';",
							selected);
			ResultSet result = dbc.GetResult(query);
			result.next();
			Integer cetagory_id = result.getInt(1);
			dvd.set_category_id(cetagory_id);

			dbc = new DatabaseConnection();
			query = String
					.format("INSERT INTO DVDs (DVD_name, DVD_duration, DVD_publication_date, DVD_category_id) VALUES ('%s', '%d', '%d-01-01', '%d');",
							dvd.get_name(), dvd.get_duration(),
							dvd.get_publish_year(), dvd.get_category_id());

			dbc.Insert(query);

		} catch (Exception DuplicateKeyException) {
			; // zaten varsa ekleme
		} finally {
			try {
				dbc = new DatabaseConnection();
				query = String
						.format("SELECT DVD_id FROM DVDs WHERE (DVD_name = '%s') AND (DVD_publication_date = '%d-01-01');",
								dvd.get_name(), dvd.get_publish_year());
				ResultSet result = dbc.GetResult(query);
				result.next();
				dvd.set_DVD_id(result.getInt(1)); // her türlü DVD_id bul
				dbc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			common.set_type_id(dvd.get_type_id());
			common.set_x_id(dvd.get_DVD_id());
			common.set_name(dvd.get_name());
			this.setResponsePage(new SourcePlacedPage(common,false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
