package com.eaf.service.common.iib.mapper.request;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.iib.mapper.DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;

public class DocDecisionServiceRequestMapper  extends DecisionServiceRequestMapper {
	private static transient Logger logger = Logger.getLogger(DocDecisionServiceRequestMapper.class);
	
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
				incomeMapper(uloPersonalInfo, iibPersonalInfo);				
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
	public void mapPersonalVerificationResult(VerificationResultDataM uloVerification,decisionservice_iib.VerificationResultDataM iibVerResult) {
		try {
			super.mapPersonalVerificationResult(uloVerification, iibVerResult);
			super.privilegeProjectCodeMapper(uloVerification, iibVerResult);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
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
	
}
