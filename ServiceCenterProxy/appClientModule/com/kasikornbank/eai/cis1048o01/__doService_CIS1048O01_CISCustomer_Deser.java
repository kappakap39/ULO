/**
 * __doService_CIS1048O01_CISCustomer_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1048o01;

public class __doService_CIS1048O01_CISCustomer_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public __doService_CIS1048O01_CISCustomer_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new __doService_CIS1048O01_CISCustomer();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_20_196) {
          ((__doService_CIS1048O01_CISCustomer)value).setValidationFlag(strValue);
          return true;}
        else if (qName==QName_20_38) {
          ((__doService_CIS1048O01_CISCustomer)value).setTypeCode(strValue);
          return true;}
        else if (qName==QName_20_26) {
          ((__doService_CIS1048O01_CISCustomer)value).setNum(strValue);
          return true;}
        else if (qName==QName_20_261) {
          ((__doService_CIS1048O01_CISCustomer)value).setAcctNum(strValue);
          return true;}
        else if (qName==QName_20_263) {
          ((__doService_CIS1048O01_CISCustomer)value).setProspectFlag(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_20_27) {
          ((__doService_CIS1048O01_CISCustomer)value).setContactAddrObj((com.kasikornbank.eai.cis1048o01.ContactAddrObj)objValue);
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_20_27 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1",
                  "contactAddrObj");
    private final static javax.xml.namespace.QName QName_20_26 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_20_38 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1",
                  "typeCode");
    private final static javax.xml.namespace.QName QName_20_261 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1",
                  "acctNum");
    private final static javax.xml.namespace.QName QName_20_196 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1",
                  "validationFlag");
    private final static javax.xml.namespace.QName QName_20_263 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1",
                  "prospectFlag");
}
