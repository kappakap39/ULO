/**
 * _____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1037a01;

public class _____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public _____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new _____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_128) {
          ((_____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact)value).setLocationCode(strValue);
          return true;}
        else if (qName==QName_0_129) {
          ((_____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact)value).setPhAvailTimeCde(strValue);
          return true;}
        else if (qName==QName_0_130) {
          ((_____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact)value).setPhExtNum(strValue);
          return true;}
        else if (qName==QName_0_131) {
          ((_____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact)value).setPhNum(strValue);
          return true;}
        else if (qName==QName_0_83) {
          ((_____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact)value).setTypeCode(strValue);
          return true;}
        else if (qName==QName_0_115) {
          ((_____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact)value).setName(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_128 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "locationCode");
    private final static javax.xml.namespace.QName QName_0_130 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "phExtNum");
    private final static javax.xml.namespace.QName QName_0_131 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "phNum");
    private final static javax.xml.namespace.QName QName_0_115 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "name");
    private final static javax.xml.namespace.QName QName_0_83 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "typeCode");
    private final static javax.xml.namespace.QName QName_0_129 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "phAvailTimeCde");
}
