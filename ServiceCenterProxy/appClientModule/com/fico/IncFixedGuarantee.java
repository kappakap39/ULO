//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.fico;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for incFixedGuarantee complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="incFixedGuarantee">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="sub" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="accountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="retentionDate" type="{http://www.w3.org/2001/XMLSchema}date" form="qualified"/>
 *         &lt;element name="branchNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="retentionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="retentionAmt" type="{http://www.w3.org/2001/XMLSchema}decimal" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "incFixedGuarantee", propOrder = {
    "accountNo",
    "sub",
    "accountName",
    "retentionDate",
    "branchNo",
    "retentionType",
    "retentionAmt"
})
public class IncFixedGuarantee
    implements Serializable
{

    protected String accountNo;
    protected String sub;
    protected String accountName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar retentionDate;
    protected String branchNo;
    protected String retentionType;
    @XmlElement(required = true)
    protected BigDecimal retentionAmt;

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
     * Gets the value of the sub property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSub() {
        return sub;
    }

    /**
     * Sets the value of the sub property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSub(String value) {
        this.sub = value;
    }

    /**
     * Gets the value of the accountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets the value of the accountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountName(String value) {
        this.accountName = value;
    }

    /**
     * Gets the value of the retentionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRetentionDate() {
        return retentionDate;
    }

    /**
     * Sets the value of the retentionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRetentionDate(XMLGregorianCalendar value) {
        this.retentionDate = value;
    }

    /**
     * Gets the value of the branchNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchNo() {
        return branchNo;
    }

    /**
     * Sets the value of the branchNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchNo(String value) {
        this.branchNo = value;
    }

    /**
     * Gets the value of the retentionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetentionType() {
        return retentionType;
    }

    /**
     * Sets the value of the retentionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetentionType(String value) {
        this.retentionType = value;
    }

    /**
     * Gets the value of the retentionAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRetentionAmt() {
        return retentionAmt;
    }

    /**
     * Sets the value of the retentionAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRetentionAmt(BigDecimal value) {
        this.retentionAmt = value;
    }

}
