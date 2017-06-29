package wicket.quickstart;

import java.awt.Button;
import java.util.Date;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;

public class UserPage extends BasePage {

	public UserPage() {
		User user = new User();
		// this.add(new UserLogin("loginForm", user));
	}
}
