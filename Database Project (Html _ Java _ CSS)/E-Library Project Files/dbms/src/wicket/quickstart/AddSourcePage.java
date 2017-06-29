package wicket.quickstart;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOption;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.markup.html.form.RadioChoice;
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

public class AddSourcePage extends BasePage {
	
	public AddSourcePage() {
		AdminNavigationPanel adminNavigation = new AdminNavigationPanel(
				"adminNavigation");
		this.add(adminNavigation);
	
		Link addBookPageLink = new Link("addbook") {
			@Override
			public void onClick() {
				this.setResponsePage(new AddBookPage());
			}
		};
		this.add(addBookPageLink);
		
		Link addDVDPageLink = new Link("adddvd") {
			@Override
			public void onClick() {
				this.setResponsePage(new AddDVDPage());
			}
		};
		this.add(addDVDPageLink);
		
		Link addMagazinePageLink = new Link("addmagazine") {
			@Override
			public void onClick() {
				this.setResponsePage(new AddMagazinePage());
			}
		};
		this.add(addMagazinePageLink);
	}
	
}