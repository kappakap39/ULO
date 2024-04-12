package com.eaf.service.common.iib.mapper.response;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.iib.model.ApplicationGroup;
import com.ava.flp.eapp.iib.model.Applications;
import com.ava.flp.eapp.iib.model.Doclists;
import com.ava.flp.eapp.iib.model.DocumentScenarios;
import com.ava.flp.eapp.iib.model.DocumentSuggestions;
import com.ava.flp.eapp.iib.model.IncomeCalculations;
import com.ava.flp.eapp.iib.model.IncomeSources;
import com.ava.flp.eapp.iib.model.KBankPayroll;
import com.ava.flp.eapp.iib.model.PersonalInfos;
import com.ava.flp.eapp.iib.model.VerificationResultApplications;
import com.ava.flp.eapp.iib.model.VerificationResultPersonalInfos;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IncomeCalculationDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.IncomeSourceDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.eapp.mapper.EDecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.DecisionServiceResponseMapper;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

public class IncomeCalculationDecisionServiceResponseMapper  extends EDecisionServiceResponseMapper{
	private static transient Logger logger = Logger.getLogger(EDecisionServiceResponseMapper.class);	
	private static String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public  void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		logger.debug("start applicationGroupMapper!!");
		personalInfoMapper(uloApplicationGroup, iibApplicationGroup);
		
		uloApplicationGroup.setRequireVerify(MConstant.FLAG_N);
		if(processRequireVerify(uloApplicationGroup.getPersonalInfos())){
			uloApplicationGroup.setRequireVerify(MConstant.FLAG_Y);
		}
	}
	String BORROWER = ServiceCache.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String SUPPLEMENTARY = ServiceCache.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");

	@Override
	public  void personalInfoMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getPersonalInfos() && iibApplicationGroup.getPersonalInfos().size()>0){
			for(PersonalInfos iibPersonalInfo : iibApplicationGroup.getPersonalInfos()){
				PersonalInfoDataM uloPersonalInfo = uloApplicationGroup.getPersonalById(iibPersonalInfo.getPersonalId());
				if(null==uloPersonalInfo){
					 logger.debug("Cant find personal id >>"+iibPersonalInfo.getPersonalId());
					 continue;
				}
				logger.debug("find ulo perosnal id >>"+iibPersonalInfo.getPersonalId());
				mapPersonalInfo(uloPersonalInfo, iibPersonalInfo);			
				
				if(!ServiceUtil.empty(iibPersonalInfo.getVerificationResult())){
					verificationResultMapper(uloApplicationGroup,uloPersonalInfo, null,iibPersonalInfo.getVerificationResult());
					if(!ServiceUtil.empty(iibPersonalInfo.getVerificationResult().getIncomeSources())){
						uloPersonalInfo.setIncomeSources(new ArrayList<IncomeSourceDataM>());
						for(IncomeSources iibIncomeSource : iibPersonalInfo.getVerificationResult().getIncomeSources()){
							mapIncomeSource(uloPersonalInfo,iibIncomeSource);
						}
					}						
				}else{
					logger.debug("iibPersonalInfo.getVerificationResult()  is null");
				}	
			}
		}
	}	
	
	@Override
	public  void verificationResultMapper(ApplicationGroupDataM uloApplicationGroup,Object object
			,VerificationResultApplications iibVerificationResult,VerificationResultPersonalInfos iibVerificationResultPerson) throws Exception{
		 logger.debug("##mapper ulo verification####");
		 PersonalInfoDataM uloPersonalInfo =(PersonalInfoDataM)object;
		 VerificationResultDataM uloVerificationResult = uloPersonalInfo.getVerificationResult();
		 if(null==uloVerificationResult){
			 uloVerificationResult = new VerificationResultDataM();
			 uloPersonalInfo.setVerificationResult(uloVerificationResult);
			 logger.debug("verification is null will create new !!");
		 }
		 mapPersonalVerificationResult(uloApplicationGroup,uloVerificationResult, iibVerificationResultPerson);
	}
	
	@Override
	public  void mapPersonalInfo(PersonalInfoDataM uloPersonalInfo,PersonalInfos iibPersonalInfo){
		logger.debug("-----map personal info------");
		uloPersonalInfo.setTotVerifiedIncome(iibPersonalInfo.getTotVerifiedIncome());
		uloPersonalInfo.setTypeOfFin(iibPersonalInfo.getTypeofFin());
		uloPersonalInfo.setBureauRequiredFlag(iibPersonalInfo.getRequireBureau());
		if(null!=iibPersonalInfo.getVerificationResult() && null!=iibPersonalInfo.getVerificationResult().getVerifiedIncomeSource() && !"".equals(iibPersonalInfo.getVerificationResult().getVerifiedIncomeSource())){
			uloPersonalInfo.setSorceOfIncome(iibPersonalInfo.getVerificationResult().getVerifiedIncomeSource());
		}	

		logger.debug("iibPersonalInfo.getTotVerifiedIncome()>>"+iibPersonalInfo.getTotVerifiedIncome());
		logger.debug("iibPersonalInfo.getTypeofFin()>>"+iibPersonalInfo.getTypeofFin());
	
		//Map KbankPayroll
		if(PERSONAL_RELATION_APPLICATION_LEVEL.equals(iibPersonalInfo.getPersonalType()))
		{
			logger.debug("Mapping KbankPayrollDataM paymentAccountNo to uloPersonalInfo payrollAccountNo ");
			KBankPayroll iibKPayroll = iibPersonalInfo.getKBankPayroll();
			if(iibKPayroll != null)
				uloPersonalInfo.setPayrollAccountNo(iibKPayroll.getPaymentAccountNo());
			{
			}
		}
	}
	@Override
	public  void mapPersonalVerificationResult(ApplicationGroupDataM uloApplicationGroup,VerificationResultDataM uloVerResult
			,VerificationResultPersonalInfos iibVerResult) throws Exception{
		
		logger.debug("mapping docuement complete flag");
		if(null!=iibVerResult.getDocCompletedFlag() && !"".equals(iibVerResult.getDocCompletedFlag())){
			uloVerResult.setDocCompletedFlag(iibVerResult.getDocCompletedFlag());	
		}
		if(null!=iibVerResult.getDocumentSuggestions() && iibVerResult.getDocumentSuggestions().size()>0){
			ArrayList<RequiredDocDataM> requireDocs = new ArrayList<RequiredDocDataM>();
			uloVerResult.setRequiredDocs(requireDocs);
			for(DocumentSuggestions iibDocSuggestions : iibVerResult.getDocumentSuggestions()){
				mapDocumentSuggestions(uloVerResult, iibDocSuggestions);
			}	
		}
		
		if(null != iibVerResult.getIncomeCalculations() && iibVerResult.getIncomeCalculations().size() > 0) {
			ArrayList<IncomeCalculationDataM> incomeCalculations = new ArrayList<IncomeCalculationDataM>();
			uloVerResult.setIncomeCalculations(incomeCalculations);
			for(IncomeCalculations iibIncomeCalculation : iibVerResult.getIncomeCalculations()) {
				mapIncomeCalculations(uloVerResult, iibIncomeCalculation);
			}	
		}
	}	

	@Override
	public  void mapIncomeSource(PersonalInfoDataM uloPersonalInfo,IncomeSources iibIncomeSource){
		IncomeSourceDataM uloIncomeSource = uloPersonalInfo.getIncomeSourceByType(iibIncomeSource.getIncomeSource());
		if(null!=uloIncomeSource){
			uloIncomeSource.setIncomeSource(iibIncomeSource.getIncomeSource());
		}else{
			uloIncomeSource = new IncomeSourceDataM();
			uloIncomeSource.setIncomeSource(iibIncomeSource.getIncomeSource());
			uloPersonalInfo.addIncomeSources(uloIncomeSource);
		}	
	}
	@Override
	public  void mapDocumentSuggestions(VerificationResultDataM uloVerResult,DocumentSuggestions iibDocSuggestions) {	
		if(null!=iibDocSuggestions.getDocumentScenarios()){
			for(DocumentScenarios iibDocScrnario : iibDocSuggestions.getDocumentScenarios()){
				RequiredDocDataM uloRequireDoc = new RequiredDocDataM();
				uloVerResult.addRequiredDocs(uloRequireDoc);
				
				uloRequireDoc.setScenarioType(iibDocSuggestions.getDocumentGroup());
				uloRequireDoc.setDocCompletedFlag(iibDocSuggestions.getDocCompletedFlag());
				uloRequireDoc.setDocumentGroupCode(DecisionServiceUtil.bigDecimalToString( iibDocScrnario.getScen()));
				mapDocumentScenarios(uloRequireDoc,iibDocScrnario);				
			}
			
		}else{
			logger.debug("iib DocumentScenarios is Null>>");
			if(null!=uloVerResult.getRequiredDocs() && uloVerResult.getRequiredDocs().size()>0){
				ArrayList<RequiredDocDataM> requireDocs = new ArrayList<RequiredDocDataM>();
				for(RequiredDocDataM requireDoc : uloVerResult.getRequiredDocs()){
					logger.debug("requireDoc.getScenarioType()>>"+requireDoc.getScenarioType());
					logger.debug("iibDocSuggestions.getDocumentGroup()>>"+iibDocSuggestions.getDocumentGroup());
					if(!requireDoc.getScenarioType().equals(iibDocSuggestions.getDocumentGroup())){
						RequiredDocDataM  requireDocument = new RequiredDocDataM();
						requireDocument.setScenarioType(iibDocSuggestions.getDocumentGroup());
						requireDocs.add(requireDocument);
					}else{
						requireDoc.setScenarioType(iibDocSuggestions.getDocumentGroup());
					}					
				}
				requireDocs.addAll(uloVerResult.getRequiredDocs());
				uloVerResult.setRequiredDocs(requireDocs);

			}else{
				RequiredDocDataM  requireDocument = new RequiredDocDataM();
				requireDocument.setScenarioType(iibDocSuggestions.getDocumentGroup());
				uloVerResult.addRequiredDocs(requireDocument);
			}
			
		}
	}

	@Override
	public  void mapDocumentScenarios(RequiredDocDataM  requireDocument,DocumentScenarios iibDocScenario) {
		if(null==iibDocScenario.getDoclists()){
			logger.debug("iibDocScenario.getDoclists() is null");
		}else{
			for(Doclists  iibDocList : iibDocScenario.getDoclists()){
				logger.debug("iibDocList.getDocumentType()>>"+iibDocList.getDocumentType());
				RequiredDocDetailDataM uloRequireDoc = requireDocument.filterRequiredDocDetail(iibDocList.getDocumentType());
				if(null==uloRequireDoc){
					uloRequireDoc = new RequiredDocDetailDataM();
					requireDocument.addRequiredDocDetails(uloRequireDoc);
				}
				uloRequireDoc.setDocumentCode(iibDocList.getDocumentType());
				uloRequireDoc.setMandatoryFlag(ProcessUtil.getULOBooleanFlag(iibDocScenario.getScenType()));
			}
		}
	}
	private boolean processRequireVerify(List<PersonalInfoDataM> personals){
		if ((personals == null) || (personals.isEmpty())) {
			return false;
		}
		for (PersonalInfoDataM person : personals) {
			if (person != null) {
				VerificationResultDataM ver = person.getVerificationResult();
				if (ver != null) {
					if ("Y".equalsIgnoreCase(ver.getRequiredVerCustFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerHrFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerPrivilegeFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerWebFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequiredVerIncomeFlag()) || 
							"Y".equalsIgnoreCase(ver.getRequirePriorityPass())) {
								return true;
					}
					
					if (!PersonalInfoDataM.PersonalType.INFO.equals(person.getPersonalType()) && 
							"N".equalsIgnoreCase(ver.getDocCompletedFlag())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
