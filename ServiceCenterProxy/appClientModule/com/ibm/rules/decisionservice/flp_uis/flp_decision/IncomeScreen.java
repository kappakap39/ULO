//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ibm.rules.decisionservice.flp_uis.flp_decision;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for incomeScreen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="incomeScreen">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="incomeScreenType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "incomeScreen", propOrder = {
    "incomeScreenType"
})
public class IncomeScreen {

    protected String incomeScreenType;

    /**
     * Gets the value of the incomeScreenType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeScreenType() {
        return incomeScreenType;
    }

    /**
     * Sets the value of the incomeScreenType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeScreenType(String value) {
        this.incomeScreenType = value;
    }

}
