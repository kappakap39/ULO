package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.bpm.mock.mapper.BPMFicoRequestMapper;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.ApplicationLogDataM;
import com.eaf.orig.ulo.model.app.BankStatementDataM;
import com.eaf.orig.ulo.model.app.BankStatementDetailDataM;
import com.eaf.orig.ulo.model.app.BundleHLDataM;
import com.eaf.orig.ulo.model.app.BundleKLDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.ClosedEndFundDataM;
import com.eaf.orig.ulo.model.app.DebtInfoDataM;
import com.eaf.orig.ulo.model.app.FinancialInstrumentDataM;
import com.eaf.orig.ulo.model.app.FixedAccountDataM;
import com.eaf.orig.ulo.model.app.FixedGuaranteeDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.KVIIncomeDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DetailDataM;
import com.eaf.orig.ulo.model.app.OpenedEndFundDataM;
import com.eaf.orig.ulo.model.app.OpenedEndFundDetailDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PayrollDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDetailDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PreviousIncomeDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.orig.ulo.model.app.SalaryCertDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDetailDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.app.TaweesapDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.FicoServiceProxy;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.orig.bpm.workflow.decision.mock.model.Application;
import com.orig.bpm.workflow.model.BPMFicoRequest;
import com.orig.bpm.workflow.model.BPMFicoResponse;
import com.orig.bpm.workflow.proxy.BPMMainFlowProxy;

@WebServlet("/Fico")
public class Fico extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static transient Logger logger = Logger.getLogger(Fico.class);
    public Fico() {
        super();
    }    
    public static class filePath{
    	public static String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+"properties"+File.separator+"fico"+File.separator+"creditApplication.properties";
    }    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String process = request.getParameter("process");
		logger.debug("process : "+process);
		Gson gson = new Gson();
		if(process.equals("defualt")){
			File file = new File((filePath.file));
			Properties prop = new Properties();
			prop.load(new FileInputStream(file));
			
			HashMap<String,Object> object = new HashMap<String, Object>();
			object.put(FicoServiceProxy.url, prop.get(FicoServiceProxy.url));
			
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("request")){
			String applicationGroupNo = request.getParameter("applicationGroupNo_input");
			String decisionStation = request.getParameter("decision_station");
			logger.debug("Loading data from Decision Station : "+decisionStation);
			BPMFicoResponse bmpFicoResponse = null;
			Application ficoAppGroup = new Application();
			try{
				OrigApplicationGroupDAO applicationGroupDAO = ORIGDAOFactory.getApplicationGroupDAO();
				String applicationGroupId = applicationGroupDAO.getApplicationGroupIdByApplicationGroupNo(applicationGroupNo);
				ApplicationGroupDataM applicationGroup = applicationGroupDAO.loadOrigApplicationGroupM(applicationGroupId);
				
				ficoAppGroup.setAction(decisionStation);
				BPMFicoRequestMapper.mapFiCOApplication(ficoAppGroup, applicationGroup, decisionStation);
				bmpFicoResponse = callBPM(ficoAppGroup);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
			HashMap<String,Object> object = new HashMap<String,Object>();
				object.put("jsonRq", gson.toJson(ficoAppGroup));
				object.put("jsonRs", gson.toJson(bmpFicoResponse));
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("loadData")){
			String applicationGroupId = request.getParameter("applicationGroupId_input");
			String decisionStation = request.getParameter("decision_station");
			logger.debug("Loading data from Decision Station : "+decisionStation);
			ApplicationGroupDataM applicationGroupData = null;
			try{
				applicationGroupData = ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(applicationGroupId);
//				Application ficoAppGroup = new Application();
//				logger.debug("ULO ApplicationGroup : "+gson.toJson(applicationGroupData));
//				ficoAppGroup.setAction(decisionStation);
//				BPMFicoRequestMapper.mapFiCOApplication(ficoAppGroup,applicationGroupData,decisionStation);
//				callBPM(ficoAppGroup);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
			ResponseUtils.sendJsonResponse(response, applicationGroupData);
		}
	}	
	
	private BPMFicoResponse callBPM(Application ficoAppGroup){
		BPMFicoRequest bpmFicoRequest = new BPMFicoRequest();
		bpmFicoRequest.setInputFicoApp(ficoAppGroup.getCreditApplication());
		bpmFicoRequest.setAction(ficoAppGroup.getAction());
		BPMFicoResponse bpmFicoResponse = null;
		try{
			String BPM_HOST = ServiceCache.getProperty("BPM_HOST");
			String BPM_PORT = ServiceCache.getProperty("BPM_PORT");
			String BPM_USER_ID = ServiceCache.getProperty("BPM_USER_ID");
			String BPM_PASSWORD = ServiceCache.getProperty("BPM_PASSWORD");
			logger.debug("BPM_HOST : "+BPM_HOST);
			logger.debug("BPM_PORT : "+BPM_PORT);
			logger.debug("BPM_USER_ID : "+BPM_USER_ID);
			logger.debug("BPM_PASSWORD : "+BPM_PASSWORD);
			BPMMainFlowProxy bpmMainFlowProxy = new BPMMainFlowProxy(BPM_HOST,Integer.valueOf(BPM_PORT),BPM_USER_ID,BPM_PASSWORD);
			logger.debug("callBPM() BPMFicoRequest = "+new Gson().toJson(bpmFicoRequest));
			bpmFicoResponse = bpmMainFlowProxy.callFicoService(bpmFicoRequest);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return bpmFicoResponse;
	}
	
	public void mapFico(HttpServletRequest request,ApplicationGroupDataM ficoRequest){
		String applicationId = request.getParameter("applicationId_input");
		String templateCode = request.getParameter("templateCode_input");
		String isVETO = request.getParameter("isVETO_input");
		String testLogicalDate = request.getParameter("testLogicalDate_input");
		String applicationCreateDate = request.getParameter("applicationCreateDate_input");
		String channelCode = request.getParameter("channelCode_input");
		String strategySelRandomNum1 = request.getParameter("strategySelRandomNum1_input");
		String isFraudApplication = request.getParameter("isFraudApplication_input");
		String isFraudCompany = request.getParameter("isFraudCompany_input");
		String policyExceptionDate = request.getParameter("policyExceptionDate_input");
		String existingAssignedCount = request.getParameter("priorityPassQuota_existingAssignedCount_input");

		ficoRequest.setApplicationGroupNo(applicationId);
		ficoRequest.setApplicationTemplate(templateCode);
		ArrayList<ApplicationLogDataM> applicationLogs = new ArrayList<>();
			ApplicationLogDataM applicationLog = new ApplicationLogDataM();
			applicationLog.setLifeCycle(isVETO.equals("") ? null : new BigDecimal(isVETO));
			applicationLogs.add(applicationLog);
		ficoRequest.setApplicationLogs(applicationLogs);
		ficoRequest.setApplicationDate(applicationCreateDate.equals("") ? null : Date.valueOf(applicationCreateDate));
		ficoRequest.setApplyChannel(channelCode);
		ficoRequest.setPolicyExSignOffDate(policyExceptionDate.equals("") ? null : Date.valueOf(policyExceptionDate));
		
		
		String[] personalAppList = request.getParameterValues("personalApp[]");
		String[] creditDetailList = request.getParameterValues("creditDetail[]");
		Gson gson = new Gson();
		
		ArrayList<PersonalInfoDataM> personalInfos = new ArrayList<>();
		ArrayList<ApplicationDataM> applications = new ArrayList<>();
		for(String personalApp : personalAppList){
			PersonalInfoDataM personalInfo = new PersonalInfoDataM();
			ApplicationDataM application = new ApplicationDataM();
			logger.debug("personalApp : "+personalApp);
			application.setExistingPriorityPass(existingAssignedCount);
			
			JsonObject personalAppObject = gson.fromJson(personalApp, JsonObject.class);
			personalInfo.setPersonalType(personalAppObject.get("idType_input").getAsString());
			personalInfo.setPersonalId(personalAppObject.get("idNumber_input").getAsString());
			personalInfo.setIdno(personalAppObject.get("cisID_input").getAsString());
			ficoRequest.setApplicationType(personalAppObject.get("applicantType_input").getAsString());
			personalInfo.setDegree(personalAppObject.get("educationLevel_input").getAsString());
			personalInfo.setkGroupFlag(personalAppObject.get("isKGroupStaff_input").getAsString());
			
			ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices = new ArrayList<>();
				SpecialAdditionalServiceDataM specialAdditionalService = new SpecialAdditionalServiceDataM();
				specialAdditionalService.setCompleteData(personalAppObject.get("isInformationComplete_input").getAsString());
			
			personalInfo.setCisPrimSegment(personalAppObject.get("cisPrimarySegment_input").getAsString());
			personalInfo.setCisPrimSubSegment(personalAppObject.get("cisPrimarySubSegment_input").getAsString());
			personalInfo.setCisSecSegment(personalAppObject.get("cisSecondarySegment_input").getAsString());
			personalInfo.setCisSecSubSegment(personalAppObject.get("cisSecondarySubSegment_input").getAsString());
			personalInfo.setReceiveIncomeMethod(personalAppObject.get("incomeReceiveCode_input").getAsString());
			
			JsonObject documentSlaType = personalAppObject.getAsJsonObject("documentSlaType");
			
			ArrayList<ReasonDataM> reasons = new ArrayList<>();
				ReasonDataM reason = new ReasonDataM();
				reason.setReasonCode(documentSlaType.get("code_input").getAsString());
			reasons.add(reason);
			application.setReasons(reasons);
			
			JsonElement previousEmploymentList = personalAppObject.get("previousEmployment");
			for(JsonElement previousEmployment : previousEmploymentList.getAsJsonArray()){
				JsonObject previousEmploymentObj = previousEmployment.getAsJsonObject();
				if(!previousEmploymentObj.get("previousEmploymentYears_input").getAsString().equals(""))
					personalInfo.setPrevWorkYear(new BigDecimal(previousEmploymentObj.get("previousEmploymentYears_input").getAsString()));
				if(!previousEmploymentObj.get("previousEmploymentMonths_input").getAsString().equals(""))
					personalInfo.setPrevWorkMonth(new BigDecimal(previousEmploymentObj.get("previousEmploymentMonths_input").getAsString()));
			}

			JsonObject idAddress = personalAppObject.getAsJsonObject("idAddress");
			ArrayList<AddressDataM> addresses = new ArrayList<>();
			AddressDataM address1 = new AddressDataM();
			address1.setAddress(idAddress.get("IDAddress_address_houseNumber_input").getAsString());
			address1.setVilapt(idAddress.get("IDAddress_address_village_input").getAsString());
			address1.setBuilding(idAddress.get("IDAddress_address_buildingName_input").getAsString());
			address1.setTambol(idAddress.get("IDAddress_address_subDistrict_input").getAsString());
			address1.setAmphur(idAddress.get("IDAddress_address_district_input").getAsString());
			address1.setProvinceDesc(idAddress.get("IDAddress_address_province_input").getAsString());
			if(!idAddress.get("IDAddress_address_postalCode_input").getAsString().equals(""))
				address1.setZipcode(idAddress.get("IDAddress_address_postalCode_input").getAsString());
			address1.setCountry(idAddress.get("IDAddress_address_country_input").getAsString());
			address1.setPhone1(idAddress.get("IDAddress_address_fixedLineNumber_input").getAsString());
			address1.setFloor(idAddress.get("IDAddress_address_floorNumber_input").getAsString());
			address1.setMoo(idAddress.get("IDAddress_address_moo_input").getAsString());
			address1.setSoi(idAddress.get("IDAddress_address_soi_input").getAsString());
			address1.setRoad(idAddress.get("IDAddress_address_street_input").getAsString());
			addresses.add(address1);
			
			JsonObject currentResidence = personalAppObject.getAsJsonObject("currentResidence");
			AddressDataM address2 = new AddressDataM();
			if(!currentResidence.get("currentResidence_monthsAtAddress_input").getAsString().equals(""))
				address2.setResidem(new BigDecimal(currentResidence.get("currentResidence_monthsAtAddress_input").getAsString()));
			if(!currentResidence.get("currentResidence_yearsAtAddress_input").getAsString().equals(""))
				address2.setResidey(new BigDecimal(currentResidence.get("currentResidence_yearsAtAddress_input").getAsString()));
			address2.setAdrsts(currentResidence.get("currentResidence_currentResidentialStatus_input").getAsString());
			address2.setAddress(currentResidence.get("currentResidence_address_houseNumber_input").getAsString());
			address2.setVilapt(currentResidence.get("currentResidence_address_village_input").getAsString());
			address2.setBuilding(currentResidence.get("currentResidence_address_buildingName_input").getAsString());
			address2.setTambol(currentResidence.get("currentResidence_address_subDistrict_input").getAsString());
			address2.setAmphur(currentResidence.get("currentResidence_address_district_input").getAsString());
			address2.setProvinceDesc(currentResidence.get("currentResidence_address_province_input").getAsString());
			if(!currentResidence.get("currentResidence_address_postalCode_input").getAsString().equals(""))
				address2.setZipcode(currentResidence.get("currentResidence_address_postalCode_input").getAsString());
			address2.setCountry(currentResidence.get("currentResidence_address_country_input").getAsString());
			address2.setPhone1(currentResidence.get("currentResidence_address_fixedLineNumber_input").getAsString());
			address2.setAddressType(currentResidence.get("currentResidence_address_type_input").getAsString());
			address2.setFloor(currentResidence.get("currentResidence_address_floorNumber_input").getAsString());
			address2.setMoo(currentResidence.get("currentResidence_address_moo_input").getAsString());
			address2.setSoi(currentResidence.get("currentResidence_address_soi_input").getAsString());
			address2.setRoad(currentResidence.get("currentResidence_address_street_input").getAsString());
			addresses.add(address2);
			personalInfo.setAddresses(addresses);
			
			JsonObject person = personalAppObject.getAsJsonObject("person");
			if(!person.get("person_birthDate_input").getAsString().equals(""))
				personalInfo.setBirthDate(Date.valueOf(person.get("person_birthDate_input").getAsString()));
			if(!person.get("person_numberOfDependents_input").getAsString().equals(""))
				personalInfo.setNoOfChild(new BigDecimal(person.get("person_numberOfDependents_input").getAsString()));
			personalInfo.setMarried(person.get("person_maritalStatus_input").getAsString());
			personalInfo.setMobileNo(person.get("person_phoneNumber_input").getAsString());
			personalInfo.setNationality(person.get("person_nationality_input").getAsString());
			ArrayList<DebtInfoDataM> debtInfos = new ArrayList<>();
			if(!person.get("otherDebt_commercial_amount_input").getAsString().equals("")){
				DebtInfoDataM debtInfo1 = new DebtInfoDataM();
				debtInfo1.setDebtType("02");
				debtInfo1.setDebtAmt(new BigDecimal(person.get("otherDebt_commercial_amount_input").getAsString()));
				debtInfos.add(debtInfo1);
			}
			if(!person.get("otherDebt_commercial_amount_input").getAsString().equals("")){
				DebtInfoDataM debtInfo2 = new DebtInfoDataM();
				debtInfo2.setDebtType("01");
				debtInfo2.setDebtType(person.get("otherDebt_welfate_amount_input").getAsString());
				debtInfos.add(debtInfo2);
			}
			if(!person.get("otherDebt_other_debt_amount_input").getAsString().equals("")){
				DebtInfoDataM debtInfo3 = new DebtInfoDataM();
				debtInfo3.setDebtType("99");
				debtInfo3.setDebtType(person.get("otherDebt_other_debt_amount_input").getAsString());
				debtInfos.add(debtInfo3);
			}
			personalInfo.setDebtInfos(debtInfos);
			
			personalInfo.setThTitleCode(person.get("person_name_prefix_input").getAsString());
			personalInfo.setThFirstName(person.get("person_name_first_input").getAsString());
			personalInfo.setThMidName(person.get("person_name_middle_input").getAsString());
			personalInfo.setThLastName(person.get("person_name_last_input").getAsString());
			
			JsonElement currentEmploymentList = personalAppObject.get("currentEmployment");
			
			for(JsonElement currentEmployment : currentEmploymentList.getAsJsonArray()){
				JsonObject currentEmploymentObj = currentEmployment.getAsJsonObject();
				currentEmploymentObj.get("currentEmployment_employment_monthsWithEmployer_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_occupationCode_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_professionCode_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_positionType_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerTitleCode_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_currentEmploymentYears_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_companyName_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_houseNumber_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_village_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_buildingName_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_subDistrict_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_district_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_province_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_postalCode_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_country_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_fixedLineNumber_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_type_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_floorNumber_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_moo_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_soi_input").getAsString();
				currentEmploymentObj.get("currentEmployment_employment_employerAddress_address_street_input").getAsString();
			}
			
			JsonObject response = personalAppObject.getAsJsonObject("response");
			VerificationResultDataM verificationResult = new VerificationResultDataM();
			verificationResult.setVerHrResultCode(response.get("HRVerificationResponse_HRVerificationResult_input").getAsString());
			if(!response.get("HRVerificationResponse_bonus_input").getAsString().equals(""))
				personalInfo.setHrBonus(new BigDecimal(response.get("HRVerificationResponse_bonus_input").getAsString()));
//			response.get("HRVerificationResponse_otherVariableIncome_input").getAsString();
			if(!response.get("HRVerificationResponse_verifiedIncome_input").getAsString().equals(""))
				personalInfo.setHrIncome(new BigDecimal(response.get("HRVerificationResponse_verifiedIncome_input").getAsString()));
			verificationResult.setVerCusResultCode(response.get("customerVerificationResponse_customerVerificationResult_input").getAsString());
			
			JsonElement websiteCode = response.get("websiteVerificationResponse_websiteCode_input");
			JsonElement websiteVerificationResult = response.get("websiteVerificationResponse_websiteVerificationResult_input");
			JsonObject website = new JsonObject();
			website.add("websiteCode", websiteCode);
			website.add("websiteVerificationResult", websiteVerificationResult);
			ArrayList<WebVerificationDataM> webVerifications = new ArrayList<>();
			if(website.get("websiteCode").isJsonArray()){
				int websiteLength = website.get("websiteCode").getAsJsonArray().size();
				for(int i=0;i<websiteLength;i++){
					WebVerificationDataM webVerification = new WebVerificationDataM();
					webVerification.setWebCode(website.get("websiteCode").getAsJsonArray().get(i).getAsString());
					webVerification.setVerResultId(website.get("websiteVerificationResult").getAsJsonArray().get(i).getAsString());
					webVerifications.add(webVerification);
				}
			}else{
				WebVerificationDataM webVerification = new WebVerificationDataM();
				webVerification.setWebCode(website.get("websiteCode").getAsString());
				webVerification.setVerResultId(website.get("websiteVerificationResult").getAsString());
				webVerifications.add(webVerification);
			}
			verificationResult.setWebVerifications(webVerifications);
			personalInfo.setVerificationResult(verificationResult);
			
			ArrayList<IncomeInfoDataM> incomeInfos = new ArrayList<>();
			JsonElement incInfoList = personalAppObject.get("incInfo");
			logger.debug("incInfoList : "+incInfoList);
			for(JsonElement incInfo : incInfoList.getAsJsonArray()){
				IncomeInfoDataM incomeInfo = new IncomeInfoDataM();
				JsonObject incInfoObj = incInfo.getAsJsonObject();
				logger.debug("incInfo : "+incInfo);
				incomeInfo.setIncomeType(incInfoObj.get("inc_info_income_type_input").getAsString());
				JsonElement payslipList = incInfoObj.get("payslip");
				ArrayList<IncomeCategoryDataM> allIncomes = new ArrayList<>();
				ArrayList<PayslipDataM> payslips = new ArrayList<>();
				String INC_TYPE_PAYSLIP = ServiceCache.getConstant("INC_TYPE_PAYSLIP");
				for(JsonElement payslip : payslipList.getAsJsonArray()){
					logger.debug("payslip : "+payslip);
					PayslipDataM payslipM = new PayslipDataM();
					JsonObject payslipObj = payslip.getAsJsonObject();
					logger.debug("INC_TYPE_PAYSLIP : "+INC_TYPE_PAYSLIP);
					payslipM.setIncomeType(INC_TYPE_PAYSLIP);
					payslipM.setNoMonth(payslipObj.get("inc_payslip_no_month_input").getAsString().equals("") ? 0 
							: Integer.parseInt(payslipObj.get("inc_payslip_no_month_input").getAsString()));
//					payslipM.setAccumIncome(payslipObj.get("inc_payslip_accum_income_input").getAsString());
					payslipM.setBonus(payslipObj.get("inc_payslip_bonus_input").getAsString().equals("") ? null 
							: new BigDecimal(payslipObj.get("inc_payslip_bonus_input").getAsString()));
					payslipM.setSumSso(payslipObj.get("inc_payslip_sum_sso_input").getAsString().equals("") ? null 
							: new BigDecimal(payslipObj.get("inc_payslip_sum_sso_input").getAsString()));
					payslipM.setSpacialPension(payslipObj.get("inc_payslip_special_pension_input").getAsString().equals("") ? null : new BigDecimal(payslipObj.get("inc_payslip_special_pension_input").getAsString()));
//					payslipM.setAccumOthIncome(payslipObj.get("inc_payslip_accum_oth_income_input").getAsString());
					payslipM.setBonusMonth(payslipObj.get("inc_payslip_bonus_month_input").getAsString());
					payslipM.setBonusYear(payslipObj.get("inc_payslip_bonus_year_input").getAsString());
					JsonElement monthlyList = payslipObj.get("monthly");
					
					ArrayList<PayslipMonthlyDataM> payslipMonthlys = new ArrayList<>();
					for(JsonElement monthly : monthlyList.getAsJsonArray()){
						PayslipMonthlyDataM payslipMonthly = new PayslipMonthlyDataM();
						logger.debug("monthly : "+monthly);
						JsonObject monthlyObj = monthly.getAsJsonObject();
						payslipMonthly.setIncomeType(monthlyObj.get("inc_payslip_monthly_income_type_input").getAsString());
						payslipMonthly.setIncomeDesc(monthlyObj.get("inc_payslip_monthly_income_desc_input").getAsString());
						payslipMonthly.setIncomeOthDesc(monthlyObj.get("inc_payslip_monthly_income_oth_desc_input").getAsString());
						JsonObject detailObj = monthlyObj.get("detail").getAsJsonObject();
						JsonElement month = detailObj.get("inc_payslip_monthly_detail_month_input");
						JsonElement year = detailObj.get("inc_payslip_monthly_detail_year_input");
						JsonElement amount = detailObj.get("inc_payslip_monthly_detail_amount_input");
						ArrayList<PayslipMonthlyDetailDataM> payslipMonthlyDetails = new ArrayList<>();
						if(month.isJsonArray()){
							int detailLength = month.getAsJsonArray().size();
							for(int i=0;i<detailLength;i++){
								PayslipMonthlyDetailDataM payslipMonthlyDetail = new PayslipMonthlyDetailDataM();
								payslipMonthlyDetail.setMonth(month.getAsJsonArray().get(i).getAsString());
								payslipMonthlyDetail.setYear(year.getAsJsonArray().get(i).getAsString());
								payslipMonthlyDetail.setAmount(amount.getAsJsonArray().get(i).getAsString().equals("") ? null 
										: new BigDecimal(amount.getAsJsonArray().get(i).getAsString()) );
								payslipMonthlyDetails.add(payslipMonthlyDetail);
							}
						}else{
							PayslipMonthlyDetailDataM payslipMonthlyDetail = new PayslipMonthlyDetailDataM();
							payslipMonthlyDetail.setMonth(month.getAsString());
							payslipMonthlyDetail.setYear(year.getAsString());
							payslipMonthlyDetail.setAmount(amount.getAsString().equals("") ? null : new BigDecimal(amount.getAsString()));
							payslipMonthlyDetails.add(payslipMonthlyDetail);
						}
						payslipMonthly.setPayslipMonthlyDetails(payslipMonthlyDetails);
						payslipMonthlys.add(payslipMonthly);
					}
					payslipM.setPayslipMonthlys(payslipMonthlys);
					payslips.add(payslipM);
				}
				allIncomes.addAll(payslips);
				
				ArrayList<YearlyTawi50DataM> yearlyTawi50s = new ArrayList<>();
				JsonObject yearlyTawi = incInfoObj.getAsJsonObject("yearlyTawi");
				JsonElement companyName = yearlyTawi.get("inc_yearly_tawi_company_name_input");
				JsonElement year = yearlyTawi.get("inc_yearly_tawi_year_input");
				JsonElement noMonth = yearlyTawi.get("inc_yearly_tawi_no_month_input");
				JsonElement income40_1 = yearlyTawi.get("inc_yearly_tawi_income40_1_input");
				JsonElement income40_2 = yearlyTawi.get("inc_yearly_tawi_income40_2_input");
				JsonElement sumSSO = yearlyTawi.get("inc_yearly_tawi_sum_sso_input");
				String INC_TYPE_YEARLY_50TAWI = ServiceCache.getConstant("INC_TYPE_YEARLY_50TAWI");
				if(companyName.isJsonArray()){
					int yearlyTawiLength = companyName.getAsJsonArray().size();
					for(int i=0;i<yearlyTawiLength;i++){
						YearlyTawi50DataM yearlyTawi50M = new YearlyTawi50DataM();
						yearlyTawi50M.setIncomeType(INC_TYPE_YEARLY_50TAWI);
						yearlyTawi50M.setCompanyName(companyName.getAsJsonArray().get(i).getAsString());
						yearlyTawi50M.setYear(year.getAsJsonArray().get(i).getAsString().equals("") ? 0 
								: Integer.parseInt(year.getAsJsonArray().get(i).getAsString()));
						yearlyTawi50M.setMonth(noMonth.getAsJsonArray().get(i).getAsString().equals("") ? 0 
								: Integer.parseInt(noMonth.getAsJsonArray().get(i).getAsString()));
						yearlyTawi50M.setIncome401(income40_1.getAsJsonArray().get(i).getAsString().equals("") ? null : new BigDecimal(income40_1.getAsJsonArray().get(i).getAsString()));
						yearlyTawi50M.setIncome402(income40_2.getAsJsonArray().get(i).getAsString().equals("") ? null : new BigDecimal(income40_2.getAsJsonArray().get(i).getAsString()));
						yearlyTawi50M.setSumSso(sumSSO.getAsJsonArray().get(i).getAsString().equals("") ? null : new BigDecimal(sumSSO.getAsJsonArray().get(i).getAsString()));
						yearlyTawi50s.add(yearlyTawi50M);
					}
				}else{
					YearlyTawi50DataM yearlyTawi50M = new YearlyTawi50DataM();
					yearlyTawi50M.setIncomeType(INC_TYPE_YEARLY_50TAWI);
					yearlyTawi50M.setCompanyName(companyName.getAsString());
					yearlyTawi50M.setYear(year.getAsString().equals("") ? 0 : Integer.parseInt(year.getAsString()));
					yearlyTawi50M.setMonth(noMonth.getAsString().equals("") ? 0 : Integer.parseInt(noMonth.getAsString()));
					yearlyTawi50M.setIncome401(income40_1.getAsString().equals("") ? null : new BigDecimal(income40_1.getAsString()));
					yearlyTawi50M.setIncome402(income40_2.getAsString().equals("") ? null : new BigDecimal(income40_2.getAsString()));
					yearlyTawi50M.setSumSso(sumSSO.getAsString().equals("") ? null : new BigDecimal(sumSSO.getAsString()));
					yearlyTawi50s.add(yearlyTawi50M);
				}
				allIncomes.addAll(yearlyTawi50s);
				
				ArrayList<TaweesapDataM> taweesaps = new ArrayList<>();
				String INC_TYPE_TAWEESAB = ServiceCache.getConstant("INC_TYPE_TAWEESAB");
				TaweesapDataM taweesapM = new TaweesapDataM();
				JsonObject taweesap = incInfoObj.getAsJsonObject("taweesap");
				taweesapM.setIncomeType(INC_TYPE_TAWEESAB);
				taweesapM.setAum(taweesap.get("inc_taweesap_aum_input").getAsString().equals("") ? null : new BigDecimal(taweesap.get("inc_taweesap_aum_input").getAsString()));
				taweesaps.add(taweesapM);
				allIncomes.addAll(taweesaps);
				
				ArrayList<SavingAccountDataM> savingAccounts = new ArrayList<>();
				String INC_TYPE_SAVING_ACCOUNT = ServiceCache.getConstant("INC_TYPE_SAVING_ACCOUNT");
				if(incInfoObj.get("savingAcc").isJsonArray()){
					for(JsonElement savingAcc : incInfoObj.getAsJsonArray("savingAcc")){
						SavingAccountDataM savingAccountM = new SavingAccountDataM();
						JsonObject savingAccObj = savingAcc.getAsJsonObject();
						savingAccountM.setIncomeType(INC_TYPE_SAVING_ACCOUNT);
						savingAccountM.setOpenDate(savingAccObj.get("inc_saving_acc_open_date_input").getAsString().equals("") 
								? null : Date.valueOf(savingAccObj.get("inc_saving_acc_open_date_input").getAsString()));
						savingAccountM.setAccountNo(savingAccObj.get("inc_saving_acc_account_no_input").getAsString());
						savingAccountM.setAccountName(savingAccObj.get("inc_saving_acc_account_name_input").getAsString());
						savingAccountM.setHoldingRatio(savingAccObj.get("inc_saving_acc_holding_ratio_input").getAsString().equals("")
								? null : new BigDecimal(savingAccObj.get("inc_saving_acc_holding_ratio_input").getAsString()));
						savingAccountM.setBankCode(savingAccObj.get("inc_saving_acc_bank_code_input").getAsString());
						if(savingAccObj.get("detail").isJsonArray()){
							ArrayList<SavingAccountDetailDataM> savingAccountDetails = new ArrayList<>();
							for(JsonElement detail : savingAccObj.getAsJsonArray("detail")){
								SavingAccountDetailDataM savingAccountDetailM = new SavingAccountDetailDataM();
								JsonObject detailObj = detail.getAsJsonObject();
								savingAccountDetailM.setMonth(detailObj.get("inc_saving_acc_inc_saving_acc_detail_month_input").getAsString());
								savingAccountDetailM.setYear(detailObj.get("inc_saving_acc_inc_saving_acc_detail_year_input").getAsString());
								savingAccountDetailM.setAmount(detailObj.get("inc_saving_acc_inc_saving_acc_detail_amount_input").getAsString().equals("") 
										? null : new BigDecimal(detailObj.get("inc_saving_acc_inc_saving_acc_detail_amount_input").getAsString()));
								savingAccountDetails.add(savingAccountDetailM);
							}
							savingAccountM.setSavingAccountDetails(savingAccountDetails);
							savingAccounts.add(savingAccountM);
						}else{
							ArrayList<SavingAccountDetailDataM> savingAccountDetails = new ArrayList<>();
							SavingAccountDetailDataM savingAccountDetailM = new SavingAccountDetailDataM();
							JsonObject detailObj = savingAccObj.getAsJsonObject("detail");
							savingAccountDetailM.setMonth(detailObj.get("inc_saving_acc_inc_saving_acc_detail_month_input").getAsString());
							savingAccountDetailM.setYear(detailObj.get("inc_saving_acc_inc_saving_acc_detail_year_input").getAsString());
							savingAccountDetailM.setAmount(detailObj.get("inc_saving_acc_inc_saving_acc_detail_amount_input").getAsString().equals("") 
									? null : new BigDecimal(detailObj.get("inc_saving_acc_inc_saving_acc_detail_amount_input").getAsString()));
							savingAccountDetails.add(savingAccountDetailM);
							savingAccountM.setSavingAccountDetails(savingAccountDetails);
							savingAccounts.add(savingAccountM);
						}
					}
				}else{
					SavingAccountDataM savingAccountM = new SavingAccountDataM();
					JsonObject savingAccObj = incInfoObj.getAsJsonObject("savingAcc");
					savingAccountM.setIncomeType(INC_TYPE_SAVING_ACCOUNT);
					savingAccountM.setOpenDate(savingAccObj.get("inc_saving_acc_open_date_input").getAsString().equals("") 
							? null : Date.valueOf(savingAccObj.get("inc_saving_acc_open_date_input").getAsString()));
					savingAccountM.setAccountNo(savingAccObj.get("inc_saving_acc_account_no_input").getAsString());
					savingAccountM.setAccountName(savingAccObj.get("inc_saving_acc_account_name_input").getAsString());
					savingAccountM.setHoldingRatio(savingAccObj.get("inc_saving_acc_holding_ratio_input").getAsString().equals("")
							? null : new BigDecimal(savingAccObj.get("inc_saving_acc_holding_ratio_input").getAsString()));
					savingAccountM.setBankCode(savingAccObj.get("inc_saving_acc_bank_code_input").getAsString());
					if(savingAccObj.get("detail").isJsonArray()){
						ArrayList<SavingAccountDetailDataM> savingAccountDetails = new ArrayList<>();
						for(JsonElement detail : savingAccObj.getAsJsonArray("detail")){
							SavingAccountDetailDataM savingAccountDetailM = new SavingAccountDetailDataM();
							JsonObject detailObj = detail.getAsJsonObject();
							savingAccountDetailM.setMonth(detailObj.get("inc_saving_acc_inc_saving_acc_detail_month_input").getAsString());
							savingAccountDetailM.setYear(detailObj.get("inc_saving_acc_inc_saving_acc_detail_year_input").getAsString());
							savingAccountDetailM.setAmount(detailObj.get("inc_saving_acc_inc_saving_acc_detail_amount_input").getAsString().equals("") 
									? null : new BigDecimal(detailObj.get("inc_saving_acc_inc_saving_acc_detail_amount_input").getAsString()));
							savingAccountDetails.add(savingAccountDetailM);
						}
						savingAccountM.setSavingAccountDetails(savingAccountDetails);
						savingAccounts.add(savingAccountM);
					}else{
						ArrayList<SavingAccountDetailDataM> savingAccountDetails = new ArrayList<>();
						SavingAccountDetailDataM savingAccountDetailM = new SavingAccountDetailDataM();
						JsonObject detailObj = savingAccObj.getAsJsonObject("detail");
						savingAccountDetailM.setMonth(detailObj.get("inc_saving_acc_inc_saving_acc_detail_month_input").getAsString());
						savingAccountDetailM.setYear(detailObj.get("inc_saving_acc_inc_saving_acc_detail_year_input").getAsString());
						savingAccountDetailM.setAmount(detailObj.get("inc_saving_acc_inc_saving_acc_detail_amount_input").getAsString().equals("") 
								? null : new BigDecimal(detailObj.get("inc_saving_acc_inc_saving_acc_detail_amount_input").getAsString()));
						savingAccountDetails.add(savingAccountDetailM);
						savingAccountM.setSavingAccountDetails(savingAccountDetails);
						savingAccounts.add(savingAccountM);
					}
				}
				allIncomes.addAll(savingAccounts);
				
				ArrayList<SalaryCertDataM> salaryCerts = new ArrayList<>();
				JsonObject salaryCertObj = incInfoObj.getAsJsonObject("salaryCert");
				JsonElement income = salaryCertObj.get("inc_salary_cert_income_input");
				JsonElement salaryCertCompanyName = salaryCertObj.get("inc_salary_cert_company_name_input");
				String INC_TYPE_SALARY_CERT = ServiceCache.getConstant("INC_TYPE_SALARY_CERT");
				if(income.isJsonArray()){
					int salaryCertLength = income.getAsJsonArray().size();
					for(int i=0;i<salaryCertLength;i++){
						SalaryCertDataM salaryCertM = new SalaryCertDataM();
						salaryCertM.setIncomeType(INC_TYPE_SALARY_CERT);
						salaryCertM.setIncome(income.getAsJsonArray().get(i).getAsString().equals("") ? null 
								: new BigDecimal(income.getAsJsonArray().get(i).getAsString()));
						salaryCertM.setCompanyName(salaryCertCompanyName.getAsJsonArray().get(i).getAsString().equals("") ? null 
								: salaryCertCompanyName.getAsJsonArray().get(i).getAsString());
						salaryCerts.add(salaryCertM);
					}
				}else{
					SalaryCertDataM salaryCertM = new SalaryCertDataM();
					salaryCertM.setIncomeType(INC_TYPE_SALARY_CERT);
					salaryCertM.setIncome(income.getAsString().equals("") ? null : new BigDecimal(income.getAsString()));
					salaryCertM.setCompanyName(salaryCertCompanyName.getAsString());
				}
				allIncomes.addAll(salaryCerts);
				
				ArrayList<PreviousIncomeDataM> previousIncomes = new ArrayList<>();
				String INC_TYPE_PREVIOUS_INCOME = ServiceCache.getConstant("INC_TYPE_PREVIOUS_INCOME");
				PreviousIncomeDataM previousIncomeM = new PreviousIncomeDataM();
				JsonObject previousIncomeObj = incInfoObj.getAsJsonObject("previousIncome");
				previousIncomeM.setIncomeType(INC_TYPE_PREVIOUS_INCOME);
				previousIncomeM.setIncome(previousIncomeObj.get("inc_previous_income_income_input").getAsString().equals("") ? null 
						: new BigDecimal(previousIncomeObj.get("inc_previous_income_income_input").getAsString()));
				previousIncomes.add(previousIncomeM);
				allIncomes.addAll(previousIncomes);
				
				ArrayList<PayrollDataM> payRolls = new ArrayList<>();
				String INC_TYPE_PAYROLL = ServiceCache.getConstant("INC_TYPE_PAYROLL");
				PayrollDataM payRollM = new PayrollDataM();
				JsonObject payrollObj = incInfoObj.getAsJsonObject("payroll");
				payRollM.setIncomeType(INC_TYPE_PAYROLL);
				payRollM.setIncome(payrollObj.get("inc_payroll_lowestIncome_input").getAsString().equals("") ? null 
						: new BigDecimal(payrollObj.get("inc_payroll_lowestIncome_input").getAsString()));
				payRollM.setNoOfEmployee(payrollObj.get("inc_payroll_no_of_employee_input").getAsString().equals("") ? 0
						: Integer.parseInt(payrollObj.get("inc_payroll_no_of_employee_input").getAsString()));
				payRolls.add(payRollM);
				allIncomes.addAll(payRolls);
				
				ArrayList<OpenedEndFundDataM> opendEndFounds = new ArrayList<>();
				String INC_TYPE_OPENED_END_FUND = ServiceCache.getConstant("INC_TYPE_OPENED_END_FUND");
				JsonElement opnEndFundObjs = incInfoObj.get("opnEndFund");
				for(JsonElement opnEndFund : opnEndFundObjs.getAsJsonArray()){
					logger.debug("opnEndFund : "+opnEndFund);
					OpenedEndFundDataM opendEndFoundM = new OpenedEndFundDataM();
					JsonObject opnEndFundObj = opnEndFund.getAsJsonObject();
					opendEndFoundM.setIncomeType(INC_TYPE_OPENED_END_FUND);
					opendEndFoundM.setOpenDate(opnEndFundObj.get("inc_opn_end_fund_open_date_input").getAsString().equals("") ? null 
							: Date.valueOf(opnEndFundObj.get("inc_opn_end_fund_open_date_input").getAsString()));
					opendEndFoundM.setAccountNo(opnEndFundObj.get("inc_opn_end_fund_account_no_input").getAsString());
					opendEndFoundM.setAccountName(opnEndFundObj.get("inc_opn_end_fund_account_name_input").getAsString());
					opendEndFoundM.setHoldingRatio(opnEndFundObj.get("inc_opn_end_fund_holding_ratio_input").getAsString().equals("") 
							? null : new BigDecimal(opnEndFundObj.get("inc_opn_end_fund_holding_ratio_input").getAsString()));
					opendEndFoundM.setBankCode(opnEndFundObj.get("inc_opn_end_fund_bank_code_input").getAsString());
					opendEndFoundM.setFundName(opnEndFundObj.get("inc_opn_end_fund_fund_name_input").getAsString());
					
					ArrayList<OpenedEndFundDetailDataM> openEndFundDetails = new ArrayList<>();
					JsonObject detail = opnEndFundObj.get("detail").getAsJsonObject();
					JsonElement detailMonth = detail.get("inc_opn_end_fund_detail_month_input");
					JsonElement detailYear = detail.get("inc_opn_end_fund_detail_year_input");
					JsonElement detailAmount = detail.get("inc_opn_end_fund_detail_amount_input");
					if(detailMonth.isJsonArray()){
						int detailLength = detailMonth.getAsJsonArray().size();
						for(int i=0;i<detailLength;i++){
							OpenedEndFundDetailDataM openEndFundDetailM = new OpenedEndFundDetailDataM();
							openEndFundDetailM.setMonth(detailMonth.getAsJsonArray().get(i).getAsString());
							openEndFundDetailM.setYear(detailYear.getAsJsonArray().get(i).getAsString());
							openEndFundDetailM.setAmount(detailAmount.getAsJsonArray().get(i).getAsString().equals("") ? null 
									: new BigDecimal(detailAmount.getAsJsonArray().get(i).getAsString()));
							openEndFundDetails.add(openEndFundDetailM);
						}
					}else{
						OpenedEndFundDetailDataM openEndFundDetailM = new OpenedEndFundDetailDataM();
						openEndFundDetailM.setMonth(detailMonth.getAsString());
						openEndFundDetailM.setYear(detailYear.getAsString());
						openEndFundDetailM.setAmount(detailAmount.getAsString().equals("") ? null 
								: new BigDecimal(detailAmount.getAsString()));
						openEndFundDetails.add(openEndFundDetailM);
					}
					opendEndFounds.add(opendEndFoundM);
				}
				allIncomes.addAll(opendEndFounds);

				ArrayList<MonthlyTawi50DataM> monthlyTawi50s = new ArrayList<>();
				String INC_TYPE_MONTHLY_50TAWI = ServiceCache.getConstant("INC_TYPE_MONTHLY_50TAWI");
				JsonElement monthlyTawiObjs = incInfoObj.get("monthlyTawi");
				for(JsonElement monthlyTawi : monthlyTawiObjs.getAsJsonArray()){
					logger.debug("monthlyTawi : "+monthlyTawi);
					MonthlyTawi50DataM monthlyTawi50M = new MonthlyTawi50DataM();
					JsonObject monthlyTawiObj = monthlyTawi.getAsJsonObject();
					
					monthlyTawi50M.setIncomeType(INC_TYPE_MONTHLY_50TAWI);
					monthlyTawi50M.setCompanyName(monthlyTawiObj.get("inc_monthly_tawi_company_name_input").getAsString());
					monthlyTawi50M.setCompareFlag(monthlyTawiObj.get("inc_monthly_tawi_compare_flag_input").getAsString());
					
					ArrayList<MonthlyTawi50DetailDataM> monthlyTaxi50Details = new ArrayList<>();
					JsonObject detail = monthlyTawiObj.get("detail").getAsJsonObject();
					JsonElement detailMonth = detail.get("inc_monthly_tawi_detail_month_input");
					JsonElement detailYear = detail.get("inc_monthly_tawi_detail_year_input");
					JsonElement detailAmount = detail.get("inc_monthly_tawi_detail_amount_input");
					if(detailMonth.isJsonArray()){
						int detailLength = detailMonth.getAsJsonArray().size();
						for(int i=0;i<detailLength;i++){
							MonthlyTawi50DetailDataM monthlyTaxi50DetailM = new MonthlyTawi50DetailDataM();
							monthlyTaxi50DetailM.setMonth(detailMonth.getAsJsonArray().get(i).getAsString());
							monthlyTaxi50DetailM.setYear(detailYear.getAsJsonArray().get(i).getAsString());
							monthlyTaxi50DetailM.setAmount(detailAmount.getAsJsonArray().get(i).getAsString().equals("") 
									? null : new BigDecimal(detailAmount.getAsJsonArray().get(i).getAsString()));
							monthlyTaxi50Details.add(monthlyTaxi50DetailM);
						}
					}else{
						MonthlyTawi50DetailDataM monthlyTaxi50DetailM = new MonthlyTawi50DetailDataM();
						monthlyTaxi50DetailM.setMonth(detailMonth.getAsString());
						monthlyTaxi50DetailM.setYear(detailYear.getAsString());
						monthlyTaxi50DetailM.setAmount(detailAmount.getAsString().equals("") ? null 
								: new BigDecimal(detailAmount.getAsString()));
						monthlyTaxi50Details.add(monthlyTaxi50DetailM);
					}
					monthlyTawi50s.add(monthlyTawi50M);
				}
				allIncomes.addAll(monthlyTawi50s);
				
				ArrayList<KVIIncomeDataM> kviIncomes = new ArrayList<>();
				String INC_TYPE_KVI_INCOME = ServiceCache.getConstant("INC_TYPE_KVI_INCOME");
				KVIIncomeDataM kviIncomeM = new KVIIncomeDataM();
				JsonObject kviObj = incInfoObj.getAsJsonObject("kvi");
				kviIncomeM.setIncomeType(INC_TYPE_KVI_INCOME);
				kviIncomeM.setPercentChequeReturn(kviObj.get("inc_kvi_percent_cheque_return_input").getAsString().equals("") ? null 
						: new BigDecimal(kviObj.get("inc_kvi_percent_cheque_return_input").getAsString()));
				kviIncomeM.setVerifiedIncome(kviObj.get("inc_kvi_verified_income_input").getAsString().equals("") ? null 
						: new BigDecimal(kviObj.get("inc_kvi_verified_income_input").getAsString()));
				kviIncomes.add(kviIncomeM);
				allIncomes.addAll(kviIncomes);
				
				ArrayList<FixedGuaranteeDataM> fixedGuarantees = new ArrayList<>();
				String INC_TYPE_FIXED_GUARANTEE = ServiceCache.getConstant("INC_TYPE_FIXED_GUARANTEE");
				FixedGuaranteeDataM fixedGuaranteeM = new FixedGuaranteeDataM();
				JsonObject fixedGuaranteeObj = incInfoObj.getAsJsonObject("fixedGuarantee");
				fixedGuaranteeM.setIncomeType(INC_TYPE_FIXED_GUARANTEE);
				fixedGuaranteeM.setAccountNo(fixedGuaranteeObj.get("inc_fixed_guarantee_account_no_input").getAsString());
				fixedGuaranteeM.setSub(fixedGuaranteeObj.get("inc_fixed_guarantee_sub_input").getAsString());
				fixedGuaranteeM.setAccountName(fixedGuaranteeObj.get("inc_fixed_guarantee_account_name_input").getAsString());
				fixedGuaranteeM.setRetentionDate(fixedGuaranteeObj.get("inc_fixed_guarantee_retention_date_input").getAsString().equals("") 
						? null : Date.valueOf(fixedGuaranteeObj.get("inc_fixed_guarantee_retention_date_input").getAsString()));
				fixedGuaranteeM.setBranchNo(fixedGuaranteeObj.get("inc_fixed_guarantee_branch_no_input").getAsString());
				fixedGuaranteeM.setRetentionType(fixedGuaranteeObj.get("inc_fixed_guarantee_rentention_type_input").getAsString());
				fixedGuaranteeM.setRetentionAmt(fixedGuaranteeObj.get("inc_fixed_guarantee_rentention_amt_input").getAsString().equals("")
						? null : new BigDecimal(fixedGuaranteeObj.get("inc_fixed_guarantee_rentention_amt_input").getAsString()));
				fixedGuarantees.add(fixedGuaranteeM);
				allIncomes.addAll(fixedGuarantees);
				
				ArrayList<FixedAccountDataM> fixedAccounts = new ArrayList<>();
				String INC_TYPE_FIXED_ACCOUNT = ServiceCache.getConstant("INC_TYPE_FIXED_ACCOUNT");
				FixedAccountDataM fixedAccountM = new FixedAccountDataM();
				JsonObject fixedAccObj = incInfoObj.getAsJsonObject("fixedAcc");
				fixedAccountM.setIncomeType(INC_TYPE_FIXED_ACCOUNT);
				fixedAccountM.setOpenDate(fixedAccObj.get("inc_fixed_acc_open_date_input").getAsString().equals("") ? null 
						: Date.valueOf(fixedAccObj.get("inc_fixed_acc_open_date_input").getAsString()));
				fixedAccountM.setAccountNo(fixedAccObj.get("inc_fixed_acc_account_no_input").getAsString());
				fixedAccountM.setAccountName(fixedAccObj.get("inc_fixed_acc_account_name_input").getAsString());
				fixedAccountM.setHoldingRatio(fixedAccObj.get("inc_fixed_acc_holding_ratio_input").getAsString().equals("") ? null 
						: new BigDecimal(fixedAccObj.get("inc_fixed_acc_holding_ratio_input").getAsString()));
				fixedAccountM.setAccountBalance(fixedAccObj.get("inc_fixed_acc_account_balance_input").getAsString().equals("") ?
						null : new BigDecimal(fixedAccObj.get("inc_fixed_acc_account_balance_input").getAsString()));
				fixedAccountM.setBankCode(fixedAccObj.get("inc_fixed_acc_bank_code_input").getAsString());
				fixedAccounts.add(fixedAccountM);
				allIncomes.addAll(fixedAccounts);
				
				ArrayList<FinancialInstrumentDataM> financialInstruments = new ArrayList<>();
				String INC_TYPE_FIN_INSTR_KBANK = ServiceCache.getConstant("INC_TYPE_FIN_INSTR_KBANK");
				FinancialInstrumentDataM financialInstrumentM = new FinancialInstrumentDataM();
				JsonObject finInstrumentObj = incInfoObj.getAsJsonObject("finInstrument");
				financialInstrumentM.setIncomeType(INC_TYPE_FIN_INSTR_KBANK);
				financialInstrumentM.setOpenDate(finInstrumentObj.get("inc_fin_instrument_open_date_input").getAsString().equals("")
						? null : Date.valueOf(finInstrumentObj.get("inc_fin_instrument_open_date_input").getAsString()));
				financialInstrumentM.setExpireDate(finInstrumentObj.get("inc_fin_instrument_expire_date_input").getAsString().equals("")
						? null : Date.valueOf(finInstrumentObj.get("inc_fin_instrument_expire_date_input").getAsString()));
				financialInstrumentM.setIssuerName(finInstrumentObj.get("inc_fin_instrument_issuer_name_input").getAsString());
				financialInstrumentM.setInstrumentType(finInstrumentObj.get("inc_fin_instrument_instrument_type_input").getAsString());
				financialInstrumentM.setHolderName(finInstrumentObj.get("inc_fin_instrument_holder_name_input").getAsString());
				financialInstrumentM.setHoldingRatio(finInstrumentObj.get("inc_fin_instrument_holding_ratio_input").getAsString().equals("")
						? null : new BigDecimal(finInstrumentObj.get("inc_fin_instrument_holding_ratio_input").getAsString()));
				financialInstrumentM.setCurrentBalance(finInstrumentObj.get("inc_fin_instrument_current_balance_input").getAsString().equals("")
						? null : new BigDecimal(finInstrumentObj.get("inc_fin_instrument_current_balance_input").getAsString()));
				financialInstrumentM.setInstrumentTypeDesc(finInstrumentObj.get("inc_fin_instrument_instrument_type_desc_input").getAsString());
				financialInstruments.add(financialInstrumentM);
				allIncomes.addAll(financialInstruments);
				
				ArrayList<ClosedEndFundDataM> closeEndFounds = new ArrayList<>();
				String INC_TYPE_CLOSED_END_FUND = ServiceCache.getConstant("INC_TYPE_CLOSED_END_FUND");
				ClosedEndFundDataM closeEndFundM = new ClosedEndFundDataM();
				JsonObject clsEndFundObj = incInfoObj.getAsJsonObject("clsEndFund");
				closeEndFundM.setIncomeType(INC_TYPE_CLOSED_END_FUND);
				closeEndFundM.setBankCode(clsEndFundObj.get("inc_cls_end_fund_bank_code_input").getAsString());
				closeEndFundM.setFundName(clsEndFundObj.get("inc_cls_end_fund_fund_name_input").getAsString());
				closeEndFundM.setAccountNo(clsEndFundObj.get("inc_cls_end_fund_account_no_input").getAsString());
				closeEndFundM.setAccountName(clsEndFundObj.get("inc_cls_end_fund_account_name_input").getAsString());
				closeEndFundM.setHoldingRatio(clsEndFundObj.get("inc_cls_end_fund_holding_ratio_input").getAsString().equals("") 
						? null : new BigDecimal(clsEndFundObj.get("inc_cls_end_fund_holding_ratio_input").getAsString()));
				closeEndFundM.setAccountBalance(clsEndFundObj.get("inc_cls_end_fund_account_balance_input").getAsString().equals("")
						? null : new BigDecimal(clsEndFundObj.get("inc_cls_end_fund_account_balance_input").getAsString()));
				closeEndFounds.add(closeEndFundM);
				allIncomes.addAll(closeEndFounds);
				
				ArrayList<BankStatementDataM> bankStatements = new ArrayList<>();
				String INC_TYPE_BANK_STATEMENT = ServiceCache.getConstant("INC_TYPE_BANK_STATEMENT");
				if(incInfoObj.get("bankStatement").isJsonArray()){
					for(JsonElement bankStatement : incInfoObj.getAsJsonArray("bankStatement")){
						BankStatementDataM bankStatementM = new BankStatementDataM();
						JsonObject bankStatementObj = bankStatement.getAsJsonObject();
						bankStatementM.setIncomeType(INC_TYPE_BANK_STATEMENT);
						bankStatementM.setBankCode(bankStatementObj.get("inc_bank_statement_bank_code_input").getAsString());
						bankStatementM.setStatementCode(bankStatementObj.get("inc_bank_statement_statement_code_input").getAsString());
						bankStatementM.setAdditionalCode(bankStatementObj.get("inc_bank_statement_additional_code_input").getAsString());
						ArrayList<BankStatementDetailDataM> bankStatementDetails = new ArrayList<>();
						if(bankStatementObj.get("detail").isJsonArray()){
							for(JsonElement detail : bankStatementObj.getAsJsonArray("detail")){
								BankStatementDetailDataM detailM = new BankStatementDetailDataM();
								JsonObject detailObj = detail.getAsJsonObject();
								detailM.setMonth(detailObj.get("inc_bank_statement_detail_month_input").getAsString());
								detailM.setYear(detailObj.get("inc_bank_statement_detail_year_input").getAsString());
								detailM.setAmount(detailObj.get("inc_bank_statement_detail_amount_input").getAsString().equals("")
										? null : new BigDecimal(detailObj.get("inc_bank_statement_detail_amount_input").getAsString()));
								bankStatementDetails.add(detailM);
							}
						}else{
							BankStatementDetailDataM detailM = new BankStatementDetailDataM();
							JsonObject detailObj = bankStatementObj.getAsJsonObject("detail");
							detailM.setMonth(detailObj.get("inc_bank_statement_detail_month_input").getAsString());
							detailM.setYear(detailObj.get("inc_bank_statement_detail_year_input").getAsString());
							detailM.setAmount(detailObj.get("inc_bank_statement_detail_amount_input").getAsString().equals("")
									? null : new BigDecimal(detailObj.get("inc_bank_statement_detail_amount_input").getAsString()));
							bankStatementDetails.add(detailM);
						}
						bankStatementM.setBankStatementDetails(bankStatementDetails);
						bankStatements.add(bankStatementM);
					}
				}else{
					BankStatementDataM bankStatementM = new BankStatementDataM();
					JsonObject bankStatementObj = incInfoObj.getAsJsonObject("bankStatement");
					bankStatementM.setIncomeType(INC_TYPE_BANK_STATEMENT);
					bankStatementM.setBankCode(bankStatementObj.get("inc_bank_statement_bank_code_input").getAsString());
					bankStatementM.setStatementCode(bankStatementObj.get("inc_bank_statement_statement_code_input").getAsString());
					bankStatementM.setAdditionalCode(bankStatementObj.get("inc_bank_statement_additional_code_input").getAsString());
					ArrayList<BankStatementDetailDataM> bankStatementDetails = new ArrayList<>();
					if(bankStatementObj.get("detail").isJsonArray()){
						for(JsonElement detail : bankStatementObj.getAsJsonArray("detail")){
							BankStatementDetailDataM detailM = new BankStatementDetailDataM();
							JsonObject detailObj = detail.getAsJsonObject();
							detailM.setMonth(detailObj.get("inc_bank_statement_detail_month_input").getAsString());
							detailM.setYear(detailObj.get("inc_bank_statement_detail_year_input").getAsString());
							detailM.setAmount(detailObj.get("inc_bank_statement_detail_amount_input").getAsString().equals("")
									? null : new BigDecimal(detailObj.get("inc_bank_statement_detail_amount_input").getAsString()));
							bankStatementDetails.add(detailM);
						}
					}else{
						BankStatementDetailDataM detailM = new BankStatementDetailDataM();
						JsonObject detailObj = bankStatementObj.getAsJsonObject("detail");
						detailM.setMonth(detailObj.get("inc_bank_statement_detail_month_input").getAsString());
						detailM.setYear(detailObj.get("inc_bank_statement_detail_year_input").getAsString());
						detailM.setAmount(detailObj.get("inc_bank_statement_detail_amount_input").getAsString().equals("")
								? null : new BigDecimal(detailObj.get("inc_bank_statement_detail_amount_input").getAsString()));
						bankStatementDetails.add(detailM);
					}
					bankStatementM.setBankStatementDetails(bankStatementDetails);
					bankStatements.add(bankStatementM);
				}
				allIncomes.addAll(bankStatements);
				
				BundleSMEDataM bundleSMEM = new BundleSMEDataM();
				JsonObject bundleSMEObj = incInfoObj.getAsJsonObject("bundleSME");
				bundleSMEM.setApplicantQuality(bundleSMEObj.get("orig_bundle_sme_applicant_quality_input").getAsString());
				bundleSMEM.setApprovalLimit(bundleSMEObj.get("orig_bundle_sme_approval_limit_input").getAsString().equals("") ? null 
						: new BigDecimal(bundleSMEObj.get("orig_bundle_sme_approval_limit_input").getAsString()));
				bundleSMEM.setBusOwnerFlag(bundleSMEObj.get("orig_bundle_sme_business_owner_flag_input").getAsString());
				bundleSMEM.setCorporateRatio(bundleSMEObj.get("orig_bundle_sme_corporate_ratio_input").getAsString().equals("") ? null
						: new BigDecimal(bundleSMEObj.get("orig_bundle_sme_corporate_ratio_input").getAsString()));
				bundleSMEM.setgDscrReq(bundleSMEObj.get("orig_bundle_sme_g_dscr_req_input").getAsString().equals("") ? null 
						: new BigDecimal(bundleSMEObj.get("orig_bundle_sme_g_dscr_req_input").getAsString()));
				bundleSMEM.setgTotExistPayment(bundleSMEObj.get("orig_bundle_sme_g_total_exist_payment_input").getAsString().equals("")
						? null : new BigDecimal(bundleSMEObj.get("orig_bundle_sme_g_total_exist_payment_input").getAsString()));
				bundleSMEM.setgTotNewPayReq(bundleSMEObj.get("orig_bundle_sme_g_total_newpay_req_input").getAsString().equals("")
						? null : new BigDecimal(bundleSMEObj.get("orig_bundle_sme_g_total_newpay_req_input").getAsString()));
				bundleSMEM.setBorrowingType(bundleSMEObj.get("orig_bundle_sme_sme_borrower_type_input").getAsString());
				bundleSMEM.setIndividualRatio(bundleSMEObj.get("orig_bundle_sme_individual_ratio_input").getAsString().equals("")
						? null : new BigDecimal(bundleSMEObj.get("orig_bundle_sme_individual_ratio_input").getAsString()));
				bundleSMEM.setApprovalDate(bundleSMEObj.get("orig_bundle_sme_approvedDate_input").getAsString().equals("") ? null 
						: Date.valueOf(bundleSMEObj.get("orig_bundle_sme_approvedDate_input").getAsString()));
				application.setBundleSME(bundleSMEM);
				
				BundleKLDataM bundleKLM = new BundleKLDataM();
				JsonObject bundleKLObj = incInfoObj.getAsJsonObject("bundleKL");
				bundleKLM.setVerifiedIncome(bundleKLObj.get("orig_bundle_kl_verfied_income_input").getAsString().equals("") ? null 
						: new BigDecimal(bundleKLObj.get("orig_bundle_kl_verfied_income_input").getAsString()));
				bundleKLM.setVerifiedDate(bundleKLObj.get("orig_bundle_kl_verfied_date_input").getAsString().equals("") ? null 
						: Date.valueOf(bundleKLObj.get("orig_bundle_kl_verfied_date_input").getAsString()));
				bundleKLM.setEstimated_income(bundleKLObj.get("orig_bundle_kl_estimated_income_input").getAsString().equals("") ? null 
						: new BigDecimal(bundleKLObj.get("orig_bundle_kl_estimated_income_input").getAsString()));
				
				BundleHLDataM bundleHLM = new BundleHLDataM();
				JsonObject bundleHLObj = incInfoObj.getAsJsonObject("bundleHL");
				bundleHLM.setApproveCreditLine(bundleHLObj.get("orig_bundle_hl_approve_credit_line_input").getAsString().equals("") 
						? null : new BigDecimal(bundleHLObj.get("orig_bundle_hl_approve_credit_line_input").getAsString()));
				bundleHLM.setVerifiedIncome(bundleHLObj.get("orig_bundle_hl_verified_income_input").getAsString().equals("") 
						? null : new BigDecimal(bundleHLObj.get("orig_bundle_hl_verified_income_input").getAsString()));
				bundleHLM.setApprovedDate(bundleHLObj.get("orig_bundle_hl_approved_date_input").getAsString().equals("") ? null 
						: Date.valueOf(bundleHLObj.get("orig_bundle_hl_approved_date_input").getAsString()));
				bundleHLM.setCcApprovedAmt(bundleHLObj.get("orig_bundle_hl_cc_approved_amt_input").getAsString().equals("") ? null
						: new BigDecimal(bundleHLObj.get("orig_bundle_hl_cc_approved_amt_input").getAsString()));
				bundleHLM.setKecApprovedAmt(bundleHLObj.get("orig_bundle_hl_kec_approved_amt_input").getAsString().equals("") ? null
						: new BigDecimal(bundleHLObj.get("orig_bundle_hl_kec_approved_amt_input").getAsString()));
				application.setBundleHL(bundleHLM);
				
				
				JsonObject payrollFileObj = incInfoObj.getAsJsonObject("payrollFile");
				JsonElement payrollCurrMonth = payrollFileObj.get("inc_payroll_file_info_payrollCurrMonth");
				JsonElement payrollPrev1Month = payrollFileObj.get("inc_payroll_file_info_payrollPrev1Month");
				JsonElement payrollPrev2Month = payrollFileObj.get("inc_payroll_file_info_payrollPrev2Month");
				JsonElement payrollPrev3Month = payrollFileObj.get("inc_payroll_file_info_payrollPrev3Month");
				JsonElement payrollPrev4Month = payrollFileObj.get("inc_payroll_file_info_payrollPrev4Month");
				JsonElement payrollPrev5Month = payrollFileObj.get("inc_payroll_file_info_payrollPrev5Month");
				JsonElement payrollPrev6Month = payrollFileObj.get("inc_payroll_file_info_payrollPrev6Month");
				JsonElement payrollPrev7Month = payrollFileObj.get("inc_payroll_file_info_payrollPrev7Month");
				JsonElement payrollPrev8Month = payrollFileObj.get("inc_payroll_file_info_payrollPrev8Month");
				JsonElement payrollPrev9Month = payrollFileObj.get("inc_payroll_file_info_payrollPrev9Month");
				JsonElement payrollPrev10Month = payrollFileObj.get("inc_payroll_file_info_payrollPrev10Month");
				JsonElement payrollPrev11Month = payrollFileObj.get("inc_payroll_file_info_payrollPrev11Month");
				if(payrollCurrMonth.isJsonArray()){
					int payrollFileLength = payrollCurrMonth.getAsJsonArray().size();
					for(int i=0;i<payrollFileLength;i++){
						payrollCurrMonth.getAsJsonArray().get(i).getAsString();
						payrollPrev1Month.getAsJsonArray().get(i).getAsString();
						payrollPrev2Month.getAsJsonArray().get(i).getAsString();
						payrollPrev3Month.getAsJsonArray().get(i).getAsString();
						payrollPrev4Month.getAsJsonArray().get(i).getAsString();
						payrollPrev5Month.getAsJsonArray().get(i).getAsString();
						payrollPrev6Month.getAsJsonArray().get(i).getAsString();
						payrollPrev7Month.getAsJsonArray().get(i).getAsString();
						payrollPrev8Month.getAsJsonArray().get(i).getAsString();
						payrollPrev9Month.getAsJsonArray().get(i).getAsString();
						payrollPrev10Month.getAsJsonArray().get(i).getAsString();
						payrollPrev11Month.getAsJsonArray().get(i).getAsString();
					}
				}else{
					payrollCurrMonth.getAsString();
					payrollPrev1Month.getAsString();
					payrollPrev2Month.getAsString();
					payrollPrev3Month.getAsString();
					payrollPrev4Month.getAsString();
					payrollPrev5Month.getAsString();
					payrollPrev6Month.getAsString();
					payrollPrev7Month.getAsString();
					payrollPrev8Month.getAsString();
					payrollPrev9Month.getAsString();
					payrollPrev10Month.getAsString();
					payrollPrev11Month.getAsString();
				}
				incomeInfo.setAllIncomes(allIncomes);
				incomeInfos.add(incomeInfo);
			}
			personalInfo.setIncomeInfos(incomeInfos);
			
			JsonObject documentList = personalAppObject.get("documentList").getAsJsonObject();
			ArrayList<ApplicationImageDataM> applicationImages = new ArrayList<>();
			if(documentList.get("documents_documentCode_input").isJsonArray()){
				int documentLength = documentList.get("documents_documentCode_input").getAsJsonArray().size();
				ApplicationImageDataM applicationImage = new ApplicationImageDataM();
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = new ArrayList<>();
				for(int i=0;i<documentLength;i++){
					ApplicationImageSplitDataM applicationImageSplit = new ApplicationImageSplitDataM();
					applicationImageSplit.setDocType(documentList.get("documents_documentCode_input").getAsJsonArray().get(i).getAsString());
//					applicationImageSplit.setddocumentList.get("documents_documentType_input").getAsJsonArray().get(i).getAsString();
					if(!documentList.get("documents_expiryDate_input").getAsJsonArray().get(i).getAsString().equals("")){
						if(documentList.get("documents_documentCode_input").getAsJsonArray().get(i).getAsString().equals("DOC_ID_01_ID")){
							personalInfo.setCidExpDate(Date.valueOf(documentList.get("documents_expiryDate_input").getAsJsonArray().get(i).getAsString()));
						}else if(documentList.get("documents_documentCode_input").getAsJsonArray().get(i).getAsString().equals("DOC_ID_05_ID")){
							personalInfo.setWorkPermitExpDate(Date.valueOf(documentList.get("documents_expiryDate_input").getAsJsonArray().get(i).getAsString()));
						}else{
							personalInfo.setVisaExpDate(Date.valueOf(documentList.get("documents_expiryDate_input").getAsJsonArray().get(i).getAsString()));
						}
					}
					applicationImageSplits.add(applicationImageSplit);
				}
				applicationImage.setApplicationImageSplits(applicationImageSplits);
			}else{
				ApplicationImageDataM applicationImage = new ApplicationImageDataM();
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = new ArrayList<>();
					ApplicationImageSplitDataM applicationImageSplit = new ApplicationImageSplitDataM();
					applicationImageSplit.setDocType(documentList.get("documents_documentCode_input").getAsString());
	//				applicationImageSplit.setdocumentList.get("documents_documentType_input").getAsString();
					if(documentList.get("documents_documentCode_input").getAsString().equals("DOC_ID_01_ID")){
						personalInfo.setCidExpDate(Date.valueOf(documentList.get("documents_expiryDate_input").getAsString()));
					}else if(documentList.get("documents_documentCode_input").getAsString().equals("DOC_ID_05_ID")){
						personalInfo.setWorkPermitExpDate(Date.valueOf(documentList.get("documents_expiryDate_input").getAsString()));
					}else{
						personalInfo.setVisaExpDate(documentList.get("documents_expiryDate_input").getAsString().equals("") ? null 
								: Date.valueOf(documentList.get("documents_expiryDate_input").getAsString()));
					}
					applicationImageSplits.add(applicationImageSplit);
				applicationImage.setApplicationImageSplits(applicationImageSplits);
				applicationImages.add(applicationImage);
			}
			ficoRequest.setApplicationImages(applicationImages);
			
			ReferencePersonDataM referencePerson = new ReferencePersonDataM();
			JsonObject referenceList = personalAppObject.get("reference").getAsJsonObject();
			if(referenceList.get("reference_title_input").isJsonArray()){
				int referenceLength = referenceList.get("reference_title_input").getAsJsonArray().size();
				for(int i=0;i<referenceLength;i++){
					referencePerson.setThTitleCode(referenceList.get("reference_title_input").getAsJsonArray().get(i).getAsString());
					referencePerson.setThLastName(referenceList.get("reference_lastName_input").getAsJsonArray().get(i).getAsString());
					referencePerson.setThFirstName(referenceList.get("reference_firstName_input").getAsJsonArray().get(i).getAsString());
					referencePerson.setRelation(referenceList.get("reference_relationshipWithBorrower_input").getAsJsonArray().get(i).getAsString());
					referencePerson.setPhone1(referenceList.get("reference_fixedLineHome_input").getAsJsonArray().get(i).getAsString());
					referencePerson.setOfficePhoneExt(referenceList.get("reference_fixedLineWork_input").getAsJsonArray().get(i).getAsString());
					referencePerson.setMobile(referenceList.get("reference_mobileNumber_input").getAsJsonArray().get(i).getAsString());
				}
			}else{
				referencePerson.setThTitleCode(referenceList.get("reference_title_input").getAsString());
				referencePerson.setThLastName(referenceList.get("reference_lastName_input").getAsString());
				referencePerson.setThFirstName(referenceList.get("reference_firstName_input").getAsString());
				referencePerson.setRelation(referenceList.get("reference_relationshipWithBorrower_input").getAsString());
				referencePerson.setPhone1(referenceList.get("reference_fixedLineHome_input").getAsString());
				referencePerson.setOfficePhoneExt(referenceList.get("reference_fixedLineWork_input").getAsString());
				referencePerson.setMobile(referenceList.get("reference_mobileNumber_input").getAsString());
			}
			applications.add(application);
			personalInfos.add(personalInfo);
			ArrayList<ReferencePersonDataM> referencePersons = new ArrayList<ReferencePersonDataM>();
			referencePersons.add(referencePerson);
			ficoRequest.setReferencePersons(referencePersons);
		}
		
		int i=0;
		for(String creditDetail : creditDetailList){
			ApplicationDataM application = applications.get(i);
			PersonalInfoDataM personalInfo = personalInfos.get(i);
			logger.debug("creditDetail : "+creditDetail);
			logger.debug("check application : "+gson.toJson(application));
			logger.debug("check personalInfo : "+gson.toJson(personalInfo));
			JsonObject creditDetailObj = gson.fromJson(creditDetail, JsonObject.class);
//			creditDetailObj.get("creditDetails_typeOfKecProgram_input").getAsString();
			logger.debug("project code : "+creditDetailObj.get("creditDetails_projectCode_input").getAsString());
			application.setProjectCode(creditDetailObj.get("creditDetails_projectCode_input").getAsString());
			application.setApplicationRecordId(creditDetailObj.get("creditDetails_uniqueProductId_input").getAsString());
			application.setSpSignoffDate(creditDetailObj.get("creditDetails_spSignOffDate_input").getAsString().equals("") ? null 
					: Date.valueOf(creditDetailObj.get("creditDetails_spSignOffDate_input").getAsString()));
//			application.setProjectCode(creditDetailObj.get("creditDetails_wealthProjectCode_input").getAsString());
			application.setFinalAppDecision(creditDetailObj.get("creditDetails_caFinalDecision_input").getAsString());
			personalInfo.setPlaceReceiveCard(creditDetailObj.get("creditDetails_productMailingAddressType_input").getAsString());
			ArrayList<LoanDataM> loans = new ArrayList<>();
			LoanDataM loan = new LoanDataM();
				ArrayList<PaymentMethodDataM> paymentMethods = new ArrayList<>();
					PaymentMethodDataM paymentMethod = new PaymentMethodDataM();
					paymentMethod.setPaymentMethod(creditDetailObj.get("creditDetails_isAutoPayment_input").getAsString());
					paymentMethod.setPaymentRatio(creditDetailObj.get("creditDetails_transferPercentage_input").getAsString().equals("")
							? null : new BigDecimal(creditDetailObj.get("creditDetails_transferPercentage_input").getAsString()));
					paymentMethod.setPaymentMethod(creditDetailObj.get("creditDetails_paymentMethod_input").getAsString());
					paymentMethods.add(paymentMethod);
				CardDataM card = new CardDataM();
				card.setPercentLimitMaincard(creditDetailObj.get("creditDetails_requestCreditLimitPercent_input").getAsString());
				card.setMainCardNo(creditDetailObj.get("creditDetails_accountNumberMC_input").getAsString());
				card.setApplicationType(creditDetailObj.get("creditDetails_subProduct_input").getAsString());
				card.setCardNo(creditDetailObj.get("creditDetails_accountNumberInc_input").getAsString());
				ArrayList<CashTransferDataM> cashTransfers = new ArrayList<>(); 
					CashTransferDataM cashTransfer = new CashTransferDataM();
					cashTransfer.setCashTransferType(creditDetailObj.get("creditDetails_cashOnTransfer_input").getAsString());
				loan.setCashTransfers(cashTransfers);
				loan.setCard(card);	
				loan.setRequestLoanAmt(creditDetailObj.get("creditDetails_requestedAmount_input").getAsString().equals("") ? null 
						: new BigDecimal(creditDetailObj.get("creditDetails_requestedAmount_input").getAsString()));
				loan.setRequestTerm(creditDetailObj.get("creditDetails_requestedTerm_input").getAsString().equals("") ? null 
						: new BigDecimal(creditDetailObj.get("creditDetails_requestedTerm_input").getAsString()));
				loan.setLoanAmt(creditDetailObj.get("creditDetails_caApprovedAmount_input").getAsString().equals("") ? null 
						: new BigDecimal(creditDetailObj.get("creditDetails_caApprovedAmount_input").getAsString()));
//				personalInfo.setPaymentMethods(paymentMethods);
				loans.add(loan);
			application.setLoans(loans);
//			creditDetailObj.get("creditDetails_isMailingAddressMatchCardlink_input").getAsString();
//			creditDetailObj.get("creditDetails_isMailingAddressMatchNcb_input").getAsString();
//			creditDetailObj.get("creditDetails_isMailingAddressMatchId_input").getAsString();
			ArrayList<AddressDataM> addresses = new ArrayList<>();
				AddressDataM address = new AddressDataM();
				address.setAddress(creditDetailObj.get("mailingAddress_address_houseNumber_input").getAsString());
				address.setVilapt(creditDetailObj.get("mailingAddress_address_village_input").getAsString());
				address.setBuilding(creditDetailObj.get("mailingAddress_address_buildingName_input").getAsString());
				address.setTambol(creditDetailObj.get("mailingAddress_address_subDistrict_input").getAsString());
				address.setAmphur(creditDetailObj.get("mailingAddress_address_district_input").getAsString());
				address.setProvinceDesc(creditDetailObj.get("mailingAddress_address_province_input").getAsString());
				address.setZipcode(creditDetailObj.get("mailingAddress_address_postalCode_input").getAsString().equals("") ? null 
						: creditDetailObj.get("mailingAddress_address_postalCode_input").getAsString());
				address.setCountry(creditDetailObj.get("mailingAddress_address_country_input").getAsString());
				address.setPhone1(creditDetailObj.get("mailingAddress_address_fixedLineNumber_input").getAsString());
				address.setAddressType(creditDetailObj.get("mailingAddress_address_type_input").getAsString());
				address.setFloor(creditDetailObj.get("mailingAddress_address_floorNumber_input").getAsString());
				address.setMoo(creditDetailObj.get("mailingAddress_address_moo_input").getAsString());
				address.setSoi(creditDetailObj.get("mailingAddress_address_soi_input").getAsString());
				address.setRoad(creditDetailObj.get("mailingAddress_address_street_input").getAsString());
				addresses.add(address);
			personalInfo.setAddresses(addresses);
			applications.set(i, application);
			personalInfos.set(i, personalInfo);
			i++;
			logger.debug("application : "+gson.toJson(application));
		}
		ficoRequest.setApplications(applications);
		ficoRequest.setPersonalInfos(personalInfos);
	}


}
