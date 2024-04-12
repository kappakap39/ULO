/**
 * Address_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class Address_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public Address_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new Address();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_21_605) {
          ((Address)value).setAmphurCode(strValue);
          return true;}
        else if (qName==QName_21_227) {
          ((Address)value).setBuilding(strValue);
          return true;}
        else if (qName==QName_21_606) {
          ((Address)value).setCountryCode(strValue);
          return true;}
        else if (qName==QName_21_607) {
          ((Address)value).setDistrictCode(strValue);
          return true;}
        else if (qName==QName_21_229) {
          ((Address)value).setFloor(strValue);
          return true;}
        else if (qName==QName_21_225) {
          ((Address)value).setMoo(strValue);
          return true;}
        else if (qName==QName_21_608) {
          ((Address)value).setNumber(strValue);
          return true;}
        else if (qName==QName_21_609) {
          ((Address)value).setPlace(strValue);
          return true;}
        else if (qName==QName_21_223) {
          ((Address)value).setPoBox(strValue);
          return true;}
        else if (qName==QName_21_610) {
          ((Address)value).setPostcode(strValue);
          return true;}
        else if (qName==QName_21_611) {
          ((Address)value).setProvinceCode(strValue);
          return true;}
        else if (qName==QName_21_231) {
          ((Address)value).setRoad(strValue);
          return true;}
        else if (qName==QName_21_228) {
          ((Address)value).setRoom(strValue);
          return true;}
        else if (qName==QName_21_230) {
          ((Address)value).setSoi(strValue);
          return true;}
        else if (qName==QName_21_226) {
          ((Address)value).setVillage(strValue);
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
    private final static javax.xml.namespace.QName QName_21_223 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "poBox");
    private final static javax.xml.namespace.QName QName_21_230 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "soi");
    private final static javax.xml.namespace.QName QName_21_605 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "amphurCode");
    private final static javax.xml.namespace.QName QName_21_226 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "village");
    private final static javax.xml.namespace.QName QName_21_227 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "building");
    private final static javax.xml.namespace.QName QName_21_608 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "number");
    private final static javax.xml.namespace.QName QName_21_225 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "moo");
    private final static javax.xml.namespace.QName QName_21_610 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "postcode");
    private final static javax.xml.namespace.QName QName_21_607 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "districtCode");
    private final static javax.xml.namespace.QName QName_21_231 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "road");
    private final static javax.xml.namespace.QName QName_21_609 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "place");
    private final static javax.xml.namespace.QName QName_21_229 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "floor");
    private final static javax.xml.namespace.QName QName_21_228 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "room");
    private final static javax.xml.namespace.QName QName_21_611 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "provinceCode");
    private final static javax.xml.namespace.QName QName_21_606 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "countryCode");
}
