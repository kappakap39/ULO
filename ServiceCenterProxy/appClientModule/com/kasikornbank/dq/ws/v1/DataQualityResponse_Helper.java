/**
 * DataQualityResponse_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws.v1;

public class DataQualityResponse_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(DataQualityResponse.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("contactEmails");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "contactEmails"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactEmailList"));
        field.setMinOccursIs0(true);
        field.setMaxOccurs(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("contactNumbers");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "contactNumbers"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactNumberList"));
        field.setMinOccursIs0(true);
        field.setMaxOccurs(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("fields");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "fields"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "dqField"));
        field.setMinOccursIs0(true);
        field.setMaxOccurs(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("otherAddresses");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "otherAddresses"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "othetAddressList"));
        field.setMinOccursIs0(true);
        field.setMaxOccurs(true);
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
          new DataQualityResponse_Ser(
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
          new DataQualityResponse_Deser(
            javaType, xmlType, typeDesc);
    };

}
