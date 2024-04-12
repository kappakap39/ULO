package com.eaf.orig.ulo.app.view.form.question;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

public class KEMailStatement extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(KEMailStatement.class);
	String K_EMAIL_STATEMENT_QUESTION = SystemConstant.getConstant("K_EMAIL_STATEMENT_QUESTION");
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
//		IdentifyQuestionDataM identifyquestionDataM =(IdentifyQuestionDataM)objectElement;
//		QuestionObjectDataM questionObject = (QuestionObjectDataM)getObjectRequest();
//		ApplicationGroupDataM applicationGroup = questionObject.getApplicationGroup();
		String personalId = (String)objectElement;
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
//		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(questionObject.getPersonalId());
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);
		String seq = (String)getObjectRequest();
		return "/orig/ulo/subform/question/KEMailStatement.jsp?PERSONAL_ID="+personalId+"&SEQ="+seq;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		logger.debug("processElement >> "+KEMailStatement.class);
		String personalId= (String)objectElement;
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);
		String EMAIL = request.getParameter("EMAIL_"+personalInfo.getPersonalId());
		logger.debug("EMAIL >>"+EMAIL);
		if(!Util.empty(personalInfo)){
			personalInfo.setEmailPrimary(EMAIL);
	}	
		return null;
	}
	@Override
	public String renderElementFlag(HttpServletRequest request,Object objectElement) {
		String SPECIAL_ADDITIONAL_SERVICE_POSTAL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_POSTAL");
		String SPECIAL_ADDITIONAL_SERVICE_EMAIL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_EMAIL");
		String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		String GEN_PARAM_CC_INFINITE = SystemConstant.getConstant("GEN_PARAM_CC_INFINITE");
		String GEN_PARAM_CC_WISDOM = SystemConstant.getConstant("GEN_PARAM_CC_WISDOM");
		String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		String personalId = (String)objectElement;
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);
		if(!Util.empty(personalInfo)){
			ArrayList<String> products = applicationGroup.filterProductPersonal(personalInfo.getPersonalId(), PERSONAL_RELATION_APPLICATION_LEVEL);
			for(String product : products){
				SpecialAdditionalServiceDataM specialAdditionalServicePostal = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalInfo.getPersonalId(), product, SPECIAL_ADDITIONAL_SERVICE_POSTAL);
				SpecialAdditionalServiceDataM specialAdditionalServiceEmail = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalInfo.getPersonalId(), product, SPECIAL_ADDITIONAL_SERVICE_EMAIL);
				if(SystemConfig.getGeneralParam(GEN_PARAM_CC_INFINITE).contains(applicationGroup.getApplicationTemplate()) ||
						SystemConfig.getGeneralParam(GEN_PARAM_CC_WISDOM).contains(applicationGroup.getApplicationTemplate())){
					if(!Util.empty(personalInfo) && PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
						if(!Util.empty(specialAdditionalServiceEmail) && !Util.empty(personalInfo) && Util.empty(personalInfo.getEmailPrimary())){
							return MConstant.FLAG.YES;
						}
					}
				}else{
					if(!Util.empty(personalInfo) && PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
						if(Util.empty(specialAdditionalServicePostal) && !Util.empty(personalInfo) && Util.empty(personalInfo.getEmailPrimary())){
							return MConstant.FLAG.YES;
						}
					}
				}
			}
		}
		return MConstant.FLAG.NO;
	}
}
