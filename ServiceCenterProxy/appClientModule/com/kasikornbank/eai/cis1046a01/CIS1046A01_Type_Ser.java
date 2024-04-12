/**
 * CIS1046A01_Type_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1046a01;

public class CIS1046A01_Type_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public CIS1046A01_Type_Ser(
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
           elemQName = QName_9_0;
           context.qName2String(elemQName, true);
           elemQName = QName_9_171;
           context.qName2String(elemQName, true);
           elemQName = QName_9_94;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        CIS1046A01_Type bean = (CIS1046A01_Type) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_9_0;
          propValue = bean.getKBankHeader();
          serializeChild(propQName, null, 
              propValue, 
              QName_9_328,
              true,null,context);
          propQName = QName_9_171;
          propValue = bean.getCISHeader();
          serializeChild(propQName, null, 
              propValue, 
              QName_9_329,
              false,null,context);
          propQName = QName_9_94;
          propValue = bean.getCISCustomer();
          serializeChild(propQName, null, 
              propValue, 
              QName_9_330,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_9_94 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "CISCustomer");
    private final static javax.xml.namespace.QName QName_9_330 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  ">>doService>CIS1046A01>CISCustomer");
    private final static javax.xml.namespace.QName QName_9_329 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  ">>doService>CIS1046A01>CISHeader");
    private final static javax.xml.namespace.QName QName_9_328 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  ">>doService>CIS1046A01>KBankHeader");
    private final static javax.xml.namespace.QName QName_9_171 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "CISHeader");
    private final static javax.xml.namespace.QName QName_9_0 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1",
                  "KBankHeader");
}
