/**
 * ___doService_CIS0222I01_CISCustomer_zipCodesObj_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0222i01;

public class ___doService_CIS0222I01_CISCustomer_zipCodesObj_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public ___doService_CIS0222I01_CISCustomer_zipCodesObj_Ser(
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
           elemQName = QName_2_114;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        ___doService_CIS0222I01_CISCustomer_zipCodesObj bean = (___doService_CIS0222I01_CISCustomer_zipCodesObj) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_2_114;
          propValue = bean.getZipCodesVect();
          serializeChild(propQName, null, 
              propValue, 
              QName_2_115,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_2_115 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  ">>>>doService>CIS0222I01>CISCustomer>zipCodesObj>zipCodesVect");
    private final static javax.xml.namespace.QName QName_2_114 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "zipCodesVect");
}
