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
import com.eaf.orig.ulo.constant.MConstant;

@SuppressWarnings("serial")
public class ModuleFormHandler extends FormHandler implements Serializable,Cloneable{
	private static transient Logger logger = Logger.getLogger(ModuleFormHandler.class);	
	public ModuleFormHandler(){
		super();
		errorForms = new ArrayList<FormErrorDataM>();
		subForms = new ArrayList<ORIGSubForm>();
		requestData = new HashMap<String, String>();
		objectForm = null;
		uniqueIds = new ArrayList<String>();
	}
	public String ModuleForm = "ModuleForm";
	private HashMap<String,String> requestData;
	private HashMap<String,Object> formData;
	private ArrayList<String> uniqueIds;
	private Object objectForm;
	private ArrayList<ORIGSubForm> subForms;
	private String subFormId;
	private String formId;
	private String roleId;
	private String tabId;
	private String rowId;
	private String mode;
	private String buttonFile;
	private String formJS;
	private ArrayList<FormErrorDataM> errorForms;
	
	@Override
	public boolean validInSessionScope() {	
		return false;
	}
	@Override
	public boolean validateForm(HttpServletRequest request) {	
		String validateForm = request.getParameter("validateForm");
		logger.debug("validateForm >> "+validateForm);
		if("Y".equals(validateForm)){
			if(!Util.empty(subForms)){
				for (ORIGSubForm subForm : subForms) {
					if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request, objectForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
						logger.debug("subForm.getSubFormClass >> "+subForm.getSubFormClass());	
						subForm.setSubformData(requestData);
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
		if(!Util.empty(subForms)){
			for (ORIGSubForm subForm : subForms) {
				if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request, objectForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
					logger.debug("subForm.getSubFormClass >> "+subForm.getSubFormClass());	
					subForm.setSubformData(requestData);
					subForm.setProperties(request,objectForm);
				}
			}
		}
	}
	
	@Override
	public void setProperties(HttpServletRequest request,String subformId) {
		logger.debug("subformId >> "+subformId);
		if(!Util.empty(subForms)){
			for (ORIGSubForm subForm : subForms) {
				if(subForm.getSubFormID().equals(subformId)){
					subForm.setSubformData(requestData);
					subForm.setProperties(request,objectForm);
				}
			}
		}
	}
	@Override
	public void clearForm() {		
		
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
	public Object getObjectForm(){
		return objectForm;
	}	
	@Override
	public ArrayList<ORIGSubForm> getSubForm() {
		return subForms;
	}
	@Override
	public void setSubForm(ArrayList<ORIGSubForm> subForm) {
		this.subForms = subForm;
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
	@Override
	public String getRowId() {
		return rowId;
	}
	@Override
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}	
	@Override
	public String getMode() {
		return mode;
	}
	@Override
	public void setMode(String mode) {
		this.mode = mode;
	}
	@Override
	public void setObjectForm(Object objectForm) {
		this.objectForm = objectForm;
	}
	@Override
	public String getRequestData(String paramName){
		if(null == requestData){
			requestData = new HashMap<String, String>();
		}
		return requestData.get(paramName);
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
	public String getButtonFile() {
		return buttonFile;
	}
	@Override
	public void setButtonFile(String buttonFile) {
		this.buttonFile = buttonFile;
	}
	@Override
	public String getFormName() {
		return ModuleForm;
	}
	@Override
	public void setFormName(String formName) {
		this.ModuleForm = formName;
	}
	@Override
	public void clearErrorForm() {
		if(null != errorForms){
			errorForms.clear();
		}
		errorForms = new ArrayList<FormErrorDataM>();
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
	public void setFormData(String keyName, Object value) {
		formData.put(keyName,value);
	}
	@Override
	public Object getFormData(String keyName) {
		return formData.get(keyName);
	}
	@Override
	public HashMap<String,Object> getFormData() {
		return formData;
	}
	@Override
	public void setFormData(HashMap<String, Object> formData) {
		this.formData = formData;
	}
	@Override
	public void setUniqueId(String uniqueId) {
		this.uniqueIds.add(uniqueId);
	}
	@Override
	public ArrayList<String> getUniqueIds() {
		return uniqueIds;
	}
	@Override
	public void setUniqueIds(ArrayList<String> uniqueIds) {
		this.uniqueIds = uniqueIds;
	}
	@Override
	public String getRequestDataString(String keyName) {
		if(null == requestData){
			requestData = new HashMap<String, String>();
		}
		Object formDataObject = requestData.get(keyName);
		if(formDataObject instanceof String){
			return (String)formDataObject;
		}
		return null;
	}
}
