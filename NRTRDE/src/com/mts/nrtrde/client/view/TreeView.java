/**
 * 
 */
package com.mts.nrtrde.client.view;


import java.util.ArrayList;

import com.extjs.gxt.ui.client.binder.TreeBinder;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SimplePanel;

import com.mts.nrtrde.client.NRTRDE;
import com.mts.nrtrde.client.event.NavigationEvent;
import com.mts.nrtrde.client.presenter.TreePresenter;

/**
 * @author Oded Nissan
 *
 */
@SuppressWarnings("deprecation")
public class TreeView extends LayoutContainer implements  TreePresenter.Display  {

	private TreePanel menuTree;
	private TreePresenter presenter;
	
	public TreeView()
	{
		
		
	}
	
	 private TreePanel createTree() {
		  

		 BaseTreeModel nrtrde = new BaseTreeModel();
		 nrtrde.set("name",NRTRDE.constants.nrtrde());
		 BaseTreeModel config = new BaseTreeModel();
		 config.set("name",NRTRDE.constants.config());
		 BaseTreeModel reports = new BaseTreeModel();
		 reports.set("name",NRTRDE.constants.report());
		 BaseTreeModel regenerate = new BaseTreeModel();
		 regenerate.set("name",NRTRDE.constants.regeneratetaps());
		 
		 
		 BaseTreeModel generate = new BaseTreeModel();
		 generate.set("name",NRTRDE.constants.generation());
		 nrtrde.add(generate);
		 

		 BaseTreeModel params = new BaseTreeModel();
		 params.set("name",NRTRDE.constants.params());
		 
		 BaseTreeModel schedule = new BaseTreeModel();
		 schedule.set("name",NRTRDE.constants.scheduleTask());
		 BaseTreeModel ftp = new BaseTreeModel();
		 ftp.set("name",NRTRDE.constants.ftpTask());
		 config.add(schedule);
		 config.add(ftp);
		 config.add(params);
		 
		 BaseTreeModel fileDeliver = new BaseTreeModel();
		 fileDeliver.set("name",NRTRDE.constants.fileDeliveryReport());
		 BaseTreeModel errorReport = new BaseTreeModel();
		 errorReport.set("name",NRTRDE.constants.errorReport());
		 reports.add(fileDeliver);
		 reports.add(errorReport);
		 ArrayList<BaseTreeModel> modelList = new ArrayList<BaseTreeModel>();
		 modelList.add(nrtrde);
		 modelList.add(config);
		 modelList.add(regenerate);
		 modelList.add(reports);
		 TreeStore<BaseTreeModel> store = new TreeStore<BaseTreeModel>();
		 store.add(modelList, true);
		 
		 TreePanel<BaseTreeModel> menuTree = new TreePanel<BaseTreeModel>(store);
		 menuTree.setDisplayProperty("name");
		 AbstractImagePrototype leaf = AbstractImagePrototype.create(NRTRDE.images.leaf());
		 menuTree.getStyle().setLeafIcon(leaf);
		 AbstractImagePrototype folder = AbstractImagePrototype.create(NRTRDE.images.folder());
		 menuTree.getStyle().setNodeCloseIcon(folder);
		 AbstractImagePrototype folderOpen = AbstractImagePrototype.create(NRTRDE.images.folderopen());
		 menuTree.getStyle().setNodeOpenIcon(folderOpen);
		 menuTree.addListener(Events.OnClick, new Listener<TreePanelEvent<BaseTreeModel>>() {

			public void handleEvent(TreePanelEvent<BaseTreeModel> be) {
				// TODO Auto-generated method stub
				String item = be.getNode().getModel().get("name");
				GWT.log("got: "+ item);
				presenter.getEventBus().fireEvent(new NavigationEvent(item));
			}
			 
		 });
		
		return(menuTree); 
		 
		 
		 
	 }


	public TreePanel getTree() {
		// TODO Auto-generated method stub
		return this.menuTree;
	}

	@Override
	protected void onRender(Element parent, int index) {
		// TODO Auto-generated method stub
		super.onRender(parent, index);
		menuTree = createTree();
		menuTree.setSize("100%", "50%");
		add(menuTree);
		addStyleName("tree-panel");
		setSize("100%", "98%");
		layout();
	 	
	}

	public void setPresenter(TreePresenter presenter) {
		this.presenter = presenter;
	}

	public TreePresenter getPresenter() {
		return presenter;
	}
	

	
	 
	 
}
