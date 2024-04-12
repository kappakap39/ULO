package com.eaf.orig.ulo.formcontrol.view.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.FormErrorDataM;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.cardMaintenance.CardMaintenanceDataM;

@SuppressWarnings("serial")
public class CardMaintenanceFormHandler extends FormHandler implements Serializable,Cloneable{
	private static transient Logger logger = Logger.getLogger(CardMaintenanceFormHandler.class);
	public CardMaintenanceFormHandler(){
		super();
		objectForm = new CardMaintenanceDataM();
		subForms = new ArrayList<ORIGSubForm>();
	}
	private CardMaintenanceDataM objectForm;
	private CardMaintenanceDataM lastObjectForm;
	private ArrayList<ORIGSubForm> subForms;
	private String subFormId;
	private String formId;
	private String roleId;
	private String tabId;
	private String templateId;
	private String buttonFile;
	private String formJS;
	private ArrayList<FormErrorDataM> errorForms;
	private HashMap<String,String> requestData;
	@Override
	public boolean validInSessionScope() {		
		return false;
	}	
	@Override
	public boolean validateForm(HttpServletRequest request) {
		String validateForm = request.getParameter("validateForm");
		logger.debug("validateForm >> "+validateForm);
		if(validateForm(validateForm)){
			if(!Util.empty(subForms)){
				for (ORIGSubForm subForm : subForms) {
					logger.debug("subForm.getSubFormClass >> "+subForm.getSubFormClass());	
					HashMap<String,Object> errors = subForm.validateForm(request,objectForm);
					logger.debug("errors >> "+errors);
					if(!Util.empty(errors)){
						FormErrorDataM formError = new FormErrorDataM();
						formError.setSubformId(subForm.getSubFormID());
						formError.setErrors(errors);
						errorForms.add(formError);
					}
				}
			}			
			return Util.empty(errorForms);
		}
		return true;
	}
	@Override
	public void postProcessForm(HttpServletRequest request) {		
		
	}
	@Override
	public void setProperties(HttpServletRequest request) {
		
	}
	@Override
	public void setProperties(HttpServletRequest request,String subformId) {
		logger.debug("subformId >> "+subformId);
		if(!Util.empty(subForms)){
			for (ORIGSubForm subForm : subForms) {
				if(subForm.getSubFormID().equals(subformId)){
					subForm.setProperties(request,objectForm);
				}
			}
		}
	}
	@Override
	public void clearForm() {
		
	}
	@Override
	public CardMaintenanceDataM getObjectForm() {
		if(null == objectForm){
			objectForm = new CardMaintenanceDataM();
		}
		return objectForm;
	}	
	@Override
	public void setObjectForm(Object objectForm) {
		if(objectForm instanceof CardMaintenanceDataM){
			this.objectForm = (CardMaintenanceDataM)objectForm;
		}
	}
	@Override
	public ArrayList<ORIGSubForm> getSubForm() {
		return subForms;
	}	
	@Override
	public void setSubForm(ArrayList<ORIGSubForm> subForms) {
		this.subForms = subForms;
	}
	public String getSubFormId() {
		return subFormId;
	}
	public void setSubFormId(String subFormId) {
		this.subFormId = subFormId;
	}
	@Override
	public String getFormId() {
		return formId;
	}
	@Override
	public void setFormId(String formId) {
		this.formId = formId;
	}
	@Override
	public String getRoleId() {
		return roleId;
	}
	@Override
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Override
	public String getTabId() {
		return tabId;
	}
	@Override
	public void setTabId(String tabId) {
		this.tabId = tabId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	@Override
	public String getButtonFile() {
		return buttonFile;
	}
	@Override
	public void setButtonFile(String buttonFile) {
		this.buttonFile = buttonFile;
	}		
	@Override
	public String getFormName(){
		return "CardMaintenanceForm";
	}
	@Override
	public String getFormJS() {
		return formJS;
	}
	@Override
	public void setFormJS(String formJS) {
		this.formJS = formJS;
	}
	@Override
	public ArrayList<FormErrorDataM> getErrorForm() {
		return errorForms;
	}
	@Override
	public void setErrorForm(ArrayList<FormErrorDataM> errorForm) {
		this.errorForms = errorForm;
	}
	@Override
	public void clearErrorForm() {
		if(null != errorForms){
			errorForms.clear();
		}
		errorForms = new ArrayList<FormErrorDataM>();
	}
	@Override
	public HashMap<String, String> getRequestData() {
		return requestData;
	}
	@Override
	public void setRequestData(HashMap<String, String> requestData) {
		this.requestData = requestData;
	}
	@Override
	public void setRequestData(HttpServletRequest request) {
		if(null == requestData){
			requestData = new HashMap<String, String>();
		}
		Enumeration<String> enumeration = request.getParameterNames();
		if(null != enumeration){
		    while(enumeration.hasMoreElements()){
		        String name = (String) enumeration.nextElement();
		        String value = request.getParameter(name);
		        requestData.put(name,value);
		    }
		}
	    logger.debug("requestData >>"+requestData);
	}
	@Override
	public CardMaintenanceDataM getLastObjectForm() {
		return lastObjectForm;
	}
	@Override
	public void setLastObjectForm(Object lastObjectForm) {
		this.lastObjectForm = (CardMaintenanceDataM)lastObjectForm;
	}
	@Override
	public ORIGSubForm getSubForm(String subFormId) {
		if(!Util.empty(subForms)){
			for (ORIGSubForm subForm : subForms){
				if(subForm.getSubFormID().equals(subFormId)){
					return subForm;
				}
			}
		}
		return null;
	}
}
