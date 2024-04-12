package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;

@SuppressWarnings("serial")
public class ReferencePersonSubForm3 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(ReferencePersonSubForm3.class);
	String subformId = "REFERENCE_PERSON_SUBFORM_3";
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.REFERENCE_PERSON_3);
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
			ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.REFERENCE_PERSON_3);
			logger.debug("element >> "+elements.size());
			for(ElementInf element:elements){
				if(MConstant.FLAG_Y.equals(element.renderElementFlag(request,element))){
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
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) {
		String[] REFERENCE_EXCEPTION_APPLICATION_TEMPLATE = SystemConstant.getArrayConstant("REFERENCE_EXCEPTION_APPLICATION_TEMPLATE");	
		ArrayList<String> templateConditions = new ArrayList<String>(Arrays.asList(REFERENCE_EXCEPTION_APPLICATION_TEMPLATE));
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		String templateId = applicationGroup.getApplicationTemplate();
		logger.debug(">>>templateId>>>"+templateId);
		if(templateConditions.contains(templateId)){
			return MConstant.FLAG_N;
		}
		return  MConstant.FLAG_Y;
	}
	
}
