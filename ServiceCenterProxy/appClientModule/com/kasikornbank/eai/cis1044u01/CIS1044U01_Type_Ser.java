/**
 * CIS1044U01_Type_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1044u01;

public class CIS1044U01_Type_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public CIS1044U01_Type_Ser(
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
           elemQName = QName_2_0;
           context.qName2String(elemQName, true);
           elemQName = QName_2_1;
           context.qName2String(elemQName, true);
           elemQName = QName_2_2;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        CIS1044U01_Type bean = (CIS1044U01_Type) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_2_0;
          propValue = bean.getKBankHeader();
          serializeChild(propQName, null, 
              propValue, 
              QName_2_168,
              true,null,context);
          propQName = QName_2_1;
          propValue = bean.getCISHeader();
          serializeChild(propQName, null, 
              propValue, 
              QName_2_169,
              false,null,context);
          propQName = QName_2_2;
          propValue = bean.getCISIndivCust();
          serializeChild(propQName, null, 
              propValue, 
              QName_2_170,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_2_0 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "KBankHeader");
    private final static javax.xml.namespace.QName QName_2_170 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  ">>doService>CIS1044U01>CISIndivCust");
    private final static javax.xml.namespace.QName QName_2_2 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "CISIndivCust");
    private final static javax.xml.namespace.QName QName_2_168 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  ">>doService>CIS1044U01>KBankHeader");
    private final static javax.xml.namespace.QName QName_2_1 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  "CISHeader");
    private final static javax.xml.namespace.QName QName_2_169 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1044U01/v1",
                  ">>doService>CIS1044U01>CISHeader");
}
