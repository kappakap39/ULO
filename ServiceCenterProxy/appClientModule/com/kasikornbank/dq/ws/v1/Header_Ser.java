/**
 * Header_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class Header_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public Header_Ser(
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
        Header bean = (Header) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_21_98;
          propValue = bean.getBeginTimestamp();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_18,
              false,null,context);
          propQName = QName_21_99;
          propValue = bean.getEndTimestamp();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_18,
              false,null,context);
          propQName = QName_21_97;
          propValue = bean.getServiceId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_102;
          propValue = bean.getSourceSystem();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_104;
          propValue = new java.lang.Integer(bean.getStatus());
          serializeChild(propQName, null, 
              propValue, 
              QName_1_604,
              true,null,context);
          propQName = QName_21_100;
          propValue = bean.getTransactionId();
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
    private final static javax.xml.namespace.QName QName_21_97 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "serviceId");
    private final static javax.xml.namespace.QName QName_21_102 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "sourceSystem");
    private final static javax.xml.namespace.QName QName_21_100 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "transactionId");
    private final static javax.xml.namespace.QName QName_1_18 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "dateTime");
    private final static javax.xml.namespace.QName QName_21_104 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "status");
    private final static javax.xml.namespace.QName QName_1_109 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_21_99 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "endTimestamp");
    private final static javax.xml.namespace.QName QName_1_604 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "int");
    private final static javax.xml.namespace.QName QName_21_98 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "beginTimestamp");
}
