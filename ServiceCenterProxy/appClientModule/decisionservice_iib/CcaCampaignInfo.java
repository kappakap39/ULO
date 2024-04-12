//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package decisionservice_iib;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ccaCampaignInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ccaCampaignInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="additionalDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="applicationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cisNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="campaignCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="enFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enTitleCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expiryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="highestIncome" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="idNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thTitleCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="accountNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="limit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="interest" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="term" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ccaCampaignInfo", propOrder = {
    "additionalDetail",
    "applicationType",
    "cisNo",
    "campaignCode",
    "effectiveDate",
    "enFirstName",
    "enLastName",
    "enTitleCode",
    "expiryDate",
    "highestIncome",
    "idNo",
    "projectCode",
    "thFirstName",
    "thLastName",
    "thTitleCode",
    "updateDate",
    "updateTime",
    "accountNo",
    "limit",
    "interest",
    "term"
})
public class CcaCampaignInfo {

    protected String additionalDetail;
    protected String applicationType;
    protected String cisNo;
    protected String campaignCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveDate;
    protected String enFirstName;
    protected String enLastName;
    protected String enTitleCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expiryDate;
    protected BigDecimal highestIncome;
    protected String idNo;
    protected String projectCode;
    protected String thFirstName;
    protected String thLastName;
    protected String thTitleCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updateDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updateTime;
    protected String accountNo;
    protected BigDecimal limit;
    protected BigDecimal interest;
    protected BigDecimal term;

    /**
     * Gets the value of the additionalDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalDetail() {
        return additionalDetail;
    }

    /**
     * Sets the value of the additionalDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalDetail(String value) {
        this.additionalDetail = value;
    }

    /**
     * Gets the value of the applicationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationType() {
        return applicationType;
    }

    /**
     * Sets the value of the applicationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationType(String value) {
        this.applicationType = value;
    }

    /**
     * Gets the value of the cisNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCisNo() {
        return cisNo;
    }

    /**
     * Sets the value of the cisNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCisNo(String value) {
        this.cisNo = value;
    }

    /**
     * Gets the value of the campaignCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCampaignCode() {
        return campaignCode;
    }

    /**
     * Sets the value of the campaignCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCampaignCode(String value) {
        this.campaignCode = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the enFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnFirstName() {
        return enFirstName;
    }

    /**
     * Sets the value of the enFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnFirstName(String value) {
        this.enFirstName = value;
    }

    /**
     * Gets the value of the enLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnLastName() {
        return enLastName;
    }

    /**
     * Sets the value of the enLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnLastName(String value) {
        this.enLastName = value;
    }

    /**
     * Gets the value of the enTitleCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnTitleCode() {
        return enTitleCode;
    }

    /**
     * Sets the value of the enTitleCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnTitleCode(String value) {
        this.enTitleCode = value;
    }

    /**
     * Gets the value of the expiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the value of the expiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiryDate(XMLGregorianCalendar value) {
        this.expiryDate = value;
    }

    /**
     * Gets the value of the highestIncome property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHighestIncome() {
        return highestIncome;
    }

    /**
     * Sets the value of the highestIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHighestIncome(BigDecimal value) {
        this.highestIncome = value;
    }

    /**
     * Gets the value of the idNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * Sets the value of the idNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNo(String value) {
        this.idNo = value;
    }

    /**
     * Gets the value of the projectCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * Sets the value of the projectCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

    /**
     * Gets the value of the thFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThFirstName() {
        return thFirstName;
    }

    /**
     * Sets the value of the thFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThFirstName(String value) {
        this.thFirstName = value;
    }

    /**
     * Gets the value of the thLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThLastName() {
        return thLastName;
    }

    /**
     * Sets the value of the thLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThLastName(String value) {
        this.thLastName = value;
    }

    /**
     * Gets the value of the thTitleCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThTitleCode() {
        return thTitleCode;
    }

    /**
     * Sets the value of the thTitleCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThTitleCode(String value) {
        this.thTitleCode = value;
    }

    /**
     * Gets the value of the updateDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the value of the updateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateDate(XMLGregorianCalendar value) {
        this.updateDate = value;
    }

    /**
     * Gets the value of the updateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets the value of the updateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateTime(XMLGregorianCalendar value) {
        this.updateTime = value;
    }

    /**
     * Gets the value of the accountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * Sets the value of the accountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNo(String value) {
        this.accountNo = value;
    }

    /**
     * Gets the value of the limit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLimit() {
        return limit;
    }

    /**
     * Sets the value of the limit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLimit(BigDecimal value) {
        this.limit = value;
    }

    /**
     * Gets the value of the interest property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getInterest() {
        return interest;
    }

    /**
     * Sets the value of the interest property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setInterest(BigDecimal value) {
        this.interest = value;
    }

    /**
     * Gets the value of the term property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTerm() {
        return term;
    }

    /**
     * Sets the value of the term property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTerm(BigDecimal value) {
        this.term = value;
    }

}
