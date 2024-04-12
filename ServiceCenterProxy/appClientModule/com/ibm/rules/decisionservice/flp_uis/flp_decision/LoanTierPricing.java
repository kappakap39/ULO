//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ibm.rules.decisionservice.flp_uis.flp_decision;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for loanTierPricing complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="loanTierPricing">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flatRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="intRateAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="intRateIndex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="intRateSign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="intRateSpread" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="lastInstallment" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="monthlyInstallment" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="rateType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="term" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="termType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalInstallment" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loanTierPricing", propOrder = {
    "effectiveDate",
    "flatRate",
    "intRateAmount",
    "intRateIndex",
    "intRateSign",
    "intRateSpread",
    "lastInstallment",
    "monthlyInstallment",
    "rateType",
    "term",
    "termType",
    "totalInstallment"
})
public class LoanTierPricing {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveDate;
    protected BigDecimal flatRate;
    protected BigDecimal intRateAmount;
    protected String intRateIndex;
    protected String intRateSign;
    protected BigDecimal intRateSpread;
    protected BigDecimal lastInstallment;
    protected BigDecimal monthlyInstallment;
    protected String rateType;
    protected Integer term;
    protected String termType;
    protected BigDecimal totalInstallment;

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the flatRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFlatRate() {
        return flatRate;
    }

    /**
     * Sets the value of the flatRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFlatRate(BigDecimal value) {
        this.flatRate = value;
    }

    /**
     * Gets the value of the intRateAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIntRateAmount() {
        return intRateAmount;
    }

    /**
     * Sets the value of the intRateAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIntRateAmount(BigDecimal value) {
        this.intRateAmount = value;
    }

    /**
     * Gets the value of the intRateIndex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntRateIndex() {
        return intRateIndex;
    }

    /**
     * Sets the value of the intRateIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntRateIndex(String value) {
        this.intRateIndex = value;
    }

    /**
     * Gets the value of the intRateSign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntRateSign() {
        return intRateSign;
    }

    /**
     * Sets the value of the intRateSign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntRateSign(String value) {
        this.intRateSign = value;
    }

    /**
     * Gets the value of the intRateSpread property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIntRateSpread() {
        return intRateSpread;
    }

    /**
     * Sets the value of the intRateSpread property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIntRateSpread(BigDecimal value) {
        this.intRateSpread = value;
    }

    /**
     * Gets the value of the lastInstallment property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLastInstallment() {
        return lastInstallment;
    }

    /**
     * Sets the value of the lastInstallment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLastInstallment(BigDecimal value) {
        this.lastInstallment = value;
    }

    /**
     * Gets the value of the monthlyInstallment property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonthlyInstallment() {
        return monthlyInstallment;
    }

    /**
     * Sets the value of the monthlyInstallment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonthlyInstallment(BigDecimal value) {
        this.monthlyInstallment = value;
    }

    /**
     * Gets the value of the rateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateType() {
        return rateType;
    }

    /**
     * Sets the value of the rateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateType(String value) {
        this.rateType = value;
    }

    /**
     * Gets the value of the term property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTerm() {
        return term;
    }

    /**
     * Sets the value of the term property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTerm(Integer value) {
        this.term = value;
    }

    /**
     * Gets the value of the termType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermType() {
        return termType;
    }

    /**
     * Sets the value of the termType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermType(String value) {
        this.termType = value;
    }

    /**
     * Gets the value of the totalInstallment property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalInstallment() {
        return totalInstallment;
    }

    /**
     * Sets the value of the totalInstallment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalInstallment(BigDecimal value) {
        this.totalInstallment = value;
    }

}
