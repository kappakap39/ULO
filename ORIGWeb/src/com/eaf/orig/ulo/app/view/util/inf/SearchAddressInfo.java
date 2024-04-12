package com.eaf.orig.ulo.app.view.util.inf;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.CIS0222I01ServiceProxy;
import com.eaf.service.module.model.CIS0222I01RequestDataM;
import com.eaf.service.module.model.CIS0222I01ResponseDataM;
import com.eaf.service.module.model.CISZipCodeDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class SearchAddressInfo implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(SearchAddressInfo.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		logger.debug("SearchAddressInfo..");
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.SEARCH_ADDRESS_INFO); 
		try{
			ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
			UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");
			String userId = userM.getUserName();
			String FUNCTION_ID = request.getParameter("FUNCTION");
			String SEARCH_BY_TAMBOL = SystemConstant.getConstant("SEARCH_BY_TAMBOL");
			String SEARCH_BY_PROVINCE = SystemConstant.getConstant("SEARCH_BY_PROVINCE");
			String SEARCH_TAMBOL = request.getParameter("SEARCH_TAMBOL");
			String SEARCH_AMPHUR = request.getParameter("SEARCH_AMPHUR");
			String SEARCH_PROVINCE = request.getParameter("SEARCH_PROVINCE");
			logger.debug("FUNCTION_ID >> "+FUNCTION_ID);
			logger.debug("SEARCH_TAMBOL >> "+SEARCH_TAMBOL);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
			serviceRequest.setServiceId(CIS0222I01ServiceProxy.serviceId);
			serviceRequest.setEndpointUrl(SystemConfig.getProperty("CIS0222I01_ENDPOINT_URL"));
			serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
			serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			serviceRequest.setUserId(userId);
			CIS0222I01RequestDataM CIS0222I01Request = new CIS0222I01RequestDataM(); 
			if(SEARCH_BY_TAMBOL.equals(FUNCTION_ID)){
				CIS0222I01Request.setTumbol(SEARCH_TAMBOL);		
			}else if(SEARCH_BY_PROVINCE.equals(FUNCTION_ID)){
				CIS0222I01Request.setAmphur(SEARCH_AMPHUR);
				CIS0222I01Request.setProvince(SEARCH_PROVINCE);
			}
			serviceRequest.setObjectData(CIS0222I01Request);
			ServiceCenterProxy proxy = new ServiceCenterProxy();	
			String transactionId = (String)request.getSession().getAttribute("transactionId");
			ServiceResponseDataM serviceResponse = proxy.requestService(serviceRequest,transactionId);	
			String serviceResult = serviceResponse.getStatusCode();
			logger.debug("serviceResult : "+serviceResult);			
			if(ServiceResponse.Status.SUCCESS.equals(serviceResult)){
				CIS0222I01ResponseDataM CIS0222I01Response = (CIS0222I01ResponseDataM)serviceResponse.getObjectData();
				ArrayList<CISZipCodeDataM> cisZipCodes =  CIS0222I01Response.getCisZipCode();
				return responseData.success(new Gson().toJson(cisZipCodes));
			}else{
				return responseData.error(serviceResponse.getErrorInfo());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			return responseData.error(e);
		}
	}
}
