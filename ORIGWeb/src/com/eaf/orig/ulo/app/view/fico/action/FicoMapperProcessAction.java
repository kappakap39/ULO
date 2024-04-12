package com.eaf.orig.ulo.app.view.fico.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.ejb.view.ApplicationManager;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;
import com.google.gson.Gson;
import com.orig.bpm.workflow.util.JSONUtil;
@Deprecated
public class FicoMapperProcessAction extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(FicoMapperProcessAction.class);
	@Override
	public Object processAction(){
		logger.debug("processAction()");
		try {
			JSONObject json = new JSONObject( request.getParameter("json"));
			logger.debug("Request parameter Map : " + request.getParameterMap());
			logger.debug("JSON : \n" + json.toString());
			mapItAll(json);			
		} catch (JSONException e) {
			logger.error("Error on processAction() :", e);
		}
		return null;
	}
	
	/**
	 * Utilities Class for Mapping
	 * @version initial
	 * @author Norrapat Nimmanee
	 * 
	 */
	private static class Utils {
		/**
		 * Getting JSON Object in JSON Array by seeking <b>KEY</b> & <b>VALUE</b> (Key Value pair) on each JSON Array<br>
		 * If <b>found</b> then return that JSON Object
		 * @param Key
		 * @param Value
		 * @param jsonArr
		 * @return JSONObject
		 */
		static JSONObject getJsonByKeyValuePair(String Key, String Value, JSONArray jsonArr) {
			if (jsonArr == null) {
				return null;
			}
			
			int count = jsonArr.length();
			JSONObject jObj = null;
			for(int i = 0 ; i < count ; i++) {
				try {
					jObj = jsonArr.getJSONObject(i);
					if (Value == jObj.getString(Key)) {
						break;
					}
				} catch (JSONException e) {
					continue;
				}
			}
			return jObj;
		}
		
		/**
		 * Make sure that Flags String is correct. This method will automatically make change to Flag.<br/>
		 * ------------------------------------------------------------------------------------------------<br/>
		 * <b>Input -> Output</b><br/>
		 * true -> "Y"<br/>
		 * false -> "N"
		 * 
		 * @param b Boolean
		 * @return String <b>EX.</b> "Y" -or- "N"
		 */
		@SuppressWarnings("unused")
		public static String getFlag(boolean b) {
			if (b) {
				return getFlag("true");
			} else {
				return getFlag("false");
			}
		}

		/**
		 * Make sure that Flags String is correct. This method will automatically make change to Flag.<br/>
		 * ------------------------------------------------------------------------------------------------<br/>
		 * <b>Input -> Output</b><br/>
		 * "true" -> "Y"<br/>
		 * "false" -> "N"<br/>
		 * "active" -> "A"<br/>
		 * "inactive" -> "I"<br/>
		 * "notfound" -> "404"
		 * 
		 * @param flag String
		 * @return String <b>EX.</b> "Y" -or- "N"
		 */
		public static String getFlag(String flag) {
			if (null == flag) {
				return null;
			}
			String ret = "";
			if (flag.equalsIgnoreCase("true")) {
				ret = MConstant.FLAG.YES;
			} else if (flag.equalsIgnoreCase("false")) {
				ret = MConstant.FLAG.NO;
			} else if (flag.equalsIgnoreCase("active")) {
				ret = MConstant.FLAG.ACTIVE;
			} else if (flag.equalsIgnoreCase("inactive")) {
				ret = MConstant.FLAG.INACTIVE;
			} else if (flag.equalsIgnoreCase("notfound")) {
				ret = MConstant.FLAG.NOTFOUND;
			}
			return ret;
		}
	}
	
	/**
	 * Mapping all fields from FICO. And then save to database by EJB
	 * @param mainJson [JSON from FICO]
	 */
	public static void mapItAll(JSONObject mainJson) {
		logger.debug("mapItAll()");
		if (mainJson == null) {
			return;
		}
		
		ApplicationGroupDataM applicationGroup = null;
		String applicationGroupId = null;
		
		// Get Application Group to map
		applicationGroupId = JSONUtil.getString(mainJson, "creditApplication.applicationID");
		if (applicationGroupId == null || applicationGroupId.equalsIgnoreCase("")) {
			logger.warn("Application Group ID is NULL");
			return;
		}

		try{
			OrigApplicationGroupDAO applicationGroupDAO = ModuleFactory.getApplicationGroupDAO();
			applicationGroup = applicationGroupDAO.loadOrigApplicationGroupM(applicationGroupId);
			if (null == applicationGroup) {
				logger.fatal("Application Group " + applicationGroupId + " not found ");
				return;
			}
			mapOrigApplicationGroup(applicationGroup, JSONUtil.getJson(mainJson, "creditApplication"));
//			logger.debug("Application Group toJSON : \n" +  new Gson().toJson(applicationGroup));
		}catch(Exception e){
			logger.fatal("ERROR :" , e);
			return;
		}
		
		// Saving
		UserDetailM userM = new UserDetailM();
		userM.setUserName("FICO");
		try {
			ApplicationManager manager = ORIGServiceProxy.getApplicationManager();
			manager.saveApplication(applicationGroup,userM);
		} catch (Exception e) {
			logger.error("Saving Error : " + e.toString());
		}
		
	}
	
	/*
	// Example
	public static void mapOrigApplications(ApplicationGroupDataM applicationGroup){
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		if(!Util.empty(applications)){
			for (ApplicationDataM application : applications) {
				CardDataM card = application.getCard();
				PersonalRelationDataM personalRelation = applicationGroup.getPersonalReation(application.getApplicationRecordId(), "A", "A");
				PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalRelation.getPersonalId());
			}
		}
	}*/
	
	/**
	 * ORIG_APPLICATION_GROUP Mapper
	 * @param app [ApplicationGroupDataM]
	 * @param json [creditApplication]
	 */
	public static void mapOrigApplicationGroup(ApplicationGroupDataM applicationGroup, JSONObject json) {
		logger.debug("mapOrigApplicationGroup()");
		if (applicationGroup == null) {
			logger.warn("mapOrigApplicationGroup() ApplicationGroupDataM is null!");
			return;
		}
		if (json == null) {
			logger.warn("mapOrigApplicationGroup() json is null!");
			return;
		}

		applicationGroup.setIsVetoEligible(JSONUtil.getString(json, "isVETOEligible"));
		applicationGroup.setLastDecision(JSONUtil.getString(json, "isFraudApplication"));
		
		List<ApplicationDataM> applications = applicationGroup.getApplications();
		if(!Util.empty(applications)){
			for (ApplicationDataM application : applications) {
				mapOrigApplication(application, json);
				
			}
		} else {
			logger.warn("applications = applicationGroup.getApplications() is Empty!");
		}
		PersonalInfoMapper.mapPersonalInfoList(applicationGroup, json);
	}
	
	/**
	 * ORIG_APPLICATION Mapper <b><u>NOT COMPLETED</u></b>
	 * @param application [ApplicationData]
	 * @param json [creditApplication]
	 */
	public static void mapOrigApplication(ApplicationDataM application, JSONObject json) {
		logger.debug("mapOrigApplication()");
		if (application == null) {
			logger.warn("mapOrigApplication() ApplicationDataM is null!");
			return;
		}
		if (json == null) {
			logger.warn("mapOrigApplication() json is null!");
			return;
		}
		
		// Project_Code
		// Prolicy_Program_ID
		List<LoanDataM> loansData = application.getLoans();
		if (Util.empty(loansData)) {
			logger.warn("loansData = application.getLoans() is Empty!");
			return;
		}
		for(LoanDataM loanData : loansData) {
			mapOrigLoan(loanData, json);
			
		}
	}
	
	
	/**
	 * ORIG_LOAN Mapper <b><u>NOT COMPLETED</u></b>
	 * @param loan [LoanData]
	 * @param json [creditApplication]
	 */
	public static void mapOrigLoan(LoanDataM loan, JSONObject json) {
		logger.debug("mapOrigLoan()");
		if (loan == null) {
			logger.warn("mapOrigLoan() LoanDataM is null!");
			return;
		}
		if (json == null) {
			logger.warn("mapOrigLoan() json is null!");
			return;
		}
		
		JSONObject offerCreditDetailJSON = null;
		
		// Mapping CardDataM
		CardDataM card = loan.getCard();
		if (null != card) {
			offerCreditDetailJSON = mapOrigCard(card, json);
		}
		
		if (null != offerCreditDetailJSON) {
			// Map Loan
			loan.setFinalCreditLimit(JSONUtil.getBigDecimal(offerCreditDetailJSON, "firstInstallmentDate"));
			loan.setInstallmentAmt(JSONUtil.getBigDecimal(offerCreditDetailJSON, "installmentAmount"));
			loan.setInterestRate(JSONUtil.getBigDecimal(offerCreditDetailJSON, "interestRate"));
//			loan.setMaxCreditLimit(JSONUtil.getBigDecimal(offerCreditDetailJSON, "minLimitForProduct")); // ??????
			loan.setMaxCreditLimitBot(JSONUtil.getBigDecimal(offerCreditDetailJSON, "maxLimitForBOT"));
			loan.setMinCreditLimit(JSONUtil.getBigDecimal(offerCreditDetailJSON, "minLoanAmount"));
		} else {
			logger.error("offerCreditDetailJSON is NULL");
		}
	}
	
	/**
	 * ORIG_CARD Mapper <b><u>NOT COMPLETED</u></b>
	 * @param card [CardDataM]
	 * @param json [creditApplication]
	 * @return JSONObject offerCreditDetailJSON
	 */
	public static JSONObject mapOrigCard(CardDataM card, JSONObject json) {
		logger.debug("mapOrigCard()");
		if (card == null) {
			logger.warn("mapCard() CardDataM is null!");
			return null;
		}
		if (json == null) {
			logger.warn("mapCard() json is null!");
			return null;
		}
//		card.setMembershipNo(JSONUtil.getString(jsonPersonal, "ThaiBevMembershipNo"));
		JSONArray offersJSON = null;
		JSONObject offerCreditDetailJSON = null;
		JSONArray creditDetails = JSONUtil.getArray(json, "creditRequests"); // ???
		JSONObject creditDetail = Utils.getJsonByKeyValuePair("flpCardCode", card.getCardType(), creditDetails);
		
		// Use uniqueProductID as Key
		String uniqueProductID = JSONUtil.getString(creditDetail, "uniqueProductID");
		
		card.setPlasticCode(JSONUtil.getString(creditDetail, "plasticCardCode"));
		//card.setFlpCardLevel(JSONUtil.getString(creditDetail, "flpCardLevel")); // CARD_TYPE ??
		card.setCardType(JSONUtil.getString(creditDetail, "flpCardCode"));
		card.setFlpCardType(JSONUtil.getString(creditDetail, "flpCardType"));
		
		offersJSON = JSONUtil.getArray(json, "Offer");
		for (int i = 0 , offerJsonCount = 0; i<offerJsonCount; i++) {
			JSONObject offerJSON;
			try {
				offerJSON = offersJSON.getJSONObject(i);
				JSONArray offerCreditDetails = JSONUtil.getArray(offerJSON, "creditdetails");
				
				offerCreditDetailJSON = Utils.getJsonByKeyValuePair("uniqueProductID", uniqueProductID, offerCreditDetails);
				if (null != offerCreditDetailJSON) {
					JSONObject pricing;
					try {
						pricing = JSONUtil.getArray(offerCreditDetailJSON, "pricing").getJSONObject(0);
						card.setCardFee(JSONUtil.getString(pricing, "feeWaiveCode"));
					} catch (JSONException e) {
						logger.error("pricing is NULL");
					}
				}
			} catch (JSONException e1) { }
		}
		return offerCreditDetailJSON;
	}

	public static class PersonalInfoMapper {

		/**
		 * ORIG_PERSONAL_INFO Group Mapper
		 * @param appGroup [ApplicationGroupDataM]
		 * @param json [creditApplication]
		 */
		public static void mapPersonalInfoList(ApplicationGroupDataM appGroup, JSONObject json) {
			logger.debug("mapPersonalInfoList()");
			if (appGroup == null) {
				logger.warn("mapPersonalInfoList() ApplicationGroupDataM is null!");
				return;
			}
			if (json == null) {
				logger.warn("mapPersonalInfoList() json is null!");
				return;
			}
			
			List<PersonalInfoDataM> personalInfos = appGroup.getPersonalInfos();
			if (Util.empty(personalInfos)) {
				logger.error("personalInfos = appGroup.getPersonalInfos() is Empty!");
				return;
			}
			for(PersonalInfoDataM p : personalInfos) {
				logger.debug("Person : " + p.toString());
				logger.debug("Person IDNO : " + p.getIdno());
				
				JSONArray personalInfoJsonArray = (JSONArray)JSONUtil.getArray(json, "personalApplicant");
				JSONObject personalInfoJson = Utils.getJsonByKeyValuePair("idNumber", p.getIdno(), personalInfoJsonArray);
				mapPersonalInfo(p, personalInfoJson, json);
				VerificationResultDataM verificationResult = new VerificationResultDataM();
				mapXRulesVerificationResult(verificationResult, personalInfoJson);
				p.setVerificationResult(verificationResult);
				
			}
		}
		
		/**
		 * ORIG_PERSONAL_INFO Mapper
		 * @param person [PersonalInfoDataM]
		 * @param personalApplicantJson [PersonalApplicantJson]
		 * @param json [creditApplication]
		 */
		public static void mapPersonalInfo(PersonalInfoDataM person, JSONObject personalApplicantJson, JSONObject json) {
			logger.debug("mapPersonalInfo()");
			if (person == null) {
				logger.warn("mapPersonalInfo() person is null!");
				return;
			}
			if (json == null) {
				logger.warn("mapPersonalInfo() json is null!");
				return;
			}
			
//			person.setIncPolicySegmentCode(JSONUtil.getString(json, "creditRequests.creditDetails.policySegmentID"));
			person.setNoMainCard(JSONUtil.getString(personalApplicantJson, "numberOfPrimaryCards"));
			person.setNoSupCard(JSONUtil.getString(personalApplicantJson, "numberOfSuplimentaryCards"));
//			person.setReferCriteria(JSONUtil.getString(json, "creditRequests.creditDetails.ruleResults.override.specialSegmentCriteria"));
			person.setSorceOfIncome(JSONUtil.getString(personalApplicantJson, "incomeSource")); // WSDL not found this field.
			person.setTotVerifiedIncome(JSONUtil.getBigDecimal(personalApplicantJson, "verifiedIncome"));
			
			person.setIncomeInfos((ArrayList<IncomeInfoDataM>) getIncomeInfo(personalApplicantJson));
		}
		
		public static List<IncomeInfoDataM> getIncomeInfo(JSONObject json) {
			if (json == null) {
				logger.warn("mapIncomeInfo() json is null!");
				return null;
			}
			logger.debug("mapIncomeInfo()");
//			try {
//				logger.debug(json.toString(4));
//			} catch (JSONException e1) {
//				logger.debug("JSON NULL");
//			}
			List<IncomeInfoDataM> IncomeInfos = new ArrayList<IncomeInfoDataM>();
			
			//INC_INFO.setIncomeType(JSONUtil.getString(json, "creditApplication.personalApplicant.incomeScreenRecommendation.incomeScreenTypeCode"));
			JSONArray incomeScreenRecommendations = JSONUtil.getArray(json, "incomeScreenRecommendation");
			
			if (null != incomeScreenRecommendations) {
				int length = incomeScreenRecommendations.length();
				for (int i=0;i<length;i++) {
					IncomeInfoDataM incomeInfo;
					try {
						incomeInfo = new IncomeInfoDataM();
						
						JSONObject incomeScreenRecommendation = incomeScreenRecommendations.getJSONObject(i);
						String incomeScreenTypeCode = JSONUtil.getString(incomeScreenRecommendation, "incomeScreenTypeCode");
						HashMap<String, Object> listBox = ListBoxControl.get("38", "SYSTEM_ID2", incomeScreenTypeCode);
						if (null == listBox || listBox.size() == 0) {
							logger.debug("listbox null");
							continue;
						}
						incomeInfo.setIncomeType((String)listBox.get("CHOICE_NO"));
						IncomeInfos.add(incomeInfo);
						
					} catch (JSONException e) {
						logger.error("mapXRulesVerificationResult() Error : "+ e.toString());
						e.printStackTrace();
					}
				}
			}
			return IncomeInfos;
		}
	}
	
	
	/**
	 * ORIG_CARDLINKl_CUSTOMER Mapper
	 * @param cardlink [CardlinkCustomerDataM]
	 * @param json [personalApplicant/cardlinkCudstNum]
	 */
	public static void mapOrigCardlinkCustomer(CardlinkCustomerDataM cardlink, JSONObject json) {
		logger.debug("mapOrigCardlinkCustomer()");
		if (cardlink == null) {
			logger.warn("mapOrigCardlinkCustomer() CardlinkCustomerDataM is null!");
			return;
		}
		if (json == null) {
			logger.warn("mapOrigCardlinkCustomer() json is null!");
			return;
		}
		cardlink.setCardlinkCustNo(JSONUtil.getString(json, "cusNum"));
		cardlink.setOrgId(JSONUtil.getString(json, "orgNum"));
	}
	
	/**
	 * ORIG_PAYMENT_METHOD Mapper <b><u>NOT COMPLETED</u></b>
	 * @param paymentMethod [PaymentMethodDataM]
	 * @param json [creditRequests/creditDetails]
	 */
	public static void mapOrigPaymentMethod(PaymentMethodDataM paymentMethod, JSONObject json) {
		logger.debug("mapOrigPaymentMethod()");
		if (paymentMethod == null) {
			logger.warn("mapOrigPaymentMethod() PaymentMethodDataM is null!");
			return;
		}
		if (json == null) {
			logger.warn("mapOrigPaymentMethod() json is null!");
			return;
		}
		
	}
	
	/**
	 * XRULES_IDENTIFY_QUESTION_SET Mapper
	 * @param json [peronalApplicant]
	 * @param indentifyQuestions [Identify Questions REF]
	 * @return List of IdentifyQuestionSetDataM
	 */
	public static List<IdentifyQuestionSetDataM> mapXRulesIdentifyQuestionSet(JSONObject json, List<IdentifyQuestionDataM> indentifyQuestions) {
		logger.debug("mapXRulesIdentifyQuestionSet()");
		if (json == null) {
			logger.warn("mapXRulesIdentifyQuestionSet() json is null!");
			return null;
		}
		
		//indentifyQuestions
		// HRVerify
		String questionSetCode = JSONUtil.getString(json, "HRVerificationRequest.questionSetCode");
		List<HashMap<String, Object>> verifyHrQuestions = CacheControl.search("QuestionCacheDataM", "SET_TYPE", questionSetCode);
		
		if(!Util.empty(verifyHrQuestions)){
			for (HashMap<String, Object> verifyHrQuestion : verifyHrQuestions) {
				String qcode = (String)verifyHrQuestion.get("QUESTION_CODE");
				if (!Util.empty(qcode)){
					IdentifyQuestionDataM verifyHrIndentifyQuestion = new IdentifyQuestionDataM();
					verifyHrIndentifyQuestion.setQuestionNo(qcode);
					indentifyQuestions.add(verifyHrIndentifyQuestion);
				}
			}
		}
		// identifyQuestionSets
		List<IdentifyQuestionSetDataM> indentifyQuestionSets = new ArrayList<IdentifyQuestionSetDataM>();
		
		JSONArray callTos = JSONUtil.getArray(json, "customerVerificationRequest.callTo");
		if (null != callTos) {
			for(int i = 0, count = callTos.length(); i < count ; i++) {
				JSONObject callTo;
				try {
					callTo = callTos.getJSONObject(i);
					String callType = JSONUtil.getString(callTo, "callType");
					String whom = JSONUtil.getString(callTo, "whom");
					
					IdentifyQuestionSetDataM indentifyQuestionSet = new IdentifyQuestionSetDataM();
					indentifyQuestionSet.setQuestionSetCode(callType);
					indentifyQuestionSet.setCallTo(whom);
					indentifyQuestionSets.add(indentifyQuestionSet);
					
					List<HashMap<String, Object>> verifyCusQuestions = CacheControl.search("QuestionCacheDataM", "SET_TYPE", callType);
					
					if(!Util.empty(verifyCusQuestions)){
						for (HashMap<String, Object> verifyCusQuestion : verifyCusQuestions) {
							String qcode = (String)verifyCusQuestion.get("QUESTION_CODE");
							if (!Util.empty(qcode)){
								IdentifyQuestionDataM verifyCusIndentifyQuestion = new IdentifyQuestionDataM();
								verifyCusIndentifyQuestion.setQuestionNo(qcode);
								indentifyQuestions.add(verifyCusIndentifyQuestion);
							}
						}
					}
				} catch (JSONException e) {
					logger.debug("callTo [" + i + "] is NULL");
				}
			}
		}
	
		
		return indentifyQuestionSets;
	}
	
	/**
	 * XRULES_OR_POLICY_RULES Mapper <b><u>NOT COMPLETED</u></b>
	 * @param xr [ORPolicyRulesDataM]
	 * @param json [creditRequests/creditDetails/ruleResults/override]
	 */
	public static void mapXRulesOrPolicyRules(ORPolicyRulesDataM xr, JSONObject json) {
		logger.debug("mapXRulesOrPolicyRules()");
		if (xr == null) {
			logger.warn("mapXRulesOrPolicyRules() ORPolicyRulesDataM is null!");
			return;
		}
		if (json == null) {
			logger.warn("mapXRulesOrPolicyRules() json is null!");
			return;
		}
		
		
	}
	
	/**
	 * XRULES_OR_POLICY_RULES_DETAIL Mapper <b><u>NOT COMPLETED</u></b>
	 * @param xrd [ORPolicyRulesDetailDataM]
	 * @param json [creditRequests/creditDetails/ruleResults/override/guildlines]
	 */
	public static void mapXRulesOrPolicyRulesDetail(ORPolicyRulesDetailDataM xrd, JSONObject json) {
		logger.debug("Call mapXRulesOrPolicyRulesDetail()");
		if (xrd == null) {
			logger.warn("mapXRulesOrPolicyRulesDetail() ORPolicyRulesDetailDataM is null!");
			return;
		}
		if (json == null) {
			logger.warn("mapXRulesOrPolicyRulesDetail() json is null!");
			return;
		}
		
	}
	
	/**
	 * XRULES_POLICY_RULES Mapper <b><u>NOT COMPLETED</u></b>
	 * @param policyRules [PolicyRulesDataM]
	 * @param json [creditRequests/creditDetails/ruleResults]
	 */
	public static void mapXRulesPolicyRules(PolicyRulesDataM policyRules, JSONObject json) {
		logger.debug("Call mapXRulesPolicyRules()");
		if (policyRules == null) {
			logger.warn("mapXRulesPolicyRules() PolicyRulesDataM is null!");
			return;
		}
		if (json == null) {
			logger.warn("mapXRulesPolicyRules() json is null!");
			return;
		}
		
		
	}
	
	/**
	 * XRULES_REQUIRED_DOC Mapper
	 * @param requiredDoc [RequiredDocDataM]
	 * @param json [personalApplicant/documentMandatoryList/scenario]
	 */
	public static void mapXRulesRequiredDoc(RequiredDocDataM requiredDoc, JSONObject json) {
		logger.debug("Call mapXRulesRequiredDoc()");
		if (requiredDoc == null) {
			logger.warn("mapXRulesRequiredDoc() RequiredDocDataM is null!");
			return;
		}
		if (json == null) {
			logger.warn("mapXRulesRequiredDoc() json is null!");
			return;
		}
		
		requiredDoc.setDocumentGroupCode(JSONUtil.getString(json, "scenarioID"));
	}
	
	/**
	 * XRULES_REQUIRED_DOC_DETAIL Mapper
	 * @param requiredDoc [RequiredDocDataM]
	 * @param json [personalApplicant/documentMandatoryList/scenario/document]
	 */
	public static void mapXRulesRequiredDocDetail(RequiredDocDetailDataM requiredDocDetail, JSONObject json) {
		logger.debug("Call mapXRulesRequiredDocDetail()");
		if (requiredDocDetail == null) {
			logger.warn("mapXRulesRequiredDocDetail() RequiredDocDetailDataM is null!");
			return;
		}
		if (json == null) {
			logger.warn("mapXRulesRequiredDocDetail() json is null!");
			return;
		}
		
		requiredDocDetail.setDocumentCode(JSONUtil.getString(json, "docID"));
	}
	
	/**
	 * XRULES_VERIFICATION_RESULT Mapper
	 * @param xrulesVerification [VerificationResultDataM]
	 * @param json [personalApplicant]
	 */
	public static void mapXRulesVerificationResult(VerificationResultDataM xrulesVerification, JSONObject json) {
		logger.debug("Call mapXRulesVerificationResult()");
		if (xrulesVerification == null) {
			logger.warn("mapXRulesVerificationResult() VerificationResultDataM is null!");
			return;
		}
		if (json == null) {
			logger.warn("mapXRulesVerificationResult() json is null!");
			return;
		}
		
		xrulesVerification.setDocCompletedFlag(Utils.getFlag(JSONUtil.getString(json, "isDocumentComplete")));
		xrulesVerification.setRequiredVerCustFlag(Utils.getFlag(JSONUtil.getString(json, "customerVerificationRequest.isCustomerVerificationRequired")));
		xrulesVerification.setRequiredVerHrFlag(Utils.getFlag(JSONUtil.getString(json, "HRVerificationRequest.isHRVerificationRequired")));
		xrulesVerification.setRequiredVerPrivilegeFlag(Utils.getFlag(JSONUtil.getString(json, "privilageScreenRequiredFlag")));  // isPrivilageScreenRequired
		
		List<IdentifyQuestionDataM> indentifyQuestions = new ArrayList<IdentifyQuestionDataM>();
		xrulesVerification.setIndentifyQuesitionSets((ArrayList<IdentifyQuestionSetDataM>) mapXRulesIdentifyQuestionSet(json, indentifyQuestions));
		xrulesVerification.setIndentifyQuestions((ArrayList<IdentifyQuestionDataM>) indentifyQuestions);
		
		
		JSONArray websiteVerificationRequest = JSONUtil.getArray(json, "websiteVerificationRequest");
		if(null != websiteVerificationRequest){
			int length = websiteVerificationRequest.length();
			for (int i=0;i<length;i++) {
				
				WebVerificationDataM webVerification;
				try {
					webVerification = mapXRulesWebVerification(websiteVerificationRequest.getJSONObject(i));
					xrulesVerification.addWebVerification(webVerification);
				} catch (JSONException e) {
					logger.error("mapXRulesVerificationResult() Error : "+ e.toString());
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * XRULES_WEB_VERIFICATION Mapper
	 * @param json [personalApplicant/websiteVerificationRequest]
	 * @return WebVerificationDataM
	 */
	public static WebVerificationDataM mapXRulesWebVerification(JSONObject json) {
		logger.info("Call mapXRulesWebVerification()");
		if (json == null) {
			logger.warn("mapXRulesWebVerification() json is null!");
			return null;
		}
		WebVerificationDataM webVerification = new WebVerificationDataM();
		String websiteCode = JSONUtil.getString(json, "websiteCode");
		HashMap<String, Object> websiteHash = CacheControl.get("Website", websiteCode);
		if(!Util.empty(websiteHash)){
			webVerification.setWebCode((String)websiteHash.get("CODE"));
		}
		return webVerification;
	}
}
