/**
 * __doService_CIS0314I01_EAIHeader_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0314i01;

public class __doService_CIS0314I01_EAIHeader_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public __doService_CIS0314I01_EAIHeader_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new __doService_CIS0314I01_EAIHeader();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_3_97) {
          ((__doService_CIS0314I01_EAIHeader)value).setServiceId(strValue);
          return true;}
        else if (qName==QName_3_98) {
          ((__doService_CIS0314I01_EAIHeader)value).setBeginTimestamp(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseDateTimeToCalendar(strValue));
          return true;}
        else if (qName==QName_3_99) {
          ((__doService_CIS0314I01_EAIHeader)value).setEndTimestamp(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseDateTimeToCalendar(strValue));
          return true;}
        else if (qName==QName_3_100) {
          ((__doService_CIS0314I01_EAIHeader)value).setTransactionId(strValue);
          return true;}
        else if (qName==QName_3_101) {
          ((__doService_CIS0314I01_EAIHeader)value).setSourceTransactionId(strValue);
          return true;}
        else if (qName==QName_3_102) {
          ((__doService_CIS0314I01_EAIHeader)value).setSourceSystem(strValue);
          return true;}
        else if (qName==QName_3_10) {
          ((__doService_CIS0314I01_EAIHeader)value).setUserId(strValue);
          return true;}
        else if (qName==QName_3_103) {
          ((__doService_CIS0314I01_EAIHeader)value).setPassword(strValue);
          return true;}
        else if (qName==QName_3_104) {
          ((__doService_CIS0314I01_EAIHeader)value).setStatus(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
          return true;}
        else if (qName==QName_3_105) {
          ((__doService_CIS0314I01_EAIHeader)value).setReasonCode(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
          return true;}
        else if (qName==QName_3_106) {
          ((__doService_CIS0314I01_EAIHeader)value).setMultiMsgFlg(strValue);
          return true;}
        else if (qName==QName_3_107) {
          ((__doService_CIS0314I01_EAIHeader)value).setOrderMsgTypeCode(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
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
    private final static javax.xml.namespace.QName QName_3_99 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "endTimestamp");
    private final static javax.xml.namespace.QName QName_3_106 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "multiMsgFlg");
    private final static javax.xml.namespace.QName QName_3_105 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "reasonCode");
    private final static javax.xml.namespace.QName QName_3_103 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "password");
    private final static javax.xml.namespace.QName QName_3_107 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "orderMsgTypeCode");
    private final static javax.xml.namespace.QName QName_3_101 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "sourceTransactionId");
    private final static javax.xml.namespace.QName QName_3_10 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "userId");
    private final static javax.xml.namespace.QName QName_3_104 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "status");
    private final static javax.xml.namespace.QName QName_3_98 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "beginTimestamp");
    private final static javax.xml.namespace.QName QName_3_102 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "sourceSystem");
    private final static javax.xml.namespace.QName QName_3_97 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "serviceId");
    private final static javax.xml.namespace.QName QName_3_100 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "transactionId");
}
