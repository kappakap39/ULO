//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.kasikornbank.eai.eaiws.cis1044u01.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for doServiceResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="doServiceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CIS1044U01Response">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="KBankHeader">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="funcNm" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength30"/>
 *                             &lt;element name="rqUID" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength47"/>
 *                             &lt;element name="rsAppId" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
 *                             &lt;element name="rsUID" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength47"/>
 *                             &lt;element name="rsDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                             &lt;element name="statusCode" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength2"/>
 *                             &lt;element name="errorVect" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="error" maxOccurs="10" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="errorAppId" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
 *                                                 &lt;element name="errorAppAbbrv" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
 *                                                 &lt;element name="errorCode" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength30"/>
 *                                                 &lt;element name="errorDesc" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength256"/>
 *                                                 &lt;element name="errorSeverity" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength2"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="corrID" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength32" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="CISCustomer" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="num" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength10"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "doServiceResponse", propOrder = {
    "cis1044U01Response"
})
public class DoServiceResponse {

    @XmlElement(name = "CIS1044U01Response", required = true)
    protected DoServiceResponse.CIS1044U01Response cis1044U01Response;

    /**
     * Gets the value of the cis1044U01Response property.
     * 
     * @return
     *     possible object is
     *     {@link DoServiceResponse.CIS1044U01Response }
     *     
     */
    public DoServiceResponse.CIS1044U01Response getCIS1044U01Response() {
        return cis1044U01Response;
    }

    /**
     * Sets the value of the cis1044U01Response property.
     * 
     * @param value
     *     allowed object is
     *     {@link DoServiceResponse.CIS1044U01Response }
     *     
     */
    public void setCIS1044U01Response(DoServiceResponse.CIS1044U01Response value) {
        this.cis1044U01Response = value;
    }


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
     *         &lt;element name="KBankHeader">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="funcNm" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength30"/>
     *                   &lt;element name="rqUID" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength47"/>
     *                   &lt;element name="rsAppId" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
     *                   &lt;element name="rsUID" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength47"/>
     *                   &lt;element name="rsDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="statusCode" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength2"/>
     *                   &lt;element name="errorVect" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="error" maxOccurs="10" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="errorAppId" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
     *                                       &lt;element name="errorAppAbbrv" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
     *                                       &lt;element name="errorCode" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength30"/>
     *                                       &lt;element name="errorDesc" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength256"/>
     *                                       &lt;element name="errorSeverity" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength2"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="corrID" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength32" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="CISCustomer" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="num" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength10"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "kBankHeader",
        "cisCustomer"
    })
    public static class CIS1044U01Response {

        @XmlElement(name = "KBankHeader", required = true)
        protected DoServiceResponse.CIS1044U01Response.KBankHeader kBankHeader;
        @XmlElement(name = "CISCustomer")
        protected DoServiceResponse.CIS1044U01Response.CISCustomer cisCustomer;

        /**
         * Gets the value of the kBankHeader property.
         * 
         * @return
         *     possible object is
         *     {@link DoServiceResponse.CIS1044U01Response.KBankHeader }
         *     
         */
        public DoServiceResponse.CIS1044U01Response.KBankHeader getKBankHeader() {
            return kBankHeader;
        }

        /**
         * Sets the value of the kBankHeader property.
         * 
         * @param value
         *     allowed object is
         *     {@link DoServiceResponse.CIS1044U01Response.KBankHeader }
         *     
         */
        public void setKBankHeader(DoServiceResponse.CIS1044U01Response.KBankHeader value) {
            this.kBankHeader = value;
        }

        /**
         * Gets the value of the cisCustomer property.
         * 
         * @return
         *     possible object is
         *     {@link DoServiceResponse.CIS1044U01Response.CISCustomer }
         *     
         */
        public DoServiceResponse.CIS1044U01Response.CISCustomer getCISCustomer() {
            return cisCustomer;
        }

        /**
         * Sets the value of the cisCustomer property.
         * 
         * @param value
         *     allowed object is
         *     {@link DoServiceResponse.CIS1044U01Response.CISCustomer }
         *     
         */
        public void setCISCustomer(DoServiceResponse.CIS1044U01Response.CISCustomer value) {
            this.cisCustomer = value;
        }


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
         *         &lt;element name="num" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength10"/>
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
            "num"
        })
        public static class CISCustomer {

            @XmlElement(required = true, nillable = true)
            protected String num;

            /**
             * Gets the value of the num property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNum() {
                return num;
            }

            /**
             * Sets the value of the num property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNum(String value) {
                this.num = value;
            }

        }


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
         *         &lt;element name="funcNm" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength30"/>
         *         &lt;element name="rqUID" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength47"/>
         *         &lt;element name="rsAppId" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
         *         &lt;element name="rsUID" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength47"/>
         *         &lt;element name="rsDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="statusCode" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength2"/>
         *         &lt;element name="errorVect" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="error" maxOccurs="10" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="errorAppId" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
         *                             &lt;element name="errorAppAbbrv" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
         *                             &lt;element name="errorCode" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength30"/>
         *                             &lt;element name="errorDesc" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength256"/>
         *                             &lt;element name="errorSeverity" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength2"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="corrID" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength32" minOccurs="0"/>
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
            "funcNm",
            "rqUID",
            "rsAppId",
            "rsUID",
            "rsDt",
            "statusCode",
            "errorVect",
            "corrID"
        })
        public static class KBankHeader {

            @XmlElement(required = true, nillable = true)
            protected String funcNm;
            @XmlElement(required = true, nillable = true)
            protected String rqUID;
            @XmlElement(required = true, nillable = true)
            protected String rsAppId;
            @XmlElement(required = true, nillable = true)
            protected String rsUID;
            @XmlElement(required = true, nillable = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar rsDt;
            @XmlElement(required = true, nillable = true)
            protected String statusCode;
            protected DoServiceResponse.CIS1044U01Response.KBankHeader.ErrorVect errorVect;
            @XmlElementRef(name = "corrID", namespace = "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1", type = JAXBElement.class, required = false)
            protected JAXBElement<String> corrID;

            /**
             * Gets the value of the funcNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFuncNm() {
                return funcNm;
            }

            /**
             * Sets the value of the funcNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFuncNm(String value) {
                this.funcNm = value;
            }

            /**
             * Gets the value of the rqUID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRqUID() {
                return rqUID;
            }

            /**
             * Sets the value of the rqUID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRqUID(String value) {
                this.rqUID = value;
            }

            /**
             * Gets the value of the rsAppId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRsAppId() {
                return rsAppId;
            }

            /**
             * Sets the value of the rsAppId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRsAppId(String value) {
                this.rsAppId = value;
            }

            /**
             * Gets the value of the rsUID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRsUID() {
                return rsUID;
            }

            /**
             * Sets the value of the rsUID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRsUID(String value) {
                this.rsUID = value;
            }

            /**
             * Gets the value of the rsDt property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getRsDt() {
                return rsDt;
            }

            /**
             * Sets the value of the rsDt property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setRsDt(XMLGregorianCalendar value) {
                this.rsDt = value;
            }

            /**
             * Gets the value of the statusCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStatusCode() {
                return statusCode;
            }

            /**
             * Sets the value of the statusCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStatusCode(String value) {
                this.statusCode = value;
            }

            /**
             * Gets the value of the errorVect property.
             * 
             * @return
             *     possible object is
             *     {@link DoServiceResponse.CIS1044U01Response.KBankHeader.ErrorVect }
             *     
             */
            public DoServiceResponse.CIS1044U01Response.KBankHeader.ErrorVect getErrorVect() {
                return errorVect;
            }

            /**
             * Sets the value of the errorVect property.
             * 
             * @param value
             *     allowed object is
             *     {@link DoServiceResponse.CIS1044U01Response.KBankHeader.ErrorVect }
             *     
             */
            public void setErrorVect(DoServiceResponse.CIS1044U01Response.KBankHeader.ErrorVect value) {
                this.errorVect = value;
            }

            /**
             * Gets the value of the corrID property.
             * 
             * @return
             *     possible object is
             *     {@link JAXBElement }{@code <}{@link String }{@code >}
             *     
             */
            public JAXBElement<String> getCorrID() {
                return corrID;
            }

            /**
             * Sets the value of the corrID property.
             * 
             * @param value
             *     allowed object is
             *     {@link JAXBElement }{@code <}{@link String }{@code >}
             *     
             */
            public void setCorrID(JAXBElement<String> value) {
                this.corrID = ((JAXBElement<String> ) value);
            }


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
             *         &lt;element name="error" maxOccurs="10" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="errorAppId" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
             *                   &lt;element name="errorAppAbbrv" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
             *                   &lt;element name="errorCode" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength30"/>
             *                   &lt;element name="errorDesc" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength256"/>
             *                   &lt;element name="errorSeverity" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength2"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
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
                "error"
            })
            public static class ErrorVect {

                protected List<DoServiceResponse.CIS1044U01Response.KBankHeader.ErrorVect.Error> error;

                /**
                 * Gets the value of the error property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the error property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getError().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link DoServiceResponse.CIS1044U01Response.KBankHeader.ErrorVect.Error }
                 * 
                 * 
                 */
                public List<DoServiceResponse.CIS1044U01Response.KBankHeader.ErrorVect.Error> getError() {
                    if (error == null) {
                        error = new ArrayList<DoServiceResponse.CIS1044U01Response.KBankHeader.ErrorVect.Error>();
                    }
                    return this.error;
                }


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
                 *         &lt;element name="errorAppId" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
                 *         &lt;element name="errorAppAbbrv" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength5"/>
                 *         &lt;element name="errorCode" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength30"/>
                 *         &lt;element name="errorDesc" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength256"/>
                 *         &lt;element name="errorSeverity" type="{http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1}stringLength2"/>
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
                    "errorAppId",
                    "errorAppAbbrv",
                    "errorCode",
                    "errorDesc",
                    "errorSeverity"
                })
                public static class Error {

                    @XmlElement(required = true, nillable = true)
                    protected String errorAppId;
                    @XmlElement(required = true, nillable = true)
                    protected String errorAppAbbrv;
                    @XmlElement(required = true, nillable = true)
                    protected String errorCode;
                    @XmlElement(required = true, nillable = true)
                    protected String errorDesc;
                    @XmlElement(required = true, nillable = true)
                    protected String errorSeverity;

                    /**
                     * Gets the value of the errorAppId property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getErrorAppId() {
                        return errorAppId;
                    }

                    /**
                     * Sets the value of the errorAppId property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setErrorAppId(String value) {
                        this.errorAppId = value;
                    }

                    /**
                     * Gets the value of the errorAppAbbrv property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getErrorAppAbbrv() {
                        return errorAppAbbrv;
                    }

                    /**
                     * Sets the value of the errorAppAbbrv property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setErrorAppAbbrv(String value) {
                        this.errorAppAbbrv = value;
                    }

                    /**
                     * Gets the value of the errorCode property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getErrorCode() {
                        return errorCode;
                    }

                    /**
                     * Sets the value of the errorCode property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setErrorCode(String value) {
                        this.errorCode = value;
                    }

                    /**
                     * Gets the value of the errorDesc property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getErrorDesc() {
                        return errorDesc;
                    }

                    /**
                     * Sets the value of the errorDesc property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setErrorDesc(String value) {
                        this.errorDesc = value;
                    }

                    /**
                     * Gets the value of the errorSeverity property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getErrorSeverity() {
                        return errorSeverity;
                    }

                    /**
                     * Sets the value of the errorSeverity property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setErrorSeverity(String value) {
                        this.errorSeverity = value;
                    }

                }

            }

        }

    }

}
