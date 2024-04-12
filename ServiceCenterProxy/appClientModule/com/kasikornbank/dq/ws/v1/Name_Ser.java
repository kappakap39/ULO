/**
 * Name_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class Name_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public Name_Ser(
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
        Name bean = (Name) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_21_618;
          propValue = bean.getFistName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_619;
          propValue = bean.getLastName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_620;
          propValue = bean.getMiddleName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_621;
          propValue = bean.getTitleName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_21_620 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "middleName");
    private final static javax.xml.namespace.QName QName_21_619 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "lastName");
    private final static javax.xml.namespace.QName QName_1_109 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_21_618 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fistName");
    private final static javax.xml.namespace.QName QName_21_621 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "titleName");
}
