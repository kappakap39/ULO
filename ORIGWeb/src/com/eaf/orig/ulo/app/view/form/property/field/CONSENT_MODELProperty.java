package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class CONSENT_MODELProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(CONSENT_MODELProperty.class);
	private String DOC_SET_FORM = SystemConstant.getConstant("DOC_SET_FORM");
	private String DOCUMENT_TYPE_CONSENT_MODEL = SystemConstant.getConstant("DOCUMENT_TYPE_CONSENT_MODEL");
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String IM_PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("IM_PERSONAL_TYPE_SUPPLEMENTARY");
	private String FIELD_ID_CONSENT_MODEL = SystemConstant.getConstant("FIELD_ID_CONSENT_MODEL");

	@Override
	public boolean invokeValidateFlag() {
		return true;
	}

	@Override
	public boolean invokeValidateForm() {
		return true;
	}

	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {
		logger.debug("CONSENT_MODELProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request, "PREFIX_ERROR");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM) objectForm;
		FormErrorUtil formError = new FormErrorUtil();

		if (Util.empty(personalInfo.getConsentModelFlag())) {
			formError.error("CONSENT_MODEL", MessageErrorUtil.getText(request, "ERROR_CONSENT_MODEL"));
		}

		return formError.getFormError();
	}

	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("CONSENT_MODELProperty.validateFlag");
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		String templateId = applicationGroup.getApplicationTemplate();

		PersonalInfoDataM personalInfo = (PersonalInfoDataM) masterObjectForm;
		if (Util.empty(personalInfo)) {
			personalInfo = new PersonalInfoDataM();
		}
		logger.debug("roleId >> " + roleId);
		logger.debug("personalType >> " + personalInfo.getPersonalType());
		logger.debug("templateId >> " + templateId);

		if (ValidateFormInf.VALIDATE_SAVE.equals(mandatoryConfig) || ValidateFormInf.VALIDATE_SUBMIT.equals(mandatoryConfig)) {
			if (PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType()) && !applicationGroup.isVeto()
					&& !SystemConstant.lookup("CONSENT_MODEL_EXCEPTION_APPLICATION_TEMPLATE", templateId)
					&& (SystemConstant.lookup("CONSENT_MODEL_APPLY_TYPE", applicationGroup.getApplicationType()) || Util.empty(applicationGroup.getApplicationType()) )) {
				boolean foundDoc = checkReceiveDoc(applicationGroup, personalInfo, DOCUMENT_TYPE_CONSENT_MODEL);
				logger.debug("foundDoc >> " + foundDoc);
				if (foundDoc) {
					return ValidateFormInf.VALIDATE_YES;
				}

			}
		}

		return ValidateFormInf.VALIDATE_NO;
	}

	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM) objectForm;
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM) lastObjectForm;

		if (Util.empty(personalInfo)) {
			personalInfo = new PersonalInfoDataM();
		}
		if (Util.empty(lastpersonalInfo)) {
			lastpersonalInfo = new PersonalInfoDataM();
		}

		boolean compareFlag = CompareObject.compare(personalInfo.getConsentModelFlag(), lastpersonalInfo.getConsentModelFlag(), null);
		if (!compareFlag) {
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(PERSONAL_TYPE_APPLICANT, LabelUtil.getText(request, "CONSENT_MODEL"), request));
			audit.setOldValue(FormatUtil.displayText(getConsentModelFlagDesc(lastpersonalInfo.getConsentModelFlag())));
			audit.setNewValue(FormatUtil.displayText(getConsentModelFlagDesc(personalInfo.getConsentModelFlag())));
			audits.add(audit);
		}
		return audits;
	}

	private String getConsentModelFlagDesc(String consentModelFlag) {
		return Util.empty(consentModelFlag) ? "" : CacheControl.getName(FIELD_ID_CONSENT_MODEL, consentModelFlag);
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
