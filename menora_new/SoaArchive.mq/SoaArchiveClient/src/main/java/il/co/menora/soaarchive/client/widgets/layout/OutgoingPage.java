package il.co.menora.soaarchive.client.widgets.layout;


import java.util.Date;

import javax.ws.rs.Produces;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.TextCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.vaadin.polymer.paper.widget.PaperButton;
import com.vaadin.polymer.paper.widget.PaperDialog;
import com.vaadin.polymer.paper.widget.PaperDialogScrollable;

import il.co.menora.soaarchive.client.app.SoaArchiveClient;
import il.co.menora.soaarchive.client.events.ClientEventBus;
import il.co.menora.soaarchive.client.events.ReloadDataEvent;
import il.co.menora.soaarchive.client.events.ReloadDataEvent.ReloadDataEventHandler;
import il.co.menora.soaarchive.client.places.ClientPlaceController;
import il.co.menora.soaarchive.client.places.HomePlace;
import il.co.menora.soaarchive.client.places.OutgoingPlace;
import il.co.menora.soaarchive.client.places.WorkQueuePlace;
import il.co.menora.soaarchive.client.rest.SoaArchiveClientService;
import il.co.menora.soaarchive.shared.IncomingResponse;

public class OutgoingPage extends Composite {
	 
	interface OutgoingUiBinder extends UiBinder<HTMLPanel, OutgoingPage> {
    }
 
	private boolean attached = false;
	private static OutgoingUiBinder myUiBinder = GWT.create(OutgoingUiBinder.class);
	@UiField
	PaperButton outgoingButton;
	@UiField
	PaperDialog progDlg;
	@UiField
    Label rowsLabel;
	 
	 public OutgoingPage()
	 {
	        initWidget(myUiBinder.createAndBindUi(this));
			

	 }

	 private void reload() {
		GWT.log("in reload");
		destroyTable();
		initTable();
	}
	 
	 public native void destroyTable() /*-{
	 	
	 	$wnd.table.destroy();
	 	
	 }-*/;
	 
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
	 
	 
	 @UiHandler("outgoingButton")
	 void handleOutgoingButton(ClickEvent e){
		 GWT.log("incoming button pressed");
		 ClientPlaceController.get().goTo(new HomePlace());
	 }

	 public void initTable()
	 {
		 callRest();
	 }
	 
	 public void callRest()
	 {
		 
		 progDlg.open();
		 Resource resource = new Resource("/archive/rest/soaarchive/outgoing");
		 resource.get().send(new JsonCallback() {
			
			@Override
			public void onSuccess(Method method, JSONValue response) {
				// TODO Auto-generated method stub
				progDlg.close();
				GWT.log("onSuccess json respose");
				String data = response.toString();
				displayRowCounts(response);
				initTableNative(data);
				
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				// TODO Auto-generated method stub
				GWT.log("onFailure json " +  exception);

			}
		});

		 
		 
	 }
	 
	 private void displayRowCounts(JSONValue response)
	 {
		 JSONObject o =(JSONObject) response;
		 String totalRows = o.get("totalRows").toString();
		 String returnedRows = o.get("returnedRows").toString();
		 GWT.log("totalRows = " + totalRows + " returnedRows = " + returnedRows);
		 rowsLabel.setText("Displaying " + returnedRows + " out of " + totalRows + " in the database for this query");
	 }
	 
	 public native void updateData(String data) /*-{
	 	
	 	var dataObj = JSON.parse(data);
	 	$wnd.table.clear();
	 	$wnd.table.row.add(dataObj.data);
	 	$wnd.table.draw();
	 	
	 }-*/;
	 
	 public native void initTableNative(String data) /*-{
	 
	 var jsonData = JSON.parse(data);
	 console.log("data.totalrows = " + jsonData.totalRows);
	 that = this;
	 $wnd.$($doc).ready(function(){
	
	  
	 
	 
		 $wnd.table = $wnd.$('#outgoingTbl').DataTable( {
        	//'ajax' : '/archive/soaarchive.json',
        	"data" : jsonData.data,	
    	  "bFilter": false,
     	 	'columns' :[
        		{ 'data': 'id'},
        		{'data': 'incomingId'},
        		{'data': 'resendTime'},
        		{'data': 'toQueue'},
        		{'data': 'toQMgr'},
        		{'data': 'mqId'},
        		{'data': 'mqRfh2'}
        	],
      		'order': [[1, 'asc']]
   		});
	});
 }-*/;
	 
	@Override
	protected void onAttach() {
		// TODO Auto-generated method stub
		super.onAttach();
		GWT.log("onAttach called");
		initTable();
	}
	 
	

}