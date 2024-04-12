/**
 * DataQualityWebServicePortBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws;

public class DataQualityWebServicePortBindingStub extends com.ibm.ws.webservices.engine.client.Stub implements com.kasikornbank.dq.ws.v1.DataQualityWebService {
    public DataQualityWebServicePortBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws com.ibm.ws.webservices.engine.WebServicesFault {
        if (service == null) {
            super.service = new com.ibm.ws.webservices.engine.client.Service();
        }
        else {
            super.service = service;
        }
        super.engine = ((com.ibm.ws.webservices.engine.client.Service) super.service).getEngine();
        super.messageContexts = new com.ibm.ws.webservices.engine.MessageContext[1];
        java.lang.String theOption = (java.lang.String)super._getProperty("lastStubMapping");
        if (theOption == null || !theOption.equals("com.kasikornbank.dq.ws.DataQualityWebServicePortBinding")) {
                initTypeMapping();
                super._setProperty("lastStubMapping","com.kasikornbank.dq.ws.DataQualityWebServicePortBinding");
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
        javaType = com.kasikornbank.dq.ws.v1.DataQualityRequest.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "dataQualityRequest");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.DataQualityResponse.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "dataQualityResponse");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.BaseRequest.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "baseRequest");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.CustomerProfile.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "customerProfile");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.Header.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "header");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.Address.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "address");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.ContactEmail.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactEmail");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.ContactNumber.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactNumber");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.Name.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "name");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.LocationType.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "locationType");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.EnumSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.EnumDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.ContactType.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactType");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.EnumSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.EnumDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.BaseResponse.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "baseResponse");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.ContactEmailList.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactEmailList");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.ContactNumberList.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "contactNumberList");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.DqField.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "dqField");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.OthetAddressList.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "othetAddressList");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.ResponseStatus.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "responseStatus");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.dq.ws.v1.DqError.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "dqError");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

    }

    private static com.ibm.ws.webservices.engine.description.OperationDesc _validateOperation0 = null;
    private static com.ibm.ws.webservices.engine.description.OperationDesc _getvalidateOperation0() {
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
        _validateOperation0 = new com.ibm.ws.webservices.engine.description.OperationDesc("validate", com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "validate"), _params0, _returnDesc0, _faults0, "");
        _validateOperation0.setOption("portTypeQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "DataQualityWebService"));
        _validateOperation0.setOption("outputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "validateResponse"));
        _validateOperation0.setOption("targetNamespace","http://ws.dq.kasikornbank.com");
        _validateOperation0.setOption("ResponseNamespace","http://ws.dq.kasikornbank.com/");
        _validateOperation0.setOption("buildNum","gm1318.02");
        _validateOperation0.setOption("ServiceQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com", "DataQualityWebServiceService"));
        _validateOperation0.setOption("inputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com/", "validate"));
        _validateOperation0.setUse(com.ibm.ws.webservices.engine.enumtype.Use.LITERAL);
        _validateOperation0.setStyle(com.ibm.ws.webservices.engine.enumtype.Style.RPC);
        return _validateOperation0;

    }

    private int _validateIndex0 = 0;
    private synchronized com.ibm.ws.webservices.engine.client.Stub.Invoke _getvalidateInvoke0(Object[] parameters) throws com.ibm.ws.webservices.engine.WebServicesFault  {
        com.ibm.ws.webservices.engine.MessageContext mc = super.messageContexts[_validateIndex0];
        if (mc == null) {
            mc = new com.ibm.ws.webservices.engine.MessageContext(super.engine);
            mc.setOperation(DataQualityWebServicePortBindingStub._validateOperation0);
            mc.setUseSOAPAction(true);
            mc.setSOAPActionURI("");
            mc.setEncodingStyle(com.ibm.ws.webservices.engine.Constants.URI_LITERAL_ENC);
            mc.setProperty(com.ibm.wsspi.webservices.Constants.SEND_TYPE_ATTR_PROPERTY, Boolean.FALSE);
            mc.setProperty(com.ibm.wsspi.webservices.Constants.ENGINE_DO_MULTI_REFS_PROPERTY, Boolean.FALSE);
            super.primeMessageContext(mc);
            super.messageContexts[_validateIndex0] = mc;
        }
        try {
            mc = (com.ibm.ws.webservices.engine.MessageContext) mc.clone();
        }
        catch (CloneNotSupportedException cnse) {
            throw com.ibm.ws.webservices.engine.WebServicesFault.makeFault(cnse);
        }
        return new com.ibm.ws.webservices.engine.client.Stub.Invoke(connection, mc, parameters);
    }

    public com.kasikornbank.dq.ws.v1.DataQualityResponse validate(com.kasikornbank.dq.ws.v1.DataQualityRequest dataQualityRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new com.ibm.ws.webservices.engine.NoEndPointException();
        }
        java.util.Vector _resp = null;
        try {
            _resp = _getvalidateInvoke0(new java.lang.Object[] {dataQualityRequest}).invoke();

        } catch (com.ibm.ws.webservices.engine.WebServicesFault wsf) {
            Exception e = wsf.getUserException();
            throw wsf;
        } 
        try {
            return (com.kasikornbank.dq.ws.v1.DataQualityResponse) ((com.ibm.ws.webservices.engine.xmlsoap.ext.ParamValue) _resp.get(0)).getValue();
        } catch (java.lang.Exception _exception) {
            return (com.kasikornbank.dq.ws.v1.DataQualityResponse) super.convert(((com.ibm.ws.webservices.engine.xmlsoap.ext.ParamValue) _resp.get(0)).getValue(), com.kasikornbank.dq.ws.v1.DataQualityResponse.class);
        }
    }

    private static void _staticInit() {
        _validateOperation0 = _getvalidateOperation0();
    }

    static {
       _staticInit();
    }
}
