/**
 * _____doServiceResponse_CIS1034A01Response_CISCustomer_docsObj_docsVect_CISDocInfo_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1034a01;

public class _____doServiceResponse_CIS1034A01Response_CISCustomer_docsObj_docsVect_CISDocInfo_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public _____doServiceResponse_CIS1034A01Response_CISCustomer_docsObj_docsVect_CISDocInfo_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new _____doServiceResponse_CIS1034A01Response_CISCustomer_docsObj_docsVect_CISDocInfo();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_5_138) {
          ((_____doServiceResponse_CIS1034A01Response_CISCustomer_docsObj_docsVect_CISDocInfo)value).setTypeCode(strValue);
          return true;}
        else if (qName==QName_5_139) {
          ((_____doServiceResponse_CIS1034A01Response_CISCustomer_docsObj_docsVect_CISDocInfo)value).setNum(strValue);
          return true;}
        else if (qName==QName_5_193) {
          ((_____doServiceResponse_CIS1034A01Response_CISCustomer_docsObj_docsVect_CISDocInfo)value).setSuccessFlg(strValue);
          return true;}
        else if (qName==QName_5_49) {
          ((_____doServiceResponse_CIS1034A01Response_CISCustomer_docsObj_docsVect_CISDocInfo)value).setErrorDesc(strValue);
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
    private final static javax.xml.namespace.QName QName_5_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "typeCode");
    private final static javax.xml.namespace.QName QName_5_49 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "errorDesc");
    private final static javax.xml.namespace.QName QName_5_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_5_193 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "successFlg");
}
