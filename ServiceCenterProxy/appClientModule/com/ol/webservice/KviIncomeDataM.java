//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ol.webservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for kviIncomeDataM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kviIncomeDataM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="kviFid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kviId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="percentChequeReturn" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tokenId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="verifiedIncome" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kviIncomeDataM", propOrder = {
    "kviFid",
    "kviId",
    "percentChequeReturn",
    "tokenId",
    "verifiedIncome"
})
public class KviIncomeDataM {

    protected String kviFid;
    protected String kviId;
    protected BigDecimal percentChequeReturn;
    protected String tokenId;
    protected BigDecimal verifiedIncome;

    /**
     * Gets the value of the kviFid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKviFid() {
        return kviFid;
    }

    /**
     * Sets the value of the kviFid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKviFid(String value) {
        this.kviFid = value;
    }

    /**
     * Gets the value of the kviId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKviId() {
        return kviId;
    }

    /**
     * Sets the value of the kviId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKviId(String value) {
        this.kviId = value;
    }

    /**
     * Gets the value of the percentChequeReturn property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPercentChequeReturn() {
        return percentChequeReturn;
    }

    /**
     * Sets the value of the percentChequeReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPercentChequeReturn(BigDecimal value) {
        this.percentChequeReturn = value;
    }

    /**
     * Gets the value of the tokenId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * Sets the value of the tokenId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenId(String value) {
        this.tokenId = value;
    }

    /**
     * Gets the value of the verifiedIncome property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVerifiedIncome() {
        return verifiedIncome;
    }

    /**
     * Sets the value of the verifiedIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVerifiedIncome(BigDecimal value) {
        this.verifiedIncome = value;
    }

}
