package wicket.quickstart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Popup;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class BookPage extends BasePage {
	public BookPage(final Book book, String str)
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
				.format("SELECT book_name, author_name, book_publication_year, book_ISBN, "
						+ "book_rental_count, book_category_name FROM books "
						+ "JOIN authors ON (books.author_id = authors.author_id) "
						+ "JOIN book_categories ON (books.book_category_id = book_categories.book_category_id) "
						+ "WHERE book_id = %d;", book.get_book_id());

		ResultSet rs = dbc.GetResult(query);
		while (rs.next()) {
			book.set_name(rs.getString("book_name"));
			book.set_author_name(rs.getString("author_name"));
			book.set_category_name(rs.getString("book_category_name"));
			book.set_publish_year(rs.getInt("book_publication_year"));
			book.set_ISBN(rs.getString("book_ISBN"));
			book.set_rental_count(rs.getInt("book_rental_count"));
		}

		Label name = new Label("name", book.get_name());
		Label year = new Label("year", book.get_publish_year());
		Label author = new Label("author", book.get_author_name());
		Label isbn = new Label("isbn", book.get_ISBN());
		Label rental_count = new Label("rental_count", book.get_rental_count());
		Label category = new Label("category", book.get_category_name());

		add(name);
		add(year);
		add(author);
		add(isbn);
		add(rental_count);
		add(category);

		add(new Label("rentColumn", "Rent"));
		add(new Label("editColumn", "Edit"));
		add(new Label("deleteColumn", "Delete"));

		Link AddMemberPageLink = new Link("addBookLink") {
			@Override
			public void onClick() {
				Common common = new Common();
				common.set_name(book.get_name());
				common.set_x_id(book.get_book_id());
				common.set_type_id(1);

				this.setResponsePage(new SourcePlacedPage(common, false));
			}
		};
		this.add(AddMemberPageLink);

		Link editBook = new Link("editBookLink") {
			@Override
			public void onClick() {
				this.setResponsePage(new BookEditPage(book));
			}
		};
		this.add(editBook);

		Link deleteBook = new Link("deleteBookLink") {
			@Override
			public void onClick() {
				try {
					dbc.Insert(String.format(
							"DELETE FROM books where `book_id` = %d",
							book.get_book_id()));

					dbc.Insert(String
							.format("DELETE FROM records where type_id = 1 and  `x_id` = %d",
									book.get_book_id()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.setResponsePage(new HomePage());
			}
		};
		this.add(deleteBook);

		final Model<String> strMdl = Model.of(str);
		this.add(new Label("label", strMdl));

		query = "SELECT * FROM elibrary.book_informations where book_id = "
				+ book.get_book_id();
		List<Book> list = dbc.get_books(query);

		ListDataProvider<Book> listDataProvider = new ListDataProvider<Book>(
				list);

		DataView<Book> dataView = new DataView<Book>("rows", listDataProvider) {
			int rowCounter = 0;

			@Override
			protected void populateItem(Item<Book> item) {
				final Book book = item.getModelObject();
				rowCounter++;
				item.add(new Label("rowCounter", rowCounter));
				Label locationele = new Label("locationele", book.get_url());				
				Label locationphy = new Label(
						"locationphy",
						String.format(
								"%s - %s.floor - %s.bookcase - %s.bookshelf - %s.order",
								book.get_library_name(),
								book.get_floor_value(),
								book.get_bookcase_name(),
								book.get_bookshelf_name(), book.get_column_id()));
				item.add(locationphy);
				item.add(locationele);
				if (book.get_physical_electronic() == 1) {
					locationele.setVisible(false);
				} else {
					locationphy.setVisible(false);
				}

				if (book.get_available() == 0) {
					item.add(new Label("available", "Available"));
				} else {
					item.add(new Label("available", String.format(
							"Available in %d days", book.get_available())));
				}
				item.add(new Link("rent") {
					@Override
					public void onClick() {
						try {
							if (BasicAuthenticationSession.get().isSignedIn()) {
								if (book.get_available() == 0) {
									rent(book);
									this.setResponsePage(new BookPage(book,
											"Book is rented."));
								} else {
									this.setResponsePage(new BookPage(book,
											"Book is not available for "
													+ book.get_available()
													+ "days."));
								}
							} else {
								this.setResponsePage(new BookPage(book,
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
						common.set_name(book.get_name());
						common.set_type_id(1);
						common.set_x_id(book.get_book_id());
						common.set_record_id(book.get_record_id());
						common.set_library_name(book.get_library_name());
						common.set_floor_name(book.get_floor_value());
						common.set_bookcase_id(book.get_bookcase_id());
						common.set_bookshelf_id(book.get_bookshelf_id());
						common.set_column(book.get_column_id());
						common.set_url(book.get_url());
						common.set_physical_electronic(book
								.get_physical_electronic());
						this.setResponsePage(new SourceEditPage(common));
					}
				});
				item.add(new Link("deleteLink") {
					@Override
					public void onClick() {
						Book tempbook = new Book();
						tempbook.set_book_id(book.get_book_id());
						try {
							dbc.Insert(String
									.format("DELETE FROM records where `record_id` = %d",
											book.get_record_id()));

							this.setResponsePage(new BookPage(tempbook, ""));
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
											x, book.get_record_id()));

							this.setResponsePage(new BookPage(book, ""));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				if (((BasicAuthenticationSession) BasicAuthenticationSession
						.get()).getUser().get_authorityState() == 1) {
					item.get("rent").setVisible(false);
					item.get("waitLink").setVisible(false);
				} else {
					item.get("editLink").setVisible(false);
					item.get("deleteLink").setVisible(false);

				}
				if (book.get_available() == 0
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
			get("addBookLink").setVisible(false);
			get("editBookLink").setVisible(false);
			get("deleteBookLink").setVisible(false);
			get("rentColumn").setEnabled(false);
		}

		add(dataView);
	}

	private void rent(Book book) throws Exception {
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
						x, book.get_record_id()));
		dbc.Insert(String
				.format("UPDATE physical_resources MODIFY SET `current_log_id` = (SELECT max(log_id) FROM logs)"
						+ " where record_id = %d;", book.get_record_id()));
		dbc.Insert(String
				.format("UPDATE books MODIFY SET `book_rental_count` = `book_rental_count`+ 1"
						+ " where `book_id` = (SELECT `x_id` from records where `record_id`= %d)",
						book.get_record_id()));
		dbc.close();
	}

}