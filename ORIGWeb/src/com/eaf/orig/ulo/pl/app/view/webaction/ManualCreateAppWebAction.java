package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Timestamp;
import java.util.Calendar;
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
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.BusinessClassManager;
import com.eaf.orig.ulo.pl.app.utility.DisplayMatrixTool;
import com.eaf.orig.ulo.pl.app.utility.MapDefaultDataM;
import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class ManualCreateAppWebAction extends WebActionHelper implements WebAction {

	Logger logger = Logger.getLogger(ManualCreateAppWebAction.class);

	private String nextAction = "";
	private PLOrigFormHandler origForm;
	private String customerType = null;
	PLApplicationDataM applicationM;

	@Override
	public Event toEvent() {

		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if (userM == null) {
			userM = new UserDetailM();
		}

		String userName = userM.getUserName();

		if (OrigUtil.isEmptyString(applicationM.getDeFirstId())) {
			applicationM.setDeFirstId(userName);
			applicationM.setDeStartDate(new Date());
			applicationM.setCheckFirstApp("Y");
		}

		if (OrigUtil.isEmptyString(applicationM.getCreateBy())) {
			applicationM.setCreateBy(userName);
			applicationM.setUpdateBy(userName);
		}else{
			applicationM.setUpdateBy(userName);
		}

		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		applicationM.setAppDate(currentTimestamp);

		applicationM.setLifeCycle(1);

		applicationM.setDeLastId(userName);

		applicationM.setOwner(userName);

		PLApplicationEvent event = new PLApplicationEvent(PLApplicationEvent.DE_CREDATE_MANUAL, applicationM, userM);

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
	public boolean preModelRequest(){		
		try{			
			String pDomain = getRequest().getParameter("productdomain");
			String pGroup = getRequest().getParameter("productgroup");
			String pFamily = getRequest().getParameter("productfamily");
			String product = getRequest().getParameter("product");
			String saleType = getRequest().getParameter("saleType");
			customerType = getRequest().getParameter("customertype");
			String currentTab = getRequest().getParameter("currentTab");
			String seller_branch = getRequest().getParameter("seller_branch_code");

			if(OrigUtil.isEmptyString(currentTab)){
				currentTab = "MAIN_TAB";
			}

			if(OrigUtil.isEmptyString(customerType)){
				customerType = OrigConstant.CUSTOMER_TYPE_INDIVIDUAL;
			}

			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");

			origForm = new PLOrigFormHandler();

			applicationM = origForm.getAppForm();

			if(applicationM == null){
				applicationM = new PLApplicationDataM();
			}

			applicationM.setProductDomain(pDomain);
			applicationM.setProductGroup(pGroup);
			applicationM.setProductFamily(pFamily);
			applicationM.setProduct(product);
			applicationM.setSaleType(saleType);
             
			PLSaleInfoDataM saleInfoM = applicationM.getSaleInfo();
			if (null == saleInfoM) {
				saleInfoM = new PLSaleInfoDataM();
				applicationM.setSaleInfo(saleInfoM);
			}
			if(!OrigUtil.isEmptyString(seller_branch)) {
				saleInfoM.setSalesBranchCode(seller_branch);
			}
            PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
            personalM.setCustomerType(customerType);
            
			logger.debug("Product Domain " + applicationM.getProductDomain());
			logger.debug("Product Group " + applicationM.getProductGroup());
			logger.debug("Product Family " + applicationM.getProductFamily());
			logger.debug("Product " + applicationM.getProduct());
			logger.debug("Sale Type " + applicationM.getSaleType());
			logger.debug("Customer Type " + personalM.getCustomerType());
			
			BusinessClassManager buinessClass = new BusinessClassManager();

			String busClass = buinessClass.findBus(pDomain, pGroup, pFamily, product, saleType);

			/** Initial Application **/
			if(!OrigUtil.isEmptyString(busClass)){
				applicationM.setBusinessClassId(busClass);
			}

			applicationM.setAppDate(new Timestamp(new Date().getTime()));
			
			ORIGGeneratorManager origGenBean = PLORIGEJBService.getGeneratorManager();
			
			String appNo = origGenBean.getApplicationNo(applicationM);		
			
			applicationM.setApplicationNo(appNo);
			applicationM.setDeFirstId(userM.getUserName());
			applicationM.setDeStartDate(new Date());
			applicationM.setDeLastDate(new Date());
									
			applicationM.setAppDecision(OrigConstant.Action.START_SHORT_FORM);
			applicationM.setPriority("0");

			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGForm, origForm);

		}catch(Exception e){
			logger.fatal("Exception >> ",e);
			nextAction = "page=ManualCreateApp";
			this.RemoveSession();
			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			return false;
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}

	@Override
	public String getNextActionParameter() {
		return nextAction;
	}

	@Override
	protected void doSuccess(EventResponse erp) {

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
				
				this.LoadApplication(applicationM.getAppRecordID(), customerType, null, userM ,response);
								
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
				nextAction = "page=ManualCreateApp";
			}			
		}catch(Exception e){
			logger.fatal("Exception ", e);
			this.RemoveSession();
			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			nextAction = "page=ManualCreateApp";
		}
	}

	@Override
	protected void doFail(EventResponse erp) {
		logger.debug("[doFail]");
		this.RemoveSession();
		SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		nextAction = "page=ManualCreateApp";
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
			nextAction = "page=ManualCreateApp";
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
		return true;
	}

}
