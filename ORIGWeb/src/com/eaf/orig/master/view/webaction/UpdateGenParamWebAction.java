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
import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.GenParamEvent;

/**
 * @author Administrator
 *
 * Type: UpdateGenParamWebAction
 */
public class UpdateGenParamWebAction extends WebActionHelper implements
		WebAction {

	Logger log = Logger.getLogger(UpdateGenParamWebAction.class);
	private GeneralParamM genParamM;
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		GenParamEvent genParamEvent = new GenParamEvent();
		genParamEvent.setEventType(GenParamEvent.GEN_PARAM_UPDATE);
		
		log.debug("GenParamEvent.GEN_PARAM_UPDATE=" + GenParamEvent.GEN_PARAM_UPDATE);
		
		genParamEvent.setObject(genParamM);
		
		log.debug("genParamM=" + genParamM);
		log.debug("genParamEvent=" + genParamEvent);
		
		return genParamEvent;
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
		
		String Code = getRequest().getParameter("Code");
		String busClass = getRequest().getParameter("busClass");
		String Value = getRequest().getParameter("Value");
		String Value2 = getRequest().getParameter("Value2");
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		 
		 log.debug("Code ="+Code);
		 log.debug("busClass ="+busClass);
		 log.debug("Value ="+Value);
		 log.debug("Value2 ="+Value2);
		 log.debug("userName ="+userName);
		 		 
		 genParamM = new GeneralParamM(); 
		 
		 genParamM.setParamCode(Code);
		 genParamM.setBusClassID(busClass);
		 genParamM.setParamValue(Value);
		 genParamM.setParamValue2(Value2);
		 genParamM.setUpdateBy(userName);
		 
		 ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
			origMasterForm.setFormErrors(new Vector());
			if (genParamM.getParamCode()==null || "".equalsIgnoreCase(genParamM.getParamCode())){
				origMasterForm.getFormErrors().add("Parameter Code is required");
			}
			if (genParamM.getBusClassID()==null || "".equalsIgnoreCase(genParamM.getBusClassID())){
				origMasterForm.getFormErrors().add("Business Class is required");
			}
			if (genParamM.getParamValue()==null || "".equalsIgnoreCase(genParamM.getParamValue())){
				origMasterForm.getFormErrors().add("Parameter Value is required");
			}
			
			if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
				getRequest().getSession().setAttribute("EDIT_GEN_PARAM_M",genParamM);
				return false;
			}else {
				return true;
			}
	}

	public int getNextActivityType() {
         
        return FrontController.ACTION;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return "action=SearchGenParam";
    }
    
    protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
//		***Refresh Cache
		log.debug("i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshCache("GeneralParamDataM");
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
