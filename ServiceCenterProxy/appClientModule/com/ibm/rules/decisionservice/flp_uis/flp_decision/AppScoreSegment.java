//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ibm.rules.decisionservice.flp_uis.flp_decision;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for appScoreSegment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="appScoreSegment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="appScores" type="{http://www.ibm.com/rules/decisionservice/Flp_uis/Flp_decision}appScore" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="criteria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialSegment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "appScoreSegment", propOrder = {
    "appScores",
    "criteria",
    "specialSegment"
})
public class AppScoreSegment {

    @XmlElement(nillable = true)
    protected List<AppScore> appScores;
    protected String criteria;
    protected String specialSegment;

    /**
     * Gets the value of the appScores property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the appScores property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAppScores().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AppScore }
     * 
     * 
     */
    public List<AppScore> getAppScores() {
        if (appScores == null) {
            appScores = new ArrayList<AppScore>();
        }
        return this.appScores;
    }

    /**
     * Gets the value of the criteria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCriteria() {
        return criteria;
    }

    /**
     * Sets the value of the criteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCriteria(String value) {
        this.criteria = value;
    }

    /**
     * Gets the value of the specialSegment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialSegment() {
        return specialSegment;
    }

    /**
     * Sets the value of the specialSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialSegment(String value) {
        this.specialSegment = value;
    }

}
