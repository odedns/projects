package il.co.menora.soaarchive.client.places;

import com.google.gwt.place.shared.PlaceHistoryHandler;

public class ClientPlaceHistoryHandler {
	public static final PlaceHistoryHandler INSTANCE = new PlaceHistoryHandler(ClientPlaceHistoryMapper.INSTANCE);
}
