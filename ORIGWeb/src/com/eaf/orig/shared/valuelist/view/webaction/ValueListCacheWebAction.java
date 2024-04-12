package com.eaf.orig.shared.valuelist.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.ValueListDAO;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author unchan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ValueListCacheWebAction extends WebActionHelper implements WebAction {
	
	private static Logger logger = Logger.getLogger(ValueListCacheWebAction.class);
	public static final String VALUE_LIST_SEARCH_CODE = "VALUE_LIST_SEARCH_CODE";
	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		return null;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		return false;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		try{
			ValueListM valueListM = (ValueListM)getRequest().getSession().getAttribute("VALUE_LIST");
			Vector cacheDataVect = (Vector)getRequest().getSession().getAttribute("CACHE_POPUP");
			
			int atPage = 1;
			
			int itemsPerPage = valueListM.getItemsPerPage();
			
			String atPageStr = getRequest().getParameter("atPage");
			
			String itemsPerPageStr = getRequest().getParameter("itemsPerPage");
			
			logger.debug("atPageStr : "+atPageStr);
			
			logger.debug("itemsPerPageStr : "+itemsPerPageStr);
			
			if(atPageStr!=null && !("").equals(atPageStr)) atPage = Integer.parseInt(atPageStr);
			
			if(itemsPerPageStr!=null && !("").equals(itemsPerPageStr)){ 
				int itemsPerPageTemp = Integer.parseInt(itemsPerPageStr);
				if(itemsPerPage!=itemsPerPageTemp){ 
					itemsPerPage = itemsPerPageTemp;
					valueListM.setAtPage(1);
				}
			}
			
			valueListM.setItemsPerPage(itemsPerPage);
			valueListM.setAtPage(atPage);
			
			ORIGUtility utility = new ORIGUtility();
			valueListM = utility.getDataFromCache(cacheDataVect, valueListM);
			
			getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
		}catch(Exception e){
			logger.fatal(e.toString());
			return false;
		}
		
		return true;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		ValueListM valueListM = (ValueListM)getRequest().getSession().getAttribute("VALUE_LIST");
		if(valueListM.getReturnToAction().equals("")) return FrontController.PAGE;
		return FrontController.ACTION;
	}

	public String getNextActionParameter(){
		ValueListM valueListM = (ValueListM)getRequest().getSession().getAttribute("VALUE_LIST");
		if(!valueListM.getReturnToAction().equals("")) return valueListM.getReturnToAction();
		return valueListM.getReturnToPage();
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
