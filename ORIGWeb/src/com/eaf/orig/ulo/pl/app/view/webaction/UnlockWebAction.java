package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Connection;
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
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationLogDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.orig.bpm.proxy.BpmProxyService;
import com.orig.bpm.ulo.model.WorkflowDataM;
import com.orig.bpm.ulo.proxy.BpmWorkflow;

public class UnlockWebAction extends WebActionHelper implements WebAction {
	
	static Logger log = Logger.getLogger(UnlockWebAction.class);
	
	Vector<PLApplicationDataM> appDataVect = new Vector<PLApplicationDataM>();
	UserDetailM userM = new UserDetailM();

	@Override
	public Event toEvent() {
		PLApplicationEvent appEvent = new PLApplicationEvent();
		appEvent.setObject(appDataVect);
		appEvent.setEventType(PLApplicationEvent.DE_UNBLOCK);
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
		userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");		
		String unBlockAppRecID = getRequest().getParameter("UnblockAppRecId");
		String bAppRecID = getRequest().getParameter("BlockAppRecId");	
		if(null == unBlockAppRecID){
			log.debug("ERROR : NotFound AppRecID for Unlock Application !!");
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error();
			SearchHandler.PushErrorMessage(getRequest(), MSG);	
			return false;
		}
		try{
			doSelectApplication(unBlockAppRecID);
			doCancleApplication(bAppRecID);
		}catch(Exception e){
//			log.fatal("Exception ",e);
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);	
			return false;
		}
		return true;
	}
	
	public void doSelectApplication(String appRecID) throws Exception{
		
		log.debug("doSelectApplication AppRecID >> "+appRecID);
		
		//load appInFo
		ORIGDAOUtilLocal origUtil = PLORIGEJBService.getORIGDAOUtilLocal();			
		PLApplicationDataM applicationM = origUtil.loadOrigApplication(appRecID);

		applicationM.setAppRecordID(appRecID);
		applicationM.setAppDecision(OrigConstant.Action.UNBLOCK);
		applicationM.setUpdateBy(userM.getUserName());
		applicationM.setApplicationStatus(null);
		
		Vector<PLApplicationLogDataM> appLogVect = new Vector<PLApplicationLogDataM>();
		PLApplicationLogDataM appLogM = new PLApplicationLogDataM();
				appLogM.setApplicationRecordID(appRecID);
		applicationM.setBlockFlag(null);
		
		appLogM.setUpdateBy(userM.getUserName());
		appLogM.setCreateBy(userM.getUserName());
		appLogVect.add(appLogM);
		
		applicationM.setApplicationLogVect(appLogVect);
		applicationM.setUnLock(OrigConstant.FLAG_Y);
		
		//clear data
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);

		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(OrigUtil.isEmptyObject(xrulesVerM)){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		xrulesVerM.setDedupResult(null);
		xrulesVerM.setDedupUpdateBy(null);
		xrulesVerM.setDedupUpdateDate(null);
		xrulesVerM.setDedupUpdateRole(null);
		xrulesVerM.setDedupVehicleResult(null);
		xrulesVerM.setDedupVehicleUpdateRole(null);
		
//		#SeptemWi Comment
//		Vector<PLXRulesDedupDataM> deDupVect = xrulesVerM.getVXRulesDedupDataM();			
//		XRulesRemoteDAOUtilManager xrulesBean = PLORIGEJBService.getXRulesDAOUtilManager();			
//			xrulesBean.daleteTableDeDup(deDupVect);

		applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
		appDataVect.add(applicationM);
	}
	
	public void doCancleApplication(String oAppRecID) throws Exception{
		PLApplicationDataM bApplicationM = null;
		Vector<PLApplicationLogDataM> bAppLogVect = null;
		String bAppRecIDArr[] = oAppRecID.split(",");
		WorkflowDataM workflowM = null;
		UserDetailM bUserM = null;
		if(null != bAppRecIDArr){
			ORIGDAOUtilLocal origUtil = PLORIGEJBService.getORIGDAOUtilLocal();	
			BpmWorkflow bpmWf = BpmProxyService.getBpmWorkflow();	
			//WebActionUtil webU = new WebActionUtil();
			
			for(String appRecID : bAppRecIDArr){
				
				workflowM = new WorkflowDataM();
				workflowM.setAppRecordID(appRecID);
				String lastOwner = bpmWf.GetLastBlockOwner(workflowM,getConnection());
				
				if(OrigUtil.isEmptyString(lastOwner)){
					lastOwner = userM.getUserName();
				}
				
				log.debug("doCancleApplication AppRecID >> "+appRecID);
				log.debug("doCancleApplication LastOwner >> "+lastOwner);
				
				bApplicationM = origUtil.loadAppInFo(appRecID);
				
				bApplicationM.setAppRecordID(appRecID);
				bApplicationM.setBlockFlag(OrigConstant.FLAG_C);
				bApplicationM.setAppDecision(OrigConstant.Action.CANCEL);
				bApplicationM.setUpdateBy(userM.getUserName());
				bApplicationM.setApplicationStatus(null);
				bApplicationM.setOwner(lastOwner);
				
				bAppLogVect = new Vector<PLApplicationLogDataM>();
				PLApplicationLogDataM bappLogM = new PLApplicationLogDataM();
					bappLogM.setApplicationRecordID(appRecID);
					bappLogM.setUpdateBy(userM.getUserName());
					bappLogM.setCreateBy(userM.getUserName());
					bAppLogVect.add(bappLogM);
				
				bApplicationM.setApplicationLogVect(bAppLogVect);
				
				bUserM = new UserDetailM();
				bUserM.setUserName(lastOwner);				
				
				//set final application decision #Praisan 20130426
				OrigApplicationUtil.getInstance().setFinalAppDecision(bApplicationM, bUserM);
				bApplicationM.setAppInfo(OrigUtil.getApplicatonXML(bApplicationM));				
				appDataVect.add(bApplicationM);				
			}
		}
	}
	
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	
	public String getNextActionParameter() {
		return "action=SearchInboxUnblockAction&role"+userM.getCurrentRole();
	}
	
	protected void doSuccess(EventResponse erp){
		
	}
	
	@Override
	protected void doFail(EventResponse erp) {
//		SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		SearchHandler.PushErrorMessage(getRequest(), erp.getMessage());	
	}
	
	@Override
	public boolean getCSRFToken(){
		return true;
	}
	private Connection getConnection(){
		try{
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			return origService.getConnection(OrigServiceLocator.WORKFLOW_DB);
		}catch(Exception e){
			log.fatal("Connection is null" + e.getMessage());
		}
		return null;
	}
}
