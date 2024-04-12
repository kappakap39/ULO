package com.eaf.orig.ulo.app.view.form.business;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.pega.PegaApplicationUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.pega.ErrorDataM;
import com.eaf.orig.ulo.model.pega.FollowUpResponse;

public class DE1_2SaveFollowUpTopegaApplication extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(DE1_2SaveFollowUpTopegaApplication.class);
	String CALL_OPERATOR_FOLLOW = SystemConstant.getConstant("CALL_OPERATOR_FOLLOW");
	String ERROR_CODE_PEGA_INVALID_STATE = SystemConstant.getConstant("ERROR_CODE_PEGA_INVALID_STATE");
	@Override
	public Object processAction() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			logger.debug("DE1_2SaveFollowUpTopegaApplication..");	
			applicationGroup.setAlreadyFollowFlag(this.getAlreadyFollowFlag(applicationGroup));
			FollowUpResponse followUpResponse = PegaApplicationUtil.sendToPega(applicationGroup, userM, false);
			logger.debug("followUpResponse.getStatusCode : "+followUpResponse.getStatusCode());
			if(ServiceResponse.Status.SUCCESS.equals(followUpResponse.getStatusCode())){
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
