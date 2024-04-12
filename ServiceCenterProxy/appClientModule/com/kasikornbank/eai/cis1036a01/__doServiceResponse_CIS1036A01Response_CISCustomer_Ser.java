/**
 * __doServiceResponse_CIS1036A01Response_CISCustomer_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1036a01;

public class __doServiceResponse_CIS1036A01Response_CISCustomer_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public __doServiceResponse_CIS1036A01Response_CISCustomer_Ser(
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
           elemQName = QName_7_139;
           context.qName2String(elemQName, true);
           elemQName = QName_7_219;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        __doServiceResponse_CIS1036A01Response_CISCustomer bean = (__doServiceResponse_CIS1036A01Response_CISCustomer) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
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
          propQName = QName_7_219;
          propValue = bean.getContactAddrObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_7_244,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_7_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_7_219 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "contactAddrObj");
    private final static javax.xml.namespace.QName QName_7_244 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  ">>>doServiceResponse>CIS1036A01Response>CISCustomer>contactAddrObj");
    private final static javax.xml.namespace.QName QName_7_29 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
                  "stringLength10");
}
