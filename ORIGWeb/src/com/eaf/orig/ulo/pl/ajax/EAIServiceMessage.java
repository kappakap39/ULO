package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
//import com.eaf.ulo.pl.eai.util.EAILogic;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class EAIServiceMessage implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {		
		PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(request);		
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);		
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();			
			personM.setXrulesVerification(xrulesVerM);
		}		
//		EAILogic eaiLogic = new EAILogic();
//		if(eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceCisNo())
//			||eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceAmcTamc())
//				||eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceBankRuptcy())
//					||eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceBlockcode())
//						||eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceclassifyLevel())
//							||eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceNplLpm())
//				){
//			return PLXrulesConstant.EAITFB0137I01_ERROR;
//		}
		return "";
	}

}
