/**
 * 
 */
package com.mts.nrtrde.client.view;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.PagingModelMemoryProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

import com.mts.nrtrde.client.ErrorReportDetails;
import com.mts.nrtrde.client.FileDeliveryDetails;
import com.mts.nrtrde.client.presenter.ErrorReportPresenter;

/**
 * @author C5132784
 *
 */
public class ErrorReportView extends LayoutContainer implements ErrorReportPresenter.Display {
	
	public ErrorReportView()
	{
		}

	@Override
	protected void onRender(Element parent, int index) {
		// TODO Auto-generated method stub
		super.onRender(parent, index);
		
		
		ArrayList<ErrorReportDetails> errorFileList = new ArrayList<ErrorReportDetails>();
		/*
		EDeliveryDetails fd = new FileDeliveryDetails();
		fd.setRecordNum(1);
		fd.setFDRfileName("File name");
		fd.setNRTRDEfileName("foo");
		fd.setVPMN("vPMN");
		
		fileDeliveryList.add(fd);
		*/
		 PagingModelMemoryProxy proxy = new PagingModelMemoryProxy(errorFileList);  
		    PagingLoader loader = new BasePagingLoader(proxy);  
		       loader.setRemoteSort(true);  
		       ListStore<ErrorReportDetails> errorStore = new ListStore<ErrorReportDetails>(loader);    
		     final PagingToolBar toolBar = new PagingToolBar(5);  
		     toolBar.bind(loader);  
		     loader.load(0, 5);  
			
			ArrayList<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
		
			    
			    
			ColumnConfig column = new ColumnConfig();    
			column.setId("FDRfileName");    
			column.setHeader("Err File Name");
			column.setWidth(200);
	 		configs.add(column);
	 		 		
			column = new ColumnConfig();    
			column.setId("VPMN");    
			column.setHeader("VPMN");
			
			
	    	configs.add(column);
	    	column = new ColumnConfig();    
			column.setId("errorCode");    
			column.setHeader("Error Code");
			configs.add(column);
			
			
			column = new ColumnConfig();    
			column.setId("recordType");    
			column.setHeader("Record Type");
			configs.add(column);
	    	
			column = new ColumnConfig();    
			column.setId("endDate");    
			column.setHeader("ER Event Date");
			column.setWidth(200);
	    	configs.add(column);
	    	
			column = new ColumnConfig();    
			column.setId("NRTRDEfileName");    
			column.setHeader("NRTRDE File Name");
			column.setWidth(200);
	    	configs.add(column);
			
		
			
			column = new ColumnConfig();    
			column.setId("recordNum");    
			column.setHeader("Record Number");    
			column.setWidth(200);
			configs.add(column);
			
			
			ColumnModel cm = new ColumnModel(configs);  
			Grid<ErrorReportDetails> grid = new Grid<ErrorReportDetails>(errorStore, cm);   
			grid.setStyleAttribute("borderTop", "none");   
		//	grid.setAutoExpandColumn("name");   
			grid.setBorders(true);   
			grid.setStripeRows(true);  
			
			 Label nrtdreFileNameLabel = new Label("NRTRDE File Name:");
		    //DatePicker fromPicker = new DatePicker();
		    TextField nrtdreFileName = new TextField();
		    
		    Label seqNumLabel = new Label("Sequence Num:");
		    TextField seqNum = new TextField();
		    
		    Label VPMNLabel = new Label("VPMN:");
		    TextField VPMN = new TextField();
		    
		    Label errFileNameLabel = new Label("Err File Name:");
		    TextField errFileName = new TextField();

		    Label recordTypeLabel = new Label("Record Type:");
		    SimpleComboBox<String> recordType = new SimpleComboBox<String>();
		    recordType.setWidth("200px");
		    Label errorCodeLabel = new Label("Error Code:");
		    TextField errorCode = new TextField();
		    
		    Button searchButton = new Button("Search");
		    Button resetButton = new Button("Reset");
		    HorizontalPanel hp = new HorizontalPanel();
		    hp.add(searchButton);
		    hp.add(resetButton);
		   
		    com.google.gwt.user.client.ui.Grid gridLayout = new  com.google.gwt.user.client.ui.Grid(4, 4);
		    gridLayout.setCellSpacing(10);
		    gridLayout.setWidget(0, 0, nrtdreFileNameLabel);
		    gridLayout.setWidget(0, 1, nrtdreFileName);
		    gridLayout.setWidget(0, 2, errFileNameLabel);
		    gridLayout.setWidget(0, 3, errFileName);
		    gridLayout.setWidget(1,0,seqNumLabel);
		    gridLayout.setWidget(1,1,seqNum);
		    gridLayout.setWidget(1,2,recordTypeLabel);
		    gridLayout.setWidget(1,3,recordType);
		    gridLayout.setWidget(2,0,VPMNLabel);
		    gridLayout.setWidget(2,1,VPMN);
		    gridLayout.setWidget(2,2,errorCodeLabel);
		    gridLayout.setWidget(2,3,errorCode);
		    gridLayout.setWidget(3,3,hp);
		
		    ContentPanel cp = new ContentPanel();  
			 cp.setFrame(true);  
			 cp.setHeading("FTP Task List");  
		
			 cp.setButtonAlign(HorizontalAlignment.CENTER);  
			 cp.setLayout(new FitLayout());  
			 cp.setBottomComponent(toolBar);
			 cp.setSize(600,600);
		    cp.setFrame(true);
		    cp.setHeading("Error Report View");
		    HorizontalPanel hp1 = new HorizontalPanel();
		 
		    hp1.add(gridLayout);
		    cp.setTopComponent(hp1);
			cp.add(grid);
		    
		     add(cp);
		    setLayout(new FitLayout());
		    layout();
	}

}
