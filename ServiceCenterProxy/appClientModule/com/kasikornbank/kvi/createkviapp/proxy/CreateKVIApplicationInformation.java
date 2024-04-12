/**
 * CreateKVIApplicationInformation.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.createkviapp.proxy;

public class CreateKVIApplicationInformation implements com.ibm.ws.webservices.multiprotocol.ServiceInformation {

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
        inner0.put("serviceRequest", list0);

        com.ibm.ws.webservices.engine.description.OperationDesc serviceRequest0Op = _serviceRequest0Op();
        list0.add(serviceRequest0Op);

        operationDescriptions.put("BasicHttpBinding_ICreateKVIApplication",inner0);
        operationDescriptions = java.util.Collections.unmodifiableMap(operationDescriptions);
    }

    private static com.ibm.ws.webservices.engine.description.OperationDesc _serviceRequest0Op() {
        com.ibm.ws.webservices.engine.description.OperationDesc serviceRequest0Op = null;
        com.ibm.ws.webservices.engine.description.ParameterDesc[]  _params0 = new com.ibm.ws.webservices.engine.description.ParameterDesc[] {
         new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "objCreateKVIApplicationRequest"), com.ibm.ws.webservices.engine.description.ParameterDesc.IN, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "CreateKVIApplicationRequest"), com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationRequest.class, false, false, false, true, true, false), 
          };
        _params0[0].setOption("partName","CreateKVIApplicationRequest");
        _params0[0].setOption("partQNameString","{http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication}CreateKVIApplicationRequest");
        _params0[0].setOption("inputPosition","0");
        com.ibm.ws.webservices.engine.description.ParameterDesc  _returnDesc0 = new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "ServiceRequestResult"), com.ibm.ws.webservices.engine.description.ParameterDesc.OUT, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "CreateKVIApplicationResponse"), com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationResponse.class, true, false, false, true, true, false); 
        _returnDesc0.setOption("partName","CreateKVIApplicationResponse");
        _returnDesc0.setOption("partQNameString","{http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication}CreateKVIApplicationResponse");
        _returnDesc0.setOption("outputPosition","0");
        com.ibm.ws.webservices.engine.description.FaultDesc[]  _faults0 = new com.ibm.ws.webservices.engine.description.FaultDesc[] {
          };
        serviceRequest0Op = new com.ibm.ws.webservices.engine.description.OperationDesc("serviceRequest", com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "ServiceRequest"), _params0, _returnDesc0, _faults0, null);
        serviceRequest0Op.setOption("portTypeQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "ICreateKVIApplication"));
        serviceRequest0Op.setOption("outputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "ICreateKVIApplication_ServiceRequest_OutputMessage"));
        serviceRequest0Op.setOption("outputWSAAction","http://tempuri.org/ICreateKVIApplication/ServiceRequestResponse");
        serviceRequest0Op.setOption("ResponseLocalPart","ServiceRequestResponse");
        serviceRequest0Op.setOption("targetNamespace","http://tempuri.org/");
        serviceRequest0Op.setOption("ResponseNamespace","http://tempuri.org/");
        serviceRequest0Op.setOption("buildNum","gm1318.02");
        serviceRequest0Op.setOption("inputWSAAction","http://tempuri.org/ICreateKVIApplication/ServiceRequest");
        serviceRequest0Op.setOption("ServiceQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "CreateKVIApplication"));
        serviceRequest0Op.setOption("inputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "ICreateKVIApplication_ServiceRequest_InputMessage"));
        serviceRequest0Op.setStyle(com.ibm.ws.webservices.engine.enumtype.Style.WRAPPED);
        return serviceRequest0Op;

    }


    private static void initTypeMappings() {
        typeMappings = new java.util.HashMap();
        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "CreateKVIApplicationRequest"),
                         com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationRequest.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "CreateKVIApplicationResponse"),
                         com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationResponse.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader", "KBankRequestHeader"),
                         com.kasikornbank.kvi.createkviapp.proxy.KBankRequestHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "RequestData"),
                         com.kasikornbank.kvi.createkviapp.proxy.RequestData.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader", "KBankResponseHeader"),
                         com.kasikornbank.kvi.createkviapp.proxy.KBankResponseHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "ResponseData"),
                         com.kasikornbank.kvi.createkviapp.proxy.ResponseData.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader", "ArrayOfKBankResponseError"),
                         com.kasikornbank.kvi.createkviapp.proxy.KBankResponseError[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader", "KBankResponseError"),
                         com.kasikornbank.kvi.createkviapp.proxy.KBankResponseError.class);

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
