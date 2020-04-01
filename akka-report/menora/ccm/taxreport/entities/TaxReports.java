/**
 * 
 */
package menora.ccm.taxreport.entities;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ODEDNI
 *
 */
@XmlRootElement
public class TaxReports {

	private String reportYear;
	private List<TaxReportInsured> reportList;
	
	
	public TaxReports()
	{
		reportList = new LinkedList<TaxReportInsured>();
	}
	
	
	public void addReport(TaxReportInsured report)
	{
		reportList.add(report);
	}


	@XmlElement( name="taxReportInsured",type=TaxReportInsured.class)
	public List<TaxReportInsured> getReportList() {
		return reportList;
	}


	public void setReportList(List<TaxReportInsured> reportList) {
		this.reportList = reportList;
	}

	@XmlElement
	public String getReportYear() {
		return reportYear;
	}


	public void setReportYear(String reportYear) {
		this.reportYear = reportYear;
	}
	
}
