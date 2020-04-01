/**
 * 
 */
package com.mts.nrtrde.client.view;

import com.google.gwt.user.client.ui.Composite;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mts.nrtrde.client.presenter.NavigationPresenter;

/**
 * @author Oded Nissan
 *
 */
public class NavigationView extends Composite implements NavigationPresenter.Display {
	
	
	SimplePanel panel;
	Anchor navigation;
	public NavigationView()
	{
	
		panel = new SimplePanel();
		
		navigation = new Anchor("NRTRDE->config");
		panel.add(navigation);
		panel.addStyleName("navigation-panel");
		panel.setSize("100%", "100%");
		
		initWidget(panel);
		
	}

}
