package com.eaf.orig.shared.valuelist.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.search.SearchM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class SearchMWebAction extends WebActionHelper implements WebAction {
	static Logger logger = Logger.getLogger(SearchMWebAction.class);
	@Override
	public Event toEvent(){
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
		try{
			SearchM searchM = (SearchM) getRequest().getSession().getAttribute("SearchM");
			int atPage = 1;			
			int itemsPerPage = searchM.getItemsPerPage();
			
			String atPageStr = getRequest().getParameter("atPage");
			
			String itemsPerPageStr = getRequest().getParameter("itemsPerPage");
			if(itemsPerPageStr == null || "".equals(itemsPerPageStr)){
				itemsPerPageStr = "20";
			}
			
			logger.debug("atPageStr : "+atPageStr);			
			logger.debug("itemsPerPageStr : "+itemsPerPageStr);
			
			if(atPageStr!=null && !atPageStr.equals("")) atPage = Integer.parseInt(atPageStr);
			
			if(itemsPerPageStr!=null && !itemsPerPageStr.equals("")){ 
				int itemsPerPageTemp = Integer.parseInt(itemsPerPageStr);
				if(itemsPerPage!=itemsPerPageTemp){ 
					itemsPerPage = itemsPerPageTemp;
					searchM.setAtPage(1);
				}
			}
			
			searchM.setItemsPerPage(itemsPerPage);
			searchM.setAtPage(atPage);
			
			logger.debug("valueListM.getAtPage()"+searchM.getAtPage());
			
			ORIGDAOUtilLocal origBean =  PLORIGEJBService.getORIGDAOUtilLocal();
			searchM = origBean.getResultSearchM(searchM);	
			
			getRequest().getSession().setAttribute("SearchM", searchM);
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		SearchM searchM = (SearchM) getRequest().getSession().getAttribute("SearchM");
		if(searchM.getReturnToAction().equals("")) return FrontController.PAGE;
		return FrontController.ACTION;
	}
	@Override
	public String getNextActionParameter(){
		SearchM searchM = (SearchM) getRequest().getSession().getAttribute("SearchM");
		if(!searchM.getReturnToAction().equals("")) return searchM.getReturnToAction();
		return searchM.getReturnToPage();
	}
	@Override
	public boolean getCSRFToken() {
		return false;
	}
	
}
