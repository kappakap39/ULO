/**
 * KBankRequestHeader_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.createkviapp.proxy;

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
        if (qName==QName_1_4) {
          ((KBankRequestHeader)value).setFuncNm(strValue);
          return true;}
        else if (qName==QName_1_5) {
          ((KBankRequestHeader)value).setRqUID(strValue);
          return true;}
        else if (qName==QName_1_6) {
          ((KBankRequestHeader)value).setRqDt(strValue);
          return true;}
        else if (qName==QName_1_7) {
          ((KBankRequestHeader)value).setRqAppId(strValue);
          return true;}
        else if (qName==QName_1_8) {
          ((KBankRequestHeader)value).setUserId(strValue);
          return true;}
        else if (qName==QName_1_9) {
          ((KBankRequestHeader)value).setTerminalId(strValue);
          return true;}
        else if (qName==QName_1_10) {
          ((KBankRequestHeader)value).setUserLangPref(strValue);
          return true;}
        else if (qName==QName_1_11) {
          ((KBankRequestHeader)value).setAuthUserId(strValue);
          return true;}
        else if (qName==QName_1_12) {
          ((KBankRequestHeader)value).setCorrID(strValue);
          return true;}
        else if (qName==QName_1_13) {
          ((KBankRequestHeader)value).setAuthLevel(strValue);
          return true;}
        else if (qName==QName_1_14) {
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
    private final static javax.xml.namespace.QName QName_1_10 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "UserLangPref");
    private final static javax.xml.namespace.QName QName_1_13 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "AuthLevel");
    private final static javax.xml.namespace.QName QName_1_8 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "UserId");
    private final static javax.xml.namespace.QName QName_1_12 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "CorrID");
    private final static javax.xml.namespace.QName QName_1_9 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "TerminalId");
    private final static javax.xml.namespace.QName QName_1_4 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "FuncNm");
    private final static javax.xml.namespace.QName QName_1_5 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RqUID");
    private final static javax.xml.namespace.QName QName_1_6 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RqDt");
    private final static javax.xml.namespace.QName QName_1_14 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "Filler");
    private final static javax.xml.namespace.QName QName_1_7 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RqAppId");
    private final static javax.xml.namespace.QName QName_1_11 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "AuthUserId");
}
