package com.eaf.orig.ulo.app.view.form.subform.ia;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.CompareSensitiveUtility;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.model.FormControlDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class ListPersonalInfoSubForm extends ORIGSubForm{
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");	
	String IM_PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("IM_PERSONAL_TYPE_SUPPLEMENTARY");	
	String IA_PERSONAL_APPLICANT_FORM = SystemConstant.getConstant("IA_PERSONAL_APPLICANT_FORM");
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");
	private static transient Logger logger = Logger.getLogger(ListPersonalInfoSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {	
		FormErrorUtil formError = new FormErrorUtil();
		try {
			 DocumentCheckListDataM documentCheckList;
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			PersonalInfoDataM personalMain = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			ArrayList<DocumentCheckListDataM> docList = applicationGroup.getDocumentCheckLists();
			ArrayList<ApplicationImageSplitDataM> docListArr = applicationGroup.getImageSplits();
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			ApplicationGroupDataM applicationGroupM = ORIGForm.getObjectForm();
			PersonalInfoDataM mainCardPersonal =applicationGroupM.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			PersonalInfoDataM supCardPersonal =applicationGroupM.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroupM.getUsingPersonalInfo();
			if(!Util.empty(personalInfos)){
				for(PersonalInfoDataM personalInfo : personalInfos){
					HashMap<String,String> requestDataForm = new HashMap<String, String>();
					requestDataForm.put("REQ_PERSONAL_TYPE", personalInfo.getPersonalType());
					requestDataForm.put("REQ_PERSONAL_ID", personalInfo.getPersonalId());
					requestDataForm.put("VALIDATE_FORM_TYPE", "MAIN_FORM");
					FormControlDataM formControlM = new FormControlDataM();
					formControlM.setFormId(IA_PERSONAL_APPLICANT_FORM);
					formControlM.setRoleId(FormControl.getFormRoleId(request));
					formControlM.setRequest(request);
					formControlM.setManualRequestData(true);
					formControlM.setRequestData(requestDataForm);
					boolean errorForm = formError.mandatoryForm(formControlM);
					logger.debug("errorForm2 >> "+errorForm);
					String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
					String MESSAGE_ERROR ="";
					logger.debug("typePersonal >> "+personalInfo.getPersonalType());
					logger.debug("PERSONAL_TYPE_APPLICANT >> "+PERSONAL_TYPE_APPLICANT);
					//  PersonalInfo is main
					if(personalInfo.getPersonalType().equals(PERSONAL_TYPE_APPLICANT)){
						 MESSAGE_ERROR = MessageErrorUtil.getText(request, "MESSAGE_MAIN_REGIS");
					}else{ // PersonalInfo is Sub
						 MESSAGE_ERROR = MessageErrorUtil.getText(request, "PREFIX_POPUP_SUP_FORM_ERROR");
					}
					if(!errorForm){
						formError.error(MESSAGE_ERROR+personalInfo.getThName());
					}
				}
			}

			if(!Util.empty(docListArr)){
				for(ApplicationImageSplitDataM docChecklist : docListArr){
					String IMType = docChecklist.getApplicantTypeIM();
					if(IM_PERSONAL_TYPE_APPLICANT.equals(IMType)){
						if(Util.empty(personalMain)){
							formError.error(MessageErrorUtil.getText("MESSAGE_MAIN_REGIS"));
						}
					}else{
						String Type = IMType.substring(0,1);
						String seq = IMType.substring(1,IMType.length());
						PersonalInfoDataM personalSup =applicationGroup.getIMPersonal(Type,Integer.parseInt(seq));
						if(Util.empty(personalSup)){
							formError.error(MessageErrorUtil.getText("MESSAGE_SUB_REGIS"));
						}
					}
				}
			}
			
			if(!Util.empty(mainCardPersonal)&&!Util.empty(supCardPersonal)){
				logger.debug("mainIdNO :"+mainCardPersonal.getIdno());
				logger.debug("subIdNO :"+supCardPersonal.getIdno());
				if(mainCardPersonal.getIdno().equals(supCardPersonal.getIdno())){
					formError.error(MessageErrorUtil.getText("ERROR_MAIN_CARD"));
					}
				}
			
		} catch (Exception e) {
			logger.fatal("Error",e);
		}
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {	
		return false;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElementList = new ArrayList<FieldElement>();
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ArrayList<PersonalInfoDataM> personalInfos =  applicationGroup.getPersonalInfos();
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo : personalInfos){
				FormControlDataM formControlM = new FormControlDataM();
				formControlM.setFormId("IA_PERSONAL_APPLICANT_FORM");
				formControlM.setRoleId(roleId);
				formControlM.setRequest(request);
				HashMap<String,String> requestData = new HashMap<String, String>();
				requestData.put("REQ_PERSONAL_SEQ",FormatUtil.getString(personalInfo.getSeq()));
				requestData.put("REQ_PERSONAL_TYPE",personalInfo.getPersonalType());
				requestData.put("REQ_PERSONAL_ID",personalInfo.getPersonalId());
				formControlM.setRequestData(requestData);
				fieldElementList.addAll(CompareSensitiveUtility.tabElementForm(request, formControlM, personalInfo));

			}
		}
		return fieldElementList;
	}@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ApplicationGroupDataM lastapplicationGroup = (ApplicationGroupDataM)lastObjectForm;
		ArrayList<AuditDataM> auditFormList = new ArrayList<AuditDataM>();
		
		FormControlDataM formControlM = new FormControlDataM();
		formControlM.setFormId(IA_PERSONAL_APPLICANT_FORM);
		formControlM.setRoleId(FormControl.getFormRoleId(request));
		formControlM.setRequest(request);			
		formControlM.setManualRequestData(false);
		
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo : personalInfos){
				if(!PERSONAL_TYPE_INFO.equals(personalInfo.getPersonalType())){
					PersonalInfoDataM personalTypeSupplyment = lastapplicationGroup.getPersonalInfo(personalInfo.getPersonalType());
					FormHandler currentForm = FormControl.getFormHandler(formControlM);
					ArrayList<ORIGSubForm> subForms = currentForm.getSubForm();
					if(!Util.empty(subForms)){
						for (ORIGSubForm subForm : subForms) {
							ArrayList<AuditDataM> audits = subForm.auditForm(request, personalInfo, personalTypeSupplyment);
							auditFormList.addAll(audits);
						}
					}
				}
			}
		}
		return auditFormList;
	}
}
