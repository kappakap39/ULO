//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ava.workflow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rsError complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rsError">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorAppAbbrv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorAppId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorSeverity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rsError", propOrder = {
    "errorAppAbbrv",
    "errorAppId",
    "errorCode",
    "errorDesc",
    "errorSeverity"
})
public class RsError {

    protected String errorAppAbbrv;
    protected String errorAppId;
    protected String errorCode;
    protected String errorDesc;
    protected String errorSeverity;

    /**
     * Gets the value of the errorAppAbbrv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorAppAbbrv() {
        return errorAppAbbrv;
    }

    /**
     * Sets the value of the errorAppAbbrv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorAppAbbrv(String value) {
        this.errorAppAbbrv = value;
    }

    /**
     * Gets the value of the errorAppId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorAppId() {
        return errorAppId;
    }

    /**
     * Sets the value of the errorAppId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorAppId(String value) {
        this.errorAppId = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the errorDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * Sets the value of the errorDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDesc(String value) {
        this.errorDesc = value;
    }

    /**
     * Gets the value of the errorSeverity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorSeverity() {
        return errorSeverity;
    }

    /**
     * Sets the value of the errorSeverity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorSeverity(String value) {
        this.errorSeverity = value;
    }

}
