package com.eaf.orig.ulo.app.view.webaction;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.FormControlDataM;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.NotifyMessage;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.j2ee.pattern.view.form.TabHandleManager;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.AuthorizedApplicationAction;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.EnquiryLogDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;
import com.orig.bpm.workflow.model.BPMTaskDetail;
import com.orig.bpm.workflow.proxy.BPMMainFlowProxy;

@SuppressWarnings("serial")
public class LoadApplicationWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(LoadApplicationWebAction.class);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean preModelRequest() {
		String applicationGroupId = getRequest().getParameter("APPLICATION_GROUP_ID");
		String TASK_ID = getRequest().getParameter("TASK_ID");
		String APPLICATION_TYPE = getRequest().getParameter("APPLICATION_TYPE");
		String JOB_STATE = getRequest().getParameter("JOB_STATE");
		String APPLICATION_TEMPLATE = getRequest().getParameter("APPLICATION_TEMPLATE");
		String MAIN_SCREEN_INBOX = getRequest().getParameter("MAIN_SCREEN_INBOX");
		String source = getRequest().getParameter("SOURCE");
		
		FlowControlDataM flowControl = (FlowControlDataM) getRequest().getSession().getAttribute(SessionControl.FlowControl);
		UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String roleId = FormControl.getFormRoleId(getRequest());
		String transactionId = roleId+"_"+applicationGroupId;
		logger.debug("transactionId >> "+transactionId);
		TraceController trace = new TraceController(this.getClass().getName(),transactionId);
		getRequest().getSession().setAttribute("transactionId",transactionId);
		logger.debug("flowControl.getActionType() >> " + flowControl.getActionType());
		logger.debug("APPLICATION_GROUP_ID >> "+applicationGroupId);	
		logger.debug("TASK_ID >> "+TASK_ID);
		logger.debug("roleId >> "+roleId);
		logger.debug("APPLICATION_TEMPLATE >> "+APPLICATION_TEMPLATE);
		logger.debug("SOURCE >> "+source);	
		FormControlDataM formControlM = new FormControlDataM();
		
		HashMap objectRequestForm = new HashMap();
		objectRequestForm.put("APPLICATION_GROUP_ID",applicationGroupId);
		objectRequestForm.put("TASK_ID",TASK_ID);
		objectRequestForm.put("APPLICATION_TYPE",APPLICATION_TYPE);
		objectRequestForm.put("JOB_STATE",JOB_STATE);
		objectRequestForm.put("APPLICATION_TEMPLATE",APPLICATION_TEMPLATE);
		objectRequestForm.put("SOURCE",source);
		
		String formId = null;
		if("INBOX".equals(MAIN_SCREEN_INBOX)){
			//Add logic check claim job user
			String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
			String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
			int bpmPort = FormatUtil.getInt(BPM_PORT);
			Long taskIdSend = FormatUtil.getLong(TASK_ID);
			logger.debug("BPM_HOST >> "+BPM_HOST);
			logger.debug("BPM_PORT >> "+bpmPort);	
			try {
					BPMMainFlowProxy proxy = new BPMMainFlowProxy(BPM_HOST, bpmPort);
					BPMTaskDetail bpmTaskDetail = proxy.getTaskDetail(taskIdSend);
					if(!Util.empty(bpmTaskDetail) && !Util.empty(bpmTaskDetail.getOwner()) && !bpmTaskDetail.getOwner().equalsIgnoreCase(userM.getUserName())){
						NotifyForm.warn(getRequest(),MessageErrorUtil.getText("ERROR_CALIM_CHECK_OWNER"));
						return false;
					}
			}catch(Exception e){
				logger.fatal("ERROR",e);
				NotifyForm.error(getRequest(),e);
			}
			if((ApplicationUtil.eApp(source) || ApplicationUtil.cjd((source))) && !SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType())){
				formId = "E_NORMAL_APPLICATION_FORM";
			}else{
				formId = FormControl.getApplicationFormId(objectRequestForm,roleId,getRequest());
			}
			
			//Redirect KPL INC(TOPUP) to NORMAL_APPLICATION_FORM
			if(KPLUtil.isKPL(APPLICATION_TEMPLATE) && formId.equals(KPLUtil.INCREASE_APPLICATION_FORM))
			{
				formId = KPLUtil.NORMAL_APPLICATION_FORM;
			}
			
		}else{
			formId = FormControl.getApplicationFormId(applicationGroupId, roleId, getRequest());
		}
		logger.debug("Load.formId >> "+formId);
		
		formControlM.setFormId(formId);
		formControlM.setRoleId(roleId);
		formControlM.setRequest(getRequest());
		formControlM.setResponse(getResponse());
		
		trace.create("LoadApplication");
		try{
		FormHandler currentForm = FormControl.getFormHandlerAppGroup(formControlM);
		trace.end("LoadApplication");
		String formHandlerName = currentForm.getFormName();
		logger.debug("formHandlerName >> "+formHandlerName);	
//		Create Tab Instant Session.
		TabHandleManager tabHandlerManager = (TabHandleManager) getRequest().getSession().getAttribute("tabHandlerManager");
		if(null == tabHandlerManager){
			tabHandlerManager = new TabHandleManager();
			getRequest().getSession().setAttribute("tabHandlerManager",tabHandlerManager);
		}
		tabHandlerManager.clear();
		tabHandlerManager.setTabId(formHandlerName);		
//		Create Form Instant Session.
		FormHandleManager formHandlerManager = (FormHandleManager) getRequest().getSession().getAttribute("formHandlerManager");
		if(null == formHandlerManager){
			formHandlerManager = new FormHandleManager();
			getRequest().getSession().setAttribute("formHandlerManager",formHandlerManager);
		}
		formHandlerManager.clear();
		formHandlerManager.setCurrentFormHandler(formHandlerName);
//		set currentForm Session
		getRequest().getSession().setAttribute(formHandlerName,currentForm);	
//		createOrigEnquiryLog 
		if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType())){
			createOrigEnquiryLog(userM,applicationGroupId);
		}
		
			trace.create("ClaimProcess");
			ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(getRequest());
//			validate claim by
//			String claimBy = ORIGDAOFactory.getApplicationGroupDAO().getClaimBy(applicationGroupId);
			String claimBy = applicationGroup.getClaimBy();
			logger.debug("claimBy >> "+claimBy);
			if(!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType())
					&& !Util.empty(claimBy) && !claimBy.equals(userM.getUserName())){
				NotifyForm.warn(getRequest(),NotifyMessage.errorClaimApplicationByOtherUser(getRequest(),applicationGroupId));
				return false;	
			}
//			validate fraud flag
			if(null != applicationGroup){
				AuthorizedApplicationAction authorizedApplication = new AuthorizedApplicationAction();
				String authData = authorizedApplication.processAuth(getRequest(),AuthorizedApplicationAction.CLAIM,applicationGroup.getJobState());
				logger.debug("authData >> "+authData);
				String fraudFlag = new JSONObject(authData).getString("fraudFlag");
				logger.debug("fraudFlag : "+fraudFlag);
				if(MConstant.FLAG_Y.equals(fraudFlag)&&!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType())){
					NotifyForm.warn(getRequest(),NotifyMessage.errorClaimApplicationSendToFraud(getRequest(),applicationGroupId));
					return false;
				}
				String cancelFlag = new JSONObject(authData).getString("cancelFlag");
				logger.debug("cancelFlag : "+cancelFlag);
				if(MConstant.FLAG_Y.equals(cancelFlag)&&!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType())){
					NotifyForm.warn(getRequest(),NotifyMessage.errorClaimApplicationCancel(getRequest(),applicationGroupId));
					return false;
				}
			}
//			validate wf_state flag
			if(null != applicationGroup){
				String wfState = applicationGroup.getWfState();
				logger.debug("wfState >> "+wfState);
				if(MConstant.FLAG_R.equals(wfState)&&!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType())){
					ORIGDAOFactory.getApplicationGroupDAO().setWfState(applicationGroupId, null);//Temp. Fix prod defect 707180
				}
			}
			trace.end("ClaimProcess");
			trace.create("UpdateClaimBy");
//			claim application flag
			if(!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType())){
				ORIGDAOFactory.getApplicationGroupDAO().updateClaimBy(applicationGroupId,userM.getUserName());
			}
			trace.end("UpdateClaimBy");
		}catch(Exception e){
			logger.fatal("ERROR",e);
			NotifyForm.error(getRequest(),MessageErrorUtil.getText("ERROR_LOAD_APPLICATION"));
			return false;
		}
		trace.trace();
		return true;
	}
	@Override
	public int getNextActivityType() {
		return 0;
	}
	private void createOrigEnquiryLog(UserDetailM userM, String APPLICATION_GROUP_ID) {
		String enquiryId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ENQUIRY_LOG_PK);
		EnquiryLogDataM enquiryLog = new EnquiryLogDataM();
		enquiryLog.setEnquiryLogId(enquiryId);
		enquiryLog.setUsername(userM.getUserName());
		enquiryLog.setEnquiryDate(ApplicationDate.getDate());
		enquiryLog.setApplicationGroupId(APPLICATION_GROUP_ID);
		try{
			ORIGDAOFactory.getEnquiryLogDAO().createOrigEnquiryLogM(enquiryLog);
		}catch(ApplicationException e){
			logger.fatal("ERROR",e);
		}
	}
}
