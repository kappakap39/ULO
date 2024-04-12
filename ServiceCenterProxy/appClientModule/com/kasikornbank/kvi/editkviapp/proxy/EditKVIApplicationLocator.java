/**
 * EditKVIApplicationLocator.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.kasikornbank.kvi.editkviapp.proxy;

public class EditKVIApplicationLocator extends com.ibm.ws.webservices.multiprotocol.AgnosticService implements com.ibm.ws.webservices.multiprotocol.GeneratedService, com.kasikornbank.kvi.editkviapp.proxy.EditKVIApplication {

    public EditKVIApplicationLocator() {
        super(com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
           "http://tempuri.org/",
           "EditKVIApplication"));

        context.setLocatorName("com.kasikornbank.kvi.editkviapp.proxy.EditKVIApplicationLocator");
    }

    public EditKVIApplicationLocator(com.ibm.ws.webservices.multiprotocol.ServiceContext ctx) {
        super(ctx);
        context.setLocatorName("com.kasikornbank.kvi.editkviapp.proxy.EditKVIApplicationLocator");
    }

    // Use to get a proxy class for basicHttpBinding_IEditKVIApplication
    private final java.lang.String basicHttpBinding_IEditKVIApplication_address = "http://localhost:38787/KVIWebServices/EditKVIApplication.svc";

    public java.lang.String getBasicHttpBinding_IEditKVIApplicationAddress() {
        if (context.getOverriddingEndpointURIs() == null) {
            return basicHttpBinding_IEditKVIApplication_address;
        }
        String overriddingEndpoint = (String) context.getOverriddingEndpointURIs().get("BasicHttpBinding_IEditKVIApplication");
        if (overriddingEndpoint != null) {
            return overriddingEndpoint;
        }
        else {
            return basicHttpBinding_IEditKVIApplication_address;
        }
    }

    private java.lang.String basicHttpBinding_IEditKVIApplicationPortName = "BasicHttpBinding_IEditKVIApplication";

    // The WSDD port name defaults to the port name.
    private java.lang.String basicHttpBinding_IEditKVIApplicationWSDDPortName = "BasicHttpBinding_IEditKVIApplication";

    public java.lang.String getBasicHttpBinding_IEditKVIApplicationWSDDPortName() {
        return basicHttpBinding_IEditKVIApplicationWSDDPortName;
    }

    public void setBasicHttpBinding_IEditKVIApplicationWSDDPortName(java.lang.String name) {
        basicHttpBinding_IEditKVIApplicationWSDDPortName = name;
    }

    public com.kasikornbank.kvi.editkviapp.proxy.IEditKVIApplication getBasicHttpBinding_IEditKVIApplication() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(getBasicHttpBinding_IEditKVIApplicationAddress());
        }
        catch (java.net.MalformedURLException e) {
            return null; // unlikely as URL was validated in WSDL2Java
        }
        return getBasicHttpBinding_IEditKVIApplication(endpoint);
    }

    public com.kasikornbank.kvi.editkviapp.proxy.IEditKVIApplication getBasicHttpBinding_IEditKVIApplication(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        com.kasikornbank.kvi.editkviapp.proxy.IEditKVIApplication _stub =
            (com.kasikornbank.kvi.editkviapp.proxy.IEditKVIApplication) getStub(
                basicHttpBinding_IEditKVIApplicationPortName,
                (String) getPort2NamespaceMap().get(basicHttpBinding_IEditKVIApplicationPortName),
                com.kasikornbank.kvi.editkviapp.proxy.IEditKVIApplication.class,
                "com.kasikornbank.kvi.editkviapp.proxy.BasicHttpBinding_IEditKVIApplicationStub",
                portAddress.toString());
        if (_stub instanceof com.ibm.ws.webservices.engine.client.Stub) {
            ((com.ibm.ws.webservices.engine.client.Stub) _stub).setPortName(basicHttpBinding_IEditKVIApplicationWSDDPortName);
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
            if (com.kasikornbank.kvi.editkviapp.proxy.IEditKVIApplication.class.isAssignableFrom(serviceEndpointInterface)) {
                return getBasicHttpBinding_IEditKVIApplication();
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
        if ("BasicHttpBinding_IEditKVIApplication".equals(inputPortName)) {
            return getBasicHttpBinding_IEditKVIApplication();
        }
        else  {
            throw new javax.xml.rpc.ServiceException();
        }
    }

    public void setPortNamePrefix(java.lang.String prefix) {
        basicHttpBinding_IEditKVIApplicationWSDDPortName = prefix + "/" + basicHttpBinding_IEditKVIApplicationPortName;
    }

    public javax.xml.namespace.QName getServiceName() {
        return com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://tempuri.org/", "EditKVIApplication");
    }

    private java.util.Map port2NamespaceMap = null;

    protected synchronized java.util.Map getPort2NamespaceMap() {
        if (port2NamespaceMap == null) {
            port2NamespaceMap = new java.util.HashMap();
            port2NamespaceMap.put(
               "BasicHttpBinding_IEditKVIApplication",
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
        if  (portName.getLocalPart().equals("BasicHttpBinding_IEditKVIApplication")) {
            return new javax.xml.rpc.Call[] {
                createCall(portName, "ServiceRequest", "null"),
            };
        }
        else {
            throw new javax.xml.rpc.ServiceException("WSWS3062E: Error: portName should not be null.");
        }
    }
}
