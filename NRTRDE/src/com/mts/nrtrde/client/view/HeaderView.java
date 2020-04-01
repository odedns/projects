/**
 * 
 */
package com.mts.nrtrde.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mts.nrtrde.client.NRTRDE;
import com.mts.nrtrde.client.presenter.HeaderPresenter;

/**
 * @author Oded Nissan
 *
 */
public class HeaderView extends Composite  implements HeaderPresenter.Display{
	DockPanel panel;
	HorizontalPanel linkPanel;
	
	HorizontalPanel titlePanel;
	Anchor helpLink;
	Anchor logoutLink;
	
	
	public HeaderView()
	{
		panel = new DockPanel();
		
		
		titlePanel = new HorizontalPanel();
		titlePanel.add(new Image(NRTRDE.images.logo()));
		//titlePanel.add(new HTML("<h2>this is the header</h2>"));
		panel.add(titlePanel,DockPanel.WEST);
		panel.addStyleName("header-panel");
		
		
		
		linkPanel = new HorizontalPanel();
		helpLink = new Anchor("Help");
		logoutLink = new Anchor("Logout");
		
		linkPanel.setSpacing(5);
		linkPanel.add(helpLink);
		linkPanel.add(logoutLink);
		
		panel.add(linkPanel,DockPanel.EAST);
		panel.setCellHorizontalAlignment(linkPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		
		int w = Window.getClientWidth();
		String width = w - 40 + "px";
		panel.setWidth(width);
		GWT.log("setting width of panel: " + (w - 10));
		
		panel.addStyleName("header-panel");
		initWidget(panel);
		
		
	}

	public HasClickHandlers getHelplink() {
		// TODO Auto-generated method stub
		return (this.helpLink);
	}

	public HasClickHandlers getLogoutLink() {
		// TODO Auto-generated method stub
		return(this.logoutLink);
	}
	

}
