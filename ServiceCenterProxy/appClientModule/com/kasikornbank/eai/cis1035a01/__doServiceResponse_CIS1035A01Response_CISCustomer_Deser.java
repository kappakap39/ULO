/**
 * __doServiceResponse_CIS1035A01Response_CISCustomer_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1035a01;

public class __doServiceResponse_CIS1035A01Response_CISCustomer_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public __doServiceResponse_CIS1035A01Response_CISCustomer_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new __doServiceResponse_CIS1035A01Response_CISCustomer();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_6_139) {
          ((__doServiceResponse_CIS1035A01Response_CISCustomer)value).setNum(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_6_198) {
          ((__doServiceResponse_CIS1035A01Response_CISCustomer)value).setContactsObj((com.kasikornbank.eai.cis1035a01.___doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj)objValue);
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_6_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_6_198 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "contactsObj");
}
