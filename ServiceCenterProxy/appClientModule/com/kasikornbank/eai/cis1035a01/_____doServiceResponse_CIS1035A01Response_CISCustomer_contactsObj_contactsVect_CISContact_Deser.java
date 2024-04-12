/**
 * _____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1035a01;

public class _____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public _____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new _____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_6_139) {
          ((_____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact)value).setNum(strValue);
          return true;}
        else if (qName==QName_6_138) {
          ((_____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact)value).setTypeCode(strValue);
          return true;}
        else if (qName==QName_6_203) {
          ((_____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact)value).setLocationCode(strValue);
          return true;}
        else if (qName==QName_6_204) {
          ((_____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact)value).setPhNum(strValue);
          return true;}
        else if (qName==QName_6_205) {
          ((_____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact)value).setPhExtNum(strValue);
          return true;}
        else if (qName==QName_6_213) {
          ((_____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact)value).setSeqNum(strValue);
          return true;}
        else if (qName==QName_6_214) {
          ((_____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact)value).setSuccessFlag(strValue);
          return true;}
        else if (qName==QName_6_49) {
          ((_____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact)value).setErrorDesc(strValue);
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
    private final static javax.xml.namespace.QName QName_6_49 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "errorDesc");
    private final static javax.xml.namespace.QName QName_6_203 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "locationCode");
    private final static javax.xml.namespace.QName QName_6_205 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "phExtNum");
    private final static javax.xml.namespace.QName QName_6_213 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "seqNum");
    private final static javax.xml.namespace.QName QName_6_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_6_204 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "phNum");
    private final static javax.xml.namespace.QName QName_6_214 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "successFlag");
    private final static javax.xml.namespace.QName QName_6_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "typeCode");
}
