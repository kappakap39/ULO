/**
 * KYCObj_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0314i01;

public class KYCObj_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public KYCObj_Ser(
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
           elemQName = QName_3_154;
           context.qName2String(elemQName, true);
           elemQName = QName_3_155;
           context.qName2String(elemQName, true);
           elemQName = QName_3_156;
           context.qName2String(elemQName, true);
           elemQName = QName_3_157;
           context.qName2String(elemQName, true);
           elemQName = QName_3_158;
           context.qName2String(elemQName, true);
           elemQName = QName_3_159;
           context.qName2String(elemQName, true);
           elemQName = QName_3_160;
           context.qName2String(elemQName, true);
           elemQName = QName_3_161;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        KYCObj bean = (KYCObj) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_3_154;
          propValue = bean.getSrcAsstCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_23,
              false,null,context);
          }
          propQName = QName_3_155;
          propValue = bean.getSrcAsstOthDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_84,
              false,null,context);
          }
          propQName = QName_3_156;
          propValue = bean.getPolitcnPosiDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_84,
              false,null,context);
          }
          propQName = QName_3_157;
          propValue = bean.getValAsstAmt();
          serializeChild(propQName, null, 
              propValue, 
              QName_3_162,
              false,null,context);
          propQName = QName_3_158;
          propValue = bean.getRiskResnCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_121,
              false,null,context);
          }
          propQName = QName_3_159;
          propValue = bean.getValAsstCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_22,
              false,null,context);
          }
          propQName = QName_3_160;
          propValue = bean.getValAsstDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_163,
              false,null,context);
          }
          propQName = QName_3_161;
          propValue = bean.getRiskLevCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_3_30,
              false,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_3_30 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "stringLength1");
    private final static javax.xml.namespace.QName QName_3_155 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "srcAsstOthDesc");
    private final static javax.xml.namespace.QName QName_3_84 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "stringLength100");
    private final static javax.xml.namespace.QName QName_3_157 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "valAsstAmt");
    private final static javax.xml.namespace.QName QName_3_163 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "stringLength60");
    private final static javax.xml.namespace.QName QName_3_23 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "stringLength15");
    private final static javax.xml.namespace.QName QName_3_159 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "valAsstCode");
    private final static javax.xml.namespace.QName QName_3_161 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "riskLevCode");
    private final static javax.xml.namespace.QName QName_3_154 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "srcAsstCode");
    private final static javax.xml.namespace.QName QName_3_121 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "stringLength40");
    private final static javax.xml.namespace.QName QName_3_162 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "decimalLength18frictions17_2");
    private final static javax.xml.namespace.QName QName_3_156 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "politcnPosiDesc");
    private final static javax.xml.namespace.QName QName_3_160 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "valAsstDesc");
    private final static javax.xml.namespace.QName QName_3_158 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "riskResnCode");
    private final static javax.xml.namespace.QName QName_3_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CIS0314I01/v2",
                  "stringLength2");
}
