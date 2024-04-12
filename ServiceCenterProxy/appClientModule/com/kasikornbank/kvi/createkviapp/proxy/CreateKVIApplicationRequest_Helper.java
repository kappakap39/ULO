/**
 * CreateKVIApplicationRequest_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.createkviapp.proxy;

public class CreateKVIApplicationRequest_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(CreateKVIApplicationRequest.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("KBankRequestHeader");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "KBankRequestHeader"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader", "KBankRequestHeader"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("requestData");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "RequestData"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "RequestData"));
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
          new CreateKVIApplicationRequest_Ser(
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
          new CreateKVIApplicationRequest_Deser(
            javaType, xmlType, typeDesc);
    };

}
