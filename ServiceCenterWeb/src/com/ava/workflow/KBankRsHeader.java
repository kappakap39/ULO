//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ava.workflow;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for kBankRsHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kBankRsHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="corrID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="funcNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rqUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rsAppId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rsDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="rsError" type="{http://workflow.ava.com/}rsError" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="rsUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kBankRsHeader", propOrder = {
    "corrID",
    "funcNm",
    "rqUID",
    "rsAppId",
    "rsDt",
    "rsError",
    "rsUID",
    "statusCode"
})
public class KBankRsHeader {

    protected String corrID;
    protected String funcNm;
    protected String rqUID;
    protected String rsAppId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar rsDt;
    @XmlElement(nillable = true)
    protected List<RsError> rsError;
    protected String rsUID;
    protected String statusCode;

    /**
     * Gets the value of the corrID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrID() {
        return corrID;
    }

    /**
     * Sets the value of the corrID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrID(String value) {
        this.corrID = value;
    }

    /**
     * Gets the value of the funcNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFuncNm() {
        return funcNm;
    }

    /**
     * Sets the value of the funcNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFuncNm(String value) {
        this.funcNm = value;
    }

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rsAppId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRsAppId() {
        return rsAppId;
    }

    /**
     * Sets the value of the rsAppId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRsAppId(String value) {
        this.rsAppId = value;
    }

    /**
     * Gets the value of the rsDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRsDt() {
        return rsDt;
    }

    /**
     * Sets the value of the rsDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRsDt(XMLGregorianCalendar value) {
        this.rsDt = value;
    }

    /**
     * Gets the value of the rsError property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rsError property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRsError().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RsError }
     * 
     * 
     */
    public List<RsError> getRsError() {
        if (rsError == null) {
            rsError = new ArrayList<RsError>();
        }
        return this.rsError;
    }

    /**
     * Gets the value of the rsUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRsUID() {
        return rsUID;
    }

    /**
     * Sets the value of the rsUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRsUID(String value) {
        this.rsUID = value;
    }

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusCode(String value) {
        this.statusCode = value;
    }

}
