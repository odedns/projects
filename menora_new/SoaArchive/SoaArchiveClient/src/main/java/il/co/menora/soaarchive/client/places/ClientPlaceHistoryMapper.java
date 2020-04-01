package il.co.menora.soaarchive.client.places;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ //
		HomePlace.Tokenizer.class, //
		WorkQueuePlace.Tokenizer.class, //
		OutgoingPlace.Tokenizer.class
})
public interface ClientPlaceHistoryMapper extends PlaceHistoryMapper {
	public static final ClientPlaceHistoryMapper INSTANCE = GWT.create(ClientPlaceHistoryMapper.class);
}
