package wicket.quickstart;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class AdminNavigationPanel extends Panel {
	public AdminNavigationPanel(String id) {
		super(id);

		Link homePageLink = new Link("home") {
			@Override
			public void onClick() {
				this.setResponsePage(new HomePage());
			}
		};
		this.add(homePageLink);

		Link admin_profile_page_link = new Link("admin_profile_page_link") {
			@Override
			public void onClick() {
				this.setResponsePage(new AdminProfilePage());
			}
		};
		this.add(admin_profile_page_link);

		Link admin_edit_page_link = new Link("admin_edit_page_link") {
			@Override
			public void onClick() {
				this.setResponsePage(new EditProfilePage());
			}
		};
		this.add(admin_edit_page_link);
		
		Link add_source_page_link = new Link("add_source_page_link") {
			@Override
			public void onClick() {
				this.setResponsePage(new AddSourcePage());
			}
		};
		this.add(add_source_page_link);
		
		Link messages_page_link = new Link("messages_page_link") {
			@Override
			public void onClick() {
				this.setResponsePage(new ShowSuggestionsPage());
			}
		};
		this.add(messages_page_link);
	}
}