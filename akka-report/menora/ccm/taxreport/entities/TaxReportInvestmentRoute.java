package menora.ccm.taxreport.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TAX_REPORT_INVESTMENT_ROUTE database table.
 * 
 */
@Entity
@Table(name="TAX_REPORT_INVESTMENT_ROUTE")
@NamedQuery(name="TaxReportInvestmentRoute.findAll", query="SELECT t FROM TaxReportInvestmentRoute t")
public class TaxReportInvestmentRoute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="INVESTMENT_ROUTE_NUMBER")
	private long investmentRouteNumber;

	@Column(name="COVER_SEQUENCE")
	private BigDecimal coverSequence;
	
	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="CREATE_USER")
	private String createUser;
	
	@Column(name="EXECUTION_NUMBER")
	private BigDecimal executionNumber;

	@Column(name="ROUTE_CODE")
	private BigDecimal routeCode;

	@Column(name="ROUTE_NAME")
	private String routeName;

	@Column(name="ROUTE_TITLE_SWITCH")
	private BigDecimal routeTitleSwitch;

	@Column(name="YIELD_SUM")
	private BigDecimal yieldSum;

	//bi-directional many-to-one association to TaxReportCover
	
	@ManyToOne
	@JoinColumn(name="POLICY_SEQUENCE")
	private TaxReportPolicy taxReportPolicy;

	public TaxReportInvestmentRoute() {
	}

	public long getInvestmentRouteNumber() {
		return this.investmentRouteNumber;
	}

	public void setInvestmentRouteNumber(long investmentRouteNumber) {
		this.investmentRouteNumber = investmentRouteNumber;
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

	public String getRouteName() {
		return this.routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public BigDecimal getYieldSum() {
		return this.yieldSum;
	}

	public void setYieldSum(BigDecimal yieldSum) {
		this.yieldSum = yieldSum;
	}

	
	public BigDecimal getCoverSequence() {
		return coverSequence;
	}

	public void setCoverSequence(BigDecimal coverSequence) {
		this.coverSequence = coverSequence;
	}

	@XmlTransient
	public TaxReportPolicy getTaxReportPolicy() {
		return taxReportPolicy;
	}

	public void setTaxReportPolicy(TaxReportPolicy taxReportPolicy) {
		this.taxReportPolicy = taxReportPolicy;
	}

	public BigDecimal getExecutionNumber() {
		return executionNumber;
	}

	public void setExecutionNumber(BigDecimal executionNumber) {
		this.executionNumber = executionNumber;
	}

	public BigDecimal getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(BigDecimal routeCode) {
		this.routeCode = routeCode;
	}

	public BigDecimal getRouteTitleSwitch() {
		return routeTitleSwitch;
	}

	public void setRouteTitleSwitch(BigDecimal routeTitleSwitch) {
		this.routeTitleSwitch = routeTitleSwitch;
	}

}