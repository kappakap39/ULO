//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package decisionservice_iib;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bolFinancialStatement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bolFinancialStatement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="financialDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="financialId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="financialType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="netProfitAndLoss" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seq" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="totalIncome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bolFinancialStatement", propOrder = {
    "financialDate",
    "financialId",
    "financialType",
    "netProfitAndLoss",
    "orgId",
    "postDate",
    "seq",
    "totalIncome"
})
public class BolFinancialStatement {

    protected String financialDate;
    protected String financialId;
    protected String financialType;
    protected String netProfitAndLoss;
    protected String orgId;
    protected String postDate;
    @XmlElement(required = true)
    protected String seq;
    protected String totalIncome;

    /**
     * Gets the value of the financialDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinancialDate() {
        return financialDate;
    }

    /**
     * Sets the value of the financialDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinancialDate(String value) {
        this.financialDate = value;
    }

    /**
     * Gets the value of the financialId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinancialId() {
        return financialId;
    }

    /**
     * Sets the value of the financialId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinancialId(String value) {
        this.financialId = value;
    }

    /**
     * Gets the value of the financialType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinancialType() {
        return financialType;
    }

    /**
     * Sets the value of the financialType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinancialType(String value) {
        this.financialType = value;
    }

    /**
     * Gets the value of the netProfitAndLoss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetProfitAndLoss() {
        return netProfitAndLoss;
    }

    /**
     * Sets the value of the netProfitAndLoss property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetProfitAndLoss(String value) {
        this.netProfitAndLoss = value;
    }

    /**
     * Gets the value of the orgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * Sets the value of the orgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgId(String value) {
        this.orgId = value;
    }

    /**
     * Gets the value of the postDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostDate() {
        return postDate;
    }

    /**
     * Sets the value of the postDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostDate(String value) {
        this.postDate = value;
    }

    /**
     * Gets the value of the seq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeq() {
        return seq;
    }

    /**
     * Sets the value of the seq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeq(String value) {
        this.seq = value;
    }

    /**
     * Gets the value of the totalIncome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalIncome() {
        return totalIncome;
    }

    /**
     * Sets the value of the totalIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalIncome(String value) {
        this.totalIncome = value;
    }

}
