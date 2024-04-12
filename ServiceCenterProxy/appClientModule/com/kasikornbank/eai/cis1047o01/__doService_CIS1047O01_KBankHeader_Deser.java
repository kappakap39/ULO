/**
 * __doService_CIS1047O01_KBankHeader_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1047o01;

public class __doService_CIS1047O01_KBankHeader_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public __doService_CIS1047O01_KBankHeader_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new __doService_CIS1047O01_KBankHeader();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_6) {
          ((__doService_CIS1047O01_KBankHeader)value).setFuncNm(strValue);
          return true;}
        else if (qName==QName_0_7) {
          ((__doService_CIS1047O01_KBankHeader)value).setRqUID(strValue);
          return true;}
        else if (qName==QName_0_8) {
          ((__doService_CIS1047O01_KBankHeader)value).setRqDt(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseDateTimeToCalendar(strValue));
          return true;}
        else if (qName==QName_0_9) {
          ((__doService_CIS1047O01_KBankHeader)value).setRqAppId(strValue);
          return true;}
        else if (qName==QName_0_10) {
          ((__doService_CIS1047O01_KBankHeader)value).setUserId(strValue);
          return true;}
        else if (qName==QName_0_11) {
          ((__doService_CIS1047O01_KBankHeader)value).setTerminalId(strValue);
          return true;}
        else if (qName==QName_0_12) {
          ((__doService_CIS1047O01_KBankHeader)value).setUserLangPref(strValue);
          return true;}
        else if (qName==QName_0_13) {
          ((__doService_CIS1047O01_KBankHeader)value).setCorrID(strValue);
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
    private final static javax.xml.namespace.QName QName_0_9 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "rqAppId");
    private final static javax.xml.namespace.QName QName_0_10 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "userId");
    private final static javax.xml.namespace.QName QName_0_11 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "terminalId");
    private final static javax.xml.namespace.QName QName_0_13 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "corrID");
    private final static javax.xml.namespace.QName QName_0_12 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "userLangPref");
    private final static javax.xml.namespace.QName QName_0_6 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "funcNm");
    private final static javax.xml.namespace.QName QName_0_8 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "rqDt");
    private final static javax.xml.namespace.QName QName_0_7 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "rqUID");
}
