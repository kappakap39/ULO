//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ol.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for lpmCustomer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lpmCustomer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="classCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditSumryObj" type="{http://webservice.ol.com/}creditSumryObj" minOccurs="0"/>
 *         &lt;element name="custNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxDayDiff" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="percentUnpaidMore30D" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lpmCustomer", propOrder = {
    "classCode",
    "creditSumryObj",
    "custNo",
    "maxDayDiff",
    "percentUnpaidMore30D"
})
public class LpmCustomer {

    protected String classCode;
    protected CreditSumryObj creditSumryObj;
    protected String custNo;
    protected Integer maxDayDiff;
    protected Integer percentUnpaidMore30D;

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassCode(String value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the creditSumryObj property.
     * 
     * @return
     *     possible object is
     *     {@link CreditSumryObj }
     *     
     */
    public CreditSumryObj getCreditSumryObj() {
        return creditSumryObj;
    }

    /**
     * Sets the value of the creditSumryObj property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditSumryObj }
     *     
     */
    public void setCreditSumryObj(CreditSumryObj value) {
        this.creditSumryObj = value;
    }

    /**
     * Gets the value of the custNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustNo() {
        return custNo;
    }

    /**
     * Sets the value of the custNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustNo(String value) {
        this.custNo = value;
    }

    /**
     * Gets the value of the maxDayDiff property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxDayDiff() {
        return maxDayDiff;
    }

    /**
     * Sets the value of the maxDayDiff property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxDayDiff(Integer value) {
        this.maxDayDiff = value;
    }

    /**
     * Gets the value of the percentUnpaidMore30D property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPercentUnpaidMore30D() {
        return percentUnpaidMore30D;
    }

    /**
     * Sets the value of the percentUnpaidMore30D property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPercentUnpaidMore30D(Integer value) {
        this.percentUnpaidMore30D = value;
    }

}
