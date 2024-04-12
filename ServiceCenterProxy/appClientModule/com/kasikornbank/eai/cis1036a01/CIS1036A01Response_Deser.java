/**
 * CIS1036A01Response_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1036a01;

public class CIS1036A01Response_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public CIS1036A01Response_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new CIS1036A01Response();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_7_0) {
          ((CIS1036A01Response)value).setKBankHeader((com.kasikornbank.eai.cis1036a01.__doServiceResponse_CIS1036A01Response_KBankHeader)objValue);
          return true;}
        else if (qName==QName_7_94) {
          ((CIS1036A01Response)value).setCISCustomer((com.kasikornbank.eai.cis1036a01.__doServiceResponse_CIS1036A01Response_CISCustomer)objValue);
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_7_0 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "KBankHeader");
    private final static javax.xml.namespace.QName QName_7_94 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "CISCustomer");
}
