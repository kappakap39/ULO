package com.eaf.orig.ulo.pl.app.view.webaction;

import java.io.PrintWriter;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.utility.ILOGModule;
import com.eaf.orig.ulo.pl.app.utility.ILOGTool;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class ILOGExecutionWebAction extends WebActionHelper implements WebAction{
	Logger logger = Logger.getLogger(ILOGExecutionWebAction.class);
	@Override
	public Event toEvent() {
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
		logger.debug("ILOGExecution ... ");		
		String ILOGModule = getRequest().getParameter("ilog-module");		
		if(OrigUtil.isEmptyString(ILOGModule)){
			logger.debug("ILOGExecution Require IlogModule ");
			return true;
		}
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String decision = getRequest().getParameter("decision_option");
		String docRefID = getRequest().getParameter("decision_ref_no");		
		
		logger.debug("do ILOGExecution()..AppRecID "+applicationM.getAppRecordID());
		
		logger.debug("Application Decision ... "+decision);
		logger.debug("ILOG Module ... "+ILOGModule);
		logger.debug("DOCUMENT_REF_ID ... "+docRefID);
		
		JSONArray jArray = new JSONArray();
		JSONObject jObj = null;
		applicationM.setExecuteDecision(null);
		
		ORIGXRulesTool tool = new ORIGXRulesTool();
		
		String SUMMARY_RULE_DISPLAY = getRequest().getParameter(OrigConstant.SUMMARY_RULE_DISPLAY);		
		logger.debug("SUMMARY_RULE_DISPLAY >> "+SUMMARY_RULE_DISPLAY);
		
		try{
			switch (Integer.valueOf(ILOGModule)) {
				case OrigConstant.IlogModule.MODULE_DC_DECISION:
					this.ILOGDCDecision(applicationM, userM, decision, jArray, docRefID);		
					if(OrigConstant.SUMMARY_RULE_DISPLAY.equals(SUMMARY_RULE_DISPLAY)){
						tool.ModuleExecuteRules(applicationM, jArray);
					}
					break;
				case OrigConstant.IlogModule.MODULE_DCI_DECISION:
					this.ILOGDCIDecision(applicationM, userM, decision, jArray, docRefID);
					if(OrigConstant.SUMMARY_RULE_DISPLAY.equals(SUMMARY_RULE_DISPLAY)){
						tool.ModuleExecuteRules(applicationM, jArray);
					}
					break;
				case OrigConstant.IlogModule.MODULE_VC_DECISION:
					this.ILOGVCDecision(applicationM, userM, decision, jArray, docRefID);
					if(OrigConstant.SUMMARY_RULE_DISPLAY.equals(SUMMARY_RULE_DISPLAY)){
						tool.ModuleExecuteRules(applicationM, jArray);
					}
					break;
				case OrigConstant.IlogModule.MODULE_DE_SUP_DECISION:
					this.ILOGDE_SUPDecision(applicationM, userM, decision, jArray, docRefID);
					if(OrigConstant.SUMMARY_RULE_DISPLAY.equals(SUMMARY_RULE_DISPLAY)){
						tool.ModuleExecuteRules(applicationM, jArray);
					}
					break;
				default:
					jArray = new JSONArray();
					jObj = this.CreateDefaultDecision();
					jArray.put(jObj);					
					break;
			}
		}catch (Exception e) {
			logger.fatal("Exception "+e.getMessage());
			logger.error("ILOGExecutionWebAction Error !! Return error Message ");
			jArray = new JSONArray();
			jObj = this.ERRORMessage();
			jArray.put(jObj);
		}
		
		logger.debug("success ILOGExecution()..AppRecID "+applicationM.getAppRecordID());
		
		try{
			getResponse().setContentType("application/json;charset=UTF-8");
			PrintWriter out = getResponse().getWriter();
			out.println(jArray.toString());
			out.close();
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}
		return true;
	}
	
	@Override
	public int getNextActivityType() {
		return FrontController.NOTFORWARD;
	}
	
	
	public void ILOGDCIDecision(PLApplicationDataM applicationM , UserDetailM userM, String decision , JSONArray jArray ,String docRefID) throws Exception{
		JSONObject jObj = null;
		String appDecision = null;
//		ILOGTool ilogTool = new ILOGTool();
		ILOGModule module = new ILOGModule();
		boolean defaultDecision = false;
		if (OrigConstant.wfProcessState.SEND.equalsIgnoreCase(decision) 
				|| OrigConstant.wfProcessState.SENDX.equalsIgnoreCase(decision)){		
		
			appDecision = module.ModuleDCIDecision(applicationM, userM, decision ,docRefID);	
			
			logger.debug("Logic ILOGDCIDecision ... "+appDecision);			
			
			this.DisplayXrulesResult(applicationM, userM, jArray);
			
			if(OrigConstant.Action.REJECT.equals(appDecision)
					||OrigConstant.Action.CANCEL.equals(appDecision)){
				applicationM.setAppDecision(appDecision);
				applicationM.setExecuteDecision(PLXrulesConstant.ResultCode.CODE_SETUP_DECISION);
			}
			if(OrigConstant.Action.REJECT.equals(appDecision)){
				defaultDecision = true;
				jObj = this.CreateRejectDecision();
				jArray.put(jObj);
			}
			
			margeReason(appDecision, applicationM);			
		}		
		if(!defaultDecision){
			jObj = this.CreateDefaultDecision();
			jArray.put(jObj);
		}		
	}
	
	public void DisplayXrulesResult(PLApplicationDataM applicationM , UserDetailM userM,JSONArray jArray){
		JSONObject jObj = null;		
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		/**Display Verify Customer*/
//		jObj = new JSONObject();
//		jObj.put(Constant.SERVICE_ID, PLXrulesConstant.ModuleService.VERIFY_CUSTOMER);
//		jObj.put(Constant.TYPE, Constant.EXECUTE);
//		jObj.put(Constant.RESULT_CODE, HTMLRenderUtil.replaceNull(xrulesVerM.getVerCusResultCode()));
//		jObj.put(Constant.RESULT_DESC,  HTMLRenderUtil.replaceNull(xrulesVerM.getVerCusResult()));
		jArray.put(jObj);
		
		/**Display Verify HR*/
//		jObj = new JSONObject();
//		jObj.put(Constant.SERVICE_ID, PLXrulesConstant.ModuleService.VERIFY_HR);
//		jObj.put(Constant.TYPE, Constant.EXECUTE);
//		jObj.put(Constant.RESULT_CODE, HTMLRenderUtil.replaceNull(xrulesVerM.getVerHRResultCode()));
//		jObj.put(Constant.RESULT_DESC,  HTMLRenderUtil.replaceNull(xrulesVerM.getVerHRResult()));
		jArray.put(jObj);
		
	}
	
	public void ILOGDCDecision(PLApplicationDataM applicationM , UserDetailM userM,String decision ,JSONArray jArray,String docRefID) throws Exception{
		JSONObject jObj = null;
		String appDecision = null;
		
//		ILOGTool ilogTool = new ILOGTool();		
		ILOGModule module = new ILOGModule();
		boolean defaultDecision = false;
		if (OrigConstant.wfProcessState.SEND.equalsIgnoreCase(decision) 
				|| OrigConstant.wfProcessState.SENDX.equalsIgnoreCase(decision)){		
		
			appDecision = module.ModuleDCDecision(applicationM, userM, decision, docRefID);	
			
			logger.debug("Logic DCDecision ... "+appDecision);			

			this.DisplayXrulesResult(applicationM, userM, jArray);
			
			if(OrigConstant.Action.REJECT.equals(appDecision)
				|| OrigConstant.Action.CONFIRM_REJECT.equals(appDecision)
					|| OrigConstant.Action.SEND_TO_VC.equals(appDecision)
						||OrigConstant.Action.CANCEL.equals(appDecision)){
				applicationM.setAppDecision(appDecision);
				applicationM.setExecuteDecision(PLXrulesConstant.ResultCode.CODE_SETUP_DECISION);
			}
			if(OrigConstant.Action.REJECT.equals(appDecision)
					|| OrigConstant.Action.CONFIRM_REJECT.equals(appDecision)){
				defaultDecision = true;
				jObj = this.CreateRejectDecision();
				jArray.put(jObj);
			}
			
			margeReason(appDecision, applicationM);
			
		}		
		if(!defaultDecision){
			jObj = this.CreateDefaultDecision();
			jArray.put(jObj);
		}
	}
	
	public void ILOGVCDecision(PLApplicationDataM applicationM , UserDetailM userM,String decision ,JSONArray jArray, String docRefID) throws Exception{
		JSONObject jObj = null;
		String appDecision = null;
//		ILOGTool ilogTool = new ILOGTool();
		ILOGModule module = new ILOGModule();
		boolean defaultDecision = false;
		
		if (OrigConstant.Action.SEND_TO_CA.equalsIgnoreCase(decision) ||
				OrigConstant.wfProcessState.SENDX.equalsIgnoreCase(decision)){
//			
			appDecision = module.ModuleVCDecision(applicationM, userM, decision, docRefID);	
			
			logger.debug("Logic ILOGVCDecision ... "+appDecision);			

			this.DisplayXrulesResult(applicationM, userM, jArray);
			
			if(OrigConstant.Action.REJECT.equals(appDecision)
					||OrigConstant.Action.CANCEL.equals(appDecision)){
				applicationM.setAppDecision(appDecision);
				applicationM.setExecuteDecision(PLXrulesConstant.ResultCode.CODE_SETUP_DECISION);
			}
			if(OrigConstant.Action.REJECT.equals(appDecision)){
				defaultDecision = true;
				jObj = this.CreateRejectDecision();
				jArray.put(jObj);
			}
			margeReason(appDecision, applicationM);
		}		
		if(!defaultDecision){
			jObj = this.CreateDefaultDecision();
			jArray.put(jObj);
		}
	}
	
	public void ILOGDE_SUPDecision(PLApplicationDataM applicationM , UserDetailM userM,String decision ,JSONArray jArray,String docRefID) throws Exception{
		JSONObject jObj = null;
		String appDecision = null;
		ILOGTool ilogTool = new ILOGTool();
		boolean defaultDecision = false;
		
		if (OrigConstant.wfProcessState.SENDX.equalsIgnoreCase(decision)){		
		
			appDecision = ilogTool.ModuleDE_SUPDecision(applicationM, userM, decision, docRefID);	
			
			if (OrigConstant.Action.CONFIRM_REJECT.equals(appDecision)){
				applicationM.setAppDecision(appDecision);
				applicationM.setExecuteDecision(PLXrulesConstant.ResultCode.CODE_SETUP_DECISION);
				defaultDecision = true;
				jObj = this.CreateRejectDecision();
				jArray.put(jObj);
				applicationM.setAppDecision(appDecision);
				
				Vector<PLReasonDataM> vReason = new Vector<PLReasonDataM>();
				PLReasonDataM reasonDataM = new PLReasonDataM();
				reasonDataM.setReasonCode("10");
				reasonDataM.setReasonType("34");
				reasonDataM.setRole(userM.getCurrentRole());
				reasonDataM.setApplicationRecordId(applicationM.getAppRecordID());
				vReason.add(reasonDataM);
				applicationM.setReasonVect(vReason);
			}
			
			margeReason(appDecision, applicationM);
			
		}
		
		if(!defaultDecision){
			jObj = this.CreateDefaultDecision();
			jArray.put(jObj);
		}
	}
	
	public JSONObject CreateDefaultDecision(){
		JSONObject jObj = new JSONObject();	
//			jObj.put(Constant.TYPE, Constant.FINAL_EXE);
//			jObj.put(Constant.RESULT_CODE, PLXrulesConstant.ResultCode.CODE_DEFAULT);
//			jObj.put(Constant.RESULT_DESC, "");	
		return jObj;
	}
	
	public JSONObject CreateRejectDecision(){
		JSONObject jObj = new JSONObject();	
//			jObj.put(Constant.TYPE, Constant.FINAL_EXE);
//			jObj.put(Constant.RESULT_CODE, PLXrulesConstant.ResultCode.CODE_REJECT);
//			jObj.put(Constant.RESULT_DESC, ErrorUtil.getShortErrorMessage(getRequest(),"REJECT_APP_MSG"));	
		return jObj;
	}
	
	public JSONObject ERRORMessage(){
		JSONObject jObj = new JSONObject();	
//			jObj.put(Constant.TYPE, Constant.FINAL_EXE);
//			jObj.put(Constant.RESULT_CODE, OrigConstant.ILOGResult.ERROR);
//			String MSG = Message.error();
//			jObj.put(Constant.RESULT_DESC, MSG);	
		return jObj;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}
	
	public void margeReason(String decision,PLApplicationDataM applicationM){
		logger.debug("margeReason() decision >> "+decision);
		Vector<PLReasonDataM> reasonVect = applicationM.getReasonVect();
		applicationM.setReasonVect(getReason(decision, reasonVect));
	}
	
	public Vector<PLReasonDataM> getReason(String decision ,Vector<PLReasonDataM> reasonVect){
		Vector<PLReasonDataM> data = new Vector<PLReasonDataM>();
		if(null != reasonVect){
			logger.debug("getReason() reasonVect.size >> "+reasonVect.size());
			for(PLReasonDataM dataM : reasonVect){
				if(null != dataM){
					if(LogicDecision(decision, dataM)){
						data.add(dataM);
					}
				}
			}
		}
		return data;		
	}
	
	public boolean LogicDecision(String decision,PLReasonDataM dataM){
		if(OrigConstant.Action.REJECT.equals(decision)
				|| OrigConstant.Action.CONFIRM_REJECT.equals(decision)){
			if(OrigConstant.fieldId.REJECT_REASON.equals(dataM.getReasonType())){
				return true;
			}
		}else if(OrigConstant.Action.CANCEL.equals(decision)){
			if(OrigConstant.fieldId.CANCEL_REASON.equals(dataM.getReasonType())){
				return true;
			}
		}else{
			if(!OrigConstant.fieldId.REJECT_REASON.equals(dataM.getReasonType()) && 
					!OrigConstant.fieldId.CANCEL_REASON.equals(dataM.getReasonType())){
				return true;
			}
		}		
		return false;
	}
}
