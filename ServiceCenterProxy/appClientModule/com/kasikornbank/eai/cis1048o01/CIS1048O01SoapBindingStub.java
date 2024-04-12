/**
 * CIS1048O01SoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1048o01;

public class CIS1048O01SoapBindingStub extends com.ibm.ws.webservices.engine.client.Stub implements com.kasikornbank.eai.cis1048o01.CIS1048O01Soap {
    public CIS1048O01SoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws com.ibm.ws.webservices.engine.WebServicesFault {
        if (service == null) {
            super.service = new com.ibm.ws.webservices.engine.client.Service();
        }
        else {
            super.service = service;
        }
        super.engine = ((com.ibm.ws.webservices.engine.client.Service) super.service).getEngine();
        super.messageContexts = new com.ibm.ws.webservices.engine.MessageContext[1];
        java.lang.String theOption = (java.lang.String)super._getProperty("lastStubMapping");
        if (theOption == null || !theOption.equals("com.kasikornbank.eai.cis1048o01.CIS1048O01SoapBinding")) {
                initTypeMapping();
                super._setProperty("lastStubMapping","com.kasikornbank.eai.cis1048o01.CIS1048O01SoapBinding");
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
        javaType = com.kasikornbank.eai.cis1048o01.CIS1048O01_Type.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">doService>CIS1048O01");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.__doService_CIS1048O01_KBankHeader.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>doService>CIS1048O01>KBankHeader");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength30");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength47");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength5");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength32");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength8");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength2");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.CISHeader.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>doService>CIS1048O01>CISHeader");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength6");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength4");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.__doService_CIS1048O01_CISCustomer.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>doService>CIS1048O01>CISCustomer");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength1");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength10");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.ContactAddrObj.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>>doService>CIS1048O01>CISCustomer>contactAddrObj");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength15");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.AddrRelObj.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>>>doService>CIS1048O01>CISCustomer>contactAddrObj>addrRelObj");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.CISAddrRelation[].class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>>>>doService>CIS1048O01>CISCustomer>contactAddrObj>addrRelObj>addrRelsVect");
        compQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "CISAddrRelation");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>>>>>doService>CIS1048O01>CISCustomer>contactAddrObj>addrRelObj>addrRelsVect>CISAddrRelation");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.ArraySerializerFactory.class, javaType, xmlType, compQName, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.ArrayDeserializerFactory.class, javaType, xmlType, compQName, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.CISAddrRelation.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>>>>>doService>CIS1048O01>CISCustomer>contactAddrObj>addrRelObj>addrRelsVect>CISAddrRelation");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength20");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.CIS1048O01Response.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">doServiceResponse>CIS1048O01Response");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.__doServiceResponse_CIS1048O01Response_KBankHeader.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>doServiceResponse>CIS1048O01Response>KBankHeader");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.Error[].class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>>doServiceResponse>CIS1048O01Response>KBankHeader>errorVect");
        compQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "error");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>>>doServiceResponse>CIS1048O01Response>KBankHeader>errorVect>error");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.ArraySerializerFactory.class, javaType, xmlType, compQName, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.ArrayDeserializerFactory.class, javaType, xmlType, compQName, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.Error.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>>>doServiceResponse>CIS1048O01Response>KBankHeader>errorVect>error");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = java.lang.String.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "stringLength256");
        compTypeQName = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleSerializerFactory.class, javaType, xmlType, null, compTypeQName);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializerFactory.class, javaType, xmlType, null, compTypeQName);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

        javaType = com.kasikornbank.eai.cis1048o01.__doServiceResponse_CIS1048O01Response_CISCustomer.class;
        xmlType = com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">>doServiceResponse>CIS1048O01Response>CISCustomer");
        sf = com.ibm.ws.webservices.engine.encoding.ser.BaseSerializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanSerializerFactory.class, javaType, xmlType);
        df = com.ibm.ws.webservices.engine.encoding.ser.BaseDeserializerFactory.createFactory(com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializerFactory.class, javaType, xmlType);
        if (sf != null || df != null) {
            tm.register(javaType, xmlType, sf, df);
        }

    }

    private static com.ibm.ws.webservices.engine.description.OperationDesc _doServiceOperation0 = null;
    private static com.ibm.ws.webservices.engine.description.OperationDesc _getdoServiceOperation0() {
        com.ibm.ws.webservices.engine.description.ParameterDesc[]  _params0 = new com.ibm.ws.webservices.engine.description.ParameterDesc[] {
         new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "CIS1048O01"), com.ibm.ws.webservices.engine.description.ParameterDesc.IN, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">doService>CIS1048O01"), com.kasikornbank.eai.cis1048o01.CIS1048O01_Type.class, false, false, false, false, true, false), 
          };
        _params0[0].setOption("partName",">doService>CIS1048O01");
        _params0[0].setOption("partQNameString","{http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1}>doService>CIS1048O01");
        _params0[0].setOption("inputPosition","0");
        com.ibm.ws.webservices.engine.description.ParameterDesc  _returnDesc0 = new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "CIS1048O01Response"), com.ibm.ws.webservices.engine.description.ParameterDesc.OUT, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", ">doServiceResponse>CIS1048O01Response"), com.kasikornbank.eai.cis1048o01.CIS1048O01Response.class, true, false, false, false, true, false); 
        _returnDesc0.setOption("partName",">doServiceResponse>CIS1048O01Response");
        _returnDesc0.setOption("partQNameString","{http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1}>doServiceResponse>CIS1048O01Response");
        _returnDesc0.setOption("outputPosition","0");
        com.ibm.ws.webservices.engine.description.FaultDesc[]  _faults0 = new com.ibm.ws.webservices.engine.description.FaultDesc[] {
          };
        _doServiceOperation0 = new com.ibm.ws.webservices.engine.description.OperationDesc("doService", com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "doService"), _params0, _returnDesc0, _faults0, "");
        _doServiceOperation0.setOption("portTypeQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "CIS1048O01Soap"));
        _doServiceOperation0.setOption("outputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "doServiceResponse"));
        _doServiceOperation0.setOption("ResponseLocalPart","doServiceResponse");
        _doServiceOperation0.setOption("targetNamespace","http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1");
        _doServiceOperation0.setOption("ResponseNamespace","http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1");
        _doServiceOperation0.setOption("buildNum","gm1318.02");
        _doServiceOperation0.setOption("inoutOrderingReq","false");
        _doServiceOperation0.setOption("ServiceQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "CIS1048O01"));
        _doServiceOperation0.setOption("inputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "doService"));
        _doServiceOperation0.setUse(com.ibm.ws.webservices.engine.enumtype.Use.LITERAL);
        _doServiceOperation0.setStyle(com.ibm.ws.webservices.engine.enumtype.Style.WRAPPED);
        return _doServiceOperation0;

    }

    private int _doServiceIndex0 = 0;
    private synchronized com.ibm.ws.webservices.engine.client.Stub.Invoke _getdoServiceInvoke0(Object[] parameters) throws com.ibm.ws.webservices.engine.WebServicesFault  {
        com.ibm.ws.webservices.engine.MessageContext mc = super.messageContexts[_doServiceIndex0];
        if (mc == null) {
            mc = new com.ibm.ws.webservices.engine.MessageContext(super.engine);
            mc.setOperation(CIS1048O01SoapBindingStub._doServiceOperation0);
            mc.setUseSOAPAction(true);
            mc.setSOAPActionURI("");
            mc.setEncodingStyle(com.ibm.ws.webservices.engine.Constants.URI_LITERAL_ENC);
            mc.setProperty(com.ibm.wsspi.webservices.Constants.SEND_TYPE_ATTR_PROPERTY, Boolean.FALSE);
            mc.setProperty(com.ibm.wsspi.webservices.Constants.ENGINE_DO_MULTI_REFS_PROPERTY, Boolean.FALSE);
            super.primeMessageContext(mc);
            super.messageContexts[_doServiceIndex0] = mc;
        }
        try {
            mc = (com.ibm.ws.webservices.engine.MessageContext) mc.clone();
        }
        catch (CloneNotSupportedException cnse) {
            throw com.ibm.ws.webservices.engine.WebServicesFault.makeFault(cnse);
        }
        return new com.ibm.ws.webservices.engine.client.Stub.Invoke(connection, mc, parameters);
    }

    public com.kasikornbank.eai.cis1048o01.CIS1048O01Response doService(com.kasikornbank.eai.cis1048o01.CIS1048O01_Type CIS1048O01) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new com.ibm.ws.webservices.engine.NoEndPointException();
        }
        java.util.Vector _resp = null;
        try {
            _resp = _getdoServiceInvoke0(new java.lang.Object[] {CIS1048O01}).invoke();

        } catch (com.ibm.ws.webservices.engine.WebServicesFault wsf) {
            Exception e = wsf.getUserException();
            throw wsf;
        } 
        try {
            return (com.kasikornbank.eai.cis1048o01.CIS1048O01Response) ((com.ibm.ws.webservices.engine.xmlsoap.ext.ParamValue) _resp.get(0)).getValue();
        } catch (java.lang.Exception _exception) {
            return (com.kasikornbank.eai.cis1048o01.CIS1048O01Response) super.convert(((com.ibm.ws.webservices.engine.xmlsoap.ext.ParamValue) _resp.get(0)).getValue(), com.kasikornbank.eai.cis1048o01.CIS1048O01Response.class);
        }
    }

    private static void _staticInit() {
        _doServiceOperation0 = _getdoServiceOperation0();
    }

    static {
       _staticInit();
    }
}
