//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package decisionservice_iib;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.kasikornbank.manualfile.MonthEndBalance;


/**
 * <p>Java class for accountInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accountInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countOwner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currentBalance" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="holdBalance" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="limitOpenDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="monthEndBalance" type="{http://kasikornbank.com/ManualFile}monthEndBalance" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="openDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="productGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subAccountNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="useVerifiedIncome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accountInfo", propOrder = {
    "accountNo",
    "accountName",
    "countOwner",
    "currentBalance",
    "effectiveDate",
    "holdBalance",
    "limitOpenDate",
    "monthEndBalance",
    "openDate",
    "productGroup",
    "subAccountNo",
    "useVerifiedIncome"
})
public class AccountInfo {

    protected String accountNo;
    protected String accountName;
    protected String countOwner;
    protected BigDecimal currentBalance;
    protected String effectiveDate;
    protected BigDecimal holdBalance;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar limitOpenDate;
    protected List<MonthEndBalance> monthEndBalance;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar openDate;
    protected String productGroup;
    protected String subAccountNo;
    protected String useVerifiedIncome;

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
     * Gets the value of the countOwner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountOwner() {
        return countOwner;
    }

    /**
     * Sets the value of the countOwner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountOwner(String value) {
        this.countOwner = value;
    }

    /**
     * Gets the value of the currentBalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    /**
     * Sets the value of the currentBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurrentBalance(BigDecimal value) {
        this.currentBalance = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectiveDate(String value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the holdBalance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHoldBalance() {
        return holdBalance;
    }

    /**
     * Sets the value of the holdBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHoldBalance(BigDecimal value) {
        this.holdBalance = value;
    }

    /**
     * Gets the value of the limitOpenDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLimitOpenDate() {
        return limitOpenDate;
    }

    /**
     * Sets the value of the limitOpenDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLimitOpenDate(XMLGregorianCalendar value) {
        this.limitOpenDate = value;
    }

    /**
     * Gets the value of the monthEndBalance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monthEndBalance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonthEndBalance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonthEndBalance }
     * 
     * 
     */
    public List<MonthEndBalance> getMonthEndBalance() {
        if (monthEndBalance == null) {
            monthEndBalance = new ArrayList<MonthEndBalance>();
        }
        return this.monthEndBalance;
    }

    /**
     * Gets the value of the openDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOpenDate() {
        return openDate;
    }

    /**
     * Sets the value of the openDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOpenDate(XMLGregorianCalendar value) {
        this.openDate = value;
    }

    /**
     * Gets the value of the productGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductGroup() {
        return productGroup;
    }

    /**
     * Sets the value of the productGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductGroup(String value) {
        this.productGroup = value;
    }

    /**
     * Gets the value of the subAccountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubAccountNo() {
        return subAccountNo;
    }

    /**
     * Sets the value of the subAccountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubAccountNo(String value) {
        this.subAccountNo = value;
    }

    /**
     * Gets the value of the useVerifiedIncome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseVerifiedIncome() {
        return useVerifiedIncome;
    }

    /**
     * Sets the value of the useVerifiedIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseVerifiedIncome(String value) {
        this.useVerifiedIncome = value;
    }

}
