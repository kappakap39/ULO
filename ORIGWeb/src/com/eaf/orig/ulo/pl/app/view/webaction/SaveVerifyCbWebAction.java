package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.ValidationForm;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SaveVerifyCbWebAction extends WebActionHelper implements WebAction {

	PLApplicationDataM applicationM = new PLApplicationDataM();
	UserDetailM userM = new UserDetailM();
	
	static Logger logger = Logger.getLogger(SaveVerifyCbWebAction.class);
	
	@Override
	public Event toEvent() {
		PLApplicationEvent appEvent = new PLApplicationEvent();
		appEvent.setObject(applicationM);
		appEvent.setEventType(PLApplicationEvent.CB_VERIFY);
		appEvent.setUserM(userM);
		return appEvent;
	}

	@Override
	public boolean requiredModelRequest() {
		return true;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return defaultProcessResponse(response);
	}

	@Override
	public boolean preModelRequest(){
		
		String cbAction = getRequest().getParameter("param-action");
		String comment = getRequest().getParameter("param-comment");
		String appRecId = getRequest().getParameter("param-apprecid");
		
		String searchType = (String) getRequest().getSession().getAttribute("searchType");
		
		if(OrigUtil.isEmptyString(cbAction)){
			cbAction = OrigConstant.Action.SAVE_DRAFT;
		}
		
		try{		
			userM = (UserDetailM)getRequest().getSession().getAttribute("ORIGUser");
			
			ORIGDAOUtilLocal origBean  = PLORIGEJBService.getORIGDAOUtilLocal();			
			applicationM = origBean.loadOrigApplication(appRecId);
			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);

			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			if(OrigUtil.isEmptyObject(xrulesVerM)){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}
			
			xrulesVerM.setNcbSupComment(comment);
			
			PLXRulesPolicyRulesDataM policyRulesNCB = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BEGINNING_NCB_FICO);
			if(policyRulesNCB == null){
				policyRulesNCB = new PLXRulesPolicyRulesDataM();
				policyRulesNCB.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_BEGINNING_NCB_FICO);
				policyRulesNCB.setPersonalId(personalM.getPersonalID());
				policyRulesNCB.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));
				policyRulesNCB.setUpdateBy(userM.getUserName());
			}
			
			if(cbAction.equals(OrigConstant.Action.SEND_TO_CB)){
				xrulesVerM.setNCBResult("Request NCB Data");
				xrulesVerM.setNCBCode("RQ");
				policyRulesNCB.setResultCode("RQ");
				policyRulesNCB.setResultDesc("Request NCB Data");
			}else if(cbAction.equals(OrigConstant.Action.REJECT)){
				xrulesVerM.setNCBResult("Fail");
				xrulesVerM.setNCBCode("F");
				policyRulesNCB.setResultCode("F");
				policyRulesNCB.setResultDesc("Fail");
				
				applicationM.setJobType(OrigConstant.typeColor.typeRed);
				xrulesVerM.setSummaryOverideRuleCode(OrigConstant.SummaryOverideRuleCode.FAIL);
				xrulesVerM.setSummaryOverideRuleResult(OrigConstant.SummaryOverideRuleResult.FAIL);
				xrulesVerM.setSummaryOverideRuleUpdateBy(userM.getUserName());
				
				Vector<PLReasonDataM> reasonvt = new Vector<PLReasonDataM>();
				PLReasonDataM reasonM = new PLReasonDataM();
				reasonM.setApplicationRecordId(applicationM.getAppRecordID());
				reasonM.setReasonType(OrigConstant.fieldId.REJECT_REASON);
				reasonM.setReasonCode(OrigConstant.RejectReason.NCB_FICO_FAIL);
				reasonM.setRole(userM.getCurrentRole());
				reasonvt.add(reasonM);
				applicationM.setReasonVect(reasonvt);
				
			}else if(cbAction.equals(OrigConstant.wfProcessState.SENDX)){
				xrulesVerM.setNCBResult("Pass");
				xrulesVerM.setNCBCode("P");
				policyRulesNCB.setResultCode("P");
				policyRulesNCB.setResultDesc("Pass");
			}
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BEGINNING_NCB_FICO, policyRulesNCB);
			
			applicationM.setApplicationStatus(null);
			applicationM.setAppDecision(cbAction);
			applicationM.setCreateBy(userM.getUserName());
			applicationM.setUpdateBy(userM.getUserName());
			
			WebActionUtil webUtil = new WebActionUtil();
				webUtil.getAction(applicationM, userM, null, searchType);
		}catch(Exception e){
//			logger.fatal("Exception >> ",e);
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);	
			return false;
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	
	@Override
	public String getNextActionParameter(){
		String action = "action=SearchVerifyNCBWebAction";
		ValueListM valueM = (ValueListM)getRequest().getSession().getAttribute("VALUE_LIST");
		if(null != valueM && valueM.getAtPage() != 0){
			action = "action=ValueListWebAction&handleForm=N&atPage="+valueM.getAtPage();
		}
		return action;
	}

	@Override
	public boolean getCSRFToken() {
		return true;
	}
	
	@Override
	protected void doSuccess(EventResponse erp){
		
	}

	@Override
	protected void doFail(EventResponse erp){
//		SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		SearchHandler.PushErrorMessage(getRequest(), erp.getMessage());	
	}
	
	@Override
	public boolean validationForm() {
		return ValidationForm.getCbSupValidationForm(getRequest());
	}

}
