/**
 * __doServiceResponse_CIS1044U01Response_KBankHeader_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1044u01;

public class __doServiceResponse_CIS1044U01Response_KBankHeader_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public __doServiceResponse_CIS1044U01Response_KBankHeader_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new __doServiceResponse_CIS1044U01Response_KBankHeader();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_2_6) {
          ((__doServiceResponse_CIS1044U01Response_KBankHeader)value).setFuncNm(strValue);
          return true;}
        else if (qName==QName_2_7) {
          ((__doServiceResponse_CIS1044U01Response_KBankHeader)value).setRqUID(strValue);
          return true;}
        else if (qName==QName_2_141) {
          ((__doServiceResponse_CIS1044U01Response_KBankHeader)value).setRsAppId(strValue);
          return true;}
        else if (qName==QName_2_142) {
          ((__doServiceResponse_CIS1044U01Response_KBankHeader)value).setRsUID(strValue);
          return true;}
        else if (qName==QName_2_143) {
          ((__doServiceResponse_CIS1044U01Response_KBankHeader)value).setRsDt(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseDateTimeToCalendar(strValue));
          return true;}
        else if (qName==QName_2_144) {
          ((__doServiceResponse_CIS1044U01Response_KBankHeader)value).setStatusCode(strValue);
          return true;}
        else if (qName==QName_2_13) {
          ((__doServiceResponse_CIS1044U01Response_KBankHeader)value).setCorrID(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_2_145) {
          if (objValue instanceof java.util.List) {
            com.kasikornbank.eai.cis1044u01.Error[] array = new com.kasikornbank.eai.cis1044u01.Error[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((__doServiceResponse_CIS1044U01Response_KBankHeader)value).setErrorVect(array);
          } else { 
            ((__doServiceResponse_CIS1044U01Response_KBankHeader)value).setErrorVect((com.kasikornbank.eai.cis1044u01.Error[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_2_7 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "rqUID");
    private final static javax.xml.namespace.QName QName_2_142 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "rsUID");
    private final static javax.xml.namespace.QName QName_2_144 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "statusCode");
    private final static javax.xml.namespace.QName QName_2_141 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "rsAppId");
    private final static javax.xml.namespace.QName QName_2_6 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "funcNm");
    private final static javax.xml.namespace.QName QName_2_145 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "errorVect");
    private final static javax.xml.namespace.QName QName_2_13 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "corrID");
    private final static javax.xml.namespace.QName QName_2_143 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "rsDt");
}
