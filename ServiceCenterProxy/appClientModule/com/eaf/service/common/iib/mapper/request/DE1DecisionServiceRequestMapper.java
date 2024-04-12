package com.eaf.service.common.iib.mapper.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.service.common.iib.mapper.DecisionServiceRequestMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;

import decisionservice_iib.DocumentSlaTypeDataM;

public class DE1DecisionServiceRequestMapper extends DecisionServiceRequestMapper {
	private static transient Logger logger = Logger.getLogger(DE1DecisionServiceRequestMapper.class);
	
	@Override
	public decisionservice_iib.ApplicationGroupDataM getDecsionServiceMapper(ApplicationGroupDataM uloApplicationGroup,String decisionPoint)throws Exception {		
		if (null==uloApplicationGroup) {
			String msg = "uloApplicationGroup is null!";
			logger.debug(msg);
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
			logger.debug(msg);
			throw new Exception(msg);
		}else{
			
			for(PersonalInfoDataM uloPersonalInfo :  uloPersonalInfos){
				decisionservice_iib.PersonalInfoDataM iibPersonalInfo = new decisionservice_iib.PersonalInfoDataM();
				iibApplicationGroup.getPersonalInfos().add(iibPersonalInfo);
			
				mapPersonalInfo(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
				addressMapper(uloPersonalInfo, iibPersonalInfo);
				debtInfoMapper(uloPersonalInfo, iibPersonalInfo);
				ncbMapper(uloPersonalInfo, iibPersonalInfo);
				
				if(null!=uloPersonalInfo.getVerificationResult()){
					decisionservice_iib.VerificationResultDataM iibVerResult = new decisionservice_iib.VerificationResultDataM();
					verificationMapper(uloPersonalInfo, iibVerResult);
					iibPersonalInfo.setVerificationResult(iibVerResult);
				}	
				
				imageSplitMapper(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
			}
		}
	}
	
	@Override
	public void mapLoan(LoanDataM uloLoan,decisionservice_iib.LoanDataM iibLoan) {
		iibLoan.setLoanId(uloLoan.getLoanId());
		iibLoan.setRequestLoanAmt(uloLoan.getRequestLoanAmt());
		iibLoan.setRequestTerm(uloLoan.getRequestTerm());
		cashTransferMapper(uloLoan,iibLoan);
	}
	
	public void  cashTransferMapper(LoanDataM uloLoan ,decisionservice_iib.LoanDataM iibLoan) {
		if(null!=uloLoan.getCashTransfers() && uloLoan.getCashTransfers().size()>0){
			for(CashTransferDataM uloCashTransfer : uloLoan.getCashTransfers()){
				decisionservice_iib.CashTransferDataM iibCashTransfer = new decisionservice_iib.CashTransferDataM();
				mapCashTransfer(uloCashTransfer, iibCashTransfer);
				iibLoan.getCashTransfers().add(iibCashTransfer);
			}
		}else{
			logger.debug("uloCashTransfers is null!");
		}
	}
}
