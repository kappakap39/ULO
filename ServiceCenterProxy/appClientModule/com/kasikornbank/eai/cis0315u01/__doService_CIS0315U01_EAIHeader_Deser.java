/**
 * __doService_CIS0315U01_EAIHeader_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0315u01;

public class __doService_CIS0315U01_EAIHeader_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public __doService_CIS0315U01_EAIHeader_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new __doService_CIS0315U01_EAIHeader();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_3_47) {
          ((__doService_CIS0315U01_EAIHeader)value).setServiceId(strValue);
          return true;}
        else if (qName==QName_3_48) {
          ((__doService_CIS0315U01_EAIHeader)value).setBeginTimestamp(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseDateTimeToCalendar(strValue));
          return true;}
        else if (qName==QName_3_49) {
          ((__doService_CIS0315U01_EAIHeader)value).setEndTimestamp(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseDateTimeToCalendar(strValue));
          return true;}
        else if (qName==QName_3_50) {
          ((__doService_CIS0315U01_EAIHeader)value).setTransactionId(strValue);
          return true;}
        else if (qName==QName_3_51) {
          ((__doService_CIS0315U01_EAIHeader)value).setSourceTransactionId(strValue);
          return true;}
        else if (qName==QName_3_52) {
          ((__doService_CIS0315U01_EAIHeader)value).setSourceSystem(strValue);
          return true;}
        else if (qName==QName_3_53) {
          ((__doService_CIS0315U01_EAIHeader)value).setUserId(strValue);
          return true;}
        else if (qName==QName_3_54) {
          ((__doService_CIS0315U01_EAIHeader)value).setPassword(strValue);
          return true;}
        else if (qName==QName_3_55) {
          ((__doService_CIS0315U01_EAIHeader)value).setStatus(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
          return true;}
        else if (qName==QName_3_56) {
          ((__doService_CIS0315U01_EAIHeader)value).setReasonCode(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
          return true;}
        else if (qName==QName_3_57) {
          ((__doService_CIS0315U01_EAIHeader)value).setMultiMsgFlg(strValue);
          return true;}
        else if (qName==QName_3_58) {
          ((__doService_CIS0315U01_EAIHeader)value).setOrderMsgTypeCode(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
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
    private final static javax.xml.namespace.QName QName_3_56 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "reasonCode");
    private final static javax.xml.namespace.QName QName_3_55 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "status");
    private final static javax.xml.namespace.QName QName_3_49 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "endTimestamp");
    private final static javax.xml.namespace.QName QName_3_58 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "orderMsgTypeCode");
    private final static javax.xml.namespace.QName QName_3_47 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "serviceId");
    private final static javax.xml.namespace.QName QName_3_51 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "sourceTransactionId");
    private final static javax.xml.namespace.QName QName_3_52 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "sourceSystem");
    private final static javax.xml.namespace.QName QName_3_53 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "userId");
    private final static javax.xml.namespace.QName QName_3_50 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "transactionId");
    private final static javax.xml.namespace.QName QName_3_54 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "password");
    private final static javax.xml.namespace.QName QName_3_48 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "beginTimestamp");
    private final static javax.xml.namespace.QName QName_3_57 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "multiMsgFlg");
}
