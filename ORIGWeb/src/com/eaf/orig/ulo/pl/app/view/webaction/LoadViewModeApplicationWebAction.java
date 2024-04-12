package com.eaf.orig.ulo.pl.app.view.webaction;

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
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.BusinessClassManager;
import com.eaf.orig.ulo.pl.app.utility.DisplayMatrixTool;
import com.eaf.orig.ulo.pl.app.utility.MapDefaultDataM;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class LoadViewModeApplicationWebAction  extends WebActionHelper implements WebAction {
	static Logger logger = Logger.getLogger(LoadViewModeApplicationWebAction.class);
	String nextAction = "action=FristPLApp";
	
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
	public boolean preModelRequest(){
		
		String appRecordID = getRequest().getParameter("appRecordID");
		
		String formID = null;
		String currentTab = "MAIN_TAB";
		String drawDownFlag = null;
		
		logger.debug("Load Application WebAction ... AppRecordID >> "+appRecordID);		
		
		if(OrigUtil.isEmptyString(appRecordID)){
			logger.warn("Empty AppRecID Cannot Claim Application.. !!");
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error();
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			return false;
		}
				
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");	
		
		PLOrigFormHandler origFrom = new PLOrigFormHandler();		
		PLApplicationDataM applicationM = origFrom.getAppForm();		
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
			origFrom.setAppForm(applicationM);
		}
		
		try{			
			applicationM.setAppRecordID(appRecordID);

			PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();			
			applicationM = origBean.loadPLApplicationDataM(applicationM.getAppRecordID());
						
			applicationM.setLastAppStatus(applicationM.getApplicationStatus());
			applicationM.setLastJobState(applicationM.getJobState());
					
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);			
			if(OrigUtil.isEmptyString(personalM.getCustomerType())){
				personalM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
			}
											
			formID = ORIGFormUtil.getFormIDByBus(applicationM.getBusinessClassId());	
			
			userM.setCurrentRole(OrigConstant.ROLE_DC);
										
			setBusClassM(applicationM);
			
			/**set MatrixServiceID*/
			String searchType = (String) getRequest().getSession().getAttribute("searchType");			
			ProcessMenuM currentMenuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");			
			if(null == currentMenuM) currentMenuM = new ProcessMenuM();
			
			DisplayMatrixTool matrix = new DisplayMatrixTool();			
			applicationM.setMatrixServiceID(matrix.getMatrixServiceID(userM, applicationM, currentMenuM, searchType));
			MapDefaultDataM.map(applicationM);
			
			origFrom.setAppForm(applicationM);
			
//			origFrom.getSubForms().clear();
			origFrom.setLoadedSubForms(false);
			
			origFrom.loadSubFormsForDrawDown(userM.getRoles(), formID, drawDownFlag);
			origFrom.setCurrentTab(currentTab);
			origFrom.setFormID(formID);
			
			nextAction="page=PL_MAIN_APPFORM";
			
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGForm, origFrom);			
				
		}catch(Exception e){
//			logger.fatal("Load Application Error >> ",e);
			this.RemoveSession();
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			return false;
		}		
		
		return true;
	}
	
	public void setBusClassM(PLApplicationDataM applicationM){
 		BusinessClassManager busClassM = new BusinessClassManager();
 		String value[] = (applicationM.getBusinessClassId()).split("_");
		String result[] = (busClassM.findAppValue(value[0])).split("_");				
		
		applicationM.setProductDomain(result[0]);				
		applicationM.setProductGroup(result[1]);
		applicationM.setProductFamily(result[2]);
 		
		applicationM.setProduct(value[1]);				
		applicationM.setSaleType(value[2]);
	}
	
	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;
	}
	
	@Override
	public String getNextActionParameter(){
		return  nextAction;
	}
	
	@Override
	public boolean getCSRFToken(){
		return false;
	}
	
	public void RemoveSession(){
		getRequest().getSession().removeAttribute(PLOrigFormHandler.PLORIGForm);
	}
}
