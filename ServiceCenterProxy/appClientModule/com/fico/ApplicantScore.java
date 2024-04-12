//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.fico;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for applicantScore complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="applicantScore">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="totalScore" type="{http://www.w3.org/2001/XMLSchema}int" form="qualified"/>
 *         &lt;element name="scoreModelID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="coarseRiskGrade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="fineRiskGrade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="constantScore" type="{http://www.w3.org/2001/XMLSchema}int" form="qualified"/>
 *         &lt;element name="AScoreFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="inProcess" type="{http://www.w3.org/2001/XMLSchema}int" form="qualified"/>
 *         &lt;element name="systemProductCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "applicantScore", propOrder = {
    "totalScore",
    "scoreModelID",
    "coarseRiskGrade",
    "fineRiskGrade",
    "constantScore",
    "aScoreFlag",
    "inProcess",
    "systemProductCode"
})
public class ApplicantScore
    implements Serializable
{

    protected int totalScore;
    protected String scoreModelID;
    protected String coarseRiskGrade;
    protected String fineRiskGrade;
    protected int constantScore;
    @XmlElement(name = "AScoreFlag")
    protected String aScoreFlag;
    protected int inProcess;
    protected String systemProductCode;

    /**
     * Gets the value of the totalScore property.
     * 
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Sets the value of the totalScore property.
     * 
     */
    public void setTotalScore(int value) {
        this.totalScore = value;
    }

    /**
     * Gets the value of the scoreModelID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScoreModelID() {
        return scoreModelID;
    }

    /**
     * Sets the value of the scoreModelID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScoreModelID(String value) {
        this.scoreModelID = value;
    }

    /**
     * Gets the value of the coarseRiskGrade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoarseRiskGrade() {
        return coarseRiskGrade;
    }

    /**
     * Sets the value of the coarseRiskGrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoarseRiskGrade(String value) {
        this.coarseRiskGrade = value;
    }

    /**
     * Gets the value of the fineRiskGrade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFineRiskGrade() {
        return fineRiskGrade;
    }

    /**
     * Sets the value of the fineRiskGrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFineRiskGrade(String value) {
        this.fineRiskGrade = value;
    }

    /**
     * Gets the value of the constantScore property.
     * 
     */
    public int getConstantScore() {
        return constantScore;
    }

    /**
     * Sets the value of the constantScore property.
     * 
     */
    public void setConstantScore(int value) {
        this.constantScore = value;
    }

    /**
     * Gets the value of the aScoreFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAScoreFlag() {
        return aScoreFlag;
    }

    /**
     * Sets the value of the aScoreFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAScoreFlag(String value) {
        this.aScoreFlag = value;
    }

    /**
     * Gets the value of the inProcess property.
     * 
     */
    public int getInProcess() {
        return inProcess;
    }

    /**
     * Sets the value of the inProcess property.
     * 
     */
    public void setInProcess(int value) {
        this.inProcess = value;
    }

    /**
     * Gets the value of the systemProductCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemProductCode() {
        return systemProductCode;
    }

    /**
     * Sets the value of the systemProductCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemProductCode(String value) {
        this.systemProductCode = value;
    }

}
