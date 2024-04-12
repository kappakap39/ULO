/**
 * ______doServiceRespo_750316492_CISAddrRelation_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1046a01;

public class ______doServiceRespo_750316492_CISAddrRelation_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ______doServiceRespo_750316492_CISAddrRelation_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new ______doServiceRespo_750316492_CISAddrRelation();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_9_138) {
          ((______doServiceRespo_750316492_CISAddrRelation)value).setTypeCode(strValue);
          return true;}
        else if (qName==QName_9_214) {
          ((______doServiceRespo_750316492_CISAddrRelation)value).setSuccessFlag(strValue);
          return true;}
        else if (qName==QName_9_49) {
          ((______doServiceRespo_750316492_CISAddrRelation)value).setErrorDesc(strValue);
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
    private final static javax.xml.namespace.QName QName_9_214 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "successFlag");
    private final static javax.xml.namespace.QName QName_9_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "typeCode");
    private final static javax.xml.namespace.QName QName_9_49 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "errorDesc");
}
