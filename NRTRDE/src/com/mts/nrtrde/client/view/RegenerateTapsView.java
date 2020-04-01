/**
 * 
 */
package com.mts.nrtrde.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mts.nrtrde.client.FileDeliveryDetails;
import com.mts.nrtrde.client.RegenerateTapsDetails;
import com.mts.nrtrde.client.presenter.RegenerateTapsPresenter;

/**
 * @author C5132784
 *
 */
public class RegenerateTapsView extends Composite implements RegenerateTapsPresenter.Display {
	
	
	
	public RegenerateTapsView()
	{
	
		CellTable<RegenerateTapsDetails> table = new CellTable<RegenerateTapsDetails>();
		  // Create name column.
	    TextColumn<RegenerateTapsDetails> keyNumColumn = new TextColumn<RegenerateTapsDetails>() {
	     


		@Override
		public String getValue(RegenerateTapsDetails object) {
			// TODO Auto-generated method stub
			return Integer.valueOf(object.getKeyNum()).toString();
		}
	    };

	    TextColumn<RegenerateTapsDetails> eventTypeColumn = new TextColumn<RegenerateTapsDetails>() {
	    

		

		@Override
		public String getValue(RegenerateTapsDetails object) {
			// TODO Auto-generated method stub
			return object.getEventType();
		}
	    };
	    TextColumn<RegenerateTapsDetails> eventDateColumn = new TextColumn<RegenerateTapsDetails>() {
	      
			
			

			@Override
			public String getValue(RegenerateTapsDetails object) {
				// TODO Auto-generated method stub
				return object.getEventDate().toString();
			}
	      };
	      
	     
		   TextColumn<RegenerateTapsDetails> billingDateColumn = new TextColumn<RegenerateTapsDetails>() {
			      


				
					@Override
					public String getValue(RegenerateTapsDetails object) {
						// TODO Auto-generated method stub
						return object.getBillingDate().toString();
					}
			};
			 TextColumn<RegenerateTapsDetails> ratingStatusColumn = new TextColumn<RegenerateTapsDetails>() {
			       

					@Override
					public String getValue(RegenerateTapsDetails object) {
						// TODO Auto-generated method stub
						return object.getRatingStatus();
					}
			 };
	    // Add the columns.
	    
			 TextColumn<RegenerateTapsDetails> priceColumn = new TextColumn<RegenerateTapsDetails>() {
			      
			
					@Override
					public String getValue(RegenerateTapsDetails object) {
						// TODO Auto-generated method stub
						return Double.valueOf(object.getPrice()).toString();
					}
			 };
			 TextColumn<RegenerateTapsDetails> callStartDate = new TextColumn<RegenerateTapsDetails>() {
				 
			   
					
				
					@Override
					public String getValue(RegenerateTapsDetails object) {
						// TODO Auto-generated method stub
						return object.getCallStartTime().toString();
					}
			 };
			 TextColumn<RegenerateTapsDetails> callendDate = new TextColumn<RegenerateTapsDetails>() {
				 
				   
					
					
					@Override
					public String getValue(RegenerateTapsDetails object) {
						// TODO Auto-generated method stub
						return object.getCallEndTime().toString();
					}
			 };
	    table.addColumn(keyNumColumn, "Key Num");
	    table.addColumn(eventTypeColumn, "Event Type");
	    table.addColumn(eventDateColumn, "Event Date");
	    table.addColumn(billingDateColumn, "Billing  Date");
	    table.addColumn(ratingStatusColumn, "Rating Status");
	    
	    table.addColumn(priceColumn, "Price ");
	    table.addColumn(callStartDate, "Call start Time");
	    table.addColumn(callendDate, "Call end Time");
	    //ListDataProvider<FileInfo> dataProvider = new ListDataProvider<FileInfo>();
	    //dataProvider.addDataDisplay(table);
	    //dataProvider.setList(getFileInfoList()); 
	//    table.setColumnWidth(nameColumn, 30, Unit.PCT);
	 //   table.setColumnWidth(typeColumn, 20, Unit.PCT);
	    //table.setColumnWidth(dateColumn, 50, Unit.PCT);
	    table.setWidth("100%", true);
	    
	    SimplePager pager;
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, true, 0,true);
	    pager.setDisplay(table);
	    pager.setPageSize(20);
	    
	    VerticalPanel panel = new VerticalPanel();
	    //panel.setSize("100%", "100%");
	    panel.add(table);
	    panel.add(pager);
	    VerticalPanel vp = new VerticalPanel();
	    vp.setSpacing(5);
	    //vp.setSize("100%", "100%");
	    HorizontalPanel hp1 = new HorizontalPanel();
	    Label operatorLabel = new Label("Please select or type an Operator name:");
	    TextBox operator = new TextBox();
	    Button searchButton = new Button("Search");
	    hp1.add(operatorLabel);
	    hp1.add(operator);
	    hp1.add(searchButton);
	    hp1.setSpacing(10);
	    RadioButton rb1 = new RadioButton("type","Launch Test");
	    RadioButton rb2 = new RadioButton("type","Commercial Launch");
	    Button generateButton = new Button("Generate");
	    HorizontalPanel hp2 = new HorizontalPanel();
	    hp2.add(rb1);
	    hp2.add(rb2);
	    hp2.add(generateButton);
	    hp2.setSpacing(10);
	    
	    
	    VerticalPanel vp1 = new VerticalPanel();
	    vp1.add(hp1);
	    vp1.add(hp2);
	    
	    vp.add(vp1);
	    vp.add(panel);
	    initWidget(vp);	
		
	}

}
