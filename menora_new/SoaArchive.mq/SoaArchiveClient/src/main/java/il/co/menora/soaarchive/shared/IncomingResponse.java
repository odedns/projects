package il.co.menora.soaarchive.shared;

import java.io.Serializable;
import java.util.List;

public class IncomingResponse implements Serializable {

	private int totalRows;
	private int returnedRows;
	
	private List<IncomingDto> data;
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public List<IncomingDto> getData() {
		return data;
	}
	public void setData(List<IncomingDto> data) {
		this.data = data;
	}
	public int getReturnedRows() {
		return returnedRows;
	}
	public void setReturnedRows(int returnedRows) {
		this.returnedRows = returnedRows;
	}
}


