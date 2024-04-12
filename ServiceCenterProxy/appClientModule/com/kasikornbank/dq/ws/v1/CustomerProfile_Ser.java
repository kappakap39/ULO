/**
 * CustomerProfile_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class CustomerProfile_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public CustomerProfile_Ser(
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
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        CustomerProfile bean = (CustomerProfile) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_21_574;
          propValue = bean.getAssetWithoutLand();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_575;
          propValue = bean.getBusinessCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_576;
          propValue = bean.getCisId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_577;
          propValue = bean.getConsentFlag();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_578;
          propValue = bean.getContactAddress();
          serializeChild(propQName, null, 
              propValue, 
              QName_22_601,
              false,null,context);
          propQName = QName_21_565;
          {
            propValue = bean.getContactEmails();
            if (propValue != null) {
              for (int i=0; i<java.lang.reflect.Array.getLength(propValue); i++) {
                serializeChild(propQName, null, 
                    java.lang.reflect.Array.get(propValue, i), 
                    QName_22_602,
                    true,null,context);
              }
            }
          }
          propQName = QName_21_566;
          {
            propValue = bean.getContactNumbers();
            if (propValue != null) {
              for (int i=0; i<java.lang.reflect.Array.getLength(propValue); i++) {
                serializeChild(propQName, null, 
                    java.lang.reflect.Array.get(propValue, i), 
                    QName_22_603,
                    true,null,context);
              }
            }
          }
          propQName = QName_21_579;
          propValue = bean.getCustomerType();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_580;
          propValue = bean.getCustomerTypeCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_581;
          propValue = bean.getDateOfBirthOrRegistrationDate();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_582;
          propValue = bean.getDocumentType();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_583;
          propValue = bean.getEducationLevelOfDependant();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_584;
          propValue = bean.getExpireDate();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_585;
          propValue = bean.getGender();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_586;
          propValue = bean.getIdCardNoOrRegistrationNo();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_587;
          propValue = bean.getIssueBy();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_588;
          propValue = bean.getIssueDate();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_589;
          propValue = bean.getMarriageStatus();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_590;
          propValue = bean.getNameEng();
          serializeChild(propQName, null, 
              propValue, 
              QName_22_207,
              false,null,context);
          propQName = QName_21_591;
          propValue = bean.getNameThai();
          serializeChild(propQName, null, 
              propValue, 
              QName_22_207,
              false,null,context);
          propQName = QName_21_592;
          propValue = bean.getNationalityCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_593;
          propValue = bean.getNoOfEmployee();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_594;
          propValue = bean.getOccupationCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_568;
          {
            propValue = bean.getOtherAddresses();
            if (propValue != null) {
              for (int i=0; i<java.lang.reflect.Array.getLength(propValue); i++) {
                serializeChild(propQName, null, 
                    java.lang.reflect.Array.get(propValue, i), 
                    QName_22_601,
                    true,null,context);
              }
            }
          }
          propQName = QName_21_595;
          propValue = bean.getPosition();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_596;
          propValue = bean.getProfessionCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_300;
          propValue = bean.getRaceCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_597;
          propValue = bean.getRegistrationAddress();
          serializeChild(propQName, null, 
              propValue, 
              QName_22_601,
              false,null,context);
          propQName = QName_21_598;
          propValue = bean.getSalary();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_599;
          propValue = bean.getSourceOfConsent();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_109,
              false,null,context);
          }
          propQName = QName_21_600;
          propValue = bean.getWorkAddress();
          serializeChild(propQName, null, 
              propValue, 
              QName_22_601,
              false,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_21_576 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "cisId");
    private final static javax.xml.namespace.QName QName_1_109 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_21_574 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "assetWithoutLand");
    private final static javax.xml.namespace.QName QName_21_590 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "nameEng");
    private final static javax.xml.namespace.QName QName_21_598 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "salary");
    private final static javax.xml.namespace.QName QName_21_300 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "raceCode");
    private final static javax.xml.namespace.QName QName_21_582 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "documentType");
    private final static javax.xml.namespace.QName QName_21_597 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "registrationAddress");
    private final static javax.xml.namespace.QName QName_21_595 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "position");
    private final static javax.xml.namespace.QName QName_21_577 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "consentFlag");
    private final static javax.xml.namespace.QName QName_22_601 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://ws.dq.kasikornbank.com/",
                  "address");
    private final static javax.xml.namespace.QName QName_21_592 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "nationalityCode");
    private final static javax.xml.namespace.QName QName_21_581 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "dateOfBirthOrRegistrationDate");
    private final static javax.xml.namespace.QName QName_21_593 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "noOfEmployee");
    private final static javax.xml.namespace.QName QName_21_600 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "workAddress");
    private final static javax.xml.namespace.QName QName_21_580 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "customerTypeCode");
    private final static javax.xml.namespace.QName QName_21_568 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "otherAddresses");
    private final static javax.xml.namespace.QName QName_21_585 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "gender");
    private final static javax.xml.namespace.QName QName_22_602 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://ws.dq.kasikornbank.com/",
                  "contactEmail");
    private final static javax.xml.namespace.QName QName_21_583 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "educationLevelOfDependant");
    private final static javax.xml.namespace.QName QName_21_587 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "issueBy");
    private final static javax.xml.namespace.QName QName_21_588 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "issueDate");
    private final static javax.xml.namespace.QName QName_21_599 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "sourceOfConsent");
    private final static javax.xml.namespace.QName QName_22_603 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://ws.dq.kasikornbank.com/",
                  "contactNumber");
    private final static javax.xml.namespace.QName QName_21_575 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "businessCode");
    private final static javax.xml.namespace.QName QName_21_596 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "professionCode");
    private final static javax.xml.namespace.QName QName_21_586 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "idCardNoOrRegistrationNo");
    private final static javax.xml.namespace.QName QName_21_594 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "occupationCode");
    private final static javax.xml.namespace.QName QName_22_207 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://ws.dq.kasikornbank.com/",
                  "name");
    private final static javax.xml.namespace.QName QName_21_579 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "customerType");
    private final static javax.xml.namespace.QName QName_21_589 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "marriageStatus");
    private final static javax.xml.namespace.QName QName_21_584 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "expireDate");
    private final static javax.xml.namespace.QName QName_21_566 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "contactNumbers");
    private final static javax.xml.namespace.QName QName_21_565 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "contactEmails");
    private final static javax.xml.namespace.QName QName_21_578 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "contactAddress");
    private final static javax.xml.namespace.QName QName_21_591 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "nameThai");
}
