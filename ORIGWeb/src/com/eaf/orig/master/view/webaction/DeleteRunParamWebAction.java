/*
 * Created on Nov 27, 2007
 * Created by Prawit Limwattanachai
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.master.view.webaction;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.RunningParamM;
import com.eaf.orig.shared.control.event.RunParamEvent;

/**
 * @author Administrator
 * 
 * Type: DeleteGenParamWebAction
 */
public class DeleteRunParamWebAction extends WebActionHelper implements WebAction {

    Logger log = Logger.getLogger(DeleteRunParamWebAction.class);

    Vector runParamToDelete;

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {

        RunParamEvent runParamEvent = new RunParamEvent();
        runParamEvent.setEventType(RunParamEvent.RUN_PARAM_DELETE);

        log.debug("RunParamEvent.GEN_PARAM_DELETE=" + RunParamEvent.RUN_PARAM_DELETE);

        runParamEvent.setObject(runParamToDelete);

        log.debug("genParamToDelete = " + runParamToDelete);
        log.debug("genParamEvent=" + runParamEvent);

        return runParamEvent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {

        return defaultProcessResponse(response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        String[] strRunParamToDelete = getRequest().getParameterValues("runningParamChk");

        // *** log genParamToDelete
        if (strRunParamToDelete != null && strRunParamToDelete.length > 0) {
            runParamToDelete = new Vector();
            log.debug("////// strRunParamToDelete =" + strRunParamToDelete);
            log.debug("////// strRunParamToDelete.length =" + strRunParamToDelete.length);
            for (int i = 0; i < strRunParamToDelete.length; i++) {
                RunningParamM runningPramaM = new RunningParamM();
                log.debug("////// genParamToDelete " + i + " = " + strRunParamToDelete[i]);
                String[] param = strRunParamToDelete[i].split(",");
                if (param != null && param.length >= 2) {
                    runningPramaM.setParamID(param[0]);
                    runningPramaM.setParamType(param[1]);
                    runParamToDelete.add(runningPramaM);
                }                
            }
        }
        // ****

        if (strRunParamToDelete == null || strRunParamToDelete.length <= 0) {
            log.debug("////// strRunParamToDelete is null //////");
            return false;
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
         
        return FrontController.ACTION;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {

        //	System.out.println("Session FIRST_SEARCH_USER_NAME ->" +
        // getRequest().getSession(true).getAttribute("FIRST_SEARCH_USER_NAME"));

        return "action=SearchRunningParam";
    }

    protected void doSuccess(EventResponse arg0) {          
        ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
        getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
       // com.eaf.cache.TableLookupCache.refreshCache("RunParamDataM");
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
