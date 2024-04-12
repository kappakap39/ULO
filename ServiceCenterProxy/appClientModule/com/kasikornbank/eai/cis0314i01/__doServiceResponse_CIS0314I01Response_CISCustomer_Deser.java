/**
 * __doServiceResponse_CIS0314I01Response_CISCustomer_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0314i01;

public class __doServiceResponse_CIS0314I01Response_CISCustomer_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public __doServiceResponse_CIS0314I01Response_CISCustomer_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new __doServiceResponse_CIS0314I01Response_CISCustomer();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_3_139) {
          ((__doServiceResponse_CIS0314I01Response_CISCustomer)value).setNum(strValue);
          return true;}
        else if (qName==QName_3_138) {
          ((__doServiceResponse_CIS0314I01Response_CISCustomer)value).setTypeCode(strValue);
          return true;}
        else if (qName==QName_3_144) {
          ((__doServiceResponse_CIS0314I01Response_CISCustomer)value).setThTitle(strValue);
          return true;}
        else if (qName==QName_3_145) {
          ((__doServiceResponse_CIS0314I01Response_CISCustomer)value).setThFstName(strValue);
          return true;}
        else if (qName==QName_3_146) {
          ((__doServiceResponse_CIS0314I01Response_CISCustomer)value).setThLstName(strValue);
          return true;}
        else if (qName==QName_3_147) {
          ((__doServiceResponse_CIS0314I01Response_CISCustomer)value).setEngTitle(strValue);
          return true;}
        else if (qName==QName_3_148) {
          ((__doServiceResponse_CIS0314I01Response_CISCustomer)value).setEngFstName(strValue);
          return true;}
        else if (qName==QName_3_149) {
          ((__doServiceResponse_CIS0314I01Response_CISCustomer)value).setEngLstName(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_3_150) {
          ((__doServiceResponse_CIS0314I01Response_CISCustomer)value).setKYCObj((com.kasikornbank.eai.cis0314i01.KYCObj)objValue);
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_3_150 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "KYCObj");
    private final static javax.xml.namespace.QName QName_3_144 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "thTitle");
    private final static javax.xml.namespace.QName QName_3_149 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "engLstName");
    private final static javax.xml.namespace.QName QName_3_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "num");
    private final static javax.xml.namespace.QName QName_3_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "typeCode");
    private final static javax.xml.namespace.QName QName_3_148 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "engFstName");
    private final static javax.xml.namespace.QName QName_3_146 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "thLstName");
    private final static javax.xml.namespace.QName QName_3_147 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "engTitle");
    private final static javax.xml.namespace.QName QName_3_145 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "thFstName");
}
