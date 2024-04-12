package com.eaf.service.common.iib.mapper.request;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;

import decisionservice_iib.FicoScoreDataM;

public class IncomeDecisionServiceRequestMapper extends DecisionServiceRequestMapper{
	private static transient Logger logger = Logger.getLogger(IncomeDecisionServiceRequestMapper.class);
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
			return iibApplicationGroup;
		}
	}
	
	@Override
	public void applicationMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
		ArrayList<ApplicationDataM> uloApplicationList = uloApplicationGroup.getApplicationLifeCycle(new BigDecimal(uloApplicationGroup.getMaxLifeCycle()));
		if (null==uloApplicationList) {
			String msg = "uloApplicationList  is null!";
			throw new Exception(msg);
		}else{
			for(ApplicationDataM uloApplication : uloApplicationList){
				if(!RECOMMEND_DECISION_CANCEL.equals(uloApplication.getFinalAppDecision())){
					decisionservice_iib.ApplicationDataM iibApplication = new decisionservice_iib.ApplicationDataM();
					iibApplicationGroup.getApplications().add(iibApplication);
					mapApplication(uloApplicationGroup, uloApplication, iibApplication);
					loanMapper(uloApplicationGroup, uloApplication, iibApplication);
					
					decisionservice_iib.VerificationResultDataM iibVerResult = new decisionservice_iib.VerificationResultDataM();
					iibApplication.setVerificationResult(iibVerResult);
					verificationMapper(uloApplication, iibVerResult);
				}
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
				mapLoan(uloLoan, iibLoan);
				iibLoan.setCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				//add coa product code to ulo loan for compare set card type
				uloLoan.setRequestCoaProductCode(ProcessUtil.getCoaProductCode(uloApplicationGroup.getApplicationType(),uloApplication.getProduct(),uloLoan));
				iibApplication.getLoans().add(iibLoan);
				
				decisionservice_iib.CardDataM iibCard = new decisionservice_iib.CardDataM();
				if(uloLoan.getCard() != null)
				{mapCard(uloLoan.getCard(), iibCard);}
				iibLoan.setCard(iibCard);
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
				ncbMapper(uloPersonalInfo, iibPersonalInfo);
				incomeMapper(uloPersonalInfo, iibPersonalInfo);
				allIncomeBundlingMapper(uloPersonalInfo, uloApplicationGroup, iibPersonalInfo);
				imageSplitMapper(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
							
				decisionservice_iib.VerificationResultDataM iibVerResult = new decisionservice_iib.VerificationResultDataM();
				iibPersonalInfo.setVerificationResult(iibVerResult);
				if(null!=uloPersonalInfo.getVerificationResult()){
					verificationMapper(uloPersonalInfo, iibVerResult);
				}
//				#rawi comment income source for support new suggest.
//				incomeSourceMapper(uloPersonalInfo, iibVerResult);
				
			}
		}
	}
	
	@Override
	public void mapLoan(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan) {
		iibLoan.setLoanId(uloLoan.getLoanId());		
		iibLoan.setRequestLoanAmt(uloLoan.getRequestLoanAmt());
    	iibLoan.setRequestTerm(uloLoan.getRequestTerm());
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
	public void mapPersonalVerificationResult(VerificationResultDataM uloVerification,decisionservice_iib.VerificationResultDataM iibVerResult) {
		try {
			super.mapPersonalVerificationResult(uloVerification, iibVerResult);
			super.privilegeProjectCodeMapper(uloVerification, iibVerResult);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}	
	}
}
