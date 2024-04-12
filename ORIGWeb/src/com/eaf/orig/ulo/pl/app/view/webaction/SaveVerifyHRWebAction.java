package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SaveVerifyHRWebAction extends WebActionHelper implements WebAction{
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest() {
		try{
			
			UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}
			String finalStaus = getRequest().getParameter("verhr-final-staus");
			
			JsonObjectUtil jObjectUtil = new JsonObjectUtil();
			
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();		
			
			logger.debug("[preModelRequest] finalStatus "+finalStaus);	
			
			String verHrResultField = "result_"+PLXrulesConstant.ModuleService.VERIFY_HR;
			String verHrCodeField = "code_"+PLXrulesConstant.ModuleService.VERIFY_HR;
			
				xrulesVerM.setVerHRResult(cacheUtil.getNaosCacheDisplayNameDataM(71, finalStaus));
				xrulesVerM.setVerHRResultCode(finalStaus);
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicy = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
//			if(xrulesPolicy==null){
//				 xrulesPolicy=new PLXRulesPolicyRulesDataM();
//				 xrulesPolicy.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(xrulesPolicy);
//			}
			xrulesPolicy.setResultCode(xrulesVerM.getVerHRResultCode());
			xrulesPolicy.setResultDesc(xrulesVerM.getVerHRResult());	
			xrulesPolicy.setUpdateBy(userM.getUserName());					 
			/*End Set Policy Rules */	
				
				
			jObjectUtil.CreateJsonObject(verHrCodeField,finalStaus);
			jObjectUtil.CreateJsonObject(verHrResultField,cacheUtil.getNaosCacheDisplayNameDataM(71, finalStaus));
			jObjectUtil.CreateJsonObject("STYLE",ORIGLogic.LogicColorStyleResult(finalStaus, ""));	
			jObjectUtil.ResponseJsonArray(getResponse());
		
		}catch (Exception e) {
			logger.fatal("Error ",e);
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
