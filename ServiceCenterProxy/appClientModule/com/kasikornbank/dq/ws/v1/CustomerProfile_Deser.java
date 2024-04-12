/**
 * CustomerProfile_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class CustomerProfile_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public CustomerProfile_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new CustomerProfile();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_21_574) {
          ((CustomerProfile)value).setAssetWithoutLand(strValue);
          return true;}
        else if (qName==QName_21_575) {
          ((CustomerProfile)value).setBusinessCode(strValue);
          return true;}
        else if (qName==QName_21_576) {
          ((CustomerProfile)value).setCisId(strValue);
          return true;}
        else if (qName==QName_21_577) {
          ((CustomerProfile)value).setConsentFlag(strValue);
          return true;}
        else if (qName==QName_21_579) {
          ((CustomerProfile)value).setCustomerType(strValue);
          return true;}
        else if (qName==QName_21_580) {
          ((CustomerProfile)value).setCustomerTypeCode(strValue);
          return true;}
        else if (qName==QName_21_581) {
          ((CustomerProfile)value).setDateOfBirthOrRegistrationDate(strValue);
          return true;}
        else if (qName==QName_21_582) {
          ((CustomerProfile)value).setDocumentType(strValue);
          return true;}
        else if (qName==QName_21_583) {
          ((CustomerProfile)value).setEducationLevelOfDependant(strValue);
          return true;}
        else if (qName==QName_21_584) {
          ((CustomerProfile)value).setExpireDate(strValue);
          return true;}
        else if (qName==QName_21_585) {
          ((CustomerProfile)value).setGender(strValue);
          return true;}
        else if (qName==QName_21_586) {
          ((CustomerProfile)value).setIdCardNoOrRegistrationNo(strValue);
          return true;}
        else if (qName==QName_21_587) {
          ((CustomerProfile)value).setIssueBy(strValue);
          return true;}
        else if (qName==QName_21_588) {
          ((CustomerProfile)value).setIssueDate(strValue);
          return true;}
        else if (qName==QName_21_589) {
          ((CustomerProfile)value).setMarriageStatus(strValue);
          return true;}
        else if (qName==QName_21_592) {
          ((CustomerProfile)value).setNationalityCode(strValue);
          return true;}
        else if (qName==QName_21_593) {
          ((CustomerProfile)value).setNoOfEmployee(strValue);
          return true;}
        else if (qName==QName_21_594) {
          ((CustomerProfile)value).setOccupationCode(strValue);
          return true;}
        else if (qName==QName_21_595) {
          ((CustomerProfile)value).setPosition(strValue);
          return true;}
        else if (qName==QName_21_596) {
          ((CustomerProfile)value).setProfessionCode(strValue);
          return true;}
        else if (qName==QName_21_300) {
          ((CustomerProfile)value).setRaceCode(strValue);
          return true;}
        else if (qName==QName_21_598) {
          ((CustomerProfile)value).setSalary(strValue);
          return true;}
        else if (qName==QName_21_599) {
          ((CustomerProfile)value).setSourceOfConsent(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_21_578) {
          ((CustomerProfile)value).setContactAddress((com.kasikornbank.dq.ws.v1.Address)objValue);
          return true;}
        else if (qName==QName_21_590) {
          ((CustomerProfile)value).setNameEng((com.kasikornbank.dq.ws.v1.Name)objValue);
          return true;}
        else if (qName==QName_21_591) {
          ((CustomerProfile)value).setNameThai((com.kasikornbank.dq.ws.v1.Name)objValue);
          return true;}
        else if (qName==QName_21_597) {
          ((CustomerProfile)value).setRegistrationAddress((com.kasikornbank.dq.ws.v1.Address)objValue);
          return true;}
        else if (qName==QName_21_600) {
          ((CustomerProfile)value).setWorkAddress((com.kasikornbank.dq.ws.v1.Address)objValue);
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        if (qName==QName_21_565) {
          com.kasikornbank.dq.ws.v1.ContactEmail[] array = new com.kasikornbank.dq.ws.v1.ContactEmail[listValue.size()];
          listValue.toArray(array);
          ((CustomerProfile)value).setContactEmails(array);
          return true;}
        else if (qName==QName_21_566) {
          com.kasikornbank.dq.ws.v1.ContactNumber[] array = new com.kasikornbank.dq.ws.v1.ContactNumber[listValue.size()];
          listValue.toArray(array);
          ((CustomerProfile)value).setContactNumbers(array);
          return true;}
        else if (qName==QName_21_568) {
          com.kasikornbank.dq.ws.v1.Address[] array = new com.kasikornbank.dq.ws.v1.Address[listValue.size()];
          listValue.toArray(array);
          ((CustomerProfile)value).setOtherAddresses(array);
          return true;}
        return false;
    }
    private final static javax.xml.namespace.QName QName_21_575 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "businessCode");
    private final static javax.xml.namespace.QName QName_21_593 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "noOfEmployee");
    private final static javax.xml.namespace.QName QName_21_597 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "registrationAddress");
    private final static javax.xml.namespace.QName QName_21_594 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "occupationCode");
    private final static javax.xml.namespace.QName QName_21_591 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "nameThai");
    private final static javax.xml.namespace.QName QName_21_582 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "documentType");
    private final static javax.xml.namespace.QName QName_21_580 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "customerTypeCode");
    private final static javax.xml.namespace.QName QName_21_579 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "customerType");
    private final static javax.xml.namespace.QName QName_21_568 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "otherAddresses");
    private final static javax.xml.namespace.QName QName_21_300 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "raceCode");
    private final static javax.xml.namespace.QName QName_21_589 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "marriageStatus");
    private final static javax.xml.namespace.QName QName_21_600 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "workAddress");
    private final static javax.xml.namespace.QName QName_21_585 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "gender");
    private final static javax.xml.namespace.QName QName_21_578 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "contactAddress");
    private final static javax.xml.namespace.QName QName_21_587 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "issueBy");
    private final static javax.xml.namespace.QName QName_21_598 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "salary");
    private final static javax.xml.namespace.QName QName_21_581 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "dateOfBirthOrRegistrationDate");
    private final static javax.xml.namespace.QName QName_21_565 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "contactEmails");
    private final static javax.xml.namespace.QName QName_21_583 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "educationLevelOfDependant");
    private final static javax.xml.namespace.QName QName_21_576 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "cisId");
    private final static javax.xml.namespace.QName QName_21_584 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "expireDate");
    private final static javax.xml.namespace.QName QName_21_596 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "professionCode");
    private final static javax.xml.namespace.QName QName_21_586 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "idCardNoOrRegistrationNo");
    private final static javax.xml.namespace.QName QName_21_577 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "consentFlag");
    private final static javax.xml.namespace.QName QName_21_566 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "contactNumbers");
    private final static javax.xml.namespace.QName QName_21_592 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "nationalityCode");
    private final static javax.xml.namespace.QName QName_21_590 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "nameEng");
    private final static javax.xml.namespace.QName QName_21_595 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "position");
    private final static javax.xml.namespace.QName QName_21_574 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "assetWithoutLand");
    private final static javax.xml.namespace.QName QName_21_599 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "sourceOfConsent");
    private final static javax.xml.namespace.QName QName_21_588 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "issueDate");
}
