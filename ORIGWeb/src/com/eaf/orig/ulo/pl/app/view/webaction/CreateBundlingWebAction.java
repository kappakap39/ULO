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
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.BusinessClassManager;
import com.eaf.orig.ulo.pl.app.utility.DisplayMatrixTool;
import com.eaf.orig.ulo.pl.app.utility.MapDefaultDataM;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.app.utility.PLOrigUtility;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.control.event.PLApplicationEvent;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLBundleCCDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

public class CreateBundlingWebAction extends WebActionHelper implements WebAction{
	static Logger logger = Logger.getLogger(CreateBundlingWebAction.class);
	private int eventType;
	String nextAction;

	@Override
	public Event toEvent(){
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");		
		if(null == userM){
			userM = new UserDetailM();
		}		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		String userName = userM.getUserName();

		applicationM.setUpdateBy(userName);
		applicationM.setDcLastId(userName);
		
		PLApplicationEvent event = new PLApplicationEvent(eventType, applicationM, userM);
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

		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String pDomain = getRequest().getParameter("productdomain");
		String pGroup = getRequest().getParameter("productgroup");
		String pFamily = getRequest().getParameter("productfamily");
		String product = getRequest().getParameter("product");
		String saleType = getRequest().getParameter("saleType");
		String customerType = getRequest().getParameter("customertype");
		String currentTab = getRequest().getParameter("currentTab");
		String creditCardAppScore = getRequest().getParameter("creditCardAppScore");
		String creditCardResult = getRequest().getParameter("creditCardResult");
		String sellBranceCode = getRequest().getParameter("createbundling_branch_code");

		if(ORIGUtility.isEmptyString(currentTab)){
			currentTab = "MAIN_TAB";
		}

		if(ORIGUtility.isEmptyString(customerType)){
			customerType = OrigConstant.CUSTOMER_TYPE_INDIVIDUAL;
		}
		
		try{

			PLOrigFormHandler origForm = new PLOrigFormHandler();
			PLApplicationDataM applicationM =  new PLApplicationDataM();

			applicationM.setProductDomain(pDomain);
			applicationM.setProductGroup(pGroup);
			applicationM.setProductFamily(pFamily);
			applicationM.setProduct(product);
			applicationM.setSaleType(saleType);
			
//			septemwi comment
//			applicationM.setAuditFlag(OrigConstant.FLAG_Y);

			BusinessClassManager buinessClass = new BusinessClassManager();

			String busClass = buinessClass.findBus(pDomain, pGroup, pFamily,product, saleType);
			
			logger.debug("busClass=" + busClass);
			/** Initial Application **/
			if(!ORIGUtility.isEmptyString(busClass)){
				applicationM.setBusinessClassId(busClass);
			}
			
			PLPersonalInfoDataM personM = new PLPersonalInfoDataM();

			personM.setCustomerType(customerType);

			personM.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
			Vector<PLPersonalInfoDataM> personVt = new Vector<PLPersonalInfoDataM>();
			personVt.add(personM);
			applicationM.setPersonalInfoVect(personVt);
			applicationM.setLifeCycle(1);

			applicationM.setOwner(userM.getUserName());


			applicationM.setAppDate( new Timestamp(new Date().getTime()));
			
			applicationM.setDcFirstId(userM.getUserName());
			applicationM.setDcStartDate(new Date());
			applicationM.setDcLastDate(new Date());

			applicationM.setPriority("0");
			PLBundleCCDataM bundleCCM = applicationM.getBundleCCM();
			if (bundleCCM == null) {
				bundleCCM = new PLBundleCCDataM();
				applicationM.setBundleCCM(bundleCCM);
			}
			bundleCCM.setAppRecId(applicationM.getAppRecordID());
			bundleCCM.setCreateBy(userM.getUserName());
			bundleCCM.setUpdateBy(userM.getUserName());
			bundleCCM.setCreditCardResult(creditCardResult);
			bundleCCM.setCreditCardAppScore(creditCardAppScore);
			
			ORIGXRulesTool origXrulesTool = new ORIGXRulesTool();
			String busClassID = applicationM.getBusinessClassId();

			
			if(OrigConstant.BusClass.FCP_KEC_CC.equals(busClassID)){
				
				ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
				XrulesRequestDataM xrulesRequest = origXrulesTool.MapXrulesRequestDataM(applicationM
																	,PLXrulesConstant.ModuleService.BUNDLE_CREDIT_CARD_RESULT,userM);
				XrulesResponseDataM response = xRulesService.ILOGServiceModule(xrulesRequest);
					busClassID = response.getResultDesc();
				
				if(busClassID != null){
					applicationM.setBusinessClassId(busClassID);
					saleType = PLOrigUtility.getSaleTypeFormBussClass(busClassID);
					applicationM.setSaleType(saleType);
				}		
				
				applicationM.setAppDecision(OrigConstant.Action.START);

			}else{				
				applicationM.setAppDecision(OrigConstant.Action.START_BUNDLING_CLAIM);
			}

			PLSaleInfoDataM saleInfoM = applicationM.getSaleInfo();

			if (saleInfoM == null) {
				saleInfoM = new PLSaleInfoDataM();
				applicationM.setSaleInfo(saleInfoM);
			}
			saleInfoM.setSalesBranchCode(sellBranceCode);

			eventType = PLApplicationEvent.DC_CREATE_BUNDLING;
			origForm.setAppForm(applicationM);
			
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGForm, origForm);

			return true;
		}catch(Exception e){
			logger.fatal("Exception >> ",e);
			nextAction = "page=DC_CREATE_BUNDLING_SCREEN";
			this.RemoveSession();
			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			return false;
		}
	}

	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;
	}

	@Override
	protected void doSuccess(EventResponse erp){
		
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
				String message = ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR");
				if(!OrigUtil.isEmptyString(response.getResultDesc())){
					message = response.getResultDesc();
				}			
				this.RemoveSession();
				SearchHandler.PushErrorMessage(getRequest(),message);
				nextAction = "page=DC_CREATE_BUNDLING_SCREEN";
			}			
			
		}catch(Exception e){
			logger.fatal("Exception ", e);
			this.RemoveSession();
			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			nextAction = "page=DC_CREATE_BUNDLING_SCREEN";
		}
	}

	@Override
	protected void doFail(EventResponse erp) {
		logger.debug("[doFail]");
		this.RemoveSession();
		SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
		nextAction = "page=DC_CREATE_BUNDLING_SCREEN";
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
				applicationM.setJobState(WorkflowConstant.JobState.DC_IQ);
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
			nextAction = "page=DC_CREATE_BUNDLING_SCREEN";
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
	public String getNextActionParameter(){
		return nextAction;
	}

	@Override
	public boolean getCSRFToken(){
		return true;
	}
}
