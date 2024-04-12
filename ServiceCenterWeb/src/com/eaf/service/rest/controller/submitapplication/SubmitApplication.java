package com.eaf.service.rest.controller.submitapplication;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.CsvSerializer;
import org.jsefa.csv.config.CsvConfiguration;
import org.jsefa.csv.lowlevel.config.QuoteMode;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.docset.model.DocTypeSegment;
import com.eaf.im.rest.docset.model.DocumentSegment;
import com.eaf.im.rest.docset.model.FollowupReason;
import com.eaf.im.rest.docset.model.InquireDocSetRequest;
import com.eaf.im.rest.docset.model.InquireDocSetResponse;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.dao.PegaApplicationDAO;
import com.eaf.orig.ulo.app.dao.PegaApplicationDAOImpl;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankBranchInfoDataM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.DocumentCheckListUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.pega.FollowUpRequest;
import com.eaf.orig.ulo.model.submitapplication.model.SubmitApplicationObjectDataM;
import com.eaf.orig.ulo.service.app.submit.dao.SubmitApplicationFactory;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.orig.ulo.service.submitapplication.model.CSVContentDataM;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.dao.ServiceFactory;
import com.eaf.service.common.exception.ServiceControlException;
import com.eaf.service.common.model.FollowUpPegaRefDataObject;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.manual.DecisionServiceProxy;
import com.eaf.service.module.manual.InquiryDocSetControlProxy;
import com.eaf.service.module.manual.PegaFollowUpServiceProxy;
import com.eaf.service.rest.application.model.SubmitApplicationRequest;
import com.eaf.service.rest.controller.submitapplication.model.CreateApplicationRequest;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class SubmitApplication {
	private static transient Logger logger = Logger.getLogger(SubmitApplication.class);
	String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
	String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
	String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
	String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
	String CACHE_BRANCH_INFO = SystemConstant.getConstant("CACHE_BRANCH_INFO");
	String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	String SUBMIT_APPLICATION_CSV_CONTENT_HEADER = SystemConstant.getConstant("SUBMIT_APPLICATION_CSV_CONTENT_HEADER");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");	
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String FIELD_ID_CONTACT_TIME = SystemConstant.getConstant("FIELD_ID_CONTACT_TIME");
	String CACH_NAME_DOCUMENT_LIST = SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST");
	String FIELD_ID_DOC_CHECKLIST_REASON = SystemConstant.getConstant("FIELD_ID_DOC_CHECKLIST_REASON");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_CURRENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT_CARDLINK");
	String FIELD_ID_CHECKLIST_REASON_TYPE = SystemConstant.getConstant("FIELD_ID_CHECKLIST_REASON_TYPE");
	String REASON_TYPE_OTHER = SystemConstant.getConstant("REASON_TYPE_OTHER");
	String REASON_TYPE_CHECKLIST = SystemConstant.getConstant("REASON_TYPE_CHECKLIST");
	String SCENARIO =SystemConstant.getConstant("DEFUALT_DSCENARIO");
	String DEFUALT_ALREADY_FOLLOW = SystemConstant.getConstant("DEFUALT_ALREADY_FOLLOW");
	String DEFUALT_DUMMY = SystemConstant.getConstant("DEFUALT_DUMMY");
	String FIELD_ID_COVER_PAGE_TYPE = SystemConstant.getConstant("FIELD_ID_COVER_PAGE_TYPE");
	ArrayList<String> DOC_TYPE_ADDITIONAL = SystemConfig.getArrayListGeneralParam("DOC_TYPE_ADDITIONAL");
	String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");	
	String IM_PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("IM_PERSONAL_TYPE_SUPPLEMENTARY");	
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
	String DOC_RELATION_REF_LEVEL_PERSONAL = SystemConstant.getConstant("DOC_RELATION_REF_LEVEL_PERSONAL");
	String FIELD_ID_DOC_SCENARIO_GROUP = SystemConstant.getConstant("FIELD_ID_DOC_SCENARIO_GROUP");
	ArrayList<String> DEFUALT_FIX_ZONE_REASON_CODE = SystemConstant.getArrayListConstant("DEFUALT_FIX_ZONE_REASON_CODE");
	String DEFUALT_IM_SUPPLEMENTARY_SEQ = SystemConstant.getConstant("DEFUALT_IM_SUPPLEMENTARY_SEQ");
	String DOC_FOLLOW_UP_REASON_CODE_DOC_NOT_CLEAR = SystemConstant.getConstant("DOC_FOLLOW_UP_REASON_CODE_DOC_NOT_CLEAR");
	public ServiceResponseDataM inquireDocSetService(String uniqueId,SubmitApplicationRequest submitApplicationRequest,String userId) throws Exception{
		String IM_INQUIRE_DOC_SET_URL = SystemConfig.getProperty("IM_INQUIRE_DOC_SET_URL");
		logger.debug("IM_INQUIRE_DOC_SET_URL>>"+IM_INQUIRE_DOC_SET_URL);
		ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
			serviceRequest.setServiceId(InquiryDocSetControlProxy.serviceId);
			serviceRequest.setEndpointUrl(IM_INQUIRE_DOC_SET_URL);
			serviceRequest.setUserId(userId);
			serviceRequest.setUniqueId(uniqueId);		
			serviceRequest.setRefId(submitApplicationRequest.getQr2());
		InquireDocSetRequest inquiryDocSetRequest = new InquireDocSetRequest();
			inquiryDocSetRequest.setSetID(submitApplicationRequest.getQr2());
			inquiryDocSetRequest.setDocContentType("");
			serviceRequest.setObjectData(inquiryDocSetRequest);
		ServiceCenterProxy serviceCenterProxy = new ServiceCenterProxy(submitApplicationRequest.getTransactionId());
		ServiceResponseDataM serviceResponse = serviceCenterProxy.requestService(serviceRequest);
		
		return serviceResponse;												
	}	
	public ServiceResponseDataM sendDummyToPega(ApplicationGroupDataM applicationGroup,SubmitApplicationObjectDataM submitApplicationObject) throws Exception{
		return sendFollowUpToPega(applicationGroup,submitApplicationObject,new ArrayList<String>(),true);
	}
	public ServiceResponseDataM sendFollowUpToPega(SubmitApplicationObjectDataM submitApplicationObject,ArrayList<String> docTypeCheckLists) throws Exception {
		return sendFollowUpToPega(null,submitApplicationObject,docTypeCheckLists,false);
	}
	public ServiceResponseDataM sendFollowUpToPega(ApplicationGroupDataM applicationGroup,SubmitApplicationObjectDataM submitApplicationObject,ArrayList<String> docTypeCheckLists) throws Exception {
		return sendFollowUpToPega(applicationGroup,submitApplicationObject,docTypeCheckLists,false);
	}
	public ServiceResponseDataM sendFollowUpToPega(ApplicationGroupDataM applicationGroup,SubmitApplicationObjectDataM submitApplicationObject
			,ArrayList<String> docTypeCheckLists,boolean isDummy) throws Exception {
		SubmitApplicationRequest submitApplicationRequest = (SubmitApplicationRequest)submitApplicationObject.getSubmitApplicationRequest();
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		String URL = SystemConfig.getProperty("PEGA_FOLLOWUP_URL");
		String userId = submitApplicationObject.getUserId();		
		FollowUpPegaRefDataObject refDataObject = new FollowUpPegaRefDataObject();
			refDataObject.setAction(FollowUpPegaRefDataObject.Action.SERVICE_SUBMIT);	
			refDataObject.setLifeCycle(applicationGroup.getMaxLifeCycle());
		FollowUpRequest followUpRequest =  new FollowUpRequest();
			followUpRequest.setCaseID(submitApplicationRequest.getQr2());
			followUpRequest.setUserId(userId);
			ArrayList<CSVContentDataM> csvContents = new ArrayList<CSVContentDataM>();
			if(isDummy){
				refDataObject.setFollowUpGroup(FollowUpPegaRefDataObject.FollowUpGroup.DUMMY);
				csvContents = mapDummyCSVContent(applicationGroup,submitApplicationObject);
				followUpRequest.setPegaOperatorID(SystemConfig.getProperty("PEGA_OPERATOER_ID"));
			}else{				
				refDataObject.setFollowUpGroup(FollowUpPegaRefDataObject.FollowUpGroup.FOLLOW);
				csvContents = mapCSVContent(applicationGroup,submitApplicationObject,docTypeCheckLists);
				if(Util.empty(csvContents)){
					refDataObject.setFollowUpGroup(FollowUpPegaRefDataObject.FollowUpGroup.DUMMY);
					csvContents = mapDummyCSVContent(applicationGroup,submitApplicationObject);
					followUpRequest.setPegaOperatorID(SystemConfig.getProperty("PEGA_OPERATOER_ID"));
				}else{					
					followUpRequest.setPegaOperatorID(userId);
				}
			}
			followUpRequest.setCSVContent(getCSVContent(csvContents));				
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(PegaFollowUpServiceProxy.serviceId);
				serviceRequest.setUserId(submitApplicationObject.getUserId());
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setUniqueId(!Util.empty(applicationGroupId)?applicationGroupId:submitApplicationRequest.getQr2());
				serviceRequest.setRefId(submitApplicationRequest.getQr2());
				serviceRequest.setObjectData(followUpRequest);	
				serviceRequest.setServiceData(refDataObject);
			ServiceCenterProxy proxy = new ServiceCenterProxy(submitApplicationRequest.getTransactionId());
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
		return serivceResponse;
	}
	
	public ServiceResponseDataM sendFollowUpToPega(ApplicationGroupDataM applicationGroup,SubmitApplicationObjectDataM submitApplicationObject) throws Exception{
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		FollowUpPegaRefDataObject refDataObject = new FollowUpPegaRefDataObject();
			refDataObject.setAction(FollowUpPegaRefDataObject.Action.SERVICE_SUBMIT);
			refDataObject.setLifeCycle(applicationGroup.getMaxLifeCycle());
		SubmitApplicationRequest submitApplicationRequest = (SubmitApplicationRequest)submitApplicationObject.getSubmitApplicationRequest();		
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		String URL = SystemConfig.getProperty("PEGA_FOLLOWUP_URL");
		String userId = submitApplicationObject.getUserId();
		FollowUpRequest followUpRequest =  new FollowUpRequest();
			followUpRequest.setCaseID(submitApplicationRequest.getQr2());
			followUpRequest.setUserId(userId);
			followUpRequest.setPegaOperatorID(userId);
			refDataObject.setFollowUpGroup(FollowUpPegaRefDataObject.FollowUpGroup.FOLLOW);
			ArrayList<CSVContentDataM> csvContents = mapCSVContent(applicationGroup,userId);
			if(Util.empty(csvContents)){
				refDataObject.setFollowUpGroup(FollowUpPegaRefDataObject.FollowUpGroup.DUMMY);
				csvContents = mapDummyCSVContent(applicationGroup, submitApplicationObject);
				followUpRequest.setPegaOperatorID(SystemConfig.getProperty("PEGA_OPERATOER_ID"));
			}
			followUpRequest.setCSVContent(getCSVContent(csvContents));
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(PegaFollowUpServiceProxy.serviceId);
				serviceRequest.setUserId(submitApplicationObject.getUserId());
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setUniqueId(!Util.empty(applicationGroupId)?applicationGroupId:submitApplicationRequest.getQr2());
				serviceRequest.setRefId(submitApplicationRequest.getQr2());
				serviceRequest.setObjectData(followUpRequest);		
				serviceRequest.setServiceData(refDataObject);
			ServiceCenterProxy proxy = new ServiceCenterProxy(submitApplicationRequest.getTransactionId());
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
		return serivceResponse;
	}
	
	public static ArrayList<CSVContentDataM> mapCSVContent(ApplicationGroupDataM applicationGroup,String userId){
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
		String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
		String ADDRESS_TYPE_CURRENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT_CARDLINK");
		String FIELD_ID_CONTACT_TIME = SystemConstant.getConstant("FIELD_ID_CONTACT_TIME");
		String SCENARIO = SystemConstant.getConstant("DEFUALT_DSCENARIO");
		String DEFUALT_ALREADY_FOLLOW = SystemConstant.getConstant("DEFUALT_ALREADY_FOLLOW");
		String CACH_NAME_DOCUMENT_LIST = SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST");
		String REASON_TYPE_OTHER = SystemConstant.getConstant("REASON_TYPE_OTHER");
		String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
		String FIELD_ID_CHECKLIST_REASON_TYPE = SystemConstant.getConstant("FIELD_ID_CHECKLIST_REASON_TYPE");
		String FIELD_ID_DOC_SCENARIO_GROUP = SystemConstant.getConstant("FIELD_ID_DOC_SCENARIO_GROUP");
		PersonalInfoDataM personalInfoApplicant = PersonalInfoUtil.getMainPersonalInfo(applicationGroup);
		if(null == personalInfoApplicant){
			personalInfoApplicant = new PersonalInfoDataM();
		}		
		ArrayList<CSVContentDataM> csvContents = new ArrayList<CSVContentDataM>();
		AddressDataM homeAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_CURRENT);
		if(null == homeAddress){
			homeAddress = new AddressDataM();
		}
		AddressDataM officeAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_WORK);
		if(null == officeAddress){
			officeAddress = new AddressDataM();
		}
		AddressDataM cardLinkAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_CURRENT_CARDLINK);
		if(null == cardLinkAddress){
			cardLinkAddress=  new AddressDataM();
		}
		
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos(SystemConstant.getArrayListConstant("PERSONAL_TYPE_LIST_CONDITION"));
		if(Util.empty(Util.empty(personalInfos))){
			personalInfos = new ArrayList<PersonalInfoDataM>();
		}		
		String customerAvailable = CacheControl.getName(FIELD_ID_CONTACT_TIME, "CHOICE_NO", personalInfoApplicant.getContactTime(), "MAPPING8");		
		logger.debug("customerAvailable : "+customerAvailable);		
		for(PersonalInfoDataM personalInfo : personalInfos){
			String personalType = personalInfo.getPersonalType();
			String personalId = personalInfo.getPersonalId();
			int seq = personalInfo.getSeq();
			logger.debug("personalType : "+personalType);
			logger.debug("personalId : "+personalId);
			logger.debug("seq : "+seq);
			String applicantTypeIM = PersonalInfoUtil.getIMPersonalType(personalType, seq);
			logger.debug("applicantTypeIM : "+applicantTypeIM);
			ArrayList<DocumentCheckListDataM> documentCheckLists = applicationGroup.getDocumentCheckListApplicantTypeIM(applicantTypeIM);
			logger.debug("documentCheckLists : "+documentCheckLists);
			if(null != documentCheckLists){
				for(DocumentCheckListDataM  documentCheckList : documentCheckLists){
					if(null==documentCheckList) {
						documentCheckList = new DocumentCheckListDataM();
					}
					
					// CR74 : validate DOC_COMPLETE_FLAG before submit to PEGA
					if(!isDocComplete(personalInfo, documentCheckList)) {
						String docCode = documentCheckList.getDocumentCode();	
						String docName = CacheControl.getName(CACH_NAME_DOCUMENT_LIST,docCode);
						ArrayList<String> docReasonIds = documentCheckList.getDocumentCheckListDocReasonCodes();						
						String documentGroup = applicationGroup.getDocumentCategory(docCode);
						String reasonTypeCode = getDocumentReasonType(personalInfo,docCode,docReasonIds);
						String group = CacheControl.getName(FIELD_ID_DOC_SCENARIO_GROUP,"CHOICE_NO",documentGroup,"DISPLAY_NAME");
						if(Util.empty(reasonTypeCode)){
							reasonTypeCode = REASON_TYPE_OTHER;
						}
						logger.debug("docCode : "+docCode);
						logger.debug("documentGroup : "+documentGroup);
						logger.debug("group : "+group);
						logger.debug("reasonTypeCode : "+reasonTypeCode);					
						String reasonTypeDesc  = CacheControl.getName(FIELD_ID_CHECKLIST_REASON_TYPE, "CHOICE_NO", reasonTypeCode, "MAPPING8");
						String personalTypeDesc = CacheControl.getName(FIELD_ID_PERSONAL_TYPE,personalType);
						
						CSVContentDataM csvContent =  new CSVContentDataM();	
							csvContent.setCaseID(applicationGroup.getApplicationGroupNo());		
							csvContent.setGroup(group);
							csvContent.setScenario(SCENARIO);
							csvContent.setDocName(docName);
							csvContent.setMandatory((mandatoryFlag(personalInfo,docCode))?MConstant.FLAG_M:MConstant.FLAG_O);
							csvContent.setDocReason(reasonTypeDesc);
							csvContent.setReasonDetail(personalTypeDesc+" "+FormatUtil.displayText(personalInfo.getThFirstName())+getReasonCodeName(documentCheckList.getDocumentCheckListReasons())+"//"+documentCheckList.getRemark());
							csvContent.setCustAvailableTime(Util.empty(customerAvailable)?"N/A":customerAvailable);
							csvContent.setAlreadyFollow(MConstant.FLAG.YES.equals(applicationGroup.getAlreadyFollowFlag())?MConstant.FLAG.YES:DEFUALT_ALREADY_FOLLOW);
							csvContent.setMobile(personalInfoApplicant.getMobileNo());
							csvContent.setTelHome(homeAddress.getPhone1());
							csvContent.setTelHomeExt(homeAddress.getExt1());
							csvContent.setTelOffice(officeAddress.getPhone1());
							csvContent.setTelOfficeExt(officeAddress.getExt1());
							csvContent.setTelCardLink(cardLinkAddress.getPhone1());
							csvContent.setTelCardLinkExt(cardLinkAddress.getExt1());
							csvContent.setFirstNameTH(personalInfoApplicant.getThFirstName());
							csvContent.setMiddleNameTH(personalInfoApplicant.getThMidName());
							csvContent.setLastNameTH(personalInfoApplicant.getThLastName());
							csvContent.setFirstNameEN(personalInfoApplicant.getEnFirstName());
							csvContent.setMiddleNameEN(personalInfoApplicant.getEnMidName());
							csvContent.setLastNameEN(personalInfoApplicant.getEnLastName());
							csvContent.setPrimarySegment(personalInfoApplicant.getCisPrimSegment());
							csvContent.setPrimarySubSegment(personalInfoApplicant.getCisSecSubSegment());
							csvContent.setSecondarySegment(personalInfoApplicant.getCisSecSegment());
							csvContent.setSecondarySubSegment(personalInfoApplicant.getCisSecSubSegment());
//							csvContent.setRMOperatorID(userId);
						csvContents.add(csvContent);
					}
				}
			}			 
		}
		return csvContents;
	}
	public static String getReasonCodeName(ArrayList<DocumentCheckListReasonDataM> documemtCheckListReasonList){
		String FIELD_ID_DOC_CHECKLIST_REASON = SystemConstant.getConstant("FIELD_ID_DOC_CHECKLIST_REASON");
		String FOLLOW_UP_REASON_DOC_NOT_CLEAR = SystemConstant.getConstant("DOC_FOLLOW_UP_REASON_CODE_DOC_NOT_CLEAR");
		StringBuilder docReasonNames = new StringBuilder("");
		try {
			if(!Util.empty(documemtCheckListReasonList)){
				String SLASH ="";
				for(DocumentCheckListReasonDataM documentCheckListReasonDataM :documemtCheckListReasonList){						
					String docName = "";
					//check doc reason is not DOCUMENT_NOT_CLEAR
					if(!FOLLOW_UP_REASON_DOC_NOT_CLEAR.equals(documentCheckListReasonDataM.getDocReason())){
						docName = CacheControl.getName(FIELD_ID_DOC_CHECKLIST_REASON, documentCheckListReasonDataM.getDocReason());
						if("".equals(docReasonNames) || docReasonNames.length() == 0){
							docReasonNames.append("//");
							docReasonNames.append(docName);
						}
						else{
							docReasonNames.append(SLASH+docName);
						}
					}
					SLASH="//";
				}				
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return docReasonNames.toString();
	}
	public static boolean mandatoryFlag(PersonalInfoDataM personalInfo,String docCode){
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();		
		if(null != verificationResult){
			return verificationResult.containRequiredDocMandatoryFlag(docCode,MConstant.FLAG.YES);
		}
		return false;
	}
	public static String getDocumentReasonType(PersonalInfoDataM personalInfo,String documentCode,ArrayList<String> reasonIds){
		String REASON_TYPE_OTHER = SystemConstant.getConstant("REASON_TYPE_OTHER");
		String reasonTypeCode = REASON_TYPE_OTHER;
		try{
			boolean mandatoryFlag  = mandatoryFlag(personalInfo, documentCode);
			logger.debug("mandatoryFlag : "+mandatoryFlag);
			PegaApplicationDAO pegaApplicationDAO = new PegaApplicationDAOImpl();
			reasonTypeCode = pegaApplicationDAO.getDocumentReasonType(reasonIds,documentCode, mandatoryFlag);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		logger.debug("reasonTypeCode : "+reasonTypeCode);
		return reasonTypeCode;
	}
	private ArrayList<CSVContentDataM> mapCSVContent(ApplicationGroupDataM applicationGroup,SubmitApplicationObjectDataM submitApplicationObject,ArrayList<String> docTypeCheckLists){
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		SubmitApplicationRequest submitApplicationRequest = (SubmitApplicationRequest)submitApplicationObject.getSubmitApplicationRequest();
		ArrayList<CSVContentDataM> csvContents = new ArrayList<CSVContentDataM>();
		ArrayList<String> personalDocTypeIds  = new ArrayList<String>();
//		String userId = submitApplicationObject.getUserId();
		InquireDocSetResponse inquireDocSetResponse = (InquireDocSetResponse)submitApplicationObject.getInquireDocSetResponse();
		PersonalInfoDataM  personalInfoApplicant = getApplicationTypePersonalInfo(applicationGroup);	
		if(null == personalInfoApplicant) {
			personalInfoApplicant = new PersonalInfoDataM();
		}
		AddressDataM homeAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_CURRENT);
		if(Util.empty(homeAddress)){
			homeAddress = new AddressDataM();
		}
		AddressDataM officeAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_WORK);
		if(Util.empty(officeAddress)){
			officeAddress = new AddressDataM();
		}
		AddressDataM cardLinkAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_CURRENT_CARDLINK);
		if(Util.empty(cardLinkAddress)){
			cardLinkAddress=  new AddressDataM();
		}
		String customerAvailable = CacheControl.getName(FIELD_ID_CONTACT_TIME, "CHOICE_NO", personalInfoApplicant.getContactTime(), "MAPPING8");		
		ArrayList<DocTypeSegment> docTypeList = inquireDocSetResponse.getDocTypeList();
		for(DocTypeSegment docTypeSegment : docTypeList){
			ArrayList<FollowupReason> followupReasons = docTypeSegment.getFollowupReason();
			if(!Util.empty(followupReasons)){				
				String docCode = docTypeSegment.getDocTypeCode();
				String docName = CacheControl.getName(CACH_NAME_DOCUMENT_LIST,docCode);
				String group = CacheControl.getName(FIELD_ID_DOC_SCENARIO_GROUP,"CHOICE_NO",docTypeSegment.getDocTypeGroup(),"DISPLAY_NAME");
				String reasonTypeDesc  = CacheControl.getName(FIELD_ID_CHECKLIST_REASON_TYPE, "CHOICE_NO", REASON_TYPE_OTHER, "MAPPING8");
				String followUpRemark = docTypeSegment.getFollowUpRemark();
				if(!Util.empty(docCode) && docTypeCheckLists.contains(docCode)){
					reasonTypeDesc  = CacheControl.getName(FIELD_ID_CHECKLIST_REASON_TYPE, "CHOICE_NO", REASON_TYPE_CHECKLIST, "MAPPING8");
				}
				ArrayList<DocumentSegment> documentSegments = docTypeSegment.getDocumentSegment();
				if(!Util.empty(documentSegments)){
					for(DocumentSegment documentSegment : documentSegments){
						String applicantType = documentSegment.getApplicantType();
						if(Util.empty(applicantType)){
							applicantType = IM_PERSONAL_TYPE_APPLICANT;
						}
						logger.debug("applicantType : "+applicantType);
						String personalDocTypeId = applicantType+docCode;
						if(!personalDocTypeIds.contains(personalDocTypeId)){
							personalDocTypeIds.add(personalDocTypeId);
							String personalType = getPersonalTypeFromIMPersonalType(applicantType);
							String personalTypeDesc = CacheControl.getName(FIELD_ID_PERSONAL_TYPE,personalType);
							CSVContentDataM csvContent =  new CSVContentDataM();	
								csvContent.setCaseID(submitApplicationRequest.getQr2());		
								csvContent.setGroup(group);
								csvContent.setScenario(SCENARIO);
								csvContent.setDocName(docName);
								csvContent.setMandatory((MConstant.FLAG_Y.equals(getFixedZoneFlag(docTypeSegment)))?MConstant.FLAG_M:MConstant.FLAG_O);
								csvContent.setDocReason(reasonTypeDesc);
								csvContent.setReasonDetail(personalTypeDesc+" "+FormatUtil.displayText(personalInfoApplicant.getThFirstName())
										+getReasonCodeNameList(followupReasons)+"//"+FormatUtil.displayText(followUpRemark)+"//");
								csvContent.setCustAvailableTime(Util.empty(customerAvailable)?"N/A":customerAvailable);
								csvContent.setAlreadyFollow(DEFUALT_ALREADY_FOLLOW);									
//								csvContent.setRMOperatorID(userId);
								mapPersonalCSVContent(csvContent, personalInfoApplicant, homeAddress, officeAddress, cardLinkAddress);
							csvContents.add(csvContent);
						}
					}
				}else{
					String applicantType = IM_PERSONAL_TYPE_APPLICANT;
					logger.debug("applicantType : "+applicantType);
					String personalDocTypeId = applicantType+docCode;
					if(!personalDocTypeIds.contains(personalDocTypeId)){
						personalDocTypeIds.add(personalDocTypeId);
						String personalType = getPersonalTypeFromIMPersonalType(applicantType);
						String personalTypeDesc = CacheControl.getName(FIELD_ID_PERSONAL_TYPE,personalType);
						CSVContentDataM csvContent =  new CSVContentDataM();	
							csvContent.setCaseID(submitApplicationRequest.getQr2());		
							csvContent.setGroup(group);
							csvContent.setScenario(SCENARIO);
							csvContent.setDocName(docName);
							csvContent.setMandatory((MConstant.FLAG_Y.equals(getFixedZoneFlag(docTypeSegment)))?MConstant.FLAG_M:MConstant.FLAG_O);
							csvContent.setDocReason(reasonTypeDesc);
							csvContent.setReasonDetail(personalTypeDesc+" "+FormatUtil.displayText(personalInfoApplicant.getThFirstName())
									+"//"+getReasonCodeNameList(followupReasons)+"//"+FormatUtil.displayText(followUpRemark)+"//");
							csvContent.setCustAvailableTime(Util.empty(customerAvailable)?"N/A":customerAvailable);
							csvContent.setAlreadyFollow(DEFUALT_ALREADY_FOLLOW);									
//							csvContent.setRMOperatorID(userId);
							mapPersonalCSVContent(csvContent, personalInfoApplicant, homeAddress, officeAddress, cardLinkAddress);
						csvContents.add(csvContent);
					}
				}
			}		
		}
		return csvContents;
	}
	private void mapPersonalCSVContent(CSVContentDataM csvContent,PersonalInfoDataM  personalInfoApplicant
			,AddressDataM homeAddress,AddressDataM officeAddress,AddressDataM cardLinkAddress){
		csvContent.setMobile(personalInfoApplicant.getMobileNo());
		csvContent.setTelHome(homeAddress.getPhone1());
		csvContent.setTelHomeExt(homeAddress.getExt1());
		csvContent.setTelOffice(officeAddress.getPhone1());
		csvContent.setTelOfficeExt(officeAddress.getExt1());
		csvContent.setTelCardLink(cardLinkAddress.getPhone1());
		csvContent.setTelCardLinkExt(cardLinkAddress.getExt1());
		csvContent.setFirstNameTH(personalInfoApplicant.getThFirstName());
		csvContent.setMiddleNameTH(personalInfoApplicant.getThMidName());
		csvContent.setLastNameTH(personalInfoApplicant.getThLastName());
		csvContent.setFirstNameEN(personalInfoApplicant.getEnFirstName());
		csvContent.setMiddleNameEN(personalInfoApplicant.getEnMidName());
		csvContent.setLastNameEN(personalInfoApplicant.getEnLastName());
		csvContent.setPrimarySegment(personalInfoApplicant.getCisPrimSegment());
		csvContent.setPrimarySubSegment(personalInfoApplicant.getCisSecSubSegment());
		csvContent.setSecondarySegment(personalInfoApplicant.getCisSecSegment());
		csvContent.setSecondarySubSegment(personalInfoApplicant.getCisSecSubSegment());
	}
	
	private ArrayList<CSVContentDataM> mapDummyCSVContent(ApplicationGroupDataM applicationGroup,SubmitApplicationObjectDataM submitApplicationObject){
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		ArrayList<CSVContentDataM> csvContents = new ArrayList<CSVContentDataM>();
		SubmitApplicationRequest submitApplicationRequest = (SubmitApplicationRequest)submitApplicationObject.getSubmitApplicationRequest();
//		String userId = submitApplicationObject.getUserId();
		PersonalInfoDataM  personalInfoApplicant = getApplicationTypePersonalInfo(applicationGroup);	
		if(null == personalInfoApplicant){
			personalInfoApplicant = new PersonalInfoDataM();
		}
		AddressDataM homeAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_CURRENT);
		if(Util.empty(homeAddress)){
			homeAddress = new AddressDataM();
		}
		AddressDataM officeAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_WORK);
		if(Util.empty(officeAddress)){
			officeAddress = new AddressDataM();
		}
		AddressDataM cardLinkAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_CURRENT_CARDLINK);
		if(Util.empty(cardLinkAddress)){
			cardLinkAddress=  new AddressDataM();
		}
		String customerAvailable = CacheControl.getName(FIELD_ID_CONTACT_TIME, "CHOICE_NO", personalInfoApplicant.getContactTime(), "MAPPING8");
		CSVContentDataM csvContent =  new CSVContentDataM();	
			csvContent.setCaseID(submitApplicationRequest.getQr2());		
			csvContent.setGroup(DEFUALT_DUMMY);
			csvContent.setScenario(SCENARIO);
			csvContent.setDocName("");
			csvContent.setMandatory(MConstant.FLAG_O);
			csvContent.setDocReason("");
			csvContent.setReasonDetail("");
			csvContent.setCustAvailableTime(Util.empty(customerAvailable)?"N/A":customerAvailable);
			csvContent.setAlreadyFollow(DEFUALT_ALREADY_FOLLOW);
			csvContent.setMobile(personalInfoApplicant.getMobileNo());
			csvContent.setTelHome(homeAddress.getPhone1());
			csvContent.setTelHomeExt(homeAddress.getExt1());
			csvContent.setTelOffice(officeAddress.getPhone1());
			csvContent.setTelOfficeExt(officeAddress.getExt1());
			csvContent.setTelCardLink(cardLinkAddress.getPhone1());
			csvContent.setTelCardLinkExt(cardLinkAddress.getExt1());
			csvContent.setFirstNameTH(personalInfoApplicant.getThFirstName());
			csvContent.setMiddleNameTH(personalInfoApplicant.getThMidName());
			csvContent.setLastNameTH(personalInfoApplicant.getThLastName());
			csvContent.setFirstNameEN(personalInfoApplicant.getEnFirstName());
			csvContent.setMiddleNameEN(personalInfoApplicant.getEnMidName());
			csvContent.setLastNameEN(personalInfoApplicant.getEnLastName());
			csvContent.setPrimarySegment(personalInfoApplicant.getCisPrimSegment());
			csvContent.setPrimarySubSegment(personalInfoApplicant.getCisSecSubSegment());
			csvContent.setSecondarySegment(personalInfoApplicant.getCisSecSegment());
			csvContent.setSecondarySubSegment(personalInfoApplicant.getCisSecSubSegment());
//			csvContent.setRMOperatorID(userId);
			csvContents.add(csvContent);
		return csvContents;
	}
	private String getCSVContent(ArrayList<CSVContentDataM> csvContentDataMList){
		logger.debug("Csv Header.."+SUBMIT_APPLICATION_CSV_CONTENT_HEADER);
		CsvConfiguration csvConfiguration = new CsvConfiguration();
			csvConfiguration.setFieldDelimiter(',');
			csvConfiguration.setDefaultNoValueString("");
			csvConfiguration.setQuoteCharacter('"');
			csvConfiguration.setDefaultQuoteMode(QuoteMode.ALWAYS);
//			csvConfiguration.getSimpleTypeConverterProvider().registerConverterType(String.class,CsvUniCode.class);
			csvConfiguration.setLineBreak("\n");
		CsvSerializer serializer = (CsvSerializer) CsvIOFactory.createFactory(csvConfiguration, CSVContentDataM.class).createSerializer();
        StringWriter writer = new StringWriter();
        serializer.open(writer);
        serializer.getLowLevelSerializer().writeLine(SUBMIT_APPLICATION_CSV_CONTENT_HEADER); // the header
        if(!Util.empty(csvContentDataMList)){
        	for(CSVContentDataM csvContent : csvContentDataMList){
                serializer.write(csvContent);
        	}
        }
        serializer.close(true);
    	logger.debug("csv.toString().."+writer.toString());
        return writer.toString();
	}
	
	private PersonalInfoDataM getApplicationTypePersonalInfo(ApplicationGroupDataM applicationGroup){
		PersonalInfoDataM  personalInfo = new PersonalInfoDataM();
		if(!Util.empty(applicationGroup)){
			String APPLICATION_TYPE = applicationGroup.getApplicationType();
			if(APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE)){
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_INFO);
			}else{
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			}	
		}
		return personalInfo;
	}
	private String getReasonCodeNameList(ArrayList<FollowupReason> followupReasons){
		StringBuilder docReasonNames = new StringBuilder("");
		if(!Util.empty(followupReasons)){
			String SLASH ="";
			for(FollowupReason followupReason :followupReasons){
				String docName = "";
				//check doc reason is not DOCUMENT_NOT_CLEAR
				if(!DOC_FOLLOW_UP_REASON_CODE_DOC_NOT_CLEAR.equals(followupReason.getReasonCode())){
					docName = CacheControl.getName(FIELD_ID_DOC_CHECKLIST_REASON, followupReason.getReasonCode());
					if("".equals(docReasonNames) || docReasonNames.length() == 0){
						docReasonNames.append("//");
						docReasonNames.append(docName);
					}
					else{
						docReasonNames.append(SLASH+docName);
					}
				}
				SLASH="//";
			}				
		}
		return docReasonNames.toString();
	}
	
	private static boolean isDocComplete(PersonalInfoDataM personalInfo, DocumentCheckListDataM  documentCheckList) {
		
		boolean isDocComplete = true; 
		String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
		String REASON_CODE_NO_DOC = SystemConstant.getConstant("REASON_CODE_NO_DOC");
		String chkListDocCode = documentCheckList.getDocumentCode();
		String chkListDocTypeId = documentCheckList.getDocTypeId();
		logger.debug("chkListDocCode : "+chkListDocCode);	// ORIG_DOC_CHECK_LIST.DOCUMENT_CODE
		logger.debug("chkListDocTypeId : "+chkListDocTypeId); // ORIG_DOC_CHECK_LIST.DOC_TYPE_ID
		boolean foundReasonNoDoc = false;
		ArrayList<DocumentCheckListReasonDataM> documentCheckListReasons = documentCheckList.getDocumentCheckListReasons();
		if(null!=documentCheckListReasons){
			for(DocumentCheckListReasonDataM documentCheckReasons : documentCheckListReasons){
				String reasonCode = documentCheckReasons.getDocReason();
				if(REASON_CODE_NO_DOC.equals(reasonCode)){
					foundReasonNoDoc = true;
					break;
				}
			}
		}
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(null==verificationResult) {
			verificationResult = new VerificationResultDataM();
		}		
		ArrayList<RequiredDocDataM> requiredDocList = verificationResult.getRequiredDocs();
		if(!ServiceUtil.empty(requiredDocList)) {
			for(RequiredDocDataM requiredDoc : requiredDocList) {
				if(null==requiredDoc) {
					requiredDoc = new RequiredDocDataM();
				}
				
				ArrayList<RequiredDocDetailDataM> requiredDocDetailList = requiredDoc.getRequiredDocDetails();
				if(!ServiceUtil.empty(requiredDocDetailList)) {
					for(RequiredDocDetailDataM requiredDocDetail : requiredDocDetailList) {
						if(null==requiredDocDetail) {
							requiredDocDetail = new RequiredDocDetailDataM();
						}
						
						String reqDocScnType = requiredDoc.getScenarioType();
						String reqDocDocCompletedFlag = requiredDoc.getDocCompletedFlag();
						String reqDocDetailDocCode = requiredDocDetail.getDocumentCode();
						logger.debug("reqDocScnType : " + reqDocScnType);	// XRULES_REQUIRED_DOC.SCENARIO_TYPE
						logger.debug("reqDocDocCompletedFlag : " + reqDocDocCompletedFlag);	// XRULES_REQUIRED_DOC.DOC_COMPLETED_FLAG
						logger.debug("reqDocDetailDocCode : " + reqDocDetailDocCode); // XRULES_REQUIRED_DOC_DETAIL.DOCUMENT_CODE
						if(!ServiceUtil.empty(chkListDocCode) && !ServiceUtil.empty(reqDocScnType) && !ServiceUtil.empty(chkListDocTypeId) && !ServiceUtil.empty(reqDocDetailDocCode)	// check null
								&& chkListDocTypeId.equals(reqDocScnType) && chkListDocCode.equals(reqDocDetailDocCode)) {		// check match
								
							if(null==reqDocDocCompletedFlag || !FLAG_YES.equals(reqDocDocCompletedFlag) || (FLAG_YES.equals(reqDocDocCompletedFlag) && foundReasonNoDoc)) {
								isDocComplete = false;
							}
						}
					} // end of for : RequiredDocDetail
				}
			} // end of for : RequiredDoc
		}
		
		logger.info("result isDocComplete : "+isDocComplete);
		return isDocComplete;
	}
	public WorkflowResponseDataM createWorkflowTask(ApplicationGroupDataM applicationGroup) throws Exception{
		WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			workflowRequest.setUserId(applicationGroup.getUserId());
			workflowRequest.setUsername(applicationGroup.getUserId());
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
		WorkflowResponseDataM workflowResponse = workflowManager.createWorkflowTask(workflowRequest);
		applicationGroup.setInstantId(workflowResponse.getPiid());
		logger.debug("workflowResponse.getResultCode : "+workflowResponse.getResultCode());
		logger.debug("workflowResponse.getResultDesc : "+workflowResponse.getResultDesc());
		if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
			throw new Exception(workflowResponse.getResultDesc());
		}
		return workflowResponse;
	}
	
	public boolean isContainDocumentReasons(InquireDocSetResponse inquireDocSetResponse){
		 ArrayList<DocTypeSegment> docTypeList =  inquireDocSetResponse.getDocTypeList();
		 if(!Util.empty(docTypeList)){
			 for(DocTypeSegment docTypeSegment : docTypeList){
				 ArrayList<FollowupReason> followUpReasonList =  docTypeSegment.getFollowupReason();
				 if(!Util.empty(followUpReasonList)){
					 return true;
				 }
			 }
		}
		return false;
	}
	
	public void requestDecisionService(ApplicationGroupDataM applicationGroup,String decsionPoint) throws Exception{	
		try {			
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(decsionPoint);
			requestDecision.setUserId(applicationGroup.getUserId());
			 
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(DecisionServiceProxy.serviceId);
				serviceRequest.setUserId(applicationGroup.getUserId());
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setObjectData(requestDecision);
				
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
				DecisionServiceResponseDataM decisionResponse = (DecisionServiceResponseDataM)serivceResponse.getObjectData();
				if(serivceResponse.getStatusCode().equals(ServiceResponse.Status.SUCCESS)){
					postProcessRequestDecisionService(applicationGroup);
				}else{
					throw new Exception(ServiceResponse.Status.BUSINESS_EXCEPTION+" : call response error "); 
				}
			}else{
				throw new Exception(ServiceResponse.Status.BUSINESS_EXCEPTION+" : call decision error "); 
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);	
			throw new Exception(e.getLocalizedMessage()); 
		}
		
		
		/*try{	
			logger.debug("BPM_HOST >> "+BPM_HOST);
			logger.debug("BPM_PORT >> "+BPM_PORT);	
			WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
				workflowRequest.setApplicationGroup(applicationGroup);
				workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				workflowRequest.setFormAction(applicationGroup.getFormAction());
				workflowRequest.setDecisionAction(applicationGroup.getDecisionAction());
				workflowRequest.setInstantId(String.valueOf(applicationGroup.getInstantId()));	
				workflowRequest.setDecisionStation(ficoDecsionPoint);
				workflowRequest.setUserId(applicationGroup.getUserId());
				workflowRequest.setUsername(applicationGroup.getUserId());
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
			WorkflowResponseDataM workflowResponse = workflowManager.callBPMDecisionService(workflowRequest);
			String resultCode = workflowResponse.getResultCode();
			logger.debug("resultCode >> "+resultCode);
			if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(resultCode)){
				postProcessRequestFico(applicationGroup);
			}else{
				if(BpmProxyConstant.RestAPIResult.FICO_ERROR.equals(resultCode)){
					throw new Exception(workflowResponse.getErrorCode()+" : "+workflowResponse.getErrorMsg()); 
				}else{
					throw new Exception(workflowResponse.getResultDesc()); 
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);	
			throw new Exception(e.getLocalizedMessage()); 
		}*/
	}
	
	public void requestDecisionService(ApplicationGroupDataM applicationGroup,String decsionPoint,String userId) throws Exception{	
		try {			
			DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
			requestDecision.setApplicationGroup(applicationGroup);
			requestDecision.setDecisionPoint(decsionPoint);
			requestDecision.setUserId(applicationGroup.getUserId());
			 
			String URL = SystemConfig.getProperty("CALL_DECISION_SERVICE_URL");
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(DecisionServiceProxy.serviceId);
				serviceRequest.setUserId(userId);
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setObjectData(requestDecision);
				
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
				DecisionServiceResponseDataM decisionResponse = (DecisionServiceResponseDataM)serivceResponse.getObjectData();
				if(serivceResponse.getStatusCode().equals(ServiceResponse.Status.SUCCESS)){
					postProcessRequestDecisionService(applicationGroup);
				}else{
					throw new Exception(ServiceResponse.Status.BUSINESS_EXCEPTION+" : call response error "); 
				}
			}else{
				throw new Exception(ServiceResponse.Status.BUSINESS_EXCEPTION+" : call decision error "); 
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);	
			throw new Exception(e.getLocalizedMessage()); 
		}
	}
	
	public static void postProcessRequestDecisionService(ApplicationGroupDataM applicationGroup){
		DocumentCheckListUtil.defaultNotReceivedDocumentReason(applicationGroup);
	}
	
	String REASON_TYPE_CHECK_LIST = SystemConstant.getConstant("REASON_TYPE_CHECK_LIST");
	public ArrayList<String> getDocTypeCheckLists(InquireDocSetResponse inquireDocSetResponse) throws ServiceControlException{
		ArrayList<String> docTypeCheckLists = new ArrayList<String>();
		 ArrayList<DocTypeSegment> docTypeList =  inquireDocSetResponse.getDocTypeList();
		 if(!Util.empty(docTypeList)){
			 for(DocTypeSegment docTypeSegment : docTypeList){
				 String docCode = docTypeSegment.getDocTypeCode();
				 ArrayList<FollowupReason> followUpReasonList =  docTypeSegment.getFollowupReason();					
				 if(!Util.empty(followUpReasonList)){	
					 String fixedZoneFlag = getFixedZoneFlag(docTypeSegment);
					 ArrayList<String> reasonIds = new ArrayList<String>();								 
					 for(FollowupReason  followupReason : followUpReasonList){
						 String reasonCode = followupReason.getReasonCode();
						 if(!reasonIds.contains(reasonCode) && !Util.empty(reasonCode)){
							 reasonIds.add(reasonCode);
						 }
					 }
					 if(!Util.empty(docCode) && !Util.empty(reasonIds)){
						 String documentReasonType =  SubmitApplicationFactory.getSubmitApplicationDAO()
								 .getDocumentReasonType(reasonIds,docCode,(MConstant.FLAG_Y.equals(fixedZoneFlag)));
						 if(REASON_TYPE_CHECK_LIST.equals(documentReasonType) && !docTypeCheckLists.contains(docCode)){
							 docTypeCheckLists.add(docCode);
						 }
					 }	
				 }
			 }
		}
		return docTypeCheckLists;
	}
	private String getFixedZoneFlag(DocTypeSegment docTypeSegment){
		ArrayList<FollowupReason> followUpReasonList =  docTypeSegment.getFollowupReason();
		ArrayList<DocumentSegment>  documentSegmentList =  docTypeSegment.getDocumentSegment();
		 String fixedZoneFlag = MConstant.FLAG_N;
		 if(!Util.empty(documentSegmentList) && !fixedZoneFlag.equals(MConstant.FLAG_Y)){
			 for(DocumentSegment  documentSegment : documentSegmentList){
				 String fixZone = documentSegment.getFixedZoneFlag();
				 if(MConstant.FLAG_Y.equals(fixZone)){
					 fixedZoneFlag = fixZone;
				 }
			 }
		 }
		 ArrayList<String> reasonIds = new ArrayList<String>();
		 for(FollowupReason  followupReason : followUpReasonList){
			 String reasonCode = followupReason.getReasonCode();
			 if(!reasonIds.contains(reasonCode) && !Util.empty(reasonCode)){
				 reasonIds.add(reasonCode);
			 }
		 }	
		 // defualt fix zone when  doc is  not receive
		 if(Util.empty(documentSegmentList) && !Util.empty(DEFUALT_FIX_ZONE_REASON_CODE.retainAll(reasonIds))){
			 fixedZoneFlag = MConstant.FLAG_Y;
		 }
		 return fixedZoneFlag;
	}
	
	public ApplicationGroupDataM createNewApplication(CreateApplicationRequest createApplicationRequest) throws Exception{	
		
		SubmitApplicationRequest submitApplicationRequest = createApplicationRequest.getSubmitApplicationRequest();
		InquireDocSetResponse inquireDocSetResponse = createApplicationRequest.getInquireDocSetResponse();
		String applicationGroupId = createApplicationRequest.getApplicationGroupId();
		String coverPageType = createApplicationRequest.getCoverPageType();
		String userId = createApplicationRequest.getUserId();
		
		String templateCode = this.getTemplateCode(submitApplicationRequest.getQr1());
		String templateId = CacheControl.getName(CACHE_TEMPLATE,"TEMPLATE_CODE",templateCode ,"TEMPLATE_ID");
		String bunding = CacheControl.getName(CACHE_TEMPLATE,"TEMPLATE_CODE", templateCode,"BUNDING");
		String applicationGroupNo = submitApplicationRequest.getQr2();
		String rcCode = submitApplicationRequest.getRcCode();
		String branchCode = submitApplicationRequest.getBranchCode();
		String webScanUser = submitApplicationRequest.getWebScanUser();
		String applyChanel = submitApplicationRequest.getChannel();
		Date scanDate = submitApplicationRequest.getScanDate();
		String coverpagePriority = submitApplicationRequest.getPriority();		
		logger.debug("applicationGroupNo : "+applicationGroupNo);
		logger.debug("branchCode : "+branchCode);
		logger.debug("templateId : "+templateId);
		logger.debug("rcCode : "+rcCode);
		logger.debug("webScanUser : "+webScanUser);
		logger.debug("applyChanel : "+applyChanel);
		logger.debug("scanDate : "+scanDate);
		logger.debug("bunding : "+bunding);
		logger.debug("coverpagePriority : "+coverpagePriority);		
		ApplicationGroupDataM applicationGroup = new ApplicationGroupDataM();
		applicationGroup.setApplicationGroupId(applicationGroupId);
		applicationGroup.setWebScanUser(webScanUser);
		applicationGroup.setApplicationTemplate(templateId);		
		applicationGroup.setApplicationGroupNo(applicationGroupNo);
		applicationGroup.setRcCode(rcCode);
		applicationGroup.setBranchNo(branchCode);
//		applicationGroup.setBranchName(CacheControl.getName(CACHE_BRANCH_INFO,"KBNK_BR_NO",branchCode,"TH_CNTR_NM"));
//		applicationGroup.setBranchRegion(CacheControl.getName(CACHE_BRANCH_INFO,"KBNK_BR_NO",branchCode,"KBNK_RGON_CD"));
//		applicationGroup.setBranchZone(CacheControl.getName(CACHE_BRANCH_INFO,"KBNK_BR_NO",branchCode,"KBNK_ZON_NO"));
		DIHQueryResult<KbankBranchInfoDataM> kbankBranchResult = DIHProxy.getKbankBranchData(branchCode);
		KbankBranchInfoDataM kbankBranchInfo = kbankBranchResult.getResult();
			applicationGroup.setBranchName(kbankBranchInfo.getBranchName());
			applicationGroup.setBranchRegion(kbankBranchInfo.getBranchRegion());
			applicationGroup.setBranchZone(kbankBranchInfo.getBranchZone());		
		applicationGroup.setUserId(userId);
		applicationGroup.setApplyChannel(applyChanel);
		applicationGroup.setApplicationDate(Util.toSqlDate(scanDate));
		applicationGroup.setApplyDate(Util.toSqlDate(scanDate));
		applicationGroup.setRandomNo1(getRandomNo());
		applicationGroup.setRandomNo2(getRandomNo());
		applicationGroup.setRandomNo3(getRandomNo());
		applicationGroup.setBundleFlag(!Util.empty(bunding)?MConstant.FLAG_Y:"");
		applicationGroup.setCoverpageType(getCoverageType(coverPageType));
		applicationGroup.setCoverpagePriority(coverpagePriority);
		mapImageIndex(inquireDocSetResponse, applicationGroup);	
		LookupServiceCenter.getServiceCenterManager().saveApplication(applicationGroup,userId,true);		
		return applicationGroup;
	}
	@SuppressWarnings("unused")
	public void mapImageIndex(InquireDocSetResponse inquireDocSetResponse,ApplicationGroupDataM applicationGroup){
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		logger.debug("applicationGroupId>>"+applicationGroupId);		
		logger.debug("DOC_TYPE_ADDITIONAL>>"+applicationGroupId);		
		ArrayList<DocumentCheckListDataM> documentCheckLists = new ArrayList<DocumentCheckListDataM>();
		String appImgId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_IMG_PK);
		ApplicationImageDataM applicationImage = new ApplicationImageDataM();
		applicationImage.setAppImgId(appImgId);
		applicationImage.setPath(inquireDocSetResponse.getIMURL());
		applicationImage.setApplicationGroupId(applicationGroupId);
		
		ArrayList<ApplicationImageDataM> lastApplicationImages =applicationGroup.getApplicationImages();
		if(!Util.empty(lastApplicationImages)){
			for(ApplicationImageDataM lastApplicationImage :lastApplicationImages){
				ArrayList<ApplicationImageSplitDataM> lastApplicationImageSplits =lastApplicationImage.getApplicationImageSplits();
				if(!Util.empty(lastApplicationImageSplits)){
					for(ApplicationImageSplitDataM lastApplicationImageSplit : lastApplicationImageSplits){
						String documentType  = lastApplicationImageSplit.getDocType();
						logger.debug("documentType : "+documentType);
						if(null!=documentType && DOC_TYPE_ADDITIONAL.contains(documentType)){
							String appImgSplitId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APP_IMG_SPLIT_PK);
							lastApplicationImageSplit.setImgPageId(appImgSplitId);
							lastApplicationImageSplit.setAppImgId(appImgId);
							applicationImage.add(lastApplicationImageSplit);
						}
					}
				}
			}
		}
		
		ArrayList<DocTypeSegment> docTypeSegmentList =  inquireDocSetResponse.getDocTypeList();			
		if(!Util.empty(docTypeSegmentList)){
			for(DocTypeSegment  docTypeSegment:docTypeSegmentList){
				String docTypeCode = docTypeSegment.getDocTypeCode();
				String docTypeGroup=docTypeSegment.getDocTypeGroup();
				String followUpRemark =docTypeSegment.getFollowUpRemark();
				ArrayList<String> applicantTypes = new ArrayList<String>();
				ArrayList<DocumentSegment>  documentSegmentList = docTypeSegment.getDocumentSegment();
				String DOCUMENT_CATEGORY = CacheControl.getName(FIELD_ID_DOC_SCENARIO_GROUP,"MAPPING9",docTypeGroup,"CHOICE_NO"); 
				logger.debug("DOCUMENT_CATEGORY >> "+DOCUMENT_CATEGORY);
				if(!Util.empty(documentSegmentList)){
					for(DocumentSegment documentSegment : documentSegmentList){
						String applicantType = documentSegment.getApplicantType();
						if(!Util.empty(documentSegment.getIMInternalID())){
							String appImgSplitId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APP_IMG_SPLIT_PK);
							ApplicationImageSplitDataM applicationImageSplit = new ApplicationImageSplitDataM();
							logger.debug("appImgId >> "+appImgId);
							logger.debug("applicantType >> "+applicantType);
							applicationImageSplit.setAppImgId(appImgId);
							applicationImageSplit.setImgPageId(appImgSplitId);
							applicationImageSplit.setDocType(docTypeCode);
							applicationImageSplit.setDocTypeGroup(DOCUMENT_CATEGORY);
							applicationImageSplit.setFixedZoneFlag(documentSegment.getFixedZoneFlag());
							applicationImageSplit.setSeq(documentSegment.getSeq());
							applicationImageSplit.setDocTypeSeq(documentSegment.getDocTypeSeq());
							applicationImageSplit.setImageId(documentSegment.getIMInternalID());
							applicationImageSplit.setApplicantTypeIM(applicantType);
							applicationImage.add(applicationImageSplit);	
						}
						if(!Util.empty(applicantType) && !applicantTypes.contains(applicantType)){
							applicantTypes.add(applicantType);
						}
					}
				}
				
				logger.debug("applicantTypes : "+applicantTypes);
				if(!Util.empty(applicantTypes)){
					for(String applicantType : applicantTypes){						
						ArrayList<FollowupReason> followupReasons = docTypeSegment.getFollowupReason();
						if(!Util.empty(followupReasons)){
							String docCheckListId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_DOC_CHECK_LIST_PK );
							DocumentCheckListDataM documentCheckList = new DocumentCheckListDataM();
								documentCheckList.setDocCheckListId(docCheckListId);
								documentCheckList.setDocumentCode(docTypeCode);
								documentCheckList.setDocTypeId(docTypeGroup);
								documentCheckList.setApplicantTypeIM(applicantType);		
								documentCheckList.setApplicationGroupId(applicationGroupId);
							for(FollowupReason followupReason :followupReasons){
								String docCheckListReasonId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_DOC_CHKLST_REASON_PK);
								DocumentCheckListReasonDataM  documentCheckListReason = new DocumentCheckListReasonDataM();
								documentCheckListReason.setDocCheckListId(docCheckListId);
								documentCheckListReason.setDocCheckListReasonId(docCheckListReasonId);
								documentCheckListReason.setDocReason(followupReason.getReasonCode());
								documentCheckList.add(documentCheckListReason);
							}
						    documentCheckLists.add(documentCheckList);
						}					
					}						
				}
			}
		}		

		ArrayList<ApplicationImageDataM> applicationImages = new ArrayList<ApplicationImageDataM>();
		applicationImages.add(applicationImage);
		applicationGroup.setApplicationImages(applicationImages);
		
		ArrayList<DocumentCheckListDataM> docCheckListCurs = applicationGroup.getDocumentCheckLists();
		if(null == docCheckListCurs) {
			docCheckListCurs = new ArrayList<>();
			applicationGroup.setDocumentCheckLists(docCheckListCurs);
		}
		ArrayList<DocumentCheckListDataM> docCheckListManuals = applicationGroup.getDocumentCheckListManuals();
		if(null == docCheckListManuals) {
			docCheckListManuals = new ArrayList<>();
			applicationGroup.setDocumentCheckListManuals(docCheckListManuals);
		}
		ArrayList<DocumentCheckListDataM> docCheckListManualAdditionals = applicationGroup.getDocumentCheckListManualAdditionals();
		if(null == docCheckListManualAdditionals) {
			docCheckListManualAdditionals = new ArrayList<>();
			applicationGroup.setDocumentCheckListManualAdditionals(docCheckListManualAdditionals);
		}
		logger.debug("!!DOC_CHECK_LIST!! SIZE :: " + docCheckListCurs.size());
		logger.debug("!!DOC_CHECK_LIST!! MANUAL_SIZE :: " + docCheckListManuals.size());
		logger.debug("!!DOC_CHECK_LIST!! ADDITN_SIZE :: " + docCheckListManualAdditionals.size());
		docCheckListManuals.addAll(docCheckListManualAdditionals);
		for(DocumentCheckListDataM doc : documentCheckLists) {
			String docCode = doc.getDocumentCode();
			logger.debug("!!DOC_CHECK_LIST!! DOC_CODE :: " + docCode);
			int index = 0;
			for(DocumentCheckListDataM docManual : docCheckListManuals) {
				String docCodeManual = docManual.getDocumentCode();
				logger.debug("!!DOC_CHECK_LIST!! DOC_MANUAL_CODE :: " + docCodeManual);
				if(docCode.equals(docCodeManual)) {
					docCheckListManuals.remove(index);
					index--;
				}
				index++;
			}
		}
		documentCheckLists.addAll(docCheckListManuals);
		applicationGroup.setDocumentCheckLists(documentCheckLists);
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				String personalId = personalInfo.getPersonalId();
				String personalType = personalInfo.getPersonalType();
				int personalSeq = personalInfo.getSeq();
				PersonalInfoUtil.defaultPersonalIdOfImage(applicationGroup, personalId, personalType, personalSeq);
			}
		}
		ArrayList<DocumentCheckListDataM> docLog = applicationGroup.getDocumentCheckLists();
		logger.debug("!!DOC_CHECK_LIST!! MAP_IMAGE_INDEX_END :: " + docLog.size());
		for(DocumentCheckListDataM doc : docLog) {
			logger.debug("!!DOC_CHECK_LIST!! MAP_IMAGE_INDEX_END DOC_CODE :: " + doc.getDocumentCode());
			logger.debug("!!DOC_CHECK_LIST!! MAP_IMAGE_INDEX_END MAN_FLAG :: " + doc.getManualFlag());
		}
	}
	private Integer getRandomNo(){
		try { 
			Random rand = new Random();
			StringBuilder randomNo = new StringBuilder();
			randomNo.append(rand.nextInt(10)).append(rand.nextInt(10)).append(rand.nextInt(10));					 
			return Integer.valueOf(randomNo.toString());	
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
	
	public static String getCoverageType(String qrCoverpage){
		String FIELD_ID_COVER_PAGE_TYPE = SystemConstant.getConstant("FIELD_ID_COVER_PAGE_TYPE");
		String coverpageType = CacheControl.getName(FIELD_ID_COVER_PAGE_TYPE,"SYSTEM_ID2",qrCoverpage,"CHOICE_NO");
//		if(coverpageType == null){
//			coverpageType = CacheControl.getName(FIELD_ID_COVER_PAGE_TYPE,"SYSTEM_ID2",null,"CHOICE_NO");
//		}
		if(Util.empty(coverpageType)){
			coverpageType = SystemConstant.getConstant("COVER_PAGE_TYPE_NEW");
		}
		logger.debug("coverpageType >> "+coverpageType);
		return coverpageType;
	}
	
	public String getPersonalTypeFromIMPersonalType(String applicantType) {
		String personalType = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		if(applicantType.contains(IM_PERSONAL_TYPE_APPLICANT)){
			personalType = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		}else if(applicantType.contains(IM_PERSONAL_TYPE_SUPPLEMENTARY)){
			personalType = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
		}
		return personalType;
	}
	public String getTemplateCode(String qr1){
		String templateCode="";
		if(!Util.empty(qr1)){
			templateCode=qr1.substring(5, qr1.length()-3);
		}
		logger.debug("templateCode : "+templateCode);
		return templateCode;
	}
	public static boolean errorInstant(Integer instantId){
		return (null == instantId || instantId == 0);
	}
	public void workflowTaskProcessAction(ApplicationGroupDataM applicationGroup,String userId) throws Exception{
		createWorkflowTask(applicationGroup);
	}
	public static boolean sendDummyToPegaFlag(String applicationGroupId,int lifeCycle) throws Exception{		
		logger.debug("applicationGroupId : "+applicationGroupId);
		logger.debug("lifeCycle : "+lifeCycle);
		return ServiceFactory.getServiceDAO().sendDummyToPegaFlag(applicationGroupId,lifeCycle);
	}
	public static ErrorData error(String errorType,String errorCode,String errorDesc){
		ErrorData errorData = new ErrorData();
		errorData.setErrorType(errorType);
		errorData.setErrorCode(errorCode);
		errorData.setErrorDesc(errorDesc);
		errorData.setErrorInformation(errorDesc);
		return errorData;
	}
	public static ErrorData error(Exception e){	
		ErrorData errorData = new ErrorData();
		errorData.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
		errorData.setErrorInformation(e.getLocalizedMessage());
		errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
		return errorData;
	}
	public static ProcessResponse error(ServiceResponseDataM serviceResponse){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(serviceResponse.getStatusCode());
		ErrorData errorData = new ErrorData();
		ServiceErrorInfo errorInfo = serviceResponse.getErrorInfo();
		if(null != errorInfo){
			errorData.setErrorCode(errorInfo.getErrorCode());
			errorData.setErrorDesc(errorInfo.getErrorDesc());
			errorData.setErrorInformation(errorInfo.getErrorInformation());
			errorData.setErrorSystem(errorInfo.getErrorSystem());
			errorData.setErrorTime(errorInfo.getErrorTime());
			errorData.setErrorType(errorInfo.getErrorType());
			errorData.setServiceId(errorInfo.getServiceId());
		}
		processResponse.setErrorData(errorData);
		return processResponse;
	}
}
