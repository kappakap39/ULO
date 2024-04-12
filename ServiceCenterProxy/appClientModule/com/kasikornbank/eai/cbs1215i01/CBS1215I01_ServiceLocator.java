/**
 * CBS1215I01_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cbs1215i01;

public class CBS1215I01_ServiceLocator extends com.ibm.ws.webservices.multiprotocol.AgnosticService implements com.ibm.ws.webservices.multiprotocol.GeneratedService, com.kasikornbank.eai.cbs1215i01.CBS1215I01_Service {

    public CBS1215I01_ServiceLocator() {
        super(com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
           "http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2",
           "CBS1215I01"));

        context.setLocatorName("com.kasikornbank.eai.cbs1215i01.CBS1215I01_ServiceLocator");
    }

    public CBS1215I01_ServiceLocator(com.ibm.ws.webservices.multiprotocol.ServiceContext ctx) {
        super(ctx);
        context.setLocatorName("com.kasikornbank.eai.cbs1215i01.CBS1215I01_ServiceLocator");
    }

    // Use to get a proxy class for CBS1215I01SoapPort
    private final java.lang.String CBS1215I01SoapPort_address = "http://localhost:7001/EAIWS/CBS1215I01.jws";

    public java.lang.String getCBS1215I01SoapPortAddress() {
        if (context.getOverriddingEndpointURIs() == null) {
            return CBS1215I01SoapPort_address;
        }
        String overriddingEndpoint = (String) context.getOverriddingEndpointURIs().get("CBS1215I01SoapPort");
        if (overriddingEndpoint != null) {
            return overriddingEndpoint;
        }
        else {
            return CBS1215I01SoapPort_address;
        }
    }

    private java.lang.String CBS1215I01SoapPortPortName = "CBS1215I01SoapPort";

    // The WSDD port name defaults to the port name.
    private java.lang.String CBS1215I01SoapPortWSDDPortName = "CBS1215I01SoapPort";

    public java.lang.String getCBS1215I01SoapPortWSDDPortName() {
        return CBS1215I01SoapPortWSDDPortName;
    }

    public void setCBS1215I01SoapPortWSDDPortName(java.lang.String name) {
        CBS1215I01SoapPortWSDDPortName = name;
    }

    public com.kasikornbank.eai.cbs1215i01.CBS1215I01Soap getCBS1215I01SoapPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(getCBS1215I01SoapPortAddress());
        }
        catch (java.net.MalformedURLException e) {
            return null; // unlikely as URL was validated in WSDL2Java
        }
        return getCBS1215I01SoapPort(endpoint);
    }

    public com.kasikornbank.eai.cbs1215i01.CBS1215I01Soap getCBS1215I01SoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        com.kasikornbank.eai.cbs1215i01.CBS1215I01Soap _stub =
            (com.kasikornbank.eai.cbs1215i01.CBS1215I01Soap) getStub(
                CBS1215I01SoapPortPortName,
                (String) getPort2NamespaceMap().get(CBS1215I01SoapPortPortName),
                com.kasikornbank.eai.cbs1215i01.CBS1215I01Soap.class,
                "com.kasikornbank.eai.cbs1215i01.CBS1215I01SoapBindingStub",
                portAddress.toString());
        if (_stub instanceof com.ibm.ws.webservices.engine.client.Stub) {
            ((com.ibm.ws.webservices.engine.client.Stub) _stub).setPortName(CBS1215I01SoapPortWSDDPortName);
        }
        return _stub;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.kasikornbank.eai.cbs1215i01.CBS1215I01Soap.class.isAssignableFrom(serviceEndpointInterface)) {
                return getCBS1215I01SoapPort();
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("WSWS3273E: Error: There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        String inputPortName = portName.getLocalPart();
        if ("CBS1215I01SoapPort".equals(inputPortName)) {
            return getCBS1215I01SoapPort();
        }
        else  {
            throw new javax.xml.rpc.ServiceException();
        }
    }

    public void setPortNamePrefix(java.lang.String prefix) {
        CBS1215I01SoapPortWSDDPortName = prefix + "/" + CBS1215I01SoapPortPortName;
    }

    public javax.xml.namespace.QName getServiceName() {
        return com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CBS1215I01/v2", "CBS1215I01");
    }

    private java.util.Map port2NamespaceMap = null;

    protected synchronized java.util.Map getPort2NamespaceMap() {
        if (port2NamespaceMap == null) {
            port2NamespaceMap = new java.util.HashMap();
            port2NamespaceMap.put(
               "CBS1215I01SoapPort",
               "http://schemas.xmlsoap.org/wsdl/soap/");
        }
        return port2NamespaceMap;
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            String serviceNamespace = getServiceName().getNamespaceURI();
            for (java.util.Iterator i = getPort2NamespaceMap().keySet().iterator(); i.hasNext(); ) {
                ports.add(
                    com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                        serviceNamespace,
                        (String) i.next()));
            }
        }
        return ports.iterator();
    }

    public javax.xml.rpc.Call[] getCalls(javax.xml.namespace.QName portName) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            throw new javax.xml.rpc.ServiceException("WSWS3062E: Error: portName should not be null.");
        }
        if  (portName.getLocalPart().equals("CBS1215I01SoapPort")) {
            return new javax.xml.rpc.Call[] {
                createCall(portName, "doService", "null"),
            };
        }
        else {
            throw new javax.xml.rpc.ServiceException("WSWS3062E: Error: portName should not be null.");
        }
    }
}
