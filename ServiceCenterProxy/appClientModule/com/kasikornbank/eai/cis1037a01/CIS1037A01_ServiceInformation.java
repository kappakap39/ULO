/**
 * CIS1037A01_ServiceInformation.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1037a01;

public class CIS1037A01_ServiceInformation implements com.ibm.ws.webservices.multiprotocol.ServiceInformation {

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

        operationDescriptions.put("CIS1037A01SoapPort",inner0);
        operationDescriptions = java.util.Collections.unmodifiableMap(operationDescriptions);
    }

    private static com.ibm.ws.webservices.engine.description.OperationDesc _doService0Op() {
        com.ibm.ws.webservices.engine.description.OperationDesc doService0Op = null;
        com.ibm.ws.webservices.engine.description.ParameterDesc[]  _params0 = new com.ibm.ws.webservices.engine.description.ParameterDesc[] {
         new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "CIS1037A01"), com.ibm.ws.webservices.engine.description.ParameterDesc.IN, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">doService>CIS1037A01"), com.kasikornbank.eai.cis1037a01.CIS1037A01_Type.class, false, false, false, false, true, false), 
          };
        _params0[0].setOption("partName",">doService>CIS1037A01");
        _params0[0].setOption("partQNameString","{http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1}>doService>CIS1037A01");
        _params0[0].setOption("inputPosition","0");
        com.ibm.ws.webservices.engine.description.ParameterDesc  _returnDesc0 = new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "CIS1037A01Response"), com.ibm.ws.webservices.engine.description.ParameterDesc.OUT, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">doServiceResponse>CIS1037A01Response"), com.kasikornbank.eai.cis1037a01.CIS1037A01Response.class, true, false, false, false, true, false); 
        _returnDesc0.setOption("partName",">doServiceResponse>CIS1037A01Response");
        _returnDesc0.setOption("partQNameString","{http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1}>doServiceResponse>CIS1037A01Response");
        _returnDesc0.setOption("outputPosition","0");
        com.ibm.ws.webservices.engine.description.FaultDesc[]  _faults0 = new com.ibm.ws.webservices.engine.description.FaultDesc[] {
          };
        doService0Op = new com.ibm.ws.webservices.engine.description.OperationDesc("doService", com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "doService"), _params0, _returnDesc0, _faults0, null);
        doService0Op.setOption("portTypeQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "CIS1037A01Soap"));
        doService0Op.setOption("outputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "doServiceResponse"));
        doService0Op.setOption("ResponseLocalPart","doServiceResponse");
        doService0Op.setOption("targetNamespace","http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1");
        doService0Op.setOption("ResponseNamespace","http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1");
        doService0Op.setOption("buildNum","gm1318.02");
        doService0Op.setOption("inoutOrderingReq","false");
        doService0Op.setOption("ServiceQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "CIS1037A01"));
        doService0Op.setOption("inputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "doService"));
        doService0Op.setStyle(com.ibm.ws.webservices.engine.enumtype.Style.WRAPPED);
        return doService0Op;

    }


    private static void initTypeMappings() {
        typeMappings = new java.util.HashMap();
        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">doService>CIS1037A01"),
                         com.kasikornbank.eai.cis1037a01.CIS1037A01_Type.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>doService>CIS1037A01>KBankHeader"),
                         com.kasikornbank.eai.cis1037a01.__doService_CIS1037A01_KBankHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength30"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength47"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength5"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength32"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength8"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength2"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>doService>CIS1037A01>CISHeader"),
                         com.kasikornbank.eai.cis1037a01.CISHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength4"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength6"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>doService>CIS1037A01>CISIndivCust"),
                         com.kasikornbank.eai.cis1037a01.__doService_CIS1037A01_CISIndivCust.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength1"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "decimalLength17frictions17_2"),
                         java.math.BigDecimal.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "intLength2"),
                         int.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength20"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doService>CIS1037A01>CISIndivCust>contactAddrObj"),
                         com.kasikornbank.eai.cis1037a01.___doService_CIS1037A01_CISIndivCust_contactAddrObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength40"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength15"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength10"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength50"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doService>CIS1037A01>CISIndivCust>contactsObj"),
                         com.kasikornbank.eai.cis1037a01.___doService_CIS1037A01_CISIndivCust_contactsObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>>doService>CIS1037A01>CISIndivCust>contactsObj>contactsVect"),
                         com.kasikornbank.eai.cis1037a01._____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>>>doService>CIS1037A01>CISIndivCust>contactsObj>contactsVect>CISContact"),
                         com.kasikornbank.eai.cis1037a01._____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "intLength6"),
                         int.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength120"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength60"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doService>CIS1037A01>CISIndivCust>KYCObj"),
                         com.kasikornbank.eai.cis1037a01.___doService_CIS1037A01_CISIndivCust_KYCObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength3"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doService>CIS1037A01>CISIndivCust>obsContactAddrObj"),
                         com.kasikornbank.eai.cis1037a01.ObsContactAddrObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength70"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength105"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doService>CIS1037A01>CISIndivCust>obsOffclAddrObj"),
                         com.kasikornbank.eai.cis1037a01.ObsOffclAddrObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doService>CIS1037A01>CISIndivCust>offclAddrObj"),
                         com.kasikornbank.eai.cis1037a01.___doService_CIS1037A01_CISIndivCust_offclAddrObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength80"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength13"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength9"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">doServiceResponse>CIS1037A01Response"),
                         com.kasikornbank.eai.cis1037a01.CIS1037A01Response.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>doServiceResponse>CIS1037A01Response>KBankHeader"),
                         com.kasikornbank.eai.cis1037a01.__doServiceResponse_CIS1037A01Response_KBankHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doServiceResponse>CIS1037A01Response>KBankHeader>errorVect"),
                         com.kasikornbank.eai.cis1037a01.Error[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>>doServiceResponse>CIS1037A01Response>KBankHeader>errorVect>error"),
                         com.kasikornbank.eai.cis1037a01.Error.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", "stringLength256"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>doServiceResponse>CIS1037A01Response>CISIndivCust"),
                         com.kasikornbank.eai.cis1037a01.__doServiceResponse_CIS1037A01Response_CISIndivCust.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doServiceResponse>CIS1037A01Response>CISIndivCust>contactAddrObj"),
                         com.kasikornbank.eai.cis1037a01.___doServiceResponse_CIS1037A01Response_CISIndivCust_contactAddrObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doServiceResponse>CIS1037A01Response>CISIndivCust>contactsObj"),
                         com.kasikornbank.eai.cis1037a01.___doServiceResponse_CIS1037A01Response_CISIndivCust_contactsObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>>doServiceResponse>CIS1037A01Response>CISIndivCust>contactsObj>contactsVect"),
                         com.kasikornbank.eai.cis1037a01._____doServiceResponse_CIS1037A01Response_CISIndivCust_contactsObj_contactsVect_CISContact[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>>>doServiceResponse>CIS1037A01Response>CISIndivCust>contactsObj>contactsVect>CISContact"),
                         com.kasikornbank.eai.cis1037a01._____doServiceResponse_CIS1037A01Response_CISIndivCust_contactsObj_contactsVect_CISContact.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doServiceResponse>CIS1037A01Response>CISIndivCust>CVRSInfoObj"),
                         com.kasikornbank.eai.cis1037a01.CVRSInfoObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>>doServiceResponse>CIS1037A01Response>CISIndivCust>CVRSInfoObj>regulsObj"),
                         com.kasikornbank.eai.cis1037a01.RegulsObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>>>doServiceResponse>CIS1037A01Response>CISIndivCust>CVRSInfoObj>regulsObj>regulsVect"),
                         com.kasikornbank.eai.cis1037a01.CVRSRegulation[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>>>>doServiceResponse>CIS1037A01Response>CISIndivCust>CVRSInfoObj>regulsObj>regulsVect>CVRSRegulation"),
                         com.kasikornbank.eai.cis1037a01.CVRSRegulation.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doServiceResponse>CIS1037A01Response>CISIndivCust>KYCObj"),
                         com.kasikornbank.eai.cis1037a01.___doServiceResponse_CIS1037A01Response_CISIndivCust_KYCObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1037A01/v1", ">>>doServiceResponse>CIS1037A01Response>CISIndivCust>offclAddrObj"),
                         com.kasikornbank.eai.cis1037a01.___doServiceResponse_CIS1037A01Response_CISIndivCust_offclAddrObj.class);

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
