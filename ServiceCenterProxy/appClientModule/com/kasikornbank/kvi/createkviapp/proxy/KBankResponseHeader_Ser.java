/**
 * KBankResponseHeader_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.createkviapp.proxy;

public class KBankResponseHeader_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public KBankResponseHeader_Ser(
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
           elemQName = QName_1_4;
           context.qName2String(elemQName, true);
           elemQName = QName_1_5;
           context.qName2String(elemQName, true);
           elemQName = QName_1_38;
           context.qName2String(elemQName, true);
           elemQName = QName_1_39;
           context.qName2String(elemQName, true);
           elemQName = QName_1_40;
           context.qName2String(elemQName, true);
           elemQName = QName_1_41;
           context.qName2String(elemQName, true);
           elemQName = QName_1_42;
           context.qName2String(elemQName, true);
           elemQName = QName_1_12;
           context.qName2String(elemQName, true);
           elemQName = QName_1_14;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        KBankResponseHeader bean = (KBankResponseHeader) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_1_4;
          propValue = bean.getFuncNm();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_15,
              false,null,context);
          }
          propQName = QName_1_5;
          propValue = bean.getRqUID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_15,
              false,null,context);
          }
          propQName = QName_1_38;
          propValue = bean.getRsAppId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_15,
              false,null,context);
          }
          propQName = QName_1_39;
          propValue = bean.getRsDt();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_15,
              false,null,context);
          }
          propQName = QName_1_40;
          propValue = bean.getRsUID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_15,
              false,null,context);
          }
          propQName = QName_1_41;
          propValue = bean.getStatusCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_15,
              false,null,context);
          }
          propQName = QName_1_42;
          propValue = bean.getError();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_43,
              false,null,context);
          propQName = QName_1_12;
          propValue = bean.getCorrID();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_15,
              false,null,context);
          }
          propQName = QName_1_14;
          propValue = bean.getFiller();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_2_15,
              false,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_1_39 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RsDt");
    private final static javax.xml.namespace.QName QName_1_43 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "ArrayOfKBankResponseError");
    private final static javax.xml.namespace.QName QName_1_12 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "CorrID");
    private final static javax.xml.namespace.QName QName_1_4 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "FuncNm");
    private final static javax.xml.namespace.QName QName_1_5 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RqUID");
    private final static javax.xml.namespace.QName QName_1_41 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "StatusCode");
    private final static javax.xml.namespace.QName QName_1_40 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RsUID");
    private final static javax.xml.namespace.QName QName_1_14 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "Filler");
    private final static javax.xml.namespace.QName QName_1_42 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "Error");
    private final static javax.xml.namespace.QName QName_2_15 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_1_38 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader",
                  "RsAppId");
}
