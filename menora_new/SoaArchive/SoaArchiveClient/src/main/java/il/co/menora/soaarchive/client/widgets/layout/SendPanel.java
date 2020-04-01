package il.co.menora.soaarchive.client.widgets.layout;

import java.util.Date;
import java.util.List;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.JsonEncoderDecoder;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.vaadin.polymer.paper.widget.PaperButton;
import com.vaadin.polymer.paper.widget.PaperCheckbox;
import com.vaadin.polymer.paper.widget.PaperDialog;
import com.vaadin.polymer.paper.widget.PaperInput;
import com.vaadin.polymer.paper.widget.PaperRadioButton;

import il.co.menora.soaarchive.client.bundle.ClientResources;
import il.co.menora.soaarchive.client.exceptions.ClientExceptionsUtils;
import il.co.menora.soaarchive.shared.MQDto;
import il.co.menora.soaarchive.shared.MQResponse;

public class SendPanel extends Composite {
	
	interface InfoPanelUiBinder extends UiBinder<HTMLPanel, SendPanel> {
    }
 
	interface MQDtoCodec extends JsonEncoderDecoder<MQDto> {}
	
	interface MQResponseCodec extends JsonEncoderDecoder<MQResponse>{}

	private static MQDtoCodec mqCodec = GWT.create(MQDtoCodec.class);
	
	private static MQResponseCodec mqRespCodec = GWT.create(MQResponseCodec.class);
	
	private static InfoPanelUiBinder myUiBinder = GWT.create(InfoPanelUiBinder.class);
	
	@UiField
	Label msgToSendLabel;
	
	@UiField
	PaperInput queueMgr; 
	@UiField
	PaperInput queueName;
	@UiField
	PaperInput replyToQueue;
	@UiField
	PaperInput priority;
	@UiField
	PaperCheckbox createNew;
	
	
	@UiField
	PaperInput expiryDate;
	
	@UiField
	PaperCheckbox useQM;
	@UiField
	PaperCheckbox useQueue;
	
	@UiField
	PaperCheckbox sendExpired;
	
	
	@UiField
	PaperRadioButton updateExpiredRadio;
	@UiField
	PaperRadioButton updateAllRadio;
	
	@UiField
	PaperButton resendButton;
	
	@UiField
	PaperDialog progDlg;
	
	
	List<String> ids;




	
	public SendPanel()
	{
        initWidget(myUiBinder.createAndBindUi(this));
        useQM.setChecked(true);
        useQueue.setChecked(true);
        queueMgr.setDisabled(true);
        queueName.setDisabled(true);
        expiryDate.setDisabled(true);
        updateAllRadio.setDisabled(true);
        updateExpiredRadio.setDisabled(true);
	}
	
	@UiHandler("useQM")
	void handleQMCB(ClickEvent e)
	{
		PaperCheckbox cb = (PaperCheckbox)e.getSource();
		queueMgr.setDisabled(cb.getChecked());

	}
	@UiHandler("useQueue")
	void handleQueueCB(ClickEvent e)
	{
		PaperCheckbox cb = (PaperCheckbox)e.getSource();
		queueName.setDisabled(cb.getChecked());
		
	}
	
	@UiHandler("sendExpired")
	void handlesendExpired(ClickEvent e)
	{
		PaperCheckbox cb = (PaperCheckbox)e.getSource();
		expiryDate.setDisabled(!cb.getChecked());
		updateAllRadio.setDisabled(!cb.getChecked());
		updateExpiredRadio.setDisabled(!cb.getChecked());

	}
	
	@UiHandler("updateExpiredRadio")
	void handleUpdateExpiredRadio(ClickEvent e)
	{
		updateAllRadio.setChecked(false);


	}
	@UiHandler("updateAllRadio")
	void handleUpdateAllRadio(ClickEvent e)
	{
		updateExpiredRadio.setChecked(false);


	}
	
	
	@UiHandler("expiryDate")
	 void handleDateInputClick(ClickEvent e) {
		
		 int left = expiryDate.getAbsoluteLeft();
		 int top = expiryDate.getAbsoluteTop();
		 ClosablePopup popup = new ClosablePopup();
		 popup.setAutoHideEnabled(true);
		 popup.setPopupPosition(left - 300, top);
		 DatePicker dp = new DatePicker();
		 dp.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				// TODO Auto-generated method stub
				popup.hide();
				Date date = event.getValue();
				String s = DateTimeFormat.getFormat(DataTablePage.DATE_PATTERN).format(date);
				expiryDate.setValue(s);
			}
		});
		 
		 popup.add(dp);
		 popup.show();
		 
	 }

	@UiHandler("resendButton")
	void handleResendButton(ClickEvent e)
	{

		if(ids != null ) {
			GWT.log("resend clicked ids = " + ids.toString());
		}
		MQDto dto = new MQDto();
		dto.setCreateMsgId(createNew.getChecked());
		if(!useQM.getChecked()) {
			dto.setQueueMgr(queueMgr.getValue());
		} 
		if(!useQueue.getChecked()) {
			dto.setQueueName(queueName.getValue());
		}
		dto.setIds(ids);
		dto.setReplyToQueue(replyToQueue.getValue());
		String sPrio = priority.getValue();
		int priority=0;
		if(sPrio != null && sPrio.length() > 0) {
			priority = Integer.parseInt(sPrio);
		}
		dto.setPriority(priority);
		dto.setValidityDate(new Date());
		
		 String ds = expiryDate.getValue();
		 if(ds != null && ds.length() > 0) {
			 try {
				 Date valid = DateTimeFormat.getFormat(DataTablePage.DATE_PATTERN).parse(ds);
				 dto.setValidityDate(valid);
			 } catch(Exception e1) {
				 Window.alert("Error in date field.");
				 return;
			 }
		 }
		dto.setSendInvalid(sendExpired.getChecked());
		dto.setUpdateMsgDate(updateAllRadio.getChecked());
		 
		 // send request
		 progDlg.open();
		 Resource resource = new Resource("/archive/rest/soaarchive/mq");
		 JSONValue request = mqCodec.encode(dto);
			resource.post().json(request).send(new JsonCallback() {
					
					@Override
					public void onSuccess(Method method, JSONValue response) {
						// TODO Auto-generated method stub
						progDlg.close();
						MQResponse resp = mqRespCodec.decode(response);
						String exp = "";
						if(resp.getNumExpired() > 0) {
							exp = "Number of expired messaged skipped: " + resp.getNumExpired();
						}
						Window.alert("Status : " +  resp.getMsg() + "\r\nNumber of Messages Successfuly Resent: " + resp.getNumProcessed() +"\n" + exp);						
					}
					
					@Override
					public void onFailure(Method method, Throwable exception) {
						// TODO Auto-generated method stub
						progDlg.close();
						Window.alert("Error: " + exception);
						ClientExceptionsUtils.handleSessionExpiration(method);

					}
				});
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
		msgToSendLabel.setText("Number of Messages to Send: " + ids.size());
	}
	
	

	
}
