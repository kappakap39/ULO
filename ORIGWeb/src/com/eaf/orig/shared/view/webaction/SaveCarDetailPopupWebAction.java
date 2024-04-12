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
import com.eaf.orig.shared.constant.EJBConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author admin
 *
 * Type: SaveCarDetailWebAction
 */
public class SaveCarDetailPopupWebAction extends WebActionHelper implements
        WebAction {
    Logger logger = Logger.getLogger(SaveCarDetailPopupWebAction.class);

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
		try{
		ApplicationDataM appM = ORIGForm.getAppForm();
		VehicleDataM vehicleM = appM.getVehicleDataM();
		if(vehicleM==null){
		    vehicleM = new VehicleDataM();
		    logger.debug("Vehicle is null");
		    }else{
		        if(vehicleM.getVehicleID()==0){ 
		            //int vehicleID=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.DUP_VEHICLE_ID));
		        	ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
		        	int vehicleID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.DUP_VEHICLE_ID));
		        	vehicleM.setVehicleID(vehicleID);
		            vehicleM.setDrawDownStatus("NEW");
		            }
		    }
		
		Vector loanVt = appM.getLoanVect();
		LoanDataM loanM = null;
		if(loanVt==null){
		    loanVt = new Vector();
		    logger.debug("Loan is null");
		    }else{
		        loanM = (LoanDataM)loanVt.get(0);
		        vehicleM.setLoanDataM(loanM);
		        }

	    getRequest().getSession().setAttribute("REWRITE_FLAG","Y");
        return true;
		}catch (Exception e) {
            logger.error("Exception:",e);
            return false;
        }
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
