//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ol.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cis0368I01Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cis0368I01Response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CISCustomer" type="{http://webservice.ol.com/}cisCustomerCIS0368I01" minOccurs="0"/>
 *         &lt;element name="kBankHeader" type="{http://webservice.ol.com/}kBankHeader" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cis0368I01Response", propOrder = {
    "cisCustomer",
    "kBankHeader"
})
public class Cis0368I01Response {

    @XmlElement(name = "CISCustomer")
    protected CisCustomerCIS0368I01 cisCustomer;
    protected KBankHeader kBankHeader;

    /**
     * Gets the value of the cisCustomer property.
     * 
     * @return
     *     possible object is
     *     {@link CisCustomerCIS0368I01 }
     *     
     */
    public CisCustomerCIS0368I01 getCISCustomer() {
        return cisCustomer;
    }

    /**
     * Sets the value of the cisCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link CisCustomerCIS0368I01 }
     *     
     */
    public void setCISCustomer(CisCustomerCIS0368I01 value) {
        this.cisCustomer = value;
    }

    /**
     * Gets the value of the kBankHeader property.
     * 
     * @return
     *     possible object is
     *     {@link KBankHeader }
     *     
     */
    public KBankHeader getKBankHeader() {
        return kBankHeader;
    }

    /**
     * Sets the value of the kBankHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link KBankHeader }
     *     
     */
    public void setKBankHeader(KBankHeader value) {
        this.kBankHeader = value;
    }

}
