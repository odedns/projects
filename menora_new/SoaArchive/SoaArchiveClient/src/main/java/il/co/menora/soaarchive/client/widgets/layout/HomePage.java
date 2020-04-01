package il.co.menora.soaarchive.client.widgets.layout;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.polymer.elemental.Event;
import com.vaadin.polymer.elemental.EventListener;
import com.vaadin.polymer.paper.PaperButtonElement;
import com.vaadin.polymer.paper.widget.PaperButton;

import il.co.menora.soaarchive.client.app.ApplicationStatus;
import il.co.menora.soaarchive.client.events.ClientEventBus;
import il.co.menora.soaarchive.client.events.ReloadDataEvent;
import il.co.menora.soaarchive.client.events.ReloadDataEvent.ReloadDataEventHandler;

public class HomePage extends Composite {
	private static HomePageUiBinder uiBinder = GWT.create(HomePageUiBinder.class);

	interface HomePageUiBinder extends UiBinder<Widget, HomePage> {
		// Marker interface
	}

	@UiField
	SpanElement timeSpan;
	@UiField
	PaperButtonElement aboutButton;
	
	@UiField
	HTMLPanel mainPanel;
	
	public HomePage() {
		initWidget(uiBinder.createAndBindUi(this));

		aboutButton.addEventListener("click", new EventListener<Event>() {
			@Override
			public void handleEvent(Event event) {
				Window.alert("Version " + ApplicationStatus.getApplicationInfoDto().getVersion());
			}
		});

		reload();
	}

	private void reload() {
		GWT.log("in reload");
		timeSpan.setInnerText(new Date().toString());
		mainPanel.add(new PaperButton("aa"));
		
	
	
	}

	@Override
	protected void onLoad() {
		ClientEventBus.registerEventHandlerOnLoad(this, ReloadDataEvent.TYPE, new ReloadDataEventHandler() {
			@Override
			public void onEvent(ReloadDataEvent event) {
				reload();
			}
		});
	}

	@Override
	protected void onUnload() {
		ClientEventBus.removeEventHandlersOnUnload(this);
	}

}
