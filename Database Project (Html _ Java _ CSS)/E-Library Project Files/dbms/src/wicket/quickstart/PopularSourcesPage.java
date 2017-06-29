package wicket.quickstart;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.form.RangeTextField;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.validation.validator.RangeValidator;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

public class PopularSourcesPage extends BasePage {
	@SuppressWarnings({ "unchecked", "deprecation" })
	public PopularSourcesPage() throws Exception {
		GuestNavigationPanel guestNavigation = new GuestNavigationPanel(
				"guestNavigation");
		this.add(guestNavigation);

		UserNavigationPanel userNavigation = new UserNavigationPanel(
				"userNavigation");
		this.add(userNavigation);

		if (BasicAuthenticationSession.get().isSignedIn()) {
			get("guestNavigation").setVisible(false);
		} else {
			get("userNavigation").setVisible(false);
		}

		// BOOK
		DatabaseConnection dbc = new DatabaseConnection();
		String query = "SELECT book_id, book_name, author_name, book_category_name, book_publication_year, book_rental_count "
				+ "FROM books JOIN authors ON (books.author_id = authors.author_id) "
				+ " JOIN  book_categories ON (books.book_category_id = book_categories.book_category_id) "
				+ " ORDER BY book_rental_count DESC LIMIT 5;";
		ResultSet rs = dbc.GetResult(query);
		List<Book> booklist = new ArrayList();

		while (rs.next()) {
			Book book = new Book();
			book.set_book_id(rs.getInt("book_id"));
			book.set_name(rs.getString("book_name"));
			book.set_author_name(rs.getString("author_name"));
			book.set_category_name(rs.getString("book_category_name"));
			book.set_publish_year(rs.getInt("book_publication_year"));
			book.set_rental_count(rs.getInt("book_rental_count"));
			booklist.add(book);
		}

		ListDataProvider<Book> booklistDataProvider = new ListDataProvider<Book>(
				booklist);

		DataView<Book> bookdataView = new DataView<Book>("bookrows",
				booklistDataProvider) {
			@Override
			protected void populateItem(Item<Book> bookitem) {
				final Book book = bookitem.getModelObject();
				RepeatingView repeatingView = new RepeatingView("bookdataRow");
				// NAME
				repeatingView.add(new Label(repeatingView.newChildId(), book
						.get_name()));
				// AUTHOR
				repeatingView.add(new Label(repeatingView.newChildId(), book
						.get_author_name()));
				// CATEGORY
				repeatingView.add(new Label(repeatingView.newChildId(), book
						.get_category_name()));
				// PUBLISH YEAR
				repeatingView.add(new Label(repeatingView.newChildId(), book
						.get_publish_year()));
				// RENT COUNT
				repeatingView.add(new Label(repeatingView.newChildId(), book
						.get_rental_count()));
				// LINK
				repeatingView.add(new Link(repeatingView.newChildId()) {
					@Override
					public void onClick() {
						try {
							this.setResponsePage(new BookPage(book, ""));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				bookitem.add(repeatingView);
			}
		};
		add(bookdataView);

		// MAGAZINE
		dbc = new DatabaseConnection();
		query = "SELECT magazine_id, magazine_name, magazine_category_name, magazine_issue_number, magazine_publication_date, magazine_rental_count "
				+ "FROM magazines "
				+ " JOIN  magazine_categories ON (magazines.magazine_category_id = magazine_categories.magazine_category_id) "
				+ " ORDER BY magazine_rental_count DESC LIMIT 5;";
		rs = dbc.GetResult(query);
		List<Magazine> magazinelist = new ArrayList();

		while (rs.next()) {
			Magazine magazine = new Magazine();
			magazine.set_magazine_id(rs.getInt("magazine_id"));
			magazine.set_name(rs.getString("magazine_name"));
			magazine.set_category_name(rs.getString("magazine_category_name"));
			magazine.set_publish_year(1900 + (rs
					.getDate("magazine_publication_date")).getYear());
			magazine.set_issue_number(rs.getInt("magazine_issue_number"));
			magazine.set_rental_count(rs.getInt("magazine_rental_count"));
			magazinelist.add(magazine);
		}

		ListDataProvider<Magazine> magazinelistDataProvider = new ListDataProvider<Magazine>(
				magazinelist);

		DataView<Magazine> magazinedataView = new DataView<Magazine>(
				"magazinerows", magazinelistDataProvider) {
			@Override
			protected void populateItem(Item<Magazine> magazineitem) {
				final Magazine magazine = magazineitem.getModelObject();
				RepeatingView repeatingView = new RepeatingView(
						"magazinedataRow");
				// NAME
				repeatingView.add(new Label(repeatingView.newChildId(),
						magazine.get_name()));
				// ISSUE
				repeatingView.add(new Label(repeatingView.newChildId(),
						magazine.get_issue_number()));
				// CATEGORY
				repeatingView.add(new Label(repeatingView.newChildId(),
						magazine.get_category_name()));
				// PUBLISH YEAR
				repeatingView.add(new Label(repeatingView.newChildId(),
						magazine.get_publish_year()));

				/*
				 * // PHYSICAL-ELECTRONIC if (magazine.get_physical_electronic()
				 * == 0) { repeatingView.add(new
				 * Label(repeatingView.newChildId(), "Physical")); } else {
				 * repeatingView.add(new Label(repeatingView.newChildId(),
				 * "Electronic")); } // AVAILABLE if
				 * (magazine.get_availability() == 0) { repeatingView.add(new
				 * Label(repeatingView.newChildId(), "Available")); } else {
				 * repeatingView.add(new Label(repeatingView.newChildId(),
				 * String.format("Available in %d days",
				 * magazine.get_availability()))); }
				 */

				// RENT COUNT
				repeatingView.add(new Label(repeatingView.newChildId(),
						magazine.get_rental_count()));
				// LINK
				repeatingView.add(new Link(repeatingView.newChildId()) {
					@Override
					public void onClick() {
						try {
							this.setResponsePage(new MagazinePage(magazine, ""));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				magazineitem.add(repeatingView);
			}
		};
		add(magazinedataView);

		// DVD
		dbc = new DatabaseConnection();
		query = "SELECT DVD_id, DVD_name, DVD_category_name, DVD_duration, DVD_publication_date, DVD_rental_count "
				+ "FROM DVDs "
				+ " JOIN  DVD_categories ON (DVDs.DVD_category_id = DVD_categories.DVD_category_id) "
				+ " ORDER BY DVD_rental_count DESC LIMIT 5;";

		rs = dbc.GetResult(query);
		List<Dvd> dvdlist = new ArrayList();

		while (rs.next()) {
			Dvd dvd = new Dvd();
			dvd.set_DVD_id(rs.getInt("DVD_id"));
			dvd.set_name(rs.getString("DVD_name"));
			dvd.set_category_name(rs.getString("DVD_category_name"));
			dvd.set_publish_year(1900 + (rs.getDate("DVD_publication_date"))
					.getYear());
			dvd.set_duration(rs.getInt("DVD_duration"));
			dvd.set_rental_count(rs.getInt("DVD_rental_count"));
			dvdlist.add(dvd);
		}

		ListDataProvider<Dvd> dvdlistDataProvider = new ListDataProvider<Dvd>(
				dvdlist);

		DataView<Dvd> dvddataView = new DataView<Dvd>("dvdrows",
				dvdlistDataProvider) {
			@Override
			protected void populateItem(Item<Dvd> dvditem) {
				final Dvd dvd = dvditem.getModelObject();
				RepeatingView repeatingView = new RepeatingView("dvddataRow");
				// NAME
				repeatingView.add(new Label(repeatingView.newChildId(), dvd
						.get_name()));
				// DURATION
				repeatingView.add(new Label(repeatingView.newChildId(), dvd
						.get_duration()));
				// CATEGORY
				repeatingView.add(new Label(repeatingView.newChildId(), dvd
						.get_category_name()));
				// PUBLISH YEAR
				repeatingView.add(new Label(repeatingView.newChildId(), dvd
						.get_publish_year()));

				/*
				 * // PHYSICAL-ELECTRONIC if (dvd.get_physical_electronic() ==
				 * 0) { repeatingView.add(new Label(repeatingView.newChildId(),
				 * "Physical")); } else { repeatingView.add(new
				 * Label(repeatingView.newChildId(), "Electronic")); } //
				 * AVAILABLE if (dvd.get_availability() == 0) {
				 * repeatingView.add(new Label(repeatingView.newChildId(),
				 * "Available")); } else { repeatingView.add(new
				 * Label(repeatingView.newChildId(),
				 * String.format("Available in %d days",
				 * dvd.get_availability()))); }
				 */
				// RENT COUNT
				repeatingView.add(new Label(repeatingView.newChildId(), dvd
						.get_rental_count()));
				// LINK
				repeatingView.add(new Link(repeatingView.newChildId()) {
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
				dvditem.add(repeatingView);
			}
		};
		add(dvddataView);

		dbc.close();
	}
}