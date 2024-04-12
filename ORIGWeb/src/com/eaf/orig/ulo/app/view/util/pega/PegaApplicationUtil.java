package com.eaf.orig.ulo.app.view.util.pega;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.CsvSerializer;
import org.jsefa.csv.config.CsvConfiguration;
import org.jsefa.csv.lowlevel.config.QuoteMode;

import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigFollowDocHistoryDAO;
import com.eaf.orig.ulo.app.dao.PegaApplicationDAO;
import com.eaf.orig.ulo.app.dao.PegaApplicationDAOImpl;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationUtil;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;
import com.eaf.orig.ulo.model.app.FollowDocHistoryDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.pega.ErrorDataM;
import com.eaf.orig.ulo.model.pega.FollowUpRequest;
import com.eaf.orig.ulo.model.pega.FollowUpResponse;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceLogData;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.manual.PegaFollowUpServiceProxy;

public class PegaApplicationUtil {
	private static transient Logger logger = Logger.getLogger(PegaApplicationUtil.class);
	private static String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	private static String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	private static String ADDRESS_TYPE_CURRENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT_CARDLINK");
	private static String FIELD_ID_CONTACT_TIME = SystemConstant.getConstant("FIELD_ID_CONTACT_TIME");
	private static String SUBMIT_APPLICATION_CSV_CONTENT_HEADER = SystemConstant.getConstant("SUBMIT_APPLICATION_CSV_CONTENT_HEADER");
	private static String SCENARIO = SystemConstant.getConstant("DEFUALT_DSCENARIO");
	private static String DEFUALT_ALREADY_FOLLOW = SystemConstant.getConstant("DEFUALT_ALREADY_FOLLOW");
	private static String DEFUALT_DUMMY = SystemConstant.getConstant("DEFUALT_DUMMY");
	private static String FIELD_ID_DOC_CHECKLIST_REASON = SystemConstant.getConstant("FIELD_ID_DOC_CHECKLIST_REASON");
	private static String CACH_NAME_DOCUMENT_LIST = SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST");
	private static String REASON_TYPE_OTHER = SystemConstant.getConstant("REASON_TYPE_OTHER");
	private static String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
	private static String FIELD_ID_CHECKLIST_REASON_TYPE = SystemConstant.getConstant("FIELD_ID_CHECKLIST_REASON_TYPE");
	private static String FIELD_ID_DOC_SCENARIO_GROUP = SystemConstant.getConstant("FIELD_ID_DOC_SCENARIO_GROUP");
	private static String DOC_FOLLOW_UP_REASON_CODE_DOC_NOT_CLEAR = SystemConstant.getConstant("DOC_FOLLOW_UP_REASON_CODE_DOC_NOT_CLEAR");
	
	private static String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private static String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	public static boolean isDummyToPegaFlag(ApplicationGroupDataM applicationGroup){
		boolean isDummyToPegaFlag = true;
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(null != personalInfo){
			VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
			if(null != verificationResult){
				ArrayList<RequiredDocDataM> requiredDocs = verificationResult.getRequiredDocs();
				if(!Util.empty(requiredDocs)){
					for (RequiredDocDataM requiredDoc : requiredDocs) {
						 ArrayList<RequiredDocDetailDataM> requiredDocDetails = requiredDoc.getRequiredDocDetails();
						 if(!Util.empty(requiredDocDetails)){
							 isDummyToPegaFlag = false;
						 }
					}
				}
			}
		}
		return isDummyToPegaFlag;
	}
	
	public static String getDVUserForPEGA()
	{
		String NONDV_PEGA_OPERATOR = SystemConstant.getConstant("NONDV_PEGA_OPERATOR");
		
		//Find all DV users
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<String> dvOnlineList = new ArrayList<>();
		ArrayList<String> dvAllList = new ArrayList<>();
		OrigObjectDAO orig = new OrigObjectDAO();
		try{	
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT UD.USER_NAME, UD.LOGON_FLAG FROM US_USER_DETAIL UD ");
			sql.append(" JOIN USER_ROLE UR ON UR.USER_NAME = UD.USER_NAME ");
			sql.append(" WHERE UR.ROLE_ID = 'R0078' AND UD.USER_NAME LIKE 'K%' AND UD.ACTIVE_STATUS = 'A' ");
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()) 
			{
				if(MConstant.FLAG.YES.equals(rs.getString("LOGON_FLAG")))
				{dvOnlineList.add(rs.getString("USER_NAME"));}
				
				dvAllList.add(rs.getString("USER_NAME"));
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}
		finally
		{
			try {
				orig.closeConnection(conn, rs, ps);
			} catch (Exception e) {}
		}
		
		if(dvOnlineList.size() > 0)
		{
			//Randomly choose from online DV users
			NONDV_PEGA_OPERATOR = dvOnlineList.get(new Random().nextInt(dvOnlineList.size()));
		}
		else
		{
			//Randomly choose from all DV users
			if(dvAllList.size() > 0) //check size in case database problem
			{NONDV_PEGA_OPERATOR = dvAllList.get(new Random().nextInt(dvAllList.size()));}
		}
		
		logger.debug("Assign user to Follow-up PEGA in non-DV station : " + NONDV_PEGA_OPERATOR);
		return NONDV_PEGA_OPERATOR;
	}
	
	public static FollowUpResponse sendToPega(ApplicationGroupDataM applicationGroup,UserDetailM userM ,boolean isDummy) throws Exception {
		//String NONDV_PEGA_OPERATOR = SystemConstant.getConstant("NONDV_PEGA_OPERATOR");
		String ROLE_DV = SystemConstant.getConstant("ROLE_DV");
		String ROLE_FU = SystemConstant.getConstant("ROLE_FU");
		String taskId = applicationGroup.getTaskId();
		logger.debug("taskId : "+taskId);
		ServiceLogData serviceLogData = com.eaf.service.common.dao.ServiceFactory.getServiceDAO()
					.getServiceData(applicationGroup.getApplicationGroupNo(),PegaFollowUpServiceProxy.serviceId, taskId);
		if(ServiceConstant.Status.SUCCESS.equals(serviceLogData.getResponseCode())){
			logger.warn("send to pega success from service log.");
			FollowUpResponse followUpResponse = new FollowUpResponse();
				followUpResponse.setStatusCode(ServiceConstant.Status.SUCCESS);
			return followUpResponse;
		}
		logger.debug("sendToPega..");
		logger.debug("isDummy >> "+isDummy);
		String URL = SystemConfig.getProperty("PEGA_FOLLOWUP_URL");
		logger.debug("URL >> "+URL);
		FollowUpRequest followUpRequest =  new FollowUpRequest();
			followUpRequest.setCaseID(applicationGroup.getApplicationGroupNo());
			followUpRequest.setUserId(userM.getUserName());
		ArrayList<CSVContentDataM> csvContents = new ArrayList<CSVContentDataM>();
		if(isDummy){
			csvContents = mappingDummyCSVContent(applicationGroup,userM);
			followUpRequest.setPegaOperatorID(SystemConfig.getProperty("PEGA_OPERATOER_ID"));
		}else{				
			//Change userM for KPL Saving Plus in case Non-DV, Non-FU Follow PEGA
			String role = (String) userM.getRoles().elementAt(0);
			csvContents = mappingCSVContent(applicationGroup, userM);
			followUpRequest.setPegaOperatorID((!ROLE_DV.equals(role) && !ROLE_FU.equals(role)) ? getDVUserForPEGA() : userM.getUserName());
			if(Util.empty(csvContents)){
				csvContents = mappingDummyCSVContent(applicationGroup,userM);
				followUpRequest.setPegaOperatorID(SystemConfig.getProperty("PEGA_OPERATOER_ID"));
			}
		}
		followUpRequest.setCSVContent(getCSVContent(csvContents));		
		ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
			serviceRequest.setServiceId(PegaFollowUpServiceProxy.serviceId);
			serviceRequest.setUserId(userM.getUserName());
			serviceRequest.setEndpointUrl(URL);
			serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
			serviceRequest.setObjectData(followUpRequest);	
			serviceRequest.setServiceData(taskId);
		ServiceCenterProxy proxy = new ServiceCenterProxy();
		ServiceResponseDataM serviceResponse = proxy.requestService(serviceRequest);
		String followUpResponseResult = serviceResponse.getStatusCode();
		logger.debug("followUpResponseResult >> "+followUpResponseResult);
		FollowUpResponse followUpResponse = (FollowUpResponse)serviceResponse.getObjectData();
		if(ServiceResponse.Status.SUCCESS.equals(followUpResponseResult)){
			if(!isDummy){
				createDocumentFollowUpHistoryPega(applicationGroup,userM);
			}
		}else{
			/* change to use method from DecisionApplicationUtil						
				String idNo = "";
			if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
				idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
			} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
				idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
			}
			UtilityDAO utilDao = new UtilityDAOImpl();
			utilDao.deleteErrorSubmitIATimestamp(idNo);
			*/
			DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
			if(Util.empty(followUpResponse)){
				followUpResponse = new FollowUpResponse();
				followUpResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				List<ErrorDataM> errors = new ArrayList<ErrorDataM>();
				ErrorDataM error = new ErrorDataM();
				error.setErrorDesc("response is null");
				errors.add(error);
				followUpResponse.setError(errors);
			}
		}
		return followUpResponse;
	}	
	
	public static ArrayList<CSVContentDataM> mappingCSVContent(ApplicationGroupDataM applicationGroup,UserDetailM userM ){
		String ROLE_FU = SystemConstant.getConstant("ROLE_FU");
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
		if(Util.empty(personalInfos)){
			personalInfos = new ArrayList<PersonalInfoDataM>();
		}		
		String customerAvailable = CacheControl.getName(FIELD_ID_CONTACT_TIME, "CHOICE_NO", personalInfoApplicant.getContactTime(), "MAPPING8");		
		logger.debug("customerAvailable : "+customerAvailable);		
		logger.debug("personalInfos : "+personalInfos);
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
				logger.debug("Is role FU : " + ROLE_FU.equals(userM.getRoles().elementAt(0)));
				if(ROLE_FU.equals(userM.getRoles().elementAt(0))) {
					ArrayList<DocumentCheckListDataM> documentCheckListManuals = applicationGroup.getDocumentCheckListManuals();
					ArrayList<DocumentCheckListDataM> documentCheckListManualAdditionals = applicationGroup.getDocumentCheckListManualAdditionals();
					if(null != documentCheckListManuals) {
						documentCheckLists.addAll(documentCheckListManuals);
					}
					if(null != documentCheckListManualAdditionals) {
						documentCheckLists.addAll(documentCheckListManualAdditionals);
					}
					logger.debug("RoleFU documentCheckLists : " + documentCheckLists);
				}
				for(DocumentCheckListDataM  documentCheckList : documentCheckLists){
					if(null==documentCheckList) {
						documentCheckList = new DocumentCheckListDataM();
					}
					
					if(!isDocComplete(personalInfo, documentCheckList)
						|| (KPLUtil.isSavingPlusDoc(documentCheckList.getDocumentCode()) && KPLUtil.isSavingPlusNoDoc(applicationGroup))
							) {
						String docCode = documentCheckList.getDocumentCode();
						String docName = CacheControl.getName(CACH_NAME_DOCUMENT_LIST,docCode);
						ArrayList<String> docReasonIds = documentCheckList.getDocumentCheckListDocReasonCodes();										
//						String documentGroup = applicationGroup.getDocumentCategory(docCode);
						String docTypeId = documentCheckList.getDocTypeId();
						String reasonTypeCode = getDocumentReasonType(personalInfo,docCode,docReasonIds);
						String group = CacheControl.getName(FIELD_ID_DOC_SCENARIO_GROUP,"CHOICE_NO",docTypeId,"DISPLAY_NAME");
						if(Util.empty(reasonTypeCode)){
							reasonTypeCode = REASON_TYPE_OTHER;
						}
						logger.debug("docCode : "+docCode);
						logger.debug("docTypeId : "+docTypeId);
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
							csvContent.setReasonDetail(personalTypeDesc+" "+FormatUtil.displayText(personalInfo.getThFirstName())+getReasonCodeNameList(documentCheckList.getDocumentCheckListReasons())+"//"+documentCheckList.getRemark());
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
//							csvContent.setRMOperatorID(userM.getUserName());
						csvContents.add(csvContent);	
					}
				} // end of for : documentCheckLists
			}			 
		}
		return csvContents;
	}
	
	public static ArrayList<CSVContentDataM> mappingDummyCSVContent(ApplicationGroupDataM applicationGroup,UserDetailM userM ){
		PersonalInfoDataM personalInfoApplicant = PersonalInfoUtil.getMainPersonalInfo(applicationGroup);
		if(null == personalInfoApplicant){
			personalInfoApplicant = new PersonalInfoDataM();
		}
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
		String customerAvailable = CacheControl.getName(FIELD_ID_CONTACT_TIME, "CHOICE_NO", personalInfoApplicant.getContactTime(), "MAPPING8");
		ArrayList<CSVContentDataM> csvContents = new ArrayList<CSVContentDataM>();
		CSVContentDataM csvContent =  new CSVContentDataM();	
			csvContent.setCaseID(applicationGroup.getApplicationGroupNo());		
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
//			csvContent.setRMOperatorID(userM.getUserName());
		csvContents.add(csvContent);
		return csvContents;
	}
		
	public static String getCSVContent(ArrayList<CSVContentDataM> csvContents){
		logger.debug("Csv Header.."+SUBMIT_APPLICATION_CSV_CONTENT_HEADER);
		CsvConfiguration csvConfiguration = new CsvConfiguration();
			csvConfiguration.setFieldDelimiter(',');
			csvConfiguration.setDefaultNoValueString("");
			csvConfiguration.setQuoteCharacter('"');
			csvConfiguration.setDefaultQuoteMode(QuoteMode.ALWAYS);
//			csvConfiguration.getSimpleTypeConverterProvider().registerConverterType(String.class, CsvUniCode.class);
//			csvConfiguration.setQuoteCharacterEscapeMode(EscapeMode.ESCAPE_CHARACTER);
			csvConfiguration.setLineBreak("\n");
		CsvSerializer serializer = (CsvSerializer) CsvIOFactory.createFactory(csvConfiguration, CSVContentDataM.class).createSerializer();
        StringWriter writer = new StringWriter();
        serializer.open(writer);
        serializer.getLowLevelSerializer().writeLine(SUBMIT_APPLICATION_CSV_CONTENT_HEADER); // the header
        if(!Util.empty(csvContents)){
        	for(CSVContentDataM csvContent : csvContents){
                serializer.write(csvContent);
        	}
        }
        serializer.close(true);
    	logger.debug("csv.toString().."+writer.toString());
        return writer.toString();
	}
		
	public static String getReasonCodeNameList(ArrayList<DocumentCheckListReasonDataM> documemtCheckListReasonList){
		StringBuilder docReasonNames = new StringBuilder("");
		try {
			if(!Util.empty(documemtCheckListReasonList)){
				String SLASH ="";
				for(DocumentCheckListReasonDataM documentCheckListReasonDataM :documemtCheckListReasonList){						
					String docName = "";
					//check doc reason is not DOCUMENT_NOT_CLEAR
					if(!DOC_FOLLOW_UP_REASON_CODE_DOC_NOT_CLEAR.equals(documentCheckListReasonDataM.getDocReason())){
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
		
	public static void createDocumentFollowUpHistoryPega(ApplicationGroupDataM applicationGroup,UserDetailM userM){
		try{
			logger.debug("ApplicationGroupId>>>"+applicationGroup.getApplicationGroupId());
			ArrayList<FollowDocHistoryDataM> followDocumentHistorys = getMappingFollowDocHistory(applicationGroup,userM);
			if(!Util.empty(followDocumentHistorys)){
				ORIGDAOFactory.getFollowDocHistoryDAO().createOrigFollowDocHistoryM(followDocumentHistorys);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
	}
		
	private static boolean isDocComplete(PersonalInfoDataM personalInfo, DocumentCheckListDataM  documentCheckList) {
		if(null != documentCheckList.getManualFlag()) {
			logger.debug("Is manual document, doc is not complete, return false :: " + documentCheckList.getManualFlag());
			return false;
		}
		boolean isDocComplete = true; 
		String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
		String REASON_CODE_NO_DOC = SystemConstant.getConstant("REASON_CODE_NO_DOC");
		String chkListDocCode = documentCheckList.getDocumentCode();
		String chkListDocTypeId = documentCheckList.getDocTypeId();
		boolean foundGeneratedReasonNoDoc = false;
		ArrayList<DocumentCheckListReasonDataM> documentCheckListReasons = documentCheckList.getDocumentCheckListReasons();
		if(null!=documentCheckListReasons){
			for(DocumentCheckListReasonDataM documentCheckReasons : documentCheckListReasons){
				String reasonCode = documentCheckReasons.getDocReason();
				String generateFlag = documentCheckReasons.getGenerateFlag();
				if(REASON_CODE_NO_DOC.equals(reasonCode) && FLAG_YES.equals(generateFlag)){
					foundGeneratedReasonNoDoc = true;
					break; 
				}
			}
		}
		logger.debug("chkListDocCode : "+chkListDocCode);	// ORIG_DOC_CHECK_LIST.DOCUMENT_CODE
		logger.debug("chkListDocTypeId : "+chkListDocTypeId); // ORIG_DOC_CHECK_LIST.DOC_TYPE_ID
		
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(null==verificationResult) {
			verificationResult = new VerificationResultDataM();
		}
		String verDocCompletedFlag = verificationResult.getDocCompletedFlag();
		logger.debug("XRULES_VERIFICATION_RESULT.DOC_COMPLETED_FLAG : "+verDocCompletedFlag);
		
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
							if((null==reqDocDocCompletedFlag || !FLAG_YES.equals(reqDocDocCompletedFlag)) || (FLAG_YES.equals(reqDocDocCompletedFlag) && !foundGeneratedReasonNoDoc)) {	// check detail
								isDocComplete = false;
							}
						}
					}
				}
			}
		}
		
		logger.info("result isDocComplete : "+isDocComplete);
		return isDocComplete;
	}
	private static ArrayList<FollowDocHistoryDataM> getMappingFollowDocHistory(ApplicationGroupDataM applicationGroup,UserDetailM userM){
		ArrayList<FollowDocHistoryDataM> followDocHistoryList = new ArrayList<FollowDocHistoryDataM>();
		try {
			String applicationGroupId = applicationGroup.getApplicationGroupId();
			logger.debug("applicationGroupId>>"+applicationGroupId);
			OrigFollowDocHistoryDAO dao=ORIGDAOFactory.getFollowDocHistoryDAO();
			int maxSeq = dao.selectMaxFollowupSeq(applicationGroupId);
			int currentSeq=maxSeq+1;
			logger.debug("maxSeq>>"+maxSeq);
			logger.debug("currentSeq>>"+currentSeq);
			ArrayList<DocumentCheckListDataM> documentCheckLists = applicationGroup.getDocumentCheckLists();
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
			if(!Util.empty(personalInfos)){
				for(PersonalInfoDataM personalInfo:personalInfos){
					if(!Util.empty(documentCheckLists)){
						for(DocumentCheckListDataM documentCheckList :documentCheckLists){
							if(!isDocComplete(personalInfo, documentCheckList) 
								|| (KPLUtil.isSavingPlusDoc(documentCheckList.getDocumentCode()) && KPLUtil.isSavingPlusNoDoc(applicationGroup))
							){
								String documentCode = documentCheckList.getDocumentCode();
								ArrayList<DocumentCheckListReasonDataM> docCheckListReasons = documentCheckList.getDocumentCheckListReasons();
								if(!Util.empty(docCheckListReasons)){
									for(DocumentCheckListReasonDataM docCheckListReason : docCheckListReasons){
										if(!isContainDocReasonAndDocCode(followDocHistoryList,docCheckListReason.getDocReason(),documentCode)){
											FollowDocHistoryDataM followDocHistory = new FollowDocHistoryDataM();
											followDocHistory.setApplicationGroupId(applicationGroup.getApplicationGroupId());
											followDocHistory.setCreateBy(userM.getUserNo());
											followDocHistory.setUpdateBy(userM.getUserNo());
											followDocHistory.setDocumentCode(documentCode);
											followDocHistory.setFollowupSeq(currentSeq);
											followDocHistory.setDocReason(docCheckListReason.getDocReason());
											followDocHistoryList.add(followDocHistory);
										}
									}							
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR");
		}
		return followDocHistoryList;
	}
		
	private static boolean isContainDocReasonAndDocCode(ArrayList<FollowDocHistoryDataM> followDocHistoryList,String docReason,String docCode){
		if(!Util.empty(followDocHistoryList)){
			for(FollowDocHistoryDataM followDocHis:followDocHistoryList){
				String docCodeFollow = followDocHis.getDocumentCode();
				String docReasonFollow = followDocHis.getDocReason();
				if(!Util.empty(docCodeFollow) && !Util.empty(docReasonFollow) && docCodeFollow.equals(docCode) && docReasonFollow.equals(docReason)){
					return true;
				}
			}
		}
		return false;
	}
//	private static boolean isCheckListDocumentReason(ApplicationGroupDataM applicationGroup,PersonalInfoDataM personalInfo,ArrayList<String> reasonCodes,String documentCode){
//		boolean isCheckListDocument = false;
//		try{
//			VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
//			if(!Util.empty(verificationResult) && !Util.empty(reasonCodes) &&  !Util.empty(documentCode)){
//				boolean isContainMandatoryFag  = verificationResult.isContainRequiredDocDetailMandatoryFlag(MConstant.FLAG.YES);
//				logger.debug("isContainMandatoryFag : "+isContainMandatoryFag);
//				PegaApplicationDAO pegaApplicationDAO = new PegaApplicationDAOImpl();
//				isCheckListDocument = pegaApplicationDAO.isCheckListReason(reasonCodes, documentCode, isContainMandatoryFag);
//			}
//		}catch(Exception e){
//			logger.fatal("ERROR",e);
//		}
//		logger.debug("isCheckListDocument : "+isCheckListDocument);
//		return isCheckListDocument;
//	}		
	
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
	
}
