/**
 * CIS1048O01_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1048o01;

public class CIS1048O01_ServiceLocator extends com.ibm.ws.webservices.multiprotocol.AgnosticService implements com.ibm.ws.webservices.multiprotocol.GeneratedService, com.kasikornbank.eai.cis1048o01.CIS1048O01_Service {

    public CIS1048O01_ServiceLocator() {
        super(com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
           "http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1",
           "CIS1048O01"));

        context.setLocatorName("com.kasikornbank.eai.cis1048o01.CIS1048O01_ServiceLocator");
    }

    public CIS1048O01_ServiceLocator(com.ibm.ws.webservices.multiprotocol.ServiceContext ctx) {
        super(ctx);
        context.setLocatorName("com.kasikornbank.eai.cis1048o01.CIS1048O01_ServiceLocator");
    }

    // Use to get a proxy class for CIS1048O01SoapPort
    private final java.lang.String CIS1048O01SoapPort_address = "http://localhost:7001/EAIWS/CIS1048O01.jws";

    public java.lang.String getCIS1048O01SoapPortAddress() {
        if (context.getOverriddingEndpointURIs() == null) {
            return CIS1048O01SoapPort_address;
        }
        String overriddingEndpoint = (String) context.getOverriddingEndpointURIs().get("CIS1048O01SoapPort");
        if (overriddingEndpoint != null) {
            return overriddingEndpoint;
        }
        else {
            return CIS1048O01SoapPort_address;
        }
    }

    private java.lang.String CIS1048O01SoapPortPortName = "CIS1048O01SoapPort";

    // The WSDD port name defaults to the port name.
    private java.lang.String CIS1048O01SoapPortWSDDPortName = "CIS1048O01SoapPort";

    public java.lang.String getCIS1048O01SoapPortWSDDPortName() {
        return CIS1048O01SoapPortWSDDPortName;
    }

    public void setCIS1048O01SoapPortWSDDPortName(java.lang.String name) {
        CIS1048O01SoapPortWSDDPortName = name;
    }

    public com.kasikornbank.eai.cis1048o01.CIS1048O01Soap getCIS1048O01SoapPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(getCIS1048O01SoapPortAddress());
        }
        catch (java.net.MalformedURLException e) {
            return null; // unlikely as URL was validated in WSDL2Java
        }
        return getCIS1048O01SoapPort(endpoint);
    }

    public com.kasikornbank.eai.cis1048o01.CIS1048O01Soap getCIS1048O01SoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        com.kasikornbank.eai.cis1048o01.CIS1048O01Soap _stub =
            (com.kasikornbank.eai.cis1048o01.CIS1048O01Soap) getStub(
                CIS1048O01SoapPortPortName,
                (String) getPort2NamespaceMap().get(CIS1048O01SoapPortPortName),
                com.kasikornbank.eai.cis1048o01.CIS1048O01Soap.class,
                "com.kasikornbank.eai.cis1048o01.CIS1048O01SoapBindingStub",
                portAddress.toString());
        if (_stub instanceof com.ibm.ws.webservices.engine.client.Stub) {
            ((com.ibm.ws.webservices.engine.client.Stub) _stub).setPortName(CIS1048O01SoapPortWSDDPortName);
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
            if (com.kasikornbank.eai.cis1048o01.CIS1048O01Soap.class.isAssignableFrom(serviceEndpointInterface)) {
                return getCIS1048O01SoapPort();
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
        if ("CIS1048O01SoapPort".equals(inputPortName)) {
            return getCIS1048O01SoapPort();
        }
        else  {
            throw new javax.xml.rpc.ServiceException();
        }
    }

    public void setPortNamePrefix(java.lang.String prefix) {
        CIS1048O01SoapPortWSDDPortName = prefix + "/" + CIS1048O01SoapPortPortName;
    }

    public javax.xml.namespace.QName getServiceName() {
        return com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1048O01/v1", "CIS1048O01");
    }

    private java.util.Map port2NamespaceMap = null;

    protected synchronized java.util.Map getPort2NamespaceMap() {
        if (port2NamespaceMap == null) {
            port2NamespaceMap = new java.util.HashMap();
            port2NamespaceMap.put(
               "CIS1048O01SoapPort",
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
        if  (portName.getLocalPart().equals("CIS1048O01SoapPort")) {
            return new javax.xml.rpc.Call[] {
                createCall(portName, "doService", "null"),
            };
        }
        else {
            throw new javax.xml.rpc.ServiceException("WSWS3062E: Error: portName should not be null.");
        }
    }
}
