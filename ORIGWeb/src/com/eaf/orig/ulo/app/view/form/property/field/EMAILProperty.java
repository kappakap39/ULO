package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class EMAILProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(EMAILProperty.class);
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue) {
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		PersonalInfoDataM lastPersonalInfo = (PersonalInfoDataM)lastObjectForm;
		
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastPersonalInfo)){
			lastPersonalInfo = new PersonalInfoDataM();
		}
		
		boolean compare = CompareObject.compare(personalInfo.getEmailPrimary(), lastPersonalInfo.getEmailPrimary(),null);
		if(!compare){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalInfo.getPersonalType(), LabelUtil.getText(request, "EMAIL"), request));
			audit.setOldValue(FormatUtil.displayText(lastPersonalInfo.getEmailPrimary()));
			audit.setNewValue(FormatUtil.displayText(personalInfo.getEmailPrimary()));
			audits.add(audit);
		}
		return audits;
	}
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm){
		String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		String SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT");
		String SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT");
		String SPECIAL_ADDITIONAL_SERVICE_EMAIL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_EMAIL");

		ApplicationGroupDataM   applicationGroup = FormControl.getOrigObjectForm(request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
		String personalId = personalInfo.getPersonalId();
		logger.debug("personalInfoID>>>"+personalInfo.getPersonalId());
/*		if(!Util.empty(applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_SPENDING_ALERT)) ){
			logger.debug("validateFlag>>>EMAIL>>>M");
			return ValidateFormInf.VALIDATE_SUBMIT;
		}
		if(!Util.empty(applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_DUE_ALERT))){
			logger.debug("validateFlag>>>EMAIL>>>M");
			return ValidateFormInf.VALIDATE_SUBMIT;
		}*/
		if(!Util.empty(applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_EMAIL))){
			logger.debug("validateFlag>>>EMAIL>>>M");
			return ValidateFormInf.VALIDATE_SUBMIT;
		}
		logger.debug("validateFlag>>>EMAIL>>>N");
		return ValidateFormInf.VALIDATE_NO;
	}
}
