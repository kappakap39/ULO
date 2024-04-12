/*
 * Created on Oct 30, 2007
 * Created by weeraya
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

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

import javax.ejb.EJBException;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.control.event.ApplicationEvent;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.PreScoreDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author weeraya
 *
 * Type: SaveApplicationWebAction
 */
public class CMRSubmitApplicationWebAction extends WebActionHelper implements
        WebAction {
	
	Logger logger = Logger.getLogger(CMRSubmitApplicationWebAction.class);
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		if(null == userM){
			userM = new UserDetailM();
		}
 
		ORIGFormHandler formHandler=(ORIGFormHandler)getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
		ApplicationDataM applicationDataM = formHandler.getAppForm();
		
		String username = userM.getUserName();
		String cmrfirstid = applicationDataM.getCmrFirstId();
		String cmrlastid = applicationDataM.getCmrLastId();
		
		if( cmrfirstid == null || ("").equals(cmrfirstid)){
			applicationDataM.setCmrFirstId(username);
			applicationDataM.setCmrStartDate(new Date());
		}
		applicationDataM.setCmrLastId(username);
		applicationDataM.setCmrEndDate(new Date());
		
		if(ORIGUtility.isEmptyString(applicationDataM.getCreateBy())){
			applicationDataM.setCreateBy(username);
			applicationDataM.setUpdateBy(username);
		}else{
			applicationDataM.setUpdateBy(username);
		}
		// PreScoreDataM prmPreScoreDataM=applicationDataM.getPreScoreDataM();
		//Set Model  for de
		ORIGUtility utility=new ORIGUtility();
        //PersonalInfoDataM personalInfoDataM;
        //String personalType = (String) getRequest().getSession().getAttribute(
         //       "PersonalType");
        //if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
           // personalInfoDataM = (PersonalInfoDataM) getRequest()
          //          .getSession(true).getAttribute("MAIN_POPUP_DATA");
       // } else {
        //    personalInfoDataM = utility.getPersonalInfoByType(formHandler
         //           .getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
       // }
        Vector vApplicationDataM=applicationDataM.getPersonalInfoVect();
        for(int i=0;i<vApplicationDataM.size();i++){
        PersonalInfoDataM    personalInfoDataM=(PersonalInfoDataM)vApplicationDataM.get(i);
        PreScoreDataM prmPreScoreDataM=personalInfoDataM.getPreScoreDataM(); 
        if(!("Y").equals(applicationDataM.getDrawDownFlag())){
	        if(OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(personalInfoDataM.getCustomerType())||OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType())){
	        	//Occupation
	        	personalInfoDataM.setOccupation( prmPreScoreDataM.getOccupation());
	        	//position
	        	personalInfoDataM.setPosition(prmPreScoreDataM.getPosition());
	        	//Business Size
	        	personalInfoDataM.setBusinessSize(prmPreScoreDataM.getBusinessSize());
	        	//Salary
	        	personalInfoDataM.setSalary(prmPreScoreDataM.getSalary());
	        	//Other Income
	        	personalInfoDataM.setOtherIncome(prmPreScoreDataM.getOtherIncome());
	        	//Qualification
	        	personalInfoDataM.setQualification(prmPreScoreDataM.getQualification());
	        	//Total Working Year
	        	personalInfoDataM.setYearOfWork(prmPreScoreDataM.getTotalWorkYear());
	        	//Total month
	        	personalInfoDataM.setMonthOfWork(prmPreScoreDataM.getTotalWorkMonth());
	        	//Time in current address
	        	personalInfoDataM.setTimeInCurrentAddress(prmPreScoreDataM.getTimeInCurrentAddressYear()+ ( (double)prmPreScoreDataM.getTimeInCurrentAddressMonth()/100d ));
	        	//Land ownership
	        	personalInfoDataM.setLandOwnerShip(prmPreScoreDataM.getLandOwnerShip());
	        	//Current Address Type
	        	personalInfoDataM.setHouseRegisStatus(prmPreScoreDataM.getHouseRegistStatus());
	        	//Business Type
	        	personalInfoDataM.setBusinessType(prmPreScoreDataM.getBusinessType());
	        }else if (OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(personalInfoDataM.getCustomerType())){
	              //  	Occupation
	        	personalInfoDataM.setOccupation( prmPreScoreDataM.getOccupation());
	        	//Business Size
	        	prmPreScoreDataM.setBusinessSize(prmPreScoreDataM.getBusinessSize());
	        	//, Business Type
	        	prmPreScoreDataM.setBusinessType(prmPreScoreDataM.getBusinessType());
	        	//, Salary,
	        	personalInfoDataM.setSalary(prmPreScoreDataM.getSalary());
	        	// Other Income, 
	        	personalInfoDataM.setOtherIncome(prmPreScoreDataM.getOtherIncome());
	        	//Land ownership, 
	        	personalInfoDataM.setLandOwnerShip(prmPreScoreDataM.getLandOwnerShip());
	        	//cheque returned,
	        	if(prmPreScoreDataM.getChequeReturn()!=null){
	        	personalInfoDataM.setChequeReturn(prmPreScoreDataM.getChequeReturn().intValue());
	        	}
	        	// Number of employee
	        	personalInfoDataM.setNoOfEmployee(prmPreScoreDataM.getNoOfEmployee());
	        	//Current Address Type
	        	personalInfoDataM.setHouseRegisStatus(prmPreScoreDataM.getHouseRegistStatus());
//	        	Qualification
	        	personalInfoDataM.setQualification(prmPreScoreDataM.getQualification());
	        }		
        }
        }
		ApplicationEvent event = new ApplicationEvent(ApplicationEvent.CMR_SUBMIT, applicationDataM, userM);
		return event;
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
    public boolean processEventResponse(EventResponse arg0) {
		 
		return defaultProcessResponse(arg0);
	}

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
    	/*ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGMandatoryErrorUtil errorUtil = new ORIGMandatoryErrorUtil();
        ORIGUtility utility = new ORIGUtility();
        
        String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
        String personalSeq = getRequest().getParameter("personalSeq");
        int seq = 0;
        if(!ORIGUtility.isEmptyString(personalSeq)){
            seq = Integer.parseInt(personalSeq);
        }
        
        //Get Personal Info
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(applicationDataM,OrigConstant.PERSONAL_TYPE_APPLICANT);	    		  	
        
        boolean isError = errorUtil.getMandateErrorCMR(getRequest(), personalInfoDataM.getCustomerType());
        
        if(isError){
        	return false;
        }*/
        
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
    	
        return FrontController.PAGE;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
    	return "page=CMR_SUMMARY_SCREEN";
    }
    
    protected void doSuccess(EventResponse arg0) {
    	logger.debug("In doSuccess SaveApplicationWebAction");
    	ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	ApplicationDataM applicationDataM = formHandler.getAppForm();
    	if(ORIGUtility.isEmptyString(applicationDataM.getApplicationNo())){
	        ORIGApplicationManager manager = ORIGEJBService.getApplicationManager();
	        try {
				applicationDataM.setApplicationNo(manager.selectApplicationNo(applicationDataM.getAppRecordID()));
				logger.debug("Application Number = "+applicationDataM.getApplicationNo());
			} catch (EJBException e) {
				logger.error("Error : ",e);
			}
    	}
	}
    
	protected void doFail(EventResponse arg0) {
		ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        String errMsg = arg0.getMessage();
        if(ORIGUtility.isEmptyString(errMsg)){
        	errMsg = "Operation fail,please contact admin";
        }
        formHandler.getFormErrors().add(errMsg);
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
