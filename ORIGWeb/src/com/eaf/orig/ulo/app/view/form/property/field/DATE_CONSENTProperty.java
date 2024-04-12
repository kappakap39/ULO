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
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DATE_CONSENTProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(DATE_CONSENTProperty.class);
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String DOC_SET_FORM = SystemConstant.getConstant("DOC_SET_FORM");
	private String APPLICATION_TEMPLATE_CC_BUNDLE_SME = SystemConstant.getConstant("APPLICATION_TEMPLATE_CC_BUNDLE_SME");
	private String APPLICATION_TEMPLATE_KEC_BUNDLE_SME = SystemConstant.getConstant("APPLICATION_TEMPLATE_KEC_BUNDLE_SME");
	private String APPLICATION_TEMPLATE_CC_KEC_BUNDLE_HL = SystemConstant.getConstant("APPLICATION_TEMPLATE_CC_KEC_BUNDLE_HL");
	private String BUTTON_ACTION_SEND_TO_FU = SystemConstant.getConstant("BUTTON_ACTION_SEND_TO_FU");
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("DATE_CONSENTProperty.validateFlag");
		String buttonAction = request.getParameter("buttonAction");
		logger.debug("buttonAction--"+buttonAction);
		
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);

		PersonalInfoDataM personalInfo = (PersonalInfoDataM) masterObjectForm;
	
		if(FormControl.getObjectForm(request) instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalapplicationinfoDataM= (PersonalApplicationInfoDataM)FormControl.getObjectForm(request);
			personalInfo = personalapplicationinfoDataM.getPersonalInfo();
		}
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		
		String checkValidate = applicationGroup.getApplicationType();
		logger.debug("checkValidate--"+checkValidate);
		String applicationTemplate=applicationGroup.getApplicationTemplate();
		
		
		if(BUTTON_ACTION_SEND_TO_FU.equals(buttonAction)){
			return ValidateFormInf.VALIDATE_NO;	
		}
		else if(APPLICATION_TEMPLATE_CC_BUNDLE_SME.equals(applicationTemplate)
				||APPLICATION_TEMPLATE_KEC_BUNDLE_SME.equals(applicationTemplate)
				||APPLICATION_TEMPLATE_CC_KEC_BUNDLE_HL.equals(applicationTemplate)){
			return ValidateFormInf.VALIDATE_NO;	
		}
		else if("ADD".equals(checkValidate)){
			return ValidateFormInf.VALIDATE_NO;	
		}
		else if(personalInfo!=null&&!PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
			return ValidateFormInf.VALIDATE_NO;	
		}
		else{
			return ValidateFormInf.VALIDATE_YES;			
		}
	}
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){		
		logger.debug("ID_NOProperty.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();		
		return audits;
	}
}
