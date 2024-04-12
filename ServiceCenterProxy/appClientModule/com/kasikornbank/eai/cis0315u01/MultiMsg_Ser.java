/**
 * MultiMsg_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0315u01;

public class MultiMsg_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public MultiMsg_Ser(
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
           elemQName = QName_3_55;
           context.qName2String(elemQName, true);
           elemQName = QName_3_56;
           context.qName2String(elemQName, true);
           elemQName = QName_3_79;
           context.qName2String(elemQName, true);
           elemQName = QName_3_82;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        MultiMsg bean = (MultiMsg) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_3_55;
          propValue = bean.getStatus();
          serializeChild(propQName, null, 
              propValue, 
              QName_3_61,
              false,null,context);
          propQName = QName_3_56;
          propValue = bean.getReasonCode();
          serializeChild(propQName, null, 
              propValue, 
              QName_3_62,
              false,null,context);
          propQName = QName_3_79;
          propValue = bean.getReasonDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_12,
              false,null,context);
          }
          propQName = QName_3_82;
          propValue = bean.getRefFieldVect();
          serializeChild(propQName, null, 
              propValue, 
              QName_3_83,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_3_55 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "status");
    private final static javax.xml.namespace.QName QName_2_12 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_3_61 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "intLength1");
    private final static javax.xml.namespace.QName QName_3_79 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "reasonDesc");
    private final static javax.xml.namespace.QName QName_3_56 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "reasonCode");
    private final static javax.xml.namespace.QName QName_3_83 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  ">>>>>doServiceResponse>CIS0315U01Response>EAIHeader>multiMsgVect>MultiMsg>refFieldVect");
    private final static javax.xml.namespace.QName QName_3_62 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "intLength5");
    private final static javax.xml.namespace.QName QName_3_82 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0315U01/v2",
                  "refFieldVect");
}
