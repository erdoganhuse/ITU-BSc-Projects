package wicket.quickstart;

import java.sql.ResultSet;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

public class UserEditProfileForm extends Form {
	private TextField u_name;
	private TextField u_lname;
	private TextField u_uname;
	private PasswordTextField currentpassword;
	private PasswordTextField newpassword;
	private PasswordTextField anewpassword;

	public UserEditProfileForm(String id) {
		super(id);
		String name =((BasicAuthenticationSession) BasicAuthenticationSession.get())
				.getUser().getName();
		String surname =((BasicAuthenticationSession) BasicAuthenticationSession.get())
				.getUser().getSurname();
		String username =((BasicAuthenticationSession) BasicAuthenticationSession.get())
				.getUser().getUsername();
		
		u_name = new TextField("u_name", Model.of(name));
		add(u_name);

		u_lname = new TextField("u_lname", Model.of(surname));
		add(u_lname);

		u_uname = new TextField("u_uname", Model.of(username));
		add(u_uname);
		
		currentpassword = new PasswordTextField("currentpassword", Model.of(""));
		add(currentpassword);
		currentpassword.setRequired(false);

		newpassword = new PasswordTextField("newpassword", Model.of(""));
		add(newpassword);
		newpassword.setRequired(false);

		anewpassword = new PasswordTextField("anewpassword", Model.of(""));
		add(anewpassword);
		newpassword.setRequired(false);

	}

	public void onSubmit() {
		DatabaseConnection dbc = new DatabaseConnection();

		ResultSet rs;
		try {
			rs = dbc.GetResult(String
					.format("select `user_id`,`user_password` from users where `user_nickname` = '%s';",
							((BasicAuthenticationSession) BasicAuthenticationSession
									.get()).getUser().getUsername()));
			rs.next();
			int x = rs.getInt("user_id");
			String u_pass = rs.getString("user_password");
			if (u_name.getModelObject() != null) {
				dbc.Insert(String
						.format("UPDATE users MODIFY SET `user_name` = '%s' where `user_id` = %d;",
								u_name.getModelObject(), x));
			}
			if (u_lname.getModelObject() != null) {
				dbc.Insert(String
						.format("UPDATE users MODIFY SET `user_surname` = '%s' where `user_id` = %d;",
								u_lname.getModelObject(), x));
			}
			if (u_uname.getModelObject() != null) {
				dbc.Insert(String
						.format("UPDATE users MODIFY SET `user_nickname` = '%s' where `user_id` = %d;",
								u_uname.getModelObject(), x));
			}
			String cur_pass = (String) currentpassword.getModelObject();
			if (cur_pass.equals(u_pass)
					&& newpassword.getModelObject() != null
					&& anewpassword.getModelObject() != null
					&& newpassword.getModelObject().toString()
							.equals(anewpassword.getModelObject().toString())) {
				dbc.Insert(String
						.format("UPDATE users MODIFY SET `user_password` = '%s' where `user_id` = %d;",
								newpassword.getModelObject(), x));
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		((BasicAuthenticationSession) BasicAuthenticationSession.get())
		.getUser().setName(u_name.getDefaultModelObjectAsString());
		((BasicAuthenticationSession) BasicAuthenticationSession.get())
		.getUser().setSurname(u_lname.getDefaultModelObjectAsString());
		this.setResponsePage(new HomePage());
	}
}
