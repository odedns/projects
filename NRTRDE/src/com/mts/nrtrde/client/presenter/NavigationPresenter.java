/**
 * 
 */
package com.mts.nrtrde.client.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.mts.nrtrde.client.presenter.ErrorReportPresenter.Display;
import com.mts.nrtrde.client.view.NavigationView;

/**
 * @author Oded Nissan
 *
 */
public class NavigationPresenter implements Presenter {

	
	private EventBus eventBus;
	private Display display;
	
	public NavigationPresenter(EventBus eventBus,Display display)
	{
		this.eventBus = eventBus;
		this.display = display;
		
		
	}
	/* (non-Javadoc)
	 * @see com.mts.nrtrde.client.presenter.Presenter#go(com.google.gwt.user.client.ui.HasWidgets)
	 */
	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
		 container.clear();
		 //NavigationView v = (NavigationView)display;
		 
		 container.add(display.asWidget());

	}

	public interface Display {
		
		Widget asWidget();
	}

}
