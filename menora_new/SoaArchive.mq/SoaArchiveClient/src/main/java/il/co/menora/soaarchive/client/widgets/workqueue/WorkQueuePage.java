package il.co.menora.soaarchive.client.widgets.workqueue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import il.co.menora.soaarchive.client.places.ClientPlaceController;
import il.co.menora.soaarchive.client.places.WorkQueuePlace;

public class WorkQueuePage extends Composite {
	private static WorkQueuePageUiBinder uiBinder = GWT.create(WorkQueuePageUiBinder.class);

	interface WorkQueuePageUiBinder extends UiBinder<Widget, WorkQueuePage> {
		// Marker interface
	}

	@UiField
	SpanElement workQueueNumberSpan;

	public WorkQueuePage() {
		initWidget(uiBinder.createAndBindUi(this));

		WorkQueuePlace place = (WorkQueuePlace) ClientPlaceController.get().getWhere();
		workQueueNumberSpan.setInnerText(place.getQueueId());
	}
}
