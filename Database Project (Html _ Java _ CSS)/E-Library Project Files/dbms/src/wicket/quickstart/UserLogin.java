package wicket.quickstart;

import java.awt.Button;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class UserLogin extends Form {
	private TextField usernameField;
	private PasswordTextField passwordField;
	private Label loginStatus;

	public UserLogin(String id) {
		super(id);

		usernameField = new TextField("username", Model.of(""));
		passwordField = new PasswordTextField("password", Model.of(""));

		add(usernameField);
		add(passwordField);
		Link registerPage_link = new Link("registerPage_link") {
			public void onClick() {
				this.setResponsePage(new registerPage(""));
			}
		};
		add(registerPage_link);
	}

	public void onSubmit() {
		String username = (String) usernameField.getDefaultModelObject();
		String password = (String) passwordField.getDefaultModelObject();

		boolean authResult = BasicAuthenticationSession.get().signIn(username,
				password);
		// if authentication succeeds redirect user to the requested page
		if (authResult)
			this.setResponsePage(new HomePage());
	}
}