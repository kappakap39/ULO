package com.kasikornbank.kvi.editkviapp.proxy;

public class IEditKVIApplicationProxy implements com.kasikornbank.kvi.editkviapp.proxy.IEditKVIApplication {
  private boolean _useJNDI = true;
  private boolean _useJNDIOnly = false;
  private String _endpoint = null;
  private com.kasikornbank.kvi.editkviapp.proxy.IEditKVIApplication __iEditKVIApplication = null;
  
  public IEditKVIApplicationProxy() {
    _initIEditKVIApplicationProxy();
  }
  
  private void _initIEditKVIApplicationProxy() {
  
    if (_useJNDI || _useJNDIOnly) {
      try {
        javax.naming.InitialContext ctx = new javax.naming.InitialContext();
        __iEditKVIApplication = ((com.kasikornbank.kvi.editkviapp.proxy.EditKVIApplication)ctx.lookup("java:comp/env/service/EditKVIApplication")).getBasicHttpBinding_IEditKVIApplication();
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
    if (__iEditKVIApplication == null && !_useJNDIOnly) {
      try {
        __iEditKVIApplication = (new com.kasikornbank.kvi.editkviapp.proxy.EditKVIApplicationLocator()).getBasicHttpBinding_IEditKVIApplication();
        
      }
      catch (javax.xml.rpc.ServiceException serviceException) {
        if ("true".equalsIgnoreCase(System.getProperty("DEBUG_PROXY"))) {
          System.out.println("Unable to obtain port: javax.xml.rpc.ServiceException: " + serviceException.getMessage());
          serviceException.printStackTrace(System.out);
        }
      }
    }
    if (__iEditKVIApplication != null) {
      if (_endpoint != null)
        ((javax.xml.rpc.Stub)__iEditKVIApplication)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
      else
        _endpoint = (String)((javax.xml.rpc.Stub)__iEditKVIApplication)._getProperty("javax.xml.rpc.service.endpoint.address");
    }
    
  }
  
  
  public void useJNDI(boolean useJNDI) {
    _useJNDI = useJNDI;
    __iEditKVIApplication = null;
    
  }
  
  public void useJNDIOnly(boolean useJNDIOnly) {
    _useJNDIOnly = useJNDIOnly;
    __iEditKVIApplication = null;
    
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (__iEditKVIApplication == null)
      _initIEditKVIApplicationProxy();
    if (__iEditKVIApplication != null)
      ((javax.xml.rpc.Stub)__iEditKVIApplication)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.kasikornbank.kvi.editkviapp.proxy.EditKVIApplicationResponse serviceRequest(com.kasikornbank.kvi.editkviapp.proxy.EditKVIApplicationRequest objEditKVIApplicationRequest) throws java.rmi.RemoteException{
    if (__iEditKVIApplication == null)
      _initIEditKVIApplicationProxy();
    return __iEditKVIApplication.serviceRequest(objEditKVIApplicationRequest);
  }
  
  
  public com.kasikornbank.kvi.editkviapp.proxy.IEditKVIApplication getIEditKVIApplication() {
    if (__iEditKVIApplication == null)
      _initIEditKVIApplicationProxy();
    return __iEditKVIApplication;
  }
  
}