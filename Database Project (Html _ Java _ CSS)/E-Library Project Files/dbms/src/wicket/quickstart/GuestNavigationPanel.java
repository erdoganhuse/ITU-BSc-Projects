package wicket.quickstart;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class GuestNavigationPanel extends Panel {
	public GuestNavigationPanel(String id) {
		super(id);

		Link homePageLink = new Link("home") {
			@Override
			public void onClick() {
				this.setResponsePage(new HomePage());
			}
		};
		this.add(homePageLink);
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
		Link requests_suggestions_page_link = new Link(
				"requests_suggestions_page_link") {
			@Override
			public void onClick() {
				try {
					this.setResponsePage(new RequestsSuggestionsPage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		this.add(requests_suggestions_page_link);
		Link contact_us_page_link = new Link("contact_us_page_link") {
			@Override
			public void onClick() {
				this.setResponsePage(new ContactPage());
			}
		};
		this.add(contact_us_page_link);
		Link about_us_page_link = new Link("about_us_page_link") {
			@Override
			public void onClick() {
				this.setResponsePage(new AboutUsPage());
			}
		};
		this.add(about_us_page_link);
	}
}