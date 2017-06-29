package wicket.quickstart;

import java.util.Date;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class UserBasePage extends WebPage {
	public UserBasePage() {
		this(null);
	}

	public UserBasePage(IModel model) {
		super(model);
		this.add(new UserNavigationPanel("userNavigation"));

	}
}