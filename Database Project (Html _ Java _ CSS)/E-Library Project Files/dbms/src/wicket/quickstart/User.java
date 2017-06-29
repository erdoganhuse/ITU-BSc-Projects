package wicket.quickstart;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
	private Integer _id = null;
	private String _name = null;
	private String _surname = null;
	private String _username = null;
	private String _password = null;
	private Integer _point = null;
	private Integer _authorityState = null;
	private Timestamp _last_access_time = null;

	public User(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public User(String name, String surname, String username, String password,
			Integer AuthorStatu, Integer point) {
		this.setUsername(username);
		this.setPassword(password);
		this.setName(name);
		this.setSurname(surname);
		this.set_authorityState(AuthorStatu);
		this.setPoint(point);
	}

	public User() {
	}

	public void setName(String name) {
		this._name = name;
	}

	public String getName() {
		return this._name;
	}

	public void setSurname(String surname) {
		this._surname = surname;
	}

	public String getSurname() {
		return this._surname;
	}

	public void setUsername(String username) {
		this._username = username;
	}

	public String getUsername() {
		return this._username;
	}

	public void setPassword(String password) {
		this._password = password;
	}

	public String getPassword() {
		return this._password;
	}

	public Integer get_authorityState() {
		return _authorityState;
	}

	public void set_authorityState(Integer _authorStatu) {
		this._authorityState = _authorStatu;
	}

	public void setPoint(Integer point) {
		this._point = point;
	}

	public Integer getPoint() {
		return this._point;
	}

	public Integer get_id() {
		return _id;
	}

	public void set_id(Integer _id) {
		this._id = _id;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_surname() {
		return _surname;
	}

	public void set_surname(String _surname) {
		this._surname = _surname;
	}

	public String get_username() {
		return _username;
	}

	public void set_username(String _username) {
		this._username = _username;
	}

	public String get_password() {
		return _password;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	public Integer get_point() {
		return _point;
	}

	public void set_point(Integer _point) {
		this._point = _point;
	}

	public Timestamp get_last_access_time() {
		return _last_access_time;
	}

	public void set_last_access_time(Timestamp _last_access_time) {
		this._last_access_time = _last_access_time;
	}
}