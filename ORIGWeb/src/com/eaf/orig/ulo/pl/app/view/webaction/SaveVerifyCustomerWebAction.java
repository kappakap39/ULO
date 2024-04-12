package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDataPhoneVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SaveVerifyCustomerWebAction  extends WebActionHelper implements WebAction{
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest(){
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest(){			
		try{			
			
			UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}
		
			Vector<PLXRulesDataPhoneVerificationDataM> xruleDataPhoneVect = xrulesVerM.getxRulesDataPhoneVerificationDataMs();	
			String tickValue = null;
			String reMark =  null;
			if(!ORIGUtility.isEmptyVector(xruleDataPhoneVect)){
				for(PLXRulesDataPhoneVerificationDataM xrulesPhoneM : xruleDataPhoneVect){
					tickValue = getRequest().getParameter("verdata-"+xrulesPhoneM.getFieldId());
					reMark = getRequest().getParameter("verdata-remark-"+xrulesPhoneM.getFieldId());
					xrulesPhoneM.setResult(tickValue);
					xrulesPhoneM.setRemark(reMark);
				}
			}
			
			JsonObjectUtil jObjectUtil = new JsonObjectUtil();
			
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			
			String finalStaus = getRequest().getParameter("vercus-final-status");
			
			logger.debug("[preModelRequest] finalStaus "+finalStaus);	
			
			String verCusResultField = "result_"+PLXrulesConstant.ModuleService.VERIFY_CUSTOMER;
			String verCusCodeField = "code_"+PLXrulesConstant.ModuleService.VERIFY_CUSTOMER;
			
			xrulesVerM.setVerCusResult(cacheUtil.getNaosCacheDisplayNameDataM(71, finalStaus));
			xrulesVerM.setVerCusResultCode(finalStaus);
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicy = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CUSTOMER);
//			if(xrulesPolicy==null){
//				 xrulesPolicy=new PLXRulesPolicyRulesDataM();
//				 xrulesPolicy.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_VERIFY_CUSTOMER);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(xrulesPolicy);
//			}
			xrulesPolicy.setResultCode(xrulesVerM.getVerCusResultCode());
			xrulesPolicy.setResultDesc(xrulesVerM.getVerCusResult());	
			xrulesPolicy.setUpdateBy(userM.getUserName());					 
			/*End Set Policy Rules */
			
			jObjectUtil.CreateJsonObject(verCusCodeField,finalStaus);
			jObjectUtil.CreateJsonObject(verCusResultField,cacheUtil.getNaosCacheDisplayNameDataM(71, finalStaus));
			jObjectUtil.CreateJsonObject("STYLE",ORIGLogic.LogicColorStyleResult(finalStaus, ""));	
			jObjectUtil.ResponseJsonArray(getResponse());
			
		} catch (Exception e) {
			logger.debug("Error ",e);
		}		
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
