package com.eaf.orig.ulo.pl.app.view.webaction;

import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.pl.ajax.ExecuteReset;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;
import com.eaf.xrules.ulo.pl.moduleservice.model.EAIDataM;

public class ExecuteXRulesManualWebAction extends WebActionHelper implements WebAction{
	Logger logger = Logger.getLogger(ExecuteXrulesModuleWebAction.class);
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
	public boolean preModelRequest(){
		String serviceID = getRequest().getParameter("serviceID");
		String buttonID = getRequest().getParameter("buttonID");
		String reset_service	= getRequest().getParameter("reset_service");
		JSONArray jArray = null;		
		try{
			
			logger.debug("[preModelRequest]... serviceID "+serviceID);
			logger.debug("[preModelRequest]... ButtonID "+buttonID);
			logger.debug("[preModelRequest]... reset_service "+reset_service);
			
			PLOrigFormHandler  formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(PLOrigFormHandler.PLORIGForm);				
			PLApplicationDataM applicationM = formHandler.getAppForm();
			
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			if(OrigConstant.FLAG_Y.equals(reset_service)){
				doResetService(applicationM, serviceID, userM);
			}
	
			ORIGXRulesTool tool = new ORIGXRulesTool();
			
			XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(applicationM, Integer.parseInt(serviceID), userM);
				
			ExecuteServiceManager execute = ORIGEJBService.getExecuteServiceManager();
			
			XrulesResponseDataM reponse = execute.ExecuteManualService(requestM);			
			
			jArray = tool.DisplayServiceExecute(reponse, applicationM,getRequest());
			
			if(null == jArray) jArray = new JSONArray();
			
			tool.MapFinalResultM(reponse, applicationM, jArray,userM,buttonID);
			
			tool.ButtonStyleEngine(jArray, buttonID, applicationM, getRequest());
			
			PLApplicationDataM sensitiveappM = (PLApplicationDataM)SerializeUtil.clone(formHandler.getAppForm());
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGSensitive,sensitiveappM);
			
			getResponse().setContentType("application/json;charset=UTF-8");
			PrintWriter out = getResponse().getWriter();
			out.println(jArray.toString());
			out.close();
	
		}catch(Exception e){
			logger.debug("Exception ",e);
		}
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}
	public static void doResetService(PLApplicationDataM applicationM,String serviceID,UserDetailM userM){
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
		if(OrigUtil.isEmptyString(serviceID)){
			return;
		}
		ExecuteReset reset = new ExecuteReset();
		switch (Integer.valueOf(serviceID)) {
			case PLXrulesConstant.ModuleService.DEPLICATE_APPLICATION:	
				xrulesVerM.setExecute1Result(null);
				reset.doDEPLICATE_APPLICATION(xrulesVerM, userM);
				break;
			default:
				break;
		}
	}
}
