package com.eaf.orig.ulo.app.view.form.property.field;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class PREV_WORKProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(PREV_WORKProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("PREV_WORKProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(Util.empty(personalInfo.getPrevWorkYear()) || Util.empty(personalInfo.getPrevWorkMonth())){
			formError.error("PREV_WORK",PREFIX_ERROR+LabelUtil.getText(request,"PREV_WORK"));
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
		
		BigDecimal zero = new BigDecimal(0);
		BigDecimal prevTotWorkYear = Util.empty(personalInfo.getPrevWorkYear()) ? new BigDecimal(0) : personalInfo.getPrevWorkYear();
		BigDecimal prevLastTotWorkYear = Util.empty(lastpersonalInfo.getPrevWorkYear()) ? new BigDecimal(0) : lastpersonalInfo.getPrevWorkYear();
		BigDecimal prevTotWorkMonth =  Util.empty(personalInfo.getPrevWorkMonth()) ? new BigDecimal(0) : personalInfo.getPrevWorkMonth();
		BigDecimal prevLastTotWorkMonth =  Util.empty(lastpersonalInfo.getPrevWorkMonth()) ? new BigDecimal(0) : lastpersonalInfo.getPrevWorkMonth();
		
		boolean compareTotYear = CompareObject.compare(prevTotWorkYear, prevLastTotWorkYear,null);
		boolean compareTotMonth = CompareObject.compare(prevTotWorkMonth, prevLastTotWorkMonth,null);
		boolean compareFlag = false;
		if(!compareTotYear || !compareTotMonth){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(personalInfo.getPersonalType(), LabelUtil.getText(request, "PREV_WORK"),request));
			if(zero.compareTo(prevLastTotWorkYear) != 0 || zero.compareTo(prevLastTotWorkMonth) != 0){
				audit.setOldValue(FormatUtil.displayText(String.valueOf(prevLastTotWorkYear)) + " " + LabelUtil.getText(request, "YEAR") + 
						 " " + String.valueOf(prevLastTotWorkMonth) + " " +  LabelUtil.getText(request, "MONTH"));
				compareFlag = true;
			}
			if(zero.compareTo(prevTotWorkYear) != 0 || zero.compareTo(prevTotWorkMonth) != 0){
				audit.setNewValue(FormatUtil.displayText(String.valueOf(prevTotWorkYear)) + " " + LabelUtil.getText(request, "YEAR") + 
						 " " + String.valueOf(prevTotWorkMonth) + " " +  LabelUtil.getText(request, "MONTH"));
				compareFlag = true;
			}
			if(compareFlag){
				audits.add(audit);
			}
		}
		return audits;
	}
}
