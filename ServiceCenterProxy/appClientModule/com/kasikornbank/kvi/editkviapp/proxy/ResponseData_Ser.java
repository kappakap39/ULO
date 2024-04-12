/**
 * ResponseData_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.editkviapp.proxy;

public class ResponseData_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public ResponseData_Ser(
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
           elemQName = QName_12_384;
           context.qName2String(elemQName, true);
           elemQName = QName_12_385;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        ResponseData bean = (ResponseData) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_12_384;
          propValue = bean.getFID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_12_385;
          propValue = bean.getTokenID();
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
    private final static javax.xml.namespace.QName QName_12_384 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.EditKVIApplication",
                  "FID");
    private final static javax.xml.namespace.QName QName_12_385 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.EditKVIApplication",
                  "TokenID");
    private final static javax.xml.namespace.QName QName_1_109 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
}
