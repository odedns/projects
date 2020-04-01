package il.co.menora.soaarchive.shared;

import java.io.Serializable;
import java.util.List;

public class StatusDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String CLOSED_MANUALLY = "CLOSED_MANUALLY";
	public static final String OPENED_MANUALLY = "OPENED_MANUALLY";
	public static final String NEW = "NEW";
	public static final String RERUN = "RERUN";
	public static final String BACK_FROM_RERUN = "BACK_FROM_RERUN";

	private List<String> ids;
	private String status;
	private SearchDto searchDto;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public static String cvtStatus(String status)
	{
		String st = null;
		if(status == null) {
			return("U");
		}
		switch(status) {
		case StatusDto.CLOSED_MANUALLY:
			st = "C";
			break;
		case StatusDto.OPENED_MANUALLY:
			st = "O";
			break;
		case StatusDto.NEW:
			st = "N";
			break;
		case StatusDto.RERUN:
			st = "R";
			break;
		case StatusDto.BACK_FROM_RERUN:
			st = "B";
			break;
		default:
			st = "U";
			break;
		
		}
		return(st);
	}
	public SearchDto getSearchDto() {
		return searchDto;
	}
	public void setSearchDto(SearchDto searchDto) {
		this.searchDto = searchDto;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
}
