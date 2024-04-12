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

public class MOBILE_NOProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(MOBILE_NOProperty.class);
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		try{
			String personalType = (String)objectValue;
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
			PersonalInfoDataM lastPersonalInfo = (PersonalInfoDataM)lastObjectForm;
			
			if(Util.empty(personalInfo)){
				personalInfo = new PersonalInfoDataM();
			}
			if(Util.empty(lastPersonalInfo)){
				lastPersonalInfo = new PersonalInfoDataM();
			}
			
			boolean compare = CompareObject.compare(personalInfo.getMobileNo(), lastPersonalInfo.getMobileNo(),null);
			logger.debug("compare >> "+compare);
			if(!compare){
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						personalType, LabelUtil.getText(request, "MOBILE"), request));
				audit.setOldValue(FormatUtil.displayText(lastPersonalInfo.getMobileNo()));
				audit.setNewValue(FormatUtil.displayText(personalInfo.getMobileNo()));
				audits.add(audit);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return audits;
	}
	
}
