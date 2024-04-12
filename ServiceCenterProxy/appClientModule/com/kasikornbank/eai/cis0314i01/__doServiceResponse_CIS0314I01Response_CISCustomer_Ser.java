/**
 * __doServiceResponse_CIS0314I01Response_CISCustomer_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0314i01;

public class __doServiceResponse_CIS0314I01Response_CISCustomer_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public __doServiceResponse_CIS0314I01Response_CISCustomer_Ser(
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
           elemQName = QName_3_139;
           context.qName2String(elemQName, true);
           elemQName = QName_3_138;
           context.qName2String(elemQName, true);
           elemQName = QName_3_144;
           context.qName2String(elemQName, true);
           elemQName = QName_3_145;
           context.qName2String(elemQName, true);
           elemQName = QName_3_146;
           context.qName2String(elemQName, true);
           elemQName = QName_3_147;
           context.qName2String(elemQName, true);
           elemQName = QName_3_148;
           context.qName2String(elemQName, true);
           elemQName = QName_3_149;
           context.qName2String(elemQName, true);
           elemQName = QName_3_150;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        __doServiceResponse_CIS0314I01Response_CISCustomer bean = (__doServiceResponse_CIS0314I01Response_CISCustomer) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_3_139;
          propValue = bean.getNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_29,
              true,null,context);
          }
          propQName = QName_3_138;
          propValue = bean.getTypeCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_30,
              true,null,context);
          }
          propQName = QName_3_144;
          propValue = bean.getThTitle();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_151,
              false,null,context);
          }
          propQName = QName_3_145;
          propValue = bean.getThFstName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_152,
              false,null,context);
          }
          propQName = QName_3_146;
          propValue = bean.getThLstName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_82,
              false,null,context);
          }
          propQName = QName_3_147;
          propValue = bean.getEngTitle();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_151,
              false,null,context);
          }
          propQName = QName_3_148;
          propValue = bean.getEngFstName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_152,
              false,null,context);
          }
          propQName = QName_3_149;
          propValue = bean.getEngLstName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_82,
              false,null,context);
          }
          propQName = QName_3_150;
          propValue = bean.getKYCObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_3_153,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_3_30 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "stringLength1");
    private final static javax.xml.namespace.QName QName_3_150 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "KYCObj");
    private final static javax.xml.namespace.QName QName_3_152 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "stringLength120");
    private final static javax.xml.namespace.QName QName_3_29 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "stringLength10");
    private final static javax.xml.namespace.QName QName_3_153 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  ">>>doServiceResponse>CIS0314I01Response>CISCustomer>KYCObj");
    private final static javax.xml.namespace.QName QName_3_144 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "thTitle");
    private final static javax.xml.namespace.QName QName_3_149 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "engLstName");
    private final static javax.xml.namespace.QName QName_3_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "num");
    private final static javax.xml.namespace.QName QName_3_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "typeCode");
    private final static javax.xml.namespace.QName QName_3_148 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "engFstName");
    private final static javax.xml.namespace.QName QName_3_151 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "stringLength20");
    private final static javax.xml.namespace.QName QName_3_82 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "stringLength50");
    private final static javax.xml.namespace.QName QName_3_146 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "thLstName");
    private final static javax.xml.namespace.QName QName_3_147 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "engTitle");
    private final static javax.xml.namespace.QName QName_3_145 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "thFstName");
}
