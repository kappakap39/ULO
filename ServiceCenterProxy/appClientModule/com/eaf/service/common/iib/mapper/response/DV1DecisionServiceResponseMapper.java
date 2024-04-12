package com.eaf.service.common.iib.mapper.response;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

public class DV1DecisionServiceResponseMapper extends DecisionServiceResponseMapper{
	private static transient Logger logger = Logger.getLogger(DV1DecisionServiceResponseMapper.class);
	String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String EXECUTE_KEY_PROGRAM_NEW_INCOME = ServiceCache.getConstant("EXECUTE_KEY_PROGRAM_NEW_INCOME");
	@Override
	public  void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) throws Exception{
		uloApplicationMapper(uloApplicationGroup, iibApplicationGroup);
		personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
		mapApplicationGroup(uloApplicationGroup, iibApplicationGroup);
		mapThaiBevPartner(uloApplicationGroup, iibApplicationGroup);
		
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
	public void loanMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication)throws Exception {
		if(null!=iibApplication.getLoans() && iibApplication.getLoans().size()>0){
			for(decisionservice_iib.LoanDataM iibLoan : iibApplication.getLoans()){
				LoanDataM uloLoan = uloApplication.getloanById(iibLoan.getLoanId());
				if(null!=uloLoan){
					mapLoan(uloLoan, iibLoan);	
				}else{
					logger.debug("Cant find ulo loan id>>"+iibLoan.getLoanId());
				}							 
			}
		}else{
			logger.debug("iib loans is null!!");
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
				
				if(null!=iibPersonalInfo.getCardlinkCards() &&  iibPersonalInfo.getCardlinkCards().size()>0){
					for(decisionservice_iib.CardlinkCard iibCardLinkCard : iibPersonalInfo.getCardlinkCards()){
						cardlinkCardMapper(uloPersonalInfo, iibCardLinkCard);
					}						
				}	
				
				decisionservice_iib.ApplicationDataM  iibApplication = ProcessUtil.filterIIBPersonalApplication(iibApplicationGroup.getApplications(), uloPersonalInfo.getPersonalId());
				if(null!=iibApplication){
					privilegeProjectCodeMapper(uloPersonalInfo, iibApplication);
				}	
			}
		}else{
			logger.debug("iib personal is null!!");
		}
	}
	
	public  void mapApplication(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication,decisionservice_iib.ApplicationDataM iibApplication){
		uloApplication.setApplicationRecordId(iibApplication.getApplicationRecordId());
		uloApplication.setApplicationType(ProcessUtil.setApplyType(iibApplication.getApplyType()));
		uloApplication.setIsVetoEligibleFlag(iibApplication.getIsVetoEligibleFlag());		
		uloApplication.setPolicyProgramId(iibApplication.getPolicyProgramId());
		uloApplication.setRecommendDecision(ProcessUtil.getULORecommendDecision(iibApplication.getRecommendDecision()));
		//#CR-ADD
		if(iibApplication.getRecommendProjectCode() != null && !iibApplication.getRecommendProjectCode().isEmpty()){
			uloApplication.setProjectCode(iibApplication.getRecommendProjectCode());
		}
		
		if(null!=iibApplication.getReferCriteria() && iibApplication.getReferCriteria().size()>0){
			uloApplication.setReferCriteria(ProcessUtil.mapReferCriterias(iibApplication.getReferCriteria()));
		}
		String setReExecute = iibApplication.getReExecuteKeyProgramFlag();
		if (EXECUTE_KEY_PROGRAM_NEW_INCOME.equals(setReExecute) && ProcessUtil.REC_APP_DECISION_APPROVE.equalsIgnoreCase(uloApplication.getRecommendDecision())) {
			uloApplication.setRecommendDecision(ServiceCache.getConstant("DECISION_SERVICE_PROCEED"));
		}
		logger.debug("uloApplication.getRecommendDecision()>>"+uloApplication.getRecommendDecision());
		if (ProcessUtil.REC_APP_DECISION_APPROVE.equalsIgnoreCase(uloApplication.getRecommendDecision())) {
			uloApplication.setFinalAppDecision(ProcessUtil.FINAL_APP_DECISION_APPROVE);
			uloApplication.setFinalAppDecisionDate(DecisionServiceUtil.getDate());
			uloApplication.setFinalAppDecisionBy(uloApplicationGroup.getUserId());
		} else if (ProcessUtil.REC_APP_DECISION_REJECT.equalsIgnoreCase(uloApplication.getRecommendDecision())) {
			uloApplication.setFinalAppDecision(ProcessUtil.FINAL_APP_DECISION_REJECT);
			uloApplication.setFinalAppDecisionDate(DecisionServiceUtil.getDate());
			uloApplication.setFinalAppDecisionBy(uloApplicationGroup.getUserId());
			mapReasonPolicyRule(uloApplication,iibApplication);
		}
		if(null!=iibApplication.getReExecuteKeyProgramFlag() && !"".equals(iibApplication.getReExecuteKeyProgramFlag())){
			uloApplication.setReExecuteKeyProgramFlag(iibApplication.getReExecuteKeyProgramFlag());
		}		
	}
	
}
