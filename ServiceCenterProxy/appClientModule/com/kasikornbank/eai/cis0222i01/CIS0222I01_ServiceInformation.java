/**
 * CIS0222I01_ServiceInformation.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis0222i01;

public class CIS0222I01_ServiceInformation implements com.ibm.ws.webservices.multiprotocol.ServiceInformation {

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

        operationDescriptions.put("CIS0222I01SoapPort",inner0);
        operationDescriptions = java.util.Collections.unmodifiableMap(operationDescriptions);
    }

    private static com.ibm.ws.webservices.engine.description.OperationDesc _doService0Op() {
        com.ibm.ws.webservices.engine.description.OperationDesc doService0Op = null;
        com.ibm.ws.webservices.engine.description.ParameterDesc[]  _params0 = new com.ibm.ws.webservices.engine.description.ParameterDesc[] {
         new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "CIS0222I01"), com.ibm.ws.webservices.engine.description.ParameterDesc.IN, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">doService>CIS0222I01"), com.kasikornbank.eai.cis0222i01.CIS0222I01_Type.class, false, false, false, false, true, false), 
          };
        _params0[0].setOption("partName",">doService>CIS0222I01");
        _params0[0].setOption("partQNameString","{http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2}>doService>CIS0222I01");
        _params0[0].setOption("inputPosition","0");
        com.ibm.ws.webservices.engine.description.ParameterDesc  _returnDesc0 = new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "CIS0222I01Response"), com.ibm.ws.webservices.engine.description.ParameterDesc.OUT, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">doServiceResponse>CIS0222I01Response"), com.kasikornbank.eai.cis0222i01.CIS0222I01Response.class, true, false, false, false, true, false); 
        _returnDesc0.setOption("partName",">doServiceResponse>CIS0222I01Response");
        _returnDesc0.setOption("partQNameString","{http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2}>doServiceResponse>CIS0222I01Response");
        _returnDesc0.setOption("outputPosition","0");
        com.ibm.ws.webservices.engine.description.FaultDesc[]  _faults0 = new com.ibm.ws.webservices.engine.description.FaultDesc[] {
          };
        doService0Op = new com.ibm.ws.webservices.engine.description.OperationDesc("doService", com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "doService"), _params0, _returnDesc0, _faults0, null);
        doService0Op.setOption("portTypeQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "CIS0222I01Soap"));
        doService0Op.setOption("outputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "doServiceResponse"));
        doService0Op.setOption("ResponseLocalPart","doServiceResponse");
        doService0Op.setOption("targetNamespace","http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2");
        doService0Op.setOption("ResponseNamespace","http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2");
        doService0Op.setOption("buildNum","gm1318.02");
        doService0Op.setOption("inoutOrderingReq","false");
        doService0Op.setOption("ServiceQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "CIS0222I01"));
        doService0Op.setOption("inputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "doService"));
        doService0Op.setStyle(com.ibm.ws.webservices.engine.enumtype.Style.WRAPPED);
        return doService0Op;

    }


    private static void initTypeMappings() {
        typeMappings = new java.util.HashMap();
        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">doService>CIS0222I01"),
                         com.kasikornbank.eai.cis0222i01.CIS0222I01_Type.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>doService>CIS0222I01>EAIHeader"),
                         com.kasikornbank.eai.cis0222i01.__doService_CIS0222I01_EAIHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "stringLength12"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "stringLength32"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "intLength1"),
                         int.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "intLength5"),
                         int.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "stringLength1"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>doService>CIS0222I01>CISCustomer"),
                         com.kasikornbank.eai.cis0222i01.__doService_CIS0222I01_CISCustomer.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>>doService>CIS0222I01>CISCustomer>zipCodesObj"),
                         com.kasikornbank.eai.cis0222i01.___doService_CIS0222I01_CISCustomer_zipCodesObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>>>doService>CIS0222I01>CISCustomer>zipCodesObj>zipCodesVect"),
                         com.kasikornbank.eai.cis0222i01.____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>>>>doService>CIS0222I01>CISCustomer>zipCodesObj>zipCodesVect>CISZipCode"),
                         com.kasikornbank.eai.cis0222i01._____doService_CIS0222I01_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "stringLength30"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "stringLength40"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">doServiceResponse>CIS0222I01Response"),
                         com.kasikornbank.eai.cis0222i01.CIS0222I01Response.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>doServiceResponse>CIS0222I01Response>EAIHeader"),
                         com.kasikornbank.eai.cis0222i01.__doServiceResponse_CIS0222I01Response_EAIHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>>doServiceResponse>CIS0222I01Response>EAIHeader>multiMsgVect"),
                         com.kasikornbank.eai.cis0222i01.MultiMsg[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>>>doServiceResponse>CIS0222I01Response>EAIHeader>multiMsgVect>MultiMsg"),
                         com.kasikornbank.eai.cis0222i01.MultiMsg.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>>>>doServiceResponse>CIS0222I01Response>EAIHeader>multiMsgVect>MultiMsg>refFieldVect"),
                         com.kasikornbank.eai.cis0222i01.RefField[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>>>>>doServiceResponse>CIS0222I01Response>EAIHeader>multiMsgVect>MultiMsg>refFieldVect>RefField"),
                         com.kasikornbank.eai.cis0222i01.RefField.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>doServiceResponse>CIS0222I01Response>CISCustomer"),
                         com.kasikornbank.eai.cis0222i01.__doServiceResponse_CIS0222I01Response_CISCustomer.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>>doServiceResponse>CIS0222I01Response>CISCustomer>zipCodesObj"),
                         com.kasikornbank.eai.cis0222i01.___doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "intLength3"),
                         int.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>>>doServiceResponse>CIS0222I01Response>CISCustomer>zipCodesObj>zipCodesVect"),
                         com.kasikornbank.eai.cis0222i01._____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", ">>>>>doServiceResponse>CIS0222I01Response>CISCustomer>zipCodesObj>zipCodesVect>CISZipCode"),
                         com.kasikornbank.eai.cis0222i01._____doServiceResponse_CIS0222I01Response_CISCustomer_zipCodesObj_zipCodesVect_CISZipCode.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS0222I01/v2", "stringLength5"),
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
