package menora.ccm.taxreport.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;




import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "TAX_REPORT_INSURED")
@NamedQueries ({
	@NamedQuery(name = "TaxReportInsured.findAll", query = "SELECT t FROM TaxReportInsured t"),
	@NamedQuery (name="TaxReportInsured.findByPopulation",
			query= "SELECT t FROM TaxReportInsured t WHERE t.executionNumber=:popCode ORDER BY t.insuredSequence, t.executionNumber"			
			),
	@NamedQuery(name= "TaxReportInsured.count", query="SELECT count(t)  FROM TaxReportInsured t WHERE t.executionNumber=:popCode"),
	@NamedQuery(name= "TaxReportInsured.min_max", query="SELECT MIN(t.insuredSequence),MAX(t.insuredSequence) FROM TaxReportInsured t WHERE t.executionNumber=:popCode"),
	@NamedQuery (name="TaxReportInsured.findByIdPopulation",
	query= "SELECT t FROM TaxReportInsured t WHERE t.insuredSequence=:id AND t.executionNumber=:popCode"),
	@NamedQuery(name="TaxReportInsured.findByPopulationEx",
	query= "SELECT t FROM TaxReportInsured t WHERE t.executionNumber=:popCode AND (t.insuredSequence >= :first AND t.insuredSequence < :last) ORDER BY t.insuredSequence "
	)
})
@XmlRootElement
public class TaxReportInsured implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INSURED_SEQUENCE")
	private long insuredSequence;

	@Column(name = "APPARTMENT_NUMBER")
	private String appartmentNumber;

	@Column(name = "BIRTH_DATE")
	private BigDecimal birthDate;


	@Column(name="APPENDIX_CLAUSE_45_AA")
	private BigDecimal appendixClause45Aa;

	@Column(name="APPENDIX_CLAUSE_45_AB")
	private BigDecimal appendixClause45Ab;

	@Column(name="APPENDIX_CLAUSE_47")
	private BigDecimal appendixClause47;

	@Column(name="APPENDIX_CLAUSE_LOWC")
	private BigDecimal appendixClauseLowc;

	@Column(name="APPENDIX_CLAUSE_OTHER")
	private BigDecimal appendixClauseOther;

	@Column(name="ATTACHMENT_LOCATION")
	private BigDecimal attachmentLocation;

	private String barcode;

	@Column(name="BENEFICIARY_UPDATE_DAT")
	private BigDecimal beneficiaryUpdateDat;
	
	@Column(name = "CITY_NAME")
	private String cityName;

	@Column(name="CONCENTRATION_PAGE_SWITCH")
	private BigDecimal concentrationPageSwitch;

	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	@Column(name = "CREATE_USER")
	private String createUser;

	@Column(name="DEPOSIT_SUM_SWITCH")
	private BigDecimal depositSumSwitch;
	
	@Column(name="ERROR_REASON_CODE")
	private BigDecimal errorReasonCode;

	@Column(name = "EXECUTION_NUMBER")
	private BigDecimal executionNumber;

	@Column(name = "FAX_NUMBER")
	private String faxNumber;

	@Column(name = "FIRST_NAME")
	private String firstName;

	private String gender;

	@Column(name = "HOUSE_NUMBER")
	private String houseNumber;

	@Column(name = "ID_NUMBER")
	private int idNumber;

	@Column(name = "ID_TYPE")
	private String idType;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "LOSS_OF_WORKING_CAPACITY")
	private BigDecimal lossOfWorkingCapacity;

	@Column(name="LOWC_DEPOSIT_SWITCH")
	private BigDecimal lowcDepositSwitch;

	@Column(name="LOWC_SHOW_SWITCH")
	private BigDecimal lowcShowSwitch;

	@Column(name = "MARITIAL_STATUS")
	private String maritialStatus;

	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	private String pob;

	@Column(name = "REDEMPTION_SUM")
	private BigDecimal redemptionSum;

	@Column(name = "RISK_SUM")
	private BigDecimal riskSum;

	@Column(name="STATUS_FOR_REPORT")
	private BigDecimal statusForReport;
	
	@Column(name = "STREET_NAME")
	private String streetName;

	@Column(name = "ZIP_CODE")
	private String zipCode;

	// bi-directional many-to-one association to TaxReportPolicy
	
	@OneToMany(mappedBy = "taxReportInsured")
	private List<TaxReportPolicy> taxReportPolicies;

	//bi-directional one-to-one association to TaxReportDepositForInsured
	@OneToOne(mappedBy="taxReportInsured")
	private TaxReportDepositForInsured taxReportDepositForInsured;

	//bi-directional many-to-one association to TaxReportPensionByAge
	@OneToMany(mappedBy="taxReportInsured")
	private List<TaxReportPensionByAge> taxReportPensionByAges;


	public TaxReportInsured() {
	}

	public long getInsuredSequence() {
		return this.insuredSequence;
	}

	public void setInsuredSequence(long insuredSequence) {
		this.insuredSequence = insuredSequence;
	}

	public String getAppartmentNumber() {
		return this.appartmentNumber;
	}

	public void setAppartmentNumber(String appartmentNumber) {
		this.appartmentNumber = appartmentNumber;
	}

	public BigDecimal getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(BigDecimal birthDate) {
		this.birthDate = birthDate;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
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

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getLossOfWorkingCapacity() {
		return this.lossOfWorkingCapacity;
	}

	public void setLossOfWorkingCapacity(BigDecimal lossOfWorkingCapacity) {
		this.lossOfWorkingCapacity = lossOfWorkingCapacity;
	}

	public String getMaritialStatus() {
		return this.maritialStatus;
	}

	public void setMaritialStatus(String maritialStatus) {
		this.maritialStatus = maritialStatus;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPob() {
		return this.pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public BigDecimal getRedemptionSum() {
		return this.redemptionSum;
	}

	public void setRedemptionSum(BigDecimal redemptionSum) {
		this.redemptionSum = redemptionSum;
	}

	
	public BigDecimal getRiskSum() {
		return this.riskSum;
	}

	public void setRiskSum(BigDecimal riskSum) {
		this.riskSum = riskSum;
	}

	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@XmlElementWrapper
	@XmlElement( name="taxReportPolicy",type=TaxReportPolicy.class)
	public List<TaxReportPolicy> getTaxReportPolicies() {
		return this.taxReportPolicies;
	}

	public void setTaxReportPolicies(List<TaxReportPolicy> taxReportPolicies) {
		this.taxReportPolicies = taxReportPolicies;
	}

	public TaxReportPolicy addTaxReportPolicy(TaxReportPolicy taxReportPolicy) {
		getTaxReportPolicies().add(taxReportPolicy);
		taxReportPolicy.setTaxReportInsured(this);

		return taxReportPolicy;
	}

	public TaxReportPolicy removeTaxReportPolicy(TaxReportPolicy taxReportPolicy) {
		getTaxReportPolicies().remove(taxReportPolicy);
		taxReportPolicy.setTaxReportInsured(null);

		return taxReportPolicy;
	}

	public BigDecimal getAppendixClause45Aa() {
		return appendixClause45Aa;
	}

	public void setAppendixClause45Aa(BigDecimal appendixClause45Aa) {
		this.appendixClause45Aa = appendixClause45Aa;
	}

	public BigDecimal getAppendixClause45Ab() {
		return appendixClause45Ab;
	}

	public void setAppendixClause45Ab(BigDecimal appendixClause45Ab) {
		this.appendixClause45Ab = appendixClause45Ab;
	}

	public BigDecimal getAppendixClause47() {
		return appendixClause47;
	}

	public void setAppendixClause47(BigDecimal appendixClause47) {
		this.appendixClause47 = appendixClause47;
	}

	public BigDecimal getAppendixClauseLowc() {
		return appendixClauseLowc;
	}

	public void setAppendixClauseLowc(BigDecimal appendixClauseLowc) {
		this.appendixClauseLowc = appendixClauseLowc;
	}

	public BigDecimal getAppendixClauseOther() {
		return appendixClauseOther;
	}

	public void setAppendixClauseOther(BigDecimal appendixClauseOther) {
		this.appendixClauseOther = appendixClauseOther;
	}

	public BigDecimal getConcentrationPageSwitch() {
		return concentrationPageSwitch;
	}

	public void setConcentrationPageSwitch(BigDecimal concentrationPageSwitch) {
		this.concentrationPageSwitch = concentrationPageSwitch;
	}

	public BigDecimal getDepositSumSwitch() {
		return depositSumSwitch;
	}

	public void setDepositSumSwitch(BigDecimal depositSumSwitch) {
		this.depositSumSwitch = depositSumSwitch;
	}

	public BigDecimal getLowcDepositSwitch() {
		return lowcDepositSwitch;
	}

	public void setLowcDepositSwitch(BigDecimal lowcDepositSwitch) {
		this.lowcDepositSwitch = lowcDepositSwitch;
	}

	public BigDecimal getLowcShowSwitch() {
		return lowcShowSwitch;
	}

	public void setLowcShowSwitch(BigDecimal lowcShowSwitch) {
		this.lowcShowSwitch = lowcShowSwitch;
	}

	public TaxReportDepositForInsured getTaxReportDepositForInsured() {
		return taxReportDepositForInsured;
	}

	public void setTaxReportDepositForInsured(TaxReportDepositForInsured taxReportDepositForInsured) {
		this.taxReportDepositForInsured = taxReportDepositForInsured;
	}

	@XmlElementWrapper
	@XmlElement( name="taxReportPensionByAge",type=TaxReportPensionByAge.class)
	public List<TaxReportPensionByAge> getTaxReportPensionByAges() {
		return taxReportPensionByAges;
	}

	public void setTaxReportPensionByAges(List<TaxReportPensionByAge> taxReportPensionByAges) {
		this.taxReportPensionByAges = taxReportPensionByAges;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	public BigDecimal getStatusForReport() {
		return statusForReport;
	}

	public void setStatusForReport(BigDecimal statusForReport) {
		this.statusForReport = statusForReport;
	}

	public BigDecimal getAttachmentLocation() {
		return attachmentLocation;
	}

	public void setAttachmentLocation(BigDecimal attachmentLocation) {
		this.attachmentLocation = attachmentLocation;
	}

	public BigDecimal getBeneficiaryUpdateDat() {
		return beneficiaryUpdateDat;
	}

	public void setBeneficiaryUpdateDat(BigDecimal beneficiaryUpdateDat) {
		this.beneficiaryUpdateDat = beneficiaryUpdateDat;
	}


	public BigDecimal getErrorReasonCode() {
		return errorReasonCode;
	}

	public void setErrorReasonCode(BigDecimal errorReasonCode) {
		this.errorReasonCode = errorReasonCode;
	}

}