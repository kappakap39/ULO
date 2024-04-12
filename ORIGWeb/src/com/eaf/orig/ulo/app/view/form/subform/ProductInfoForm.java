package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.google.gson.Gson;

public class ProductInfoForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(ProductInfoForm.class);
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM) appForm);
	}
	
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		String subformId = "PRODUCT_INFO_SUBFORM";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();		
		return formError.getFormError();
	}
	
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request, Object objectForm){
		String FLAG = MConstant.FLAG_Y;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
//		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
//		String roleId = FormControl.getFormRoleId(request);
//		if(ADD_CARD_NO_APPLICATION_ROLE.equals(roleId)){
//			if(!Util.empty(personalInfos)){
//				for(PersonalInfoDataM personalInfo : personalInfos){
//					if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) && !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
//						FLAG = MConstant.FLAG_Y;
//						break;
//					}else{
//						FLAG = MConstant.FLAG_N;
//					}
//				}
//			}
//		}
		String roleId = FormControl.getFormRoleId(request);
		if(ADD_CARD_NO_APPLICATION_ROLE.equals(roleId)){
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			if(!Util.empty(personalInfo)){
				if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) && !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
					FLAG = MConstant.FLAG_Y;
				}else{
					FLAG = MConstant.FLAG_N;
				}
			}
		}
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
		if(applicationGroup.isVeto()){
			FLAG = MConstant.FLAG_N;
		}
		logger.debug("FLAG : "+FLAG);
		return FLAG;
	}
}
