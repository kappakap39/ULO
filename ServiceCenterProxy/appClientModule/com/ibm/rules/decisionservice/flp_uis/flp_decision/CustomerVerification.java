//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ibm.rules.decisionservice.flp_uis.flp_decision;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for customerVerification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="customerVerification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customerQuestionSegment" type="{http://www.ibm.com/rules/decisionservice/Flp_uis/Flp_decision}customerQuestionSegment" minOccurs="0"/>
 *         &lt;element name="customerVerificationRequiredFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customerVerification", propOrder = {
    "customerQuestionSegment",
    "customerVerificationRequiredFlag"
})
public class CustomerVerification {

    protected CustomerQuestionSegment customerQuestionSegment;
    protected String customerVerificationRequiredFlag;

    /**
     * Gets the value of the customerQuestionSegment property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerQuestionSegment }
     *     
     */
    public CustomerQuestionSegment getCustomerQuestionSegment() {
        return customerQuestionSegment;
    }

    /**
     * Sets the value of the customerQuestionSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerQuestionSegment }
     *     
     */
    public void setCustomerQuestionSegment(CustomerQuestionSegment value) {
        this.customerQuestionSegment = value;
    }

    /**
     * Gets the value of the customerVerificationRequiredFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerVerificationRequiredFlag() {
        return customerVerificationRequiredFlag;
    }

    /**
     * Sets the value of the customerVerificationRequiredFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerVerificationRequiredFlag(String value) {
        this.customerVerificationRequiredFlag = value;
    }

}
