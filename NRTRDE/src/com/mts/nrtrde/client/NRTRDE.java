package com.mts.nrtrde.client;



import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Theme;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.mts.nrtrde.client.event.NavigationEvent;
import com.mts.nrtrde.client.presenter.HeaderPresenter;
import com.mts.nrtrde.client.presenter.TreePresenter;
import com.mts.nrtrde.client.view.HeaderView;
import com.mts.nrtrde.client.view.NavigationView;
import com.mts.nrtrde.client.view.ParametersView;
import com.mts.nrtrde.client.view.TreeView;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NRTRDE implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */


	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */

	 public static final AppResources images = GWT.create(AppResources.class);
	 public static final NRTRDEConstants constants = GWT.create(NRTRDEConstants.class);
//	 DialogBox loginDialog;

	/**
	 * This is the entry point method.
	 */
	
	@SuppressWarnings("deprecation")
	public void onModuleLoad() {

	
	
		GXT.setDefaultTheme(Theme.BLUE, true);
		
		HeaderView headerView = new HeaderView();
		SimpleEventBus eventBus = new SimpleEventBus();
		HeaderPresenter headerPresenter = new HeaderPresenter(eventBus, headerView);
		TreeView treeView = new TreeView();
		TreePresenter treePresenter = new TreePresenter(eventBus,treeView);
		treeView.setPresenter(treePresenter);
		NavigationView navView = new NavigationView();
		SimplePanel	content = new SimplePanel();

		content.setSize("100%", "100%");
		
		VerticalPanel vContentPanel = new VerticalPanel();
		vContentPanel.setSize("100%", "100%");
		vContentPanel.add(navView);
		vContentPanel.add(content);				
		eventBus.fireEvent(new NavigationEvent("NRTRDE"));		 		 
		AppController appController = new AppController(content,eventBus);
		
	
		RootPanel.get("headerId").add(headerView);
		RootPanel.get("navId").add(treeView);
		/*
		 * need to expand the tree only after adding the TreePanel to 
		 * the root panel.
		 */
		treeView.getTree().expandAll();
		RootPanel.get("contId").add(vContentPanel);
		showLogin();
	
		
		
		
	}
	
	
	private void showLogin() {
		// TODO Auto-generated method stub
		
		final LoginDialog loginDialog = new LoginDialog();
		
		FormPanel formPanel = new FormPanel();
		formPanel.setHeading("Login");
		
		
		TextField userField = new TextField();
		userField.setFieldLabel("Username:");
		TextField passField = new TextField();
		passField.setFieldLabel("Password:");
		

		formPanel.add(userField);
		formPanel.add(passField);
		//formPanel.add(loginButton);
		Button loginButton = loginDialog.getButtonById("login");
		loginButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				// TODO Auto-generated method stub
				loginDialog.hide();
			}
		});
			
		
		formPanel.layout();
		loginDialog.setHideOnButtonClick(true);
		loginDialog.add(formPanel);
		loginDialog.show();
	}
	
	
}
