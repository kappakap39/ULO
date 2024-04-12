package com.eaf.orig.ulo.pl.app.view.webaction;

import java.io.PrintWriter;

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
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool.Constant;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

public class ExecuteChangeCreditCardResultWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(ExecuteChangeCreditCardResultWebAction.class);
	@Override
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean preModelRequest() {
		String buttonID = getRequest().getParameter("buttonID");
		JSONArray jArray = new JSONArray();
		JSONObject jObject = new JSONObject();		
		try{	
			logger.debug("[preModelRequest]... ButtonID  "+buttonID);			
			
			PLOrigFormHandler  formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			PLApplicationDataM appM = formHandler.getAppForm();
			
			PLPersonalInfoDataM applicantPerson = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			if(applicantPerson == null){
				applicantPerson = new PLPersonalInfoDataM();
				applicantPerson.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
				appM.getPersonalInfoVect().add(applicantPerson);
			}
			
			PLXRulesVerificationResultDataM verResult = applicantPerson.getXrulesVerification();
			if(verResult == null){
				verResult = new PLXRulesVerificationResultDataM();
				applicantPerson.setXrulesVerification(verResult);
			}
			
			ORIGXRulesTool origXrulesTool = new ORIGXRulesTool();
			int serviceId = PLXrulesConstant.ModuleService.BUNDLE_CREDIT_CARD_RESULT;
			XrulesRequestDataM xrulesRequestM = origXrulesTool.MapXrulesRequestDataM(appM, serviceId, userM);
			
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesResponseDataM xrulesResponseM = xRulesService.ILOGServiceModule(xrulesRequestM);
			if(xrulesResponseM != null){
				logger.debug("@@@@@ xrulesResponseM.getResultCode():" + xrulesResponseM.getResultCode());
				logger.debug("@@@@@ xrulesResponseM.getResultDesc():" + xrulesResponseM.getResultDesc());
			}else{
				jObject.put(Constant.RESULT_CODE, OrigConstant.ResultCode.ERROR);
			}
			
			jObject.put(Constant.RESULT_CODE, xrulesResponseM.getResultCode());
			jObject.put(Constant.RESULT_DESC, xrulesResponseM.getResultDesc());
			jArray.put(jObject);
	
		}catch(Exception e){
			logger.error("Error ",e);
//			jObject.put(Constant.RESULT_CODE, OrigConstant.ResultCode.ERROR);
			jArray.put(jObject);
		}
		try{
			getResponse().setContentType("application/json;charset=UTF-8");
			PrintWriter out = getResponse().getWriter();
			out.println(jArray.toString());
			out.close();
		}catch(Exception e){
			logger.error("Error", e);
		}
		
		return true;
	}

	@Override
	public int getNextActivityType() {
		// TODO Auto-generated method stub
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
