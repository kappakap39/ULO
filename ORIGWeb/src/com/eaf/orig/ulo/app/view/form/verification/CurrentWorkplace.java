package com.eaf.orig.ulo.app.view.form.verification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class CurrentWorkplace  extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(CurrentWorkplace.class);	
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String APPLICATION_TYPE_INCREASE =SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	private String APPLICATION_TYPE_ADD_SUP =SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");	
	private String CURRENT_WORK_PLACE_EXCEPTION_APPLICATION_TEMPLATE =SystemConstant.getConstant("VER_CURRENT_WORK_PLACE_EXCEPTION_APPLICATION_TEMPLATE");
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/verification/CurrentWorkplaceSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		return null;
	}	
	@Override
	public String renderElementFlag(HttpServletRequest request,Object objectElement) {
		String renderFlag = MConstant.FLAG_Y;
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		if(!Util.empty(applicationGroup)){
			String applicationType = applicationGroup.getApplicationType();
			if(APPLICATION_TYPE_ADD_SUP.equals(applicationType) ||  isBundle(applicationGroup)){
				renderFlag = MConstant.FLAG_N;
			}
		}		
		logger.debug("CurrentWorkplace renderElementFlag >>  "+renderFlag);		
		return renderFlag;
	}
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		String subformId="VERIFICATION_CUSTOMER_INFO_SUBFORM";
		FormErrorUtil formError = new FormErrorUtil();
		PersonalInfoDataM personalInfo  = (PersonalInfoDataM)objectElement;
		formError.mandatory(subformId, "OCCUPATION", personalInfo.getOccupation(), request);
		formError.mandatory(subformId, "PROFESSION", personalInfo.getProfession(), request);
		if(!Util.empty(personalInfo.getDisplayEditBTN()) && MConstant.FLAG_Y.equals(personalInfo.getDisplayEditBTN())){
			formError.mandatory(subformId, "EXECUTE_BTN","EXECUTE_BTN", "", request);
		}
		return formError.getFormError();
	}
	
	private boolean isBundle(ApplicationGroupDataM applicationGroup){
		ArrayList<String>  EXCEPTION_APPLICATION_TEMPLATE= new ArrayList<String>(Arrays.asList(CURRENT_WORK_PLACE_EXCEPTION_APPLICATION_TEMPLATE.split(",")));
		String templateType = applicationGroup.getApplicationTemplate();
		logger.debug("templateType>>"+templateType);
		if(EXCEPTION_APPLICATION_TEMPLATE.contains(templateType)){
			return true;
		}
		return false;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] fieldNameList={"OCCUPATION","PROFESSION"};		
		try {
			try {
				PersonalInfoDataM personalInfo = ((PersonalInfoDataM)objectForm);	
				if(Util.empty(personalInfo)){
					personalInfo = new PersonalInfoDataM();
				}
				 for(String elementId:fieldNameList){
					 FieldElement fieldElement = new FieldElement();
					 fieldElement.setElementId(elementId);
					 fieldElement.setElementLevel(CompareDataM.UniqueLevel.PERSONAL);
					 fieldElement.setElementGroupId(personalInfo.getPersonalId());
					 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
					 fieldElements.add(fieldElement);
				 }
				
			} catch (Exception e) {
				logger.fatal("ERROR",e);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
}

