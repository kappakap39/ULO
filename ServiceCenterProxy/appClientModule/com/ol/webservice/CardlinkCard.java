//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ol.webservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for cardlinkCard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cardlinkCard">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="autoPayAcctNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="autoPayFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billCycle" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="blockCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="blockDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="cardCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cardNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cardNoInHash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cardlinkCardId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cardlinkCustNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coaProductCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditLimitAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="expiryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="lastIncreaseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="lastPaymentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="mainFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mon24Profile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="openDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="orgNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outstandingBal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="plasticCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="realCustNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="realOrgNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="supCustNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="supOrgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wealthFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cardlinkCard", propOrder = {
    "autoPayAcctNo",
    "autoPayFlag",
    "billCycle",
    "blockCode",
    "blockDate",
    "cardCode",
    "cardNo",
    "cardNoInHash",
    "cardlinkCardId",
    "cardlinkCustNo",
    "coaProductCode",
    "creditLimitAmount",
    "expiryDate",
    "lastIncreaseDate",
    "lastPaymentDate",
    "mainFlag",
    "mon24Profile",
    "openDate",
    "orgNo",
    "outstandingBal",
    "plasticCode",
    "projectCode",
    "realCustNo",
    "realOrgNo",
    "supCustNo",
    "supOrgId",
    "wealthFlag"
})
public class CardlinkCard {

    protected String autoPayAcctNo;
    protected String autoPayFlag;
    protected int billCycle;
    protected String blockCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar blockDate;
    protected String cardCode;
    protected String cardNo;
    protected String cardNoInHash;
    protected String cardlinkCardId;
    protected String cardlinkCustNo;
    protected String coaProductCode;
    protected BigDecimal creditLimitAmount;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expiryDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastIncreaseDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastPaymentDate;
    protected String mainFlag;
    protected String mon24Profile;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar openDate;
    protected String orgNo;
    protected BigDecimal outstandingBal;
    protected String plasticCode;
    protected String projectCode;
    protected String realCustNo;
    protected String realOrgNo;
    protected String supCustNo;
    protected String supOrgId;
    protected String wealthFlag;

    /**
     * Gets the value of the autoPayAcctNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoPayAcctNo() {
        return autoPayAcctNo;
    }

    /**
     * Sets the value of the autoPayAcctNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoPayAcctNo(String value) {
        this.autoPayAcctNo = value;
    }

    /**
     * Gets the value of the autoPayFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoPayFlag() {
        return autoPayFlag;
    }

    /**
     * Sets the value of the autoPayFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoPayFlag(String value) {
        this.autoPayFlag = value;
    }

    /**
     * Gets the value of the billCycle property.
     * 
     */
    public int getBillCycle() {
        return billCycle;
    }

    /**
     * Sets the value of the billCycle property.
     * 
     */
    public void setBillCycle(int value) {
        this.billCycle = value;
    }

    /**
     * Gets the value of the blockCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlockCode() {
        return blockCode;
    }

    /**
     * Sets the value of the blockCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlockCode(String value) {
        this.blockCode = value;
    }

    /**
     * Gets the value of the blockDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBlockDate() {
        return blockDate;
    }

    /**
     * Sets the value of the blockDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBlockDate(XMLGregorianCalendar value) {
        this.blockDate = value;
    }

    /**
     * Gets the value of the cardCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardCode() {
        return cardCode;
    }

    /**
     * Sets the value of the cardCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardCode(String value) {
        this.cardCode = value;
    }

    /**
     * Gets the value of the cardNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * Sets the value of the cardNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNo(String value) {
        this.cardNo = value;
    }

    /**
     * Gets the value of the cardNoInHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNoInHash() {
        return cardNoInHash;
    }

    /**
     * Sets the value of the cardNoInHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNoInHash(String value) {
        this.cardNoInHash = value;
    }

    /**
     * Gets the value of the cardlinkCardId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardlinkCardId() {
        return cardlinkCardId;
    }

    /**
     * Sets the value of the cardlinkCardId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardlinkCardId(String value) {
        this.cardlinkCardId = value;
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
     * Gets the value of the coaProductCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoaProductCode() {
        return coaProductCode;
    }

    /**
     * Sets the value of the coaProductCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoaProductCode(String value) {
        this.coaProductCode = value;
    }

    /**
     * Gets the value of the creditLimitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditLimitAmount() {
        return creditLimitAmount;
    }

    /**
     * Sets the value of the creditLimitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditLimitAmount(BigDecimal value) {
        this.creditLimitAmount = value;
    }

    /**
     * Gets the value of the expiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the value of the expiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiryDate(XMLGregorianCalendar value) {
        this.expiryDate = value;
    }

    /**
     * Gets the value of the lastIncreaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastIncreaseDate() {
        return lastIncreaseDate;
    }

    /**
     * Sets the value of the lastIncreaseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastIncreaseDate(XMLGregorianCalendar value) {
        this.lastIncreaseDate = value;
    }

    /**
     * Gets the value of the lastPaymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastPaymentDate() {
        return lastPaymentDate;
    }

    /**
     * Sets the value of the lastPaymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastPaymentDate(XMLGregorianCalendar value) {
        this.lastPaymentDate = value;
    }

    /**
     * Gets the value of the mainFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainFlag() {
        return mainFlag;
    }

    /**
     * Sets the value of the mainFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainFlag(String value) {
        this.mainFlag = value;
    }

    /**
     * Gets the value of the mon24Profile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMon24Profile() {
        return mon24Profile;
    }

    /**
     * Sets the value of the mon24Profile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMon24Profile(String value) {
        this.mon24Profile = value;
    }

    /**
     * Gets the value of the openDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOpenDate() {
        return openDate;
    }

    /**
     * Sets the value of the openDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOpenDate(XMLGregorianCalendar value) {
        this.openDate = value;
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
     * Gets the value of the outstandingBal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOutstandingBal() {
        return outstandingBal;
    }

    /**
     * Sets the value of the outstandingBal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOutstandingBal(BigDecimal value) {
        this.outstandingBal = value;
    }

    /**
     * Gets the value of the plasticCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlasticCode() {
        return plasticCode;
    }

    /**
     * Sets the value of the plasticCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlasticCode(String value) {
        this.plasticCode = value;
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
     * Gets the value of the realCustNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRealCustNo() {
        return realCustNo;
    }

    /**
     * Sets the value of the realCustNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRealCustNo(String value) {
        this.realCustNo = value;
    }

    /**
     * Gets the value of the realOrgNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRealOrgNo() {
        return realOrgNo;
    }

    /**
     * Sets the value of the realOrgNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRealOrgNo(String value) {
        this.realOrgNo = value;
    }

    /**
     * Gets the value of the supCustNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupCustNo() {
        return supCustNo;
    }

    /**
     * Sets the value of the supCustNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupCustNo(String value) {
        this.supCustNo = value;
    }

    /**
     * Gets the value of the supOrgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupOrgId() {
        return supOrgId;
    }

    /**
     * Sets the value of the supOrgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupOrgId(String value) {
        this.supOrgId = value;
    }

    /**
     * Gets the value of the wealthFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWealthFlag() {
        return wealthFlag;
    }

    /**
     * Sets the value of the wealthFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWealthFlag(String value) {
        this.wealthFlag = value;
    }

}
