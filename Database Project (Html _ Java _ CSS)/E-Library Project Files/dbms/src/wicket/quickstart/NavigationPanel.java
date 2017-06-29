package wicket.quickstart;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class NavigationPanel extends Panel {
	public NavigationPanel(String id) {
		super(id);
		Link loginlink = new Link("loginlink") {
			@Override
			public void onClick() {
				this.setResponsePage(new SignInPage());
			}
		};
		this.add(loginlink);
		Link logoutlink = new Link("logoutlink") {
			@Override
			public void onClick() {
				BasicAuthenticationSession.get().signOut();
				this.setResponsePage(new HomePage());
			}
		};
		this.add(logoutlink);
		Link registerlink = new Link("registerlink") {
			@Override
			public void onClick() {
				this.setResponsePage(new registerPage(""));
			}
		};
		this.add(registerlink);
		String namesurname = ((BasicAuthenticationSession) BasicAuthenticationSession
				.get()).getUser().getName()
				+ " "
				+ ((BasicAuthenticationSession) BasicAuthenticationSession
						.get()).getUser().getSurname();
		if (((BasicAuthenticationSession) BasicAuthenticationSession.get())
				.getUser().getName() == null) {
			namesurname = "";
		}
		Label user_name = new Label("user_name", namesurname);
		this.add(user_name);

		Link homePageLink = new Link("home") {
			@Override
			public void onClick() {
				this.setResponsePage(new HomePage());
			}
		};
		this.add(homePageLink);

		if (BasicAuthenticationSession.get().isSignedIn()) {
			loginlink.setVisible(false);
			registerlink.setVisible(false);
		} else {
			logoutlink.setVisible(false);
			user_name.setVisible(false);
		}
	}
}