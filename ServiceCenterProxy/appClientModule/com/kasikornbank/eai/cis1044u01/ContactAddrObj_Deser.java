/**
 * ContactAddrObj_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1044u01;

public class ContactAddrObj_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ContactAddrObj_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new ContactAddrObj();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_2_109) {
          ((ContactAddrObj)value).setAmphur(strValue);
          return true;}
        else if (qName==QName_2_110) {
          ((ContactAddrObj)value).setBuilding(strValue);
          return true;}
        else if (qName==QName_2_179) {
          ((ContactAddrObj)value).setCntryDesc(strValue);
          return true;}
        else if (qName==QName_2_112) {
          ((ContactAddrObj)value).setFloor(strValue);
          return true;}
        else if (qName==QName_2_113) {
          ((ContactAddrObj)value).setMailNum(strValue);
          return true;}
        else if (qName==QName_2_114) {
          ((ContactAddrObj)value).setMoo(strValue);
          return true;}
        else if (qName==QName_2_115) {
          ((ContactAddrObj)value).setName(strValue);
          return true;}
        else if (qName==QName_2_116) {
          ((ContactAddrObj)value).setPoBox(strValue);
          return true;}
        else if (qName==QName_2_117) {
          ((ContactAddrObj)value).setPostCode(strValue);
          return true;}
        else if (qName==QName_2_118) {
          ((ContactAddrObj)value).setPreferedCode(strValue);
          return true;}
        else if (qName==QName_2_119) {
          ((ContactAddrObj)value).setProvince(strValue);
          return true;}
        else if (qName==QName_2_120) {
          ((ContactAddrObj)value).setRoad(strValue);
          return true;}
        else if (qName==QName_2_121) {
          ((ContactAddrObj)value).setRoom(strValue);
          return true;}
        else if (qName==QName_2_122) {
          ((ContactAddrObj)value).setSoi(strValue);
          return true;}
        else if (qName==QName_2_123) {
          ((ContactAddrObj)value).setTumbol(strValue);
          return true;}
        else if (qName==QName_2_124) {
          ((ContactAddrObj)value).setVillage(strValue);
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
    private final static javax.xml.namespace.QName QName_2_118 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "preferedCode");
    private final static javax.xml.namespace.QName QName_2_117 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "postCode");
    private final static javax.xml.namespace.QName QName_2_110 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "building");
    private final static javax.xml.namespace.QName QName_2_122 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "soi");
    private final static javax.xml.namespace.QName QName_2_121 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "room");
    private final static javax.xml.namespace.QName QName_2_120 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "road");
    private final static javax.xml.namespace.QName QName_2_115 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "name");
    private final static javax.xml.namespace.QName QName_2_112 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "floor");
    private final static javax.xml.namespace.QName QName_2_123 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "tumbol");
    private final static javax.xml.namespace.QName QName_2_114 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "moo");
    private final static javax.xml.namespace.QName QName_2_179 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "cntryDesc");
    private final static javax.xml.namespace.QName QName_2_109 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "amphur");
    private final static javax.xml.namespace.QName QName_2_116 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "poBox");
    private final static javax.xml.namespace.QName QName_2_124 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "village");
    private final static javax.xml.namespace.QName QName_2_119 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "province");
    private final static javax.xml.namespace.QName QName_2_113 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "mailNum");
}
