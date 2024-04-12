/**
 * Address_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class Address_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public Address_Ser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    public void serialize(
        javax.xml.namespace.QName name,
        org.xml.sax.Attributes attributes,
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        context.startElement(name, addAttributes(attributes, value, context));
        addElements(value, context);
        context.endElement();
    }
    protected org.xml.sax.Attributes addAttributes(
        org.xml.sax.Attributes attributes,
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        Address bean = (Address) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_21_605;
          propValue = bean.getAmphurCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_227;
          propValue = bean.getBuilding();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_606;
          propValue = bean.getCountryCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_607;
          propValue = bean.getDistrictCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_229;
          propValue = bean.getFloor();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_225;
          propValue = bean.getMoo();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_608;
          propValue = bean.getNumber();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_609;
          propValue = bean.getPlace();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_223;
          propValue = bean.getPoBox();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_610;
          propValue = bean.getPostcode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_611;
          propValue = bean.getProvinceCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_231;
          propValue = bean.getRoad();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_228;
          propValue = bean.getRoom();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_230;
          propValue = bean.getSoi();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_226;
          propValue = bean.getVillage();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
        }
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
    private final static javax.xml.namespace.QName QName_1_109 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
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
