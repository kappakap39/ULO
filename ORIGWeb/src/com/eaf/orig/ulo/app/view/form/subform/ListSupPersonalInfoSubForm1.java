package com.eaf.orig.ulo.app.view.form.subform;

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
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ProcessForm;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class ListSupPersonalInfoSubForm1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(ListSupPersonalInfoSubForm1.class);
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String COMPLETE_FLAG_FIRST_LOAD_PERSONAL = SystemConstant.getConstant("COMPLETE_FLAG_FIRST_LOAD_PERSONAL");
	String LOAD_PERSONAL_COMPLETE = SystemConstant.getConstant("LOAD_PERSONAL_COMPLETE");
	
	String popupForm = "SUPPLEMENTARY_APPLICANT_FORM";
	private String subformId = "LIST_SUP_PERSONAL_INFO_SUBFORM_1";
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
	}
	
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {	
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
		ArrayList<PersonalInfoDataM> personals = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		PersonalInfoDataM mainCardPersonal =applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		PersonalInfoDataM supCardPersonal =applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
		FormErrorUtil formError = new FormErrorUtil();
		if(applicationGroup.isSupplementaryApplicant()){
			if(Util.empty(personals)){
			formError.mandatory(subformId, "PERSONAL_TYPE",personals, request);
			}else if(!Util.empty(supCardPersonal) && (Util.empty(supCardPersonal.getCompleteData())||COMPLETE_FLAG_FIRST_LOAD_PERSONAL.equals(supCardPersonal.getCompleteData()))){
			formError.mandatory(subformId, "PERSONAL_TYPE",null, request);	
			}
		}
		
		if(!Util.empty(mainCardPersonal)&&!Util.empty(supCardPersonal)){
			logger.debug("mainIdNO :"+mainCardPersonal.getIdno());
			logger.debug("subIdNO :"+supCardPersonal.getIdno());
			if(mainCardPersonal.getIdno().equals(supCardPersonal.getIdno())){
				formError.error(MessageErrorUtil.getText("ERROR_MAIN_CARD"));
				}
			}
		
		if(!Util.empty(personals)){
			for(PersonalInfoDataM personalInfoM : personals){
				FormControlDataM formControlM = new FormControlDataM();
				String formId = getSupplementaryFormId(applicationGroup);
				formControlM.setFormId(formId);
				formControlM.setRoleId(roleId);
				formControlM.setRequest(request);
				formControlM.setProcessForm(ProcessForm.SUP_APPLICANT_VALIDATE);
				HashMap<String,String> requestData = new HashMap<String, String>();
				requestData.put("REQ_PERSONAL_SEQ",FormatUtil.getString(personalInfoM.getSeq()));
				requestData.put("REQ_PERSONAL_TYPE",personalInfoM.getPersonalType());
				requestData.put("REQ_PERSONAL_ID",personalInfoM.getPersonalId());
				formControlM.setRequestData(requestData);
				boolean errorForm = formError.mandatoryForm(formControlM);
				logger.debug("errorForm >> "+errorForm);				
				String MESSAGE_ERROR = MessageErrorUtil.getText(request, "PREFIX_POPUP_SUP_FORM_ERROR");
				if(!errorForm && !Util.empty(personalInfoM.getCompleteData()) && LOAD_PERSONAL_COMPLETE.equals(personalInfoM.getCompleteData())){
					formError.error(MESSAGE_ERROR+personalInfoM.getThName());
				}
			}
		}
		return formError.getFormError();
	}
	
	public String getSupplementaryFormId(ApplicationGroupDataM applicationGroup){
//		if(APPLICATION_TYPE_ADD_SUP.equals(applicationGroup.getApplicationType())) {
//			popupForm="SUP_CARD_SEPARATELY_FORM";
//		}
		logger.debug("popupForm >> "+popupForm);
		return popupForm;
	}
	
	@Override
	public boolean validateSubForm(HttpServletRequest request) {	
		return false;
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		logger.debug("subformId(displayValueForm) : "+subformId);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ArrayList<PersonalInfoDataM> personalInfoSupplement = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		if(!Util.empty(personalInfoSupplement)){
			for(PersonalInfoDataM personalInfo : personalInfoSupplement){
				personalInfo.setLinkFirstName(personalInfo.getThFirstName());
				personalInfo.setLinkLastName(personalInfo.getThLastName());
			}
		}
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		formValue.clearValue("LIST_SUP_PERSONAL",applicationGroup);
		
		//***** Clear Flag Address Editor *****//
		formValue.clearValue("SEND_DOC",applicationGroup);
		
		
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		logger.debug("sup.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<>();
		try{
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
			ApplicationGroupDataM lastApplicationGroup = (ApplicationGroupDataM)lastObjectForm;
			
			ArrayList<ORIGSubForm> subForms = FormControl.getSubForm(popupForm, roleId, lastObjectForm, request);
			logger.debug("subForms size >> "+subForms.size());
			if(!Util.empty(applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY))){
				for(ORIGSubForm subForm : subForms){
					if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
						ArrayList<AuditDataM> auditForms = subForm.auditForm(request, applicationGroup, lastApplicationGroup);
						if(!Util.empty(auditForms)){
							audits.addAll(auditForms);
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return audits;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElementList = new ArrayList<FieldElement>();
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ArrayList<PersonalInfoDataM> personalInfos =  applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo : personalInfos){
				FormControlDataM formControlM = new FormControlDataM();
				formControlM.setFormId("SUPPLEMENTARY_APPLICANT_FORM");
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
	}
}
