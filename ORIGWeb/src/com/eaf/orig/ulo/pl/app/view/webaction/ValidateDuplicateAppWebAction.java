package com.eaf.orig.ulo.pl.app.view.webaction;
//
//import java.io.IOException;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
//import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
//import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
//import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
//import com.eaf.orig.ulo.pl.model.app.ApplicationDuplicateModuleDataM;
//import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class ValidateDuplicateAppWebAction extends WebActionHelper implements WebAction{
	
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
	public boolean preModelRequest(){
		
//		JsonObjectUtil jsonObjectUtil = new JsonObjectUtil();	
//		
//		try{			
//			String idNo = getRequest().getParameter("identification_no");
//			
//			logger.debug("[requiredModelRequest]..idNo "+idNo);
//			
//			PLApplicationDataM plAppM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
//			
//			if(plAppM == null) plAppM = new PLApplicationDataM();
//
//			logger.debug("[requiredModelRequest]..BusinessClass "+plAppM.getBusinessClassId());
//	
///**New Logic Validate Duplicate Application #SeptemWi*/
//			
//			PLORIGApplicationManager origBean  = PLORIGEJBService.getPLORIGApplicationManager();			
//			ApplicationDuplicateModuleDataM duplicateModuleM = origBean.ValidateDuplicateApplication(plAppM);
//			
//			if(duplicateModuleM.isDuplicateApp()){
//				logger.debug("Duplicate Application !!");
//				plAppM.setAppDecision(duplicateModuleM.getDecision());
//				plAppM.setBlockFlag(duplicateModuleM.getBlockFlag());
////				plAppM.setAppDuplicateVect(duplicateModuleM.getAppDuplicateVect());
//			}
//			jsonObjectUtil.CreateJsonObject("status",plAppM.getAppDecision());
//			
//		} catch (Exception e) {
//			logger.fatal("Error ",e);
//			jsonObjectUtil.CreateJsonObject("error",e.getLocalizedMessage());			
//		}	
//		try {			
//			jsonObjectUtil.ResponseJsonArray(getResponse());			
//		} catch (IOException e) {
//			logger.fatal("Error ",e);
//		}
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
