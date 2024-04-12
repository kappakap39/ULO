/**
 * Header_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class Header_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public Header_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new Header();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_21_98) {
          ((Header)value).setBeginTimestamp(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseDateTimeToCalendar(strValue));
          return true;}
        else if (qName==QName_21_99) {
          ((Header)value).setEndTimestamp(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseDateTimeToCalendar(strValue));
          return true;}
        else if (qName==QName_21_97) {
          ((Header)value).setServiceId(strValue);
          return true;}
        else if (qName==QName_21_102) {
          ((Header)value).setSourceSystem(strValue);
          return true;}
        else if (qName==QName_21_104) {
          ((Header)value).setStatus(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseint(strValue));
          return true;}
        else if (qName==QName_21_100) {
          ((Header)value).setTransactionId(strValue);
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
    private final static javax.xml.namespace.QName QName_21_99 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "endTimestamp");
    private final static javax.xml.namespace.QName QName_21_100 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "transactionId");
    private final static javax.xml.namespace.QName QName_21_97 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "serviceId");
    private final static javax.xml.namespace.QName QName_21_98 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "beginTimestamp");
    private final static javax.xml.namespace.QName QName_21_104 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "status");
    private final static javax.xml.namespace.QName QName_21_102 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "sourceSystem");
}
