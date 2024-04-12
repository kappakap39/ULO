/**
 * KBankResponseError_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.editkviapp.proxy;

public class KBankResponseError_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public KBankResponseError_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new KBankResponseError();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_11_386) {
          ((KBankResponseError)value).setErrorAppId(strValue);
          return true;}
        else if (qName==QName_11_387) {
          ((KBankResponseError)value).setErrorAppAbbrv(strValue);
          return true;}
        else if (qName==QName_11_388) {
          ((KBankResponseError)value).setErrorCode(strValue);
          return true;}
        else if (qName==QName_11_389) {
          ((KBankResponseError)value).setErrorDesc(strValue);
          return true;}
        else if (qName==QName_11_390) {
          ((KBankResponseError)value).setErrorSeverity(strValue);
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
    private final static javax.xml.namespace.QName QName_11_387 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "ErrorAppAbbrv");
    private final static javax.xml.namespace.QName QName_11_389 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "ErrorDesc");
    private final static javax.xml.namespace.QName QName_11_390 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "ErrorSeverity");
    private final static javax.xml.namespace.QName QName_11_386 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "ErrorAppId");
    private final static javax.xml.namespace.QName QName_11_388 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "ErrorCode");
}
