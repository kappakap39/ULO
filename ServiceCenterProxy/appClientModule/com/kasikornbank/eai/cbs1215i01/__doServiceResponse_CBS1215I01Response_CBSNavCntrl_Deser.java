/**
 * __doServiceResponse_CBS1215I01Response_CBSNavCntrl_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cbs1215i01;

public class __doServiceResponse_CBS1215I01Response_CBSNavCntrl_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public __doServiceResponse_CBS1215I01Response_CBSNavCntrl_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new __doServiceResponse_CBS1215I01Response_CBSNavCntrl();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_90) {
          ((__doServiceResponse_CBS1215I01Response_CBSNavCntrl)value).setMoreDataInd(strValue);
          return true;}
        else if (qName==QName_0_91) {
          ((__doServiceResponse_CBS1215I01Response_CBSNavCntrl)value).setNumRec(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
          return true;}
        else if (qName==QName_0_92) {
          ((__doServiceResponse_CBS1215I01Response_CBSNavCntrl)value).setTotalRec(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseInteger(strValue));
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
    private final static javax.xml.namespace.QName QName_0_91 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "NumRec");
    private final static javax.xml.namespace.QName QName_0_90 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "MoreDataInd");
    private final static javax.xml.namespace.QName QName_0_92 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "TotalRec");
}
