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

public class OccupationPopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(OccupationPopupForm.class);
	String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");	
	String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");	
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
		String elementNamePersonal = getRequestData("ElementNamePersonal");
		if(!Util.empty(elementNamePersonal) && formHandleManager.existFormName(REKEY_FORM)){
			String[] personalIdElement = elementNamePersonal.split("\\_");
			if(!Util.empty(personalIdElement)){
				String personalId = personalIdElement[personalIdElement.length-1];
				Object ojbForm = FormControl.getObjectForm(request);
				if(ojbForm instanceof ApplicationGroupDataM){
					if(!Util.empty(((ApplicationGroupDataM) ojbForm).getPersonalById(personalId))){
						personalInfo = ((ApplicationGroupDataM) ojbForm).getPersonalById(personalId);
					}
				}
			}
		}
		if(Util.empty(getRequestData("origElementValue")) && formHandleManager.existFormName(REKEY_FORM)){
			personalInfo.setOccupation(null);
			personalInfo.setOccpationOther(null);
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
		PersonalInfoDataM occupationInfo = (PersonalInfoDataM)objectForm;
		String occupation = occupationInfo.getOccupation();		
		logger.debug("occupation >> "+occupation);
		logger.debug("GOVERNMENT_OFFICER >> "+GOVERNMENT_OFFICER);		
		personalInfo.setOccupation(occupation);		
		if(GOVERNMENT_OFFICER.equals(occupation)){
			personalInfo.setPositionDesc(null);
		}else{
			personalInfo.setPositionCode(null);
		}		
		personalInfo.setOccpationOther(occupationInfo.getOccpationOther());		
		logger.debug("FIELD_ID_OCCUPATION >> "+FIELD_ID_OCCUPATION);
		logger.debug("OCCUPATION_OTHER >> "+personalInfo.getOccupation());		
		String textOccpation = "";		
		if(OCCUPATION_OTHER.equals(personalInfo.getOccupation())){
			textOccpation = personalInfo.getOccpationOther();
		}else{
			textOccpation = ListBoxControl.getName(FIELD_ID_OCCUPATION,personalInfo.getOccupation());
		}
		String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");	
		FormHandleManager formHandleManager = (FormHandleManager) request.getSession(true).getAttribute("formHandlerManager");
		if(null == formHandleManager){
			formHandleManager = new FormHandleManager();
		}		
		ElementFormDataM elementForm = new ElementFormDataM();
		elementForm.setElementId(ORIG_ELEMENTID);
		elementForm.setElementValue(textOccpation);
		if(formHandleManager.existFormName(REKEY_FORM)){
			elementForm.setElementParam(personalInfo.getOccupation());
			elementForm.setFormName(REKEY_FORM);
		}
		Gson gson = new Gson();		
		return gson.toJson(elementForm);
	}
}
