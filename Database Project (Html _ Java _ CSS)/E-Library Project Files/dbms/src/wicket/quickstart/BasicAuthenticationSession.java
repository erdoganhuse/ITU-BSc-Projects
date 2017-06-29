package wicket.quickstart;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class BasicAuthenticationSession extends AuthenticatedWebSession {

	private DatabaseConnection dbc;
	private User user;

	public BasicAuthenticationSession(Request request) {
		super(request);
		user = new User();
		user.setUsername("");
		user.setName("");
		user.setSurname("");
		user.set_authorityState(0);
	}

	@Override
	public void signOut() {
		signIn(false);
		user = new User();
		user.setUsername("");
		user.setName("");
		user.setSurname("");
		user.set_authorityState(0);
	}

	@Override
	public boolean authenticate(String username, String password) {
		dbc = new DatabaseConnection();
		ResultSet rs;
		User tempUser = new User();
		boolean check = false;
		tempUser.setUsername("");
		tempUser.setPassword("");
		try {
			String query = String.format(
					"SELECT * FROM users where user_nickname = '%s'", username);
			rs = dbc.GetResult(query);
			while (rs.next()) {
				tempUser = new User(rs.getString("user_name"),
						rs.getString("user_surname"),
						rs.getString("user_nickname"),
						rs.getString("user_password"),
						rs.getInt("user_authority_state"),
						rs.getInt("user_point"));
				tempUser.set_id(rs.getInt("user_id"));
				tempUser.set_last_access_time(rs.getTimestamp("user_last_access_time"));
			}
		
			String hashed_password = hashtomd5(password);
			
			if (tempUser.getUsername().equals(username)
					&& tempUser.getPassword().equals(hashed_password)) {
				check = true;

				query = String
						.format("UPDATE users SET `user_last_access_time` = NOW() WHERE user_id = '%d'",
								tempUser.get_id());
				dbc.Insert(query);
			} else {
				check = false;
				user.setUsername("");
				user.setName("");
				user.setSurname("");
				user.set_authorityState(0);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbc.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.setUser(tempUser);
		return check;
	}

	public User getUser() {
		return user;
	}

	private void setUser(User user) {
		this.user = user;
	}

	@Override
	public Roles getRoles() {
		return null;
	}
	
	public String hashtomd5(String orginal_pass) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(orginal_pass.getBytes());
			byte[] byteData = md.digest();

	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orginal_pass;
	}
}