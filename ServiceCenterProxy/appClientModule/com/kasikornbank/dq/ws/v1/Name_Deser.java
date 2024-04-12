/**
 * Name_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class Name_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public Name_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new Name();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_21_618) {
          ((Name)value).setFistName(strValue);
          return true;}
        else if (qName==QName_21_619) {
          ((Name)value).setLastName(strValue);
          return true;}
        else if (qName==QName_21_620) {
          ((Name)value).setMiddleName(strValue);
          return true;}
        else if (qName==QName_21_621) {
          ((Name)value).setTitleName(strValue);
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
    private final static javax.xml.namespace.QName QName_21_620 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "middleName");
    private final static javax.xml.namespace.QName QName_21_619 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "lastName");
    private final static javax.xml.namespace.QName QName_21_618 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fistName");
    private final static javax.xml.namespace.QName QName_21_621 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "titleName");
}
