package com.eaf.service.common.iib.mapper.response;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

public class PB1DecisionServiceResponseMapper extends DecisionServiceResponseMapper {
	private static transient Logger logger = Logger.getLogger(PB1DecisionServiceResponseMapper.class);
	String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup)throws Exception {
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
	public  void personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,decisionservice_iib.ApplicationGroupDataM iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getPersonalInfos() && iibApplicationGroup.getPersonalInfos().size()>0){
			for(decisionservice_iib.PersonalInfoDataM iibPersonalInfo : iibApplicationGroup.getPersonalInfos()){
				PersonalInfoDataM uloPersonalInfo = uloApplicationGroup.getPersonalById(iibPersonalInfo.getPersonalId());
				if(null==uloPersonalInfo){
					 logger.debug("Cant find personal id >>"+iibPersonalInfo.getPersonalId());
					 continue;
				}
				logger.debug("Cant find ulo perosnal id >>"+iibPersonalInfo.getPersonalId());
				mapPersonalInfo(uloPersonalInfo, iibPersonalInfo);			
				
				if(null!=iibPersonalInfo.getBolOrganizations()){
					for(decisionservice_iib.BolOrganization iibBolOrganization : iibPersonalInfo.getBolOrganizations()){						 
						mapBolOrganization(uloPersonalInfo,iibBolOrganization);
					}
				}
				
				if(null!=iibPersonalInfo.getCardlinkCustomers()&&  iibPersonalInfo.getCardlinkCustomers().size()>0){
					for(decisionservice_iib.CardlinkCustomer   iibCardLinkCust : iibPersonalInfo.getCardlinkCustomers()){	
						cardlinkCustomerMapper(uloPersonalInfo, iibCardLinkCust);
					}
				}
				
				if(null!=iibPersonalInfo.getCardlinkCards() &&  iibPersonalInfo.getCardlinkCards().size()>0){
					for(decisionservice_iib.CardlinkCard iibCardLinkCard : iibPersonalInfo.getCardlinkCards()){
						cardlinkCardMapper(uloPersonalInfo, iibCardLinkCard);
					}				
				}
				
				if(null!=iibPersonalInfo.getNcbInfo()){
					ncbInfoMapper(uloPersonalInfo, iibPersonalInfo);
				}
				
				decisionservice_iib.ApplicationDataM  iibApplication = ProcessUtil.filterIIBPersonalApplication(iibApplicationGroup.getApplications(), uloPersonalInfo.getPersonalId());
				if(null!=iibApplication){
					privilegeProjectCodeMapper(uloPersonalInfo, iibApplication);
				}	
			}
		}
	}	
	
	@Override
	public void ncbInfoMapper(PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo) throws Exception {	
		mapMainNcbInfo(uloPersonalInfo, iibPersonalInfo);
	}
	
	@Override
	public void mapPersonalInfo(PersonalInfoDataM uloPersonalInfo,decisionservice_iib.PersonalInfoDataM iibPersonalInfo) {
		uloPersonalInfo.setBureauRequiredFlag(iibPersonalInfo.getRequireBureau());
		if(null!=iibPersonalInfo.getTotalCCMainCard()&& !"".equals(iibPersonalInfo.getTotalCCMainCard())){
			uloPersonalInfo.setNoMainCard(DecisionServiceUtil.toString(iibPersonalInfo.getTotalCCMainCard()));
		}
		
		if(null!=iibPersonalInfo.getTotalCCSupCard() && !"".equals(iibPersonalInfo.getTotalCCSupCard())){
			uloPersonalInfo.setNoSupCard(DecisionServiceUtil.toString(iibPersonalInfo.getTotalCCSupCard()));
		}
	}
	@Override
	public void verificationResultMapper(ApplicationGroupDataM uloApplicationGroup, Object object,decisionservice_iib.VerificationResultDataM iibVerificationResult) throws Exception {
		 if(object instanceof PersonalInfoDataM){
			 logger.debug("##mapper ulo verification####");
			 PersonalInfoDataM uloPersonalInfo =(PersonalInfoDataM)object;
			 VerificationResultDataM uloVerificationResult = uloPersonalInfo.getVerificationResult();
			 if(null==uloVerificationResult){
				 uloVerificationResult = new VerificationResultDataM();
				 uloPersonalInfo.setVerificationResult(uloVerificationResult);
				 logger.debug("verification is null will create new !!");
			 }
			 mapPersonalVerificationResult(uloApplicationGroup,uloVerificationResult, iibVerificationResult);
		 }else if(object instanceof ApplicationDataM){
			 ApplicationDataM uloApplication = (ApplicationDataM)object;
			 VerificationResultDataM uloVerResult =uloApplication.getVerificationResult();
			 if(null==uloVerResult){
				 uloVerResult = new VerificationResultDataM();
				 uloApplication.setVerificationResult(uloVerResult);
			 }
			 mapApplicationVerificationResult(uloVerResult, iibVerificationResult);
		 }
	}
	@Override
	public void mapPersonalVerificationResult(ApplicationGroupDataM uloApplicationGroup
			,VerificationResultDataM uloVerResult,decisionservice_iib.VerificationResultDataM iibVerResult)throws Exception {
	}
}
