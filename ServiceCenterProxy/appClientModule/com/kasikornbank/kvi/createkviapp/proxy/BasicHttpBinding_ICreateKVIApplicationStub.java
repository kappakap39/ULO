/**
 * BasicHttpBinding_ICreateKVIApplicationStub.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.createkviapp.proxy;

public class BasicHttpBinding_ICreateKVIApplicationStub extends com.ibm.ws.webservices.engine.client.Stub implements com.kasikornbank.kvi.createkviapp.proxy.ICreateKVIApplication {
    public BasicHttpBinding_ICreateKVIApplicationStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws com.ibm.ws.webservices.engine.WebServicesFault {
        if (service == null) {
            super.service = new com.ibm.ws.webservices.engine.client.Service();
        }
        else {
            super.service = service;
        }
        super.engine = ((com.ibm.ws.webservices.engine.client.Service) super.service).getEngine();
        super.messageContexts = new com.ibm.ws.webservices.engine.MessageContext[1];
        java.lang.String theOption = (java.lang.String)super._getProperty("lastStubMapping");
        if (theOption == null || !theOption.equals("com.kasikornbank.kvi.createkviapp.proxy.BasicHttpBinding_ICreateKVIApplication")) {
                initTypeMapping();
                super._setProperty("lastStubMapping","com.kasikornbank.kvi.createkviapp.proxy.BasicHttpBinding_ICreateKVIApplication");
        }
        super.cachedEndpoint = endpointURL;
        super.connection = ((com.ibm.ws.webservices.engine.client.Service) super.service).getConnection(endpointURL);
    }

    private void initTypeMapping() {
        javax.xml.rpc.encoding.TypeMapping tm = super.getTypeMapping(com.ibm.ws.webservices.engine.Constants.URI_LITERAL_ENC);
        java.lang.Class javaType = null;
        javax.xml.namespace.QName xmlType = null;
        javax.xml.namespace.QName compQName = null;
        javax.xml.namespace.QName compTypeQName = null;
        com.ibm.ws.webservices.engine.encoding.SerializerFactory sf = null;
        com.ibm.ws.webservices.engine.encoding.DeserializerFactory df = null;
        javaType = com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationRequest.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "CreateKVIApplicationRequest");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationResponse.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "CreateKVIApplicationResponse");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.kvi.createkviapp.proxy.KBankRequestHeader.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader", "KBankRequestHeader");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.kvi.createkviapp.proxy.RequestData.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "RequestData");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.kvi.createkviapp.proxy.KBankResponseHeader.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader", "KBankResponseHeader");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.kvi.createkviapp.proxy.ResponseData.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.CreateKVIApplication", "ResponseData");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.kvi.createkviapp.proxy.KBankResponseError[].class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader", "ArrayOfKBankResponseError");
        compQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader", "KBankResponseError");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader", "KBankResponseError");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.ArraySerializerFactory.class, javaType, xmlType, compQName, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.ArrayDeserializerFactory.class, javaType, xmlType, compQName, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.kvi.createkviapp.proxy.KBankResponseError.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://schemas.datacontract.org/2004/07/KVIDataContract.KBankHeader", "KBankResponseError");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

    }

    private static com.ibm.ws.webservices.engine.description.OperationDesc _serviceRequestOperation0 = null;
    private static com.ibm.ws.webservices.engine.description.OperationDesc _getserviceRequestOperation0() {
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
        _serviceRequestOperation0 = new com.ibm.ws.webservices.engine.description.OperationDesc("serviceRequest", com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "ServiceRequest"), _params0, _returnDesc0, _faults0, "http://tempuri.org/ICreateKVIApplication/ServiceRequest");
        _serviceRequestOperation0.setOption("portTypeQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "ICreateKVIApplication"));
        _serviceRequestOperation0.setOption("outputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "ICreateKVIApplication_ServiceRequest_OutputMessage"));
        _serviceRequestOperation0.setOption("outputWSAAction","http://tempuri.org/ICreateKVIApplication/ServiceRequestResponse");
        _serviceRequestOperation0.setOption("ResponseLocalPart","ServiceRequestResponse");
        _serviceRequestOperation0.setOption("targetNamespace","http://tempuri.org/");
        _serviceRequestOperation0.setOption("ResponseNamespace","http://tempuri.org/");
        _serviceRequestOperation0.setOption("buildNum","gm1318.02");
        _serviceRequestOperation0.setOption("inputWSAAction","http://tempuri.org/ICreateKVIApplication/ServiceRequest");
        _serviceRequestOperation0.setOption("ServiceQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "CreateKVIApplication"));
        _serviceRequestOperation0.setOption("inputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "ICreateKVIApplication_ServiceRequest_InputMessage"));
        _serviceRequestOperation0.setUse(com.ibm.ws.webservices.engine.enumtype.Use.LITERAL);
        _serviceRequestOperation0.setStyle(com.ibm.ws.webservices.engine.enumtype.Style.WRAPPED);
        return _serviceRequestOperation0;

    }

    private int _serviceRequestIndex0 = 0;
    private synchronized com.ibm.ws.webservices.engine.client.Stub.Invoke _getserviceRequestInvoke0(Object[] parameters) throws com.ibm.ws.webservices.engine.WebServicesFault  {
        com.ibm.ws.webservices.engine.MessageContext mc = super.messageContexts[_serviceRequestIndex0];
        if (mc == null) {
            mc = new com.ibm.ws.webservices.engine.MessageContext(super.engine);
            mc.setOperation(BasicHttpBinding_ICreateKVIApplicationStub._serviceRequestOperation0);
            mc.setUseSOAPAction(true);
            mc.setSOAPActionURI("http://tempuri.org/ICreateKVIApplication/ServiceRequest");
            mc.setEncodingStyle(com.ibm.ws.webservices.engine.Constants.URI_LITERAL_ENC);
            mc.setProperty(com.ibm.wsspi.webservices.Constants.SEND_TYPE_ATTR_PROPERTY, Boolean.FALSE);
            mc.setProperty(com.ibm.wsspi.webservices.Constants.ENGINE_DO_MULTI_REFS_PROPERTY, Boolean.FALSE);
            super.primeMessageContext(mc);
            super.messageContexts[_serviceRequestIndex0] = mc;
        }
        try {
            mc = (com.ibm.ws.webservices.engine.MessageContext) mc.clone();
        }
        catch (CloneNotSupportedException cnse) {
            throw com.ibm.ws.webservices.engine.WebServicesFault.makeFault(cnse);
        }
        return new com.ibm.ws.webservices.engine.client.Stub.Invoke(connection, mc, parameters);
    }

    public com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationResponse serviceRequest(com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationRequest objCreateKVIApplicationRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new com.ibm.ws.webservices.engine.NoEndPointException();
        }
        java.util.Vector _resp = null;
        try {
            _resp = _getserviceRequestInvoke0(new java.lang.Object[] {objCreateKVIApplicationRequest}).invoke();

        } catch (com.ibm.ws.webservices.engine.WebServicesFault wsf) {
            Exception e = wsf.getUserException();
            throw wsf;
        } 
        try {
            return (com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationResponse) ((com.ibm.ws.webservices.engine.xmlsoap.ext.ParamValue) _resp.get(0)).getValue();
        } catch (java.lang.Exception _exception) {
            return (com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationResponse) super.convert(((com.ibm.ws.webservices.engine.xmlsoap.ext.ParamValue) _resp.get(0)).getValue(), com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationResponse.class);
        }
    }

    private static void _staticInit() {
        _serviceRequestOperation0 = _getserviceRequestOperation0();
    }

    static {
       _staticInit();
    }
}
