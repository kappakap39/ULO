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
package com.eaf.orig.shared.view.webaction;

import java.math.BigDecimal;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.ApprovAuthorM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.ApprovAuthorEvent;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * Type: SaveApprovAuthorWebAction
 */
public class SaveApprovAuthorWebAction extends WebActionHelper implements
		WebAction {
	Logger log = Logger.getLogger(SaveApprovAuthorWebAction.class);
	private ApprovAuthorM approvAuthorM;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		ApprovAuthorEvent approvAthrEvent = new ApprovAuthorEvent();
		
		approvAthrEvent.setEventType(ApprovAuthorEvent.APPROV_AUTHOR_SAVE);
		log.debug("ApprovAuthorEvent.APPROV_AUTHOR_SAVE=" + ApprovAuthorEvent.APPROV_AUTHOR_SAVE);
		approvAthrEvent.setObject(approvAuthorM);
		log.debug("approvAuthorM=" + approvAuthorM);
		log.debug("approvAthrEvent=" + approvAthrEvent);
		
		return approvAthrEvent;
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
//		clear session for search field
		getRequest().getSession().removeAttribute("FIRST_SEARCH_groupName");
		getRequest().getSession().removeAttribute("FIRST_SEARCH_loanType");
		getRequest().getSession().removeAttribute("FIRST_SEARCH_customerType");
		
		ORIGUtility utility = new ORIGUtility();
		
		String grpName = getRequest().getParameter("grpName");
		String loanTyp = getRequest().getParameter("loanTyp");
		String cusType = getRequest().getParameter("cusType");
		BigDecimal creditApprov = utility.stringToBigDecimal(getRequest().getParameter("creditApprov"));
		BigDecimal minDnPayHvGua = utility.stringToBigDecimal(getRequest().getParameter("minDnPayHvGua"));
		BigDecimal minTrmHvGua = utility.stringToBigDecimal(getRequest().getParameter("minTrmHvGua"));
		BigDecimal maxTrmHvGua = utility.stringToBigDecimal(getRequest().getParameter("maxTrmHvGua"));
		BigDecimal minDnPayNoGua = utility.stringToBigDecimal(getRequest().getParameter("minDnPayNoGua"));
		BigDecimal minTrmNoGua = utility.stringToBigDecimal(getRequest().getParameter("minTrmNoGua"));
		BigDecimal maxTrmNoGua = utility.stringToBigDecimal(getRequest().getParameter("maxTrmNoGua"));
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
		String userName = userM.getUserName();
		 
		 log.debug("grpName ="+grpName);
		 log.debug("loanTyp ="+loanTyp);
		 log.debug("cusType ="+cusType);
		 log.debug("creditApprov ="+creditApprov);
		 log.debug("minDnPayHvGua ="+minDnPayHvGua);
		 log.debug("minTrmHvGua ="+minTrmHvGua);
		 log.debug("maxTrmHvGua ="+maxTrmHvGua);
		 log.debug("minDnPayNoGua ="+minDnPayNoGua);
		 log.debug("minTrmNoGua ="+minTrmNoGua);
		 log.debug("maxTrmNoGua ="+maxTrmNoGua);
		 log.debug("userName ="+userName);
		 		 
		 approvAuthorM = new ApprovAuthorM(); 
		 
		 approvAuthorM.setGroupName(grpName);
		 approvAuthorM.setLoanType(loanTyp);
		 approvAuthorM.setCustomerType(cusType);
		 approvAuthorM.setCreditApproval(creditApprov);
		 approvAuthorM.setMinDownGua(minDnPayHvGua);
		 approvAuthorM.setMinTermGua(minTrmHvGua);
		 approvAuthorM.setMaxTermGua(maxTrmHvGua);
		 approvAuthorM.setMinDownNoGua(minDnPayNoGua);
		 approvAuthorM.setMinTermNoGua(minTrmNoGua);
		 approvAuthorM.setMaxTermNoGua(maxTrmNoGua);
		 approvAuthorM.setCreateBy(userName);
		 approvAuthorM.setUpdateBy(userName);
		
		 ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
			origMasterForm.setFormErrors(new Vector());
			if (approvAuthorM.getGroupName()==null || "".equalsIgnoreCase(approvAuthorM.getGroupName())){
				origMasterForm.getFormErrors().add("Group Name is required");
			}
			if (approvAuthorM.getLoanType()==null || "".equalsIgnoreCase(approvAuthorM.getLoanType())){
				origMasterForm.getFormErrors().add("Loan Type is required");
			}
			if (approvAuthorM.getCustomerType()==null || "".equalsIgnoreCase(approvAuthorM.getCustomerType())){
				origMasterForm.getFormErrors().add("Customer Type is required");
			}
			
			if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
				getRequest().getSession().setAttribute("ADD_APPROV_AUTHOR_DATAM",approvAuthorM);
				return false;
			}else {
				getRequest().getSession().setAttribute("ADD_APPROV_AUTHOR_DATAM",approvAuthorM);
				return true;
			}
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		 
		return FrontController.ACTION;
	}
	protected void doFail(EventResponse arg0) {
		log.debug("sorry i'm in do fail.!!!" );
		ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
        String errMsg = arg0.getMessage();
        origMasterForm.getFormErrors().add(errMsg);
	}
	protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);		
		getRequest().getSession().removeAttribute("ADD_APPROV_AUTHOR_DATAM");
				
//		***Refresh Cache
//    	log.debug("sorry i'm refreshing Cache.!!!" );
//		com.eaf.cache.TableLookupCache.refreshAll();
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
