package il.co.menora.soaarchive.client.widgets.layout;


import java.util.Date;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.TextCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.dev.shell.remoteui.RemoteMessageProto.Message.Request.ServiceType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.vaadin.polymer.paper.widget.PaperButton;
import com.vaadin.polymer.paper.widget.PaperCheckbox;
import com.vaadin.polymer.paper.widget.PaperDialog;
import com.vaadin.polymer.paper.widget.PaperInput;

import il.co.menora.soaarchive.client.events.ClientEventBus;
import il.co.menora.soaarchive.client.events.ReloadDataEvent;
import il.co.menora.soaarchive.client.events.ReloadDataEvent.ReloadDataEventHandler;
import il.co.menora.soaarchive.client.places.ClientPlaceController;
import il.co.menora.soaarchive.client.places.OutgoingPlace;
import il.co.menora.soaarchive.shared.SOAServiceType;
import il.co.menora.soaarchive.shared.Statuses;

public class DataTablePage extends Composite {
	 
	interface MyDataTableUiBinder extends UiBinder<HTMLPanel, DataTablePage> {
    }
 
	private static final String DATE_PATTERN = "dd/MM/yyyy hh:mm:ss";
	private static MyDataTableUiBinder myUiBinder = GWT.create(MyDataTableUiBinder.class);
	
	@UiField
	PaperButton searchButton;
	@UiField
	PaperInput fromDateInput;
	@UiField
	PaperInput toDateInput;
	
	@UiField
	PaperInput serviceNameInput;
	
	@UiField
	PaperCheckbox newCheckBox;
	@UiField
	PaperCheckbox cmCheckBox;
	@UiField
	PaperCheckbox rrCheckBox;
	@UiField
	PaperCheckbox rrbCheckBox;
	@UiField
	PaperCheckbox iibCheckBox;
	@UiField
	PaperCheckbox dpCheckBox;
	@UiField
	PaperCheckbox expiredCheckBox;
	@UiField
	PaperDialog modalDlg;
	@UiField
	HTMLPanel dialogPanel;
	@UiField
	PaperDialog progDlg;
	@UiField
	PaperButton outgoingButton;
	@UiField
	PaperButton sendButton;
	@UiField
	PaperButton sendAllButton; 
    @UiField
    Label rowsLabel;
    
	private boolean attached = false;
	
	 public DataTablePage()
	 {
	        initWidget(myUiBinder.createAndBindUi(this));
			 Defaults.setServiceRoot("/archive");
			 Defaults.setDateFormat(null);
	 }
	 
	 
	 private void reload() {
			GWT.log("in reload");
			destroyTable();
			initTable();
		}
		 
		
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
		 
		 
	 
	 @UiHandler("searchButton")
	 void handleSearchButtonClick(ClickEvent e) {
		 Date fromDate=null;
		 Date toDate=null;
		 String fromValue = null;
		 String toValue = null;
		 GWT.log("searchbutton clicked...");
		 String serviceName = serviceNameInput.getValue();
		 fromValue = fromDateInput.getValue();
		 if(fromValue != null && fromValue.length() > 0) {
			 fromDate = DateTimeFormat.getFormat(DATE_PATTERN).parse(fromValue);
		 }
		 toValue = toDateInput.getValue();
		 if(toValue != null && toValue.length() > 0) {

			 toDate = DateTimeFormat.getFormat(DATE_PATTERN).parse(toValue);
		 }
		  GWT.log("serviceName= " + serviceName);
		 if(fromDate != null) {
			  GWT.log("fromDate = " + fromDate.toString());
		 }
		 if(toDate != null) {
			  GWT.log("toDate = " + toDate.toString());
		 }
	
		JSONArray array = new JSONArray();
		int i=0;
		if(newCheckBox.getChecked()) {
			array.set(i++, new JSONString(Statuses.STATUS_NEW));

		}
		if(rrCheckBox.getChecked()) {
			array.set(i++, new JSONString(Statuses.STATUS_RR));

		}
		if(rrbCheckBox.getChecked()) {
			array.set(i++, new JSONString(Statuses.STATUS_RRFB));
		}
		if(cmCheckBox.getChecked()) {
			array.set(i++, new JSONString(Statuses.STATUS_CM));

		}
		JSONArray typeArray = new JSONArray();
		int j=0;
		if(dpCheckBox.getChecked()) {
			typeArray.set(j++, new JSONString(SOAServiceType.TYPE_DP));
		}
		if(iibCheckBox.getChecked()) {
			typeArray.set(j++, new JSONString(SOAServiceType.TYPE_IIB));

		}
		Resource resource = new Resource("/archive/rest/soaarchive");
		JSONObject request = new JSONObject();
		request.put("serviceName", new JSONString(serviceName));
		request.put("fromDate", new JSONString(fromValue));
		request.put("toDate", new JSONString(toValue));
		request.put("statuses",array);
		request.put("serviceTypes", typeArray);
		if(expiredCheckBox.getChecked()) {
			request.put("showInvalid",  new JSONString("true"));
		}
		resource.post().json(request).send(new JsonCallback() {
				
				@Override
				public void onSuccess(Method method, JSONValue response) {
					// TODO Auto-generated method stub
					GWT.log("on success respons = " + response );
					String data = response.toString();
					displayRowCounts(response);
					updateData(data);
				}
				
				@Override
				public void onFailure(Method method, Throwable exception) {
					// TODO Auto-generated method stub
					GWT.log("error = " + exception);
				}
			});

		  
	 }

	
		 @UiHandler("fromDateInput")
		 void handleDateInputClick(ClickEvent e) {
			 GWT.log("date input clicked");
			 
			 int left = fromDateInput.getAbsoluteLeft();
			 int top = fromDateInput.getAbsoluteTop();
			 ClosablePopup popup = new ClosablePopup();
			  popup.setPopupPosition(left, top+60);
			 DatePicker dp = new DatePicker();
			 dp.addValueChangeHandler(new ValueChangeHandler<Date>() {
				
				@Override
				public void onValueChange(ValueChangeEvent<Date> event) {
					// TODO Auto-generated method stub
					popup.hide();
					Date date = event.getValue();
					String s = DateTimeFormat.getFormat(DATE_PATTERN).format(date);
					GWT.log("date = " + date.toString());
					fromDateInput.setValue(s);
				}
			});
			 
			 popup.add(dp);
			 popup.show();
			 
		 }
		
		 @UiHandler("toDateInput")
		 void handleToDateInputClick(ClickEvent e) {
			 GWT.log("date input clicked");
			 
			 int left = toDateInput.getAbsoluteLeft();
			 int top = toDateInput.getAbsoluteTop();
			  ClosablePopup popup = new ClosablePopup();
			  popup.setPopupPosition(left, top+60);
			  
			  
			 DatePicker dp = new DatePicker();
			 
			 dp.addValueChangeHandler(new ValueChangeHandler<Date>() {
				
				@Override
				public void onValueChange(ValueChangeEvent<Date> event) {
					// TODO Auto-generated method stub
					popup.hide();
					Date date = event.getValue();
					String s = DateTimeFormat.getFormat(DATE_PATTERN).format(date);
					GWT.log("date = " + date.toString());
					toDateInput.setValue(s);
					
				}
			});
			 popup.add(dp);
			 popup.show();
			 
		 }

	  
	 @UiHandler("sendButton")
	 void handleSendButton(ClickEvent e)
	 {
		 GWT.log("in handleSendButton");
		 JavaScriptObject o = selectedRows();
		 JsArrayInteger intArrary =  o.cast();
		 for(int i=0; i < intArrary.length(); ++i) {
			 GWT.log("o[" + i +"]=" + intArrary.get(i));
			 
		 }
		 SendPanel sendPanel = new SendPanel(); 
		 sendPanel.setText("in send panel");
		 dialogPanel.clear();
		 dialogPanel.add(sendPanel);
		 modalDlg.open();
	 }
	 @UiHandler("sendAllButton")
	 void handleSendAllButton(ClickEvent e)
	 {
		 SendPanel sendPanel = new SendPanel(); 
		 sendPanel.setText("in send panel");
		 dialogPanel.clear();
		 dialogPanel.add(sendPanel);
		 modalDlg.open();
	 }
	 
	 
	 @UiHandler("outgoingButton")
	 void handleOutgoingButton(ClickEvent e){
		 GWT.log("outgoint button pressed");
		 ClientPlaceController.get().goTo(new OutgoingPlace());
	 }
	 
	 private String createTableRow(String label, Object value)
	 {
		 StringBuffer sb = new StringBuffer();
		 sb.append("<tr><td>");
		 sb.append(label);
		 sb.append(": </td><td>");
		 sb.append(value);
		 sb.append("</td></tr>");
		 return(sb.toString());
	 }
	 
	 
	 private String getJsonObjectValue(JSONObject o,String key)
	 {
		 String s = null;
		 
		 JSONValue jsonStr =  o.get(key);
		 if(jsonStr != null) {
			 if(jsonStr.isString() != null) {
				 s = ((JSONString)jsonStr).stringValue();
			 } else {
				 s = jsonStr.toString();
			 }
		 }
		 return(s);
	 }
	 
	 
	 public void showAdditional(int id)
	 {
		 GWT.log("additional info");
		 Resource resource = new Resource("/archive/rest/soaarchive/" + id);
		 resource.get().send(new JsonCallback() {
			
			@Override
			public void onSuccess(Method method, JSONValue response) {
				
				JSONObject o = (JSONObject) response;
				StringBuffer sb = new StringBuffer();
				String value = o.get("id").toString();
				sb.append("<table>");
				sb.append(createTableRow("ID", id));
				value = getJsonObjectValue(o,"fromOutgoingId"); 
				sb.append(createTableRow("Parent ID",value));
				value = getJsonObjectValue(o,"serviceName"); 
				sb.append(createTableRow("Service Name ",value));
				value = getJsonObjectValue(o,"serviceType"); 
				sb.append(createTableRow("Service Type ",value));
				value = getJsonObjectValue(o,"status"); 
				sb.append(createTableRow("Status",value));
				value = getJsonObjectValue(o,"statusTime"); 
				sb.append(createTableRow("Status Time",value));
				value = getJsonObjectValue(o,"lastUser"); 
				sb.append(createTableRow("Last User",value));
				value = getJsonObjectValue(o,"errorTime"); 
				sb.append(createTableRow("Error Time",value));
				value = getJsonObjectValue(o,"incomingTime"); 
				sb.append(createTableRow("Incoming Time",value));
				value = o.get("rerunsNum").toString(); 
				sb.append(createTableRow("Num Reruns",value));
				value = getJsonObjectValue(o,"fromQueue"); 
				sb.append(createTableRow("Queue",value));
				value = getJsonObjectValue(o,"fromQMgr"); 
				sb.append(createTableRow("Queue Manager",value));
				value = getJsonObjectValue(o,"errorCode"); 
				sb.append(createTableRow("Error Code",value));
				value = getJsonObjectValue(o,"errorMessage"); 
				sb.append(createTableRow("Error Message",value));
				value = o.get("payloadSize").toString(); 
				sb.append(createTableRow("Payload Size",value));
				value = getJsonObjectValue(o,"getTime"); 
				sb.append(createTableRow("Get Time",value));
				value = getJsonObjectValue(o,"incomingTime"); 
				sb.append(createTableRow("Incoming Time",value));
				value = getJsonObjectValue(o,"fromServerName"); 
				sb.append(createTableRow("Server Name",value));
				value = getJsonObjectValue(o,"fromServerIp"); 
				sb.append(createTableRow("Server IP",value));
				value = o.get("mqMessageId").toString(); 
				sb.append(createTableRow("MQ Msg ID",value));
				value = o.get("mqId").toString(); 
				sb.append(createTableRow("MQ ID",value));
				value = getJsonObjectValue(o,"mqRfh2"); 
				sb.append(createTableRow("MQ RFH2",value));
				value = o.get("mqBackoutCount").toString(); 
				sb.append(createTableRow("MQ BackoutCount",value));
				value = getJsonObjectValue(o,"bipMsg"); 
				sb.append(createTableRow("Bip Msg",value));
				value = getJsonObjectValue(o,"expiryTime"); 
				sb.append(createTableRow("Expiry Time",value));
				String text = sb.toString();
				InfoPanel infoPanel = new InfoPanel();
				infoPanel.setText(text);
				dialogPanel.clear();
				dialogPanel.add(infoPanel);
				modalDlg.open();
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				// TODO Auto-generated method stub
				GWT.log("error = " + exception);
				
			}
		});
		 
		 
		 
	 }
	 
	 public void showErrorMessage(int id)
	 {
		 GWT.log("in showeErrorMessge =" + id);
		 
		 Resource resource = new Resource("/archive/rest/soaarchive/error/" + id);
		 resource.get().send(new TextCallback() {
			
			@Override
			public void onSuccess(Method method, String response) {
				// TODO Auto-generated method stub
				GWT.log("success msg = " + response);
				InfoPanel infoPanel = new InfoPanel();
				infoPanel.setText(response);
				 dialogPanel.clear();
				 dialogPanel.add(infoPanel);
				 modalDlg.open();
				
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				// TODO Auto-generated method stub
				GWT.log("error = " + exception);
			}
		});

		 
		 
		 
	 }
	 
	 public void showPayloadData(int id){
		 GWT.log("in showPayloadData =" + id);
		 
		 Resource resource = new Resource("/archive/rest/soaarchive/payload/" + id);
		 resource.get().send(new TextCallback() {
			
			@Override
			public void onSuccess(Method method, String response) {
				// TODO Auto-generated method stub
				GWT.log("success payload = " + response);
				InfoPanel infoPanel = new InfoPanel();
				infoPanel.setText(response);
				 dialogPanel.clear();
				 dialogPanel.add(infoPanel);
				 modalDlg.open();
				
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				// TODO Auto-generated method stub
				GWT.log("error = " + exception);
			}
		});

		 
		 
	 }
	 
	 
	 public native JavaScriptObject selectedRows() /*-{
	   	var rows_selected = $wnd.table.column(0).checkboxes.selected();
           console.log("row selectd=" + rows_selected);
       		return(rows_selected);
	 }-*/;
	 
	 
	 public void initTable()
	 {
		 callRest();
	 }
	 
	 public void callRest()
	 {
		 
		 progDlg.open();
		 Resource resource = new Resource("/archive/rest/soaarchive");
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
	 	for(var i=0; i < dataObj.data.length; ++i) {
	 		$wnd.table.row.add(dataObj.data[i]);
	 	}
	 	$wnd.table.draw();
	 	
	 }-*/;
	 
	 public native void destroyTable() /*-{
	 	
	 	$wnd.table.destroy();
	 	
	 }-*/;
	 
	 
	 
	 public native void initTableNative(String data) /*-{
	 
	 var jsonData = JSON.parse(data);
	 console.log("data.totalrows = " + jsonData.totalRows);
	 that = this;
	 $wnd.$($doc).ready(function(){
	
	  
	  $wnd.showData= function(data){
   			console.log("in showData data=" + data);
   			that.@il.co.menora.soaarchive.client.widgets.layout.DataTablePage::showPayloadData(I)(data);
   	  };
	 $wnd.showError= function(data){
   			console.log("in showError data=" + data);
   			that.@il.co.menora.soaarchive.client.widgets.layout.DataTablePage::showErrorMessage(I)(data);
   	  };
	 
	 $wnd.showAdditional= function(data){
   			console.log("in showAdditional data=" + data);
   			that.@il.co.menora.soaarchive.client.widgets.layout.DataTablePage::showAdditional(I)(data);
   	  };
	 
		 $wnd.table = $wnd.$('#example').DataTable( {
        	"data" : jsonData.data,	
       	    "bFilter": false,
            'columns' :[
        		{ 'data': 'id'},
        		{'data': 'serviceName'},
        		{'data': 'serviceType'},
        		{'data': 'status'},
		        {'data': 'statusTime'},
		        {'data': 'lastUser'},
		        {'data': 'fromOutgoingId'},
		        {'data': 'payloadSize'},
		 		{'data': 'incomingTime'}, 
		 		{'data': 'errorTime'}, 
		 		{'data': 'errorCode'},
		 		{'data': 'errorMessage'},
		 		{'data': 'fromQueue'},
		 		{'data': 'fromQMgr'},
		 		{'data': 'rerunsNum'},
		 		{'data': 'additionalId'}

        	],
	      'columnDefs': [
	         {
	            'targets': 0,
	            'checkboxes': {
	               'selectRow': false
	            },
	           'className': 'mdl-data-table__cell--non-numeric'
	
	         },
	         {
	          'targets': 15,
	          'render': function(data,type,full,meta) {
	          				return('<img src="images/info.png" height="20" width="20" alt="info" title="information" onclick="showAdditional(' +data + ');"/>'
	                  +'<img src="images/xml.png" height="20" width="20" alt="info" title="xml" onclick="showData(' +data + ');"/>'
	                + '<img src="images/error.png" height="20" width="20" alt="info" title="error" onclick="showError(' +data + ');">');
	          				
	          				
	          			}
	          }
	      ],
	      'select': {
	         'style': 'multi'
	      },
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

	@Override
	protected void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		GWT.log("onDetach called");
		destroyTable();
	}

}
