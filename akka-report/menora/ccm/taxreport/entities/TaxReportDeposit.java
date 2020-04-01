package menora.ccm.taxreport.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TAX_REPORT_DEPOSIT database table.
 * 
 */
@Entity
@Table(name="TAX_REPORT_DEPOSIT")
@NamedQuery(name="TaxReportDeposit.findAll", query="SELECT t FROM TaxReportDeposit t")
public class TaxReportDeposit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DEPOSIT_SEQUENCE")
	private long depositSequence;

	@Column(name="ALL_DEPOSIT_SUM")
	private BigDecimal allDepositSum;

	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="DEPOSIT_DATE")
	private BigDecimal depositDate;

	@Column(name="EMPLOYEE_BENEFIT")
	private BigDecimal employeeBenefit;

	@Column(name="EMPLOYER_BENEFIT")
	private BigDecimal employerBenefit;

	@Column(name="EXECUTION_NUMBER")
	private BigDecimal executionNumber;

	@Column(name="LOSS_OF_WORKING_CAPACITY")
	private BigDecimal lossOfWorkingCapacity;

	private BigDecimal others;

	@Column(name="POLICY_INDIVIDUAL_SENIOR_EMPLOYEE")
	private BigDecimal policyIndividualSeniorEmployee;

	
	@Column(name="SALARY_MONTH")
	private BigDecimal salaryMonth;

	@Column(name="PREVIOUS_YEAR_SWITCH")
	private BigDecimal previousYearSwitch;

	@Column(name="SALARY_SUM")
	private BigDecimal salarySum;

	private BigDecimal severance;

	//bi-directional many-to-one association to TaxReportPolicy
	
	@ManyToOne
	@JoinColumn(name="POLICY_SEQUENCE")
	private TaxReportPolicy taxReportPolicy;

	public TaxReportDeposit() {
	}

	public long getDepositSequence() {
		return this.depositSequence;
	}

	public void setDepositSequence(long depositSequence) {
		this.depositSequence = depositSequence;
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

	public BigDecimal getDepositDate() {
		return this.depositDate;
	}

	public void setDepositDate(BigDecimal depositDate) {
		this.depositDate = depositDate;
	}

	public BigDecimal getEmployeeBenefit() {
		return this.employeeBenefit;
	}

	public void setEmployeeBenefit(BigDecimal employeeBenefit) {
		this.employeeBenefit = employeeBenefit;
	}

	public BigDecimal getEmployerBenefit() {
		return this.employerBenefit;
	}

	public void setEmployerBenefit(BigDecimal employerBenefit) {
		this.employerBenefit = employerBenefit;
	}

	public BigDecimal getLossOfWorkingCapacity() {
		return this.lossOfWorkingCapacity;
	}

	public void setLossOfWorkingCapacity(BigDecimal lossOfWorkingCapacity) {
		this.lossOfWorkingCapacity = lossOfWorkingCapacity;
	}

	public BigDecimal getOthers() {
		return this.others;
	}

	public void setOthers(BigDecimal others) {
		this.others = others;
	}

	public BigDecimal getSalaryMonth() {
		return this.salaryMonth;
	}

	public void setSalaryMonth(BigDecimal salaryMonth) {
		this.salaryMonth = salaryMonth;
	}

	public BigDecimal getSalarySum() {
		return this.salarySum;
	}

	public void setSalarySum(BigDecimal salarySum) {
		this.salarySum = salarySum;
	}

	public BigDecimal getSeverance() {
		return this.severance;
	}

	public void setSeverance(BigDecimal severance) {
		this.severance = severance;
	}

	@XmlTransient
	public TaxReportPolicy getTaxReportPolicy() {
		return this.taxReportPolicy;
	}

	public void setTaxReportPolicy(TaxReportPolicy taxReportPolicy) {
		this.taxReportPolicy = taxReportPolicy;
	}

	public BigDecimal getPreviousYearSwitch() {
		return previousYearSwitch;
	}

	public void setPreviousYearSwitch(BigDecimal previousYearSwitch) {
		this.previousYearSwitch = previousYearSwitch;
	}

	public BigDecimal getAllDepositSum() {
		return allDepositSum;
	}

	public void setAllDepositSum(BigDecimal allDepositSum) {
		this.allDepositSum = allDepositSum;
	}

	public BigDecimal getPolicyIndividualSeniorEmployee() {
		return policyIndividualSeniorEmployee;
	}

	public void setPolicyIndividualSeniorEmployee(BigDecimal policyIndividualSeniorEmployee) {
		this.policyIndividualSeniorEmployee = policyIndividualSeniorEmployee;
	}

	public BigDecimal getExecutionNumber() {
		return executionNumber;
	}

	public void setExecutionNumber(BigDecimal executionNumber) {
		this.executionNumber = executionNumber;
	}

	
	

}