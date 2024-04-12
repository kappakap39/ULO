package com.eaf.orig.shared.view.webaction;

import java.util.HashMap;
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
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.SLADataM;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class LoadReKeyAppWebAction extends WebActionHelper implements WebAction {
    static Logger log = Logger.getLogger(LoadReKeyAppWebAction.class);

    private String action = "";

    private int next = 0;

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
        try {
            log.debug("++++++++++++++ Start LoadReKeyAppWebAction  ++++++++++++++++++++");
            	
            log.debug("++++++++++++++ End LoadReKeyAppWebAction  ++++++++++++++++++++");
            return true;
        } catch (Exception e) {
            log.error("ERROR",e);
            return false;
        }
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
        return "page=REKEY_SCREEN";
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
