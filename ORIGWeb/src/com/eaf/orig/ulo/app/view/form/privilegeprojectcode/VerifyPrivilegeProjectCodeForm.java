package com.eaf.orig.ulo.app.view.form.privilegeprojectcode;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class VerifyPrivilegeProjectCodeForm extends FormHelper implements FormAction {
	private static transient Logger logger = Logger.getLogger(VerifyPrivilegeProjectCodeForm.class);
	
	@Override
	public Object getObjectForm() {
		int PROJECT_CODE_INDEX = 0;
				
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();		
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);

		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult = new VerificationResultDataM();
		}
		PrivilegeProjectCodeDataM privilegeProjectCode = verificationResult.getPrivilegeProjectCode(PROJECT_CODE_INDEX);
		if(Util.empty(privilegeProjectCode)){
			 privilegeProjectCode = new PrivilegeProjectCodeDataM();
		}
		setFormData(SystemConstant.getConstant("PRVLG_FORM_DATA_NAME"),MConstant.FLAG.NO); 
	
		return privilegeProjectCode;
	}
	@Override
	public String processForm() {
		String RCC_PROJECT_CODE =request.getParameter("RCC_PROJECT_CODE");
		String DOCUMENT_TYPE =request.getParameter("DOCUMENT_TYPE");
		logger.debug("RCC_PROJECT_CODE>>"+RCC_PROJECT_CODE);
		logger.debug("DOCUMENT_TYPE>>"+DOCUMENT_TYPE);
		
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectForm;
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();	
		
		ArrayList<ApplicationDataM> ApplicationiDataList =ApplicationUtil.getApplicationCCProducts(applicationGroup);
		if(!Util.empty(ApplicationiDataList) && !Util.empty(RCC_PROJECT_CODE)){
			for(ApplicationDataM apllItem :ApplicationiDataList){
				apllItem.setProjectCode(RCC_PROJECT_CODE);
			}
		}
		
		PersonalInfoDataM  personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult =  new VerificationResultDataM();
			personalInfo.setVerificationResult(verificationResult);
		}
		if(!Util.empty(DOCUMENT_TYPE)){
			verificationResult.setVerPrivilegeResult(CacheControl.getName(SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS"), SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED")));
			verificationResult.setVerPrivilegeResultCode(SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED"));
		}else{
			verificationResult.setVerPrivilegeResult(CacheControl.getName(SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS"), SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED")));
			verificationResult.setVerPrivilegeResultCode(SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED"));
		}
		ArrayList<PrivilegeProjectCodeDataM> privilegeProjectCodes = verificationResult.getPrivilegeProjectCodes();
		if(Util.empty(privilegeProjectCodes)){
			privilegeProjectCodes = new ArrayList<PrivilegeProjectCodeDataM>();
			verificationResult.setPrivilegeProjectCodes(privilegeProjectCodes);
			privilegeProjectCodes.add(new PrivilegeProjectCodeDataM());
		}
		int PROJECT_CODE_INDEX = 0;
		if(!Util.empty(verificationResult.getPrivilegeProjectCode(PROJECT_CODE_INDEX))){
			privilegeProjectCodes.set(PROJECT_CODE_INDEX,privilegeProjectCode);
		}else{
			privilegeProjectCodes.add(privilegeProjectCode);
		}
		return null;
	}
}
