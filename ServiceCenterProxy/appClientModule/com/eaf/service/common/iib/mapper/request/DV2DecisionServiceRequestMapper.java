package com.eaf.service.common.iib.mapper.request;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesConditionDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;

import decisionservice_iib.PolicyRuleReasonDataM;

public class DV2DecisionServiceRequestMapper extends DecisionServiceRequestMapper {	
	private static transient Logger logger = Logger.getLogger(DV2DecisionServiceRequestMapper.class);
	String RECOMMEND_DECISION_CANCEL = ServiceCache.getConstant("RECOMMEND_DECISION_CANCEL");
	@Override
	public decisionservice_iib.ApplicationGroupDataM getDecsionServiceMapper(ApplicationGroupDataM uloApplicationGroup,String decisionPoint)throws Exception {
		if (null==uloApplicationGroup) {
			String msg = "uloApplicationGroup is null!";
			throw new Exception(msg);
		}else{
			decisionservice_iib.ApplicationGroupDataM iibApplicationGroup = new decisionservice_iib.ApplicationGroupDataM();
			mapApplicationGroup(decisionPoint, uloApplicationGroup, iibApplicationGroup);
			applicationMapper(uloApplicationGroup, iibApplicationGroup);
			personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
			
			if(null!=uloApplicationGroup.getReferencePersons()){
				for(ReferencePersonDataM uloRefPerson : uloApplicationGroup.getReferencePersons()){
					decisionservice_iib.ReferencePersonDataM iibRefPersonal = new decisionservice_iib.ReferencePersonDataM();
					mapReferencePerson(uloRefPerson, iibRefPersonal);
					iibApplicationGroup.getReferencePersons().add(iibRefPersonal);
				}
			}		
			return iibApplicationGroup;
		}
	}
	
	@Override
	public void personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
		ArrayList<PersonalInfoDataM> uloPersonalInfos = uloApplicationGroup.getPersonalInfos();
		if (null==uloPersonalInfos) {
			String msg = "uloPersonal is null!";
			throw new Exception(msg);
		}else{
			for(PersonalInfoDataM uloPersonalInfo :  uloPersonalInfos){
				decisionservice_iib.PersonalInfoDataM iibPersonalInfo = new decisionservice_iib.PersonalInfoDataM();
				iibApplicationGroup.getPersonalInfos().add(iibPersonalInfo);
			
				mapPersonalInfo(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
				addressMapper(uloPersonalInfo, iibPersonalInfo);
				debtInfoMapper(uloPersonalInfo, iibPersonalInfo);
				ncbMapper(uloPersonalInfo, iibPersonalInfo);
				incomeMapper(uloPersonalInfo, iibPersonalInfo);
				allIncomeBundlingMapper(uloPersonalInfo, uloApplicationGroup, iibPersonalInfo);
				imageSplitMapper(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
							
				decisionservice_iib.VerificationResultDataM iibVerResult = new decisionservice_iib.VerificationResultDataM();
				iibPersonalInfo.setVerificationResult(iibVerResult);
				if(null!=uloPersonalInfo.getVerificationResult()){					
					verificationMapper(uloPersonalInfo, iibVerResult);
				}	
				incomeSourceMapper(uloPersonalInfo, iibVerResult);
			}
		}
	}
	
	@Override
	public void loanMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication)throws Exception {
		if (null==uloApplication.getLoans()) {
			String msg = "uloLoans is null!";
			logger.debug(msg);
		}else{
			for(LoanDataM uloLoan : uloApplication.getLoans()){
				logger.debug("uloLoan loanID: "+uloLoan.getLoanId());
				decisionservice_iib.LoanDataM iibLoan = new decisionservice_iib.LoanDataM();
				
				//KPL Additional Add handle for diffReq calculate button
				if(!Util.empty(uloApplication.getDiffRequestFlag()))
				{mapLoanCalcDiff(uloLoan, iibLoan);}
				else
				{mapLoan(uloLoan, iibLoan);}
				
				iibLoan.setCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				//add coa product code to ulo loan for compare set card type
				uloLoan.setRequestCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				iibApplication.getLoans().add(iibLoan);

				if(null!=uloLoan.getSpecialAdditionalServiceIds() && uloLoan.getSpecialAdditionalServiceIds().size()>0){
					iibLoan.getSpecialAdditionalServiceIds().addAll(uloLoan.getSpecialAdditionalServiceIds());
				}
				
				if(null!=uloLoan.getPaymentMethodId()){
					PaymentMethodDataM uloPaymentMethod = uloApplicationGroup.getPaymentMethodById(uloLoan.getPaymentMethodId());
					decisionservice_iib.PaymentMethodDataM  iibPaymentMethod = new decisionservice_iib.PaymentMethodDataM();
					mapPaymentMethod(uloPaymentMethod, iibPaymentMethod);
					iibLoan.getPaymentMethods().add(iibPaymentMethod);
				}
				logger.debug("uloLoan before mapCard: "+uloLoan.getLoanId());
				decisionservice_iib.CardDataM iibCard = new decisionservice_iib.CardDataM();
				if(uloLoan.getCard() != null)
				{mapCard(uloLoan.getCard(), iibCard);}
				logger.debug("iibLoan setCard: "+iibCard.getCardId());
				iibLoan.setCard(iibCard);
				
				cashTransferMapper(uloLoan,iibLoan);
			}
		}
	}
	
	@Override
	public void mapPersonalVerificationResult(VerificationResultDataM uloVerification,decisionservice_iib.VerificationResultDataM iibVerResult) {
		super.mapPersonalVerificationResult(uloVerification, iibVerResult);
		super.privilegeProjectCodeMapper(uloVerification, iibVerResult);
	}
	
	@Override
	public void mapPersonalInfo(ApplicationGroupDataM uloApplicationGroup,PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo) {
		super.mapPersonalInfo(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
		uloPersonalInfo.setSorceOfIncome(iibPersonalInfo.getSorceOfIncome());
	}
	
	public void  policyRulesMapper(ArrayList<PolicyRulesDataM> uloPolicyRulesList,decisionservice_iib.VerificationResultDataM iibVerificationResult) {
		if(null!=uloPolicyRulesList && uloPolicyRulesList.size()>0){
			ArrayList<String> policyCodes = new ArrayList<String>();
			for(PolicyRulesDataM uloPolicyRules : uloPolicyRulesList){
				String policyCode = uloPolicyRules.getPolicyCode();
				decisionservice_iib.PolicyRulesDataM iibPolicyRule = new decisionservice_iib.PolicyRulesDataM();
				if(!ServiceCache.getConstant("POLICY_CODE_IGNOR_DV2").equals(policyCode)){
					if(!policyCodes.contains(policyCode)){
						iibPolicyRule=ProcessUtil.getDecisionServicePolicyRule(iibVerificationResult.getPolicyRules(), policyCode);	
						if(null==iibPolicyRule){
							iibPolicyRule = new decisionservice_iib.PolicyRulesDataM();
							iibVerificationResult.getPolicyRules().add(iibPolicyRule);
						}
					}else{
						iibVerificationResult.getPolicyRules().add(iibPolicyRule);
					}						
					mapPolicyRules(uloPolicyRules, iibPolicyRule);
					if(null!=uloPolicyRules.getOrPolicyRules()){
					   for(ORPolicyRulesDataM uloORPolicyRules : uloPolicyRules.getOrPolicyRules()){
						   decisionservice_iib.OrPolicyRulesDataM iibOrPolicyRules = new decisionservice_iib.OrPolicyRulesDataM();						
						   mapORPolicyRules(uloORPolicyRules, iibOrPolicyRules);
						   iibPolicyRule.getOrPolicyRules().add(iibOrPolicyRules);
					   }
				   	}
					if(null!=uloPolicyRules.getPolicyRulesConditions()){
						for(PolicyRulesConditionDataM uloPolicyRuleCondition : uloPolicyRules.getPolicyRulesConditions()){
						   decisionservice_iib.PolicyRuleConditionDataM iibPolicyRuleCondition = new decisionservice_iib.PolicyRuleConditionDataM();
						   mapPolicyRulesCondition(uloPolicyRuleCondition, iibPolicyRuleCondition);
						   iibPolicyRule.getPolicyRuleConditions().add(iibPolicyRuleCondition);
					   }
					}
					if(null!=uloPolicyRules.getReason()){
						decisionservice_iib.PolicyRuleReasonDataM iibPolicyRuleReason = new PolicyRuleReasonDataM();
						iibPolicyRuleReason.setReasonCode(uloPolicyRules.getReason());
						iibPolicyRuleReason.setReasonRank(String.valueOf(uloPolicyRules.getRank()));
						iibPolicyRule.getPolicyRuleReasons().add(iibPolicyRuleReason);
					}
					// add for check existing policy
					policyCodes.add(policyCode);
				}
			}
		}
	}
}
