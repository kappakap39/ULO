package com.kasikornbank.eai.cis0314i01;

public class CIS0314I01SoapProxy implements com.kasikornbank.eai.cis0314i01.CIS0314I01Soap {
  private boolean _useJNDI = true;
  private boolean _useJNDIOnly = false;
  private String _endpoint = null;
  private com.kasikornbank.eai.cis0314i01.CIS0314I01Soap __cIS0314I01Soap = null;
  
  public CIS0314I01SoapProxy() {
    _initCIS0314I01SoapProxy();
  }
  
  private void _initCIS0314I01SoapProxy() {
  
    if (_useJNDI || _useJNDIOnly) {
      try {
        javax.naming.InitialContext ctx = new javax.naming.InitialContext();
        __cIS0314I01Soap = ((com.kasikornbank.eai.cis0314i01.CIS0314I01_Service)ctx.lookup("java:comp/env/service/CIS0314I01")).getCIS0314I01SoapPort();
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
    if (__cIS0314I01Soap == null && !_useJNDIOnly) {
      try {
        __cIS0314I01Soap = (new com.kasikornbank.eai.cis0314i01.CIS0314I01_ServiceLocator()).getCIS0314I01SoapPort();
        
      }
      catch (javax.xml.rpc.ServiceException serviceException) {
        if ("true".equalsIgnoreCase(System.getProperty("DEBUG_PROXY"))) {
          System.out.println("Unable to obtain port: javax.xml.rpc.ServiceException: " + serviceException.getMessage());
          serviceException.printStackTrace(System.out);
        }
      }
    }
    if (__cIS0314I01Soap != null) {
      if (_endpoint != null)
        ((javax.xml.rpc.Stub)__cIS0314I01Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
      else
        _endpoint = (String)((javax.xml.rpc.Stub)__cIS0314I01Soap)._getProperty("javax.xml.rpc.service.endpoint.address");
    }
    
  }
  
  
  public void useJNDI(boolean useJNDI) {
    _useJNDI = useJNDI;
    __cIS0314I01Soap = null;
    
  }
  
  public void useJNDIOnly(boolean useJNDIOnly) {
    _useJNDIOnly = useJNDIOnly;
    __cIS0314I01Soap = null;
    
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (__cIS0314I01Soap == null)
      _initCIS0314I01SoapProxy();
    if (__cIS0314I01Soap != null)
      ((javax.xml.rpc.Stub)__cIS0314I01Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.kasikornbank.eai.cis0314i01.CIS0314I01Response doService(com.kasikornbank.eai.cis0314i01.CIS0314I01_Type CIS0314I01) throws java.rmi.RemoteException{
    if (__cIS0314I01Soap == null)
      _initCIS0314I01SoapProxy();
    return __cIS0314I01Soap.doService(CIS0314I01);
  }
  
  
  public com.kasikornbank.eai.cis0314i01.CIS0314I01Soap getCIS0314I01Soap() {
    if (__cIS0314I01Soap == null)
      _initCIS0314I01SoapProxy();
    return __cIS0314I01Soap;
  }
  
}