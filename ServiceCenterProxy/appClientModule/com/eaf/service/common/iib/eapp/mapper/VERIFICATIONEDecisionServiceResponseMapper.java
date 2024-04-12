package com.eaf.service.common.iib.eapp.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.iib.model.ApplicationGroup;
import com.ava.flp.eapp.iib.model.Applications;
import com.ava.flp.eapp.iib.model.DocumentSuggestions;
import com.ava.flp.eapp.iib.model.IncomeSources;
import com.ava.flp.eapp.iib.model.PersonalInfos;
import com.ava.flp.eapp.iib.model.SavingAccountsPersonalInfos;
import com.ava.flp.eapp.iib.model.VerificationResultPersonalInfos;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.FinalVerifyResultDataM.VerifyId;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IncomeSourceDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.util.DecisionServiceCacheControl;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.util.ServiceUtil;

public class VERIFICATIONEDecisionServiceResponseMapper extends EDecisionServiceResponseMapper {
	private static transient Logger logger = Logger.getLogger(VERIFICATIONEDecisionServiceResponseMapper.class);
	private static String PERSONAL_RELATION_APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public void applicationGroupMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception {
		logger.debug("start applicationGroupMapper!!");
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
		
		uloApplicationGroup.setRequireVerify(MConstant.FLAG_N);
		if(processRequireVerify(uloApplicationGroup.getPersonalInfos())){
			uloApplicationGroup.setRequireVerify(MConstant.FLAG_Y);
		}
		
		if ( null != uloApplicationGroup.getSourceAction() && null != iibApplicationGroup.getProcessAction()
				&& !uloApplicationGroup.getSourceAction().equals( iibApplicationGroup.getProcessAction() ) ) {
			uloApplicationGroup.setSourceAction( iibApplicationGroup.getProcessAction() );
		}
	}
	public  void uloApplicationMapper(ApplicationGroupDataM uloApplicationGroup,ApplicationGroup iibApplicationGroup) throws Exception{
		if(null!=iibApplicationGroup.getApplications() && iibApplicationGroup.getApplications().size()>0){
			for(Applications iibApplication : iibApplicationGroup.getApplications()){
				ApplicationDataM uloApplication = uloApplicationGroup.getApplicationById(iibApplication.getApplicationRecordId());
				if(null!=uloApplication){					
					mapApplication(uloApplicationGroup, uloApplication, iibApplication);
				}else{
					logger.debug("iib application is null!!");
				}
				
			}
		} 
	}
	
	public  void mapApplication(ApplicationGroupDataM uloApplicationGroup ,ApplicationDataM uloApplication,Applications iibApplication){
		uloApplication.setApplicationRecordId(iibApplication.getApplicationRecordId());
		uloApplication.setApplicationType(ProcessUtil.setApplyType(iibApplication.getApplyType()));
		uloApplication.setIsVetoEligibleFlag(iibApplication.getIsVetoEligibleFlag());		
		uloApplication.setPolicyProgramId(iibApplication.getPolicyProgramId());
		uloApplication.setRecommendDecision(ProcessUtil.getULORecommendDecision(iibApplication.getRecommendDecision()));
		uloApplication.setLowIncomeFlag(iibApplication.getLowIncomeFlag());
		//#CR-ADD
		if(iibApplication.getRecommendProjectCode() != null && !iibApplication.getRecommendProjectCode().isEmpty()){
			uloApplication.setProjectCode(iibApplication.getRecommendProjectCode());
		}
		
		
		if(null!=iibApplication.getReExecuteKeyProgramFlag() && !"".equals(iibApplication.getReExecuteKeyProgramFlag())){
			uloApplication.setReExecuteKeyProgramFlag(iibApplication.getReExecuteKeyProgramFlag());
		}
	}
	
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
				incomeInfoMapper(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
							
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
				
				Applications  iibApplication = ProcessUtil.filterIIBPersonalApplications(iibApplicationGroup.getApplications(), uloPersonalInfo.getPersonalId());
				if(!ServiceUtil.empty(iibApplication)){
					privilegeProjectCodeMapper(uloPersonalInfo, iibApplication);
				}
				if(!ServiceUtil.empty(iibPersonalInfo.getSavingAccounts())){
					for(SavingAccountsPersonalInfos iibSavingAccount : iibPersonalInfo.getSavingAccounts()){	
						savingAccountMapper(uloPersonalInfo, iibSavingAccount);
					}
				}
			}
		}
	}	
	
	@Override
	public void  incomeInfoMapper(ApplicationGroupDataM uloApplicationGroup,PersonalInfoDataM uloPersonalInfo, PersonalInfos iibPersonalInfo)  throws Exception{
		//map income at  decision point  in DE1 and Income service  : new CR  in file  Change FollowUp_20170327	
		try {
			mapperIncomeInfo(uloApplicationGroup, uloPersonalInfo, iibPersonalInfo);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	@Override
	public  void mapPersonalVerificationResult(ApplicationGroupDataM uloApplicationGroup,VerificationResultDataM uloVerResult
			,VerificationResultPersonalInfos iibVerResult) throws Exception{
		
//		logger.debug("iibVerResult>>"+new Gson().toJson(iibVerResult));
		// Map doc complete
		uloVerResult.setDocCompletedFlag(iibVerResult.getDocCompletedFlag());
		uloVerResult.setRequirePriorityPass(iibVerResult.getRequiredPriorityPass());
		uloVerResult.setRequiredVerCustFlag(iibVerResult.getRequiredVerCustFlag());
		uloVerResult.setRequiredVerHrFlag(iibVerResult.getRequiredVerHrFlag());
		uloVerResult.setRequiredVerIncomeFlag(iibVerResult.getRequiredVerIncomeFlag());
		uloVerResult.setRequiredVerPrivilegeFlag(iibVerResult.getRequiredVerPrivilegeFlag());
		
		if(null!=uloApplicationGroup.getApplications() && uloApplicationGroup.getApplications().size()>0){
			logger.debug("<<VERIFICATIONEDecisionServiceResponseMapper foundReExecutionFla>>");
			if(!foundReExecutionFlag(uloApplicationGroup.getApplications())){
				uloVerResult.setRequiredVerWebFlag(iibVerResult.getRequiredVerWebFlag());
			}
		}else{
			uloVerResult.setRequiredVerWebFlag(iibVerResult.getRequiredVerWebFlag());
		}

		// Set require Customer Verification Flag
		boolean completedCusVer = ProcessUtil.isCompletedVerification(uloApplicationGroup, VerifyId.VERIFY_CUSTOMER);//ST000000000023 : Update only not completed, I0048 Also
		if(!completedCusVer){
			if (ProcessUtil.YES_FLAG.equalsIgnoreCase(uloVerResult.getRequiredVerCustFlag())) {
				uloVerResult.setVerCusResult(ProcessUtil.verRequiredDesc);
				uloVerResult.setVerCusResultCode(ProcessUtil.verRequiredCode);
				
				//uloVerResult.setIndentifyQuesitionSets((ArrayList<IdentifyQuestionSetDataM>) ProcessUtil.mergeCustomerQuestionSet(uloVerResult.getIndentifyQuesitionSets(), iibVerResult.getQuestionSets()));
				
				//Add new or replace questions by set codes
				String questionType = DecisionServiceCacheControl.getConstant("QUESTION_SET_TYPE_C");
				//uloVerResult.setIndentifyQuestions((ArrayList<IdentifyQuestionDataM>) ProcessUtil.replaceQuestionByType(uloVerResult.getIndentifyQuesitionSets(), uloVerResult.getIndentifyQuestions(), questionType));
				uloVerResult.setIndentifyQuestions((ArrayList<IdentifyQuestionDataM>) ProcessUtil.replaceQuestionByType(uloVerResult.getIndentifyQuesitionSets(), uloVerResult.getIndentifyQuestions(), questionType, uloApplicationGroup.getReprocessFlag(), VerifyId.VERIFY_CUSTOMER));
			}else {
				uloVerResult.setRequiredVerCustFlag(DecisionServiceUtil.FLAG_N);
				uloVerResult.setVerCusResult(ProcessUtil.verWaivedDesc);
				uloVerResult.setVerCusResultCode(ProcessUtil.verWaivedCode);
				logger.debug("ReprocessFlag : "+uloApplicationGroup.getReprocessFlag());
				if((ProcessUtil.YES_FLAG.equals(uloApplicationGroup.getReprocessFlag()))){
					uloVerResult.setIndentifyQuesitionSets(null);
					uloVerResult.setIndentifyQuestions(null);
					uloVerResult.setVerCusComplete(null);
				}
			}
		}

		// Set require HR Verification Flag
		boolean completedHrVer = ProcessUtil.isCompletedVerification(uloApplicationGroup, VerifyId.VERIFY_HR);//ST000000000023 : Update only not completed, I0048 Also
		logger.debug("completedHrVer : "+completedHrVer);		
		if(!completedHrVer){
			if (DecisionServiceUtil.FLAG_Y.equalsIgnoreCase(uloVerResult.getRequiredVerHrFlag())) {
				uloVerResult.setVerHrResult(ProcessUtil.verRequiredDesc);
				uloVerResult.setVerHrResultCode(ProcessUtil.verRequiredCode);
		//		uloVerResult.setIndentifyQuesitionSets((ArrayList<IdentifyQuestionSetDataM>) ProcessUtil.mergeHRQuestionSet(uloVerResult.getIndentifyQuesitionSets(), iibVerResult.getIndentifyQuesitionSets()));
				
				//Add new or replace questions by set codes
				String questionType = DecisionServiceCacheControl.getConstant("QUESTION_SET_TYPE_HR");;
				uloVerResult.setIndentifyQuestions((ArrayList<IdentifyQuestionDataM>) ProcessUtil.replaceQuestionByType(uloVerResult.getIndentifyQuesitionSets(), uloVerResult.getIndentifyQuestions(), questionType));

			} else {
				uloVerResult.setRequiredVerHrFlag(DecisionServiceUtil.FLAG_N);
				uloVerResult.setVerHrResult(ProcessUtil.verWaivedDesc);
				uloVerResult.setVerHrResultCode(ProcessUtil.verWaivedCode);
			}

		} 
		
		// Map require income verification flag
		if (DecisionServiceUtil.FLAG_Y.equals(uloVerResult.getRequiredVerIncomeFlag())) {
			uloVerResult.setSummaryIncomeResult(ProcessUtil.verRequiredDesc);
			uloVerResult.setSummaryIncomeResultCode(ProcessUtil.verRequiredCode);
		} else {			
			if(!ProcessUtil.isCompletedVerification(uloApplicationGroup, VerifyId.VERIFY_INCOME)){//Refer to I0048 : Correct verification result logic
				uloVerResult.setSummaryIncomeResult(ProcessUtil.verWaivedDesc);
				uloVerResult.setSummaryIncomeResultCode(ProcessUtil.verWaivedCode);				
				uloVerResult.setRequiredVerIncomeFlag(DecisionServiceUtil.FLAG_N);
			}								
		}

		//Map required ver web  flag
		if(null!=iibVerResult.getWebVerifications()&& iibVerResult.getWebVerifications().size()>0 
				&& DecisionServiceUtil.FLAG_Y.equals(iibVerResult.getRequiredVerWebFlag())){
			mapVerWebVerification(uloVerResult, iibVerResult.getWebVerifications(), uloApplicationGroup);
		}else{
			logger.debug("webverification is null!!");
			boolean isCompleted = ProcessUtil.isCompletedVerification(uloApplicationGroup, VerifyId.VERIFY_WEB);	
			logger.debug("isVerWeb Completed>>>"+isCompleted);
			if(!isCompleted){
				if(DecisionServiceUtil.FLAG_N.equals(iibVerResult.getRequiredVerWebFlag())){
					uloVerResult.setVerWebResult(ProcessUtil.verWaivedDesc);
					uloVerResult.setVerWebResultCode(ProcessUtil.verWaivedCode);
					uloVerResult.setWebVerifications(new ArrayList<WebVerificationDataM>());
				}
			}
		}
		
//		if(null!=iibVerResult.getPriorityPassVerification() && iibVerResult.getPriorityPassVerification().size()>0){
//			for(decisionservice_iib.PriorityPassVerificationTypeDataM iibPriorityPass : iibVerResult.getPriorityPassVerification()){
//				mapPriorityPassVerification(uloVerResult,iibPriorityPass);
//			}
//		}
		
		
		if(null!=iibVerResult.getDocumentSuggestions() && iibVerResult.getDocumentSuggestions().size()>0){
			ArrayList<RequiredDocDataM> requireDocs = new ArrayList<RequiredDocDataM>();
			uloVerResult.setRequiredDocs(requireDocs);
			for(DocumentSuggestions iibDocSuggestions : iibVerResult.getDocumentSuggestions()){
				mapDocumentSuggestions(uloVerResult, iibDocSuggestions);
			}	
		}
		
		// Map require privilege verification flag
		if (DecisionServiceUtil.FLAG_Y.equals(uloVerResult.getRequiredVerPrivilegeFlag())) {
			uloVerResult.setVerPrivilegeResult(ProcessUtil.verRequiredDesc);
			uloVerResult.setVerPrivilegeResultCode(ProcessUtil.verRequiredCode);
			ProcessUtil.addImageSplitAndRequiredDocCaseRequirePrivilegeVer(uloApplicationGroup);
		} else {
			uloVerResult.setRequiredVerPrivilegeFlag(DecisionServiceUtil.FLAG_N);
			uloVerResult.setVerPrivilegeResult(ProcessUtil.verWaivedDesc);
			uloVerResult.setVerPrivilegeResultCode(ProcessUtil.verWaivedCode);
			logger.debug("reprocessFlag : "+uloApplicationGroup.getReprocessFlag());
			if(DecisionServiceUtil.FLAG_Y.equals(uloApplicationGroup.getReprocessFlag())){
				uloVerResult.setPrivilegeProjectCodes(null);
			}
		}
	}	
	
	public boolean foundReExecutionFlag(ArrayList<ApplicationDataM> applications){
		if(!ServiceUtil.empty(applications)){
			for(ApplicationDataM application:applications) {
				if(!ServiceUtil.empty(application.getReExecuteKeyProgramFlag())) {
					logger.debug("foundReExecutionFlag : found reExecutionFlag ");
					return true;
				}
			}
		}
		logger.debug("foundReExecutionFlag :not found reExecutionFlag ");
		return false;
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
