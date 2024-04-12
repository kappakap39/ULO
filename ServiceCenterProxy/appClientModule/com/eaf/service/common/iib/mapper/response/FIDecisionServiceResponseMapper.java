package com.eaf.service.common.iib.mapper.response;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

import decisionservice_iib.CapportLoanDataM;

public class FIDecisionServiceResponseMapper extends DecisionServiceResponseMapper {
	private static transient Logger logger = Logger.getLogger(FIDecisionServiceResponseMapper.class);
	String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public  void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) throws Exception{
		uloApplicationMapper(uloApplicationGroup, iibApplicationGroup);
		personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
		mapApplicationGroup(uloApplicationGroup, iibApplicationGroup);
		mapThaiBevPartner(uloApplicationGroup, iibApplicationGroup);
		priorityPassMapper(uloApplicationGroup, iibApplicationGroup);
		
		//Mapping null application
		ArrayList<PersonalInfoDataM> personalInfos = uloApplicationGroup.getUsingPersonalInfo();
		for(PersonalInfoDataM personalInfo : personalInfos){
			if(!ServiceUtil.empty(personalInfo)){
				ArrayList<ApplicationDataM> applications = uloApplicationGroup.getApplications(personalInfo.getPersonalId(),personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
				if(ServiceUtil.empty(applications)){
					mapApplicationCaseNoApplication(iibApplicationGroup, uloApplicationGroup,personalInfo.getPersonalId());
				}
			}
		}
	}
	
	@Override
	public void personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
		if(null!=iibApplicationGroup.getPersonalInfos() && iibApplicationGroup.getPersonalInfos().size()>0){
			for(decisionservice_iib.PersonalInfoDataM iibPersonalInfo : iibApplicationGroup.getPersonalInfos()){
				PersonalInfoDataM uloPersonalInfo = uloApplicationGroup.getPersonalById(iibPersonalInfo.getPersonalId());
				if(null==uloPersonalInfo){
					logger.debug("Cant find ulo personal id>>"+iibPersonalInfo.getPersonalId());
					continue;
				}
													
				if(null!=iibPersonalInfo.getApplicationScores()&& iibPersonalInfo.getApplicationScores().size()>0){
					for(decisionservice_iib.ApplicationScoreDataM iibAppScore : iibPersonalInfo.getApplicationScores()){
						mapApplicationScore(uloPersonalInfo,iibAppScore);
					}
				}
					
				if(null!=iibPersonalInfo.getCardlinkCustomers()&&  iibPersonalInfo.getCardlinkCustomers().size()>0){
					for(decisionservice_iib.CardlinkCustomer   iibCardLinkCust : iibPersonalInfo.getCardlinkCustomers()){	
						cardlinkCustomerMapper(uloPersonalInfo, iibCardLinkCust);
					}
				}
				
				if(null!=iibPersonalInfo.getCardlinkCards() &&  iibPersonalInfo.getCardlinkCards().size()>0){
					for(decisionservice_iib.CardlinkCard iibCardLinkCard : iibPersonalInfo.getCardlinkCards()){
						cardlinkCardMapper(uloPersonalInfo, iibCardLinkCard);
					}						
				}
				
				decisionservice_iib.ApplicationDataM  iibApplication = ProcessUtil.filterIIBPersonalApplication(iibApplicationGroup.getApplications(), uloPersonalInfo.getPersonalId());
				if(null!=iibApplication){
					privilegeProjectCodeMapper(uloPersonalInfo, iibApplication);
				}	
				
				mapKBankInfo(uloPersonalInfo, iibPersonalInfo);
			}
		}else{
			logger.debug("iib personal is null!!");
		}
	}
	@Override
	public void mapApplication(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication) {

		uloApplication.setApplicationRecordId(iibApplication.getApplicationRecordId());
		uloApplication.setApplicationType(ProcessUtil.setApplyType(iibApplication.getApplyType()));
		uloApplication.setIsVetoEligibleFlag(iibApplication.getIsVetoEligibleFlag());		
		uloApplication.setPolicyProgramId(iibApplication.getPolicyProgramId());
		//#CR-ADD
		if(iibApplication.getRecommendProjectCode() != null && !iibApplication.getRecommendProjectCode().isEmpty()){
			uloApplication.setProjectCode(iibApplication.getRecommendProjectCode());
		}
		
		if(null!=iibApplication.getReferCriteria() && iibApplication.getReferCriteria().size()>0){
			uloApplication.setReferCriteria(ProcessUtil.mapReferCriterias(iibApplication.getReferCriteria()));
		}
		

		String recommendDecision = ProcessUtil.getULORecommendDecision(iibApplication.getRecommendDecision());
		logger.debug("recommendDecision>>"+recommendDecision);
		logger.debug("uloApplicationGroup.getCallEscalateFlag()>>"+uloApplicationGroup.getCalledEscalateFlag());
		if(DecisionServiceUtil.FLAG_N.equals(uloApplicationGroup.getCalledEscalateFlag())){
			uloApplication.setRecommendDecision(recommendDecision);
		}
		
		if (ProcessUtil.REC_APP_DECISION_APPROVE.equalsIgnoreCase(recommendDecision)) {
			uloApplication.setFinalAppDecision(ProcessUtil.FINAL_APP_DECISION_APPROVE);
			uloApplication.setFinalAppDecisionDate(DecisionServiceUtil.getDate());
			uloApplication.setFinalAppDecisionBy(uloApplicationGroup.getUserId());
		} else if (ProcessUtil.REC_APP_DECISION_REJECT.equalsIgnoreCase(recommendDecision)) {
			uloApplication.setFinalAppDecision(ProcessUtil.FINAL_APP_DECISION_REJECT);
			uloApplication.setFinalAppDecisionDate(DecisionServiceUtil.getDate());
			uloApplication.setFinalAppDecisionBy(uloApplicationGroup.getUserId());
			mapReasonPolicyRule(uloApplication,iibApplication);
		}
		if(null!=iibApplication.getReExecuteKeyProgramFlag() && !"".equals(iibApplication.getReExecuteKeyProgramFlag())){
			uloApplication.setReExecuteKeyProgramFlag(iibApplication.getReExecuteKeyProgramFlag());
		}	
	}
	
	@Override
	public void mapLoan(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan) {
		if(null!=iibLoan){
			uloLoan.setLoanId(iibLoan.getLoanId());
			//new loan
			if(null!=iibLoan.getProductFactor() && !"".equals(iibLoan.getProductFactor())){
				uloLoan.setProductFactor(iibLoan.getProductFactor());
			}
			if(null!=iibLoan.getDebtAmount() && !"".equals(iibLoan.getDebtAmount())){
				uloLoan.setDebtAmount(iibLoan.getDebtAmount());
			}
			if(null!=iibLoan.getDebtBurdenCreditLimit() && !"".equals(iibLoan.getDebtBurdenCreditLimit())){
				uloLoan.setDebtBurdenCreditLimit(iibLoan.getDebtBurdenCreditLimit());
			}
			if(null!=iibLoan.getDebtRecommend() && !"".equals(iibLoan.getDebtRecommend())){
				uloLoan.setDebtRecommend(iibLoan.getDebtRecommend());
			}
			if(null!=iibLoan.getDebtBurden() && !"".equals(iibLoan.getDebtBurden())){
				uloLoan.setDebtBurden(iibLoan.getDebtBurden());
			}
			
			//KPL Additional
			CapportLoanDataM iibCapport = iibLoan.getCapport();
			if(iibCapport != null)
			{
				uloLoan.setApplicationCapPortName(iibCapport.getApplicationCapportId());
				uloLoan.setAmountCapPortName(iibCapport.getAmountCapportId());
			}
			
			uloLoan.setCoreBankProduct(iibLoan.getCbProduct());
			uloLoan.setCoreBankSubProduct(iibLoan.getCbSubProduct());
			uloLoan.setCoreBankMarketCode(iibLoan.getCbMarketCode());
		}
		
	}
	
	@Override
	public void mapCard(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan,decisionservice_iib.ApplicationDataM iibApplication, String uloApplicationType){
		CardDataM uloCard = uloLoan.getCard();
		decisionservice_iib.CardDataM iibCard =   iibLoan.getCard();
		if(null!=iibCard){
			uloCard.setCardLinkCardCode(iibCard.getCardlinkCardCode());
			uloCard.setPlasticCode(iibCard.getPlasticCode());
			String cardType = ProcessUtil.getCardType(uloApplicationType,uloCard, iibLoan,iibApplication, uloLoan.getRequestCoaProductCode());
			if(null!=cardType && !"".equals(cardType)){
				uloCard.setCardType(cardType);
				if(ProcessUtil.isEligibleCoaProductCode(iibLoan)){
					uloCard.setRecommendCardCode(cardType);
				}
			}	
			String cardLevel = ProcessUtil.getCardTypeLevel(uloApplicationType,iibLoan,iibApplication);
			if(null!=cardLevel && !"".equals(cardLevel)){
				uloCard.setCardLevel(cardLevel);
			}
			
			if(null!=iibLoan.getLoanPricings() && iibLoan.getLoanPricings().size()>0){
				decisionservice_iib.LoanPricingDataM iibLoanPricing = new decisionservice_iib.LoanPricingDataM();
				iibLoanPricing = iibLoan.getLoanPricings().get(0);
				uloCard.setCardFee(iibLoanPricing.getFeeWaiveCode());
			}		
			if(null!=iibCard.getPlasticCode() && !"".equals(iibCard.getPlasticCode())){
				uloCard.setPlasticCode(iibCard.getPlasticCode());
			}
		}else{
			logger.debug("iib card is null!!");
		}
	}
	
	//Defect#3443 - remove wf to ULO loanTiers mapping 
/*	public void loanTiersMapper(LoanDataM uloLoan, decisionservice_iib.LoanDataM iibLoan)throws Exception{
		super.mapMainloanTiers(uloLoan, iibLoan);
	}*/
	
	public void loanPricingsMapper(LoanDataM uloLoan, decisionservice_iib.LoanDataM iibLoan )throws Exception{
		super.mapMainloanPricings(uloLoan, iibLoan);
	}
}
