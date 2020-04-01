package menora.ccm.taxreport.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TAX_REPORT_XML_FILE database table.
 * 
 */
@Entity
@Table(name="TAX_REPORT_XML_FILE")
@NamedQuery(name="TaxReportXmlFile.findAll", query="SELECT t FROM TaxReportXmlFile t")
public class TaxReportXmlFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="XML_FILE_NUMBER")
	private long xmlFileNumber;

	@Column(name="CCM_FROM_ID")
	private BigDecimal ccmFromId;

	@Column(name="CCM_FTP_MESSAGE")
	private String ccmFtpMessage;

	@Column(name="CCM_FTP_STATUS")
	private short ccmFtpStatus;

	@Column(name="CCM_FTP_TIMESTAMP")
	private Timestamp ccmFtpTimestamp;

	@Column(name="CCM_SAFE_MESSAGE")
	private String ccmSafeMessage;

	@Column(name="CCM_SAFE_STATUS")
	private short ccmSafeStatus;

	@Column(name="CCM_SAFE_TIMESTAMP")
	private Timestamp ccmSafeTimestamp;

	@Column(name="CCM_TO_ID")
	private BigDecimal ccmToId;

	@Column(name="CCM_XML_MESSAGE")
	private String ccmXmlMessage;

	@Column(name="CCM_XML_STATUS")
	private short ccmXmlStatus;

	@Column(name="CCM_XML_TIMESTAMP")
	private Timestamp ccmXmlTimestamp;

	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="EXECUTION_NUMBER")
	private BigDecimal executionNumber;

	@Column(name="XML_FILE_NAME")
	private String xmlFileName;

	public TaxReportXmlFile() {
	}

	public long getXmlFileNumber() {
		return this.xmlFileNumber;
	}

	public void setXmlFileNumber(long xmlFileNumber) {
		this.xmlFileNumber = xmlFileNumber;
	}

	public BigDecimal getCcmFromId() {
		return this.ccmFromId;
	}

	public void setCcmFromId(BigDecimal ccmFromId) {
		this.ccmFromId = ccmFromId;
	}

	public String getCcmFtpMessage() {
		return this.ccmFtpMessage;
	}

	public void setCcmFtpMessage(String ccmFtpMessage) {
		this.ccmFtpMessage = ccmFtpMessage;
	}

	public short getCcmFtpStatus() {
		return this.ccmFtpStatus;
	}

	public void setCcmFtpStatus(short ccmFtpStatus) {
		this.ccmFtpStatus = ccmFtpStatus;
	}

	public Timestamp getCcmFtpTimestamp() {
		return this.ccmFtpTimestamp;
	}

	public void setCcmFtpTimestamp(Timestamp ccmFtpTimestamp) {
		this.ccmFtpTimestamp = ccmFtpTimestamp;
	}

	public String getCcmSafeMessage() {
		return this.ccmSafeMessage;
	}

	public void setCcmSafeMessage(String ccmSafeMessage) {
		this.ccmSafeMessage = ccmSafeMessage;
	}

	public short getCcmSafeStatus() {
		return this.ccmSafeStatus;
	}

	public void setCcmSafeStatus(short ccmSafeStatus) {
		this.ccmSafeStatus = ccmSafeStatus;
	}

	public Timestamp getCcmSafeTimestamp() {
		return this.ccmSafeTimestamp;
	}

	public void setCcmSafeTimestamp(Timestamp ccmSafeTimestamp) {
		this.ccmSafeTimestamp = ccmSafeTimestamp;
	}

	public BigDecimal getCcmToId() {
		return this.ccmToId;
	}

	public void setCcmToId(BigDecimal ccmToId) {
		this.ccmToId = ccmToId;
	}

	public String getCcmXmlMessage() {
		return this.ccmXmlMessage;
	}

	public void setCcmXmlMessage(String ccmXmlMessage) {
		this.ccmXmlMessage = ccmXmlMessage;
	}

	public short getCcmXmlStatus() {
		return this.ccmXmlStatus;
	}

	public void setCcmXmlStatus(short ccmXmlStatus) {
		this.ccmXmlStatus = ccmXmlStatus;
	}

	public Timestamp getCcmXmlTimestamp() {
		return this.ccmXmlTimestamp;
	}

	public void setCcmXmlTimestamp(Timestamp ccmXmlTimestamp) {
		this.ccmXmlTimestamp = ccmXmlTimestamp;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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