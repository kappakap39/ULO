//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ol.webservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wisdomDataM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wisdomDataM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cardId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuranceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noPriorityPassSup" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="premiumAmt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="priorityPassFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priorityPassMain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priorityPassMemo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priorityPassNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quotaOf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referralWisdomNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reqPriorityPassSup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transferFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wisdomDataM", propOrder = {
    "cardId",
    "insuranceType",
    "noPriorityPassSup",
    "premiumAmt",
    "priorityPassFlag",
    "priorityPassMain",
    "priorityPassMemo",
    "priorityPassNo",
    "quotaOf",
    "referralWisdomNo",
    "reqPriorityPassSup",
    "transferFrom"
})
public class WisdomDataM {

    protected String cardId;
    protected String insuranceType;
    protected BigDecimal noPriorityPassSup;
    protected BigDecimal premiumAmt;
    protected String priorityPassFlag;
    protected String priorityPassMain;
    protected String priorityPassMemo;
    protected String priorityPassNo;
    protected String quotaOf;
    protected String referralWisdomNo;
    protected String reqPriorityPassSup;
    protected String transferFrom;

    /**
     * Gets the value of the cardId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * Sets the value of the cardId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardId(String value) {
        this.cardId = value;
    }

    /**
     * Gets the value of the insuranceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuranceType() {
        return insuranceType;
    }

    /**
     * Sets the value of the insuranceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuranceType(String value) {
        this.insuranceType = value;
    }

    /**
     * Gets the value of the noPriorityPassSup property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNoPriorityPassSup() {
        return noPriorityPassSup;
    }

    /**
     * Sets the value of the noPriorityPassSup property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNoPriorityPassSup(BigDecimal value) {
        this.noPriorityPassSup = value;
    }

    /**
     * Gets the value of the premiumAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPremiumAmt() {
        return premiumAmt;
    }

    /**
     * Sets the value of the premiumAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPremiumAmt(BigDecimal value) {
        this.premiumAmt = value;
    }

    /**
     * Gets the value of the priorityPassFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriorityPassFlag() {
        return priorityPassFlag;
    }

    /**
     * Sets the value of the priorityPassFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriorityPassFlag(String value) {
        this.priorityPassFlag = value;
    }

    /**
     * Gets the value of the priorityPassMain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriorityPassMain() {
        return priorityPassMain;
    }

    /**
     * Sets the value of the priorityPassMain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriorityPassMain(String value) {
        this.priorityPassMain = value;
    }

    /**
     * Gets the value of the priorityPassMemo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriorityPassMemo() {
        return priorityPassMemo;
    }

    /**
     * Sets the value of the priorityPassMemo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriorityPassMemo(String value) {
        this.priorityPassMemo = value;
    }

    /**
     * Gets the value of the priorityPassNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriorityPassNo() {
        return priorityPassNo;
    }

    /**
     * Sets the value of the priorityPassNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriorityPassNo(String value) {
        this.priorityPassNo = value;
    }

    /**
     * Gets the value of the quotaOf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuotaOf() {
        return quotaOf;
    }

    /**
     * Sets the value of the quotaOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuotaOf(String value) {
        this.quotaOf = value;
    }

    /**
     * Gets the value of the referralWisdomNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferralWisdomNo() {
        return referralWisdomNo;
    }

    /**
     * Sets the value of the referralWisdomNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferralWisdomNo(String value) {
        this.referralWisdomNo = value;
    }

    /**
     * Gets the value of the reqPriorityPassSup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqPriorityPassSup() {
        return reqPriorityPassSup;
    }

    /**
     * Sets the value of the reqPriorityPassSup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqPriorityPassSup(String value) {
        this.reqPriorityPassSup = value;
    }

    /**
     * Gets the value of the transferFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransferFrom() {
        return transferFrom;
    }

    /**
     * Sets the value of the transferFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransferFrom(String value) {
        this.transferFrom = value;
    }

}
