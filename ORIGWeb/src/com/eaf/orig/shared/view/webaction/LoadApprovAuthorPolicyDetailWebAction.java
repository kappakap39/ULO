/*
 * Created on Sep 29, 2008
 * Created by Sankom
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

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.cache.CacheDataInf;
import com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler;
import com.eaf.orig.master.shared.model.ApprovAuthorM;
import com.eaf.orig.master.shared.model.OrigPolicyVersionDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.xrules.shared.constant.XRulesConstant;

/**
 * @author Sankom
 * 
 * Type: LoadApprovAuthorPolicyDetailWebAction
 */
public class LoadApprovAuthorPolicyDetailWebAction extends WebActionHelper implements WebAction {
    Logger log = Logger.getLogger(LoadApprovAuthorPolicyDetailWebAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        String apprvAction = getRequest().getParameter("apprvAction");
        String groupName = getRequest().getParameter("groupName");
        String loanType = getRequest().getParameter("loanType");
        String customerType = getRequest().getParameter("customerType");
         
        if (apprvAction == null) {
            apprvAction = "";
        }
        if (groupName == null) {
            groupName = "";
        }
        if(customerType==null){
        customerType="";    
        }
        if(loanType==null){loanType="";}
        groupName=ORIGDisplayFormatUtil.trim(groupName);
        loanType=ORIGDisplayFormatUtil.trim(loanType);
        customerType=ORIGDisplayFormatUtil.trim(customerType);
        //Get Group Name
        Vector vPolicyVersionDetail = (Vector) getRequest().getSession().getAttribute("POLICY_VERSION_APPRV_DETAIL");
        //Load Group Name
        log.debug("apprvAction=" + apprvAction);
        log.debug("groupName=" + groupName);
        Vector vApprovGroup = null;
        //HashMap
        // hGroupName=(HashMap)getRequest().getSession().getAttribute("HMAP_APPPRV_GROUP");
        if ("load".equals(apprvAction)) {
            //Vector
            // vGroupName=(Vector)getRequest().getSession().getAttribute("APPRV_GROUP_NAME");
            // vApprovGroup=(Vector)hGroupName.get(groupName);
            getRequest().getSession().setAttribute("SELECT_APPRV_GROUP", this.getApprvGroup(vPolicyVersionDetail, groupName, loanType, customerType));
        } else if ("new".equals(apprvAction)) {
            String createGroup = getRequest().getParameter("createGroup");
            Vector vGroupName = this.getGroupName(vPolicyVersionDetail);
            if(vGroupName.contains(createGroup)){
                ORIGMasterFormHandler origMasterForm = (ORIGMasterFormHandler) getRequest().getSession().getAttribute("ORIGMasterForm");
                String errMsg="Duplicate Group Name";
                log.debug("Save Fail Error " +errMsg);
                origMasterForm.getFormErrors().add(errMsg);		
                return false;
            }
            vGroupName.add(createGroup);
            Vector createApprovGroup = this.createApprovGroup(createGroup, vPolicyVersionDetail);
            getRequest().getSession().setAttribute("SELECT_APPRV_GROUP", createApprovGroup);          
            getRequest().getSession().setAttribute("APPRV_GROUP_NAME", vGroupName);
        } else if ("edit".equals(apprvAction)) {
            String itemIndex = getRequest().getParameter("selectItem");
            if (itemIndex != null) {
                ORIGUtility utility = ORIGUtility.getInstance();
                int index = utility.stringToInt(itemIndex);
                Vector selectApprvGroup = (Vector) getRequest().getSession().getAttribute("SELECT_APPRV_GROUP");
                ApprovAuthorM selectApprv = (ApprovAuthorM) selectApprvGroup.get(index);
                if (selectApprv != null) {
                    getRequest().getSession().setAttribute("SELECT_APPRV_DETAIL", selectApprv);
                    getRequest().getSession().setAttribute("SELECT_ITEM", itemIndex);
                }
            }
        } else if ("delete".equals(apprvAction)) {
            if (groupName != null && !"".equals(groupName)) {
                log.debug("Delete Group Name "+groupName);
                deleteGroup(vPolicyVersionDetail, groupName);
            }
            Vector vGroupName = this.getGroupName(vPolicyVersionDetail);
            getRequest().getSession().setAttribute("APPRV_GROUP_NAME", vGroupName);
        } else {
            //Load Group Name
            Vector vGroupName = this.getGroupName(vPolicyVersionDetail);
            getRequest().getSession().setAttribute("APPRV_GROUP_NAME", vGroupName);

            // Create HashMap Group Name
            // hGroupName=this.getApprvGroup(vPolicyVersionDetail,);
            //getRequest().getSession().setAttribute("HMAP_APPPRV_GROUP",hGroupName);
        }
        getRequest().getSession().setAttribute("APPRV_ACTION", apprvAction);
        getRequest().getSession().setAttribute("APPRV_SELECT_LOAN_TYPE", loanType);
        getRequest().getSession().setAttribute("APPRV_SELECT_GROUP_NAME", groupName);
        getRequest().getSession().setAttribute("APPRV_SELECT_CUSTOMER_TYPE", customerType);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.PAGE;
    }

    public Vector getGroupName(Vector vPolicyVersionDetail) {
        Vector vresult = new Vector();
        for (int i = 0; i < vPolicyVersionDetail.size(); i++) {
            ApprovAuthorM approvalAuthorM = (ApprovAuthorM) vPolicyVersionDetail.get(i);
            if (!vresult.contains(approvalAuthorM.getGroupName())) {
                vresult.add(approvalAuthorM.getGroupName());
            }
        }
        return vresult;
    }

    public Vector getApprvGroup(Vector vPolicyVersionDetail, String groupName, String loanType, String customerType) {

        if (groupName == null) {
            groupName = "";
        }
        Vector vresult = new Vector();
        for (int i = 0; i < vPolicyVersionDetail.size(); i++) {
            ApprovAuthorM approvalAuthorM = (ApprovAuthorM) vPolicyVersionDetail.get(i);
            if (groupName.equals(approvalAuthorM.getGroupName()) && loanType.equals(approvalAuthorM.getLoanType())
                    && customerType.equals(approvalAuthorM.getCustomerType())) {
                vresult.add(approvalAuthorM);
            }
        }
        if(vresult.size()==0&&!"".equals(groupName)){
            OrigPolicyVersionDataM policyVersionDataM = (OrigPolicyVersionDataM) getRequest().getSession().getAttribute("POLICY_VERSION"); 
            String policyVersion = policyVersionDataM.getPolicyVersion();
            vresult=createApproval(policyVersion,groupName,loanType,customerType);
            vPolicyVersionDetail.addAll(vresult);
        }        
        return vresult;
    }

    public Vector createApprovGroup(String groupName, Vector vPolicyVersionDetail) {
        OrigPolicyVersionDataM policyVersionDataM = (OrigPolicyVersionDataM) getRequest().getSession().getAttribute("POLICY_VERSION"); 
        String policyVersion = policyVersionDataM.getPolicyVersion();
        Vector result = new Vector();
        ORIGUtility utility = ORIGUtility.getInstance();
        //Loan Type
        Vector loanTypeVect = utility.loadCacheByName("LoanType");
        //Customer Type
        Vector customerTypeVect = utility.loadCacheByName("CustomerType");
        //Scoring Result
        //Car Type
        for (int i = 0; i < loanTypeVect.size(); i++) {
            CacheDataInf cacheLoan = (CacheDataInf) loanTypeVect.get(i);
            String loanCode = cacheLoan.getCode();
            for (int j = 0; j < customerTypeVect.size(); j++) {
                CacheDataInf cacheCust = (CacheDataInf) loanTypeVect.get(j);
                String custCode = cacheCust.getCode();
                Vector vCreateApprv=createApproval(policyVersion,groupName,loanCode,custCode);
                result.addAll(vCreateApprv);
            }
        }
        if (result.size() > 0) {
            vPolicyVersionDetail.addAll(result);
        }
        return result;
    }

    private void deleteGroup(Vector vPolicyVersionDetail, String groupName) {
        for (int i = vPolicyVersionDetail.size()-1 ; i >=0; i--) {
            ApprovAuthorM approvalAuthorM = (ApprovAuthorM) vPolicyVersionDetail.get(i);
            log.debug("groupName="+groupName+"   approvalAuthorM.getGroupName()="+approvalAuthorM.getGroupName());
            if (groupName.equals(approvalAuthorM.getGroupName())) {
                vPolicyVersionDetail.remove(i);
            }
        }
    }
  public Vector createApproval(String policyVersion ,String groupName,String loanType,String customerType){
   Vector result=new Vector();    
   //-----------------------
   ApprovAuthorM approvAuthorM_N_ACC = new ApprovAuthorM();
   approvAuthorM_N_ACC.setGroupName(groupName);
   approvAuthorM_N_ACC.setPolicyVersion(policyVersion);
   approvAuthorM_N_ACC.setLoanType(loanType);
   approvAuthorM_N_ACC.setCustomerType(customerType);
   approvAuthorM_N_ACC.setCarType(OrigConstant.CAR_TYPE_NEW);
   approvAuthorM_N_ACC.setScoringResult(XRulesConstant.scoreingResult.RESULT_ACCEPT);
   result.add(approvAuthorM_N_ACC);
   //-----------------
   //-----------------------
   ApprovAuthorM approvAuthorM_N_REF = new ApprovAuthorM();
   approvAuthorM_N_REF.setGroupName(groupName);
   approvAuthorM_N_REF.setPolicyVersion(policyVersion);
   approvAuthorM_N_REF.setLoanType(loanType);
   approvAuthorM_N_REF.setCustomerType(customerType);
   approvAuthorM_N_REF.setCarType(OrigConstant.CAR_TYPE_NEW);
   approvAuthorM_N_REF.setScoringResult(XRulesConstant.scoreingResult.RESULT_REFER);
   result.add(approvAuthorM_N_REF);
   //-----------------
   //    	        // -----------------------
   ApprovAuthorM approvAuthorM_N_REJ = new ApprovAuthorM();
   approvAuthorM_N_REJ.setGroupName(groupName);
   approvAuthorM_N_REJ.setPolicyVersion(policyVersion);
   approvAuthorM_N_REJ.setLoanType(loanType);
   approvAuthorM_N_REJ.setCustomerType(customerType);
   approvAuthorM_N_REJ.setCarType(OrigConstant.CAR_TYPE_NEW);
   approvAuthorM_N_REJ.setScoringResult(XRulesConstant.scoreingResult.RESULT_REJECT);
   result.add(approvAuthorM_N_REJ);
   //-----------------
   //-----------------------
   ApprovAuthorM approvAuthorM_U_ACC = new ApprovAuthorM();
   approvAuthorM_U_ACC.setGroupName(groupName);
   approvAuthorM_U_ACC.setPolicyVersion(policyVersion);
   approvAuthorM_U_ACC.setLoanType(loanType);
   approvAuthorM_U_ACC.setCustomerType(customerType);
   approvAuthorM_U_ACC.setCarType(OrigConstant.CAR_TYPE_USE);
   approvAuthorM_U_ACC.setScoringResult(XRulesConstant.scoreingResult.RESULT_ACCEPT);
   result.add(approvAuthorM_U_ACC);
   //-----------------
   //-----------------------
   ApprovAuthorM approvAuthorM_U_REF = new ApprovAuthorM();
   approvAuthorM_U_REF.setGroupName(groupName);
   approvAuthorM_U_REF.setPolicyVersion(policyVersion);
   approvAuthorM_U_REF.setLoanType(loanType);
   approvAuthorM_U_REF.setCustomerType(customerType);
   approvAuthorM_U_REF.setCarType(OrigConstant.CAR_TYPE_USE);
   approvAuthorM_U_REF.setScoringResult(XRulesConstant.scoreingResult.RESULT_REFER);
   result.add(approvAuthorM_U_REF);
   //-----------------
   //    	        // -----------------------
   ApprovAuthorM approvAuthorM_U_REJ = new ApprovAuthorM();
   approvAuthorM_U_REJ.setGroupName(groupName);
   approvAuthorM_U_REJ.setPolicyVersion(policyVersion);
   approvAuthorM_U_REJ.setLoanType(loanType);
   approvAuthorM_U_REJ.setCustomerType(customerType);
   approvAuthorM_U_REJ.setCarType(OrigConstant.CAR_TYPE_USE);
   approvAuthorM_U_REJ.setScoringResult(XRulesConstant.scoreingResult.RESULT_REJECT);
   result.add(approvAuthorM_U_REJ);
   //-----------------
   return result;
  }

@Override
public boolean getCSRFToken() {
	// TODO Auto-generated method stub
	return false;
}  
}
