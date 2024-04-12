package com.eaf.service.common.iib.mapper.request;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

public class DCDecisionServiceRequestMapper extends DecisionServiceRequestMapper {
	private static transient Logger logger = Logger.getLogger(DCDecisionServiceRequestMapper.class);
	String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public decisionservice_iib.ApplicationGroupDataM getDecsionServiceMapper(ApplicationGroupDataM uloApplicationGroup,String decisionPoint)throws Exception {
		if (null==uloApplicationGroup) {
			String msg = "uloApplicationGroup is null!";
			throw new Exception(msg);
			
		}else{
			decisionservice_iib.ApplicationGroupDataM iibApplicationGroup  = new decisionservice_iib.ApplicationGroupDataM();
			mapApplicationGroup(decisionPoint, uloApplicationGroup, iibApplicationGroup);
			ArrayList<ApplicationDataM>uloApplications = uloApplicationGroup.getApplications();
			ArrayList<PersonalInfoDataM> personalInfos = uloApplicationGroup.getUsingPersonalInfo();
				for(PersonalInfoDataM personalInfo : personalInfos){
					if(!ServiceUtil.empty(personalInfo)){
						ArrayList<ApplicationDataM> applications = uloApplicationGroup.getApplications(personalInfo.getPersonalId(),personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
						if(ServiceUtil.empty(applications)){
							String DEFUALT_DUMMY = ServiceCache.getConstant("DEFUALT_DUMMY");
							String DECISION_SERVICE_PROCEED = ServiceCache.getConstant("DECISION_SERVICE_PROCEED");
							decisionservice_iib.ApplicationDataM application = new decisionservice_iib.ApplicationDataM();
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
							iibApplicationGroup.getApplications().add(application);
						}
					}
				}
				//Defect Prod # I427/5
//				if(uloApplications  == null || uloApplications.size() == 0){
//					String DEFUALT_DUMMY = ServiceCache.getConstant("DEFUALT_DUMMY");
//					String PERSONAL_TYPE_APPLICANT = ServiceCache.getConstant("PERSONAL_TYPE_APPLICANT");
//					String PERSONAL_TYPE_SUPPLEMENTARY = ServiceCache.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
//					String DECISION_SERVICE_PROCEED = ServiceCache.getConstant("DECISION_SERVICE_PROCEED");
//					PersonalInfoDataM personalInfo = uloApplicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
//					logger.debug("defect info : >> personal mapper dc >> "+new Gson().toJson(personalInfo));
//					if(personalInfo == null){
//						personalInfo = uloApplicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
//					}
//					decisionservice_iib.ApplicationDataM application = new decisionservice_iib.ApplicationDataM();
//					application.setApplicationRecordId(DEFUALT_DUMMY);
//					decisionservice_iib.LoanDataM loan = new decisionservice_iib.LoanDataM();
//					loan.setLoanId(DEFUALT_DUMMY);
//					decisionservice_iib.CardDataM card = new decisionservice_iib.CardDataM();
//					card.setCardId(DEFUALT_DUMMY);
//					if(personalInfo != null && personalInfo.getPersonalId() != null){
//						card.setPersonalId(personalInfo.getPersonalId());
//						application.setPersonalId(personalInfo.getPersonalId());
//					}
//					application.setRecommendDecision(ProcessUtil.getDecisionServiceProductStatus(DECISION_SERVICE_PROCEED));
//					application.setPreviousProductStatus(ProcessUtil.getDecisionServiceProductStatus(DECISION_SERVICE_PROCEED));
//					loan.setCard(card);
//					application.getLoans().add(loan);
//					application.setLifeCycle(uloApplicationGroup.getMaxLifeCycle());
//					iibApplicationGroup.getApplications().add(application);
//				}
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
	public void mapApplicationGroup(String decisionPoint,ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) {
		super.mapApplicationGroup(decisionPoint, uloApplicationGroup,iibApplicationGroup);
		iibApplicationGroup.setDocumentSLAType(uloApplicationGroup.getDocumentSLAType());
		//KPL Additional
		iibApplicationGroup.setFullFraudFlag(uloApplicationGroup.getFullFraudFlag());
	}
	
   @Override
   public void loanMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication)throws Exception {
	   if (null==uloApplication.getLoans()) {
			String msg = "uloLoans is null!";
			logger.debug(msg);
		}else{
			for(LoanDataM uloLoan : uloApplication.getLoans()){
				decisionservice_iib.LoanDataM iibLoan = new decisionservice_iib.LoanDataM();
				mapLoan(uloLoan, iibLoan);
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
				
				decisionservice_iib.CardDataM iibCard = new decisionservice_iib.CardDataM();
				if(uloLoan.getCard() != null)
				{mapCard(uloLoan.getCard(), iibCard);}
				iibLoan.setCard(iibCard);
				
				cashTransferMapper(uloLoan,iibLoan);
			}
		}
   }
	
	@Override
	public void mapCard(CardDataM uloCard,decisionservice_iib.CardDataM iibCard) {	
		iibCard.setCardId(uloCard.getCardId());
		iibCard.setCardLevel(uloCard.getCardLevel());	
		iibCard.setCardNo(uloCard.getCardNo());
		iibCard.setCgaApplyChannel(uloCard.getCgaApplyChannel());
		iibCard.setCgaCode(uloCard.getCgaCode());
		iibCard.setChassisNo(uloCard.getChassisNo());
		iibCard.setLoanId(uloCard.getLoanId());
		iibCard.setMainCardNo(uloCard.getMainCardNo());
		iibCard.setMembershipNo(uloCard.getMembershipNo());
		iibCard.setCardType(uloCard.getApplicationType());
		iibCard.setCardEntryType(ProcessUtil.getCardEntryType(uloCard.getApplicationType()));
	}
	@Override
	public void mapPersonalVerificationResult(VerificationResultDataM uloVerification,decisionservice_iib.VerificationResultDataM iibVerResult) {
		super.mapPersonalVerificationResult(uloVerification, iibVerResult);
		iibVerResult.setDocFollowUpStatus(uloVerification.getDocFollowUpStatus());
	}
}
