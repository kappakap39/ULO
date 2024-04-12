/**
 * _____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1035a01;

public class _____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public _____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact_Ser(
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
           elemQName = QName_6_138;
           context.qName2String(elemQName, true);
           elemQName = QName_6_203;
           context.qName2String(elemQName, true);
           elemQName = QName_6_204;
           context.qName2String(elemQName, true);
           elemQName = QName_6_205;
           context.qName2String(elemQName, true);
           elemQName = QName_6_206;
           context.qName2String(elemQName, true);
           elemQName = QName_6_207;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        _____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact bean = (_____doService_CIS1035A01_CISCustomer_contactsObj_contactsVect_CISContact) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_6_138;
          propValue = bean.getTypeCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_6_22,
              true,null,context);
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
          propQName = QName_6_206;
          propValue = bean.getPhAvailTimeCde();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_6_22,
              false,null,context);
          }
          propQName = QName_6_207;
          propValue = bean.getName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_6_82,
              false,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_6_82 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "stringLength50");
    private final static javax.xml.namespace.QName QName_6_204 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "phNum");
    private final static javax.xml.namespace.QName QName_6_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "typeCode");
    private final static javax.xml.namespace.QName QName_6_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "stringLength2");
    private final static javax.xml.namespace.QName QName_6_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "stringLength30");
    private final static javax.xml.namespace.QName QName_6_207 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "name");
    private final static javax.xml.namespace.QName QName_6_206 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "phAvailTimeCde");
    private final static javax.xml.namespace.QName QName_6_203 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "locationCode");
    private final static javax.xml.namespace.QName QName_6_205 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "phExtNum");
    private final static javax.xml.namespace.QName QName_6_151 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "stringLength20");
}
