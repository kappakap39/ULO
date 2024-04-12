package com.eaf.orig.ulo.app.view.form.process;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.DocumentCheckListUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class ProcessActionDE1_2 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(ProcessActionDE1_2.class);
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	String KPL_V2_TEMPLATE = SystemConstant.getConstant("KPL_V2_TEMPLATE");
	
	@Override
	public HashMap<String, Object> validateAction(HttpServletRequest request,Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String buttonAction = getButtonAction();
		logger.debug("buttonAction : "+buttonAction);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		String jobState = applicationGroup.getJobState();
		logger.debug("jobState >> "+jobState);
		if(BUTTON_ACTION_SUBMIT.equals(buttonAction)){
			if(SystemConstant.lookup("JOBSTATE_REQUIRE_VERIFY_INCOME",jobState)){
				PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
				if(!MConstant.FLAG_N.equals(personalInfo.getDisplayEditBTN())){
					formError.error(MessageErrorUtil.getText(request,"NOT_ALLOW_TO_SUBMIT_DE1_2")); 
				}
			}
			
			boolean isReasonApplicantDocFollowUp = DocumentCheckListUtil.isReasonApplicantDocumentFollowUp(applicationGroup);
			boolean isReasonSupplementatyDocFollowUp = DocumentCheckListUtil.isReasonSupplementaryDocumentFollowUp(applicationGroup);
			if(isReasonApplicantDocFollowUp || isReasonSupplementatyDocFollowUp){					
				formError.error(MessageErrorUtil.getText("ERROR_DOCUMENT_COMPLETE_FLAG"));
			}
		}
		return formError.getFormError();
	}
	@Override
	public Object preProcessAction() {
		return null;
	}
	@Override
	public Object processAction() {
	 	ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
			VerifyUtil.setVerificationResultByBirthDate(applicationGroup);
			// CR267 New Default letter channel for KPL version 2
			if (null != applicationGroup.getApplicationTemplate() 
					&& KPL_V2_TEMPLATE.equals( applicationGroup.getApplicationTemplate() ) ) {
				ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
				if (null != applications && applications.size() > 0) {
					for (ApplicationDataM applicationDataM : applications) {
						applicationDataM.setLetterChannel( MConstant.FLAG_Y );
					}
				}
			}
			processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setResultDesc(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
	@Override
	public Object postProcessAction() {
		return null;
	}
}