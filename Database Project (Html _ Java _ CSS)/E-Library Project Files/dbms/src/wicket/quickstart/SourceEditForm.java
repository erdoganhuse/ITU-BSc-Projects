package wicket.quickstart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
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

public class SourceEditForm extends Form {
	private String selectedMake;
	private String selectedMake2;
	private String selectedMake3;
	private String selectedMake4;
	private final Map<String, List<String>> modelsMap = new HashMap<String, List<String>>();
	private final Map<String, List<String>> modelsMap2 = new HashMap<String, List<String>>();
	private final Map<String, List<String>> modelsMap3 = new HashMap<String, List<String>>();
	Common common;
	NumberTextField column;
	TextField url;

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

	public SourceEditForm(String id, final Common common) {
		super(id);
		this.common = common;

		Label sourcename = new Label("sourcename", common.get_name());
		add(sourcename);

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
			DatabaseConnection dbcBookshelf = new DatabaseConnection();
			ResultSet rsBookshelf = dbcBookshelf
					.GetResult("SELECT bookshelf_name FROM bookshelves where bookshelf_id = "
							+ this.common.get_bookshelf_id() + ";");
			rsBookshelf.next();
			this.common.set_bookshelf_name(rsBookshelf
					.getString("bookshelf_name"));
			DatabaseConnection dbcBookcases = new DatabaseConnection();
			ResultSet rsBookcases = dbcBookcases
					.GetResult("SELECT bookcase_name FROM bookcases where bookcase_id = "
							+ this.common.get_bookcase_id() + ";");
			rsBookcases.next();
			this.common.set_bookcase_name(rsBookcases
					.getString("bookcase_name"));
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
		selectedMake = this.common.get_library_name();
		final DropDownChoice<String> models = new DropDownChoice<String>(
				"_floor_name",
				new PropertyModel<String>(this, "selectedMake2"), modelChoices);
		models.setOutputMarkupId(true);
		selectedMake2 = this.common.get_floor_name();

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
		selectedMake3 = this.common.get_bookcase_name();
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
		selectedMake4 = this.common.get_bookshelf_name();
		models2.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {

				target.add(models3);
			}
		});

		column = new NumberTextField("_column", Model.of(this.common
				.get_column()));
		url = new TextField("_url", Model.of(common.get_url()));
		this.add(url);
		this.add(librarylabel);
		this.add(floorlabel);
		this.add(bookcaselabel);
		this.add(bookshelflabel);
		this.add(columnlabel);
		this.add(urllabel);
		this.add(column);
		this.add(makes);
		this.add(models);
		this.add(models2);
		this.add(models3);
		if (common.get_physical_electronic() == 0) {
			librarylabel.setVisible(false);
			floorlabel.setVisible(false);
			bookcaselabel.setVisible(false);
			bookshelflabel.setVisible(false);
			columnlabel.setVisible(false);
			column.setVisible(false);
			makes.setVisible(false);
			models.setVisible(false);
			models2.setVisible(false);
			models3.setVisible(false);
		} else {
			url.setVisible(false);
			urllabel.setVisible(false);
		}

	}

	public final void onSubmit() {
		String duplicate_error = "";
		String query = "";
		DatabaseConnection dbc = new DatabaseConnection();
		try {
			if (common.get_physical_electronic() == 1) {
				this.common.set_library_name(selectedMake);
				this.common.set_floor_name(selectedMake2);
				this.common.set_bookcase_name(selectedMake3);
				this.common.set_bookshelf_name(selectedMake4);
				this.common.set_column((Integer) column.getModelObject());
				dbc = new DatabaseConnection();
				ResultSet result;
				query = String
						.format("SELECT library_id FROM libraries WHERE library_name = '%s';",
								this.common.get_library_name());

				result = dbc.GetResult(query);

				result.next();
				this.common.set_library_id(result.getInt(1));
				dbc = new DatabaseConnection();
				query = String
						.format("SELECT floor_id FROM floors WHERE (floor_name = '%s') AND (library_id = '%d');",
								this.common.get_floor_name(),
								this.common.get_library_id());
				result = dbc.GetResult(query);
				result.next();
				this.common.set_floor_id(result.getInt(1));

				dbc = new DatabaseConnection();
				query = String
						.format("SELECT bookcase_id FROM bookcases WHERE (bookcase_name = '%s') AND (floor_id = '%d');",
								this.common.get_bookcase_name(),
								this.common.get_floor_id());
				result = dbc.GetResult(query);
				result.next();
				this.common.set_bookcase_id(result.getInt(1));

				dbc = new DatabaseConnection();
				query = String
						.format("SELECT bookshelf_id FROM bookshelves WHERE (bookshelf_name = '%s') AND (bookcase_id = '%d');",
								this.common.get_bookshelf_name(),
								this.common.get_bookcase_id());
				result = dbc.GetResult(query);
				result.next();
				this.common.set_bookshelf_id(result.getInt(1));

				dbc.Insert(String
						.format("UPDATE physical_resources "
								+ "SET bookshelf_id = %d,book_column = %d where record_id = %d;",
								common.get_bookshelf_id(), common.get_column(),
								common.get_record_id()));

			} else {
				common.set_url((String) url.getModelObject());
				dbc = new DatabaseConnection();
				dbc.Insert(String.format("UPDATE electronic_resources"
						+ " SET url = '%s' where record_id = %d",
						common.get_url(), common.get_record_id()));
			}

		} catch (Exception DuplicateKeyException) {
			duplicate_error = "Location is taken. Record cannot be replaced. Please enter another location.";
		} finally {
			try {
				dbc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (common.get_type_id() == 1) {
			Book b = new Book();
			b.set_book_id(common.get_x_id());
			try {
				this.setResponsePage(new BookPage(b, duplicate_error));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (common.get_type_id() == 2) {
			Magazine m = new Magazine();
			m.set_magazine_id(common.get_x_id());
			try {
				this.setResponsePage(new MagazinePage(m, duplicate_error));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (common.get_type_id() == 3) {
			Dvd d = new Dvd();
			d.set_DVD_id(common.get_x_id());
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
