package il.co.menora.soaarchive.shared;

import java.util.List;

public class OutgoingResponse {

	private int totalRows;
	private int returnedRows;
	
	private List<OutgoingDto> data;
	
	
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public List<OutgoingDto> getData() {
		return data;
	}
	public void setData(List<OutgoingDto> data) {
		this.data = data;
	}
	public int getReturnedRows() {
		return returnedRows;
	}
	public void setReturnedRows(int returnedRows) {
		this.returnedRows = returnedRows;
	}
}
