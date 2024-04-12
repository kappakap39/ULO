/**
 * __doServiceResponse_CIS1037A01Response_CISIndivCust_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1037a01;

public class __doServiceResponse_CIS1037A01Response_CISIndivCust_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public __doServiceResponse_CIS1037A01Response_CISIndivCust_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new __doServiceResponse_CIS1037A01Response_CISIndivCust();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_61) {
          ((__doServiceResponse_CIS1037A01Response_CISIndivCust)value).setNum(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_0_33) {
          ((__doServiceResponse_CIS1037A01Response_CISIndivCust)value).setContactAddrObj((com.kasikornbank.eai.cis1037a01.___doServiceResponse_CIS1037A01Response_CISIndivCust_contactAddrObj)objValue);
          return true;}
        else if (qName==QName_0_34) {
          ((__doServiceResponse_CIS1037A01Response_CISIndivCust)value).setContactsObj((com.kasikornbank.eai.cis1037a01.___doServiceResponse_CIS1037A01Response_CISIndivCust_contactsObj)objValue);
          return true;}
        else if (qName==QName_0_153) {
          ((__doServiceResponse_CIS1037A01Response_CISIndivCust)value).setCVRSInfoObj((com.kasikornbank.eai.cis1037a01.CVRSInfoObj)objValue);
          return true;}
        else if (qName==QName_0_53) {
          ((__doServiceResponse_CIS1037A01Response_CISIndivCust)value).setKYCObj((com.kasikornbank.eai.cis1037a01.___doServiceResponse_CIS1037A01Response_CISIndivCust_KYCObj)objValue);
          return true;}
        else if (qName==QName_0_65) {
          ((__doServiceResponse_CIS1037A01Response_CISIndivCust)value).setOffclAddrObj((com.kasikornbank.eai.cis1037a01.___doServiceResponse_CIS1037A01Response_CISIndivCust_offclAddrObj)objValue);
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_153 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "CVRSInfoObj");
    private final static javax.xml.namespace.QName QName_0_61 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_0_53 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "KYCObj");
    private final static javax.xml.namespace.QName QName_0_33 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "contactAddrObj");
    private final static javax.xml.namespace.QName QName_0_65 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "offclAddrObj");
    private final static javax.xml.namespace.QName QName_0_34 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "contactsObj");
}
