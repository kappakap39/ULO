/*
 * Created on Nov 2, 2007
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
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.InsuranceDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author admin
 *
 * Type: LoadCarDetailWebAction
 */
public class LoadCarDetailWebAction extends WebActionHelper implements
        WebAction {
    
    Logger logger  = Logger.getLogger(LoadCarDetailWebAction.class);

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
        logger.debug("+++++LoadCarDetailWebAction+++++");
		UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String loanType = getRequest().getParameter("loanType");
		logger.debug("loanType is:"+loanType);
		
		try{
			if(null==userM)	userM=new UserDetailM();		
						
			ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
			ORIGFormHandler popupForm = (ORIGFormHandler) ORIGForm.getPopupForm();
			if(popupForm == null){
			    popupForm = new ORIGFormHandler();
			    ORIGForm.setPopupForm(popupForm);
			}
			Vector userRoles = userM.getRoles();
			String formID = "CAR_DETAIL_FORM";
			String currentTab = "MAIN_TAB";
			//****************************************
			popupForm.getSubForms().clear();
			popupForm.setIsLoadedSubForms(false);
			//****************************************
			popupForm.loadSubForms(userRoles, formID);
			popupForm.setCurrentTab(currentTab);
			popupForm.setFormID(formID);
			//*****************************************
			
			//****************************************
			ORIGForm.getSubForms().clear();
			ORIGForm.setIsLoadedSubForms(false);
			//****************************************
			ORIGForm.loadSubForms(userRoles, formID);
			ORIGForm.setCurrentTab(currentTab);
			ORIGForm.setFormID(formID);
			//*****************************************
			
			//Get Personal
			ORIGUtility utility = new ORIGUtility();
			ApplicationDataM appM = ORIGForm.getAppForm();
			if(appM == null){
			    appM = new ApplicationDataM();
			}
			
			String seqStr = (String) getRequest().getParameter("seq");
	        int seq = 0;
	        if(seqStr != null && !("").equals(seqStr)){
	            seq = Integer.parseInt(seqStr);
	        }
	        
			if(seq == 0){
			    VehicleDataM vehicleM = new VehicleDataM();
			    vehicleM.setInsuranceDataM(new InsuranceDataM());
			    appM.setVehicleDataM(vehicleM);
			    appM.setLoanType(loanType);
			    appM.setLoanVect(new Vector());
			}else{
			    Vector carDetailVt = (Vector)getRequest().getSession().getAttribute("VEHICLE_RESULT");
			    VehicleDataM vehicleM = null;
			    Vector loanVt = new Vector();
			    if(carDetailVt==null){
			        carDetailVt = new Vector();
			        }
			    for(int i=0;i<carDetailVt.size();i++){
			        vehicleM = (VehicleDataM)carDetailVt.get(i);
			        if(seq==vehicleM.getVehicleID()){
			            appM.setVehicleDataM(vehicleM);
			            loanVt.add(vehicleM.getLoanDataM());
			            appM.setLoanVect(loanVt);
			            }
			        }
			}
			
			ApplicationDataM applicationDataM = ORIGForm.getAppForm();
			if(applicationDataM == null){
				applicationDataM = new ApplicationDataM();
			}
			applicationDataM.setJobState(ORIGWFConstant.JobState.UW_NEW_STATE);
				
			//Set null date
			applicationDataM.setNullDate(utility.getNulldate(userM.getDefaultOfficeCode()));
			applicationDataM.setSysNulldate(utility.getSysNulldate(userM.getDefaultOfficeCode()));
			
			Vector loanVect = ORIGForm.getAppForm().getLoanVect();
	    	LoanDataM loanDataM = new LoanDataM();
	    	if(loanVect!=null && loanVect.size() > 0){
	    		loanDataM = (LoanDataM) loanVect.elementAt(0);
	    	}
	    	if(loanDataM != null){
	    		loanDataM.setCostOfFinanceTmp(loanDataM.getCostOfFinancialAmt());
	    	}
	        return true;
		}catch (Exception e) {         
	        logger.error("exception ",e);
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
