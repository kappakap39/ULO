/**
 * __doService_CIS1046A01_KBankHeader_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1046a01;

public class __doService_CIS1046A01_KBankHeader_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public __doService_CIS1046A01_KBankHeader_Ser(
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
           elemQName = QName_9_6;
           context.qName2String(elemQName, true);
           elemQName = QName_9_7;
           context.qName2String(elemQName, true);
           elemQName = QName_9_8;
           context.qName2String(elemQName, true);
           elemQName = QName_9_9;
           context.qName2String(elemQName, true);
           elemQName = QName_9_10;
           context.qName2String(elemQName, true);
           elemQName = QName_9_11;
           context.qName2String(elemQName, true);
           elemQName = QName_9_12;
           context.qName2String(elemQName, true);
           elemQName = QName_9_13;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        __doService_CIS1046A01_KBankHeader bean = (__doService_CIS1046A01_KBankHeader) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_9_6;
          propValue = bean.getFuncNm();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_9_16,
              true,null,context);
          }
          propQName = QName_9_7;
          propValue = bean.getRqUID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_9_17,
              true,null,context);
          }
          propQName = QName_9_8;
          propValue = bean.getRqDt();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_18,
              true,null,context);
          propQName = QName_9_9;
          propValue = bean.getRqAppId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_9_19,
              true,null,context);
          }
          propQName = QName_9_10;
          propValue = bean.getUserId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_9_20,
              true,null,context);
          }
          propQName = QName_9_11;
          propValue = bean.getTerminalId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_9_21,
              true,null,context);
          }
          propQName = QName_9_12;
          propValue = bean.getUserLangPref();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_9_22,
              false,null,context);
          }
          propQName = QName_9_13;
          propValue = bean.getCorrID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_9_20,
              false,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_9_19 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "stringLength5");
    private final static javax.xml.namespace.QName QName_9_6 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "funcNm");
    private final static javax.xml.namespace.QName QName_9_13 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "corrID");
    private final static javax.xml.namespace.QName QName_9_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "stringLength2");
    private final static javax.xml.namespace.QName QName_9_20 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "stringLength32");
    private final static javax.xml.namespace.QName QName_9_8 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "rqDt");
    private final static javax.xml.namespace.QName QName_9_9 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "rqAppId");
    private final static javax.xml.namespace.QName QName_9_12 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "userLangPref");
    private final static javax.xml.namespace.QName QName_9_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "stringLength30");
    private final static javax.xml.namespace.QName QName_9_17 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "stringLength47");
    private final static javax.xml.namespace.QName QName_9_10 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "userId");
    private final static javax.xml.namespace.QName QName_1_18 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "dateTime");
    private final static javax.xml.namespace.QName QName_9_11 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "terminalId");
    private final static javax.xml.namespace.QName QName_9_7 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "rqUID");
    private final static javax.xml.namespace.QName QName_9_21 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "stringLength8");
}
