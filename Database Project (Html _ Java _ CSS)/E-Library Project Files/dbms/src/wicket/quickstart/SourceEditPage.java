package wicket.quickstart;

public class SourceEditPage extends BasePage {
	public SourceEditPage(Common common) {
		AdminNavigationPanel adminNavigation = new AdminNavigationPanel(
				"adminNavigation");
		this.add(adminNavigation);
		add(new SourceEditForm("source_edit_form", common));
	}
}
