//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ibm.rules.decisionservice.flp_uis.flp_decision;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oldIncomeSegment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oldIncomeSegment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oldIncomes" type="{http://www.ibm.com/rules/decisionservice/Flp_uis/Flp_decision}oldIncome" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oldIncomeSegment", propOrder = {
    "oldIncomes"
})
public class OldIncomeSegment {

    @XmlElement(nillable = true)
    protected List<OldIncome> oldIncomes;

    /**
     * Gets the value of the oldIncomes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oldIncomes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOldIncomes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OldIncome }
     * 
     * 
     */
    public List<OldIncome> getOldIncomes() {
        if (oldIncomes == null) {
            oldIncomes = new ArrayList<OldIncome>();
        }
        return this.oldIncomes;
    }

}
