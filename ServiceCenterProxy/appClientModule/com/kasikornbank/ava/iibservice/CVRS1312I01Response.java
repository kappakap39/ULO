//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.kasikornbank.ava.iibservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import decisionservice_iib.ApplicationGroupDataM;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CVRS1312I01Response" type="{http://DecisionService_IIB}applicationGroupDataM"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cvrs1312I01Response"
})
@XmlRootElement(name = "CVRS1312I01Response")
public class CVRS1312I01Response {

    @XmlElement(name = "CVRS1312I01Response", required = true, nillable = true)
    protected ApplicationGroupDataM cvrs1312I01Response;

    /**
     * Gets the value of the cvrs1312I01Response property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationGroupDataM }
     *     
     */
    public ApplicationGroupDataM getCVRS1312I01Response() {
        return cvrs1312I01Response;
    }

    /**
     * Sets the value of the cvrs1312I01Response property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationGroupDataM }
     *     
     */
    public void setCVRS1312I01Response(ApplicationGroupDataM value) {
        this.cvrs1312I01Response = value;
    }

}
