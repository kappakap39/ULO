//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ol.webservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for thaiBevPartner complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="thaiBevPartner">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="avgMonthlyPurchasing" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="citizenID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partnersince" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thaiBevMembershipNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "thaiBevPartner", propOrder = {
    "avgMonthlyPurchasing",
    "citizenID",
    "name",
    "partnersince",
    "surname",
    "thaiBevMembershipNo"
})
public class ThaiBevPartner {

    protected BigDecimal avgMonthlyPurchasing;
    protected String citizenID;
    protected String name;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar partnersince;
    protected String surname;
    protected String thaiBevMembershipNo;

    /**
     * Gets the value of the avgMonthlyPurchasing property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAvgMonthlyPurchasing() {
        return avgMonthlyPurchasing;
    }

    /**
     * Sets the value of the avgMonthlyPurchasing property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAvgMonthlyPurchasing(BigDecimal value) {
        this.avgMonthlyPurchasing = value;
    }

    /**
     * Gets the value of the citizenID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitizenID() {
        return citizenID;
    }

    /**
     * Sets the value of the citizenID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitizenID(String value) {
        this.citizenID = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the partnersince property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPartnersince() {
        return partnersince;
    }

    /**
     * Sets the value of the partnersince property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPartnersince(XMLGregorianCalendar value) {
        this.partnersince = value;
    }

    /**
     * Gets the value of the surname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Gets the value of the thaiBevMembershipNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThaiBevMembershipNo() {
        return thaiBevMembershipNo;
    }

    /**
     * Sets the value of the thaiBevMembershipNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThaiBevMembershipNo(String value) {
        this.thaiBevMembershipNo = value;
    }

}
