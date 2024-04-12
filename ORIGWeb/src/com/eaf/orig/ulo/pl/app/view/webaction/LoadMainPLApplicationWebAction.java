package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Timestamp;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.ava.bpm.model.response.WorkflowResponse;
import com.ava.bpm.util.ResultCodeConstant;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.BusinessClassManager;
import com.eaf.orig.ulo.pl.app.utility.DisplayMatrixTool;
import com.eaf.orig.ulo.pl.app.utility.MapDefaultDataM;
import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.app.utility.Performance;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class LoadMainPLApplicationWebAction extends WebActionHelper implements WebAction{	
	
	static Logger logger = Logger.getLogger(LoadMainPLApplicationWebAction.class);	
	
	Performance perf = new Performance("LoadMainPLApplicationWebAction",Performance.Module.LOAD_APPLICATION);	
	String tranID = perf.GenTrancationID();
	
	String nextAction = "action=FristPLApp";	
	
	@Override
	public Event toEvent(){
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
	public boolean preModelRequest(){
						
		String appRecordID = getRequest().getParameter("appRecordID");
		String roleElement = getRequest().getParameter("roleElement");
		String customerType = getRequest().getParameter("customerType");
		
		logger.debug("Load Application WebAction ... AppRecordID >> "+appRecordID);		
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");	
		
		perf.init("preModelRequest", appRecordID, tranID, userM);
		
		if(OrigUtil.isEmptyString(appRecordID)){
			logger.warn("Empty AppRecID Cannot Claim Application.. !!");			
//			#septem comment change ERROR Message!
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error();
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			return false;
		}
				
		WorkflowResponse response = null;
		
		PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();	
		
		try{	
			perf.track(Performance.Action.CLAIM, Performance.START);
			response = origBean.ClaimWorkQueueApplication(appRecordID, userM);	
			perf.track(Performance.Action.CLAIM, Performance.END);
			
			if(null != response && ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())){	
				
				logger.debug("Claim Application Success!! >> Next Step Load Application..");
				perf.track(Performance.Action.LOAD_APPLICATION, Performance.START);
				this.LoadApplication(appRecordID, customerType, roleElement, userM ,response);
				perf.track(Performance.Action.LOAD_APPLICATION, Performance.END);				
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
				SearchHandler.PushErrorMessage(getRequest(),MSG);
				return false;
			}			
		}catch(Exception e){
//			logger.fatal("Exception Load Application >> ",e);
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

	public String getNextActionParameter(){
		return  nextAction;
	}

	@Override
	public boolean getCSRFToken(){
		return true;
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
			applicationM.setTransactionID(tranID);
			
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
				applicationM.setJobState(WorkflowConstant.JobState.DE_IQ);
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
					
			/**set MatrixServiceID*/
			String searchType = (String) getRequest().getSession().getAttribute("searchType");			
			ProcessMenuM currentMenuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");			
			if(null == currentMenuM) currentMenuM = new ProcessMenuM();
			
			DisplayMatrixTool matrix = new DisplayMatrixTool();			
			applicationM.setMatrixServiceID(matrix.getMatrixServiceID(userM, applicationM, currentMenuM, searchType));
			
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
			logger.fatal("Load Application Error >> ",e);
			this.RemoveSession();
			throw new Exception(e.getMessage());
		}
	}
		
	public void RemoveSession(){
		getRequest().getSession().removeAttribute(PLOrigFormHandler.CloanPlApplication);
		getRequest().getSession().removeAttribute(PLOrigFormHandler.PLORIGSensitive);
		getRequest().getSession().removeAttribute(PLOrigFormHandler.PLORIGForm);
		getRequest().getSession().removeAttribute("PL_CAPPORT");		
	}	
	
}
