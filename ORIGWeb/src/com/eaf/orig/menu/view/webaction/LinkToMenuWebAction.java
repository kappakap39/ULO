package com.eaf.orig.menu.view.webaction;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

/**
 * @author kitinat
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LinkToMenuWebAction extends WebActionHelper implements WebAction {

	static Logger log = Logger.getLogger(LinkToMenuWebAction.class);
/**
	 * @see com.bara.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	String linkTo="";
	public Event toEvent() {
		return null;
	}
	/**
	 * @see com.bara.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		return false;
	}
	/**
	 * @see com.bara.j2ee.pattern.view.webaction.WebAction#processEventResponse(EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		return false;
	}
	/**
	 * @see com.bara.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		String goTo = getRequest().getParameter("go");
		HashMap hMap = (HashMap)getRequest().getSession().getAttribute("hMap");
		log.debug("go to ==>" + goTo);
		String GOTO = goTo.toUpperCase();
		log.debug("hMap.............= "+hMap);
		if(hMap!=null){
			if(hMap.containsKey(goTo)){
			 	linkTo = (String) hMap.get(goTo);
				log.debug("Link to ==>" + linkTo);	
				//getRequest().getSession().removeAttribute("hMap");
				return true;
			}else if(hMap.containsKey(GOTO)){
				linkTo = (String) hMap.get(GOTO);
				log.debug("Link to ==>" + linkTo);		
				//getRequest().getSession().removeAttribute("hMap");
				return true;		
			}
			else{
			return false;
			}
		}else{
			return false;
		}
	}
	/**
	 * @see com.bara.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		return FrontController.ACTION;
  	}
  	public String getNextActionParameter() {
  	String re	="action=Menu_Show&menuSequence="+linkTo ;
  	return re ;	
  	}
	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
