//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ol.webservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pfCrSumryObj complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pfCrSumryObj">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="acctCnt" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="totAccrIntAmt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="totLimAmt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="totMthlyInstAmt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="totOstAmt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="worstDelqDay" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pfCrSumryObj", propOrder = {
    "acctCnt",
    "totAccrIntAmt",
    "totLimAmt",
    "totMthlyInstAmt",
    "totOstAmt",
    "worstDelqDay"
})
public class PfCrSumryObj {

    protected Integer acctCnt;
    protected BigDecimal totAccrIntAmt;
    protected BigDecimal totLimAmt;
    protected BigDecimal totMthlyInstAmt;
    protected BigDecimal totOstAmt;
    protected Integer worstDelqDay;

    /**
     * Gets the value of the acctCnt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAcctCnt() {
        return acctCnt;
    }

    /**
     * Sets the value of the acctCnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAcctCnt(Integer value) {
        this.acctCnt = value;
    }

    /**
     * Gets the value of the totAccrIntAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotAccrIntAmt() {
        return totAccrIntAmt;
    }

    /**
     * Sets the value of the totAccrIntAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotAccrIntAmt(BigDecimal value) {
        this.totAccrIntAmt = value;
    }

    /**
     * Gets the value of the totLimAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotLimAmt() {
        return totLimAmt;
    }

    /**
     * Sets the value of the totLimAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotLimAmt(BigDecimal value) {
        this.totLimAmt = value;
    }

    /**
     * Gets the value of the totMthlyInstAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotMthlyInstAmt() {
        return totMthlyInstAmt;
    }

    /**
     * Sets the value of the totMthlyInstAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotMthlyInstAmt(BigDecimal value) {
        this.totMthlyInstAmt = value;
    }

    /**
     * Gets the value of the totOstAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotOstAmt() {
        return totOstAmt;
    }

    /**
     * Sets the value of the totOstAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotOstAmt(BigDecimal value) {
        this.totOstAmt = value;
    }

    /**
     * Gets the value of the worstDelqDay property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWorstDelqDay() {
        return worstDelqDay;
    }

    /**
     * Sets the value of the worstDelqDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWorstDelqDay(Integer value) {
        this.worstDelqDay = value;
    }

}
