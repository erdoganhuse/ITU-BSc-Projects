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
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class SourcePlacedForm extends Form {
	private String selectedMake;
	private String selectedMake2;
	private String selectedMake3;
	private String selectedMake4;
	private final Map<String, List<String>> modelsMap = new HashMap<String, List<String>>();
	private final Map<String, List<String>> modelsMap2 = new HashMap<String, List<String>>();
	private final Map<String, List<String>> modelsMap3 = new HashMap<String, List<String>>();

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

	public String getSelectedMake3() {
		return selectedMake3;
	}

	public void setSelectedMake3(String selectedMake3) {
		this.selectedMake3 = selectedMake3;
	}

	public String getSelectedMake4() {
		return selectedMake4;
	}

	public void setSelectedMake4(String selectedMake4) {
		this.selectedMake4 = selectedMake4;
	}

	protected Common c = null;

	public SourcePlacedForm(String id, final Common common, Boolean checkUrl) {
		super(id);
		c = common;

		Label sourcename = new Label("sourcename", c.get_name());
		add(sourcename);

		CompoundPropertyModel model = new CompoundPropertyModel(common);
		this.setDefaultModel(model);
		TextField url = new TextField("_url");

		Label librarylabel = new Label("librarylabel", "Library Name: ");
		Label floorlabel = new Label("floorlabel", "Library Floor: ");
		Label bookcaselabel = new Label("bookcaselabel", "Bookcase: ");
		Label bookshelflabel = new Label("bookshelflabel", "Bookshelf: ");
		Label columnlabel = new Label("columnlabel", "Column(1-50): ");
		Label urllabel = new Label("urllabel", "URL: ");

		try {
			DatabaseConnection dbclib = new DatabaseConnection();
			String query = "SELECT * FROM libraries;";
			ResultSet lib = dbclib.GetResult(query);
			while (lib.next()) {
				DatabaseConnection dbcfloor = new DatabaseConnection();
				query = String.format(
						"SELECT * FROM floors WHERE library_id = %d;",
						lib.getInt("library_id"));
				ResultSet floor = dbcfloor.GetResult(query);
				List<String> floorList = new ArrayList<String>();
				while (floor.next()) {
					DatabaseConnection dbccase = new DatabaseConnection();
					query = String.format(
							"SELECT * FROM bookcases WHERE floor_id = %d;",
							floor.getInt("floor_id"));
					ResultSet cases = dbccase.GetResult(query);
					List<String> casesList = new ArrayList<String>();
					while (cases.next()) {
						DatabaseConnection dbcsehlf = new DatabaseConnection();
						query = String
								.format("SELECT * FROM bookshelves WHERE bookcase_id = %d;",
										cases.getInt("bookcase_id"));
						ResultSet shelf = dbcsehlf.GetResult(query);
						List<String> shelfList = new ArrayList<String>();
						while (shelf.next()) {
							shelfList.add(shelf.getString("bookshelf_name"));
						}
						dbcsehlf.close();

						casesList.add(cases.getString("bookcase_name"));
						modelsMap3.put(cases.getString("bookcase_name"),
								shelfList);
					}
					dbccase.close();

					floorList.add(floor.getString("floor_name"));
					modelsMap2.put(floor.getString("floor_name"), casesList);
				}
				dbcfloor.close();

				modelsMap.put(lib.getString("library_name"), floorList);
			}
			dbclib.close();

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
				"_library_name",
				new PropertyModel<String>(this, "selectedMake"), makeChoices);

		final DropDownChoice<String> models = new DropDownChoice<String>(
				"_floor_name",
				new PropertyModel<String>(this, "selectedMake2"), modelChoices);
		models.setOutputMarkupId(true);

		makes.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(models);
			}
		});

		IModel<List<? extends String>> makeChoices2 = new AbstractReadOnlyModel<List<? extends String>>() {
			@Override
			public List<String> getObject() {
				return new ArrayList<String>(modelsMap2.keySet());
			}
		};
		IModel<List<? extends String>> modelChoices2 = new AbstractReadOnlyModel<List<? extends String>>() {
			@Override
			public List<String> getObject() {
				List<String> models = modelsMap2.get(selectedMake2);
				if (models == null) {
					models = Collections.emptyList();
				}
				return models;
			}
		};
		final DropDownChoice<String> models2 = new DropDownChoice<String>(
				"_bookcase_name", new PropertyModel<String>(this,
						"selectedMake3"), modelChoices2);
		models2.setOutputMarkupId(true);

		models.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {

				target.add(models2);
			}
		});
		IModel<List<? extends String>> makeChoices3 = new AbstractReadOnlyModel<List<? extends String>>() {
			@Override
			public List<String> getObject() {
				return new ArrayList<String>(modelsMap3.keySet());
			}
		};
		IModel<List<? extends String>> modelChoices3 = new AbstractReadOnlyModel<List<? extends String>>() {
			@Override
			public List<String> getObject() {
				List<String> models = modelsMap3.get(selectedMake3);
				if (models == null) {
					models = Collections.emptyList();
				}
				return models;
			}
		};
		final DropDownChoice<String> models3 = new DropDownChoice<String>(
				"_bookshelf_name", new PropertyModel<String>(this,
						"selectedMake4"), modelChoices3);
		models3.setOutputMarkupId(true);

		models2.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {

				target.add(models3);
			}
		});

		Link urlLink = new Link("urlLink") {

			@Override
			public void onClick() {
				this.setResponsePage(new SourcePlacedPage(common, true));
			}

		};

		this.add(url);
		this.add(librarylabel);
		this.add(floorlabel);
		this.add(bookcaselabel);
		this.add(bookshelflabel);
		this.add(columnlabel);
		this.add(urllabel);
		this.add(new NumberTextField("_column").setRequired(false));
		this.add(makes);
		this.add(models);
		this.add(models2);
		this.add(models3);
		this.add(urlLink);

		DatabaseConnection dbc = new DatabaseConnection();
		try {
			String query = String
					.format("SELECT * FROM records WHERE ((type_id = %d) AND (x_id = %d) AND (`physical-electronic` = 0))",
							common.get_type_id(), common.get_x_id());
			ResultSet rs = dbc.GetResult(query);

			if (!checkUrl) {
				url.setVisible(false);
				urllabel.setVisible(false);
			} else {
				librarylabel.setVisible(false);
				floorlabel.setVisible(false);
				bookcaselabel.setVisible(false);
				bookshelflabel.setVisible(false);
				columnlabel.setVisible(false);
				makes.setVisible(false);
				models.setVisible(false);
				models2.setVisible(false);
				models3.setVisible(false);
				this.get("_column").setVisible(false);
				urlLink.setVisible(false);
			}
			if (rs.next()) {
				urlLink.setVisible(false);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public final void onSubmit() {
		Common common = (Common) this.getModelObject();

		c.set_library_name(selectedMake);
		c.set_floor_name(selectedMake2);
		c.set_bookcase_name(selectedMake3);
		c.set_bookshelf_name(selectedMake4);

		String duplicate_error = "";

		DatabaseConnection dbc = new DatabaseConnection();
		String query = "";
		try {
			if (c.get_library_name() != null && c.get_floor_name() != null
					&& c.get_bookcase_name() != null
					&& c.get_bookshelf_name() != null) {
				dbc = new DatabaseConnection();
				query = String
						.format("INSERT INTO records (`type_id`, `x_id`, `physical-electronic`) VALUES ('%d', '%d', %s);",
								c.get_type_id(), c.get_x_id(), "True"); // fiziksel
				dbc.Insert(query);

				dbc = new DatabaseConnection();
				query = "SELECT MAX(record_id) FROM records;";
				ResultSet result = dbc.GetResult(query);
				result.next();
				c.set_record_id(result.getInt(1));

				dbc = new DatabaseConnection();
				query = String
						.format("SELECT library_id FROM libraries WHERE library_name = '%s';",
								c.get_library_name());
				result = dbc.GetResult(query);
				result.next();
				c.set_library_id(result.getInt(1));

				dbc = new DatabaseConnection();
				query = String
						.format("SELECT floor_id FROM floors WHERE (floor_name = '%s') AND (library_id = '%d');",
								c.get_floor_name(), c.get_library_id());
				result = dbc.GetResult(query);
				result.next();
				c.set_floor_id(result.getInt(1));

				dbc = new DatabaseConnection();
				query = String
						.format("SELECT bookcase_id FROM bookcases WHERE (bookcase_name = '%s') AND (floor_id = '%d');",
								c.get_bookcase_name(), c.get_floor_id());
				result = dbc.GetResult(query);
				result.next();
				c.set_bookcase_id(result.getInt(1));

				dbc = new DatabaseConnection();
				query = String
						.format("SELECT bookshelf_id FROM bookshelves WHERE (bookshelf_name = '%s') AND (bookcase_id = '%d');",
								c.get_bookshelf_name(), c.get_bookcase_id());
				result = dbc.GetResult(query);
				result.next();
				c.set_bookshelf_id(result.getInt(1));

				dbc = new DatabaseConnection();
				query = String
						.format("INSERT INTO physical_resources (record_id, bookshelf_id, book_column) VALUES ('%d', '%d', '%d');",
								c.get_record_id(), c.get_bookshelf_id(),
								c.get_column());
				dbc.Insert(query);
			}
			if (c.get_url() != null) {
				dbc = new DatabaseConnection();
				query = String
						.format("INSERT INTO records (`type_id`, `x_id`, `physical-electronic`) VALUES ('%d', '%d', %s);",
								c.get_type_id(), c.get_x_id(), "False"); // elektronik
				dbc.Insert(query);

				dbc = new DatabaseConnection();
				query = "SELECT MAX(record_id) FROM records;";
				ResultSet result = dbc.GetResult(query);
				result.next();
				c.set_record_id(result.getInt(1));

				dbc = new DatabaseConnection();
				query = String
						.format("INSERT INTO electronic_resources (`record_id`, `url`) VALUES ('%d','%s');",
								c.get_record_id(), c.get_url());
				dbc.Insert(query);
			}

		} catch (Exception DuplicateKeyException) {
			try {
				duplicate_error = "Location is taken. Record cannot be placed. Please enter another location.";
				dbc = new DatabaseConnection();
				query = String
						.format("SELECT COUNT(*) FROM physical_resources WHERE record_id = '%d';",
								c.get_record_id());
				ResultSet result = dbc.GetResult(query);
				result.next();
				if (result.getInt(1) > 0) {
					dbc = new DatabaseConnection();
					query = String.format(
							"DELETE FROM records WHERE record_id = '%d';",
							c.get_record_id()); //
					dbc.Insert(query);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				dbc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (common.get_type_id() == 1) {
			Book b = new Book();
			b.set_book_id(c.get_x_id());
			try {
				this.setResponsePage(new BookPage(b, duplicate_error));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (common.get_type_id() == 2) {
			Magazine m = new Magazine();
			m.set_magazine_id(c.get_x_id());
			try {
				this.setResponsePage(new MagazinePage(m, duplicate_error));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (common.get_type_id() == 3) {
			Dvd d = new Dvd();
			d.set_DVD_id(c.get_x_id());
			try {
				this.setResponsePage(new DvdPage(d, duplicate_error));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.setResponsePage(new HomePage());
		}
	}
}