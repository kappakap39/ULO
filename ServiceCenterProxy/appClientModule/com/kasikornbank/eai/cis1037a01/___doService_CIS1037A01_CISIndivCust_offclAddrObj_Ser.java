/**
 * ___doService_CIS1037A01_CISIndivCust_offclAddrObj_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1037a01;

public class ___doService_CIS1037A01_CISIndivCust_offclAddrObj_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public ___doService_CIS1037A01_CISIndivCust_offclAddrObj_Ser(
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
           elemQName = QName_0_109;
           context.qName2String(elemQName, true);
           elemQName = QName_0_110;
           context.qName2String(elemQName, true);
           elemQName = QName_0_111;
           context.qName2String(elemQName, true);
           elemQName = QName_0_112;
           context.qName2String(elemQName, true);
           elemQName = QName_0_113;
           context.qName2String(elemQName, true);
           elemQName = QName_0_114;
           context.qName2String(elemQName, true);
           elemQName = QName_0_115;
           context.qName2String(elemQName, true);
           elemQName = QName_0_116;
           context.qName2String(elemQName, true);
           elemQName = QName_0_117;
           context.qName2String(elemQName, true);
           elemQName = QName_0_118;
           context.qName2String(elemQName, true);
           elemQName = QName_0_119;
           context.qName2String(elemQName, true);
           elemQName = QName_0_120;
           context.qName2String(elemQName, true);
           elemQName = QName_0_121;
           context.qName2String(elemQName, true);
           elemQName = QName_0_122;
           context.qName2String(elemQName, true);
           elemQName = QName_0_123;
           context.qName2String(elemQName, true);
           elemQName = QName_0_124;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        ___doService_CIS1037A01_CISIndivCust_offclAddrObj bean = (___doService_CIS1037A01_CISIndivCust_offclAddrObj) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_0_109;
          propValue = bean.getAmphur();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_14,
              false,null,context);
          }
          propQName = QName_0_110;
          propValue = bean.getBuilding();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_125,
              false,null,context);
          }
          propQName = QName_0_111;
          propValue = bean.getCntryCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_20,
              false,null,context);
          }
          propQName = QName_0_112;
          propValue = bean.getFloor();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_19,
              false,null,context);
          }
          propQName = QName_0_113;
          propValue = bean.getMailNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_95,
              false,null,context);
          }
          propQName = QName_0_114;
          propValue = bean.getMoo();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_102,
              false,null,context);
          }
          propQName = QName_0_115;
          propValue = bean.getName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_98,
              false,null,context);
          }
          propQName = QName_0_116;
          propValue = bean.getPoBox();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_92,
              false,null,context);
          }
          propQName = QName_0_117;
          propValue = bean.getPostCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_17,
              false,null,context);
          }
          propQName = QName_0_118;
          propValue = bean.getPreferedCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_88,
              false,null,context);
          }
          propQName = QName_0_119;
          propValue = bean.getProvince();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_125,
              false,null,context);
          }
          propQName = QName_0_120;
          propValue = bean.getRoad();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_125,
              false,null,context);
          }
          propQName = QName_0_121;
          propValue = bean.getRoom();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_102,
              false,null,context);
          }
          propQName = QName_0_122;
          propValue = bean.getSoi();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_125,
              false,null,context);
          }
          propQName = QName_0_123;
          propValue = bean.getTumbol();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_14,
              false,null,context);
          }
          propQName = QName_0_124;
          propValue = bean.getVillage();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_125,
              false,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_0_117 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "postCode");
    private final static javax.xml.namespace.QName QName_0_102 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength10");
    private final static javax.xml.namespace.QName QName_0_19 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength8");
    private final static javax.xml.namespace.QName QName_0_110 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "building");
    private final static javax.xml.namespace.QName QName_0_121 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "room");
    private final static javax.xml.namespace.QName QName_0_113 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "mailNum");
    private final static javax.xml.namespace.QName QName_0_112 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "floor");
    private final static javax.xml.namespace.QName QName_0_17 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength5");
    private final static javax.xml.namespace.QName QName_0_92 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength20");
    private final static javax.xml.namespace.QName QName_0_20 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength2");
    private final static javax.xml.namespace.QName QName_0_88 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength1");
    private final static javax.xml.namespace.QName QName_0_111 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "cntryCode");
    private final static javax.xml.namespace.QName QName_0_14 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength30");
    private final static javax.xml.namespace.QName QName_0_116 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "poBox");
    private final static javax.xml.namespace.QName QName_0_125 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength40");
    private final static javax.xml.namespace.QName QName_0_98 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength50");
    private final static javax.xml.namespace.QName QName_0_115 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "name");
    private final static javax.xml.namespace.QName QName_0_109 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "amphur");
    private final static javax.xml.namespace.QName QName_0_123 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "tumbol");
    private final static javax.xml.namespace.QName QName_0_122 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "soi");
    private final static javax.xml.namespace.QName QName_0_120 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "road");
    private final static javax.xml.namespace.QName QName_0_118 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "preferedCode");
    private final static javax.xml.namespace.QName QName_0_114 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "moo");
    private final static javax.xml.namespace.QName QName_0_124 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "village");
    private final static javax.xml.namespace.QName QName_0_119 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "province");
    private final static javax.xml.namespace.QName QName_0_95 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength15");
}
