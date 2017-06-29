package wicket.quickstart;

import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.RangeTextField;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.validation.validator.RangeValidator;

public class UserProfilePage extends BasePage {
	DatabaseConnection dbc;

	public UserProfilePage() throws Exception {
		UserNavigationPanel userNavigation = new UserNavigationPanel(
				"userNavigation");
		this.add(userNavigation);
		
		Link suggestion = new Link("suggestionlink") {
			@Override
			public void onClick() {
				this.setResponsePage(new RequestsSuggestionsPage());
			}
		};
		this.add(suggestion);
		
		String name = ((BasicAuthenticationSession) BasicAuthenticationSession
				.get()).getUser().getName();
		String surname = ((BasicAuthenticationSession) BasicAuthenticationSession
				.get()).getUser().getSurname();
		Date last_access = ((BasicAuthenticationSession) BasicAuthenticationSession
				.get()).getUser().get_last_access_time();
		Format datetimer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		TextField u_name;
		u_name = new TextField("u_name", Model.of(name));
		add(u_name);
		TextField u_lname;
		u_lname = new TextField("u_lname", Model.of(surname));
		add(u_lname);
		add(new Label("last_login", datetimer.format(last_access)));
		
		
		dbc = new DatabaseConnection();
		String str = String.format(
				"select `user_id` from users where `user_nickname` = '%s';",
				((BasicAuthenticationSession) BasicAuthenticationSession.get())
						.getUser().getUsername());
		ResultSet rs = dbc.GetResult(str);
		rs.next();
		int x = rs.getInt("user_id");
		add(getBookTable(String
				.format("SELECT * FROM book_informations where record_id in "
						+ "(SELECT record_id FROM waiting_resources where user_id = %d)",
						x)));
		add(getDvdTable(String
				.format("SELECT * FROM DVD_informations where record_id in "
						+ "(SELECT record_id FROM waiting_resources where user_id = %d)",
						x)));
		add(getMagazineTable(String
				.format("SELECT * FROM magazine_informations where record_id in "
						+ "(SELECT record_id FROM waiting_resources where user_id = %d)",
						x)));
		dbc.close();

	}

	public DataView getBookTable(String string) throws Exception {
		List<Book> list = new ArrayList();
		list = dbc.get_books(string);
		ListDataProvider<Book> listDataProvider = new ListDataProvider<Book>(
				list);

		DataView<Book> dataView = new DataView<Book>("book_rows",
				listDataProvider) {
			@Override
			protected void populateItem(Item<Book> item) {
				final Book book = item.getModelObject();
				item.add(new Label("name", book.get_name()));
				item.add(new Label("availability", book.get_available()));				
				item.add(new Link("link") {
					@Override
					public void onClick() {
						try {
							this.setResponsePage(new BookPage(book, ""));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				item.add(new Link("delete") {
					@Override
					public void onClick() {
						try {
							DatabaseConnection dbcreq = new DatabaseConnection();
							String query = String
									.format("DELETE FROM waiting_resources WHERE user_id = %d;",
											((BasicAuthenticationSession) BasicAuthenticationSession
													.get()).getUser().get_id(),"AND record_id = %d;",book.get_record_id());
							dbcreq.Insert(query);
							
							this.setResponsePage(new UserProfilePage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		};

		return dataView;
	}

	public DataView getDvdTable(String string) throws Exception {

		
		List<Dvd> list = new ArrayList();
		list = dbc.get_dvds(string);

		ListDataProvider<Dvd> listDataProvider = new ListDataProvider<Dvd>(list);

		DataView<Dvd> dataView = new DataView<Dvd>("dvd_rows", listDataProvider) {
			protected void populateItem(Item<Dvd> item) {
				final Dvd dvd = item.getModelObject();
				item.add(new Label("name", dvd.get_name()));
				item.add(new Label("availability", dvd.get_availability()));			
				item.add(new Link("link") {
					@Override
					public void onClick() {
						try {
							this.setResponsePage(new DvdPage(dvd, ""));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				item.add(new Link("delete") {
					@Override
					public void onClick() {
						try {
							DatabaseConnection dbcreq = new DatabaseConnection();
							String query = String
									.format("DELETE FROM waiting_resources WHERE user_id = %d;",
											((BasicAuthenticationSession) BasicAuthenticationSession
													.get()).getUser().get_id(),"AND record_id = %d;",dvd.get_record_id());
							dbcreq.Insert(query);
							
							this.setResponsePage(new UserProfilePage());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		};

		return dataView;
	}

	public DataView getMagazineTable(String string) throws Exception {
		List<Magazine> list = new ArrayList();
		list = dbc.get_magazines(string);


		ListDataProvider<Magazine> listDataProvider = new ListDataProvider<Magazine>(
				list);

		DataView<Magazine> dataView = new DataView<Magazine>("magazine_rows",
				listDataProvider) {
			protected void populateItem(Item<Magazine> item) {
				final Magazine magazine = item.getModelObject();
				RepeatingView repeatingView = new RepeatingView(
						"magazine_dataRow");
				item.add(new Label("name", magazine.get_name()));
				item.add(new Label("availability", magazine.get_availability()));				
				item.add(new Link("link") {
					@Override
					public void onClick() {
						try {
							this.setResponsePage(new MagazinePage(magazine, ""));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				item.add(new Link("delete") {
					@Override
					public void onClick() {
						try {
							DatabaseConnection dbcreq = new DatabaseConnection();
							String query = String
									.format("DELETE FROM waiting_resources WHERE user_id = %d;",
											((BasicAuthenticationSession) BasicAuthenticationSession
													.get()).getUser().get_id(),"AND record_id = %d;",magazine.get_record_id());
							dbcreq.Insert(query);
							
							this.setResponsePage(new UserProfilePage());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		};

		return dataView;
	}
}