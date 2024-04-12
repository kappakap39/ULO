package com.kasikornbank.eai.cis1036a01;

public class CIS1036A01SoapProxy implements com.kasikornbank.eai.cis1036a01.CIS1036A01Soap {
  private boolean _useJNDI = true;
  private boolean _useJNDIOnly = false;
  private String _endpoint = null;
  private com.kasikornbank.eai.cis1036a01.CIS1036A01Soap __cIS1036A01Soap = null;
  
  public CIS1036A01SoapProxy() {
    _initCIS1036A01SoapProxy();
  }
  
  private void _initCIS1036A01SoapProxy() {
  
    if (_useJNDI || _useJNDIOnly) {
      try {
        javax.naming.InitialContext ctx = new javax.naming.InitialContext();
        __cIS1036A01Soap = ((com.kasikornbank.eai.cis1036a01.CIS1036A01_Service)ctx.lookup("java:comp/env/service/CIS1036A01")).getCIS1036A01SoapPort();
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
    if (__cIS1036A01Soap == null && !_useJNDIOnly) {
      try {
        __cIS1036A01Soap = (new com.kasikornbank.eai.cis1036a01.CIS1036A01_ServiceLocator()).getCIS1036A01SoapPort();
        
      }
      catch (javax.xml.rpc.ServiceException serviceException) {
        if ("true".equalsIgnoreCase(System.getProperty("DEBUG_PROXY"))) {
          System.out.println("Unable to obtain port: javax.xml.rpc.ServiceException: " + serviceException.getMessage());
          serviceException.printStackTrace(System.out);
        }
      }
    }
    if (__cIS1036A01Soap != null) {
      if (_endpoint != null)
        ((javax.xml.rpc.Stub)__cIS1036A01Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
      else
        _endpoint = (String)((javax.xml.rpc.Stub)__cIS1036A01Soap)._getProperty("javax.xml.rpc.service.endpoint.address");
    }
    
  }
  
  
  public void useJNDI(boolean useJNDI) {
    _useJNDI = useJNDI;
    __cIS1036A01Soap = null;
    
  }
  
  public void useJNDIOnly(boolean useJNDIOnly) {
    _useJNDIOnly = useJNDIOnly;
    __cIS1036A01Soap = null;
    
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (__cIS1036A01Soap == null)
      _initCIS1036A01SoapProxy();
    if (__cIS1036A01Soap != null)
      ((javax.xml.rpc.Stub)__cIS1036A01Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.kasikornbank.eai.cis1036a01.CIS1036A01Response doService(com.kasikornbank.eai.cis1036a01.CIS1036A01_Type CIS1036A01) throws java.rmi.RemoteException{
    if (__cIS1036A01Soap == null)
      _initCIS1036A01SoapProxy();
    return __cIS1036A01Soap.doService(CIS1036A01);
  }
  
  
  public com.kasikornbank.eai.cis1036a01.CIS1036A01Soap getCIS1036A01Soap() {
    if (__cIS1036A01Soap == null)
      _initCIS1036A01SoapProxy();
    return __cIS1036A01Soap;
  }
  
}