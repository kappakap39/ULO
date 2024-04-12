/*
 * Created on Nov 13, 2007
 * Created by Sankom Sanpunya
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
package com.eaf.orig.shared.popup.view.webaction;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.master.shared.model.OrigPolicyRulesExceptionDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.OrigPolicyRulesExceptionDAO;
//import com.eaf.orig.shared.dao.exceptions.OrigMasterMException;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigXRulesUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesDataM;
import com.eaf.xrules.shared.model.XRulesPolicyRulesDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sanom
 * 
 * Type: SaveXrulesPolicyRulesPopupWebAction
 */
public class SaveXrulesPolicyRulesPopupWebAction extends WebActionHelper implements WebAction {
    private static Logger log = (Logger) Logger
            .getLogger(SaveXrulesPolicyRulesPopupWebAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
         
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
         
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
         
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        //Save Policy rules;
        log.debug("SaveXrulesPolicyRulesPopupWebAction-->preModelRequest");
        log.debug("SaveXrulesPolicyRulesPopupWebAction Webation");
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession()
                .getAttribute("ORIGForm");
        UserDetailM ORIGUser = (UserDetailM) getRequest().getSession()
                .getAttribute("ORIGUser");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
        if (applicationDataM == null) {
            applicationDataM = new ApplicationDataM();
        }
        ORIGUtility utility = new ORIGUtility();
        String personalType = (String) getRequest().getSession().getAttribute(
                "PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(
                    true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(ORIGForm
                    .getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
            //personalInfoDataM.setPersonalType(personalType);
            //applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
        }
        VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();

        if (applicationDataM.getJobState() == null) {
            applicationDataM.setJobState("ST0200");
        }
        int serviceID = XRulesConstant.ServiceID.POLICYRULES;   
        XRulesVerificationResultDataM xRulesVerification = personalInfoDataM
        .getXrulesVerification();
        Vector  xRuesPolicyRules=xRulesVerification.getVXRulesPolicyRulesDataM();
        Vector xRuesPolicyRulesAuto = new Vector();
        Vector xRuesPolicyRulesManual = new Vector();        
        for (int i = 0; i < xRuesPolicyRules.size(); i++) {
            XRulesPolicyRulesDataM prmXRulesPolicyRulesDataM = (XRulesPolicyRulesDataM) xRuesPolicyRules
                    .get(i);
            if (XRulesConstant.PolicyRulesType.AUTO
                    .equals(prmXRulesPolicyRulesDataM.getPolicyType())) {
                xRuesPolicyRulesAuto.add(prmXRulesPolicyRulesDataM);
            } else if (XRulesConstant.PolicyRulesType.MANUAL
                    .equals(prmXRulesPolicyRulesDataM.getPolicyType())) {
                xRuesPolicyRulesManual.add(prmXRulesPolicyRulesDataM);
            }
        }
        //============================
//        OrigPolicyRulesExceptionDAO origPolicyRulesExceptionDAO=ORIGDAOFactory.getOrigPolicyRulesExceptionDAO();
        Vector vPolicyException=null;
            try {
//                vPolicyException= origPolicyRulesExceptionDAO.loadModelOrigPolicyRulesExceptionDataM(applicationDataM.getPolicyVersion());
            	vPolicyException = PLORIGEJBService.getORIGDAOUtilLocal().loadModelOrigPolicyRulesExceptionDataM(applicationDataM.getPolicyVersion());            	
            } catch (Exception e) {                 
                log.error("Error ",e);
            }
           if(vPolicyException==null){vPolicyException=new Vector();} 
        for(int i=0;i<xRuesPolicyRulesManual.size();i++){
            XRulesPolicyRulesDataM  prmXRulesPolicyRulesDataM=(XRulesPolicyRulesDataM)xRuesPolicyRulesManual.get(i);
            String policyItemResult=getRequest().getParameter(prmXRulesPolicyRulesDataM.getPolicyCode());                  
            if( (policyItemResult!=null && prmXRulesPolicyRulesDataM.getResult()!=null ) && !policyItemResult.equals(prmXRulesPolicyRulesDataM.getResult()) ){
               log.debug("Change");
                for(int j=0;j<vPolicyException.size();j++){
                  OrigPolicyRulesExceptionDataM origPolicyRulesExceptionDataM=(OrigPolicyRulesExceptionDataM)vPolicyException.get(j);
                  if(origPolicyRulesExceptionDataM.getPolicyCode().equals(prmXRulesPolicyRulesDataM.getPolicyCode())){
                      log.debug("Change Policy Ex hit");
                      applicationDataM.setXuwDecision("");
                      applicationDataM.setXuwOverrideBy("");
                  }
                }
            }
            if(policyItemResult==null){policyItemResult="";} 
            prmXRulesPolicyRulesDataM.setResult(policyItemResult);
        }
        String policyRulesFinalResult=getRequest().getParameter("policyRulesFinalResult");
        log.debug("policyRulesFinalResult "+policyRulesFinalResult);
        xRulesVerification.setPolicyRulesResult(policyRulesFinalResult); 
        String updateRole="";
        if(ORIGUser!=null &&ORIGUser.getRoles()!=null&& ORIGUser.getRoles().size()>0){
             updateRole=(String)ORIGUser.getRoles().get(0); 
        }
        xRulesVerification.setPolicyRulesUpdateBy(ORIGUser.getUserName());
        xRulesVerification.setPolicyRulesUpdateDate(new Timestamp(
                Calendar.getInstance().getTimeInMillis()));
        xRulesVerification.setPolicyRulesUpdateRole(updateRole);
        log.debug("Call Service " + serviceID);
        String strXrulesService = String.valueOf(serviceID);
        OrigXRulesUtil xrulesUtil = OrigXRulesUtil.getInstance();
        XRulesDataM xruleDataM = null;          
        String xrulesExecuteResult = "";  
        xrulesExecuteResult = policyRulesFinalResult; 
        //String txtResultName = getRequest().getParameter("txtResultName");
        //getRequest().getSession().setAttribute("txtResultName", txtResultName);
        getRequest().getSession().setAttribute("execResult",
                xrulesExecuteResult);
        getRequest().getSession().setAttribute("xrulseExecuteService",
                strXrulesService);
        log.debug("Execute XruleVerification  Xrules Reuslt ="
                + xrulesExecuteResult);
        getRequest().getSession().setAttribute("openPopup", "N");
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

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
