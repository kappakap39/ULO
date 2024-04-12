/**
 * RequestData_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.createkviapp.proxy;

public class RequestData_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public RequestData_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new RequestData();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_16) {
          ((RequestData)value).setFGAppNo(strValue);
          return true;}
        else if (qName==QName_0_17) {
          ((RequestData)value).setFCDept(strValue);
          return true;}
        else if (qName==QName_0_18) {
          ((RequestData)value).setFCInputID(strValue);
          return true;}
        else if (qName==QName_0_19) {
          ((RequestData)value).setFGRequestor(strValue);
          return true;}
        else if (qName==QName_0_20) {
          ((RequestData)value).setFGRequestorL(strValue);
          return true;}
        else if (qName==QName_0_21) {
          ((RequestData)value).setFGType(strValue);
          return true;}
        else if (qName==QName_0_22) {
          ((RequestData)value).setFGID(strValue);
          return true;}
        else if (qName==QName_0_23) {
          ((RequestData)value).setFGCisNo(strValue);
          return true;}
        else if (qName==QName_0_24) {
          ((RequestData)value).setFGRequestor1(strValue);
          return true;}
        else if (qName==QName_0_25) {
          ((RequestData)value).setFGRequestorL1(strValue);
          return true;}
        else if (qName==QName_0_26) {
          ((RequestData)value).setFGType1(strValue);
          return true;}
        else if (qName==QName_0_27) {
          ((RequestData)value).setFGID1(strValue);
          return true;}
        else if (qName==QName_0_28) {
          ((RequestData)value).setFGCisNo1(strValue);
          return true;}
        else if (qName==QName_0_29) {
          ((RequestData)value).setFGRequestor2(strValue);
          return true;}
        else if (qName==QName_0_30) {
          ((RequestData)value).setFGRequestorL2(strValue);
          return true;}
        else if (qName==QName_0_31) {
          ((RequestData)value).setFGType2(strValue);
          return true;}
        else if (qName==QName_0_32) {
          ((RequestData)value).setFGID2(strValue);
          return true;}
        else if (qName==QName_0_33) {
          ((RequestData)value).setFGCisNo2(strValue);
          return true;}
        else if (qName==QName_0_34) {
          ((RequestData)value).setFCBusiness(strValue);
          return true;}
        else if (qName==QName_0_35) {
          ((RequestData)value).setFCBusinessDesc(strValue);
          return true;}
        else if (qName==QName_0_36) {
          ((RequestData)value).setPercentShareHolder(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseBigDecimal(strValue));
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
    private final static javax.xml.namespace.QName QName_0_23 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGCisNo");
    private final static javax.xml.namespace.QName QName_0_36 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "PercentShareHolder");
    private final static javax.xml.namespace.QName QName_0_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGAppNo");
    private final static javax.xml.namespace.QName QName_0_33 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGCisNo2");
    private final static javax.xml.namespace.QName QName_0_28 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGCisNo1");
    private final static javax.xml.namespace.QName QName_0_35 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FCBusinessDesc");
    private final static javax.xml.namespace.QName QName_0_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGID");
    private final static javax.xml.namespace.QName QName_0_31 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGType2");
    private final static javax.xml.namespace.QName QName_0_26 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGType1");
    private final static javax.xml.namespace.QName QName_0_21 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGType");
    private final static javax.xml.namespace.QName QName_0_29 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGRequestor2");
    private final static javax.xml.namespace.QName QName_0_30 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGRequestorL2");
    private final static javax.xml.namespace.QName QName_0_24 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGRequestor1");
    private final static javax.xml.namespace.QName QName_0_25 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGRequestorL1");
    private final static javax.xml.namespace.QName QName_0_32 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGID2");
    private final static javax.xml.namespace.QName QName_0_27 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGID1");
    private final static javax.xml.namespace.QName QName_0_18 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FCInputID");
    private final static javax.xml.namespace.QName QName_0_34 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FCBusiness");
    private final static javax.xml.namespace.QName QName_0_17 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FCDept");
    private final static javax.xml.namespace.QName QName_0_20 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGRequestorL");
    private final static javax.xml.namespace.QName QName_0_19 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication",
                  "FGRequestor");
}
