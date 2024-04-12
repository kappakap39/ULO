package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class CBSendBackWebAction extends WebActionHelper implements WebAction {
	
	PLApplicationDataM applicationM = new PLApplicationDataM();
	UserDetailM userM = new UserDetailM();
	String supRole = null;

	@Override
	public Event toEvent() {
		PLApplicationEvent appEvent = new PLApplicationEvent();
		appEvent.setObject(applicationM);
		appEvent.setEventType(PLApplicationEvent.CB_SEND_BACK);
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
	public boolean preModelRequest() {
		
		String appRecId = getRequest().getParameter("appRecId");
		String reasonCode = getRequest().getParameter("sendReasonCode");
		String remark = getRequest().getParameter("sendRemark");
		String searchType = (String) getRequest().getSession().getAttribute("searchType");
		
		userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		applicationM = PLORIGEJBService.getORIGDAOUtilLocal().loadOrigApplication(appRecId);
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		
//		if(OrigUtil.isEmptyObject(personalM)){
//			personalM = new PLPersonalInfoDataM();
//			Vector<PLPersonalInfoDataM> perVect = new Vector<PLPersonalInfoDataM>();
//			perVect.add(personalM);
//			applicationM.setPersonalInfoVect(perVect);
//		}
		
		PLXRulesVerificationResultDataM verificationM = personalM.getXrulesVerification();
		if(OrigUtil.isEmptyObject(verificationM)){
			verificationM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(verificationM);
		}
		
		applicationM.setAppDecision(OrigConstant.wfProcessState.SENDX);
		
		Vector<PLReasonDataM> reasonVect = new Vector<PLReasonDataM>();
		PLReasonDataM reasonM = new PLReasonDataM();
		
		reasonM.setReasonType("82");
		reasonM.setReasonCode(reasonCode);
		reasonM.setRemark(remark);
		reasonM.setApplicationRecordId(appRecId);
		reasonM.setRole(userM.getCurrentRole());
		reasonM.setCreateBy(userM.getUserName());
		
		reasonVect.add(reasonM);
		
		applicationM.setReasonVect(reasonVect);
//		applicationM.setCreateBy(userM.getUserName());
		applicationM.setUpdateBy(userM.getUserName());
		applicationM.setReasonVect(reasonVect);
		applicationM.setAppRecordID(appRecId);
		applicationM.setApplicationStatus(null);
		applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));

		verificationM.setNCBResult("Send Back");
		verificationM.setNCBCode("SB");
		
		WebActionUtil webUtil = new WebActionUtil();
		webUtil.getAction(applicationM, userM, null, searchType);
		
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	@Override
	public String getNextActionParameter() {
		return "action=CBPLInbox&searchType="+OrigConstant.SEARCH_TYPE_INBOX+"&role="+userM.getCurrentRole();
	}
	
	@Override
	protected void doSuccess(EventResponse erp){
		getRequest().getSession().removeAttribute(ORIGInboxDataM.Constant.ORIG_INBOX);		
	}

	@Override
	protected void doFail(EventResponse erp){
//		SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		SearchHandler.PushErrorMessage(getRequest(), erp.getMessage());	
	}
	
	@Override
	public boolean getCSRFToken(){
		return true;
	}

}
