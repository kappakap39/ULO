//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ibm.rules.decisionservice.flp_uis.flp_decision;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for productLoan complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="productLoan">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="averageUtilize1" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="averageUtilize2" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="avgCreditLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="creditLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="debtBurden" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="maxDebtBurden" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="maxDebtBurdenCreditLimit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="portion" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="productId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "productLoan", propOrder = {
    "averageUtilize1",
    "averageUtilize2",
    "avgCreditLimit",
    "creditLimit",
    "debtBurden",
    "maxDebtBurden",
    "maxDebtBurdenCreditLimit",
    "portion",
    "productId"
})
public class ProductLoan {

    protected BigDecimal averageUtilize1;
    protected BigDecimal averageUtilize2;
    protected BigDecimal avgCreditLimit;
    protected BigDecimal creditLimit;
    protected BigDecimal debtBurden;
    protected BigDecimal maxDebtBurden;
    protected BigDecimal maxDebtBurdenCreditLimit;
    protected BigDecimal portion;
    protected String productId;

    /**
     * Gets the value of the averageUtilize1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAverageUtilize1() {
        return averageUtilize1;
    }

    /**
     * Sets the value of the averageUtilize1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAverageUtilize1(BigDecimal value) {
        this.averageUtilize1 = value;
    }

    /**
     * Gets the value of the averageUtilize2 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAverageUtilize2() {
        return averageUtilize2;
    }

    /**
     * Sets the value of the averageUtilize2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAverageUtilize2(BigDecimal value) {
        this.averageUtilize2 = value;
    }

    /**
     * Gets the value of the avgCreditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAvgCreditLimit() {
        return avgCreditLimit;
    }

    /**
     * Sets the value of the avgCreditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAvgCreditLimit(BigDecimal value) {
        this.avgCreditLimit = value;
    }

    /**
     * Gets the value of the creditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    /**
     * Sets the value of the creditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCreditLimit(BigDecimal value) {
        this.creditLimit = value;
    }

    /**
     * Gets the value of the debtBurden property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDebtBurden() {
        return debtBurden;
    }

    /**
     * Sets the value of the debtBurden property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDebtBurden(BigDecimal value) {
        this.debtBurden = value;
    }

    /**
     * Gets the value of the maxDebtBurden property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxDebtBurden() {
        return maxDebtBurden;
    }

    /**
     * Sets the value of the maxDebtBurden property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxDebtBurden(BigDecimal value) {
        this.maxDebtBurden = value;
    }

    /**
     * Gets the value of the maxDebtBurdenCreditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxDebtBurdenCreditLimit() {
        return maxDebtBurdenCreditLimit;
    }

    /**
     * Sets the value of the maxDebtBurdenCreditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxDebtBurdenCreditLimit(BigDecimal value) {
        this.maxDebtBurdenCreditLimit = value;
    }

    /**
     * Gets the value of the portion property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPortion() {
        return portion;
    }

    /**
     * Sets the value of the portion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPortion(BigDecimal value) {
        this.portion = value;
    }

    /**
     * Gets the value of the productId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the value of the productId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductId(String value) {
        this.productId = value;
    }

}
