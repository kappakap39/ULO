/**
 * _____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0222i01;

public class _____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public _____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new _____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_2_118) {
          ((_____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode)value).setTumbolDesc(strValue);
          return true;}
        else if (qName==QName_2_119) {
          ((_____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode)value).setAmphurDesc(strValue);
          return true;}
        else if (qName==QName_2_120) {
          ((_____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode)value).setProvinceDesc(strValue);
          return true;}
        else if (qName==QName_2_135) {
          ((_____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode)value).setZipCode(strValue);
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
    private final static javax.xml.namespace.QName QName_2_120 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "provinceDesc");
    private final static javax.xml.namespace.QName QName_2_135 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "zipCode");
    private final static javax.xml.namespace.QName QName_2_119 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "amphurDesc");
    private final static javax.xml.namespace.QName QName_2_118 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "tumbolDesc");
}
