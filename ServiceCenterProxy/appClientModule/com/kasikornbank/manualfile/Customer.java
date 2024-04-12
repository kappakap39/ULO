//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.kasikornbank.manualfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for customer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="customer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addressLine1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addressLine2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addressLine3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billCycle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cardlinkCustNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="custType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="empPosition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="financialInfoCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firstOpenDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="incomeAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobileNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="occupation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referAddressLine1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referAddressLine2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referAddressLine3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referZipcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vatCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zipCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customer", propOrder = {
    "addressLine1",
    "addressLine2",
    "addressLine3",
    "billCycle",
    "cardlinkCustNo",
    "creditLine",
    "custType",
    "empPosition",
    "financialInfoCode",
    "firstOpenDate",
    "incomeAmount",
    "mobileNo",
    "occupation",
    "orgNo",
    "referAddressLine1",
    "referAddressLine2",
    "referAddressLine3",
    "referZipcode",
    "vatCode",
    "zipCode"
})
public class Customer {

    protected String addressLine1;
    protected String addressLine2;
    protected String addressLine3;
    protected String billCycle;
    protected String cardlinkCustNo;
    protected String creditLine;
    protected String custType;
    protected String empPosition;
    protected String financialInfoCode;
    protected String firstOpenDate;
    protected String incomeAmount;
    protected String mobileNo;
    protected String occupation;
    protected String orgNo;
    protected String referAddressLine1;
    protected String referAddressLine2;
    protected String referAddressLine3;
    protected String referZipcode;
    protected String vatCode;
    protected String zipCode;

    /**
     * Gets the value of the addressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the value of the addressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine1(String value) {
        this.addressLine1 = value;
    }

    /**
     * Gets the value of the addressLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets the value of the addressLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine2(String value) {
        this.addressLine2 = value;
    }

    /**
     * Gets the value of the addressLine3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * Sets the value of the addressLine3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine3(String value) {
        this.addressLine3 = value;
    }

    /**
     * Gets the value of the billCycle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillCycle() {
        return billCycle;
    }

    /**
     * Sets the value of the billCycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillCycle(String value) {
        this.billCycle = value;
    }

    /**
     * Gets the value of the cardlinkCustNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardlinkCustNo() {
        return cardlinkCustNo;
    }

    /**
     * Sets the value of the cardlinkCustNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardlinkCustNo(String value) {
        this.cardlinkCustNo = value;
    }

    /**
     * Gets the value of the creditLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditLine() {
        return creditLine;
    }

    /**
     * Sets the value of the creditLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditLine(String value) {
        this.creditLine = value;
    }

    /**
     * Gets the value of the custType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustType() {
        return custType;
    }

    /**
     * Sets the value of the custType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustType(String value) {
        this.custType = value;
    }

    /**
     * Gets the value of the empPosition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmpPosition() {
        return empPosition;
    }

    /**
     * Sets the value of the empPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmpPosition(String value) {
        this.empPosition = value;
    }

    /**
     * Gets the value of the financialInfoCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinancialInfoCode() {
        return financialInfoCode;
    }

    /**
     * Sets the value of the financialInfoCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinancialInfoCode(String value) {
        this.financialInfoCode = value;
    }

    /**
     * Gets the value of the firstOpenDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstOpenDate() {
        return firstOpenDate;
    }

    /**
     * Sets the value of the firstOpenDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstOpenDate(String value) {
        this.firstOpenDate = value;
    }

    /**
     * Gets the value of the incomeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeAmount() {
        return incomeAmount;
    }

    /**
     * Sets the value of the incomeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeAmount(String value) {
        this.incomeAmount = value;
    }

    /**
     * Gets the value of the mobileNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets the value of the mobileNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobileNo(String value) {
        this.mobileNo = value;
    }

    /**
     * Gets the value of the occupation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Sets the value of the occupation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupation(String value) {
        this.occupation = value;
    }

    /**
     * Gets the value of the orgNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgNo() {
        return orgNo;
    }

    /**
     * Sets the value of the orgNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgNo(String value) {
        this.orgNo = value;
    }

    /**
     * Gets the value of the referAddressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferAddressLine1() {
        return referAddressLine1;
    }

    /**
     * Sets the value of the referAddressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferAddressLine1(String value) {
        this.referAddressLine1 = value;
    }

    /**
     * Gets the value of the referAddressLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferAddressLine2() {
        return referAddressLine2;
    }

    /**
     * Sets the value of the referAddressLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferAddressLine2(String value) {
        this.referAddressLine2 = value;
    }

    /**
     * Gets the value of the referAddressLine3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferAddressLine3() {
        return referAddressLine3;
    }

    /**
     * Sets the value of the referAddressLine3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferAddressLine3(String value) {
        this.referAddressLine3 = value;
    }

    /**
     * Gets the value of the referZipcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferZipcode() {
        return referZipcode;
    }

    /**
     * Sets the value of the referZipcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferZipcode(String value) {
        this.referZipcode = value;
    }

    /**
     * Gets the value of the vatCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVatCode() {
        return vatCode;
    }

    /**
     * Sets the value of the vatCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVatCode(String value) {
        this.vatCode = value;
    }

    /**
     * Gets the value of the zipCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the value of the zipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipCode(String value) {
        this.zipCode = value;
    }

}
