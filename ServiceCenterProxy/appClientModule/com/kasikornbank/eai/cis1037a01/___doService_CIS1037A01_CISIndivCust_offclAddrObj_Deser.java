/**
 * ___doService_CIS1037A01_CISIndivCust_offclAddrObj_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1037a01;

public class ___doService_CIS1037A01_CISIndivCust_offclAddrObj_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ___doService_CIS1037A01_CISIndivCust_offclAddrObj_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new ___doService_CIS1037A01_CISIndivCust_offclAddrObj();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_109) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setAmphur(strValue);
          return true;}
        else if (qName==QName_0_110) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setBuilding(strValue);
          return true;}
        else if (qName==QName_0_111) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setCntryCode(strValue);
          return true;}
        else if (qName==QName_0_112) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setFloor(strValue);
          return true;}
        else if (qName==QName_0_113) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setMailNum(strValue);
          return true;}
        else if (qName==QName_0_114) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setMoo(strValue);
          return true;}
        else if (qName==QName_0_115) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setName(strValue);
          return true;}
        else if (qName==QName_0_116) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setPoBox(strValue);
          return true;}
        else if (qName==QName_0_117) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setPostCode(strValue);
          return true;}
        else if (qName==QName_0_118) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setPreferedCode(strValue);
          return true;}
        else if (qName==QName_0_119) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setProvince(strValue);
          return true;}
        else if (qName==QName_0_120) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setRoad(strValue);
          return true;}
        else if (qName==QName_0_121) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setRoom(strValue);
          return true;}
        else if (qName==QName_0_122) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setSoi(strValue);
          return true;}
        else if (qName==QName_0_123) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setTumbol(strValue);
          return true;}
        else if (qName==QName_0_124) {
          ((___doService_CIS1037A01_CISIndivCust_offclAddrObj)value).setVillage(strValue);
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
    private final static javax.xml.namespace.QName QName_0_122 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "soi");
    private final static javax.xml.namespace.QName QName_0_121 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "room");
    private final static javax.xml.namespace.QName QName_0_120 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "road");
    private final static javax.xml.namespace.QName QName_0_115 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "name");
    private final static javax.xml.namespace.QName QName_0_112 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "floor");
    private final static javax.xml.namespace.QName QName_0_123 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "tumbol");
    private final static javax.xml.namespace.QName QName_0_114 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "moo");
    private final static javax.xml.namespace.QName QName_0_116 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "poBox");
    private final static javax.xml.namespace.QName QName_0_119 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "province");
    private final static javax.xml.namespace.QName QName_0_109 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "amphur");
    private final static javax.xml.namespace.QName QName_0_113 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "mailNum");
    private final static javax.xml.namespace.QName QName_0_124 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "village");
    private final static javax.xml.namespace.QName QName_0_111 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "cntryCode");
    private final static javax.xml.namespace.QName QName_0_118 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "preferedCode");
    private final static javax.xml.namespace.QName QName_0_117 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "postCode");
    private final static javax.xml.namespace.QName QName_0_110 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "building");
}
