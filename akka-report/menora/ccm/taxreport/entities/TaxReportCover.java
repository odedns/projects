package menora.ccm.taxreport.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TAX_REPORT_COVER database table.
 * 
 */
@Entity
@Table(name="TAX_REPORT_COVER")
@NamedQuery(name="TaxReportCover.findAll", query="SELECT t FROM TaxReportCover t")
public class TaxReportCover implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COVER_SEQUENCE")
	private long coverSequence;

	@Column(name="COVER_CATEGORY")
	private BigDecimal coverCategory;

	@Column(name="COVER_SUB_CATEGORY")
	private BigDecimal coverSubCategory;

	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="EXECUTION_NUMBER")
	private BigDecimal executionNumber;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="ID_NUMBER")
	private int idNumber;

	@Column(name="ID_TYPE")
	private String idType;

	@Column(name="INSURANCE_SUM")
	private BigDecimal insuranceSum;

	@Column(name="INSURED_NUMBER_IN_POLICY")
	private BigDecimal insuredNumberInPolicy;

	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="PREMIA_SUM")
	private BigDecimal premiaSum;

	@Column(name="TARIFF_NAME")
	private String tariffName;

	@Column(name="TARIFF_STATUS")
	private BigDecimal tariffStatus;

	//bi-directional many-to-one association to TaxReportPolicy
	
	@ManyToOne
	@JoinColumn(name="POLICY_SEQUENCE")
	private TaxReportPolicy taxReportPolicy;

	public TaxReportCover() {
	}

	public long getCoverSequence() {
		return this.coverSequence;
	}

	public void setCoverSequence(long coverSequence) {
		this.coverSequence = coverSequence;
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

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdType() {
		return this.idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public BigDecimal getInsuranceSum() {
		return this.insuranceSum;
	}

	public void setInsuranceSum(BigDecimal insuranceSum) {
		this.insuranceSum = insuranceSum;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getPremiaSum() {
		return this.premiaSum;
	}

	public void setPremiaSum(BigDecimal premiaSum) {
		this.premiaSum = premiaSum;
	}

	public String getTariffName() {
		return this.tariffName;
	}

	public void setTariffName(String tariffName) {
		this.tariffName = tariffName;
	}

	@XmlTransient
	public TaxReportPolicy getTaxReportPolicy() {
		return this.taxReportPolicy;
	}

	public void setTaxReportPolicy(TaxReportPolicy taxReportPolicy) {
		this.taxReportPolicy = taxReportPolicy;
	}

	public BigDecimal getTariffStatus() {
		return tariffStatus;
	}

	public void setTariffStatus(BigDecimal tariffStatus) {
		this.tariffStatus = tariffStatus;
	}

	public BigDecimal getInsuredNumberInPolicy() {
		return insuredNumberInPolicy;
	}

	public void setInsuredNumberInPolicy(BigDecimal insuredNumberInPolicy) {
		this.insuredNumberInPolicy = insuredNumberInPolicy;
	}

	
	public BigDecimal getExecutionNumber() {
		return executionNumber;
	}

	public void setExecutionNumber(BigDecimal executionNumber) {
		this.executionNumber = executionNumber;
	}

	public BigDecimal getCoverCategory() {
		return coverCategory;
	}

	public void setCoverCategory(BigDecimal coverCategory) {
		this.coverCategory = coverCategory;
	}

	public BigDecimal getCoverSubCategory() {
		return coverSubCategory;
	}

	public void setCoverSubCategory(BigDecimal coverSubCategory) {
		this.coverSubCategory = coverSubCategory;
	}


}