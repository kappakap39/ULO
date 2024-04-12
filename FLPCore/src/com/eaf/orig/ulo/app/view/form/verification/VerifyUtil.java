package com.eaf.orig.ulo.app.view.form.verification;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BundleHLDataM;
import com.eaf.orig.ulo.model.app.BundleKLDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PhoneVerificationDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class VerifyUtil {
	private static transient Logger logger = Logger.getLogger(VerifyUtil.class);
	static String STATUS_COMPLETED_NOT_PASS = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS");
	static String VALIDATION_STATUS_REQUIRED = SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED");
	static String VALIDATION_STATUS_WAIT_FOR_RE_CHECK = SystemConstant.getConstant("VALIDATION_STATUS_WAIT_FOR_RE_CHECK");
	static String VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT = SystemConstant.getConstant("VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT");
	static String VER_CUS_COMPLETE_CANCEL = SystemConstant.getConstant("VER_CUS_COMPLETE_CANCEL");
	static String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	static String VER_HR_RESULT_QUESTION_DEPTH_CHECKING = SystemConstant.getConstant("VER_HR_RESULT_QUESTION_DEPTH_CHECKING");
	static String VALIDATION_STATUS_WAIVED = SystemConstant.getConstant("VALIDATION_STATUS_WAIVED");
	static String VALIDATION_STATUS_COMPLETED = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");

	static String FICO_DECISION_POINT_DV2 =SystemConstant.getConstant("FICO_DECISION_POINT_DV2");
	static String FICO_DECISION_POINT_DC = SystemConstant.getConstant("FICO_DECISION_POINT_DC");
	static String DECISION_ACTION_CANCEL =SystemConstant.getConstant("DECISION_ACTION_CANCEL");
	static String PRODUCT_K_PERSONAL_LOAN =SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	static String RECOMMEND_DECISION_CANCEL =SystemConstant.getConstant("RECOMMEND_DECISION_CANCEL");
	static String RECOMMEND_DECISION_APPROVED =SystemConstant.getConstant("RECOMMEND_DECISION_APPROVED");
	static String FIELD_ID_DATA_VALIDATION_STATUS =SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS");
	static String CONTACT_RESULT_CANCEL = SystemConstant.getConstant("CONTACT_RESULT_CANCEL");
	static String VALIDATION_STATUS_COMPLETED_NOT_FRAUD = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_FRAUD");
	static String DOCUMENT_OVER_SLA_BY_CUSTOMER = SystemConstant.getConstant("DOCUMENT_OVER_SLA_BY_CUSTOMER");
	static String DOCUMENT_OVER_SLA_BY_HR = SystemConstant.getConstant("DOCUMENT_OVER_SLA_BY_HR");
	static String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	static String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	static String VALIDATION_STATUS_COMPLETED_NOT_PASS = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS");
	static String VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER = SystemConstant.getConstant("VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER");
	static String QUESTION_SET_CACHE_DATAM = SystemConstant.getConstant("QUESTION_SET_CACHE_DATAM");
	static String QUESTION_CACHE = SystemConstant.getConstant("QUESTION_CACHE");
	static String VER_CUS_COMPLETE_VERIFY_COMPLETED = SystemConstant.getConstant("VER_CUS_COMPLETE_VERIFY_COMPLETED");
	static String QUESTION_SET_TYPE_VERIRY_CUSTOMER = SystemConstant.getConstant("QUESTION_SET_TYPE_VERIRY_CUSTOMER");
	static String QUESTION_SET_VERIFY_CUSTOMER_OTHER = SystemConstant.getConstant("QUESTION_SET_VERIFY_CUSTOMER_OTHER");
	
	public static String ERROR_TYPE_HR_SEND_TO_FRAUD = "ERROR_SEND_TO_FRAUD";
	public static String ERROR_TYPE_REQUIRED_SOME_VERIFICATION = "REQUIRED_SOME_VERIFICATION";
	public static String ERROR_TYPE_REQUIRED_ALL_VERIFICATION = "REQUIRED_ALL_VERIFICATION";
		
	public static String validationVerification(ApplicationGroupDataM applicationGroup ){
		String ERROR_TYPE="";
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);	
		VerificationResultDataM  verificationResult = personalInfo.getVerificationResult();
		if(!Util.empty(verificationResult)){
			IdentifyQuestionDataM identifyQuestion = verificationResult.getIndentifyQuesitionNo(VER_HR_RESULT_QUESTION);
			if(Util.empty(identifyQuestion)){
				identifyQuestion = new IdentifyQuestionDataM();
			}
			String verifyCustomerResult =  VerifyResult.getVerifyCustomerResult(applicationGroup);
			String verifyCustomerCompleteResult = verificationResult.getVerCusComplete();
			String verifyHrResult = verificationResult.getVerHrResultCode();
			String verifyHrQuestionResult = identifyQuestion.getCustomerAnswer();
			logger.debug("verifyCustomerResult : "+verifyCustomerResult);
			logger.debug("verifyCustomerCompleteResult : "+verifyCustomerCompleteResult);
			logger.debug("verifyHrResult : "+verifyHrResult);
			logger.debug("verifyHrQuestionResult : "+verifyHrQuestionResult);
			if(!(isContains(STATUS_COMPLETED_NOT_PASS,verifyCustomerResult) && isContains(VER_CUS_COMPLETE_CANCEL,verifyCustomerCompleteResult))){
				if(isContains(STATUS_COMPLETED_NOT_PASS,verifyHrResult)&&isContains(VER_HR_RESULT_QUESTION_DEPTH_CHECKING,verifyHrQuestionResult)){
					return ERROR_TYPE_HR_SEND_TO_FRAUD;
				}
				if(isVerificationStatusWait(verifyCustomerResult) || isVerificationStatusWait(verifyHrResult) 
						|| isVerificationStatusWait(verificationResult.getVerWebResultCode()) ){
						if(isContains(VALIDATION_STATUS_REQUIRED,verifyCustomerResult) 
								|| isContains(VALIDATION_STATUS_REQUIRED,verificationResult.getVerHrResultCode()) 
									|| isContains(VALIDATION_STATUS_REQUIRED,verificationResult.getVerWebResultCode())) {
							 return ERROR_TYPE_REQUIRED_SOME_VERIFICATION;
						}
				}else if(isContains(VALIDATION_STATUS_REQUIRED,verifyCustomerResult) 
						||isContains(VALIDATION_STATUS_REQUIRED,verifyHrResult) 
							||isContains(VALIDATION_STATUS_REQUIRED,verificationResult.getVerWebResultCode())
								||isContains(VALIDATION_STATUS_REQUIRED,verificationResult.getVerPrivilegeResultCode())
									||isContains(VALIDATION_STATUS_REQUIRED,verificationResult.getSummaryIncomeResultCode())) {
						 return ERROR_TYPE_REQUIRED_ALL_VERIFICATION;
				}
			}
		}
		return ERROR_TYPE;
	}
	
	public static boolean isVerificationStatusWait(String verificationResultCode) {
		if(VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT.equals(verificationResultCode) 
				|| VALIDATION_STATUS_WAIT_FOR_RE_CHECK.equals(verificationResultCode)) {
			return true;
		}
		return false;
	}
	
	public static  boolean isFollowUpVerCustomer(VerificationResultDataM verificationResult, ApplicationGroupDataM applicationGroup){
		if(!Util.empty(verificationResult)){
			String resultCode = VerifyResult.getVerifyCustomerResult(applicationGroup);
			if(null != resultCode && 
					(VALIDATION_STATUS_WAIVED.equals(resultCode)|| VALIDATION_STATUS_COMPLETED.equals(resultCode) 
					 || STATUS_COMPLETED_NOT_PASS.equals(resultCode))){
				return true;
			}
		}
		return false;
	}
	
	private static boolean isContains(String firstValue ,String compareValue){
		if(!Util.empty(firstValue) && !Util.empty(compareValue)){
			if(firstValue.contains(compareValue)){
				return true;
			}
		}
		return false;
	}
	
	public static void setVerificationResultByOverSLAFlag(ApplicationGroupDataM applicationGroup, String overSLAFlag){
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo : personalInfos){
				VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
			 	if(Util.empty(verificationResult)){
					verificationResult =  new VerificationResultDataM();
					personalInfo.setVerificationResult(verificationResult);
				}
			 	if(!VALIDATION_STATUS_WAIVED.equals(verificationResult.getVerCusResultCode())){
					if(DOCUMENT_OVER_SLA_BY_CUSTOMER.equals(overSLAFlag)){
						setVerificationResult(personalInfo, VALIDATION_STATUS_COMPLETED_NOT_FRAUD);
					}else if(DOCUMENT_OVER_SLA_BY_HR.equals(overSLAFlag)){
						setVerificationResult(personalInfo, VALIDATION_STATUS_COMPLETED_NOT_FRAUD);
					}
			 	}
			}
		}
	}
	
	private static void setVerificationResult(PersonalInfoDataM personalInfo, String verificationStatus){
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult =  new VerificationResultDataM();
			personalInfo.setVerificationResult(verificationResult);
		}
		String cusResultDesc = CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, verificationStatus, "DISPLAY_NAME");
		verificationResult.setVerCusResultCode(verificationStatus);
		verificationResult.setVerCusResult(cusResultDesc);
	}
	
	public static void setVerificationResult(ApplicationGroupDataM applicationGroup, String verificationStatus){
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo : personalInfos){
				setVerificationResult(personalInfo, verificationStatus);
			}
		}
	}
	
	public static void setVerificationResultByDecision(ApplicationGroupDataM applicationGroup, String approvalResultDecision){
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
		
		PersonalInfoDataM personalInfoM = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(Util.empty(personalInfoM)){
			personalInfoM = new PersonalInfoDataM();
		}
		VerificationResultDataM verificationResultM = personalInfoM.getVerificationResult();
		if(Util.empty(verificationResultM)){
			verificationResultM = new VerificationResultDataM();
		}
		ArrayList<PhoneVerificationDataM> phoneVerifications = verificationResultM.filterPhoneVerifications(applicationGroup.getLifeCycle());
		
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo : personalInfos){
				logger.debug("setVerificationResultByDecision.personalId >> "+personalInfo.getPersonalId());
				VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
				if (Util.empty(verificationResult)){
					verificationResult = new VerificationResultDataM();
					personalInfo.setVerificationResult(verificationResult);
				}
				
				if(!VALIDATION_STATUS_WAIVED.equals(verificationResult.getVerCusResultCode())){
					logger.debug("verificationResult >> "+verificationResult.getVerCusComplete());
					verificationResult.setVerCusComplete(approvalResultDecision);
					if(!Util.empty(phoneVerifications)){
						if (VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER.equals(approvalResultDecision)) {
							logger.debug("Case Not Contact ... ");
							setVerificationResult(personalInfo, VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT);
						}else if(VER_CUS_COMPLETE_CANCEL.equals(approvalResultDecision)){
							logger.debug("Case Cancel app ..");
							setVerificationResult(personalInfo, VALIDATION_STATUS_COMPLETED_NOT_PASS);
						}else if (VER_CUS_COMPLETE_VERIFY_COMPLETED.equals(approvalResultDecision)) {
							logger.debug("VER_CUS_COMPLETE_VERIFY_COMPLETED >> "+approvalResultDecision);
							ArrayList<CustomerVerifyQuestionResult> customerVerifyResults = getCustomerVerifyQuestionResult(verificationResult);	
							if(validateCustomerVerifyResult(customerVerifyResults)){										
								setVerificationResult(personalInfo, VALIDATION_STATUS_COMPLETED);
							}else{	
								setVerificationResult(personalInfo, VALIDATION_STATUS_COMPLETED_NOT_PASS);
							}
						}
					}
				}
			}
		}
	}
	
	public static void setVerificationResultByPhoneStatus(ApplicationGroupDataM applicationGroup,
			String phoneNo, String contactType, String phoneVerStatus, String remark,
			String ext, HttpServletRequest request){
		UserDetailM	userM =(UserDetailM)request.getSession().getAttribute("ORIGUser");
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo : personalInfos){
				if(!Util.empty(personalInfo)){
				 	logger.debug("phoneVerStatus >> "+phoneVerStatus);
				 	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
				 	if(Util.empty(verificationResult)){
						verificationResult =  new VerificationResultDataM();
						personalInfo.setVerificationResult(verificationResult);
					}
				 	
					if(!Util.empty(phoneVerStatus)){
						setVerificationResult(personalInfo, VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT);
						if(MConstant.FLAG_Y.equals(phoneVerStatus)){
							verificationResult.setVerCusComplete(VER_CUS_COMPLETE_VERIFY_COMPLETED);
						}else if(MConstant.FLAG_N.equals(phoneVerStatus)){
							verificationResult.setVerCusComplete(VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER);
						}
					}

					ArrayList<PhoneVerificationDataM> phoneVerifications = verificationResult.getPhoneVerifications();
					if(Util.empty(phoneVerifications)){
						phoneVerifications = new ArrayList<PhoneVerificationDataM> ();
						verificationResult.setPhoneVerifications(phoneVerifications);
					}
					StringBuilder str = new StringBuilder();
					str.append(phoneNo);
					if(!Util.empty(ext)){
						str.append("  "+MessageUtil.getText(request, "TO"));
						str.append("  "+ext);
					}
					PhoneVerificationDataM phoneVerification = new PhoneVerificationDataM();
						phoneVerification.setSeq(1);
						phoneVerification.setRemark(remark);
						phoneVerification.setTelephoneNumber(str.toString());
						phoneVerification.setPhoneVerStatus(phoneVerStatus);
						phoneVerification.setContactType(contactType);
						phoneVerification.setCreateDate(ApplicationDate.getTimestamp());
						phoneVerification.setCreateBy(userM.getFirstName());
						phoneVerification.setLifeCycle(applicationGroup.getLifeCycle());
					phoneVerifications.add(phoneVerification);
				 	
				}
			}
		}
	}
	
	public static void setVerificationResultByBirthDate(ApplicationGroupDataM applicationGroup){
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo : personalInfos){
				Date birthDate = personalInfo.getBirthDate();
				VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
			 	if(Util.empty(verificationResult)){
					verificationResult =  new VerificationResultDataM();
					personalInfo.setVerificationResult(verificationResult);
				}
				if(!VALIDATION_STATUS_WAIVED.equals(verificationResult.getVerCusResultCode())){
					BigDecimal totWorkYear = personalInfo.getTotWorkYear();
					logger.debug("birthDate >> "+birthDate);
					logger.debug("totWorkYear >> "+totWorkYear);			
					if(isOverTotWorkYear(birthDate,totWorkYear)){
						setVerificationResult(personalInfo, VALIDATION_STATUS_REQUIRED);
					}
				}
			}
		}
	}
	
	private static ArrayList<CustomerVerifyQuestionResult> getCustomerVerifyQuestionResult(VerificationResultDataM verificationResult){
		ArrayList<CustomerVerifyQuestionResult> customerVerifyResults = new ArrayList<CustomerVerifyQuestionResult>();
		ArrayList<IdentifyQuestionSetDataM> questionSets = verificationResult.filterIndentifyQuesitionSets(QUESTION_SET_TYPE_VERIRY_CUSTOMER);
		if(!Util.empty(questionSets)){
			for(IdentifyQuestionSetDataM questionSet:questionSets){
				String questionSetCode = questionSet.getQuestionSetCode();
				logger.debug("questionSetCode >> "+questionSetCode);
				int minAnswer = Integer.parseInt(CacheControl.getName(QUESTION_SET_CACHE_DATAM,"SET_TYPE",questionSetCode,"MIN_ANSWER"));		
				ArrayList<HashMap<String,Object>> questions = CacheControl.search(QUESTION_CACHE,"SET_TYPE",questionSetCode);
				int answer = 0;	
				
				if(!Util.empty(questions)){
					for(HashMap<String, Object> row : questions){
						String questionNo = (String)row.get("CODE");
						IdentifyQuestionDataM identifyQuestion = verificationResult.getIndentifyQuesitionNo(questionNo,questionSetCode);						
						if(null != identifyQuestion && !Util.empty(identifyQuestion.getResult()) 
								&& MConstant.FLAG_Y.equals(identifyQuestion.getResult()) 
								&& questionSetCode.equals(identifyQuestion.getQuestionSetCode())){
							answer++;
						}
					}
				}
				CustomerVerifyQuestionResult customerVerifyQuestionResult = new CustomerVerifyQuestionResult();
				customerVerifyQuestionResult.setCount(answer);
				customerVerifyQuestionResult.setQuestionSet(questionSetCode);
				if (answer >= minAnswer){
					customerVerifyQuestionResult.setResult(SystemConstant.getConstant("DECISION_ACTION_PASS"));
				}else{
					customerVerifyQuestionResult.setResult(SystemConstant.getConstant("DECISION_ACTION_FAIL"));
				}
				logger.debug("customerVerifyQuestionResult >> "+customerVerifyQuestionResult.getResult());
				logger.debug("answer >> "+answer);
				logger.debug("minAnswer >> "+minAnswer);
				customerVerifyResults.add(customerVerifyQuestionResult);
			}
		}
		return customerVerifyResults;	
	}
	
	private static boolean validateCustomerVerifyResult(ArrayList<CustomerVerifyQuestionResult> customerVerifyResults){
		 if(!Util.empty(customerVerifyResults)){
			for(CustomerVerifyQuestionResult customerVerifyQuestionResult:customerVerifyResults){
				if(SystemConstant.getConstant("DECISION_ACTION_FAIL").equals(customerVerifyQuestionResult.getResult())){
					return false;
				}
			} 
		}
		return true;
	}
	
	private static boolean isOverTotWorkYear(Date birthDate,BigDecimal totWork){
		try{
			  if(!Util.empty(birthDate) && !Util.empty(totWork)){
				  if(getAge(birthDate).compareTo(totWork)==-1){
					  return true;
				  }
			  }
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return false;
	}
	
	private static BigDecimal getAge(Date birthDate) {
	    if (!Util.empty(birthDate)) {
	    	Calendar birthDateCal = Calendar.getInstance();
	    	birthDateCal.setTime(birthDate);
	    	Calendar CurrentCal = Calendar.getInstance();
	    	int age = CurrentCal.get(Calendar.YEAR) - birthDateCal.get(Calendar.YEAR);
	    	if (CurrentCal.get(Calendar.DAY_OF_YEAR) < birthDateCal.get(Calendar.DAY_OF_YEAR))
	    	age--;
	    	logger.debug(">>>>AGE>>>"+age);
	    	return  new BigDecimal(age);
	    }
	    return BigDecimal.ZERO;
	}
	public static class VerifyResult{
		public static String getVerifyIncomeResult(ApplicationGroupDataM applicationGroup,String roleId){
			logger.debug("roleId : "+roleId);
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
			if(Util.empty(verificationResult)){
				verificationResult = new VerificationResultDataM();
			}
			String verifyIncomeResultCode = verificationResult.getSummaryIncomeResultCode();
			String ROLE_DV = SystemConstant.getConstant("ROLE_DV");
			if(ROLE_DV.equals(roleId)){
				if(!Util.empty(verifyIncomeResultCode) && !VALIDATION_STATUS_WAIVED.equals(verifyIncomeResultCode)){
					verifyIncomeResultCode = getVerifyIncomeCompareFlag(applicationGroup,verifyIncomeResultCode);
				}
			}
			logger.debug("verifyIncomeResultCode : "+verifyIncomeResultCode);
			return verifyIncomeResultCode;
		}
		public static String getVerifyCustomerResult(ApplicationGroupDataM applicationGroup){
			String verifyCustomerResult = "";
			int requireCase = 0;
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
			for(PersonalInfoDataM personalInfoM : personalInfos){
				logger.debug("personalInfoM >> "+personalInfoM.getPersonalId());
				VerificationResultDataM verificationResultM = personalInfoM.getVerificationResult();
				if(Util.empty(verificationResultM)){
					verificationResultM = new VerificationResultDataM();
					personalInfoM.setVerificationResult(verificationResultM);
				}
				if(!VALIDATION_STATUS_WAIVED.equals(verificationResultM.getVerCusResultCode())){							
					if(VALIDATION_STATUS_REQUIRED.equals(verificationResultM.getVerCusResultCode())){
						logger.debug("verCusResultCode is require");
						verifyCustomerResult = verificationResultM.getVerCusResultCode();
						break;
					}				
					if(!Util.empty(verificationResultM.getVerCusComplete())){
						verifyCustomerResult = verificationResultM.getVerCusResultCode();
						requireCase++;
					}
				}
				if(VALIDATION_STATUS_WAIVED.equals(verificationResultM.getVerCusResultCode())){											
					verifyCustomerResult = verificationResultM.getVerCusResultCode();
				}
			}		
			if(!Util.empty(verifyCustomerResult)){
				if(requireCase == personalInfos.size()){
					PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
					logger.debug("personalInfo.getPersonalId() >> "+personalInfo.getPersonalId());
					VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
					if(Util.empty(verificationResult)){
						verificationResult = new VerificationResultDataM();
						personalInfo.setVerificationResult(verificationResult);
					}
					
					if(!Util.empty(verificationResult.getVerCusComplete())){
						verifyCustomerResult = verificationResult.getVerCusResultCode();
					}
				}
			}
			logger.debug("verifyCustomerResult : "+verifyCustomerResult);
			return verifyCustomerResult;
		}
	
		//KPL Additional
		public static void setRequiredVerifyCustomer(ApplicationGroupDataM applicationGroup)
		{
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
			for(PersonalInfoDataM personalInfoM : personalInfos){
				logger.debug("personalInfoM >> "+personalInfoM.getPersonalId());
				VerificationResultDataM verificationResultM = personalInfoM.getVerificationResult();
				if(Util.empty(verificationResultM)){
					verificationResultM = new VerificationResultDataM();
					personalInfoM.setVerificationResult(verificationResultM);
				}
				
				//Manually set required ver_cus
				verificationResultM.setVerCusComplete(null);
				verificationResultM.setVerCusResult("Required");
				verificationResultM.setVerCusResultCode("01");
				verificationResultM.setRequiredVerCustFlag(MConstant.FLAG.YES);
			}	
			
		}
	
	}
	
	public static String getVerifyIncomeCompareFlag(ApplicationGroupDataM applicationGroup,String currentResultCode){
		logger.debug("currentResultCode : "+currentResultCode);
		if(!Util.empty(applicationGroup)){
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);	
			ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationRelationLifeCycle(personalInfo.getPersonalId());
			ArrayList<IncomeInfoDataM>  incomeInfos = personalInfo.getIncomeInfos();
			VerificationResultDataM resultDataM = personalInfo.getVerificationResult();
			String requireVerIncome = resultDataM.getRequiredVerIncomeFlag();
			logger.debug("incomeInfos : "+incomeInfos);
			logger.debug("requireVerIncome : "+requireVerIncome);
			if(MConstant.FLAG_Y.equals(requireVerIncome)){
				if(!Util.empty(incomeInfos)){
					for(IncomeInfoDataM incomeInfo:incomeInfos){
						if(!Util.empty(incomeInfo) && !Util.empty(incomeInfo.getCompareFlag()) && incomeInfo.getCompareFlag().equals(MConstant.FLAG_N)) {
							logger.debug("Find Income compareFlag : "+incomeInfo.getCompareFlag());
							return VALIDATION_STATUS_REQUIRED;
						}					
					}	
				}
			}else{
				return VALIDATION_STATUS_WAIVED;
			}
			if(!Util.empty(applications)){
				for(ApplicationDataM applicationItem:applications){
					BundleHLDataM bundleHL = applicationItem.getBundleHL();
					BundleSMEDataM bundleSME = applicationItem.getBundleSME();
					BundleKLDataM bundleKL = applicationItem.getBundleKL();					
					///// check bundleHL compareFlag
					if(!Util.empty(bundleHL) && MConstant.FLAG_N.equals(bundleHL.getCompareFlag())){
						logger.debug("Find bundleHL compareFlag >> "+bundleHL.getCompareFlag());
						return VALIDATION_STATUS_REQUIRED;
					}
					///// check bundleSME compareFlag
					if(!Util.empty(bundleSME) &&  MConstant.FLAG_N.equals(bundleSME.getCompareFlag())){
						logger.debug("Find bundleSME compareFlag >> "+bundleSME.getCompareFlag());
						return VALIDATION_STATUS_REQUIRED;
					}
					///// check bundleKL compareFlag
					if(!Util.empty(bundleKL) && MConstant.FLAG_N.equals(bundleKL.getCompareFlag())){
						logger.debug("Find bundleKL compareFlag >> "+bundleKL.getCompareFlag());
						return VALIDATION_STATUS_REQUIRED;
					}					
				}				
			}			
		}
		return currentResultCode;
	}
	
	
	public static boolean isCompleteVerCutomer(VerificationResultDataM verRsult){
		String verCustResult =verRsult.getVerCusResultCode();
		logger.debug("verCustResult >> "+verCustResult);
		if(VALIDATION_STATUS_COMPLETED.equals(verCustResult) || VALIDATION_STATUS_COMPLETED_NOT_PASS.equals(verCustResult) 
				|| VALIDATION_STATUS_COMPLETED_NOT_FRAUD.equals(verCustResult)){
			return true;
		}	
			return false;
	}
	public static boolean isCompleteVerWeb(VerificationResultDataM verRsult){
		String verWebtResultCode =verRsult.getVerWebResultCode();
		logger.debug("verWebtResultCode >> "+verWebtResultCode);
		if(VALIDATION_STATUS_COMPLETED.equals(verWebtResultCode) || VALIDATION_STATUS_COMPLETED_NOT_PASS.equals(verWebtResultCode) 
				|| VALIDATION_STATUS_COMPLETED_NOT_FRAUD.equals(verWebtResultCode)){
			return true;
		}	
		return false;
	}
	
	@SuppressWarnings("serial")
	public static class CustomerVerifyQuestionResult implements Serializable,Cloneable{
		public CustomerVerifyQuestionResult(){
			super();
		}
		private String questionSet;
		private String result;
		private int count;
		public String getQuestionSet() {
			return questionSet;
		}
		public void setQuestionSet(String questionSet) {
			this.questionSet = questionSet;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}		
	}
}
