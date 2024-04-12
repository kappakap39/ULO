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
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SPECIAL_MERCHANT_TYPEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(SPECIAL_MERCHANT_TYPEProperty.class);
	String FIELD_ID_SPECIAL_MERCHANT_TYPE = SystemConstant.getConstant("FIELD_ID_SPECIAL_MERCHANT_TYPE");
	String ELEMENT_ID;
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		logger.debug("SPECIAL_MERCHANT_TYPEProperty.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();	
		try{
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
			PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
			
			if(Util.empty(personalInfo)){
				personalInfo = new PersonalInfoDataM();
			}
			if(Util.empty(lastpersonalInfo)){
				lastpersonalInfo = new PersonalInfoDataM();
			}
			
			boolean compareFlag = CompareObject.compare(personalInfo.getSpecialMerchantType(), lastpersonalInfo.getSpecialMerchantType(),null);
			if(!compareFlag){
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						OrigConstant.PERSONAL_TYPE_APPLICANT, LabelUtil.getText(request, "SPECIAL_MERCHANT_TYPE"), request));
				audit.setOldValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_SPECIAL_MERCHANT_TYPE, lastpersonalInfo.getSpecialMerchantType())));
				audit.setNewValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_SPECIAL_MERCHANT_TYPE, personalInfo.getSpecialMerchantType())));
				audits.add(audit);
			}
		}catch(Exception e){
			logger.fatal("ERROR >> ",e);
		}
		return audits;
	}
	
	@Override
	public void auditInfo(String subformId, String elementId) {
		this.ELEMENT_ID = elementId;
	}
}
