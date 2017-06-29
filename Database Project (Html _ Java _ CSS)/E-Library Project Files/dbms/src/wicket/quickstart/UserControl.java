package wicket.quickstart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class UserControl {
	private User unkown = null;
	private DatabaseConnection dbcon = null;

	public UserControl(User user) {
		this.unkown = user;
		dbcon = new DatabaseConnection();
	}

	public User GetUser() throws Exception {
		try {
			String query = "SELECT * FROM users";

			ResultSet result = dbcon.GetResult(query);
			boolean check = false;
			while (result.next()) {
				if (result.getString("user_password").equals(
						this.unkown.getPassword())
						&& result.getString("user_nickname").equals(
								this.unkown.getUsername())) {
					this.unkown.setName(result.getString("user_name"));
					this.unkown.setSurname(result.getString("user_surname"));
					this.unkown.set_id(result.getInt("user_id"));
					this.unkown.setPoint(result.getInt("user_point"));
					this.unkown.set_authorityState(result
							.getInt("user_authority_state"));
					check = true;
				}
			}
			if (check == false) {
				this.unkown = null;
			} else {
				query = String
						.format("UPDATE users SET `user_last_access_time` = NOW() WHERE user_id = '%d'",
								this.unkown.get_id());
				dbcon.Insert(query);
			}

			return this.unkown;

		} catch (SQLException e) {
			throw new UnsupportedOperationException(e.getMessage());
		} finally {
			dbcon.close();
		}
	}

	public void AddUser() {
		String query = String
				.format("INSERT INTO "
						+ "users (`user_nickname`, `user_name`, `user_surname`, `user_password`,`user_authority_state`) "
						+ "VALUES ('%s', '%s', '%s', '%s', %d);",
						unkown.getUsername(), unkown.getName(),
						unkown.getSurname(), unkown.getPassword(),
						unkown.get_authorityState());
		try {
			dbcon.Insert(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}