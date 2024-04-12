package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
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

public class VerificationValidationSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(VerificationValidationSubForm.class);
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup  = (ApplicationGroupDataM)appForm;
		PersonalInfoDataM personalInfo  = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(null != personalInfo){
			String[] elementSubform = SystemConstant.getConstant("VERIFICATION_VALIDATION_"+roleId).split(",");
			if(null != elementSubform){
				for(int i=0;i<elementSubform.length;i++){
					ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFICATION_VALIDATION,elementSubform[i]);
						element.processElement(request, personalInfo.getVerificationResult());
				} 
			}
		}
	}
	
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformID ="VERIFICATION_VALIDATION_SUBFROM";
		FormErrorUtil formError = new FormErrorUtil();
		String roleId = FormControl.getFormRoleId(request);
		String[] elementSubform = SystemConstant.getConstant("VERIFICATION_VALIDATION_"+roleId).split(",");		
		ApplicationGroupDataM applicationGroup  = (ApplicationGroupDataM)appForm;
		PersonalInfoDataM personalInfo  = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(null != personalInfo){
			for(int i=0;i<elementSubform.length;i++){
				ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFICATION_VALIDATION,elementSubform[i]);
				element.setObjectRequest(applicationGroup);
				HashMap<String,Object> hFormError= element.validateElement(request, personalInfo.getVerificationResult());
				if(!Util.empty(hFormError)){
					formError.addAll(hFormError);
				}
			} 
		}		
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
