/**
 * ___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0222i01;

public class ___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new ___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_2_133) {
          ((___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj)value).setTotZipCodeCnt(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_2_114) {
          if (objValue instanceof java.util.List) {
            com.kasikornbank.eai.cis0222i01._____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode[] array = new com.kasikornbank.eai.cis0222i01._____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj)value).setZipCodesVect(array);
          } else { 
            ((___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj)value).setZipCodesVect((com.kasikornbank.eai.cis0222i01._____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_2_133 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "totZipCodeCnt");
    private final static javax.xml.namespace.QName QName_2_114 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "zipCodesVect");
}
