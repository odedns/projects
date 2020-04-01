/**
 * 
 */
package com.mts.nrtrde.client.view;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.PagingModelMemoryProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;

import com.mts.nrtrde.client.ScheduleTaskDetails;
import com.mts.nrtrde.client.presenter.ScheduleTaskPresenter;

/**
 * @author Oded Nissan
 *
 */
public class ScheduleTaskView extends LayoutContainer implements ScheduleTaskPresenter.Display {
	
	Dialog scheduleTaskDialog;
	public ScheduleTaskView()
	{
	}

	@Override
	protected void onRender(Element parent, int index) {
		// TODO Auto-generated method stub
		super.onRender(parent, index);
		
		ArrayList<ScheduleTaskDetails> scheduleTastList = new ArrayList<ScheduleTaskDetails>();
		
	    PagingModelMemoryProxy proxy = new PagingModelMemoryProxy(scheduleTastList);  
	    PagingLoader loader = new BasePagingLoader(proxy);  
	       loader.setRemoteSort(true);  
	       ListStore<ScheduleTaskDetails> scheduleList = new ListStore<ScheduleTaskDetails>(loader);    
	     final PagingToolBar toolBar = new PagingToolBar(5);  
	     toolBar.bind(loader);  
	     loader.load(0, 5);  
		
		ArrayList<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
		  
		ColumnConfig column = new ColumnConfig();    
		column.setId("taskName");    
		column.setHeader("Task Name");
		column.setWidth(200);
 		configs.add(column);
 		 		
		column = new ColumnConfig();    
		column.setId("cronExpression");    
		column.setHeader("Cron Expression");
		column.setWidth(200);
    	configs.add(column);
    	
		column = new ColumnConfig();    
		column.setId("moduleName");    
		column.setHeader("Module Name");
		column.setWidth(200);
    	configs.add(column);
    	
		column = new ColumnConfig();    
		column.setId("taskType");    
		column.setHeader("Task Type");
		column.setWidth(200);
    	configs.add(column);
    	
		column = new ColumnConfig();    
		column.setId("status");    
		column.setHeader("Status");
		column.setWidth(200);
    	configs.add(column);
		
		
		ColumnModel cm = new ColumnModel(configs);  
		Grid<ScheduleTaskDetails> grid = new Grid<ScheduleTaskDetails>(scheduleList, cm);   
		grid.setStyleAttribute("borderTop", "none");   
		//grid.setAutoExpandColumn("name");   
		grid.setBorders(true);   
		grid.setStripeRows(true);  
		
		 ContentPanel cp = new ContentPanel();  
		 cp.setFrame(true);  
		 cp.setHeading("Schedule Task List");  
	//	 cp.setIcon(Resources.ICONS.table());  
		 cp.setButtonAlign(HorizontalAlignment.CENTER);  
		 cp.setLayout(new FitLayout());  
		 cp.setBottomComponent(toolBar);
		 
		 
		
		 Button addButton = new Button("Add");
		 Button editButton = new Button("Edit");
		 Button deleteButton = new Button("Delete");
		 Button generateButton = new Button("Generate");
		 HorizontalPanel buttonPanel = new HorizontalPanel();
		 buttonPanel.add(addButton);
		 buttonPanel.add(editButton);
		 buttonPanel.add(deleteButton);
		 buttonPanel.add(generateButton);
		 buttonPanel.setSpacing(2);
		  
		 
		 cp.setSize(600,600);
		 cp.setTopComponent(buttonPanel);
		 cp.add(grid);  
		 add(cp);
		 setLayout(new FitLayout());
		 layout();
		    
		 scheduleTaskDialog = createSchedulTaskpDialog();
		  
		 
			
			addButton.addListener(Events.OnClick, new Listener<BaseEvent>() {

				public void handleEvent(BaseEvent be) {
					// TODO Auto-generated method stub
				
					scheduleTaskDialog.show();
					
				}
			});
			editButton.addListener(Events.OnClick, new Listener<BaseEvent>() {

				public void handleEvent(BaseEvent be) {
					// TODO Auto-generated method stub
				
					scheduleTaskDialog.show();
					
				}
			});
		
	}
	
	private Dialog createSchedulTaskpDialog()
	{
		final Dialog scheduleTaskDialog = new Dialog();
		scheduleTaskDialog.setWidth(350);
		
		TextField taskNameField = new TextField();
		taskNameField.setFieldLabel("Task Name");
		SimpleComboBox<String> modelList = new SimpleComboBox<String>();
	    modelList.setFieldLabel("Model");  
	    SimpleComboBox<String> taskList = new SimpleComboBox<String>();
	    taskList.setFieldLabel("Task");  
	    taskList.add("one");
	    taskList.add("two");
	    taskList.setEditable(false);
	    
		TextField cronField = new TextField();
		cronField.setFieldLabel("Cron Expression:");
		
		Radio radio = new Radio();  
	    radio.setBoxLabel("Enable");  
	    radio.setValue(true);  
	  
	    Radio radio2 = new Radio();  
	    radio2.setBoxLabel("Disable");  
	  
	    RadioGroup radioGroup = new RadioGroup();  
	    radioGroup.setFieldLabel("Status");  
	    radioGroup.add(radio);  
	    radioGroup.add(radio2);  
	
		
		
	
		TextArea remarkField = new TextArea();
		remarkField.setFieldLabel("Remark");
		FormPanel formPanel = new FormPanel();
		formPanel.add(taskNameField);
		formPanel.add(modelList);
		formPanel.add(taskList);
		formPanel.add(cronField);
		formPanel.add(radioGroup);
		formPanel.add(remarkField);
		formPanel.setHeading("Add Schedule Task ");
		formPanel.layout();
		
		scheduleTaskDialog.setButtons(Dialog.OKCANCEL);
		scheduleTaskDialog.setHideOnButtonClick(true);
		scheduleTaskDialog.add(formPanel);
		
		
		
		return(scheduleTaskDialog);
		
	}

	
}
