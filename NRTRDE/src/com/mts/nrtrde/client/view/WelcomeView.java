/**
 * 
 */
package com.mts.nrtrde.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mts.nrtrde.client.NRTRDE;
import com.mts.nrtrde.client.presenter.WelcomePresenter;

/**
 * @author C5132784
 *
 */
public class WelcomeView extends Composite implements WelcomePresenter.Display {

	
	
	public WelcomeView()
	{
		SimplePanel panel = new SimplePanel();
	
		panel.add(new Image(NRTRDE.images.NRTRDE()));
		initWidget(panel);
		
	}
	
	
	
}
