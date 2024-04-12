package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.ava.bpm.model.response.WorkflowResponse;
import com.ava.bpm.util.ResultCodeConstant;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.SerializeUtil;
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
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class CreateICDCWebAction extends WebActionHelper implements WebAction {
	
	static Logger logger = Logger.getLogger(CreateICDCWebAction.class);
	
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
		applicationM.setDcLastId(userName);	
		applicationM.setOwner(userName);
		PLApplicationEvent event = new PLApplicationEvent(eventType,applicationM, userM);		
		return event;
	}

	@Override
	public boolean requiredModelRequest(){
		return true;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return defaultProcessResponse(response);
	}

	@Override
	public boolean preModelRequest() {
		
		String cardNo = getRequest().getParameter("card_no");
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		if(formHandler == null ){
			formHandler = new PLOrigFormHandler();
			getRequest().getSession().setAttribute(OrigConstant.PL_FORMHANDLER_NAME, formHandler);
		}
		PLApplicationDataM applicationM = formHandler.getAppForm();
		if(applicationM == null){
			applicationM = new PLApplicationDataM();
			formHandler.setAppForm(applicationM);
		}
		
		applicationM.setBusinessClassId(OrigConstant.BusClass.FCP_KEC_IC);
		applicationM.setICDCFlag(OrigConstant.FLAG_Y);
		applicationM.setLifeCycle(1);
		applicationM.setAppDate(new Timestamp(new Date().getTime()));
		
		applicationM.setAppDecision(OrigConstant.Action.START_APPLICATION_ICDC);
		
		try{
			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
			
			//load account card
			PLAccountCardDataM accCardM = origBean.loadAccountCardByCardNo(cardNo);
			
			//if not found card data return error
			if(accCardM == null){
				SearchHandler.PushErrorMessage(getRequest(),"*" + ErrorUtil.getShortErrorMessage(getRequest(),"NOT_FOUND_CARD_NO"));
				return false;
			}else{
				//find citizen id from card no
				String citizenId = origBean.loadCitizenNoByCardNo(cardNo);
				
				//if found citizen id
				if(!OrigUtil.isEmptyString(citizenId)){
					//initial personal data
					PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
					
					personalM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
					personalM.setIdNo(citizenId);
					
					PLAccountCardDataM acCardM = origBean.loadAccountCardByCardNo(cardNo);
					//initial card data
					PLCardDataM cardM = new PLCardDataM();
						cardM.setCardNo(acCardM.getCardNo());
						cardM.setCardFace(acCardM.getCardFace());
						cardM.setCardType(acCardM.getCardType());
						cardM.setEmbossName(acCardM.getEmbossName());
					personalM.setCardInformation(cardM);
					
					//load account data for project code
					PLAccountDataM accountM = origBean.loadAccountNoCardData(acCardM.getAccountId());
					applicationM.setProjectCode(accountM.getProjectCode());

					getRequest().getSession().setAttribute("REFRESH_ICDC", "Y");
					
					eventType = PLApplicationEvent.DC_CREATE_ICDC;
					
				}else{
					SearchHandler.PushErrorMessage(getRequest(),"Not found citizen id for increase/decrease.");
					return false;
				}
				return true;
			}
		}catch (Exception e){
			logger.fatal("Exception >> ",e);
			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			return false;
		}
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
				
				this.LoadApplication(applicationM.getAppRecordID(), OrigConstant.CUSTOMER_TYPE_INDIVIDUAL, null, userM ,response, erp.getEncapData());
								
				nextAction = "page=PL_MAIN_APPFORM";
				
			}else{
				if(null == response){
					response = new WorkflowResponse();
				}
				logger.debug("Cannot Claim Application >> "+response.getResultDesc());
				String message = ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR");
				if(!OrigUtil.isEmptyString(response.getResultDesc())){
					message = response.getResultDesc();
				}			
				this.RemoveSession();
				SearchHandler.PushErrorMessage(getRequest(),message);
				nextAction = "page=DC_CREATE_ICDC_SCREEN";
			}			
			
		}catch(Exception e){
			logger.fatal("Exception ", e);
			this.RemoveSession();
			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			nextAction = "page=DC_CREATE_ICDC_SCREEN";
		}
	}

	@Override
	protected void doFail(EventResponse erp) {
		logger.debug("[doFail]");
		this.RemoveSession();
		SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		nextAction="page=DC_CREATE_ICDC_SCREEN";
	}
	
	
	public void LoadApplication(String appRecordID ,String customerType ,String roleElement , UserDetailM userM ,WorkflowResponse response, Object idNo) throws Exception{
		
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
			personalInfoM.setIdNo((String)idNo);
			
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
				applicationM.setJobState(WorkflowConstant.JobState.DC_ICDC_IQ);
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
				if(applicationM.getFinalCreditLimit() == null || applicationM.getFinalCreditLimit().doubleValue() == 0){
					applicationM.setFinalCreditLimit(applicationM.getRecommentCreditLine());
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
			logger.fatal("Exception >> ",e);
			nextAction = "page=DC_CREATE_ICDC_SCREEN";
			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
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
		
	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
