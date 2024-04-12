/**
 * Create Date Apr 19, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
*/
package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.OrigBusinessClassUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.app.utility.PLOrigUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.OverrideCapportDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

/**
 * @author Sankom
 *
 */
public class CADecisionSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(CADecisionSubForm.class);
	PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
		logger.debug("CADecisionSubForm");
		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		String fainalCredit = request.getParameter("ca_final_credit");
		String finalCreditReason = request.getParameter("final_credit_reason");
		String finalCreditRemark = request.getParameter("ca_final_credit_remark");
		String refference = request.getParameter("ca_decision_refference");
		String reasonType = request.getParameter("reasonType");
		String reason[] = request.getParameterValues("reasonOption");
		String decision = request.getParameter("decision_option");
		String caDecision = request.getParameter("decision_option");
				
		BigDecimal fainalCreditLine = DataFormatUtility.replaceCommaForBigDecimal(fainalCredit);
		
		applicationM.setFinalCreditLimit(fainalCreditLine);
		applicationM.setFinalCreditLimitReason(finalCreditReason);
		applicationM.setFinalCreditLimitRemark(finalCreditRemark);
		applicationM.setPolicyExcDocNo(refference);
		
		String role= userM.getCurrentRole();
		
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
				
		if(OrigUtil.isEmptyString(decision)) return;		

		/**#Praisan Decision From Execute ILOG Submit Action !!*/
		if(!OrigConstant.Action.CANCEL.equals(decision)){
			if(PLXrulesConstant.ResultCode.CODE_SETUP_DECISION.equals(applicationM.getExecuteDecision())
					&& !OrigUtil.isEmptyString(applicationM.getAppDecision())){
				logger.debug("Decision has Setup Form Other Logic");
				ORIGCacheDataM origCacheM = cacheUtil.GetDecisionCacheDataM(role, applicationM.getBusinessClassId());			
				PLOrigUtility.SetDecisionModelAppM(applicationM, origCacheM);
				return;
			}
		}
		/**END #Praisan*/
		
		applicationM.setAppDecision(caDecision);
		String decisionRole = role;
		if(!OrigUtil.isEmptyString(applicationM.getJobState()) && OrigConstant.roleJobState.CA_I_SUP.equals(applicationM.getJobState())){
			decisionRole = OrigConstant.ROLE_CA_SUP;
		}
		
		ORIGCacheDataM origCacheM = cacheUtil.GetDecisionCacheDataM(decisionRole, applicationM.getBusinessClassId());
		
		PLOrigUtility.SetDecisionModelAppM(applicationM, origCacheM);
		
		applicationM.setReasonVect(null);
		if(reason != null){
			Vector<PLReasonDataM> reasonVT = new Vector<PLReasonDataM>();
			for(int i=0;i<reason.length;i++){
				if(reason[i] != null){
					String reasonCode = reason[i];
					PLReasonDataM reasonM = new PLReasonDataM();
					reasonM.setApplicationRecordId(applicationM.getAppRecordID());
					reasonM.setReasonCode(reasonCode);
					reasonM.setReasonType(reasonType);
					reasonM.setRole((String)userM.getRoles().elementAt(0));
					reasonVT.add(reasonM);
				}
			}
			applicationM.setReasonVect(reasonVT);
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		PLApplicationDataM applicationM = formHandler.getAppForm();
		boolean result = false;
		PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
		ProcessMenuM menuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
		if(menuM == null) menuM = new ProcessMenuM();	
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);

		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();		
		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
		}
		
		String decision = request.getParameter("decision_option");
		if(OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType())){
			if(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType()){ //Praisan 20121218 validate only type submit (1)
				String finalCreditLine  = request.getParameter("ca_final_credit");
				String creditLineReason = request.getParameter("final_credit_reason");
				String creditLineRemark = request.getParameter("ca_final_credit_remark");
				double finalCreditLimit = 0;
				if(finalCreditLine != null && !"".equals(finalCreditLine)){
					finalCreditLimit = DataFormatUtility.replaceCommaForBigDecimal(finalCreditLine).doubleValue();
				}		
				
				if ((Double.compare(finalCreditLimit, 0) == 0 && !OrigConstant.roleJobState.CA_I_SUP.equals(applicationM.getJobState())) && !decision.startsWith(OrigConstant.Action.SEND_BACK)){
					formHandler.PushErrorMessage("ca_final_credit", ErrorUtil.getShortErrorMessage(request, "FINAL_CREDIT_LINE"));
					result = true;
				}				
				
	            if(OrigConstant.Action.APPROVE.equals(decision)){
	            	//validate recommend result must Accept
	            	if(!isMatchWithRecommendResult(personalM, OrigConstant.recommendResult.ACCEPT)){
	            		formHandler.PushErrorMessage("", "cannot approve with recommend result not ACCEPT");
	    				result = true;
	            	}
	            	//validate credit line
	            	if(OrigConstant.BusClass.FCP_KEC_IC.equals(applicationM.getBusinessClassId())){
	    				result = this.validateInCreaseCriditLine(formHandler, request, finalCreditLimit, applicationM);
	            	}else if(OrigConstant.BusClass.FCP_KEC_DC.equals(applicationM.getBusinessClassId())){
	    				result = this.validateDeCreaseCriditLine(formHandler, request, finalCreditLimit, applicationM);
	            	}else{
	            		result = this.validateFinalCreditLine(formHandler, request, applicationM, finalCreditLimit);
	            	}
	            	
	            	//validate approve date of project code
	            	if(!OrigBusinessClassUtil.getInstance().isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW) 
	            			&& isOverApproveDateProjectCode(applicationM)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OUT_OF_PROJECT_DATE"));
	    				result = true;
	            	}
	            	//validate minimum final credit line
	            	BigDecimal minCreditLine = appUtil.getMinFinalCreditLine(applicationM);
	            	if(finalCreditLimit < minCreditLine.doubleValue()){
	            		try{
	            			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "MIN_FINAL") + DataFormatUtility.displayCommaNumberNullOrZeroToSpecify(minCreditLine,"0.00") + " "+ 
	            				                         MessageResourceUtil.getTextDescription(request, "BATH"));
	            		}catch(Exception e){}
	    				result = true;
	            	}
	            	//validate %Debt burden Inc. KEC
	            	if(!PLXrulesConstant.ResultCode.CODE_WAIVED.equals(getDebtResultCode(applicationM)) && getDebtPercentIncKEC(applicationM) > getDebtPercentNormal(applicationM)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "DEBT_KEC_MORE_NORMAL"));
	    				result = true;
	            	}
	            	//validate lending limit
	            	if(!OrigApplicationUtil.getInstance().isCreditLimitAuthorize(userM.getUserName(), menuM.getMenuID(), finalCreditLimit, applicationM.getJobType())){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "NOT_ENOUGH_AUTHORIZE"));
	    				result = true;
	            	}
	            	//Validate capport
	            	if(!OrigBusinessClassUtil.getInstance().isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW) 
	            			&& isOverCreditLineCapport(finalCreditLimit, request)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OVER_CREDIT_CAPPORT"));
	    				result = true;
					}
	            	//validate final credit line reason
	            	if((DataFormatUtility.parseDoubleNullToZero(applicationM.getRecommentCreditLine()) != finalCreditLimit)){
						if (creditLineReason == null || "".equals(creditLineReason)){
							formHandler.PushErrorMessage("final_credit_reason", ErrorUtil.getShortErrorMessage(request, "FINAL_DIFF_RECOMMEND"));
							result = true;
						}else if (OrigConstant.creditLineReason.SPECIFY.equals(creditLineReason) && (creditLineRemark == null || "".equals(creditLineRemark))){
							formHandler.PushErrorMessage("ca_final_credit_remark", ErrorUtil.getShortErrorMessage(request, "FINAL_DIFF_RECOMMEND"));
							result = true;
						}
					}
				}else if(OrigConstant.Action.OVERRIDE.equals(decision)){
					//validate recommend result must Accept
	            	if(!isMatchWithRecommendResult(personalM, OrigConstant.recommendResult.OVERIDE)){
	            		formHandler.PushErrorMessage("", "cannot override with recommend result not OVERIDE");
	    				result = true;
	            	}
	            	
	            	//validate credit line
	            	if(OrigConstant.BusClass.FCP_KEC_IC.equals(applicationM.getBusinessClassId())){
	    				result = this.validateInCreaseCriditLine(formHandler, request, finalCreditLimit, applicationM);
	            	}else if(OrigConstant.BusClass.FCP_KEC_DC.equals(applicationM.getBusinessClassId())){
	    				result = this.validateDeCreaseCriditLine(formHandler, request, finalCreditLimit, applicationM);
	            	}else{
	            		result = this.validateFinalCreditLine(formHandler, request, applicationM, finalCreditLimit);
	            	}
	            	
	            	//validate %Debt burden Inc. KEC
	            	if(!PLXrulesConstant.ResultCode.CODE_WAIVED.equals(getDebtResultCode(applicationM)) && getDebtPercentIncKEC(applicationM) > getDebtPercentOverride(applicationM)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "DEBT_KEC_MORE_OVERRIDE"));
	    				result = true;
	            	}            	
	            	//validate override authorize
	        		if(!OrigApplicationUtil.getInstance().isOverrideAuthorize(userM.getUserName(), menuM.getMenuID(), applicationM)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "NOT_ENOUGH_AUTHORIZE"));
	    				result = true;
	            	}
	        		//validate lending limit ----------------------------------------------------- CR01
	            	if(!OrigApplicationUtil.getInstance().isCreditLimitAuthorize(userM.getUserName(), menuM.getMenuID(), finalCreditLimit, applicationM.getJobType())){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "NOT_ENOUGH_AUTHORIZE"));
	    				result = true;
	            	}// ----------------------------------------------------- CR01
	        		//validate approve date of project code
	            	if(!OrigBusinessClassUtil.getInstance().isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW) 
	            			&& isOverApproveDateProjectCode(applicationM)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OUT_OF_PROJECT_DATE"));
	    				result = true;
	            	}
	            	//validate minimum final credit line
	            	BigDecimal minCreditLine = appUtil.getMinFinalCreditLine(applicationM);
	            	if(finalCreditLimit < minCreditLine.doubleValue()){
	            		try{
	            			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "MIN_FINAL") + DataFormatUtility.displayCommaNumberNullOrZeroToSpecify(minCreditLine,"0.00") + " "+ 
		                         MessageResourceUtil.getTextDescription(request, "BATH"));
	            		}catch(Exception e){}
	    				result = true;
	            	}
	            	//Validate capport
	            	if(!OrigBusinessClassUtil.getInstance().isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW) 
	            			&& isOverCreditLineCapport(finalCreditLimit, request)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OVER_CREDIT_CAPPORT"));
	    				result = true;
					}
	            	
	            	//Validate Override capport
	            	OverrideCapportDataM overideCapportM = appUtil.getOverideCapport(applicationM.getAppRecordID());
	            	if(!OrigConstant.BusClass.FCP_KEC_DC.equals(applicationM.getBusinessClassId()) && !isApproveOverideCapport(overideCapportM)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OVER_OVERRIDE_CAPPORT"));
	    				result = true;
	            	}
	            	
	            	//validate final credit line reason 
	            	if((DataFormatUtility.parseDoubleNullToZero(applicationM.getRecommentCreditLine()) != finalCreditLimit)){
						if (creditLineReason == null || "".equals(creditLineReason)){
							formHandler.PushErrorMessage("final_credit_reason", ErrorUtil.getShortErrorMessage(request, "FINAL_DIFF_RECOMMEND"));
							result = true;
						}else if (OrigConstant.creditLineReason.SPECIFY.equals(creditLineReason) && (creditLineRemark == null || "".equals(creditLineRemark))){
							formHandler.PushErrorMessage("ca_final_credit_remark", ErrorUtil.getShortErrorMessage(request, "FINAL_DIFF_RECOMMEND"));
							result = true;
						}
					}
				}else if(OrigConstant.Action.POLICY_EXCEPTION.equals(decision)){
					//validate refference no
					String exceptionRef = request.getParameter("ca_decision_refference");
					if(exceptionRef == null || "".equals(exceptionRef)){
						formHandler.PushErrorMessage("ca_decision_refference",ErrorUtil.getShortErrorMessage(request, "REQUIRE_EX_REF"));
						result = true;
					}
					
					//validate lending limit ----------------------------------------------------- CR01
	            	if(!OrigApplicationUtil.getInstance().isCreditLimitAuthorize(userM.getUserName(), menuM.getMenuID(), finalCreditLimit, OrigConstant.typeColor.typeGreen)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "NOT_ENOUGH_AUTHORIZE"));
	    				result = true;
	            	}// ----------------------------------------------------- CR01
	            	
	            	//validate approve date of project code
	            	if(!OrigBusinessClassUtil.getInstance().isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW) 
	            			&& isOverApproveDateProjectCode(applicationM)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OUT_OF_PROJECT_DATE"));
	    				result = true;
	            	}
	            	
	            	//validate credit line
	            	if(OrigConstant.BusClass.FCP_KEC_IC.equals(applicationM.getBusinessClassId())){
	    				result = this.validateInCreaseCriditLine(formHandler, request, finalCreditLimit, applicationM);
	            	}else if(OrigConstant.BusClass.FCP_KEC_DC.equals(applicationM.getBusinessClassId())){
	    				result = this.validateDeCreaseCriditLine(formHandler, request, finalCreditLimit, applicationM);
	            	}else{
	            		result = this.validateFinalCreditLine(formHandler, request, applicationM, finalCreditLimit);
	            	}
	            	//validate minimum final credit line
	            	BigDecimal minCreditLine = appUtil.getMinFinalCreditLine(applicationM);
	            	if(finalCreditLimit < minCreditLine.doubleValue()){
	            		try{
	            			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "MIN_FINAL") + DataFormatUtility.displayCommaNumberNullOrZeroToSpecify(minCreditLine,"0.00") + " "+ 
	            				                         MessageResourceUtil.getTextDescription(request, "BATH"));
	            		}catch(Exception e){}
	    				result = true;
	            	}
	            	//Validate capport
	            	if(!OrigBusinessClassUtil.getInstance().isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW) 
	            			&& isOverCreditLineCapport(finalCreditLimit, request)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OVER_CREDIT_CAPPORT"));
	    				result = true;
					}
	            	//Validate Override capport
	            	OverrideCapportDataM overideCapportM = appUtil.getOverideCapport(applicationM.getAppRecordID());
	            	if(!OrigConstant.BusClass.FCP_KEC_DC.equals(applicationM.getBusinessClassId()) && !isApproveOverideCapport(overideCapportM)){
	            		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OVER_OVERRIDE_CAPPORT"));
	    				result = true;
	            	}
	            	//validate final credit line reason
					if((DataFormatUtility.parseDoubleNullToZero(applicationM.getRecommentCreditLine()) != finalCreditLimit)){
						if (creditLineReason == null || "".equals(creditLineReason)){
							formHandler.PushErrorMessage("final_credit_reason", ErrorUtil.getShortErrorMessage(request, "FINAL_DIFF_RECOMMEND"));
							result = true;
						}else if (OrigConstant.creditLineReason.SPECIFY.equals(creditLineReason) && (creditLineRemark == null || "".equals(creditLineRemark))){
							formHandler.PushErrorMessage("ca_final_credit_remark", ErrorUtil.getShortErrorMessage(request, "FINAL_DIFF_RECOMMEND"));
							result = true;
						}
					}
				}else if(OrigConstant.Action.ESCALATE.equals(decision)){
					PLXRulesVerificationResultDataM verResult = personalM.getXrulesVerification();
					if(!OrigBusinessClassUtil.getInstance().isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW)){
	            		//validate approve date of project code
	            		if(isOverApproveDateProjectCode(applicationM)){
	            			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OUT_OF_PROJECT_DATE"));
	        				result = true;
	            		}
	            		//Validate capport
	                	if(isOverCreditLineCapport(finalCreditLimit, request)){
	                		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OVER_CREDIT_CAPPORT"));
	        				result = true;
	    				}
	                	if(!isMatchWithRecommendResult(personalM, OrigConstant.recommendResult.ACCEPT) ||
	                			(applicationM.getPolicyExcDocNo() != null && !"".equals(applicationM.getPolicyExcDocNo()))){
		                	//Validate override capport
	                    	OverrideCapportDataM overideCapportM = appUtil.getOverideCapport(applicationM.getAppRecordID());
	                    	if(!isApproveOverideCapport(overideCapportM)){
	                    		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OVER_OVERRIDE_CAPPORT"));
	            				result = true;
	                    	}
	                	}
	            	}else if(OrigConstant.BusClass.FCP_KEC_IC.equals(applicationM.getBusinessClassId()) 
	            			&& ((verResult != null && !OrigConstant.recommendResult.ACCEPT.equals(verResult.getRecommendResult())) ||
	            					(applicationM.getPolicyExcDocNo() != null && !"".equals(applicationM.getPolicyExcDocNo())))){
	            		//Validate Override capport
	                	OverrideCapportDataM overideCapportM = appUtil.getOverideCapport(applicationM.getAppRecordID());
	                	if(!isApproveOverideCapport(overideCapportM)){
	                		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "OVER_OVERRIDE_CAPPORT"));
	        				result = true;
	                	}
	            	}
					if(isMatchWithRecommendResult(personalM, OrigConstant.recommendResult.ACCEPT)||
                			(applicationM.getPolicyExcDocNo() != null && !"".equals(applicationM.getPolicyExcDocNo()))){
						//validate lending limit
						if(OrigApplicationUtil.getInstance().isCreditLimitAuthorize(userM.getUserName(), menuM.getMenuID(), finalCreditLimit, applicationM.getJobType())){
	                		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "ESCALATE_IN_OTHERIZE"));
	        				result = true;
	                	}
					}else if(isMatchWithRecommendResult(personalM, OrigConstant.recommendResult.OVERIDE)){
						boolean passLending = false;
						boolean passOverride = false;
						//validate lending limit
						if(OrigApplicationUtil.getInstance().isCreditLimitAuthorize(userM.getUserName(), menuM.getMenuID(), finalCreditLimit, applicationM.getJobType())){
							passLending = true;
	                	}
						//validate override authorize
	                	if(OrigApplicationUtil.getInstance().isOverrideAuthorize(userM.getUserName(), menuM.getMenuID(), applicationM)){
	                		passOverride = true;
	                	}
	                	if(passLending && passOverride){
	                		formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "ESCALATE_IN_OTHERIZE"));
	                		result = true;
	                	}
					}
					//validate final credit line reason
					if((DataFormatUtility.parseDoubleNullToZero(applicationM.getRecommentCreditLine()) != finalCreditLimit)){
						if (creditLineReason == null || "".equals(creditLineReason)){
							formHandler.PushErrorMessage("final_credit_reason", ErrorUtil.getShortErrorMessage(request, "FINAL_DIFF_RECOMMEND"));
							result = true;
						}else if (OrigConstant.creditLineReason.SPECIFY.equals(creditLineReason) && (creditLineRemark == null || "".equals(creditLineRemark))){
							formHandler.PushErrorMessage("ca_final_credit_remark", ErrorUtil.getShortErrorMessage(request, "FINAL_DIFF_RECOMMEND"));
							result = true;
						}
					}
				} 
	        }			
			
			if(OrigConstant.BusClass.FCP_KEC_CC.equals(applicationM.getBusinessClassId()) 
					|| OrigConstant.BusClass.FCP_KEC_CG.equals(applicationM.getBusinessClassId())){
				if(OrigConstant.Action.REJECT.equals(decision) && "RJ".equals(xrulesVerM.getRecommendResult())){
					logger.debug("do not validate Reason..");
				}else{
					String reasonType = request.getParameter("reasonType");
					if(reasonType != null && !reasonType.equals("")){
			            String[] decisionOption = request.getParameterValues("reasonOption");              
			            if (decisionOption==null || decisionOption.length==0) {
				           	formHandler.PushErrorMessage("div-decision-reason", ErrorUtil.getShortErrorMessage(request, "REASON_OPTION"));
				           	result = true;
			            }
					}
				}
			}else{
				String reasonType = request.getParameter("reasonType");
				if(reasonType != null && !reasonType.equals("")){
		            String[] decisionOption = request.getParameterValues("reasonOption");              
		            if (decisionOption==null || decisionOption.length==0) {
			           	formHandler.PushErrorMessage("div-decision-reason", ErrorUtil.getShortErrorMessage(request, "REASON_OPTION"));
			           	result = true;
		            }
				}
			}
		}
		
		return result;
	}
	
	private boolean isOverApproveDateProjectCode(PLApplicationDataM appM){
		if (appUtil == null){
			appUtil = new PLOrigApplicationUtil();
		}
		boolean result = false;
		if(!OrigConstant.FLAG_Y.equals(appM.getExceptionProject())){
			PLProjectCodeDataM projectCodeDataM = appUtil.getProjectCodeDataM(appM.getProjectCode());
			
	//    	logger.debug("@@@@@ Approve date of project code :"+projectCodeDataM.getApprovedate());
	    	
			Date currentDate = DataFormatUtility.trim(new Date());
	    	
	//    	logger.debug("@@@@@ current date :" + currentDate);
	    	
	    	if(projectCodeDataM != null && projectCodeDataM.getApprovedate() != null && projectCodeDataM.getApprovedate().compareTo(currentDate) < 0){
				result = true;
	    	}
		}
    	return result;
	}
	private boolean isMatchWithRecommendResult(PLPersonalInfoDataM applicantPerson, String checkResult){
		boolean result = false;
		PLXRulesVerificationResultDataM verResult = applicantPerson.getXrulesVerification();
    	if(verResult != null && checkResult.equals(verResult.getRecommendResult())){
			result = true;
    	}
    	return result;
	}
	private boolean isOverCreditLineCapport(double finalCreditLimit, HttpServletRequest request){
		boolean result = false;
		CapportGroupDataM capportGroupM = (CapportGroupDataM)request.getSession().getAttribute("PL_CAPPORT");
    	if(capportGroupM != null && !OrigUtil.isEmptyString(capportGroupM.getCapportGroupId())){
			if((finalCreditLimit + capportGroupM.getCapportUsed()) > capportGroupM.getCapportAmount()){
				result = true;
			}
		}
    	return result;
	}
	private boolean isApproveOverideCapport(OverrideCapportDataM overideCapportM){
		boolean result = false;
    	if(OrigConstant.Action.APPROVE.equals(overideCapportM.getResult())){
			result = true;
    	}
    	return result;
	}
	private double getDebtPercentNormal(PLApplicationDataM appM){
		double debtPercent = 0;
		if(appM != null){
			PLPersonalInfoDataM applicantPerson = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			ORIGCacheDataM cacheM = ORIGCacheUtil.getInstance().GetListboxCacheDataM(OrigConstant.fieldId.INCOME_TYPE, appM.getBusinessClassId(), applicantPerson.getApplicationIncomeType());
			if(cacheM.getSystemID9() != null && !"".equals(cacheM.getSystemID9())){
				debtPercent = Double.parseDouble(cacheM.getSystemID9());
			}
		}
//		logger.debug("@@@@@ debtPercent :" + debtPercent);
		return debtPercent;
	}
	private double getDebtPercentOverride(PLApplicationDataM appM){
		double debtPercent = 0;
		if(appM != null){
			PLPersonalInfoDataM applicantPerson = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			ORIGCacheDataM cacheM = ORIGCacheUtil.getInstance().GetListboxCacheDataM(OrigConstant.fieldId.INCOME_TYPE, appM.getBusinessClassId(), applicantPerson.getApplicationIncomeType());
			if(cacheM.getSystemID10() != null && !"".equals(cacheM.getSystemID10())){
				debtPercent = Double.parseDouble(cacheM.getSystemID10());
			}
		}
//		logger.debug("@@@@@ debtPercent :" + debtPercent);
		return debtPercent;
	}
	private double getDebtPercentIncKEC(PLApplicationDataM appM){
		double debtPercent = 0;
		if(appM != null){
			PLPersonalInfoDataM applicantPerson = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM verResult = null;
			PLXRulesDebtBdDataM debtBDM = null;
			//find model PLXRulesDebtBdDataM
			if(applicantPerson != null){
				verResult = applicantPerson.getXrulesVerification();
				if(verResult != null){
					debtBDM = verResult.getXRulesDebtBdDataM();
				}
			}
			if(debtBDM == null){
				debtBDM = new PLXRulesDebtBdDataM();
			}
			if(debtBDM.getDebtBurdentScoreAdjust() != null){
				debtPercent = debtBDM.getDebtBurdentScoreAdjust().doubleValue();
			}
		}
//		logger.debug("@@@@@ debtPercent :" + debtPercent);
		return debtPercent;
	}
	
	private String getDebtResultCode(PLApplicationDataM appM){
		String resultCode = null;
		if(appM != null){
			PLPersonalInfoDataM applicantPerson = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM verResult = null;
			//find model PLXRulesDebtBdDataM
			if(applicantPerson != null){
				verResult = applicantPerson.getXrulesVerification();
				if(verResult != null){
					resultCode = verResult.getDebtBdCode();
				}
			}
		}
//		logger.debug("@@@@@ resultCode :" + resultCode);
		return resultCode;
	}
	
	
	
	private boolean validateInCreaseCriditLine(PLOrigFormHandler formHandler, HttpServletRequest request, double finalCreditLine, PLApplicationDataM appM){
		PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
		boolean hasError = false;
		double currentCreditLine = appUtil.getCurrentCreditLine(appM);
		if(finalCreditLine <= currentCreditLine){
			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "INCREASE_CREDIT_AMOUNT"));
			hasError = true;
		}
		return hasError;
	}
	
	private boolean validateDeCreaseCriditLine(PLOrigFormHandler formHandler, HttpServletRequest request, double finalCreditLine, PLApplicationDataM appM){
		PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
		boolean hasError = false;
		double currentCreditLine = appUtil.getCurrentCreditLine(appM);
		if(finalCreditLine >= currentCreditLine){
			formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "DECREASE_CREDIT_AMOUNT"));
			hasError = true;
		}
		return hasError;
	}
	
	private boolean validateFinalCreditLine(PLOrigFormHandler formHandler, HttpServletRequest request, PLApplicationDataM appM, double finalCreditLine){
		boolean hasError = false;
		if(appM.getPolicyExcDocNo() != null && !"".equals(appM.getPolicyExcDocNo())){
			BigDecimal exceptionCredit = appM.getExceptionCreditLine() == null? new BigDecimal(0):appM.getExceptionCreditLine();			
			if(finalCreditLine > exceptionCredit.doubleValue()){
				formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "FINAL_MORE_BOT5X"));
				hasError = true;
			}
		}else{
			double recommendCreditLine = appM.getRecommentCreditLine() == null? 0: appM.getRecommentCreditLine().doubleValue();
			if(finalCreditLine > recommendCreditLine){
				formHandler.PushErrorMessage("", ErrorUtil.getShortErrorMessage(request, "FINAL_MORE_RECOMMEND"));
				hasError = true;
			}
		}
		return hasError;
	}
}
