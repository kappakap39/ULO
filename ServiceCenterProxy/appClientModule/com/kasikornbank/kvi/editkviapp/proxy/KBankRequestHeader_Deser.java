/**
 * KBankRequestHeader_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.editkviapp.proxy;

public class KBankRequestHeader_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public KBankRequestHeader_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new KBankRequestHeader();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_11_348) {
          ((KBankRequestHeader)value).setFuncNm(strValue);
          return true;}
        else if (qName==QName_11_349) {
          ((KBankRequestHeader)value).setRqUID(strValue);
          return true;}
        else if (qName==QName_11_350) {
          ((KBankRequestHeader)value).setRqDt(strValue);
          return true;}
        else if (qName==QName_11_351) {
          ((KBankRequestHeader)value).setRqAppId(strValue);
          return true;}
        else if (qName==QName_11_81) {
          ((KBankRequestHeader)value).setUserId(strValue);
          return true;}
        else if (qName==QName_11_352) {
          ((KBankRequestHeader)value).setTerminalId(strValue);
          return true;}
        else if (qName==QName_11_353) {
          ((KBankRequestHeader)value).setUserLangPref(strValue);
          return true;}
        else if (qName==QName_11_354) {
          ((KBankRequestHeader)value).setAuthUserId(strValue);
          return true;}
        else if (qName==QName_11_355) {
          ((KBankRequestHeader)value).setCorrID(strValue);
          return true;}
        else if (qName==QName_11_356) {
          ((KBankRequestHeader)value).setAuthLevel(strValue);
          return true;}
        else if (qName==QName_11_357) {
          ((KBankRequestHeader)value).setFiller(strValue);
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
    private final static javax.xml.namespace.QName QName_11_81 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "UserId");
    private final static javax.xml.namespace.QName QName_11_348 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "FuncNm");
    private final static javax.xml.namespace.QName QName_11_356 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "AuthLevel");
    private final static javax.xml.namespace.QName QName_11_357 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "Filler");
    private final static javax.xml.namespace.QName QName_11_353 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "UserLangPref");
    private final static javax.xml.namespace.QName QName_11_349 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RqUID");
    private final static javax.xml.namespace.QName QName_11_354 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "AuthUserId");
    private final static javax.xml.namespace.QName QName_11_355 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "CorrID");
    private final static javax.xml.namespace.QName QName_11_351 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RqAppId");
    private final static javax.xml.namespace.QName QName_11_350 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RqDt");
    private final static javax.xml.namespace.QName QName_11_352 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "TerminalId");
}
