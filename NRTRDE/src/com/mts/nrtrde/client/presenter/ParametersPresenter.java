/**
 * 
 */
package com.mts.nrtrde.client.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.mts.nrtrde.client.presenter.ErrorReportPresenter.Display;

/**
 * @author C5132784
 *
 */
public class ParametersPresenter implements Presenter {

	
	private EventBus eventBus;
	private Display display;
	
	public ParametersPresenter(EventBus eventBus,Display display)
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
		 container.add(display.asWidget());

	}

	public interface Display {
		
		Widget asWidget();
	}

}
