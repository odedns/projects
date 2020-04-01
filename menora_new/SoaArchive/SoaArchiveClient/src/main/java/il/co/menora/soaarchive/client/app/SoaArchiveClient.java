package il.co.menora.soaarchive.client.app;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.Defaults;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.polymer.Polymer;
import com.vaadin.polymer.elemental.Function;
import com.vaadin.polymer.iron.IronIconElement;
import com.vaadin.polymer.iron.IronIconsElement;
import com.vaadin.polymer.neon.FadeInAnimationElement;
import com.vaadin.polymer.paper.PaperButtonElement;
import com.vaadin.polymer.paper.PaperDialogElement;
import com.vaadin.polymer.paper.PaperDialogScrollableElement;
import com.vaadin.polymer.paper.PaperDrawerPanelElement;
import com.vaadin.polymer.paper.PaperFabElement;
import com.vaadin.polymer.paper.PaperHeaderPanelElement;
import com.vaadin.polymer.paper.PaperIconButtonElement;
import com.vaadin.polymer.paper.PaperIconItemElement;
import com.vaadin.polymer.paper.PaperInputElement;
import com.vaadin.polymer.paper.PaperMenuElement;
import com.vaadin.polymer.paper.PaperRippleElement;
import com.vaadin.polymer.paper.PaperSubmenuElement;
import com.vaadin.polymer.paper.PaperTextareaElement;
import com.vaadin.polymer.paper.PaperToolbarElement;
import com.vaadin.polymer.paper.widget.PaperDialogScrollable;

import il.co.menora.soaarchive.client.bundle.ClientResources;
import il.co.menora.soaarchive.client.events.ClientEventBus;
import il.co.menora.soaarchive.client.exceptions.ClientExceptionsUtils;
import il.co.menora.soaarchive.client.places.ClientPlaceController;
import il.co.menora.soaarchive.client.places.ClientPlaceHistoryHandler;
import il.co.menora.soaarchive.client.places.HomePlace;
import il.co.menora.soaarchive.client.rpc.AsyncCallbackDecorator;
import il.co.menora.soaarchive.client.rpc.ClientService;
import il.co.menora.soaarchive.client.widgets.layout.AppPanel;
import il.co.menora.soaarchive.shared.ApplicationInfoDto;

public class SoaArchiveClient implements EntryPoint {
	private static Logger logger = Logger.getLogger(SoaArchiveClient.class.getSimpleName());

	@Override
	public void onModuleLoad() {
		// Setting UncaughtExceptionHandler as soon as possible to catch all exceptions
		GWT.log("SoaArchive Client onMuduleLoad......");
		 Defaults.setServiceRoot("/archive");
		 Defaults.setDateFormat(null);
		 
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				ClientExceptionsUtils.onUncaughtException(e);
			}
		});

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				GWT.log("polymer importer...");
				importPolymerAndinit();
			}
		});
	}

	protected void importPolymerAndinit() {
		// Importing polymer elements/icons before running application
		Polymer.importHref(
				Arrays.asList( //
						FadeInAnimationElement.SRC, //
						IronIconElement.SRC, //
						IronIconsElement.SRC, //
						PaperButtonElement.SRC, //
						PaperDialogElement.SRC, //
						PaperDrawerPanelElement.SRC, //
						PaperFabElement.SRC, //
						PaperHeaderPanelElement.SRC, //
						PaperIconButtonElement.SRC, //
						PaperIconItemElement.SRC, //
						PaperInputElement.SRC, //
						PaperRippleElement.SRC, //
						PaperTextareaElement.SRC, //
						PaperToolbarElement.SRC,
						PaperMenuElement.SRC,
						PaperSubmenuElement.SRC), //
						
				new Function<Object, Object>() {
					@Override
					public Object call(Object arg) {
						init();
						return null;
					}
				});
	}

	private void init() {
		logger.log(Level.INFO, "SoaArchiveClient: Executing getApplicationInfo");

		ClientService.get().getApplicationInfo(new AsyncCallbackDecorator<ApplicationInfoDto>() {
			@Override
			public void onSuccess(ApplicationInfoDto applicationInfoDto) {
				logger.log(Level.INFO, "SoaArchiveClient: getApplicationInfo executed successfully, userName=" + applicationInfoDto.getUserName());

				ApplicationStatus.setApplicationInfoDto(applicationInfoDto);

				RootPanel.get().insert(new AppPanel(), 0);

				// Using HomePlace as a default to know when the application is in an invalid place and navigate to the real home page
				ClientPlaceHistoryHandler.INSTANCE.register(ClientPlaceController.get(), ClientEventBus.get(), new HomePlace());
				ClientPlaceHistoryHandler.INSTANCE.handleCurrentHistory();
			}
		});

		ClientResources.INSTANCE.style().ensureInjected();
	}
}
