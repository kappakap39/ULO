/**
 * __doService_CBS1215I01_CBSNavCntrl_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cbs1215i01;

public class __doService_CBS1215I01_CBSNavCntrl_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public __doService_CBS1215I01_CBSNavCntrl_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new __doService_CBS1215I01_CBSNavCntrl();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_32) {
          ((__doService_CBS1215I01_CBSNavCntrl)value).setMaxRows(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
          return true;}
        else if (qName==QName_0_33) {
          ((__doService_CBS1215I01_CBSNavCntrl)value).setStartInd(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
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
    private final static javax.xml.namespace.QName QName_0_32 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "MaxRows");
    private final static javax.xml.namespace.QName QName_0_33 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "StartInd");
}
