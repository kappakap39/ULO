package com.eaf.orig.ulo.app.view.form.template;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class OccupationForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(OccupationForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
		String formId = FormControl.getFormId("OCCUPATION_FORM",applicationGroup.getProductId(),applicationGroup.getBundingId());
		logger.debug("formId >> "+formId);
		ArrayList<ORIGSubForm> subForms = FormControl.getSubForm(formId,roleId);
		if(!Util.empty(subForms)){
			for(ORIGSubForm subForm:subForms){
				if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,appForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
					subForm.setProperties(request, appForm);
				}
			}
		}
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		HashMap errors = new HashMap<>();
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
		String formId = FormControl.getFormId("OCCUPATION_FORM",applicationGroup.getProductId(),applicationGroup.getBundingId());
		logger.debug("formId >> "+formId);
		ArrayList<ORIGSubForm> subForms = FormControl.getSubForm(formId,roleId);
		if(!Util.empty(subForms)){
			for(ORIGSubForm subForm:subForms){
				if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,appForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
					HashMap error = subForm.validateForm(request, appForm);
					if(null != error){
						errors.putAll(error);
					}
				}
			}
		}
		return errors;
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		int CNT_ERROR = 0;
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		String roleId = FormControl.getFormRoleId(request);
		String formId = FormControl.getFormId("OCCUPATION_FORM",applicationGroup.getProductId(),applicationGroup.getBundingId());
		logger.debug("formId >> "+formId);
		ArrayList<ORIGSubForm> subForms = FormControl.getSubForm(formId,roleId);
		if(!Util.empty(subForms)){
			for(ORIGSubForm subForm:subForms){
				if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,applicationGroup)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
					boolean IS_SUCCES = subForm.validateSubForm(request);
					if(!IS_SUCCES){
						CNT_ERROR++;
					}
				}
			}
		}
		return (CNT_ERROR==0);
	}
}
