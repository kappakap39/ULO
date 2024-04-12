/**
 * __doServiceResponse_CIS1034A01Response_KBankHeader_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1034a01;

public class __doServiceResponse_CIS1034A01Response_KBankHeader_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public __doServiceResponse_CIS1034A01Response_KBankHeader_Ser(
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
           elemQName = QName_5_6;
           context.qName2String(elemQName, true);
           elemQName = QName_5_7;
           context.qName2String(elemQName, true);
           elemQName = QName_5_40;
           context.qName2String(elemQName, true);
           elemQName = QName_5_41;
           context.qName2String(elemQName, true);
           elemQName = QName_5_42;
           context.qName2String(elemQName, true);
           elemQName = QName_5_43;
           context.qName2String(elemQName, true);
           elemQName = QName_5_44;
           context.qName2String(elemQName, true);
           elemQName = QName_5_13;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        __doServiceResponse_CIS1034A01Response_KBankHeader bean = (__doServiceResponse_CIS1034A01Response_KBankHeader) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_5_6;
          propValue = bean.getFuncNm();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_5_16,
              true,null,context);
          }
          propQName = QName_5_7;
          propValue = bean.getRqUID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_5_17,
              true,null,context);
          }
          propQName = QName_5_40;
          propValue = bean.getRsAppId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_5_19,
              true,null,context);
          }
          propQName = QName_5_41;
          propValue = bean.getRsUID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_5_17,
              true,null,context);
          }
          propQName = QName_5_42;
          propValue = bean.getRsDt();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_18,
              true,null,context);
          propQName = QName_5_43;
          propValue = bean.getStatusCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_5_22,
              true,null,context);
          }
          propQName = QName_5_44;
          propValue = bean.getErrorVect();
          serializeChild(propQName, null, 
              propValue, 
              QName_5_190,
              false,null,context);
          propQName = QName_5_13;
          propValue = bean.getCorrID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_5_20,
              false,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_5_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "stringLength2");
    private final static javax.xml.namespace.QName QName_5_20 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "stringLength32");
    private final static javax.xml.namespace.QName QName_5_6 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "funcNm");
    private final static javax.xml.namespace.QName QName_5_13 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "corrID");
    private final static javax.xml.namespace.QName QName_5_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "stringLength30");
    private final static javax.xml.namespace.QName QName_5_17 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "stringLength47");
    private final static javax.xml.namespace.QName QName_5_43 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "statusCode");
    private final static javax.xml.namespace.QName QName_5_44 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "errorVect");
    private final static javax.xml.namespace.QName QName_5_190 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  ">>>doServiceResponse>CIS1034A01Response>KBankHeader>errorVect");
    private final static javax.xml.namespace.QName QName_5_42 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "rsDt");
    private final static javax.xml.namespace.QName QName_5_41 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "rsUID");
    private final static javax.xml.namespace.QName QName_5_7 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "rqUID");
    private final static javax.xml.namespace.QName QName_1_18 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "dateTime");
    private final static javax.xml.namespace.QName QName_5_40 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "rsAppId");
    private final static javax.xml.namespace.QName QName_5_19 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "stringLength5");
}
