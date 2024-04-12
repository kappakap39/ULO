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

//import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.ava.bpm.model.response.WorkflowResponse;
import com.ava.bpm.util.ResultCodeConstant;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
//import com.eaf.j2ee.system.LoadXML;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
//import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
//import com.eaf.orig.wf.service.ejb.ORIGWFServiceManager;
import com.eaf.orig.wf.shared.ORIGWFConstant;
//import com.eaf.orig.wf.shared.model.WorkListM;

/**
 * @author weeraya
 *
 * Type: LoadMainApplicationWebAction
 */
public class LoadMainApplicationWebAction extends WebActionHelper implements WebAction {
    
	Logger log = Logger.getLogger(LoadMainApplicationWebAction.class);
  
    public Event toEvent() {
         
        return null;
    }

    public boolean requiredModelRequest() {
         
        return false;
    }
    
    public boolean processEventResponse(EventResponse response) {
         
        return false;
    }
  
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
			String loanType = (String) getRequest().getParameter("loanType");//Product
			String orgType = (String) getRequest().getParameter("orgType");
			String channel = (String) getRequest().getParameter("channel");
			String appRecordID = (String) getRequest().getParameter("appRecordID");
			String appStatus = (String) getRequest().getParameter("appStatus");
			String jobState = (String) getRequest().getParameter("jobState");
			String drawDownFlag = (String)getRequest().getParameter("drawDown");
			String idNo = (String)getRequest().getParameter("idNo");
			String cancelApp=(String)getRequest().getSession().getAttribute("cancelApp");
			log.debug("appRecordID = "+appRecordID);
			log.debug("appStatus = "+appStatus);
			log.debug("jobState = "+jobState);
			log.debug("drawDownFlag = "+drawDownFlag);
			log.debug("idNo = "+idNo);
			log.debug("cancelApp="+cancelApp);
			//Set Job state
			if(OrigConstant.JobState.XUW_NEW_STATE.equals(jobState)){
			    jobState=ORIGWFConstant.JobState.UW_NEW_STATE;
			log.debug("Set  Change JobState XUW Claim App"+jobState);    			
			}
			String fromMultiMailIn = (String)getRequest().getParameter("fromMultiMailIn");
			ApplicationDataM applicationDataM = null;			
			String searchType = (String)getRequest().getSession().getAttribute("searchType");
			
			log.debug("searchType="+searchType);
			
			ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManager();
			//20080321 if uw  check autorized internal checker
			if(OrigConstant.ROLE_UW.equals(userM.getRoles().elementAt(0))){				
				String interanChk = utility.getInternalChecker(userM.getUserName());
				if(  !"Enquiry".equals(searchType)){
					if(null==interanChk||"".equals(interanChk)){
					    errMsg="User unauthorized Internal Checker!";	
					  }
				}
				
			}
			if(!ORIGUtility.isEmptyString(appRecordID) && "".equals(errMsg)){
				try{
				//  Remove Model 	WorkListM
					//WorkListM workListM = new WorkListM();
					boolean isClaimApp = false;
					WorkflowResponse workflowResponse = new WorkflowResponse(); 
					
					if(!("Reopen".equals(searchType)) && !("Enquiry".equals(searchType)&&!OrigConstant.ORIG_FLAG_Y.equals(cancelApp) ) &&!(OrigConstant.SEARCH_TYPE_REVERSE.equals(searchType)) ){
					    log.debug("Claim Application ");
					// Remove WPS   
					//	String providerUrl = (String)LoadXML.getServiceURL().get("ORIGWF");
					//	String jndi = (String)LoadXML.getServiceJNDI().get("ORIGWF");
					//	ORIGWFServiceManager OrigWFManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrl, jndi);					    
					//	workListM = OrigWFManager.claimApplication(appRecordID,appStatus,jobState,userM.getUserName());
					  /**
					   * Call Claim Application BPM Proxy
					   * */
					    
					    ORIGApplicationManager origAppManager = ORIGEJBService.getApplicationManager();
					    workflowResponse = origAppManager.claimApplication(appRecordID, appStatus, jobState , userM, "", "", "");
					    isClaimApp =true;
					}					
				
					
//					Remove From Model WorklisM change to WorkflowResponse
//					if(workListM != null){
//						errMsg = workListM.getErrorMsg();
//						log.debug("err msg form workflow ->" + errMsg);
//						if(errMsg == null || "".equals(errMsg)){
//							String aiid = workListM.getAiid();
//							log.debug("aiid form workflow ->" + aiid);
//							try{
//								//Load application by application record id
//								//if(!ORIGUtility.isEmptyString(appRecordID)){
//									String providerUrlEXT = null;
//									String jndiEXT = null;
//									
//									applicationDataM = applicationManager.loadApplicationDataM(applicationDataM, appRecordID, providerUrlEXT, jndiEXT);
//									applicationDataM.setAiid(aiid);
//									applicationDataM.setClaimDate(new Timestamp(System.currentTimeMillis()));
//								//}
//							}catch(Exception e){
//								log.error("load application exception",e);
//								errMsg = "Load application error : " + e.getMessage();
//							}
//						}else{
//							log.error("load application error -> Not found application in workflow");
//							if(OrigConstant.ORIG_FLAG_Y.equals(cancelApp)){
//							    errMsg = "Application end of Workflow" ;
//							  }else{
//							  errMsg = "Workflow Error : " + errMsg;
//							}
//						}
//					}else{
//						log.error("load application error -> worklist null");
//						errMsg = "Can not call Business Process";
//					}
					
					if(workflowResponse != null){
						errMsg = workflowResponse.getResultCode();
						if(ResultCodeConstant.RESULT_CODE_SUCCESS.equals(errMsg)||!isClaimApp){
							String jobid = workflowResponse.getJobId();
							log.debug("jobid form workflow ->" + jobid);
							try{
								//Load application by application record id
								//if(!ORIGUtility.isEmptyString(appRecordID)){
									String providerUrlEXT = null;
									String jndiEXT = null;
									
									applicationDataM = applicationManager.loadApplicationDataM(applicationDataM, appRecordID, providerUrlEXT, jndiEXT);
									
									applicationDataM.setClaimDate(new Timestamp(System.currentTimeMillis()));
									applicationDataM.setJobID(workflowResponse.getJobId());
									applicationDataM.setPtID(workflowResponse.getPtid());
									applicationDataM.setPtType(workflowResponse.getPtType());
									applicationDataM.setOwner(workflowResponse.getOwner());
								//}
							}catch(Exception e){
								log.error("load application exception",e);
								errMsg = "Load application error : " + e.getMessage();
							}
						}else{
							log.error("load application error -> Not found application in workflow");
							if(OrigConstant.ORIG_FLAG_Y.equals(cancelApp)){
							    errMsg = "Application end of Workflow" ;
							  }else{
								  if(!ORIGUtility.isEmptyString(errMsg)){
									  errMsg = "Workflow Error > " +"Code "+errMsg+" : "+workflowResponse.getResultDesc();
								  }else{
									  errMsg = "Not found application in workflow";
								  }
							}
						}
					}else{
						log.error("load application error -> WorkflowResponse null");
						errMsg = "Can not call BPM Proxy";
					}
					
					
				}catch(Exception e){
					log.error("cliam application error",e);
					errMsg = "Load application error : " + e.getMessage();
				}
			}
			log.debug("applicationDataM = "+applicationDataM);
			
			//Add check RESULT_CODE_SUCCESS from claim Application
			
			if(errMsg == null || ("").equals(errMsg)||(ResultCodeConstant.RESULT_CODE_SUCCESS.equals(errMsg))){
				if(applicationDataM == null){
					applicationDataM = new ApplicationDataM();
					
					//Get Application Record ID
					//String prmApplicationRecordID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.APPLICATION_ID); 
					ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
					String prmApplicationRecordID = generatorManager.generateUniqueIDByName(EJBConstant.APPLICATION_ID);
					applicationDataM.setAppRecordID(prmApplicationRecordID);
					
//					/** Load Image Category Test appRecordID : 2576 */
					
					log.debug("[Load Image Start] .. ");
					
//					applicationDataM.setImageCategoryVect(ORIGDAOFactory.getApplicationImageMDAO().LoadModelImageCategoryDataM("4597"));
					
					log.debug("[Load Image End] .. ");
					
					/** Load Image Category End*/
					
					//set temp jobstate for user create new application
					if(OrigConstant.ROLE_CMR.equals(userM.getRoles().get(0))){ // CMR
						applicationDataM.setJobState(ORIGWFConstant.JobState.CMR_DRAFT_STATE);
					}else if(OrigConstant.ROLE_DE.equals(userM.getRoles().get(0))){ // DE
						applicationDataM.setJobState(ORIGWFConstant.JobState.DE_DRAFT_STATE);
					}
					
					applicationDataM.setLoanType(loanType);
				}
				
				log.debug("personal Size = "+applicationDataM.getPersonalInfoVect().size());
				
				PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
				log.debug("personalInfoDataM = "+personalInfoDataM);
				if(personalInfoDataM == null){

					if(drawDownFlag!=null&&drawDownFlag.equals("Y")){
					    	//case draw down auto populate personal
					      	personalInfoDataM = (PersonalInfoDataM)getRequest().getSession().getAttribute("CUSTOMER_DRAW_DOWN");
					      	
					      	//getRequest().getSession().removeAttribute("CUSTOMER_DRAW_DOWN");
					      	appRecordID = applicationManager.getAppRecordForCreateDrawDown(idNo);
					      	log.debug("appRecordID = "+appRecordID);
					      	applicationDataM = applicationManager.loadApplicationDataMForDrawDown(applicationDataM, appRecordID);
					      	applicationDataM.setDrawDownFlag("Y");
					      	//Set Job State
							if(OrigConstant.ROLE_CMR.equals(userM.getRoles().get(0))){ // CMR
								applicationDataM.setJobState(ORIGWFConstant.JobState.CMR_DRAFT_STATE);
							}else if(OrigConstant.ROLE_DE.equals(userM.getRoles().get(0))){ // DE
								applicationDataM.setJobState(ORIGWFConstant.JobState.DE_DRAFT_STATE);
							}
							//Set appRecord ID
//							String prmApplicationRecordID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.APPLICATION_ID); 
							
							String prmApplicationRecordID =  PLORIGEJBService.getORIGDAOUtilLocal().getUniqByName(EJBConstant.APPLICATION_ID);
							
							applicationDataM.setAppRecordID(prmApplicationRecordID);
							
							personalInfoDataM.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
							
					}else{
						    personalInfoDataM = new PersonalInfoDataM();
						    personalInfoDataM.setCustomerType(customerType);
						    personalInfoDataM.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
						    personalInfoDataM.setCmpCode(utility.getGeneralParamByCode(OrigConstant.ORIG_CMPCDE));
					}
					
					String fromMultiApp = (String)getRequest().getSession().getAttribute("fromMultiApp");
					log.debug("fromMultiApp==>"+fromMultiApp);
					PersonalInfoDataM tempPersonalInfoDataM = (PersonalInfoDataM)getRequest().getSession().getAttribute("personalInfoSession");
					log.debug("tempPersonalInfoDataM===="+tempPersonalInfoDataM);
					if ("Y".equalsIgnoreCase(fromMultiApp) && tempPersonalInfoDataM != null) {					
					    log.debug("tempPersonalInfoDataM.getThaiTitleName()====>"+tempPersonalInfoDataM.getThaiTitleName());
					    applicationDataM.getPersonalInfoVect().add(tempPersonalInfoDataM);
					    personalInfoDataM = tempPersonalInfoDataM;
					} else {
					    applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
					} 
					
				}
				
				LoanDataM loanDataM = new LoanDataM();
				if(OrigConstant.ORG_AUTO_LOAN.equals(orgType)){
				    VehicleDataM vehicleDataM = (VehicleDataM) SerializeUtil.clone(applicationDataM.getVehicleDataM());
					Vector loanDataMVect = applicationDataM.getLoanVect();
					if(loanDataMVect != null && loanDataMVect.size() > 0){
						loanDataM = (LoanDataM) loanDataMVect.elementAt(0);
					}
					if(vehicleDataM != null){
						vehicleDataM.setLoanDataM(loanDataM);
					}
					applicationDataM.setVehicleDataMTmp(vehicleDataM);
				}
				
				
				//Set null date
				applicationDataM.setNullDate(utility.getNulldate(userM.getDefaultOfficeCode()));
				applicationDataM.setSysNulldate(utility.getSysNulldate(userM.getDefaultOfficeCode()));
				
				if(ORIGUtility.isEmptyString(applicationDataM.getCreateBy())){
					applicationDataM.setCreateBy(userM.getUserName());
				}else{
					applicationDataM.setUpdateBy(userM.getUserName());
				}
				ORIGForm.setAppForm(applicationDataM);
				
				personalInfoDataM.setAddressIndex((utility.getMaxAddressSeq(personalInfoDataM.getAddressTmpVect()))+1);
				personalInfoDataM.setFinanceIndex((utility.getMaxFinanceSeq(personalInfoDataM.getFinanceVect()))+1);
				personalInfoDataM.setChangeNameIndex((utility.getMaxChangeNameSeq(personalInfoDataM.getChangeNameVect()))+1);
				ORIGForm.getAppForm().setMainCustomerType(personalInfoDataM.getCustomerType());
				
				/*********************** Loan Form-SubForm********************/
				Vector userRoles = userM.getRoles();
//				String formID = getRequest().getParameter("formID");
				String currentTab = getRequest().getParameter("currentTab");
				String busClassID = ORIGFormUtil.getBusinessID(loanType,channel,orgType);
				if(applicationDataM.getBusinessClassId()!=null && !"".equals(applicationDataM.getBusinessClassId())){
				    busClassID = applicationDataM.getBusinessClassId();
				}else{
				    applicationDataM.setBusinessClassId(busClassID);
				}
				String formID = ORIGFormUtil.getFormIDByBus(busClassID);
				log.debug(">>> Loading formID="+formID);
				if(formID==null || formID.equals("")){
				    formID="DE_FORM";//For Applicatin from Image
				    log.debug(">>> Loading formID Case Application from Image="+formID);
				}
				//****************************************
				ORIGForm.getSubForms().clear();
				ORIGForm.setIsLoadedSubForms(false);
				//****************************************
				ORIGForm.loadSubFormsForDrawDown(userRoles, formID, drawDownFlag);
				ORIGForm.setCurrentTab(currentTab);
				ORIGForm.setFormID(formID);
				//*****************************************	
				loadWorkFlag = true;
				log.debug("userM.getRoles().elementAt(0) = "+userM.getRoles().elementAt(0));
				if(!OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
				if(OrigConstant.ROLE_UW.equals(userM.getRoles().elementAt(0))){
					Vector loanVect = ORIGForm.getAppForm().getLoanVect();
			    	loanDataM = new LoanDataM();
			    	log.debug("loanVect = "+loanVect.size());
			    	if(loanVect != null && loanVect.size() > 0){
			    		loanDataM = (LoanDataM) loanVect.elementAt(0);
//			    		if(loanDataM.getInternalCkecker() != null && !("").equals(loanDataM.getInternalCkecker())){
//			    			loanDataM.setInternalCheckTmp(loanDataM.getInternalCkecker());
//			    		}
				        
				        //String creditApproval = utility.getCreditApproval(userM.getUserName());
//				        log.debug("interanChk = "+interanChk);
				        
				        if(ORIGUtility.isEmptyString(loanDataM.getInternalCkecker())){
				            String interanChk = utility.getInternalChecker(userM.getUserName());
				        	loanDataM.setInternalCkecker(interanChk);
				        }
				        //loanDataM.setInternalCkecker(interanChk);
				    	//loanDataM.setCreditApproval(creditApproval);
			    	}
				}
				}
				//Load Policy
			    //=========================================
	            String policyVersion = applicationDataM.getPolicyVersion();
	            if (policyVersion == null || "".equals(policyVersion)) {
	                Timestamp timeCreateDate=applicationDataM.getCreateDate();
	                if(timeCreateDate==null){
	                    timeCreateDate=new Timestamp(System.currentTimeMillis());
	                }
	                OrigPolicyVersionDataM origPoicy = utility.getPolicyVersion( new Date( timeCreateDate.getTime()) );
	                if (origPoicy == null) {
	                    log.fatal("NO Policy Version Match");	                   
	                } else {
	                    applicationDataM.setPolicyVersion(origPoicy.getPolicyVersion());	
	                    policyVersion=origPoicy.getPolicyVersion();
	                }
	            }
	            log.debug("Policy Version "+policyVersion);
				//==========================================
				
				getRequest().getSession(true).setAttribute("ORIGForm",ORIGForm);
				getRequest().getSession(true).setAttribute("PersonalType",OrigConstant.PERSONAL_TYPE_APPLICANT);
				//Clone application
				ApplicationDataM applicationDataMClone=(ApplicationDataM)SerializeUtil.clone(ORIGForm.getAppForm());
				getRequest().getSession(true).setAttribute("applicationClone",applicationDataMClone);
			}
			
			//Add for (ResultCodeConstant.RESULT_CODE_SUCCESS.equals(errMsg))
			if(ResultCodeConstant.RESULT_CODE_SUCCESS.equals(errMsg)){
				errMsg ="";
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
