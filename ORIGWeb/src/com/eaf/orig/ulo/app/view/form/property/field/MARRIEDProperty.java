package com.eaf.orig.ulo.app.view.form.property.field;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class MARRIEDProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(MARRIEDProperty.class);
	private String FIELD_ID_MARRIED = SystemConstant.getConstant("FIELD_ID_MARRIED");
	
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
		
		boolean compareFlag = CompareObject.compare(personalInfo.getMarried(), lastpersonalInfo.getMarried(),null);
		if(!compareFlag){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalInfo.getPersonalType(), LabelUtil.getText(request, "MARRIED"), request));
			audit.setOldValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_MARRIED, lastpersonalInfo.getMarried())));
			audit.setNewValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_MARRIED, personalInfo.getMarried())));
			audits.add(audit);
		}
		return audits;
	}
}
