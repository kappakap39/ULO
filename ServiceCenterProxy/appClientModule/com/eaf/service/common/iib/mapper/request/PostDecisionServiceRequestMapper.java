package com.eaf.service.common.iib.mapper.request;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;
import com.eaf.service.common.iib.mapper.DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;

import decisionservice_iib.FicoScoreDataM;
import decisionservice_iib.VerificationResultDataM;

public class PostDecisionServiceRequestMapper extends DecisionServiceRequestMapper{
	private static transient Logger logger = Logger.getLogger(PostDecisionServiceRequestMapper.class);
	
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
			
			if(null!=uloApplicationGroup.getSaleInfos()){
				for(SaleInfoDataM uloSaleInfo : uloApplicationGroup.getSaleInfos()){
					decisionservice_iib.SaleInfoDataM iibSaleInfo = new decisionservice_iib.SaleInfoDataM();
					mapSaleInfo(uloSaleInfo, iibSaleInfo);
					iibApplicationGroup.getSaleInfos().add(iibSaleInfo);
				}
			}
			
			return iibApplicationGroup;
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
				mapLoan(uloLoan, iibLoan);
				iibLoan.setCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				//add coa product code to ulo loan for compare set card type
				uloLoan.setRequestCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				iibApplication.getLoans().add(iibLoan);
				
				decisionservice_iib.CardDataM iibCard = new decisionservice_iib.CardDataM();
				if(uloLoan.getCard() != null)
				{mapCard(uloLoan.getCard(), iibCard);}
				iibLoan.setCard(iibCard);
				
				if(null!=uloLoan.getPaymentMethodId()){
					PaymentMethodDataM uloPaymentMethod = uloApplicationGroup.getPaymentMethodById(uloLoan.getPaymentMethodId());
					decisionservice_iib.PaymentMethodDataM  iibPaymentMethod = new decisionservice_iib.PaymentMethodDataM();
					mapPaymentMethod(uloPaymentMethod, iibPaymentMethod);
					iibLoan.getPaymentMethods().add(iibPaymentMethod);
				}
			}
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
				allIncomeBundlingMapper(uloPersonalInfo, uloApplicationGroup, iibPersonalInfo);
				imageSplitMapper(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
			}
		}
	}
	 
	@Override
	public void mapLoan(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan) {
		iibLoan.setLoanId(uloLoan.getLoanId());
		iibLoan.setRequestLoanAmt(uloLoan.getRequestLoanAmt());
    	iibLoan.setRequestTerm(uloLoan.getRequestTerm());
    	iibLoan.setNormalCreditLimit(uloLoan.getFinalCreditLimit());
	}
	
	@Override
	public void mapCard(CardDataM uloCard, decisionservice_iib.CardDataM iibCard) {
		iibCard.setCardType(uloCard.getCardType());
		iibCard.setCgaApplyChannel(uloCard.getCgaApplyChannel());
		iibCard.setCgaCode(uloCard.getCgaCode());
		iibCard.setChassisNo(uloCard.getChassisNo());
		iibCard.setMainCardNo(uloCard.getMainCardNo());
		iibCard.setMembershipNo(uloCard.getMembershipNo());
		iibCard.setCardEntryType(ProcessUtil.getCardEntryType(uloCard.getApplicationType()));
	}
	
	@Override
	public void mapNcbInfo(NcbInfoDataM uloNcbInfo,decisionservice_iib.NcbInfoDataM iibNcbInfo) {		
		iibNcbInfo.setFicoErrorCode(uloNcbInfo.getFicoErrorCode());
		iibNcbInfo.setFicoErrorMsg(uloNcbInfo.getFicoErrorMsg());
		iibNcbInfo.setFicoReason1Code(uloNcbInfo.getFicoReason1Code());
		iibNcbInfo.setFicoReason1Desc(uloNcbInfo.getFicoReason1Desc());
		iibNcbInfo.setFicoReason2Code(uloNcbInfo.getFicoReason2Code());
		iibNcbInfo.setFicoReason2Desc(uloNcbInfo.getFicoReason2Desc());
		iibNcbInfo.setFicoReason3Code(uloNcbInfo.getFicoReason3Code());
		iibNcbInfo.setFicoReason3Desc(uloNcbInfo.getFicoReason3Desc());
		iibNcbInfo.setFicoReason4Code(uloNcbInfo.getFicoReason4Code());
		iibNcbInfo.setFicoReason4Desc(uloNcbInfo.getFicoReason4Desc());
		decisionservice_iib.FicoScoreDataM iibFicoScore = new FicoScoreDataM();
		iibFicoScore.setFicoScore(uloNcbInfo.getFicoScore());
		iibNcbInfo.setFicoScore(iibFicoScore);
	}
	
	@Override
	public void policyRulesMapper(ArrayList<PolicyRulesDataM> uloPolicyRulesList,VerificationResultDataM iibVerificationResult) {
		 // not map rule in this decision
	}
}
