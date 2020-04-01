package il.co.menora.soaarchive.client.widgets.layout;


import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.polymer.elemental.Element;
import com.vaadin.polymer.elemental.Event;
import com.vaadin.polymer.elemental.EventListener;
import com.vaadin.polymer.paper.PaperIconButtonElement;

import il.co.menora.soaarchive.client.events.ClientEventBus;
import il.co.menora.soaarchive.client.events.ReloadDataEvent;
import il.co.menora.soaarchive.client.places.ClientPlaceController;
import il.co.menora.soaarchive.client.places.HomePlace;
import il.co.menora.soaarchive.client.places.OutgoingPlace;

public class AppPanel extends Composite {
	private static AppPanelUiBinder uiBinder = GWT.create(AppPanelUiBinder.class);

	interface AppPanelUiBinder extends UiBinder<Widget, AppPanel> {
		// Marker interface
	}

	@UiField
	PaperIconButtonElement reloadButton;
	@UiField
	Element logoutAnchor;
	@UiField
	HTMLPanel appPanel;
	DataTablePage dataTablePage;
	
	
	public AppPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		dataTablePage = new DataTablePage();

		reloadButton.addEventListener("click", new EventListener<Event>() {
			@Override
			public void handleEvent(Event event) {
				ClientEventBus.get().fireEvent(new ReloadDataEvent());
			}
		});

		
		logoutAnchor.addEventListener("click", new EventListener<Event>() {

			@Override
			public void handleEvent(Event event) {
				Window.Location.replace("/archive/logout.jsp");
				
			}
		});
		
	}

	private void reload() {
		appPanel.clear();

		Place place = ClientPlaceController.get().getWhere();

		if (place instanceof HomePlace) {
			appPanel.add(new DataTablePage());
		} else if (place instanceof OutgoingPlace) {
			appPanel.add(new OutgoingPage());
		}
	}

	@Override
	protected void onLoad() {
		ClientEventBus.registerEventHandlerOnLoad(this, PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
			@Override
			public void onPlaceChange(PlaceChangeEvent event) {
				reload();
			}
		});
	}

	@Override
	protected void onUnload() {
		ClientEventBus.removeEventHandlersOnUnload(this);
	}

	public static native void alert(String msg) /*-{
		var a = "5";
		a = a + 1;
		alert(a + " " + msg);
	}-*/;
}
