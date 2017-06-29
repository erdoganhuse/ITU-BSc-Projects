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

public class SearchForm extends Form {
	private CheckBox electronicCb;
	private CheckBox physicalCb;
	private CheckBox bookCb;
	private CheckBox magazinCb;
	private CheckBox dvdCb;
	private Label searchStatus;
	private TextField searchField;

	private String selectedMake = "ALL";
	private String selectedMake2 = "ALL";
	private final Map<String, List<String>> modelsMap = new HashMap<String, List<String>>();

	public String getSelectedMake() {
		return selectedMake;
	}

	public void setSelectedMake(String selectedMake) {
		this.selectedMake = selectedMake;
	}

	public String getSelectedMake2() {
		return selectedMake2;
	}

	public void setSelectedMake2(String selectedMake2) {
		this.selectedMake2 = selectedMake2;
	}

	public SearchForm(String id) {
		super(id);
		electronicCb = new CheckBox("electronic", Model.of(Boolean.FALSE));
		physicalCb = new CheckBox("physical", Model.of(Boolean.FALSE));
		searchStatus = new Label("searchStatus", Model.of(""));
		searchField = new TextField<>("searchField", Model.of(""));
		this.add(electronicCb);
		this.add(physicalCb);
		this.add(searchStatus);
		this.add(searchField);

		try {
			DatabaseConnection dbc = new DatabaseConnection();

			String query = "SELECT book_category_name FROM book_categories ORDER BY book_category_name;";
			ResultSet bookSet = dbc.GetResult(query);
			List bookList = new ArrayList();
			while (bookSet.next()) {
				bookList.add(bookSet.getString("book_category_name"));
			}

			query = "SELECT DVD_category_name FROM DVD_categories ORDER BY DVD_category_name;";
			ResultSet DVDSet = dbc.GetResult(query);
			List DVDList = new ArrayList();
			while (DVDSet.next()) {
				DVDList.add(DVDSet.getString("DVD_category_name"));
			}

			query = "SELECT magazine_category_name FROM magazine_categories ORDER BY magazine_category_name;";
			ResultSet magazineSet = dbc.GetResult(query);
			List magazineList = new ArrayList();
			while (magazineSet.next()) {
				magazineList.add(magazineSet
						.getString("magazine_category_name"));
			}

			bookList.add("ALL");
			DVDList.add("ALL");
			magazineList.add("ALL");
			List allList = new ArrayList();
			allList.add("ALL");

			modelsMap.put("Book", bookList);
			modelsMap.put("DVD", DVDList);
			modelsMap.put("Magazine", magazineList);
			modelsMap.put("ALL", allList);
			dbc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		IModel<List<? extends String>> makeChoices = new AbstractReadOnlyModel<List<? extends String>>() {
			@Override
			public List<String> getObject() {
				return new ArrayList<String>(modelsMap.keySet());
			}
		};

		IModel<List<? extends String>> modelChoices = new AbstractReadOnlyModel<List<? extends String>>() {
			@Override
			public List<String> getObject() {
				List<String> models = modelsMap.get(selectedMake);
				if (models == null) {
					models = Collections.emptyList();
				}
				return models;
			}
		};
		final DropDownChoice<String> makes = new DropDownChoice<String>(
				"type_name", new PropertyModel<String>(this, "selectedMake"),
				makeChoices);

		final DropDownChoice<String> models = new DropDownChoice<String>(
				"category_name", new PropertyModel<String>(this,
						"selectedMake2"), modelChoices);
		models.setOutputMarkupId(true);

		this.add(makes);
		this.add(models);
		makes.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(models);
			}
		});
	}

	public final void onSubmit() {
		String type_name = selectedMake;
		String category_name = selectedMake2;

		Boolean book = false;
		Boolean dvd = false;
		Boolean magazine = false;
		Integer category_id = null;

		try {
			DatabaseConnection dbc = new DatabaseConnection();
			if (type_name == "Book") {
				book = true;
				if (category_name == "ALL") {
					category_id = 0; // ALL seçilirse id 0 olur
				} else {
					String query = String
							.format("SELECT book_category_id FROM book_categories WHERE book_category_name = '%s';",
									category_name);
					ResultSet bookcat = dbc.GetResult(query);
					bookcat.next();
					category_id = bookcat.getInt("book_category_id");
				}
			} else if (type_name == "DVD") {
				dvd = true;
				if (category_name == "ALL") {
					category_id = 0; // ALL seçilirse id 0 olur
				} else {
					String query = String
							.format("SELECT DVD_category_id FROM DVD_categories WHERE DVD_category_name = '%s';",
									category_name);
					ResultSet dvdcat = dbc.GetResult(query);
					dvdcat.next();
					category_id = dvdcat.getInt("DVD_category_id");
				}
			} else if (type_name == "Magazine") {
				magazine = true;
				if (category_name == "ALL") {
					category_id = 0; // ALL seçilirse id 0 olur
				} else {
					String query = String
							.format("SELECT magazine_category_id FROM magazine_categories WHERE magazine_category_name = '%s';",
									category_name);
					ResultSet magazinecat = dbc.GetResult(query);
					magazinecat.next();
					category_id = magazinecat.getInt("magazine_category_id");
				}
			} else if (type_name == "ALL") {
				category_id = 0;
				book = true;
				dvd = true;
				magazine = true;
			}
			dbc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Boolean electronic = (Boolean) electronicCb.getDefaultModelObject();
		Boolean physical = (Boolean) physicalCb.getDefaultModelObject();

		String search = (String) searchField.getDefaultModelObject();
		if (electronic && physical && book && dvd && magazine) {
			searchStatus.setDefaultModelObject("True");

		} else {
			searchStatus.setDefaultModelObject("False");
			try {
				this.setResponsePage(new SearchPage(book, dvd, magazine,
						search, electronic, physical, category_id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}