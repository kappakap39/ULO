/**
 * RequestData_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.createkviapp.proxy;

public class RequestData_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(RequestData.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGAppNo");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGAppNo"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FCDept");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FCDept"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FCInputID");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FCInputID"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGRequestor");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGRequestor"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGRequestorL");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGRequestorL"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGType");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGType"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGID");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGID"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGCisNo");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGCisNo"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGRequestor1");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGRequestor1"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGRequestorL1");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGRequestorL1"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGType1");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGType1"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGID1");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGID1"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGCisNo1");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGCisNo1"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGRequestor2");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGRequestor2"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGRequestorL2");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGRequestorL2"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGType2");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGType2"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGID2");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGID2"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FGCisNo2");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FGCisNo2"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FCBusiness");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FCBusiness"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("FCBusinessDesc");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "FCBusinessDesc"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMinOccursIs0(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("percentShareHolder");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "PercentShareHolder"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
          new RequestData_Ser(
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
          new RequestData_Deser(
            javaType, xmlType, typeDesc);
    };

}
