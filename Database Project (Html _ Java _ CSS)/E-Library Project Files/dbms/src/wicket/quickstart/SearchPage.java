package wicket.quickstart;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale.Category;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;

public class SearchPage extends BasePage {

	DatabaseConnection dbc;

	@SuppressWarnings("unchecked")
	public SearchPage(Boolean book, Boolean dvd, Boolean magazine,
			String search, Boolean electronic, Boolean physical,
			Integer category_id) throws Exception {

		GuestNavigationPanel guestNavigation = new GuestNavigationPanel(
				"guestNavigation");
		this.add(guestNavigation);

		UserNavigationPanel userNavigation = new UserNavigationPanel(
				"userNavigation");
		this.add(userNavigation);

		AdminNavigationPanel adminNavigation = new AdminNavigationPanel(
				"adminNavigation");
		this.add(adminNavigation);

		if (BasicAuthenticationSession.get().isSignedIn()) {
			get("guestNavigation").setVisible(false);
			if (((BasicAuthenticationSession) BasicAuthenticationSession.get())
					.getUser().get_authorityState() == 0) {
				get("adminNavigation").setVisible(false);
			} else {
				get("userNavigation").setVisible(false);
			}
		} else {
			get("adminNavigation").setVisible(false);
			get("userNavigation").setVisible(false);
		}

		dbc = new DatabaseConnection();
		if (search == null) {
			search = "";
		}
		String ele = electronic.toString();
		String phy = physical.toString();

		String book_query = "SELECT book_name,author_name,book_category_name,book_publication_year,book_id,book_ISBN,book_rental_count FROM books"
				+ " inner join authors on (authors.author_id = books.author_id) "
				+ " join book_categories on (books.book_category_id = book_categories.book_category_id) WHERE"
				+ " (book_name LIKE \"%"
				+ search
				+ "%\" OR author_name LIKE \"%" + search + "%\")";
		String dvd_query = "SELECT DVD_name,DVD_publication_date,DVD_category_name,DVD_duration,DVD_id FROM DVDs "
				+ "JOIN DVD_categories ON (DVDs.DVD_category_id = DVD_categories.DVD_category_id) WHERE (DVD_name LIKE \"%"
				+ search + "%\")";
		String magazine_query = "SELECT * FROM magazines JOIN magazine_categories ON (magazines.magazine_category_id = magazine_categories.magazine_category_id) WHERE (magazine_name LIKE \"%"
				+ search + "%\")";
		if (category_id != 0) {
			book_query += " and books.book_category_id = " + category_id;
			dvd_query += "  and DVDs.DVD_category_id = " + category_id;
			magazine_query += " and magazines.magazine_category_id = "
					+ category_id;
		}
		if (physical != electronic) {
			book_query += " and book_id in (select x_id from records where type_id = 1 and `physical-electronic` = "
					+ physical + " )";
			dvd_query += " and DVD_id in (select x_id from records where type_id = 3 and `physical-electronic` = "
					+ physical + " )";
			magazine_query += " and magazine_id in (select x_id from records where type_id = 2 and `physical-electronic` = "
					+ physical + " )";
		}

		book_query += " ORDER BY book_name;";
		dvd_query += " ORDER BY DVD_name;";
		magazine_query += " ORDER BY magazine_name;";
		
		
		add(getBookTable(book_query));
		add(getDvdTable(dvd_query));
		add(getMagazineTable(magazine_query));

		if (!book) {
			get("book_rows").setVisible(false);

		}
		if (!dvd) {
			get("dvd_rows").setVisible(false);

		}
		if (!magazine) {
			get("magazine_rows").setVisible(false);
		}
		dbc.close();
	}

	public DataView getBookTable(String string) throws Exception {
		ResultSet rs = dbc.GetResult(string);
		List<Book> list = new ArrayList();

		while (rs.next()) {
			Book book = new Book(rs.getString("book_name"),
					rs.getString("author_name"),
					rs.getInt("book_publication_year"), rs.getInt("book_id"));
			book.set_ISBN(rs.getString("book_ISBN"));
			book.set_rental_count(rs.getInt("book_rental_count"));
			book.set_category_name(rs.getString("book_category_name"));
			list.add(book);
		}

		ListDataProvider<Book> listDataProvider = new ListDataProvider<Book>(
				list);

		DataView<Book> dataView = new DataView<Book>("book_rows",
				listDataProvider) {
			@Override
			protected void populateItem(Item<Book> item) {
				final Book book = item.getModelObject();
				item.add(new Label("name", book.get_name()));
				item.add(new Label("author", book.get_author_name()));
				item.add(new Label("category", book.get_category_name()));
				item.add(new Label("year", book.get_publish_year()));
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
			}
		};

		return dataView;
	}

	public DataView getDvdTable(String string) throws Exception {

		ResultSet rs = dbc.GetResult(string);
		List<Dvd> list = new ArrayList();

		while (rs.next()) {
			Dvd dvd = new Dvd(rs.getString("DVD_name"),
					rs.getInt("DVD_publication_date"),
					rs.getInt("DVD_duration"), rs.getInt("DVD_id"));
			dvd.set_category_name(rs.getString("DVD_category_name"));
			list.add(dvd);
		}

		ListDataProvider<Dvd> listDataProvider = new ListDataProvider<Dvd>(list);

		DataView<Dvd> dataView = new DataView<Dvd>("dvd_rows", listDataProvider) {
			protected void populateItem(Item<Dvd> item) {
				final Dvd dvd = item.getModelObject();
				item.add(new Label("name", dvd.get_name()));
				item.add(new Label("year", dvd.get_publish_year()));
				item.add(new Label("duration", dvd.get_duration()));
				item.add(new Label("category", dvd.get_category_name()));
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

			}
		};

		return dataView;
	}

	public DataView getMagazineTable(String string) throws Exception {

		ResultSet rs = dbc.GetResult(string);
		List<Magazine> list = new ArrayList();

		while (rs.next()) {
			Magazine magazine = new Magazine(rs.getString("magazine_name"),
					rs.getInt("magazine_publication_date"),
					rs.getInt("magazine_issue_number"));
			magazine.set_magazine_id(rs.getInt("magazine_id"));
			magazine.set_category_name(rs.getString("magazine_category_name"));
			list.add(magazine);
		}

		ListDataProvider<Magazine> listDataProvider = new ListDataProvider<Magazine>(
				list);

		DataView<Magazine> dataView = new DataView<Magazine>("magazine_rows",
				listDataProvider) {
			protected void populateItem(Item<Magazine> item) {
				final Magazine magazine = item.getModelObject();
				RepeatingView repeatingView = new RepeatingView(
						"magazine_dataRow");
				item.add(new Label("name", magazine.get_name()));
				item.add(new Label("year", magazine.get_publish_year()));
				item.add(new Label("category", magazine.get_category_name()));
				item.add(new Label("issue", magazine.get_issue_number()));
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
			}
		};

		return dataView;
	}

}