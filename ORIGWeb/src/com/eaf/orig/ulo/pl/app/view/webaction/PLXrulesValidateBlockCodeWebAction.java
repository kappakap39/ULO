package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;

public class PLXrulesValidateBlockCodeWebAction  extends WebActionHelper implements WebAction{
	
	Logger logger = Logger.getLogger(this.getClass());
	
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
		logger.debug("[ValidateBlockCode]");
		JsonObjectUtil jObjectUtil = new JsonObjectUtil();		
		try{
//			PLApplicationDataM plApplicationDataM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
//			
//			if(plApplicationDataM == null) plApplicationDataM = new PLApplicationDataM();
//			
//			PLXrulesApplicationDataM plXrulesAppM = new PLXrulesApplicationDataM();
//			
//			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
//			
//			PLOrigXrulesUtil plXrulesUtil = new PLOrigXrulesUtil();
//			
//			plXrulesAppM = plXrulesUtil.mapPLXrulesAppModel(plApplicationDataM, plXrulesAppM, userM);
//			
//			ExecuteServiceManager ExecuteBean = PLORIGEJBService.getExecuteServiceManager();
//			
//			PLXrulesResultDataM plXrulesResultM = ExecuteBean.ExecuteBlockCode(plXrulesAppM);
//			
//			if(plXrulesResultM == null) plXrulesResultM = new PLXrulesResultDataM();
//			
//			plXrulesAppM.setPlXrulesEaiM(plXrulesResultM.getPlXrulesEaiM());
//			
//			EAIDataM plXrulesEaiM = plXrulesResultM.getPlXrulesEaiM();
//			
//			if(plXrulesEaiM == null) plXrulesEaiM = new EAIDataM();
			
//			logger.debug("[BlockCode] "+plXrulesEaiM.getBlockCode());
			
//			if(PLXrulesConstant.EAI.BLOCKCODE_O.equals(plXrulesEaiM.getBlockCode())){
//				String lastPaymentDate = getRequest().getParameter("lastpayment_date");
//				if(ORIGUtility.isEmptyString(lastPaymentDate)){
//					jObjectUtil.CreateJsonObject("lastpayment_date", "* Error Field : lastpayment_date");
//				}
//			}
//			getRequest().getSession(true).setAttribute(PLXrulesConstant.Session.PLXRulesAppDataM, plXrulesAppM);
			
			jObjectUtil.ResponseJsonArray(getResponse());
		}catch (Exception e) {
			return false;
		}				
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
