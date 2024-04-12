/**
 * ___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0222i01;

public class ___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("totZipCodeCnt");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "totZipCodeCnt"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "intLength3"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("zipCodesVect");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "zipCodesVect"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>>>doServiceResponse>CIS0222I01Response>CISCustomer>zipCodesObj>zipCodesVect"));
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
          new ___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_Ser(
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
          new ___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_Deser(
            javaType, xmlType, typeDesc);
    };

}
