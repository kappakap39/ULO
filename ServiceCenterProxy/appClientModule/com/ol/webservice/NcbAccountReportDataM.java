//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ol.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ncbAccountReportDataM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ncbAccountReportDataM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountReportId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="restructureGT1Yr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rules" type="{http://webservice.ol.com/}ncbAccountReportRuleDataM" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="seq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="trackingCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ncbAccountReportDataM", propOrder = {
    "accountReportId",
    "restructureGT1Yr",
    "rules",
    "seq",
    "trackingCode"
})
public class NcbAccountReportDataM {

    protected String accountReportId;
    protected String restructureGT1Yr;
    @XmlElement(nillable = true)
    protected List<NcbAccountReportRuleDataM> rules;
    protected int seq;
    protected String trackingCode;

    /**
     * Gets the value of the accountReportId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountReportId() {
        return accountReportId;
    }

    /**
     * Sets the value of the accountReportId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountReportId(String value) {
        this.accountReportId = value;
    }

    /**
     * Gets the value of the restructureGT1Yr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestructureGT1Yr() {
        return restructureGT1Yr;
    }

    /**
     * Sets the value of the restructureGT1Yr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestructureGT1Yr(String value) {
        this.restructureGT1Yr = value;
    }

    /**
     * Gets the value of the rules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NcbAccountReportRuleDataM }
     * 
     * 
     */
    public List<NcbAccountReportRuleDataM> getRules() {
        if (rules == null) {
            rules = new ArrayList<NcbAccountReportRuleDataM>();
        }
        return this.rules;
    }

    /**
     * Gets the value of the seq property.
     * 
     */
    public int getSeq() {
        return seq;
    }

    /**
     * Sets the value of the seq property.
     * 
     */
    public void setSeq(int value) {
        this.seq = value;
    }

    /**
     * Gets the value of the trackingCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingCode() {
        return trackingCode;
    }

    /**
     * Sets the value of the trackingCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingCode(String value) {
        this.trackingCode = value;
    }

}
