package menora.ccm.taxreport.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.math.BigDecimal;


/**
 * The persistent class for the TAX_REPORT_DEPOSIT_FOR_INSURED database table.
 * 
 */
@Entity
@Table(name="TAX_REPORT_DEPOSIT_FOR_INSURED")
@NamedQuery(name="TaxReportDepositForInsured.findAll", query="SELECT t FROM TaxReportDepositForInsured t")
public class TaxReportDepositForInsured implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DEPOSIT_FOR_INSURED_SEQUENCE")
	private long depositForInsuredSequence;
	@Column(name="ALL_DEPOSIT_SUM")
	private BigDecimal allDepositSum;

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

	/*
	@Column(name="POLICY_INDIVIDUAL_SENIOR_EMPLOYEE")
	private BigDecimal policyIndividualSeniorEmployee;
*/
	@Column(name="PREVIOUS_YEAR_SWITCH")
	private BigDecimal previousYearSwitch;

	@Column(name="SALARY_MONTH")
	private BigDecimal salaryMonth;

	private BigDecimal severance;

	
	@OneToOne
	@JoinColumn(name="INSURED_SEQUENCE")
	private TaxReportInsured taxReportInsured;

	public TaxReportDepositForInsured() {
	}

	public BigDecimal getAllDepositSum() {
		return this.allDepositSum;
	}

	public void setAllDepositSum(BigDecimal allSum) {
		this.allDepositSum = allSum;
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

	public BigDecimal getPreviousYearSwitch() {
		return this.previousYearSwitch;
	}

	public void setPreviousYearSwitch(BigDecimal previousYearSwitch) {
		this.previousYearSwitch = previousYearSwitch;
	}

	public BigDecimal getSalaryMonth() {
		return this.salaryMonth;
	}

	public void setSalaryMonth(BigDecimal salaryMonth) {
		this.salaryMonth = salaryMonth;
	}

	public BigDecimal getSeverance() {
		return this.severance;
	}

	public void setSeverance(BigDecimal severance) {
		this.severance = severance;
	}
	
	@XmlTransient
	public TaxReportInsured getTaxReportInsured() {
		return this.taxReportInsured;
	}

	public void setTaxReportInsured(TaxReportInsured taxReportInsured) {
		this.taxReportInsured = taxReportInsured;
	}

	public long getDepositForInsuredSequence() {
		return depositForInsuredSequence;
	}

	public void setDepositForInsuredSequence(long depositForInsuredSequence) {
		this.depositForInsuredSequence = depositForInsuredSequence;
	}

	public BigDecimal getExecutionNumber() {
		return executionNumber;
	}

	public void setExecutionNumber(BigDecimal executionNumber) {
		this.executionNumber = executionNumber;
	}

	/*
	public BigDecimal getPolicyIndividualSeniorEmployee() {
		return policyIndividualSeniorEmployee;
	}

	public void setPolicyIndividualSeniorEmployee(BigDecimal policyIndividualSeniorEmployee) {
		this.policyIndividualSeniorEmployee = policyIndividualSeniorEmployee;
	}
*/
}