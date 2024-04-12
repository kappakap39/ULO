/**
 * MultiMsg_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0315u01;

public class MultiMsg_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public MultiMsg_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new MultiMsg();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_3_55) {
          ((MultiMsg)value).setStatus(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
          return true;}
        else if (qName==QName_3_56) {
          ((MultiMsg)value).setReasonCode(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
          return true;}
        else if (qName==QName_3_79) {
          ((MultiMsg)value).setReasonDesc(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_3_82) {
          if (objValue instanceof java.util.List) {
            com.kasikornbank.eai.cis0315u01.RefField[] array = new com.kasikornbank.eai.cis0315u01.RefField[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((MultiMsg)value).setRefFieldVect(array);
          } else { 
            ((MultiMsg)value).setRefFieldVect((com.kasikornbank.eai.cis0315u01.RefField[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_3_55 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "status");
    private final static javax.xml.namespace.QName QName_3_79 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "reasonDesc");
    private final static javax.xml.namespace.QName QName_3_56 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "reasonCode");
    private final static javax.xml.namespace.QName QName_3_82 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "refFieldVect");
}
