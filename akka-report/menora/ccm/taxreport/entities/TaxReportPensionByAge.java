package menora.ccm.taxreport.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.math.BigDecimal;


/**
 * The persistent class for the TAX_REPORT_PENSION_BY_AGE database table.
 * 
 */
@Entity
@Table(name="TAX_REPORT_PENSION_BY_AGE")
@NamedQuery(name="TaxReportPensionByAge.findAll", query="SELECT t FROM TaxReportPensionByAge t")
public class TaxReportPensionByAge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PENSION_SEQUENCE")
	private long pensionSequence;
	
	@Column(name="EXECUTION_NUMBER")
	private BigDecimal executionNumber;

	@Column(name="MONTHLY_PENSION_ON_RETIREMENT")
	private BigDecimal monthlyPensionOnRetirement;

	@Column(name="RETIREMENT_AGE")
	private BigDecimal retirementAge;

	//bi-directional many-to-one association to TaxReportInsured
	@ManyToOne
	@JoinColumn(name="INSURED_SEQUENCE")
	private TaxReportInsured taxReportInsured;

	public TaxReportPensionByAge() {
	}

	public BigDecimal getMonthlyPensionOnRetirement() {
		return this.monthlyPensionOnRetirement;
	}

	public void setMonthlyPensionOnRetirement(BigDecimal monthlyPensionOnRetirement) {
		this.monthlyPensionOnRetirement = monthlyPensionOnRetirement;
	}

	public BigDecimal getRetirementAge() {
		return this.retirementAge;
	}

	public void setRetirementAge(BigDecimal retirementAge) {
		this.retirementAge = retirementAge;
	}

	@XmlTransient
	public TaxReportInsured getTaxReportInsured() {
		return this.taxReportInsured;
	}

	public void setTaxReportInsured(TaxReportInsured taxReportInsured) {
		this.taxReportInsured = taxReportInsured;
	}

	public long getPensionSequence() {
		return pensionSequence;
	}

	public void setPensionSequence(long pensionSequence) {
		this.pensionSequence = pensionSequence;
	}

	public BigDecimal getExecutionNumber() {
		return executionNumber;
	}

	public void setExecutionNumber(BigDecimal executionNumber) {
		this.executionNumber = executionNumber;
	}

}