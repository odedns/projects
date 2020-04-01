package com.mts.nrtrde.client;

import java.util.ArrayList;  
import java.util.List;  
  

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;  
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BasePagingLoader;  
import com.extjs.gxt.ui.client.data.ModelData;  
import com.extjs.gxt.ui.client.data.PagingLoadResult;  
import com.extjs.gxt.ui.client.data.PagingLoader;  
import com.extjs.gxt.ui.client.data.PagingModelMemoryProxy;  
import com.extjs.gxt.ui.client.store.ListStore;  
import com.extjs.gxt.ui.client.widget.ContentPanel;  
import com.extjs.gxt.ui.client.widget.LayoutContainer;  
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;  
import com.extjs.gxt.ui.client.widget.grid.ColumnData;  
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;  
import com.extjs.gxt.ui.client.widget.grid.Grid;  
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;  
import com.extjs.gxt.ui.client.widget.layout.FitLayout;  
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;  
import com.extjs.gxt.ui.client.widget.table.NumberCellRenderer;  
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;  
import com.google.gwt.i18n.client.DateTimeFormat;  
import com.google.gwt.i18n.client.NumberFormat;  
import com.google.gwt.user.client.Element;  

class Stock extends BaseModel {
	
	public Stock(String name,String symbol,String last)
	{
		set("name", name);
		set("symbol",symbol);
		set("last",last);
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		set("name", name);
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return get("name");
	}
	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		set("symbol", symbol);
	}
	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return get("symbol");
	}
	/**
	 * @param last the last to set
	 */
	public void setLast(String last) {
		set("last",last);
	}
	/**
	 * @return the last
	 */
	public String getLast() {
		return get("last");
	}
}


public class MemoryPagingGridExample extends LayoutContainer {  
  
  @Override  
  protected void onRender(Element parent, int index) {  
    super.onRender(parent, index);  
  
    setLayout(new FlowLayout(10));  
  
    ArrayList<Stock> l = new ArrayList<Stock>();
    l.add(new Stock("AAAA","DOX","1234"));
    // add paging support for a local collection of models  
    PagingModelMemoryProxy proxy = new PagingModelMemoryProxy(l);  
  
    // loader  
    PagingLoader<PagingLoadResult<ModelData>> loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);  
    loader.setRemoteSort(true);  
  
    ListStore<Stock> store = new ListStore<Stock>(loader);  
  
    final PagingToolBar toolBar = new PagingToolBar(10);  
    toolBar.bind(loader);  
  
    loader.load(0, 10);  
  
    final NumberFormat currency = NumberFormat.getCurrencyFormat();  
    final NumberFormat number = NumberFormat.getFormat("0.00");  
    final NumberCellRenderer<Grid<Stock>> numberRenderer = new NumberCellRenderer<Grid<Stock>>(currency);  
  
    GridCellRenderer<Stock> change = new GridCellRenderer<Stock>() {  
      public String render(Stock model, String property, ColumnData config, int rowIndex, int colIndex,  
          ListStore<Stock> store, Grid<Stock> grid) {  
        double val = (Double) model.get(property);  
        String style = val < 0 ? "red" : "green";  
        return "<span style='color:" + style + "'>" + number.format(val) + "</span>";  
      }  
    };  
  
    GridCellRenderer<Stock> gridNumber = new GridCellRenderer<Stock>() {  
      public String render(Stock model, String property, ColumnData config, int rowIndex, int colIndex,  
          ListStore<Stock> store, Grid<Stock> grid) {  
        return numberRenderer.render(null, property, model.get(property));  
      }  
    };  
  
    List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
  
    ColumnConfig column = new ColumnConfig();  
    column.setId("name");  
    column.setHeader("Company");  
    column.setWidth(200);  
    configs.add(column);  
  
    column = new ColumnConfig();  
    column.setId("symbol");  
    column.setHeader("Symbol");  
    column.setWidth(100);  
    configs.add(column);  
  
    column = new ColumnConfig();  
    column.setId("last");  
    column.setHeader("Last");  
    column.setAlignment(HorizontalAlignment.RIGHT);  
    column.setWidth(75);  
    column.setRenderer(gridNumber);  
    configs.add(column);  
  
      ColumnModel cm = new ColumnModel(configs);  
  
    ContentPanel cp = new ContentPanel();  
    cp.setFrame(true);  
    cp.setHeading("Local Paging Grid");  
   // cp.setIcon(Resources.ICONS.table());  
    cp.setButtonAlign(HorizontalAlignment.CENTER);  
    cp.setLayout(new FitLayout());  
    cp.setBottomComponent(toolBar);  
    cp.setSize(600, 200);  
  
    Grid<Stock> grid = new Grid<Stock>(store, cm);  
    grid.setBorders(true);  
    grid.setAutoExpandColumn("name");  
    grid.getAriaSupport().setDescribedBy(toolBar.getId() + "-display");  
  
    cp.add(grid);  
  
    add(cp);  
  }  
  
}  