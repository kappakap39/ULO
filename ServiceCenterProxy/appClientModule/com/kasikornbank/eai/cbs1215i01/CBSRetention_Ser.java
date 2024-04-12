/**
 * CBSRetention_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cbs1215i01;

public class CBSRetention_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public CBSRetention_Ser(
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
           elemQName = QName_0_58;
           context.qName2String(elemQName, true);
           elemQName = QName_0_59;
           context.qName2String(elemQName, true);
           elemQName = QName_0_60;
           context.qName2String(elemQName, true);
           elemQName = QName_0_61;
           context.qName2String(elemQName, true);
           elemQName = QName_0_62;
           context.qName2String(elemQName, true);
           elemQName = QName_0_63;
           context.qName2String(elemQName, true);
           elemQName = QName_0_64;
           context.qName2String(elemQName, true);
           elemQName = QName_0_65;
           context.qName2String(elemQName, true);
           elemQName = QName_0_66;
           context.qName2String(elemQName, true);
           elemQName = QName_0_67;
           context.qName2String(elemQName, true);
           elemQName = QName_0_68;
           context.qName2String(elemQName, true);
           elemQName = QName_0_69;
           context.qName2String(elemQName, true);
           elemQName = QName_0_70;
           context.qName2String(elemQName, true);
           elemQName = QName_0_71;
           context.qName2String(elemQName, true);
           elemQName = QName_0_72;
           context.qName2String(elemQName, true);
           elemQName = QName_0_73;
           context.qName2String(elemQName, true);
           elemQName = QName_0_74;
           context.qName2String(elemQName, true);
           elemQName = QName_0_75;
           context.qName2String(elemQName, true);
           elemQName = QName_0_76;
           context.qName2String(elemQName, true);
           elemQName = QName_0_26;
           context.qName2String(elemQName, true);
           elemQName = QName_0_27;
           context.qName2String(elemQName, true);
           elemQName = QName_0_77;
           context.qName2String(elemQName, true);
           elemQName = QName_0_28;
           context.qName2String(elemQName, true);
           elemQName = QName_0_78;
           context.qName2String(elemQName, true);
           elemQName = QName_0_79;
           context.qName2String(elemQName, true);
           elemQName = QName_0_80;
           context.qName2String(elemQName, true);
           elemQName = QName_0_81;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        CBSRetention bean = (CBSRetention) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_0_58;
          propValue = bean.getCancelAuthUserId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_23,
              false,null,context);
          }
          propQName = QName_0_59;
          propValue = bean.getCancelBranchDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_82,
              false,null,context);
          }
          propQName = QName_0_60;
          propValue = bean.getCancelBranchId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_83,
              false,null,context);
          }
          propQName = QName_0_61;
          propValue = bean.getCancelRetentionReason();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_82,
              false,null,context);
          }
          propQName = QName_0_62;
          propValue = bean.getCancelUserId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_21,
              false,null,context);
          }
          propQName = QName_0_63;
          propValue = bean.getConcept();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_84,
              false,null,context);
          }
          propQName = QName_0_64;
          propValue = bean.getCreateAuthUserId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_23,
              false,null,context);
          }
          propQName = QName_0_65;
          propValue = bean.getCurCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_31,
              true,null,context);
          }
          propQName = QName_0_66;
          propValue = bean.getEntryOrigin();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_82,
              false,null,context);
          }
          propQName = QName_0_67;
          propValue = bean.getIndexSeq();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_85,
              true,null,context);
          propQName = QName_0_68;
          propValue = bean.getRetentionAmt();
          serializeChild(propQName, null, 
              propValue, 
              QName_0_57,
              true,null,context);
          propQName = QName_0_69;
          propValue = bean.getRetentionCancelDt();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_86,
              false,null,context);
          propQName = QName_0_70;
          propValue = bean.getRetentionCancelTime();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_18,
              false,null,context);
          propQName = QName_0_71;
          propValue = bean.getRetentionCreateTime();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_18,
              true,null,context);
          propQName = QName_0_72;
          propValue = bean.getRetentionCreationDt();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_86,
              true,null,context);
          propQName = QName_0_73;
          propValue = bean.getRetentionInd();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_30,
              true,null,context);
          }
          propQName = QName_0_74;
          propValue = bean.getRetentionIndDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_29,
              true,null,context);
          }
          propQName = QName_0_75;
          propValue = bean.getRetentionMatDt();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_86,
              true,null,context);
          propQName = QName_0_76;
          propValue = bean.getRetentionNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_87,
              true,null,context);
          }
          propQName = QName_0_26;
          propValue = bean.getRetentionSitnCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_29,
              true,null,context);
          }
          propQName = QName_0_27;
          propValue = bean.getRetentionType();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_22,
              true,null,context);
          }
          propQName = QName_0_77;
          propValue = bean.getRetentionTypeDesc();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_82,
              true,null,context);
          }
          propQName = QName_0_28;
          propValue = bean.getSubTermNum();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_31,
              false,null,context);
          }
          propQName = QName_0_78;
          propValue = bean.getTransactionDt();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_86,
              true,null,context);
          propQName = QName_0_79;
          propValue = bean.getTrnBranchId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_83,
              true,null,context);
          }
          propQName = QName_0_80;
          propValue = bean.getTrnBranchName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_82,
              true,null,context);
          }
          propQName = QName_0_81;
          propValue = bean.getUserId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_0_21,
              true,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_0_84 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength100");
    private final static javax.xml.namespace.QName QName_0_26 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionSitnCode");
    private final static javax.xml.namespace.QName QName_0_27 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionType");
    private final static javax.xml.namespace.QName QName_0_79 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "TrnBranchId");
    private final static javax.xml.namespace.QName QName_0_78 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "TransactionDt");
    private final static javax.xml.namespace.QName QName_0_58 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "CancelAuthUserId");
    private final static javax.xml.namespace.QName QName_0_59 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "CancelBranchDesc");
    private final static javax.xml.namespace.QName QName_0_77 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionTypeDesc");
    private final static javax.xml.namespace.QName QName_0_63 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "Concept");
    private final static javax.xml.namespace.QName QName_0_75 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionMatDt");
    private final static javax.xml.namespace.QName QName_0_28 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "SubTermNum");
    private final static javax.xml.namespace.QName QName_0_23 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength15");
    private final static javax.xml.namespace.QName QName_0_82 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength50");
    private final static javax.xml.namespace.QName QName_0_29 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength10");
    private final static javax.xml.namespace.QName QName_0_76 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionNum");
    private final static javax.xml.namespace.QName QName_0_74 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionIndDesc");
    private final static javax.xml.namespace.QName QName_1_86 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "date");
    private final static javax.xml.namespace.QName QName_0_81 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "UserId");
    private final static javax.xml.namespace.QName QName_0_57 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "decimalLength18frictions18_2");
    private final static javax.xml.namespace.QName QName_0_69 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionCancelDt");
    private final static javax.xml.namespace.QName QName_0_68 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionAmt");
    private final static javax.xml.namespace.QName QName_1_18 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "dateTime");
    private final static javax.xml.namespace.QName QName_0_65 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "CurCode");
    private final static javax.xml.namespace.QName QName_0_85 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "intLength4");
    private final static javax.xml.namespace.QName QName_0_61 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "CancelRetentionReason");
    private final static javax.xml.namespace.QName QName_0_72 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionCreationDt");
    private final static javax.xml.namespace.QName QName_0_73 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionInd");
    private final static javax.xml.namespace.QName QName_0_71 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionCreateTime");
    private final static javax.xml.namespace.QName QName_0_62 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "CancelUserId");
    private final static javax.xml.namespace.QName QName_0_70 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "RetentionCancelTime");
    private final static javax.xml.namespace.QName QName_0_60 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "CancelBranchId");
    private final static javax.xml.namespace.QName QName_0_64 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "CreateAuthUserId");
    private final static javax.xml.namespace.QName QName_0_21 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength8");
    private final static javax.xml.namespace.QName QName_0_87 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength7");
    private final static javax.xml.namespace.QName QName_0_66 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "EntryOrigin");
    private final static javax.xml.namespace.QName QName_0_83 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength4");
    private final static javax.xml.namespace.QName QName_0_31 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength3");
    private final static javax.xml.namespace.QName QName_0_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength2");
    private final static javax.xml.namespace.QName QName_0_30 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "stringLength1");
    private final static javax.xml.namespace.QName QName_0_67 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "IndexSeq");
    private final static javax.xml.namespace.QName QName_0_80 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
                  "TrnBranchName");
}
