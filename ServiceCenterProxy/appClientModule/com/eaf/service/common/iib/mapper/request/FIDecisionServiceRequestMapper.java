package com.eaf.service.common.iib.mapper.request;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardlinkCardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.service.common.iib.mapper.DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;

import decisionservice_iib.CardlinkCard;
import decisionservice_iib.CardlinkCustomer;

public class FIDecisionServiceRequestMapper extends DecisionServiceRequestMapper{
	private static transient Logger logger = Logger.getLogger(FIDecisionServiceRequestMapper.class);
	
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
				cardLinkCustomerMapper(uloPersonalInfo, iibPersonalInfo);
				cardLinkMapper(uloPersonalInfo, iibPersonalInfo);
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
				decisionservice_iib.LoanDataM iibLoan = new decisionservice_iib.LoanDataM();
				iibLoan.setCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				//add coa product code to ulo loan for compare set card type
				uloLoan.setRequestCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				mapLoan(uloLoan, iibLoan);
				
				//KPL Additional
				iibLoan.setRecommendTerm(uloLoan.getTerm());
				iibLoan.setDebtBurden(uloLoan.getDebtBurden()); 
				
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
				loanFeeMapper(uloLoan, iibLoan);
				loanPricingMapper(uloLoan, iibLoan);
				loanTierMapper(uloLoan, iibLoan);
			}
		}
	}
	
	@Override
	public void mapApplication(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication) {
		super.mapApplication(uloApplicationGroup, uloApplication, iibApplication);
		iibApplication.setCaProductStatus(ProcessUtil.getDecisionFinalAppDecision(uloApplication.getFinalAppDecision()));
	}
	
//	@Override
//	public void mapCardlinkCustomer(CardlinkCustomerDataM uloCardLinkCustomer,CardlinkCustomer iibCardLinkCustomer) {
//		iibCardLinkCustomer.setVatCode(uloCardLinkCustomer.getVatCode()) ;
//		iibCardLinkCustomer.setCardlinkCustNo(uloCardLinkCustomer.getCardlinkCustNo());
//		iibCardLinkCustomer.setOrgNo(uloCardLinkCustomer.getOrgId());
//		iibCardLinkCustomer.setFinancialInfoCode(uloCardLinkCustomer.getFinancialInfoCode());
//		
//	}
	
//	@Override
//	public void mapCardlinkCard(CardlinkCardDataM uloCardLinkCard,CardlinkCard iibCardLinkCard) {
//		iibCardLinkCard.setMainOrgNo(uloCardLinkCard.getOrgId());
//		iibCardLinkCard.setMainCustNo(uloCardLinkCard.getCardlinkCustNo());
//		iibCardLinkCard.setCardNoInEncrypt(ProcessUtil.getCardEncrypt(uloCardLinkCard.getCardCode()));
//		iibCardLinkCard.setBlockCode(uloCardLinkCard.getBlockCode());
//		iibCardLinkCard.setProjectCode(uloCardLinkCard.getProjectCode());
//		iibCardLinkCard.setSupOrgNo(uloCardLinkCard.getSupOrgId());
//		iibCardLinkCard.setCoaProductCode(uloCardLinkCard.getCoaProductCode());
//		iibCardLinkCard.setCardCode(uloCardLinkCard.getCardCode());
//		iibCardLinkCard.setPlasticCode(uloCardLinkCard.getPlasticCode());
//		iibCardLinkCard.setCardlinkCardId(uloCardLinkCard.getCardlinkCardId());
//	}
}
