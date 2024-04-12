//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.kasikornbank.manualfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ncbAccounts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ncbAccounts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SegmentValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MemberCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MemberShortName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OwnershipIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateAccountOpened" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateOfLastPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateAccountClose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AsOfDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditLimit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AmountOwed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AmountPastDue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DefaultDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InstalmentFrequency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InstallmentAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InstallmentNumberOfPayments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccountStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LoanClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentHistory1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentHistory2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentHistoryStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentHistoryEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LoanObjective" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Collateral1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Collateral2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Collateral3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateOfLastDebtRestructure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PercentPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeOfCreditCard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumberOfCoborrowers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnitMake" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnitModel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreditTypeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ncbAccounts", propOrder = {
    "segmentValue",
    "memberCode",
    "memberShortName",
    "accountNumber",
    "accountType",
    "ownershipIndicator",
    "currencyCode",
    "dateAccountOpened",
    "dateOfLastPayment",
    "dateAccountClose",
    "asOfDate",
    "creditLimit",
    "amountOwed",
    "amountPastDue",
    "defaultDate",
    "instalmentFrequency",
    "installmentAmount",
    "installmentNumberOfPayments",
    "accountStatus",
    "loanClass",
    "paymentHistory1",
    "paymentHistory2",
    "paymentHistoryStartDate",
    "paymentHistoryEndDate",
    "loanObjective",
    "collateral1",
    "collateral2",
    "collateral3",
    "dateOfLastDebtRestructure",
    "percentPayment",
    "typeOfCreditCard",
    "numberOfCoborrowers",
    "unitMake",
    "unitModel",
    "creditTypeFlag"
})
public class NcbAccounts {

    @XmlElement(name = "SegmentValue")
    protected String segmentValue;
    @XmlElement(name = "MemberCode")
    protected String memberCode;
    @XmlElement(name = "MemberShortName")
    protected String memberShortName;
    @XmlElement(name = "AccountNumber")
    protected String accountNumber;
    @XmlElement(name = "AccountType")
    protected String accountType;
    @XmlElement(name = "OwnershipIndicator")
    protected String ownershipIndicator;
    @XmlElement(name = "CurrencyCode")
    protected String currencyCode;
    @XmlElement(name = "DateAccountOpened")
    protected String dateAccountOpened;
    @XmlElement(name = "DateOfLastPayment")
    protected String dateOfLastPayment;
    @XmlElement(name = "DateAccountClose")
    protected String dateAccountClose;
    @XmlElement(name = "AsOfDate")
    protected String asOfDate;
    @XmlElement(name = "CreditLimit")
    protected String creditLimit;
    @XmlElement(name = "AmountOwed")
    protected String amountOwed;
    @XmlElement(name = "AmountPastDue")
    protected String amountPastDue;
    @XmlElement(name = "DefaultDate")
    protected String defaultDate;
    @XmlElement(name = "InstalmentFrequency")
    protected String instalmentFrequency;
    @XmlElement(name = "InstallmentAmount")
    protected String installmentAmount;
    @XmlElement(name = "InstallmentNumberOfPayments")
    protected String installmentNumberOfPayments;
    @XmlElement(name = "AccountStatus")
    protected String accountStatus;
    @XmlElement(name = "LoanClass")
    protected String loanClass;
    @XmlElement(name = "PaymentHistory1")
    protected String paymentHistory1;
    @XmlElement(name = "PaymentHistory2")
    protected String paymentHistory2;
    @XmlElement(name = "PaymentHistoryStartDate")
    protected String paymentHistoryStartDate;
    @XmlElement(name = "PaymentHistoryEndDate")
    protected String paymentHistoryEndDate;
    @XmlElement(name = "LoanObjective")
    protected String loanObjective;
    @XmlElement(name = "Collateral1")
    protected String collateral1;
    @XmlElement(name = "Collateral2")
    protected String collateral2;
    @XmlElement(name = "Collateral3")
    protected String collateral3;
    @XmlElement(name = "DateOfLastDebtRestructure")
    protected String dateOfLastDebtRestructure;
    @XmlElement(name = "PercentPayment")
    protected String percentPayment;
    @XmlElement(name = "TypeOfCreditCard")
    protected String typeOfCreditCard;
    @XmlElement(name = "NumberOfCoborrowers")
    protected String numberOfCoborrowers;
    @XmlElement(name = "UnitMake")
    protected String unitMake;
    @XmlElement(name = "UnitModel")
    protected String unitModel;
    @XmlElement(name = "CreditTypeFlag")
    protected String creditTypeFlag;

    /**
     * Gets the value of the segmentValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentValue() {
        return segmentValue;
    }

    /**
     * Sets the value of the segmentValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentValue(String value) {
        this.segmentValue = value;
    }

    /**
     * Gets the value of the memberCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemberCode() {
        return memberCode;
    }

    /**
     * Sets the value of the memberCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemberCode(String value) {
        this.memberCode = value;
    }

    /**
     * Gets the value of the memberShortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemberShortName() {
        return memberShortName;
    }

    /**
     * Sets the value of the memberShortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemberShortName(String value) {
        this.memberShortName = value;
    }

    /**
     * Gets the value of the accountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the accountType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Sets the value of the accountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountType(String value) {
        this.accountType = value;
    }

    /**
     * Gets the value of the ownershipIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnershipIndicator() {
        return ownershipIndicator;
    }

    /**
     * Sets the value of the ownershipIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnershipIndicator(String value) {
        this.ownershipIndicator = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the dateAccountOpened property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateAccountOpened() {
        return dateAccountOpened;
    }

    /**
     * Sets the value of the dateAccountOpened property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateAccountOpened(String value) {
        this.dateAccountOpened = value;
    }

    /**
     * Gets the value of the dateOfLastPayment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOfLastPayment() {
        return dateOfLastPayment;
    }

    /**
     * Sets the value of the dateOfLastPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfLastPayment(String value) {
        this.dateOfLastPayment = value;
    }

    /**
     * Gets the value of the dateAccountClose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateAccountClose() {
        return dateAccountClose;
    }

    /**
     * Sets the value of the dateAccountClose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateAccountClose(String value) {
        this.dateAccountClose = value;
    }

    /**
     * Gets the value of the asOfDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsOfDate() {
        return asOfDate;
    }

    /**
     * Sets the value of the asOfDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsOfDate(String value) {
        this.asOfDate = value;
    }

    /**
     * Gets the value of the creditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditLimit() {
        return creditLimit;
    }

    /**
     * Sets the value of the creditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditLimit(String value) {
        this.creditLimit = value;
    }

    /**
     * Gets the value of the amountOwed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountOwed() {
        return amountOwed;
    }

    /**
     * Sets the value of the amountOwed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountOwed(String value) {
        this.amountOwed = value;
    }

    /**
     * Gets the value of the amountPastDue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountPastDue() {
        return amountPastDue;
    }

    /**
     * Sets the value of the amountPastDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountPastDue(String value) {
        this.amountPastDue = value;
    }

    /**
     * Gets the value of the defaultDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultDate() {
        return defaultDate;
    }

    /**
     * Sets the value of the defaultDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultDate(String value) {
        this.defaultDate = value;
    }

    /**
     * Gets the value of the instalmentFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstalmentFrequency() {
        return instalmentFrequency;
    }

    /**
     * Sets the value of the instalmentFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstalmentFrequency(String value) {
        this.instalmentFrequency = value;
    }

    /**
     * Gets the value of the installmentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstallmentAmount() {
        return installmentAmount;
    }

    /**
     * Sets the value of the installmentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstallmentAmount(String value) {
        this.installmentAmount = value;
    }

    /**
     * Gets the value of the installmentNumberOfPayments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstallmentNumberOfPayments() {
        return installmentNumberOfPayments;
    }

    /**
     * Sets the value of the installmentNumberOfPayments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstallmentNumberOfPayments(String value) {
        this.installmentNumberOfPayments = value;
    }

    /**
     * Gets the value of the accountStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountStatus() {
        return accountStatus;
    }

    /**
     * Sets the value of the accountStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountStatus(String value) {
        this.accountStatus = value;
    }

    /**
     * Gets the value of the loanClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanClass() {
        return loanClass;
    }

    /**
     * Sets the value of the loanClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanClass(String value) {
        this.loanClass = value;
    }

    /**
     * Gets the value of the paymentHistory1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentHistory1() {
        return paymentHistory1;
    }

    /**
     * Sets the value of the paymentHistory1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentHistory1(String value) {
        this.paymentHistory1 = value;
    }

    /**
     * Gets the value of the paymentHistory2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentHistory2() {
        return paymentHistory2;
    }

    /**
     * Sets the value of the paymentHistory2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentHistory2(String value) {
        this.paymentHistory2 = value;
    }

    /**
     * Gets the value of the paymentHistoryStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentHistoryStartDate() {
        return paymentHistoryStartDate;
    }

    /**
     * Sets the value of the paymentHistoryStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentHistoryStartDate(String value) {
        this.paymentHistoryStartDate = value;
    }

    /**
     * Gets the value of the paymentHistoryEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentHistoryEndDate() {
        return paymentHistoryEndDate;
    }

    /**
     * Sets the value of the paymentHistoryEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentHistoryEndDate(String value) {
        this.paymentHistoryEndDate = value;
    }

    /**
     * Gets the value of the loanObjective property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanObjective() {
        return loanObjective;
    }

    /**
     * Sets the value of the loanObjective property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanObjective(String value) {
        this.loanObjective = value;
    }

    /**
     * Gets the value of the collateral1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollateral1() {
        return collateral1;
    }

    /**
     * Sets the value of the collateral1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollateral1(String value) {
        this.collateral1 = value;
    }

    /**
     * Gets the value of the collateral2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollateral2() {
        return collateral2;
    }

    /**
     * Sets the value of the collateral2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollateral2(String value) {
        this.collateral2 = value;
    }

    /**
     * Gets the value of the collateral3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollateral3() {
        return collateral3;
    }

    /**
     * Sets the value of the collateral3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollateral3(String value) {
        this.collateral3 = value;
    }

    /**
     * Gets the value of the dateOfLastDebtRestructure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOfLastDebtRestructure() {
        return dateOfLastDebtRestructure;
    }

    /**
     * Sets the value of the dateOfLastDebtRestructure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfLastDebtRestructure(String value) {
        this.dateOfLastDebtRestructure = value;
    }

    /**
     * Gets the value of the percentPayment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPercentPayment() {
        return percentPayment;
    }

    /**
     * Sets the value of the percentPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPercentPayment(String value) {
        this.percentPayment = value;
    }

    /**
     * Gets the value of the typeOfCreditCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeOfCreditCard() {
        return typeOfCreditCard;
    }

    /**
     * Sets the value of the typeOfCreditCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeOfCreditCard(String value) {
        this.typeOfCreditCard = value;
    }

    /**
     * Gets the value of the numberOfCoborrowers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfCoborrowers() {
        return numberOfCoborrowers;
    }

    /**
     * Sets the value of the numberOfCoborrowers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfCoborrowers(String value) {
        this.numberOfCoborrowers = value;
    }

    /**
     * Gets the value of the unitMake property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitMake() {
        return unitMake;
    }

    /**
     * Sets the value of the unitMake property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitMake(String value) {
        this.unitMake = value;
    }

    /**
     * Gets the value of the unitModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitModel() {
        return unitModel;
    }

    /**
     * Sets the value of the unitModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitModel(String value) {
        this.unitModel = value;
    }

    /**
     * Gets the value of the creditTypeFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditTypeFlag() {
        return creditTypeFlag;
    }

    /**
     * Sets the value of the creditTypeFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditTypeFlag(String value) {
        this.creditTypeFlag = value;
    }

}
