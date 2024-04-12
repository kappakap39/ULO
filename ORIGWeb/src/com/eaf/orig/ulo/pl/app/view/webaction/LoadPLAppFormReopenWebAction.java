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
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
//import com.eaf.orig.ulo.pl.app.servlet.ClearDataInformation;
import com.eaf.orig.ulo.pl.app.utility.BusinessClassManager;
import com.eaf.orig.ulo.pl.app.utility.DisplayMatrixTool;
import com.eaf.orig.ulo.pl.app.utility.MapDefaultDataM;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class LoadPLAppFormReopenWebAction extends WebActionHelper implements WebAction{

	static Logger logger = Logger.getLogger(LoadPLAppFormReopenWebAction.class);
	
	String nextAction;
	@Override
	public Event toEvent() {
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
	public boolean preModelRequest() {
		
//		logger.debug("@@@@@..Begin");
		
		String appRecordID = getRequest().getParameter("appRecordID");
		String customerType = getRequest().getParameter("customerType");
		
//		String loadAppType = getRequest().getParameter("loadAppType");		
//		String roleElement = getRequest().getParameter("roleElement");
						
//		logger.debug("@@@@@..AppRecordID "+appRecordID);
//		logger.debug("@@@@@..LoadAppType "+loadAppType);
//		logger.debug("@@@@@..roleElement "+roleElement);
//		logger.debug("@@@@@..customerType "+customerType);
		
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
		
//		WorkflowResponse workFlowResponse = new WorkflowResponse();
		String formID = null;
		String currentTab = "MAIN_TAB";
		String drawDownFlag = null;		
		String role  = null;
		
		PLApplicationDataM applicationM = origForm.getAppForm();
		
		if(null == applicationM){
			applicationM = new PLApplicationDataM();			
		}
		
		try{
			
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			Vector userRoles = userM.getRoles();
			
			applicationM.setAppRecordID(appRecordID);

			PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();
			
			applicationM = origBean.loadPLApplicationDataM(applicationM.getAppRecordID());
			
			applicationM.setClaimDate(new Timestamp(System.currentTimeMillis()));
			
//			#septemwi set last jobstate and application status
			applicationM.setLastAppStatus(applicationM.getApplicationStatus());
			applicationM.setLastJobState(applicationM.getJobState());			
			
//			applicationM.setJobID(workFlowResponse.getJobId());
//			applicationM.setPtID(workFlowResponse.getPtid());
//			applicationM.setPtType(workFlowResponse.getPtType());
			
			/** Initial PersonalInfo (Applicant) **/
			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			
			if(!OrigUtil.isEmptyString(customerType)){
				personalM.setCustomerType(customerType);	
			}
			
			if(OrigUtil.isEmptyString(personalM.getCustomerType())){ 
				personalM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
			}
											
			formID = ORIGFormUtil.getFormIDByBus(applicationM.getBusinessClassId());	
			role = userM.getCurrentRole();
			if(OrigConstant.ROLE_DF.equals(role)){
				userRoles.insertElementAt(OrigConstant.ROLE_DF_REJECT, 0);
			}
				
			
			if(ORIGUtility.isEmptyString(applicationM.getJobState())){ 
				applicationM.setJobState(WorkflowConstant.JobState.DE_IQ);
			}
			
//			logger.debug("@@@@@..FormID "+formID);
//			logger.debug("@@@@@..Role "+role);
//			logger.debug("@@@@@..Job State "+applicationM.getJobState());
			
			logger.debug("FormID >> "+formID+" Role "+role+" Job State "+applicationM.getJobState()+" BusinessClass "+applicationM.getBusinessClassId());
			
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
			
//			logger.debug("@@@@@..getSubForms() "+origForm.getSubForms());
			
			nextAction="page=PL_MAIN_APPFORM";
			
			/* No need to clear verification data (need clear when submit reopen)
			ClearDataInformation clearData = new ClearDataInformation();
			clearData.ClearPLXRulesVerificationResultDataM(null,applicationM, userM);
			*/
			
//			clone session
//			PLApplicationDataM cloanApplicationM = (PLApplicationDataM)SerializeUtil.clone(plOrigForm.getAppForm());			
//			getRequest().getSession(true).setAttribute(PLOrigFormHandler.CloanPlApplication,cloanApplicationM);
									
//			Clone Session Sensitive
			PLApplicationDataM sApplicationM = (PLApplicationDataM)SerializeUtil.clone(origForm.getAppForm());
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGSensitive,sApplicationM);				
			
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGForm, origForm);			

//			logger.debug("@@@@@..End");
				
		}catch(Exception e){
//			logger.fatal("@@@@@ ..Error ",e);
//			nextAction="action=FristPLApp";
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
	public boolean getCSRFToken() {
		return false;
	}
	
	public void RemoveSession(){
		getRequest().getSession().removeAttribute(PLOrigFormHandler.PLORIGSensitive);
		getRequest().getSession().removeAttribute(PLOrigFormHandler.PLORIGForm);
	}
}
