/**
 * ___doService_CIS1035A01_CISCustomer_contactsObj_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1035a01;

public class ___doService_CIS1035A01_CISCustomer_contactsObj_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public ___doService_CIS1035A01_CISCustomer_contactsObj_Ser(
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
           elemQName = QName_6_200;
           context.qName2String(elemQName, true);
           elemQName = QName_6_201;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        ___doService_CIS1035A01_CISCustomer_contactsObj bean = (___doService_CIS1035A01_CISCustomer_contactsObj) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_6_200;
          propValue = bean.getTotCntctCnt();
          serializeChild(propQName, null, 
              propValue, 
              QName_6_34,
              true,null,context);
          propQName = QName_6_201;
          propValue = bean.getContactsVect();
          serializeChild(propQName, null, 
              propValue, 
              QName_6_202,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_6_34 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "intLength2");
    private final static javax.xml.namespace.QName QName_6_201 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "contactsVect");
    private final static javax.xml.namespace.QName QName_6_200 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  "totCntctCnt");
    private final static javax.xml.namespace.QName QName_6_202 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1035A01/v1",
                  ">>>>doService>CIS1035A01>CISCustomer>contactsObj>contactsVect");
}
