/**
 * 
 */
package com.mts.nrtrde.client;

import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;

/**
 * @author C5132784
 *
 */
public class LoginDialog extends Dialog {

	@Override
	public String getButtons() {
		// TODO Auto-generated method stub
		return super.getButtons();
	}

	@Override
	protected void createButtons() {
		// TODO Auto-generated method stub
//		super.createButtons();
		
	  getButtonBar().removeAll();
	  setFocusWidget(null);
	  Button loginBtn = new Button("Login");
	  loginBtn.setItemId("login");
	  //loginBtn.addSelectionListener(l);
	  setFocusWidget(loginBtn);
	  addButton(loginBtn);
		

	}

}
