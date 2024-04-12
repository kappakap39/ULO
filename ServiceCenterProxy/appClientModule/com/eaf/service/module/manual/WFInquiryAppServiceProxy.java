package com.eaf.service.module.manual;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import com.ava.workflow.AddressM;
import com.ava.workflow.ApplicationGroupM;
import com.ava.workflow.ApplicationM;
import com.ava.workflow.CardM;
import com.ava.workflow.CardlinkCustM;
import com.ava.workflow.IncomeInfoM;
import com.ava.workflow.IncomeSourceM;
import com.ava.workflow.KBankRqHeader;
import com.ava.workflow.KBankRsHeader;
import com.ava.workflow.LoanM;
import com.ava.workflow.OrCodeM;
import com.ava.workflow.PersonalInfoM;
import com.ava.workflow.PersonalRefM;
import com.ava.workflow.PreviousIncomeM;
import com.ava.workflow.VerResultM;
import com.ava.workflow.WebsiteM;
import com.ava.workflow.WfInquiryReqM;
import com.ava.workflow.WfInquiryRespM;
import com.ava.workflow.WorkFlowPortProxy;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.AddressDataM;
import com.eaf.service.module.model.ApplicationDataM;
import com.eaf.service.module.model.ApplicationGroupDataM;
import com.eaf.service.module.model.CardDataM;
import com.eaf.service.module.model.CardlinkCustDataM;
import com.eaf.service.module.model.IncomeInfoDataM;
import com.eaf.service.module.model.IncomeSourceDataM;
import com.eaf.service.module.model.LoanDataM;
import com.eaf.service.module.model.OrCodeDataM;
import com.eaf.service.module.model.PersonalInfoDataM;
import com.eaf.service.module.model.PersonalRefDataM;
import com.eaf.service.module.model.PreviousIncomeDataM;
import com.eaf.service.module.model.VerResultDataM;
import com.eaf.service.module.model.WFInquiryAppRequestDataM;
import com.eaf.service.module.model.WFInquiryAppResponseDataM;
import com.eaf.service.module.model.WebsiteDataM;
import com.eaf.service.rest.model.ServiceResponse;

public class WFInquiryAppServiceProxy extends ServiceControlHelper implements ServiceControl {
	private static transient Logger logger = Logger.getLogger(WFInquiryAppServiceProxy.class);
	public static final String url = "urlWebService";
	public static final String serviceId = "WFInquiryApp";
//	public static final String callSuccess = ServiceCache.getConstant("SERVICE_CALL_CODE");
//	public static final String callError = ServiceCache.getConstant("SERVICE_ERROR_CODE");
//	public static final String successCode = ServiceCache.getConstant("SUCCESS_CODE");
	
	public static class requestConstants{
		public static final String cisNo = "cis_no";
		public static final String docNo = "doc_no";
		public static final String docType = "doc_type";
		public static final String thFirstName = "th_first_name";
		public static final String thLastName = "th_last_name";
		public static final String dob = "dob";
		public static final String appStatus = "app_status";
		public static final String bookedFlag = "booked_flag";
		public static final String productCode = "product_code";
		public static final String appFromDate = "app_date_from";
		public static final String appToDate = "app_date_to";
	}
	
	public static class responseConstants{
		public static final String noRecord = "no_record";
		public static final String applicationGroupM = "applicationGroupM";
		public static class appGroupSeg{
			public static final String appNo = "app_no";
			public static final String appDate = "app_date";
		}
		public static class appSeg{
			public static final String personalId = "personal_id";
			public static final String docNo = "doc_no";
			public static final String docType = "doc_type";
			public static final String thFirstname = "th_firstname";
			public static final String thMidname = "th_midname";
			public static final String thLastname = "th_lastname";
			public static final String applicantType = "applicant_type";
			public static final String finalVerifiedIncome = "final_verified_income";
			public static final String incomePolicySegmentCode = "income_policy_segment_code";
		}
		public static class veriResultSeg{
			public static final String verifyCustResult = "verify_cust_result";
		}
		public static class webVeriResultSeg{
			public static final String websiteCode = "website_code";
			public static final String websiteReusult = "website_reusult";
		}
		public static class cardlinkCustSeg{
			public static final String cardlinkCustNo = "cardlink_cust_no";
			public static final String orgId = "org_id";
		}
		public static class incomeSeg{
			public static final String incomeType = "income_type";
		}
		public static class previousIncSeg{
			public static final String income = "income";
			public static final String incomeDate = "incomeDate";
		}
		public static class appItemSeg{
			public static final String appItemNo = "app_item_no";
			public static final String appStatus = "app_status";
			public static final String appStatusDate = "app_status_date";
			public static final String projectCode = "project_code";
			public static final String kbankProductCode = "kbank_product_code";
			public static final String vetoFlag = "veto_flag";
			public static final String rejectCode = "reject_code";
			public static final String applyType = "apply_type";
			public static final String bundleProduct = "bundle_product";
			public static final String bundleProductCreditLimit = "bundle_product_credit_limit";
			public static final String policyProgramId = "policy_program_id";
		}
		public static class orCodeSeg{
			public static final String orCode = "or_code";
		}
		public static class loanSeg{
			public static final String accountNo = "account_no";
			public static final String finalCreditLimit = "final_credit_limit";
			public static final String installmentAmt = "installment_amt";
			public static final String bookedFlag = "booked_flag";
			public static final String cycleCut = "cycle_cut";
		}
		public static class personRefSegLoan{
			public static final String pesonalId = "pesonal_id";
			public static final String applicantType = "applicant_type";
		}
		public static class cardSeg{
			public static final String cardCode = "card_code";
			public static final String cardLevel = "card_level";
			public static final String plasticType = "plastic_type";
			public static final String existCardno = "existCardno";
			public static final String docNo = "doc_no";
			public static final String cardApplyType = "card_apply_type";
			public static final String cardNo = "card_no";
			public static final String mainCardNo = "main_card_no";
			public static final String maincardNoEncrypted = "maincard_no_encrypted";
		}
		public static class personRefSegApp{
			public static final String personalId = "personal_id";
			public static final String applicantType = "applicant_type";
		}
	}
	
	public static class errorConstants{
		public static final String unmatched = "00";
		public static final String notfound = "01";
		public static final String error = "02";
	}
	
	public ServiceRequestTransaction requestTransaction() {
		logger.debug("request transaction");
		ServiceRequestTransaction requestTran = new ServiceRequestTransaction();
		try{
			WfInquiryReqM request = new WfInquiryReqM();
			KBankRqHeader kBankRqHeader = new KBankRqHeader();
			kBankRqHeader.setFuncNm(serviceId);
			kBankRqHeader.setRqUID(serviceRequest.getServiceReqResId());
			
			GregorianCalendar gc = new GregorianCalendar();
			
			kBankRqHeader.setRqDt(ServiceUtil.getXMLGregorianCalendar());
			String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
			kBankRqHeader.setRqAppId(RqAppId);
			kBankRqHeader.setUserId(serviceRequest.getUserId());
			kBankRqHeader.setCorrID(serviceRequest.getServiceReqResId());
			request.setKBankRqHeader(kBankRqHeader);
			
			WFInquiryAppRequestDataM WFInquiryApp = (WFInquiryAppRequestDataM)serviceRequest.getObjectData();

			request.setCisNo(WFInquiryApp.getCisNo());
			request.setDocNo(WFInquiryApp.getDocNo());
			request.setDocType(WFInquiryApp.getDocType());
			request.setThFirstName(WFInquiryApp.getThFirstName());
			request.setThLastName(WFInquiryApp.getThLastName());
			if(!ServiceUtil.empty(WFInquiryApp.getDob())){
				gc.setTime(WFInquiryApp.getDob());
				XMLGregorianCalendar xmlGc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
				request.setDob(xmlGc);
			}
			request.setAppStatus(WFInquiryApp.getAppStatus());
			request.setBookedFlag(WFInquiryApp.getBookedFlag());
			request.setProductCode(WFInquiryApp.getProductCode());
			if(!ServiceUtil.empty(WFInquiryApp.getAppFromDate())){
				gc.setTime(WFInquiryApp.getAppFromDate());
				XMLGregorianCalendar xmlGc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
				request.setAppDateFrom(xmlGc);
			}
			if(!ServiceUtil.empty(WFInquiryApp.getAppToDate())){
				gc.setTime(WFInquiryApp.getAppToDate());
				XMLGregorianCalendar xmlGc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
				request.setAppDateTo(xmlGc);
			}
			request.setKBankRqHeader(kBankRqHeader);
			requestTran.serviceInfo(ServiceConstant.OUT, request, serviceRequest);
		}catch(Exception e){
			logger.fatal("Error ",e);
		}
		return requestTran;
	}
	
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("service transaction");
		ServiceTransaction servTran = new ServiceTransaction();
		try{
			WfInquiryReqM request = (WfInquiryReqM)requestServiceObject;
			WorkFlowPortProxy proxy = new WorkFlowPortProxy();
			proxy._getDescriptor().setEndpoint(getEndpointUrl());
			WfInquiryRespM respTran = proxy.inquiry(request);
			servTran.setServiceTransactionObject(respTran);
			servTran.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			servTran.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
		}
		return servTran;
	}
	
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction) {
		logger.debug("response transaction");
		ServiceResponseTransaction respTran = new ServiceResponseTransaction();
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode())){
			WFInquiryAppResponseDataM WFInquiryAppResponse = new WFInquiryAppResponseDataM();
			WfInquiryRespM response = (WfInquiryRespM)serviceTransaction.getServiceTransactionObject();
			KBankRsHeader KBankRsHeader = response.getKBankHeader();
			if(ServiceResponse.Status.SUCCESS.equals(KBankRsHeader.getStatusCode())){
				WFInquiryAppResponse.setNoRecord(response.getNoRecord());
				List<ApplicationGroupM> applicationGroups = response.getApplicationGroupM();
				if(!ServiceUtil.empty(applicationGroups)){
					ArrayList<ApplicationGroupDataM> applicationGroupList = new ArrayList<>();
					for(ApplicationGroupM applicationGroup : applicationGroups){
						ApplicationGroupDataM applicationGroupMap = new ApplicationGroupDataM();
						applicationGroupMap.setAppNo(applicationGroup.getAppNo());
						applicationGroupMap.setAppDate(ServiceUtil.empty(applicationGroup.getAppDate()) ? null 
								: applicationGroup.getAppDate().toGregorianCalendar().getTime());
						mapPersonalInfos(applicationGroupMap, applicationGroup);
						mapApplications(applicationGroupMap, applicationGroup);	
						applicationGroupList.add(applicationGroupMap);
					}
					WFInquiryAppResponse.setApplicationGroupM(applicationGroupList);
					WFInquiryAppResponse.setPersonalId(response.getPersonalId());
				}
				serviceResponse.setObjectData(WFInquiryAppResponse);
				respTran.serviceInfo(ServiceConstant.IN, response, serviceResponse);
			}else{
				serviceResponse.setObjectData(WFInquiryAppResponse);
				respTran.serviceInfo(ServiceConstant.IN, response, serviceResponse);
			}
		}
		return respTran;
	}
	
	private void mapPersonalInfos(ApplicationGroupDataM applicationGroupMap, ApplicationGroupM applicationGroup){
		try{
			List<PersonalInfoM> personalInfos = applicationGroup.getPersonalInfoM();
			if(!ServiceUtil.empty(personalInfos)){
				ArrayList<PersonalInfoDataM> personalInfoList = new ArrayList<>();
				for(PersonalInfoM personalInfo : personalInfos){
					PersonalInfoDataM personalInfoMap = new PersonalInfoDataM();
					personalInfoMap.setPersonalId(personalInfo.getPersonalId());
					personalInfoMap.setDocNo(personalInfo.getDocNo());
					personalInfoMap.setDocType(personalInfo.getDocType());
					personalInfoMap.setThFirstname(personalInfo.getThFirstname());
					personalInfoMap.setThMidname(personalInfo.getThMidname());
					personalInfoMap.setThLastname(personalInfo.getThLastname());
					personalInfoMap.setCisNo(personalInfo.getCisNo());
					personalInfoMap.setDob(personalInfo.getDob());
					personalInfoMap.setApplicantType(personalInfo.getApplicantType());
					personalInfoMap.setFinalVerifiedIncome(personalInfo.getFinalVerifiedIncome());
					personalInfoMap.setMailAddress(personalInfo.getMailingAddress());
					personalInfoMap.setTypeOfFin(personalInfo.getTypeOfFin());
					
					ArrayList<AddressDataM> addressList = new ArrayList<>();
					List<AddressM> addresses = personalInfo.getAddressM();
					if(!ServiceUtil.empty(addresses)){
						for(AddressM address : addresses){
							AddressDataM addressMap = new AddressDataM();
							addressMap.setAddressType(address.getAddressType());
							addressMap.setHouseNumber(address.getHouseNumber());
							addressMap.setVillage(address.getVillage());
							addressMap.setFloorNumber(address.getFloorNumber());
							addressMap.setRoom(address.getRoom());
							addressMap.setBuildingName(address.getBuildingName());
							addressMap.setMoo(address.getMoo());
							addressMap.setSoi(address.getSoi());
							addressMap.setStreet(address.getStreet());
							addressMap.setSubDistrict(address.getSubDistrict());
							addressMap.setDistrict(address.getDistrict());
							addressMap.setProvince(address.getProvince());
							addressMap.setPostalCode(address.getPostalCode());
							addressMap.setCountry(address.getCountry());
							addressList.add(addressMap);
						}
						personalInfoMap.setAddresses(addressList);
					}									
					
					VerResultM verResult = personalInfo.getVerResultM();
					if(!ServiceUtil.empty(verResult)){
						VerResultDataM verResultMap = new VerResultDataM();
						verResultMap.setVerifyCusResult(verResult.getVerifyCustResult());
						
						List<WebsiteM> websites = verResult.getWebsiteM();
						if(!ServiceUtil.empty(websites)){
							ArrayList<WebsiteDataM> websiteList = new ArrayList<>();
							for(WebsiteM website : websites){
								WebsiteDataM websiteMap = new WebsiteDataM();
								websiteMap.setWebsiteCode(website.getWebsiteCode());
								websiteMap.setWebsiteResult(website.getWebsiteReusult());
								websiteList.add(websiteMap);
							}
							verResultMap.setWebsites(websiteList);
						}
						personalInfoMap.setVerResult(verResultMap);
					}
					
					CardlinkCustM cardlinkCust = personalInfo.getCardlinkCustM();
					if(!ServiceUtil.empty(cardlinkCust)){
						CardlinkCustDataM cardlinkCustMap = new CardlinkCustDataM();
						cardlinkCustMap.setCardlinkCustNo(cardlinkCust.getCardlinkCustNo());
						cardlinkCustMap.setOrgId(cardlinkCust.getOrgId());
						personalInfoMap.setCardlinkCust(cardlinkCustMap);
					}
					
					List<IncomeInfoM> incomeInfos = personalInfo.getIncomeInfoM();
					if(!ServiceUtil.empty(incomeInfos)){
						ArrayList<IncomeInfoDataM> incomeInfoList = new ArrayList<>();
						for(IncomeInfoM incomeInfo : incomeInfos){
							IncomeInfoDataM incomeInfoMap = new IncomeInfoDataM();
							incomeInfoMap.setIncomeType(incomeInfo.getIncomeType());
							List<PreviousIncomeM> previousIncomes = incomeInfo.getPreviousIncomeM();
							if(!ServiceUtil.empty(previousIncomes)){
								ArrayList<PreviousIncomeDataM> previousIncomeList = new ArrayList<>();
								for(PreviousIncomeM previousIncome : previousIncomes){
									PreviousIncomeDataM previousIncomeMap = new PreviousIncomeDataM();
									previousIncomeMap.setIncome(previousIncome.getIncome());
									if(!ServiceUtil.empty(previousIncome.getIncomeDate())){
										previousIncomeMap.setIncomeDate(previousIncome.getIncomeDate().toGregorianCalendar().getTime());
									}
									previousIncomeList.add(previousIncomeMap);
								}
								incomeInfoMap.setPreviousIncomeM(previousIncomeList);
							}
							incomeInfoList.add(incomeInfoMap);
						}
						personalInfoMap.setIncomeInfos(incomeInfoList);
					}
					List<IncomeSourceM> incomeSources = personalInfo.getIncomeSourceM();
					if(!ServiceUtil.empty(incomeSources)){
						ArrayList<IncomeSourceDataM> incomeSourceList = new ArrayList<>();
						for(IncomeSourceM incomeSource : incomeSources){
							IncomeSourceDataM incomeSourceMap = new IncomeSourceDataM();
							incomeSourceMap.setIncomeSoucre(incomeSource.getIncomeSource());
							incomeSourceList.add(incomeSourceMap);
						}
						personalInfoMap.setIncomeSources(incomeSourceList);
					}
					personalInfoList.add(personalInfoMap);
				}
				applicationGroupMap.setPersonalInfos(personalInfoList);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	
	private void mapApplications(ApplicationGroupDataM applicationGroupMap, ApplicationGroupM applicationGroup){
		try{
			List<ApplicationM> applications = applicationGroup.getApplicationM();
			if(!ServiceUtil.empty(applications)){
				ArrayList<ApplicationDataM> applicationList = new ArrayList<>();
				for(ApplicationM application : applications){
					ApplicationDataM applicationMap = new ApplicationDataM();
					applicationMap.setAppItemNo(application.getAppItemNo());
					applicationMap.setAppStatus(application.getAppStatus());
					applicationMap.setAppStatusDate(ServiceUtil.empty(application.getAppStatusDate()) ? null 
							: application.getAppStatusDate().toGregorianCalendar().getTime());
					applicationMap.setProjectCode(application.getProjectCode());
					applicationMap.setKbankProductCode(application.getKbankProductCode());
					applicationMap.setVetoFlag(application.getVetoFlag());
					applicationMap.setRejectCode(application.getRejectCode());
					applicationMap.setApplyType(application.getApplyType());
					applicationMap.setBundleProduct(application.getBundleProduct());
					applicationMap.setBundleProductCreditLimit(ServiceUtil.empty(application.getBundleProductCreditLimit()) ? null : application.getBundleProductCreditLimit().toString());
					applicationMap.setPolicyProgramId(application.getPolicyProgramId());
					applicationMap.setMainAppItemNo(application.getMainAppItemNo());
					
					
					VerResultM verResult = application.getVerResultM();
					if(!ServiceUtil.empty(verResult)){
						VerResultDataM verResultMap = new VerResultDataM();
						
						ArrayList<OrCodeDataM> orCodeList = new ArrayList<>();
						List<OrCodeM> orCodes = verResult.getOrCodeM();
						if(!ServiceUtil.empty(orCodes)){
							for(OrCodeM orCode : orCodes){
								OrCodeDataM orCodeMap = new OrCodeDataM();
								orCodeMap.setOrCode(orCode.getOrCode());
								orCodeList.add(orCodeMap);
							}
						}
						verResultMap.setOrCodes(orCodeList);
					}
														
					List<LoanM> loans = application.getLoanM();
					if(!ServiceUtil.empty(loans)){
						ArrayList<LoanDataM> loanList = new ArrayList<>();
						for(LoanM loan : loans){
							LoanDataM loanMap = new LoanDataM();
							loanMap.setAccountNo(loan.getAccountNo());
							loanMap.setFinalCreditLimit(loan.getFinalCreditLimit());
							loanMap.setInstallmentAmt(loan.getInstallmentAmt());
							loanMap.setBookedFlag(loan.getBookedFlag());
							loanMap.setCycleCut(loan.getCycleCut());
							loanMap.setPaymentMethod(loan.getPaymentMethod());
							loanMap.setAutoPaymentNo(loan.getAutopayAccountNo());
							List<PersonalRefM> personalRefs = loan.getPersonalRefM();
							if(!ServiceUtil.empty(personalRefs)){
								ArrayList<PersonalRefDataM> personalRefList = new ArrayList<>();
								for(PersonalRefM personalRef : personalRefs){
									PersonalRefDataM personalRefMap = new PersonalRefDataM();
									personalRefMap.setPersonalId(personalRef.getPesonalId());
									personalRefMap.setApplicantType(personalRef.getApplicantType());
									personalRefList.add(personalRefMap);
								}
								loanMap.setPersonalRefs(personalRefList);
							}
							
							CardM card = loan.getCardM();
							if(!ServiceUtil.empty(card)){
								CardDataM cardMap = new CardDataM();
								cardMap.setCardCode(card.getCardCode());
								cardMap.setCardLevel(card.getCardLevel());
								cardMap.setPlasticType(card.getPlasticType());
								cardMap.setDocNo(card.getDocNo());
								cardMap.setCardApplyType(card.getCardApplyType());
								cardMap.setCardNo(card.getCardNo());
								cardMap.setCardNoEncrypted(card.getCardNoEncrypted());
								cardMap.setPriorityPassNo(card.getPriorityPassNo());
								cardMap.setCardType(card.getCardType());
								cardMap.setCardlinkCustNo(card.getCardlinkCustNo());
								cardMap.setCardlinkOrgNo(card.getCardlinkOrgNo());
								cardMap.setMainCardNo(card.getMainCardNo());
								cardMap.setMaincardNoEncrypted(card.getMaincardNoEncrypted());
								loanMap.setCard(cardMap);
							}
							loanList.add(loanMap);
						}
						applicationMap.setLoans(loanList);
					}
					
					List<PersonalRefM> personalRefs = application.getPersonalRefM();
					if(!ServiceUtil.empty(personalRefs)){
						ArrayList<PersonalRefDataM> personalRefList = new ArrayList<>();
						for(PersonalRefM personalRef : personalRefs){
							PersonalRefDataM personalRefMap = new PersonalRefDataM();
							personalRefMap.setPersonalId(personalRef.getPesonalId());
							personalRefMap.setApplicantType(personalRef.getApplicantType());
							personalRefList.add(personalRefMap);
						}
						applicationMap.setPersonalRefs(personalRefList);
					}
					applicationList.add(applicationMap);
				}
				applicationGroupMap.setApplications(applicationList);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
}
