/*
 * Created on Nov 21, 2007
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
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.control.event.OrigMasterEvent;

/**
 * @author Administrator
 *
 * Type: UpdateUsrDetailWebAction
 */
public class UpdateUsrDetailWebAction extends WebActionHelper implements
		WebAction {

	Logger log = Logger.getLogger(UpdateUsrDetailWebAction.class);
	private UserDetailM userDetailM;

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		
		OrigMasterEvent origMasterEvent = new OrigMasterEvent();
		origMasterEvent.setEventType(OrigMasterEvent.USER_DETAIL_UPDATE);
		
		log.debug("OrigMasterEvent.USER_DETAIL_UPDATE=" + OrigMasterEvent.USER_DETAIL_UPDATE);
		
		origMasterEvent.setObject(userDetailM);
		
		log.debug("userDetailM=" + userDetailM);
		log.debug("origMasterEvent=" + origMasterEvent);
		
		return origMasterEvent;
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
		
		String userName = getRequest().getParameter("userName");
		 String firstName = getRequest().getParameter("firstName");
		 String lastName = getRequest().getParameter("lastName");
		 String description = getRequest().getParameter("description");
		 String region = getRequest().getParameter("region");
		 String telephone = getRequest().getParameter("telephone");
		 String mobilePhone = getRequest().getParameter("mobilePhone");
		 String email = getRequest().getParameter("email");
		 String jobDescription = getRequest().getParameter("jobDescription");
		 String zoneID = getRequest().getParameter("zone");
		 String department = getRequest().getParameter("department");
		 String status = getRequest().getParameter("active");
		 String position = getRequest().getParameter("position");
		 String skipIP = getRequest().getParameter("skipIP");
		 String communicationChannel = getRequest().getParameter("communicationChannel");
		 String officeCode = getRequest().getParameter("officeCode");
		 String unlockChk = getRequest().getParameter("unlockChk");
		 String imagePath = getRequest().getParameter("imagePath");
		 
		 UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			if(null == userM){
				userM = new UserDetailM();
			}
			String updateBy = userM.getUserName();
		 
		 log.debug("userName ="+userName);
		 log.debug("firstName ="+firstName);
		 log.debug("lastName ="+lastName);
		 log.debug("description ="+description);
		 log.debug("position ="+position);
		 log.debug("communicationChannel ="+communicationChannel);
		 log.debug("officeCode ="+officeCode);
		 log.debug("skipIP ="+skipIP);
		 log.debug("status ="+status);
		 log.debug("unlockChk ="+unlockChk);
		 log.debug("updateBy ="+updateBy);
		 log.debug("imagePath ="+imagePath);
		 
		 userDetailM =  (UserDetailM) getRequest().getSession().getAttribute("USER_DETAIL_M_SESSION_FROM_PROFILE");
		 if(userDetailM==null){
		 	userDetailM = new UserDetailM();
		 }
		 
		userDetailM.setUserName(userName);
		userDetailM.setFirstName(firstName);
		userDetailM.setLastName(lastName);
		userDetailM.setDescription(description);
		userDetailM.setRegion(region);
		userDetailM.setTelephone(telephone);
		userDetailM.setMobilePhone(mobilePhone);
		userDetailM.setPosition(position);
		userDetailM.setEmail(email);
		userDetailM.setJobDescription(jobDescription);
		userDetailM.setZoneID(zoneID);
		userDetailM.setDepartment(department);
		userDetailM.setPosition(position);
		userDetailM.setStatus(status);
		userDetailM.setSkipIP(skipIP);
		userDetailM.setCommunicate_channel(communicationChannel);
		userDetailM.setDefaultOfficeCode(officeCode);
		userDetailM.setUpdateBy(updateBy);
		userDetailM.setImgPath(imagePath);
		
		if("Y".equalsIgnoreCase(unlockChk)){
		 	userDetailM.setLogOnFlag("Y");
		 }else{
		 	userDetailM.setLogOnFlag("");
		 }
		
		ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
		origMasterForm.setFormErrors(new Vector());
		if (userDetailM.getUserName()==null || "".equalsIgnoreCase(userDetailM.getUserName())){
			origMasterForm.getFormErrors().add("User Name is required");
		}
		if (userDetailM.getFirstName()==null || "".equalsIgnoreCase(userDetailM.getFirstName())){
			origMasterForm.getFormErrors().add("First Name is required");
		}
		if (userDetailM.getLastName()==null || "".equalsIgnoreCase(userDetailM.getLastName())){
			origMasterForm.getFormErrors().add("Last Name is required");
		}
		if (userDetailM.getDescription()==null || "".equalsIgnoreCase(userDetailM.getDescription())){
			origMasterForm.getFormErrors().add("User Description is required");
		}
		if (userDetailM.getStatus()==null || "".equalsIgnoreCase(userDetailM.getStatus())){
			origMasterForm.getFormErrors().add("Active is required");
		}
		if (userDetailM.getCommunicate_channel()==null || "".equalsIgnoreCase(userDetailM.getCommunicate_channel())){
			origMasterForm.getFormErrors().add("Communication Channel is required");
		}
		if("1".equalsIgnoreCase(userDetailM.getCommunicate_channel())){
			if(userDetailM.getEmail()==null || "".equalsIgnoreCase(userDetailM.getEmail())){
				origMasterForm.getFormErrors().add("Email is required");
			}
		}
		if("2".equalsIgnoreCase(userDetailM.getCommunicate_channel())){
			if(userDetailM.getMobilePhone()==null || "".equalsIgnoreCase(userDetailM.getMobilePhone())){
				origMasterForm.getFormErrors().add("Mobile Phone is required");
			}
		}
		if (userDetailM.getUserBranchVect()==null || userDetailM.getUserBranchVect().size()<=0){
			origMasterForm.getFormErrors().add("Branch is required");
		}
		
		if (origMasterForm.getFormErrors()!=null && origMasterForm.getFormErrors().size() > 0){
			getRequest().getSession().setAttribute("EDIT_USER_DETAIL_DATAM",userDetailM);
			return false;
		}else {
			 getRequest().getSession().removeAttribute("USER_DETAIL_M_SESSION_FROM_PROFILE");
			 getRequest().getSession().removeAttribute("EDIT_USER_DETAIL_DATAM");
			 getRequest().getSession().removeAttribute("ADD_USER_DETAIL_DATAM");
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
        return "action=SearchUserDetail";
    }
    
    protected void doSuccess(EventResponse arg0) {
		ORIGMasterFormHandler origMasterForm = new ORIGMasterFormHandler();
		getRequest().getSession().setAttribute("ORIGMasterForm",origMasterForm);
		
//		***Refresh Cache
		log.debug("i'm refreshing Cache.!!!" );
		com.eaf.cache.TableLookupCache.refreshCache("UsrName");
		com.eaf.cache.TableLookupCache.refreshCache("UserName");
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
