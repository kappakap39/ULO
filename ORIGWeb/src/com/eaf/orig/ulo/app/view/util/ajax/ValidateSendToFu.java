package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class ValidateSendToFu implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ValidateSendToFu.class);
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.VALIDATE_SEND_TO_FU);
		String ROLE_DE1_1 = SystemConstant.getConstant("ROLE_DE1_1");
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)ORIGForm.getObjectForm();
		String roleId = FormControl.getFormRoleId(request);
		String data = null;
		JSONUtil errorSendToFU = new JSONUtil();
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		try{
			List<String> errorMessages = new ArrayList<String>();
			// validate Reason
			String reasonErrorMsg = validateReasonCheckList(request,applicationGroup);
			if(!Util.empty(reasonErrorMsg)){
				errorMessages.add(reasonErrorMsg);
			}
			// validate TH_FIRST_LAST_NAME 
			logger.debug("roleId : "+roleId);
			if(ROLE_DE1_1.equals(roleId)){
				String nameErrorMsg = validateTH_FIRST_LAST_NAME(request,applicationGroup);
				if(!Util.empty(nameErrorMsg)){
					errorMessages.add(nameErrorMsg);
				}
			}
			if(!Util.empty(errorMessages)){
				errorSendToFU.put("ERROR",createErrorMessage(errorMessages));
				data = errorSendToFU.getJSON();
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		logger.debug("data : "+data);
		return responseData.success(data);
		//return responseData.success(new Gson().toJson(Jsondata));
	}
	private String createErrorMessage(List<String> errorMessages){
		StringBuilder errorMsg = new StringBuilder("");
		for(String error :errorMessages){
			errorMsg.append("<div>"+error+"</div>");
		}
		return errorMsg.toString();
	}
	public String validateReasonCheckList(HttpServletRequest request,ApplicationGroupDataM applicationGroup)throws Exception{
		String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
		try{
			PersonalInfoDataM personalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			ArrayList<PersonalInfoDataM> personalSupplementary = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
			String executeFlag = applicationGroup.getExecuteFlag();
			boolean nullReasonApplicant = true;
			boolean validReasonCheckList = true;
			ArrayList<String> DOC_CODEAr = new ArrayList<String>();
			String prefix = "";
			if (!Util.empty(personalApplicant)) {
					VerificationResultDataM verificationA = personalApplicant.getVerificationResult();
				
					if (Util.empty(verificationA)) {
						verificationA = new VerificationResultDataM();
					}
					ArrayList<RequiredDocDataM> requiredDocListsA = verificationA.getRequiredDocs();
					if(Util.empty(requiredDocListsA)){
						requiredDocListsA = new ArrayList<RequiredDocDataM>();
					}
				
				for(RequiredDocDataM requestDoc:requiredDocListsA){
					ArrayList<RequiredDocDetailDataM> docDetails = requestDoc.getRequiredDocDetails();
					for(RequiredDocDetailDataM docDetail:docDetails){
						DOC_CODEAr.add(docDetail.getDocumentCode());
						}
					}
				
				for(String DOC_CODE : DOC_CODEAr){
					prefix="_"+DOC_CODE+"_"+personalApplicant.getPersonalType()+"_"+personalApplicant.getPersonalId();
					String[] reason = request.getParameterValues("FOLLOWED_DOC_REASON_SELECT"+prefix);
					logger.debug("reason : "+Arrays.toString(reason));
					if(!Util.empty(reason)){
						nullReasonApplicant = false;
					}
				}
				if(Util.empty(requiredDocListsA)){
					validReasonCheckList = false;	//Jsondata.put("ERROR", "N");
				}else if(!Util.empty(requiredDocListsA) && nullReasonApplicant && MConstant.FLAG_Y.equals(applicationGroup.getDocCompletedFlag())){
					validReasonCheckList = false;	//Jsondata.put("ERROR", "N");
				}
			}
//			else if(Util.empty(personalApplicant)){
//				validReasonCheckList = false;	//Jsondata.put("ERROR", "N");
//			}
			if(!Util.empty(personalSupplementary)){
				for(PersonalInfoDataM personal:personalSupplementary){
					VerificationResultDataM verificationB = personal.getVerificationResult();
					if (Util.empty(verificationB)) {
							verificationB = new VerificationResultDataM();
						}
					ArrayList<RequiredDocDataM> requiredDocListsB = verificationB.getRequiredDocs();
					if (!Util.empty(personal)){
						if(Util.empty(requiredDocListsB)){
							requiredDocListsB = new ArrayList<RequiredDocDataM>();
						}
						for(RequiredDocDataM requestDoc:requiredDocListsB){
							ArrayList<RequiredDocDetailDataM> docDetails = requestDoc.getRequiredDocDetails();
							for(RequiredDocDetailDataM docDetail:docDetails){
								DOC_CODEAr.add(docDetail.getDocumentCode());
							}
						}
						boolean nullreason = true;
						for(String DOC_CODE : DOC_CODEAr){
							prefix="_"+DOC_CODE+"_"+personal.getPersonalType()+"_"+personal.getPersonalId();
							String[] reason = request.getParameterValues("FOLLOWED_DOC_REASON_SELECT"+prefix);
							logger.debug("reason : "+Arrays.toString(reason));
							if(!Util.empty(reason)){
								nullreason = false;
							}
						}
						if(Util.empty(requiredDocListsB)){
							validReasonCheckList = false;	//Jsondata.put("ERROR", "N");
						}else if(!Util.empty(requiredDocListsB) && nullreason && nullReasonApplicant && MConstant.FLAG_Y.equals(applicationGroup.getDocCompletedFlag())){
							validReasonCheckList = false;	//Jsondata.put("ERROR", "N");
						}else{
							validReasonCheckList = true;	//Jsondata.clear();
						}
					}
				}
			}
			
			if(Util.empty(executeFlag) || MConstant.FLAG_N.equals(executeFlag)){
				return MessageErrorUtil.getText("ERROR_PLEASE_EXECUTE"); 
			}
			
			if(!validReasonCheckList){
				return MessageErrorUtil.getText("ERROR_PLEASE_SELECT_REASON_CHECK_LIST");
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new Exception(e);
		}
		return "";
	}
	private String validateTH_FIRST_LAST_NAME(HttpServletRequest request,ApplicationGroupDataM applicationGroup)throws Exception{
		String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
		try{
			boolean validThFirstLastName = true;
			ArrayList<PersonalInfoDataM> usingPersonalInfos = applicationGroup.getUsingPersonalInfo();
			if(!Util.empty(usingPersonalInfos)){
				for(PersonalInfoDataM personalInfo : usingPersonalInfos){
					if(!Util.empty(personalInfo)){
						String TH_FIRST_NAME = "";
						String TH_LAST_NAME = "";
						if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
							TH_FIRST_NAME = request.getParameter("TH_FIRST_NAME");
							TH_LAST_NAME = request.getParameter("TH_LAST_NAME");
						}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType())){
							TH_FIRST_NAME = personalInfo.getThFirstName();
							TH_LAST_NAME = personalInfo.getThLastName();
						}
						logger.debug("TH_FIRST_NAME : "+TH_FIRST_NAME);
						logger.debug("TH_LAST_NAME : "+TH_LAST_NAME);
						if(Util.empty(TH_FIRST_NAME) || Util.empty(TH_LAST_NAME)){
							validThFirstLastName = false;
						}
					}
				}
			}else{
				validThFirstLastName = false;
			}
			logger.debug("validThFirstLastName : "+validThFirstLastName);
			if(!validThFirstLastName){
				String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
				return PREFIX_ERROR+LabelUtil.getText(request,"TH_FIRST_LAST_NAME");
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new Exception(e);
		}
		return "";
	}
}
