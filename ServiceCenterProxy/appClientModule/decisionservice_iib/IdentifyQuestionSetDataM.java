//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package decisionservice_iib;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for identifyQuestionSetDataM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="identifyQuestionSetDataM">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="callTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="questionSetCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="questionSetId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="questionSetType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "identifyQuestionSetDataM", propOrder = {
    "callTo",
    "questionSetCode",
    "questionSetId",
    "questionSetType",
    "verResultId"
})
public class IdentifyQuestionSetDataM {

    protected String callTo;
    protected String questionSetCode;
    protected String questionSetId;
    protected String questionSetType;
    protected String verResultId;

    /**
     * Gets the value of the callTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallTo() {
        return callTo;
    }

    /**
     * Sets the value of the callTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallTo(String value) {
        this.callTo = value;
    }

    /**
     * Gets the value of the questionSetCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionSetCode() {
        return questionSetCode;
    }

    /**
     * Sets the value of the questionSetCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionSetCode(String value) {
        this.questionSetCode = value;
    }

    /**
     * Gets the value of the questionSetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionSetId() {
        return questionSetId;
    }

    /**
     * Sets the value of the questionSetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionSetId(String value) {
        this.questionSetId = value;
    }

    /**
     * Gets the value of the questionSetType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionSetType() {
        return questionSetType;
    }

    /**
     * Sets the value of the questionSetType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionSetType(String value) {
        this.questionSetType = value;
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
