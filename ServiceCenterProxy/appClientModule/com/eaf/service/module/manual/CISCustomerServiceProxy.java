package com.eaf.service.module.manual;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CVRSValidationResultDataM;
import com.eaf.orig.ulo.model.app.KYCDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.model.dih.CISAddressDataM;
import com.eaf.orig.ulo.model.dih.CISCustomerDataM;
import com.eaf.orig.ulo.model.dih.CISelcAddressDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.dao.ServiceDAO;
import com.eaf.service.common.dao.ServiceFactory;
import com.eaf.service.common.model.CISRefDataObject;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.util.CisAddressServiceUtil;
import com.eaf.service.common.util.CisApplicantUtil;
import com.eaf.service.common.util.ServiceErrorResponseUtil;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.CIS0314I01RequestDataM;
import com.eaf.service.module.model.CIS0314I01ResponseDataM;
import com.eaf.service.module.model.CIS0315U01RequestDataM;
import com.eaf.service.module.model.CIS1034A01RequestDataM;
import com.eaf.service.module.model.CIS1035A01RequestDataM;
import com.eaf.service.module.model.CIS1035A01ResponseDataM;
import com.eaf.service.module.model.CIS1036A01RequestDataM;
import com.eaf.service.module.model.CIS1036A01ResponseDataM;
import com.eaf.service.module.model.CIS1037A01RequestDataM;
import com.eaf.service.module.model.CIS1037A01ResponseDataM;
import com.eaf.service.module.model.CIS1044U01RequestDataM;
import com.eaf.service.module.model.CIS1046A01RequestDataM;
import com.eaf.service.module.model.CIS1047O01RequestDataM;
import com.eaf.service.module.model.CIS1048O01AddressDataM;
import com.eaf.service.module.model.CIS1048O01RequestDataM;
import com.eaf.service.module.model.CISAddrRelationDataM;
import com.eaf.service.module.model.CISContactDataM;
import com.eaf.service.module.model.CISDocInfoDataM;
import com.eaf.service.module.model.CISTelephoneDataM;
import com.eaf.service.module.model.CVRSDQ0001AddressDataM;
import com.eaf.service.module.model.CVRSDQ0001ContactDataM;
import com.eaf.service.module.model.CVRSDQ0001RequestDataM;
import com.eaf.service.module.model.CVRSDQ0001ResponseDataM;
import com.eaf.service.module.model.ResponseValidateResultDataM;

public class CISCustomerServiceProxy {
	private static transient Logger logger = Logger.getLogger(CISCustomerServiceProxy.class);
	ServiceCenterProxy proxy = new ServiceCenterProxy();	
	String FIELD_ID_CID_TYPE = ServiceCache.getConstant("FIELD_ID_CID_TYPE");
	String FIELD_ID_BUSINESS_TYPE = ServiceCache.getConstant("FIELD_ID_BUSINESS_TYPE");
	String FIELD_ID_DEGREE  = ServiceCache.getConstant("FIELD_ID_DEGREE");
	String FIELD_ID_OCCUPATION = ServiceCache.getConstant("FIELD_ID_OCCUPATION");
	String FIELD_ID_EN_TITLE_CODE = ServiceCache.getConstant("FIELD_ID_EN_TITLE_CODE");
	String FIELD_ID_COMPANY_TITLE = ServiceCache.getConstant("FIELD_ID_COMPANY_TITLE");
	String FIELD_ID_MARRIED = ServiceCache.getConstant("FIELD_ID_MARRIED");
	String FIELD_ID_NATIONALITY = ServiceCache.getConstant("FIELD_ID_NATIONALITY");
	String FIELD_ID_PROFESSION = ServiceCache.getConstant("FIELD_ID_PROFESSION");
	String FIELD_ID_GENDER = ServiceCache.getConstant("FIELD_ID_GENDER");
	String FIELD_ID_TH_TITLE_CODE = ServiceCache.getConstant("FIELD_ID_TH_TITLE_CODE");
	String FINAL_APP_DECISION_APPROVE = ServiceCache.getConstant("FINAL_APP_DECISION_APPROVE");
	String FIELD_ID_ADDRESS_TYPE = ServiceCache.getConstant("FIELD_ID_ADDRESS_TYPE");
	String ADDRESS_TYPE_CURRENT = ServiceCache.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = ServiceCache.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = ServiceCache.getConstant("ADDRESS_TYPE_DOCUMENT");
	String CIDTYPE_IDCARD = ServiceCache.getConstant("CIDTYPE_IDCARD");
	String CIDTYPE_PASSPORT = ServiceCache.getConstant("CIDTYPE_PASSPORT");
	String CIDTYPE_MIGRANT = ServiceCache.getConstant("CIDTYPE_MIGRANT");
	String CIS_LAND_LINE_PHONE_CODE = ServiceCache.getConstant("CIS_LAND_LINE_PHONE_CODE");
	String CIS_MOBILE_PHONE_CODE = ServiceCache.getConstant("CIS_MOBILE_PHONE_CODE");
	String CIS_FAX_CODE = ServiceCache.getConstant("CIS_FAX_CODE");
	String CIS_MAIL_TYPE_DEFAULT = ServiceCache.getConstant("CIS_MAIL_TYPE_DEFAULT");
	String CIS_PROSPECT_FLAG = ServiceCache.getConstant("CIS_PROSPECT_FLAG");
	String CIS_1037_PROSPECT_FLAG = ServiceCache.getConstant("CIS_1037_PROSPECT_FLAG");
	String CIS_CUSTOMER_TYPE_INDIVIDUAL = ServiceCache.getConstant("CIS_CUSTOMER_TYPE_INDIVIDUAL");
	String CIS_CUSTOMER_TYPE_CODE = ServiceCache.getConstant("CIS_CUSTOMER_TYPE_CODE");
	String CIS_CUSTOMER_TYPE_KGROUP = ServiceCache.getConstant("CIS_CUSTOMER_TYPE_KGROUP");
	String CIS_CUSTOMER_TYPE_FOREIGNER = ServiceCache.getConstant("CIS_CUSTOMER_TYPE_FOREIGNER");
	String CIS_CUSTOMER_TYPE_OCCUPATION = ServiceCache.getConstant("CIS_CUSTOMER_TYPE_OCCUPATION");
	String CIS_ACCOUNT_LEVEL_DEFAULT = ServiceCache.getConstant("CIS_ACCOUNT_LEVEL_DEFAULT");
	String CIS_CURRENT_LOCATION_CODE = ServiceCache.getConstant("CIS_CURRENT_LOCATION_CODE");
	String CIS_OWNER_BRANCH_NO_DEFAULT = ServiceCache.getConstant("CIS_OWNER_BRANCH_NO_DEFAULT");
	String CIS_SALARY_CODE = ServiceCache.getConstant("CIS_SALARY_CODE");
	String CIS_CIRCULATION_INCOME_CODE = ServiceCache.getConstant("CIS_CIRCULATION_INCOME_CODE");
	String CIS_FREELANCE_INCOME_CODE = ServiceCache.getConstant("CIS_FREELANCE_INCOME_CODE");
	String CIS_MONTHLY_INCOME_CODE = ServiceCache.getConstant("CIS_MONTHLY_INCOME_CODE");
	String FLAG_YES = ServiceCache.getConstant("FLAG_YES");
	String FLAG_NO = ServiceCache.getConstant("FLAG_NO");
	String BONUS_TEXT = ServiceCache.getConstant("BONUS_TEXT");
	String COMMISSION_TEXT = ServiceCache.getConstant("COMMISSION_TEXT");
	String OTHER_TEXT = ServiceCache.getConstant("OTHER_TEXT");
	String CIS_COMPLETE_DOCUMENT_DEFAULT_FLAG = ServiceCache.getConstant("CIS_COMPLETE_DOCUMENT_DEFAULT_FLAG");
	String CIS_COMPLETE_KYC_DOCUMENT_DEFAULT_FLAG = ServiceCache.getConstant("CIS_COMPLETE_KYC_DOCUMENT_DEFAULT_FLAG");
	String CIS_TITLE_FORCED_SAVE_DEFAULT_FLAG = ServiceCache.getConstant("CIS_TITLE_FORCED_SAVE_DEFAULT_FLAG");
	String SALE_TYPE_NORMAL_SALES = ServiceCache.getConstant("SALE_TYPE_NORMAL_SALES");
	String CIS_FUNC_TYPE = ServiceCache.getConstant("CIS_FUNC_TYPE");
	String CIS_DATA_TYPE_PHONE = ServiceCache.getConstant("CIS_DATA_TYPE_PHONE");
	String CIS_DATA_TYPE_EMAIL = ServiceCache.getConstant("CIS_DATA_TYPE_EMAIL");
	String CIS_DATA_TYPE_FAX = ServiceCache.getConstant("CIS_DATA_TYPE_FAX");
	String CIS_ADDRESS_STATUS_CODE = ServiceCache.getConstant("CIS_ADDRESS_STATUS_CODE");
	String CIS_DEFAULT_SERVICE_HUB_NUMBER = ServiceCache.getConstant("CIS_DEFAULT_SERVICE_HUB_NUMBER");
	String CIS_LOCATION_HOME = ServiceCache.getConstant("CIS_LOCATION_HOME");
	String CIS_LOCATION_OFFICE = ServiceCache.getConstant("CIS_LOCATION_OFFICE");
	String CIS_CONTACT_EMAIL_TYPE_CODE=ServiceCache.getConstant("CIS_CONTACT_EMAIL_TYPE_CODE");
	String CIS_CONTACT_MOBILE_PHONE_TYPE_CODE=ServiceCache.getConstant("CIS_CONTACT_MOBILE_PHONE_TYPE_CODE");
	String CIS_CONTACT_FAX_TYPE_CODE=ServiceCache.getConstant("CIS_CONTACT_FAX_TYPE_CODE");
	String CIS_REQUEST_FLP_ADDRESS_TYPES=ServiceCache.getConstant("CIS_REQUEST_FLP_ADDRESS_TYPES");
	String TITLE_OTHER=ServiceCache.getConstant("TITLE_OTHER");
	String COUNTRY_TH = ServiceCache.getConstant("COUNTRY_TH");
	String OCCUPATION_REGISTRATION = ServiceCache.getConstant("OCCUPATION_REGISTRATION");
	String OCCUPATION_OWN_BUSINESS = ServiceCache.getConstant("OCCUPATION_OWN_BUSINESS");
	String CIS_CONTACT_EMAIL_LOCATION = ServiceCache.getConstant("CIS_CONTACT_EMAIL_LOCATION");
	String CIS_WORK_LOCATION_CODE = ServiceCache.getConstant("CIS_WORK_LOCATION_CODE");
	String PROFESSION_OTHER = ServiceCache.getConstant("PROFESSION_OTHER_CIS");
	String APPLICATION_TYPE_INCREASE = ServiceCache.getConstant("APPLICATION_TYPE_INCREASE");
	public static final String NEW_DATA = "NEW";
	public static final String EXISTING_NOT_SAME_DATA = "NOT_SAME";
	public static final String EXISTING_SAME_DATA = "SAME";
	String requestTransactionId = "";
	boolean isAddHomePhone = false;
	boolean isAddWorkPhone = false;
	boolean isAddMobile = false;
	public ProcessResponse createCISCustomer(ApplicationGroupDataM applicationGroup,PersonalInfoDataM personalInfo,CISCustomerDataM cisCustomerData, String userId, String department){
		
		boolean isNewCustomer = true; // for new customer
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		ProcessResponse processResponse = new ProcessResponse();
		requestTransactionId = applicationGroup.getTransactionId();
		logger.debug("applicationGroupId : "+applicationGroupId);
		logger.debug("requestTransactionId : "+requestTransactionId);
			processResponse.setStatusCode(ServiceResponse.Status.SUCCESS); 
		try{
			ServiceDAO serviceDAO = ServiceFactory.getServiceDAO();
			CISRefDataObject personalRefData = new CISRefDataObject();
				personalRefData.setPersonalId(personalInfo.getPersonalId());
				personalRefData.setRefType(CISRefDataObject.RefType.PERSONAL);
			ArrayList<CISTelephoneDataM> telephonesForCISMapping = getTelephoneForCIS(personalInfo);
			if(null==cisCustomerData){
				cisCustomerData = new CISCustomerDataM();
			}
			
			//Call CVRSDQ0001
		
			String CALL_CVRSDQ0001_FLAG = ServiceCache.getGeneralParam("CALL_CVRSDQ0001_FLAG");
			logger.debug("CALL_CVRSDQ0001_FLAG>>"+CALL_CVRSDQ0001_FLAG);
			if(!FLAG_NO.equals(CALL_CVRSDQ0001_FLAG)){
				CISRefDataObject CVRSDQ0001DataObject = serviceDAO.getServiceData(CVRSDQ0001ServiceProxy.serviceId,applicationGroupId,personalRefData);			
				if(!CVRSDQ0001DataObject.isSuccess()){
					ServiceResponseDataM  CVRSDQ0001Response= callCVRSDQ0001(personalInfo,applicationGroup,cisCustomerData,telephonesForCISMapping,userId,personalRefData,isNewCustomer);
					if(ServiceResponse.Status.SUCCESS.equals(CVRSDQ0001Response.getStatusCode())){
						CVRSDQ0001ResponseDataM cvrsdq0001response = (CVRSDQ0001ResponseDataM)CVRSDQ0001Response.getObjectData();
						ArrayList<ResponseValidateResultDataM>  validateFieldResults = cvrsdq0001response.getResponseValidateFields();
						if(null!=validateFieldResults && validateFieldResults.size()>0){
							String isDeleteComplete = serviceDAO.deleteCVRSValidationResultTable(personalInfo.getPersonalId());
							logger.debug("isDeleteComplete>>"+isDeleteComplete);
							for(ResponseValidateResultDataM fieldResultValidate : validateFieldResults){
								CVRSValidationResultDataM cvrsValidationResultDataM = new CVRSValidationResultDataM();
								cvrsValidationResultDataM.setFieldId(fieldResultValidate.getFieldId());
								cvrsValidationResultDataM.setFieldName(fieldResultValidate.getFieldName());
								cvrsValidationResultDataM.setPersonalId(fieldResultValidate.getPersonalId());
								cvrsValidationResultDataM.setErrorCode(fieldResultValidate.getErrorCode());
								cvrsValidationResultDataM.setErrorDesc(fieldResultValidate.getErrorDesc());
								cvrsValidationResultDataM.setFieldGroup(fieldResultValidate.getFildGroup()); 
								cvrsValidationResultDataM.setCreateDate(ServiceUtil.getCurrentDate());
								cvrsValidationResultDataM.setCreateBy(userId);
								try {
									serviceDAO.createCVRSValidationResultTable(cvrsValidationResultDataM);
														
								} catch (Exception e) {
									logger.fatal("ERROR",e);
//									throw new Exception(e);
									return ServiceErrorResponseUtil.fail(e,CVRSDQ0001ServiceProxy.serviceId);
								}							
							}
						}
					}else{
						logger.debug("error call CVRSDQ0001 service ");
						return ServiceErrorResponseUtil.fail(CVRSDQ0001Response);
					}
				}
			}
			
			CISRefDataObject CIS1037A01DataObject = serviceDAO.getServiceData(CIS1037A01ServiceProxy.serviceId,applicationGroupId,personalRefData);			
			logger.debug(CIS1037A01DataObject);
			if(CIS1037A01DataObject.isSuccess() && !ServiceUtil.empty(CIS1037A01DataObject.getCisNo())){
				String cisNo = CIS1037A01DataObject.getCisNo();
				if(!ServiceUtil.empty(cisNo) && ServiceUtil.empty(personalInfo.getCisNo())){
					personalInfo.setCisNo(String.valueOf(Integer.parseInt(cisNo)));
				}				
			}else{
				ServiceResponseDataM serviceResponse = callCIS1037A01(applicationGroup, personalInfo, userId, personalRefData, telephonesForCISMapping, department,cisCustomerData);
				String statusCode = serviceResponse.getStatusCode();
				logger.debug("CIS1037A01.statusCode : "+statusCode);
				if(ServiceResponse.Status.SUCCESS.equals(statusCode)){	
					CIS1037A01ResponseDataM CIS1037A01Response = (CIS1037A01ResponseDataM)serviceResponse.getObjectData();
					String cisNo = CIS1037A01Response.getCustomerId();
					personalInfo.setCisNo(String.valueOf(Integer.parseInt(cisNo)));
				}else{
					return ServiceErrorResponseUtil.fail(serviceResponse);
				}
			}			
			logger.debug("cisNo : "+personalInfo.getCisNo());
			personalRefData.setCisNo(getCustomerId(personalInfo.getCisNo()));
			
//			#rawi comment not call on add cis
//			CISRefDataObject CIS1034A01DataObject = serviceDAO.getServiceData(CIS1034A01ServiceProxy.serviceId,applicationGroupId, ServiceConstant.IN, personalRefData);
//			logger.debug(CIS1034A01DataObject);
//			if(!CIS1034A01DataObject.isSuccess()){
//				ServiceResponseDataM cis1034A01Response = callCIS1034A01(applicationGroup, personalInfo, userId, personalRefData);
//				String resultCode = cis1034A01Response.getResultCode();
//				logger.debug("CIS1034A01.resultCode : "+resultCode);
//				if(!ServiceResponse.Status.SUCCESS.equals(resultCode)){
//					processResponse.setStatusCode(resultCode);
//					processResponse.setErrorMsg(cis1034A01Response.getErrorMsg());
//					processResponse.setErrorDesc(cis1034A01Response.getResultDesc());
//					return processResponse;
//				}
//			}
			
			CISRefDataObject addressRefData = new CISRefDataObject();
				addressRefData.setPersonalId(personalInfo.getPersonalId());
				addressRefData.setRefType(CISRefDataObject.RefType.ADDRESS);
				addressRefData.setCisNo(getCustomerId(personalInfo.getCisNo()));
			CISRefDataObject CIS1036A01DataObject = serviceDAO.getServiceData(CIS1036A01ServiceProxy.serviceId,applicationGroupId,addressRefData);
			logger.debug(CIS1036A01DataObject);			
			if(CIS1036A01DataObject.isSuccess()&&!ServiceUtil.empty(CIS1036A01DataObject.getCisAddressId())){
				addressRefData.setCisAddressId(CIS1036A01DataObject.getCisAddressId());
			}else{
				ServiceResponseDataM serviceResponse = callCIS1036A01(applicationGroup,personalInfo,userId,addressRefData,ADDRESS_TYPE_WORK, department,cisCustomerData);
				String statusCode = serviceResponse.getStatusCode();
				logger.debug("CIS1036A01.statusCode : "+statusCode);			
				if(ServiceResponse.Status.SUCCESS.equals(statusCode)){
					CIS1036A01ResponseDataM CIS1036A01ResponseObject = (CIS1036A01ResponseDataM)serviceResponse.getObjectData();
					addressRefData.setCisAddressId(CIS1036A01ResponseObject.getAddressId());	
				}else{
					return ServiceErrorResponseUtil.fail(serviceResponse);
				}
			}
			
			logger.debug("cisAddressId : "+addressRefData.getCisAddressId());
			
			CISRefDataObject CIS1046A01DataObject = serviceDAO.getServiceData(CIS1046A01ServiceProxy.serviceId,applicationGroupId,addressRefData);
			logger.debug(CIS1046A01DataObject);
			if(!CIS1046A01DataObject.isSuccess()){
				AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE_WORK);
				ServiceResponseDataM serviceResponse = callCIS1046A01(applicationGroup,personalInfo,userId,addressRefData,address, department);
				String statusCode = serviceResponse.getStatusCode();
				logger.debug("CIS1046A01.statusCode : "+statusCode);
				if(!ServiceResponse.Status.SUCCESS.equals(statusCode)){
					return ServiceErrorResponseUtil.fail(serviceResponse);
				}
			}
			
			CISRefDataObject CIS1035A01DataObject = serviceDAO.getServiceData(CIS1035A01ServiceProxy.serviceId,applicationGroupId,personalRefData);
			logger.debug(CIS1035A01DataObject);
			if(!CIS1035A01DataObject.isSuccess() && !ServiceUtil.empty(telephonesForCISMapping)){
				ServiceResponseDataM serviceResponse = callCIS1035A01(applicationGroup, personalInfo, userId, personalRefData, telephonesForCISMapping, department, cisCustomerData);
				String statusCode = serviceResponse.getStatusCode();
				logger.debug("CIS1035A01.statusCode : "+statusCode);
				if(!ServiceResponse.Status.SUCCESS.equals(statusCode)){
					return ServiceErrorResponseUtil.fail(serviceResponse);
				}
			}
	
			CISRefDataObject CIS0315U01DataObject = serviceDAO.getServiceData(
					CIS0315U01ServiceProxy.serviceId,applicationGroupId, personalRefData);
			logger.debug(CIS0315U01DataObject);
			KYCDataM kyc = personalInfo.getKyc();
			if(ServiceUtil.empty(kyc)){
				kyc = new KYCDataM();
			}
			
			if(!CIS0315U01DataObject.isSuccess() && FLAG_YES.equals(kyc.getRelationFlag())){
				
				ServiceResponseDataM serviceResponse  = callCIS0315U01(applicationGroup, personalInfo, null,userId, personalRefData);
				String statusCode = serviceResponse.getStatusCode();
				logger.debug("resultCode(cis0315U01Service) : "+statusCode);
				if(!ServiceResponse.Status.SUCCESS.equals(statusCode)){
					return ServiceErrorResponseUtil.fail(serviceResponse);
				}
			}
			/*
			 * CR55
			 * Call CIS0314I01 for inquiry KYC then update ORIG_KYC.CUSTOMER_RISK_GRADE
			 */
			ServiceResponseDataM CIS0314I01updateKycResponse =callCIS0314I01Service(applicationGroup, personalInfo, userId, personalRefData);
			logger.debug("CIS0314I01updateKycResponse.getStatusCode() : "+CIS0314I01updateKycResponse.getStatusCode());
			if(!ServiceResponse.Status.SUCCESS.equals(CIS0314I01updateKycResponse.getStatusCode())){
				return ServiceErrorResponseUtil.fail(CIS0314I01updateKycResponse);
			}
			CIS0314I01ResponseDataM CIS0314I01kycResponse =(CIS0314I01ResponseDataM)CIS0314I01updateKycResponse.getObjectData();
			if(kyc == null){
				kyc = new KYCDataM();
				kyc.setRelationFlag(FLAG_NO);
				kyc.setPersonalId(personalInfo.getPersonalId());
				personalInfo.setKyc(kyc);
			}
			kyc.setCustomerRiskGrade(CIS0314I01kycResponse.getRiskLevelCode());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			ErrorData errorData = new ErrorData();
			errorData.setErrorInformation(e.getLocalizedMessage());
			errorData.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
			errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
			processResponse.setErrorData(errorData);
		}
		return processResponse;
	}
	
	public ProcessResponse updateCISCustomer(ApplicationGroupDataM applicationGroup,PersonalInfoDataM personalInfo,CISCustomerDataM cisCustomerData,String userId,String department){
			
		boolean isNewCustomer = false; // for existing customer
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS); 
		try {
			ServiceDAO serviceDAO = ServiceFactory.getServiceDAO();
			
			CISRefDataObject personalRefData = new CISRefDataObject();
				personalRefData.setPersonalId(personalInfo.getPersonalId());
				personalRefData.setRefType(CISRefDataObject.RefType.PERSONAL);
			if(null==cisCustomerData){
				cisCustomerData = new CISCustomerDataM();
			}
			
			AddressDataM documentAddress = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
			if(ServiceUtil.empty(documentAddress)){
				documentAddress = new AddressDataM();
			}
			String registAddressCountry = documentAddress.getCountry();
			
			ArrayList<CISTelephoneDataM> telephonesForCISMapping = getTelephoneForCIS(personalInfo);
//			logger.debug("cisCustomerData>>"+ new Gson().toJson(cisCustomerData));
			// 1# new service
			//Call CVRSDQ0001
			String CALL_CVRSDQ0001_FLAG = ServiceCache.getGeneralParam("CALL_CVRSDQ0001_FLAG");
			logger.debug("CALL_CVRSDQ0001_FLAG>>"+CALL_CVRSDQ0001_FLAG);
			logger.debug("registAddressCountry : "+registAddressCountry);
			if(!FLAG_NO.equals(CALL_CVRSDQ0001_FLAG)) { 
				ServiceResponseDataM  CVRSDQ0001Response= callCVRSDQ0001(personalInfo,applicationGroup,cisCustomerData,telephonesForCISMapping,userId,personalRefData, isNewCustomer);
				if(ServiceResponse.Status.SUCCESS.equals(CVRSDQ0001Response.getStatusCode())){
					CVRSDQ0001ResponseDataM cvrsdq0001response = (CVRSDQ0001ResponseDataM)CVRSDQ0001Response.getObjectData();
					ArrayList<ResponseValidateResultDataM>  validateFieldResults = cvrsdq0001response.getResponseValidateFields();
					if(null!=validateFieldResults && validateFieldResults.size()>0){
						String isDeleteComplete = serviceDAO.deleteCVRSValidationResultTable(personalInfo.getPersonalId());
						logger.debug("isDeleteComplete>>"+isDeleteComplete);
						for(ResponseValidateResultDataM fieldResultValidate : validateFieldResults){
							CVRSValidationResultDataM cvrsValidationResultDataM = new CVRSValidationResultDataM();
							cvrsValidationResultDataM.setFieldId(fieldResultValidate.getFieldId());
							cvrsValidationResultDataM.setFieldName(fieldResultValidate.getFieldName());
							cvrsValidationResultDataM.setPersonalId(fieldResultValidate.getPersonalId());
							cvrsValidationResultDataM.setErrorCode(fieldResultValidate.getErrorCode());
							cvrsValidationResultDataM.setErrorDesc(fieldResultValidate.getErrorDesc());
							cvrsValidationResultDataM.setFieldGroup(fieldResultValidate.getFildGroup());
							cvrsValidationResultDataM.setCreateDate(ServiceUtil.getCurrentDate());
							cvrsValidationResultDataM.setCreateBy(userId);
							try {
								serviceDAO.createCVRSValidationResultTable(cvrsValidationResultDataM);
							} catch (Exception e) {
								logger.fatal("ERROR",e);
								return ServiceErrorResponseUtil.fail(e, CVRSDQ0001ServiceProxy.serviceId);
							}							
						}
					}
				}else{
					logger.debug("error call CVRSDQ0001 service ");
					return ServiceErrorResponseUtil.fail(CVRSDQ0001Response);
				}
			}

			// 3#
			//call when if change contact?Email or  not in Existing(check match by phone/mobile/email value with active in DIH(VC_IP_X_ELC_ADR_REL.IP_X_ELC_ADR_REF_ST_CD=1))	
			boolean isExistingCotact = 	isExistingContact(telephonesForCISMapping, cisCustomerData,CIS_ADDRESS_STATUS_CODE);
			logger.debug("isExistingCotact>>"+isExistingCotact);
			if (!isExistingCotact) {
					if(!ServiceUtil.empty(telephonesForCISMapping)){
						ServiceResponseDataM serviceResponse = callCIS1035A01(applicationGroup, personalInfo, userId,personalRefData, telephonesForCISMapping, department, cisCustomerData);
						String statusCode = serviceResponse.getStatusCode();
						logger.debug("CIS1035A01.statusCode : " + statusCode);
					if (!ServiceResponse.Status.SUCCESS.equals(statusCode)) {
						return ServiceErrorResponseUtil.fail(serviceResponse);
					} else {
						CIS1035A01ResponseDataM responseObject = (CIS1035A01ResponseDataM) serviceResponse.getObjectData();
						if(responseObject != null){
							ArrayList<CISContactDataM> cisContacts = responseObject.getCisContact();
							if (null != cisContacts) {
								for (CISContactDataM cisContact : cisContacts) {
									logger.debug("cisContact.getContactTypeCode()>>"+ cisContact.getContactTypeCode());
									if (CIS_MOBILE_PHONE_CODE.equals(cisContact.getContactTypeCode())) {
										personalInfo.setMobileIdRef(cisContact.getContactId());
									}else if(CIS_CONTACT_EMAIL_TYPE_CODE.equals(cisContact.getContactTypeCode())) {
										personalInfo.setEmailIdRef(cisContact.getContactId());
									}else if (ADDRESS_TYPE_CURRENT.equals(cisContact.getContactLocation())) {
											AddressDataM uloAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
											if(null!=uloAddress){
												uloAddress.setPhoneIdRef(cisContact.getContactId());
											}
									}else if(ADDRESS_TYPE_WORK.equals(cisContact.getContactLocation())){
										AddressDataM uloAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
										if(null!=uloAddress){
											uloAddress.setPhoneIdRef(cisContact.getContactId());
										}
									}
								}
							}else{
								logger.debug("Cis Contact is null!!");
							}
						}else{
							logger.debug("Cis Contact is null!!");
						}
					}
				}
		}
									
		// 4# new service
		//call when if new Contact /Email or not prefer(VC_IP_X_ELC_ADR_REL_PREF_F<>Y)
		logger.debug("Call CIS1047O01 service");
		ServiceResponseDataM CIS1047O01Response = null;
		//Case Phone
		if(null!=telephonesForCISMapping && telephonesForCISMapping.size()>0){
			
			 //#mapping refid when model is null and not call 1035A
			 if(!ServiceUtil.empty(cisCustomerData)){
				 ArrayList<CISelcAddressDataM> cisAddress =  cisCustomerData.getElcAddresses();
				 cisMappingNullRefId(cisAddress,personalInfo);
			 }
			 
			 for(CISTelephoneDataM uloTelData : telephonesForCISMapping){
				 logger.debug("uloTelData.getPhoneType() : "+uloTelData.getPhoneType());
				 logger.debug("uloTelData.getPhoneLocation() : "+uloTelData.getPhoneLocation());
				 CISelcAddressDataM cisTelEnquiry =cisCustomerData.selectCISelcAddresTypeByPhoneNo(uloTelData.getPhoneType(),uloTelData.getPhoneLocation(),uloTelData.getPhoneNo(),uloTelData.getPhoneExt());
				 logger.debug("cisTelEnquiry : "+cisTelEnquiry);	
				 if(null==cisTelEnquiry || !FLAG_YES.equals(cisTelEnquiry.getPrefF())){
					  	if( !(cisTelEnquiry == null)
					  		|| (isAddHomePhone && isAddWorkPhone && CIS_LAND_LINE_PHONE_CODE.equals(uloTelData.getPhoneType()) && CIS_CURRENT_LOCATION_CODE.equals(uloTelData.getPhoneLocation()))
					  		|| (isAddHomePhone && !isAddWorkPhone && CIS_LAND_LINE_PHONE_CODE.equals(uloTelData.getPhoneType()) && CIS_CURRENT_LOCATION_CODE.equals(uloTelData.getPhoneLocation()))
					  		|| (isAddWorkPhone && !isAddHomePhone && CIS_LAND_LINE_PHONE_CODE.equals(uloTelData.getPhoneType()) && CIS_WORK_LOCATION_CODE.equals(uloTelData.getPhoneLocation()))
					  		|| !CIS_LAND_LINE_PHONE_CODE.equals(uloTelData.getPhoneType()) ){
					  		if(!(CIS_WORK_LOCATION_CODE.equals(uloTelData.getPhoneLocation()) && isAddHomePhone && isAddWorkPhone)){
								CIS1047O01Response =callCIS1047O01(personalInfo, applicationGroup, userId, CIS_DATA_TYPE_PHONE, uloTelData, personalRefData, department);
								logger.debug("CIS1047O01.statusCode : "+CIS1047O01Response.getStatusCode());
								if(!ServiceResponse.Status.SUCCESS.equals(CIS1047O01Response.getStatusCode())){
									return ServiceErrorResponseUtil.fail(CIS1047O01Response);
								}
					  		}
					  	}
					  	
						if(!ServiceUtil.empty(uloTelData.getFax())){
							CIS1047O01Response =callCIS1047O01(personalInfo, applicationGroup, userId, CIS_DATA_TYPE_FAX, uloTelData, personalRefData, department);
							logger.debug("CIS1047O01.statusCode Fax : "+CIS1047O01Response.getStatusCode());
							if(!ServiceResponse.Status.SUCCESS.equals(CIS1047O01Response.getStatusCode())){
								return ServiceErrorResponseUtil.fail(CIS1047O01Response);
							}
						}				
				 }
			 } 
		}
				
		 CISelcAddressDataM cisEmailEnquiry =cisCustomerData.selectCISelcAddresType(CIS_CONTACT_EMAIL_TYPE_CODE,CIS_CONTACT_EMAIL_LOCATION);
		//Case Email		
		if(personalInfo.getEmailPrimary() != null && 
				( null==cisEmailEnquiry || (!FLAG_YES.equals(cisEmailEnquiry.getPrefF()) && personalInfo.getEmailPrimary().equals(cisEmailEnquiry.getElcAdrDtl())))) {
			CISTelephoneDataM cisEmailTel	=new CISTelephoneDataM();
			CIS1047O01Response =callCIS1047O01(personalInfo, applicationGroup, userId,CIS_DATA_TYPE_EMAIL,cisEmailTel, personalRefData, department);
			logger.debug("CIS1047O01.statusCode Email : "+CIS1047O01Response.getStatusCode());
			if(!ServiceResponse.Status.SUCCESS.equals(CIS1047O01Response.getStatusCode())){
				return ServiceErrorResponseUtil.fail(CIS1047O01Response);
			}
		 }
				
			// 5# 
			//Call when  if change/Add new Address Information
			CISRefDataObject addressRefData = new CISRefDataObject();
			addressRefData.setPersonalId(personalInfo.getPersonalId());
			addressRefData.setRefType(CISRefDataObject.RefType.ADDRESS);
			addressRefData.setCisNo(getCustomerId(personalInfo.getCisNo()));
			ArrayList<AddressDataM> uloAddresses = personalInfo.getAddresses();
			logger.debug("registAddressCountry : "+registAddressCountry);
			//DF1332 if apply type = INC not process address data to cis
			if ( ! APPLICATION_TYPE_INCREASE.equals( applicationGroup.getApplicationType() ) ) {
				if(null!=uloAddresses && uloAddresses.size()>0){	// CR75 : If country of registration address <> Thai, no sending this address to CIS.		
					for(AddressDataM uloAddress : uloAddresses){
							String addressType = uloAddress.getAddressType();
							logger.debug("addressType>>"+addressType);
						if(!(ADDRESS_TYPE_DOCUMENT.equals(addressType) && !COUNTRY_TH.equals(registAddressCountry))){
							if(!CIS_REQUEST_FLP_ADDRESS_TYPES.contains(addressType)){
								continue;
							}
							String  isNewAddress = isNewAddress(uloAddress, cisCustomerData.getAddresses());
							logger.debug("isNewAddress>>"+isNewAddress);
							if(!EXISTING_SAME_DATA.equals(isNewAddress)){			
								// 6# new service
								//Call CIS1048O01 for delete old address relation
								if(EXISTING_NOT_SAME_DATA.equals(isNewAddress)){
									logger.debug("Call CIS1048O01 service");															
										String cisAddressType = ServiceCache.getName(FIELD_ID_ADDRESS_TYPE, uloAddress.getAddressType(), "MAPPING4");
										logger.debug("Call CIS1048O01 service Address Type >>"+cisAddressType);
										CISAddressDataM cisDocAddress=cisCustomerData.selectCISAddresType(cisAddressType);
										if(null!=cisDocAddress && null!=cisDocAddress.getAdrId() && !"".equals(cisDocAddress.getAdrId())){
											ServiceResponseDataM CIS1048O01serviceResponse = callCIS1048O01(personalInfo, applicationGroup,cisDocAddress,userId,personalRefData, department);
											String CIS1048O01statusCode = CIS1048O01serviceResponse.getStatusCode();
											logger.debug("CIS1048O01.statusCode : "+CIS1048O01statusCode);
											if(!ServiceResponse.Status.SUCCESS.contains(CIS1048O01statusCode)){
												return ServiceErrorResponseUtil.fail(CIS1048O01serviceResponse);
											}
										}
								}
								
								//#5 new add New Address
								ServiceResponseDataM CIS1036A01serviceResponse = callCIS1036A01(applicationGroup, personalInfo, userId, addressRefData,addressType, department,cisCustomerData);
								String statusCode = CIS1036A01serviceResponse.getStatusCode();
								logger.debug("CIS1036A01.statusCode : " + statusCode);
								if (ServiceResponse.Status.SUCCESS.equals(statusCode)) {
									CIS1036A01ResponseDataM CIS1036A01ResponseObject = (CIS1036A01ResponseDataM) CIS1036A01serviceResponse.getObjectData();
									logger.debug("CIS1036A01ResponseObject.getAddressId() : " + CIS1036A01ResponseObject.getAddressId());
									addressRefData.setCisAddressId(CIS1036A01ResponseObject.getAddressId());
									uloAddress.setAddressIdRef(CIS1036A01ResponseObject.getAddressId());
																	
									// 7#
									//CIS1046A01 for add new Address Relation
									ServiceResponseDataM CIS1046A01serviceResponse = callCIS1046A01(applicationGroup,personalInfo,userId,addressRefData,uloAddress, department);
									String CIS1046A01statusCode = CIS1046A01serviceResponse.getStatusCode();
									logger.debug("CIS1046A01.statusCode : "+CIS1046A01statusCode);
									if(!ServiceResponse.Status.SUCCESS.equals(CIS1046A01statusCode)){
										return ServiceErrorResponseUtil.fail(CIS1046A01serviceResponse);
									}
								} else {
									return ServiceErrorResponseUtil.fail(CIS1036A01serviceResponse);
								}
							}
						}
					} // end of for
				}
			}
			
			
			// 2# new service
			logger.debug("call CIS1044U01 service ");
			ServiceResponseDataM cis1044U01Response =callCIS1044U01(personalInfo, applicationGroup, userId, cisCustomerData,personalRefData, department);
			if(!ServiceResponse.Status.SUCCESS.equals(cis1044U01Response.getStatusCode())){
				return ServiceErrorResponseUtil.fail(cis1044U01Response);
			}
			
			// if ORIG_KYC_RELATION_FLAG=Y will call 8 and 9
			KYCDataM kycDataM = personalInfo.getKyc();
			
			if(null!=kycDataM &&  FLAG_YES.contains(kycDataM.getRelationFlag())){
				// 8# 
				//Call CIS0314I01 for inquiry KYC
				ServiceResponseDataM CIS0314I01serviceResponse =callCIS0314I01Service(applicationGroup, personalInfo, userId, personalRefData);
				logger.debug("CIS0314I01serviceResponse.getStatusCode() : "+CIS0314I01serviceResponse.getStatusCode());
				if(!ServiceResponse.Status.SUCCESS.equals(CIS0314I01serviceResponse.getStatusCode())){
					return ServiceErrorResponseUtil.fail(CIS0314I01serviceResponse);
				}
				CIS0314I01ResponseDataM CIS0314I01Response =(CIS0314I01ResponseDataM)CIS0314I01serviceResponse.getObjectData();
				
				// 9#
				// call CIS0315U01 for update CIS KYC info
					ServiceResponseDataM serviceResponse  = callCIS0315U01(applicationGroup, personalInfo,CIS0314I01Response,userId, personalRefData);
					String statusCode = serviceResponse.getStatusCode();
					logger.debug("resultCode(cis0315U01Service) : "+statusCode);
					if(!ServiceResponse.Status.SUCCESS.equals(statusCode)){
						return ServiceErrorResponseUtil.fail(serviceResponse);
					}
			}
			// 10#
			/*
			 * CR55
			 * Call CIS0314I01 for inquiry KYC then update ORIG_KYC.CUSTOMER_RISK_GRADE
			 */
			ServiceResponseDataM CIS0314I01updateKycResponse =callCIS0314I01Service(applicationGroup, personalInfo, userId, personalRefData);
			logger.debug("CIS0314I01updateKycResponse.getStatusCode() : "+CIS0314I01updateKycResponse.getStatusCode());
			if(!ServiceResponse.Status.SUCCESS.equals(CIS0314I01updateKycResponse.getStatusCode())){
				return ServiceErrorResponseUtil.fail(CIS0314I01updateKycResponse);
			}
			CIS0314I01ResponseDataM CIS0314I01kycResponse =(CIS0314I01ResponseDataM)CIS0314I01updateKycResponse.getObjectData();
			if(kycDataM == null){
				kycDataM = new KYCDataM();
				kycDataM.setPersonalId(personalInfo.getPersonalId());
				kycDataM.setRelationFlag(FLAG_NO);
				personalInfo.setKyc(kycDataM);
			}
			kycDataM.setCustomerRiskGrade(CIS0314I01kycResponse.getRiskLevelCode());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			ErrorData errorData = new ErrorData();
			errorData.setErrorInformation(e.getLocalizedMessage());
			errorData.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
			errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
			processResponse.setErrorData(errorData);
		}
		return processResponse;
	}
	
	public ServiceResponseDataM callCIS1037A01(ApplicationGroupDataM applicationGroup,PersonalInfoDataM personalInfo, 
			String userId, CISRefDataObject personalRefData, ArrayList<CISTelephoneDataM> cisTelephones, String department,CISCustomerDataM cisCustomerData) throws Exception{
	
		ServiceResponseDataM serviceResponse = new ServiceResponseDataM();
		try {
			CIS1037A01RequestDataM cis1037a01Request = new CIS1037A01RequestDataM();
			cis1037a01Request.setBranchNo(department);	// ORIG_APPLICATION_GROUP.BRANCH_NO(VARCHAR2)		
			cis1037a01Request.setUserId(userId);	//	ORIG_APPLICATION.FINAL_APP_DECISION_BY(VARCHAR2)		
			cis1037a01Request.setBirthDate(personalInfo.getBirthDate());	//ORIG_PERSONAL_INFO.BIRTH_DATE(DATE)
			cis1037a01Request.setCustomerTypeCode(getCustomerTypeCode(personalInfo,cisCustomerData));
			if(ServiceUtil.empty(personalInfo.getNoOfChild())){
				personalInfo.setNoOfChild(new BigDecimal(0));
			}
			cis1037a01Request.setChildrenAmount(personalInfo.getNoOfChild().intValue());	//ORIG_PERSONAL_INFO.NO_OF_CHILD(NUMBER)
			
			AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
			if(ServiceUtil.empty(currentAddress)){
				currentAddress = new AddressDataM();
			}
			
			cis1037a01Request.setContactAddressAmphur(currentAddress.getAmphur());	//ORIG_PERSONAL_ADDRESS.AMPHUR(VARCHAR2)
			cis1037a01Request.setContactAddressBuilding(currentAddress.getBuilding());	//ORIG_PERSONAL_ADDRESS.BUILDING(VARCHAR2)
			cis1037a01Request.setContactAddressCountry(currentAddress.getCountry());	//ORIG_PERSONAL_ADDRESS.COUNTRY(VARCHAR2)
			cis1037a01Request.setContactAddressFloor(currentAddress.getFloor());	//ORIG_PERSONAL_ADDRESS.FLOOR(VARCHAR2)
			cis1037a01Request.setContactAddressNumber(currentAddress.getAddress());	//ORIG_PERSONAL_ADDRESS.ADDRESS(VARCHAR2)
			cis1037a01Request.setContactAddressMoo(currentAddress.getMoo());	//ORIG_PERSONAL_ADDRESS.MOO(VARCHAR2)
			cis1037a01Request.setContactAddressPostCode(currentAddress.getZipcode());	//ORIG_PERSONAL_ADDRESS.ZIPCODE(NUMBER)
			cis1037a01Request.setContactAddressProvince(currentAddress.getProvinceDesc());	//ORIG_PERSONAL_ADDRESS.PROVINCE_DESC(VARCHAR2)	
			cis1037a01Request.setContactAddressRoad(currentAddress.getRoad());	//ORIG_PERSONAL_ADDRESS.ROAD(VARCHAR2)
			cis1037a01Request.setContactAddressRoom(currentAddress.getRoom());	//ORIG_PERSONAL_ADDRESS.ROOM(VARCHAR2)
			cis1037a01Request.setContactAddressSoi(currentAddress.getSoi());	//ORIG_PERSONAL_ADDRESS.SOI(VARCHAR2)
			cis1037a01Request.setContactAddressTumbol(currentAddress.getTambol());	//ORIG_PERSONAL_ADDRESS.TAMBOL(VARCHAR2)
			cis1037a01Request.setContactAddressMooban(currentAddress.getVilapt());	//ORIG_PERSONAL_ADDRESS.VILAPT(VARCHAR2)
			
			//CISContact1
			if(!ServiceUtil.empty(cisTelephones)){
				CISTelephoneDataM cisTelephone = cisTelephones.get(0);
				cis1037a01Request.setTelephoneLocation(cisTelephone.getPhoneLocation());
				cis1037a01Request.setTelephoneNumber(cisTelephone.getPhoneNo());	//ORIG_PERSONAL_ADDRESS.PHONE1(VARCHAR2)
				cis1037a01Request.setTelephoneExtNumber(cisTelephone.getPhoneExt());	//ORIG_PERSONAL_ADDRESS.EXT1(VARCHAR2)
				cis1037a01Request.setTelephoneType(cisTelephone.getPhoneType());
				cisTelephones.remove(0);
			}
		
			//CISContact2
			cis1037a01Request.setMailName(personalInfo.getEmailPrimary());	//ORIG_PERSONAL_ADDRESS.ADDRESS1(VARCHAR2)
			cis1037a01Request.setMailType(CIS_MAIL_TYPE_DEFAULT);	//ORIG_PERSONAL_ADDRESS.ADDRESS_TYPE(VARCHAR2)
				
			cis1037a01Request.setCustomerType(getCustomerType(personalInfo));	//ORIG_PERSONAL_INFO.CUSTOMER_TYPE(VARCHAR2)
			cis1037a01Request.setDocumentId(personalInfo.getIdno());	//ORIG_PERSONAL_INFO.IDNO(VARCHAR2)
			String documentType = ServiceCache.getName(FIELD_ID_CID_TYPE, personalInfo.getCidType(), "MAPPING4");
			cis1037a01Request.setDocumentType(ServiceUtil.empty(documentType) ? null : documentType);	//ORIG_PERSONAL_INFO.CID_TYPE(VARCHAR2)
			String degree = ServiceCache.getName(FIELD_ID_DEGREE, personalInfo.getDegree(), "MAPPING4");
			cis1037a01Request.setDegree(ServiceUtil.empty(degree) ? null : degree);	//ORIG_PERSONAL_INFO.DEGREE(VARCHAR2)
			cis1037a01Request.setEnglishName(personalInfo.getEnFirstName());	//ORIG_PERSONAL_INFO.EN_FIRST_NAME(VARCHAR2)
			cis1037a01Request.setEnglishLastName(personalInfo.getEnLastName());	//ORIG_PERSONAL_INFO.EN_LAST_NAME(VARCHAR2)
			cis1037a01Request.setEnglishMiddleName(personalInfo.getEnMidName());	//ORIG_PERSONAL_INFO.EN_MID_NAME(VARCHAR2)
			String englishTitleName ="";
			if(TITLE_OTHER.equals(personalInfo.getEnTitleCode())){
				englishTitleName = personalInfo.getEnTitleDesc();
			}else{
				englishTitleName= ServiceCache.getName(FIELD_ID_EN_TITLE_CODE, personalInfo.getEnTitleCode(), "MAPPING4");
			}
			
			cis1037a01Request.setEnglishTitleName(ServiceUtil.empty(englishTitleName) ? null : englishTitleName);	//ORIG_PERSONAL_INFO.EN_TITLE_CODE(VARCHAR2)
			cis1037a01Request.setCardExpiryDate(personalInfo.getCidExpDate());	//ORIG_PERSONAL_INFO.CID_EXP_DATE(DATE)
			String maritalStatus = ServiceCache.getName(FIELD_ID_MARRIED, personalInfo.getMarried(), "MAPPING4");
			cis1037a01Request.setMaritalStatus(ServiceUtil.empty(maritalStatus) ? null : maritalStatus);	//ORIG_PERSONAL_INFO.MARRIED(VARCHAR2)
			String nationalityCode = ServiceCache.getName(FIELD_ID_NATIONALITY, personalInfo.getNationality(), "MAPPING4");
			cis1037a01Request.setNationalityCode(ServiceUtil.empty(nationalityCode) ? null : nationalityCode);	//ORIG_PERSONAL_INFO.NATIONALITY(VARCHAR2)
			
			CisAddressServiceUtil.setAddressLine(currentAddress);
			cis1037a01Request.setContactAddressLine1(currentAddress.getAddress1());	//Use CIS lib to generate this field
			cis1037a01Request.setContactAddressLine2(currentAddress.getAddress2());	//Use CIS lib to generate this field
			
			AddressDataM documentAddress = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
			if(ServiceUtil.empty(documentAddress)){
				documentAddress = new AddressDataM();
			}
			
			// CR75 : Registration address: To send **ONLY** country field by sending nationality to country field
			if(CIDTYPE_PASSPORT.equals(personalInfo.getCidType())) {
				cis1037a01Request.setOfficialAddressCountry(personalInfo.getNationality());
			} else {
				CisAddressServiceUtil.setAddressLine(documentAddress);	// This is to set documentAddress.Address2
				cis1037a01Request.setOfficialAddressLine1(documentAddress.getAddress1());	//Use CIS lib to generate this field
				cis1037a01Request.setOfficialAddressLine2(documentAddress.getAddress2());	//Use CIS lib to generate this field
				cis1037a01Request.setOfficialAddressAmphur(documentAddress.getAmphur());
				cis1037a01Request.setOfficialAddressBuilding(documentAddress.getBuilding());
				cis1037a01Request.setOfficialAddressCountry(documentAddress.getCountry());
				cis1037a01Request.setOfficialAddressFloor(documentAddress.getFloor());
				cis1037a01Request.setOfficialAddressNumber(documentAddress.getAddress());
				cis1037a01Request.setOfficialAddressMoo(documentAddress.getMoo());
				cis1037a01Request.setOfficialAddressPostCode(documentAddress.getZipcode());
				cis1037a01Request.setOfficialAddressProvince(documentAddress.getProvinceDesc());
				cis1037a01Request.setOfficialAddressRoad(documentAddress.getRoad());
				cis1037a01Request.setOfficialAddressRoom(documentAddress.getRoom());
				cis1037a01Request.setOfficialAddressSoi(documentAddress.getSoi());
				cis1037a01Request.setOfficialAddressTumbol(documentAddress.getTambol());
				cis1037a01Request.setOfficialAddressMooban(documentAddress.getVilapt());
			}
			String occupation = ServiceCache.getName(FIELD_ID_OCCUPATION, personalInfo.getOccupation(), "MAPPING4");
			cis1037a01Request.setOccupation(ServiceUtil.empty(occupation) ? null : occupation);	//ORIG_PERSONAL_INFO.OCCUPATION(VARCHAR2)
			
			String professionCode = ServiceCache.getName(FIELD_ID_PROFESSION, personalInfo.getProfession(), "MAPPING4");
			if(!ServiceUtil.empty(CisApplicantUtil.mappingProfessionRiskGroup(personalInfo.getBusinessType()))){
				professionCode = CisApplicantUtil.mappingProfessionRiskGroup(personalInfo.getBusinessType());
			}
			cis1037a01Request.setProfessionCode(ServiceUtil.empty(professionCode) ? null : professionCode);	//ORIG_PERSONAL_INFO.PROFESSION(VARCHAR2)
			
			if(PROFESSION_OTHER.equals(professionCode)){
				String professionOther = personalInfo.getProfessionOther();
					if(!ServiceUtil.empty(CisApplicantUtil.mappingProfessionOther(personalInfo.getProfession()))){
						professionOther = CisApplicantUtil.mappingProfessionOther(personalInfo.getProfession());
					}
				cis1037a01Request.setOthProfessDesc(null == professionOther ? "" : professionOther);
			}
			cis1037a01Request.setProspectFlag(CIS_1037_PROSPECT_FLAG);
			String raceCode = ServiceCache.getName(FIELD_ID_NATIONALITY, personalInfo.getRace(), "MAPPING4");
			cis1037a01Request.setRaceCode(ServiceUtil.empty(raceCode) ? null : raceCode);	//ORIG_PERSONAL_INFO.RACE(VARCHAR2)
			
			String gender = ServiceCache.getName(FIELD_ID_GENDER, personalInfo.getGender(), "MAPPING4");
			cis1037a01Request.setGender(ServiceUtil.empty(gender) ? null : gender);	//ORIG_PERSONAL_INFO.GENDER(VARCHAR2)
			cis1037a01Request.setOwnerBrNo(CIS_OWNER_BRANCH_NO_DEFAULT);
			cis1037a01Request.setThaiName(personalInfo.getThFirstName())	;	//ORIG_PERSONAL_INFO.TH_FIRST_NAME(VARCHAR2)
			cis1037a01Request.setThaiLastName(personalInfo.getThLastName());	//ORIG_PERSONAL_INFO.TH_LAST_NAME(VARCHAR2)
			cis1037a01Request.setThaiMiddleName(personalInfo.getThMidName());	//ORIG_PERSONAL_INFO.TH_MID_NAME(VARCHAR2)
			String thaiTitileName="";
			if(TITLE_OTHER.equals(personalInfo.getThTitleCode())){
				thaiTitileName= personalInfo.getThTitleDesc();
			}else{
				thaiTitileName= ServiceCache.getName(FIELD_ID_TH_TITLE_CODE, personalInfo.getThTitleCode(), "MAPPING4");
			}
			cis1037a01Request.setThaiTitleName(ServiceUtil.empty(thaiTitileName) ? null : thaiTitileName);	//ORIG_PERSONAL_INFO.TH_TITLE_CODE(VARCHAR2)
			cis1037a01Request.setCustomerType(getCustomerType(personalInfo));
			cis1037a01Request.setTitleNameCode(CIS_TITLE_FORCED_SAVE_DEFAULT_FLAG);
			cis1037a01Request.setSalary(getSalaryCode(personalInfo.getTotVerifiedIncome()));
			logger.debug("cis1037a01Request.getSalary() >> " +cis1037a01Request.getSalary());
			
			//CR0240: Map DisclosureFlag
			if(!ServiceUtil.empty(personalInfo.getDisclosureFlag()) && !"0".equals(personalInfo.getDisclosureFlag())){
				cis1037a01Request.setConsendFlag(personalInfo.getDisclosureFlag());
			}
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(ServiceCache.getProperty("CIS1037A01_ENDPOINT_URL"));
				serviceRequest.setServiceId(CIS1037A01ServiceProxy.serviceId);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setUserId(userId);
				serviceRequest.setObjectData(cis1037a01Request);
				serviceRequest.setServiceData(personalRefData);
				serviceResponse = proxy.requestService(serviceRequest,requestTransactionId);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(CIS1037A01ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceResponse;
	}
	
	public ServiceResponseDataM callCIS1034A01(ApplicationGroupDataM applicationGroup,PersonalInfoDataM personalInfo, String userId,CISRefDataObject personalRefData, String department) throws Exception{		
		ServiceResponseDataM serviceResponse = new ServiceResponseDataM();
		try {
			CIS1034A01RequestDataM cis1034a01Request = new CIS1034A01RequestDataM();
			cis1034a01Request.setUserId(userId);
			cis1034a01Request.setHubNo(CIS_DEFAULT_SERVICE_HUB_NUMBER);	//N/A
			cis1034a01Request.setBranchNo(department);	// ORIG_APPLICATION_GROUP.BRANCH_NO(VARCHAR2)
			cis1034a01Request.setConfirmFlag(null);	//N/A
			cis1034a01Request.setValidateFlag(null);	//N/A
			cis1034a01Request.setTotalRecord(1);	//1		
			cis1034a01Request.setCustomerType(getCustomerType(personalInfo));
			cis1034a01Request.setCustomerId(getCustomerId(personalInfo.getCisNo()));
			
			//ArrayList
			ArrayList<CISDocInfoDataM> cisDocInfos = new ArrayList<>();
			
//			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
//			if(!ServiceUtil.empty(personalInfos)){
//				for(PersonalInfoDataM personalInfoM : personalInfos){
				CISDocInfoDataM cisDocInfo = new CISDocInfoDataM();
					cisDocInfo.setDocumentNo(personalInfo.getIdno());	//ORIG_PERSONAL_INFO.IDNO(VARCHAR2)
					cisDocInfo.setDocumentType(ServiceCache.getName(FIELD_ID_CID_TYPE, personalInfo.getCidType(), "MAPPING4"));	//ORIG_PERSONAL_INFO.CID_TYPE(VARCHAR2)
					cisDocInfo.setPlaceIssue(null);	//N/A
					cisDocInfo.setIssueDate(null);	//N/A
					cisDocInfo.setExpiryDate(personalInfo.getCidExpDate());	//ORIG_PERSONAL_INFO.CID_EXP_DATE(DATE)
					cisDocInfos.add(cisDocInfo);
//				}
				cis1034a01Request.setCisDocInfo(cisDocInfos);
//			}
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(ServiceCache.getProperty("CIS1034A01_ENDPOINT_URL"));
				serviceRequest.setServiceId(CIS1034A01ServiceProxy.serviceId);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setUserId(userId);
				serviceRequest.setObjectData(cis1034a01Request);
				serviceRequest.setServiceData(personalRefData);
				serviceResponse=proxy.requestService(serviceRequest,requestTransactionId);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(CIS1034A01ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceResponse;
	}
	
	public ServiceResponseDataM callCIS1036A01(ApplicationGroupDataM applicationGroup,PersonalInfoDataM personalInfo,String userId,CISRefDataObject personalRefData,String addressType, String department,CISCustomerDataM cisCustomerData) throws Exception{
		ServiceResponseDataM serviceResponse = new ServiceResponseDataM();
		
		try {
			AddressDataM address = personalInfo.getAddress(addressType);
			if(null == address){
				address = new AddressDataM();
				address.setAddressType(addressType);
			}
			CIS1036A01RequestDataM cis1036a01Request = new CIS1036A01RequestDataM();		
				cis1036a01Request.setUserId(userId);	//	ORIG_APPLICATION.FINAL_APP_DECISION_BY(VARCHAR2)				
				cis1036a01Request.setHubNo(CIS_DEFAULT_SERVICE_HUB_NUMBER);	//N/A
				cis1036a01Request.setBranchNo(department);	// ORIG_APPLICATION_GROUP.BRANCH_NO(VARCHAR2)
				cis1036a01Request.setConfirmFlag(null);	//N/A
				cis1036a01Request.setValidateFlag(null);	//N/A		
				
				cis1036a01Request.setCustomerType(getCustomerType(personalInfo));
				cis1036a01Request.setCustomerId(getCustomerId(personalInfo.getCisNo()));
				cis1036a01Request.setCustomerTypeCode(getCustomerTypeCode(personalInfo,cisCustomerData));
				cis1036a01Request.setAddressId(getAddressIdFormat(address.getAddressIdRef()));	//N/A
				String titleCompany= ServiceCache.getName(FIELD_ID_COMPANY_TITLE, "CHOICE_NO", address.getCompanyTitle(), "MAPPING4");
				cis1036a01Request.setAddressName(ServiceUtil.empty(titleCompany)?address.getCompanyName():titleCompany+""+address.getCompanyName());	//ORIG_PERSONAL_ADDRESS.COMPANY_NAME(VARCHAR2)
				cis1036a01Request.setMailBoxNo(null);	//N/A
				cis1036a01Request.setMailNo(address.getAddress());	//ORIG_PERSONAL_ADDRESS.ADDRESS(VARCHAR2)
				cis1036a01Request.setMoo(address.getMoo());	//ORIG_PERSONAL_ADDRESS.MOO(VARCHAR2)
				cis1036a01Request.setMooban(address.getVilapt());	//ORIG_PERSONAL_ADDRESS.VILAPT(VARCHAR2)
				cis1036a01Request.setBuilding(address.getBuilding());	//ORIG_PERSONAL_ADDRESS.BUILDING(VARCHAR2)
				cis1036a01Request.setRoom(address.getRoom());	//ORIG_PERSONAL_ADDRESS.ROOM(VARCHAR2)
				cis1036a01Request.setFloor(address.getFloor());	//ORIG_PERSONAL_ADDRESS.FLOOR(VARCHAR2)
				cis1036a01Request.setSoi(address.getSoi());	//ORIG_PERSOI(VARCHAR2)
				cis1036a01Request.setRoad(address.getRoad());	//ORIG_PERSONAL_ADDRESS.ROAD(VARCHAR2)
				cis1036a01Request.setTumbol(address.getTambol());	//ORIG_PERSONALSONAL_ADDRESS._ADDRESS.TAMBOL(VARCHAR2)
				cis1036a01Request.setAmphur(address.getAmphur());	//ORIG_PERSONAL_ADDRESS.AMPHUR(VARCHAR2)
				cis1036a01Request.setProvince(address.getProvinceDesc());	//ORIG_PERSONAL_ADDRESS.PROVINCE_DESC(VARCHAR2)
				cis1036a01Request.setPostCode(address.getZipcode());	//ORIG_PERSONAL_ADDRESS.ZIPCODE(NUMBER)
				cis1036a01Request.setCountry(address.getCountry());	//ORIG_PERSONAL_ADDRESS.COUNTRY(VARCHAR2)
				
				CisAddressServiceUtil.setAddressLine(address);
				cis1036a01Request.setLine1(address.getAddress1());	//Use CIS lib to generate this field
				cis1036a01Request.setLine2(address.getAddress2());	//Use CIS lib to generate this field
				
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
					serviceRequest.setEndpointUrl(ServiceCache.getProperty("CIS1036A01_ENDPOINT_URL"));
					serviceRequest.setServiceId(CIS1036A01ServiceProxy.serviceId);
					serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
					serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
					serviceRequest.setUserId(userId);
					serviceRequest.setObjectData(cis1036a01Request);
					serviceRequest.setServiceData(personalRefData);
					serviceResponse = proxy.requestService(serviceRequest,requestTransactionId);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(CIS1036A01ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceResponse;
	}
		
	public ServiceResponseDataM callCIS1046A01(ApplicationGroupDataM applicationGroup, PersonalInfoDataM personalInfo,String userId,CISRefDataObject personalRefData,AddressDataM uloAddress, String department) throws Exception{
		ServiceResponseDataM serviceResponse = new ServiceResponseDataM();
		try {
			if(null == uloAddress){
				uloAddress = new AddressDataM();
			}
			CIS1046A01RequestDataM cis1046a01Request = new CIS1046A01RequestDataM();
				cis1046a01Request.setUserId(userId);
				cis1046a01Request.setHubNumber(CIS_DEFAULT_SERVICE_HUB_NUMBER);	//N/A
				cis1046a01Request.setBranchNo(department);	// ORIG_APPLICATION_GROUP.BRANCH_NO(VARCHAR2)
				cis1046a01Request.setConfirmFlag(null);	//N/A
				cis1046a01Request.setValidateFlag(null);	//N/A		
				cis1046a01Request.setCustomerType(getCustomerType(personalInfo));
				cis1046a01Request.setCustomerId(getCustomerId(personalInfo.getCisNo()));		
				cis1046a01Request.setAddressId(personalRefData.getCisAddressId());	//Address ID from CIS1036A01
				ArrayList<CISAddrRelationDataM> cisAddrRelations = cis1046a01Request.getCisAddrRelations();
				if(ServiceUtil.empty(cisAddrRelations)){
					cisAddrRelations = new ArrayList<CISAddrRelationDataM>();
					cis1046a01Request.setCisAddrRelations(cisAddrRelations);
				}
				CISAddrRelationDataM cisAddrRelation = new CISAddrRelationDataM();
//					String addressType = ServiceCache.getName(FIELD_ID_ADDRESS_TYPE, address.getAddressType(), "MAPPING4");
				String cisAddressType = ServiceCache.getName(FIELD_ID_ADDRESS_TYPE, "CHOICE_NO", uloAddress.getAddressType(), "MAPPING4"); 
					cisAddrRelation.setAddressType(cisAddressType);//ORIG_PERSONAL_ADDRESS.ADDRESS_TYPE
					cisAddrRelations.add(cisAddrRelation);
				cis1046a01Request.setCisAddrRelations(cisAddrRelations);
				cis1046a01Request.setAccountId(null);	//N/A
				cis1046a01Request.setAccountReferenceId(null);	//N/A
				cis1046a01Request.setAccountLevel(CIS_ACCOUNT_LEVEL_DEFAULT);	// C=Customer		
				cis1046a01Request.setProspectFlag(CIS_PROSPECT_FLAG);
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
					serviceRequest.putRawData(CIS1046A01ServiceProxy.requestConstants.userId, applicationGroup.getUserId());
					serviceRequest.setEndpointUrl(ServiceCache.getProperty("CIS1046A01_ENDPOINT_URL"));
					serviceRequest.setServiceId(CIS1046A01ServiceProxy.serviceId);
					serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
					serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
					serviceRequest.setUserId(userId);
					serviceRequest.setObjectData(cis1046a01Request);
					serviceRequest.setServiceData(personalRefData);	
					serviceResponse =  proxy.requestService(serviceRequest,requestTransactionId);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(CIS1046A01ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceResponse;
	}
	
	public ServiceResponseDataM callCIS1035A01(ApplicationGroupDataM applicationGroup, PersonalInfoDataM personalInfo, 
			String userId,CISRefDataObject personalRefData, ArrayList<CISTelephoneDataM> cisTelephones, String department, CISCustomerDataM cisCustomerData) throws Exception{
			ServiceResponseDataM serviceResponse = new ServiceResponseDataM();
			
			if(ServiceUtil.empty(cisTelephones)){
				cisTelephones = new ArrayList<CISTelephoneDataM>();
			}
			try {
				CIS1035A01RequestDataM cis1035a01Request = new CIS1035A01RequestDataM();
				cis1035a01Request.setUserId(userId);
				cis1035a01Request.setHubNo(CIS_DEFAULT_SERVICE_HUB_NUMBER);	//N/A
				cis1035a01Request.setBranchNo(department);	//N/A
				cis1035a01Request.setConfirmFlag(null);	//N/A
				cis1035a01Request.setValidateFlag(null);	//N/A				
				
				ArrayList<CISContactDataM> cisContacts = new ArrayList<>();
				AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE_WORK);	
				if(null == address){
					address = new AddressDataM();
					address.setAddressType(ADDRESS_TYPE_WORK);
				}
				CISelcAddressDataM cisTelEnquiry = null;
				for(CISTelephoneDataM cisTelephone : cisTelephones){
					logger.debug("cisTelephone.getPhoneType() : "+cisTelephone.getPhoneType());
					logger.debug("cisTelephone.getPhoneLocation() : "+cisTelephone.getPhoneLocation());
					cisTelEnquiry =cisCustomerData.selectCISelcAddresType(cisTelephone.getPhoneType(),cisTelephone.getPhoneLocation());
					logger.debug("cisTelEnquiry : "+cisTelEnquiry);
					if(cisTelEnquiry == null ||
					  (cisTelEnquiry != null && (!cisTelephone.getPhoneNo().equals(cisTelEnquiry.getTelNo()) || 
						(cisTelephone.getPhoneExt() != null && !cisTelephone.getPhoneExt().equals(cisTelEnquiry.getTelExnNo())))) ){						
						CISContactDataM cisContact = new CISContactDataM();
						cisContact.setTypeCode(cisTelephone.getPhoneType());
						cisContact.setLocationCode(cisTelephone.getPhoneLocation());
						cisContact.setContactNo(cisTelephone.getPhoneNo());	//ORIG_PERSONAL_INFO.MOBILE_NO(VARCHAR2)
						cisContact.setContactExtNo(cisTelephone.getPhoneExt());	//N/A
						cisContact.setContactAvailabilityTime(null);	//N/A
					//	cisContact.setContactName(personalInfo.getEmailPrimary());	//ORIG_PERSONAL_INFO.EMAIL_PRIMARY(VARCHAR2)
						cisContacts.add(cisContact);
						if(CIS_LAND_LINE_PHONE_CODE.equals(cisTelephone.getPhoneType()) && CIS_CURRENT_LOCATION_CODE.equals(cisTelephone.getPhoneLocation())){
							isAddHomePhone = true;
						}
						if(CIS_LAND_LINE_PHONE_CODE.equals(cisTelephone.getPhoneType()) && CIS_WORK_LOCATION_CODE.equals(cisTelephone.getPhoneLocation())){
							isAddWorkPhone = true;
						}
					}
				}
				logger.debug("isAddHomePhone : "+isAddHomePhone);
				logger.debug("isAddWorkPhone : "+isAddWorkPhone);
				
				if(!ServiceUtil.empty(personalInfo.getEmailPrimary())){
					cisTelEnquiry =cisCustomerData.selectCISelcAddresType(CIS_CONTACT_EMAIL_TYPE_CODE, CIS_CONTACT_EMAIL_LOCATION);
					if(cisTelEnquiry == null || (cisTelEnquiry != null && !personalInfo.getEmailPrimary().equals(cisTelEnquiry.getElcAdrDtl()))){
						CISContactDataM cisEmailContact = new CISContactDataM();
						cisEmailContact.setTypeCode(CIS_CONTACT_EMAIL_TYPE_CODE);
						cisEmailContact.setContactAvailabilityTime(null);	//N/A
						cisEmailContact.setContactName(personalInfo.getEmailPrimary());	//ORIG_PERSONAL_INFO.EMAIL_PRIMARY(VARCHAR2)
						cisEmailContact.setLocationCode(CIS_CONTACT_EMAIL_LOCATION);
						cisContacts.add(cisEmailContact);
					}
				}				
				logger.debug("cisContacts.size() : "+cisContacts.size());
				cis1035a01Request.setTotalRecord(cisContacts.size());	//COUNT
				if(cisContacts.size() > 0){							
					cis1035a01Request.setCustomerType(getCustomerType(personalInfo));
					cis1035a01Request.setCustomerId(getCustomerId(personalInfo.getCisNo()));
					cis1035a01Request.setCisContact(cisContacts);
					ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
						serviceRequest.setEndpointUrl(ServiceCache.getProperty("CIS1035A01_ENDPOINT_URL"));
						serviceRequest.setServiceId(CIS1035A01ServiceProxy.serviceId);
						serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
						serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
						serviceRequest.setUserId(userId);
						serviceRequest.setObjectData(cis1035a01Request);
						serviceRequest.setServiceData(personalRefData);
						serviceResponse =proxy.requestService(serviceRequest,requestTransactionId);
				}else{
					serviceResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
				}
				
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
				serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
				serviceResponse.setErrorInfo(error(CIS1035A01ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
			}		
			
		return serviceResponse;
	}
	
	public ServiceResponseDataM callCIS0314I01Service(ApplicationGroupDataM applicationGroup,PersonalInfoDataM personalInfo, String userId,CISRefDataObject serviceCISCustomer ){
		ServiceResponseDataM serviceResponse = new ServiceResponseDataM();
		
		try{
			CIS0314I01RequestDataM cis0314i01Request = new CIS0314I01RequestDataM();
			cis0314i01Request.setCustomerType(getCustomerType(personalInfo));
			cis0314i01Request.setCustomerId(getCustomerId(personalInfo.getCisNo()));
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CIS0314I01ServiceProxy.serviceId);
				serviceRequest.setEndpointUrl(ServiceCache.getProperty("CIS0314I01_ENDPOINT_URL"));
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setUserId(userId);
				serviceRequest.setObjectData(cis0314i01Request);
				serviceRequest.setServiceData(serviceCISCustomer);
			serviceResponse = proxy.requestService(serviceRequest,requestTransactionId);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(CIS0314I01ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceResponse;
	}
	
	public ServiceResponseDataM callCIS0315U01(ApplicationGroupDataM applicationGroup, PersonalInfoDataM personalInfo,CIS0314I01ResponseDataM CIS0314I01Response,String userId, CISRefDataObject personalRefData) throws Exception{
		ServiceResponseDataM serviceResponse = new ServiceResponseDataM();
	
			try {
				KYCDataM kyc = personalInfo.getKyc();
				if(ServiceUtil.empty(kyc)){
					kyc = new KYCDataM();
				}			
				CIS0315U01RequestDataM cis0315u01Request = new CIS0315U01RequestDataM();
				logger.debug("getCustomerId(personalInfo.getCisNo()) >> "+getCustomerId(personalInfo.getCisNo()));
				cis0315u01Request.setCustomerId(getCustomerId(personalInfo.getCisNo()));	
				cis0315u01Request.setPolicalPositionDescription(kyc.getRelPosition());	//ORIG_KYC.REL_POSITION(VARCHAR2)
				cis0315u01Request.setSoucreAssetCode(getSourceOfIncome(personalInfo));
				cis0315u01Request.setSoucreAssetOtherDescription(getSourceOfIncomeDesc(personalInfo));
				if(null!=CIS0314I01Response){
					cis0315u01Request.setAssetValueAmount(CIS0314I01Response.getAssetValueAmount());
					cis0315u01Request.setAssetValueCode(CIS0314I01Response.getAssetValueCode());
				}
			
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
					serviceRequest.setEndpointUrl(ServiceCache.getProperty("CIS0315U01_ENDPOINT_URL"));
					serviceRequest.setServiceId(CIS0315U01ServiceProxy.serviceId);
					serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
					serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
					serviceRequest.setUserId(userId);
					serviceRequest.setObjectData(cis0315u01Request);
					serviceRequest.setServiceData(personalRefData);
					serviceResponse = proxy.requestService(serviceRequest,requestTransactionId);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
				serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
				serviceResponse.setErrorInfo(error(CIS0315U01ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
			}
			return serviceResponse;
	}
//	private String getCustomerTypeCode(PersonalInfoDataM personalInfo){
		
//		String customerTypeCode = "";
//		String CID_TYPE_CACHE = FIELD_ID_CID_TYPE;
//		String choiceNo = personalInfo.getCidType();
//		if(CIDTYPE_IDCARD.equals(personalInfo.getCidType())) {
//			CID_TYPE_CACHE = FIELD_ID_OCCUPATION;
//			choiceNo = personalInfo.getOccupation();
//		}
//		String custTypeCode = ServiceCache.getName(CID_TYPE_CACHE, choiceNo, "SYSTEM_ID4");
//		if(!ServiceUtil.empty(custTypeCode)){
//			customerTypeCode = custTypeCode;
//		}else{
//			customerTypeCode = CIS_CUSTOMER_TYPE_INDIVIDUAL;
//		}
		
//		String occupation = ServiceCache.getName(FIELD_ID_OCCUPATION, personalInfo.getOccupation(), "MAPPING4");
//		logger.debug("kGroupFlag : "+personalInfo.getkGroupFlag());
//		logger.debug("CidType : "+personalInfo.getCidType());
//		logger.debug("occupation : "+occupation);
//		
//		if(FLAG_YES.equals(personalInfo.getkGroupFlag())) {
//			return CIS_CUSTOMER_TYPE_KGROUP;
//		} else if(CIDTYPE_PASSPORT.equals(personalInfo.getCidType()) || CIDTYPE_MIGRANT.equals(personalInfo.getCidType())) {
//			return CIS_CUSTOMER_TYPE_FOREIGNER;
//		} else if(OCCUPATION_OWN_BUSINESS.equals(occupation) || OCCUPATION_REGISTRATION.equals(occupation)) {
//			return CIS_CUSTOMER_TYPE_OCCUPATION;
//		}
//				
//		return CIS_CUSTOMER_TYPE_CODE;
//		
//	}
	private String getCustomerTypeCode(PersonalInfoDataM personalInfo,CISCustomerDataM cisCustomer){
		
//		String customerTypeCode = "";
//		String CID_TYPE_CACHE = FIELD_ID_CID_TYPE;
//		String choiceNo = personalInfo.getCidType();
//		if(CIDTYPE_IDCARD.equals(personalInfo.getCidType())) {
//			CID_TYPE_CACHE = FIELD_ID_OCCUPATION;
//			choiceNo = personalInfo.getOccupation();
//		}
//		String custTypeCode = ServiceCache.getName(CID_TYPE_CACHE, choiceNo, "SYSTEM_ID4");
//		if(!ServiceUtil.empty(custTypeCode)){
//			customerTypeCode = custTypeCode;
//		}else{
//			customerTypeCode = CIS_CUSTOMER_TYPE_INDIVIDUAL;
//		}
		
		String occupation = ServiceCache.getName(FIELD_ID_OCCUPATION, personalInfo.getOccupation(), "MAPPING4");
		logger.debug("kGroupFlag : "+personalInfo.getkGroupFlag());
		logger.debug("CidType : "+personalInfo.getCidType());
		logger.debug("occupation : "+occupation);
		
		if(!ServiceUtil.empty(cisCustomer) && CIS_CUSTOMER_TYPE_KGROUP.equals(cisCustomer.getIpLglStcTpCd())) {
			return CIS_CUSTOMER_TYPE_KGROUP;
		} else if(CIDTYPE_PASSPORT.equals(personalInfo.getCidType()) || CIDTYPE_MIGRANT.equals(personalInfo.getCidType())) {
			return CIS_CUSTOMER_TYPE_FOREIGNER;
		} else if(OCCUPATION_OWN_BUSINESS.equals(occupation) || OCCUPATION_REGISTRATION.equals(occupation)) {
			return CIS_CUSTOMER_TYPE_OCCUPATION;
		}
				
		return CIS_CUSTOMER_TYPE_CODE;
		
	}
	public static String getCustomerId(String cisNo){
		if(null == cisNo){
			cisNo = "";
		}		
		return String.format("%10s",cisNo).replace(' ','0');
	}
	public String getOwnerBranchNumber(String branchNo){
		if(null == branchNo){
			branchNo = CIS_OWNER_BRANCH_NO_DEFAULT;
		}		
		return String.format("%4s",branchNo).replace(' ','0');
	}
	public String getKYCBranchNumber(String branchNo){
		if(null == branchNo){
			branchNo = CIS_OWNER_BRANCH_NO_DEFAULT;
		}		
		return String.format("%4s",branchNo).replace(' ','0');
	}
	
	public String getAddressIdFormat(String addressId){
		if(null == addressId){
			return "";
		}		
		return String.format("%15s",addressId).replace(' ','0');
	}
	public String getContactIdFormat(String contactRefId){
		if(null == contactRefId){
			return "";
		}		
		return String.format("%15s",contactRefId).replace(' ','0');
	}
	
	private String getCustomerType(PersonalInfoDataM personalInfo){
		String customerType = personalInfo.getCustomerType();
		if(ServiceUtil.empty(customerType)){
			customerType = ServiceCache.getConstant("CIS_CUSTOMER_TYPE_INDIVIDUAL");
		}
		return customerType;
	}
	private String getSourceOfIncome(PersonalInfoDataM personalInfo){
		String sourceOfIncome = "000000000000000";
		char[] source = sourceOfIncome.toCharArray();
		if(!ServiceUtil.empty(personalInfo.getSalary())){
			source[Integer.parseInt(CIS_SALARY_CODE)] = '1';
		}
		if(!ServiceUtil.empty(personalInfo.getFreelanceIncome())){
			source[Integer.parseInt(CIS_FREELANCE_INCOME_CODE)] = '1';
		}
		if(!ServiceUtil.empty(personalInfo.getCirculationIncome())){
			source[Integer.parseInt(CIS_CIRCULATION_INCOME_CODE)] = '1';
		}
		if(!ServiceUtil.empty(personalInfo.getMonthlyIncome())){
			source[Integer.parseInt(CIS_MONTHLY_INCOME_CODE)] = '1';
		}
		sourceOfIncome = new String(source);
		return sourceOfIncome;
	}
	private String getSourceOfIncomeDesc(PersonalInfoDataM personalInfo){
		List<String> sourceOfIncomeDescs = new ArrayList<>();
		if(!ServiceUtil.empty(personalInfo.getSrcOthIncBonus())){
			sourceOfIncomeDescs.add(BONUS_TEXT);
		}
		if(!ServiceUtil.empty(personalInfo.getSrcOthIncCommission())){
			sourceOfIncomeDescs.add(COMMISSION_TEXT);
		}
		if(!ServiceUtil.empty(personalInfo.getSrcOthIncOther())){
			sourceOfIncomeDescs.add(OTHER_TEXT);
		}
		return StringUtils.join(sourceOfIncomeDescs, ',');
	}
	
	public void cisMappingNullRefId(ArrayList<CISelcAddressDataM> addresses,PersonalInfoDataM personalInfo){
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(ServiceUtil.empty(currentAddress)){
			currentAddress = new AddressDataM();
		}
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(ServiceUtil.empty(workAddress)){
			workAddress = new AddressDataM();
		}
		
		if(null!=addresses && addresses.size()>0 && null!= personalInfo){
			for(CISelcAddressDataM cisAddress : addresses){
				if(!ServiceUtil.empty(cisAddress.getElcAdrUseTpCd()) 
						&& cisAddress.getElcAdrUseTpCd().equals(ServiceCache.getName(FIELD_ID_ADDRESS_TYPE, ADDRESS_TYPE_CURRENT, "MAPPING4"))
						&& ServiceUtil.empty(currentAddress.getAddressIdRef())){
					currentAddress.setAddressIdRef(cisAddress.getElcAdrId());
				}
				if(!ServiceUtil.empty(cisAddress.getElcAdrUseTpCd()) 
						&& cisAddress.getElcAdrUseTpCd().equals(ServiceCache.getName(FIELD_ID_ADDRESS_TYPE, ADDRESS_TYPE_WORK, "MAPPING4"))
						&& ServiceUtil.empty(workAddress.getAddressIdRef())){
					workAddress.setAddressIdRef(cisAddress.getElcAdrId());
				}
				if(!ServiceUtil.empty(cisAddress.getElcAdrUseTpCd()) 
						&& cisAddress.getElcAdrUseTpCd().equals(CIS_MOBILE_PHONE_CODE)
						&& ServiceUtil.empty(personalInfo.getMobileIdRef())){
					personalInfo.setMobileIdRef(cisAddress.getElcAdrId());
				}
			}
		}
	}
	
	private ArrayList<CISTelephoneDataM> getTelephoneForCIS(PersonalInfoDataM personalInfo){
		
		ArrayList<CISTelephoneDataM> cisTelephones = new ArrayList<>();
		
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(ServiceUtil.empty(currentAddress)){
			currentAddress = new AddressDataM();
		}
		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(ServiceUtil.empty(workAddress)){
			workAddress = new AddressDataM();
		}
		
		if(!ServiceUtil.empty(currentAddress.getPhone1())){
			CISTelephoneDataM currentTelephone = new CISTelephoneDataM();
				currentTelephone.setPhoneLocation(ADDRESS_TYPE_CURRENT);
				currentTelephone.setPhoneNo(currentAddress.getPhone1());
				currentTelephone.setPhoneExt(currentAddress.getExt1());
				currentTelephone.setPhoneType(CIS_LAND_LINE_PHONE_CODE);
				currentTelephone.setFax(currentAddress.getFax());
			cisTelephones.add(currentTelephone);
		}
		if(!ServiceUtil.empty(workAddress.getPhone1())){
			CISTelephoneDataM workTelephone = new CISTelephoneDataM();
				workTelephone.setPhoneLocation(ADDRESS_TYPE_WORK);
				workTelephone.setPhoneNo(workAddress.getPhone1());
				workTelephone.setPhoneExt(workAddress.getExt1());
				workTelephone.setPhoneType(CIS_LAND_LINE_PHONE_CODE);
				workTelephone.setFax(workAddress.getFax());
			cisTelephones.add(workTelephone);
		}
		if(!ServiceUtil.empty(personalInfo.getMobileNo())){
			CISTelephoneDataM workTelephone = new CISTelephoneDataM();
				workTelephone.setPhoneNo(personalInfo.getMobileNo());
				workTelephone.setPhoneType(CIS_MOBILE_PHONE_CODE);
			cisTelephones.add(workTelephone);
		}
		
		return cisTelephones;
	}
	private String getSalaryCode(BigDecimal salaryAmt){
		if(null==salaryAmt) return "";
		if(isBetween(BigDecimal.ZERO, new BigDecimal(7999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_0_TO_7999");
		}else if(isBetween(new BigDecimal(8000), new BigDecimal(14999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_8000_TO_14999");
		}else if(isBetween(new BigDecimal(15000), new BigDecimal(19999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_15000_TO_19999");
		}else if(isBetween(new BigDecimal(20000), new BigDecimal(29999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_20000_TO_29999");
		}else if(isBetween(new BigDecimal(30000), new BigDecimal(49999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_30000_TO_49999");
		}else if(isBetween(new BigDecimal(50000),new BigDecimal(69999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_50000_TO_69999");
		}else if(isBetween(new BigDecimal(70000),new BigDecimal(99999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_70000_TO_99999");
		}else if(isBetween(new BigDecimal(100000),new BigDecimal(249999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_100000_TO_249999");
		}else if(isBetween(new BigDecimal(250000),new BigDecimal(499999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_250000_TO_499999");
		}else if(isBetween(new BigDecimal(500000),new BigDecimal(999999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_500000_TO_999999");
		}else if(isBetween(new BigDecimal(1000000),new BigDecimal(1499999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_1000000_TO_1499999");
		}else if(isBetween(new BigDecimal(1500000),new BigDecimal(2499999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_1500000_TO_2499999");
		}else if(isBetween(new BigDecimal(2500000),new BigDecimal(4999999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_2500000_TO_4999999");
		}else if(isBetween(new BigDecimal(5000000),new BigDecimal(7499999), salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_BTW_5000000_TO_7499999");
		}else if(isBetween(new BigDecimal(7500000),null, salaryAmt)){
			return ServiceCache.getConstant("CIS_SALARYCODE_MORE_OR_EQUAL_7500000");
		} 
		return "";
	}
	private boolean isBetween(BigDecimal start,BigDecimal end,BigDecimal value){
		if(null==end){
			return value.compareTo(start)>=0;
		}
		return value.compareTo(start)>=0 && value.compareTo(end) <=0 ;
	}
	public ServiceResponseDataM  callCVRSDQ0001(PersonalInfoDataM personalInfo,ApplicationGroupDataM applicationGroup,CISCustomerDataM cisCustomer,ArrayList<CISTelephoneDataM> cisTelephones,String userId,CISRefDataObject personalRefData, boolean isNewCustomer){
		ServiceResponseDataM serviceResponse  = new ServiceResponseDataM();
		
		try {
			CVRSDQ0001RequestDataM CVRSDQ0001Request = new CVRSDQ0001RequestDataM();
			CVRSDQ0001Request.setEducation(ServiceCache.getName(FIELD_ID_DEGREE, personalInfo.getDegree(), "MAPPING4"));
			CVRSDQ0001Request.setGender(ServiceCache.getName(FIELD_ID_GENDER, personalInfo.getGender(), "MAPPING4"));
			String engTitleName ="";
			if(TITLE_OTHER.equals(personalInfo.getEnTitleCode())){
				engTitleName= personalInfo.getEnTitleDesc();
			}else{
				engTitleName = ServiceCache.getName(FIELD_ID_EN_TITLE_CODE, personalInfo.getEnTitleCode(), "MAPPING4");
			}
			CVRSDQ0001Request.setEngTitleName(engTitleName);
			CVRSDQ0001Request.setMarriageStatus(ServiceCache.getName(FIELD_ID_MARRIED, personalInfo.getMarried(), "MAPPING4"));
			CVRSDQ0001Request.setNationality(ServiceCache.getName(FIELD_ID_NATIONALITY, personalInfo.getNationality(), "MAPPING4"));
			CVRSDQ0001Request.setRace(ServiceCache.getName(FIELD_ID_NATIONALITY, personalInfo.getRace(), "MAPPING4"));
			CVRSDQ0001Request.setOccupation(ServiceCache.getName(FIELD_ID_OCCUPATION, personalInfo.getOccupation(), "MAPPING4"));
			
			String professionCode = ServiceCache.getName(FIELD_ID_PROFESSION, personalInfo.getProfession(), "MAPPING4");
			if(!ServiceUtil.empty(CisApplicantUtil.mappingProfessionRiskGroup(personalInfo.getBusinessType()))){
				professionCode = CisApplicantUtil.mappingProfessionRiskGroup(personalInfo.getBusinessType());
			}
			CVRSDQ0001Request.setProfession(ServiceUtil.empty(professionCode) ? null : professionCode);
			
			String thTitleName ="";
			if(TITLE_OTHER.equals(personalInfo.getThTitleCode())){
				thTitleName= personalInfo.getThTitleDesc();
			}else{
				thTitleName = ServiceCache.getName(FIELD_ID_TH_TITLE_CODE, personalInfo.getThTitleCode(), "MAPPING4");
			}
			
			CVRSDQ0001Request.setThaiTitleName(thTitleName);
			CVRSDQ0001Request.setCustomerType(getCustomerType(personalInfo));
			CVRSDQ0001Request.setCustomerTypeCode(getCustomerTypeCode(personalInfo,cisCustomer));
			CVRSDQ0001Request.setPersonalId(personalInfo.getPersonalId());
			CVRSDQ0001Request.setAsset(cisCustomer.getAstExcldLand());
			CVRSDQ0001Request.setDocumentType(ServiceCache.getName(FIELD_ID_CID_TYPE, personalInfo.getCidType(), "MAPPING4"));
//			CVRSDQ0001Request.setConsentFlag(personalInfo.getCo);
			CVRSDQ0001Request.setDateOfBirth(personalInfo.getBirthDate());
			CVRSDQ0001Request.setEngFirstName(personalInfo.getEnFirstName());
			CVRSDQ0001Request.setEngLastName(personalInfo.getEnLastName());
			CVRSDQ0001Request.setEngMiddleName(personalInfo.getEnMidName());
			CVRSDQ0001Request.setExpireDate(personalInfo.getCidExpDate());
			CVRSDQ0001Request.setIdNo(personalInfo.getIdno());
//			CVRSDQ0001Request.setIssueBy(personalInfo.get);
			CVRSDQ0001Request.setIssueDate(personalInfo.getCidIssueDate());
//			CVRSDQ0001Request.setNoOfEmployee(personalInfo.getN);
			CVRSDQ0001Request.setPosition(cisCustomer.getPosCd());
			CVRSDQ0001Request.setSalary(getSalaryCode(personalInfo.getTotVerifiedIncome()));
			CVRSDQ0001Request.setThaiFirstName(personalInfo.getThFirstName());
			CVRSDQ0001Request.setThaiLastName(personalInfo.getThLastName());
			CVRSDQ0001Request.setThaiMiddleName(personalInfo.getThMidName());
			CVRSDQ0001Request.setBusinessCode(cisCustomer.getKbnkIdyClCd());
			CVRSDQ0001Request.setEmail(personalInfo.getEmailPrimary());
			
			if(PROFESSION_OTHER.equals(professionCode)){
				String professionOther = personalInfo.getProfessionOther();
				if(!ServiceUtil.empty(CisApplicantUtil.mappingProfessionOther(personalInfo.getProfession()))){
					professionOther = CisApplicantUtil.mappingProfessionOther(personalInfo.getProfession());
				}
				CVRSDQ0001Request.setProfessionOther(professionOther);
			}
			ArrayList<AddressDataM> addresses=personalInfo.getAddresses();
//			ArrayList<CVRSDQ0001AddressDataM> requestOtherAddrList= new ArrayList<CVRSDQ0001AddressDataM>();
			
			ArrayList<CVRSDQ0001ContactDataM> contacts = new ArrayList<CVRSDQ0001ContactDataM>();
			
			CVRSDQ0001ContactDataM requestEmailDataM = new CVRSDQ0001ContactDataM();
			requestEmailDataM.setEmail(personalInfo.getEmailPrimary());
			contacts.add(requestEmailDataM);
					
			if(null!=cisTelephones && cisTelephones.size()>0){
				for(CISTelephoneDataM cisTelephone : cisTelephones){
					if(ADDRESS_TYPE_WORK.equals(cisTelephone.getPhoneLocation())){
						CVRSDQ0001ContactDataM requestContactDataM = new CVRSDQ0001ContactDataM();
						requestContactDataM.setTelephoneNumber(cisTelephone.getPhoneNo());
						requestContactDataM.setExt(cisTelephone.getPhoneExt());
						requestContactDataM.setFax(cisTelephone.getFax());
						requestContactDataM.setLocation(CIS_LOCATION_OFFICE);
						contacts.add(requestContactDataM);
					}else if(ADDRESS_TYPE_CURRENT.equals(cisTelephone.getPhoneLocation())){
						CVRSDQ0001ContactDataM requestContactDataM = new CVRSDQ0001ContactDataM();
						requestContactDataM.setTelephoneNumber(cisTelephone.getPhoneNo());
						requestContactDataM.setExt(cisTelephone.getPhoneExt());
						requestContactDataM.setFax(cisTelephone.getFax());
						requestContactDataM.setLocation(CIS_LOCATION_HOME);
						contacts.add(requestContactDataM);
					}else{
						if(null!=personalInfo.getMobileNo() && !"".equals(personalInfo.getMobileNo())){
							CVRSDQ0001ContactDataM requestMobileContactDataM = new CVRSDQ0001ContactDataM();
							requestMobileContactDataM.setMobileNo(personalInfo.getMobileNo());
							contacts.add(requestMobileContactDataM);
						}
					}
				}
			}
			
			CVRSDQ0001Request.setContacts(contacts);
			
			if(null!=addresses){ 
				for(AddressDataM address :  addresses){
					if(ADDRESS_TYPE_WORK.equals(address.getAddressType())){
					CVRSDQ0001AddressDataM requestWorkAddress = new CVRSDQ0001AddressDataM();
					requestWorkAddress.setAddressType(address.getAddressType());
					requestWorkAddress.setCountryCode(address.getCountry());
					requestWorkAddress.setDistrict(address.getTambol());
					requestWorkAddress.setAmphurCode(address.getAmphur());
					requestWorkAddress.setBuilding(address.getBuilding());
					requestWorkAddress.setFloor(address.getFloor());
					requestWorkAddress.setMoo(address.getMoo());
					requestWorkAddress.setAddressNo(address.getAddress());
					requestWorkAddress.setPostcode(address.getZipcode());
					requestWorkAddress.setProvince(address.getProvinceDesc());
					requestWorkAddress.setRoad(address.getRoad());
					requestWorkAddress.setRoom(address.getRoom());
					requestWorkAddress.setSoi(address.getSoi());
					requestWorkAddress.setVillage(address.getVilapt());
					requestWorkAddress.setAddressLine1(address.getAddress1());
					requestWorkAddress.setAddressLine2(address.getAddress2());
					String titleCompany= ServiceCache.getName(FIELD_ID_COMPANY_TITLE, "CHOICE_NO", address.getCompanyTitle(), "MAPPING4");
					requestWorkAddress.setAddressName(ServiceUtil.empty(titleCompany)?address.getCompanyName():titleCompany+""+address.getCompanyName());
					CVRSDQ0001Request.setWorkAddress(requestWorkAddress);
				}else if(ADDRESS_TYPE_CURRENT.equals(address.getAddressType())){
					CVRSDQ0001AddressDataM requestContactAddress = new CVRSDQ0001AddressDataM();
					requestContactAddress.setAddressType(address.getAddressType());
					requestContactAddress.setCountryCode(address.getCountry());
					requestContactAddress.setDistrict(address.getTambol());
					requestContactAddress.setAmphurCode(address.getAmphur());
					requestContactAddress.setBuilding(address.getBuilding());
					requestContactAddress.setFloor(address.getFloor());
					requestContactAddress.setMoo(address.getMoo());
					requestContactAddress.setAddressNo(address.getAddress());
					requestContactAddress.setPostcode(address.getZipcode());
					requestContactAddress.setProvince(address.getProvinceDesc());
					requestContactAddress.setRoad(address.getRoad());
					requestContactAddress.setRoom(address.getRoom());
					requestContactAddress.setSoi(address.getSoi());
					requestContactAddress.setVillage(address.getVilapt());
					requestContactAddress.setAddressLine1(address.getAddress1());
					requestContactAddress.setAddressLine2(address.getAddress2());
					CVRSDQ0001Request.setContactAddress(requestContactAddress);
				}else if(ADDRESS_TYPE_DOCUMENT.equals(address.getAddressType())){
					
					logger.debug("address country : "+address.getCountry());
					logger.debug("CidType : "+personalInfo.getCidType());
					logger.debug("isNewCustomer : "+isNewCustomer);
					// CR75 : If country of registration address <> Thai, no sending this address to CIS.
					if((COUNTRY_TH.equals(address.getCountry()))
							|| (!COUNTRY_TH.equals(address.getCountry()) && isNewCustomer)) {
						
						CVRSDQ0001AddressDataM requestRegisAddress = new CVRSDQ0001AddressDataM();
						if(isNewCustomer && CIDTYPE_PASSPORT.equals(personalInfo.getCidType())) {
							// CR75 : Registration address: To send **ONLY** country field by sending nationality to country field
							requestRegisAddress.setCountryCode(personalInfo.getNationality());
						} else {
							requestRegisAddress.setAddressType(address.getAddressType());
							requestRegisAddress.setCountryCode(address.getCountry());
							requestRegisAddress.setDistrict(address.getTambol());
							requestRegisAddress.setAmphurCode(address.getAmphur());
							requestRegisAddress.setBuilding(address.getBuilding());
							requestRegisAddress.setFloor(address.getFloor());
							requestRegisAddress.setMoo(address.getMoo());
							requestRegisAddress.setAddressNo(address.getAddress());
							requestRegisAddress.setPostcode(address.getZipcode());
							requestRegisAddress.setProvince(address.getProvinceDesc());
							requestRegisAddress.setRoad(address.getRoad());
							requestRegisAddress.setRoom(address.getRoom());
							requestRegisAddress.setSoi(address.getSoi());
							requestRegisAddress.setVillage(address.getVilapt());
							requestRegisAddress.setAddressLine1(address.getAddress1());
							requestRegisAddress.setAddressLine2(address.getAddress2());
						}
						CVRSDQ0001Request.setRegistrationAddress(requestRegisAddress);
					}
				}/*else{
					CVRSDQ0001AddressDataM requestOtherAddress = new CVRSDQ0001AddressDataM();
					requestOtherAddress.setAddressType(address.getAddressType());
					requestOtherAddress.setCountryCode(address.getCountry());
					requestOtherAddress.setDistrict(address.getTambol());
					requestOtherAddress.setAmphurCode(address.getAmphur());
					requestOtherAddress.setBuilding(address.getBuilding());
					requestOtherAddress.setFloor(address.getFloor());
					requestOtherAddress.setMoo(address.getMoo());
					requestOtherAddress.setAddressNo(address.getAddress());
					requestOtherAddress.setPostcode(address.getZipcode());
					requestOtherAddress.setProvince(address.getProvinceDesc());
					requestOtherAddress.setRoad(address.getRoad());
					requestOtherAddress.setRoom(address.getRoom());
					requestOtherAddress.setSoi(address.getSoi());
					requestOtherAddress.setVillage(address.getVilapt());
					requestOtherAddrList.add(requestOtherAddress);
				}*/
				
			}
		}
//		CVRSDQ0001Request.setOtherAddresses(requestOtherAddrList);
				
		ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
		serviceRequest.setEndpointUrl(ServiceCache.getProperty("CVRSDQ0001_ENDPOINT_URL"));
		serviceRequest.setServiceId(CVRSDQ0001ServiceProxy.serviceId);
		serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
		serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
		serviceRequest.setUserId(userId);
		serviceRequest.setObjectData(CVRSDQ0001Request);
		serviceRequest.setServiceData(personalRefData);
		serviceResponse  = proxy.requestService(serviceRequest,requestTransactionId);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(CVRSDQ0001ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceResponse;
	}
	
	public ServiceResponseDataM  callCIS1044U01(PersonalInfoDataM personalInfo,ApplicationGroupDataM applicationGroup,String userId,CISCustomerDataM cisCustomer,CISRefDataObject personalRefData, String department){
		ServiceResponseDataM serviceResponse  = new ServiceResponseDataM();
		
		try {
			CIS1044U01RequestDataM CIS1044U01Request = new CIS1044U01RequestDataM();	
			CIS1044U01Request.setBranchNo(department);
			CIS1044U01Request.setHubNo(CIS_DEFAULT_SERVICE_HUB_NUMBER);
			CIS1044U01Request.setUserId(userId);
			CIS1044U01Request.setProspectFlag(cisCustomer.getPrvnF()); 
			CIS1044U01Request.setCustomerId(getCustomerId(personalInfo.getCisNo()));
			CIS1044U01Request.setMultipleContactChannel(cisCustomer.getPrefCtcMthCd());
			String thTitleName ="";
			if(TITLE_OTHER.equals(personalInfo.getThTitleCode())){
				thTitleName=personalInfo.getThTitleDesc();
			}else{
				thTitleName=ServiceCache.getName(FIELD_ID_TH_TITLE_CODE, personalInfo.getThTitleCode(), "MAPPING4");
			}
			CIS1044U01Request.setThTitle(thTitleName);
			CIS1044U01Request.setThFstName(personalInfo.getThFirstName());
			CIS1044U01Request.setThMidName(personalInfo.getThMidName());
			CIS1044U01Request.setThLstName(personalInfo.getThLastName());
			String engTitleName = "";
			if(TITLE_OTHER.equals(personalInfo.getEnTitleCode())){
				engTitleName=personalInfo.getEnTitleDesc();
			}else{
				engTitleName=ServiceCache.getName(FIELD_ID_EN_TITLE_CODE, personalInfo.getEnTitleCode(), "MAPPING4");
			}
			CIS1044U01Request.setEngTitle(engTitleName);
			CIS1044U01Request.setEngFstName(personalInfo.getEnFirstName());
			CIS1044U01Request.setEngMidName(personalInfo.getEnMidName());
			CIS1044U01Request.setEngLstName(personalInfo.getEnLastName());
			CIS1044U01Request.setCustTypeCode(getCustomerTypeCode(personalInfo,cisCustomer));
			/*INC1058114 don't do anything to this field value
			CIS1044U01Request.setSvcBrchCode(getOwnerBranchNumber(cisCustomer.getDomcBrNo()));
			*/
			CIS1044U01Request.setSvcBrchCode(cisCustomer.getDomcBrNo());
			CIS1044U01Request.setDocTypeCode(ServiceCache.getName(FIELD_ID_CID_TYPE, personalInfo.getCidType(), "MAPPING4"));
			CIS1044U01Request.setDocNum(personalInfo.getIdno() );
			CIS1044U01Request.setIdCrdExpDate(personalInfo.getCidExpDate());
			CIS1044U01Request.setIdCrdIssuDate(personalInfo.getCidIssueDate());
			CIS1044U01Request.setIdCrdIssuPlaceDesc(cisCustomer.getGovtIssur());

			if(personalInfo.getIdno().equals(cisCustomer.getIdentNo()) && cisCustomer.getExpDt().contains(ServiceUtil.toString(personalInfo.getCidExpDate()))){
				CIS1044U01Request.setIdCrdDesc(personalInfo.getIdno()+cisCustomer.getIdentNo());
			}
			
			CIS1044U01Request.setBirthDate(personalInfo.getBirthDate());
			CIS1044U01Request.setTaxNum(cisCustomer.getIpTaxId());
			AddressDataM documentAddress = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
			if(ServiceUtil.empty(documentAddress)){
				documentAddress = new AddressDataM();
			}
		
			String cisDocAddressType = ServiceCache.getName(FIELD_ID_ADDRESS_TYPE, documentAddress.getAddressType(), "MAPPING4");
			CISAddressDataM cisDocAddress=cisCustomer.selectCISAddresType(cisDocAddressType);
			if(null!=cisDocAddress){
				CIS1044U01Request.setOfficialAddressCountry(cisDocAddress.getCountry() );
			}else{
				logger.debug("CIS DOC ADDRESS IS NULL");
			}
		
			
			AddressDataM contactAddress = personalInfo.getAddress(personalInfo.getMailingAddress());
			if(ServiceUtil.empty(contactAddress)){
				contactAddress = new AddressDataM();
			}
			String cisContactddressType = ServiceCache.getName(FIELD_ID_ADDRESS_TYPE, contactAddress.getAddressType(), "MAPPING4");
			CISAddressDataM cisContactAddress=cisCustomer.selectCISAddresType(cisContactddressType);
			if(null!=cisContactAddress){
				CIS1044U01Request.setContactAddressCountry(cisContactAddress.getCountry());
			}
			CIS1044U01Request.setGender(ServiceCache.getName(FIELD_ID_GENDER, personalInfo.getGender(), "MAPPING4"));
			CIS1044U01Request.setMaritalStatus(ServiceCache.getName(FIELD_ID_MARRIED, personalInfo.getMarried(), "MAPPING4") );
			CIS1044U01Request.setReligion(cisCustomer.getRlgCd());
			CIS1044U01Request.setOccupation(ServiceCache.getName(FIELD_ID_OCCUPATION, personalInfo.getOccupation(), "MAPPING4") );
			CIS1044U01Request.setJobPosition(cisCustomer.getPosCd() );
			logger.debug("cisCustomer.getKbnkIdyClCd() >> " + cisCustomer.getKbnkIdyClCd());
			CIS1044U01Request.setBusinessTypeCode(cisCustomer.getKbnkIdyClCd());
			CIS1044U01Request.setSalary(null==personalInfo.getSalary()?"":ServiceUtil.toString(personalInfo.getSalary()));
			CIS1044U01Request.setStartWorkDate(ServiceUtil.toDate(cisCustomer.getOcpDt(), ServiceUtil.EN, ServiceUtil.Format.yyyy_MM_dd));
			CIS1044U01Request.setDegree(ServiceCache.getName(FIELD_ID_DEGREE, personalInfo.getDegree(), "MAPPING4"));
			CIS1044U01Request.setNationality(ServiceCache.getName(FIELD_ID_NATIONALITY, personalInfo.getNationality(), "MAPPING4"));
			CIS1044U01Request.setRace(cisCustomer.getRaceCd());
//			CIS1044U01Request.setBookNumberForChangeName(book_number );
//			CIS1044U01Request.setSequenceNumberForChangeName(seq_number );
//			CIS1044U01Request.setTheDateChangeName(DataFormat.stringToDate(change_name_date));
			// *Remove CR219
//			if(ApplicationUtil.eApp(applicationGroup.getSource())){
//				//if is eapp set blank value
//				CIS1044U01Request.setConsendFlag("");
//			}
//			else{
//				CIS1044U01Request.setConsendFlag((personalInfo.getDisclosureFlag()!=null?personalInfo.getDisclosureFlag():"N"));//(personalInfo.getDisclosureFlag()!=null?personalInfo.getDisclosureFlag():"N")
//			}
			if(!ServiceUtil.empty(personalInfo.getDisclosureFlag()) && !"0".equals(personalInfo.getDisclosureFlag())){
				CIS1044U01Request.setConsendFlag(personalInfo.getDisclosureFlag());
			}
			CIS1044U01Request.setSourceOfConsent(cisCustomer.getConsndSrcStmCd() );
			SaleInfoDataM saleBranch = applicationGroup.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
			if(null!=saleBranch){
				CIS1044U01Request.setKycBranch(getKYCBranchNumber(saleBranch.getSalesBranchCode()));
			}
			CIS1044U01Request.setCompleteDocumentFlag(CIS_COMPLETE_DOCUMENT_DEFAULT_FLAG );
			CIS1044U01Request.setCompleteKYCDocumentFlag(CIS_COMPLETE_KYC_DOCUMENT_DEFAULT_FLAG) ;
			
			String professionCode = ServiceCache.getName(FIELD_ID_PROFESSION, personalInfo.getProfession(), "MAPPING4");
			if(!ServiceUtil.empty(CisApplicantUtil.mappingProfessionRiskGroup(personalInfo.getBusinessType()))){
				professionCode = CisApplicantUtil.mappingProfessionRiskGroup(personalInfo.getBusinessType());
			}
			CIS1044U01Request.setProfessionCode(ServiceUtil.empty(professionCode) ? null : professionCode);
			
			if(PROFESSION_OTHER.equals(professionCode)){
				String professionOther = personalInfo.getProfessionOther();
				if(!ServiceUtil.empty(CisApplicantUtil.mappingProfessionOther(personalInfo.getProfession()))){
					professionOther = CisApplicantUtil.mappingProfessionOther(personalInfo.getProfession());
				}
				CIS1044U01Request.setOthProfessDesc(null == professionOther ? "" : professionOther);
			}
			CIS1044U01Request.setNumberOfEmployee(ServiceUtil.getInt(cisCustomer.getNoOfEmp()));
			CIS1044U01Request.setAssetExcludeLand(ServiceUtil.toBigDecimal( cisCustomer.getAstExcldLand()));
//			CIS1044U01Request.setMultipleContactChannel(multi_contact_chanel);
			if(null!=personalInfo.getNoOfChild()){
				CIS1044U01Request.setAmountOfChildren(Integer.valueOf(String.valueOf(personalInfo.getNoOfChild())));
			}
			CIS1044U01Request.setAgeOfOldestChild(ServiceUtil.differenceYear(ServiceUtil.toDate(cisCustomer.getOldstDpndBrthDt(), ServiceUtil.EN, ServiceUtil.Format.yyyy_MM_dd),ServiceUtil.getCurrentDate()));
			CIS1044U01Request.setAgeOfYoungestChild(ServiceUtil.differenceYear(ServiceUtil.toDate(cisCustomer.getYngstDpndBrthDt(), ServiceUtil.EN, ServiceUtil.Format.yyyy_MM_dd),ServiceUtil.getCurrentDate()));
			CIS1044U01Request.setFamilyIncomeRange(cisCustomer.getFamIncmSegCd());
			CIS1044U01Request.setCustomerSegment(cisCustomer.getPrimSegCd());
			CIS1044U01Request.setTitleForcedSaveFlag(CIS_TITLE_FORCED_SAVE_DEFAULT_FLAG);
//			CIS1044U01Request.setAddrOffCntctSameCde(flag_same_address);
//			CIS1044U01Request.setVipFlag(vip_flag);
			CIS1044U01Request.setDeathFlag(cisCustomer.getDeathF());
			CIS1044U01Request.setDeathDate(ServiceUtil.toDate(cisCustomer.getDeathDt(), ServiceUtil.EN, ServiceUtil.Format.yyyy_MM_dd));
			CIS1044U01Request.setCustomerType(getCustomerType(personalInfo));
			logger.debug("personalInfo.getTotVerifiedIncome() >> " +personalInfo.getTotVerifiedIncome());
			CIS1044U01Request.setSalary(getSalaryCode(personalInfo.getTotVerifiedIncome()));
			logger.debug("CIS1044U01Request.getSalary() >> " +CIS1044U01Request.getSalary());
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
			serviceRequest.setEndpointUrl(ServiceCache.getProperty("CIS1044U01_ENDPOINT_URL"));
			serviceRequest.setServiceId(CIS1044U01ServiceProxy.serviceId);
			serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
			serviceRequest.setUserId(userId);
			serviceRequest.setObjectData(CIS1044U01Request);
			serviceRequest.setServiceData(personalRefData);
			serviceResponse  = proxy.requestService(serviceRequest,requestTransactionId);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(CIS1044U01ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceResponse;
	}
	
	public ServiceResponseDataM  callCIS1047O01(PersonalInfoDataM personalInfo,ApplicationGroupDataM applicationGroup,String userId,String dataType,CISTelephoneDataM cisTelephoneData,CISRefDataObject personalRefData, String department){
		ServiceResponseDataM serviceResponse  = new ServiceResponseDataM();
		serviceResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		
		try {
			CIS1047O01RequestDataM CIS1047O01Request = new CIS1047O01RequestDataM();				 
			CIS1047O01Request.setUserId(userId);
			CIS1047O01Request.setBranchNo(department);
			
			AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
			if(null==currentAddress){
				currentAddress = new AddressDataM();
			}
			
			AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
			if(null==workAddress){
				workAddress = new AddressDataM();
			}
			if(ADDRESS_TYPE_CURRENT.equals(cisTelephoneData.getPhoneLocation())){
				CIS1047O01Request.setContactAddressNum(getContactIdFormat(currentAddress.getPhoneIdRef()));
			}else if(ADDRESS_TYPE_WORK.equals(cisTelephoneData.getPhoneLocation())){
				CIS1047O01Request.setContactAddressNum(getContactIdFormat(workAddress.getPhoneIdRef()));
			}
			
			
			if(CIS_DATA_TYPE_EMAIL.equals(dataType)){
				CIS1047O01Request.setContactAddressNum(getContactIdFormat(personalInfo.getEmailIdRef()));
				CIS1047O01Request.setContactTypeCode(CIS_CONTACT_EMAIL_TYPE_CODE);
			}else if (CIS_DATA_TYPE_FAX.equals(dataType)){				
				CIS1047O01Request.setContactTypeCode(CIS_CONTACT_FAX_TYPE_CODE);
				dataType=CIS_DATA_TYPE_EMAIL;
			}else{
				if(ADDRESS_TYPE_CURRENT.equals(cisTelephoneData.getPhoneLocation()) || ADDRESS_TYPE_WORK.equals(cisTelephoneData.getPhoneLocation())){
					CIS1047O01Request.setContactTypeCode(CIS_LAND_LINE_PHONE_CODE);
				}else if(CIS_MOBILE_PHONE_CODE.equals(cisTelephoneData.getPhoneType())){
					CIS1047O01Request.setContactAddressNum(getContactIdFormat(personalInfo.getMobileIdRef()));
					CIS1047O01Request.setContactTypeCode(CIS_CONTACT_MOBILE_PHONE_TYPE_CODE);
				}	
			}

			CIS1047O01Request.setContactFunctionTypeCode(CIS_FUNC_TYPE);		
			CIS1047O01Request.setCustomerId(getCustomerId(personalInfo.getCisNo()));
			CIS1047O01Request.setDataTypeCode(dataType);
			CIS1047O01Request.setHubNo(CIS_DEFAULT_SERVICE_HUB_NUMBER);
			
			if(!ServiceUtil.empty(CIS1047O01Request.getContactAddressNum())){
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(ServiceCache.getProperty("CIS1047O01_ENDPOINT_URL"));
				serviceRequest.setServiceId(CIS1047O01ServiceProxy.serviceId);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setUserId(userId);
				serviceRequest.setObjectData(CIS1047O01Request);
				serviceRequest.setServiceData(personalRefData);
			serviceResponse = proxy.requestService(serviceRequest,requestTransactionId);
			}

		} catch (Exception e) {
			logger.fatal("ERROR",e);
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(CIS1047O01ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceResponse;
	}
	
	public ServiceResponseDataM  callCIS1048O01(PersonalInfoDataM personalInfo,ApplicationGroupDataM applicationGroup,CISAddressDataM cisDocAddress,String userId,CISRefDataObject personalRefData, String department){
		ServiceResponseDataM serviceResponse  = new ServiceResponseDataM();
		
		try {
			CIS1048O01RequestDataM CIS1048O01Request = new CIS1048O01RequestDataM();	
			CIS1048O01Request.setBranchNo(department);
			CIS1048O01Request.setContactAddressId(getAddressIdFormat(cisDocAddress.getAdrId()));
			ArrayList<CIS1048O01AddressDataM> CIS1048O01AddressTypes = new ArrayList<CIS1048O01AddressDataM>();
			if(null!=cisDocAddress){
				CIS1048O01AddressDataM CIS1048O01addressType = new CIS1048O01AddressDataM();
				CIS1048O01addressType.setAddressType(cisDocAddress.getAddressType());
				CIS1048O01AddressTypes.add(CIS1048O01addressType);
			}
			
			CIS1048O01Request.setContactAddressTypes(CIS1048O01AddressTypes);
			CIS1048O01Request.setCustomerId(getCustomerId(personalInfo.getCisNo()));
			CIS1048O01Request.setCustomerType(getCustomerType(personalInfo));
			CIS1048O01Request.setHubNo(CIS_DEFAULT_SERVICE_HUB_NUMBER);
			CIS1048O01Request.setUserId(userId);
					
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(ServiceCache.getProperty("CIS1048O01_ENDPOINT_URL"));
				serviceRequest.setServiceId(CIS1048O01ServiceProxy.serviceId);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setUserId(userId);
				serviceRequest.setObjectData(CIS1048O01Request);
				serviceRequest.setServiceData(personalRefData);				
//				logger.debug("serviceRequest>>"+ new Gson().toJson(serviceRequest));
			serviceResponse= proxy.requestService(serviceRequest,requestTransactionId);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(CIS1048O01ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		return serviceResponse;
	}
		
	private  static boolean isExistingData(Object uloValue ,Object dihValue){
		logger.debug("uloValue>>>"+uloValue);
		logger.debug("dihValue>>>"+dihValue);
			if(ServiceUtil.empty(uloValue) && ServiceUtil.empty(dihValue)){
				return true;
			} else if((ServiceUtil.empty(uloValue) && !ServiceUtil.empty(dihValue)) 
					|| (!ServiceUtil.empty(uloValue) && ServiceUtil.empty(dihValue))) {
				return false;
			} else if(uloValue instanceof String && dihValue instanceof String){
				if(!uloValue.equals(dihValue)){
					return false;
				}
			}else if(uloValue instanceof BigDecimal && dihValue instanceof BigDecimal){
				BigDecimal uloValue1 = (BigDecimal) uloValue;
				BigDecimal dihValue2 = (BigDecimal) dihValue;
				if(uloValue1.compareTo(dihValue2) != 0){
					return false;
				}
			}else if(uloValue instanceof Timestamp && dihValue instanceof Timestamp){
				if(!uloValue.equals(dihValue)){
					return false;
				}
			}else if(uloValue instanceof Date && dihValue instanceof Date){
				if(!uloValue.equals(dihValue)){
					return false;
				}
			} else {
				if(!uloValue.equals(dihValue)){
					return false;
				}
			}
		return true;
	}
	
	private boolean isExistingContact(ArrayList<CISTelephoneDataM> uloFilterPhoneCISList,CISCustomerDataM cisCustomer ,String addrStatusCode){
		boolean isExisting = false;
		if(null!=cisCustomer){
			if(null==cisCustomer.getElcAddresses() || cisCustomer.getElcAddresses().size()==0){
				return false;
			}
			if(null!=uloFilterPhoneCISList && uloFilterPhoneCISList.size()>0){
				for(CISTelephoneDataM telPhone:uloFilterPhoneCISList){
					CISelcAddressDataM elcAddr=cisCustomer.selectCISelcAddresType(telPhone.getPhoneType(),telPhone.getPhoneLocation());
					if(null!=elcAddr  && addrStatusCode.equals(elcAddr.getIpXElcAdrRelStCd())){
						if(!isExistingData(telPhone.getPhoneNo(), elcAddr.getTelNo()) || !isExistingData(telPhone.getPhoneExt(), elcAddr.getTelExnNo())){
							return false;
						}else{
							isExisting = true;
						}
					}
				}
			}
		}
		return isExisting;
	}
	
	public String  isNewAddress(AddressDataM uloAddress,ArrayList<CISAddressDataM> cisAddresses){
		String cisAddressType = ServiceCache.getName(FIELD_ID_ADDRESS_TYPE, "CHOICE_NO", uloAddress.getAddressType(), "MAPPING4"); 
		logger.debug("cisAddressType>>"+cisAddressType);
		ArrayList<String> cisAddressTypes =new ArrayList<String>();
		if(null!=cisAddresses && cisAddresses.size()>0 ){
			for(CISAddressDataM cisAddress : cisAddresses){
				cisAddressTypes.add(cisAddress.getAddressType());
				if(cisAddressType.equals(cisAddress.getAddressType())){
					if(!isExistingData(uloAddress.getAddress(), cisAddress.getAddressNo())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getAmphur(), cisAddress.getAmphur())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getBuilding(), cisAddress.getBuilding())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getCompanyName(), cisAddress.getCompanyName())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getCountry(), cisAddress.getCountry())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getFloor(), cisAddress.getFloor())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getMoo(), cisAddress.getMoo())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getProvinceDesc(), cisAddress.getProvince())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getRoad(), cisAddress.getRoad())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getRoom(), cisAddress.getRoom())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getSoi(), cisAddress.getSoi())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getTambol(), cisAddress.getTambol())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getVilapt(), cisAddress.getVilapt())){
						return EXISTING_NOT_SAME_DATA;
					}
					if(!isExistingData(uloAddress.getZipcode(), cisAddress.getZipCode())){
						return EXISTING_NOT_SAME_DATA;
					}
				}
			}
		}
		if(!cisAddressTypes.contains(cisAddressType)){
			return NEW_DATA;
		}
		return EXISTING_SAME_DATA;
	}
	
	public ServiceErrorInfo error(String serviceId,String errorType,Exception e){	
		ServiceErrorInfo errorData = new ServiceErrorInfo();
		errorData.setServiceId(serviceId);
		errorData.setErrorType(errorType);
		errorData.setErrorInformation(e.getLocalizedMessage());
		errorData.setErrorDesc(e.getLocalizedMessage());
		errorData.setErrorCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
		errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
		return errorData;
	}
}
