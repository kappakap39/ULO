//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.kasikornbank.manualfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ncbIds complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ncbIds">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="segmentValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idIssueCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ncbIds", propOrder = {
    "segmentValue",
    "idType",
    "idNumber",
    "idIssueCountry"
})
public class NcbIds {

    protected String segmentValue;
    protected String idType;
    protected String idNumber;
    protected String idIssueCountry;

    /**
     * Gets the value of the segmentValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentValue() {
        return segmentValue;
    }

    /**
     * Sets the value of the segmentValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentValue(String value) {
        this.segmentValue = value;
    }

    /**
     * Gets the value of the idType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdType() {
        return idType;
    }

    /**
     * Sets the value of the idType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdType(String value) {
        this.idType = value;
    }

    /**
     * Gets the value of the idNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * Sets the value of the idNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNumber(String value) {
        this.idNumber = value;
    }

    /**
     * Gets the value of the idIssueCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdIssueCountry() {
        return idIssueCountry;
    }

    /**
     * Sets the value of the idIssueCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdIssueCountry(String value) {
        this.idIssueCountry = value;
    }

}
