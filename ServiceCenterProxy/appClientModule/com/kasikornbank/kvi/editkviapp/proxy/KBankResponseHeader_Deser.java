/**
 * KBankResponseHeader_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.editkviapp.proxy;

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
        if (qName==QName_11_348) {
          ((KBankResponseHeader)value).setFuncNm(strValue);
          return true;}
        else if (qName==QName_11_349) {
          ((KBankResponseHeader)value).setRqUID(strValue);
          return true;}
        else if (qName==QName_11_378) {
          ((KBankResponseHeader)value).setRsAppId(strValue);
          return true;}
        else if (qName==QName_11_379) {
          ((KBankResponseHeader)value).setRsDt(strValue);
          return true;}
        else if (qName==QName_11_380) {
          ((KBankResponseHeader)value).setRsUID(strValue);
          return true;}
        else if (qName==QName_11_381) {
          ((KBankResponseHeader)value).setStatusCode(strValue);
          return true;}
        else if (qName==QName_11_355) {
          ((KBankResponseHeader)value).setCorrID(strValue);
          return true;}
        else if (qName==QName_11_357) {
          ((KBankResponseHeader)value).setFiller(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_11_382) {
          if (objValue instanceof java.util.List) {
            com.kasikornbank.kvi.editkviapp.proxy.KBankResponseError[] array = new com.kasikornbank.kvi.editkviapp.proxy.KBankResponseError[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((KBankResponseHeader)value).setError(array);
          } else { 
            ((KBankResponseHeader)value).setError((com.kasikornbank.kvi.editkviapp.proxy.KBankResponseError[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_11_380 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RsUID");
    private final static javax.xml.namespace.QName QName_11_348 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "FuncNm");
    private final static javax.xml.namespace.QName QName_11_382 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "Error");
    private final static javax.xml.namespace.QName QName_11_357 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "Filler");
    private final static javax.xml.namespace.QName QName_11_349 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RqUID");
    private final static javax.xml.namespace.QName QName_11_378 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RsAppId");
    private final static javax.xml.namespace.QName QName_11_379 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RsDt");
    private final static javax.xml.namespace.QName QName_11_381 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "StatusCode");
    private final static javax.xml.namespace.QName QName_11_355 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "CorrID");
}
