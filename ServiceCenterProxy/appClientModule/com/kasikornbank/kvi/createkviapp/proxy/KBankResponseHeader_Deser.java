/**
 * KBankResponseHeader_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.createkviapp.proxy;

public class KBankResponseHeader_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public KBankResponseHeader_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new KBankResponseHeader();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_1_4) {
          ((KBankResponseHeader)value).setFuncNm(strValue);
          return true;}
        else if (qName==QName_1_5) {
          ((KBankResponseHeader)value).setRqUID(strValue);
          return true;}
        else if (qName==QName_1_38) {
          ((KBankResponseHeader)value).setRsAppId(strValue);
          return true;}
        else if (qName==QName_1_39) {
          ((KBankResponseHeader)value).setRsDt(strValue);
          return true;}
        else if (qName==QName_1_40) {
          ((KBankResponseHeader)value).setRsUID(strValue);
          return true;}
        else if (qName==QName_1_41) {
          ((KBankResponseHeader)value).setStatusCode(strValue);
          return true;}
        else if (qName==QName_1_12) {
          ((KBankResponseHeader)value).setCorrID(strValue);
          return true;}
        else if (qName==QName_1_14) {
          ((KBankResponseHeader)value).setFiller(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_1_42) {
          if (objValue instanceof java.util.List) {
            com.kasikornbank.kvi.createkviapp.proxy.KBankResponseError[] array = new com.kasikornbank.kvi.createkviapp.proxy.KBankResponseError[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((KBankResponseHeader)value).setError(array);
          } else { 
            ((KBankResponseHeader)value).setError((com.kasikornbank.kvi.createkviapp.proxy.KBankResponseError[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_1_39 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RsDt");
    private final static javax.xml.namespace.QName QName_1_12 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "CorrID");
    private final static javax.xml.namespace.QName QName_1_4 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "FuncNm");
    private final static javax.xml.namespace.QName QName_1_5 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RqUID");
    private final static javax.xml.namespace.QName QName_1_41 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "StatusCode");
    private final static javax.xml.namespace.QName QName_1_40 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RsUID");
    private final static javax.xml.namespace.QName QName_1_14 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "Filler");
    private final static javax.xml.namespace.QName QName_1_42 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "Error");
    private final static javax.xml.namespace.QName QName_1_38 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RsAppId");
}
