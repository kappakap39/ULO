/*
 * Created on Sep 19, 2007
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

import java.sql.Timestamp;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.img.wf.service.ejb.IMGWFServiceManager;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.j2ee.system.LoadXML;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.BankDataM;
import com.eaf.orig.shared.model.ChangeNameDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;
import com.eaf.orig.wf.shared.model.WorkListM;

/**
 * @author weeraya
 *
 * Type: LoadMainApplicationWebAction
 */
public class LoadImageApplicationWebAction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(LoadImageApplicationWebAction.class);
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
        log.debug("+++++LoadApplicationFormWebAction+++++");
        boolean loadWorkFlag = false;
		String errMsg = "";
		try{
			UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			log.debug("[userM.getRoles()] = " + userM.getRoles());
			if(null==userM)	userM=new UserDetailM();		
							
			ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
			if(ORIGForm == null){
					ORIGForm = new ORIGFormHandler();
			}
			ORIGUtility utility = new ORIGUtility();
			//*****************************************
			String customerType = (String) getRequest().getParameter("customerType");
			String loanType = (String) getRequest().getParameter("loanType");
			String requestID = (String) getRequest().getParameter("requestID");
			String firstName = (String) getRequest().getParameter("firstName");
			String lastName = (String) getRequest().getParameter("lastName");
			String citizenID = (String) getRequest().getParameter("citizenID");
			String attachID = (String) getRequest().getParameter("attachID");
			
			ApplicationDataM applicationDataM = null;
			String searchType = (String)getRequest().getSession().getAttribute("searchType");
			
			try{
				WorkListM workListM = new WorkListM();
				String providerUrl = (String)LoadXML.getServiceURL().get("IMGWF");
				String jndi = (String)LoadXML.getServiceJNDI().get("IMGWF");
				
				IMGWFServiceManager IMGManager = ORIGEJBService.getImageWFServiceManagerHome(providerUrl, jndi);
				workListM = IMGManager.claimApplication(requestID,"","",userM.getUserName());
				
				if(workListM != null){
					errMsg = workListM.getErrorMsg();
					log.debug("err msg form workflow ->" + errMsg);
					if(errMsg == null || "".equals(errMsg)){
						String aiid = workListM.getAiid();
						log.debug("aiid form workflow ->" + aiid);
						try{
							applicationDataM = new ApplicationDataM();
							
							applicationDataM.setAiid(aiid);
							applicationDataM.setRequestID(requestID);
							applicationDataM.setLoanType(loanType);
							applicationDataM.setAttachId(attachID);
							
							//Get Application Record ID
							//String prmApplicationRecordID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.APPLICATION_ID); 
							ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
							String prmApplicationRecordID = generatorManager.generateUniqueIDByName(EJBConstant.APPLICATION_ID);
							applicationDataM.setAppRecordID(prmApplicationRecordID);
							
							//set temp jobstate for user create new application
							if(OrigConstant.ROLE_CMR.equals(userM.getRoles().get(0))){ // CMR
								applicationDataM.setJobState(ORIGWFConstant.JobState.CMR_DRAFT_STATE);
							}else if(OrigConstant.ROLE_DE.equals(userM.getRoles().get(0))){ // DE
								applicationDataM.setJobState(ORIGWFConstant.JobState.DE_DRAFT_STATE);
							}
							applicationDataM.setClaimDate(new Timestamp(System.currentTimeMillis()));
							
							ORIGForm.setAppForm(applicationDataM);
							
							//Load Personal
							String providerUrlEXT = null;
							String jndiEXT = null;
//					        ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
					        
					        PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
						    personalInfoDataM.setIdNo(citizenID);
						    personalInfoDataM.setCmpCode(utility.getGeneralParamByCode(OrigConstant.ORIG_CMPCDE));
						    personalInfoDataM.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
						    
//					        personalInfoDataM = applicationEXTManager.loadModelPersonal(personalInfoDataM);
					        
						    ORIGForm.getAppForm().getPersonalInfoVect().add(personalInfoDataM);
						    
						    personalInfoDataM.setCustomerType(customerType);
						    personalInfoDataM.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
						    personalInfoDataM.setThaiFirstName(firstName);
						    personalInfoDataM.setThaiLastName(lastName);
						    //====================Set Change Name===============
						    Vector changeNameVect = personalInfoDataM.getChangeNameVect();
					        ChangeNameDataM changeNameDataM;
					        for(int i=0; i<changeNameVect.size(); i++){
					        	changeNameDataM = (ChangeNameDataM) changeNameVect.get(i);
					        	changeNameDataM.setSeq(i+1);
					        	if(("").equals(changeNameDataM.getOrigOwner()) || !("Y").equals(changeNameDataM.getOrigOwner())){
					        		changeNameDataM.setOrigOwner("N");
					        	}
					        	changeNameDataM.setCreateBy(userM.getUserName());
					        	//changeNameDataM.setUpdateBy(userM.getUserName());
					        }
					        //==============================================
					        Vector addressVect = personalInfoDataM.getAddressTmpVect();
					        AddressDataM addressDataM;
					        for(int i=0; i<addressVect.size(); i++){
					        	addressDataM = (AddressDataM) addressVect.get(i);
					        	if(("").equals(addressDataM.getOrigOwner()) || !("Y").equals(addressDataM.getOrigOwner())){
					        		addressDataM.setOrigOwner("N");
					        	}
					        	if(OrigConstant.ADDRESS_TYPE_IC.equals(addressDataM.getAddressType())){
					        		String houseId = addressDataM.getHouseID();
					        	}
					        	addressDataM.setCreateBy(userM.getUserName());
					        	//addressDataM.setUpdateBy(userM.getUserName());
					        }
					        //=======================================
					        Vector financeVect = personalInfoDataM.getFinanceVect();
					        BankDataM bankDataM;
					        for(int i=0; i<financeVect.size(); i++){
					        	bankDataM = (BankDataM) financeVect.get(i);
					        	if(("").equals(bankDataM.getOrigOwner()) || !("Y").equals(bankDataM.getOrigOwner())){
					        		bankDataM.setOrigOwner("N");
					        	}
					        	bankDataM.setCreateBy(userM.getUserName());
					        	//bankDataM.setUpdateBy(userM.getUserName());
					        }
					        //==============================================
							personalInfoDataM.setAddressIndex((utility.getMaxAddressSeq(personalInfoDataM.getAddressVect()))+1);
							personalInfoDataM.setFinanceIndex((utility.getMaxFinanceSeq(personalInfoDataM.getFinanceVect()))+1);
							personalInfoDataM.setChangeNameIndex((utility.getMaxChangeNameSeq(personalInfoDataM.getChangeNameVect()))+1);
							ORIGForm.getAppForm().setMainCustomerType(personalInfoDataM.getCustomerType());
							
							/*********************** Loan Form-SubForm********************/
							Vector userRoles = userM.getRoles();
							String formID = getRequest().getParameter("formID");
							String currentTab = getRequest().getParameter("currentTab");
							//****************************************
							ORIGForm.getSubForms().clear();
							ORIGForm.setIsLoadedSubForms(false);
							//****************************************
							ORIGForm.loadSubForms(userRoles, formID);
							ORIGForm.setCurrentTab(currentTab);
							ORIGForm.setFormID(formID);
							//*****************************************
//							Set null date
							applicationDataM.setNullDate(utility.getNulldate(userM.getDefaultOfficeCode()));
							applicationDataM.setSysNulldate(utility.getSysNulldate(userM.getDefaultOfficeCode()));
							loadWorkFlag = true;
							getRequest().getSession(true).setAttribute("ORIGForm",ORIGForm);
							getRequest().getSession(true).setAttribute("PersonalType",OrigConstant.PERSONAL_TYPE_APPLICANT);
							
						}catch(Exception e){
							log.error("load application exception",e);
							errMsg = "Load application error : " + e.getMessage();
						}
					}else{
						log.error("load application error -> Not found application in workflow");
						errMsg = "Workflow Error : " + errMsg;
					}
				}else{
					log.error("load application error -> worklist null");
					errMsg = "Can not call Business Process";
				}
			}catch(Exception e){
				log.error("cliam application error",e);
				errMsg = "Load application error : " + e.getMessage();
			}
			
			
			if(errMsg == null || ("").equals(errMsg)){
				
			}
			getRequest().getSession(true).setAttribute(OrigConstant.ERR_MESSAGE_SESSION,errMsg);
			
        }catch(Exception e){
        	log.debug("Error in LoadMainApplicationWebAction.preModelRequest() >> ", e);
        }
        return loadWorkFlag;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
         
        return FrontController.ACTION;
    }
    
    public String getNextActionParameter() {		
		return  "action=loadMenuAppForm";
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
