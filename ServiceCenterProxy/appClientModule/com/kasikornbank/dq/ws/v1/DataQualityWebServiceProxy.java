package com.kasikornbank.dq.ws.v1;

public class DataQualityWebServiceProxy implements com.kasikornbank.dq.ws.v1.DataQualityWebService {
  private boolean _useJNDI = true;
  private boolean _useJNDIOnly = false;
  private String _endpoint = null;
  private com.kasikornbank.dq.ws.v1.DataQualityWebService __dataQualityWebService = null;
  
  public DataQualityWebServiceProxy() {
    _initDataQualityWebServiceProxy();
  }
  
  private void _initDataQualityWebServiceProxy() {
  
    if (_useJNDI || _useJNDIOnly) {
      try {
        javax.naming.InitialContext ctx = new javax.naming.InitialContext();
        __dataQualityWebService = ((com.kasikornbank.dq.ws.DataQualityWebServiceService)ctx.lookup("java:comp/env/service/DataQualityWebServiceService")).getDataQualityWebServicePort();
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
    if (__dataQualityWebService == null && !_useJNDIOnly) {
      try {
        __dataQualityWebService = (new com.kasikornbank.dq.ws.DataQualityWebServiceServiceLocator()).getDataQualityWebServicePort();
        
      }
      catch (javax.xml.rpc.ServiceException serviceException) {
        if ("true".equalsIgnoreCase(System.getProperty("DEBUG_PROXY"))) {
          System.out.println("Unable to obtain port: javax.xml.rpc.ServiceException: " + serviceException.getMessage());
          serviceException.printStackTrace(System.out);
        }
      }
    }
    if (__dataQualityWebService != null) {
      if (_endpoint != null)
        ((javax.xml.rpc.Stub)__dataQualityWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
      else
        _endpoint = (String)((javax.xml.rpc.Stub)__dataQualityWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
    }
    
  }
  
  
  public void useJNDI(boolean useJNDI) {
    _useJNDI = useJNDI;
    __dataQualityWebService = null;
    
  }
  
  public void useJNDIOnly(boolean useJNDIOnly) {
    _useJNDIOnly = useJNDIOnly;
    __dataQualityWebService = null;
    
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (__dataQualityWebService == null)
      _initDataQualityWebServiceProxy();
    if (__dataQualityWebService != null)
      ((javax.xml.rpc.Stub)__dataQualityWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.kasikornbank.dq.ws.v1.DataQualityResponse validate(com.kasikornbank.dq.ws.v1.DataQualityRequest dataQualityRequest) throws java.rmi.RemoteException{
    if (__dataQualityWebService == null)
      _initDataQualityWebServiceProxy();
    return __dataQualityWebService.validate(dataQualityRequest);
  }
  
  
  public com.kasikornbank.dq.ws.v1.DataQualityWebService getDataQualityWebService() {
    if (__dataQualityWebService == null)
      _initDataQualityWebServiceProxy();
    return __dataQualityWebService;
  }
  
}