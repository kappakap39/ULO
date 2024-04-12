//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.kasikornbank.manualfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import decisionservice_iib.Error;


/**
 * <p>Java class for PayrollResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayrollResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="averageIncome12Month" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="averageIncome3Month" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="averageIncome6Month" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ccPayrollFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cisNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="companyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cycleCut" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="grossIncome12Month" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="grossIncome3Month" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="grossIncome6Month" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incomeMonth1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incomeMonth2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incomeMonth3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incomeMonth4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incomeMonth5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incomeMonth6" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="incomeTotal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="kecPayrollFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentAccountNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentChannel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="positionDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="salaryDate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="projectCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pvf12Month" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pvf3Month" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pvf6Month" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="thFirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="thLastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="welfarePayrollFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="error" type="{http://DecisionService_IIB}error"/>
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayrollResponse", propOrder = {
    "averageIncome12Month",
    "averageIncome3Month",
    "averageIncome6Month",
    "ccPayrollFlag",
    "cisNo",
    "companyName",
    "cycleCut",
    "grossIncome12Month",
    "grossIncome3Month",
    "grossIncome6Month",
    "idNo",
    "incomeMonth1",
    "incomeMonth2",
    "incomeMonth3",
    "incomeMonth4",
    "incomeMonth5",
    "incomeMonth6",
    "incomeTotal",
    "kecPayrollFlag",
    "paymentAccountNo",
    "paymentChannel",
    "positionDate",
    "salaryDate",
    "projectCode",
    "pvf12Month",
    "pvf3Month",
    "pvf6Month",
    "sso",
    "thFirstName",
    "thLastName",
    "welfarePayrollFlag",
    "error",
    "transactionId"
})
public class PayrollResponse {

    @XmlElement(required = true)
    protected String averageIncome12Month;
    @XmlElement(required = true)
    protected String averageIncome3Month;
    @XmlElement(required = true)
    protected String averageIncome6Month;
    @XmlElement(required = true)
    protected String ccPayrollFlag;
    @XmlElement(required = true)
    protected String cisNo;
    @XmlElement(required = true)
    protected String companyName;
    @XmlElement(required = true)
    protected String cycleCut;
    @XmlElement(required = true)
    protected String grossIncome12Month;
    @XmlElement(required = true)
    protected String grossIncome3Month;
    @XmlElement(required = true)
    protected String grossIncome6Month;
    @XmlElement(required = true)
    protected String idNo;
    @XmlElement(required = true)
    protected String incomeMonth1;
    @XmlElement(required = true)
    protected String incomeMonth2;
    @XmlElement(required = true)
    protected String incomeMonth3;
    @XmlElement(required = true)
    protected String incomeMonth4;
    @XmlElement(required = true)
    protected String incomeMonth5;
    @XmlElement(required = true)
    protected String incomeMonth6;
    @XmlElement(required = true)
    protected String incomeTotal;
    @XmlElement(required = true)
    protected String kecPayrollFlag;
    @XmlElement(required = true)
    protected String paymentAccountNo;
    @XmlElement(required = true)
    protected String paymentChannel;
    @XmlElement(required = true)
    protected String positionDate;
    protected int salaryDate;
    @XmlElement(required = true)
    protected String projectCode;
    @XmlElement(required = true)
    protected String pvf12Month;
    @XmlElement(required = true)
    protected String pvf3Month;
    @XmlElement(required = true)
    protected String pvf6Month;
    @XmlElement(required = true)
    protected String sso;
    @XmlElement(required = true)
    protected String thFirstName;
    @XmlElement(required = true)
    protected String thLastName;
    @XmlElement(required = true)
    protected String welfarePayrollFlag;
    @XmlElement(required = true)
    protected Error error;
    protected String transactionId;

    /**
     * Gets the value of the averageIncome12Month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAverageIncome12Month() {
        return averageIncome12Month;
    }

    /**
     * Sets the value of the averageIncome12Month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAverageIncome12Month(String value) {
        this.averageIncome12Month = value;
    }

    /**
     * Gets the value of the averageIncome3Month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAverageIncome3Month() {
        return averageIncome3Month;
    }

    /**
     * Sets the value of the averageIncome3Month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAverageIncome3Month(String value) {
        this.averageIncome3Month = value;
    }

    /**
     * Gets the value of the averageIncome6Month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAverageIncome6Month() {
        return averageIncome6Month;
    }

    /**
     * Sets the value of the averageIncome6Month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAverageIncome6Month(String value) {
        this.averageIncome6Month = value;
    }

    /**
     * Gets the value of the ccPayrollFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcPayrollFlag() {
        return ccPayrollFlag;
    }

    /**
     * Sets the value of the ccPayrollFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcPayrollFlag(String value) {
        this.ccPayrollFlag = value;
    }

    /**
     * Gets the value of the cisNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCisNo() {
        return cisNo;
    }

    /**
     * Sets the value of the cisNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCisNo(String value) {
        this.cisNo = value;
    }

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the cycleCut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCycleCut() {
        return cycleCut;
    }

    /**
     * Sets the value of the cycleCut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCycleCut(String value) {
        this.cycleCut = value;
    }

    /**
     * Gets the value of the grossIncome12Month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrossIncome12Month() {
        return grossIncome12Month;
    }

    /**
     * Sets the value of the grossIncome12Month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrossIncome12Month(String value) {
        this.grossIncome12Month = value;
    }

    /**
     * Gets the value of the grossIncome3Month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrossIncome3Month() {
        return grossIncome3Month;
    }

    /**
     * Sets the value of the grossIncome3Month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrossIncome3Month(String value) {
        this.grossIncome3Month = value;
    }

    /**
     * Gets the value of the grossIncome6Month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrossIncome6Month() {
        return grossIncome6Month;
    }

    /**
     * Sets the value of the grossIncome6Month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrossIncome6Month(String value) {
        this.grossIncome6Month = value;
    }

    /**
     * Gets the value of the idNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * Sets the value of the idNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNo(String value) {
        this.idNo = value;
    }

    /**
     * Gets the value of the incomeMonth1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeMonth1() {
        return incomeMonth1;
    }

    /**
     * Sets the value of the incomeMonth1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeMonth1(String value) {
        this.incomeMonth1 = value;
    }

    /**
     * Gets the value of the incomeMonth2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeMonth2() {
        return incomeMonth2;
    }

    /**
     * Sets the value of the incomeMonth2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeMonth2(String value) {
        this.incomeMonth2 = value;
    }

    /**
     * Gets the value of the incomeMonth3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeMonth3() {
        return incomeMonth3;
    }

    /**
     * Sets the value of the incomeMonth3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeMonth3(String value) {
        this.incomeMonth3 = value;
    }

    /**
     * Gets the value of the incomeMonth4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeMonth4() {
        return incomeMonth4;
    }

    /**
     * Sets the value of the incomeMonth4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeMonth4(String value) {
        this.incomeMonth4 = value;
    }

    /**
     * Gets the value of the incomeMonth5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeMonth5() {
        return incomeMonth5;
    }

    /**
     * Sets the value of the incomeMonth5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeMonth5(String value) {
        this.incomeMonth5 = value;
    }

    /**
     * Gets the value of the incomeMonth6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeMonth6() {
        return incomeMonth6;
    }

    /**
     * Sets the value of the incomeMonth6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeMonth6(String value) {
        this.incomeMonth6 = value;
    }

    /**
     * Gets the value of the incomeTotal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeTotal() {
        return incomeTotal;
    }

    /**
     * Sets the value of the incomeTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeTotal(String value) {
        this.incomeTotal = value;
    }

    /**
     * Gets the value of the kecPayrollFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKecPayrollFlag() {
        return kecPayrollFlag;
    }

    /**
     * Sets the value of the kecPayrollFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKecPayrollFlag(String value) {
        this.kecPayrollFlag = value;
    }

    /**
     * Gets the value of the paymentAccountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentAccountNo() {
        return paymentAccountNo;
    }

    /**
     * Sets the value of the paymentAccountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentAccountNo(String value) {
        this.paymentAccountNo = value;
    }

    /**
     * Gets the value of the paymentChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentChannel() {
        return paymentChannel;
    }

    /**
     * Sets the value of the paymentChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentChannel(String value) {
        this.paymentChannel = value;
    }

    /**
     * Gets the value of the positionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPositionDate() {
        return positionDate;
    }

    /**
     * Sets the value of the positionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPositionDate(String value) {
        this.positionDate = value;
    }

    /**
     * Gets the value of the salaryDate property.
     * 
     */
    public int getSalaryDate() {
        return salaryDate;
    }

    /**
     * Sets the value of the salaryDate property.
     * 
     */
    public void setSalaryDate(int value) {
        this.salaryDate = value;
    }

    /**
     * Gets the value of the projectCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * Sets the value of the projectCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

    /**
     * Gets the value of the pvf12Month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPvf12Month() {
        return pvf12Month;
    }

    /**
     * Sets the value of the pvf12Month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPvf12Month(String value) {
        this.pvf12Month = value;
    }

    /**
     * Gets the value of the pvf3Month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPvf3Month() {
        return pvf3Month;
    }

    /**
     * Sets the value of the pvf3Month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPvf3Month(String value) {
        this.pvf3Month = value;
    }

    /**
     * Gets the value of the pvf6Month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPvf6Month() {
        return pvf6Month;
    }

    /**
     * Sets the value of the pvf6Month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPvf6Month(String value) {
        this.pvf6Month = value;
    }

    /**
     * Gets the value of the sso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSso() {
        return sso;
    }

    /**
     * Sets the value of the sso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSso(String value) {
        this.sso = value;
    }

    /**
     * Gets the value of the thFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThFirstName() {
        return thFirstName;
    }

    /**
     * Sets the value of the thFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThFirstName(String value) {
        this.thFirstName = value;
    }

    /**
     * Gets the value of the thLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThLastName() {
        return thLastName;
    }

    /**
     * Sets the value of the thLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThLastName(String value) {
        this.thLastName = value;
    }

    /**
     * Gets the value of the welfarePayrollFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWelfarePayrollFlag() {
        return welfarePayrollFlag;
    }

    /**
     * Sets the value of the welfarePayrollFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWelfarePayrollFlag(String value) {
        this.welfarePayrollFlag = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link Error }
     *     
     */
    public Error getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link Error }
     *     
     */
    public void setError(Error value) {
        this.error = value;
    }

    /**
     * Gets the value of the transactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionId(String value) {
        this.transactionId = value;
    }

}
