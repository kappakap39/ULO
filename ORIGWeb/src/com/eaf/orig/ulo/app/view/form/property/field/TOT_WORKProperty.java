package com.eaf.orig.ulo.app.view.form.property.field;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class TOT_WORKProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(TOT_WORKProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {
		logger.debug("TOT_WORKProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		FormErrorUtil formError = new FormErrorUtil();
		
		// Get object form
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;

		int personalAge = Calendar.getInstance().get(Calendar.YEAR) - getYear(personalInfo.getBirthDate());
		logger.debug("Age : " + personalAge);
		
		int totWorkYear = (Util.empty(personalInfo.getTotWorkYear()) ? 0 : personalInfo.getTotWorkYear().intValue());
		int totWorkMonth = (Util.empty(personalInfo.getTotWorkMonth()) ? 0 : personalInfo.getTotWorkMonth().intValue());
		String occupation = personalInfo.getOccupation();
		
		if(SystemConstant.lookup("OCCUPATION_MANDATORY", occupation)){
			if (totWorkYear == 0) {
				if (totWorkMonth <= 0) {
					formError.error("TOT_WORK", MessageErrorUtil.getText(request, "ERROR_INVALID_TOT_WORK"));
				}
			} else if (totWorkMonth == 0) {
				if (totWorkYear <= 0) {
					formError.error("TOT_WORK", MessageErrorUtil.getText(request, "ERROR_INVALID_TOT_WORK"));
				}
			}
		}
		
		if (totWorkMonth > 11) {
			formError.error("TOT_WORK", MessageErrorUtil.getText(request, "ERROR_INVALID_TOT_WORK_MONTH"));
		}
		
		return formError.getFormError();
	}
	
	private int getYear(Date birthDate) {
		if(birthDate==null){
			return 0;
		}
		Calendar car = Calendar.getInstance();
		car.setTime(birthDate);
		
		return car.get(Calendar.YEAR);
	}
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
		if(ValidateFormInf.VALIDATE_SAVE.equals(mandatoryConfig) || ValidateFormInf.VALIDATE_SUBMIT.equals(mandatoryConfig)){
			if(SystemConstant.lookup("OCCUPATION_MANDATORY", personalInfo.getOccupation())){
				return ValidateFormInf.VALIDATE_YES;
			}else{
				return ValidateFormInf.VALIDATE_NO;
			}
		}
		 
		return ValidateFormInf.VALIDATE_YES;
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
		
		BigDecimal zero = new BigDecimal(0);
		BigDecimal totWorkYear = Util.empty(personalInfo.getTotWorkYear()) ? new BigDecimal(0) : personalInfo.getTotWorkYear();
		BigDecimal lastTotWorkYear = Util.empty(lastpersonalInfo.getTotWorkYear()) ? new BigDecimal(0) : lastpersonalInfo.getTotWorkYear();
		BigDecimal totWorkMonth =  Util.empty(personalInfo.getTotWorkMonth()) ? new BigDecimal(0) : personalInfo.getTotWorkMonth();
		BigDecimal lastTotWorkMonth =  Util.empty(lastpersonalInfo.getTotWorkMonth()) ? new BigDecimal(0) : lastpersonalInfo.getTotWorkMonth();
		boolean compareTotYear = CompareObject.compare(totWorkYear, lastTotWorkYear,null);
		boolean compareTotMonth = CompareObject.compare(totWorkMonth, lastTotWorkMonth,null);
		boolean compareFlag = false;
		if(!compareTotYear || !compareTotMonth){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(personalInfo.getPersonalType(),LabelUtil.getText(request, "TOT_WORK"),request));
			if(zero.compareTo(lastTotWorkYear) != 0 || zero.compareTo(lastTotWorkMonth) != 0){
				audit.setOldValue(FormatUtil.displayText(String.valueOf(lastTotWorkYear)) + " " + LabelUtil.getText(request, "YEAR") + 
						 " " + String.valueOf(lastTotWorkMonth) + " " +  LabelUtil.getText(request, "MONTH"));
				compareFlag = true;
			}
			if(zero.compareTo(totWorkYear) != 0 || zero.compareTo(totWorkMonth) != 0){
				audit.setNewValue(FormatUtil.displayText(String.valueOf(totWorkYear)) + " " + LabelUtil.getText(request, "YEAR") + 
						 " " + String.valueOf(totWorkMonth) + " " +  LabelUtil.getText(request, "MONTH"));
				compareFlag = true;
			}
			if(compareFlag){
				audits.add(audit);
			}
		
		}
		return audits;
	}
}
