/*
 * Created on Oct 31, 2007
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
package com.eaf.orig.shared.view.form.subform;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.EjbUtilException;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.ProposalDataM;
import com.eaf.orig.shared.model.ReasonDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author weeraya
 *
 * Type: ResultSubForm
 */
public class UWCardResultSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(UWCardResultSubForm.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.NaosSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
    	ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
    	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGUtility utility = new ORIGUtility();
        
    	String final_credit_limit = request.getParameter("final_credit_limit");
        String decision = request.getParameter("decision_uw");
        String decision_reason = request.getParameter("decision_reason");
        String[] reason = request.getParameterValues("reason_uw");
        String proposal_expiry_date = request.getParameter("proposal_expiry_date");
        String escalateGroup = request.getParameter("escalateGroup");
        logger.debug("reasons uw = "+reason);
        logger.debug("decision = "+decision);
        
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        applicationDataM.setUwDecision(decision);
        applicationDataM.setUwDecisionReason(decision_reason);
        applicationDataM.setFinalCreditLimit(utility.stringToBigDecimal(final_credit_limit));
        applicationDataM.setEscalateTo(escalateGroup);
        
        //set HproposalM
        ProposalDataM proposalM = applicationDataM.getProposalDataM();
        if(proposalM!=null){
            proposalM.setFinalCreditLimit(utility.stringToBigDecimal(final_credit_limit));
            proposalM.setProposalExpireDate(ORIGUtility.parseThaiToEngDate(proposal_expiry_date));
            proposalM.setUpdateBy(userM.getUserName());
            if(proposalM.getCreateBy()==null){
                proposalM.setCreateBy(userM.getUserName());
            }
        }
        
        if(reason!=null){
			Vector reasonVt = utility.removeReasonByRole(applicationDataM.getReasonVect(), OrigConstant.ROLE_UW);
			ReasonDataM reasonDataM; 
			for(int i=0; i<reason.length; i++){
				logger.debug("reason = " + reason[i]);
				reasonDataM = new ReasonDataM();
				reasonDataM.setReasonCode(reason[i]);
				reasonDataM.setReasonType(decision);
				logger.debug("decision " + decision);
				reasonDataM.setReasonSeq(i+1);
				reasonDataM.setRole(OrigConstant.ROLE_UW);
				reasonDataM.setCreateBy(userM.getUserName());
				reasonDataM.setUpdateBy(userM.getUserName());
				reasonVt.add(reasonDataM);
			}
			applicationDataM.setReasonVect(reasonVt);
		}		
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.NaosSubForm#validateForm(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public HashMap validateForm(HttpServletRequest request, Object appForm) {
         
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateSubForm(javax.servlet.http.HttpServletRequest)
     */
    public boolean validateSubForm(HttpServletRequest request) {
        boolean result = false;
        UserDetailM userDetailM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        
        ApplicationDataM appForm = formHandler.getAppForm();
        if (appForm == null) {
            appForm = new ApplicationDataM();
        }
        ORIGUtility utility = new ORIGUtility();
        Vector vErrors = formHandler.getFormErrors();
        Vector vErrorFields = formHandler.getErrorFields();
        Vector vNotErrorFields = formHandler.getNotErrorFields();
        if (vErrors == null) {
            vErrors = new Vector();
        }
        String errorMsg = null;
        
        String decision = request.getParameter("decision_uw");
        if(ORIGWFConstant.ApplicationDecision.APPROVE.equals(decision)){
	        String final_credit_limit = request.getParameter("final_credit_limit");
	        try {
	            final_credit_limit = URLDecoder.decode(final_credit_limit,"UTF-8");
	        } catch (UnsupportedEncodingException e1) {
	            logger.error(e1);
	        }
	        
//	        UtilityDAO utilDAO = ORIGDAOFactory.getUtilityDAO();
	        try {
//	            BigDecimal lendingLimit = utilDAO.getLendingLimit(userDetailM.getUserName(),appForm.getBusinessClassId());
	        	BigDecimal lendingLimit = PLORIGEJBService.getORIGDAOUtilLocal().getLendingLimit(userDetailM.getUserName(),appForm.getBusinessClassId());
	            if(lendingLimit!=null && lendingLimit.compareTo(utility.stringToBigDecimal(final_credit_limit)) < 0 ){
	                errorMsg = "Final credit limit must less than your lending limit.";
	                vErrors.add(errorMsg);
	                result = true;
	            }
	        } catch (Exception e) {
	            logger.error(e);
	        }
        }
        
        return result;
    }

}
