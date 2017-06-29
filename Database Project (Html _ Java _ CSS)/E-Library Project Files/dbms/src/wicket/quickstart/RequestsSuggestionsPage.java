package wicket.quickstart;

import java.util.Date;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.RangeTextField;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.validation.validator.RangeValidator;

public class RequestsSuggestionsPage extends BasePage {
	private TextArea message;

	public RequestsSuggestionsPage() {
		GuestNavigationPanel guestNavigation = new GuestNavigationPanel(
				"guestNavigation");
		this.add(guestNavigation);

		final Input input = new Input();
		setDefaultModel(new CompoundPropertyModel<Input>(input));

		Form<?> form = new Form("form") {
			@Override
			protected void onSubmit() {
				final DatabaseConnection dbc = new DatabaseConnection();
				String str = "";
				if (((BasicAuthenticationSession) BasicAuthenticationSession
						.get()).isSignedIn()) {
					final Integer user_id = ((BasicAuthenticationSession) BasicAuthenticationSession
							.get()).getUser().get_id();

					str = String
							.format("INSERT INTO suggestions (`suggestion`,`user_id`) VALUES ('%s',%d)",
									input, user_id);
				} else {
					str = String
							.format("INSERT INTO suggestions (`suggestion`) VALUES ('%s')",
									input);
				}
				try {
					dbc.Insert(str);
					dbc.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		add(form);
		form.add(new TextArea<String>("text"));

	}

	private static class Input implements IClusterable {
		public String text = "";

		@Override
		public String toString() {
			return text;
		}
	}

}