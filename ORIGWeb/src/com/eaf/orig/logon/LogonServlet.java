package com.eaf.orig.logon;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Vector;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.ias.shared.model.ObjectM;
import com.eaf.ias.shared.model.RoleM;
import com.eaf.ias.shared.model.service.IASServiceRequest;
import com.eaf.ias.shared.model.service.IASServiceResponse;
import com.eaf.j2ee.pattern.control.util.LogoutUtil;
import com.eaf.orig.logon.dao.OrigLogOnDAO;
import com.eaf.orig.profile.model.LogonDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.constant.MConstant;
//import com.eaf.core.ulo.common.rest.RESTClient;
//import com.eaf.core.ulo.common.rest.RESTResponse;
//import com.eaf.ias.shared.model.helper.IASServiceResponseMapper;

//@WebServlet(value="/LogonServlet")
public class LogonServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(LogonServlet.class);	
	String IAS_SERVICE_AUTHENTICATION_URL = SystemConfig.getProperty("IAS_SERVICE_AUTHENTICATION_URL");
	String CHECK_LOGON_FLAG = SystemConfig.getProperty("CHECK_LOGON_FLAG");
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");		
		logger.debug("userName >> "+userName);
//		logger.debug("password >> "+password);
		initLogon(userName, password, request);
		ArrayList<String> errorLogon = new ArrayList<String>();
		try{
			errorLogon = getErrorLogon(userName,password,request);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			errorLogon.add(LogonEngine.ERROR_PLEASE_CONTRACT_ADMIN);
		}
		logger.info("errorLogon >> "+errorLogon);
		if(Util.empty(errorLogon)){
			request.getSession().setAttribute("FirstLogon",MConstant.FLAG.YES);
			request.getSession().setAttribute("userName",userName);
			request.getSession().setAttribute("password",password);
			request.getSession().setAttribute("linkUrl",LogoutUtil.getUrl(request));
			response.sendRedirect("FrontController?action=UserAction&type="+MConstant.LOGON);			
		}else{
			String errorCode = errorLogon.get(0);
			logger.error("errorCode >> "+errorCode);
			request.getSession().setAttribute("ERROR_CODE",LogonEngine.getCode(errorCode));
			LogonDataM logon = (LogonDataM)request.getSession().getAttribute("LogonData");
				logon.setAction(MConstant.LOGON);
				logon.setType(MConstant.PROCESS.FAIL);
				logon.setCreateDate(ApplicationDate.getTimestamp());
			try{
				ORIGServiceProxy.getControlLogManager().saveLog(logon);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
			response.sendRedirect("origLogon.jsp?ERROR_CODE="+LogonEngine.getCode(errorCode));
		}
	}
	private void initLogon(String userName,String password,HttpServletRequest request){
		request.getSession().removeAttribute("userName");
		request.getSession().removeAttribute("password");
		request.getSession().removeAttribute("FirstLogon");
		request.getSession().removeAttribute("ERROR_CODE");
		request.getSession().removeAttribute("ERROR_MSG");
//		#rawi comment getRemoteAddr,getRemoteHost
//		String ip = request.getRemoteAddr();
//		logger.debug("ip >> "+ip);
//		request.getSession().setAttribute("ip",ip);
		LogonDataM logon = new LogonDataM();
			logon.setUserName(userName);
//			logon.setClientName(request.getRemoteHost());
//			logon.setIp(request.getRemoteAddr());
		request.getSession().setAttribute("LogonData",logon);
		request.getSession().setAttribute("LogonUserName",userName);
		request.getSession().setAttribute("LogonPassword",password);
	}
	private ArrayList<String> getErrorLogon(String userName,String password,HttpServletRequest request){
		ArrayList<String> errorLogon = new ArrayList<>();
		if(Util.empty(userName)){
			errorLogon.add(LogonEngine.ERROR_REQUIRE_USERNAME);
		}
		if(Util.empty(password)){
			errorLogon.add(LogonEngine.ERROR_REQUIRE_PASSWORD);
		}
		if(!Util.empty(userName) && !Util.empty(password)){
			try{
				IASServiceRequest serviceRequest = new IASServiceRequest();
					serviceRequest.setUserName(userName);
					serviceRequest.setPassword(password);
					serviceRequest.setSystemName(OrigConstant.SYSTEM_NAME);				
//				RESTClient restClient = new RESTClient();
//				RESTResponse restResponse = restClient.executeRESTCall(IAS_SERVICE_AUTHENTICATION_URL,serviceRequest);
//				String authResult = IASServiceResponseMapper.authResult(restResponse.getJsonResponse());
					RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
						@Override
						protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
					        if(connection instanceof HttpsURLConnection ){
					            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
									@Override
									public boolean verify(String arg0, SSLSession arg1) {
										return true;
									}
								});
					        }
							super.prepareConnection(connection, httpMethod);
						}
					});
				ResponseEntity<IASServiceResponse> responseEntity = restTemplate.postForEntity(IAS_SERVICE_AUTHENTICATION_URL,serviceRequest,IASServiceResponse.class);
				IASServiceResponse serviceResponse = responseEntity.getBody();
				String authResult = serviceResponse.getAuthResult();
				logger.info("authResult ="+authResult);
				if(authResult.indexOf("Logon Fail") == -1){			 
//					Vector<RoleM> vRoles = IASServiceResponseMapper.getRole(restResponse.getJsonResponse());
//					Vector<ObjectM> vObject = IASServiceResponseMapper.getObject(restResponse.getJsonResponse());
					Vector<RoleM> vRoles = serviceResponse.getRoles();
					Vector<ObjectM> vObject = serviceResponse.getObjects();
					if (vRoles == null || vRoles.size() == 0 || vObject == null || vObject.size() == 0){					
						errorLogon.add(LogonEngine.ERROR_USER_CONFIGURATION_NOT_COMPLETED);
					}		
					logger.info("CHECK_LOGON_FLAG >> "+CHECK_LOGON_FLAG);
					OrigLogOnDAO logOnDAO = ModuleFactory.getOrigLogOnDAO();								
					String LOGON_FLAG = logOnDAO.logonOrigApp(userName);
					logger.info("LOGON_FLAG >> "+LOGON_FLAG);
					if(MConstant.FLAG.NOTFOUND.equals(LOGON_FLAG)){ 
						errorLogon.add(LogonEngine.ERROR_USER_CONFIGURATION_NOT_COMPLETED);
					}else if(MConstant.FLAG.INACTIVE.equals(LOGON_FLAG)){
						errorLogon.add(LogonEngine.ERROR_INACTIVE_USER);
					}else if(MConstant.FLAG.YES.equals(LOGON_FLAG) && MConstant.FLAG.YES.equals(CHECK_LOGON_FLAG)){
						errorLogon.add(LogonEngine.ERROR_LOGON_ANOTHER_COMPUTER);
					}
				}else{
					errorLogon.add(LogonEngine.ERROR_MSG);
					request.getSession().setAttribute("ERROR_MSG",authResult);
				}
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				errorLogon.add(LogonEngine.ERROR_PLEASE_CONTRACT_ADMIN);
			}
		}
		return errorLogon;
	}
}
