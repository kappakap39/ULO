//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package decisionservice_iib;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monthlyTawi50DetailDataM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monthlyTawi50DetailDataM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="month" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monthlyTawiDetailId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monthlyTawiId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="year" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monthlyTawi50DetailDataM", propOrder = {
    "amount",
    "month",
    "monthlyTawiDetailId",
    "monthlyTawiId",
    "seq",
    "year"
})
public class MonthlyTawi50DetailDataM {

    protected BigDecimal amount;
    protected String month;
    protected String monthlyTawiDetailId;
    protected String monthlyTawiId;
    protected int seq;
    protected String year;

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonth(String value) {
        this.month = value;
    }

    /**
     * Gets the value of the monthlyTawiDetailId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonthlyTawiDetailId() {
        return monthlyTawiDetailId;
    }

    /**
     * Sets the value of the monthlyTawiDetailId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonthlyTawiDetailId(String value) {
        this.monthlyTawiDetailId = value;
    }

    /**
     * Gets the value of the monthlyTawiId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonthlyTawiId() {
        return monthlyTawiId;
    }

    /**
     * Sets the value of the monthlyTawiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonthlyTawiId(String value) {
        this.monthlyTawiId = value;
    }

    /**
     * Gets the value of the seq property.
     * 
     */
    public int getSeq() {
        return seq;
    }

    /**
     * Sets the value of the seq property.
     * 
     */
    public void setSeq(int value) {
        this.seq = value;
    }

    /**
     * Gets the value of the year property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYear(String value) {
        this.year = value;
    }

}
