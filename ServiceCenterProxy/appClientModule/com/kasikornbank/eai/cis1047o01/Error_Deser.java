/**
 * Error_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1047o01;

public class Error_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public Error_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new Error();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_52) {
          ((Error)value).setErrorAppId(strValue);
          return true;}
        else if (qName==QName_0_53) {
          ((Error)value).setErrorAppAbbrv(strValue);
          return true;}
        else if (qName==QName_0_54) {
          ((Error)value).setErrorCode(strValue);
          return true;}
        else if (qName==QName_0_55) {
          ((Error)value).setErrorDesc(strValue);
          return true;}
        else if (qName==QName_0_56) {
          ((Error)value).setErrorSeverity(strValue);
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
    private final static javax.xml.namespace.QName QName_0_55 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "errorDesc");
    private final static javax.xml.namespace.QName QName_0_52 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "errorAppId");
    private final static javax.xml.namespace.QName QName_0_54 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "errorCode");
    private final static javax.xml.namespace.QName QName_0_53 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "errorAppAbbrv");
    private final static javax.xml.namespace.QName QName_0_56 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "errorSeverity");
}
