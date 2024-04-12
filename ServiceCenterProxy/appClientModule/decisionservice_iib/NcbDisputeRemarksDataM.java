//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package decisionservice_iib;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ncbDisputeRemarksDataM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ncbDisputeRemarksDataM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Dispute" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisputeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisputeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DisputeRemark1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisputeRemark2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisputeRemark3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisputeRemark4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisputeRemark5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisputeRemark6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisputeTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReferenceSegment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReferenceSegmentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Remarks5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SegmentValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ncbDisputeRemarksDataM", propOrder = {
    "dispute",
    "disputeCode",
    "disputeDate",
    "disputeRemark1",
    "disputeRemark2",
    "disputeRemark3",
    "disputeRemark4",
    "disputeRemark5",
    "disputeRemark6",
    "disputeTime",
    "referenceSegment",
    "referenceSegmentType",
    "remarks5",
    "segmentValue"
})
public class NcbDisputeRemarksDataM {

    @XmlElement(name = "Dispute")
    protected String dispute;
    @XmlElement(name = "DisputeCode")
    protected String disputeCode;
    @XmlElement(name = "DisputeDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar disputeDate;
    @XmlElement(name = "DisputeRemark1")
    protected String disputeRemark1;
    @XmlElement(name = "DisputeRemark2")
    protected String disputeRemark2;
    @XmlElement(name = "DisputeRemark3")
    protected String disputeRemark3;
    @XmlElement(name = "DisputeRemark4")
    protected String disputeRemark4;
    @XmlElement(name = "DisputeRemark5")
    protected String disputeRemark5;
    @XmlElement(name = "DisputeRemark6")
    protected String disputeRemark6;
    @XmlElement(name = "DisputeTime")
    protected String disputeTime;
    @XmlElement(name = "ReferenceSegment")
    protected String referenceSegment;
    @XmlElement(name = "ReferenceSegmentType")
    protected String referenceSegmentType;
    @XmlElement(name = "Remarks5")
    protected String remarks5;
    @XmlElement(name = "SegmentValue")
    protected String segmentValue;

    /**
     * Gets the value of the dispute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDispute() {
        return dispute;
    }

    /**
     * Sets the value of the dispute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispute(String value) {
        this.dispute = value;
    }

    /**
     * Gets the value of the disputeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisputeCode() {
        return disputeCode;
    }

    /**
     * Sets the value of the disputeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisputeCode(String value) {
        this.disputeCode = value;
    }

    /**
     * Gets the value of the disputeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDisputeDate() {
        return disputeDate;
    }

    /**
     * Sets the value of the disputeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDisputeDate(XMLGregorianCalendar value) {
        this.disputeDate = value;
    }

    /**
     * Gets the value of the disputeRemark1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisputeRemark1() {
        return disputeRemark1;
    }

    /**
     * Sets the value of the disputeRemark1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisputeRemark1(String value) {
        this.disputeRemark1 = value;
    }

    /**
     * Gets the value of the disputeRemark2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisputeRemark2() {
        return disputeRemark2;
    }

    /**
     * Sets the value of the disputeRemark2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisputeRemark2(String value) {
        this.disputeRemark2 = value;
    }

    /**
     * Gets the value of the disputeRemark3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisputeRemark3() {
        return disputeRemark3;
    }

    /**
     * Sets the value of the disputeRemark3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisputeRemark3(String value) {
        this.disputeRemark3 = value;
    }

    /**
     * Gets the value of the disputeRemark4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisputeRemark4() {
        return disputeRemark4;
    }

    /**
     * Sets the value of the disputeRemark4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisputeRemark4(String value) {
        this.disputeRemark4 = value;
    }

    /**
     * Gets the value of the disputeRemark5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisputeRemark5() {
        return disputeRemark5;
    }

    /**
     * Sets the value of the disputeRemark5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisputeRemark5(String value) {
        this.disputeRemark5 = value;
    }

    /**
     * Gets the value of the disputeRemark6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisputeRemark6() {
        return disputeRemark6;
    }

    /**
     * Sets the value of the disputeRemark6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisputeRemark6(String value) {
        this.disputeRemark6 = value;
    }

    /**
     * Gets the value of the disputeTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisputeTime() {
        return disputeTime;
    }

    /**
     * Sets the value of the disputeTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisputeTime(String value) {
        this.disputeTime = value;
    }

    /**
     * Gets the value of the referenceSegment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceSegment() {
        return referenceSegment;
    }

    /**
     * Sets the value of the referenceSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceSegment(String value) {
        this.referenceSegment = value;
    }

    /**
     * Gets the value of the referenceSegmentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceSegmentType() {
        return referenceSegmentType;
    }

    /**
     * Sets the value of the referenceSegmentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceSegmentType(String value) {
        this.referenceSegmentType = value;
    }

    /**
     * Gets the value of the remarks5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks5() {
        return remarks5;
    }

    /**
     * Sets the value of the remarks5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks5(String value) {
        this.remarks5 = value;
    }

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

}
