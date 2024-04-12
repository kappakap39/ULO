package com.eaf.orig.ulo.app.view.form.business;

import org.apache.log4j.Logger;

import com.ava.flp.cjd.model.CJDResponse;
import com.ava.flp.cjd.model.CJDResponseServiceProxyRequest;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.pega.PegaApplicationUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.CJDResponseUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.pega.ErrorDataM;
import com.eaf.orig.ulo.model.pega.FollowUpResponse;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.CJDResponseServiceProxy;

public class DVSaveFollowUpTopegaApplication extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(DVSaveFollowUpTopegaApplication.class);
	String CALL_OPERATOR_FOLLOW = SystemConstant.getConstant("CALL_OPERATOR_FOLLOW");
	String ERROR_CODE_PEGA_INVALID_STATE = SystemConstant.getConstant("ERROR_CODE_PEGA_INVALID_STATE");
	@Override
	public Object processAction() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			logger.debug("DVSaveFollowUpTopegaApplication..");	
			applicationGroup.setAlreadyFollowFlag(this.getAlreadyFollowFlag(applicationGroup));
			FollowUpResponse followUpResponse = null;
			boolean isCJD = ApplicationUtil.cjd(applicationGroup.getSource());
			//process cjd response
			String userId = SystemConstant.getConstant("SYSTEM_USER");
			CJDResponseServiceProxyRequest cjdRequest = new CJDResponseServiceProxyRequest();
			cjdRequest.setApplicationGroup(applicationGroup);
			cjdRequest.setCompleteFlag("N");
			if(!isCJD){
				followUpResponse = PegaApplicationUtil.sendToPega(applicationGroup, userM, false);
				logger.debug("followUpResponse.getStatusCode : "+followUpResponse.getStatusCode());
				if(ServiceResponse.Status.SUCCESS.equals(followUpResponse.getStatusCode())){
//					PegaApplicationUtil.createDocumentFollowUpHistoryPega(applicationGroup,userM);
					processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);				
				}else{
					processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				}
				if(!Util.empty(followUpResponse.getError())){
					ErrorDataM error = followUpResponse.getError().get(0);
					if(ERROR_CODE_PEGA_INVALID_STATE.equals(error.getErrorCode())){
						processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
					}
				}
			}else{
				ProcessActionResponse processCJDResponse = CJDResponseUtil.requestCJDResponse(cjdRequest, userId);
				logger.debug("processFinalApprovalResultResponse result : " + processCJDResponse.getResultCode());
				if(ServiceResponse.Status.SUCCESS.equals(processCJDResponse.getResultCode())){
					processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);				
				}else{
					processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				}
			}
		
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setResultDesc(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
	
	private String getAlreadyFollowFlag(ApplicationGroupDataM applicationGroup){
		String alearedyFollowFlag = MConstant.FLAG.NO;
		PersonalInfoDataM personalInfo =  PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		VerificationResultDataM verificationResultDataM = personalInfo.getVerificationResult();
		if(!Util.empty(verificationResultDataM)){
			String verCustFlag = verificationResultDataM.getRequiredVerCustFlag();
			String customerSegment = applicationGroup.getCallOperator();
			logger.debug("verCustFlag : "+verCustFlag);
			logger.debug("customerSegment : "+customerSegment);
			if(CALL_OPERATOR_FOLLOW.equals(customerSegment) && MConstant.FLAG.YES.equals(verCustFlag)){
				alearedyFollowFlag= MConstant.FLAG.YES;
			}
		}
		logger.debug("alreadyFollowFlag : "+alearedyFollowFlag);
		return alearedyFollowFlag;
	}
}
