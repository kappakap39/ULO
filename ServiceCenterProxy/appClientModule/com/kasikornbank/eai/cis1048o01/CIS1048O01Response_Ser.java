/**
 * CIS1048O01Response_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1048o01;

public class CIS1048O01Response_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public CIS1048O01Response_Ser(
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
           elemQName = QName_20_0;
           context.qName2String(elemQName, true);
           elemQName = QName_20_2;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        CIS1048O01Response bean = (CIS1048O01Response) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_20_0;
          propValue = bean.getKBankHeader();
          serializeChild(propQName, null, 
              propValue, 
              QName_20_561,
              true,null,context);
          propQName = QName_20_2;
          propValue = bean.getCISCustomer();
          serializeChild(propQName, null, 
              propValue, 
              QName_20_562,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_20_0 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1",
                  "KBankHeader");
    private final static javax.xml.namespace.QName QName_20_562 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1",
                  ">>doServiceResponse>CIS1048O01Response>CISCustomer");
    private final static javax.xml.namespace.QName QName_20_561 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1",
                  ">>doServiceResponse>CIS1048O01Response>KBankHeader");
    private final static javax.xml.namespace.QName QName_20_2 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1",
                  "CISCustomer");
}
