/**
 * __doService_CIS0222I01_EAIHeader_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0222i01;

public class __doService_CIS0222I01_EAIHeader_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public __doService_CIS0222I01_EAIHeader_Ser(
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
           elemQName = QName_2_97;
           context.qName2String(elemQName, true);
           elemQName = QName_2_98;
           context.qName2String(elemQName, true);
           elemQName = QName_2_99;
           context.qName2String(elemQName, true);
           elemQName = QName_2_100;
           context.qName2String(elemQName, true);
           elemQName = QName_2_101;
           context.qName2String(elemQName, true);
           elemQName = QName_2_102;
           context.qName2String(elemQName, true);
           elemQName = QName_2_10;
           context.qName2String(elemQName, true);
           elemQName = QName_2_103;
           context.qName2String(elemQName, true);
           elemQName = QName_2_104;
           context.qName2String(elemQName, true);
           elemQName = QName_2_105;
           context.qName2String(elemQName, true);
           elemQName = QName_2_106;
           context.qName2String(elemQName, true);
           elemQName = QName_2_107;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        __doService_CIS0222I01_EAIHeader bean = (__doService_CIS0222I01_EAIHeader) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_2_97;
          propValue = bean.getServiceId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_108,
              true,null,context);
          }
          propQName = QName_2_98;
          propValue = bean.getBeginTimestamp();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_18,
              true,null,context);
          propQName = QName_2_99;
          propValue = bean.getEndTimestamp();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_18,
              false,null,context);
          propQName = QName_2_100;
          propValue = bean.getTransactionId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_20,
              false,null,context);
          }
          propQName = QName_2_101;
          propValue = bean.getSourceTransactionId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_2_102;
          propValue = bean.getSourceSystem();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              true,null,context);
          }
          propQName = QName_2_10;
          propValue = bean.getUserId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_2_103;
          propValue = bean.getPassword();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_2_104;
          propValue = bean.getStatus();
          serializeChild(propQName, null, 
              propValue, 
              QName_2_110,
              false,null,context);
          propQName = QName_2_105;
          propValue = bean.getReasonCode();
          serializeChild(propQName, null, 
              propValue, 
              QName_2_111,
              false,null,context);
          propQName = QName_2_106;
          propValue = bean.getMultiMsgFlg();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_30,
              false,null,context);
          }
          propQName = QName_2_107;
          propValue = bean.getOrderMsgTypeCode();
          serializeChild(propQName, null, 
              propValue, 
              QName_2_110,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_2_108 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "stringLength12");
    private final static javax.xml.namespace.QName QName_2_103 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "password");
    private final static javax.xml.namespace.QName QName_2_97 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "serviceId");
    private final static javax.xml.namespace.QName QName_2_101 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "sourceTransactionId");
    private final static javax.xml.namespace.QName QName_1_18 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "dateTime");
    private final static javax.xml.namespace.QName QName_2_107 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "orderMsgTypeCode");
    private final static javax.xml.namespace.QName QName_2_105 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "reasonCode");
    private final static javax.xml.namespace.QName QName_2_104 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "status");
    private final static javax.xml.namespace.QName QName_1_109 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_2_106 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "multiMsgFlg");
    private final static javax.xml.namespace.QName QName_2_99 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "endTimestamp");
    private final static javax.xml.namespace.QName QName_2_10 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "userId");
    private final static javax.xml.namespace.QName QName_2_100 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "transactionId");
    private final static javax.xml.namespace.QName QName_2_98 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "beginTimestamp");
    private final static javax.xml.namespace.QName QName_2_102 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "sourceSystem");
    private final static javax.xml.namespace.QName QName_2_111 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "intLength5");
    private final static javax.xml.namespace.QName QName_2_30 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "stringLength1");
    private final static javax.xml.namespace.QName QName_2_110 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "intLength1");
    private final static javax.xml.namespace.QName QName_2_20 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "stringLength32");
}
