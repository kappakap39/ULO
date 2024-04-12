/**
 * DataQualityRequest_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class DataQualityRequest_Ser extends com.kasikornbank.dq.ws.v1.BaseRequest_Ser {
    /**
     * Constructor
     */
    public DataQualityRequest_Ser(
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
        attributes = super.addAttributes(attributes, value, context);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        super.addElements(value, context);
        DataQualityRequest bean = (DataQualityRequest) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_21_564;
          propValue = bean.getCustomerProfile();
          serializeChild(propQName, null, 
              propValue, 
              QName_22_564,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_21_564 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "customerProfile");
    private final static javax.xml.namespace.QName QName_22_564 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://ws.dq.kasikornbank.com/",
                  "customerProfile");
}
