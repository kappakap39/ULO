package com.eaf.orig.ulo.pl.app.view.webaction;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class VerifyNCBPopupWebAction extends WebActionHelper implements WebAction{

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
		String comment = getRequest().getParameter("cb-comment");
	    SearchHandler HandlerM = (SearchHandler) getRequest().getSession().getAttribute("SEARCH_DATAM");
		if(HandlerM == null){
			HandlerM = new SearchHandler();
		}
		SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
		if(searchDataM == null){
			searchDataM = new SearchHandler.SearchDataM();
			HandlerM.setSearchM(searchDataM);
		}
		searchDataM.setComment(comment);
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.POPUP_DIALOG;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}
}
