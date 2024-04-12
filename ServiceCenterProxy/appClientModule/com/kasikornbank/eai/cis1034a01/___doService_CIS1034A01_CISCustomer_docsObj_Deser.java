/**
 * ___doService_CIS1034A01_CISCustomer_docsObj_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1034a01;

public class ___doService_CIS1034A01_CISCustomer_docsObj_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ___doService_CIS1034A01_CISCustomer_docsObj_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new ___doService_CIS1034A01_CISCustomer_docsObj();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_5_182) {
          ((___doService_CIS1034A01_CISCustomer_docsObj)value).setTotDocCnt(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_5_183) {
          if (objValue instanceof java.util.List) {
            com.kasikornbank.eai.cis1034a01._____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo[] array = new com.kasikornbank.eai.cis1034a01._____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((___doService_CIS1034A01_CISCustomer_docsObj)value).setDocsVect(array);
          } else { 
            ((___doService_CIS1034A01_CISCustomer_docsObj)value).setDocsVect((com.kasikornbank.eai.cis1034a01._____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_5_183 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "docsVect");
    private final static javax.xml.namespace.QName QName_5_182 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "totDocCnt");
}
