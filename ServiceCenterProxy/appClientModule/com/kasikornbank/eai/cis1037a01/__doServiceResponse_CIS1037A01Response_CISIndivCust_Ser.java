/**
 * __doServiceResponse_CIS1037A01Response_CISIndivCust_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1037a01;

public class __doServiceResponse_CIS1037A01Response_CISIndivCust_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public __doServiceResponse_CIS1037A01Response_CISIndivCust_Ser(
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
           elemQName = QName_0_33;
           context.qName2String(elemQName, true);
           elemQName = QName_0_34;
           context.qName2String(elemQName, true);
           elemQName = QName_0_153;
           context.qName2String(elemQName, true);
           elemQName = QName_0_53;
           context.qName2String(elemQName, true);
           elemQName = QName_0_61;
           context.qName2String(elemQName, true);
           elemQName = QName_0_65;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        __doServiceResponse_CIS1037A01Response_CISIndivCust bean = (__doServiceResponse_CIS1037A01Response_CISIndivCust) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_0_33;
          propValue = bean.getContactAddrObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_154,
              false,null,context);
          propQName = QName_0_34;
          propValue = bean.getContactsObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_155,
              false,null,context);
          propQName = QName_0_153;
          propValue = bean.getCVRSInfoObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_156,
              false,null,context);
          propQName = QName_0_53;
          propValue = bean.getKYCObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_157,
              false,null,context);
          propQName = QName_0_61;
          propValue = bean.getNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_102,
              true,null,context);
          }
          propQName = QName_0_65;
          propValue = bean.getOffclAddrObj();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_158,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_0_155 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  ">>>doServiceResponse>CIS1037A01Response>CISIndivCust>contactsObj");
    private final static javax.xml.namespace.QName QName_0_157 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  ">>>doServiceResponse>CIS1037A01Response>CISIndivCust>KYCObj");
    private final static javax.xml.namespace.QName QName_0_154 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  ">>>doServiceResponse>CIS1037A01Response>CISIndivCust>contactAddrObj");
    private final static javax.xml.namespace.QName QName_0_158 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  ">>>doServiceResponse>CIS1037A01Response>CISIndivCust>offclAddrObj");
    private final static javax.xml.namespace.QName QName_0_61 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "num");
    private final static javax.xml.namespace.QName QName_0_153 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "CVRSInfoObj");
    private final static javax.xml.namespace.QName QName_0_102 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "stringLength10");
    private final static javax.xml.namespace.QName QName_0_33 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "contactAddrObj");
    private final static javax.xml.namespace.QName QName_0_53 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "KYCObj");
    private final static javax.xml.namespace.QName QName_0_34 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "contactsObj");
    private final static javax.xml.namespace.QName QName_0_65 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  "offclAddrObj");
    private final static javax.xml.namespace.QName QName_0_156 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1",
                  ">>>doServiceResponse>CIS1037A01Response>CISIndivCust>CVRSInfoObj");
}
