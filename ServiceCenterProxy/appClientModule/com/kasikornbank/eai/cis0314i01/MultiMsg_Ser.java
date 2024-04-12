/**
 * MultiMsg_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0314i01;

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
           elemQName = QName_3_104;
           context.qName2String(elemQName, true);
           elemQName = QName_3_105;
           context.qName2String(elemQName, true);
           elemQName = QName_3_124;
           context.qName2String(elemQName, true);
           elemQName = QName_3_127;
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
          propQName = QName_3_104;
          propValue = bean.getStatus();
          serializeChild(propQName, null, 
              propValue, 
              QName_3_110,
              false,null,context);
          propQName = QName_3_105;
          propValue = bean.getReasonCode();
          serializeChild(propQName, null, 
              propValue, 
              QName_3_111,
              false,null,context);
          propQName = QName_3_124;
          propValue = bean.getReasonDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_3_127;
          propValue = bean.getRefFieldVect();
          serializeChild(propQName, null, 
              propValue, 
              QName_3_143,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_3_105 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "reasonCode");
    private final static javax.xml.namespace.QName QName_3_104 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "status");
    private final static javax.xml.namespace.QName QName_3_143 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  ">>>>>doServiceResponse>CIS0314I01Response>EAIHeader>multiMsgVect>MultiMsg>refFieldVect");
    private final static javax.xml.namespace.QName QName_3_111 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "intLength5");
    private final static javax.xml.namespace.QName QName_1_109 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_3_110 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "intLength1");
    private final static javax.xml.namespace.QName QName_3_127 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "refFieldVect");
    private final static javax.xml.namespace.QName QName_3_124 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "reasonDesc");
}
