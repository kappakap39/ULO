/**
 * DataQualityResponse_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class DataQualityResponse_Deser extends com.kasikornbank.dq.ws.v1.BaseResponse_Deser {
    /**
     * Constructor
     */
    public DataQualityResponse_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new DataQualityResponse();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return super.tryElementSetFromString(qName, strValue);
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return super.tryAttributeSetFromString(qName, strValue);
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        return super.tryElementSetFromObject(qName, objValue);
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        if (qName==QName_21_565) {
          com.kasikornbank.dq.ws.v1.ContactEmailList[] array = new com.kasikornbank.dq.ws.v1.ContactEmailList[listValue.size()];
          listValue.toArray(array);
          ((DataQualityResponse)value).setContactEmails(array);
          return true;}
        else if (qName==QName_21_566) {
          com.kasikornbank.dq.ws.v1.ContactNumberList[] array = new com.kasikornbank.dq.ws.v1.ContactNumberList[listValue.size()];
          listValue.toArray(array);
          ((DataQualityResponse)value).setContactNumbers(array);
          return true;}
        else if (qName==QName_21_567) {
          com.kasikornbank.dq.ws.v1.DqField[] array = new com.kasikornbank.dq.ws.v1.DqField[listValue.size()];
          listValue.toArray(array);
          ((DataQualityResponse)value).setFields(array);
          return true;}
        else if (qName==QName_21_568) {
          com.kasikornbank.dq.ws.v1.OthetAddressList[] array = new com.kasikornbank.dq.ws.v1.OthetAddressList[listValue.size()];
          listValue.toArray(array);
          ((DataQualityResponse)value).setOtherAddresses(array);
          return true;}
        return super.tryElementSetFromList(qName, listValue);
    }
    private final static javax.xml.namespace.QName QName_21_567 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fields");
    private final static javax.xml.namespace.QName QName_21_566 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "contactNumbers");
    private final static javax.xml.namespace.QName QName_21_568 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "otherAddresses");
    private final static javax.xml.namespace.QName QName_21_565 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "contactEmails");
}
