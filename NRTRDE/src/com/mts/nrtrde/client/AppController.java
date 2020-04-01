/**
 * 
 */
package com.mts.nrtrde.client;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.mts.nrtrde.client.event.NavigationEvent;
import com.mts.nrtrde.client.event.NavigationEventHandler;
import com.mts.nrtrde.client.presenter.ErrorReportPresenter;
import com.mts.nrtrde.client.presenter.FileDeliveryReportPresenter;
import com.mts.nrtrde.client.presenter.FtpTaskPresenter;
import com.mts.nrtrde.client.presenter.GenerationPresenter;
import com.mts.nrtrde.client.presenter.ParametersPresenter;
import com.mts.nrtrde.client.presenter.Presenter;
import com.mts.nrtrde.client.presenter.RegenerateTapsPresenter;
import com.mts.nrtrde.client.presenter.ScheduleTaskPresenter;
import com.mts.nrtrde.client.presenter.WelcomePresenter;
import com.mts.nrtrde.client.view.ErrorReportView;
import com.mts.nrtrde.client.view.FileDeliveryReportView;
import com.mts.nrtrde.client.view.FtpTaskView;
import com.mts.nrtrde.client.view.GenerationView;
import com.mts.nrtrde.client.view.ParametersView;
import com.mts.nrtrde.client.view.RegenerateTapsView;
import com.mts.nrtrde.client.view.ScheduleTaskView;
import com.mts.nrtrde.client.view.WelcomeView;


/**
 * @author Oded Nissan
 *
 */
public class AppController implements Presenter, ValueChangeHandler<String> {
	
	EventBus eventBus;
	HasWidgets  container;
	HashMap<String,Presenter> navigationMap;
	
	public AppController(HasWidgets sPanel,EventBus eventBus)
	{
		this.eventBus = eventBus;
		this.container = sPanel;
		bind();
		
	}
	
	
	private void initPresenters()
	{
		navigationMap = new HashMap<String,Presenter>();
		//navigationMap.put(NRTRDE.constants.generate(), value)
		WelcomePresenter welcomePresenter = new WelcomePresenter(this.eventBus, new WelcomeView());
		navigationMap.put("NRTRDE", welcomePresenter);
		GenerationPresenter generatePresenter = new GenerationPresenter(this.eventBus,new GenerationView());
		navigationMap.put(NRTRDE.constants.generation(), generatePresenter);
		FileDeliveryReportPresenter filePresenter = new FileDeliveryReportPresenter(eventBus, new FileDeliveryReportView());
		navigationMap.put(NRTRDE.constants.fileDeliveryReport(), filePresenter);
		ErrorReportPresenter errorPresenter = new ErrorReportPresenter(eventBus, new ErrorReportView());
		navigationMap.put(NRTRDE.constants.errorReport(), errorPresenter);
		FtpTaskPresenter ftpPresenter = new FtpTaskPresenter(eventBus, new FtpTaskView());
		navigationMap.put(NRTRDE.constants.ftpTask(), ftpPresenter);
		ParametersPresenter paramPresenter = new ParametersPresenter(eventBus, new ParametersView());
		navigationMap.put(NRTRDE.constants.params(), paramPresenter);
		ScheduleTaskPresenter schedulePresenter = new ScheduleTaskPresenter(eventBus, new ScheduleTaskView());
		navigationMap.put(NRTRDE.constants.scheduleTask(), schedulePresenter);
		RegenerateTapsPresenter regeneratePresenter = new RegenerateTapsPresenter(eventBus, new RegenerateTapsView());
		//navigationMap.put(NRTRDE.constants.regeneratetaps(), regeneratePresenter);
		
		
	}
	
	
	
	public void bind()
	{
		initPresenters();
		 History.addValueChangeHandler(this);
		 eventBus.addHandler(NavigationEvent.TYPE,
			        new NavigationEventHandler() {
			          public void doNavigation(NavigationEvent event) {
			            GWT.log("navigation event: " + event.getId());
			            Presenter presenter = navigationMap.get(event.getId());
			            if(null == presenter) {
			            	GWT.log("Cannot find Presenter:" + event.getId());
			            	//Window.alert("cannot find presenter");
			            	if(event.getId().equals(NRTRDE.constants.regeneratetaps())) {
			            		FormsExample e = new FormsExample();
			            		 container.clear();
			            		 container.add(e);
			            		
			            	}
			            
			            } else {
			            	
			            	presenter.go(container);
			            }
			          }
			        });  

		
	}

	

	public void onValueChange(ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
		
	}

	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
		
	}

}
