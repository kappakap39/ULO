/**
 * ___doService_CIS1046A01_CISCustomer_contactAddrObj_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1046a01;

public class ___doService_CIS1046A01_CISCustomer_contactAddrObj_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public ___doService_CIS1046A01_CISCustomer_contactAddrObj_Ser(
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
           elemQName = QName_9_139;
           context.qName2String(elemQName, true);
           elemQName = QName_9_334;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        ___doService_CIS1046A01_CISCustomer_contactAddrObj bean = (___doService_CIS1046A01_CISCustomer_contactAddrObj) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_9_139;
          propValue = bean.getNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_9_23,
              true,null,context);
          }
          propQName = QName_9_334;
          propValue = bean.getAddrRelObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_9_335,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_9_335 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  ">>>>doService>CIS1046A01>CISCustomer>contactAddrObj>addrRelObj");
    private final static javax.xml.namespace.QName QName_9_334 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "addrRelObj");
    private final static javax.xml.namespace.QName QName_9_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_9_23 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "stringLength15");
}
