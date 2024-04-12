package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Timestamp;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.ava.bpm.model.response.WorkflowResponse;
import com.ava.bpm.util.ResultCodeConstant;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.BusinessClassManager;
import com.eaf.orig.ulo.pl.app.utility.DisplayMatrixTool;
import com.eaf.orig.ulo.pl.app.utility.MapDefaultDataM;
import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class LoadMainPLReopenApplicationWebAction extends WebActionHelper implements WebAction {
	
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
		PLApplicationDataM plApplicationDataM = formHandler.getAppForm();
		String userName = userM.getUserName();
		
		plApplicationDataM.setUpdateBy(userName);
		plApplicationDataM.setCaLastId(userName);
		
		PLApplicationEvent event = new PLApplicationEvent(eventType,plApplicationDataM, userM);		
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

	@Override
	public boolean preModelRequest() {
		
		logger.debug("@@@@@..Begin reopen application");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if (null == userM) {
			userM = new UserDetailM();
		}
		
		String appRecordID = getRequest().getParameter("appRecordID");
		
		logger.debug("@@@@@..AppRecordID "+appRecordID);
	
		PLOrigFormHandler plOrigForm = new PLOrigFormHandler();
		PLORIGApplicationManager plApplicationManager = PLORIGEJBService.getPLORIGApplicationManager();
		PLApplicationDataM plApplicationM = plApplicationManager.loadPLApplicationDataM(appRecordID);
		
		plApplicationM.setAppRecordID(appRecordID);
		plApplicationM.setReopenFlag(OrigConstant.FLAG_Y);
		plApplicationM.setLifeCycle(plApplicationM.getLifeCycle()+1);
		plApplicationM.setAppDecision(OrigConstant.Action.REOPEN);

		//Clear final app decision
		plApplicationM.setFinalAppDecision(null);
		plApplicationM.setFinalAppDecisionBy(null);
		plApplicationM.setFinalAppDecisionDate(null);
		
		eventType = PLApplicationEvent.SUBMIT_REOPEN;
		plOrigForm.setAppForm(plApplicationM);
		getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGForm, plOrigForm);
				
		return true;
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
		//super.doSuccess(erp);		
		PLOrigFormHandler origForm = (PLOrigFormHandler) getRequest().getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		
		WorkflowResponse workFlowResponse = new WorkflowResponse();
		String formID = null;
		String currentTab = "MAIN_TAB";
		String drawDownFlag = null;		
		String role  = null;
		
		PLApplicationDataM applicationM = origForm.getAppForm();		
		if(applicationM == null){
			applicationM = new PLApplicationDataM();			
		}
		
		try{			
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");			
			Vector userRoles = userM.getRoles();
			
			logger.debug("@@@@@ .. Claim Application.");

			PLORIGApplicationManager plApplicationManager = PLORIGEJBService.getPLORIGApplicationManager();			
			workFlowResponse = plApplicationManager.claimApplication(applicationM,userM);
			
			if (ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(workFlowResponse.getResultCode())){
				
				logger.debug("@@@@@ Claim success, Start load application.");
				
				applicationM.setClaimDate(new Timestamp(System.currentTimeMillis()));
				applicationM.setJobID(workFlowResponse.getJobId());
				applicationM.setPtID(workFlowResponse.getPtid());
				applicationM.setPtType(workFlowResponse.getPtType());
				
				role = userM.getCurrentRole();
				
				if(ORIGUtility.isEmptyString(applicationM.getJobState())) 
					applicationM.setJobState(WorkflowConstant.JobState.CA_IQ);
				
				formID = ORIGFormUtil.getFormIDByBus(applicationM.getBusinessClassId());
				
				logger.debug("@@@@@ ..FormID "+formID);
				logger.debug("@@@@@ ..Role "+role);
				logger.debug("@@@@@ ..Job State "+applicationM.getJobState());
				
				/**Find Product Domain Group*/
				
		 		BusinessClassManager busClassM = new BusinessClassManager();
		 		String value[] = (applicationM.getBusinessClassId()).split("_");
				String result[] = (busClassM.findAppValue(value[0])).split("_");				
				
				applicationM.setProductDomain(result[0]);				
				applicationM.setProductGroup(result[1]);
				applicationM.setProductFamily(result[2]);
		 		
				applicationM.setProduct(value[1]);				
				applicationM.setSaleType(value[2]);
				
				/**set MatrixServiceID*/
				String searchType = (String) getRequest().getSession().getAttribute("searchType");			
				ProcessMenuM currentMenuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");			
				if(null == currentMenuM) currentMenuM = new ProcessMenuM();
				
				DisplayMatrixTool matrix = new DisplayMatrixTool();			
				applicationM.setMatrixServiceID(matrix.getMatrixServiceID(userM, applicationM, currentMenuM, searchType));
				
				/**End*/
				MapDefaultDataM.map(applicationM);
				
				origForm.setAppForm(applicationM);
				
//				plOrigForm.getSubForms().clear();
				origForm.setLoadedSubForms(false);
				
				origForm.loadSubFormsForDrawDown(userRoles, formID, drawDownFlag);
				origForm.setCurrentTab(currentTab);
				origForm.setFormID(formID);
				origForm.setAppForm(applicationM);
				
//				logger.debug("[LoadMainPLApplicationWebAction]..getSubForms() "+plOrigForm.getSubForms());
			}
			
			
			if(OrigConstant.ROLE_CA.equals(role)){
				PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
				try{
					CapportGroupDataM capportGroupM = appUtil.getCapportGroup(applicationM);
					logger.debug("@@@@@ capportFlag :"+capportGroupM.isAlertFlag());
					getRequest().getSession(true).setAttribute("PL_CAPPORT",capportGroupM);
				}catch (Exception e){
					throw new Exception(e.getMessage());
				}
			}
			
			// clone session
			PLApplicationDataM cloanApplicationM = (PLApplicationDataM)SerializeUtil.clone(origForm.getAppForm());
			
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.CloanPlApplication,cloanApplicationM);
						
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGForm, origForm);			

			logger.debug("@@@@@ ..End reopen application");
			
			nextAction="page=PL_MAIN_APPFORM";				
		}catch (Exception e) {
			logger.fatal("LoadMainPLApplicationWebAction]..Error ",e);
			e.printStackTrace();
			origForm.getErrors().put("LoadReopenAppError", "Load application error, please contact Administrator");
			nextAction="page=SEARCH_REOPEN_SCREEN";
		}
	}

	@Override
	protected void doFail(EventResponse erp) {
		//super.doFail(erp);
		PLOrigFormHandler plOrigForm = (PLOrigFormHandler) getRequest().getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		plOrigForm.getErrors().put("IniteFlowError", erp.getMessage());
		nextAction="page=SEARCH_REOPEN_SCREEN";
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
