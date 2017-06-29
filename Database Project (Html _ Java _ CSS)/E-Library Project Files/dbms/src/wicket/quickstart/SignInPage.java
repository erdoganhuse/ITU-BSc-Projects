package wicket.quickstart;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;

public class SignInPage extends BasePage {
	private String username;
	private String password;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		GuestNavigationPanel guestNavigation = new GuestNavigationPanel(
				"guestNavigation");
		this.add(guestNavigation);
		UserLogin form = new UserLogin("form");

		add(form);
	}
}