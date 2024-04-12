/**
 * RetentionsObj_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cbs1215i01;

public class RetentionsObj_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public RetentionsObj_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new RetentionsObj();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_55) {
          ((RetentionsObj)value).setTotalRetentionAmt(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseBigDecimal(strValue));
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_0_54) {
          if (objValue instanceof java.util.List) {
            com.kasikornbank.eai.cbs1215i01.CBSRetention[] array = new com.kasikornbank.eai.cbs1215i01.CBSRetention[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((RetentionsObj)value).setRetentionsVect(array);
          } else { 
            ((RetentionsObj)value).setRetentionsVect((com.kasikornbank.eai.cbs1215i01.CBSRetention[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_55 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "TotalRetentionAmt");
    private final static javax.xml.namespace.QName QName_0_54 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "retentionsVect");
}
