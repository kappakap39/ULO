/**
 * ___doService_CIS1036A01_CISCustomer_contactAddrObj_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1036a01;

public class ___doService_CIS1036A01_CISCustomer_contactAddrObj_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ___doService_CIS1036A01_CISCustomer_contactAddrObj_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new ___doService_CIS1036A01_CISCustomer_contactAddrObj();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_7_139) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setNum(strValue);
          return true;}
        else if (qName==QName_7_207) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setName(strValue);
          return true;}
        else if (qName==QName_7_223) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setPoBox(strValue);
          return true;}
        else if (qName==QName_7_224) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setMailNum(strValue);
          return true;}
        else if (qName==QName_7_225) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setMoo(strValue);
          return true;}
        else if (qName==QName_7_226) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setVillage(strValue);
          return true;}
        else if (qName==QName_7_227) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setBuilding(strValue);
          return true;}
        else if (qName==QName_7_228) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setRoom(strValue);
          return true;}
        else if (qName==QName_7_229) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setFloor(strValue);
          return true;}
        else if (qName==QName_7_230) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setSoi(strValue);
          return true;}
        else if (qName==QName_7_231) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setRoad(strValue);
          return true;}
        else if (qName==QName_7_232) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setTumbol(strValue);
          return true;}
        else if (qName==QName_7_233) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setAmphur(strValue);
          return true;}
        else if (qName==QName_7_234) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setProvince(strValue);
          return true;}
        else if (qName==QName_7_235) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setPostCode(strValue);
          return true;}
        else if (qName==QName_7_236) {
          ((___doService_CIS1036A01_CISCustomer_contactAddrObj)value).setCntryCode(strValue);
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
    private final static javax.xml.namespace.QName QName_7_231 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "road");
    private final static javax.xml.namespace.QName QName_7_229 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "floor");
    private final static javax.xml.namespace.QName QName_7_225 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "moo");
    private final static javax.xml.namespace.QName QName_7_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_7_234 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "province");
    private final static javax.xml.namespace.QName QName_7_230 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "soi");
    private final static javax.xml.namespace.QName QName_7_223 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "poBox");
    private final static javax.xml.namespace.QName QName_7_233 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "amphur");
    private final static javax.xml.namespace.QName QName_7_228 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "room");
    private final static javax.xml.namespace.QName QName_7_227 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "building");
    private final static javax.xml.namespace.QName QName_7_207 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "name");
    private final static javax.xml.namespace.QName QName_7_232 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "tumbol");
    private final static javax.xml.namespace.QName QName_7_226 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "village");
    private final static javax.xml.namespace.QName QName_7_235 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "postCode");
    private final static javax.xml.namespace.QName QName_7_224 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "mailNum");
    private final static javax.xml.namespace.QName QName_7_236 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "cntryCode");
}
