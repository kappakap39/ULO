package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import com.ava.bpm.api.process.WorkflowAPIService;
import com.ava.bpm.api.process.WorkflowProcessAPI;
import com.ava.bpm.model.response.WorkflowResponse;
import com.ava.bpm.util.ResultCodeConstant;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.ERROR;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.BusinessClassManager;
import com.eaf.orig.ulo.pl.app.utility.DisplayMatrixTool;
import com.eaf.orig.ulo.pl.app.utility.MapDefaultDataM;
import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class LoadSearchBundlingCCWebAction extends WebActionHelper implements WebAction{

	Logger logger = Logger.getLogger(this.getClass().getName());
	private int eventType;
	String nextAction;
	@Override
	public Event toEvent() {
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if (null == userM) {
			userM = new UserDetailM();
		}
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		String userName = userM.getUserName();
		
		applicationM.setUpdateBy(userName);
		applicationM.setCaLastId(userName);
		
		PLApplicationEvent event = new PLApplicationEvent(eventType,applicationM, userM);		
		return event;
	}

	@Override
	public boolean requiredModelRequest() {
		return true;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return defaultProcessResponse(response);
	}
	private boolean result = true;
	@Override
	public boolean preModelRequest(){
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");	
		
//		logger.debug("@@@@@..Begin load search bundle CC application");
		
		String appRecordID = getRequest().getParameter("appRecordID");
		
		logger.debug("Load Application WebAction ... AppRecordID >> "+appRecordID);		
		
		if(OrigUtil.isEmptyString(appRecordID)){
			logger.warn("Empty AppRecID Cannot Claim Application.. !!");
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error();
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			return false;
		}		
		try{		
			UtilityDAO utilDAO = new UtilityDAOImpl();
			String JOBSTATE = utilDAO.getJobState(appRecordID);
			
			logger.debug("JOBSTATE >> "+JOBSTATE);	
			
			if(WorkflowConstant.JobState.CA_BUNDLE_CQ.equals(JOBSTATE)
					|| WorkflowConstant.JobState.CA_BUNDLE_CQ_ESCALATE.equals(JOBSTATE)){
				Process(appRecordID,userM);		
			}else{
				String MESSAGE = createMessage(getRequest(),utilDAO.getApplicationNo(appRecordID),ERROR.MSG_WORKING_APPLICATION);
				SearchHandler.PushErrorMessage(getRequest(),MESSAGE);
				result = false;
			}
			
		}catch(Exception e){
			nextAction="page=CA_SEARCH_BUNDLE_SCREEN";
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			result = false;		
		}		
		return result;
	}
	
	public synchronized void Process(String appRecordID ,UserDetailM userM) throws Exception{
				
		PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();
		
		WorkflowResponse response = origBean.ClaimWorkQueueApplication(appRecordID, userM);	
		PLApplicationDataM applicationM = null;
		if(null != response && ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())){
			
			PLOrigFormHandler origForm = new PLOrigFormHandler();	
			
			applicationM = origBean.loadPLApplicationDataM(appRecordID);
			
			applicationM.setAppRecordID(appRecordID);
		
			WorkflowProcessAPI wfAPI = WorkflowAPIService.getWorkflowProcessAPI();
			applicationM.setPtID(wfAPI.GetProcessTemplate(applicationM.getBusinessClassId(), getWorkFlowConnection()));
	
			applicationM.setPtType(OrigConstant.Action.PULL);
			applicationM.setAppDecision(OrigConstant.Action.PULL);
			applicationM.setOwner(userM.getUserName());
			
			if(!OrigConstant.ApplicationStatus.CREDIT_VERIFYINGEDIT.equals(applicationM.getApplicationStatus())){
				applicationM.setApplicationStatus(null);
			}
			/**set MatrixServiceID*/
			String searchType = (String) getRequest().getSession().getAttribute("searchType");			
			ProcessMenuM currentMenuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");			
			if(null == currentMenuM) currentMenuM = new ProcessMenuM();
			
			DisplayMatrixTool matrix = new DisplayMatrixTool();			
			applicationM.setMatrixServiceID(matrix.getMatrixServiceID(userM, applicationM, currentMenuM, searchType));
			MapDefaultDataM.map(applicationM);
			eventType = PLApplicationEvent.CA_SUBMIT;
			origForm.setAppForm(applicationM);
			
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGForm, origForm);
			
			result = true;
			
		}else{				
			nextAction="page=CA_SEARCH_BUNDLE_SCREEN";		
			result = false;		
			UtilityDAO utilDAO = new UtilityDAOImpl();	
			String MESSAGE = createMessage(getRequest(),utilDAO.getApplicationNo(appRecordID),ERROR.MSG_CANNOT_CLAIM);
			SearchHandler.PushErrorMessage(getRequest(),MESSAGE);
		}
	}
	
	public String createMessage(HttpServletRequest request,String appNo,String message){
		StringBuilder STR = new StringBuilder("");
			STR.append(ErrorUtil.getShortErrorMessage(request, "APP_NO"));
			STR.append(" ");
			if(null != appNo){
				STR.append(appNo);
				STR.append(" ");
			}			
			STR.append(ErrorUtil.getShortErrorMessage(request,message));
		return STR.toString();
	}
	
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	
	public String getNextActionParameter(){
		return  nextAction;
	}
	
	@Override
	protected void doSuccess(EventResponse erp) {
		PLOrigFormHandler origForm = (PLOrigFormHandler) getRequest().getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		PLApplicationDataM applicationM = origForm.getAppForm();
		if(applicationM == null){
			applicationM = new PLApplicationDataM();
		}
		WorkflowResponse response = null;
		
		try{			
			
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");

			PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();

			response = origBean.ClaimWorkQueueApplication(applicationM.getAppRecordID(), userM);	
			
			if(null != response && ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())){	
				
				logger.debug("Claim Application Success!! >> Next Step Load Application..");
				
				this.LoadApplication(applicationM.getAppRecordID(), OrigConstant.CUSTOMER_TYPE_INDIVIDUAL, null, userM ,response);
								
				nextAction = "page=PL_MAIN_APPFORM";
				
			}else{
				if(null == response){
					response = new WorkflowResponse();
				}
				logger.debug("Cannot Claim Application >> "+response.getResultDesc());
//				String message = ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR");
				String MSG = Message.error();
				if(!OrigUtil.isEmptyString(response.getResultDesc())){
					MSG = response.getResultDesc();
				}			
				this.RemoveSession();
				SearchHandler.PushErrorMessage(getRequest(),MSG);
				nextAction = "page=CA_SEARCH_BUNDLE_SCREEN";
			}			
			
		}catch(Exception e){
//			logger.fatal("Exception ", e);
			this.RemoveSession();
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			nextAction = "page=CA_SEARCH_BUNDLE_SCREEN";
		}
	}
	
	public void LoadApplication(String appRecordID ,String customerType ,String roleElement , UserDetailM userM ,WorkflowResponse response) throws Exception{
		
		Vector userRoles = userM.getRoles();
		String formID = null;
		String currentTab = "MAIN_TAB";
		String drawDownFlag = null;		
		String role  = null;
		
		try{		
			PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();	
			
			PLOrigFormHandler formHandler = new PLOrigFormHandler();
			PLApplicationDataM applicationM = formHandler.getAppForm();		
			if(null == applicationM){
				applicationM = new PLApplicationDataM();
				formHandler.setAppForm(applicationM);
			}
				
			applicationM = origBean.loadPLApplicationDataM(appRecordID);
			applicationM.setClaimDate(new Timestamp(System.currentTimeMillis()));
			applicationM.setJobID(response.getJobId());
			applicationM.setPtID(response.getPtid());
			applicationM.setPtType(response.getPtType());
		
//			#septemwi set last jobstate and application status
			applicationM.setLastAppStatus(applicationM.getApplicationStatus());
			applicationM.setLastJobState(applicationM.getJobState());
			
			PLPersonalInfoDataM personalInfoM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);				
			
			if(!OrigUtil.isEmptyString(customerType)){
				personalInfoM.setCustomerType(customerType);	
			}
			
			if(OrigUtil.isEmptyString(personalInfoM.getCustomerType())){
				personalInfoM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
			}	
			
			formID = ORIGFormUtil.getFormIDByBus(applicationM.getBusinessClassId());	
			
			if(!OrigUtil.isEmptyString(roleElement)){
				userM.setInsertElementRole(roleElement);
			}	
			
			role = userM.getCurrentRole();
			
			if(OrigUtil.isEmptyString(applicationM.getJobState())) {
				applicationM.setJobState(WorkflowConstant.JobState.CA_BUNDLE_IQ);
			}
			
			logger.debug("FormID >> "+formID+" Role "+role+" Job State "+applicationM.getJobState()+" BusinessClass "+applicationM.getBusinessClassId());
			
	 		BusinessClassManager busClassM = new BusinessClassManager();
	 		String bussclassID = applicationM.getBusinessClassId();
	 		String value[] = bussclassID.split("_");
			String result[] = busClassM.findAppValue(value[0]).split("_");
							
			applicationM.setProductDomain(result[0]);				
			applicationM.setProductGroup(result[1]);
			applicationM.setProductFamily(result[2]);		 		
			applicationM.setProduct(value[1]);				
			applicationM.setSaleType(value[2]);
			
			
			if(!OrigUtil.isEmptyString(role) && OrigConstant.ROLE_CA.equals(role)){
				PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();				
				CapportGroupDataM capportGroupM = appUtil.getCapportGroup(applicationM);
				getRequest().getSession(true).setAttribute("PL_CAPPORT",capportGroupM);
	
				ProcessMenuM menuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
				if(menuM == null) menuM = new ProcessMenuM();
				if(applicationM.getFinalCreditLimit()!=null && applicationM.getRecommentCreditLine() != null
						&& applicationM.getFinalCreditLimit().compareTo(applicationM.getRecommentCreditLine()) <=0){
					appUtil.removeORFinalCreditLine(applicationM);
				}
				OrigApplicationUtil origAppUtil = new OrigApplicationUtil();
				applicationM = origAppUtil.defaultCaDecision(userM, menuM, applicationM);
				
			}
			
			/**set MatrixServiceID*/
			String searchType = (String) getRequest().getSession().getAttribute("searchType");			
			ProcessMenuM currentMenuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");			
			if(null == currentMenuM) currentMenuM = new ProcessMenuM();
			
			DisplayMatrixTool matrix = new DisplayMatrixTool();			
			applicationM.setMatrixServiceID(matrix.getMatrixServiceID(userM, applicationM, currentMenuM, searchType));
			MapDefaultDataM.map(applicationM);
			
			formHandler.setAppForm(applicationM);
			
//			formHandler.getSubForms().clear();
			formHandler.setLoadedSubForms(false);
			
			formHandler.loadSubFormsForDrawDown(userRoles, formID, drawDownFlag);
			formHandler.setCurrentTab(currentTab);
			formHandler.setFormID(formID);
						
	//		Clone Session
			PLApplicationDataM cloanAppM = (PLApplicationDataM)SerializeUtil.clone(formHandler.getAppForm());			
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.CloanPlApplication,cloanAppM);
			
	//		Clone Session Sensitive
			PLApplicationDataM sensitiveappM = (PLApplicationDataM)SerializeUtil.clone(formHandler.getAppForm());
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGSensitive,sensitiveappM);
						
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGForm, formHandler);		
			
		}catch(Exception e){
//			logger.fatal("Exception >> ",e);
			nextAction = "page=CA_SEARCH_BUNDLE_SCREEN";
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			this.RemoveSession();
			throw new Exception(e.getMessage());
		}
	}	
	
	@Override
	protected void doFail(EventResponse erp){
		logger.debug("[doFail]");
		this.RemoveSession();
		try{
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			ORIGApplicationManager origBean = ORIGEJBService.getApplicationManager();
				origBean.cancleclaimByUserId(userM.getUserName());
		}catch (Exception e) {
			logger.fatal("Exception >> ",e);
		}
//		SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		SearchHandler.PushErrorMessage(getRequest(), erp.getMessage());
		nextAction="page=CA_SEARCH_BUNDLE_SCREEN";
	}
	
	private Connection getWorkFlowConnection() {
		try{
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			return origService.getConnection(OrigServiceLocator.WORKFLOW_DB);
		}catch(Exception e){
			logger.fatal("Connection is null"+e.getMessage());
		}
		return null;
	}
	
	public void RemoveSession(){
		getRequest().getSession().removeAttribute(PLOrigFormHandler.CloanPlApplication);
		getRequest().getSession().removeAttribute(PLOrigFormHandler.PLORIGSensitive);
		getRequest().getSession().removeAttribute(PLOrigFormHandler.PLORIGForm);
		getRequest().getSession().removeAttribute("PL_CAPPORT");		
	}
	
	@Override
	public boolean getCSRFToken(){
		return false;
	}
}
