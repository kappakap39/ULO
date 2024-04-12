package com.eaf.orig.ulo.formcontrol.view.form;

import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

@SuppressWarnings("serial")
public class PersonalFormHandler extends FormHandler implements Serializable,Cloneable{
	private static transient Logger logger = Logger.getLogger(PersonalFormHandler.class);
	public static final String PersonalForm = "PersonalForm";
	private PersonalInfoDataM objectForm;
	private ArrayList<ORIGSubForm> subForms;
	private String subFormId;
	private String formId;
	private String roleId;
	private String tabId;
	private String templateId;
	private String buttonFile;
	@Override
	public boolean validInSessionScope() {		
		return false;
	}
	@Override
	public boolean validateForm(HttpServletRequest request) {		
		if(!Util.empty(subForms)){
			for (ORIGSubForm subForm : subForms) {
				if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request, objectForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
					logger.debug("subForm.getSubFormClass >> "+subForm.getSubFormClass());	
					subForm.validateForm(request,objectForm);
				}
			}
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
					subForm.setProperties(request,objectForm);
				}
			}
		}
	}
	public static PersonalFormHandler getForm(HttpServletRequest request){
		PersonalFormHandler form = (PersonalFormHandler)request.getSession().getAttribute(PersonalForm);
		if(null == form){
			form = new PersonalFormHandler();
			request.getSession().setAttribute(PersonalForm,form);
		}
		return form;		
	}
	@Override
	public void clearForm() {
		
	}
	@Override
	public PersonalInfoDataM getObjectForm() {
		return objectForm;
	}
	public void setObjectForm(PersonalInfoDataM objectForm) {
		this.objectForm = objectForm;
	}
	@Override
	public ArrayList<ORIGSubForm> getSubForm(){
		return subForms;
	}
	public void setSubForms(ArrayList<ORIGSubForm> subForms) {
		this.subForms = subForms;
	}
	public String getSubFormId() {
		return subFormId;
	}
	public void setSubFormId(String subFormId) {
		this.subFormId = subFormId;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getTabId() {
		return tabId;
	}
	public void setTabId(String tabId) {
		this.tabId = tabId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getButtonFile() {
		return buttonFile;
	}
	public void setButtonFile(String buttonFile) {
		this.buttonFile = buttonFile;
	}	
}
