/**
 * ContactNumberList_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class ContactNumberList_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ContactNumberList_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new ContactNumberList();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_21_623) {
          ((ContactNumberList)value).setSeqNo(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseint(strValue));
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
        if (qName==QName_21_567) {
          com.kasikornbank.dq.ws.v1.DqField[] array = new com.kasikornbank.dq.ws.v1.DqField[listValue.size()];
          listValue.toArray(array);
          ((ContactNumberList)value).setFields(array);
          return true;}
        return false;
    }
    private final static javax.xml.namespace.QName QName_21_567 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fields");
    private final static javax.xml.namespace.QName QName_21_623 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "seqNo");
}
