/**
 * CustomerProfile_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class CustomerProfile_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(CustomerProfile.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("assetWithoutLand");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "assetWithoutLand"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("businessCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "businessCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("cisId");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "cisId"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("consentFlag");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "consentFlag"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("contactAddress");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "contactAddress"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "address"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("contactEmails");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "contactEmails"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactEmail"));
        field.setMinOccursIs0(true);
        field.setMaxOccurs(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("contactNumbers");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "contactNumbers"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactNumber"));
        field.setMinOccursIs0(true);
        field.setMaxOccurs(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("customerType");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "customerType"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("customerTypeCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "customerTypeCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("dateOfBirthOrRegistrationDate");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "dateOfBirthOrRegistrationDate"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("documentType");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "documentType"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("educationLevelOfDependant");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "educationLevelOfDependant"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("expireDate");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "expireDate"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("gender");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "gender"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("idCardNoOrRegistrationNo");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "idCardNoOrRegistrationNo"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("issueBy");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "issueBy"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("issueDate");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "issueDate"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("marriageStatus");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "marriageStatus"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("nameEng");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "nameEng"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "name"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("nameThai");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "nameThai"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "name"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("nationalityCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "nationalityCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("noOfEmployee");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "noOfEmployee"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("occupationCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "occupationCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("otherAddresses");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "otherAddresses"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "address"));
        field.setMinOccursIs0(true);
        field.setMaxOccurs(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("position");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "position"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("professionCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "professionCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("raceCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "raceCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("registrationAddress");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "registrationAddress"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "address"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("salary");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "salary"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("sourceOfConsent");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "sourceOfConsent"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("workAddress");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "workAddress"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "address"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
    };

    /**
     * Return type metadata object
     */
    public static com.ibm.ws.webservices.engine.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static com.ibm.ws.webservices.engine.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class javaType,  
           javax.xml.namespace.QName xmlType) {
        return 
          new CustomerProfile_Ser(
            javaType, xmlType, typeDesc);
    };

    /**
     * Get Custom Deserializer
     */
    public static com.ibm.ws.webservices.engine.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class javaType,  
           javax.xml.namespace.QName xmlType) {
        return 
          new CustomerProfile_Deser(
            javaType, xmlType, typeDesc);
    };

}
