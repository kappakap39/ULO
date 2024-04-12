//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ol.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for lpmBlacklist complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lpmBlacklist">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="blacklistCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="blacklistDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="blacklistType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lpmId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lpmBlacklist", propOrder = {
    "blacklistCode",
    "blacklistDate",
    "blacklistType",
    "lpmId"
})
public class LpmBlacklist {

    protected String blacklistCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar blacklistDate;
    protected String blacklistType;
    protected String lpmId;

    /**
     * Gets the value of the blacklistCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlacklistCode() {
        return blacklistCode;
    }

    /**
     * Sets the value of the blacklistCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlacklistCode(String value) {
        this.blacklistCode = value;
    }

    /**
     * Gets the value of the blacklistDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBlacklistDate() {
        return blacklistDate;
    }

    /**
     * Sets the value of the blacklistDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBlacklistDate(XMLGregorianCalendar value) {
        this.blacklistDate = value;
    }

    /**
     * Gets the value of the blacklistType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlacklistType() {
        return blacklistType;
    }

    /**
     * Sets the value of the blacklistType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlacklistType(String value) {
        this.blacklistType = value;
    }

    /**
     * Gets the value of the lpmId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLpmId() {
        return lpmId;
    }

    /**
     * Sets the value of the lpmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLpmId(String value) {
        this.lpmId = value;
    }

}
