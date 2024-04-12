package com.kasikornbank.kvi.createkviapp.proxy;

public class ICreateKVIApplicationProxy implements com.kasikornbank.kvi.createkviapp.proxy.ICreateKVIApplication {
  private boolean _useJNDI = true;
  private boolean _useJNDIOnly = false;
  private String _endpoint = null;
  private com.kasikornbank.kvi.createkviapp.proxy.ICreateKVIApplication __iCreateKVIApplication = null;
  
  public ICreateKVIApplicationProxy() {
    _initICreateKVIApplicationProxy();
  }
  
  private void _initICreateKVIApplicationProxy() {
  
    if (_useJNDI || _useJNDIOnly) {
      try {
        javax.naming.InitialContext ctx = new javax.naming.InitialContext();
        __iCreateKVIApplication = ((com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplication)ctx.lookup("java:comp/env/service/CreateKVIApplication")).getBasicHttpBinding_ICreateKVIApplication();
      }
      catch (javax.naming.NamingException namingException) {
        if ("true".equalsIgnoreCase(System.getProperty("DEBUG_PROXY"))) {
          System.out.println("JNDI lookup failure: javax.naming.NamingException: " + namingException.getMessage());
          namingException.printStackTrace(System.out);
        }
      }
      catch (javax.xml.rpc.ServiceException serviceException) {
        if ("true".equalsIgnoreCase(System.getProperty("DEBUG_PROXY"))) {
          System.out.println("Unable to obtain port: javax.xml.rpc.ServiceException: " + serviceException.getMessage());
          serviceException.printStackTrace(System.out);
        }
      }
    }
    if (__iCreateKVIApplication == null && !_useJNDIOnly) {
      try {
        __iCreateKVIApplication = (new com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationLocator()).getBasicHttpBinding_ICreateKVIApplication();
        
      }
      catch (javax.xml.rpc.ServiceException serviceException) {
        if ("true".equalsIgnoreCase(System.getProperty("DEBUG_PROXY"))) {
          System.out.println("Unable to obtain port: javax.xml.rpc.ServiceException: " + serviceException.getMessage());
          serviceException.printStackTrace(System.out);
        }
      }
    }
    if (__iCreateKVIApplication != null) {
      if (_endpoint != null)
        ((javax.xml.rpc.Stub)__iCreateKVIApplication)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
      else
        _endpoint = (String)((javax.xml.rpc.Stub)__iCreateKVIApplication)._getProperty("javax.xml.rpc.service.endpoint.address");
    }
    
  }
  
  
  public void useJNDI(boolean useJNDI) {
    _useJNDI = useJNDI;
    __iCreateKVIApplication = null;
    
  }
  
  public void useJNDIOnly(boolean useJNDIOnly) {
    _useJNDIOnly = useJNDIOnly;
    __iCreateKVIApplication = null;
    
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (__iCreateKVIApplication == null)
      _initICreateKVIApplicationProxy();
    if (__iCreateKVIApplication != null)
      ((javax.xml.rpc.Stub)__iCreateKVIApplication)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationResponse serviceRequest(com.kasikornbank.kvi.createkviapp.proxy.CreateKVIApplicationRequest objCreateKVIApplicationRequest) throws java.rmi.RemoteException{
    if (__iCreateKVIApplication == null)
      _initICreateKVIApplicationProxy();
    return __iCreateKVIApplication.serviceRequest(objCreateKVIApplicationRequest);
  }
  
  
  public com.kasikornbank.kvi.createkviapp.proxy.ICreateKVIApplication getICreateKVIApplication() {
    if (__iCreateKVIApplication == null)
      _initICreateKVIApplicationProxy();
    return __iCreateKVIApplication;
  }
  
}