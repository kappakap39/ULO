package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

public class KBankPayrollDataM implements Serializable,Cloneable{
	
	protected BigDecimal averageIncome12Month;
    protected BigDecimal averageIncome3Month;
    protected BigDecimal averageIncome6Month;
    protected String ccPayrollFlag;
    protected String cisNo;
    protected String companyName;
    protected String cycleCut;
    protected BigDecimal grossIncome12Month;
    protected BigDecimal grossIncome3Month;
    protected BigDecimal grossIncome6Month;
    protected String idNo;
    protected BigDecimal incomeMonth1;
    protected BigDecimal incomeMonth2;
    protected BigDecimal incomeMonth3;
    protected BigDecimal incomeMonth4;
    protected BigDecimal incomeMonth5;
    protected BigDecimal incomeMonth6;
    protected BigDecimal incomeTotal;
    protected String kecPayrollFlag;
    protected String paymentAccountNo;
    protected String paymentChannel;
    protected XMLGregorianCalendar positionDate;
    protected String projectCode;
    protected BigDecimal pvf12Month;
    protected BigDecimal pvf3Month;
    protected BigDecimal pvf6Month;
    protected Integer salaryDate;
    protected BigDecimal sso;
    protected String thFirstName;
    protected String thLastName;
    protected String welfarePayrollFlag;
    
    public KBankPayrollDataM(){
		super();
	}
    
	public BigDecimal getAverageIncome12Month() {
		return averageIncome12Month;
	}
	public void setAverageIncome12Month(BigDecimal averageIncome12Month) {
		this.averageIncome12Month = averageIncome12Month;
	}
	public BigDecimal getAverageIncome3Month() {
		return averageIncome3Month;
	}
	public void setAverageIncome3Month(BigDecimal averageIncome3Month) {
		this.averageIncome3Month = averageIncome3Month;
	}
	public BigDecimal getAverageIncome6Month() {
		return averageIncome6Month;
	}
	public void setAverageIncome6Month(BigDecimal averageIncome6Month) {
		this.averageIncome6Month = averageIncome6Month;
	}
	public String getCcPayrollFlag() {
		return ccPayrollFlag;
	}
	public void setCcPayrollFlag(String ccPayrollFlag) {
		this.ccPayrollFlag = ccPayrollFlag;
	}
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCycleCut() {
		return cycleCut;
	}
	public void setCycleCut(String cycleCut) {
		this.cycleCut = cycleCut;
	}
	public BigDecimal getGrossIncome12Month() {
		return grossIncome12Month;
	}
	public void setGrossIncome12Month(BigDecimal grossIncome12Month) {
		this.grossIncome12Month = grossIncome12Month;
	}
	public BigDecimal getGrossIncome3Month() {
		return grossIncome3Month;
	}
	public void setGrossIncome3Month(BigDecimal grossIncome3Month) {
		this.grossIncome3Month = grossIncome3Month;
	}
	public BigDecimal getGrossIncome6Month() {
		return grossIncome6Month;
	}
	public void setGrossIncome6Month(BigDecimal grossIncome6Month) {
		this.grossIncome6Month = grossIncome6Month;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public BigDecimal getIncomeMonth1() {
		return incomeMonth1;
	}
	public void setIncomeMonth1(BigDecimal incomeMonth1) {
		this.incomeMonth1 = incomeMonth1;
	}
	public BigDecimal getIncomeMonth2() {
		return incomeMonth2;
	}
	public void setIncomeMonth2(BigDecimal incomeMonth2) {
		this.incomeMonth2 = incomeMonth2;
	}
	public BigDecimal getIncomeMonth3() {
		return incomeMonth3;
	}
	public void setIncomeMonth3(BigDecimal incomeMonth3) {
		this.incomeMonth3 = incomeMonth3;
	}
	public BigDecimal getIncomeMonth4() {
		return incomeMonth4;
	}
	public void setIncomeMonth4(BigDecimal incomeMonth4) {
		this.incomeMonth4 = incomeMonth4;
	}
	public BigDecimal getIncomeMonth5() {
		return incomeMonth5;
	}
	public void setIncomeMonth5(BigDecimal incomeMonth5) {
		this.incomeMonth5 = incomeMonth5;
	}
	public BigDecimal getIncomeMonth6() {
		return incomeMonth6;
	}
	public void setIncomeMonth6(BigDecimal incomeMonth6) {
		this.incomeMonth6 = incomeMonth6;
	}
	public BigDecimal getIncomeTotal() {
		return incomeTotal;
	}
	public void setIncomeTotal(BigDecimal incomeTotal) {
		this.incomeTotal = incomeTotal;
	}
	public String getKecPayrollFlag() {
		return kecPayrollFlag;
	}
	public void setKecPayrollFlag(String kecPayrollFlag) {
		this.kecPayrollFlag = kecPayrollFlag;
	}
	public String getPaymentAccountNo() {
		return paymentAccountNo;
	}
	public void setPaymentAccountNo(String paymentAccountNo) {
		this.paymentAccountNo = paymentAccountNo;
	}
	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	public XMLGregorianCalendar getPositionDate() {
		return positionDate;
	}
	public void setPositionDate(XMLGregorianCalendar positionDate) {
		this.positionDate = positionDate;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public BigDecimal getPvf12Month() {
		return pvf12Month;
	}
	public void setPvf12Month(BigDecimal pvf12Month) {
		this.pvf12Month = pvf12Month;
	}
	public BigDecimal getPvf3Month() {
		return pvf3Month;
	}
	public void setPvf3Month(BigDecimal pvf3Month) {
		this.pvf3Month = pvf3Month;
	}
	public BigDecimal getPvf6Month() {
		return pvf6Month;
	}
	public void setPvf6Month(BigDecimal pvf6Month) {
		this.pvf6Month = pvf6Month;
	}
	public Integer getSalaryDate() {
		return salaryDate;
	}
	public void setSalaryDate(Integer salaryDate) {
		this.salaryDate = salaryDate;
	}
	public BigDecimal getSso() {
		return sso;
	}
	public void setSso(BigDecimal sso) {
		this.sso = sso;
	}
	public String getThFirstName() {
		return thFirstName;
	}
	public void setThFirstName(String thFirstName) {
		this.thFirstName = thFirstName;
	}
	public String getThLastName() {
		return thLastName;
	}
	public void setThLastName(String thLastName) {
		this.thLastName = thLastName;
	}
	public String getWelfarePayrollFlag() {
		return welfarePayrollFlag;
	}
	public void setWelfarePayrollFlag(String welfarePayrollFlag) {
		this.welfarePayrollFlag = welfarePayrollFlag;
	}
}
