/**
 * ____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0222i01;

public class ____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new ____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_2_116) {
          ((____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect)value).setCISZipCode((com.kasikornbank.eai.cis0222i01._____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode)objValue);
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_2_116 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "CISZipCode");
}
