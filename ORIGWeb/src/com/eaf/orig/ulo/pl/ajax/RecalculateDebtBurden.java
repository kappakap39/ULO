package com.eaf.orig.ulo.pl.ajax;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
import com.eaf.orig.ulo.pl.app.view.form.subform.CADecisionSubForm;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.IncomeDebtDataM;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesKECOverideResultDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

public class RecalculateDebtBurden implements AjaxDisplayGenerateInf {

	private Logger logger = Logger.getLogger(this.getClass());
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf#getDisplayObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String getDisplayObject(HttpServletRequest request)
			throws PLOrigApplicationException {
		JsonObjectUtil _JsonObjectUtil = new JsonObjectUtil();
		PLOrigFormHandler plOrigForm = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		
		CADecisionSubForm caDecision = new CADecisionSubForm();
		caDecision.setProperties(request, plOrigForm);
		
		PLApplicationDataM applicationM = plOrigForm.getAppForm();
		logger.debug("@@@@@ application no:"+applicationM.getApplicationNo());
		logger.debug("@@@@@ final credit line:"+applicationM.getFinalCreditLimit());
		
		PLPersonalInfoDataM applicantPerson = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		if(applicantPerson == null){
			applicantPerson = new PLPersonalInfoDataM();
			applicantPerson.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
			applicationM.getPersonalInfoVect().add(applicantPerson);
		}
		PLXRulesVerificationResultDataM verResult = applicantPerson.getXrulesVerification();
		if(verResult == null){
			verResult = new PLXRulesVerificationResultDataM();
			applicantPerson.setXrulesVerification(verResult);
		}
		//find model PLXRulesDebtBdDataM
		PLXRulesDebtBdDataM debtBDM = verResult.getXRulesDebtBdDataM();
		if(debtBDM == null){ 
			debtBDM = new PLXRulesDebtBdDataM();
			verResult.setXRulesDebtBdDataM(debtBDM);
		}
		
		//find information for calculate %debtBD
		double finalCreditLine = applicationM.getFinalCreditLimit().doubleValue();

		debtBDM.setIncludeKEC(true);
		ExecuteServiceManager xRulesService = null;
		XrulesResponseDataM xrulesResponseM = null;
		XrulesRequestDataM xrulesRequestM = new XrulesRequestDataM();
		xrulesRequestM.setPlAppM(applicationM);
		try {
			xrulesRequestM.setServiceID(PLXrulesConstant.ModuleService.INCOME_DEBT);
			xRulesService = ORIGEJBService.getExecuteServiceManager();
			xrulesResponseM = xRulesService.ILOGServiceModule(xrulesRequestM);
		} catch (NamingException e1) {			 
			e1.printStackTrace();
		}
		if(xrulesResponseM != null){
			IncomeDebtDataM incomeDebtM = (IncomeDebtDataM)xrulesResponseM.getObj();
			verResult.setDEBT_BDResult(incomeDebtM.getDebtBurdenResult());
			verResult.setDebtBdCode(incomeDebtM.getDebtBurdenCode());
			debtBDM.setDebtBurdentScoreAdjust(incomeDebtM.getDebtBurdentScore());
			_JsonObjectUtil.CreateJsonObject("ca_debt_burden", incomeDebtM.getDebtBurdenResult());
		}else{
			_JsonObjectUtil.CreateJsonObject("ca_debt_burden", OrigConstant.FAIL);
		}
		
		xrulesResponseM = null;
		try {
			xRulesService = ORIGEJBService.getExecuteServiceManager();
			xrulesResponseM = xRulesService.ExecuteKECOverideRules(xrulesRequestM);
		} catch (NamingException e1) {			 
			e1.printStackTrace();
		}
		if(xrulesResponseM != null){			
			PLXRulesKECOverideResultDataM kecOrResult = (PLXRulesKECOverideResultDataM)xrulesResponseM.getObj();
			if(null != kecOrResult){
				
				applicationM.setJobType(kecOrResult.getColor());
				verResult.setSummaryOverideRuleCode(kecOrResult.getSummaryOrResultCode());
				verResult.setSummaryOverideRuleResult(kecOrResult.getSummaryOrResult());
				verResult.setRecommendResult(kecOrResult.getRecommendResultCode());
				
				//calculate final credit line with recommend credit line
				this.calFinalCreditLineLogic(applicationM, request, verResult);
				logger.debug("@@applicationM.getJobType():" + applicationM.getJobType());
				logger.debug("@@verResult.getSummaryOverideRuleCode():" + verResult.getSummaryOverideRuleCode());
				logger.debug("@@verResult.getRecommendResult():" + verResult.getRecommendResult());
				
				_JsonObjectUtil.CreateJsonObject("ca_overriderule", ORIGCacheUtil.getInstance().getORIGCacheDisplayNameDataM(
								OrigConstant.fieldId.RESULT_XRULES, verResult.getSummaryOverideRuleCode()));
				_JsonObjectUtil.CreateJsonObject("ca_recommend_result", ORIGCacheUtil.getInstance().getORIGCacheDisplayNameDataM(
								OrigConstant.fieldId.RECOMMEND_RESULT, verResult.getRecommendResult()));
				if(OrigConstant.recommendResult.REJECT.equals(verResult.getRecommendResult())){
					_JsonObjectUtil.CreateJsonObject("ca_final_credit", "0.00");
				}
			}
			
		}else{
			_JsonObjectUtil.CreateJsonObject("ca_recommend_result", OrigConstant.FAIL);
			_JsonObjectUtil.CreateJsonObject("ca_policy_rule", OrigConstant.FAIL);
		}
	
		
		//check cash transfer
		String cashDayOnePercent = request.getParameter("cash_day1_percent");
		String displayMode = request.getParameter("display_mode");
		logger.debug("@@@@@ cashDayOnePercent:" + cashDayOnePercent);
		logger.debug("@@@@@ displayMode:" + displayMode);
		BigDecimal transferAmount = null;
		if(cashDayOnePercent != null && !"".equals(cashDayOnePercent)){
			try{
				double cashPercent = Double.parseDouble(cashDayOnePercent);
				logger.debug("@@@@@ cashPercent:" + cashPercent);
				double amount = (cashPercent/100)*finalCreditLine;
				logger.debug("@@@@@ amount:" + amount);
				transferAmount = new BigDecimal(amount);
				logger.debug("@@@@@ transferAmount1:" + transferAmount.doubleValue());
			}catch(Exception e){
				transferAmount = new BigDecimal(finalCreditLine);
				logger.debug("@@@@@ transferAmount2:" + transferAmount.doubleValue());
			}
			//set object to display on screen
			try{
				_JsonObjectUtil.CreateJsonObject("cash_day1_first_tranfer", DataFormatUtility.displayCommaNumber(transferAmount));
			}catch (Exception e){
				logger.error("##### cannot display comma number for cash_day1_first_tranfer");
				_JsonObjectUtil.CreateJsonObject("cash_day1_first_tranfer", "");
			}
		}

		request.getSession().setAttribute(PLOrigFormHandler.PLORIGForm, plOrigForm);
		return _JsonObjectUtil.returnJson();
	}
	

	private void calFinalCreditLineLogic(PLApplicationDataM applicationM, HttpServletRequest request, PLXRulesVerificationResultDataM resultDisplay){
		PLPersonalInfoDataM applicantPerson = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		if(applicantPerson == null){
			applicantPerson = new PLPersonalInfoDataM();
			applicantPerson.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
			applicationM.getPersonalInfoVect().add(applicantPerson);
		}
		PLXRulesVerificationResultDataM verResult = applicantPerson.getXrulesVerification();
		if(verResult == null){
			verResult = new PLXRulesVerificationResultDataM();
			applicantPerson.setXrulesVerification(verResult);
		}
		
		double finalCreditLine = 0;
		double recommendCredit = 0;
		if(applicationM.getFinalCreditLimit() != null) finalCreditLine = applicationM.getFinalCreditLimit().doubleValue();
		if(applicationM.getRecommentCreditLine() != null) recommendCredit = applicationM.getRecommentCreditLine().doubleValue();
		logger.debug("finalCreditLine:" + finalCreditLine);
		logger.debug("recommendCredit:" + recommendCredit);
		logger.debug("verResult.getSummaryOverideRuleCode():" + verResult.getSummaryOverideRuleCode());
		logger.debug("applicationM.getJobType():" + applicationM.getJobType());
		if(finalCreditLine > recommendCredit && !OrigConstant.SummaryOverideRuleCode.FAIL.equals(verResult.getSummaryOverideRuleCode())){
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			if (null == userM) {
				userM = new UserDetailM();
			}
			
			//Change application to OR application
			logger.debug("Chaneg to OR application");
			applicationM.setJobType(OrigConstant.typeColor.typeYellow);
			verResult.setSummaryOverideRuleCode(OrigConstant.SummaryOverideRuleCode.OVERRIDE);
			verResult.setSummaryOverideRuleResult(OrigConstant.SummaryOverideRuleResult.OVERRIDE);
			verResult.setRecommendResult(OrigConstant.recommendResult.OVERIDE);
			
//			//Change result to display screen
//			resultDisplay.setSummaryOverideRuleCode(OrigConstant.SummaryOverideRuleCode.OVERRIDE);
//			resultDisplay.setRecommendResult(OrigConstant.recommendResult.OVERIDE);
			
			//Create model for add 41(Final Credit line) to xrules policy rule
			PLXRulesPolicyRulesDataM creditLineRule = new PLXRulesPolicyRulesDataM();			
			creditLineRule.setPersonalID(applicantPerson.getPersonalID());
			creditLineRule.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_CREDIT_LINE);
			creditLineRule.setResultCode(PLXrulesConstant.ResultCode.CODE_OVERRIDE);
			creditLineRule.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));				
			creditLineRule.setUpdateBy(userM.getUserName());
			creditLineRule.setResultDesc(PLXrulesConstant.ResultDesc.CODE_OVERRIDE_DESC);
			
			//Add 41(Final Credit line) to xrules policy rule
			logger.debug("Add plicy final credit line to verification");
			verResult.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CREDIT_LINE, creditLineRule);
		}else{
			PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
			if(!appUtil.foundORExceptFinalCreditLine(verResult.getvXRulesPolicyRulesDataM())){
				if(OrigConstant.typeColor.typeYellow.equals(applicationM.getJobType())){
					//Change application to Normal application
					logger.debug("Chaneg to Normal application");
					applicationM.setJobType(OrigConstant.typeColor.typeGreen);
					verResult.setSummaryOverideRuleCode(OrigConstant.SummaryOverideRuleCode.PASS);
					verResult.setSummaryOverideRuleResult(OrigConstant.SummaryOverideRuleResult.PASS);
					verResult.setRecommendResult(OrigConstant.recommendResult.ACCEPT);
				}
				
//				//Change result to display screen
//				resultDisplay.setSummaryOverideRuleCode(OrigConstant.SummaryOverideRuleCode.PASS);
//				resultDisplay.setRecommendResult(OrigConstant.recommendResult.ACCEPT);
				
				//Remove 41(Final Credit line) from xrules policy rule
				logger.debug("Remove plicy final credit line from verification");
				verResult.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CREDIT_LINE);
			}else{
				logger.debug("Only remove plicy final credit line from verification");
				verResult.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CREDIT_LINE);
			}
		}
	}
}
