package wicket.quickstart;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
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

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

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
import org.apache.wicket.validation.validator.RangeValidator;

public class AdminProfilePage extends BasePage {
	public AdminProfilePage() {
		AdminNavigationPanel adminNavigation = new AdminNavigationPanel(
				"adminNavigation");
		this.add(adminNavigation);

		String name = ((BasicAuthenticationSession) BasicAuthenticationSession
				.get()).getUser().getName();
		String surname = ((BasicAuthenticationSession) BasicAuthenticationSession
				.get()).getUser().getSurname();
		Date last_access = ((BasicAuthenticationSession) BasicAuthenticationSession
				.get()).getUser().get_last_access_time();
		Format datetimer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		TextField a_name;
		a_name = new TextField("a_name", Model.of(name));
		add(a_name);
		TextField a_lname;
		a_lname = new TextField("a_lname", Model.of(surname));
		add(a_lname);
		add(new Label("last_login", datetimer.format(last_access)));

		// LOGS
		List<Log> loglist = new ArrayList();
		DatabaseConnection dbc = new DatabaseConnection();

		try {
			String query = "SELECT user_name, user_surname, user_last_access_time, "
					+ "x_id, type_id, renting_date, expected_returning_date "
					+ "FROM logs JOIN users ON (logs.user_id = users.user_id) "
					+ "JOIN records ON (records.record_id = logs.record_id) "
					+ "ORDER BY log_id DESC LIMIT 10; ";
			ResultSet rs = dbc.GetResult(query);

			while (rs.next()) {
				Log log = new Log();
				log.user.set_name(rs.getString("user_name"));
				log.user.set_surname(rs.getString("user_surname"));
				log.user.set_last_access_time(rs
						.getTimestamp("user_last_access_time"));
				log.set_renting_date(rs.getDate("renting_date"));
				log.set_expected_returning_date(rs
						.getDate("expected_returning_date"));
				log.set_type_id(rs.getInt("type_id"));
				log.set_x_id(rs.getInt("x_id"));

				DatabaseConnection dbcsource = new DatabaseConnection();
				if (log.get_type_id() == 1) {
					query = String
							.format("SELECT book_name FROM books WHERE book_id = '%d';",
									log.get_x_id());
					ResultSet rsource = dbcsource.GetResult(query);
					if (rsource.next()) {
						log.set_name(rsource.getString("book_name"));
						log.set_type_name("Book");
					}

				} else if (log.get_type_id() == 2) {
					query = String
							.format("SELECT magazine_name FROM magazines WHERE magazine_id = '%d';",
									log.get_x_id());
					ResultSet rsource = dbcsource.GetResult(query);
					if (rsource.next()) {
						log.set_name(rsource.getString("magazine_name"));
						log.set_type_name("Magazine");
					}
				} else if (log.get_type_id() == 3) {
					query = String.format(
							"SELECT DVD_name FROM DVDs WHERE DVD_id = '%d';",
							log.get_x_id());
					ResultSet rsource = dbcsource.GetResult(query);
					if (rsource.next()) {
						log.set_name(rsource.getString("DVD_name"));
						log.set_type_name("DVD");
					}
				}

				dbcsource.close();
				loglist.add(log);
			}
		} catch (SQLException e) {
			throw new UnsupportedOperationException(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbc.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ListDataProvider<Log> loglistDataProvider = new ListDataProvider<Log>(
				loglist);

		DataView<Log> logdataView = new DataView<Log>("logrows",
				loglistDataProvider) {
			@Override
			protected void populateItem(Item<Log> logitem) {
				final Log log = logitem.getModelObject();
				RepeatingView repeatingView = new RepeatingView("logdataRow");
				Format datetimer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Format dater = new SimpleDateFormat("yyyy-MM-dd");

				// USER
				repeatingView.add(new Label(repeatingView.newChildId(),
						log.user.get_name() + " " + log.user.get_surname()));
				// USER LAST ACCESS
				repeatingView.add(new Label(repeatingView.newChildId(),
						datetimer.format(log.user.get_last_access_time())));
				// SOURCE
				repeatingView.add(new Label(repeatingView.newChildId(), log
						.get_name()));
				// SOURCE TYPE
				repeatingView.add(new Label(repeatingView.newChildId(), log
						.get_type_name()));
				// RENTING
				repeatingView.add(new Label(repeatingView.newChildId(), dater
						.format(log.get_renting_date())));
				// EXPECTED
				repeatingView.add(new Label(repeatingView.newChildId(), dater
						.format(log.get_expected_returning_date())));
				// LINK
				repeatingView.add(new Link(repeatingView.newChildId()) {
					@Override
					public void onClick() {
						try {
							if (log.get_type_id() == 1) {
								Book book = new Book();
								book.set_name(log.get_name());
								book.set_book_id(log.get_x_id());
								this.setResponsePage(new BookPage(book, ""));
							} else if (log.get_type_id() == 2) {
								Magazine magazine = new Magazine();
								magazine.set_name(log.get_name());
								magazine.set_magazine_id(log.get_x_id());
								this.setResponsePage(new MagazinePage(magazine,
										""));
							} else if (log.get_type_id() == 3) {
								Dvd dvd = new Dvd();
								dvd.set_name(log.get_name());
								dvd.set_DVD_id(log.get_x_id());
								this.setResponsePage(new DvdPage(dvd, ""));
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				logitem.add(repeatingView);
			}
		};
		add(logdataView);

	}

	public class Log extends Common implements Serializable {
		User user = new User();
	}
}