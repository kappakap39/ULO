/**
 * CIS0222I01Response_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0222i01;

public class CIS0222I01Response_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(CIS0222I01Response.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("EAIHeader");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "EAIHeader"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>doServiceResponse>CIS0222I01Response>EAIHeader"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("CISCustomer");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "CISCustomer"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>doServiceResponse>CIS0222I01Response>CISCustomer"));
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
          new CIS0222I01Response_Ser(
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
          new CIS0222I01Response_Deser(
            javaType, xmlType, typeDesc);
    };

}
