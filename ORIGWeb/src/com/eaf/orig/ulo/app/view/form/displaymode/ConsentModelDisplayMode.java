package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class ConsentModelDisplayMode extends FormDisplayModeHelper {
	private static transient Logger logger = Logger.getLogger(ConsentModelDisplayMode.class);
	private String DOC_SET_FORM = SystemConstant.getConstant("DOC_SET_FORM");
	private String DOCUMENT_TYPE_CONSENT_MODEL = SystemConstant.getConstant("DOCUMENT_TYPE_CONSENT_MODEL");
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String IM_PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("IM_PERSONAL_TYPE_SUPPLEMENTARY");

	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		logger.debug("ConsentModelDisplayMode.displayMode");
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		String templateId = applicationGroup.getApplicationTemplate();

		PersonalInfoDataM personalInfo = (PersonalInfoDataM) super.objectElement;
		logger.debug("personalInfoType >> " + personalInfo.getPersonalType());
		String displayMode = HtmlUtil.VIEW;
		logger.debug("IS_VETO >> " + applicationGroup.isVeto());
		if (!applicationGroup.isVeto() && PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())
				//&& !SystemConstant.lookup("CONSENT_MODEL_EXCEPTION_APPLICATION_TEMPLATE", templateId)
				&& SystemConstant.lookup("CONSENT_MODEL_EDIT_ROLE", roleId)) {
			boolean foundDoc = checkReceiveDoc(applicationGroup, personalInfo, DOCUMENT_TYPE_CONSENT_MODEL);
			logger.debug("foundDoc >> " + foundDoc);
			if (foundDoc) {
				displayMode = HtmlUtil.EDIT;
			}
		}
		return displayMode;
	}

	public boolean checkReceiveDoc(ApplicationGroupDataM applicationGroup, PersonalInfoDataM personalInfo, String documentCode) {
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
