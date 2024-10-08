//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package decisionservice_iib;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for checkKPLDuplicateResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="checkKPLDuplicateResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="kBankHeader" type="{http://DecisionService_IIB}kBankHeader" minOccurs="0"/>
 *         &lt;element name="duplicateFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duplicateProducts" type="{http://DecisionService_IIB}duplicateProducts" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkKPLDuplicateResponse", propOrder = {
    "kBankHeader",
    "duplicateFlag",
    "duplicateProducts"
})
public class CheckKPLDuplicateResponse {

    protected KBankHeader kBankHeader;
    protected String duplicateFlag;
    protected List<DuplicateProducts> duplicateProducts;

    /**
     * Gets the value of the kBankHeader property.
     * 
     * @return
     *     possible object is
     *     {@link KBankHeader }
     *     
     */
    public KBankHeader getKBankHeader() {
        return kBankHeader;
    }

    /**
     * Sets the value of the kBankHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link KBankHeader }
     *     
     */
    public void setKBankHeader(KBankHeader value) {
        this.kBankHeader = value;
    }

    /**
     * Gets the value of the duplicateFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuplicateFlag() {
        return duplicateFlag;
    }

    /**
     * Sets the value of the duplicateFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuplicateFlag(String value) {
        this.duplicateFlag = value;
    }

    /**
     * Gets the value of the duplicateProducts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the duplicateProducts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDuplicateProducts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DuplicateProducts }
     * 
     * 
     */
    public List<DuplicateProducts> getDuplicateProducts() {
        if (duplicateProducts == null) {
            duplicateProducts = new ArrayList<DuplicateProducts>();
        }
        return this.duplicateProducts;
    }

}
