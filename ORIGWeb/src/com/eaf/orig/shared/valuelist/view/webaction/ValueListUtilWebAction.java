package com.eaf.orig.shared.valuelist.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.common.utility.valuelist.ValueListUtil;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

/**
 * @author wpsadmin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ValueListUtilWebAction extends WebActionHelper implements WebAction {
	private static Logger log = Logger.getLogger(ValueListUtilWebAction.class);
	private String nextAction = "";
	private String userName = "";
	
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
		return defaultProcessResponse(response);
	}
	
	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		log.info(".....[ValueListUtilWebAction] preModelRequest()");

		boolean result = true;
		String errMsg = "";
		
		try {		
			String pageAction = this.getRequest().getParameter("PageAction");
			String pageNo = this.getRequest().getParameter("PageNo");
			String itemPerPage = this.getRequest().getParameter("ItemPerPage");			
			String nextURL = this.getRequest().getParameter("NextURL");			
			String valueListName = this.getRequest().getParameter("ValueListName");
						
			if (log.isDebugEnabled()){
				log.debug("..........pageAction is " + pageAction);
				log.debug("..........pageNo is " + pageNo);
				log.debug("..........itemPerPage is " + itemPerPage);
				log.debug("..........nextURL is " + nextURL);
				log.debug("..........valueListName is " + valueListName);
			}
			if (pageAction != null){
				pageAction = pageAction.trim();
			}else{
				pageAction ="";
			}
			
			ValueListUtil vListUtil = (ValueListUtil) this.getRequest().getSession().getAttribute(valueListName);
			
			if (vListUtil != null){
				if (pageAction.equalsIgnoreCase(ValueListUtil.ValueListPageAction.NEXT_PAGE)){
					log.debug("..........Process vListUtil.nextPage()");
					vListUtil.nextPage();
				}else if (pageAction.equalsIgnoreCase(ValueListUtil.ValueListPageAction.PREVIOUS_PAGE)){
					log.debug("..........Process vListUtil.previousPage()");
					vListUtil.previousPage();
				}else if (pageAction.equalsIgnoreCase(ValueListUtil.ValueListPageAction.GO_PAGE)){	
					log.debug("..........Process vListUtil.goPageData()");
					vListUtil.goPageData(Integer.parseInt(pageNo));
				}else if (pageAction.equalsIgnoreCase(ValueListUtil.ValueListPageAction.CHANGE_ITEMS_PER_PAGE)){	
					log.debug("..........Process vListUtil.setItemsPerPage()");
					vListUtil.setItemsPerPage(Integer.parseInt(itemPerPage));
				}else if (pageAction.equalsIgnoreCase(ValueListUtil.ValueListPageAction.NEXT_PAGE_INTERVAL)){	
					log.debug("..........Process vListUtil.nextIntervalPage()");
					vListUtil.nextIntervalPage();
				}else if (pageAction.equalsIgnoreCase(ValueListUtil.ValueListPageAction.PREVIOUS_PAGE_INTERVAL)){	
					log.debug("..........Process vListUtil.previousIntervalPage()");
					vListUtil.previousIntervalPage();
				}else{
					errMsg = "VLUWA0001 :: [ValueListUtilWebAction] preModelRequest() Warn >> Invalid PageAction.";
					log.warn(".....[ValueListUtilWebAction] preModelRequest() Warn >> Invalid PageAction.");
					System.err.println(".....[ValueListUtilWebAction] preModelRequest() Warn >> Invalid PageAction.");
				}
				
				this.getRequest().getSession().setAttribute(valueListName, vListUtil);
				
			}else{
				errMsg = "VLUWA0002 :: [ValueListUtilWebAction] preModelRequest() Error >> ValueListUtil is not loaded.";
				log.fatal(".....[ValueListUtilWebAction] preModelRequest() Error >> ValueListUtil is not loaded.");
				System.err.println(".....[ValueListUtilWebAction] preModelRequest() Error >> ValueListUtil is not loaded.");
				result = false;
			}
						
			if (nextURL != null && nextURL.trim().length() > 0){
				this.nextAction = nextURL;
			}else{
				errMsg = "VLUWA0003 :: [ValueListUtilWebAction] preModelRequest() Error >> ValueListUtil is not loaded.";
				log.fatal(".....[ValueListUtilWebAction] preModelRequest() Error >> ValueListUtil is not loaded.");
				System.err.println(".....[ValueListUtilWebAction] preModelRequest() Error >> ValueListUtil is not loaded.");
				result = false;
			}
									
		} catch (Exception e) {
			errMsg = "VLUWA0004 :: [ValueListUtilWebAction] preModelRequest() Error >> " + e.getMessage(); 
			log.fatal(".....[ValueListUtilWebAction] preModelRequest() Error >> " + e.getMessage());
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	
	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return this.nextAction;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}


}
