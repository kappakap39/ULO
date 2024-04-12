package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class BIRTH_DATEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(BIRTH_DATEProperty.class);
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){		
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
		String personalType = personalInfo.getPersonalType();
		
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		
//		boolean compareBirthDateTH = CompareObject.compare(personalInfo.getBirthDate(), lastpersonalInfo.getBirthDate());
//		if(!compareBirthDateTH){
//			AuditDataM audit = new AuditDataM();
//			audit.setFieldName(LabelUtil.getText(request, "TH_BIRTH_DATE"));
//			audit.setOldValue(FormatUtil.display(lastpersonalInfo.getBirthDate(),FormatUtil.TH,FormatUtil.Format.ddMMyyyy));
//			audit.setNewValue(FormatUtil.display(personalInfo.getBirthDate(),FormatUtil.TH,FormatUtil.Format.ddMMyyyy));
//			audits.add(audit);
//		}
		boolean compareBirthDateEN = CompareObject.compare(personalInfo.getBirthDate(), lastpersonalInfo.getBirthDate(),null);
		if(!compareBirthDateEN){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalType, LabelUtil.getText(request, "EN_BIRTH_DATE"), request));
			audit.setOldValue(FormatUtil.display(lastpersonalInfo.getBirthDate(),FormatUtil.EN,FormatUtil.Format.ddMMyyyy));
			audit.setNewValue(FormatUtil.display(personalInfo.getBirthDate(),FormatUtil.EN,FormatUtil.Format.ddMMyyyy));
			audits.add(audit);
			
			audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalType, LabelUtil.getText(request, "TH_BIRTH_DATE"), request));
			audit.setOldValue(FormatUtil.display(lastpersonalInfo.getBirthDate(),FormatUtil.TH,FormatUtil.Format.ddMMyyyy));
			audit.setNewValue(FormatUtil.display(personalInfo.getBirthDate(),FormatUtil.TH,FormatUtil.Format.ddMMyyyy));
			audits.add(audit);
		}		
		return audits;
	}
}
