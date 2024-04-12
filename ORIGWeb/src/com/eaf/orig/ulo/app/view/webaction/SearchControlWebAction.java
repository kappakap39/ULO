package com.eaf.orig.ulo.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

public class SearchControlWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(SearchControlWebAction.class);
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
		try{
			SearchFormHandler searchForm = (SearchFormHandler)getRequest().getSession().getAttribute(SearchFormHandler.SearchForm);
			if(null == searchForm){
				searchForm = new SearchFormHandler(getRequest());
			}
			int atPage = 1;			
			int itemsPerPage = searchForm.getItemsPerPage();
			String atPageStr = getRequest().getParameter("atPage");
			String itemsPerPageStr = getRequest().getParameter("itemsPerPage");			
			if(Util.empty(itemsPerPageStr)){
				itemsPerPageStr = FormatUtil.getString(searchForm.getItemsPerPage());		
			}
			if(!Util.empty(atPageStr)){
				atPage = Integer.parseInt(atPageStr);
			}
			if(!Util.empty(itemsPerPageStr)){ 
				int itemsPerPageTemp = Integer.parseInt(itemsPerPageStr);
				if(itemsPerPage!=itemsPerPageTemp){ 
					itemsPerPage = itemsPerPageTemp;
					searchForm.setAtPage(1);
				}
			}			
			searchForm.setItemsPerPage(itemsPerPage);
			searchForm.setAtPage(atPage);			
			logger.debug("searchForm.getAtPage : "+searchForm.getAtPage());		
			logger.debug("searchForm.getItemsPerPage : "+searchForm.getItemsPerPage());	
			try{
				SearchQueryEngine SearchEngine = new SearchQueryEngine();
					SearchEngine.search(searchForm);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}		
			getRequest().getSession().setAttribute(SearchFormHandler.SearchForm,searchForm);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return true;
	}
	@Override
	public int getNextActivityType() {		
		SearchFormHandler searchForm = (SearchFormHandler)getRequest().getSession().getAttribute(SearchFormHandler.SearchForm);
		return Util.empty(searchForm.getReturnAction())?FrontController.PAGE:FrontController.ACTION;
	}
	@Override
	public String getNextActionParameter() {
		SearchFormHandler searchForm = (SearchFormHandler)getRequest().getSession().getAttribute(SearchFormHandler.SearchForm);
		return Util.empty(searchForm.getReturnAction())?"":searchForm.getReturnAction();
	}
}
