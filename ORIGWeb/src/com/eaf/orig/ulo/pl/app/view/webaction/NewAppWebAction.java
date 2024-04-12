package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;

public class NewAppWebAction extends WebActionHelper implements WebAction{
	static Logger log = Logger.getLogger(NewAppWebAction.class);
	private String action = "";
    private int next = 0;
	
	@Override
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean preModelRequest() {
		// TODO Auto-generated method stub
		log.debug("++++++++++++++ Start NewAppWebAction  ++++++++++++++++++++");
        getRequest().getSession().removeAttribute("PLORIGForm");
        PLOrigFormHandler ORIGForm = new PLOrigFormHandler();
        ORIGForm = (PLOrigFormHandler) getRequest().getSession().getAttribute("PLORIGForm");
        if (ORIGForm == null) {
            ORIGForm = new PLOrigFormHandler();
        }
        
        getRequest().getSession().setAttribute("PLORIGForm", ORIGForm);

        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        Vector userRoles = userM.getRoles();
        // set current role
        String role = getRequest().getParameter("role");
        if (role != null) {
            if (!role.equals(userRoles.elementAt(0))) {
                userRoles.insertElementAt(role, 0);
            }
            userM.setRoles(userRoles);
        }
        // end set

        log.debug("LoadApplicationWebAction : userRoles(0) = " + userRoles.get(0));

        log.debug("++++++++++++++ End NewAppWebAction  ++++++++++++++++++++");
        return true;
    }
	@Override
	public int getNextActivityType() {
        return next;
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
