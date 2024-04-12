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
import com.eaf.orig.shared.utility.SerializeUtil;
//import com.eaf.orig.ulo.pl.app.rule.utility.ILOGTool;
import com.eaf.orig.ulo.pl.app.utility.ILOGControl;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

public class ExecuteXrulesModuleWebAction extends WebActionHelper implements WebAction{
	
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
	public boolean preModelRequest() {
				
		String buttonID = getRequest().getParameter("buttonID");
		JSONArray jArray = null;		
		try{
			
			logger.debug("[preModelRequest]... ButtonID  "+buttonID);			
			
			PLOrigFormHandler  formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(PLOrigFormHandler.PLORIGForm);	
			
			PLApplicationDataM applicationM = formHandler.getAppForm();
			
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			ORIGXRulesTool tool = new ORIGXRulesTool();
			
			XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(applicationM, buttonID, userM);
				
			ExecuteServiceManager execute = ORIGEJBService.getExecuteServiceManager();
			
			XrulesResponseDataM reponse = execute.ExecuteServiceRules(requestM);			
			
			jArray = tool.DisplayServiceExecute(reponse, applicationM,getRequest());
			
			if(null == jArray) jArray = new JSONArray();
			
			tool.MapFinalResultM(reponse, applicationM, jArray ,userM ,buttonID);
			
			tool.ButtonStyleEngine(jArray, buttonID, applicationM, getRequest());
			
			tool.MapMessage(jArray, reponse);
					
			String SUMMARY_RULE_DISPLAY = getRequest().getParameter(OrigConstant.SUMMARY_RULE_DISPLAY);
			
			logger.debug("SUMMARY_RULE_DISPLAY >> "+SUMMARY_RULE_DISPLAY);
			
			if(OrigConstant.SUMMARY_RULE_DISPLAY.equals(SUMMARY_RULE_DISPLAY)){
//				#septemwi comment change logic call ILOG!!
//				ILOGTool ILOG = new ILOGTool();
//				ILOG.ModuleExecuteRules(applicationM, userM);
				try{
					ILOGControl control = new ILOGControl();
						control.execute(OrigConstant.ILOGAction.EXECUTE_RULES, applicationM, userM);
					tool.ModuleExecuteRules(applicationM, jArray);
				}catch (Exception e) {
					logger.debug("ERROR Execute ILOGControl() EXECUTE_RULES ..");
				}
			}
			
			PLApplicationDataM sensitiveappM = (PLApplicationDataM)SerializeUtil.clone(formHandler.getAppForm());
			getRequest().getSession(true).setAttribute(PLOrigFormHandler.PLORIGSensitive,sensitiveappM);
			
			getResponse().setContentType("application/json;charset=UTF-8");
			PrintWriter out = getResponse().getWriter();
			out.println(jArray.toString());
			out.close();
	
		}catch(Exception e){
			logger.debug("ERROR ",e);
		}		
		return true;
	}
	
	@Override
	public int getNextActivityType() {	
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}
	
}
