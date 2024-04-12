package com.eaf.service.module.manual;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.CISRefDataObject;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.util.CisGeneralUtil;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.CIS1037A01RequestDataM;
import com.eaf.service.module.model.CIS1037A01ResponseDataM;
import com.eaf.service.module.model.RegulationDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.eai.cis1037a01.CIS1037A01Response;
import com.kasikornbank.eai.cis1037a01.CIS1037A01SoapProxy;
import com.kasikornbank.eai.cis1037a01.CIS1037A01_Type;
import com.kasikornbank.eai.cis1037a01.CISHeader;
import com.kasikornbank.eai.cis1037a01.CVRSInfoObj;
import com.kasikornbank.eai.cis1037a01.CVRSRegulation;
import com.kasikornbank.eai.cis1037a01.ObsContactAddrObj;
import com.kasikornbank.eai.cis1037a01.ObsOffclAddrObj;
import com.kasikornbank.eai.cis1037a01.RegulsObj;
import com.kasikornbank.eai.cis1037a01._____doServiceResponse_CIS1037A01Response_CISIndivCust_contactsObj_contactsVect_CISContact;
import com.kasikornbank.eai.cis1037a01._____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact;
import com.kasikornbank.eai.cis1037a01.___doServiceResponse_CIS1037A01Response_CISIndivCust_KYCObj;
import com.kasikornbank.eai.cis1037a01.___doServiceResponse_CIS1037A01Response_CISIndivCust_contactAddrObj;
import com.kasikornbank.eai.cis1037a01.___doServiceResponse_CIS1037A01Response_CISIndivCust_contactsObj;
import com.kasikornbank.eai.cis1037a01.___doService_CIS1037A01_CISIndivCust_KYCObj;
import com.kasikornbank.eai.cis1037a01.___doService_CIS1037A01_CISIndivCust_contactAddrObj;
import com.kasikornbank.eai.cis1037a01.___doService_CIS1037A01_CISIndivCust_contactsObj;
import com.kasikornbank.eai.cis1037a01.___doService_CIS1037A01_CISIndivCust_offclAddrObj;
import com.kasikornbank.eai.cis1037a01.__doServiceResponse_CIS1037A01Response_CISIndivCust;
import com.kasikornbank.eai.cis1037a01.__doServiceResponse_CIS1037A01Response_KBankHeader;
import com.kasikornbank.eai.cis1037a01.__doService_CIS1037A01_CISIndivCust;
import com.kasikornbank.eai.cis1037a01.__doService_CIS1037A01_KBankHeader;

public class CIS1037A01ServiceProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(CIS1037A01ServiceProxy.class);
	public static final String url = "urlWebService";
	public static final String serviceId = "CIS1037A01";
//	public static final String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public static final String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public static final String successCode = ServiceCache.getConstant("SUCCESS_CODE");			
	public static class requestConstants{
		public static final String brNo = "branchNo";
		public static final String confirmFlag = "confirmFlag";
		public static final String hubNo = "hubNo";
		public static final String userId = "userId";
		public static final String addrFlag = "addrFlag";
		public static final String asstAmt = "assetExcludeLand";
		public static final String birthDate = "birthDate";
		public static final String childAmt = "childrenAmount";
		public static final String consendFlag = "consendFlag";
		public static final String consentSrc = "consentSource";
		public static final String multiContactChannel = "multipleContactChannel";
		public static final String contactAddrAmp = "contactAddressAmphur";
		public static final String contactAddrBuild = "contactAddressBuilding";
		public static final String contactAddrCountry = "contactAddressCountry";
		public static final String contactAddrFloor = "contactAddressFloor";
		public static final String contactAddrNo = "contactAddressNumber";
		public static final String contactAddrMoo = "contactAddressMoo";
		public static final String contactAddrName = "contactAddressName";
		public static final String contactAddrBoxNo = "contactAddressBoxNo";
		public static final String contactAddrPostCode = "contactAddressPostCode";
		public static final String contactAddrPrefFlag = "contactAddressPreferedFlag";
		public static final String contactAddrProvince = "contactAddressProvince";
		public static final String contactAddrRoad = "contactAddressRoad";
		public static final String contactAddrRoom = "contactAddressRoom";
		public static final String contactAddrSoi = "contactAddressSoi";
		public static final String contactAddrTumbol = "contactAddressTumbol";
		public static final String contactAddrMooban = "contactAddressMooban";
		public static class CISContact{
			public static final String locationCode = "locationCode";
			public static final String avaTime = "availabilityTime";
			public static final String phExtNum = "phoneExtensionNumber";
			public static final String phNum = "phoneNumber";
			public static final String typeCode = "typeCode";
			public static final String name = "name";
		}
		public static final String cusSegment = "customerSegment";
		public static final String cusType = "customerType";
		public static final String depositCode = "depositCode";
		public static final String docId = "documentId";
		public static final String docType = "documentType";
		public static final String degree = "degree";
		public static final String empAmt = "employeeAmount";
		public static final String engName = "englishName";
		public static final String engLastName = "englishLastName";
		public static final String engMidName = "englishMiddleName";
		public static final String engTitleName = "englishTitleName";
		public static final String familyIncome = "familyIncome";
		public static final String reviewFlag = "reviewFlag";
		public static final String cardDesc = "cardDescription";
		public static final String cardExpDate = "cardExpiryDate";
		public static final String issueDate = "issueDate";
		public static final String issueBy = "issueBy";
		public static final String salary = "salary";
		public static final String comDocFlag = "completeDocumentFlag";
		public static final String kycComDocFlag = "kycCompleteDocumentFlag";
		public static final String kycBrCode = "kycBranchCode";
		public static final String maritalSt = "maritalStatus";
		public static final String menuAddFlag = "menuAddFlag";
		public static final String nationCode = "nationalityCode";
		public static final String chgBookNum = "changeBookNumber";
		public static final String chgDate = "changeDate";
		public static final String chgReasonCode = "changeReasonCode";
		public static final String chgSeqNum = "changeSequenceNumber";
		public static final String cusId = "customerId";
		public static final String conAddLine1 = "contactAddressLine1";
		public static final String conAddLine2 = "contactAddressLine2";
		public static final String offcAddrLine1 = "officialAddressLine1";
		public static final String offcAddrLine2 = "officialAddressLine2";
		public static final String occupation = "occupation";
		public static final String offcAddrAmp = "officialAddressAmphur";
		public static final String offcAddrBuild = "officialAddressBuilding";
		public static final String offcAddrCountry = "officialAddressCountry";
		public static final String offcAddrFloor = "officialAddressFloor";
		public static final String offcAddrNum = "officialAddressNumber";
		public static final String offcAddrMoo = "officialAddressMoo";
		public static final String offcAddrName = "officialAddressName";
		public static final String offcAddrBoxNo = "officialAddressBoxNo";
		public static final String offcAddrPostCode = "officialAddressPostCode";
		public static final String offcAddrPrefFlag = "officialAddressPreferedFlag";
		public static final String offcAddrProvince = "officialAddressProvince";
		public static final String offcAddrRoad = "officialAddressRoad";
		public static final String offcAddrRoom = "officialAddressRoom";
		public static final String offcAddrSoi = "officialAddressSoi";
		public static final String offcAddrTumbol = "officialAddressTumbol";
		public static final String offcAddrMooban = "officialAddressMooban";
		public static final String oldChildAge = "oldestChildAge";
		public static final String posCode = "positionCode";
		public static final String profCode = "professionCode";
		public static final String prospectFlag = "prospectFlag";
		public static final String raceCode = "raceCode";
		public static final String religionCode = "religionCode";
		public static final String reverseFlag = "reverseFlag";
		public static final String gender = "gender";
		public static final String ownBrNo = "ownerBrNo";
		public static final String taxId = "taxId";
		public static final String bsnType = "businessType";
		public static final String thName = "thaiName";
		public static final String thLastName = "thaiLastName";
		public static final String thMiddleName = "thaiMiddleName";
		public static final String thTitleName = "thaiTitleName";
		public static final String titleNameCode = "titleNameCode";
		public static final String typeCode = "customerTypeCode";
		public static final String validateFlag = "validateFlag";
		public static final String vipFlag = "vipFlag";
		public static final String workStartDate = "workStartDate";
		public static final String youngChildAge = "youngestChildAge";
	}
	
	public static class responseConstants{
		public static final String addrId = "addressId";
		public static class CISContact{
			public static final String phoneId = "phoneId";
			public static final String eAddressId = "eAddressId";
		}
		public static class customerRegulationInformation{
			public static final String regSubType = "regulationSubType";
			public static final String regType = "regulationType";
		}
		public static final String regulationAmt = "regulationAmout";
		public static final String riskCode = "riskCode";
		public static final String cusId = "customerId";
		public static final String offcAddrId = "officialAddressId";
	}
	
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("requestTransaction..");
		CIS1037A01_Type requestService = new CIS1037A01_Type();
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		__doService_CIS1037A01_KBankHeader KBankHeader = new __doService_CIS1037A01_KBankHeader(); 
			KBankHeader.setFuncNm(serviceId);
			KBankHeader.setUserId(serviceRequest.getUserId());
			String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			KBankHeader.setRqAppId(RqAppId);
			KBankHeader.setCorrID(serviceRequest.getServiceReqResId());
			KBankHeader.setRqUID(ServiceUtil.generateRqUID(RqAppId,serviceRequest.getUniqueId()));
			KBankHeader.setRqDt(ServiceApplicationDate.getCalendar());
		
		CIS1037A01RequestDataM CIS1037A01Request = (CIS1037A01RequestDataM)serviceRequest.getObjectData();
		if(null == CIS1037A01Request){
			CIS1037A01Request = new CIS1037A01RequestDataM();
		}
		CISHeader CISHeader = new CISHeader();
			CISHeader.setBrNo(CIS1037A01Request.getBranchNo());		
			CISHeader.setConfirmFlag(CIS1037A01Request.getConfirmFlag());
			CISHeader.setHubNo(CIS1037A01Request.getHubNo());
			CISHeader.setUserId(CisGeneralUtil.displayCISHeaderUserId(CIS1037A01Request.getUserId()));
		
		__doService_CIS1037A01_CISIndivCust CISIndivCust = new __doService_CIS1037A01_CISIndivCust();
			CISIndivCust.setAddrOffCntctSameCde(CIS1037A01Request.getAddrFlag());
			if(!ServiceUtil.empty(CIS1037A01Request.getAssetExcludeLand())){
				CISIndivCust.setAsstExclLndAmt(CIS1037A01Request.getAssetExcludeLand());
			}
		
		if(!ServiceUtil.empty(CIS1037A01Request.getBirthDate())){
			Calendar birthDate = new GregorianCalendar();
			birthDate.setTime(CIS1037A01Request.getBirthDate());
			CISIndivCust.setBrthDate(birthDate);
		}
		
		CISIndivCust.setChildCnt(ServiceUtil.empty(CIS1037A01Request.getChildrenAmount()) 
				? 0	: CIS1037A01Request.getChildrenAmount());
		CISIndivCust.setCnstFlg(CIS1037A01Request.getConsendFlag());
		CISIndivCust.setCnstSrcCode(CIS1037A01Request.getConsentSource());
		CISIndivCust.setCntctChnlMltCde(CIS1037A01Request.getMultipleContactChannel());
			___doService_CIS1037A01_CISIndivCust_contactAddrObj contactAddrObj = new ___doService_CIS1037A01_CISIndivCust_contactAddrObj();
			contactAddrObj.setAmphur(CIS1037A01Request.getContactAddressAmphur());
			contactAddrObj.setBuilding(CIS1037A01Request.getContactAddressBuilding());
			contactAddrObj.setCntryCode(CIS1037A01Request.getContactAddressCountry());
			contactAddrObj.setFloor(CIS1037A01Request.getContactAddressFloor());
			contactAddrObj.setMailNum(CIS1037A01Request.getContactAddressNumber());
			contactAddrObj.setMoo(CIS1037A01Request.getContactAddressMoo());
			contactAddrObj.setName(CIS1037A01Request.getContactAddressName());
			contactAddrObj.setPoBox(CIS1037A01Request.getContactAddressBoxNo());
			contactAddrObj.setPostCode(CIS1037A01Request.getContactAddressPostCode());
			contactAddrObj.setPreferedCode(CIS1037A01Request.getContactAddressPreferedFlag());
			contactAddrObj.setProvince(CIS1037A01Request.getContactAddressProvince());
			contactAddrObj.setRoad(CIS1037A01Request.getContactAddressRoad());
			contactAddrObj.setRoom(CIS1037A01Request.getContactAddressRoom());
			contactAddrObj.setSoi(CIS1037A01Request.getContactAddressSoi());
			contactAddrObj.setTumbol(CIS1037A01Request.getContactAddressTumbol());
			contactAddrObj.setVillage(CIS1037A01Request.getContactAddressMooban());
		CISIndivCust.setContactAddrObj(contactAddrObj);
			___doService_CIS1037A01_CISIndivCust_contactsObj contactsObj = new ___doService_CIS1037A01_CISIndivCust_contactsObj();
				_____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact[] contactsVect = new _____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact[2];
				for(int i=0;i<2;i++){
					contactsVect[i] = new	_____doService_CIS1037A01_CISIndivCust_contactsObj_contactsVect_CISContact();
					if(i==0){
						contactsVect[i].setLocationCode(CIS1037A01Request.getTelephoneLocation());
						contactsVect[i].setPhAvailTimeCde(CIS1037A01Request.getTelephoneAvailability());
						contactsVect[i].setPhExtNum(CIS1037A01Request.getTelephoneExtNumber());
						contactsVect[i].setPhNum(CIS1037A01Request.getTelephoneNumber());
						contactsVect[i].setTypeCode(CIS1037A01Request.getTelephoneType());
					}else if(i==1){
						if(!ServiceUtil.empty(CIS1037A01Request.getMailName())){
							contactsVect[i].setLocationCode(CIS1037A01Request.getMailLocation());
							contactsVect[i].setName(CIS1037A01Request.getMailName());
							contactsVect[i].setTypeCode(CIS1037A01Request.getMailType());
						}
					}
				}
			contactsObj.setContactsVect(contactsVect);
		CISIndivCust.setContactsObj(contactsObj);
		CISIndivCust.setCustSgmtCode(CIS1037A01Request.getCustomerSegment());
		CISIndivCust.setCustTypeCode(CIS1037A01Request.getCustomerTypeCode());
		CISIndivCust.setDepositCode(ServiceUtil.empty(CIS1037A01Request.getDepositCode()) ? "" : CIS1037A01Request.getDepositCode());
		CISIndivCust.setDocNum(CIS1037A01Request.getDocumentId());
		CISIndivCust.setDocTypeCode(CIS1037A01Request.getDocumentType());
		CISIndivCust.setEduLevelCode(CIS1037A01Request.getDegree());
		CISIndivCust.setEmpCnt(CIS1037A01Request.getEmployeeAmount());
		CISIndivCust.setEngFstName(CIS1037A01Request.getEnglishName());
		CISIndivCust.setEngLstName(CIS1037A01Request.getEnglishLastName());
		CISIndivCust.setEngMidName(CIS1037A01Request.getEnglishMiddleName());
		CISIndivCust.setEngTitle(CIS1037A01Request.getEnglishTitleName());
		CISIndivCust.setFamilyIncCode(CIS1037A01Request.getFamilyIncome());
		CISIndivCust.setFrontReviewFlag(CIS1037A01Request.getReviewFlag());
		CISIndivCust.setIdCrdDesc(CIS1037A01Request.getCardDescription());
		if(!ServiceUtil.empty(CIS1037A01Request.getCardExpiryDate())){
			Calendar cardExpDate = new GregorianCalendar(); 
			cardExpDate.setTime(CIS1037A01Request.getCardExpiryDate());
			CISIndivCust.setIdCrdExpDate(cardExpDate);
		}
		if(!ServiceUtil.empty(CIS1037A01Request.getIssueDate())){
			Calendar cardIssueDate = new GregorianCalendar();
			cardIssueDate.setTime(CIS1037A01Request.getIssueDate());
			CISIndivCust.setIdCrdIssuDate(cardIssueDate);
		}
		CISIndivCust.setIdCrdIssuPlaceDesc(CIS1037A01Request.getIssueBy());
		CISIndivCust.setIncomRang(CIS1037A01Request.getSalary());
			___doService_CIS1037A01_CISIndivCust_KYCObj KYCObj = new ___doService_CIS1037A01_CISIndivCust_KYCObj();
			KYCObj.setCompltDocFlg(CIS1037A01Request.getCompleteDocumentFlag());
			KYCObj.setCompltDocKYCFlg(CIS1037A01Request.getKycCompleteDocumentFlag());
			KYCObj.setKYCBrchCode(CIS1037A01Request.getKycBranchCode());
		CISIndivCust.setKYCObj(KYCObj);
		CISIndivCust.setMarrStatCode(CIS1037A01Request.getMaritalStatus());
		CISIndivCust.setMenuAddCode(CIS1037A01Request.getMenuAddFlag());
		CISIndivCust.setNationCode(CIS1037A01Request.getNationalityCode());
		CISIndivCust.setNmChgBookNum(CIS1037A01Request.getChangeBookNumber());
		if(!ServiceUtil.empty(CIS1037A01Request.getChangeDate())){
			Calendar changeDate = new GregorianCalendar();
			changeDate.setTime(CIS1037A01Request.getChangeDate());
			CISIndivCust.setNmChgDate(changeDate);
		}
		CISIndivCust.setNmChgResnCode(CIS1037A01Request.getChangeReasonCode());
		CISIndivCust.setNmChgSeqNum(CIS1037A01Request.getChangeSequenceNumber());
		CISIndivCust.setNum(CIS1037A01Request.getCustomerId());
			ObsContactAddrObj obsContactAddrObj = new ObsContactAddrObj();
			obsContactAddrObj.setObsAddr1Txt(CIS1037A01Request.getContactAddressLine1());
			obsContactAddrObj.setObsAddr2Txt(CIS1037A01Request.getContactAddressLine2());
		CISIndivCust.setObsContactAddrObj(obsContactAddrObj);
			ObsOffclAddrObj obsOffclAddrObj = new ObsOffclAddrObj();
			obsOffclAddrObj.setObsAddr1Txt(CIS1037A01Request.getOfficialAddressLine1());
			obsOffclAddrObj.setObsAddr2Txt(CIS1037A01Request.getOfficialAddressLine2());
		CISIndivCust.setObsOffclAddrObj(obsOffclAddrObj);
		CISIndivCust.setOccCode(CIS1037A01Request.getOccupation());
			___doService_CIS1037A01_CISIndivCust_offclAddrObj offclAddrObj = new ___doService_CIS1037A01_CISIndivCust_offclAddrObj();
			offclAddrObj.setAmphur(CIS1037A01Request.getOfficialAddressAmphur());
			offclAddrObj.setBuilding(CIS1037A01Request.getOfficialAddressBuilding());
			offclAddrObj.setCntryCode(CIS1037A01Request.getOfficialAddressCountry());
			offclAddrObj.setFloor(CIS1037A01Request.getOfficialAddressFloor());
			offclAddrObj.setMailNum(CIS1037A01Request.getOfficialAddressNumber());
			offclAddrObj.setMoo(CIS1037A01Request.getOfficialAddressMoo());
			offclAddrObj.setName(CIS1037A01Request.getOfficialAddressName());
			offclAddrObj.setPoBox(CIS1037A01Request.getOfficialAddressBoxNo());
			offclAddrObj.setPostCode(CIS1037A01Request.getOfficialAddressPostCode());
			offclAddrObj.setPreferedCode(CIS1037A01Request.getOfficialAddressPreferedFlag());
			offclAddrObj.setProvince(CIS1037A01Request.getOfficialAddressProvince());
			offclAddrObj.setRoad(CIS1037A01Request.getOfficialAddressRoad());
			offclAddrObj.setRoom(CIS1037A01Request.getOfficialAddressRoom());
			offclAddrObj.setSoi(CIS1037A01Request.getOfficialAddressSoi());
			offclAddrObj.setTumbol(CIS1037A01Request.getOfficialAddressTumbol());
			offclAddrObj.setVillage(CIS1037A01Request.getOfficialAddressMooban());
		CISIndivCust.setOffclAddrObj(offclAddrObj);
		CISIndivCust.setOldChildAgeCnt(ServiceUtil.empty(CIS1037A01Request.getOldestChildAge()) 
				|| CIS1037A01Request.getOldestChildAge() == 0 ? null : CIS1037A01Request.getOldestChildAge());
		CISIndivCust.setPositionCode(CIS1037A01Request.getPositionCode());
		CISIndivCust.setProfessCode(CIS1037A01Request.getProfessionCode());
		CISIndivCust.setOthProfessDesc(ServiceUtil.removeAllSpecialCharactors(CIS1037A01Request.getOthProfessDesc()));
		CISIndivCust.setProspectFlag(CIS1037A01Request.getProspectFlag());
		CISIndivCust.setRaceCode(CIS1037A01Request.getRaceCode());
		CISIndivCust.setReligionCode(CIS1037A01Request.getReligionCode());
		CISIndivCust.setReverseFlag(CIS1037A01Request.getReverseFlag());
		CISIndivCust.setSexCode(CIS1037A01Request.getGender());
		CISIndivCust.setSvcBrchCode(CIS1037A01Request.getOwnerBrNo());
		CISIndivCust.setTaxNum(CIS1037A01Request.getTaxId());
		CISIndivCust.setTFBBusTypeCode(CIS1037A01Request.getBusinessType());
		CISIndivCust.setThFstName(CIS1037A01Request.getThaiName());
		CISIndivCust.setThLstName(CIS1037A01Request.getThaiLastName());
		CISIndivCust.setThMidName(CIS1037A01Request.getThaiMiddleName());
		CISIndivCust.setThTitle(CIS1037A01Request.getThaiTitleName());
		CISIndivCust.setTitleNameCode(CIS1037A01Request.getTitleNameCode());
		CISIndivCust.setTypeCode(CIS1037A01Request.getCustomerType());
		CISIndivCust.setValidationFlag(CIS1037A01Request.getValidateFlag());
		CISIndivCust.setVIPCode(CIS1037A01Request.getVipFlag());
		if(!ServiceUtil.empty(CIS1037A01Request.getWorkStartDate())){
			Calendar workStartDate = new GregorianCalendar();
			workStartDate.setTime(CIS1037A01Request.getWorkStartDate());
			CISIndivCust.setWorkStartDate(workStartDate);
		}
		CISIndivCust.setYoungChildAgeCnt(ServiceUtil.empty(CIS1037A01Request.getYoungestChildAge())
				|| CIS1037A01Request.getYoungestChildAge() == 0 ? null : CIS1037A01Request.getYoungestChildAge());
		
		requestService.setKBankHeader(KBankHeader);
		requestService.setCISHeader(CISHeader);
		requestService.setCISIndivCust(CISIndivCust);
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestService, serviceRequest);
		return requestTransaction;
	}

	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			CIS1037A01_Type requestObject = (CIS1037A01_Type)requestServiceObject;
			CIS1037A01SoapProxy proxy = new CIS1037A01SoapProxy();
			String endPointUrl = serviceRequest.getEndpointUrl();
	        logger.info("endPointUrl >> "+endPointUrl);
			proxy.setEndpoint(endPointUrl);
			CIS1037A01Response responseObject = proxy.doService(requestObject);
			serviceTransaction.setServiceTransactionObject(responseObject);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}

	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		logger.debug("responseTransaction..");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		CIS1037A01Response responseObject = (CIS1037A01Response)serviceTransaction.getServiceTransactionObject();
		CIS1037A01ResponseDataM CIS1037A01Response = new CIS1037A01ResponseDataM();
		CISRefDataObject personalRefData = (CISRefDataObject)serviceRequest.getServiceData();
		if(null==personalRefData){
			personalRefData = new CISRefDataObject();
		}
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && null != responseObject){
			try{
				__doServiceResponse_CIS1037A01Response_KBankHeader KBankHeader = responseObject.getKBankHeader();
				if(ServiceResponse.Status.SUCCESS.equals(KBankHeader.getStatusCode())){
					CIS1037A01Response = getCIS1037A01Response(responseObject);
					personalRefData.setCisNo(CIS1037A01Response.getCustomerId());
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
					serviceResponse.setObjectData(CIS1037A01Response);
				}else{
					serviceResponse.setStatusCode(KBankHeader.getStatusCode());
					com.kasikornbank.eai.cis1037a01.Error[] Errors = KBankHeader.getErrorVect();
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
						errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.CIS);
						errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
						errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					if(null != Errors && Errors.length > 0){
						com.kasikornbank.eai.cis1037a01.Error Error = Errors[0];
						errorInfo.setErrorCode(Error.getErrorCode());
						errorInfo.setErrorDesc(Error.getErrorDesc());
						errorInfo.setErrorInformation(new Gson().toJson(Errors));
					}
					serviceResponse.setErrorInfo(errorInfo);
				}
			}catch(Exception e){					
				logger.fatal("ERROR",e);					
				serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
				serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		serviceResponse.setServiceData(personalRefData);
		responseTransaction.serviceInfo(ServiceConstant.IN, responseObject, serviceResponse);
		return responseTransaction;
	}
	
	private CIS1037A01ResponseDataM getCIS1037A01Response(CIS1037A01Response responseObject){
		__doServiceResponse_CIS1037A01Response_CISIndivCust CISIndivCust = responseObject.getCISIndivCust();
		if(null == CISIndivCust){
			CISIndivCust = new __doServiceResponse_CIS1037A01Response_CISIndivCust();
		}
		___doServiceResponse_CIS1037A01Response_CISIndivCust_contactAddrObj contactAddrObj = CISIndivCust.getContactAddrObj();
		if(null == contactAddrObj){
			contactAddrObj = new ___doServiceResponse_CIS1037A01Response_CISIndivCust_contactAddrObj();
		}
		___doServiceResponse_CIS1037A01Response_CISIndivCust_contactsObj contactsObj = CISIndivCust.getContactsObj();
		if(null == contactsObj){
			contactsObj = new ___doServiceResponse_CIS1037A01Response_CISIndivCust_contactsObj();
		}	
		CVRSInfoObj CVRSInfoObj = CISIndivCust.getCVRSInfoObj();
		if(null == CVRSInfoObj){
			CVRSInfoObj = new CVRSInfoObj();
		}
		RegulsObj regulsObj = CVRSInfoObj.getRegulsObj();	
		if(null == regulsObj){
			regulsObj = new RegulsObj();
		}
		___doServiceResponse_CIS1037A01Response_CISIndivCust_KYCObj KYCObj = CISIndivCust.getKYCObj(); 
		if(null == KYCObj){
			KYCObj = new ___doServiceResponse_CIS1037A01Response_CISIndivCust_KYCObj();
		}
		
		CIS1037A01ResponseDataM CIS1037A01Response = new CIS1037A01ResponseDataM();
		CIS1037A01Response.setAddressId(contactAddrObj.getNum());	

		_____doServiceResponse_CIS1037A01Response_CISIndivCust_contactsObj_contactsVect_CISContact[] contactsVects = contactsObj.getContactsVect();	
		if(null != contactsVects){
			for(int i=0;i<contactsVects.length;i++){
				if(i==0) CIS1037A01Response.setPhoneId(contactsVects[i].getNum());
				else if(i==1) CIS1037A01Response.setMailId(contactsVects[i].getNum());
			}		
		}
		ArrayList<RegulationDataM> regulations = new ArrayList<>();
		CVRSRegulation[] regulsVects = regulsObj.getRegulsVect();
		if(null != regulsVects){
			for(int i=0;i<regulsVects.length;i++){
				RegulationDataM regulation = new RegulationDataM();
				regulation.setRegulationSubType(regulsVects[i].getSubTypCode());
				regulation.setRegulationType(regulsVects[i].getTypCode());
				regulations.add(regulation);
			}
		}
		
		CIS1037A01Response.setRegulations(regulations);
		CIS1037A01Response.setTotalRegulation(regulsObj.getTotRegulCnt());
		CIS1037A01Response.setRiskCode(KYCObj.getRiskLevCode());
		CIS1037A01Response.setCustomerId(CISIndivCust.getNum());
		CIS1037A01Response.setOfficialAddressId(contactAddrObj.getNum());
		return CIS1037A01Response;
	}
}
