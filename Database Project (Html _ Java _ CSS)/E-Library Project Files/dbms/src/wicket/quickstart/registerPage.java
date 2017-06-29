package wicket.quickstart;

import java.awt.Button;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.markup.html.basic.Label;

public class registerPage extends BasePage {
	public registerPage(String string) {
		User user = new User();
		this.add(new register("register", user));
		Label warnMessage = new Label("warn", string);
		this.add(warnMessage);
		GuestNavigationPanel guestNavigation = new GuestNavigationPanel(
				"guestNavigation");
		this.add(guestNavigation);

	}
}
