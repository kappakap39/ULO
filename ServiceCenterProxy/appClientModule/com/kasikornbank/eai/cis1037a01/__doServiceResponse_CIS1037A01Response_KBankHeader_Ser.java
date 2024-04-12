/**
 * __doServiceResponse_CIS1037A01Response_KBankHeader_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1037a01;

public class __doServiceResponse_CIS1037A01Response_KBankHeader_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public __doServiceResponse_CIS1037A01Response_KBankHeader_Ser(
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
           elemQName = QName_0_141;
           context.qName2String(elemQName, true);
           elemQName = QName_0_142;
           context.qName2String(elemQName, true);
           elemQName = QName_0_143;
           context.qName2String(elemQName, true);
           elemQName = QName_0_144;
           context.qName2String(elemQName, true);
           elemQName = QName_0_145;
           context.qName2String(elemQName, true);
           elemQName = QName_0_13;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        __doServiceResponse_CIS1037A01Response_KBankHeader bean = (__doServiceResponse_CIS1037A01Response_KBankHeader) value;
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
              QName_0_14,
              true,null,context);
          }
          propQName = QName_0_7;
          propValue = bean.getRqUID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_15,
              true,null,context);
          }
          propQName = QName_0_141;
          propValue = bean.getRsAppId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_17,
              true,null,context);
          }
          propQName = QName_0_142;
          propValue = bean.getRsUID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_15,
              true,null,context);
          }
          propQName = QName_0_143;
          propValue = bean.getRsDt();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_16,
              true,null,context);
          propQName = QName_0_144;
          propValue = bean.getStatusCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_20,
              true,null,context);
          }
          propQName = QName_0_145;
          propValue = bean.getErrorVect();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_146,
              false,null,context);
          propQName = QName_0_13;
          propValue = bean.getCorrID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_18,
              false,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_0_17 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength5");
    private final static javax.xml.namespace.QName QName_0_146 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  ">>>doServiceResponse>CIS1037A01Response>KBankHeader>errorVect");
    private final static javax.xml.namespace.QName QName_0_13 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "corrID");
    private final static javax.xml.namespace.QName QName_0_20 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength2");
    private final static javax.xml.namespace.QName QName_0_141 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "rsAppId");
    private final static javax.xml.namespace.QName QName_0_15 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength47");
    private final static javax.xml.namespace.QName QName_0_6 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "funcNm");
    private final static javax.xml.namespace.QName QName_0_18 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength32");
    private final static javax.xml.namespace.QName QName_1_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "dateTime");
    private final static javax.xml.namespace.QName QName_0_143 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "rsDt");
    private final static javax.xml.namespace.QName QName_0_14 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength30");
    private final static javax.xml.namespace.QName QName_0_145 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "errorVect");
    private final static javax.xml.namespace.QName QName_0_7 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "rqUID");
    private final static javax.xml.namespace.QName QName_0_142 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "rsUID");
    private final static javax.xml.namespace.QName QName_0_144 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "statusCode");
}
