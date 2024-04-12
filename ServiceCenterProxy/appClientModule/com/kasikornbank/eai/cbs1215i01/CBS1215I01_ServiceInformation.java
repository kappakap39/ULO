/**
 * CBS1215I01_ServiceInformation.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cbs1215i01;

public class CBS1215I01_ServiceInformation implements com.ibm.ws.webservices.multiprotocol.ServiceInformation {

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

        operationDescriptions.put("CBS1215I01SoapPort",inner0);
        operationDescriptions = java.util.Collections.unmodifiableMap(operationDescriptions);
    }

    private static com.ibm.ws.webservices.engine.description.OperationDesc _doService0Op() {
        com.ibm.ws.webservices.engine.description.OperationDesc doService0Op = null;
        com.ibm.ws.webservices.engine.description.ParameterDesc[]  _params0 = new com.ibm.ws.webservices.engine.description.ParameterDesc[] {
         new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "CBS1215I01"), com.ibm.ws.webservices.engine.description.ParameterDesc.IN, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">doService>CBS1215I01"), com.kasikornbank.eai.cbs1215i01.CBS1215I01_Type.class, false, false, false, false, true, false), 
          };
        _params0[0].setOption("partName",">doService>CBS1215I01");
        _params0[0].setOption("partQNameString","{http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2}>doService>CBS1215I01");
        _params0[0].setOption("inputPosition","0");
        com.ibm.ws.webservices.engine.description.ParameterDesc  _returnDesc0 = new com.ibm.ws.webservices.engine.description.ParameterDesc(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "CBS1215I01Response"), com.ibm.ws.webservices.engine.description.ParameterDesc.OUT, com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">doServiceResponse>CBS1215I01Response"), com.kasikornbank.eai.cbs1215i01.CBS1215I01Response.class, true, false, false, false, true, false); 
        _returnDesc0.setOption("partName",">doServiceResponse>CBS1215I01Response");
        _returnDesc0.setOption("partQNameString","{http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2}>doServiceResponse>CBS1215I01Response");
        _returnDesc0.setOption("outputPosition","0");
        com.ibm.ws.webservices.engine.description.FaultDesc[]  _faults0 = new com.ibm.ws.webservices.engine.description.FaultDesc[] {
          };
        doService0Op = new com.ibm.ws.webservices.engine.description.OperationDesc("doService", com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "doService"), _params0, _returnDesc0, _faults0, null);
        doService0Op.setOption("portTypeQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "CBS1215I01Soap"));
        doService0Op.setOption("outputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "doServiceResponse"));
        doService0Op.setOption("ResponseLocalPart","doServiceResponse");
        doService0Op.setOption("targetNamespace","http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2");
        doService0Op.setOption("ResponseNamespace","http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2");
        doService0Op.setOption("buildNum","gm1318.02");
        doService0Op.setOption("inoutOrderingReq","false");
        doService0Op.setOption("ServiceQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "CBS1215I01"));
        doService0Op.setOption("inputMessageQName",com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "doService"));
        doService0Op.setStyle(com.ibm.ws.webservices.engine.enumtype.Style.WRAPPED);
        return doService0Op;

    }


    private static void initTypeMappings() {
        typeMappings = new java.util.HashMap();
        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">doService>CBS1215I01"),
                         com.kasikornbank.eai.cbs1215i01.CBS1215I01_Type.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>doService>CBS1215I01>KBankHeader"),
                         com.kasikornbank.eai.cbs1215i01.__doService_CBS1215I01_KBankHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength30"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength47"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength5"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength32"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength8"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength2"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength15"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "intLength3"),
                         int.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>doService>CBS1215I01>CBSAccount"),
                         com.kasikornbank.eai.cbs1215i01.__doService_CBS1215I01_CBSAccount.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength10"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength1"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength3"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>doService>CBS1215I01>CBSNavCntrl"),
                         com.kasikornbank.eai.cbs1215i01.__doService_CBS1215I01_CBSNavCntrl.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "intLength2"),
                         int.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">doServiceResponse>CBS1215I01Response"),
                         com.kasikornbank.eai.cbs1215i01.CBS1215I01Response.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>doServiceResponse>CBS1215I01Response>KBankHeader"),
                         com.kasikornbank.eai.cbs1215i01.__doServiceResponse_CBS1215I01Response_KBankHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>>doServiceResponse>CBS1215I01Response>KBankHeader>errorVect"),
                         com.kasikornbank.eai.cbs1215i01.Error[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>>>doServiceResponse>CBS1215I01Response>KBankHeader>errorVect>error"),
                         com.kasikornbank.eai.cbs1215i01.Error.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength256"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>doServiceResponse>CBS1215I01Response>CBSAccount"),
                         com.kasikornbank.eai.cbs1215i01.__doServiceResponse_CBS1215I01Response_CBSAccount.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>>doServiceResponse>CBS1215I01Response>CBSAccount>retentionsObj"),
                         com.kasikornbank.eai.cbs1215i01.RetentionsObj.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>>>doServiceResponse>CBS1215I01Response>CBSAccount>retentionsObj>retentionsVect"),
                         com.kasikornbank.eai.cbs1215i01.CBSRetention[].class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>>>>doServiceResponse>CBS1215I01Response>CBSAccount>retentionsObj>retentionsVect>CBSRetention"),
                         com.kasikornbank.eai.cbs1215i01.CBSRetention.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength50"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength4"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength100"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "intLength4"),
                         int.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "decimalLength18frictions18_2"),
                         java.math.BigDecimal.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "stringLength7"),
                         java.lang.String.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>doServiceResponse>CBS1215I01Response>CBSHeader"),
                         com.kasikornbank.eai.cbs1215i01.CBSHeader.class);

        typeMappings.put(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", ">>doServiceResponse>CBS1215I01Response>CBSNavCntrl"),
                         com.kasikornbank.eai.cbs1215i01.__doServiceResponse_CBS1215I01Response_CBSNavCntrl.class);

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
