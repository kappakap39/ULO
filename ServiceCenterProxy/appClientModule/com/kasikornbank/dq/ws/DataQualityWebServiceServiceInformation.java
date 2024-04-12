/**
 * DataQualityWebServiceServiceInformation.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws;

public class DataQualityWebServiceServiceInformation implements com.ibm.ws.webservices.multiprotocol.ServiceInformation {

    private static java.util.Map operationDescriptions;
    private static java.util.Map typeMappings;

    static {
         initOperationDescriptions();
         initTypeMappings();
    }

    private static void initOperationDescriptions() { 
        operationDescriptions = new java.util.HashMap();

        java.util.Map inner0 = new java.util.HashMap();

        java.util.List list0 = new java.util.ArrayList();
        inner0.put("validate", list0);

        com.ibm.ws.webservices.engine.description.OperationDesc validate0Op = _validate0Op();
        list0.add(validate0Op);

        operationDescriptions.put("DataQualityWebServicePort",inner0);
        operationDescriptions = java.util.Collections.unmodifiableMap(operationDescriptions);
    }

    private static com.ibm.ws.webservices.engine.description.OperationDesc _validate0Op() {
        com.ibm.ws.webservices.engine.description.OperationDesc validate0Op = null;
        com.ibm.ws.webservices.engine.description.ParameterDesc[]  _params0 = new com.ibm.ws.webservices.engine.description.ParameterDesc[] {
         new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "DataQualityRequest"), com.ibm.ws.webservices.engine.description.ParameterDesc.IN, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "dataQualityRequest"), com.kasikornbank.dq.ws.v1.DataQualityRequest.class, false, false, false, false, true, false), 
          };
        _params0[0].setOption("partName","dataQualityRequest");
        _params0[0].setOption("partQNameString","{http://ws.dq.kasikornbank.com/}dataQualityRequest");
        com.ibm.ws.webservices.engine.description.ParameterDesc  _returnDesc0 = new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "DataQualityResponses"), com.ibm.ws.webservices.engine.description.ParameterDesc.OUT, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "dataQualityResponse"), com.kasikornbank.dq.ws.v1.DataQualityResponse.class, true, false, false, false, true, false); 
        _returnDesc0.setOption("partName","dataQualityResponse");
        _returnDesc0.setOption("partQNameString","{http://ws.dq.kasikornbank.com/}dataQualityResponse");
        com.ibm.ws.webservices.engine.description.FaultDesc[]  _faults0 = new com.ibm.ws.webservices.engine.description.FaultDesc[] {
          };
        validate0Op = new com.ibm.ws.webservices.engine.description.OperationDesc("validate", com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "validate"), _params0, _returnDesc0, _faults0, null);
        validate0Op.setOption("portTypeQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "DataQualityWebService"));
        validate0Op.setOption("outputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "validateResponse"));
        validate0Op.setOption("targetNamespace","http://ws.dq.kasikornbank.com");
        validate0Op.setOption("ResponseNamespace","http://ws.dq.kasikornbank.com/");
        validate0Op.setOption("buildNum","gm1318.02");
        validate0Op.setOption("ServiceQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com", "DataQualityWebServiceService"));
        validate0Op.setOption("inputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "validate"));
        validate0Op.setStyle(com.ibm.ws.webservices.engine.enumtype.Style.RPC);
        return validate0Op;

    }


    private static void initTypeMappings() {
        typeMappings = new java.util.HashMap();
        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "dataQualityRequest"),
                         com.kasikornbank.dq.ws.v1.DataQualityRequest.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "dataQualityResponse"),
                         com.kasikornbank.dq.ws.v1.DataQualityResponse.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "baseRequest"),
                         com.kasikornbank.dq.ws.v1.BaseRequest.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "customerProfile"),
                         com.kasikornbank.dq.ws.v1.CustomerProfile.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "header"),
                         com.kasikornbank.dq.ws.v1.Header.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "address"),
                         com.kasikornbank.dq.ws.v1.Address.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactEmail"),
                         com.kasikornbank.dq.ws.v1.ContactEmail.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactNumber"),
                         com.kasikornbank.dq.ws.v1.ContactNumber.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "name"),
                         com.kasikornbank.dq.ws.v1.Name.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "locationType"),
                         com.kasikornbank.dq.ws.v1.LocationType.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactType"),
                         com.kasikornbank.dq.ws.v1.ContactType.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "baseResponse"),
                         com.kasikornbank.dq.ws.v1.BaseResponse.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactEmailList"),
                         com.kasikornbank.dq.ws.v1.ContactEmailList.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactNumberList"),
                         com.kasikornbank.dq.ws.v1.ContactNumberList.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "dqField"),
                         com.kasikornbank.dq.ws.v1.DqField.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "othetAddressList"),
                         com.kasikornbank.dq.ws.v1.OthetAddressList.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "responseStatus"),
                         com.kasikornbank.dq.ws.v1.ResponseStatus.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "dqError"),
                         com.kasikornbank.dq.ws.v1.DqError.class);

        typeMappings = java.util.Collections.unmodifiableMap(typeMappings);
    }

    public java.util.Map getTypeMappings() {
        return typeMappings;
    }

    public Class getJavaType(javax.xml.namespace.QName xmlName) {
        return (Class) typeMappings.get(xmlName);
    }

    public java.util.Map getOperationDescriptions(String portName) {
        return (java.util.Map) operationDescriptions.get(portName);
    }

    public java.util.List getOperationDescriptions(String portName, String operationName) {
        java.util.Map map = (java.util.Map) operationDescriptions.get(portName);
        if (map != null) {
            return (java.util.List) map.get(operationName);
        }
        return null;
    }

}
