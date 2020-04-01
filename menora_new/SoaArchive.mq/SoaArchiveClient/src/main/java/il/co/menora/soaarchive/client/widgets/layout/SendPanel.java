package il.co.menora.soaarchive.client.widgets.layout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

import il.co.menora.soaarchive.client.widgets.layout.DataTablePage.MyDataTableUiBinder;

public class SendPanel extends Composite {
	
	interface InfoPanelUiBinder extends UiBinder<HTMLPanel, SendPanel> {
    }
 
	@UiField
	HTMLPanel bodyPanel;
	
	private static InfoPanelUiBinder myUiBinder = GWT.create(InfoPanelUiBinder.class);
	
	public SendPanel()
	{
        initWidget(myUiBinder.createAndBindUi(this));

	}

	public void setText(String txt)
	{
		bodyPanel.add(new HTML(txt));
	}
}
