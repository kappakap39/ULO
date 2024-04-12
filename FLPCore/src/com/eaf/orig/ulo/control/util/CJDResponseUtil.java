package com.eaf.orig.ulo.control.util;

import org.apache.log4j.Logger;

import com.ava.flp.cjd.model.CJDResponse;
import com.ava.flp.cjd.model.CJDResponseServiceProxyRequest;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.CJDResponseServiceProxy;
import com.eaf.service.rest.model.ServiceResponse;

public class CJDResponseUtil {
	private static transient Logger logger = Logger.getLogger(CJDResponseUtil.class);
	
	public static ProcessActionResponse requestCJDResponse(CJDResponseServiceProxyRequest cjdRequest, String userId){
		String URL = SystemConfig.getProperty("CJD_RESPONSE");
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			ApplicationGroupDataM applicationGroup = cjdRequest.getApplicationGroup();
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CJDResponseServiceProxy.serviceId);
				serviceRequest.setUserId(userId);
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setObjectData(cjdRequest);
				
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
			logger.debug("request cjd Result response  : " + serivceResponse.getStatusCode());
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
				CJDResponse cjdResponse =(CJDResponse)serivceResponse.getObjectData();
				if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
					processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
				}else{
					processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR+":when  get object data");
				}
			}else{
				processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR);
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
	
	public static boolean mandatoryFlag(PersonalInfoDataM personalInfo,String docCode){
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();		
		if(null != verificationResult){
			return verificationResult.containRequiredDocMandatoryFlag(docCode,MConstant.FLAG.YES);
		}
		return false;
	}
}
