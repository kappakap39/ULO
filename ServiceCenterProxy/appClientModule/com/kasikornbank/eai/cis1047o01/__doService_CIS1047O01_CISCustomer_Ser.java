/**
 * __doService_CIS1047O01_CISCustomer_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1047o01;

public class __doService_CIS1047O01_CISCustomer_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public __doService_CIS1047O01_CISCustomer_Ser(
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
           elemQName = QName_0_25;
           context.qName2String(elemQName, true);
           elemQName = QName_0_26;
           context.qName2String(elemQName, true);
           elemQName = QName_0_27;
           context.qName2String(elemQName, true);
           elemQName = QName_0_28;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        __doService_CIS1047O01_CISCustomer bean = (__doService_CIS1047O01_CISCustomer) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_0_25;
          propValue = bean.getDataTypeCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_29,
              true,null,context);
          }
          propQName = QName_0_26;
          propValue = bean.getNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_30,
              true,null,context);
          }
          propQName = QName_0_27;
          propValue = bean.getContactAddrObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_31,
              false,null,context);
          propQName = QName_0_28;
          propValue = bean.getContactsObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_32,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_0_25 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "dataTypeCode");
    private final static javax.xml.namespace.QName QName_0_30 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "stringLength10");
    private final static javax.xml.namespace.QName QName_0_29 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "stringLength1");
    private final static javax.xml.namespace.QName QName_0_26 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_0_32 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  ">>>doService>CIS1047O01>CISCustomer>contactsObj");
    private final static javax.xml.namespace.QName QName_0_28 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "contactsObj");
    private final static javax.xml.namespace.QName QName_0_31 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  ">>>doService>CIS1047O01>CISCustomer>contactAddrObj");
    private final static javax.xml.namespace.QName QName_0_27 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1047O01/v1",
                  "contactAddrObj");
}
