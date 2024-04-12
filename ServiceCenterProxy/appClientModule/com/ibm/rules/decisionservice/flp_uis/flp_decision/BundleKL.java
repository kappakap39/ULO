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
 * <p>Java class for bundleKL complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bundleKL">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="approveCreditLine" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="estimatedIncome" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="verifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="verifiedIncome" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bundleKL", propOrder = {
    "approveCreditLine",
    "estimatedIncome",
    "verifiedDate",
    "verifiedIncome"
})
public class BundleKL {

    protected BigDecimal approveCreditLine;
    protected BigDecimal estimatedIncome;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar verifiedDate;
    protected BigDecimal verifiedIncome;

    /**
     * Gets the value of the approveCreditLine property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getApproveCreditLine() {
        return approveCreditLine;
    }

    /**
     * Sets the value of the approveCreditLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setApproveCreditLine(BigDecimal value) {
        this.approveCreditLine = value;
    }

    /**
     * Gets the value of the estimatedIncome property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEstimatedIncome() {
        return estimatedIncome;
    }

    /**
     * Sets the value of the estimatedIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEstimatedIncome(BigDecimal value) {
        this.estimatedIncome = value;
    }

    /**
     * Gets the value of the verifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVerifiedDate() {
        return verifiedDate;
    }

    /**
     * Sets the value of the verifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVerifiedDate(XMLGregorianCalendar value) {
        this.verifiedDate = value;
    }

    /**
     * Gets the value of the verifiedIncome property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVerifiedIncome() {
        return verifiedIncome;
    }

    /**
     * Sets the value of the verifiedIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVerifiedIncome(BigDecimal value) {
        this.verifiedIncome = value;
    }

}
