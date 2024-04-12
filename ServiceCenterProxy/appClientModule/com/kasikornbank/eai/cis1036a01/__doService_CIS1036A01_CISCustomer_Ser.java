/**
 * __doService_CIS1036A01_CISCustomer_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1036a01;

public class __doService_CIS1036A01_CISCustomer_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public __doService_CIS1036A01_CISCustomer_Ser(
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
           elemQName = QName_7_179;
           context.qName2String(elemQName, true);
           elemQName = QName_7_138;
           context.qName2String(elemQName, true);
           elemQName = QName_7_139;
           context.qName2String(elemQName, true);
           elemQName = QName_7_218;
           context.qName2String(elemQName, true);
           elemQName = QName_7_219;
           context.qName2String(elemQName, true);
           elemQName = QName_7_220;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        __doService_CIS1036A01_CISCustomer bean = (__doService_CIS1036A01_CISCustomer) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_7_179;
          propValue = bean.getValidationFlag();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_22,
              false,null,context);
          }
          propQName = QName_7_138;
          propValue = bean.getTypeCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_30,
              true,null,context);
          }
          propQName = QName_7_139;
          propValue = bean.getNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_29,
              true,null,context);
          }
          propQName = QName_7_218;
          propValue = bean.getCustTypeCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_7_83,
              true,null,context);
          }
          propQName = QName_7_219;
          propValue = bean.getContactAddrObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_7_221,
              false,null,context);
          propQName = QName_7_220;
          propValue = bean.getObsContactAddrObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_7_222,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_7_83 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength4");
    private final static javax.xml.namespace.QName QName_7_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_7_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "typeCode");
    private final static javax.xml.namespace.QName QName_7_218 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "custTypeCode");
    private final static javax.xml.namespace.QName QName_7_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength2");
    private final static javax.xml.namespace.QName QName_7_30 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength1");
    private final static javax.xml.namespace.QName QName_7_219 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "contactAddrObj");
    private final static javax.xml.namespace.QName QName_7_220 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "obsContactAddrObj");
    private final static javax.xml.namespace.QName QName_7_221 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  ">>>doService>CIS1036A01>CISCustomer>contactAddrObj");
    private final static javax.xml.namespace.QName QName_7_29 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength10");
    private final static javax.xml.namespace.QName QName_7_179 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "validationFlag");
    private final static javax.xml.namespace.QName QName_7_222 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  ">>>doService>CIS1036A01>CISCustomer>obsContactAddrObj");
}
