package com.eaf.orig.logon.view.webaction;

import java.util.Vector;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.UserLogOnEvent;

/**
 * @author burin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UserLogOnWebAction extends WebActionHelper implements WebAction {

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		// type on = 1 off= 0
		String uname = "";
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if (null != userM && null != userM.getUserName())
			uname = userM.getUserName();

		Vector userRole = userM.getRoles();
//		System.out.println(">>> userRole="+userRole);
		String role = "";
		if (userRole.contains("CA")) {
			role = "ca";
		} else if (userRole.contains("FU")) {
			role = "fu";
		} else if (userRole.contains("DE")) {
			role = "de";
		}
		String type = getRequest().getParameter("type");
		if (type.equalsIgnoreCase("in")) {
			return new UserLogOnEvent(uname, UserLogOnEvent.TYPE_ON, userM.getRoles());
		} else {
			return new UserLogOnEvent(uname, UserLogOnEvent.TYPE_OFF, new Vector());
		}
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		return true;
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
//		System.out.println("FirstLogon[UserLogOnWebAction] = "+getRequest().getSession().getAttribute("FirstLogon"));
//		System.out.println("FirstLogon SessionID[UserLogOnWebAction] = "+getRequest().getSession().getId());
		return true;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	public String getNextActionParameter() {
		String re = "action=Menu_Show";
		return re;
	}
	public void doSuccess(EventResponse erp) {
		try {

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void doFail(EventResponse erp) {
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
