/**
 * _____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1035a01;

public class _____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public _____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact_Ser(
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
           elemQName = QName_6_139;
           context.qName2String(elemQName, true);
           elemQName = QName_6_138;
           context.qName2String(elemQName, true);
           elemQName = QName_6_203;
           context.qName2String(elemQName, true);
           elemQName = QName_6_204;
           context.qName2String(elemQName, true);
           elemQName = QName_6_205;
           context.qName2String(elemQName, true);
           elemQName = QName_6_213;
           context.qName2String(elemQName, true);
           elemQName = QName_6_214;
           context.qName2String(elemQName, true);
           elemQName = QName_6_49;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        _____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact bean = (_____doServiceResponse_CIS1035A01Response_CISCustomer_contactsObj_contactsVect_CISContact) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_6_139;
          propValue = bean.getNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_6_23,
              false,null,context);
          }
          propQName = QName_6_138;
          propValue = bean.getTypeCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_6_22,
              false,null,context);
          }
          propQName = QName_6_203;
          propValue = bean.getLocationCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_6_22,
              false,null,context);
          }
          propQName = QName_6_204;
          propValue = bean.getPhNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_6_151,
              false,null,context);
          }
          propQName = QName_6_205;
          propValue = bean.getPhExtNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_6_16,
              false,null,context);
          }
          propQName = QName_6_213;
          propValue = bean.getSeqNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_6_29,
              false,null,context);
          }
          propQName = QName_6_214;
          propValue = bean.getSuccessFlag();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_6_30,
              false,null,context);
          }
          propQName = QName_6_49;
          propValue = bean.getErrorDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_6_194,
              false,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_6_49 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "errorDesc");
    private final static javax.xml.namespace.QName QName_6_194 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "stringLength80");
    private final static javax.xml.namespace.QName QName_6_204 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "phNum");
    private final static javax.xml.namespace.QName QName_6_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_6_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "stringLength2");
    private final static javax.xml.namespace.QName QName_6_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "typeCode");
    private final static javax.xml.namespace.QName QName_6_214 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "successFlag");
    private final static javax.xml.namespace.QName QName_6_30 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "stringLength1");
    private final static javax.xml.namespace.QName QName_6_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "stringLength30");
    private final static javax.xml.namespace.QName QName_6_23 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "stringLength15");
    private final static javax.xml.namespace.QName QName_6_29 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "stringLength10");
    private final static javax.xml.namespace.QName QName_6_203 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "locationCode");
    private final static javax.xml.namespace.QName QName_6_213 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "seqNum");
    private final static javax.xml.namespace.QName QName_6_205 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "phExtNum");
    private final static javax.xml.namespace.QName QName_6_151 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "stringLength20");
}
