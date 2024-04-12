/**
 * ____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1046a01;

public class ____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public ____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_Ser(
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
           elemQName = QName_9_336;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        ____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj bean = (____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_9_336;
          propValue = bean.getAddrRelsVect();
          serializeChild(propQName, null, 
              propValue, 
              QName_9_337,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_9_337 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  ">>>>>doService>CIS1046A01>CISCustomer>contactAddrObj>addrRelObj>addrRelsVect");
    private final static javax.xml.namespace.QName QName_9_336 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "addrRelsVect");
}
