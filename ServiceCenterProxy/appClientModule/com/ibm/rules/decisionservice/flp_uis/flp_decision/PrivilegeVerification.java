//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ibm.rules.decisionservice.flp_uis.flp_decision;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for privilegeVerification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="privilegeVerification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="privilegeVerificationRequiredFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recommendScreen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "privilegeVerification", propOrder = {
    "privilegeVerificationRequiredFlag",
    "recommendScreen"
})
public class PrivilegeVerification {

    protected String privilegeVerificationRequiredFlag;
    protected String recommendScreen;

    /**
     * Gets the value of the privilegeVerificationRequiredFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrivilegeVerificationRequiredFlag() {
        return privilegeVerificationRequiredFlag;
    }

    /**
     * Sets the value of the privilegeVerificationRequiredFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrivilegeVerificationRequiredFlag(String value) {
        this.privilegeVerificationRequiredFlag = value;
    }

    /**
     * Gets the value of the recommendScreen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecommendScreen() {
        return recommendScreen;
    }

    /**
     * Sets the value of the recommendScreen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecommendScreen(String value) {
        this.recommendScreen = value;
    }

}
