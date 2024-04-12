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
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.ulo.app.view.util.ajax.ValidateSendToFu;
import com.eaf.orig.ulo.control.util.DocumentCheckListUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class ProcessActionDE1_1 extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(ProcessActionDE1_1.class);
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	String BUTTON_ACTION_SEND_TO_FU = SystemConstant.getConstant("BUTTON_ACTION_SEND_TO_FU");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY= SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PERSONAL_ERROR_DIH_FAILED = SystemConstant.getConstant("PERSONAL_ERROR_DIH_FAILED");
	String PERSONAL_ERROR_CIS_DUPLICATE = SystemConstant.getConstant("PERSONAL_ERROR_CIS_DUPLICATE");
	@Override
	public HashMap<String, Object> validateAction(HttpServletRequest request,Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		try{
			if(BUTTON_ACTION_SUBMIT.equals(buttonAction)){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
				ArrayList<PersonalInfoDataM> usingPersonalInfos = applicationGroup.getUsingPersonalInfo();
				boolean isReasonApplicantDocFollowUp = DocumentCheckListUtil.isReasonApplicantDocumentFollowUp(applicationGroup);
				boolean isReasonSupplementatyDocFollowUp = DocumentCheckListUtil.isReasonSupplementaryDocumentFollowUp(applicationGroup);
				if(isReasonApplicantDocFollowUp || isReasonSupplementatyDocFollowUp){					
					formError.error(MessageErrorUtil.getText("ERROR_DOCUMENT_COMPLETE_FLAG"));
				}
				if(!Util.empty(usingPersonalInfos)){
//					if(PersonalInfoUtil.isDuplicateCis(usingPersonalInfos)){
//						formError.error(MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW"));
//					}
					// DEFECT 5375
					for(PersonalInfoDataM personalInfo : usingPersonalInfos){
						String PERSONAL_ERROR = personalInfo.getPersonalError();
						String PERSONAL_TYPE = personalInfo.getPersonalType();
						if(!Util.empty(PERSONAL_ERROR)){
							if(PERSONAL_ERROR_DIH_FAILED.equals(PERSONAL_ERROR)){
								if(PERSONAL_TYPE_APPLICANT.equals(PERSONAL_TYPE)){
									formError.error("ID_NO",MessageErrorUtil.getText("ERROR_DIH_CONNECTION_FAILED_APPLICANT"));
								}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(PERSONAL_TYPE)){
									formError.error(MessageErrorUtil.getText("ERROR_DIH_CONNECTION_FAILED_SUPPLEMENTARY")+personalInfo.getThName());
								}
							}else if(PERSONAL_ERROR_CIS_DUPLICATE.equals(PERSONAL_ERROR)){
								formError.error(MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW"));
							}
						}
					}
				}
			}else if(BUTTON_ACTION_SEND_TO_FU.equals(buttonAction)){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
				ValidateSendToFu validateSendToFU = new ValidateSendToFu();
				String reasonErrorMsg = validateSendToFU.validateReasonCheckList(request,applicationGroup);
				if(!Util.empty(reasonErrorMsg)){
					formError.error(reasonErrorMsg);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
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
