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
import com.eaf.orig.master.shared.model.CarBlacklistM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.CarBlacklistEvent;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: UpdateCarBlacklistWebAction
 */
public class UpdateCarBlacklistWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(SaveCarBlacklistWebAction.class);
	private CarBlacklistM carBlacklistM;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		CarBlacklistEvent carBlacklistEvent = new CarBlacklistEvent();
		carBlacklistEvent.setEventType(CarBlacklistEvent.CAR_BKLT_UPDATE);
		
		log.debug("CarBlacklistEvent.CAR_BKLT_UPDATE=" + CarBlacklistEvent.CAR_BKLT_UPDATE);
		
		carBlacklistEvent.setObject(carBlacklistM);
		
		log.debug("carBlacklistM=" + carBlacklistM);
		log.debug("carBlacklistEvent=" + carBlacklistEvent);
		
		return carBlacklistEvent;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {

		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		
		return defaultProcessResponse(response);
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		ORIGUtility utility = new ORIGUtility();
		String classNum = getRequest().getParameter("classNum");
		String engineNum = getRequest().getParameter("engineNum");
		String regisNum = getRequest().getParameter("regisNum");
		String blckSource = getRequest().getParameter("blckSource");
		String blckReason = getRequest().getParameter("blckReason");
		String blckNote = getRequest().getParameter("blckNote");
		String car_brand = getRequest().getParameter("car_brand");
		String car_model = getRequest().getParameter("car_model");
		
		String blackVehIDEdit = (String)getRequest().getSession().getAttribute("BLACK_VEH_ID_EDIT");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		 
		log.debug("classNum ="+classNum);
		 log.debug("engineNum ="+engineNum);
		 log.debug("regisNum ="+regisNum);
		 log.debug("blckSource ="+blckSource);
		 log.debug("blckReason ="+blckReason);
		 log.debug("blckNote ="+blckNote);
		 log.debug("car_brand ="+car_brand);
		 log.debug("car_model ="+car_model);
		 log.debug("userName ="+userName);
		 log.debug("blackVehIDEdit ="+blackVehIDEdit);
		 		 
		 carBlacklistM = new CarBlacklistM(); 
		 
		 carBlacklistM.setBlVehicleID(utility.stringToInt(blackVehIDEdit));
		 carBlacklistM.setChassisNum(classNum);
		 carBlacklistM.setEngineNum(engineNum);
		 carBlacklistM.setRegistrationNum(regisNum);
		 carBlacklistM.setBlSource(blckSource);
		 carBlacklistM.setBlReason(blckReason);
		 carBlacklistM.setBlNote(blckNote);
		 carBlacklistM.setBrand(car_brand);
		 carBlacklistM.setModel(car_model);
//		 carBlacklistM.setCreateBy(userName);
		 carBlacklistM.setUpdateBy(userName);
		
		 ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
			origMasterForm.setFormErrors(new Vector());
			if (carBlacklistM.getChassisNum()==null || "".equalsIgnoreCase(carBlacklistM.getChassisNum())){
				origMasterForm.getFormErrors().add("Classis No. is required");
			}
			if (carBlacklistM.getEngineNum()==null || "".equalsIgnoreCase(carBlacklistM.getEngineNum())){
				origMasterForm.getFormErrors().add("Engine No. is required");
			}
			if (carBlacklistM.getRegistrationNum()==null ||"".equalsIgnoreCase(carBlacklistM.getRegistrationNum())){
				origMasterForm.getFormErrors().add("Registration No. is required");
			}
			if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
				getRequest().getSession().setAttribute("EDIT_CAR_BLACKLIST_DATAM",carBlacklistM);
				return false;
			}else {
				return true;
			}
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {

		return FrontController.ACTION;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return "action=SearchCarBlacklist";
    }
    
    protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
//		***Refresh Cache
    	log.debug("sorry i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshAll();
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
