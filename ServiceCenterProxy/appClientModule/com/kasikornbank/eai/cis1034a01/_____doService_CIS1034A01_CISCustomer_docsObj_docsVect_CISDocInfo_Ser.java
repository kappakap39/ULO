/**
 * _____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1034a01;

public class _____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public _____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo_Ser(
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
           elemQName = QName_5_139;
           context.qName2String(elemQName, true);
           elemQName = QName_5_138;
           context.qName2String(elemQName, true);
           elemQName = QName_5_185;
           context.qName2String(elemQName, true);
           elemQName = QName_5_186;
           context.qName2String(elemQName, true);
           elemQName = QName_5_187;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        _____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo bean = (_____doService_CIS1034A01_CISCustomer_docsObj_docsVect_CISDocInfo) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_5_139;
          propValue = bean.getNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_5_23,
              true,null,context);
          }
          propQName = QName_5_138;
          propValue = bean.getTypeCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_5_22,
              true,null,context);
          }
          propQName = QName_5_185;
          propValue = bean.getCardIssuePlace();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_5_16,
              false,null,context);
          }
          propQName = QName_5_186;
          propValue = bean.getCardIssueDate();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_86,
              false,null,context);
          propQName = QName_5_187;
          propValue = bean.getCardExpDate();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_86,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_5_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_5_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "stringLength2");
    private final static javax.xml.namespace.QName QName_5_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "typeCode");
    private final static javax.xml.namespace.QName QName_5_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "stringLength30");
    private final static javax.xml.namespace.QName QName_5_23 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "stringLength15");
    private final static javax.xml.namespace.QName QName_5_185 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "cardIssuePlace");
    private final static javax.xml.namespace.QName QName_1_86 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "date");
    private final static javax.xml.namespace.QName QName_5_186 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "cardIssueDate");
    private final static javax.xml.namespace.QName QName_5_187 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1034A01/v1",
                  "cardExpDate");
}
