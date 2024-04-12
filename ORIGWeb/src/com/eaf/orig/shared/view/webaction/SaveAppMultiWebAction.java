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
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.SerializeUtil;

/**
 * @author Administrator
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class SaveAppMultiWebAction extends WebActionHelper implements WebAction {
    static Logger log = Logger.getLogger(SaveAppMultiWebAction.class);

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

    public boolean preModelRequest() {
		try {
	        ORIGFormHandler formHandler=(ORIGFormHandler)getRequest().getSession().getAttribute("ORIGForm");
			ApplicationDataM applicationDataM = (ApplicationDataM)SerializeUtil.clone(formHandler.getAppForm());
	        formHandler.getResultAppMs().addElement(applicationDataM);						
			ApplicationDataM appM = new ApplicationDataM();	        
	        PersonalInfoDataM personalInfoM  = (PersonalInfoDataM)getRequest().getSession().getAttribute("personalInfoSession");	        	        	        	        
	        Vector vPersonnals = new Vector(); 
	        vPersonnals.add(personalInfoM);
	        appM.setPersonalInfoVect(vPersonnals);
	        formHandler.setAppForm(appM);
	        
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
        return "page=MULTI_APPFORM";
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
