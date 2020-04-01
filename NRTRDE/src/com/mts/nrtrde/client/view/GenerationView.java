/**
 * 
 */
package com.mts.nrtrde.client.view;

import java.util.ArrayList;
import java.util.Date;


import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.PagingModelMemoryProxy;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.mts.nrtrde.client.FileInfo;
import com.mts.nrtrde.client.presenter.GenerationPresenter;

/**
 * @author Oded Nissan
 *
 */
public class GenerationView extends LayoutContainer  implements GenerationPresenter.Display {
	VerticalPanel panel;
	
	
	public GenerationView()
	{
	
	}
	
	
	private ArrayList<FileInfo> getFileInfoList()
	{
		ArrayList<FileInfo> list = new ArrayList<FileInfo>();
		for(int i=0; i < 200; ++i){
			FileInfo fi = new FileInfo("File" + i , "type-1", new Date());
			list.add(fi);
			
			
		}
		return(list);
		
	}


	@Override
	protected void onRender(Element parent, int index) {
		// TODO Auto-generated method stub
		super.onRender(parent, index);
		ArrayList<FileInfo> fileInfoList = getFileInfoList();
		
	    PagingModelMemoryProxy proxy = new PagingModelMemoryProxy(fileInfoList);
	    PagingLoader<PagingLoadResult<ModelData>> loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);  
	    loader.setRemoteSort(true);  
	 //   final PagingLoader loader = new BasePagingLoader(proxy);  
	       loader.setRemoteSort(true);  
	   ListStore<FileInfo> fileList = new ListStore<FileInfo>(loader);
	
	    final PagingToolBar toolBar = new PagingToolBar(15);  
	     toolBar.bind(loader);  
	     loader.load(0, 15);
	   
	     
		
		ArrayList<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
		  
		ColumnConfig column = new ColumnConfig();    
		column.setId("fileName");    
		column.setHeader("FileName");
		column.setWidth(400);
 		configs.add(column);
 		 		
		column = new ColumnConfig();    
		column.setId("type");    
		column.setHeader("File type");
		column.setWidth(200);
    	configs.add(column);
    	
		column = new ColumnConfig();    
		column.setId("dateModified");    
		column.setHeader("Date Modified");
		column.setWidth(200);
    	configs.add(column);
    	
		
		  
		  
				
		 Label fromLabel = new Label("From Date:");
		 //TextBox fromDate = new TextBox();
		 DateField fromDate = new DateField();
		 fromDate.setFieldLabel("From Date");
		 fromDate.setWidth(200);
		 DateField toDate = new DateField();
		 Label toLabel = new Label("To Date:");
		  toDate.setFieldLabel("To Date");
		  
		 Label operLabel= new Label("Operator"); 
		 SimpleComboBox<String> operList = new SimpleComboBox<String>();
		 operList.setFieldLabel("Operator:");  
		 operList.add("one");
		 operList.add("two");
		 operList.setEditable(false);
		 
		 Label fileTypeLabel= new Label("File Type:");
		 SimpleComboBox<String> fileTypeList = new SimpleComboBox<String>();
		 fileTypeList.setFieldLabel("File Type");  
		 fileTypeList.add("one");
		 fileTypeList.add("two");
		 fileTypeList.setEditable(false);
		
		 Button generateButton = new Button("Generate");
		 HorizontalPanel buttonPanel = new HorizontalPanel();
		 
		 buttonPanel.setSpacing(5);
		 
		 
		 buttonPanel.add(fromLabel);
		 buttonPanel.add(fromDate);
		 buttonPanel.add(toLabel);
		 buttonPanel.add(toDate);
		 buttonPanel.add(operLabel);
		 buttonPanel.add(operList);
		 buttonPanel.add(fileTypeLabel);
		 buttonPanel.add(fileTypeList);
		 buttonPanel.add(generateButton);
		
		
		 ColumnModel cm = new ColumnModel(configs);
		 Grid<FileInfo> grid = new Grid<FileInfo>(fileList, cm);   
		 grid.setStyleAttribute("borderTop", "none");   
		 grid.setAutoExpandColumn("fileName");   
		 grid.setBorders(true);   
		 grid.setStripeRows(true);
	
		
		 ContentPanel cp = new ContentPanel();  
		 cp.setFrame(true);  
		 cp.setHeading("Generate");
		 cp.setSize(800,600);
		
	//	 cp.setIcon(Resources.ICONS.table());  
		 cp.setButtonAlign(HorizontalAlignment.CENTER);  
		 cp.setLayout(new FitLayout());
		 cp.setTopComponent(buttonPanel);
		 cp.setBottomComponent(toolBar);
		
		// cp.add(buttonPanel);
		 cp.add(grid);
		
		
		 add(cp);
		 setLayout(new FitLayout());
		 layout();
		    
		
		  
		 
			
			
	}

}
