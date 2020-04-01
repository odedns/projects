package il.co.menora.soaarchive.client.places;

import com.google.gwt.place.shared.Place;

public abstract class AbstractPlace extends Place {
	public String getToken() {
		return ClientPlaceHistoryMapper.INSTANCE.getToken(this);
	}
}
