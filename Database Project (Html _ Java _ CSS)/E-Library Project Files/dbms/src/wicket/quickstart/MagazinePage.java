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

public class MagazinePage extends BasePage {
	public MagazinePage(final Magazine magazine, String string)
			throws Exception {
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
				.format("SELECT magazine_name, magazine_publication_date, magazine_issue_number, "
						+ "magazine_rental_count, magazine_category_name FROM magazines "
						+ "JOIN magazine_categories ON (magazines.magazine_category_id = magazine_categories.magazine_category_id) "
						+ "WHERE magazine_id = %d;", magazine.get_magazine_id());

		ResultSet rs = dbc.GetResult(query);
		rs.next();
		magazine.set_name(rs.getString("magazine_name"));
		magazine.set_category_name(rs.getString("magazine_category_name"));
		magazine.set_publish_year(1900 + (rs
				.getDate("magazine_publication_date")).getYear());
		magazine.set_issue_number(rs.getInt("magazine_issue_number"));
		magazine.set_rental_count(rs.getInt("magazine_rental_count"));

		Label name = new Label("name", magazine.get_name()
				+ " Magazine Information");
		Label year = new Label("year", magazine.get_publish_year());
		Label rental_count = new Label("rental_count",
				magazine.get_rental_count());
		Label issue = new Label("issue", magazine.get_issue_number());
		Label category = new Label("category", magazine.get_category_name());

		add(name);
		add(year);
		add(issue);
		add(rental_count);
		add(category);

		add(new Label("rentColumn", "Rent"));
		add(new Label("editColumn", "Edit"));
		add(new Label("deleteColumn", "Delete"));

		Link AddMemberPageLink = new Link("addLink") {
			@Override
			public void onClick() {
				Common common = new Common();
				common.set_name(magazine.get_name());
				common.set_x_id(magazine.get_magazine_id());
				common.set_type_id(2);

				this.setResponsePage(new SourcePlacedPage(common, false));
			}
		};
		this.add(AddMemberPageLink);

		Link editMagazine = new Link("editMagazineLink") {
			@Override
			public void onClick() {
				this.setResponsePage(new MagazineEditPage(magazine));
			}
		};
		this.add(editMagazine);

		Link deleteMagazine = new Link("deleteMagazineLink") {
			@Override
			public void onClick() {
				try {
					dbc.Insert(String.format(
							"DELETE FROM magazines where `magazine_id` = %d",
							magazine.get_magazine_id()));

					dbc.Insert(String
							.format("DELETE FROM records where type_id = 2 and `x_id` = %d",
									magazine.get_magazine_id()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.setResponsePage(new HomePage());
			}
		};
		this.add(deleteMagazine);

		final Model<String> strMdl = Model.of(string);
		this.add(new Label("label", strMdl));
		query = String.format(
				"SELECT * FROM magazine_informations WHERE magazine_id = %d;",
				magazine.get_magazine_id());
		List<Magazine> list = dbc.get_magazines(query);

		ListDataProvider<Magazine> listDataProvider = new ListDataProvider<Magazine>(
				list);

		DataView<Magazine> dataView = new DataView<Magazine>("rows",
				listDataProvider) {
			int rowCounter = 0;

			@Override
			protected void populateItem(Item<Magazine> item) {
				final Magazine magazine = item.getModelObject();
				rowCounter++;
				item.add(new Label("rowCounter", rowCounter));
				if (magazine.get_physical_electronic() == 1) {

					item.add(new Label(
							"location",
							String.format(
									"%s - %s.floor - %s.bookcase - %s.bookshelf - %s.order",
									magazine.get_library_name(),
									magazine.get_floor_value(),
									magazine.get_bookcase_name(),
									magazine.get_bookshelf_name(),
									magazine.get_column_id())));
				} else {				
						item.add(new Label("location", magazine.get_url()));					
				}

				if (magazine.get_availability() == 0) {
					item.add(new Label("available", "Available"));
				} else {
					item.add(new Label("available",
							String.format("Available in %d days",
									magazine.get_availability())));
				}
				item.add(new Link("rent") {
					@Override
					public void onClick() {
						try {
							if (BasicAuthenticationSession.get().isSignedIn()) {
								if (magazine.get_availability() == 0) {
									rent(magazine);
									this.setResponsePage(new MagazinePage(
											magazine, "Book is rented."));
								} else {
									this.setResponsePage(new MagazinePage(
											magazine,
											"Book is not available for "
													+ magazine
															.get_availability()
													+ "days."));
								}
							} else {
								this.setResponsePage(new MagazinePage(magazine,
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
						common.set_type_id(2);
						common.set_name(magazine.get_name());
						common.set_x_id(magazine.get_magazine_id());
						common.set_record_id(magazine.get_record_id());
						common.set_library_name(magazine.get_library_name());
						common.set_floor_name(magazine.get_floor_value());
						common.set_bookcase_id(magazine.get_bookcase_id());
						common.set_bookshelf_id(magazine.get_bookshelf_id());
						common.set_column(magazine.get_column_id());
						common.set_url(magazine.get_url());
						common.set_physical_electronic(magazine
								.get_physical_electronic());
						this.setResponsePage(new SourceEditPage(common));
					}
				});
				item.add(new Link("deleteLink") {
					@Override
					public void onClick() {
						Magazine tempmagazine = new Magazine();
						tempmagazine.set_magazine_id(magazine.get_magazine_id());
						try {
							dbc.Insert(String
									.format("DELETE FROM record_id where `record_id` = %d",
											magazine.get_record_id()));

							this.setResponsePage(new MagazinePage(tempmagazine,
									""));
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
											x, magazine.get_record_id()));

							this.setResponsePage(new MagazinePage(magazine, ""));
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
				if (magazine.get_availability() == 0
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
			get("editMagazineLink").setVisible(false);
			get("deleteMagazineLink").setVisible(false);

		}
		add(dataView);
	}

	private void rent(Magazine magazine) throws Exception {
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
						x, magazine.get_record_id()));
		dbc.Insert(String
				.format("UPDATE physical_resources MODIFY SET `current_log_id` = (SELECT max(log_id) FROM logs)"
						+ " where record_id = %d;", magazine.get_record_id()));
		dbc.Insert(String
				.format("UPDATE magazines MODIFY SET `magazine_rental_count` = `magazine_rental_count`+ 1"
						+ " where `magazine_id` = (SELECT `x_id` from records where `record_id`= %d)",
						magazine.get_record_id()));
		dbc.close();
	}

}