/**
 * ____doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj_addrRelObj_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1046a01;

public class ____doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj_addrRelObj_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(____doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj_addrRelObj.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("addrRelsVect");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "addrRelsVect"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>>>>doServiceResponse>CIS1046A01Response>CISCustomer>contactAddrObj>addrRelObj>addrRelsVect"));
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
          new ____doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj_addrRelObj_Ser(
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
          new ____doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj_addrRelObj_Deser(
            javaType, xmlType, typeDesc);
    };

}
