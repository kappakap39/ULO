/**
 * ___doService_CIS1036A01_CISCustomer_contactAddrObj_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1036a01;

public class ___doService_CIS1036A01_CISCustomer_contactAddrObj_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public ___doService_CIS1036A01_CISCustomer_contactAddrObj_Ser(
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
           javax.xml.namespace.QName
           elemQName = QName_7_139;
           context.qName2String(elemQName, true);
           elemQName = QName_7_207;
           context.qName2String(elemQName, true);
           elemQName = QName_7_223;
           context.qName2String(elemQName, true);
           elemQName = QName_7_224;
           context.qName2String(elemQName, true);
           elemQName = QName_7_225;
           context.qName2String(elemQName, true);
           elemQName = QName_7_226;
           context.qName2String(elemQName, true);
           elemQName = QName_7_227;
           context.qName2String(elemQName, true);
           elemQName = QName_7_228;
           context.qName2String(elemQName, true);
           elemQName = QName_7_229;
           context.qName2String(elemQName, true);
           elemQName = QName_7_230;
           context.qName2String(elemQName, true);
           elemQName = QName_7_231;
           context.qName2String(elemQName, true);
           elemQName = QName_7_232;
           context.qName2String(elemQName, true);
           elemQName = QName_7_233;
           context.qName2String(elemQName, true);
           elemQName = QName_7_234;
           context.qName2String(elemQName, true);
           elemQName = QName_7_235;
           context.qName2String(elemQName, true);
           elemQName = QName_7_236;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        ___doService_CIS1036A01_CISCustomer_contactAddrObj bean = (___doService_CIS1036A01_CISCustomer_contactAddrObj) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_7_139;
          propValue = bean.getNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_23,
              false,null,context);
          }
          propQName = QName_7_207;
          propValue = bean.getName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_82,
              false,null,context);
          }
          propQName = QName_7_223;
          propValue = bean.getPoBox();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_151,
              false,null,context);
          }
          propQName = QName_7_224;
          propValue = bean.getMailNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_23,
              false,null,context);
          }
          propQName = QName_7_225;
          propValue = bean.getMoo();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_29,
              false,null,context);
          }
          propQName = QName_7_226;
          propValue = bean.getVillage();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_121,
              false,null,context);
          }
          propQName = QName_7_227;
          propValue = bean.getBuilding();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_121,
              false,null,context);
          }
          propQName = QName_7_228;
          propValue = bean.getRoom();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_29,
              false,null,context);
          }
          propQName = QName_7_229;
          propValue = bean.getFloor();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_21,
              false,null,context);
          }
          propQName = QName_7_230;
          propValue = bean.getSoi();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_121,
              false,null,context);
          }
          propQName = QName_7_231;
          propValue = bean.getRoad();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_121,
              false,null,context);
          }
          propQName = QName_7_232;
          propValue = bean.getTumbol();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_16,
              false,null,context);
          }
          propQName = QName_7_233;
          propValue = bean.getAmphur();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_16,
              false,null,context);
          }
          propQName = QName_7_234;
          propValue = bean.getProvince();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_121,
              false,null,context);
          }
          propQName = QName_7_235;
          propValue = bean.getPostCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_19,
              false,null,context);
          }
          propQName = QName_7_236;
          propValue = bean.getCntryCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_22,
              false,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_7_235 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "postCode");
    private final static javax.xml.namespace.QName QName_7_225 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "moo");
    private final static javax.xml.namespace.QName QName_7_223 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "poBox");
    private final static javax.xml.namespace.QName QName_7_229 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "floor");
    private final static javax.xml.namespace.QName QName_7_232 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "tumbol");
    private final static javax.xml.namespace.QName QName_7_21 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength8");
    private final static javax.xml.namespace.QName QName_7_19 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength5");
    private final static javax.xml.namespace.QName QName_7_226 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "village");
    private final static javax.xml.namespace.QName QName_7_231 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "road");
    private final static javax.xml.namespace.QName QName_7_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength2");
    private final static javax.xml.namespace.QName QName_7_228 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "room");
    private final static javax.xml.namespace.QName QName_7_234 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "province");
    private final static javax.xml.namespace.QName QName_7_207 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "name");
    private final static javax.xml.namespace.QName QName_7_236 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "cntryCode");
    private final static javax.xml.namespace.QName QName_7_224 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "mailNum");
    private final static javax.xml.namespace.QName QName_7_233 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "amphur");
    private final static javax.xml.namespace.QName QName_7_23 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength15");
    private final static javax.xml.namespace.QName QName_7_82 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength50");
    private final static javax.xml.namespace.QName QName_7_121 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength40");
    private final static javax.xml.namespace.QName QName_7_227 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "building");
    private final static javax.xml.namespace.QName QName_7_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength30");
    private final static javax.xml.namespace.QName QName_7_151 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength20");
    private final static javax.xml.namespace.QName QName_7_29 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength10");
    private final static javax.xml.namespace.QName QName_7_230 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "soi");
    private final static javax.xml.namespace.QName QName_7_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "num");
}
