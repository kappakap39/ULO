package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class POSITION_GOVERNMENTProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(POSITIONProperty.class);
	private static String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private static String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");
	private static String PROFESSION_TYPE_JUDGE = SystemConstant.getConstant("PROFESSION_TYPE_JUDGE");
	private static String FIELD_ID_IMAGE_GOVERNMENT = SystemConstant.getConstant("FIELD_ID_IMAGE_GOVERNMENT");
	private static String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		String retValidate="";
		boolean imageGov = false;
		logger.debug(">>mandatoryConfig>>>"+mandatoryConfig);
		if(mandatoryConfig.equals(ValidateFormInf.VALIDATE_SUBMIT)){
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			if(null == personalInfo){
				personalInfo = new PersonalInfoDataM();
			}
			
			ArrayList<ApplicationImageSplitDataM> docListArr = applicationGroup.getImageSplits();
			for(ApplicationImageSplitDataM docList : docListArr){
				String IMType = docList.getApplicantTypeIM();
				logger.debug("docList.getDocType() >> "+docList.getDocType());
				if(IM_PERSONAL_TYPE_APPLICANT.equals(IMType) && FIELD_ID_IMAGE_GOVERNMENT.equals(docList.getDocType())){
					imageGov = true;
				}
			}
			
			if(GOVERNMENT_OFFICER.equals(personalInfo.getOccupation()) && imageGov){
				retValidate = ValidateFormInf.VALIDATE_NO;
			}else if(GOVERNMENT_OFFICER.equals(personalInfo.getOccupation()) && PROFESSION_TYPE_JUDGE.equals(personalInfo.getProfession())){
				retValidate = ValidateFormInf.VALIDATE_NO;
			}else{
				retValidate = ValidateFormInf.VALIDATE_SUBMIT;
			}
			//#FUT : 221.1
//			if (GOVERNMENT_OFFICER.equals(personalInfo.getOccupation()) && !PROFESSION_TYPE_JUDGE.equals(personalInfo.getProfession())) {
//				retValidate = ValidateFormInf.VALIDATE_NO;
//			} else {
//				retValidate = ValidateFormInf.VALIDATE_SUBMIT;
//			}
		}		
		return retValidate;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("POSITION_CODEProperty.validateForm");
		FormErrorUtil formError = new FormErrorUtil();
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");		
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}		

			if (Util.empty(personalInfo.getPositionDesc())) {
				formError.error("POSITION", PREFIX_ERROR+LabelUtil.getText(request,"POSITION"));
			}
		
		
		return formError.getFormError();
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
		
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		
		boolean compareFlag = CompareObject.compare(personalInfo.getPositionDesc(), lastpersonalInfo.getPositionDesc(),null);
		if(!compareFlag){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalInfo.getPersonalType(), LabelUtil.getText(request, "POSITION"), request));
			audit.setOldValue(FormatUtil.displayText(lastpersonalInfo.getPositionDesc()));
			audit.setNewValue(FormatUtil.displayText(personalInfo.getPositionDesc()));
			
			audits.add(audit);
		}
		return audits;
	}
}
