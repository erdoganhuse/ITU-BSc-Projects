package wicket.quickstart;

import java.util.Date;

import org.apache.wicket.Component;
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
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.validation.validator.RangeValidator;

public class HomePage extends BasePage {
	public HomePage() {
		GuestNavigationPanel guestNavigation = new GuestNavigationPanel(
				"guestNavigation");
		this.add(guestNavigation);
		
		UserNavigationPanel userNavigation = new UserNavigationPanel(
				"userNavigation");
		this.add(userNavigation);
		
		AdminNavigationPanel adminNavigation = new AdminNavigationPanel(
				"adminNavigation");
		this.add(adminNavigation);
		
		if (BasicAuthenticationSession.get().isSignedIn()) {
			get("guestNavigation").setVisible(false);
			if(((BasicAuthenticationSession) BasicAuthenticationSession.get()).getUser().get_authorityState() == 0)
			{
				get("adminNavigation").setVisible(false);
			}
			else
			{
				get("userNavigation").setVisible(false);
			}
		} 
		else {
			get("adminNavigation").setVisible(false);
			get("userNavigation").setVisible(false);
		}
		Date now = new Date();
		Label dateTimeLabel = new Label("datetime", now.toString());

		this.add(new SearchForm("searchForm"));
		this.add(dateTimeLabel);

	}
}