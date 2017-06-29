package wicket.quickstart;

import java.awt.Button;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

public class register extends Form {
	public register(String id, User user) {
		super(id);

		CompoundPropertyModel model = new CompoundPropertyModel(user);
		this.setModel(model);
		this.add(new TextField("name"));
		this.add(new TextField("surname"));
		this.add(new TextField("username"));
		this.add(new PasswordTextField("password"));
	}

	@Override
	public void onSubmit() {
		User user = (User) this.getModelObject();
		String original_password = user.get_password();
		String hashed_password = hashtomd5(original_password);
		user.set_password(hashed_password);
		user.set_authorityState(0);

		try {
			UserControl addtodb = new UserControl(user);
			addtodb.AddUser();
			this.setResponsePage(new HomePage());
		} catch (Exception ex) {
			this.setResponsePage(new registerPage(
					"Bu Kullanıcı Adı Zaten Kayıtlı, Yeniden Deneyin."));
		}

	}

	public String hashtomd5(String orginal_pass) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(orginal_pass.getBytes());
			byte[] byteData = md.digest();

	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orginal_pass;
	}
}