/**
 * __doService_CBS1215I01_KBankHeader_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cbs1215i01;

public class __doService_CBS1215I01_KBankHeader_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public __doService_CBS1215I01_KBankHeader_Ser(
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
           elemQName = QName_0_6;
           context.qName2String(elemQName, true);
           elemQName = QName_0_7;
           context.qName2String(elemQName, true);
           elemQName = QName_0_8;
           context.qName2String(elemQName, true);
           elemQName = QName_0_9;
           context.qName2String(elemQName, true);
           elemQName = QName_0_10;
           context.qName2String(elemQName, true);
           elemQName = QName_0_11;
           context.qName2String(elemQName, true);
           elemQName = QName_0_12;
           context.qName2String(elemQName, true);
           elemQName = QName_0_13;
           context.qName2String(elemQName, true);
           elemQName = QName_0_14;
           context.qName2String(elemQName, true);
           elemQName = QName_0_15;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        __doService_CBS1215I01_KBankHeader bean = (__doService_CBS1215I01_KBankHeader) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_0_6;
          propValue = bean.getFuncNm();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_16,
              true,null,context);
          }
          propQName = QName_0_7;
          propValue = bean.getRqUID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_17,
              true,null,context);
          }
          propQName = QName_0_8;
          propValue = bean.getRqDt();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_18,
              true,null,context);
          propQName = QName_0_9;
          propValue = bean.getRqAppId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_19,
              true,null,context);
          }
          propQName = QName_0_10;
          propValue = bean.getUserId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_20,
              true,null,context);
          }
          propQName = QName_0_11;
          propValue = bean.getTerminalId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_21,
              true,null,context);
          }
          propQName = QName_0_12;
          propValue = bean.getUserLangPref();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_22,
              false,null,context);
          }
          propQName = QName_0_13;
          propValue = bean.getCorrID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_20,
              false,null,context);
          }
          propQName = QName_0_14;
          propValue = bean.getAuthUserId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_23,
              false,null,context);
          }
          propQName = QName_0_15;
          propValue = bean.getAuthLevel();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_24,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_0_10 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "userId");
    private final static javax.xml.namespace.QName QName_0_20 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength32");
    private final static javax.xml.namespace.QName QName_0_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength30");
    private final static javax.xml.namespace.QName QName_0_21 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength8");
    private final static javax.xml.namespace.QName QName_0_11 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "terminalId");
    private final static javax.xml.namespace.QName QName_0_8 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "rqDt");
    private final static javax.xml.namespace.QName QName_0_19 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength5");
    private final static javax.xml.namespace.QName QName_0_9 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "rqAppId");
    private final static javax.xml.namespace.QName QName_0_7 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "rqUID");
    private final static javax.xml.namespace.QName QName_0_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength2");
    private final static javax.xml.namespace.QName QName_0_23 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength15");
    private final static javax.xml.namespace.QName QName_0_12 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "userLangPref");
    private final static javax.xml.namespace.QName QName_1_18 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "dateTime");
    private final static javax.xml.namespace.QName QName_0_14 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "authUserId");
    private final static javax.xml.namespace.QName QName_0_15 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "authLevel");
    private final static javax.xml.namespace.QName QName_0_6 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "funcNm");
    private final static javax.xml.namespace.QName QName_0_17 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength47");
    private final static javax.xml.namespace.QName QName_0_24 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "intLength3");
    private final static javax.xml.namespace.QName QName_0_13 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "corrID");
}
