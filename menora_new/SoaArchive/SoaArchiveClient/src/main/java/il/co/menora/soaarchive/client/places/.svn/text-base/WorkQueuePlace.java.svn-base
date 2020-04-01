package il.co.menora.soaarchive.client.places;

import java.util.HashMap;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

// Don't forget to add this place to ClientPlaceHistoryMapper!
public class WorkQueuePlace extends AbstractPlace {
	private final String queueId;
	private final String sortField;

	public WorkQueuePlace(String queueId, String sortField) {
		this.queueId = queueId;
		this.sortField = sortField;
	}

	public String getQueueId() {
		return queueId;
	}

	public String getSortField() {
		return sortField;
	}

	@Prefix("workQueue")
	public static class Tokenizer implements PlaceTokenizer<WorkQueuePlace> {
		@Override
		public String getToken(WorkQueuePlace place) {
			return PlaceUtils.getEncodedParameter("queueId", place.getQueueId()) + //
					PlaceUtils.getEncodedParameter("sortField", place.getSortField());
		}

		@Override
		public WorkQueuePlace getPlace(String token) {
			HashMap<String, String> parameters = PlaceUtils.getParametersFromToken(token);
			return new WorkQueuePlace(parameters.get("queueId"), //
					parameters.get("sortField"));
		}
	}
}
