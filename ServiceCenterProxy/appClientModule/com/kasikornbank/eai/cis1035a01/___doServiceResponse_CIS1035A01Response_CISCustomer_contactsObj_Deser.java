/**
 * ___doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1035a01;

public class ___doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ___doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new ___doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_6_201) {
          if (objValue instanceof java.util.List) {
            com.kasikornbank.eai.cis1035a01._____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact[] array = new com.kasikornbank.eai.cis1035a01._____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((___doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj)value).setContactsVect(array);
          } else { 
            ((___doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj)value).setContactsVect((com.kasikornbank.eai.cis1035a01._____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_6_201 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "contactsVect");
}
