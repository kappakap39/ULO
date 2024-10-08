package com.kasikornbank.eai.cbs1215i01;

public class CBS1215I01SoapProxy implements com.kasikornbank.eai.cbs1215i01.CBS1215I01Soap {
  private boolean _useJNDI = true;
  private boolean _useJNDIOnly = false;
  private String _endpoint = null;
  private com.kasikornbank.eai.cbs1215i01.CBS1215I01Soap __cBS1215I01Soap = null;
  
  public CBS1215I01SoapProxy() {
    _initCBS1215I01SoapProxy();
  }
  
  private void _initCBS1215I01SoapProxy() {
  
    if (_useJNDI || _useJNDIOnly) {
      try {
        javax.naming.InitialContext ctx = new javax.naming.InitialContext();
        __cBS1215I01Soap = ((com.kasikornbank.eai.cbs1215i01.CBS1215I01_Service)ctx.lookup("java:comp/env/service/CBS1215I01")).getCBS1215I01SoapPort();
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
    if (__cBS1215I01Soap == null && !_useJNDIOnly) {
      try {
        __cBS1215I01Soap = (new com.kasikornbank.eai.cbs1215i01.CBS1215I01_ServiceLocator()).getCBS1215I01SoapPort();
        
      }
      catch (javax.xml.rpc.ServiceException serviceException) {
        if ("true".equalsIgnoreCase(System.getProperty("DEBUG_PROXY"))) {
          System.out.println("Unable to obtain port: javax.xml.rpc.ServiceException: " + serviceException.getMessage());
          serviceException.printStackTrace(System.out);
        }
      }
    }
    if (__cBS1215I01Soap != null) {
      if (_endpoint != null)
        ((javax.xml.rpc.Stub)__cBS1215I01Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
      else
        _endpoint = (String)((javax.xml.rpc.Stub)__cBS1215I01Soap)._getProperty("javax.xml.rpc.service.endpoint.address");
    }
    
  }
  
  
  public void useJNDI(boolean useJNDI) {
    _useJNDI = useJNDI;
    __cBS1215I01Soap = null;
    
  }
  
  public void useJNDIOnly(boolean useJNDIOnly) {
    _useJNDIOnly = useJNDIOnly;
    __cBS1215I01Soap = null;
    
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (__cBS1215I01Soap == null)
      _initCBS1215I01SoapProxy();
    if (__cBS1215I01Soap != null)
      ((javax.xml.rpc.Stub)__cBS1215I01Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.kasikornbank.eai.cbs1215i01.CBS1215I01Response doService(com.kasikornbank.eai.cbs1215i01.CBS1215I01_Type CBS1215I01) throws java.rmi.RemoteException{
    if (__cBS1215I01Soap == null)
      _initCBS1215I01SoapProxy();
    return __cBS1215I01Soap.doService(CBS1215I01);
  }
  
  
  public com.kasikornbank.eai.cbs1215i01.CBS1215I01Soap getCBS1215I01Soap() {
    if (__cBS1215I01Soap == null)
      _initCBS1215I01SoapProxy();
    return __cBS1215I01Soap;
  }
  
}