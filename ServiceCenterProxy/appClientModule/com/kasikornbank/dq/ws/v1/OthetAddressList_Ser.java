/**
 * OthetAddressList_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class OthetAddressList_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public OthetAddressList_Ser(
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
        OthetAddressList bean = (OthetAddressList) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_21_567;
          {
            propValue = bean.getFields();
            if (propValue != null) {
              for (int i=0; i<java.lang.reflect.Array.getLength(propValue); i++) {
                serializeChild(propQName, null, 
                    java.lang.reflect.Array.get(propValue, i), 
                    QName_22_571,
                    true,null,context);
              }
            }
          }
          propQName = QName_21_623;
          propValue = new java.lang.Integer(bean.getSeqNo());
          serializeChild(propQName, null, 
              propValue, 
              QName_1_604,
              true,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_21_567 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fields");
    private final static javax.xml.namespace.QName QName_1_604 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "int");
    private final static javax.xml.namespace.QName QName_22_571 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://ws.dq.kasikornbank.com/",
                  "dqField");
    private final static javax.xml.namespace.QName QName_21_623 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "seqNo");
}
