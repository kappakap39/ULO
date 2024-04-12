package com.eaf.orig.ulo.app.view.form.property.field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class ID_NO_CONSENTProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(ID_NO_CONSENTProperty.class);
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String IM_PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("IM_PERSONAL_TYPE_SUPPLEMENTARY");
	private String DOCUMENT_CODE_CONSENT = SystemConstant.getConstant("DOCUMENT_CODE_CONSENT");
	private String DOC_SET_FORM = SystemConstant.getConstant("DOC_SET_FORM");
	private String APPLICATION_TEMPLATE_CC_BUNDLE_SME = SystemConstant.getConstant("APPLICATION_TEMPLATE_CC_BUNDLE_SME");
	private String APPLICATION_TEMPLATE_KEC_BUNDLE_SME = SystemConstant.getConstant("APPLICATION_TEMPLATE_KEC_BUNDLE_SME");
	private String APPLICATION_TEMPLATE_CC_KEC_BUNDLE_HL = SystemConstant.getConstant("APPLICATION_TEMPLATE_CC_KEC_BUNDLE_HL");
	private String BUTTON_ACTION_SEND_TO_FU = SystemConstant.getConstant("BUTTON_ACTION_SEND_TO_FU");
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("ID_NO_CONSENTProperty.validateFlag");
		String buttonAction = request.getParameter("buttonAction");
		logger.debug("buttonAction--"+buttonAction);
		
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);

		PersonalInfoDataM personalInfo = (PersonalInfoDataM) masterObjectForm;
		
		
		if(FormControl.getObjectForm(request) instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalapplicationinfoDataM= (PersonalApplicationInfoDataM)FormControl.getObjectForm(request);
			personalInfo = personalapplicationinfoDataM.getPersonalInfo();
		}
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}

		String checkValidate = applicationGroup.getApplicationType();
		String applicationTemplate=applicationGroup.getApplicationTemplate();
		
		if(BUTTON_ACTION_SEND_TO_FU.equals(buttonAction)){
			return ValidateFormInf.VALIDATE_NO;	
		}
		else if(APPLICATION_TEMPLATE_CC_BUNDLE_SME.equals(applicationTemplate)
				||APPLICATION_TEMPLATE_KEC_BUNDLE_SME.equals(applicationTemplate)
				||APPLICATION_TEMPLATE_CC_KEC_BUNDLE_HL.equals(applicationTemplate)){
			return ValidateFormInf.VALIDATE_NO;	
		}
		else if("ADD".equals(checkValidate)){
			return ValidateFormInf.VALIDATE_NO;	
		}
		else if(personalInfo!=null&&!PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType()) && !"INC".equals(checkValidate)){
			return ValidateFormInf.VALIDATE_NO;	
		}
		else{
			return ValidateFormInf.VALIDATE_YES;			
		}
	} 

	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("ID_NO_CONSENTProperty.displayMode");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalInfo = (PersonalInfoDataM) super.objectElement;
		logger.debug("displayMode personalInfoType >> " + personalInfo.getPersonalType());
		if (Util.empty(personalInfo)) {
			personalInfo = new PersonalInfoDataM();
		}
		boolean foundDoc = checkReceiveDoc(applicationGroup, personalInfo, DOCUMENT_CODE_CONSENT);
		logger.debug("foundDoc >> " + foundDoc);
		
		String displayMode = HtmlUtil.EDIT;
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
		if(applicationGroup.isVeto()){
			displayMode = HtmlUtil.VIEW;
		}
		if (foundDoc) { // has CONSENT Document
			displayMode = HtmlUtil.EDIT;
		}else{
			displayMode = HtmlUtil.VIEW;
		}
		return displayMode;
	}
	
	
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		logger.debug("ID_NOProperty.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
		
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		
		boolean compareFlag = CompareObject.compare(personalInfo.getIdno(), lastpersonalInfo.getIdno(),null);
		if(!compareFlag){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalInfo.getPersonalType(), LabelUtil.getText(request, "ID_NO_CONSENT"), request));
			audit.setOldValue(FormatUtil.displayText(lastpersonalInfo.getIdno()));
			audit.setNewValue(FormatUtil.displayText(personalInfo.getIdno()));
			audits.add(audit);
		}
		return audits;
	}
	private boolean checkReceiveDoc(ApplicationGroupDataM applicationGroup, PersonalInfoDataM personalInfo, String documentCode) {
		if (!Util.empty(documentCode)) {
			if (Util.empty(personalInfo)) {
				personalInfo = new PersonalInfoDataM();
			}
			VerificationResultDataM verificationResultM = personalInfo.getVerificationResult();
			if (Util.empty(verificationResultM)) {
				verificationResultM = new VerificationResultDataM();
			}

			String personalId = personalInfo.getPersonalId();
			String IMPersonalType = getIMPersonalType(personalInfo);

			List<String> imageSplitsDocType = applicationGroup.getImageSplitsDocType(personalId, IMPersonalType);
			imageSplitsDocType = checkAllDocumentForm(imageSplitsDocType, applicationGroup);
			if (Util.empty(imageSplitsDocType)) {
				imageSplitsDocType = new ArrayList<String>();
			}
			logger.debug("imageSplitsDocType >> " + imageSplitsDocType);
			logger.debug("documentCode >> " + documentCode);

			if (imageSplitsDocType.contains(documentCode)) {
				return true;
			}

		}
		return false;
	}	
	private String getIMPersonalType(PersonalInfoDataM personalInfo) {

		if (!Util.empty(personalInfo)) {
			if (PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())) {
				return IM_PERSONAL_TYPE_APPLICANT;
			} else if (PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType())) {
				int seq = personalInfo.getSeq();
				return IM_PERSONAL_TYPE_SUPPLEMENTARY + seq;
			}
		}
		return "";
	}
	private List<String> checkAllDocumentForm(List<String> imageSplitsDocType, ApplicationGroupDataM applicationGroup) {
		ArrayList<String> imageSplitsDocTypeAll = applicationGroup.getImageSplitsDocType();
		if (!Util.empty(imageSplitsDocTypeAll) && imageSplitsDocTypeAll.contains(DOC_SET_FORM)) {
			if (!(imageSplitsDocType.contains(DOC_SET_FORM))) {
				imageSplitsDocType.add(DOC_SET_FORM);
			}
		}
		return imageSplitsDocType;
	}
}
