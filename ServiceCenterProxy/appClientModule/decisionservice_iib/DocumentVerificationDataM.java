//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package decisionservice_iib;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for documentVerificationDataM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="documentVerificationDataM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentVerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="followingType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="verResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "documentVerificationDataM", propOrder = {
    "documentVerId",
    "followingType",
    "verResult",
    "verResultId"
})
public class DocumentVerificationDataM {

    protected String documentVerId;
    protected String followingType;
    protected String verResult;
    protected String verResultId;

    /**
     * Gets the value of the documentVerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentVerId() {
        return documentVerId;
    }

    /**
     * Sets the value of the documentVerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentVerId(String value) {
        this.documentVerId = value;
    }

    /**
     * Gets the value of the followingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFollowingType() {
        return followingType;
    }

    /**
     * Sets the value of the followingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFollowingType(String value) {
        this.followingType = value;
    }

    /**
     * Gets the value of the verResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerResult() {
        return verResult;
    }

    /**
     * Sets the value of the verResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerResult(String value) {
        this.verResult = value;
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
