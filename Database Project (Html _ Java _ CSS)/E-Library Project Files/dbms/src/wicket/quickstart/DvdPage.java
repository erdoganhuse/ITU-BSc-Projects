package wicket.quickstart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;

public class DvdPage extends BasePage {
	public DvdPage(final Dvd dvd, String string ) throws Exception {
		UserNavigationPanel userNavigation = new UserNavigationPanel(
				"userNavigation");
		this.add(userNavigation);
		GuestNavigationPanel guestNavigation = new GuestNavigationPanel(
				"guestNavigation");
		this.add(guestNavigation);
		AdminNavigationPanel adminNavigation = new AdminNavigationPanel(
				"adminNavigation");
		this.add(adminNavigation);
		guestNavigation.setVisible(false);
		userNavigation.setVisible(false);
		adminNavigation.setVisible(false);
		if (BasicAuthenticationSession.get().isSignedIn()) {
			if (((BasicAuthenticationSession) BasicAuthenticationSession.get())
					.getUser().get_authorityState() == 1) {
				adminNavigation.setVisible(true);
			} else {
				userNavigation.setVisible(true);
			}
		} else {
			guestNavigation.setVisible(true);
		}

		final DatabaseConnection dbc = new DatabaseConnection();
		String query = String
				.format("SELECT DVD_name, DVD_publication_date, DVD_duration, "
						+ "DVD_rental_count, DVD_category_name FROM DVDs "
						+ "JOIN DVD_categories ON (DVDs.DVD_category_id = DVD_categories.DVD_category_id) "
						+ "WHERE DVD_id = %d;", dvd.get_DVD_id());

		ResultSet rs = dbc.GetResult(query);
		rs.next();
		dvd.set_name(rs.getString("DVD_name"));
		dvd.set_category_name(rs.getString("DVD_category_name"));
		dvd.set_publish_year(1900 + (rs.getDate("DVD_publication_date"))
				.getYear());
		dvd.set_duration(rs.getInt("DVD_duration"));
		dvd.set_rental_count(rs.getInt("DVD_rental_count"));

		Label name = new Label("name", dvd.get_name());
		Label year = new Label("year", dvd.get_publish_year());
		Label duration = new Label("duration", dvd.get_duration());
		Label rental_count = new Label("rental_count", dvd.get_rental_count());
		Label category = new Label("category", dvd.get_category_name());

		add(name);
		add(year);
		add(duration);
		add(rental_count);
		add(category);

		add(new Label("rentColumn", "Rent"));
		add(new Label("editColumn", "Edit"));
		add(new Label("deleteColumn", "Delete"));
		Link AddMemberPageLink = new Link("addLink") {
			@Override
			public void onClick() {
				Common common = new Common();
				common.set_name(dvd.get_name());
				common.set_x_id(dvd.get_DVD_id());
				common.set_type_id(3);

				this.setResponsePage(new SourcePlacedPage(common, false));
			}
		};
		this.add(AddMemberPageLink);

		Link editDvd = new Link("editDvdLink") {
			@Override
			public void onClick() {
				this.setResponsePage(new DVDEditPage(dvd));
			}
		};
		this.add(editDvd);

		Link deleteDvd = new Link("deleteDvdLink") {
			@Override
			public void onClick() {
				try {
					dbc.Insert(String.format(
							"DELETE FROM DVDs where `DVD_id` = %d",
							dvd.get_DVD_id()));

					dbc.Insert(String
							.format("DELETE FROM records where type_id = 3 and `x_id` = %d",
									dvd.get_DVD_id()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.setResponsePage(new HomePage());
			}
		};
		this.add(deleteDvd);

		final Model<String> strMdl = Model.of(string);
		this.add(new Label("label", strMdl));
		query = "SELECT * FROM elibrary.DVD_informations where DVD_id = "
				+ dvd.get_DVD_id();
		List<Dvd> list = dbc.get_dvds(query);

		ListDataProvider<Dvd> listDataProvider = new ListDataProvider<Dvd>(list);

		DataView<Dvd> dataView = new DataView<Dvd>("rows", listDataProvider) {
			int rowCounter = 0;

			@Override
			protected void populateItem(Item<Dvd> item) {
				final Dvd dvd = item.getModelObject();
				rowCounter++;
				item.add(new Label("rowCounter", rowCounter));
				if (dvd.get_physical_electronic() == 1) {

					item.add(new Label(
							"location",
							String.format(
									"%s - %s.floor - %s.bookcase - %s.bookshelf - %s.order",
									dvd.get_library_name(),
									dvd.get_floor_value(),
									dvd.get_bookcase_name(),
									dvd.get_bookshelf_name(), dvd.get_column_id())));
				} else {					
						item.add(new Label("location", dvd.get_url()));					
				}
				if (dvd.get_availability() == 0) {
					item.add(new Label("available", "Available"));
				} else {
					item.add(new Label("available", String.format(
							"Available in %d days", dvd.get_availability())));
				}
				item.add(new Link("rent") {
					@Override
					public void onClick() {
						try {
							if (BasicAuthenticationSession.get().isSignedIn()) {
								if (dvd.get_availability() == 0) {
									rent(dvd);
									this.setResponsePage(new DvdPage(dvd,
											"DVD is rented."));
								} else {
									this.setResponsePage(new DvdPage(dvd,
											"DVD is not available for "
													+ dvd.get_availability()
													+ "days."));
								}
							} else {
								this.setResponsePage(new DvdPage(dvd,
										"You must login to rent."));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				item.add(new Link("editLink") {
					@Override
					public void onClick() {
						Common common = new Common();
						common.set_type_id(3);
						common.set_name(dvd.get_name());
						common.set_x_id(dvd.get_DVD_id());
						common.set_record_id(dvd.get_record_id());
						common.set_library_name(dvd.get_library_name());
						common.set_floor_name(dvd.get_floor_value());
						common.set_bookcase_id(dvd.get_bookcase_id());
						common.set_bookshelf_id(dvd.get_bookshelf_id());
						common.set_column(dvd.get_column_id());
						common.set_url(dvd.get_url());
						common.set_physical_electronic(dvd
								.get_physical_electronic());
						this.setResponsePage(new SourceEditPage(common));
					}
				});
				item.add(new Link("deleteLink") {
					@Override
					public void onClick() {
						Dvd tempdvd = new Dvd();
						tempdvd.set_DVD_id(dvd.get_DVD_id());
						try {
							dbc.Insert(String
									.format("DELETE FROM record_id where `record_id` = %d",
											dvd.get_record_id()));

							this.setResponsePage(new DvdPage(tempdvd, ""));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				item.add(new Link("waitLink") {
					@Override
					public void onClick() {
						try {
							String str = String
									.format("select `user_id` from users where `user_nickname` = '%s';",
											((BasicAuthenticationSession) BasicAuthenticationSession
													.get()).getUser()
													.getUsername());
							ResultSet rs;

							rs = dbc.GetResult(str);
							rs.next();
							int x = rs.getInt("user_id");
							dbc.Insert(String
									.format("INSERT INTO waiting_resources (`user_id`,`record_id`) values (%d,%d);",
											x, dvd.get_record_id()));

							this.setResponsePage(new DvdPage(dvd, ""));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				if (((BasicAuthenticationSession) BasicAuthenticationSession
						.get()).getUser().get_authorityState() == 1) {
					item.get("rent").setVisible(false);
				} else {
					item.get("editLink").setVisible(false);
					item.get("deleteLink").setVisible(false);

				}
				if (dvd.get_availability() == 0
						|| !BasicAuthenticationSession.get().isSignedIn()
						|| ((BasicAuthenticationSession) BasicAuthenticationSession
								.get()).getUser().get_authorityState() == 1) {
					item.get("waitLink").setVisible(false);
				}
			}
		};
		if (((BasicAuthenticationSession) BasicAuthenticationSession.get())
				.getUser().get_authorityState() == 1) {
			get("rentColumn").setVisible(false);
		} else {
			get("editColumn").setVisible(false);
			get("deleteColumn").setVisible(false);
			get("addLink").setVisible(false);
			get("editDvdLink").setVisible(false);
			get("deleteDvdLink").setVisible(false);

		}
		add(dataView);

	}

	private void rent(Dvd dvd) throws Exception {
		DatabaseConnection dbc = new DatabaseConnection();

		String str = String.format(
				"select `user_id` from users where `user_nickname` = '%s';",
				((BasicAuthenticationSession) BasicAuthenticationSession.get())
						.getUser().getUsername());
		ResultSet rs = dbc.GetResult(str);
		rs.next();
		int x = rs.getInt("user_id");
		dbc.Insert(String
				.format("INSERT INTO logs (`user_id`,`record_id`,`renting_date`,`expected_returning_date`)"
						+ " values (%d,%d,current_date(),DATE_ADD(current_date(),INTERVAL 30 DAY));",
						x, dvd.get_record_id()));
		dbc.Insert(String
				.format("UPDATE physical_resources MODIFY SET `current_log_id` = (SELECT max(log_id) FROM logs)"
						+ " where record_id = %d;", dvd.get_record_id()));
		dbc.Insert(String
				.format("UPDATE DVDs MODIFY SET `DVD_rental_count` = `DVD_rental_count`+ 1"
						+ " where `DVD_id` = (SELECT `x_id` from records where `record_id`= %d)",
						dvd.get_record_id()));
		dbc.close();

	}

}