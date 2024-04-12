/**
 * ContactNumber_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class ContactNumber_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public ContactNumber_Ser(
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
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        ContactNumber bean = (ContactNumber) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_21_615;
          propValue = bean.getExt();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_612;
          propValue = bean.getLocation();
          serializeChild(propQName, null, 
              propValue, 
              QName_22_614,
              false,null,context);
          propQName = QName_21_608;
          propValue = bean.getNumber();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_616;
          propValue = bean.getType();
          serializeChild(propQName, null, 
              propValue, 
              QName_22_617,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_22_617 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://ws.dq.kasikornbank.com/",
                  "contactType");
    private final static javax.xml.namespace.QName QName_21_616 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "type");
    private final static javax.xml.namespace.QName QName_1_109 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_21_608 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "number");
    private final static javax.xml.namespace.QName QName_21_615 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "ext");
    private final static javax.xml.namespace.QName QName_22_614 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://ws.dq.kasikornbank.com/",
                  "locationType");
    private final static javax.xml.namespace.QName QName_21_612 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "location");
}
