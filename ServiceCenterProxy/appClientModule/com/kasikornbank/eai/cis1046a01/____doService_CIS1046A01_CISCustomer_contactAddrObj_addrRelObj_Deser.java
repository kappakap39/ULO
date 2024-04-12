/**
 * ____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1046a01;

public class ____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new ____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_9_336) {
          if (objValue instanceof java.util.List) {
            com.kasikornbank.eai.cis1046a01.______doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_addrRelsVect_CISAddrRelation[] array = new com.kasikornbank.eai.cis1046a01.______doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_addrRelsVect_CISAddrRelation[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj)value).setAddrRelsVect(array);
          } else { 
            ((____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj)value).setAddrRelsVect((com.kasikornbank.eai.cis1046a01.______doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_addrRelsVect_CISAddrRelation[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_9_336 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "addrRelsVect");
}
