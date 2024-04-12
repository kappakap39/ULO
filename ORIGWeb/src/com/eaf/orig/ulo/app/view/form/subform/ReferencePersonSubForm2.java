package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;

@SuppressWarnings("serial")
public class ReferencePersonSubForm2 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(ReferencePersonSubForm1.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String subformId = "REFERENCE_PERSON_SUBFORM_2";
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.REFERENCE_PERSON_2);
		logger.debug("element >> "+elements.size());
		for(ElementInf element:elements){
			element.setObjectRequest(applicationGroup);
			if(MConstant.FLAG_Y.equals(element.renderElementFlag(request,applicationGroup))){
				element.processElement(request, applicationGroup);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("subformId >> "+subformId);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		FormErrorUtil formError = new FormErrorUtil();
		ArrayList<ReferencePersonDataM> referencePersons = applicationGroup.getReferencePersons();
		for(ReferencePersonDataM referencePerson : referencePersons){
			ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.REFERENCE_PERSON_2);
			logger.debug("element >> "+elements.size());
			for(ElementInf element:elements){
				if(MConstant.FLAG_Y.equals(element.renderElementFlag(request,applicationGroup))){
					formError.addAll(element.validateElement(request, referencePerson));
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
	public String renderSubformFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("renderSubformFlag");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		String[] REFERENCE_EXCEPTION_APPLICATION_TEMPLATE = SystemConstant.getArrayConstant("REFERENCE_EXCEPTION_APPLICATION_TEMPLATE");	
		ArrayList<String> templateConditions = new ArrayList<String>(Arrays.asList(REFERENCE_EXCEPTION_APPLICATION_TEMPLATE));
		String templateId = applicationGroup.getApplicationTemplate();
		logger.debug("templateId >> "+templateId);
		if(!templateConditions.contains(templateId)){
			RenderSubform render = new RenderSubform();
			String OCCUPATION_TYPE_ALL = SystemConstant.getConstant("OCCUPATION_TYPE_ALL");	
			String checkProductType = render.determineProductType(templateId);
			if(OCCUPATION_TYPE_ALL.equals(checkProductType)){
				return MConstant.FLAG_Y;
			}
		}
		return MConstant.FLAG_N;
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm){
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);	
		ArrayList<ReferencePersonDataM> referencePersons = applicationGroup.getReferencePersons();
		for(ReferencePersonDataM referencePerson : referencePersons){
			ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.REFERENCE_PERSON_2);
			logger.debug("element >> "+elements.size());
			for(ElementInf element:elements){
				if(MConstant.FLAG_Y.equals(element.renderElementFlag(request,applicationGroup))){
					element.displayValueElement(request, referencePerson,formValue);
				}
			}
		}
	}
	
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
	ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
	try {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
		
		ArrayList<ReferencePersonDataM> referencePersons = applicationGroup.getReferencePersons();
		for(ReferencePersonDataM referencePerson : referencePersons){
			ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.REFERENCE_PERSON_2);
			for(ElementInf element:elements){
				if(MConstant.FLAG_Y.equals(element.renderElementFlag(request,applicationGroup))){
					element.setObjectRequest(applicationGroup);
					ArrayList<FieldElement> fieldElementList = element.elementForm(request, referencePerson);
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
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ApplicationGroupDataM lastApplicationGroup = (ApplicationGroupDataM)lastObjectForm;
		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		auditFormUtil.setObjectValue(PERSONAL_TYPE_APPLICANT);
		auditFormUtil.auditForm(subformId, "PHONE1", applicationGroup, lastApplicationGroup, request);
		
		return auditFormUtil.getAuditForm();
	}
}
