/**
 * 
 */
package com.mts.nrtrde.client.view;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.google.gwt.user.client.Element;


import com.mts.nrtrde.client.presenter.ParametersPresenter;

/**
 * @author C5132784
 *
 */
public class ParametersView extends LayoutContainer implements ParametersPresenter.Display {
	
	
	
	
	public ParametersView()
	{
			
	
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void onRender(Element parent, int index) {
		// TODO Auto-generated method stub
		super.onRender(parent, index);
	
	
		TextField hostField = new TextField();
		
		hostField.setFieldLabel("Provision Server host ip");
	
		
		TextField portField = new TextField();
		portField.setFieldLabel("Provision Server host port");
	
		TextField alarmField = new TextField();
		alarmField.setFieldLabel("Alaram threshhold");
		
	
		TextField alarmCountField = new TextField();
		
		alarmCountField.setFieldLabel("Alarm threshold Summary count in the past");
		
		TextField billingField = new TextField();
		
		billingField.setFieldLabel("Billing currency");
	
		TextField notificationField = new TextField();
		
		notificationField.setFieldLabel("Notification operator phone number");
		Button submitButton = new Button("Submit");
		FormPanel generateForm = new FormPanel();
	
		generateForm.add(hostField);
		generateForm.add(portField);
		generateForm.add(portField);
		generateForm.add(alarmField);
		generateForm.add(alarmCountField);
		generateForm.add( billingField);
		generateForm.addButton(submitButton);
		generateForm.setLabelWidth(300);
		generateForm.setFieldWidth(300);
	//	generateForm.setFrame(true);
		generateForm.layout();
		
		ContentPanel panel = new ContentPanel();
		panel.setHeading("Parameters");
		panel.add(generateForm);
	
		add(panel);
		layout();
		
		
		
		
	}
	
	
	
}
