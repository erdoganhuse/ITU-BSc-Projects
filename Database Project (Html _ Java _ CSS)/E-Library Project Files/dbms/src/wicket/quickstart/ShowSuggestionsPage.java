package wicket.quickstart;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.validation.validator.RangeValidator;

import wicket.quickstart.AdminProfilePage.Log;

public class ShowSuggestionsPage extends BasePage {
	public ShowSuggestionsPage() {
		AdminNavigationPanel adminNavigation = new AdminNavigationPanel(
				"adminNavigation");
		this.add(adminNavigation);

		// LOGS
		List<Request> reqlist = new ArrayList();
		DatabaseConnection dbc = new DatabaseConnection();

		try {
			String query = "SELECT suggestion_id, suggestion, user_name, user_surname FROM "
					+ "suggestions LEFT JOIN users ON suggestions.user_id = users.user_id;";
			ResultSet rs = dbc.GetResult(query);

			while (rs.next()) {
				Request req = new Request();
				req.set_suggestion_id(rs.getInt("suggestion_id"));
				req.set_message(rs.getString("suggestion"));
				req.set_name(rs.getString("user_name"));
				req.set_surname(rs.getString("user_surname"));
				if (req.get_name() == null){
					req.set_name("Public");
					req.set_surname("User");
				}
				reqlist.add(req);
			}
		} catch (SQLException e) {
			throw new UnsupportedOperationException(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbc.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ListDataProvider<Request> reqlistDataProvider = new ListDataProvider<Request>(
				reqlist);

		DataView<Request> reqdataView = new DataView<Request>("logrows",
				reqlistDataProvider) {
			@Override
			protected void populateItem(Item<Request> reqitem) {
				final Request req = reqitem.getModelObject();
				RepeatingView repeatingView = new RepeatingView("logdataRow");

				// MESSAGE
				repeatingView.add(new Label(repeatingView.newChildId(), req
						.get_message()));
				// USER
				repeatingView.add(new Label(repeatingView.newChildId(), req
						.get_name() + " " + req.get_surname()));

				// DELETE
				repeatingView.add(new Link(repeatingView.newChildId()) {
					@Override
					public void onClick() {
						try {
							DatabaseConnection dbcreq = new DatabaseConnection();
							String query = String
									.format("DELETE FROM suggestions WHERE suggestion_id = %d;",
											req.get_suggestion_id());
							dbcreq.Insert(query);
							
							this.setResponsePage(new ShowSuggestionsPage());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				reqitem.add(repeatingView);
			}
		};
		add(reqdataView);

	}

	public class Request extends User implements Serializable {
		private String _message = null;
		private Integer _suggestion_id = null;

		public String get_message() {
			return _message;
		}

		public void set_message(String _message) {
			this._message = _message;
		}

		public Integer get_suggestion_id() {
			return _suggestion_id;
		}

		public void set_suggestion_id(Integer _suggestion_id) {
			this._suggestion_id = _suggestion_id;
		}

	}
}