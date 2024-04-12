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
 * <p>Java class for privilegeProjectCodeDataM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="privilegeProjectCodeDataM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="privilegeProjectCodeDocs" type="{http://webservice.ol.com/}privilegeProjectCodeDocDataM" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="privilegeProjectCodePrjCdes" type="{http://webservice.ol.com/}privilegeProjectCodeRccmdPrjCdeDataM" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="privilegeProjectCodeProductCCAs" type="{http://webservice.ol.com/}privilegeProjectCodeProductCCADataM" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="privilegeProjectCodeProductInsurances" type="{http://webservice.ol.com/}privilegeProjectCodeProductInsuranceDataM" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="privilegeProjectCodeProductSavings" type="{http://webservice.ol.com/}privilegeProjectCodeProductSavingDataM" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="projectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prvlgPrjCdeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="verResultId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "privilegeProjectCodeDataM", propOrder = {
    "privilegeProjectCodeDocs",
    "privilegeProjectCodePrjCdes",
    "privilegeProjectCodeProductCCAs",
    "privilegeProjectCodeProductInsurances",
    "privilegeProjectCodeProductSavings",
    "projectCode",
    "prvlgPrjCdeId",
    "verResultId"
})
public class PrivilegeProjectCodeDataM {

    @XmlElement(nillable = true)
    protected List<PrivilegeProjectCodeDocDataM> privilegeProjectCodeDocs;
    @XmlElement(nillable = true)
    protected List<PrivilegeProjectCodeRccmdPrjCdeDataM> privilegeProjectCodePrjCdes;
    @XmlElement(nillable = true)
    protected List<PrivilegeProjectCodeProductCCADataM> privilegeProjectCodeProductCCAs;
    @XmlElement(nillable = true)
    protected List<PrivilegeProjectCodeProductInsuranceDataM> privilegeProjectCodeProductInsurances;
    @XmlElement(nillable = true)
    protected List<PrivilegeProjectCodeProductSavingDataM> privilegeProjectCodeProductSavings;
    protected String projectCode;
    protected String prvlgPrjCdeId;
    protected String verResultId;

    /**
     * Gets the value of the privilegeProjectCodeDocs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the privilegeProjectCodeDocs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrivilegeProjectCodeDocs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrivilegeProjectCodeDocDataM }
     * 
     * 
     */
    public List<PrivilegeProjectCodeDocDataM> getPrivilegeProjectCodeDocs() {
        if (privilegeProjectCodeDocs == null) {
            privilegeProjectCodeDocs = new ArrayList<PrivilegeProjectCodeDocDataM>();
        }
        return this.privilegeProjectCodeDocs;
    }

    /**
     * Gets the value of the privilegeProjectCodePrjCdes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the privilegeProjectCodePrjCdes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrivilegeProjectCodePrjCdes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrivilegeProjectCodeRccmdPrjCdeDataM }
     * 
     * 
     */
    public List<PrivilegeProjectCodeRccmdPrjCdeDataM> getPrivilegeProjectCodePrjCdes() {
        if (privilegeProjectCodePrjCdes == null) {
            privilegeProjectCodePrjCdes = new ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM>();
        }
        return this.privilegeProjectCodePrjCdes;
    }

    /**
     * Gets the value of the privilegeProjectCodeProductCCAs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the privilegeProjectCodeProductCCAs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrivilegeProjectCodeProductCCAs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrivilegeProjectCodeProductCCADataM }
     * 
     * 
     */
    public List<PrivilegeProjectCodeProductCCADataM> getPrivilegeProjectCodeProductCCAs() {
        if (privilegeProjectCodeProductCCAs == null) {
            privilegeProjectCodeProductCCAs = new ArrayList<PrivilegeProjectCodeProductCCADataM>();
        }
        return this.privilegeProjectCodeProductCCAs;
    }

    /**
     * Gets the value of the privilegeProjectCodeProductInsurances property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the privilegeProjectCodeProductInsurances property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrivilegeProjectCodeProductInsurances().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrivilegeProjectCodeProductInsuranceDataM }
     * 
     * 
     */
    public List<PrivilegeProjectCodeProductInsuranceDataM> getPrivilegeProjectCodeProductInsurances() {
        if (privilegeProjectCodeProductInsurances == null) {
            privilegeProjectCodeProductInsurances = new ArrayList<PrivilegeProjectCodeProductInsuranceDataM>();
        }
        return this.privilegeProjectCodeProductInsurances;
    }

    /**
     * Gets the value of the privilegeProjectCodeProductSavings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the privilegeProjectCodeProductSavings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrivilegeProjectCodeProductSavings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrivilegeProjectCodeProductSavingDataM }
     * 
     * 
     */
    public List<PrivilegeProjectCodeProductSavingDataM> getPrivilegeProjectCodeProductSavings() {
        if (privilegeProjectCodeProductSavings == null) {
            privilegeProjectCodeProductSavings = new ArrayList<PrivilegeProjectCodeProductSavingDataM>();
        }
        return this.privilegeProjectCodeProductSavings;
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
     * Gets the value of the prvlgPrjCdeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrvlgPrjCdeId() {
        return prvlgPrjCdeId;
    }

    /**
     * Sets the value of the prvlgPrjCdeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrvlgPrjCdeId(String value) {
        this.prvlgPrjCdeId = value;
    }

    /**
     * Gets the value of the verResultId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerResultId() {
        return verResultId;
    }

    /**
     * Sets the value of the verResultId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerResultId(String value) {
        this.verResultId = value;
    }

}
