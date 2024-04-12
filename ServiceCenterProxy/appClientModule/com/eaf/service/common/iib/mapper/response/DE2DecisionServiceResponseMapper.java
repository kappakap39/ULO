package com.eaf.service.common.iib.mapper.response;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

public class DE2DecisionServiceResponseMapper extends DecisionServiceResponseMapper{	
	private static transient Logger logger = Logger.getLogger(DecisionServiceResponseMapper.class);
	@Override
	public  void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) throws Exception{
		personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
		uloApplicationMapper(uloApplicationGroup, iibApplicationGroup);
	}
	
	public  void uloApplicationMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getApplications() && iibApplicationGroup.getApplications().size()>0){
			for(decisionservice_iib.ApplicationDataM iibApplication : iibApplicationGroup.getApplications()){
				ApplicationDataM uloApplication = uloApplicationGroup.getApplicationById(iibApplication.getApplicationRecordId());
				if(null!=uloApplication){					
					mapApplication(uloApplicationGroup, uloApplication, iibApplication);
					loanMapper(uloApplicationGroup, uloApplication, iibApplication);
				}
				
			}
		} 
	}
	
	@Override
	public void personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
		if(null!=iibApplicationGroup.getPersonalInfos() && iibApplicationGroup.getPersonalInfos().size()>0){
			for(decisionservice_iib.PersonalInfoDataM iibPersonalInfo : iibApplicationGroup.getPersonalInfos()){
				PersonalInfoDataM uloPersonalInfo = uloApplicationGroup.getPersonalById(iibPersonalInfo.getPersonalId());
				if(null==uloPersonalInfo){
					logger.debug("Cant find ulo perosnal id >>"+iibPersonalInfo.getPersonalId());
					continue;
				}
				if(ServiceCache.lookup("JOBSTATE_REJECT",uloApplicationGroup.getJobState()) && !ServiceUtil.empty(iibPersonalInfo.getApplicationScores())){
					mapApplicationScore(uloPersonalInfo,iibPersonalInfo.getApplicationScores().get(0));
				}
			}
		}
	}
	
	@Override
	public void mapApplication(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication) {
		if(iibApplication.getRecommendProjectCode() != null && !iibApplication.getRecommendProjectCode().isEmpty()){
			uloApplication.setProjectCode(iibApplication.getRecommendProjectCode());
		}
	}
	
	@Override
	public void loanMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication)throws Exception {	
		if(null!=iibApplication.getLoans() && iibApplication.getLoans().size()>0){
			for(decisionservice_iib.LoanDataM iibLoan : iibApplication.getLoans()){
				LoanDataM uloLoan = uloApplication.getloanById(iibLoan.getLoanId());
				if(null==uloLoan){
					logger.debug("cant find ulo loan id >>"+iibLoan.getLoanId());
					continue;
				}
				if(null!=iibLoan.getLoanPricings() && iibLoan.getLoanPricings().size()>0){
					loanPricingsMapper(uloLoan, iibLoan);
				}

				if(null!=iibLoan.getLoanTiers() && iibLoan.getLoanTiers().size()>0){
					loanTiersMapper(uloLoan, iibLoan);
				}	
				
				//KPL Additional
				uloLoan.setFirstInstallmentDate(DecisionServiceUtil.toSqlDate(iibLoan.getFirstInstallmentDate()));
				uloLoan.setCoreBankProduct(iibLoan.getCbProduct());
				uloLoan.setCoreBankSubProduct(iibLoan.getCbSubProduct());
				uloLoan.setCoreBankMarketCode(iibLoan.getCbMarketCode());
			}
		}
	}
	
	@Override
	public void loanPricingsMapper(LoanDataM uloLoan, decisionservice_iib.LoanDataM iibLoan )throws Exception{
		mapMainloanPricings(uloLoan, iibLoan);
	}
	
	@Override
	public void loanTiersMapper(LoanDataM uloLoan, decisionservice_iib.LoanDataM iibLoan)throws Exception{
			 super.mapMainloanTiers(uloLoan, iibLoan);
	}
}
