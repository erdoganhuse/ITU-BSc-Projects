package wicket.quickstart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import com.mysql.jdbc.ResultSetMetaData;

public class DatabaseConnection {

	private Connection dbConnect = null;
	private ResultSet resultTable = null;
	private Statement statement = null;

	public DatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new UnsupportedOperationException(e.getMessage());
		}
		try {
			String dbUrl = "jdbc:mysql://24.133.151.17:3306/elibrary?useUnicode=true&characterEncoding=utf-8";
			String dbUser = "dmbs";
			String dbPassword = "itudb1302";
			this.dbConnect = DriverManager.getConnection(dbUrl, dbUser,
					dbPassword);

			statement = this.dbConnect.createStatement();

		} catch (SQLException ex) {
			throw new UnsupportedOperationException(ex.getMessage());
		} catch (Exception e) {
			throw e;
		}
	}

	public ResultSet GetResult(String query) throws Exception {
		try {
			resultTable = statement.executeQuery(query);
			// Burada saglanabilir ise db objesi degil de
			// verinin kendisi donulecek
			return resultTable;
		} catch (SQLException e) {
			throw new UnsupportedOperationException(e.getMessage());
		} catch (Exception e) {
			throw e;
		}
	}

	public void Insert(String query) throws SQLException {
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public void close() throws Exception {
		try {
			if (resultTable != null) {
				resultTable.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (dbConnect != null) {
				dbConnect.close();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Book> get_books(String book_query) throws Exception {
		ResultSet rs = this.GetResult(book_query);
		List<Book> booklist = new ArrayList();

		while (rs.next()) {
			Book tempBook = new Book();
			tempBook.set_record_id(rs.getInt("record_id"));
			tempBook.set_book_id(rs.getInt("book_id"));
			tempBook.set_name(rs.getString("book_name"));
			tempBook.set_author_name(rs.getString("author_name"));
			tempBook.set_category_name(rs.getString("book_category_name"));
			tempBook.set_publish_year(rs.getInt("book_publication_year"));
			tempBook.set_ISBN(rs.getString("book_ISBN"));
			tempBook.set_rental_count(rs.getInt("book_rental_count"));
			tempBook.set_library_name(rs.getString("library_name"));
			tempBook.set_floor_value(rs.getString("floor_name"));
			tempBook.set_bookcase_id(rs.getInt("bookcase_id"));
			tempBook.set_bookshelf_id(rs.getInt("bookshelf_id"));
			tempBook.set_bookcase_name(rs.getString("bookcase_name"));
			tempBook.set_bookshelf_name(rs.getString("bookshelf_name"));
			tempBook.set_column_id(rs.getInt("book_column"));
			tempBook.set_physical_electronic(rs.getInt("physical_electronic"));
			tempBook.set_available(rs.getInt("available"));
			tempBook.set_url(rs.getString("url"));
			booklist.add(tempBook);
		}
		return booklist;
	}

	public List<Dvd> get_dvds(String dvd_query) throws Exception {
		ResultSet rs = this.GetResult(dvd_query);
		List<Dvd> dvdlist = new ArrayList();

		while (rs.next()) {
			Dvd dvd = new Dvd();
			dvd.set_record_id(rs.getInt("record_id"));
			dvd.set_DVD_id(rs.getInt("DVD_id"));
			dvd.set_name(rs.getString("DVD_name"));
			dvd.set_category_name(rs.getString("DVD_category_name"));
			dvd.set_publish_year(1900 + (rs.getDate("DVD_publication_date"))
					.getYear());
			dvd.set_duration(rs.getInt("DVD_duration"));
			dvd.set_rental_count(rs.getInt("DVD_rental_count"));
			dvd.set_library_name(rs.getString("library_name"));
			dvd.set_floor_value(rs.getString("floor_name"));
			dvd.set_bookcase_id(rs.getInt("bookcase_id"));
			dvd.set_bookshelf_id(rs.getInt("bookshelf_id"));
			dvd.set_bookcase_name(rs.getString("bookcase_name"));
			dvd.set_bookshelf_name(rs.getString("bookshelf_name"));
			dvd.set_column_id((rs.getInt("DVD_column")));
			dvd.set_physical_electronic(rs.getInt("physical_electronic"));
			dvd.set_availability(rs.getInt("available"));
			dvd.set_url(rs.getString("url"));
			dvdlist.add(dvd);
		}
		return dvdlist;
	}

	public List<Magazine> get_magazines(String magazine_query) throws Exception {
		ResultSet rs = this.GetResult(magazine_query);
		List<Magazine> magazinelist = new ArrayList();

		while (rs.next()) {
			Magazine magazine = new Magazine();
			magazine.set_record_id(rs.getInt("record_id"));
			magazine.set_magazine_id(rs.getInt("magazine_id"));
			magazine.set_name(rs.getString("magazine_name"));
			magazine.set_category_name(rs.getString("magazine_category_name"));
			magazine.set_publish_year(1900 + (rs
					.getDate("magazine_publication_date")).getYear());
			magazine.set_issue_number(rs.getInt("magazine_issue_number"));
			magazine.set_rental_count(rs.getInt("magazine_rental_count"));
			magazine.set_library_name(rs.getString("library_name"));
			magazine.set_floor_value(rs.getString("floor_name"));
			magazine.set_bookcase_id(rs.getInt("bookcase_id"));
			magazine.set_bookshelf_id(rs.getInt("bookshelf_id"));
			magazine.set_bookcase_name(rs.getString("bookcase_name"));
			magazine.set_bookshelf_name(rs.getString("bookshelf_name"));
			magazine.set_column_id(rs.getInt("book_column"));	
			magazine.set_physical_electronic(rs.getInt("physical_electronic"));
			magazine.set_availability(rs.getInt("available"));
			magazine.set_url(rs.getString("url"));
			magazinelist.add(magazine);
		}
		return magazinelist;
	}

}