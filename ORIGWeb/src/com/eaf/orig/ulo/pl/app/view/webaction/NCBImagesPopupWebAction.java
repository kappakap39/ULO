package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class NCBImagesPopupWebAction extends WebActionHelper implements	WebAction{	
	Logger log = Logger.getLogger(this.getClass());
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
		String appRecID = getRequest().getParameter("appRecId");		
		PLOrigFormHandler origForm = new PLOrigFormHandler();
		try{
			PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();
			origForm.setAppForm(origBean.LoadNCBImageDataM(appRecID));
		}catch(Exception e){
			log.fatal("Exception ",e);
		}		

		getRequest().getSession().setAttribute(PLOrigFormHandler.PLORIGForm, origForm);
		
		com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
			(com.eaf.j2ee.pattern.control.ScreenFlowManager) getRequest().getSession(true).getAttribute("screenFlowManager");
		
		log.debug("Current Screen >> "+screenFlowManager.getCurrentScreen());
		
		origForm.setCurrentScreen(screenFlowManager.getCurrentScreen());
		
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.POPUP_DIALOG;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
