package com.eaf.orig.ulo.pl.app.view.webaction;

import java.sql.Timestamp;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.BusinessClassManager;
import com.eaf.orig.ulo.pl.app.utility.DisplayMatrixTool;
import com.eaf.orig.ulo.pl.app.utility.MapDefaultDataM;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class LoadCashDay5ApplicationWebAction extends WebActionHelper implements WebAction{
	
	static Logger logger = Logger.getLogger(LoadCashDay5ApplicationWebAction.class);
	
	String nextAction;
	
	@Override
	public Event toEvent(){
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
	public boolean preModelRequest() {
		
		String appRecordID = getRequest().getParameter("appRecordID");
		String customerType = getRequest().getParameter("customerType");		

		logger.debug("Load Application WebAction ... AppRecordID >> "+appRecordID);		
		
		if(OrigUtil.isEmptyString(appRecordID)){
			logger.warn("Empty AppRecID Cannot Load Application.. !!");
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error();
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			return false;
		}
				
		PLOrigFormHandler origForm = (PLOrigFormHandler) getRequest().getSession().getAttribute(PLOrigFormHandler.PLORIGForm);	
		origForm = new PLOrigFormHandler();		
				
		String formID = null;
		String currentTab = "MAIN_TAB";
		String drawDownFlag = null;		
		
		PLApplicationDataM applicationM = origForm.getAppForm();		
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
			origForm.setAppForm(applicationM);
		}

		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");		
		
		try{			
			Vector userRoles = userM.getRoles();
			
			applicationM.setAppRecordID(appRecordID);

			PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();
			
			applicationM = origBean.loadPLApplicationDataM(applicationM.getAppRecordID());
			
			applicationM.setClaimDate(new Timestamp(System.currentTimeMillis()));

			applicationM.setLastAppStatus(applicationM.getApplicationStatus());
			applicationM.setLastJobState(applicationM.getJobState());

			
			/** Initial PersonalInfo (Applicant) **/			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);			
			if(!OrigUtil.isEmptyString(customerType)){
				personalM.setCustomerType(customerType);
			}				
			
			if(OrigUtil.isEmptyString(personalM.getCustomerType())){
				personalM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
			}
																
			formID = "KEC_CASHDAY5_FORM";
			
			if(OrigUtil.isEmptyString(applicationM.getJobState())) {
				applicationM.setJobState(WorkflowConstant.JobState.DE_IQ);
			}
		
			/**Find Product Domain Group*/
			
	 		BusinessClassManager busClassM = new BusinessClassManager();
	 		String value[] = (applicationM.getBusinessClassId()).split("_");
			String result[] = (busClassM.findAppValue(value[0])).split("_");				
			
			applicationM.setProductDomain(result[0]);				
			applicationM.setProductGroup(result[1]);
			applicationM.setProductFamily(result[2]);
	 		
			applicationM.setProduct(value[1]);				
			applicationM.setSaleType(value[2]);

			/**End*/
			
			/**set MatrixServiceID*/
			String searchType = (String) getRequest().getSession().getAttribute("searchType");			
			ProcessMenuM currentMenuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");			
			if(null == currentMenuM) currentMenuM = new ProcessMenuM();
			
			DisplayMatrixTool matrix = new DisplayMatrixTool();			
			applicationM.setMatrixServiceID(matrix.getMatrixServiceID(userM, applicationM, currentMenuM, searchType));
			MapDefaultDataM.map(applicationM);
			
			origForm.setAppForm(applicationM);
			
//			origForm.getSubForms().clear();
			origForm.setLoadedSubForms(false);
			
			origForm.loadSubFormsForDrawDown(userRoles, formID, drawDownFlag);
			origForm.setCurrentTab(currentTab);
			origForm.setFormID(formID);
			
			nextAction="page=PL_MAIN_APPFORM";
			
//			Clone Session Sensitive
			PLApplicationDataM sApplicationM = (PLApplicationDataM)SerializeUtil.clone(origForm.getAppForm());
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGSensitive,sApplicationM);
			
//		    Clone Session
			PLApplicationDataM cloanAppM = (PLApplicationDataM)SerializeUtil.clone(origForm.getAppForm());			
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.CloanPlApplication,cloanAppM);
			
//			set Session Application
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGForm, origForm);				
			
		}catch(Exception e){
//			logger.fatal("Exception Load Application >> ",e);
			nextAction = "action=FristPLApp";
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));	
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			this.RemoveSession();
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
		return false;
	}  
	
	public void RemoveSession(){
		getRequest().getSession().removeAttribute(PLOrigFormHandler.PLORIGSensitive);
		getRequest().getSession().removeAttribute(PLOrigFormHandler.PLORIGForm);
	}
}
