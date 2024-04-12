package com.eaf.orig.ulo.pl.app.utility;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.OverrideCapportDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

public class PLOrigApplicationUtil {
	private Logger logger = Logger.getLogger(PLOrigApplicationUtil.class);
	
	public String getCreditLineCapportNo(PLApplicationDataM applicationM) throws Exception{
		ExecuteServiceManager xRulesService = null;
		XrulesResponseDataM xrulesResponseM = null;
		String capportNo = "";
		try {
			xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM xrulesRequestM = new XrulesRequestDataM();
			xrulesRequestM.setServiceID(PLXrulesConstant.ModuleService.CREDIT_LINE_CAPPORT);
			xrulesRequestM.setPlAppM(applicationM);
			xrulesResponseM = xRulesService.ILOGServiceModule(xrulesRequestM);
		} catch (NamingException e1) {			 
			e1.printStackTrace();
			throw e1;
		}
		if(xrulesResponseM != null){
			if(OrigConstant.ORIG_RESULT_FAIL.equals(xrulesResponseM.getExecuteCode())){
				throw new Exception("Execute ILog CA Capport Error");
			}
			capportNo = (String)xrulesResponseM.getObj();
		}
//		logger.debug("@@@@@ capportNo from ILOG :"+ capportNo);
		return capportNo;
	}
	
	public CapportGroupDataM getCapportGroup(PLApplicationDataM applicationM) throws Exception{
		CapportGroupDataM capGroupM = null;
		try{
			String capportNo = this.getCreditLineCapportNo(applicationM); //get credit line capport number from ILog

			if(!OrigUtil.isEmptyString(capportNo)){
				try{
					capGroupM = PLORIGEJBService.getORIGDAOUtilLocal().getCapportGroupDetails(capportNo);
					if(capGroupM != null && !OrigUtil.isEmptyString(capGroupM.getCapportGroupId()) 
							&&(capGroupM.getCapportAmount()-capGroupM.getCapportUsed()) <= (capGroupM.getCapportAmount()*capGroupM.getPercentWorning()/100)){
						capGroupM.setAlertFlag(true);
					}else{
						capGroupM.setAlertFlag(false);
					}
				}catch (Exception e){
					logger.fatal("##### Cannot get capport group :" + e.getMessage());
					throw e;
				}
			}
//			#prisan if found capport group, set capport number to application model
			if(!OrigUtil.isEmptyString(capGroupM.getCapportGroupId())){
				applicationM.setCapPortNo(capportNo);
			}else{
				applicationM.setCapPortNo(null);
			}
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
		return capGroupM;
	}
	
	public OverrideCapportDataM getOverideCapport(String appRecordID){
		return PLORIGEJBService.getORIGDAOUtilLocal().getOverrideCapport(appRecordID);
	}
	
	public BigDecimal getUserLendingLimit(String userName, String busClass){
		return PLORIGEJBService.getORIGDAOUtilLocal().getUserLendingLimit(userName, busClass);
	}
	
	public PLProjectCodeDataM getProjectCodeDataM(String projectCode){
		return PLORIGEJBService.getORIGDAOUtilLocal().Loadtable(projectCode);
	}
	
	public Double getILogCAPoint(PLApplicationDataM applicationM) throws Exception{
		
		ExecuteServiceManager xRulesService = null;
		XrulesResponseDataM xrulesResponseM = null;
		double caPoint = 0;
		try {
			xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM xrulesRequestM = new XrulesRequestDataM();
			xrulesRequestM.setServiceID(PLXrulesConstant.ModuleService.CALCULATE_POINT);
			xrulesRequestM.setPlAppM(applicationM);
			xrulesResponseM = xRulesService.ILOGServiceModule(xrulesRequestM);
		} catch (NamingException e) {			 
			logger.fatal("ERROR ",e);
			throw e;
		}
		if(xrulesResponseM != null){
			if(OrigConstant.ORIG_RESULT_FAIL.equals(xrulesResponseM.getExecuteCode())){
				throw new Exception("Execute ILog CA Point Error");
			}
			caPoint = (Double)xrulesResponseM.getObj();
		}
		logger.debug("@@@@@ caPoint from ILOG :"+ caPoint);
		return caPoint;
	}
	
	public boolean isApproveApplication(PLApplicationDataM applicationM){
		if(OrigConstant.Action.APPROVE.equals(applicationM.getAppDecision()) ||
			OrigConstant.Action.APPROVE_SKIP_DF.equals(applicationM.getAppDecision()) ||
				OrigConstant.Action.OVERRIDE.equals(applicationM.getAppDecision()) ||
					OrigConstant.Action.OVERRIDE_SKIP_DF.equals(applicationM.getAppDecision()) ||
						OrigConstant.Action.POLICY_EXCEPTION.equals(applicationM.getAppDecision()) ||
							OrigConstant.Action.POLICY_EXCEPTION_SKIP_DF.equals(applicationM.getAppDecision())){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isRejectApplication(PLApplicationDataM applicationM){
		if(OrigConstant.Action.REJECT.equals(applicationM.getAppDecision()) ||
				OrigConstant.Action.REJECT_SKIP_DF.equals(applicationM.getAppDecision())){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isCancelApplication(PLApplicationDataM applicationM){
		if(OrigConstant.Action.CANCEL.equals(applicationM.getAppDecision()) ||
				OrigConstant.Action.CANCEL_AFTER_APPROVE.equals(applicationM.getAppDecision())){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * Final Application (approve, reject, cancel)
	 */
	public boolean isFinalApplication(PLApplicationDataM applicationM){
		if(OrigConstant.Action.APPROVE.equals(applicationM.getAppDecision()) ||
			OrigConstant.Action.APPROVE_SKIP_DF.equals(applicationM.getAppDecision()) ||
				OrigConstant.Action.OVERRIDE.equals(applicationM.getAppDecision()) ||
					OrigConstant.Action.OVERRIDE_SKIP_DF.equals(applicationM.getAppDecision()) ||
						OrigConstant.Action.POLICY_EXCEPTION.equals(applicationM.getAppDecision()) ||
							OrigConstant.Action.POLICY_EXCEPTION_SKIP_DF.equals(applicationM.getAppDecision()) ||
								OrigConstant.Action.REJECT.equals(applicationM.getAppDecision()) ||
									OrigConstant.Action.CANCEL.equals(applicationM.getAppDecision())){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * Stamp user and date of decision action
	 */
	public void stampUserActionAndDate(PLApplicationDataM applicationM, String userName){
		logger.debug("userName = " + userName);
		if (applicationM.getCreateBy() == null || "".equals(applicationM.getCreateBy())) {
			applicationM.setCreateBy(userName);
			applicationM.setCreateDate(DataFormatUtility.parseTimestamp(new Date()));
			applicationM.setUpdateBy(userName);
			applicationM.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));
		} else {
			applicationM.setUpdateBy(userName);
			applicationM.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));
		}
		if(!OrigConstant.Action.SAVE_DRAFT.equals(applicationM.getAppDecision())){
			if(isApproveApplication(applicationM)){
				applicationM.setApproveDate(DataFormatUtility.parseTimestamp(new Date()));
				applicationM.setApproveBy(userName);
			}else if(OrigConstant.Action.ESCALATE.equals(applicationM.getAppDecision())){
				applicationM.setEscalateBy(userName);
				applicationM.setEscalateDate(DataFormatUtility.parseTimestamp(new Date()));
			}else if(OrigConstant.Action.REJECT.equals(applicationM.getAppDecision())){
				applicationM.setRejectBy(userName);
				applicationM.setRejectDate(DataFormatUtility.parseTimestamp(new Date()));
			}else if(OrigConstant.Action.CANCEL.equals(applicationM.getAppDecision())){
				applicationM.setCancelBy(userName);
				applicationM.setCancelDate(DataFormatUtility.parseTimestamp(new Date()));
			}
		}
	}
	
	public double getCurrentCreditLine(PLApplicationDataM appM){
		double currentCreditLine = 0;
		PLPersonalInfoDataM applicantPersonM = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		if(applicantPersonM == null) applicantPersonM = new PLPersonalInfoDataM();
		PLCardDataM cardM = applicantPersonM.getCardInformation();
		if(cardM != null && cardM.getCurCreditLine()!= null){
			currentCreditLine = cardM.getCurCreditLine().doubleValue();
		}
		return currentCreditLine;
	}
	
	public boolean foundORExceptFinalCreditLine(Vector<PLXRulesPolicyRulesDataM> policyRuleVt){
		boolean result = false;
		if(policyRuleVt != null && policyRuleVt.size() > 0){
			for(int i=0;i<policyRuleVt.size();i++){
				PLXRulesPolicyRulesDataM policyM = (PLXRulesPolicyRulesDataM)policyRuleVt.get(i);
				if(PLXrulesConstant.ResultCode.CODE_OVERRIDE.equals(policyM.getResultCode()) &&
						!OrigConstant.POLICY_RULE_ID_CREDIT_LINE.equals(policyM.getPolicyRulesId())){
					logger.debug("policy_id:" +policyM.getPolicyRulesId() +" found " + policyM.getResultCode());
					return true;
				}
			}
		}
		return result;
	}
	
	public void removeORFinalCreditLine(PLApplicationDataM appM){
		PLPersonalInfoDataM applicantPerson = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		if(applicantPerson == null){
			applicantPerson = new PLPersonalInfoDataM();
			applicantPerson.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
			appM.getPersonalInfoVect().add(applicantPerson);
		}
		PLXRulesVerificationResultDataM verResult = applicantPerson.getXrulesVerification();
		if(verResult == null){
			verResult = new PLXRulesVerificationResultDataM();
			applicantPerson.setXrulesVerification(verResult);
		}
		if(OrigConstant.SummaryOverideRuleCode.OVERRIDE.equals(verResult.getSummaryOverideRuleCode())){
			if(!this.foundORExceptFinalCreditLine(verResult.getvXRulesPolicyRulesDataM())){
				appM.setJobType(OrigConstant.typeColor.typeGreen);
				verResult.setSummaryOverideRuleCode(OrigConstant.SummaryOverideRuleCode.PASS);
				verResult.setSummaryOverideRuleResult(OrigConstant.SummaryOverideRuleResult.PASS);
				verResult.setRecommendResult(OrigConstant.recommendResult.ACCEPT);
			}
			verResult.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CREDIT_LINE);
		}
	}
	
	public BigDecimal getMinFinalCreditLine(PLApplicationDataM appM){
		String minValue = ORIGCacheUtil.getInstance().getGeneralParamValue(OrigConstant.GeneralParamCode.MIN_FINAL_CREDIT_LINE, appM.getBusinessClassId());
		if(minValue != null && !"".equals(minValue)){
			return new BigDecimal(Double.parseDouble(minValue));
		}else{
			return new BigDecimal(1000); // return default
		}
	}
}
