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
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.mts.nrtrde.client.FileDeliveryDetails;
import com.mts.nrtrde.client.presenter.FileDeliveryReportPresenter;

/**
 * @author C5132784
 *
 */
public class FileDeliveryReportView extends LayoutContainer implements FileDeliveryReportPresenter.Display {
	
	
	
	public FileDeliveryReportView()
	{
	
		}

	@Override
	protected void onRender(Element parent, int index) {
		// TODO Auto-generated method stub
		super.onRender(parent, index);
		
		ArrayList<FileDeliveryDetails> fileDeliveryList = new ArrayList<FileDeliveryDetails>();
		FileDeliveryDetails fd = new FileDeliveryDetails();
		fd.setRecordNum(1);
		fd.setFDRfileName("File name");
		fd.setNRTRDEfileName("foo");
		fd.setVPMN("vPMN");
		
		fileDeliveryList.add(fd);
		
	    PagingModelMemoryProxy proxy = new PagingModelMemoryProxy(fileDeliveryList);  
	    PagingLoader loader = new BasePagingLoader(proxy);  
	    loader.setRemoteSort(true);  
	    ListStore<FileDeliveryDetails> fileDeliveryStore = new ListStore<FileDeliveryDetails>();
        fileDeliveryStore.add(fileDeliveryList);
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
	//	column.setWidth(200);
    	configs.add(column);
    	
		column = new ColumnConfig();    
		column.setId("startDate");    
		column.setHeader("Start Date");
	//	column.setWidth(200);
    	configs.add(column);
    	
		column = new ColumnConfig();    
		column.setId("endDate");    
		column.setHeader("End Date");
	//	column.setWidth(200);
    	configs.add(column);
    	
		column = new ColumnConfig();    
		column.setId("NRTRDEfileName");    
		column.setHeader("NRTRDE File Name");
		column.setWidth(200);
    	configs.add(column);
		
		column = new ColumnConfig();    
		column.setId("ReceivedDate");    
		column.setHeader("Recieved Date");    
		column.setWidth(200);
		configs.add(column);
		
		column = new ColumnConfig();    
		column.setId("recordNum");    
		column.setHeader("Record Number");    
		//column.setWidth(200);
		configs.add(column);
		
		ColumnModel cm = new ColumnModel(configs);  
		Grid<FileDeliveryDetails> grid = new Grid<FileDeliveryDetails>(fileDeliveryStore, cm);   
		grid.setStyleAttribute("borderTop", "none");   
		//grid.setAutoExpandColumn("name");   
		grid.setBorders(true);   
		grid.setStripeRows(true);  
		
		 ContentPanel cp = new ContentPanel();  
		 cp.setFrame(true);  
		 cp.setHeading("File Delivery Report");  
	  
		 cp.setButtonAlign(HorizontalAlignment.CENTER);  
		 cp.setLayout(new FitLayout());  
		 cp.setBottomComponent(toolBar);
		 
		 
		 
		
		Label l1 = new Label("NRTRDE File Name");
	    TextField nrtdreFileName = new TextField();
	    nrtdreFileName.setFieldLabel("NRTRDE File Name");
	    
	    Label l2 = new Label("Sequence Num");
	    TextField seqNum = new TextField();
	    seqNum.setFieldLabel("Sequence Num");
	    
	    Label l3 = new Label("VPMN");
	    TextField VPMN = new TextField();
	    VPMN.setFieldLabel("VPMN");
	    
	    Label l4 = new Label("FDR File Name");
	    TextField fdrFileName = new TextField();
	    fdrFileName.setFieldLabel("FDR File Name");
	    
	    Label l5 = new Label("Start Date");
	    DateField startDate = new DateField();
	    startDate.setFieldLabel("Start Date");
	    
	    Label l6 = new Label("End Date");
	    DateField endDate = new DateField();
	    endDate.setFieldLabel("End Date");
	    Button searchButton = new Button("Search");
	    Button resetButton = new Button("Reset");
	    HorizontalPanel hp = new HorizontalPanel();
	    hp.add(searchButton);
	    hp.add(resetButton);
	    
	    com.google.gwt.user.client.ui.Grid gridLayout = new  com.google.gwt.user.client.ui.Grid(4,4);
	    gridLayout.setCellPadding(10);
	    gridLayout.setCellSpacing(10);
	    
	    
	    gridLayout.setWidget(0,0, l1);
	    
	    gridLayout.setWidget(0,1, nrtdreFileName);
	    gridLayout.setWidget(0,2, l2);
	    gridLayout.setWidget(0,3, seqNum);
	    gridLayout.setWidget(1,0, l3);
	    gridLayout.setWidget(1,1, fdrFileName);
	    gridLayout.setWidget(1,2, l4);
	    gridLayout.setWidget(1,3, VPMN);
	    gridLayout.setWidget(2,0, l5);
	    gridLayout.setWidget(2,1, startDate);
	    gridLayout.setWidget(2,2, l6);
	    gridLayout.setWidget(2,3, endDate);
	    gridLayout.setWidget(3,3, hp);

	     HorizontalPanel hp1 = new HorizontalPanel();	  
	     hp1.add(gridLayout);
	     cp.setSize(600,600);
	     cp.setTopComponent(hp1);
		 cp.add(grid);
		 setLayout(new FitLayout());
		 add(cp);
		 layout();
		 
	}

	

}
