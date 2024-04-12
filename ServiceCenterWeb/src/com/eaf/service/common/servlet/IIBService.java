package com.eaf.service.common.servlet;

import iib.ava.com.decisionservice.DecisionServiceHttpPortProxy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/Iibservice")
public class IIBService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(IIBService.class);
	private String CIS0368I01 = SystemConstant.getConstant("CIS0368I01");
	private String CVRS1312I01 = SystemConstant.getConstant("CVRS1312I01");
	private String FRAUD = SystemConstant.getConstant("FRAUD");
	private String KBANK1211I01 = SystemConstant.getConstant("KBANK1211I01");
	private String KBANK1550I01 = SystemConstant.getConstant("KBANK1550I01");
	private String KCBS = SystemConstant.getConstant("KCBS");
	private String KOC = SystemConstant.getConstant("KOC");
	private String HRIS = SystemConstant.getConstant("HRIS");
	private String CARDLINK = SystemConstant.getConstant("CARDLINK");
	private String TCBLOAN = SystemConstant.getConstant("TCBLOAN");
	private String SAFELOAN = SystemConstant.getConstant("SAFELOAN");
	private String LPM = SystemConstant.getConstant("LPM");
	private String BSCORE = SystemConstant.getConstant("BSCORE");

	private String KASSET = SystemConstant.getConstant("KASSET");
	private String SAVINGACCOUNT = SystemConstant.getConstant("SAVINGACCOUNT");
	private String FIXACCOUNT = SystemConstant.getConstant("FIXACCOUNT");
	private String CURRENTACCOUNT = SystemConstant.getConstant("CURRENTACCOUNT");
	private String BOL = SystemConstant.getConstant("BOL");
	private String WEALTH = SystemConstant.getConstant("WEALTH");

	private String PAYROLL = SystemConstant.getConstant("PAYROLL");

	public IIBService() {
		super();
	}

	public static class CONFIG_NAME {
		public static final String UrlWebService = "UrlWebService";

	}

	public static class filePath {
		public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+ "properties" + File.separator + "Iibservice.properties";
		public static final String text = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+ "properties" + File.separator + "addressDescription.txt";
	}

	private decisionservice_iib.ApplicationGroupDataM getRequest(
			HttpServletRequest request) {
		decisionservice_iib.ApplicationGroupDataM applicationGroup = new decisionservice_iib.ApplicationGroupDataM();
		decisionservice_iib.KBankHeader kbankHeader = new decisionservice_iib.KBankHeader();

		String status = request.getParameter("Status");

		String customerType = request.getParameter("customerType");
		String cisNo = request.getParameter("cisNo");
		String birthDate = request.getParameter("birthDate");
		String idno = request.getParameter("idNo");
		String IdType = request.getParameter("IdType");
		String Prefix = request.getParameter("Prefix");
		String applicantType = request.getParameter("applicantType");
		String enFirstName = request.getParameter("enFirstName");
		String enLastName = request.getParameter("enLastName");
		String enMidName = request.getParameter("enMidName");
		String nationality = request.getParameter("nationality");
		String thFirstName = request.getParameter("thFirstName");
		String thLastName = request.getParameter("thLastName");
		String thMidName = request.getParameter("thMidName");
		String orgId = request.getParameter("orgId");
		String numberOfSubmission = request.getParameter("numberOfSubmission");
		String applicationGroupNo = request.getParameter("applicationGroupNo");
		String applicationDate = request.getParameter("applicationDate");
		String companyName = request.getParameter("companyName");
		String thTitleCode = request.getParameter("thTitleCode");
		String requireBureau = request.getParameter("requireBureau");
		String fraudType = request.getParameter("fraudType");
		String cisType = request.getParameter("cisType");
		String prodCode = request.getParameter("prodCode");
		String offAddress = request.getParameter("offAddress");
		String terminalId = request.getParameter("terminalId");
		String userName = request.getParameter("userName");
		String rqUID = request.getParameter("rqUid");
		
		//Add applyDate fields for IIB KOC Service
		String applyDate = request.getParameter("applyDate");

		List<decisionservice_iib.PersonalInfoDataM> personals = new ArrayList<decisionservice_iib.PersonalInfoDataM>();
		// List<decisionservice_iib.ApplicationDataM> applications = new
		// ArrayList<decisionservice_iib.ApplicationDataM>();
		decisionservice_iib.ApplicationDataM app = new decisionservice_iib.ApplicationDataM();

		decisionservice_iib.PersonalInfoDataM personal = new decisionservice_iib.PersonalInfoDataM();

		// SET APPLICATION DATAM REQUEST
		if (!Util.empty(applicationDate))
			applicationGroup.setApplicationDate(UtilDate
					.convertDate(applicationDate));
		if (!Util.empty(numberOfSubmission))
			applicationGroup.setNumberOfSubmission(Integer
					.parseInt(numberOfSubmission));
		if (!Util.empty(orgId)) {
			app.setOrgId(orgId);
			applicationGroup.getApplications().add(app);
		}
		
		//Add applyDate for IIB KOC Service
		if (!Util.empty(applyDate))
		{
			applicationGroup.setApplyDate(UtilDate.convertDate(applyDate));
		}
		
		// SET PERSIONAL REQUEST
		if (!Util.empty(birthDate))
			personal.setBirthDate(UtilDate.convertDate(birthDate));
		if (!Util.empty(customerType))
			personal.setCustomerType(customerType);
		if (!Util.empty(cisNo))
			personal.setCisNo(cisNo);
		if (!Util.empty(thTitleCode))
			personal.setThTitleCode(thTitleCode);
		if (!Util.empty(idno))
			personal.setIdNo(idno);
		if (!Util.empty(enFirstName))
			personal.setEnFirstName(enFirstName);
		if (!Util.empty(enLastName))
			personal.setEnLastName(enLastName);
		if (!Util.empty(enMidName))
			personal.setEnMidName(enMidName);
		if (!Util.empty(nationality))
			personal.setNationality(nationality);
		if (!Util.empty(thFirstName))
			personal.setThFirstName(thFirstName);
		if (!Util.empty(thLastName))
			personal.setThLastName(thLastName);
		if (!Util.empty(thMidName))
			personal.setThMidName(thMidName);
		if (!Util.empty(requireBureau))
			personal.setRequireBureau(requireBureau);
		if (!Util.empty(applicantType))
			personal.setPersonalType(applicantType);
		if (!Util.empty(Prefix))
			personal.setThTitleCode(Prefix);
		if (!Util.empty(fraudType))
			personal.setFraudType(fraudType);
		if (!Util.empty(cisType))
			personal.setCidType(cisType);
		if (!Util.empty(offAddress))
			personal.setOfficialCountry(offAddress);

		// SET KBANK HEADER
		if (!Util.empty(terminalId))
			kbankHeader.setTerminalId(terminalId);
		if (!Util.empty(rqUID))
			kbankHeader.setTransactionId(rqUID);
		if (!Util.empty(userName))
			kbankHeader.setUserId(userName);

		applicationGroup.setKBankHeader(kbankHeader);
		
		
		if (!Util.empty(companyName))
			personal.setCompanyName(companyName);
//		if (KBANK1550I01.equals(status)) {
//			if (!Util.empty(companyName))
//				personal.setCompanyName(companyName);
//		} else {
//			if (!Util.empty(companyName)) {
//				decisionservice_iib.AddressDataM address = new decisionservice_iib.AddressDataM();
//				address.setAddressType("02");
//				address.setCompanyName(companyName);
//				List<decisionservice_iib.AddressDataM> addresss = personal.getAddresses();
//				addresss.add(address);
//			}
//		}

		personals = applicationGroup.getPersonalInfos();

		/*
		 * if(KCBS.equals(status)){ decisionservice_iib.KBankHeader kbankHeader
		 * = new decisionservice_iib.KBankHeader(); if(!Util.empty(rqUID))
		 * kbankHeader.setUserId(rqUID); if(!Util.empty(userName))
		 * kbankHeader.setTransactionId(userName);
		 * applicationGroup.setKBankHeader(kbankHeader); }
		 */

		personals.add(personal);
		// decisionservice_iib.ApplicationDataM application = new
		// decisionservice_iib.ApplicationDataM();

		applicationGroup.setApplicationGroupNo(applicationGroupNo);
		return applicationGroup;
	}

	public class ResponseObject {
		decisionservice_iib.KBankHeader kBankHeader;
		Object responseObject;
		String FoundFlag;

		public String getFoundFlag() {
			return FoundFlag;
		}

		public void setFoundFlag(String foundFlag) {
			FoundFlag = foundFlag;
		}

		public decisionservice_iib.KBankHeader getkBankHeader() {
			if (Util.empty(kBankHeader)) {
				return new decisionservice_iib.KBankHeader();
			} else {
				return kBankHeader;
			}
		}

		public void setkBankHeader(decisionservice_iib.KBankHeader kBankHeader) {
			this.kBankHeader = kBankHeader;
		}

		public Object getResponseObject() {
			return responseObject;
		}

		public void setResponseObject(Object responseObject) {
			this.responseObject = responseObject;
		}
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String processCode = request.getParameter("processCode");
		String urlWebService = request.getParameter("urlWebService");
		String status = request.getParameter("Status");
		logger.debug("processCode =>>" + processCode);
		logger.debug("Service Name =>>" + status);
		if ("request".equals(processCode)) {
			// Call Service
			decisionservice_iib.ApplicationGroupDataM requestObject = getRequest(request);
			logger.debug("urlWebService : " + urlWebService);
//			com.kasikornbank.ava.iibservice.IIBServiceHttpPortProxy proxy = new com.kasikornbank.ava.iibservice.IIBServiceHttpPortProxy();
			DecisionServiceHttpPortProxy proxy = new DecisionServiceHttpPortProxy();
			
			proxy._getDescriptor().setEndpoint(urlWebService);
			if (CIS0368I01.equals(status)) {
				ResponseObject responseObjectMsg = new ResponseObject();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.cis0368I01(requestObject);
					responseObjectMsg.setkBankHeader(responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						responseObjectMsg.setResponseObject(responseObject.getPersonalInfos().get(0).getCis0368I01Response());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					responseObjectMsg.setResponseObject(e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(responseObjectMsg);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (CVRS1312I01.equals(status)) {
				ResponseObject responseObjectMsg = new ResponseObject();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.cvrs1312I01(requestObject);
					responseObjectMsg.setkBankHeader(responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						responseObjectMsg.setResponseObject(responseObject.getPersonalInfos().get(0).getCvrs1312I01Responses());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					responseObjectMsg.setResponseObject(e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(responseObjectMsg);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (FRAUD.equals(status)) {
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.fraud(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {

						if (!Util.empty(responseObject.getFraudApplicantFlag()))
							mapResponse.put("FRAUD_APPLICATION_FLAG",responseObject.getFraudApplicantFlag());
						if (!Util.empty(responseObject.getFraudCompanyFlag()))
							mapResponse.put("FRAUD_COMPANY_FLAG",responseObject.getFraudApplicantFlag());
						mapResponse.put("FRAUD_RESPONSE", responseObject.getFraudResponse());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (KBANK1211I01.equals(status)) {
				ResponseObject responseObjectMsg = new ResponseObject();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.kbank1211I01(requestObject);
					responseObjectMsg.setkBankHeader(responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						responseObjectMsg.setResponseObject(responseObject.getPersonalInfos().get(0).getKbank1211I01Response());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					responseObjectMsg.setResponseObject(e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(responseObjectMsg);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (KBANK1550I01.equals(status)) {
				ResponseObject responseObjectMsg = new ResponseObject();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.kbank1550I01(requestObject);
					responseObjectMsg.setkBankHeader(responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						responseObjectMsg.setResponseObject(responseObject.getPersonalInfos().get(0).getKbank1550I01Response());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					responseObjectMsg.setResponseObject(e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(responseObjectMsg);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (KCBS.equals(status)) {
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.kcbs(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						mapResponse.put("KCBS_RESPONSE", responseObject.getPersonalInfos().get(0).getKcbsResponse());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (KOC.equals(status)) {
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.koc(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundCCAFlag()))
							mapResponse.put("foundCCAFlag",responseObject.getPersonalInfos().get(0).getFoundCCAFlag());
						mapResponse.put("KOC_RESPONSE",responseObject.getPersonalInfos().get(0).getCcaCampaignInfos());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (HRIS.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.hris(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundKbankEmployeeFlag()))
							mapResponse.put("foundKbankEmployeeFlag",responseObject.getPersonalInfos().get(0).getFoundKbankEmployeeFlag());
						mapResponse.put("KBANK_EMP_RESPONSE", responseObject.getPersonalInfos().get(0).getKBankEmployeeDataM());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
				
				
			} else if (CARDLINK.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.cardlink(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundCardlinkCardFlag()))
							mapResponse.put("foundCardlinkCardFlag",responseObject.getPersonalInfos().get(0).getFoundCardlinkCardFlag());
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundCardlinkCustomerFlag()))
							mapResponse.put("foundCardlinkCustomerFlag",responseObject.getPersonalInfos().get(0).getFoundCardlinkCustomerFlag());

						mapResponse.put("CARDLINK_CARD_RESPONSE",responseObject.getPersonalInfos().get(0).getCardlinkCards());
						logger.debug("response Cardlinkcards:>>"+ mapResponse.toString());

						mapResponse.put("CARDLINK_CUSTOMER_RESPONSE",responseObject.getPersonalInfos().get(0).getCardlinkCustomers());
						logger.debug("response CardlinkCustomer:>>"+ mapResponse.toString());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				
				
				
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (TCBLOAN.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.tcbLoan(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundTCBLoanFlag()))
							mapResponse.put("foundTCBLoanFlag", responseObject.getPersonalInfos().get(0).getFoundTCBLoanFlag());
							mapResponse.put("TCBLOAN_RESPONSE", responseObject.getPersonalInfos().get(0).getTcbLoans());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (SAFELOAN.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.safe(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundSAFELoanFlag()))
							mapResponse.put("foundSAFELoanFlag", responseObject.getPersonalInfos().get(0).getFoundSAFELoanFlag());
						mapResponse.put("SAFELOAN_RESPONSE", responseObject.getPersonalInfos().get(0).getSafeLoans());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (LPM.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.lpm(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundLPMBlacklistFlag()))
							mapResponse.put("foundLPMBlacklistFlag",responseObject.getPersonalInfos().get(0).getFoundLPMBlacklistFlag());
							mapResponse.put("LPM_RESPONSE", responseObject.getPersonalInfos().get(0).getLpmBlacklists());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (BSCORE.equals(status)) {
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.bScore(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundBScoreFlag()))
							mapResponse.put("foundLPMBlacklistFlag",responseObject.getPersonalInfos().get(0).getFoundBScoreFlag());
							mapResponse.put("BSCORE_RESPONSE", responseObject.getPersonalInfos().get(0).getBScores());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (KASSET.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.kAsset(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundKAssetFlag()))
							mapResponse.put("foundKAssetFlag", responseObject.getPersonalInfos().get(0).getFoundKAssetFlag());
							mapResponse.put("KASSET_RESPONSE", responseObject.getPersonalInfos().get(0).getKAssets());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (SAVINGACCOUNT.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.savingAccount(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundSavingAccountFlag()))
							mapResponse.put("foundSavingAccountFlag",responseObject.getPersonalInfos().get(0).getFoundSavingAccountFlag());
							mapResponse.put("SAVINGACCOUNT_RESPONSE",responseObject.getPersonalInfos().get(0).getSavingAccounts());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (FIXACCOUNT.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.fixAccount(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundFixAccountFlag()))
							mapResponse.put("foundFixAccountFlag",responseObject.getPersonalInfos().get(0).getFoundFixAccountFlag());
							mapResponse.put("FIXACCOUNT_RESPONSE", responseObject.getPersonalInfos().get(0).getFixAccounts());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (CURRENTACCOUNT.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.currentAccount(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundCardlinkCardFlag()))
							mapResponse.put("foundCurrentAccountFlag",responseObject.getPersonalInfos().get(0).getFoundCardlinkCardFlag());
							mapResponse.put("CURRENT_ACCOUNT_RESPONSE",responseObject.getPersonalInfos().get(0).getCurrentAccounts());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (BOL.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.bol(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundBOLFlag()))
							mapResponse.put("foundBOLFlag", responseObject.getPersonalInfos().get(0).getFoundBOLFlag());
							mapResponse.put("BOL_RESPONSE", responseObject.getPersonalInfos().get(0).getBolOrganizations());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (WEALTH.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.wealth(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos()))
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundWealthFlag())) {
							mapResponse.put("foundWealthFlag", responseObject.getPersonalInfos().get(0).getFoundWealthFlag());
							mapResponse.put("WEALTH_RESPONSE", responseObject.getPersonalInfos().get(0).getWealths());
						}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			} else if (PAYROLL.equals(status)) { // SUCCESS
				HashMap<String, Object> mapResponse = new HashMap<String, Object>();
				try {
					decisionservice_iib.ApplicationGroupDataM responseObject = proxy.payroll(requestObject);
					mapResponse.put("KBANK_HEADER",responseObject.getKBankHeader());
					if (!Util.empty(responseObject.getPersonalInfos())) {
						if (!Util.empty(responseObject.getPersonalInfos().get(0).getFoundPayrollFlag())) {
							mapResponse.put("foundPayrollFlag", responseObject.getPersonalInfos().get(0).getFoundPayrollFlag());
						}
						mapResponse.put("PAYROLL_RESPONSE", responseObject.getPersonalInfos().get(0).getKBankPayrollDataM());
					}
				} catch (Exception e) {
					logger.fatal("ERROR", e);
					mapResponse.put("RESPONSE_ERROR", e.getMessage());
				}
				String requestJson = new Gson().toJson(requestObject);
				String responseJson = new Gson().toJson(mapResponse);
				logger.debug("requestJson : " + requestJson);
				logger.debug("responseJson : " + responseJson);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("jsonRq", requestJson);
				data.put("jsonRs", responseJson);
				ResponseUtils.sendJsonResponse(response, data);
			}

		} else if ("default".equals(processCode)) {
			File file = new File((filePath.file));
			FileInputStream fileInput = new FileInputStream(file);
			logger.debug("File INPUT >>" + file);
			logger.debug("File INPUT STREAM   >>" + fileInput);
			Properties prop = new Properties();
			prop.load(fileInput);
			fileInput.close();
			logger.debug("default Status java file =>>" + processCode);
			HashMap<String, Object> data = new HashMap<>();
			data.put(CONFIG_NAME.UrlWebService,prop.getProperty(CONFIG_NAME.UrlWebService));
			logger.debug("Property Path =>>"+ prop.getProperty(CONFIG_NAME.UrlWebService));
			ResponseUtils.sendJsonResponse(response, data);
		} else if ("save".equals(processCode)) {
			File file = new File((filePath.file));
			FileInputStream fileInput = new FileInputStream(file);
			logger.debug("File INPUT >>" + file);
			logger.debug("File INPUT STREAM   >>" + fileInput);
			Properties prop = new Properties();
			prop.load(fileInput);
			fileInput.close();
			urlWebService = request.getParameter("urlProps");
			if (urlWebService == null)
				urlWebService = "";
			FileOutputStream fileUpdate = new FileOutputStream(file);
			prop.setProperty(CONFIG_NAME.UrlWebService, urlWebService);
			prop.store(fileUpdate, "data record in addressDescription.txt");
			if (null != fileUpdate) {
				fileUpdate.flush();
				fileUpdate.close();
			}
			System.gc();
			logger.debug("props : " + prop);

		}
	}
}
