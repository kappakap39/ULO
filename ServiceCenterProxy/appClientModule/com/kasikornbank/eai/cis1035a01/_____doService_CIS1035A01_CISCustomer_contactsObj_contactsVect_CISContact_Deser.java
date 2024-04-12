/**
 * _____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1035a01;

public class _____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public _____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new _____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_6_138) {
          ((_____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact)value).setTypeCode(strValue);
          return true;}
        else if (qName==QName_6_203) {
          ((_____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact)value).setLocationCode(strValue);
          return true;}
        else if (qName==QName_6_204) {
          ((_____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact)value).setPhNum(strValue);
          return true;}
        else if (qName==QName_6_205) {
          ((_____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact)value).setPhExtNum(strValue);
          return true;}
        else if (qName==QName_6_206) {
          ((_____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact)value).setPhAvailTimeCde(strValue);
          return true;}
        else if (qName==QName_6_207) {
          ((_____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact)value).setName(strValue);
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
    private final static javax.xml.namespace.QName QName_6_203 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "locationCode");
    private final static javax.xml.namespace.QName QName_6_205 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "phExtNum");
    private final static javax.xml.namespace.QName QName_6_204 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "phNum");
    private final static javax.xml.namespace.QName QName_6_206 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "phAvailTimeCde");
    private final static javax.xml.namespace.QName QName_6_207 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "name");
    private final static javax.xml.namespace.QName QName_6_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "typeCode");
}
