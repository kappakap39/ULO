/**
 * KYCObj_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0315u01;

public class KYCObj_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public KYCObj_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new KYCObj();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_3_68) {
          ((KYCObj)value).setSrcAsstCode(strValue);
          return true;}
        else if (qName==QName_3_69) {
          ((KYCObj)value).setSrcAsstOthDesc(strValue);
          return true;}
        else if (qName==QName_3_70) {
          ((KYCObj)value).setPolitcnPosiDesc(strValue);
          return true;}
        else if (qName==QName_3_71) {
          ((KYCObj)value).setValAsstAmt(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseBigDecimal(strValue));
          return true;}
        else if (qName==QName_3_72) {
          ((KYCObj)value).setValAsstCode(strValue);
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
    private final static javax.xml.namespace.QName QName_3_70 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "politcnPosiDesc");
    private final static javax.xml.namespace.QName QName_3_68 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "srcAsstCode");
    private final static javax.xml.namespace.QName QName_3_72 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "valAsstCode");
    private final static javax.xml.namespace.QName QName_3_71 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "valAsstAmt");
    private final static javax.xml.namespace.QName QName_3_69 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "srcAsstOthDesc");
}
