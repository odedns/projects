package menora.ccm.taxreport.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TAX_REPORT_RUN_CONTROL database table.
 * 
 */
@Entity
@Table(name="TAX_REPORT_RUN_CONTROL")
@NamedQuery(name="TaxReportRunControl.findAll", query="SELECT t FROM TaxReportRunControl t")
public class TaxReportRunControl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTROL_NUMBER")
	private long controlNumber;

	@Column(name="CCM_FROM_ID")
	private BigDecimal ccmFromId;

	@Column(name="CCM_TO_ID")
	private BigDecimal ccmToId;

	@Column(name="CONTROL_STATION_CODE")
	private BigDecimal controlStationCode;

	@Column(name="CONTROL_STATION_MESSAGE")
	private String controlStationMessage;

	@Column(name="CONTROL_STATION_STATUS")
	private short controlStationStatus;

	@Column(name="CONTROL_STATION_TIMESTAMP")
	private Timestamp controlStationTimestamp;

	@Column(name="EXECUTION_NUMBER")
	private BigDecimal executionNumber;

	@Column(name="XML_FILE_NAME")
	private String xmlFileName;

	public TaxReportRunControl() {
	}

	public long getControlNumber() {
		return this.controlNumber;
	}

	public void setControlNumber(long controlNumber) {
		this.controlNumber = controlNumber;
	}

	public BigDecimal getCcmFromId() {
		return this.ccmFromId;
	}

	public void setCcmFromId(BigDecimal ccmFromId) {
		this.ccmFromId = ccmFromId;
	}

	public BigDecimal getCcmToId() {
		return this.ccmToId;
	}

	public void setCcmToId(BigDecimal ccmToId) {
		this.ccmToId = ccmToId;
	}

	public BigDecimal getControlStationCode() {
		return this.controlStationCode;
	}

	public void setControlStationCode(BigDecimal controlStationCode) {
		this.controlStationCode = controlStationCode;
	}

	public String getControlStationMessage() {
		return this.controlStationMessage;
	}

	public void setControlStationMessage(String controlStationMessage) {
		this.controlStationMessage = controlStationMessage;
	}

	public short getControlStationStatus() {
		return this.controlStationStatus;
	}

	public void setControlStationStatus(short controlStationStatus) {
		this.controlStationStatus = controlStationStatus;
	}

	public Timestamp getControlStationTimestamp() {
		return this.controlStationTimestamp;
	}

	public void setControlStationTimestamp(Timestamp controlStationTimestamp) {
		this.controlStationTimestamp = controlStationTimestamp;
	}

	public BigDecimal getExecutionNumber() {
		return this.executionNumber;
	}

	public void setExecutionNumber(BigDecimal executionNumber) {
		this.executionNumber = executionNumber;
	}

	public String getXmlFileName() {
		return this.xmlFileName;
	}

	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

}