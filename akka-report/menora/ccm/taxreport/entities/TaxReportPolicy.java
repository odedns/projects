package menora.ccm.taxreport.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="TAX_REPORT_POLICY")
@NamedQuery(name="TaxReportPolicy.findAll", query="SELECT t FROM TaxReportPolicy t")
public class TaxReportPolicy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="POLICY_SEQUENCE")
	private long policySequence;

	@Column(name="AGENT_ID")
	private int agentId;

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

	@Column(name="AVERAGE_DEPOSIT_MANAGEMENT_FEE")
	private BigDecimal averageDepositManagementFee;

	@Column(name="AVERAGE_SAVING_MANAGEMENT_FEE")
	private BigDecimal averageSavingManagementFee;
	
	@Column(name="CHANGEABLE_MANAGEMENT_FEE")
	private BigDecimal changeableManagementFee;


	@Column(name="COMMENT_TYPE_SWITCH")
	private BigDecimal commentTypeSwitch;

	
	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="DEPOSIT_MANAGEMENT_FEE")
	private BigDecimal depositManagementFee;

	@Column(name="DEPOSIT_SUM")
	private BigDecimal depositSum;

	@Column(name="END_DATE")
	private BigDecimal endDate;

	@Column(name="END_YEAR_PAID_UP_VALUE")
	private BigDecimal endYearPaidUpValue;

	@Column(name="ERROR_REASON_CODE")
	private BigDecimal errorReasonCode;

	@Column(name="EXCEPTIONAL_OWNER_FULL_NAME")
	private String exceptionalOwnerFullName;

	@Column(name="EXECUTION_NUMBER")
	private BigDecimal executionNumber;

	@Column(name="FIXED_MANAGEMENT_FEE")
	private BigDecimal fixedManagementFee;

	@Column(name="HOLDER_FULL_NAME")
	private String holderFullName;
	
	@Column(name="INSURANCE_TYPE")
	private BigDecimal insuranceType;

	@Column(name="INTEREST_RATE")
	private BigDecimal interestRate;
	
	@Column(name="INVESTMENT_FUND_ADIF")
	private String investmentFundAdif;

	@Column(name="INVESTMENT_MANAGE_EXPENSES")
	private BigDecimal investmentManageExpenses;

	@Column(name="LAST_YEAR_REDMPTION_SUM")
	private BigDecimal lastYearRedmptionSum;
	
	@Column(name="LOSS_OF_WORKING_CAPACITY")
	private BigDecimal lossOfWorkingCapacity;

	@Column(name="LOSS_OF_WORKING_CAPACITY_PAY_RELEASE")
	private BigDecimal lossOfWorkingCapacityPayRelease;
	
	@Column(name="LOSS_OF_WORKING_CAPACITY_PAY_RELEASE_SWITCH")
	private BigDecimal lossOfWorkingCapacityPayReleaseSwitch;

	@Column(name="LOWC_SHOW_SWITCH")
	private BigDecimal lowcShowSwitch;

	@Column(name="MANAGE_FEE_TITLE_CONDITION")
	private BigDecimal manageFeeTitleCondition;

	@Column(name="MOBILITY_IN_SUM")
	private BigDecimal mobilityInSum;

	@Column(name="MOBILITY_OUT_SUM")
	private BigDecimal mobilityOutSum;

	@Column(name="MONTHLY_PENSION_ON_RETIREMENT")
	private BigDecimal monthlyPensionOnRetirement;

	@Column(name="PAYER_TYPE")
	private String payerType;

	@Column(name="POLICY_BALANCE_END_YEAR")
	private BigDecimal policyBalanceEndYear;

	@Column(name="POLICY_BALANCE_START_YEAR")
	private BigDecimal policyBalanceStartYear;

	@Column(name="POLICY_ENCODING")
	private String policyEncoding;
	
	@Column(name="POLICY_NAME")
	private String policyName;

	@Column(name="POLICY_NUMBER")
	private int policyNumber;

	@Column(name="POLICY_SUB_TYPE")
	private BigDecimal policySubType;

	@Column(name="POLICY_TYPE")
	private String policyType;

	@Column(name="PROFITS_LOSS_SUM")
	private BigDecimal profitsLossSum;

	@Column(name="REDEMPTION_SUM")
	private BigDecimal redemptionSum;
	
	@Column(name="REDEMPTION_WITHDRAWAL_PENALTY")
	private BigDecimal redemptionWithdrawalPenalty;

	@Column(name="RETIREMENT_AGE")
	private BigDecimal retirementAge;

	@Column(name="RISK_SUM")
	private BigDecimal riskSum;

	@Column(name="SAVING_MANAGEMENT_FEE")
	private BigDecimal savingManagementFee;

	@Column(name="START_DATE")
	private BigDecimal startDate;

	@Column(name="START_YEAR_PAID_UP_VALUE")
	private BigDecimal startYearPaidUpValue;

	@Column(name="START_YEAR_REDMPTION_SUM")
	private BigDecimal startYearRedmptionSum;

	@Column(name="STATUS_FOR_REPORT")
	private BigDecimal statusForReport;

	@Column(name="TABLE_B_SWITCH")
	private BigDecimal tableBSwitch;

	@Column(name="TABLE_G_SWITCH")
	private BigDecimal tableGSwitch;

	@Column(name="TEXT_TYPE_WITHDRAWAL_MOBILITY_SWITCH")
	private BigDecimal textTypeWithdrawalMobilitySwitch;

	@Column(name="TEXT_TYPE_WITHDRAWAL_REDEMPTION_SWITCH")
	private BigDecimal textTypeWithdrawalRedemptionSwitch;

	@Column(name="WITHDRAWAL_PENALTY")
	private BigDecimal withdrawalPenalty;

	@Column(name="WITHDRAWAL_SUM")
	private BigDecimal withdrawalSum;

	@Column(name="YEARLY_MANAGEMENT_FEE")
	private BigDecimal yearlyManagementFee;

	@Column(name="YIELD_COEFFICIENT")
	private BigDecimal yieldCoefficient;

	//bi-directional many-to-one association to TaxReportInsured
	
	@ManyToOne
	@JoinColumn(name="INSURED_SEQUENCE")
	private TaxReportInsured taxReportInsured;
	
	//bi-directional many-to-one association to TaxReportDeposit
	@OneToMany(mappedBy="taxReportPolicy")
	private List<TaxReportDeposit> taxReportDeposits;

	//bi-directional many-to-one association to TaxReportCover
	@OneToMany(mappedBy="taxReportPolicy")
	private List<TaxReportCover> taxReportCovers;

	public TaxReportPolicy() {
	}

	public long getPolicySequence() {
		return this.policySequence;
	}

	public void setPolicySequence(long policySequence) {
		this.policySequence = policySequence;
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

	public BigDecimal getDepositManagementFee() {
		return this.depositManagementFee;
	}

	public void setDepositManagementFee(BigDecimal depositManagementFee) {
		this.depositManagementFee = depositManagementFee;
	}

	public BigDecimal getEndDate() {
		return this.endDate;
	}

	public void setEndDate(BigDecimal endDate) {
		this.endDate = endDate;
	}

	public String getHolderFullName() {
		return this.holderFullName;
	}

	public void setHolderFullName(String holderFullName) {
		this.holderFullName = holderFullName;
	}

	
	public BigDecimal getInterestRate() {
		return this.interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public BigDecimal getLossOfWorkingCapacity() {
		return this.lossOfWorkingCapacity;
	}

	public void setLossOfWorkingCapacity(BigDecimal lossOfWorkingCapacity) {
		this.lossOfWorkingCapacity = lossOfWorkingCapacity;
	}

	public BigDecimal getLossOfWorkingCapacityPayRelease() {
		return this.lossOfWorkingCapacityPayRelease;
	}

	public void setLossOfWorkingCapacityPayRelease(BigDecimal lossOfWorkingCapacityPayRelease) {
		this.lossOfWorkingCapacityPayRelease = lossOfWorkingCapacityPayRelease;
	}

	public BigDecimal getMobilityInSum() {
		return this.mobilityInSum;
	}

	public void setMobilityInSum(BigDecimal mobilityInSum) {
		this.mobilityInSum = mobilityInSum;
	}

	public BigDecimal getMobilityOutSum() {
		return this.mobilityOutSum;
	}

	public void setMobilityOutSum(BigDecimal mobilityOutSum) {
		this.mobilityOutSum = mobilityOutSum;
	}

	public BigDecimal getMonthlyPensionOnRetirement() {
		return this.monthlyPensionOnRetirement;
	}

	public void setMonthlyPensionOnRetirement(BigDecimal monthlyPensionOnRetirement) {
		this.monthlyPensionOnRetirement = monthlyPensionOnRetirement;
	}

	public String getPayerType() {
		return this.payerType;
	}

	public void setPayerType(String payerType) {
		this.payerType = payerType;
	}

	public BigDecimal getPolicyBalanceEndYear() {
		return this.policyBalanceEndYear;
	}

	public void setPolicyBalanceEndYear(BigDecimal policyBalanceEndYear) {
		this.policyBalanceEndYear = policyBalanceEndYear;
	}

	public BigDecimal getPolicyBalanceStartYear() {
		return this.policyBalanceStartYear;
	}

	public void setPolicyBalanceStartYear(BigDecimal policyBalanceStartYear) {
		this.policyBalanceStartYear = policyBalanceStartYear;
	}

	public String getPolicyName() {
		return this.policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getPolicyType() {
		return this.policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public BigDecimal getProfitsLossSum() {
		return this.profitsLossSum;
	}

	public void setProfitsLossSum(BigDecimal profitsLossSum) {
		this.profitsLossSum = profitsLossSum;
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

	public BigDecimal getSavingManagementFee() {
		return this.savingManagementFee;
	}

	public void setSavingManagementFee(BigDecimal savingManagementFee) {
		this.savingManagementFee = savingManagementFee;
	}

	public BigDecimal getStartDate() {
		return this.startDate;
	}

	public void setStartDate(BigDecimal startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getWithdrawalPenalty() {
		return this.withdrawalPenalty;
	}

	public void setWithdrawalPenalty(BigDecimal withdrawalPenalty) {
		this.withdrawalPenalty = withdrawalPenalty;
	}

	public BigDecimal getWithdrawalSum() {
		return this.withdrawalSum;
	}

	public void setWithdrawalSum(BigDecimal withdrawalSum) {
		this.withdrawalSum = withdrawalSum;
	}

	public BigDecimal getYearlyManagementFee() {
		return this.yearlyManagementFee;
	}

	public void setYearlyManagementFee(BigDecimal yearlyManagementFee) {
		this.yearlyManagementFee = yearlyManagementFee;
	}

	public BigDecimal getYieldCoefficient() {
		return this.yieldCoefficient;
	}

	public void setYieldCoefficient(BigDecimal yieldCoefficient) {
		this.yieldCoefficient = yieldCoefficient;
	}

	@XmlTransient
	public TaxReportInsured getTaxReportInsured() {
		return this.taxReportInsured;
	}

	public void setTaxReportInsured(TaxReportInsured taxReportInsured) {
		this.taxReportInsured = taxReportInsured;
	}

	@XmlElementWrapper
	@XmlElement(name="taxReportDeposit", type=TaxReportDeposit.class)
	public List<TaxReportDeposit> getTaxReportDeposits() {
		return this.taxReportDeposits;
	}

	public void setTaxReportDeposits(List<TaxReportDeposit> taxReportDeposits) {
		this.taxReportDeposits = taxReportDeposits;
	}

	public TaxReportDeposit addTaxReportDeposit(TaxReportDeposit taxReportDeposit) {
		getTaxReportDeposits().add(taxReportDeposit);
		taxReportDeposit.setTaxReportPolicy(this);

		return taxReportDeposit;
	}

	public TaxReportDeposit removeTaxReportDeposit(TaxReportDeposit taxReportDeposit) {
		getTaxReportDeposits().remove(taxReportDeposit);
		taxReportDeposit.setTaxReportPolicy(null);

		return taxReportDeposit;
	}

	@XmlElementWrapper
	@XmlElement(name="taxReportCover", type=TaxReportCover.class)
	public List<TaxReportCover> getTaxReportCovers() {
		return this.taxReportCovers;
	}

	public void setTaxReportCovers(List<TaxReportCover> taxReportCovers) {
		this.taxReportCovers = taxReportCovers;
	}

	public TaxReportCover addTaxReportCover(TaxReportCover taxReportCover) {
		getTaxReportCovers().add(taxReportCover);
		taxReportCover.setTaxReportPolicy(this);

		return taxReportCover;
	}

	public TaxReportCover removeTaxReportCover(TaxReportCover taxReportCover) {
		getTaxReportCovers().remove(taxReportCover);
		taxReportCover.setTaxReportPolicy(null);

		return taxReportCover;
	}

	public BigDecimal getAverageDepositManagementFee() {
		return averageDepositManagementFee;
	}

	public void setAverageDepositManagementFee(BigDecimal averageDepositManagementFee) {
		this.averageDepositManagementFee = averageDepositManagementFee;
	}

	public BigDecimal getAverageSavingManagementFee() {
		return averageSavingManagementFee;
	}

	public void setAverageSavingManagementFee(BigDecimal averageSavingManagementFee) {
		this.averageSavingManagementFee = averageSavingManagementFee;
	}

	public BigDecimal getCommentTypeSwitch() {
		return commentTypeSwitch;
	}

	public void setCommentTypeSwitch(BigDecimal commentTypeSwitch) {
		this.commentTypeSwitch = commentTypeSwitch;
	}

	public BigDecimal getDepositSum() {
		return depositSum;
	}

	public void setDepositSum(BigDecimal depositSum) {
		this.depositSum = depositSum;
	}

	public BigDecimal getInvestmentManageExpenses() {
		return investmentManageExpenses;
	}

	public void setInvestmentManageExpenses(BigDecimal investmentManageExpenses) {
		this.investmentManageExpenses = investmentManageExpenses;
	}

	public BigDecimal getLossOfWorkingCapacityPayReleaseSwitch() {
		return lossOfWorkingCapacityPayReleaseSwitch;
	}

	public void setLossOfWorkingCapacityPayReleaseSwitch(BigDecimal lossOfWorkingCapacityPayReleaseSwitch) {
		this.lossOfWorkingCapacityPayReleaseSwitch = lossOfWorkingCapacityPayReleaseSwitch;
	}

	public BigDecimal getLowcShowSwitch() {
		return lowcShowSwitch;
	}

	public void setLowcShowSwitch(BigDecimal lowcShowSwitch) {
		this.lowcShowSwitch = lowcShowSwitch;
	}

	public String getPolicyEncoding() {
		return policyEncoding;
	}

	public void setPolicyEncoding(String policyEncoding) {
		this.policyEncoding = policyEncoding;
	}

	public BigDecimal getPolicySubType() {
		return policySubType;
	}

	public void setPolicySubType(BigDecimal policySubType) {
		this.policySubType = policySubType;
	}

	public BigDecimal getRetirementAge() {
		return retirementAge;
	}

	public void setRetirementAge(BigDecimal retirementAge) {
		this.retirementAge = retirementAge;
	}

	public BigDecimal getTableBSwitch() {
		return tableBSwitch;
	}

	public void setTableBSwitch(BigDecimal tableBSwitch) {
		this.tableBSwitch = tableBSwitch;
	}

	public BigDecimal getTableGSwitch() {
		return tableGSwitch;
	}

	public void setTableGSwitch(BigDecimal tableGSwitch) {
		this.tableGSwitch = tableGSwitch;
	}

	public BigDecimal getTextTypeWithdrawalMobilitySwitch() {
		return textTypeWithdrawalMobilitySwitch;
	}

	public void setTextTypeWithdrawalMobilitySwitch(BigDecimal textTypeWithdrawalMobilitySwitch) {
		this.textTypeWithdrawalMobilitySwitch = textTypeWithdrawalMobilitySwitch;
	}

	public BigDecimal getChangeableManagementFee() {
		return changeableManagementFee;
	}

	public void setChangeableManagementFee(BigDecimal changeableManagementFee) {
		this.changeableManagementFee = changeableManagementFee;
	}

	public BigDecimal getEndYearPaidUpValue() {
		return endYearPaidUpValue;
	}

	public void setEndYearPaidUpValue(BigDecimal endYearPaidUpValue) {
		this.endYearPaidUpValue = endYearPaidUpValue;
	}

	public BigDecimal getErrorReasonCode() {
		return errorReasonCode;
	}

	public void setErrorReasonCode(BigDecimal errorReasonCode) {
		this.errorReasonCode = errorReasonCode;
	}

		
	public BigDecimal getFixedManagementFee() {
		return fixedManagementFee;
	}

	public void setFixedManagementFee(BigDecimal fixedManagementFee) {
		this.fixedManagementFee = fixedManagementFee;
	}

	public BigDecimal getLastYearRedmptionSum() {
		return lastYearRedmptionSum;
	}

	public void setLastYearRedmptionSum(BigDecimal lastYearRedmptionSum) {
		this.lastYearRedmptionSum = lastYearRedmptionSum;
	}

	public BigDecimal getManageFeeTitleCondition() {
		return manageFeeTitleCondition;
	}

	public void setManageFeeTitleCondition(BigDecimal manageFeeTitleCondition) {
		this.manageFeeTitleCondition = manageFeeTitleCondition;
	}

	public BigDecimal getStartYearPaidUpValue() {
		return startYearPaidUpValue;
	}

	public void setStartYearPaidUpValue(BigDecimal startYearPaidUpValue) {
		this.startYearPaidUpValue = startYearPaidUpValue;
	}

	public BigDecimal getStartYearRedmptionSum() {
		return startYearRedmptionSum;
	}

	public void setStartYearRedmptionSum(BigDecimal startYearRedmptionSum) {
		this.startYearRedmptionSum = startYearRedmptionSum;
	}

	public BigDecimal getStatusForReport() {
		return statusForReport;
	}

	public void setStatusForReport(BigDecimal statusForReport) {
		this.statusForReport = statusForReport;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
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

	public BigDecimal getExecutionNumber() {
		return executionNumber;
	}

	public void setExecutionNumber(BigDecimal executionNumber) {
		this.executionNumber = executionNumber;
	}

	public int getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(int policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getExceptionalOwnerFullName() {
		return exceptionalOwnerFullName;
	}

	public void setExceptionalOwnerFullName(String exceptionalOwnerFullName) {
		this.exceptionalOwnerFullName = exceptionalOwnerFullName;
	}

	public BigDecimal getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(BigDecimal insuranceType) {
		this.insuranceType = insuranceType;
	}

	public BigDecimal getRedemptionWithdrawalPenalty() {
		return redemptionWithdrawalPenalty;
	}

	public void setRedemptionWithdrawalPenalty(BigDecimal redemptionWithdrawalPenalty) {
		this.redemptionWithdrawalPenalty = redemptionWithdrawalPenalty;
	}

	public BigDecimal getTextTypeWithdrawalRedemptionSwitch() {
		return textTypeWithdrawalRedemptionSwitch;
	}

	public void setTextTypeWithdrawalRedemptionSwitch(BigDecimal textTypeWithdrawalRedemptionSwitch) {
		this.textTypeWithdrawalRedemptionSwitch = textTypeWithdrawalRedemptionSwitch;
	}

	public String getInvestmentFundAdif() {
		return investmentFundAdif;
	}

	public void setInvestmentFundAdif(String investmentFundAdif) {
		this.investmentFundAdif = investmentFundAdif;
	}

}