/**
 * _____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0222i01;

public class _____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public _____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode_Ser(
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
           elemQName = QName_2_118;
           context.qName2String(elemQName, true);
           elemQName = QName_2_119;
           context.qName2String(elemQName, true);
           elemQName = QName_2_120;
           context.qName2String(elemQName, true);
           elemQName = QName_2_135;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        _____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode bean = (_____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_2_118;
          propValue = bean.getTumbolDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_16,
              false,null,context);
          }
          propQName = QName_2_119;
          propValue = bean.getAmphurDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_16,
              false,null,context);
          }
          propQName = QName_2_120;
          propValue = bean.getProvinceDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_121,
              false,null,context);
          }
          propQName = QName_2_135;
          propValue = bean.getZipCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_19,
              false,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_2_120 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "provinceDesc");
    private final static javax.xml.namespace.QName QName_2_135 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "zipCode");
    private final static javax.xml.namespace.QName QName_2_119 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "amphurDesc");
    private final static javax.xml.namespace.QName QName_2_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "stringLength30");
    private final static javax.xml.namespace.QName QName_2_121 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "stringLength40");
    private final static javax.xml.namespace.QName QName_2_19 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "stringLength5");
    private final static javax.xml.namespace.QName QName_2_118 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2",
                  "tumbolDesc");
}
