package com.eaf.service.common.iib.mapper.response;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

import decisionservice_iib.CapportLoanDataM;

public class FIRecalculateDecisionServiceResponseMapper extends DecisionServiceResponseMapper {
	private static transient Logger logger = Logger.getLogger(FIRecalculateDecisionServiceResponseMapper.class);
	String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public  void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) throws Exception{
		uloApplicationMapper(uloApplicationGroup, iibApplicationGroup);
		logger.debug("<<<< FIRecalculateDecisionServiceResponseMapper >>>");
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
	public  void uloApplicationMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getApplications() && iibApplicationGroup.getApplications().size()>0){
			for(decisionservice_iib.ApplicationDataM iibApplication : iibApplicationGroup.getApplications()){
				ApplicationDataM uloApplication = uloApplicationGroup.getApplicationById(iibApplication.getApplicationRecordId());
				if(null!=uloApplication){					
					loanMapper(uloApplicationGroup, uloApplication, iibApplication);	
				}else{
					logger.debug("iib application is null!!");
				}
				
			}
		} 
	}
	
	@Override
	public  void loanMapper(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication, decisionservice_iib.ApplicationDataM iibApplication) throws Exception{
		if(null!=iibApplication.getLoans() && iibApplication.getLoans().size()>0){
			for(decisionservice_iib.LoanDataM iibLoan : iibApplication.getLoans()){
				logger.debug("iibLoan.getLoanId()>>"+iibLoan.getLoanId());
				if(null==iibLoan.getLoanId()){
					continue;
				}
				LoanDataM uloLoan = uloApplication.getloanById(iibLoan.getLoanId());
				if(null!=uloLoan){
					mapLoan(uloLoan, iibLoan);
				}else{
					logger.debug("Can not find loan id is>>"+iibLoan.getLoanId());
				}
			}

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
			
		}
		
	}
}
