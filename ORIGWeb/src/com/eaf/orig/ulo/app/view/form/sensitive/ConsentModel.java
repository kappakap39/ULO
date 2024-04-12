package com.eaf.orig.ulo.app.view.form.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ConsentModel extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(ConsentModel.class);
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	private String FIELD_ID_CONSENT_MODEL = SystemConstant.getConstant("FIELD_ID_CONSENT_MODEL");
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");

	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		ModuleFormHandler RekeyForm = (ModuleFormHandler) request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) RekeyForm.getObjectForm();
		CompareDataM compareData = ((CompareDataM) objectElement);
		PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByUniqueId(applicationGroup, compareData);
		if (null == personalInfoM) {
			personalInfoM = new PersonalInfoDataM();
		}
		String elementId = FormatUtil.getPersonalElementId(personalInfoM);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(), elementId);
		String displayMode = HtmlUtil.EDIT;
		StringBuffer HTML = new StringBuffer();
		HTML.append("<div class='col-sm-12'>")
				.append("<div class='form-group'>")
				.append(HtmlUtil.getFieldLabel(request, compareData.getFieldNameType(), "col-sm-4 col-md-5 control-label"))
				.append(HtmlUtil.dropdown(CompareSensitiveFieldUtil.getElementName(compareData),
						CompareSensitiveFieldUtil.getElementSuffix(compareData), "", "", "", FIELD_ID_CONSENT_MODEL, "", displayMode, tagId,
						"col-sm-8 col-md-7", request)).append("</div></div>").append("<div class='clearfix'></div>");
		HTML.append(HtmlUtil.hidden("CONSENT_MODEL_" + CompareSensitiveFieldUtil.getElementSuffix(compareData), personalInfoM.getConsentModelFlag()));

		return HTML.toString();
	}

	@Override
	public <T> Object getObjectElement(HttpServletRequest request, Object objectForm, FieldElement fieldElement) {
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM) objectForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(fieldElement.getElementGroupId());
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		if (!Util.empty(personalInfo)) {
			CompareDataM compareData = new CompareDataM();
			String personalRefId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
			compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
			compareData.setFieldNameType(getImplementId());
			compareData.setValue(personalInfo.getConsentModelFlag());
			compareData.setRole(roleId);
			compareData.setRefId(personalRefId);
			compareData.setRefLevel(CompareDataM.RefLevel.PERSONAL);
			compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
			compareData.setUniqueId(personalInfo.getPersonalId());
			compareData.setUniqueLevel(CompareDataM.UniqueLevel.PERSONAL);
			compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));

			if (SystemConstant.lookup("CONSENT_MODEL_EDIT_ROLE", roleId) && PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())) {
				compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfo, ""));
			} else {
				compareData.setConfigData(CompareSensitiveFieldUtil.unCompareFieldConfigDataMToJSonString(applicationGroup, ""));
			}

			compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
			compareList.add(compareData);
		}
		return compareList;
	}

	@Override
	public String processElement(HttpServletRequest request, Object objectElement) {
		ModuleFormHandler RekeyForm = (ModuleFormHandler) request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) RekeyForm.getObjectForm();
		CompareDataM compareData = ((CompareDataM) objectElement);

		PersonalInfoDataM personalInfoM = CompareSensitiveFieldUtil.getPersonalInfoByUniqueId(applicationGroup, compareData);
		if (!Util.empty(personalInfoM)) {
			String paramName = CompareSensitiveFieldUtil.getParameterName(compareData);
			logger.debug("paramName >> " + paramName);
			String consentModelFlag = request.getParameter(paramName);
			logger.debug("consentModelFlag >> " + consentModelFlag);
			personalInfoM.setConsentModelFlag(consentModelFlag);
		}
		return null;
	}

	@Override
	public HashMap<String, Object> validateElement(HttpServletRequest request, Object objectElement) {
		FormErrorUtil formError = new FormErrorUtil();
		return formError.getFormError();
	}

}
