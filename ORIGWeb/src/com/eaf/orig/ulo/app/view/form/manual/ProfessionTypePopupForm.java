package com.eaf.orig.ulo.app.view.form.manual;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.json.ElementFormDataM;
import com.google.gson.Gson;

public class ProfessionTypePopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(ProfessionTypePopupForm.class);
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	@Override
	public Object getObjectForm() {
		logger.debug("getFormObject()..");
		String PERSONAL_SEQ = getRequestData("PERSONAL_SEQ");
		logger.debug("PERSONAL_SEQ >> "+PERSONAL_SEQ);		
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		ApplicationGroupDataM applicationGroup = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}		
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}		
		
		String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");	
		FormHandleManager formHandleManager = (FormHandleManager) request.getSession(true).getAttribute("formHandlerManager");
		if(null == formHandleManager){
			formHandleManager = new FormHandleManager();
		}
		if(Util.empty(getRequestData("origElementValue")) && formHandleManager.existFormName(REKEY_FORM)){
			personalInfo.setProfession(null);
			personalInfo.setProfessionOther(null);
		}		
		return personalInfo;
	}
	@Override
	public String processForm() {
		logger.debug("processForm()..");	
		String PERSONAL_SEQ = getRequestData("PERSONAL_SEQ");
		String ORIG_ELEMENTID = getRequestData("origElementId");
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		ApplicationGroupDataM applicationGroup = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}		
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
		PersonalInfoDataM ProfessionTypeInfo = (PersonalInfoDataM)objectForm;
		String perfession = ProfessionTypeInfo.getProfession();
		logger.debug("perfession : " + perfession);
		personalInfo.setProfession(perfession);
		if ("99".equals(perfession)) {
			logger.debug("set perfession other : " + ProfessionTypeInfo.getProfessionOther());
			personalInfo.setProfessionOther(ProfessionTypeInfo.getProfessionOther());
		} else {
			personalInfo.setProfessionOther(null);
		}
		
		String FIELD_ID_PROFESSION = SystemConstant.getConstant("FIELD_ID_PROFESSION");	
		String PROFESSION_OTHER = SystemConstant.getConstant("PROFESSION_OTHER");	
		logger.debug("FIELD_ID_PROFESSION >> "+FIELD_ID_PROFESSION);
		logger.debug("PROFESSION_OTHER >> "+PROFESSION_OTHER);
		
		String textProfession = "";
		
		if(PROFESSION_OTHER.equals(personalInfo.getProfession())){
			textProfession = personalInfo.getProfessionOther();
		}else{
			textProfession = ListBoxControl.getName(FIELD_ID_PROFESSION,personalInfo.getProfession());
		}
		
		String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");	
		FormHandleManager formHandleManager = (FormHandleManager) request.getSession(true).getAttribute("formHandlerManager");
		if(null == formHandleManager){
			formHandleManager = new FormHandleManager();
		}	
		ElementFormDataM elementForm = new ElementFormDataM();
		elementForm.setElementId(ORIG_ELEMENTID);
		elementForm.setElementValue(textProfession);
		if(formHandleManager.existFormName(REKEY_FORM)){
			elementForm.setElementParam(personalInfo.getProfession());
			elementForm.setFormName(REKEY_FORM);
		}
		Gson gson = new Gson();
		
		return gson.toJson(elementForm);

	}
}
