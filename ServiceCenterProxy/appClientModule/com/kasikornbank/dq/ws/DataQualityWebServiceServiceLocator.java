/**
 * DataQualityWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.dq.ws;

public class DataQualityWebServiceServiceLocator extends com.ibm.ws.webservices.multiprotocol.AgnosticService implements com.ibm.ws.webservices.multiprotocol.GeneratedService, com.kasikornbank.dq.ws.DataQualityWebServiceService {

    public DataQualityWebServiceServiceLocator() {
        super(com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
           "http://ws.dq.kasikornbank.com",
           "DataQualityWebServiceService"));

        context.setLocatorName("com.kasikornbank.dq.ws.DataQualityWebServiceServiceLocator");
    }

    public DataQualityWebServiceServiceLocator(com.ibm.ws.webservices.multiprotocol.ServiceContext ctx) {
        super(ctx);
        context.setLocatorName("com.kasikornbank.dq.ws.DataQualityWebServiceServiceLocator");
    }

    // Use to get a proxy class for dataQualityWebServicePort
    private final java.lang.String dataQualityWebServicePort_address = "https://172.30.160.117:9443/cdq-ws/DataQualityService";

    public java.lang.String getDataQualityWebServicePortAddress() {
        if (context.getOverriddingEndpointURIs() == null) {
            return dataQualityWebServicePort_address;
        }
        String overriddingEndpoint = (String) context.getOverriddingEndpointURIs().get("DataQualityWebServicePort");
        if (overriddingEndpoint != null) {
            return overriddingEndpoint;
        }
        else {
            return dataQualityWebServicePort_address;
        }
    }

    private java.lang.String dataQualityWebServicePortPortName = "DataQualityWebServicePort";

    // The WSDD port name defaults to the port name.
    private java.lang.String dataQualityWebServicePortWSDDPortName = "DataQualityWebServicePort";

    public java.lang.String getDataQualityWebServicePortWSDDPortName() {
        return dataQualityWebServicePortWSDDPortName;
    }

    public void setDataQualityWebServicePortWSDDPortName(java.lang.String name) {
        dataQualityWebServicePortWSDDPortName = name;
    }

    public com.kasikornbank.dq.ws.v1.DataQualityWebService getDataQualityWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(getDataQualityWebServicePortAddress());
        }
        catch (java.net.MalformedURLException e) {
            return null; // unlikely as URL was validated in WSDL2Java
        }
        return getDataQualityWebServicePort(endpoint);
    }

    public com.kasikornbank.dq.ws.v1.DataQualityWebService getDataQualityWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        com.kasikornbank.dq.ws.v1.DataQualityWebService _stub =
            (com.kasikornbank.dq.ws.v1.DataQualityWebService) getStub(
                dataQualityWebServicePortPortName,
                (String) getPort2NamespaceMap().get(dataQualityWebServicePortPortName),
                com.kasikornbank.dq.ws.v1.DataQualityWebService.class,
                "com.kasikornbank.dq.ws.DataQualityWebServicePortBindingStub",
                portAddress.toString());
        if (_stub instanceof com.ibm.ws.webservices.engine.client.Stub) {
            ((com.ibm.ws.webservices.engine.client.Stub) _stub).setPortName(dataQualityWebServicePortWSDDPortName);
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
            if (com.kasikornbank.dq.ws.v1.DataQualityWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                return getDataQualityWebServicePort();
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
        if ("DataQualityWebServicePort".equals(inputPortName)) {
            return getDataQualityWebServicePort();
        }
        else  {
            throw new javax.xml.rpc.ServiceException();
        }
    }

    public void setPortNamePrefix(java.lang.String prefix) {
        dataQualityWebServicePortWSDDPortName = prefix + "/" + dataQualityWebServicePortPortName;
    }

    public javax.xml.namespace.QName getServiceName() {
        return com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://ws.dq.kasikornbank.com", "DataQualityWebServiceService");
    }

    private java.util.Map port2NamespaceMap = null;

    protected synchronized java.util.Map getPort2NamespaceMap() {
        if (port2NamespaceMap == null) {
            port2NamespaceMap = new java.util.HashMap();
            port2NamespaceMap.put(
               "DataQualityWebServicePort",
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
        if  (portName.getLocalPart().equals("DataQualityWebServicePort")) {
            return new javax.xml.rpc.Call[] {
                createCall(portName, "validate", "null"),
            };
        }
        else {
            throw new javax.xml.rpc.ServiceException("WSWS3062E: Error: portName should not be null.");
        }
    }
}
