package com.eaf.orig.shared.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.PopupMasterDataM;

public class PopupMasterWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(PopupMasterWebAction.class);
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
		PopupMasterDataM popupM = new PopupMasterDataM();
			
		String searchTextBoxID = getRequest().getParameter("search-textbox-id");
		String moduleWebAction = getRequest().getParameter("module-webaction");
		String typeSearch = getRequest().getParameter("type-search");
		String searchValue = getRequest().getParameter("search-value");
		
		logger.debug("[searchTextBoxID] "+searchTextBoxID);
		logger.debug("[moduleWebAction] "+moduleWebAction);
		logger.debug("[typeSearch] "+typeSearch);
		logger.debug("[searchValue] "+searchValue);
		
		popupM.setModuleWebAction(moduleWebAction);
		popupM.setSearchTextBoxID(searchTextBoxID);
		popupM.setTypeSearch(typeSearch);
		popupM.setSearchValue(searchValue);
			
		getRequest().getSession(true).setAttribute("SearchPopupM", popupM);
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.POPUP_DIALOG;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
