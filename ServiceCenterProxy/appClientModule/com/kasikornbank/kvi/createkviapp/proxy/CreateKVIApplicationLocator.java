/**
 * CreateKVIApplicationLocator.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.createkviapp.proxy;

public class CreateKVIApplicationLocator extends com.ibm.ws.webservices.multiprotocol.AgnosticService implements com.ibm.ws.webservices.multiprotocol.GeneratedService, com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplication {

    public CreateKVIApplicationLocator() {
        super(com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
           "http://tempuri.org/",
           "CreateKVIApplication"));

        context.setLocatorName("com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationLocator");
    }

    public CreateKVIApplicationLocator(com.ibm.ws.webservices.multiprotocol.ServiceContext ctx) {
        super(ctx);
        context.setLocatorName("com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationLocator");
    }

    // Use to get a proxy class for basicHttpBinding_ICreateKVIApplication
    private final java.lang.String basicHttpBinding_ICreateKVIApplication_address = "http://localhost:8081/CreateKVIApplication.svc";

    public java.lang.String getBasicHttpBinding_ICreateKVIApplicationAddress() {
        if (context.getOverriddingEndpointURIs() == null) {
            return basicHttpBinding_ICreateKVIApplication_address;
        }
        String overriddingEndpoint = (String) context.getOverriddingEndpointURIs().get("BasicHttpBinding_ICreateKVIApplication");
        if (overriddingEndpoint != null) {
            return overriddingEndpoint;
        }
        else {
            return basicHttpBinding_ICreateKVIApplication_address;
        }
    }

    private java.lang.String basicHttpBinding_ICreateKVIApplicationPortName = "BasicHttpBinding_ICreateKVIApplication";

    // The WSDD port name defaults to the port name.
    private java.lang.String basicHttpBinding_ICreateKVIApplicationWSDDPortName = "BasicHttpBinding_ICreateKVIApplication";

    public java.lang.String getBasicHttpBinding_ICreateKVIApplicationWSDDPortName() {
        return basicHttpBinding_ICreateKVIApplicationWSDDPortName;
    }

    public void setBasicHttpBinding_ICreateKVIApplicationWSDDPortName(java.lang.String name) {
        basicHttpBinding_ICreateKVIApplicationWSDDPortName = name;
    }

    public com.kasikornbank.kvi.createkviapp.proxy.ICreateKVIApplication getBasicHttpBinding_ICreateKVIApplication() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(getBasicHttpBinding_ICreateKVIApplicationAddress());
        }
        catch (java.net.MalformedURLException e) {
            return null; // unlikely as URL was validated in WSDL2Java
        }
        return getBasicHttpBinding_ICreateKVIApplication(endpoint);
    }

    public com.kasikornbank.kvi.createkviapp.proxy.ICreateKVIApplication getBasicHttpBinding_ICreateKVIApplication(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        com.kasikornbank.kvi.createkviapp.proxy.ICreateKVIApplication _stub =
            (com.kasikornbank.kvi.createkviapp.proxy.ICreateKVIApplication) getStub(
                basicHttpBinding_ICreateKVIApplicationPortName,
                (String) getPort2NamespaceMap().get(basicHttpBinding_ICreateKVIApplicationPortName),
                com.kasikornbank.kvi.createkviapp.proxy.ICreateKVIApplication.class,
                "com.kasikornbank.kvi.createkviapp.proxy.BasicHttpBinding_ICreateKVIApplicationStub",
                portAddress.toString());
        if (_stub instanceof com.ibm.ws.webservices.engine.client.Stub) {
            ((com.ibm.ws.webservices.engine.client.Stub) _stub).setPortName(basicHttpBinding_ICreateKVIApplicationWSDDPortName);
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
            if (com.kasikornbank.kvi.createkviapp.proxy.ICreateKVIApplication.class.isAssignableFrom(serviceEndpointInterface)) {
                return getBasicHttpBinding_ICreateKVIApplication();
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
        if ("BasicHttpBinding_ICreateKVIApplication".equals(inputPortName)) {
            return getBasicHttpBinding_ICreateKVIApplication();
        }
        else  {
            throw new javax.xml.rpc.ServiceException();
        }
    }

    public void setPortNamePrefix(java.lang.String prefix) {
        basicHttpBinding_ICreateKVIApplicationWSDDPortName = prefix + "/" + basicHttpBinding_ICreateKVIApplicationPortName;
    }

    public javax.xml.namespace.QName getServiceName() {
        return com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "CreateKVIApplication");
    }

    private java.util.Map port2NamespaceMap = null;

    protected synchronized java.util.Map getPort2NamespaceMap() {
        if (port2NamespaceMap == null) {
            port2NamespaceMap = new java.util.HashMap();
            port2NamespaceMap.put(
               "BasicHttpBinding_ICreateKVIApplication",
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
        if  (portName.getLocalPart().equals("BasicHttpBinding_ICreateKVIApplication")) {
            return new javax.xml.rpc.Call[] {
                createCall(portName, "ServiceRequest", "null"),
            };
        }
        else {
            throw new javax.xml.rpc.ServiceException("WSWS3062E: Error: portName should not be null.");
        }
    }
}
