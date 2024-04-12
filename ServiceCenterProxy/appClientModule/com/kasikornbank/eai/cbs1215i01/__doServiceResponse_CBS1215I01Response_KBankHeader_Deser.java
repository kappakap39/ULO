/**
 * __doServiceResponse_CBS1215I01Response_KBankHeader_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cbs1215i01;

public class __doServiceResponse_CBS1215I01Response_KBankHeader_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public __doServiceResponse_CBS1215I01Response_KBankHeader_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new __doServiceResponse_CBS1215I01Response_KBankHeader();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_6) {
          ((__doServiceResponse_CBS1215I01Response_KBankHeader)value).setFuncNm(strValue);
          return true;}
        else if (qName==QName_0_7) {
          ((__doServiceResponse_CBS1215I01Response_KBankHeader)value).setRqUID(strValue);
          return true;}
        else if (qName==QName_0_40) {
          ((__doServiceResponse_CBS1215I01Response_KBankHeader)value).setRsAppId(strValue);
          return true;}
        else if (qName==QName_0_41) {
          ((__doServiceResponse_CBS1215I01Response_KBankHeader)value).setRsUID(strValue);
          return true;}
        else if (qName==QName_0_42) {
          ((__doServiceResponse_CBS1215I01Response_KBankHeader)value).setRsDt(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseDateTimeToCalendar(strValue));
          return true;}
        else if (qName==QName_0_43) {
          ((__doServiceResponse_CBS1215I01Response_KBankHeader)value).setStatusCode(strValue);
          return true;}
        else if (qName==QName_0_13) {
          ((__doServiceResponse_CBS1215I01Response_KBankHeader)value).setCorrID(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_0_44) {
          if (objValue instanceof java.util.List) {
            com.kasikornbank.eai.cbs1215i01.Error[] array = new com.kasikornbank.eai.cbs1215i01.Error[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((__doServiceResponse_CBS1215I01Response_KBankHeader)value).setErrorVect(array);
          } else { 
            ((__doServiceResponse_CBS1215I01Response_KBankHeader)value).setErrorVect((com.kasikornbank.eai.cbs1215i01.Error[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_7 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "rqUID");
    private final static javax.xml.namespace.QName QName_0_44 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "errorVect");
    private final static javax.xml.namespace.QName QName_0_41 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "rsUID");
    private final static javax.xml.namespace.QName QName_0_43 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "statusCode");
    private final static javax.xml.namespace.QName QName_0_6 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "funcNm");
    private final static javax.xml.namespace.QName QName_0_13 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "corrID");
    private final static javax.xml.namespace.QName QName_0_42 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "rsDt");
    private final static javax.xml.namespace.QName QName_0_40 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "rsAppId");
}
