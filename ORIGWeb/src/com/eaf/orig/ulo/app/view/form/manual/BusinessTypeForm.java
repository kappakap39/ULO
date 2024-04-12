package com.eaf.orig.ulo.app.view.form.manual;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.json.ElementFormDataM;
import com.google.gson.Gson;

public class BusinessTypeForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(BusinessTypeForm.class);
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	@Override
	public Object getObjectForm() {
		logger.debug("getFormObject()..");
		String BUSINESS_TYPE = request.getParameter("BUSINESS_TYPE");
		String PERSONAL_SEQ = getRequestData("PERSONAL_SEQ");
		logger.debug("BUSINESS_TYPE >> "+BUSINESS_TYPE);
		logger.debug("PERSONAL_SEQ >> "+PERSONAL_SEQ);
		
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
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
			personalInfo.setBusinessType(null);
			personalInfo.setBusinessTypeOther(null);
		}
		return personalInfo;
	}
	@Override
	public String processForm() {
		logger.debug("processForm()..");		
		String PERSONAL_SEQ = getRequestData("PERSONAL_SEQ");
		String ORIG_ELEMENTID = getRequestData("origElementId");
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
		
		PersonalInfoDataM BussinessTypeInfo = (PersonalInfoDataM)objectForm;
		personalInfo.setBusinessType(BussinessTypeInfo.getBusinessType());	
		personalInfo.setBusinessTypeOther(BussinessTypeInfo.getBusinessTypeOther());
		String FIELD_ID_BUSINESS_TYPE = SystemConstant.getConstant("FIELD_ID_BUSINESS_TYPE");	
		String BUSINESS_TYPE_OTHER = SystemConstant.getConstant("BUSINESS_TYPE_OTHER");		
		logger.debug("FIELD_ID_BUSINESS_TYPE >> "+FIELD_ID_BUSINESS_TYPE);
		logger.debug("BUSINESS_TYPE_OTHER >> "+BUSINESS_TYPE_OTHER);

		String textBusiness = "";
		
		if(BUSINESS_TYPE_OTHER.equals(personalInfo.getBusinessType())){
			textBusiness = personalInfo.getBusinessTypeOther();
		}else{
			textBusiness = ListBoxControl.getName(FIELD_ID_BUSINESS_TYPE,personalInfo.getBusinessType());
		}
		
		String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");	
		FormHandleManager formHandleManager = (FormHandleManager) request.getSession(true).getAttribute("formHandlerManager");
		if(null == formHandleManager){
			formHandleManager = new FormHandleManager();
		}
		ElementFormDataM elementForm = new ElementFormDataM();
		elementForm.setElementId(ORIG_ELEMENTID);
		elementForm.setElementValue(textBusiness);
		if(formHandleManager.existFormName(REKEY_FORM)){
			elementForm.setElementParam(personalInfo.getBusinessType());
			elementForm.setFormName(REKEY_FORM);
		}
		Gson gson = new Gson();
		return gson.toJson(elementForm);
		
	}
}
