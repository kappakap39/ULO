/*
 * Created on Dec 5, 2007
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

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.UserBranchM;
import com.eaf.orig.master.shared.model.UserExceptActM;
import com.eaf.orig.master.shared.model.UserNameM;
import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterUserDetailDAO;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * Type: ShowProfileWebAction
 */
public class ShowProfileWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(ShowProfileWebAction.class);
	UserDetailM userDetailM =new UserDetailM(); 
	
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
		//*** Claer ShwAddFrm in ORIGMasterFormHandler 
		ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
		origMasterForm.setShwAddFrm("");
		//***
		
		String showProfile = getRequest().getParameter("showProfile");
		String AddOrEdit = getRequest().getParameter("AddOrEdit");
		log.debug("AddOrEdit ="+AddOrEdit);
		
		
		if("show".equalsIgnoreCase(showProfile)){
			log.debug("!!! NOW i'm showing Profile");
			getRequest().getSession().setAttribute("AddOrEdit_SESSION", AddOrEdit);
			
			// *** for show userDetail changed
			
			String userName = getRequest().getParameter("userName");
			 String firstName = getRequest().getParameter("firstName");
			 String lastName = getRequest().getParameter("lastName");
			 String description = getRequest().getParameter("description");
			 String region = getRequest().getParameter("region");
			 String telephone = getRequest().getParameter("telephone");
			 String mobilePhone = getRequest().getParameter("mobilePhone");
			 String email = getRequest().getParameter("email");
			 String jobDescription = getRequest().getParameter("jobDescription");
			 String department = getRequest().getParameter("department");
			 String zoneID = getRequest().getParameter("zone");
			 String status = getRequest().getParameter("active");
			 String position = getRequest().getParameter("position");
			 String skipIP = getRequest().getParameter("skipIP");
			 String communicationChannel = getRequest().getParameter("communicationChannel");
			 String officeCode = getRequest().getParameter("officeCode");
			 
			 log.debug("userName ="+userName);
			 log.debug("firstName ="+firstName);
			 log.debug("lastName ="+lastName);
			 log.debug("description ="+description);
			 log.debug("position ="+position);
			 log.debug("communicationChannel ="+communicationChannel);
			 log.debug("officeCode ="+officeCode);
			 log.debug("skipIP ="+skipIP);
			 log.debug("status ="+status);
			 		 
			 userDetailM = new UserDetailM(); 
			 
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
			 userDetailM.setStatus(status);
			 userDetailM.setSkipIP(skipIP);
			 userDetailM.setCommunicate_channel(communicationChannel);
			 userDetailM.setDefaultOfficeCode(officeCode);
			
			getRequest().getSession().setAttribute("EDIT_USER_DETAIL_DATAM", userDetailM);
			getRequest().getSession().setAttribute("ADD_USER_DETAIL_DATAM", userDetailM);
		
			// *** END Show
		
		// **** Show Selected ****
		
		userDetailM = (UserDetailM)getRequest().getSession().getAttribute("USER_DETAIL_M_SESSION_FROM_PROFILE");
		if(userDetailM!=null){
			log.debug("Now!!! i'm showing save select ");
			log.debug(" userDetailM.getUserBranchVect() = "+ SerializeUtil.clone(userDetailM.getUserBranchVect()));
			log.debug(" userDetailM.getUserProfileVect() = "+ SerializeUtil.clone(userDetailM.getUserProfileVect()));
			log.debug(" userDetailM.getUserExceptionVect() = "+ SerializeUtil.clone(userDetailM.getUserExceptionVect()));
			
			getRequest().getSession().setAttribute("SELECTED_BRANCH", SerializeUtil.clone(userDetailM.getUserBranchVect()));
			getRequest().getSession().setAttribute("SELECTED_USER_NAME", SerializeUtil.clone(userDetailM.getUserProfileVect()));
			getRequest().getSession().setAttribute("SELECTED_EXCEPT_ACT", SerializeUtil.clone(userDetailM.getUserExceptionVect()));
			
		}else if(userDetailM==null && "add".equalsIgnoreCase(AddOrEdit)){
			
			getRequest().getSession().removeAttribute("SELECTED_BRANCH");
			getRequest().getSession().removeAttribute("SELECTED_USER_NAME");
			getRequest().getSession().removeAttribute("SELECTED_EXCEPT_ACT");
			getRequest().getSession().removeAttribute("SEARCH_BRANCH");
			getRequest().getSession().removeAttribute("SEARCH_USER_NAME");
			getRequest().getSession().removeAttribute("SEARCH_EXCEPT");
			getRequest().getSession().removeAttribute("BRANCH_DESC_SEARCH");
			getRequest().getSession().removeAttribute("USER_NAME_DESC_SEARCH");
			getRequest().getSession().removeAttribute("EXCEPT_ACT_DESC_SEARCH");
		//	getRequest().getSession().removeAttribute("AddOrEdit_SESSION");
		}else if(userDetailM==null && "edit".equalsIgnoreCase(AddOrEdit)){
			
		}
			
		// **** END Show Selected ****
		
		// *** Search all branch  *****
		
//		OrigMasterUserDetailDAO userDetailDAO = (OrigMasterUserDetailDAO)OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
		Vector branchSearchVect = new Vector();
		
		Vector selectedBranchVect = (Vector) getRequest().getSession().getAttribute("SELECTED_BRANCH");
		log.debug("selectedBranchVect = "+selectedBranchVect);
		
			try {
//				branchSearchVect = userDetailDAO.SearchAllBranch();
				branchSearchVect = PLORIGEJBService.getORIGDAOUtilLocal().SearchAllBranch();
				log.debug("branchSearchVect = "+branchSearchVect);
				log.debug("branchSearchVect.size() = "+branchSearchVect.size());
			} catch (Exception e) {
				log.fatal("",e);
			}
			
			// *** remove selbranch from searchbranch ****
			if((selectedBranchVect!=null) && (branchSearchVect!=null)){
				log.debug("now im checking selectedBranchVect && branchSearchVect at if1");
				if((selectedBranchVect.size()>0) && (branchSearchVect.size()>0)){
					log.debug("now im checking selectedBranchVect && branchSearchVect at if2");
					UserBranchM selUserBranchM = null;
					for(int i = 0; i < selectedBranchVect.size(); i++){
						selUserBranchM = (UserBranchM) selectedBranchVect.get(i);
						for(int j = 0; j < branchSearchVect.size(); j++){
							UserBranchM userBranchM = (UserBranchM) branchSearchVect.get(j);
							if( (userBranchM.getArea()).equalsIgnoreCase(selUserBranchM.getArea())){
								branchSearchVect.removeElementAt(j);
							}
						}
					}
				}
			}
		getRequest().getSession().setAttribute("SEARCH_BRANCH",branchSearchVect);
		
		// *** Search all approvAuthor *****
		
//		userDetailDAO = (OrigMasterUserDetailDAO)OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
		Vector usernameSearchVect = new Vector();
		
		Vector selectedUserNameVect = (Vector) getRequest().getSession().getAttribute("SELECTED_USER_NAME");
		log.debug("selectedUserNameVect = "+selectedUserNameVect);
		
			try {
//				usernameSearchVect = userDetailDAO.SearchAllGroupname();
				usernameSearchVect = PLORIGEJBService.getORIGDAOUtilLocal().SearchAllGroupname();
				log.debug("usernameSearchVect = "+usernameSearchVect);
				log.debug("usernameSearchVect.size() = "+usernameSearchVect.size());
			} catch (Exception e) {
				log.fatal("",e);
			}
			
			// *** remove selUserName from searchUserName ****
			if((selectedUserNameVect!=null) && (usernameSearchVect!=null)){
				log.debug("now im checking selectedUserNameVect && usernameSearchVect at if1");
				if((selectedUserNameVect.size()>0) && (usernameSearchVect.size()>0)){
					log.debug("now im checking selectedUserNameVect && usernameSearchVect at if2");
					UserNameM selUserNameM = null;
					for(int i = 0; i < selectedUserNameVect.size(); i++){
						selUserNameM = (UserNameM) selectedUserNameVect.get(i);
						for(int j = 0; j < usernameSearchVect.size(); j++){
							UserNameM userNameM = (UserNameM) usernameSearchVect.get(j);
							if( (userNameM.getGroupName()).equalsIgnoreCase(selUserNameM.getGroupName()) ){
								usernameSearchVect.removeElementAt(j);
							}
						}
					}
				}
			}
		getRequest().getSession().setAttribute("SEARCH_USER_NAME",usernameSearchVect);
		
		// *** Search all except *****
		
//		userDetailDAO = (OrigMasterUserDetailDAO)OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
		Vector exceptSearchVect = new Vector();
		
		Vector selectedExceptVect = (Vector) getRequest().getSession().getAttribute("SELECTED_EXCEPT_ACT");
		log.debug("selectedExceptVect = "+selectedExceptVect);
		
			try {
//				exceptSearchVect = userDetailDAO.SearchAllExcept();
				exceptSearchVect = PLORIGEJBService.getORIGDAOUtilLocal().SearchAllExcept();
				log.debug("exceptSearchVect = "+exceptSearchVect);
				log.debug("exceptSearchVect.size() = "+exceptSearchVect.size());
			} catch (Exception e) {
				log.fatal("",e);
			}
			
			// *** remove selbranch from searchbranch ****
			if((selectedExceptVect!=null) && (exceptSearchVect!=null)){
				log.debug("now im checking selectedExceptVect && exceptSearchVect at if1");
				if((selectedExceptVect.size()>0) && (exceptSearchVect.size()>0)){
					log.debug("now im checking selectedExceptVect && exceptSearchVect at if2");
					UserExceptActM selExceptActM = null;
					for(int i = 0; i < selectedExceptVect.size(); i++){
						selExceptActM = (UserExceptActM) selectedExceptVect.get(i);
						for(int j = 0; j < exceptSearchVect.size(); j++){
							UserExceptActM exceptActM = (UserExceptActM) exceptSearchVect.get(j);
							if( (exceptActM.getExceptID()).equalsIgnoreCase(selExceptActM.getExceptID())){
								exceptSearchVect.removeElementAt(j);
							}
						}
					}
				}
			}
		getRequest().getSession().setAttribute("SEARCH_EXCEPT",exceptSearchVect);
		
		}
		
//		*** remove Attribute when click cancelBtn
		AddOrEdit = (String)getRequest().getSession().getAttribute("AddOrEdit_SESSION");
		
		if("cancelFromProf".equalsIgnoreCase(showProfile) && "add".equalsIgnoreCase(AddOrEdit)){
			origMasterForm.setShwAddFrm(AddOrEdit);
			
			log.debug("!!! NOW "+AddOrEdit + " !!! in if");
			getRequest().getSession().removeAttribute("SELECTED_BRANCH");
			getRequest().getSession().removeAttribute("SELECTED_USER_NAME");
			getRequest().getSession().removeAttribute("SELECTED_EXCEPT_ACT");
			getRequest().getSession().removeAttribute("SEARCH_BRANCH");
			getRequest().getSession().removeAttribute("SEARCH_USER_NAME");
			getRequest().getSession().removeAttribute("SEARCH_EXCEPT");
			getRequest().getSession().removeAttribute("BRANCH_DESC_SEARCH");
			getRequest().getSession().removeAttribute("USER_NAME_DESC_SEARCH");
			getRequest().getSession().removeAttribute("EXCEPT_ACT_DESC_SEARCH");
			getRequest().getSession().removeAttribute("AddOrEdit_SESSION");
			
			return false;
		}else if("cancelFromProf".equalsIgnoreCase(showProfile) && "edit".equalsIgnoreCase(AddOrEdit)){
			origMasterForm.setShwAddFrm(AddOrEdit);
			log.debug("!!! NOW "+AddOrEdit + " !!! in else");
			return false;
		}
		// *** END remove
		
		return true;
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
