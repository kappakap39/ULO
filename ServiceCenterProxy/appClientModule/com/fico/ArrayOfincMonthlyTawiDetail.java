//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.fico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfincMonthlyTawiDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfincMonthlyTawiDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="incMonthlyTawiDetail" type="{http://www.fico.com}incMonthlyTawiDetail" maxOccurs="unbounded" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfincMonthlyTawiDetail", propOrder = {
    "incMonthlyTawiDetail"
})
public class ArrayOfincMonthlyTawiDetail
    implements Serializable
{

    @XmlElement(nillable = true)
    protected List<IncMonthlyTawiDetail> incMonthlyTawiDetail;

    /**
     * Gets the value of the incMonthlyTawiDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the incMonthlyTawiDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncMonthlyTawiDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IncMonthlyTawiDetail }
     * 
     * 
     */
    public List<IncMonthlyTawiDetail> getIncMonthlyTawiDetail() {
        if (incMonthlyTawiDetail == null) {
            incMonthlyTawiDetail = new ArrayList<IncMonthlyTawiDetail>();
        }
        return this.incMonthlyTawiDetail;
    }

}
