/**
 * CIS0222I01Response_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0222i01;

public class CIS0222I01Response_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public CIS0222I01Response_Ser(
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
           elemQName = QName_2_93;
           context.qName2String(elemQName, true);
           elemQName = QName_2_94;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        CIS0222I01Response bean = (CIS0222I01Response) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_2_93;
          propValue = bean.getEAIHeader();
          serializeChild(propQName, null, 
              propValue, 
              QName_2_122,
              true,null,context);
          propQName = QName_2_94;
          propValue = bean.getCISCustomer();
          serializeChild(propQName, null, 
              propValue, 
              QName_2_123,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_2_94 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "CISCustomer");
    private final static javax.xml.namespace.QName QName_2_122 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  ">>doServiceResponse>CIS0222I01Response>EAIHeader");
    private final static javax.xml.namespace.QName QName_2_93 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "EAIHeader");
    private final static javax.xml.namespace.QName QName_2_123 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  ">>doServiceResponse>CIS0222I01Response>CISCustomer");
}
