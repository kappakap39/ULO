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

public class CUSTOMER_TYPEProperty extends FieldPropertyHelper{
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String CIDTYPE_PASSPORT=SystemConstant.getConstant("CIDTYPE_PASSPORT");
	String VISA_TYPE_F = SystemConstant.getConstant("VISA_TYPE_F");
	private static transient Logger logger = Logger.getLogger(CUSTOMER_TYPEProperty.class);
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		logger.debug("CUSTOMER_TYPEProperty.validateFlag "+personalInfo.getVisaType());
		if(CIDTYPE_PASSPORT.equals(personalInfo.getCidType()) && !VISA_TYPE_F.equals(personalInfo.getVisaType())){
			return ValidateFormInf.VALIDATE_YES;
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		logger.debug("CUSTOMER_TYPEProperty.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
		
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		
		boolean compareFlag = CompareObject.compare(personalInfo.getVisaExpDate(), lastpersonalInfo.getVisaExpDate(),null);
		if(!compareFlag){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalInfo.getPersonalType(), LabelUtil.getText(request, "VISA_EXPIRE_DATE"), request));
			audit.setOldValue(FormatUtil.display(lastpersonalInfo.getVisaExpDate(),FormatUtil.EN,FormatUtil.Format.ddMMyyyy));
			audit.setNewValue(FormatUtil.display(personalInfo.getVisaExpDate(),FormatUtil.EN,FormatUtil.Format.ddMMyyyy));
			audits.add(audit);
		}
		return audits;
	}
}
