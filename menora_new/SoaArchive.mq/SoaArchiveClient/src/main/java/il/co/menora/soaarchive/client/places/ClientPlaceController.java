package il.co.menora.soaarchive.client.places;

import com.google.gwt.place.shared.PlaceController;

import il.co.menora.soaarchive.client.events.ClientEventBus;

public class ClientPlaceController {
	private static final PlaceController placeController = new PlaceController(ClientEventBus.get());

	public static PlaceController get() {
		return placeController;
	}
}
