package com.eaf.service.module.manual;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.CVRSDQ0001AddressDataM;
import com.eaf.service.module.model.CVRSDQ0001ContactDataM;
import com.eaf.service.module.model.CVRSDQ0001RequestDataM;
import com.eaf.service.module.model.CVRSDQ0001ResponseDataM;
import com.eaf.service.module.model.ResponseValidateResultDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.dq.ws.Address;
import com.kasikornbank.dq.ws.ContactEmail;
import com.kasikornbank.dq.ws.ContactEmailList;
import com.kasikornbank.dq.ws.ContactNumber;
import com.kasikornbank.dq.ws.ContactNumberList;
import com.kasikornbank.dq.ws.ContactType;
import com.kasikornbank.dq.ws.CustomerProfile;
import com.kasikornbank.dq.ws.DataQualityTemplateRequest;
import com.kasikornbank.dq.ws.DataQualityTemplateResponse;
import com.kasikornbank.dq.ws.DataQualityTemplateWebServicePortProxy;
import com.kasikornbank.dq.ws.DqError;
import com.kasikornbank.dq.ws.DqField;
import com.kasikornbank.dq.ws.Header;
import com.kasikornbank.dq.ws.LocationType;
import com.kasikornbank.dq.ws.Name;
import com.kasikornbank.dq.ws.OthetAddressList;

public class CVRSDQ0001ServiceProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(CVRSDQ0001ServiceProxy.class);
	public static final String serviceId = "CVRSDQ0004";
	public static final String url = "urlWebService";
	String[] BUSINESS_TYPE_RISK_GROUP = ServiceCache.getArrayConstant("BUSINESS_TYPE_RISK_GROUP");
	String FIELD_ID_PROFESSION = ServiceCache.getConstant("FIELD_ID_PROFESSION"); 
	String FIELD_ID_BUSINESS_TYPE = ServiceCache.getConstant("FIELD_ID_BUSINESS_TYPE"); 
	
	@Override
	public ServiceRequestTransaction requestTransaction() {
		CVRSDQ0001RequestDataM requetData = (CVRSDQ0001RequestDataM)serviceRequest.getObjectData();		
		DataQualityTemplateRequest requestDataQualityObject = new DataQualityTemplateRequest();
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();	
		Header requestHeader = new Header();
		requestHeader.setServiceId(serviceId);
		requestHeader.setBeginTimestamp(ServiceApplicationDate.dateToXMLGregorianCalendar(ServiceApplicationDate.getDate()));
		requestHeader.setEndTimestamp(ServiceApplicationDate.dateToXMLGregorianCalendar(ServiceApplicationDate.getDate()));
		requestHeader.setTransactionId(serviceRequest.getTransactionId());
		requestHeader.setStatus(ServiceResponse.Status.EAI_SUCCESS_CODE);
		requestHeader.setSourceSystem(ServiceCache.getConstant("DEFAULT_SOURCE_SYSTEM"));
		requestHeader.setSourceConfig(ServiceCache.getConstant("CVRSDQ0001_TEMPLATE_NAME"));
		requestDataQualityObject.setHeader(requestHeader);
		
		CustomerProfile  requestCustomerProfile = new CustomerProfile();
		Name requestNameThai = new Name();
		requestNameThai.setTitleName(requetData.getThaiTitleName());
		requestNameThai.setFistName(requetData.getThaiFirstName());
		requestNameThai.setLastName(requetData.getThaiLastName());
		requestNameThai.setMiddleName(requetData.getThaiMiddleName());
		requestCustomerProfile.setNameThai(requestNameThai);
 
		Name requestNameEng = new Name();
		requestNameEng.setTitleName(requetData.getEngTitleName());
		requestNameEng.setFistName(requetData.getEngFirstName());
		requestNameEng.setLastName(requetData.getEngLastName());
		requestNameEng.setMiddleName(requetData.getEngMiddleName());
		requestCustomerProfile.setNameEng(requestNameEng);		
		requestCustomerProfile.setGender(requetData.getGender());
		requestCustomerProfile.setMarriageStatus(requetData.getMarriageStatus());
		requestCustomerProfile.setCustomerType(requetData.getCustomerType());
		requestCustomerProfile.setCustomerTypeCode(requetData.getCustomerTypeCode());
		requestCustomerProfile.setDateOfBirthOrRegistrationDate(ServiceUtil.displayDateFormat(requetData.getDateOfBirth(),ServiceUtil.EN,ServiceUtil.Format.ddMMyyyy));
		requestCustomerProfile.setDocumentType(requetData.getDocumentType());
		requestCustomerProfile.setIssueBy(requetData.getIssueBy());
		requestCustomerProfile.setIssueDate(ServiceUtil.displayDateFormat(requetData.getIssueDate(),ServiceUtil.EN,ServiceUtil.Format.ddMMyyyy));	
		requestCustomerProfile.setExpireDate(ServiceUtil.displayDateFormat(requetData.getExpireDate(),ServiceUtil.EN,ServiceUtil.Format.ddMMyyyy));
		requestCustomerProfile.setNationalityCode(requetData.getNationality());
		requestCustomerProfile.setRaceCode(requetData.getRace());
		requestCustomerProfile.setOccupationCode(requetData.getOccupation());
		requestCustomerProfile.setProfessionCode(requetData.getProfession());		
		requestCustomerProfile.setSalary(requetData.getSalary());
		requestCustomerProfile.setIdCardNoOrRegistrationNo(requetData.getIdNo());
		requestCustomerProfile.setPosition(requetData.getPosition());
		requestCustomerProfile.setBusinessCode(requetData.getBusinessCode());
		requestCustomerProfile.setProfessionOther(ServiceUtil.removeAllSpecialCharactors(requetData.getProfessionOther()));
		
		CVRSDQ0001AddressDataM regisAddress =requetData.getRegistrationAddress();
		if(null!=regisAddress){
			Address requestRegisAddress = new  Address();
			requestRegisAddress.setCountryCode(regisAddress.getCountryCode());
			requestRegisAddress.setDistrictCode(regisAddress.getDistrict());
			requestRegisAddress.setAmphurCode(regisAddress.getAmphurCode());
			requestRegisAddress.setProvinceCode(regisAddress.getProvince());
			requestRegisAddress.setPostcode(regisAddress.getPostcode());
			requestRegisAddress.setPoBox(regisAddress.getPoBox());
			requestRegisAddress.setPlace(regisAddress.getAddressName());
			requestRegisAddress.setNumber(regisAddress.getAddressNo());
			requestRegisAddress.setMoo(regisAddress.getMoo());
			requestRegisAddress.setSoi(regisAddress.getSoi());
			requestRegisAddress.setBuilding(regisAddress.getBuilding());
			requestRegisAddress.setVillage(regisAddress.getVillage());
			requestRegisAddress.setFloor(regisAddress.getFloor());
			requestRegisAddress.setRoom(regisAddress.getRoom());
			requestRegisAddress.setRoad(regisAddress.getRoad());	
			requestRegisAddress.setAddressLine1(regisAddress.getAddressLine1());
			requestRegisAddress.setAddressLine2(regisAddress.getAddressLine2());
			requestCustomerProfile.setRegistrationAddress(requestRegisAddress);
			
		}
		
		CVRSDQ0001AddressDataM contactAddress = requetData.getContactAddress();
		if(null!=contactAddress){
			Address requestContactAddress = new Address();
			requestContactAddress.setCountryCode(contactAddress.getCountryCode());
			requestContactAddress.setDistrictCode(contactAddress.getDistrict());
			requestContactAddress.setAmphurCode(contactAddress.getAmphurCode());
			requestContactAddress.setProvinceCode(contactAddress.getProvince());
			requestContactAddress.setPostcode(contactAddress.getPostcode());
			requestContactAddress.setPoBox(contactAddress.getPoBox());
			requestContactAddress.setPlace(contactAddress.getAddressName());
			requestContactAddress.setNumber(contactAddress.getAddressNo());
			requestContactAddress.setMoo(contactAddress.getMoo());
			requestContactAddress.setBuilding(contactAddress.getBuilding());
			requestContactAddress.setVillage(contactAddress.getVillage());
			requestContactAddress.setFloor(contactAddress.getFloor());
			requestContactAddress.setRoom(contactAddress.getRoom());
			requestContactAddress.setSoi(contactAddress.getSoi());
			requestContactAddress.setRoad(contactAddress.getRoad());
			requestContactAddress.setAddressLine1(contactAddress.getAddressLine1());
			requestContactAddress.setAddressLine2(contactAddress.getAddressLine2());
			requestCustomerProfile.setContactAddress(requestContactAddress);
		}
		
		CVRSDQ0001AddressDataM workAddress =requetData.getWorkAddress();
		if(null!=workAddress){
			Address requestWorkAddress = new Address();
			requestWorkAddress.setCountryCode(workAddress.getCountryCode());
			requestWorkAddress.setDistrictCode(workAddress.getDistrict());
			requestWorkAddress.setAmphurCode(workAddress.getAmphurCode());
			requestWorkAddress.setProvinceCode(workAddress.getProvince());
			requestWorkAddress.setPostcode(workAddress.getPostcode());
			requestWorkAddress.setPoBox(workAddress.getPoBox());
			requestWorkAddress.setPlace(workAddress.getAddressName());
			requestWorkAddress.setNumber(workAddress.getAddressNo());
			requestWorkAddress.setMoo(workAddress.getMoo());
			requestWorkAddress.setBuilding(workAddress.getBuilding());
			requestWorkAddress.setVillage(workAddress.getVillage());
			requestWorkAddress.setFloor(workAddress.getFloor());
			requestWorkAddress.setRoom(workAddress.getRoom());
			requestWorkAddress.setSoi(workAddress.getSoi());
			requestWorkAddress.setRoad(workAddress.getRoad());
			requestWorkAddress.setAddressLine1(workAddress.getAddressLine1());
			requestWorkAddress.setAddressLine2(workAddress.getAddressLine2());
			requestCustomerProfile.setWorkAddress(requestWorkAddress);
		}
		
		ArrayList<CVRSDQ0001AddressDataM> otherAddresses = requetData.getOtherAddresses();
		if(null!=otherAddresses && otherAddresses.size()>0){
			for(CVRSDQ0001AddressDataM otherAddress :otherAddresses){
				Address reqOtherAdds =  new Address();
				reqOtherAdds.setCountryCode(otherAddress.getCountryCode());
				reqOtherAdds.setDistrictCode(otherAddress.getDistrict());
				reqOtherAdds.setAmphurCode(otherAddress.getAmphurCode());
				reqOtherAdds.setProvinceCode(otherAddress.getProvince());
				reqOtherAdds.setPostcode(otherAddress.getPostcode());
				reqOtherAdds.setPoBox(otherAddress.getPoBox());
				reqOtherAdds.setPlace(otherAddress.getAddressName());
				reqOtherAdds.setNumber(otherAddress.getAddressNo());
				reqOtherAdds.setMoo(otherAddress.getMoo());
				reqOtherAdds.setBuilding(otherAddress.getBuilding());
				reqOtherAdds.setVillage(otherAddress.getVillage());
				reqOtherAdds.setFloor(otherAddress.getFloor());
				reqOtherAdds.setRoom(otherAddress.getRoom());
				reqOtherAdds.setSoi(otherAddress.getSoi());
				reqOtherAdds.setRoad(otherAddress.getRoad());
				reqOtherAdds.setAddressLine1(otherAddress.getAddressLine1());
				reqOtherAdds.setAddressLine2(otherAddress.getAddressLine2());
				requestCustomerProfile.getOtherAddresses().add(reqOtherAdds);
			}
		}
			 
		ArrayList<CVRSDQ0001ContactDataM> contacts = requetData.getContacts();
		ArrayList<ContactNumber> requestContactNumbers = new ArrayList<ContactNumber>();
		ArrayList<ContactEmail> requestContactEmails = new ArrayList<ContactEmail>();
		
		if(null!=contacts && contacts.size()>0){
			for(int i=0;i<contacts.size();i++){
				CVRSDQ0001ContactDataM contact = contacts.get(i);
				LocationType requestLocationType = null;
				if(ServiceCache.getConstant("CIS_LOCATION_HOME").equals(contact.getLocation())){
					requestLocationType = LocationType.HOME;			
				}else if(CVRSDQ0001ContactDataM.CONTACT_TYPE.OFFICE.equals(contact.getLocation())){
					requestLocationType = LocationType.OFFICE;
				}
				if(null!=contact.getMobileNo() && !"".equals(contact.getMobileNo())){
					ContactNumber requestContactNumber = new ContactNumber();
					requestContactNumber.setLocation(requestLocationType);
					requestContactNumber.setNumber(contact.getMobileNo());
					requestContactNumber.setType(ContactType.MOBILE);
					requestContactNumbers.add(requestContactNumber);
				}
				
				if(null!=contact.getTelephoneNumber() && !"".equals(contact.getTelephoneNumber())){
					ContactNumber requestContactNumber = new ContactNumber();
					requestContactNumber.setLocation(requestLocationType);
					requestContactNumber.setNumber(contact.getTelephoneNumber());
					requestContactNumber.setExt(contact.getExt());
					requestContactNumber.setType(ContactType.TELEPHONE);
					requestContactNumbers.add(requestContactNumber);
				}
				
				if(null!=contact.getFax() && !"".equals(contact.getFax())){
					ContactNumber requestContactNumber = new ContactNumber();
					requestContactNumber.setLocation(requestLocationType);
					requestContactNumber.setNumber(contact.getFax());
					requestContactNumber.setExt(contact.getFaxExt());
					requestContactNumber.setType(ContactType.FAX);
					requestContactNumbers.add(requestContactNumber);
				}
				
				if(null!=contact.getEmail() && !"".equals(contact.getEmail())){
					ContactEmail requestContactEmail = new ContactEmail();
					requestContactEmail.setLocation(requestLocationType);
					requestContactEmail.setMail(contact.getEmail());					
					requestContactEmails.add(requestContactEmail);
				}
				
			}
			
			if(null!=requestContactNumbers && requestContactNumbers.size()>0){
				requestCustomerProfile.getContactNumbers().addAll(requestContactNumbers);
			}
			if(null!=requestContactEmails && requestContactEmails.size()>0){
				requestCustomerProfile.getContactEmails().addAll(requestContactEmails);
			}

		}
		
		if(null!=requetData.getSalary()){
			requestCustomerProfile.setSalary(String.valueOf(requetData.getSalary()));
		}
		requestCustomerProfile.setEducationLevelOfDependant(requetData.getEducation());
		requestCustomerProfile.setConsentFlag(requetData.getConsentFlag());
		requestCustomerProfile.setAssetWithoutLand(requetData.getAsset());
		requestCustomerProfile.setNoOfEmployee(requetData.getNoOfEmployee());
	
		requestDataQualityObject.setCustomerProfile(requestCustomerProfile);
		
		requestTransaction.serviceInfo(ServiceConstant.OUT, requestDataQualityObject, serviceRequest);
	    
		return requestTransaction;
	}
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try {
			DataQualityTemplateRequest dataQualityRequest =(DataQualityTemplateRequest)requestServiceObject;
			String endPointUrl = serviceRequest.getEndpointUrl();
	        logger.info("endPointUrl >> "+endPointUrl);
	        DataQualityTemplateWebServicePortProxy proxy = new DataQualityTemplateWebServicePortProxy();
	        proxy._getDescriptor().setEndpoint(endPointUrl);
	        DataQualityTemplateResponse responseDataQuality = proxy.validate(dataQualityRequest);
	 
	        serviceTransaction.setServiceTransactionObject(responseDataQuality);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}
	
	@Override
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		DataQualityTemplateResponse responseServiceObctject = (DataQualityTemplateResponse)serviceTransaction.getServiceTransactionObject();
		CVRSDQ0001RequestDataM requetData = (CVRSDQ0001RequestDataM)serviceRequest.getObjectData();	
		try {
			if(null!=responseServiceObctject && null!=responseServiceObctject.getHeader()){
				Header responseHeader =responseServiceObctject.getHeader();
				logger.debug("response data >>"+ new Gson().toJson(responseServiceObctject));
				String respStatus = String.valueOf(responseHeader.getStatus());
				if(ServiceResponse.CIS_STATUS.CIS_SUCCESS_CODES.contains(respStatus)){	
					serviceResponse.setObjectData(mappingResponseData(responseServiceObctject,requetData.getPersonalId()));
					serviceResponse.setStatusCode(ServiceConstant.Status.SUCCESS);
				}else{
					serviceResponse.setStatusCode(ServiceConstant.Status.BUSINESS_EXCEPTION);
					ServiceErrorInfo errorInfo = new ServiceErrorInfo();	
					errorInfo.setServiceId(serviceId);
					errorInfo.setErrorSystem(com.eaf.core.ulo.common.model.ResponseData.SystemType.CIS);
					errorInfo.setErrorTime(ServiceApplicationDate.getTimestamp());
					errorInfo.setErrorType(ErrorData.ErrorType.SERVICE_RESPONSE);
					serviceResponse.setErrorInfo(errorInfo);
				}
			}else{
				logger.debug("response is null");
				serviceResponse.setStatusCode(ServiceConstant.Status.BUSINESS_EXCEPTION);
				if(!ServiceUtil.empty(serviceTransaction.getErrorInfo()) && !ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode())){
					serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
				}else{
					serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SERVICE_RESPONSE,"response is null"));
				}
			
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);					
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
		}
		
		responseTransaction.serviceInfo(ServiceConstant.IN, responseServiceObctject, serviceResponse);
		
		return responseTransaction;
	}
	
	private CVRSDQ0001ResponseDataM mappingResponseData(DataQualityTemplateResponse responseServiceObctje,String personalId) {
		CVRSDQ0001ResponseDataM  responseObject = new CVRSDQ0001ResponseDataM();	
		responseObject.setResponseCode(ServiceConstant.Status.SUCCESS);
		ArrayList<ResponseValidateResultDataM> validationResults = new ArrayList<ResponseValidateResultDataM>();
		responseObject.setResponseValidateFields(validationResults);
		try {
			//FIELD_GRPOUP OF PERSONAL
			if(null!=responseServiceObctje.getFields()){
				List<DqField> personalFields = responseServiceObctje.getFields();
				if(null!=personalFields && personalFields.size()>0){
					for(DqField personalDqField : personalFields){
						List<DqError> dqErrors = personalDqField.getErrors();
						if(null!=dqErrors && dqErrors.size()>0){
							for(DqError error : dqErrors){
								ResponseValidateResultDataM validateResult = new ResponseValidateResultDataM();
								validateResult.setPersonalId(personalId);
								validateResult.setFieldId(String.valueOf(personalDqField.getId()));
								validateResult.setFieldName(personalDqField.getName());
								validateResult.setFildGroup(ResponseValidateResultDataM.FIELD_GROUP.PERSONAL);
								validateResult.setErrorCode(error.getCode());
								validateResult.setErrorDesc(error.getDesc());
								validationResults.add(validateResult);
							}
						}
						
					}
				}
			}
			
			//FIELD_GRPOUP OF OTHER_ADDRESS
			if(null!=responseServiceObctje.getOtherAddresses()){
				List<OthetAddressList> otherAddresses = responseServiceObctje.getOtherAddresses();
				if(null!=otherAddresses && otherAddresses.size()>0){
					for(OthetAddressList otherAddress : otherAddresses){
						List<DqField>  otherDqFields =otherAddress.getFields();
						if(null!=otherDqFields && otherDqFields.size()>0){
							for(DqField otherDqField : otherDqFields){
								List<DqError> dqErrors = otherDqField.getErrors();
								if(null!=dqErrors && dqErrors.size()>0){
									for(DqError error : dqErrors){
										ResponseValidateResultDataM validateResult = new ResponseValidateResultDataM();
										validateResult.setFieldId(String.valueOf(otherDqField.getId()));
										validateResult.setPersonalId(personalId);
										validateResult.setFieldName(otherDqField.getName());
										validateResult.setFildGroup(ResponseValidateResultDataM.FIELD_GROUP.OTHER_ADDRESS);
										validateResult.setErrorCode(error.getCode());
										validateResult.setErrorDesc(error.getDesc());
										validationResults.add(validateResult);
									}
								}
							}
						}
					}
				}
			}
			
			//FIELD_GRPOUP OF CONTACT_NUMBER
			if(null!=responseServiceObctje.getContactNumbers()){
				List<ContactNumberList> contactNumbers = responseServiceObctje.getContactNumbers();
				if(null!=contactNumbers && contactNumbers.size()>0){
					for(ContactNumberList contactNumber : contactNumbers){
						List<DqField> contactNumberDqField =contactNumber.getFields();
						if(null!=contactNumberDqField && contactNumberDqField.size()>0){
							for(DqField contactDqField : contactNumberDqField){
								List<DqError> dqErrors = contactDqField.getErrors();
								if(null!=dqErrors && dqErrors.size()>0){
									for(DqError error : dqErrors){
										ResponseValidateResultDataM validateResult = new ResponseValidateResultDataM();
										validateResult.setFieldId(String.valueOf(contactDqField.getId()));
										validateResult.setPersonalId(personalId);
										validateResult.setFieldName(contactDqField.getName());
										validateResult.setFildGroup(ResponseValidateResultDataM.FIELD_GROUP.CONTACT_NUMBER);
										validateResult.setErrorCode(error.getCode());
										validateResult.setErrorDesc(error.getDesc());
										validationResults.add(validateResult);
									}
								}
							}
						}
					}
				}
			}
			
			//FIELD_GRPOUP OF CONTACT_EMAIL
			if(null!=responseServiceObctje.getContactEmails()){
				List<ContactEmailList> contactEmails = responseServiceObctje.getContactEmails();
				if(null!=contactEmails && contactEmails.size()>0){
					for(ContactEmailList contactEmail : contactEmails){
						List<DqField> contactEmailDqFields =contactEmail.getFields();
						if(null!=contactEmailDqFields && contactEmailDqFields.size()>0){
							for(DqField contactEmailDqField : contactEmailDqFields){
								List<DqError> dqErrors = contactEmailDqField.getErrors();
								if(null!=dqErrors && dqErrors.size()>0){
									for(DqError error : dqErrors){
										ResponseValidateResultDataM validateResult = new ResponseValidateResultDataM();
										validateResult.setFieldId(String.valueOf(contactEmailDqField.getId()));
										validateResult.setPersonalId(personalId);
										validateResult.setFieldName(contactEmailDqField.getName());
										validateResult.setFildGroup(ResponseValidateResultDataM.FIELD_GROUP.CONTACT_EMAIL);
										validateResult.setErrorCode(error.getCode());
										validateResult.setErrorDesc(error.getDesc());
										validationResults.add(validateResult);
									}
								}
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		
		return responseObject;
	}
}
