package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.DocumentCheckListDisplayHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;
import com.eaf.orig.ulo.model.app.DocumentCommentDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
public class DocumentCheckListUtil {
	private static transient Logger logger = Logger.getLogger(DocumentCheckListUtil.class);
	static String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	static String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	static String DOC_SET_FORM = SystemConstant.getConstant("DOC_SET_FORM");
	public static boolean isReceiveDocumentNoFollowUp(Entry<String, Map<String, Map<String, String>>> entry1 , boolean isVerCustomer){
		try{
			if(!Util.empty(entry1) && isVerCustomer){
				boolean containDocFollowDesc = isContainKey(entry1,"docCheckListDesc");
				logger.debug("containDocFollowDesc>>"+containDocFollowDesc);
				for (Entry<String, Map<String, String>> entry2 : entry1.getValue().entrySet()) {
					Map<String, String> data = entry2.getValue();
					if(data.containsKey("found") && "Y".equals(data.get("found")) && !containDocFollowDesc){
			 			return true;
					}
				}
			}			
		}catch (Exception e) {
			logger.fatal("ERROR",e);
		}	
		return false;
	}	
	private static boolean  isContainKey(Entry<String, Map<String, Map<String, String>>> entry1 ,String key){
		try {
			if(!Util.empty(entry1)){
				for (Entry<String, Map<String, String>> entry2 : entry1.getValue().entrySet()) {
						Map<String, String> data = entry2.getValue();
						if(data.containsKey(key)){
							return true;
						}
					}
				}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return false;
	}	
	public static boolean isReasonApplicantDocumentFollowUp(ApplicationGroupDataM applicationGroup){
		try {
			PersonalInfoDataM personalInfoA = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			if (null == personalInfoA) {
				personalInfoA = new PersonalInfoDataM();
			}
			VerificationResultDataM verificationA = personalInfoA.getVerificationResult();
			if (null == verificationA) {
				verificationA = new VerificationResultDataM();
			}
			ArrayList<RequiredDocDataM> requiredDocListsA = verificationA.getRequiredDocs();
			if (null == requiredDocListsA) {
				requiredDocListsA = new ArrayList<RequiredDocDataM>();
			}
			ArrayList<DocumentCommentDataM> docComments = applicationGroup.getDocumentComments();
			if (null == docComments) {
				docComments = new ArrayList<DocumentCommentDataM>();
			}
	
			DocumentCheckListDisplayHelper docDisplayA = new DocumentCheckListDisplayHelper(personalInfoA, applicationGroup);
			Map<String, Map<String, Map<String, String>>> docsA = docDisplayA.getDocCheckList();
			if (!Util.empty(requiredDocListsA) && !Util.empty(verificationA.getDocCompletedFlag())) {
				for (Entry<String, Map<String, Map<String, String>>> entry1 : docsA.entrySet()) {			
					for (Entry<String, Map<String, String>> entry2 : entry1.getValue().entrySet()) {
						Map<String, String> data = entry2.getValue();
						if(data.containsKey("docCheckListDesc") && data.containsKey("found") && "Y".equals(data.get("found"))){
							return true;
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}	
		return false;
	}
	public static boolean isReasonSupplementaryDocumentFollowUp(ApplicationGroupDataM applicationGroup){
		try {
			ArrayList<PersonalInfoDataM> personals = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
			if(!Util.empty(personals)){
				for(PersonalInfoDataM personalInfoS:personals){
					if (null == personalInfoS) {
						personalInfoS = new PersonalInfoDataM();
					}
					VerificationResultDataM verificationS = personalInfoS.getVerificationResult();
					if (null == verificationS) {
						verificationS = new VerificationResultDataM();
					}
					ArrayList<RequiredDocDataM> requiredDocListsS = verificationS.getRequiredDocs();
					if (null == requiredDocListsS) {
						requiredDocListsS = new ArrayList<RequiredDocDataM>();
					}
					// Load Document Check List
					DocumentCheckListDisplayHelper docDisplayS = new DocumentCheckListDisplayHelper(personalInfoS, applicationGroup);
					Map<String, Map<String, Map<String, String>>> docsS = docDisplayS.getDocCheckList();
					if (!Util.empty(requiredDocListsS) && !Util.empty(verificationS.getDocCompletedFlag())) {
						for (Entry<String, Map<String, Map<String, String>>> entry1 : docsS.entrySet()) {
							for (Entry<String, Map<String, String>> entry2 : entry1.getValue().entrySet()) {
								Map<String, String> data = entry2.getValue();
								logger.debug("data supdocsS>>>"+data);
								if(data.containsKey("docCheckListDesc") && data.containsKey("found") && "Y".equals(data.get("found"))){
									return true;
								}
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}	
		return false;
	}
	
	public static boolean containDocumentCheckListCompleteFlagNo(ApplicationGroupDataM applicationGroup){
		PersonalInfoDataM personalInfoA = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		if(!Util.empty(personalInfoA)){
			VerificationResultDataM verificationA = personalInfoA.getVerificationResult();
			if(null != verificationA){
				logger.debug("verificationA.getDocCompletedFlag()>>"+verificationA.getDocCompletedFlag());
				if(!Util.empty(verificationA) && MConstant.FLAG_N.equals(verificationA.getDocCompletedFlag())){
					return true;
				}
			}
		}
		ArrayList<PersonalInfoDataM> personalSUP = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		if(!Util.empty(personalSUP)){
			for(PersonalInfoDataM personalInfoS : personalSUP){
				VerificationResultDataM verificationS = personalInfoS.getVerificationResult();
				if(null != verificationS){
					logger.debug("verificationS.getDocCompletedFlag()>>"+verificationS.getDocCompletedFlag());
					if(!Util.empty(verificationS) && MConstant.FLAG_N.equals(verificationS.getDocCompletedFlag())){
						return true;
					}
				}
			}
		}		
		return false;
	}
	
	
	public static void defaultRequiredDocFlag(ApplicationGroupDataM applicationGroup){
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				String personalId = personalInfo.getPersonalId();
				List<String> imageSplitsDocTypes = applicationGroup.getImageSplitsDocType(personalId,PersonalInfoUtil.getIMPersonalType(personalInfo));
				if(null == imageSplitsDocTypes){
					imageSplitsDocTypes = new ArrayList<String>();
				}
				VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
				if(null != verificationResult){
					ArrayList<RequiredDocDataM> requiredDocs = verificationResult.getRequiredDocs();
					if(null != requiredDocs){
						for (RequiredDocDataM requiredDoc : requiredDocs) {
							ArrayList<RequiredDocDetailDataM> requiredDocDetails = requiredDoc.getRequiredDocDetails();
							if(null != requiredDocDetails){
								for (RequiredDocDetailDataM requiredDocDetail : requiredDocDetails) {
									if(imageSplitsDocTypes.contains(requiredDocDetail.getDocumentCode())){
										requiredDocDetail.setReceivedFlag(MConstant.FLAG.YES);
									}else{
										requiredDocDetail.setReceivedFlag(MConstant.FLAG.NO);
									}
								}
							}
						}
					}
				}
			}			
		}
	}
	
	public static boolean isReceiveApplicationForm(ArrayList<String> docType){
		if(!Util.empty(docType) && docType.contains(DOC_SET_FORM)){
			if(!(docType.contains(DOC_SET_FORM))){
				return true;
			}
		}
		return false;
	}
	
	public static void defaultNotReceivedDocumentReason(ApplicationGroupDataM applicationGroup){
		String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
		defaultRequiredDocFlag(applicationGroup);
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				String personalId = personalInfo.getPersonalId();
				String personalType = personalInfo.getPersonalType();
				int personalSeq = personalInfo.getSeq();
				logger.debug("personalId : "+personalId);
				logger.debug("personalType : "+personalType);
				logger.debug("personalSeq : "+personalSeq);
				VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
				if(null != verificationResult){
					ArrayList<RequiredDocDataM> requiredDocs = verificationResult.getRequiredDocs();
					if(null != requiredDocs){
						for (RequiredDocDataM requiredDoc : requiredDocs) {	
							String docTypeId = requiredDoc.getScenarioType();
							logger.debug("docTypeId : "+docTypeId);
							ArrayList<RequiredDocDetailDataM> requiredDocDetails = requiredDoc.getRequiredDocDetails();
							if(null != requiredDocDetails){
								for (RequiredDocDetailDataM requiredDocDetail : requiredDocDetails) {
									ArrayList<DocumentCheckListReasonDataM> documentCheckListReasons = new ArrayList<DocumentCheckListReasonDataM>();
									String documentCode = requiredDocDetail.getDocumentCode();
									String receivedFlag = requiredDocDetail.getReceivedFlag();
									logger.debug("documentCode : "+documentCode);
									logger.debug("receivedFlag : "+receivedFlag);
									if(!MConstant.FLAG.YES.equals(receivedFlag)){
										String IMApplicatType = PersonalInfoUtil.getIMPersonalType(personalInfo);
										DocumentCheckListDataM documentCheckList = applicationGroup.getDocumentCheckList(IMApplicatType, documentCode);
										String DOC_FOLLOW_UP_REASON_CODE_NOT_RECEIVE = SystemConstant.getConstant("DOC_FOLLOW_UP_REASON_CODE_NOT_RECEIVE");
										logger.debug("documentCheckList : "+documentCheckList);	
										
										if(documentCheckList == null && !(documentCode.equals(DOC_SET_FORM) && !isReceiveApplicationForm(applicationGroup.getImageSplitsDocType()))) {
											documentCheckList = new DocumentCheckListDataM();
											documentCheckList.setApplicantTypeIM(IMApplicatType);
											documentCheckList.setDocumentCode(documentCode);
											documentCheckList.setDocTypeId(docTypeId);
											String docCheckListId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_DOC_CHECK_LIST_PK );
											documentCheckList.setDocCheckListId(docCheckListId);
											applicationGroup.addDocumentCheckList(documentCheckList);
										}
										if(!Util.empty(documentCheckList)){
											DocumentCheckListReasonDataM reasonM = new DocumentCheckListReasonDataM();
											reasonM.setDocReason(DOC_FOLLOW_UP_REASON_CODE_NOT_RECEIVE);
											reasonM.setGenerateFlag(FLAG_YES);
											documentCheckListReasons.add(reasonM);
											documentCheckList.setDocumentCheckListReasons(documentCheckListReasons);	
										}
									}
								}
							}
						}
					}
				}
				PersonalInfoUtil.defaultPersonalDocumentCheckListRelation(applicationGroup, personalId, personalType, personalSeq);
			}
		}
	}	
}
