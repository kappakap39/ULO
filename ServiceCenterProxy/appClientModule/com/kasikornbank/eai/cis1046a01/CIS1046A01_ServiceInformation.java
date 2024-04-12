/**
 * CIS1046A01_ServiceInformation.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1046a01;

public class CIS1046A01_ServiceInformation implements com.ibm.ws.webservices.multiprotocol.ServiceInformation {

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
        inner0.put("doService", list0);

        com.ibm.ws.webservices.engine.description.OperationDesc doService0Op = _doService0Op();
        list0.add(doService0Op);

        operationDescriptions.put("CIS1046A01SoapPort",inner0);
        operationDescriptions = java.util.Collections.unmodifiableMap(operationDescriptions);
    }

    private static com.ibm.ws.webservices.engine.description.OperationDesc _doService0Op() {
        com.ibm.ws.webservices.engine.description.OperationDesc doService0Op = null;
        com.ibm.ws.webservices.engine.description.ParameterDesc[]  _params0 = new com.ibm.ws.webservices.engine.description.ParameterDesc[] {
         new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "CIS1046A01"), com.ibm.ws.webservices.engine.description.ParameterDesc.IN, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">doService>CIS1046A01"), com.kasikornbank.eai.cis1046a01.CIS1046A01_Type.class, false, false, false, false, true, false), 
          };
        _params0[0].setOption("partName",">doService>CIS1046A01");
        _params0[0].setOption("partQNameString","{http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1}>doService>CIS1046A01");
        _params0[0].setOption("inputPosition","0");
        com.ibm.ws.webservices.engine.description.ParameterDesc  _returnDesc0 = new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "CIS1046A01Response"), com.ibm.ws.webservices.engine.description.ParameterDesc.OUT, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">doServiceResponse>CIS1046A01Response"), com.kasikornbank.eai.cis1046a01.CIS1046A01Response.class, true, false, false, false, true, false); 
        _returnDesc0.setOption("partName",">doServiceResponse>CIS1046A01Response");
        _returnDesc0.setOption("partQNameString","{http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1}>doServiceResponse>CIS1046A01Response");
        _returnDesc0.setOption("outputPosition","0");
        com.ibm.ws.webservices.engine.description.FaultDesc[]  _faults0 = new com.ibm.ws.webservices.engine.description.FaultDesc[] {
          };
        doService0Op = new com.ibm.ws.webservices.engine.description.OperationDesc("doService", com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "doService"), _params0, _returnDesc0, _faults0, null);
        doService0Op.setOption("portTypeQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "CIS1046A01Soap"));
        doService0Op.setOption("outputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "doServiceResponse"));
        doService0Op.setOption("ResponseLocalPart","doServiceResponse");
        doService0Op.setOption("targetNamespace","http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1");
        doService0Op.setOption("ResponseNamespace","http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1");
        doService0Op.setOption("buildNum","gm1318.02");
        doService0Op.setOption("inoutOrderingReq","false");
        doService0Op.setOption("ServiceQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "CIS1046A01"));
        doService0Op.setOption("inputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "doService"));
        doService0Op.setStyle(com.ibm.ws.webservices.engine.enumtype.Style.WRAPPED);
        return doService0Op;

    }


    private static void initTypeMappings() {
        typeMappings = new java.util.HashMap();
        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">doService>CIS1046A01"),
                         com.kasikornbank.eai.cis1046a01.CIS1046A01_Type.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>doService>CIS1046A01>KBankHeader"),
                         com.kasikornbank.eai.cis1046a01.__doService_CIS1046A01_KBankHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength30"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength47"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength5"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength32"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength8"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength2"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>doService>CIS1046A01>CISHeader"),
                         com.kasikornbank.eai.cis1046a01.CISHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength6"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength4"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>doService>CIS1046A01>CISCustomer"),
                         com.kasikornbank.eai.cis1046a01.__doService_CIS1046A01_CISCustomer.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength1"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength10"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>>doService>CIS1046A01>CISCustomer>contactAddrObj"),
                         com.kasikornbank.eai.cis1046a01.___doService_CIS1046A01_CISCustomer_contactAddrObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength15"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>>>doService>CIS1046A01>CISCustomer>contactAddrObj>addrRelObj"),
                         com.kasikornbank.eai.cis1046a01.____doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>>>>doService>CIS1046A01>CISCustomer>contactAddrObj>addrRelObj>addrRelsVect"),
                         com.kasikornbank.eai.cis1046a01.______doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_addrRelsVect_CISAddrRelation[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>>>>>doService>CIS1046A01>CISCustomer>contactAddrObj>addrRelObj>addrRelsVect>CISAddrRelation"),
                         com.kasikornbank.eai.cis1046a01.______doService_CIS1046A01_CISCustomer_contactAddrObj_addrRelObj_addrRelsVect_CISAddrRelation.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength20"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">doServiceResponse>CIS1046A01Response"),
                         com.kasikornbank.eai.cis1046a01.CIS1046A01Response.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>doServiceResponse>CIS1046A01Response>KBankHeader"),
                         com.kasikornbank.eai.cis1046a01.__doServiceResponse_CIS1046A01Response_KBankHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>>doServiceResponse>CIS1046A01Response>KBankHeader>errorVect"),
                         com.kasikornbank.eai.cis1046a01.Error[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>>>doServiceResponse>CIS1046A01Response>KBankHeader>errorVect>error"),
                         com.kasikornbank.eai.cis1046a01.Error.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength256"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>doServiceResponse>CIS1046A01Response>CISCustomer"),
                         com.kasikornbank.eai.cis1046a01.__doServiceResponse_CIS1046A01Response_CISCustomer.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>>doServiceResponse>CIS1046A01Response>CISCustomer>contactAddrObj"),
                         com.kasikornbank.eai.cis1046a01.___doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>>>doServiceResponse>CIS1046A01Response>CISCustomer>contactAddrObj>addrRelObj"),
                         com.kasikornbank.eai.cis1046a01.____doServiceResponse_CIS1046A01Response_CISCustomer_contactAddrObj_addrRelObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>>>>doServiceResponse>CIS1046A01Response>CISCustomer>contactAddrObj>addrRelObj>addrRelsVect"),
                         com.kasikornbank.eai.cis1046a01.______doServiceRespo_750316492_CISAddrRelation[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", ">>>>>>doServiceResponse>CIS1046A01Response>CISCustomer>contactAddrObj>addrRelObj>addrRelsVect>CISAddrRelation"),
                         com.kasikornbank.eai.cis1046a01.______doServiceRespo_750316492_CISAddrRelation.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1046A01/v1", "stringLength80"),
                         java.lang.String.class);

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
