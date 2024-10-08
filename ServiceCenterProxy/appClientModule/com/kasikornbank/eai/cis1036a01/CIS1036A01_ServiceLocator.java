/**
 * CIS1036A01_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.eai.cis1036a01;

public class CIS1036A01_ServiceLocator extends com.ibm.ws.webservices.multiprotocol.AgnosticService implements com.ibm.ws.webservices.multiprotocol.GeneratedService, com.kasikornbank.eai.cis1036a01.CIS1036A01_Service {

    public CIS1036A01_ServiceLocator() {
        super(com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
           "http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1",
           "CIS1036A01"));

        context.setLocatorName("com.kasikornbank.eai.cis1036a01.CIS1036A01_ServiceLocator");
    }

    public CIS1036A01_ServiceLocator(com.ibm.ws.webservices.multiprotocol.ServiceContext ctx) {
        super(ctx);
        context.setLocatorName("com.kasikornbank.eai.cis1036a01.CIS1036A01_ServiceLocator");
    }

    // Use to get a proxy class for CIS1036A01SoapPort
    private final java.lang.String CIS1036A01SoapPort_address = "http://localhost:7001/EAIWS/CIS1036A01.jws";

    public java.lang.String getCIS1036A01SoapPortAddress() {
        if (context.getOverriddingEndpointURIs() == null) {
            return CIS1036A01SoapPort_address;
        }
        String overriddingEndpoint = (String) context.getOverriddingEndpointURIs().get("CIS1036A01SoapPort");
        if (overriddingEndpoint != null) {
            return overriddingEndpoint;
        }
        else {
            return CIS1036A01SoapPort_address;
        }
    }

    private java.lang.String CIS1036A01SoapPortPortName = "CIS1036A01SoapPort";

    // The WSDD port name defaults to the port name.
    private java.lang.String CIS1036A01SoapPortWSDDPortName = "CIS1036A01SoapPort";

    public java.lang.String getCIS1036A01SoapPortWSDDPortName() {
        return CIS1036A01SoapPortWSDDPortName;
    }

    public void setCIS1036A01SoapPortWSDDPortName(java.lang.String name) {
        CIS1036A01SoapPortWSDDPortName = name;
    }

    public com.kasikornbank.eai.cis1036a01.CIS1036A01Soap getCIS1036A01SoapPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(getCIS1036A01SoapPortAddress());
        }
        catch (java.net.MalformedURLException e) {
            return null; // unlikely as URL was validated in WSDL2Java
        }
        return getCIS1036A01SoapPort(endpoint);
    }

    public com.kasikornbank.eai.cis1036a01.CIS1036A01Soap getCIS1036A01SoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        com.kasikornbank.eai.cis1036a01.CIS1036A01Soap _stub =
            (com.kasikornbank.eai.cis1036a01.CIS1036A01Soap) getStub(
                CIS1036A01SoapPortPortName,
                (String) getPort2NamespaceMap().get(CIS1036A01SoapPortPortName),
                com.kasikornbank.eai.cis1036a01.CIS1036A01Soap.class,
                "com.kasikornbank.eai.cis1036a01.CIS1036A01SoapBindingStub",
                portAddress.toString());
        if (_stub instanceof com.ibm.ws.webservices.engine.client.Stub) {
            ((com.ibm.ws.webservices.engine.client.Stub) _stub).setPortName(CIS1036A01SoapPortWSDDPortName);
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
            if (com.kasikornbank.eai.cis1036a01.CIS1036A01Soap.class.isAssignableFrom(serviceEndpointInterface)) {
                return getCIS1036A01SoapPort();
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
        if ("CIS1036A01SoapPort".equals(inputPortName)) {
            return getCIS1036A01SoapPort();
        }
        else  {
            throw new javax.xml.rpc.ServiceException();
        }
    }

    public void setPortNamePrefix(java.lang.String prefix) {
        CIS1036A01SoapPortWSDDPortName = prefix + "/" + CIS1036A01SoapPortPortName;
    }

    public javax.xml.namespace.QName getServiceName() {
        return com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://eai.kasikornbank.com/EAIWS/CIS1036A01/v1", "CIS1036A01");
    }

    private java.util.Map port2NamespaceMap = null;

    protected synchronized java.util.Map getPort2NamespaceMap() {
        if (port2NamespaceMap == null) {
            port2NamespaceMap = new java.util.HashMap();
            port2NamespaceMap.put(
               "CIS1036A01SoapPort",
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
        if  (portName.getLocalPart().equals("CIS1036A01SoapPort")) {
            return new javax.xml.rpc.Call[] {
                createCall(portName, "doService", "null"),
            };
        }
        else {
            throw new javax.xml.rpc.ServiceException("WSWS3062E: Error: portName should not be null.");
        }
    }
}
