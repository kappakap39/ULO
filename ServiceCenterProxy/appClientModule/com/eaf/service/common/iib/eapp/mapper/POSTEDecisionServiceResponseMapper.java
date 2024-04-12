package com.eaf.service.common.iib.eapp.mapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.iib.model.ApplicationGroup;
import com.ava.flp.eapp.iib.model.ApplicationScores;
import com.ava.flp.eapp.iib.model.Applications;
import com.ava.flp.eapp.iib.model.BScores;
import com.ava.flp.eapp.iib.model.BolOrganizations;
import com.ava.flp.eapp.iib.model.Card;
import com.ava.flp.eapp.iib.model.CardLinkCards;
import com.ava.flp.eapp.iib.model.CardLinkCustomers;
import com.ava.flp.eapp.iib.model.CcaCampaign;
import com.ava.flp.eapp.iib.model.Doclists;
import com.ava.flp.eapp.iib.model.DocumentScenarios;
import com.ava.flp.eapp.iib.model.DocumentSuggestions;
import com.ava.flp.eapp.iib.model.FicoScore;
import com.ava.flp.eapp.iib.model.Guidelines;
import com.ava.flp.eapp.iib.model.IncomeSources;
import com.ava.flp.eapp.iib.model.KBankPayroll;
import com.ava.flp.eapp.iib.model.LoanPricings;
import com.ava.flp.eapp.iib.model.LoanTiers;
import com.ava.flp.eapp.iib.model.Loans;
import com.ava.flp.eapp.iib.model.NcbAccounts;
import com.ava.flp.eapp.iib.model.NcbAddress;
import com.ava.flp.eapp.iib.model.NcbIds;
import com.ava.flp.eapp.iib.model.NcbInfo;
import com.ava.flp.eapp.iib.model.NcbNames;
import com.ava.flp.eapp.iib.model.OrPolicyRules;
import com.ava.flp.eapp.iib.model.OrPolicyRulesDetails;
import com.ava.flp.eapp.iib.model.PersonalInfos;
import com.ava.flp.eapp.iib.model.PolicyRuleConditions;
import com.ava.flp.eapp.iib.model.PolicyRuleReasons;
import com.ava.flp.eapp.iib.model.PolicyRules;
import com.ava.flp.eapp.iib.model.VerificationResultApplications;
import com.ava.flp.eapp.iib.model.VerificationResultPersonalInfos;
import com.ava.flp.eapp.iib.model.WebVerifications;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.DecisionActionUtil;
import com.eaf.flp.eapp.util.EAppUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.FinalVerifyResultDataM.VerifyId;
import com.eaf.orig.ulo.model.app.BScoreDataM;
import com.eaf.orig.ulo.model.app.GuidelineDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.IncomeSourceDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.LoanPricingDataM;
import com.eaf.orig.ulo.model.app.LoanTierDataM;
import com.eaf.orig.ulo.model.app.NCBDocumentHistoryDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesConditionDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.PriorityPassVerification;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductCCADataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonLogDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;
import com.eaf.orig.ulo.model.ncb.NcbAccountDataM;
import com.eaf.orig.ulo.model.ncb.NcbAddressDataM;
import com.eaf.orig.ulo.model.ncb.NcbIdDataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;
import com.eaf.orig.ulo.model.ncb.NcbNameDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.util.DecisionServiceCacheControl;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

import decisionservice_iib.KBankHeader;

public class POSTEDecisionServiceResponseMapper extends
EDecisionServiceResponseMapper{
	private static transient Logger logger = Logger.getLogger(POSTEDecisionServiceResponseMapper.class);
	private KBankHeader  kbankHeader;	
	private static String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PRODUCT_K_PERSONAL_LOAN  = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	@Override
	public  void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		logger.debug("start applicationGroupMapper!!");
		uloApplicationMapper(uloApplicationGroup, iibApplicationGroup);
		personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
	}
	String BORROWER = ServiceCache.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String SUPPLEMENTARY = ServiceCache.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	@Override
	public  void uloApplicationMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getApplications() && iibApplicationGroup.getApplications().size()>0){
			for(Applications iibApplication : iibApplicationGroup.getApplications()){
				ApplicationDataM uloApplication = uloApplicationGroup.getApplicationById(iibApplication.getApplicationRecordId());
				if(null!=uloApplication){					
					mapApplication(uloApplicationGroup, uloApplication, iibApplication);
					loanMapper(uloApplicationGroup, uloApplication, iibApplication);
				}else{
					logger.debug("iib application is null!!");
				}
				
			}
		} 
	}
	@Override
	public  void loanMapper(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication, Applications iibApplication) throws Exception{
		if(null!=iibApplication.getLoans() && iibApplication.getLoans().size()>0){
			for(Loans iibLoan : iibApplication.getLoans()){
				LoanDataM uloLoan = uloApplication.getloanById(iibLoan.getLoanId());
				if(null==uloLoan){
					logger.debug("cant find ulo loan id >>"+iibLoan.getLoanId());
					continue;
				}
				
				//KPL Additional Add installment Date
				else if(iibLoan.getInstallmentDate() != null)
				{uloLoan.setFirstInstallmentDate(DecisionServiceUtil.toSqlDate(iibLoan.getInstallmentDate()));}
				
				uloLoan.setCoreBankProduct(iibLoan.getCbProduct());
				uloLoan.setCoreBankSubProduct(iibLoan.getCbSubProduct());
				uloLoan.setCoreBankMarketCode(iibLoan.getCbMarketCode());
				
					if(null!=iibLoan.getLoanPricings() && iibLoan.getLoanPricings().size()>0){
						loanPricingsMapper(uloLoan, iibLoan);
					}
				
					if(null!=iibLoan.getLoanTiers() && iibLoan.getLoanTiers().size()>0){
						loanTiersMapper(uloLoan, iibLoan);
					}
				CardDataM uloCard = uloLoan.getCard();
				if(null!=uloCard){
					mapCard(uloLoan, iibLoan,iibApplication,uloApplication.getApplicationType());
				}
			}
		}
	}
	
	@Override
	public void loanTiersMapper(LoanDataM uloLoan, Loans iibLoan)throws Exception{
//		try {
//			//set when decision is DE1,DV2,FI
			mapMainloanTiers(uloLoan, iibLoan);
//		} catch (Exception e) {
//			throw new Exception(e);
//		}
	}
	@Override
	public void loanPricingsMapper(LoanDataM uloLoan, Loans iibLoan )throws Exception{
//		try {
//			//set when decision is DE1,DV2
			mapMainloanPricings(uloLoan, iibLoan);
//		} catch (Exception e) {
//			throw new Exception(e);
//		}
	}
	@Override
	public  void mapMainloanPricings(LoanDataM uloLoan, Loans iibLoan )throws  Exception{
		//Prepare data
		List<LoanPricings> iibLoanPricngs = iibLoan.getLoanPricings();
		List<LoanPricingDataM> loanPricings = new ArrayList<LoanPricingDataM>();
		uloLoan.setLoanPricings(null);//Clear to always add new since there's no update criteria
			
		if(iibLoanPricngs == null || iibLoanPricngs.size() < 1){				
			return;
		}else{
			for(LoanPricings iibPricing : iibLoanPricngs){
				LoanPricingDataM uloPricing = new LoanPricingDataM();
				mapLoanPricing(uloPricing, iibPricing);
				loanPricings.add(uloPricing);
			}			
			//Start mapping
			uloLoan.setLoanPricings((ArrayList<LoanPricingDataM>) loanPricings);
		}	
	}
	@Override
	public  void mapMainloanTiers(LoanDataM uloLoan, Loans iibLoan)throws  Exception{
		if(null!=iibLoan.getLoanTiers() && iibLoan.getLoanTiers().size()>0){
			int seq=0;
			for(LoanTiers iibLoanTier : iibLoan.getLoanTiers()){
				LoanTierDataM uloLoanTier =DecisionServiceUtil.getLoanTierDataSeq(uloLoan.getLoanTiers(), seq);
				if(null==uloLoanTier){
					uloLoanTier = new LoanTierDataM();
					uloLoan.addLoanTiers(uloLoanTier);	
				}					
				mapLoanTier(uloLoanTier, iibLoanTier,seq);
				seq++;
			}
		}
	}
	@Override
	public  void personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getPersonalInfos() && iibApplicationGroup.getPersonalInfos().size()>0){
			for(PersonalInfos iibPersonalInfo : iibApplicationGroup.getPersonalInfos()){
				PersonalInfoDataM uloPersonalInfo = uloApplicationGroup.getPersonalById(iibPersonalInfo.getPersonalId());
				if(null==uloPersonalInfo){
					 logger.debug("Cant find personal id >>"+iibPersonalInfo.getPersonalId());
					 continue;
				}
				logger.debug("find ulo perosnal id >>"+iibPersonalInfo.getPersonalId());
				if(!ServiceUtil.empty(iibPersonalInfo.getApplicationScores())){
					mapApplicationScore(uloPersonalInfo,iibPersonalInfo.getApplicationScores().get(0));
				}else{
					logger.debug("ApplicationScore is null");
				}

				if(!ServiceUtil.empty(iibPersonalInfo.getBScores())){
					for(BScores  iibBScore : iibPersonalInfo.getBScores()){	
						bScoreMapper(uloPersonalInfo, iibBScore);
						
					}
				}
			}
		}
	}	
	@Override
	public  void mapApplication(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication,Applications iibApplication){
		if(iibApplication.getRecommendProjectCode() != null && !iibApplication.getRecommendProjectCode().isEmpty()){
			uloApplication.setProjectCode(iibApplication.getRecommendProjectCode());
		}	
	}
	
	@Override
	public void  mapPersonalApplication(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication,Applications iibApplication ) {
		PersonalRelationDataM uloPersonalRelation = uloApplicationGroup.getPersonalRelationByRefId(uloApplication.getApplicationRecordId()); 
		logger.debug("iibApplication.getPersonalId()>>"+iibApplication.getPersonalId());
		PersonalInfoDataM uloPersonal = uloApplicationGroup.getPersonalById(iibApplication.getPersonalId());
		if(null==uloPersonal){
			logger.debug("ULO PersonalInfo is null!!");
			return;
		}
		if(null==uloPersonalRelation){
			uloPersonalRelation = new PersonalRelationDataM();
			uloPersonalRelation.setPersonalId(iibApplication.getPersonalId());
			uloPersonalRelation.setRefId(uloApplication.getApplicationRecordId());
			uloPersonalRelation.setPersonalType(uloPersonal.getPersonalType());
			uloPersonal.getPersonalRelations().add(uloPersonalRelation);
		}else{
			uloPersonalRelation.setPersonalId(iibApplication.getPersonalId());
		}
	}
	@Override
	public  void mapApplicationScore(PersonalInfoDataM uloPersonalInfo,ApplicationScores iibAppScore) throws Exception{
		if(null!=iibAppScore){
			uloPersonalInfo.getVerificationResult().setRiskGradeCoarseCode(iibAppScore.getCoarseRiskGrade());
			uloPersonalInfo.getVerificationResult().setRiskGradeFineCode(iibAppScore.getFineRiskGrade());
			
		}
		
	}
	@Override
	public  void mapBolOrganization(PersonalInfoDataM uloPersonalInfo,BolOrganizations iibBolOrganization) throws Exception{
		uloPersonalInfo.setCompanyOrgIdBol(iibBolOrganization.getOrgId());
		uloPersonalInfo.setPhoneNoBol(iibBolOrganization.getTelNo());
	}
	
	@Override
	public  void mapLoan(LoanDataM uloLoan ,Loans iibLoan){
		uloLoan.setLoanId(iibLoan.getLoanId());
		uloLoan.setMaxCreditLimit(iibLoan.getMaxCreditLimit());
		uloLoan.setMaxCreditLimitBot(iibLoan.getMaxCreditLimitBot());
		uloLoan.setMinCreditLimit(iibLoan.getMinCreditLimit());
		uloLoan.setRecommendLoanAmt(iibLoan.getRecommendLoanAmt());
		uloLoan.setRecommendTerm(iibLoan.getRecommendTerm());
		//uloLoan.setMaxCreditLimitWithOutDBR(iibLoan.getMaxCreditLimitWithOutDBR());
		
		if(null==uloLoan.getLoanAmt() ||  BigDecimal.ZERO.compareTo(uloLoan.getLoanAmt())==0){
			uloLoan.setLoanAmt(iibLoan.getRecommendLoanAmt());
		}
		//uloLoan.setCurrentCreditLimit(iibLoan.getCurrentCreditLimit());
		//uloLoan.setRateType(iibLoan.getRateType());
		//uloLoan.setSignSpread(iibLoan.getSignSpread());
		uloLoan.setFinalCreditLimit(iibLoan.getNormalCreditLimit());
	}
	@Override
	public  void mapCard(LoanDataM uloLoan,Loans iibLoan,Applications iibApplicatin ,String uloApplicationType){	
		CardDataM uloCard = uloLoan.getCard();
		Card iibCard =   iibLoan.getCard();
		if(null!=iibCard){
			if(null!=iibCard.getPlasticCode() && !"".equals(iibCard.getPlasticCode())){
				uloCard.setPlasticCode(iibCard.getPlasticCode());
			}

			uloCard.setCardNo(iibCard.getCardNo());
//			uloCard.setCardNoEncrypted(iibCard.getCardNoEncrypted());
//			if(!ServiceUtil.empty(iibCard.getCardNoMark())){
//				uloCard.setCardNoMark(iibCard.getCardNoMark());
//			}
			if(null!=iibCard.getCardlinkCardCode() && !"".equals(iibCard.getCardlinkCardCode())){
				uloCard.setCardLinkCardCode(iibCard.getCardlinkCardCode());
			}
			
			if(null!=iibLoan.getLoanPricings() && iibLoan.getLoanPricings().size()>0){
				LoanPricings iibLoanPricing = new LoanPricings();
				iibLoanPricing = iibLoan.getLoanPricings().get(0);
				uloCard.setCardFee(iibLoanPricing.getFeeWaiveCode());
			}	
			String cardType = ProcessUtil.getCardType(uloApplicationType,uloCard, iibLoan,iibApplicatin, uloLoan.getRequestCoaProductCode());
			if(null!=cardType && !"".equals(cardType)){
				uloCard.setCardType(cardType);
				if(ProcessUtil.isEligibleCoaProductCode(iibLoan)){
					uloCard.setRecommendCardCode(cardType);
				}
			}
			
			String cardLevel = ProcessUtil.getCardTypeLevel(uloApplicationType, iibLoan,iibApplicatin);
			if(null!=cardLevel && !"".equals(cardLevel)){
				uloCard.setCardLevel(cardLevel);
			}
		}
	}
	@Override
	public  void mapLoanPricing(LoanPricingDataM uloLoanPricing,LoanPricings iibLoanPricing) {
		uloLoanPricing.setFeeWaiveCode(iibLoanPricing.getFeeWaiveCode());	
		uloLoanPricing.setFeeStartMonth(EAppUtil.bigDecimalToInt(iibLoanPricing.getFeeStartMonth()));
		uloLoanPricing.setFeeEndMonth(EAppUtil.bigDecimalToInt(iibLoanPricing.getFeeEndMonth()));
		uloLoanPricing.setTerminationFeeFlag(iibLoanPricing.getTerminationFeeFlag());
		uloLoanPricing.setTerminationFeeCode(iibLoanPricing.getTerminationFeeCode());
	}
	@Override
	public void mapReasonPolicyRule(ApplicationDataM uloApplication, Applications iibApplication){
		ArrayList<ReasonDataM> reasons = new ArrayList<ReasonDataM>();
		ArrayList<String> uloReasonsCodeApplication = uloApplication.getReasonCodes();
		VerificationResultApplications iibVer = iibApplication.getVerificationResult();
		if(null != iibVer){
			List<PolicyRules> iibPolicyRules = iibVer.getPolicyrules();
			ArrayList<ReasonLogDataM> reasonLogs = new ArrayList<ReasonLogDataM>();
			ArrayList<ReasonDataM> reasonsList = new ArrayList<ReasonDataM>();
			for(PolicyRules iibPolicyRule : iibPolicyRules){
				if(!ServiceUtil.empty(iibPolicyRule.getPolicyRuleReasons())){
					List<PolicyRuleReasons> iibPolicyRuleReasons = iibPolicyRule.getPolicyRuleReasons();
					for(PolicyRuleReasons iibPolicyRuleReason:iibPolicyRuleReasons){
						if(!ServiceUtil.empty(iibPolicyRuleReason) && !ServiceUtil.empty(iibPolicyRuleReason.getReasonCode()) && !uloReasonsCodeApplication.contains(iibPolicyRuleReason.getReasonCode())){
							ReasonDataM reason = new ReasonDataM();
							reason.setApplicationGroupId(uloApplication.getApplicationGroupId());
							reason.setApplicationRecordId(uloApplication.getApplicationRecordId());
							reason.setReasonCode(iibPolicyRuleReason.getReasonCode());
							reason.setReasonType(ServiceCache.getConstant("RECOMMEND_DECISION_REJECTED"));
							reasonsList.add(reason);
							
							ReasonLogDataM reasonLog = new ReasonLogDataM();
							reasonLog.setApplicationGroupId(uloApplication.getApplicationGroupId());
							reasonLog.setApplicationRecordId(uloApplication.getApplicationRecordId());
							reasonLog.setReasonCode(iibPolicyRuleReason.getReasonCode());
							reasonLog.setReasonType(ServiceCache.getConstant("RECOMMEND_DECISION_REJECTED"));
							reasonLogs.add(reasonLog);
						}
					}
				}
			}
			if(!ServiceUtil.empty(reasonLogs) && !ServiceUtil.empty(reasonsList)){
				uloApplication.setReasonLogs(reasonLogs);
				uloApplication.setReasons(reasonsList);
			}
		}
	}
	@Override
	public void bScoreMapper(PersonalInfoDataM  uloPersonalInfo, BScores iibBScore) throws Exception{
		if(iibBScore !=null){
			BScoreDataM uloBScore = new BScoreDataM();
			mapBScore(uloBScore, iibBScore);
			List<BScoreDataM> uloBScoreList = uloPersonalInfo.getBscores();
			if (uloBScoreList == null) {
				uloBScoreList = new ArrayList<BScoreDataM>();
				uloPersonalInfo.setBScores((ArrayList<BScoreDataM>) uloBScoreList);
			}
			uloBScoreList.add(uloBScore);
		}
	}
	@Override
	public  void mapBScore(BScoreDataM uloBScore, BScores iibBScore) throws Exception{
			uloBScore.setLpmId(iibBScore.getLpmId());
			uloBScore.setPostDate(DecisionServiceUtil.toSqlDate(iibBScore.getPostDate()));
			uloBScore.setProductType(iibBScore.getProductType());
			uloBScore.setRiskGrade(iibBScore.getRiskGrade());
	}
	
	public KBankHeader getKbankHeader() {
		return kbankHeader;
	}
	public void setKbankHeader(KBankHeader kbankHeader) {
		this.kbankHeader = kbankHeader;
	}

	private boolean processRequireVerify(List<PersonalInfoDataM> personals){
		if ((personals == null) || (personals.isEmpty())) {
			return false;
		}
		for (PersonalInfoDataM person : personals) {
			if (person != null) {
				VerificationResultDataM ver = person.getVerificationResult();
				if (ver != null) {
					if ("Y".equalsIgnoreCase(ver.getRequiredVerCustFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerHrFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerPrivilegeFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerWebFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerIncomeFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequirePriorityPass())) {
								return true;
					}
					
					if (!PersonalInfoDataM.PersonalType.INFO.equals(person.getPersonalType()) && 
							"N".equalsIgnoreCase(ver.getDocCompletedFlag())){
						return true;
					}
				}
			}
		}
		return false;
	}
}
