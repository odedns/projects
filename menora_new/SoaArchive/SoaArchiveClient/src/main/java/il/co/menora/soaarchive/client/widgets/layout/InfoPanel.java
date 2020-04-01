package il.co.menora.soaarchive.client.widgets.layout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.vaadin.polymer.paper.widget.PaperButton;
import com.vaadin.polymer.paper.widget.PaperCard;

import il.co.menora.soaarchive.shared.StatusDto;

public class InfoPanel extends Composite {
	
	interface InfoPanelUiBinder extends UiBinder<HTMLPanel, InfoPanel> {
    }
 
	@UiField
	HTMLPanel bodyPanel;
	@UiField
	PaperCard paperCard;
	@UiField
	PaperButton closePopupButton;
	
	ClosablePopup popup;
	
	private static InfoPanelUiBinder myUiBinder = GWT.create(InfoPanelUiBinder.class);
	
	public InfoPanel()
	{
		GWT.log("info panel created...");
        initWidget(myUiBinder.createAndBindUi(this));

	}

	@UiHandler("closePopupButton")
	void handleClosePopupButton(ClickEvent e)
	{
		GWT.log("closePopupButton clicked..");
		this.popup.hide();
	}
	
	public void setTitle(String title) {
		paperCard.setHeading(title);
	}
	
	public void setText(String txt)
	{
		bodyPanel.add(new HTML(txt));
	}

	public ClosablePopup getPopup() {
		return popup;
	}

	public void setPopup(ClosablePopup popup) {
		this.popup = popup;
	}
	
	
	
	
}
