package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.PLOrigUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;

public class DecisionSubForm extends ORIGSubForm {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm){
		
		PLOrigFormHandler origForm = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		PLApplicationDataM applicationM = origForm.getAppForm();		
			if( null == applicationM)  applicationM = new PLApplicationDataM();
		
		String[] reasonArray = request.getParameterValues("reasonOption");		
		String decision = request.getParameter("decision_option");
		String docRefID = request.getParameter("decision_ref_no");
		
		String reasonType = request.getParameter("reasonType");
		String role = userM.getCurrentRole();
				
//		logger.debug("[setProperties] Doc_ref_id = "+docRefID+" Decision "+decision+" ReasonType "+reasonType+" ReasonRole "+role);
//		logger.debug("[setProperties] Decision = "+decision);
//		logger.debug("[setProperties] ReasonType = "+reasonType);
//		logger.debug("[setProperties] ReasonRole = "+role);
		
		applicationM.setThDescDecision(request.getParameter("thDescDecision"));
		applicationM.setPolicyExcDocNo(docRefID);
		
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		
		if(OrigUtil.isEmptyString(decision)){
			return;
		}				
		
		/**#SeptemWi Decision From Execute ILOG Submit Action !!*/
		if(!OrigConstant.Action.CANCEL.equals(decision)){
			if(PLXrulesConstant.ResultCode.CODE_SETUP_DECISION.equals(applicationM.getExecuteDecision())
					&& !OrigUtil.isEmptyString(applicationM.getAppDecision())){
				logger.debug("Decision has Setup Form Other Logic");
				ORIGCacheDataM origCacheM = cacheUtil.GetDecisionCacheDataM(role, applicationM.getBusinessClassId());			
				PLOrigUtility.SetDecisionModelAppM(applicationM, origCacheM);
				return;
			}
		}
		/**END #Septem*/
		
		applicationM.setAppDecision(decision);		
		
		ORIGCacheDataM origCacheM = cacheUtil.GetDecisionCacheDataM(role, applicationM.getBusinessClassId());
		
		PLOrigUtility.SetDecisionModelAppM(applicationM, origCacheM);
				
		logger.debug("Decision set Reason Type >> "+reasonType);
		
		PLReasonDataM reasonDataM = null;
		
		applicationM.setReasonVect(null);		
		if(reasonArray != null && reasonArray.length > 0){
		   Vector<PLReasonDataM> vReason = new Vector<PLReasonDataM>();
		   for(int i=0; i<reasonArray.length; i++){
			   String reason = reasonArray[i];
			   reasonDataM = new PLReasonDataM();
			   reasonDataM.setReasonCode(reason);
			   reasonDataM.setReasonType(reasonType);
			   logger.debug("Set Reason code >> "+reason);
			   reasonDataM.setRole(role);
			   reasonDataM.setApplicationRecordId(applicationM.getAppRecordID());
			   vReason.add(reasonDataM);
			   applicationM.setReasonVect(vReason);
		   }		  
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		  
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
         logger.debug("validateSubForm ");
         boolean result = false;
         PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
         UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
         
         String decision = request.getParameter("decision_option");
         String requestCreditStr = request.getParameter("card_info_credit_request");
         String currentCreditStr = request.getParameter("card_info_current_credit");
         String reasonType = request.getParameter("reasonType");
         
         PLApplicationDataM appM = formHandler.getAppForm();
         if(appM == null) appM = new PLApplicationDataM();
         /**New Logic Manual Validate Subform #SeptemWi*/
         
         String errorMsg="";                           
         if(OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType())){
        	 //CR validate reason if not role SUP or not confirm reject (if confirm reject with role SUP not validate)
        	 if(reasonType != null && !reasonType.equals("")){
                 String[] decisionOption = request.getParameterValues("reasonOption");              
                 if (decisionOption==null || decisionOption.length==0){            	
                	 errorMsg = ErrorUtil.getShortErrorMessage(request, "REASON_OPTION");            	 
                	 formHandler.PushErrorMessage("div-decision-reason", errorMsg);
                	 result = true;
                 }
        	 }
             
             if(OrigConstant.ROLE_FU.equals(userM.getCurrentRole())){
            	 if(OrigConstant.wfProcessState.SENDX.equals(decision) 
            			 && !OrigConstant.DocListResultCode.COMPLETE_BEFORE_FU.equals(appM.getDocListResultCode())
            			 	&& !OrigConstant.DocListResultCode.COMPLETE_AFTER_FU.equals(appM.getDocListResultCode())){
            		 errorMsg = ErrorUtil.getShortErrorMessage(request, "DECISION_NOT_RELATE_DOC");
            		 formHandler.PushErrorMessage("", errorMsg);
                	 result = true;
            	 }
             }else if((OrigConstant.wfProcessState.SEND.equals(decision) || OrigConstant.wfProcessState.SENDX.equals(decision))
        			 && !OrigConstant.DocListResultCode.COMPLETE_BEFORE_FU.equals(appM.getDocListResultCode())
        			 	&& !OrigConstant.DocListResultCode.COMPLETE_AFTER_FU.equals(appM.getDocListResultCode())){
        		 errorMsg = ErrorUtil.getShortErrorMessage(request, "DECISION_NOT_RELATE_DOC");
        		 formHandler.PushErrorMessage("", errorMsg);
            	 result = true;
        	 }
             
             if(OrigConstant.BusClass.FCP_KEC_DC.equals(appM.getBusinessClassId()) &&
            		 (OrigConstant.wfProcessState.SEND.equals(decision) || OrigConstant.wfProcessState.SENDX.equals(decision))){
            	 if(requestCreditStr == null || "".equals(requestCreditStr)){
            		 errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUEST_CREDIT");
            		 formHandler.PushErrorMessage("", errorMsg);
                	 result = true;
            	 }else{
            		 double requestCredit = 0;
            		 double currentCredit = 0;
            		 try{
            			 requestCredit = DataFormatUtility.replaceCommaForBigDecimal(requestCreditStr).doubleValue();
            		 }catch(NumberFormatException e){
            			 logger.error("##### Cannot convert request credit lint to number :" + e.getMessage());
            		 }
            		 if(currentCreditStr != null && !"".equals(currentCreditStr)){
            			 try{
            				 currentCredit = DataFormatUtility.replaceCommaForBigDecimal(currentCreditStr).doubleValue();
            			 }catch(NumberFormatException e){
            				 logger.error("##### Cannot convert current credit lint to number :" + e.getMessage());
            			 }
            		 }
            		 if(requestCredit >= currentCredit){
            			 errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUEST_MORE_OLD_CREDIT");
                		 formHandler.PushErrorMessage("", errorMsg);
                    	 result = true;
            		 }
            	 }
             }
         }
         
         return result;
	}
	
	
	
}
