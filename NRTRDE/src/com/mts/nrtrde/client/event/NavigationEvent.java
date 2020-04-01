/**
 * 
 */
package com.mts.nrtrde.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author Oded Nissan
 *
 */
public class NavigationEvent extends GwtEvent<NavigationEventHandler> {

	private String id;
	 public static Type<NavigationEventHandler> TYPE = new Type<NavigationEventHandler>();
	 
	 public NavigationEvent(String id) {
		    this.setId(id);
	  }
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NavigationEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(NavigationEventHandler handler) {
		// TODO Auto-generated method stub
		handler.doNavigation(this);
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

}
