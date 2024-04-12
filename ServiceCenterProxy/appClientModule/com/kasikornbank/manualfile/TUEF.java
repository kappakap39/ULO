//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.kasikornbank.manualfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TUEF complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TUEF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="header" type="{http://kasikornbank.com/ManualFile}header" minOccurs="0"/>
 *         &lt;element name="ncbNames" type="{http://kasikornbank.com/ManualFile}ncbNames" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ncbIds" type="{http://kasikornbank.com/ManualFile}ncbIds" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ncbAddress" type="{http://kasikornbank.com/ManualFile}ncbAddress" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ncbAccounts" type="{http://kasikornbank.com/ManualFile}ncbAccounts" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ncbHistory" type="{http://kasikornbank.com/ManualFile}ncbHistory" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ncbEnquiry" type="{http://kasikornbank.com/ManualFile}ncbEnquiry" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ncbDisputeRemarks" type="{http://kasikornbank.com/ManualFile}ncbDisputeRemarks" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ficoScore" type="{http://kasikornbank.com/ManualFile}ficoScore" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TUEF", propOrder = {
    "header",
    "ncbNames",
    "ncbIds",
    "ncbAddress",
    "ncbAccounts",
    "ncbHistory",
    "ncbEnquiry",
    "ncbDisputeRemarks",
    "ficoScore"
})
public class TUEF {

    protected Header header;
    protected List<NcbNames> ncbNames;
    protected List<NcbIds> ncbIds;
    protected List<NcbAddress> ncbAddress;
    protected List<NcbAccounts> ncbAccounts;
    protected List<NcbHistory> ncbHistory;
    protected List<NcbEnquiry> ncbEnquiry;
    protected List<NcbDisputeRemarks> ncbDisputeRemarks;
    protected FicoScore ficoScore;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link Header }
     *     
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link Header }
     *     
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the ncbNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ncbNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNcbNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NcbNames }
     * 
     * 
     */
    public List<NcbNames> getNcbNames() {
        if (ncbNames == null) {
            ncbNames = new ArrayList<NcbNames>();
        }
        return this.ncbNames;
    }

    /**
     * Gets the value of the ncbIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ncbIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNcbIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NcbIds }
     * 
     * 
     */
    public List<NcbIds> getNcbIds() {
        if (ncbIds == null) {
            ncbIds = new ArrayList<NcbIds>();
        }
        return this.ncbIds;
    }

    /**
     * Gets the value of the ncbAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ncbAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNcbAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NcbAddress }
     * 
     * 
     */
    public List<NcbAddress> getNcbAddress() {
        if (ncbAddress == null) {
            ncbAddress = new ArrayList<NcbAddress>();
        }
        return this.ncbAddress;
    }

    /**
     * Gets the value of the ncbAccounts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ncbAccounts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNcbAccounts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NcbAccounts }
     * 
     * 
     */
    public List<NcbAccounts> getNcbAccounts() {
        if (ncbAccounts == null) {
            ncbAccounts = new ArrayList<NcbAccounts>();
        }
        return this.ncbAccounts;
    }

    /**
     * Gets the value of the ncbHistory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ncbHistory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNcbHistory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NcbHistory }
     * 
     * 
     */
    public List<NcbHistory> getNcbHistory() {
        if (ncbHistory == null) {
            ncbHistory = new ArrayList<NcbHistory>();
        }
        return this.ncbHistory;
    }

    /**
     * Gets the value of the ncbEnquiry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ncbEnquiry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNcbEnquiry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NcbEnquiry }
     * 
     * 
     */
    public List<NcbEnquiry> getNcbEnquiry() {
        if (ncbEnquiry == null) {
            ncbEnquiry = new ArrayList<NcbEnquiry>();
        }
        return this.ncbEnquiry;
    }

    /**
     * Gets the value of the ncbDisputeRemarks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ncbDisputeRemarks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNcbDisputeRemarks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NcbDisputeRemarks }
     * 
     * 
     */
    public List<NcbDisputeRemarks> getNcbDisputeRemarks() {
        if (ncbDisputeRemarks == null) {
            ncbDisputeRemarks = new ArrayList<NcbDisputeRemarks>();
        }
        return this.ncbDisputeRemarks;
    }

    /**
     * Gets the value of the ficoScore property.
     * 
     * @return
     *     possible object is
     *     {@link FicoScore }
     *     
     */
    public FicoScore getFicoScore() {
        return ficoScore;
    }

    /**
     * Sets the value of the ficoScore property.
     * 
     * @param value
     *     allowed object is
     *     {@link FicoScore }
     *     
     */
    public void setFicoScore(FicoScore value) {
        this.ficoScore = value;
    }

}
