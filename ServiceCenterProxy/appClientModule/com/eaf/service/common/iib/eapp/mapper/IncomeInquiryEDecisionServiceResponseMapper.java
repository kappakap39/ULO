package com.eaf.service.common.iib.eapp.mapper;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.iib.model.ApplicationGroup;
import com.ava.flp.eapp.iib.model.IncomeCalculations;
import com.ava.flp.eapp.iib.model.PersonalInfos;
import com.ava.flp.eapp.iib.model.VerificationResultApplications;
import com.ava.flp.eapp.iib.model.VerificationResultPersonalInfos;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IncomeCalculationDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.util.ServiceUtil;

public class IncomeInquiryEDecisionServiceResponseMapper extends EDecisionServiceResponseMapper {
	
	private static transient Logger logger = Logger.getLogger(IncomeInquiryEDecisionServiceResponseMapper.class);
	
	@Override
	public void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception {
		logger.debug("Start INCOME_INQUIRY applicationGroupMapper");
		
		personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
	}
	
	public void personalInfoMapper(ApplicationGroupDataM uloApplicationGroup, ApplicationGroup iibApplicationGroup) throws Exception {
		if(null != iibApplicationGroup.getPersonalInfos() && iibApplicationGroup.getPersonalInfos().size() > 0) {
			for(PersonalInfos iibPersonalInfo : iibApplicationGroup.getPersonalInfos()) {
				PersonalInfoDataM uloPersonalInfo = uloApplicationGroup.getPersonalById(iibPersonalInfo.getPersonalId());
				if(null == uloPersonalInfo) {
					logger.debug("Cant find personal id >> " + iibPersonalInfo.getPersonalId());
					continue;
				}
				logger.debug("find ulo perosnal id >> " + iibPersonalInfo.getPersonalId());
				mapPersonalInfo(uloPersonalInfo, iibPersonalInfo);
				
				if(!ServiceUtil.empty(iibPersonalInfo.getVerificationResult())) {
					verificationResultMapper(uloApplicationGroup, uloPersonalInfo, null, iibPersonalInfo.getVerificationResult());					
				} else {
					logger.debug("iibPersonalInfo.getVerificationResult() is null");
				}	
			}
		}
	}
	
	public void verificationResultMapper(ApplicationGroupDataM uloApplicationGroup, Object object, 
			VerificationResultApplications iibVerificationResult, VerificationResultPersonalInfos iibVerificationResultPerson) 
			throws Exception {
		logger.debug("## mapper ulo verification ##");
		PersonalInfoDataM uloPersonalInfo = (PersonalInfoDataM) object;
		VerificationResultDataM uloVerificationResult = uloPersonalInfo.getVerificationResult();
		if(null == uloVerificationResult) {
			uloVerificationResult = new VerificationResultDataM();
			uloPersonalInfo.setVerificationResult(uloVerificationResult);
			logger.debug("verification is null will create new !!");
		}
		mapPersonalVerificationResult(uloApplicationGroup, uloVerificationResult, iibVerificationResultPerson);
	}
	
	public void mapPersonalVerificationResult(ApplicationGroupDataM uloApplicationGroup, VerificationResultDataM uloVerResult, 
			VerificationResultPersonalInfos iibVerResult) throws Exception {
		if(null != iibVerResult.getIncomeCalculations() && iibVerResult.getIncomeCalculations().size() > 0) {
			ArrayList<IncomeCalculationDataM> incomeCalculations = new ArrayList<IncomeCalculationDataM>();
			uloVerResult.setIncomeCalculations(incomeCalculations);
			for(IncomeCalculations iibIncomeCalculation : iibVerResult.getIncomeCalculations()) {
				mapIncomeCalculations(uloVerResult, iibIncomeCalculation);
			}	
		}
	}	
	
}
