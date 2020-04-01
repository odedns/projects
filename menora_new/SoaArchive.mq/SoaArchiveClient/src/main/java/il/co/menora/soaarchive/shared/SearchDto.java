package il.co.menora.soaarchive.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SearchDto implements Serializable {

	private String serviceName;
	private String fromDate;
	private String toDate;
	private List<String> statuses;
	private List<String> serviceTypes;
	private boolean showInvalid;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public List<String> getStatuses() {
		return statuses;
	}
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}
	public boolean isShowInvalid() {
		return showInvalid;
	}
	public void setShowInvalid(boolean showInvalid) {
		this.showInvalid = showInvalid;
	}
	public List<String> getServiceTypes() {
		return serviceTypes;
	}
	public void setServiceTypes(List<String> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}
	
	
	
	
}
