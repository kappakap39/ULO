/*
 * Created on Nov 5, 2007
 * Created by admin
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
package com.eaf.orig.shared.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author admin
 *
 * Type: SaveCarDetailWebAction
 */
public class SaveCarAppWebAction extends WebActionHelper implements
        WebAction {
    Logger logger = Logger.getLogger(SaveCarAppWebAction.class);

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
         
        return null;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
         
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
         
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {

		ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");
	    boolean resultFlag = false;
		try{
		    logger.debug("Start SaveCarAppWebAction");
		    ApplicationDataM appM = ORIGForm.getAppForm();
		    
			    Vector personalVt = appM.getPersonalInfoVect();
			    PersonalInfoDataM personM = (PersonalInfoDataM)personalVt.get(0);
			    Vector resultTemp = new Vector();
			    Vector result = (Vector)getRequest().getSession().getAttribute("VEHICLE_RESULT");
			    getRequest().removeAttribute("VEHICLE_RESULT");
			    VehicleDataM vehicleDataM = null;
			    if(result!=null && result.size()>0){
			        for(int i=0;i<result.size();i++){
			            vehicleDataM = (VehicleDataM)result.get(i);
			            //if(vehicleDataM.getDrawDownStatus()!=null&&vehicleDataM.getDrawDownStatus().equals("NEW")){
			                resultTemp.add(vehicleDataM);
			                }
			            //}
			        }
			        
			    logger.debug("size of vehicle vector is:"+resultTemp.size());
			    ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManager();
			    resultFlag = applicationManager.saveCarDetail(resultTemp,personM.getIdNo());
			
		    logger.debug("End SaveCarAppWebAction");
		}catch (Exception e) {
            logger.error("Exception:",e);
        }
		getRequest().getSession().removeAttribute("VALUE_LIST");
        return resultFlag;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
         
        return 0;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
