//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ol.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for indCustomerKBANK1211I01 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="indCustomerKBANK1211I01">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caseFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="guarFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="joinLoanFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uniqueCustFoundFlg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "indCustomerKBANK1211I01", propOrder = {
    "caseFlag",
    "guarFlag",
    "joinLoanFlag",
    "name",
    "uniqueCustFoundFlg"
})
public class IndCustomerKBANK1211I01 {

    protected String caseFlag;
    protected String guarFlag;
    protected String joinLoanFlag;
    protected String name;
    protected String uniqueCustFoundFlg;

    /**
     * Gets the value of the caseFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaseFlag() {
        return caseFlag;
    }

    /**
     * Sets the value of the caseFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaseFlag(String value) {
        this.caseFlag = value;
    }

    /**
     * Gets the value of the guarFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuarFlag() {
        return guarFlag;
    }

    /**
     * Sets the value of the guarFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuarFlag(String value) {
        this.guarFlag = value;
    }

    /**
     * Gets the value of the joinLoanFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoinLoanFlag() {
        return joinLoanFlag;
    }

    /**
     * Sets the value of the joinLoanFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoinLoanFlag(String value) {
        this.joinLoanFlag = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the uniqueCustFoundFlg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueCustFoundFlg() {
        return uniqueCustFoundFlg;
    }

    /**
     * Sets the value of the uniqueCustFoundFlg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueCustFoundFlg(String value) {
        this.uniqueCustFoundFlg = value;
    }

}
