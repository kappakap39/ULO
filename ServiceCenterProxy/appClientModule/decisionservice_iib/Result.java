//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package decisionservice_iib;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.kasikornbank.manualfile.TUEF;


/**
 * <p>Java class for result complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="result">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TUEF" type="{http://kasikornbank.com/ManualFile}TUEF"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "result", propOrder = {
    "tuef"
})
public class Result {

    @XmlElement(name = "TUEF", required = true)
    protected TUEF tuef;

    /**
     * Gets the value of the tuef property.
     * 
     * @return
     *     possible object is
     *     {@link TUEF }
     *     
     */
    public TUEF getTUEF() {
        return tuef;
    }

    /**
     * Sets the value of the tuef property.
     * 
     * @param value
     *     allowed object is
     *     {@link TUEF }
     *     
     */
    public void setTUEF(TUEF value) {
        this.tuef = value;
    }

}
