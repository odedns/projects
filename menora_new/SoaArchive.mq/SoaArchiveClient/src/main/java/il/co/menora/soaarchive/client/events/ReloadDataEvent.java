package il.co.menora.soaarchive.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import il.co.menora.soaarchive.client.events.ReloadDataEvent.ReloadDataEventHandler;

public class ReloadDataEvent extends GwtEvent<ReloadDataEventHandler> {
	public static interface ReloadDataEventHandler extends EventHandler {
		public void onEvent(ReloadDataEvent event);
	}

	public static Type<ReloadDataEventHandler> TYPE = new Type<ReloadDataEventHandler>();

	@Override
	protected void dispatch(ReloadDataEventHandler handler) {
		handler.onEvent(this);
	}

	@Override
	public Type<ReloadDataEventHandler> getAssociatedType() {
		return TYPE;
	}
}
