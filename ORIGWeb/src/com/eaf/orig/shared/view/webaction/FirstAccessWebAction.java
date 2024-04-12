package com.eaf.orig.shared.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FirstAccessWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(FirstAccessWebAction.class);
	private String action="";
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
	    log.debug("++++++++++++++ Start CreateProposalWebAction  ++++++++++++++++++++");
		getRequest().getSession().removeAttribute("ORIGForm");
		ORIGFormHandler ORIGForm = new ORIGFormHandler();
		ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		if(ORIGForm == null){
				ORIGForm = new ORIGFormHandler();
		}
		getRequest().getSession().setAttribute("ORIGForm",ORIGForm);	
	 	
	 	UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
	 	Vector userRoles = userM.getRoles();
		String searchType = getRequest().getParameter("searchType");
		if(searchType==null){
			searchType = (String)getRequest().getSession().getAttribute("searchType");
		}
		
 		// set current role
		String role = getRequest().getParameter("role");
		if(role != null){
			userRoles.insertElementAt(role, 0);
			userM.setRoles(userRoles);
		}
		// end set
		
		System.out.println(">>> searchType="+searchType);
		if(searchType!=null&&searchType.equals("Proposal")){
			action="page=CREATE_PROPOSAL";
		}else if(searchType!=null&&(searchType.equals(OrigConstant.SEARCH_TYPE_PENDING_PROPOSAL)||searchType.equals(OrigConstant.SEARCH_TYPE_PROPOSAL))){
			action="action=FristApp";
		}else if(searchType!=null&&searchType.equals("ClientGroup")){
			action="page=SEARCH_CLIENT_GROUP";
		}
		log.debug("++++++++++++++ End CreateProposalWebAction  ++++++++++++++++++++");		
		return true;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		return FrontController.ACTION;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return action;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
