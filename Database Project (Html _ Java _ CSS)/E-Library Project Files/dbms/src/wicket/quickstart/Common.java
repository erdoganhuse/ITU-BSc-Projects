package wicket.quickstart;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Common implements Serializable {
	private String _name = null;
	private Integer _x_id = null;
	private Integer _type_id = null;
	private Integer _physical_electronic = null;
	private String _library_name = null;
	private Integer _library_id = null;
	private String _floor_name = null;
	private Integer _floor_id = null;
	private String _bookcase_name = null;
	private Integer _bookcase_id = null;
	private String _bookshelf_name = null;
	private Integer _bookshelf_id = null;
	private Integer _column = null;
	private Integer _record_id = null;
	private Integer _current_log_id = null;
	private Date _renting_date = null;
	private Date _expected_returning_date;
	private String _url = null;
	private String _type_name = null;

	public String get_url() {
		return _url;
	}

	public void set_url(String _url) {
		this._url = _url;
	}

	public Common() {

	}

	public Integer get_current_log_id() {
		return _current_log_id;
	}

	public void set_current_log_id(Integer _current_log_id) {
		this._current_log_id = _current_log_id;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public Integer get_x_id() {
		return _x_id;
	}

	public void set_x_id(Integer _x_id) {
		this._x_id = _x_id;
	}

	public Integer get_type_id() {
		return _type_id;
	}

	public void set_type_id(Integer _type_id) {
		this._type_id = _type_id;
	}

	public Integer get_physical_electronic() {
		return _physical_electronic;
	}

	public void set_physical_electronic(Integer _physical_electronic) {
		this._physical_electronic = _physical_electronic;
	}

	public String get_library_name() {
		return _library_name;
	}

	public void set_library_name(String _library_name) {
		this._library_name = _library_name;
	}

	public Integer get_library_id() {
		return _library_id;
	}

	public void set_library_id(Integer _library_id) {
		this._library_id = _library_id;
	}

	public String get_floor_name() {
		return _floor_name;
	}

	public void set_floor_name(String _floor_name) {
		this._floor_name = _floor_name;
	}

	public Integer get_floor_id() {
		return _floor_id;
	}

	public void set_floor_id(Integer _floor_id) {
		this._floor_id = _floor_id;
	}

	public String get_bookcase_name() {
		return _bookcase_name;
	}

	public void set_bookcase_name(String _bookcase_name) {
		this._bookcase_name = _bookcase_name;
	}

	public Integer get_bookcase_id() {
		return _bookcase_id;
	}

	public void set_bookcase_id(Integer _bookcase_id) {
		this._bookcase_id = _bookcase_id;
	}

	public String get_bookshelf_name() {
		return _bookshelf_name;
	}

	public void set_bookshelf_name(String _bookshelf_name) {
		this._bookshelf_name = _bookshelf_name;
	}

	public Integer get_bookshelf_id() {
		return _bookshelf_id;
	}

	public void set_bookshelf_id(Integer _bookshelf_id) {
		this._bookshelf_id = _bookshelf_id;
	}

	public Integer get_column() {
		return _column;
	}

	public void set_column(Integer _column) {
		this._column = _column;
	}

	public Integer get_record_id() {
		return _record_id;
	}

	public void set_record_id(Integer _record_id) {
		this._record_id = _record_id;
	}

	public Date get_renting_date() {
		return _renting_date;
	}

	public void set_renting_date(Date _renting_date) {
		this._renting_date = _renting_date;
	}

	public Date get_expected_returning_date() {
		return _expected_returning_date;
	}

	public void set_expected_returning_date(Date _expected_returning_date) {
		this._expected_returning_date = _expected_returning_date;
	}

	public String get_type_name() {
		return _type_name;
	}

	public void set_type_name(String _type_name) {
		this._type_name = _type_name;
	}

}