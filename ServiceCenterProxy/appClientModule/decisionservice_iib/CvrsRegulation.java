//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package decisionservice_iib;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cvrsRegulation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cvrsRegulation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="subTypCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="typCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cvrsRegulation", propOrder = {
    "subTypCode",
    "typCode"
})
public class CvrsRegulation {

    protected String subTypCode;
    protected String typCode;

    /**
     * Gets the value of the subTypCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubTypCode() {
        return subTypCode;
    }

    /**
     * Sets the value of the subTypCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubTypCode(String value) {
        this.subTypCode = value;
    }

    /**
     * Gets the value of the typCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypCode() {
        return typCode;
    }

    /**
     * Sets the value of the typCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypCode(String value) {
        this.typCode = value;
    }

}
