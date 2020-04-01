package il.co.menora.soaarchive.client.widgets.layout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

import il.co.menora.soaarchive.client.widgets.layout.DataTablePage.MyDataTableUiBinder;

public class InfoPanel extends Composite {
	
	interface InfoPanelUiBinder extends UiBinder<HTMLPanel, InfoPanel> {
    }
 
	@UiField
	HTMLPanel bodyPanel;
	
	private static InfoPanelUiBinder myUiBinder = GWT.create(InfoPanelUiBinder.class);
	
	public InfoPanel()
	{
        initWidget(myUiBinder.createAndBindUi(this));

	}

	public void setText(String txt)
	{
		bodyPanel.add(new HTML(txt));
	}
}
