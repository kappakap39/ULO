package com.eaf.service.common.iib.mapper.request;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesConditionDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

import decisionservice_iib.PolicyRuleReasonDataM;

public class IA1DecisionServiceRequestMapper extends DecisionServiceRequestMapper {
	private static transient Logger logger = Logger.getLogger(IA1DecisionServiceRequestMapper.class);
	String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String RECOMMEND_DECISION_CANCEL = ServiceCache.getConstant("RECOMMEND_DECISION_CANCEL");
	@Override
	public decisionservice_iib.ApplicationGroupDataM getDecsionServiceMapper(ApplicationGroupDataM uloApplicationGroup,String decisionPoint)throws Exception {		
		
		logger.debug("IA1DecisionServiceRequestMapper..");
		if (null==uloApplicationGroup) {
			String msg = "ulo application group is null!";
			logger.fatal("ERROR>>"+msg);
			throw new Exception(msg);
		}else{
			decisionservice_iib.ApplicationGroupDataM iibApplicationGroup = new decisionservice_iib.ApplicationGroupDataM();
			mapApplicationGroup(decisionPoint, uloApplicationGroup, iibApplicationGroup);
			applicationMapper(uloApplicationGroup, iibApplicationGroup);
			personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
			return iibApplicationGroup;
		}
	}	
	
	@Override
	public void mapApplicationGroup(String decisionPoint,ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup){	
		iibApplicationGroup.setApplicationDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloApplicationGroup.getApplicationDate()));
		iibApplicationGroup.setApplicationGroupId(uloApplicationGroup.getApplicationGroupId());
		iibApplicationGroup.setApplicationGroupNo(uloApplicationGroup.getApplicationGroupNo());
		iibApplicationGroup.setApplicationTemplate(uloApplicationGroup.getApplicationTemplate());
		iibApplicationGroup.setSystemDate(DecisionServiceUtil.getSystemdate());
		iibApplicationGroup.setCallAction(decisionPoint);	
		String vetoFlag=DecisionServiceUtil.FLAG_N;
		if(uloApplicationGroup.getMaxLifeCycle()>1){
			vetoFlag= DecisionServiceUtil.FLAG_Y;
		}
		iibApplicationGroup.setVetoFlag(vetoFlag);
		iibApplicationGroup.setNumberOfSubmission(uloApplicationGroup.getMaxLifeCycle());
		iibApplicationGroup.setApplyChannel(uloApplicationGroup.getApplyChannel());
		iibApplicationGroup.setRandomNo1(uloApplicationGroup.getRandomNo1());
		iibApplicationGroup.setRandomNo2(uloApplicationGroup.getRandomNo2());
		iibApplicationGroup.setRandomNo3(uloApplicationGroup.getRandomNo3());
		
		//KPL Additional
		iibApplicationGroup.setApplyDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloApplicationGroup.getApplyDate()));
	}
	
	@Override
	public void applicationMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
		ArrayList<ApplicationDataM> uloApplicationList = uloApplicationGroup.getApplicationLifeCycle(new BigDecimal(uloApplicationGroup.getMaxLifeCycle()));
		int countApplication = 0;
		if(null!=uloApplicationList && uloApplicationList.size()>0){
			for(ApplicationDataM uloApplication : uloApplicationList){
				if(!RECOMMEND_DECISION_CANCEL.equals(uloApplication.getFinalAppDecision())){
					countApplication = countApplication+1;
					uloApplication.setSeq(countApplication);
					decisionservice_iib.ApplicationDataM iibApplication = new decisionservice_iib.ApplicationDataM();
					mapApplication(uloApplicationGroup, uloApplication, iibApplication);			
					loanMapper(uloApplicationGroup, uloApplication, iibApplication);
					iibApplicationGroup.getApplications().add(iibApplication);
				}
			}
			
		}else{
				ArrayList<PersonalInfoDataM> personalInfos = uloApplicationGroup.getUsingPersonalInfo();
				for(PersonalInfoDataM personalInfo : personalInfos){
					if(!ServiceUtil.empty(personalInfo)){
						ArrayList<ApplicationDataM> applications = uloApplicationGroup.getApplications(personalInfo.getPersonalId(),personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
						if(ServiceUtil.empty(applications)){
							String DEFUALT_DUMMY = ServiceCache.getConstant("DEFUALT_DUMMY");
							String DECISION_SERVICE_PROCEED = ServiceCache.getConstant("DECISION_SERVICE_PROCEED");
							decisionservice_iib.ApplicationDataM application = new decisionservice_iib.ApplicationDataM();
							if(!RECOMMEND_DECISION_CANCEL.equals(application.getFinalAppDecision())){
								application.setApplicationRecordId(DEFUALT_DUMMY);
								decisionservice_iib.LoanDataM loan = new decisionservice_iib.LoanDataM();
								loan.setLoanId(DEFUALT_DUMMY);
								decisionservice_iib.CardDataM card = new decisionservice_iib.CardDataM();
								card.setCardId(DEFUALT_DUMMY);
								if(personalInfo != null && personalInfo.getPersonalId() != null){
									card.setPersonalId(personalInfo.getPersonalId());
									application.setPersonalId(personalInfo.getPersonalId());
								}
								application.setRecommendDecision(ProcessUtil.getDecisionServiceProductStatus(DECISION_SERVICE_PROCEED));
								application.setPreviousProductStatus(ProcessUtil.getDecisionServiceProductStatus(DECISION_SERVICE_PROCEED));
								loan.setCard(card);
								application.getLoans().add(loan);
								application.setLifeCycle(uloApplicationGroup.getMaxLifeCycle());
							}
							iibApplicationGroup.getApplications().add(application);
						}
					}
				}
				//Defect Prod # I427/5
//			    String DEFUALT_DUMMY = ServiceCache.getConstant("DEFUALT_DUMMY");
//				String PERSONAL_TYPE_APPLICANT = ServiceCache.getConstant("PERSONAL_TYPE_APPLICANT");
//				String PERSONAL_TYPE_SUPPLEMENTARY = ServiceCache.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
//				String DECISION_SERVICE_PROCEED = ServiceCache.getConstant("DECISION_SERVICE_PROCEED");
//				PersonalInfoDataM personalInfo = uloApplicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
//				if(personalInfo == null){
//					personalInfo = uloApplicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
//				}
//				decisionservice_iib.ApplicationDataM application = new decisionservice_iib.ApplicationDataM();
//				application.setApplicationRecordId(DEFUALT_DUMMY);
//				decisionservice_iib.LoanDataM loan = new decisionservice_iib.LoanDataM();
//				loan.setLoanId(DEFUALT_DUMMY);
//				decisionservice_iib.CardDataM card = new decisionservice_iib.CardDataM();
//				card.setCardId(DEFUALT_DUMMY);
//				if(personalInfo != null && personalInfo.getPersonalId() != null){
//					card.setPersonalId(personalInfo.getPersonalId());
//					application.setPersonalId(personalInfo.getPersonalId());
//				}
//				application.setRecommendDecision(ProcessUtil.getDecisionServiceProductStatus(DECISION_SERVICE_PROCEED));
//				application.setPreviousProductStatus(ProcessUtil.getDecisionServiceProductStatus(DECISION_SERVICE_PROCEED));
//				loan.setCard(card);
//				application.getLoans().add(loan);
//				application.setLifeCycle(uloApplicationGroup.getMaxLifeCycle());
//				iibApplicationGroup.getApplications().add(application);
//				String msg = "uloApplicationList  is null!";
//				logger.debug(msg);
//				decisionservice_iib.ApplicationDataM iibApplication = new decisionservice_iib.ApplicationDataM();
//				mapApplication(uloApplicationGroup, new ApplicationDataM(), iibApplication);
//				iibApplicationGroup.getApplications().add(iibApplication);
//				 
//				decisionservice_iib.LoanDataM iibLoan = new decisionservice_iib.LoanDataM();
//				iibApplication.getLoans().add(iibLoan);
//				iibLoan.setCard(new decisionservice_iib.CardDataM());
		}	
	}
	
	@Override
	public void  personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
		ArrayList<PersonalInfoDataM> uloPersonalInfos = uloApplicationGroup.getPersonalInfos();
		if (null==uloPersonalInfos) {
			String msg = "uloPersonal is null!";
			throw new Exception(msg);
		}else{
			for(PersonalInfoDataM uloPersonalInfo :  uloPersonalInfos){
				logger.debug("uloPersonalInfo.personalId : "+uloPersonalInfo.getPersonalId());
				decisionservice_iib.PersonalInfoDataM iibPersonalInfo = new decisionservice_iib.PersonalInfoDataM();
				iibApplicationGroup.getPersonalInfos().add(iibPersonalInfo);
			
				mapPersonalInfo(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);				
				imageSplitMapper(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
							
				if(null!=uloPersonalInfo.getVerificationResult()){
					decisionservice_iib.VerificationResultDataM iibVerResult = new decisionservice_iib.VerificationResultDataM();
					verificationMapper(uloPersonalInfo, iibVerResult);
					iibPersonalInfo.setVerificationResult(iibVerResult);
				}
			}
		}
	}
	
	@Override
	public void loanMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication)throws Exception {
		String applicationType= uloApplicationGroup.getApplicationType();
		if(null==applicationType || "".equals(applicationType)){
			applicationType=ProcessUtil.getIAApplicationTypeFromTemplate(uloApplicationGroup.getApplicationTemplate());
		}	
		if(null==uloApplication.getLoans()){
			String msg = "ulo Loans is null!";
			logger.debug(msg);
		}else{
			for(LoanDataM uloLoan : uloApplication.getLoans()){
				decisionservice_iib.LoanDataM iibLoan = new decisionservice_iib.LoanDataM();
				mapLoan(uloLoan, iibLoan);
				iibLoan.setCoaProductCode(ProcessUtil.getCoaProductCode(applicationType,uloApplication.getProduct(),uloLoan));
				//add coa product code to ulo loan for compare set card type
				uloLoan.setRequestCoaProductCode(ProcessUtil.getCoaProductCode(applicationType,uloApplication.getProduct(),uloLoan));
				iibApplication.getLoans().add(iibLoan);

				decisionservice_iib.CardDataM iibCard = new decisionservice_iib.CardDataM();
				if(uloLoan.getCard() != null)
				{mapCard(uloLoan.getCard(), iibCard);}
				iibLoan.setCard(iibCard);

			}
		}
	} 
		
	@Override
	public void mapApplication(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication, decisionservice_iib.ApplicationDataM  iibApplication ){	
		PersonalInfoDataM uloPersonalApplication = uloApplicationGroup.getPersonalInfoByRelation(uloApplication.getApplicationRecordId());
		if(null==uloPersonalApplication){
			uloPersonalApplication = ProcessUtil.getApplicationTypePersonalInfo(uloApplicationGroup);
		}
		iibApplication.setPersonalId(null!=uloPersonalApplication?uloPersonalApplication.getPersonalId():"");								 
		iibApplication.setApplicationRecordId(uloApplication.getApplicationRecordId());
		iibApplication.setRequestProjectCode(uloApplication.getProjectCode());
		iibApplication.setRecommendProjectCode(uloApplication.getProjectCode());
		iibApplication.setOrgId(uloApplication.getProduct());
		iibApplication.setMainApplicationRecordId(uloApplication.getMaincardRecordId());
		iibApplication.setSeq(uloApplication.getSeq());
		LoanDataM uloLoan = uloApplication.getLoan();
		if(null!=uloLoan){
			if(null!=uloLoan.getCard()){			
				iibApplication.setSubProduct(ProcessUtil.getSubProduct(uloApplication.getProduct(), uloLoan.getCard().getCardType()));
			}
		}
	}
	
	@Override
	public void mapPersonalInfo(ApplicationGroupDataM uloApplicationGroup,PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo) {
		iibPersonalInfo.setPersonalId(uloPersonalInfo.getPersonalId());
		iibPersonalInfo.setBirthDate(DecisionServiceUtil.dateToXMLGregorianCalendar(uloPersonalInfo.getBirthDate()));
		if(ProcessUtil.PERSONAL_TYPE.INFOMATION.equals(uloPersonalInfo.getPersonalType())){
			iibPersonalInfo.setPersonalType(ProcessUtil.PERSONAL_TYPE.APPLICANT);
			iibPersonalInfo.setInfoFlag(DecisionServiceUtil.FLAG_Y);
			logger.debug("set personal type =A");
		}else{
			iibPersonalInfo.setPersonalType(uloPersonalInfo.getPersonalType());
		}
		iibPersonalInfo.setCisNo(uloPersonalInfo.getCisNo());
		iibPersonalInfo.setCisPrimSegment(uloPersonalInfo.getCisPrimSegment());
		iibPersonalInfo.setCisPrimSubSegment(uloPersonalInfo.getCisPrimSubSegment());
		iibPersonalInfo.setCisSecSegment(uloPersonalInfo.getCisSecSegment());
		iibPersonalInfo.setCisSecSubSegment(uloPersonalInfo.getCisSecSubSegment());
		iibPersonalInfo.setCustomerType(ProcessUtil.CUSTOMER_TYPE.INDIVIDUAL);
		iibPersonalInfo.setIdNo(uloPersonalInfo.getIdno());
		iibPersonalInfo.setMarried(uloPersonalInfo.getMarried());
		iibPersonalInfo.setThFirstName(uloPersonalInfo.getThFirstName());
		iibPersonalInfo.setThLastName(uloPersonalInfo.getThLastName());
		iibPersonalInfo.setThMidName(uloPersonalInfo.getThMidName());
		iibPersonalInfo.setThTitleCode(uloPersonalInfo.getThTitleCode());	
		iibPersonalInfo.setCidType(uloPersonalInfo.getCidType());
		iibPersonalInfo.setNationality(uloPersonalInfo.getNationality());
		iibPersonalInfo.setRequireBureau(uloPersonalInfo.getBureauRequiredFlag());
		//CR247
		if(!ServiceUtil.empty(uloPersonalInfo.getPayrollDate())){
			iibPersonalInfo.setPayDate(DecisionServiceUtil.parseInt( uloPersonalInfo.getPayrollDate() ) );
		}
		
	}
	
	@Override
	public void mapNcbInfo(NcbInfoDataM uloNcbInfo,decisionservice_iib.NcbInfoDataM iibNcbInfo){
		iibNcbInfo.setConsentRefNo(uloNcbInfo.getConsentRefNo());
	}
	
    @Override
    public void mapLoan(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan){
    	iibLoan.setLoanId(uloLoan.getLoanId());
    	iibLoan.setRequestLoanAmt(uloLoan.getRequestLoanAmt());
    	iibLoan.setRequestTerm(uloLoan.getRequestTerm());
    }
    
	@Override
	public void mapCard(CardDataM uloCard,decisionservice_iib.CardDataM iibCard) {
		if(null!=uloCard){
			iibCard.setCardType(uloCard.getCardType());
			iibCard.setCardEntryType(ProcessUtil.getCardEntryType(uloCard.getApplicationType()));
			iibCard.setCardNo(uloCard.getCardNo());
			iibCard.setCardNoEncrypted(uloCard.getCardNoEncrypted());
			iibCard.setCardNoMark(uloCard.getCardNoMark());
			iibCard.setMainCardNo(uloCard.getMainCardNo());
			iibCard.setMainCardHolderName(uloCard.getMainCardHolderName());
			iibCard.setCardId(uloCard.getCardId());
		}	
	}
	
	@Override
	public void  verificationMapper(Object uloObject,decisionservice_iib.VerificationResultDataM iibVerificationResult)throws Exception {
		if(uloObject instanceof PersonalInfoDataM){
			PersonalInfoDataM uloPersonal = (PersonalInfoDataM)uloObject;
			VerificationResultDataM uloVerification =uloPersonal.getVerificationResult();
			if(null!=uloVerification){
				if(null!=uloVerification.getRequiredDocs()&& uloVerification.getRequiredDocs().size()>0){
					for(RequiredDocDataM uloRequiredDoc : uloVerification.getRequiredDocs()){
						decisionservice_iib.RequiredDocDataM iibRequiredDoc = new decisionservice_iib.RequiredDocDataM();
						mapRequiredDoc(uloRequiredDoc, iibRequiredDoc);
						iibVerificationResult.getRequiredDocs().add(iibRequiredDoc);
					}
				}
			}		
		}else if(uloObject instanceof ApplicationDataM){			
			ApplicationDataM  uloApplication = (ApplicationDataM)uloObject;
			VerificationResultDataM uloVerification =uloApplication.getVerificationResult();
			if(null!=uloVerification){
				mapApplicationVerificationResult(uloVerification, iibVerificationResult);
				
				if(null!=uloVerification.getPolicyRules()&& uloVerification.getPolicyRules().size()>0){
					ArrayList<String> policyCodes = new ArrayList<String>();
					for(PolicyRulesDataM uloPolicyRules : uloVerification.getPolicyRules()){
						String policyCode = uloPolicyRules.getPolicyCode();
						decisionservice_iib.PolicyRulesDataM iibPolicyRule = new decisionservice_iib.PolicyRulesDataM();
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
}
