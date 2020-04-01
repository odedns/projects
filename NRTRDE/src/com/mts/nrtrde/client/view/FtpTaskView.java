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
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.mts.nrtrde.client.FtpTaskDetails;
import com.mts.nrtrde.client.presenter.FtpTaskPresenter;

/**
 * @author C5132784
 *
 */
public class FtpTaskView extends LayoutContainer implements FtpTaskPresenter.Display {
	
	Dialog ftpDialog;
	
	public FtpTaskView()
	{
	
	}
	
	
	
	private Dialog createFtpDialog()
	{
		final Dialog ftpDialog = new Dialog();
		ftpDialog.setTitle("Add FTP Server Config");
		ftpDialog.setWidth(350);
		
		TextField taskNameField = new TextField();
		taskNameField.setFieldLabel("Task Name");
		
		
		
		TextField hostField = new TextField();
		hostField.setFieldLabel("Host IP");
		
	
		TextField portField = new TextField();
		portField.setFieldLabel("Host Port");
		
		
		
		TextField userField = new TextField();
		userField.setFieldLabel("Username");
		
		TextField passField = new TextField();
		passField.setFieldLabel("Password");
		
		
		TextField remoteField = new TextField();
		remoteField.setFieldLabel("Remote Path");
		
	
		TextField localField = new TextField();
		localField.setFieldLabel("Local Path");
		
		
		TextField backupField = new TextField();
		backupField.setFieldLabel("Backup Path");
		
		
		Radio radio = new Radio();  
	    radio.setBoxLabel("Download");  
	    radio.setValue(true);  
	  
	    Radio radio2 = new Radio();  
	    radio2.setBoxLabel("Upload");  
	  
	    RadioGroup radioGroup = new RadioGroup();  
	    radioGroup.setFieldLabel("FTP Type");  
	    radioGroup.add(radio);  
	    radioGroup.add(radio2);  
		
		TextField completeField = new TextField();
		completeField.setFieldLabel("Complete Handle");
		
		
		TextField filterField = new TextField();
		filterField.setFieldLabel("Filter Expression:");
		
		Button submitButton = new Button("Submit");
		Button closeButton  = new Button("Close");
	
		FormPanel formPanel = new FormPanel();
		formPanel.add(taskNameField);
		formPanel.add(hostField);
		formPanel.add(portField);
		formPanel.add(userField);
		formPanel.add(passField);
		formPanel.add(remoteField);
		formPanel.add(localField);
		formPanel.add(backupField);
		formPanel.add(radioGroup);
		formPanel.add(completeField);
		formPanel.add(filterField);
		formPanel.layout();
		formPanel.setHeading("Add FTP Task");
		ftpDialog.setButtons(Dialog.OKCANCEL);
		ftpDialog.setHideOnButtonClick(true);
		ftpDialog.add(formPanel);
		ftpDialog.setTitle("Add FTP Task");
		//ftpDialog.show();
		
		return(ftpDialog);
		
	}



	@Override
	protected void onRender(Element parent, int index) {
		// TODO Auto-generated method stub
		super.onRender(parent, index);
		
		ArrayList<FtpTaskDetails> ftpTastList = new ArrayList<FtpTaskDetails>();
		
	    PagingModelMemoryProxy proxy = new PagingModelMemoryProxy(ftpTastList);  
	    PagingLoader loader = new BasePagingLoader(proxy);  
	       loader.setRemoteSort(true);  
	       ListStore<FtpTaskDetails> ftpList = new ListStore<FtpTaskDetails>(loader);    
	     final PagingToolBar toolBar = new PagingToolBar(5);  
	     toolBar.bind(loader);  
	     loader.load(0, 5);  
		
		ArrayList<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
		  
		ColumnConfig column = new ColumnConfig();    
		column.setId("name");    
		column.setHeader("Task Name");
		column.setWidth(150);
 		configs.add(column);
 		 		
		column = new ColumnConfig();    
		column.setId("host");    
		column.setHeader("Host IP");
		column.setWidth(100);
    	configs.add(column);
    	
		column = new ColumnConfig();    
		column.setId("ftpType");    
		column.setHeader("FTP Type");
		column.setWidth(80);
    	configs.add(column);
    	
		column = new ColumnConfig();    
		column.setId("remotePath");    
		column.setHeader("Remote Path");
		column.setWidth(200);
    	configs.add(column);
    	
		column = new ColumnConfig();    
		column.setId("localPath");    
		column.setHeader("Local Path");
		column.setWidth(200);
    	configs.add(column);
		
		column = new ColumnConfig();    
		column.setId("lastRuntime");    
		column.setHeader("Last Runtime");    
		column.setWidth(200);
		configs.add(column);
		
		ColumnModel cm = new ColumnModel(configs);  
		Grid<FtpTaskDetails> grid = new Grid<FtpTaskDetails>(ftpList, cm);   
		grid.setStyleAttribute("borderTop", "none");   
		grid.setAutoExpandColumn("name");   
		grid.setBorders(true);   
		grid.setStripeRows(true);  
		
		 ContentPanel cp = new ContentPanel();  
		 cp.setFrame(true);  
		 cp.setHeading("FTP Task List");  
	
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
		    
		 ftpDialog = createFtpDialog();
		  
		 
			
			addButton.addListener(Events.OnClick, new Listener<BaseEvent>() {

				public void handleEvent(BaseEvent be) {
					// TODO Auto-generated method stub
				
					ftpDialog.show();
					
				}
			});
			editButton.addListener(Events.OnClick, new Listener<BaseEvent>() {

				public void handleEvent(BaseEvent be) {
					// TODO Auto-generated method stub
				
					ftpDialog.show();
					
				}
			});
		
	}
	

}
