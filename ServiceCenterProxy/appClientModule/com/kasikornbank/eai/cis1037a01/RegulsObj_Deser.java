/**
 * RegulsObj_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1037a01;

public class RegulsObj_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public RegulsObj_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new RegulsObj();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_163) {
          ((RegulsObj)value).setTotRegulCnt(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_0_162) {
          if (objValue instanceof java.util.List) {
            com.kasikornbank.eai.cis1037a01.CVRSRegulation[] array = new com.kasikornbank.eai.cis1037a01.CVRSRegulation[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((RegulsObj)value).setRegulsVect(array);
          } else { 
            ((RegulsObj)value).setRegulsVect((com.kasikornbank.eai.cis1037a01.CVRSRegulation[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_162 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "regulsVect");
    private final static javax.xml.namespace.QName QName_0_163 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "totRegulCnt");
}
