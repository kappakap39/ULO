/**
 * CIS1047O01_Type_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1047o01;

public class CIS1047O01_Type_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public CIS1047O01_Type_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new CIS1047O01_Type();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_0_0) {
          ((CIS1047O01_Type)value).setKBankHeader((com.kasikornbank.eai.cis1047o01.__doService_CIS1047O01_KBankHeader)objValue);
          return true;}
        else if (qName==QName_0_1) {
          ((CIS1047O01_Type)value).setCISHeader((com.kasikornbank.eai.cis1047o01.CISHeader)objValue);
          return true;}
        else if (qName==QName_0_2) {
          ((CIS1047O01_Type)value).setCISCustomer((com.kasikornbank.eai.cis1047o01.__doService_CIS1047O01_CISCustomer)objValue);
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_0 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "KBankHeader");
    private final static javax.xml.namespace.QName QName_0_2 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "CISCustomer");
    private final static javax.xml.namespace.QName QName_0_1 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "CISHeader");
}
