/**
 * 
 */
package com.mts.nrtrde.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Oded Nissan
 *
 */
public class HeaderPresenter implements Presenter {

	private final Display display;
	private final EventBus eventBus;
	
	
	public HeaderPresenter(EventBus eventBus,Display display)
	{
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}
	
	
	
	private void bind()
	{
		ClickHandler handler = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Window.alert("link pressed");
			}
		};
		this.display.getHelplink().addClickHandler(handler);
		this.display.getLogoutLink().addClickHandler(handler);
	}
	/* (non-Javadoc)
	 * @see com.mts.nrtrde.client.presenter.Presenter#go(com.google.gwt.user.client.ui.HasWidgets)
	 */
	public void go(HasWidgets container) {
		// TODO Auto-generated method stub

	}
	
	
	public interface Display {
		HasClickHandlers getHelplink();
		HasClickHandlers getLogoutLink();
		Widget asWidget();
		
		
	}

}
