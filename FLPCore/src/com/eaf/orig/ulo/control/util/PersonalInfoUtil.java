package com.eaf.orig.ulo.control.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.FieldConfigDataM;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.j2ee.pattern.control.ProcessForm;
import com.eaf.orig.shared.model.Age;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentRelationDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.CISCustomerDataM;
import com.eaf.orig.ulo.model.search.SearchApplicantDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class PersonalInfoUtil {
	private static transient Logger logger = Logger.getLogger(PersonalInfoUtil.class);
	private static String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	private static String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	private static String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");	
	private static String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");	
	private static String IM_PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("IM_PERSONAL_TYPE_SUPPLEMENTARY");	
	private static String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");	
	private static String DOC_RELATION_REF_LEVEL_PERSONAL = SystemConstant.getConstant("DOC_RELATION_REF_LEVEL_PERSONAL");	
	private static String TITLE_BOY = SystemConstant.getConstant("TITLE_BOY");
	private static String TITLE_GIRL = SystemConstant.getConstant("TITLE_GIRL");
	private static String TITLE_MASTER = SystemConstant.getConstant("TITLE_MASTER");
	private static String TITLE_MISS = SystemConstant.getConstant("TITLE_MISS");
	private static String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
	private static String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
	private static String CIDTYPE_MIGRANT = SystemConstant.getConstant("CIDTYPE_MIGRANT");
	private static String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	private static String FLAG_NO = SystemConstant.getConstant("FLAG_NO");
	private static String PERSONAL_ERROR_CIS_DUPLICATE = SystemConstant.getConstant("PERSONAL_ERROR_CIS_DUPLICATE");
	private static String PERSONAL_ERROR_DIH_FAILED = SystemConstant.getConstant("PERSONAL_ERROR_DIH_FAILED");
	private static String TH_LAST_NAME = SystemConstant.getConstant("TH_LAST_NAME");
	private static String EN_LAST_NAME = SystemConstant.getConstant("EN_LAST_NAME");
    private static String EN_FIRST_NAME = SystemConstant.getConstant("EN_FIRST_NAME");
	private static String TH_FIRST_NAME = SystemConstant.getConstant("TH_FIRST_NAME");
	public static PersonalInfoDataM getApplicationTypePersonalInfo(ApplicationGroupDataM applicationGroup){
		PersonalInfoDataM  personalInfo = new PersonalInfoDataM();
		try{
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			if(Util.empty(personalInfo)){
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}	
		return personalInfo;
	}
	
	public static String getIMPersonalType(ApplicationGroupDataM applicationGroup) {
		PersonalInfoDataM personalInfo = getApplicationTypePersonalInfo(applicationGroup);
		if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())) {
			return IM_PERSONAL_TYPE_APPLICANT;
		} else {
			List<PersonalInfoDataM> personalList = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
			return IM_PERSONAL_TYPE_SUPPLEMENTARY+personalList.size();
		}
	}
	public static String getIMPersonalType(PersonalInfoDataM personalInfo) {
		if(!Util.empty(personalInfo)){
			if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())) {
				return IM_PERSONAL_TYPE_APPLICANT;
			} else {
				int seq = personalInfo.getSeq();
//				if(seq == 0 || seq == 1) {
//					seq = 1;
//				} else {
//					seq = seq -1;
//				}
				return IM_PERSONAL_TYPE_SUPPLEMENTARY+seq;
			}
		}
		return "";
	}
	public static String getIMPersonalType(String personalType ,int seq) {
		if(PERSONAL_TYPE_APPLICANT.equals(personalType)) {
			return IM_PERSONAL_TYPE_APPLICANT;
		} else {
//			if(seq == 0 || seq == 1) {
//				seq = 1;
//			} else {
//				seq = seq -1;
//			}
			return IM_PERSONAL_TYPE_SUPPLEMENTARY+seq;
		}
	}
	
	public static PersonalInfoDataM getMainPersonalInfo(ApplicationGroupDataM applicationGroup){
		PersonalInfoDataM  personalInfo = new PersonalInfoDataM();
		try{
			if(!Util.empty(applicationGroup)){
				String applicationType = applicationGroup.getApplicationType();
				if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
					personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_INFO);
				}else{
					personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
				}	
			}			
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}	
		return personalInfo;
	}
	
	public static void defaultPersonalIdOfImage(ApplicationGroupDataM applicationGroup,String personalId,String personalType,int personalSeq){
		logger.debug("personalId >> "+personalId);
		logger.debug("personalType >> "+personalType);
		logger.debug("personalSeq >> "+personalSeq);
		ArrayList<ApplicationImageDataM> applicationImages = applicationGroup.getApplicationImages();
		if(!Util.empty(applicationImages)){
			for (ApplicationImageDataM applicationImage : applicationImages) {
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
				if(!Util.empty(applicationImageSplits)){
					for (ApplicationImageSplitDataM applicationImageSplit : applicationImageSplits) {
						String applicantTypeIM = applicationImageSplit.getApplicantTypeIM();
						logger.debug("applicantTypeIM >> "+applicantTypeIM);
						if(!Util.empty(applicantTypeIM)){
							if(applicantTypeIM.contains(IM_PERSONAL_TYPE_APPLICANT) && PERSONAL_TYPE_APPLICANT.equals(personalType)){
								logger.debug("IM_PERSONAL_TYPE_APPLICANT");
								applicationImageSplit.setPersonalId(personalId);
							}else if(applicantTypeIM.contains(IM_PERSONAL_TYPE_SUPPLEMENTARY) && PERSONAL_TYPE_SUPPLEMENTARY.equals(personalType)){
								logger.debug("IM_PERSONAL_TYPE_SUPPLEMENTARY");
								String comparePersonalType = IM_PERSONAL_TYPE_SUPPLEMENTARY+String.valueOf(personalSeq);
								logger.debug("comparePersonalType >> "+comparePersonalType);
								if(comparePersonalType.equals(applicantTypeIM)){
									applicationImageSplit.setPersonalId(personalId);
								}
							}
						}
					}				
				}
			}
		}	
		
		defaultPersonalDocumentCheckListRelation(applicationGroup,personalId,personalType,personalSeq);
	}
	
	public static void defaultPersonalDocumentCheckListRelation(ApplicationGroupDataM applicationGroup,String personalId,String personalType,int personalSeq) {
		String applicantTypeIM = getIMPersonalType(personalType,personalSeq);
		logger.debug("applicantTypeIM>>"+applicantTypeIM);
		ArrayList<DocumentCheckListDataM>  documentCheckLists = applicationGroup.getDocumentCheckListApplicantTypeIM(applicantTypeIM);
		logger.debug("documentCheckLists>>"+documentCheckLists.size());
		if(!Util.empty(documentCheckLists)){
			for(DocumentCheckListDataM documentCheckList : documentCheckLists){
				logger.debug("documentCheckList.getDocCheckListId()>>"+documentCheckList.getDocCheckListId());
				ArrayList<DocumentRelationDataM> documentRelations = new ArrayList<DocumentRelationDataM>();
				String documentCheckListId = documentCheckList.getDocCheckListId();
				DocumentRelationDataM documentRelationDataM = new DocumentRelationDataM();
				documentRelationDataM.setDocCheckListId(documentCheckListId);
				documentRelationDataM.setRefId(personalId);
				documentRelationDataM.setRefLevel(DOC_RELATION_REF_LEVEL_PERSONAL);
				documentRelations.add(documentRelationDataM);
				documentCheckList.setDocumentRelations(documentRelations);
			}
		}
	}
	public static void clearCompareDataByPersonalId(String personalId,PersonalInfoDataM personalInfo,HttpServletRequest request){
		logger.debug("clearCompareDataByPersonalId...");
		logger.debug("personalId >>"+personalId);	
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		HashMap<String, CompareDataM> comparisonFields = applicationGroup.getComparisonField(CompareDataM.SoruceOfData.TWO_MAKER);
		logger.debug("comparisonFields >> "+comparisonFields);
		if(null != comparisonFields){
			for(Iterator<Map.Entry<String, CompareDataM>> it = comparisonFields.entrySet().iterator(); it.hasNext();) {
			      Map.Entry<String, CompareDataM> entry = it.next();
			      CompareDataM compareData = entry.getValue();
			      String uniqueId = compareData.getUniqueId();
			      if(personalId.equals(uniqueId)) {
			    	  it.remove();
			      }
			}
		}
		
	}
	
	public static void clearCompareDataCISByPersonalId(String personalId,PersonalInfoDataM personalInfo,HttpServletRequest request){
		logger.debug("clearCompareDataByPersonalId...");
		logger.debug("personalId >>"+personalId);	
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		HashMap<String, CompareDataM> comparisonFields = applicationGroup.getComparisonField(CompareDataM.SoruceOfData.CIS);
		logger.debug("comparisonFields >> "+comparisonFields);
		if(null != comparisonFields){
			for(Iterator<Map.Entry<String, CompareDataM>> it = comparisonFields.entrySet().iterator(); it.hasNext();) {
			      Map.Entry<String, CompareDataM> entry = it.next();
			      CompareDataM compareData = entry.getValue();
			      String uniqueId = compareData.getUniqueId();
			      if(personalId.equals(uniqueId)) {
			    	  it.remove();
			      }
			}
		}
		
	}
	
	public static void clearNotMatchSoruceOfDataCis(HttpServletRequest request){
		logger.debug("clearNotMatchSoruceOfDataCis..");
		ArrayList<String> uniqueIds = new ArrayList<String>();
		Object masterObjectForm = FormControl.getMasterObjectForm(request);
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
			if(null != personalInfos){
				for (PersonalInfoDataM personalInfo : personalInfos) {
//					String refId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
					uniqueIds.add(CompareSensitiveFieldUtil.generateCISUniqueObjectId(personalInfo));
				}
			}
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplication = (PersonalApplicationInfoDataM)masterObjectForm;
			ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
			if(null != personalInfos){
				for (PersonalInfoDataM personalInfo : personalInfos) {
//					String refId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
					uniqueIds.add(CompareSensitiveFieldUtil.generateCISUniqueObjectId(personalInfo));
				}
			}
			PersonalInfoDataM personalInfoItem = personalApplication.getPersonalInfo();
			if(null != personalInfoItem){
//				String refId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfoItem);
				uniqueIds.add(CompareSensitiveFieldUtil.generateCISUniqueObjectId(personalInfoItem));
			}
		}
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		logger.debug("filterPersonalInfos.size() >> "+uniqueIds.size());
		HashMap<String, CompareDataM> comparisonFields = applicationGroup.getComparisonField(CompareDataM.SoruceOfData.CIS);
		logger.debug("comparisonFields >> "+comparisonFields);
		if(null != comparisonFields){
			for(Iterator<Map.Entry<String, CompareDataM>> it = comparisonFields.entrySet().iterator(); it.hasNext();) {
			      Map.Entry<String, CompareDataM> entry = it.next();
			      CompareDataM compareData = entry.getValue();
			      FieldConfigDataM filedConfigData = CompareSensitiveFieldUtil.JSonStringToFieldConfigDataM(compareData.getConfigData());
			      String uniqueId = CompareSensitiveFieldUtil.generateCISUniqueObjectId(filedConfigData);
			      if(!uniqueIds.contains(uniqueId)) {
			    	  it.remove();
			      }
			}
		}
	}
	
	public static boolean isPersonalApplicant(ArrayList<PersonalInfoDataM> personalInfos){
		boolean isPersonalApplicant = false;
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo : personalInfos){
	 			String PERSONAL_TYPE = personalInfo.getPersonalType();
	 			if(!Util.empty(PERSONAL_TYPE) && PERSONAL_TYPE.equals(PERSONAL_TYPE_APPLICANT)){
	 				isPersonalApplicant = true;
	 			}
			}
		}
		logger.debug("isPersonalApplicant >> "+isPersonalApplicant);
		return isPersonalApplicant;
	}
	public static PersonalInfoDataM getPersonalInfoObjectForm(HttpServletRequest request){
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = new PersonalInfoDataM();
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			logger.debug("instanceof >> ApplicationGroupDataM");
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
			logger.debug("instanceof >> PersonalApplicationInfoDataM");
		}
		return personalInfo;
	}
	
	public static boolean isBundingTemplate(ApplicationGroupDataM applicationGroup){
		boolean isBundingTemplate = false;
		
		String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
		String BUNDING_K_SME = SystemConstant.getConstant("BUNDING_K_SME");
		String BUNDING_HL = SystemConstant.getConstant("BUNDING_HL");
		
		ArrayList<String> templateBundingSME =  CacheControl.getCacheNameList(CACHE_TEMPLATE, BUNDING_K_SME, "BUNDING", "TEMPLATE_ID");
		ArrayList<String> templateBundingHL =  CacheControl.getCacheNameList(CACHE_TEMPLATE, BUNDING_HL, "BUNDING", "TEMPLATE_ID");
		String template = applicationGroup.getApplicationTemplate();
//		Gson gson = new Gson();
//		logger.debug("templateBundingSME >> "+gson.toJson(templateBundingSME));
//		logger.debug("templateBundingHL >> "+gson.toJson(templateBundingHL));
		
		if(templateBundingSME.contains(template) || templateBundingHL.contains(template)){
			isBundingTemplate = true;
		}
		logger.debug("isBundingTemplate >> "+isBundingTemplate);
		return isBundingTemplate;
	}
		
	public static boolean isNullIncomeAllField(PersonalInfoDataM personalInfo){
		if((Util.empty(personalInfo.getSalary()) ||BigDecimal.ZERO==personalInfo.getSalary()) 
			&& (Util.empty(personalInfo.getCirculationIncome()) || BigDecimal.ZERO==personalInfo.getCirculationIncome())
		   &&  (Util.empty(personalInfo.getNetProfitIncome()) || BigDecimal.ZERO==personalInfo.getNetProfitIncome()) 
		   && (Util.empty(personalInfo.getFreelanceIncome()) || BigDecimal.ZERO==personalInfo.getFreelanceIncome())){
			return true;
		}
		return false;
	}
	
	public static boolean validateAge(int age,PersonalInfoDataM personalInfo){
		if(!Util.empty(personalInfo.getBirthDate()) && !Util.empty(personalInfo.getThTitleCode())){
			Age agePerson = Util.age(personalInfo.getBirthDate(), Util.getCurrentDate());
			String TITLECODE = personalInfo.getThTitleCode();
			if(agePerson.getYears() > age && (TITLE_BOY.equals(TITLECODE) || TITLE_GIRL.equals(TITLECODE)
				|| TITLE_MASTER.equals(TITLECODE) || TITLE_MISS.equals(TITLECODE))){
				return true;
			}		
		}
		return false;
	}
	
	public static String getIdFieldExpireDocument(String cidType,PersonalInfoDataM personalInfo){
		String PREFIX_ID_FIELD = "IDNO";
		if(CIDTYPE_PASSPORT.equals(cidType)){
			PREFIX_ID_FIELD = "PASSPORT";
		}else if(CIDTYPE_MIGRANT.equals(cidType)){
			PREFIX_ID_FIELD = "ALIEN";
		}
		return PREFIX_ID_FIELD+"_EXPIRE_DATE_"+FormatUtil.getPersonalElementId(personalInfo);
	}
	
	public static void setPersonalInfoForSearch(HashMap<String, Object> tableResult) {
		try {
			
			String applicationGroupId = (String) tableResult.get("APPLICATION_GROUP_ID");
			List<SearchApplicantDataM> personalList = ORIGDAOFactory.getSearchApplicantDAO().loadApplicantForSearch(applicationGroupId);
			List<SearchApplicantDataM> personalApplicantList = new ArrayList<SearchApplicantDataM>();
			List<SearchApplicantDataM> personalSupplementaryList = new ArrayList<SearchApplicantDataM>();
			if(!Util.empty(personalList)) {
				for(SearchApplicantDataM searchApplicantM : personalList) {
					
					String personalType = searchApplicantM.getPersonalType();
					if(PERSONAL_TYPE_APPLICANT.equals(personalType)) {
						personalApplicantList.add(searchApplicantM);
					} else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalType)) {
						personalSupplementaryList.add(searchApplicantM);
					}
				}
			}
			tableResult.put("PERSONAL_APPLICANT_LIST", personalApplicantList);
			tableResult.put("PERSONAL_SUPPLEMENTARY_LIST", personalSupplementaryList);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	}
	public static String getBranchReceiceName(String RC_CODE,String BRANCH_NAME_CODE){
		String BRANCH_NAME = "";
		if(!Util.empty(RC_CODE) && !Util.empty(BRANCH_NAME_CODE)){
				BRANCH_NAME = BRANCH_NAME_CODE.replaceAll(RC_CODE, "");
			if(BRANCH_NAME.length()>=1 && BRANCH_NAME.charAt(0) == ' '){
				BRANCH_NAME = BRANCH_NAME.substring(1);
			}
		}
		return BRANCH_NAME;
	}	
	public static void clearDocumentList(PersonalInfoDataM personalInfo){
		logger.debug("clearDocumentList");
		String COMPARE_SIGNATURE_NO = SystemConstant.getConstant("COMPARE_SIGNATURE_NO");
		ArrayList<RequiredDocDataM> requiredDocs = new ArrayList<RequiredDocDataM>();
		VerificationResultDataM verfification = personalInfo.getVerificationResult();
		if(Util.empty(verfification)){
			verfification = new VerificationResultDataM();
			personalInfo.setVerificationResult(verfification);
		}
		verfification.setRequiredDocs(requiredDocs);
		verfification.setDocCompletedFlag(null);
		verfification.setCompareSignatureResult(COMPARE_SIGNATURE_NO);
	}
	public static boolean isDuplicateCis(ArrayList<PersonalInfoDataM> personalInfos){
		for(PersonalInfoDataM personalInfo : personalInfos){
			if(!Util.empty(personalInfo)){
				String PERSONAL_ERROR = personalInfo.getPersonalError();
				logger.debug("PERSONAL_ERROR : "+PERSONAL_ERROR);
				if(!Util.empty(PERSONAL_ERROR) && PERSONAL_ERROR_CIS_DUPLICATE.equals(PERSONAL_ERROR)){
					return true;
				}
			}
		}
		return false;
	}
	public static PersonalInfoDataM getPersonalInfoByProcessForm(String processForm,HashMap<String,String> requestData,ApplicationGroupDataM applicationGroup){
		if(ProcessForm.SUP_APPLICANT_VALIDATE.equals(processForm)){
			String personalId = requestData.get("PERSONAL_ID");
			if(!Util.empty(personalId)){
				return applicationGroup.getPersonalById(personalId);
			}
		}
		return null;
	}
	public static DIHQueryResult<String> getCisNo(String CID_TYPE,String ID_NO,String TH_FIRST_NAME,String TH_LAST_NAME,String EN_BIRTH_DATE) throws Exception{
		String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
		String CIDTYPE_MIGRANT = SystemConstant.getConstant("CIDTYPE_MIGRANT");	
		String cisNo = "";
		logger.info("CID_TYPE : "+CID_TYPE);
		logger.info("ID_NO : "+ID_NO);
		logger.info("TH_FIRST_NAME : "+TH_FIRST_NAME);
		logger.info("TH_LAST_NAME : "+TH_LAST_NAME);
		logger.info("EN_BIRTH_DATE : "+EN_BIRTH_DATE);
		DIHQueryResult<String> dihDataResult = new DIHQueryResult<>();
		DIHProxy dihProxy = new DIHProxy();
		String foundDataCisMore1RowFlag = FLAG_NO;
		try{
			dihDataResult.setStatusCode(ResponseData.SUCCESS);
			dihDataResult.setResult("");
			//DIHQueryResult<String> dihMoreThanRows = dihProxy.foundMoreThanRows(CID_TYPE, ID_NO,EN_BIRTH_DATE);
			DIHQueryResult<ArrayList<CISCustomerDataM>> dihMoreThanRows = dihProxy.foundMoreThanRows(CID_TYPE, ID_NO,EN_BIRTH_DATE);
			logger.info("dihMoreThanRows : "+dihMoreThanRows);
			if(ResponseData.SUCCESS.equals(dihMoreThanRows.getStatusCode())||ResponseData.WARNING.equals(dihMoreThanRows.getStatusCode())){
				if(dihMoreThanRows.getResult().size()>1){
					foundDataCisMore1RowFlag = FLAG_YES;
				}
				//foundDataCisMore1RowFlag = dihMoreThanRows.getResult();
				logger.info("foundDataCisMore1RowFlag : "+foundDataCisMore1RowFlag);
				if(FLAG_NO.equals(foundDataCisMore1RowFlag)){
					/*DIHQueryResult<String>  dihCisNumber= dihProxy.getCIS_NUMBER(CID_TYPE, ID_NO);
					logger.info("dihCisNumber : "+dihCisNumber);
					if(!ResponseData.SUCCESS.equals(dihCisNumber.getStatusCode())){
						dihDataResult.setStatusCode(dihCisNumber.getStatusCode());
						dihDataResult.setErrorData(dihCisNumber.getErrorData());
						return dihDataResult;
					}*/
					ArrayList<CISCustomerDataM> cisCustomers = dihMoreThanRows.getResult();
					if(!Util.empty(cisCustomers)){
						CISCustomerDataM cisCustomer = cisCustomers.get(0);
						if(!Util.empty(cisCustomers)){
							cisNo = cisCustomer.getIpId();
						}
					}
					logger.debug("cisNo : "+cisNo);
					if(Util.empty(cisNo)&& ResponseData.WARNING.equals(dihMoreThanRows.getStatusCode()) && (CIDTYPE_PASSPORT.equals(CID_TYPE)||CIDTYPE_MIGRANT.equals(CID_TYPE))){
						DIHQueryResult<String> dihFoundMoreRows = dihProxy.foundMoreThanRows(CID_TYPE,TH_FIRST_NAME, TH_LAST_NAME,EN_BIRTH_DATE);
						if(ResponseData.SUCCESS.equals(dihFoundMoreRows.getStatusCode())){
							foundDataCisMore1RowFlag = dihFoundMoreRows.getResult();
							logger.info("foundDataCisMore1RowFlag : "+foundDataCisMore1RowFlag);
							if(FLAG_NO.equals(foundDataCisMore1RowFlag)){
								DIHQueryResult<String> dihCisNumberResult = dihProxy.getCIS_NUMBER(CID_TYPE,TH_FIRST_NAME, TH_LAST_NAME, EN_BIRTH_DATE);
								if(ResponseData.SUCCESS.equals(dihCisNumberResult.getStatusCode())){
									logger.debug("cisNo : "+dihCisNumberResult.getResult());
									dihDataResult.setResult(dihCisNumberResult.getResult());
									return dihDataResult;
								}else{
									dihDataResult.setStatusCode(dihCisNumberResult.getStatusCode());
									dihDataResult.setErrorData(dihCisNumberResult.getErrorData());
									return dihDataResult;
								}
							}else if(FLAG_YES.equals(foundDataCisMore1RowFlag)){
								dihDataResult.setStatusCode(ResponseData.BUSINESS_EXCEPTION);
								dihDataResult.setResult(FLAG_YES);
								dihDataResult.setErrorData(ErrorController.error(ResponseData.SystemType.FLP, ErrorData.ErrorType.DATA_INCORRECT, MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW")));
								return dihDataResult;
							}
						}else{
							dihDataResult.setStatusCode(dihFoundMoreRows.getStatusCode());
							dihDataResult.setErrorData(dihFoundMoreRows.getErrorData());
							return dihDataResult;
						}
					}else{
						//dihDataResult.setResult(dihCisNumber.getResult());
						dihDataResult.setResult(cisNo);
						return dihDataResult;
					}
				}else if(FLAG_YES.equals(foundDataCisMore1RowFlag)){
						dihDataResult.setStatusCode(ResponseData.BUSINESS_EXCEPTION);
						dihDataResult.setResult(FLAG_YES);
						dihDataResult.setErrorData(ErrorController.error(ResponseData.SystemType.FLP, ErrorData.ErrorType.DATA_INCORRECT, MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW")));
						return dihDataResult;
				}
			}else{
				dihDataResult.setStatusCode(dihMoreThanRows.getStatusCode());
				dihDataResult.setErrorData(dihMoreThanRows.getErrorData());
				return dihDataResult;
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			dihDataResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			dihDataResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH, ErrorData.ErrorType.SYSTEM_ERROR, e));
		}
		return dihDataResult;
	}
	public static CisCustomerResult getCisNoResult(String CID_TYPE,String ID_NO,String TH_FIRST_NAME,String TH_LAST_NAME,String EN_BIRTH_DATE)throws Exception{
		CisCustomerResult cisCustomerResult = new PersonalInfoUtil().new CisCustomerResult();
		String cisNo = "";
		DIHQueryResult<String> dihDataResult = new DIHQueryResult<>();
		Gson gson = new Gson(); 
		try{
			dihDataResult.setStatusCode(ResponseData.SUCCESS);
			dihDataResult.setResult("");
			String foundDataCisMore1RowFlag = FLAG_NO;
			DIHQueryResult<String> dihCisNoResult = getCisNo(CID_TYPE,ID_NO,TH_FIRST_NAME,TH_LAST_NAME,EN_BIRTH_DATE);
			if(ResponseData.SUCCESS.equals(dihCisNoResult.getStatusCode())){
				cisNo = dihCisNoResult.getResult();
				cisCustomerResult.setCisNo(cisNo);
				cisCustomerResult.setFoundDataCisMore1RowFlag(foundDataCisMore1RowFlag);
				dihDataResult.setResult(gson.toJson(cisCustomerResult));
			}else{
				if(FLAG_YES.equals(dihCisNoResult.getResult())){
					foundDataCisMore1RowFlag = FLAG_YES;
				}
				dihDataResult.setStatusCode(dihCisNoResult.getStatusCode());
				dihDataResult.setErrorData(dihCisNoResult.getErrorData());
				cisCustomerResult.setCisNo(cisNo);
				cisCustomerResult.setFoundDataCisMore1RowFlag(foundDataCisMore1RowFlag);
				dihDataResult.setResult(gson.toJson(cisCustomerResult));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			dihDataResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			dihDataResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH,ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		cisCustomerResult.setDihQueryResult(dihDataResult);
		logger.debug("cisCustomerResult : "+cisCustomerResult);
		return cisCustomerResult;
	}
	@SuppressWarnings("serial")
	public class CisCustomerResult implements Serializable,Cloneable{
		private String resultCode;
		private String cisNo;
		private String foundDataCisMore1RowFlag = MConstant.FLAG_N;
		private String refreshFormFlag = MConstant.FLAG_N;
		private String errorMsg;
		private DIHQueryResult<String> dihQueryResult;
		public String getResultCode() {
			return resultCode;
		}
		public void setResultCode(String resultCode) {
			this.resultCode = resultCode;
		}
		public String getCisNo() {
			return cisNo;
		}
		public void setCisNo(String cisNo) {
			this.cisNo = cisNo;
		}
		public String getFoundDataCisMore1RowFlag() {
			return foundDataCisMore1RowFlag;
		}
		public void setFoundDataCisMore1RowFlag(String foundDataCisMore1RowFlag) {
			this.foundDataCisMore1RowFlag = foundDataCisMore1RowFlag;
		}		
		public String getRefreshFormFlag() {
			return refreshFormFlag;
		}
		public void setRefreshFormFlag(String refreshFormFlag) {
			this.refreshFormFlag = refreshFormFlag;
		}
		
		public DIHQueryResult<String> getDihQueryResult() {
			return dihQueryResult;
		}
		public void setDihQueryResult(DIHQueryResult<String> dihQueryResult) {
			this.dihQueryResult = dihQueryResult;
		}
		public String getErrorMsg() {
			return errorMsg;
		}
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("CisCustomerResult [resultCode=");
			builder.append(resultCode);
			builder.append(", cisNo=");
			builder.append(cisNo);
			builder.append(", foundDataCisMore1RowFlag=");
			builder.append(foundDataCisMore1RowFlag);
			builder.append(", refreshFormFlag=");
			builder.append(refreshFormFlag);
			builder.append(", dihQueryResult=");
			builder.append(dihQueryResult);
			builder.append("]");
			return builder.toString();
		}
	}
	public static void setComparisonField(ApplicationGroupDataM applicationGroup,HashMap<String, CompareDataM> comparisonFields){
		logger.debug("setComparisonField");
		ArrayList<ComparisonGroupDataM> comparisonGroups = applicationGroup.getComparisonGroups();
		if(null == comparisonGroups){
			comparisonGroups = new ArrayList<ComparisonGroupDataM>();
			applicationGroup.setComparisonGroups(comparisonGroups);
		}
		ComparisonGroupDataM comprisionGroup = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.CIS);
		if(null == comprisionGroup){
			comprisionGroup = new ComparisonGroupDataM();
			comprisionGroup.setSrcOfData(CompareDataM.SoruceOfData.CIS);
			comparisonGroups.add(comprisionGroup);
		}
		comprisionGroup.setComparisonFields(comparisonFields);
	}
	public static ProcessResponse updateComparisonsProcess(PersonalInfoDataM personalInfo,HashMap<String, CompareDataM> comparisonFields,String srcOfData,String userId,String roleId){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		try{
			String foundDataCisMore1RowFlag = MConstant.FLAG.NO;
			String ID_NO = personalInfo.getIdno();
			String CID_TYPE = personalInfo.getCidType();
			String TH_FIRST_NAME = personalInfo.getThFirstName();
			String TH_LAST_NAME = personalInfo.getThLastName();
//			String EN_BIRTH_DATE = FormatUtil.display(personalInfo.getBirthDate(),FormatUtil.EN,FormatUtil.Format.ddMMyyyy);
			String EN_BIRTH_DATE = (null!=personalInfo.getBirthDate())?personalInfo.getBirthDate().toString():"";
			String CIS_NO = personalInfo.getCisNo();
			logger.debug("ID_NO : "+ID_NO);
			logger.debug("CID_TYPE : "+CID_TYPE);
			logger.debug("TH_FIRST_NAME : "+TH_FIRST_NAME);
			logger.debug("TH_LAST_NAME : "+TH_LAST_NAME);
			logger.debug("EN_BIRTH_DATE : "+EN_BIRTH_DATE);
			logger.debug("CIS_NO : "+CIS_NO);
			DIHQueryResult<String> dihResult = new DIHQueryResult<String>();
				dihResult.setStatusCode(ResponseData.SUCCESS);
			if(Util.empty(CIS_NO)){
//				CustomerInformation customerInfo = new CustomerInformation();
//				CisCustomerResult cisCustomerResult = customerInfo.getCisNo(CID_TYPE,ID_NO,TH_FIRST_NAME,TH_LAST_NAME,EN_BIRTH_DATE);
				CisCustomerResult cisCustomerResult = PersonalInfoUtil.getCisNoResult(CID_TYPE,ID_NO,TH_FIRST_NAME,TH_LAST_NAME,EN_BIRTH_DATE);
				DIHQueryResult<String> dihCisNoResult = cisCustomerResult.getDihQueryResult();
				if(ResponseData.SUCCESS.equals(dihCisNoResult.getStatusCode())){
					CIS_NO = cisCustomerResult.getCisNo();
					foundDataCisMore1RowFlag = cisCustomerResult.getFoundDataCisMore1RowFlag();
				}else{
					foundDataCisMore1RowFlag = cisCustomerResult.getFoundDataCisMore1RowFlag();
					dihResult.setStatusCode(dihCisNoResult.getStatusCode());
					dihResult.setErrorData(dihCisNoResult.getErrorData());
				}
			}
			if(ResponseData.SUCCESS.equals(dihResult.getStatusCode())){
				logger.debug("foundDataCisMore1RowFlag : "+foundDataCisMore1RowFlag);
				logger.debug("CIS_NO : "+CIS_NO);
				if(MConstant.FLAG.NO.equals(foundDataCisMore1RowFlag)){
					if(!Util.empty(CIS_NO)){
						if(null == comparisonFields){
							comparisonFields = new HashMap<String, CompareDataM>();
						}
						logger.debug("comparisonFields : "+comparisonFields);
						DIHProxy dihProxy = new DIHProxy();
						DIHQueryResult<String>  personalInfoMapperResult = dihProxy.personalDataMapper(CIS_NO,personalInfo,comparisonFields,false);
						if(!ResponseData.SUCCESS.equals(personalInfoMapperResult.getStatusCode())){					 
							processResponse.setStatusCode(personalInfoMapperResult.getStatusCode());
							processResponse.setErrorData(personalInfoMapperResult.getErrorData());
							return processResponse;
						}			
						personalInfo.setCisNo(CIS_NO);
						int index = 1;
						for (String elementId : comparisonFields.keySet()) {
							CompareDataM comparisonField = comparisonFields.get(elementId);
							comparisonField.setRole(roleId);
							comparisonField.setCreateBy(userId);
							comparisonField.setUpdateBy(userId);
							comparisonField.setSeq(index++);
						}
					}
				}else{
					processResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					processResponse.setErrorData(ErrorController.error(ResponseData.SystemType.CIS,ErrorData.ErrorType.DATA_INCORRECT,MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW_DE2")));
					processResponse.setData(MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW_DE2"));
				}
			}else{
				logger.debug("foundDataCisMore1RowFlag : "+foundDataCisMore1RowFlag);
				if(MConstant.FLAG.YES.equals(foundDataCisMore1RowFlag)){
					processResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					processResponse.setErrorData(ErrorController.error(ResponseData.SystemType.CIS,ErrorData.ErrorType.DATA_INCORRECT,MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW_DE2")));
					processResponse.setData(MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW_DE2"));
				}else{
					processResponse.setStatusCode(dihResult.getStatusCode());
					processResponse.setErrorData(dihResult.getErrorData());
				}
				return processResponse;
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(ErrorController.error(e));
			processResponse.setData(e.getLocalizedMessage());
		}
		return processResponse;
	}
	public static void updateComparisons(PersonalInfoDataM personalInfo,HashMap<String, CompareDataM> comparisonFields,String srcOfData,String userId,String roleId)throws Exception{
		try{
			ProcessResponse processResponse = updateComparisonsProcess(personalInfo,comparisonFields,srcOfData,userId,roleId);
			if(!processResponse.getStatusCode().equals(ServiceResponse.Status.SUCCESS)){
				throw new Exception(processResponse.getData());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new Exception(e.getLocalizedMessage());
		}
	}
	public static CisCustomerResult updateCisDataProcess(String CID_TYPE,String ID_NO,String TH_FIRST_NAME,String TH_LAST_NAME,String EN_BIRTH_DATE,int lifeCycle,String userId,String roleId,PersonalInfoDataM personalInfo,HashMap<String,CompareDataM> comparisonFields,boolean saveModel)throws Exception{
		CisCustomerResult cisDataResult = new PersonalInfoUtil().new CisCustomerResult();
		DIHQueryResult<String> dihDataResult = new DIHQueryResult<>();
		Gson gson = new Gson(); 
		try{			
			dihDataResult.setStatusCode(ResponseData.SUCCESS);
			dihDataResult.setResult("");
			cisDataResult.setFoundDataCisMore1RowFlag(FLAG_NO);
			CisCustomerResult cisNoResult = getCisNoResult(CID_TYPE,ID_NO,TH_FIRST_NAME,TH_LAST_NAME,EN_BIRTH_DATE);
			logger.debug("cisNoResult : "+cisNoResult);
			DIHQueryResult<String> dihCisNoResult = cisNoResult.getDihQueryResult();
			if(ResponseData.SUCCESS.equals(dihCisNoResult.getStatusCode())){	
				String cisNo = cisNoResult.getCisNo();
				cisDataResult.setCisNo(cisNo);
				if(!Util.empty(cisNo)){
					DIHProxy dihProxy = new DIHProxy();
					DIHQueryResult<String> personalInfoMapperResult = dihProxy.personalDataMapper(cisNo,personalInfo,comparisonFields,saveModel);
					logger.info("personalInfoMapperResult : "+personalInfoMapperResult);
					if(ResponseData.SUCCESS.equals(personalInfoMapperResult.getStatusCode())){
						cisDataResult.setCisNo(cisNo);
						if(!Util.empty(comparisonFields)){
							int index = 1;
							for (String elementId : comparisonFields.keySet()) {
								CompareDataM comparisonField = comparisonFields.get(elementId);
								comparisonField.setApplicationGroupId(personalInfo.getApplicationGroupId());
								comparisonField.setCreateBy(userId);
								comparisonField.setUpdateBy(userId);
								comparisonField.setRole(roleId);
								comparisonField.setSeq(index++);
								comparisonField.setLifeCycle(lifeCycle);
							}
						}
					}else{
						dihDataResult.setStatusCode(personalInfoMapperResult.getStatusCode());
						dihDataResult.setErrorData(personalInfoMapperResult.getErrorData());
						dihDataResult.setResult(gson.toJson(cisDataResult));
						cisDataResult.setDihQueryResult(personalInfoMapperResult);
						return cisDataResult;
					}
				}
				dihDataResult.setResult(gson.toJson(cisDataResult));
			}else{
				cisDataResult.setFoundDataCisMore1RowFlag(cisNoResult.getFoundDataCisMore1RowFlag());
				dihDataResult.setStatusCode(dihCisNoResult.getStatusCode());
				dihDataResult.setErrorData(dihCisNoResult.getErrorData());
				dihDataResult.setResult(gson.toJson(cisDataResult));
				cisDataResult.setDihQueryResult(dihCisNoResult);
				return cisDataResult;
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			dihDataResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			dihDataResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH,ErrorData.ErrorType.SYSTEM_ERROR,e));
			dihDataResult.setResult(gson.toJson(cisDataResult));
		}
		cisDataResult.setDihQueryResult(dihDataResult);
		return cisDataResult;
	}
	public static boolean validateRegExp(PersonalInfoDataM personalInfo){
		String regex1 = "[a-zA-Z0-9]";
		String regex2 = "^\\-$";
		String regex3 = "^[a-zA-Z0-9]+.?";
		String regex4 = "^[0-9]*$";
		String cidType = personalInfo.getCidType();
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		Pattern pattern3 = Pattern.compile(regex3);
		Pattern pattern4 = Pattern.compile(regex4);
		if(!Util.empty(personalInfo.getEnFirstName())){
			String enFirstName = personalInfo.getEnFirstName();
			logger.debug("enFirstName is: "+enFirstName);
			if((pattern1.matcher(enFirstName)).find()){
				if(!pattern3.matcher(enFirstName).find() || pattern4.matcher(enFirstName).find()){ return true; }
			}else{
				return true; 
			}
		}
		if(!Util.empty(personalInfo.getEnMidName())){
			String enMidName = personalInfo.getEnMidName();
			logger.debug("enMidName is: "+enMidName);
			if((pattern1.matcher(enMidName)).find()){
				if(!pattern3.matcher(enMidName).find() || pattern4.matcher(enMidName).find()){ return true; }
			}else{
				 return true; 
			}
		}
		if(!Util.empty(personalInfo.getEnLastName())){
			String enLastName = personalInfo.getEnLastName();
			logger.debug("enLastName is: "+enLastName);
			if((pattern1.matcher(enLastName)).find()){
				if(!pattern3.matcher(enLastName).find() || pattern4.matcher(enLastName).find()){ return true; }
			}else{
				if(CIDTYPE_PASSPORT.equals(cidType)){
					if(!pattern2.matcher(enLastName).find()){ return true; }
				}else{ return true; }
			}
		}
		
		return false;
	}
	public static boolean validateRegExpTH(PersonalInfoDataM personalInfo){
		String regex1 = "[a-zA-Z0-9]";
		String regex2 = "^\\-$";
		String regex3 = "^[a-zA-Z0-9]+.?";
		String regex4 = "^[0-9]*$";
		String cidType = personalInfo.getCidType();
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		Pattern pattern3 = Pattern.compile(regex3);
		Pattern pattern4 = Pattern.compile(regex4);
		if(!Util.empty(personalInfo.getThFirstName())){
			String thFirstName = personalInfo.getThFirstName();
			logger.debug("thFirstName is: "+thFirstName);
			if((pattern1.matcher(thFirstName)).find()){
				if(!pattern3.matcher(thFirstName).find() || pattern4.matcher(thFirstName).find()){ return true; }
			}else{
				return true; 
			}
		}
		if(!Util.empty(personalInfo.getThMidName())){
			String thMidName = personalInfo.getThMidName();
			logger.debug("thMidName is: "+thMidName);
			if((pattern1.matcher(thMidName)).find()){
				if(!pattern3.matcher(thMidName).find() || pattern4.matcher(thMidName).find()){ return true; }
			}else{
				 return true; 
			}
		}
		if(!Util.empty(personalInfo.getThLastName())){
			String thLastName = personalInfo.getThLastName();
			logger.debug("thLastName is: "+thLastName);
			if((pattern1.matcher(thLastName)).find()){
				if(!pattern3.matcher(thLastName).find() || pattern4.matcher(thLastName).find()){ return true; }
			}else{
				if(CIDTYPE_PASSPORT.equals(cidType)){
					if(!pattern2.matcher(thLastName).find()){ return true; }
				}else{ return true; }
			}
		}
		
		return false;
	}
	public static boolean validateRegExpByFieldName(PersonalInfoDataM personalInfo,String fieldName){
		String regex1 = "[a-zA-Z0-9]";
		String regex2 = "^\\-$";
		String regex3 = "^[a-zA-Z0-9]+.?";
		String regex4 = "^[0-9]*$";
		String cidType = personalInfo.getCidType();
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		Pattern pattern3 = Pattern.compile(regex3);
		Pattern pattern4 = Pattern.compile(regex4);
		if(!Util.empty(personalInfo.getEnFirstName()) && EN_FIRST_NAME.equals(fieldName)){
			String enFirstName = personalInfo.getEnFirstName();
			logger.debug("enFirstName is: "+enFirstName);
			if((pattern1.matcher(enFirstName)).find()){
				if(!pattern3.matcher(enFirstName).find() || pattern4.matcher(enFirstName).find()){ return true; }
			}else{
				return true; 
			}
		}
		if(!Util.empty(personalInfo.getEnLastName()) && EN_LAST_NAME.equals(fieldName)){
			String enLastName = personalInfo.getEnLastName();
			logger.debug("enLastName is: "+enLastName);
			if((pattern1.matcher(enLastName)).find()){
				if(!pattern3.matcher(enLastName).find() || pattern4.matcher(enLastName).find()){ return true; }
			}else{
				if(CIDTYPE_PASSPORT.equals(cidType)){
					if(!pattern2.matcher(enLastName).find()){ return true; }
				}else{ return true; }
			}
		}
		if(!Util.empty(personalInfo.getThFirstName()) && TH_FIRST_NAME.equals(fieldName)){
			String thFirstName = personalInfo.getThFirstName();
			logger.debug("thFirstName is: "+thFirstName);
			if((pattern1.matcher(thFirstName)).find()){
				if(!pattern3.matcher(thFirstName).find() || pattern4.matcher(thFirstName).find()){ return true; }
			}else{
				return true; 
			}
		}
		if(!Util.empty(personalInfo.getThLastName()) && TH_LAST_NAME.equals(fieldName)){
			String thLastName = personalInfo.getThLastName();
			logger.debug("thLastName is: "+thLastName);
			if((pattern1.matcher(thLastName)).find()){
				if(!pattern3.matcher(thLastName).find() || pattern4.matcher(thLastName).find()){ return true; }
			}else{
				if(CIDTYPE_PASSPORT.equals(cidType)){
					if(!pattern2.matcher(thLastName).find()){ return true; }
				}else{ return true; }
			}
		}
		return false;
	}
}
