package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class VerificationCustomerInfoSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(VerificationCustomerInfoSubForm.class);	
	String COM_REGISTRATION_ELEMENT =SystemConstant.getConstant("COMMERCIAL_REGISTRATION_ELEMENT");
	String COMMERCIAL_DOC_TYPE =SystemConstant.getConstant("VERIFICATION_COMMERCIAL_DOC_TYPE");
	String IMPLEMENT_TYPE_VERIFICATION=SystemConstant.getConstant("IMPLEMENT_TYPE_VERIFICATION");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		String roleId = FormControl.getFormRoleId(request);				
		ApplicationGroupDataM applicationGroup  = (ApplicationGroupDataM)appForm;
		PersonalInfoDataM personalInfo  = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		String personalType = personalInfo.getPersonalType();
		logger.debug("roleId >> "+roleId);
		logger.debug("personalType >> "+personalType);		
		String[] elementSubform = SystemConstant.getConstant("VERIFICATION_CUSTOMER_INFO_"+roleId).split(",");
		if(null != elementSubform){
			for(int i=0;i<elementSubform.length;i++){
				ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFICATION,elementSubform[i]);
					element.processElement(request, personalInfo);
			} 
		}
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);	
		ArrayList<String> documenTypes = applicationGroup.getImageSplitsDocType();
		if(Util.empty(documenTypes)){
			documenTypes = new ArrayList<String>();
		}		
		FormErrorUtil formError = new FormErrorUtil();		
		String[] elementSubform = SystemConstant.getConstant("VERIFICATION_CUSTOMER_INFO_"+roleId).split(",");
		if(null != elementSubform){
			ArrayList<String> elements = new ArrayList<>(Arrays.asList(elementSubform));
			boolean ignoreValidate = (!documenTypes.contains(COMMERCIAL_DOC_TYPE) && elements.contains(COM_REGISTRATION_ELEMENT));
			logger.debug("ignoreValidate >> "+ignoreValidate);
			if(!ignoreValidate){
				for(int i=0;i<elementSubform.length;i++){
					String elementId = elementSubform[i];
					logger.debug("elementId >> "+elementId);
					ElementInf elementInf = ImplementControl.getElement(IMPLEMENT_TYPE_VERIFICATION,elementId);
					if(MConstant.FLAG_Y.equals(elementInf.renderElementFlag(request,applicationGroup))){
						ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFICATION,elementSubform[i]);
						HashMap<String,Object>  errorData = element.validateElement(request, personalInfo);			
						if(!Util.empty(errorData)){
							formError.addAll(errorData);
						}
					}
				} 
			}
		}
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}	
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		try {
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);	
			String[] elementSubforms = SystemConstant.getConstant("VERIFICATION_CUSTOMER_INFO_"+roleId).split(",");
			if(!Util.empty(elementSubforms)){
				ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.VERIFICATION, elementSubforms);
				for(ElementInf element : elements){
					if(MConstant.FLAG_Y.equals(element.renderElementFlag(request, applicationGroup))){
						ArrayList<FieldElement> fieldElementList = element.elementForm(request, personalInfo);
						if(!Util.empty(fieldElementList)){
							fieldElements.addAll(fieldElementList);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
}
