//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.fico;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for incKvi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="incKvi">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="percentChequeReturn" type="{http://www.w3.org/2001/XMLSchema}decimal" form="qualified"/>
 *         &lt;element name="verifiedIncome" type="{http://www.w3.org/2001/XMLSchema}decimal" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "incKvi", propOrder = {
    "percentChequeReturn",
    "verifiedIncome"
})
public class IncKvi
    implements Serializable
{

    @XmlElement(required = true)
    protected BigDecimal percentChequeReturn;
    @XmlElement(required = true)
    protected BigDecimal verifiedIncome;

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
