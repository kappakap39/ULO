package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class RekeySensitiveFieldsForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(RekeySensitiveFieldsForm.class);
	@Override
	public Object getObjectForm() {
		FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);	
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM  applicationGroup  =	ORIGForm.getObjectForm();
		return applicationGroup;
	}
	@Override
	public String processForm() {
		return null;
	}
	
	private  ArrayList<CompareDataM> setCompareDataModel(String role,Object objectElement){
		ArrayList<ElementInf>   sensitiveFields = ImplementControl.getElements("COMPARE_SENSITIVE",SystemConstant.getConstant("COMPARE_SENSITIVE_TEST").split(","));
		ArrayList<CompareDataM> compareSensitiveFields  = new  ArrayList<CompareDataM>();
		 if(!Util.empty(sensitiveFields)){
			 for(ElementInf element :sensitiveFields){
				 Object compareObject  = element.getObjectElement(request, objectElement);
				 
				 if(compareObject.getClass().equals(CompareDataM.class)){
					 addCompareDataM((CompareDataM) compareObject,compareSensitiveFields);
				 }else{
					 addCompareDataM((ArrayList<CompareDataM>) compareObject,compareSensitiveFields);
				 }
			 }
		 }
		return compareSensitiveFields;
		
	}
	
	private  void addCompareDataM(CompareDataM compareData,ArrayList<CompareDataM> compareSensitiveFields){
		compareSensitiveFields.add(compareData);
	}
	private  void addCompareDataM(ArrayList<CompareDataM> compareList,ArrayList<CompareDataM> compareSensitiveFields){
		if(!Util.empty(compareList)){
			for(CompareDataM compareData :compareList){
				compareSensitiveFields.add(compareData);
			}
		}
	}
}
