/**
 * 
 */
package com.mts.nrtrde.client.presenter;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.Widget;
import com.mts.nrtrde.client.event.NavigationEvent;
import com.mts.nrtrde.client.presenter.HeaderPresenter.Display;

/**
 * @author Oded Nissan
 *
 */
public class TreePresenter implements Presenter {
	public EventBus getEventBus() {
		return eventBus;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	private EventBus eventBus;
	private Display display;
	
	public TreePresenter(EventBus eventBus,Display display)
	{
		this.eventBus = eventBus;
		this.display = display;
		//this.display.getTree().addListener(Events.OnClick,this);
		
	}
	
	/* (non-Javadoc)
	 * @see com.mts.nrtrde.client.presenter.Presenter#go(com.google.gwt.user.client.ui.HasWidgets)
	 */
	public void go(HasWidgets container) {
		// TODO Auto-generated method stub

	}
	
	public interface Display {
		TreePanel getTree();
		Widget asWidget();
	}

	public void onTreeItemSelected(TreeItem item) {
		// TODO Auto-generated method stub
		GWT.log("fired Event: " + item.getText());
		this.eventBus.fireEvent(new NavigationEvent(item.getText()));
	}

	public void onTreeItemStateChanged(TreeItem item) {
		// TODO Auto-generated method stub
		
	}

	public void handleEvent(TreePanelEvent<BaseTreeModel> be) {
		// TODO Auto-generated method stub
		String item = be.getNode().getModel().get("name");
		GWT.log("got: "+ item);
		this.eventBus.fireEvent(new NavigationEvent(item));
		
	}

	
}
