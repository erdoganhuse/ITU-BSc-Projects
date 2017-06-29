package wicket.quickstart;

import java.io.Serializable;

public class Dvd implements Serializable {

	private String _name = null;	
	private Integer _rental_count = 0;
	private Integer _publish_year = null;
	private Integer _duration = null;
	private Integer _availability = null;
	private Integer _physical_electronic = null;
	final private Integer _type_id = 3;
	private String _library_name = null;
	private String _floor_value = null;
	private Integer _bookcase_id = null;
	private Integer _bookshelf_id = null;
	private String _bookcase_name = null;
	private String _bookshelf_name = null;
	private Integer _column_id = null;
	private String _url = null;
	private Integer _record_id = null;
	private Integer _DVD_id = null;
	private Integer _category_id = null;
	private String _category_name = null;

	public Dvd() {

	}	

	public Dvd(String _name, Integer _rental_count, Integer _publish_year,
			Integer _duration, Integer _availability,
			Integer _physical_electronic, String _library_name,
			String _floor_value, Integer _bookcase_id, Integer _bookshelf_id,
			Integer _column_id, String _url, Integer _record_id, Integer _DVD_id,Integer _category_id) {
		super();
		this._name = _name;
		this._rental_count = _rental_count;
		this._publish_year = _publish_year;
		this._duration = _duration;
		this._availability = _availability;
		this._physical_electronic = _physical_electronic;
		this._library_name = _library_name;
		this._floor_value = _floor_value;
		this._bookcase_id = _bookcase_id;
		this._bookshelf_id = _bookshelf_id;
		this._column_id = _column_id;
		this._url = _url;
		this._record_id = _record_id;
		this._DVD_id = _DVD_id;
		this._category_id = _category_id;
	}



	public Dvd(String _name, Integer _publish_year, Integer _duration,Integer _DVD_id) {		
		this._name = _name;
		this._publish_year = _publish_year;
		this._duration = _duration;
		this._DVD_id = _DVD_id;
	}
	
	
	
	public Integer get_category_id() {
		return _category_id;
	}

	public void set_category_id(Integer _category_id) {
		this._category_id = _category_id;
	}

	public Integer get_DVD_id() {
		return _DVD_id;
	}

	public void set_DVD_id(Integer _DVD_id) {
		this._DVD_id = _DVD_id;
	}

	public Integer get_availability() {
		return _availability;
	}

	public void set_availability(Integer _availability) {
		this._availability = _availability;
	}	

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public Integer get_rental_count() {
		return _rental_count;
	}

	public void set_rental_count(Integer _rental_count) {
		this._rental_count = _rental_count;
	}

	public Integer get_publish_year() {
		return _publish_year;
	}

	public void set_publish_year(Integer _publish_year) {
		this._publish_year = _publish_year;
	}

	public Integer get_duration() {
		return _duration;
	}

	public void set_duration(Integer _duration) {
		this._duration = _duration;
	}

	public Integer get_physical_electronic() {
		return _physical_electronic;
	}

	public void set_physical_electronic(Integer _physical_electronic) {
		if (_physical_electronic > 0)
			this._physical_electronic = 1;
		else
			this._physical_electronic = 0;
	}

	public Integer get_type_id() {
		return _type_id;
	}

	public String get_library_name() {
		return _library_name;
	}

	public void set_library_name(String _library_name) {
		this._library_name = _library_name;
	}

	public String get_floor_value() {
		return _floor_value;
	}

	public void set_floor_value(String _floor_value) {
		this._floor_value = _floor_value;
	}

	public Integer get_bookcase_id() {
		return _bookcase_id;
	}

	public void set_bookcase_id(Integer _bookcase_id) {
		this._bookcase_id = _bookcase_id;
	}

	public Integer get_bookshelf_id() {
		return _bookshelf_id;
	}

	public void set_bookshelf_id(Integer _bookshelf_id) {
		this._bookshelf_id = _bookshelf_id;
	}

	public Integer get_column_id() {
		return _column_id;
	}

	public void set_column_id(Integer _column_id) {
		this._column_id = _column_id;
	}

	public String get_url() {
		return _url;
	}

	public void set_url(String _url) {
		this._url = _url;
	}

	public Integer get_record_id() {
		return _record_id;
	}

	public void set_record_id(Integer _record_id) {
		this._record_id = _record_id;
	}

	public String get_category_name() {
		return _category_name;
	}

	public void set_category_name(String _category_name) {
		this._category_name = _category_name;
	}

	public String get_bookcase_name() {
		return _bookcase_name;
	}

	public void set_bookcase_name(String _bookcase_name) {
		this._bookcase_name = _bookcase_name;
	}

	public String get_bookshelf_name() {
		return _bookshelf_name;
	}

	public void set_bookshelf_name(String _bookshelf_name) {
		this._bookshelf_name = _bookshelf_name;
	}
	
}
