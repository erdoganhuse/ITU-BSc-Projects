package wicket.quickstart;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class UserNavigationPanel extends Panel {
	public UserNavigationPanel(String id) {
		super(id);

		Link homePageLink = new Link("home") {
			@Override
			public void onClick() {
				this.setResponsePage(new HomePage());
			}
		};
		this.add(homePageLink);

		Link user_profile_page_link = new Link("user_profile_page_link") {
			@Override
			public void onClick() {
				try {
					this.setResponsePage(new UserProfilePage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		this.add(user_profile_page_link);
		Link most_popular_sources_page_link = new Link(
				"most_popular_sources_page_link") {
			@Override
			public void onClick() {
				try {
					this.setResponsePage(new PopularSourcesPage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		this.add(most_popular_sources_page_link);

		Link user_edit_page_link = new Link("user_edit_page_link") {
			@Override
			public void onClick() {
				this.setResponsePage(new EditProfilePage());
			}
		};
		this.add(user_edit_page_link);
		Link user_history_page_link = new Link("user_history_page_link") {
			@Override
			public void onClick() {
				this.setResponsePage(new ArchivePage());
			}
		};
		this.add(user_history_page_link);
	}
}