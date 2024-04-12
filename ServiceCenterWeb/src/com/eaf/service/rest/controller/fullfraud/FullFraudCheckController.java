package com.eaf.service.rest.controller.fullfraud;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsefa.Serializer;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.config.CsvConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankBranchInfoDataM;
import com.eaf.orig.ulo.fraudonline.util.CatchReadUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.KVIIncomeDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.fraudonline.FullFraudCheckResponse;
import com.eaf.orig.ulo.model.fraudonline.data.Applicant;
import com.eaf.orig.ulo.model.fraudonline.data.ApplicantCont;
import com.eaf.orig.ulo.model.fraudonline.data.ApplicantContTypeConverter;
import com.eaf.orig.ulo.model.fraudonline.data.ApplicantTypeConverter;
import com.eaf.orig.ulo.model.fraudonline.data.Application;
import com.eaf.orig.ulo.model.fraudonline.data.FinancialsPerApplicant;
import com.eaf.orig.ulo.model.fraudonline.data.FinancialsPerApplicantTypeConverter;
import com.eaf.orig.ulo.model.fraudonline.data.Introducer;
import com.eaf.orig.ulo.model.fraudonline.data.SubmitApplicationObject;
import com.eaf.orig.ulo.model.fraudonline.data.Valuer;
import com.eaf.orig.ulo.model.fraudonline.data.ValuerTypeConverter;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterController;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.manual.FraudonlinecheckProxy;
import com.eaf.service.module.model.KBKInstinctFraudCheckRequestDataM;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.model.ServiceResponse;

@Controller
public class FullFraudCheckController {


	static final transient Logger logger = Logger.getLogger(FullFraudCheckController.class);
	
	String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE");
	String FIELD_ID_NATIONALITY = SystemConstant.getConstant("FIELD_ID_NATIONALITY");
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");
	String FIELD_ID_MARRIED = SystemConstant.getConstant("FIELD_ID_MARRIED");
	String FIELD_ID_ADRSTS = SystemConstant.getConstant("FIELD_ID_ADRSTS");
	String FIELD_ID_PROFESSION = SystemConstant.getConstant("FIELD_ID_PROFESSION");
	String FIELD_ID_FRAUD_KBANK_REGION = SystemConstant.getConstant("FIELD_ID_FRAUD_KBANK_REGION");
	String FIELD_ID_FRAUD_KBANK_BRANCHPROVINCE = SystemConstant.getConstant("FIELD_ID_FRAUD_KBANK_BRANCHPROVINCE");
	String FIELD_ID_PRIORITY = SystemConstant.getConstant("FIELD_ID_PRIORITY");
	String FIELD_ID_BUSINESS_NATURE = SystemConstant.getConstant("FIELD_ID_BUSINESS_NATURE");
	String FIELD_ID_BUSINESS_TYPE = SystemConstant.getConstant("FIELD_ID_BUSINESS_TYPE");
	String FIELD_ID_TYPE_OF_FIN = SystemConstant.getConstant("FIELD_ID_TYPE_OF_FIN");
	String FIELD_ID_POSITION_LEVEL = SystemConstant.getConstant("FIELD_ID_POSITION_LEVEL");
	String FIELD_ID_DEGREE = SystemConstant.getConstant("FIELD_ID_DEGREE");
	String FIELD_ID_PLACE_RECEIVE_CARD = SystemConstant.getConstant("FIELD_ID_PLACE_RECEIVE_CARD");
	String FIELD_ID_PAYMENT_RATIO = SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO");
	String FIELD_ID_PAYMENT_METHOD = SystemConstant.getConstant("FIELD_ID_PAYMENT_METHOD");
	String FIELD_ID_RELATION_WITH_APPLICANT = SystemConstant.getConstant("FIELD_ID_RELATION_WITH_APPLICANT");
	String FIELD_ID_APPLICATION_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String FIELD_ID_APPLICATION_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_TYPE");
	
	String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	String CACHE_APPLY_CHANNEL = SystemConstant.getConstant("CACHE_APPLY_CHANNEL");
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String CACHE_NAME_USER = SystemConstant.getConstant("CACHE_NAME_USER");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	
	String SALE_TYPE_NORMAL_SALES = SystemConstant.getConstant("SALE_TYPE_NORMAL_SALES");
	String FRAUD_ONLINE_CHECK_URL=SystemConfig.getProperty("FRAUD_ONLINE_CHECK_URL");
	String SYSTEM_USER=SystemConfig.getProperty("SYSTEM_USER");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	String INC_TYPE_KVI2_INCOME = SystemConstant.getConstant("INC_TYPE_KVI2_INCOME");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD");
	String CASH_DAY_1 = SystemConstant.getConstant("CASH_DAY_1");
	
	
	String FULL_FRAUD_APPLICATION_PREFIX = SystemConstant.getConstant("FULL_FRAUD_APPLICATION_PREFIX");
	String FULL_FRAUD_APPLICANT_PREFIX = SystemConstant.getConstant("FULL_FRAUD_APPLICANT_PREFIX");
	String FULL_FRAUD_FIANANCIAL_PREFIX = SystemConstant.getConstant("FULL_FRAUD_FIANANCIAL_PREFIX");
	String FULL_FRAUD_APPLICANT_COUNT_PREFIX = SystemConstant.getConstant("FULL_FRAUD_APPLICANT_COUNT_PREFIX");
	String FULL_FRAUD_INTRODUCER_PREFIX = SystemConstant.getConstant("FULL_FRAUD_INTRODUCER_PREFIX");
	String FULL_FRAUD_VALUER_PREFIX = SystemConstant.getConstant("FULL_FRAUD_VALUER_PREFIX");
	
	String FULL_FRAUD_COUNTRY = SystemConstant.getConstant("FULL_FRAUD_COUNTRY");
	String FULL_FRAUD_DECISION = SystemConstant.getConstant("FULL_FRAUD_DECISION");
	String FULL_FRAUD_APPLICATION_USERFEILD1 = SystemConstant.getConstant("FULL_FRAUD_APPLICATION_USERFEILD1");
	String FULL_FRAUD_APPLICATION_USERFEILD16 = SystemConstant.getConstant("FULL_FRAUD_APPLICATION_USERFEILD16");
	String FULL_FRAUD_APPLICATION_USERFEILD21 = SystemConstant.getConstant("FULL_FRAUD_APPLICATION_USERFEILD21");
	String FULL_FRAUD_APPLICATION_USERFEILD22 = SystemConstant.getConstant("FULL_FRAUD_APPLICATION_USERFEILD22");
	String FULL_FRAUD_APPTYPE_CARD = SystemConstant.getConstant("FULL_FRAUD_APPTYPE_CARD");
	String FULL_FRAUD_APPTYPE_KPL = SystemConstant.getConstant("FULL_FRAUD_APPTYPE_KPL");
	String FULL_FRAUD_KBLANK_FLAG_YES = SystemConstant.getConstant("FULL_FRAUD_KBLANK_FLAG_YES");
	String FULL_FRAUD_KBLANK_FLAG_NO = SystemConstant.getConstant("FULL_FRAUD_KBLANK_FLAG_NO");
	String FULL_FRAUD_APPLICATIONCOUNT_USERFEILD6 = SystemConstant.getConstant("FULL_FRAUD_APPLICATIONCOUNT_USERFEILD6");
	
	@RequestMapping(value="/fullFraud",method={ RequestMethod.PUT, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<ProcessResponse> fullFraud(@RequestParam String applicationGroupId)throws IOException{	
				
		ProcessResponse response=new ProcessResponse();
		
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		ServiceResponseDataM serviceResponseDataM  = new ServiceResponseDataM();
		ServiceCenterController serviceController = new ServiceCenterController();
		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
			serviceLogRequest.setRefCode(applicationGroupId);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject(applicationGroupId);
			serviceLogRequest.setServiceId(ServiceConstant.ServiceId.FullFraudCheck);
			serviceLogRequest.setUniqueId(applicationGroupId);
			serviceLogRequest.setUserId(SYSTEM_USER);
		serviceController.createLog(serviceLogRequest);
		
		ArrayList<String> validJobStateFullFraud = SystemConstant.getArrayListConstant("VALID_JOBSTATE_FRAUDONLINE");
		
		try{
			//FullFraudCheck
			ApplicationGroupDataM applicationGroupDataM= ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(applicationGroupId);
			
			if (null != applicationGroupDataM && null != applicationGroupDataM.getJobState() && !Util.empty(validJobStateFullFraud) && validJobStateFullFraud.contains(applicationGroupDataM.getJobState()) ) {
				applicationGroupDataM.setUserId(applicationGroupDataM.getSourceUserId());
				ProcessActionResponse decisionResponse = EAppAction.requestDecision(applicationGroupDataM, DecisionServiceUtil.DECISION_POINT.DECISION_POINT_POST_APPROVAL, SYSTEM_USER);
				String decisionResultCode = decisionResponse.getResultCode();						
				logger.debug("decisionResultCode : "+decisionResultCode);	
				response.setStatusCode(decisionResponse.getResultCode());
				if(ServiceResponse.Status.SUCCESS.equals(decisionResultCode)){
					SubmitApplicationObject submitApplicationObject=new SubmitApplicationObject();
	
					
					//Application application=fraudonlineDAO.selectApplication(id);
					Application application=new Application();
					application.setOrganisation(FULL_FRAUD_APPLICATION_PREFIX);
					application.setCountryCode(FULL_FRAUD_COUNTRY);
					application.setApplicationNumber(applicationGroupDataM.getApplicationGroupNo());
					application.setCaptureDate(ServiceUtil.display(ServiceApplicationDate.getTimestamp(),ServiceUtil.EN,"dd/MM/yyyy"));
					application.setCaptureTime(ServiceUtil.display(ServiceApplicationDate.getTimestamp(),ServiceUtil.EN,"HHmmss"));
					application.setApplicationDate(ServiceUtil.display(applicationGroupDataM.getApplicationDate(),ServiceUtil.EN,"dd/MM/yyyy"));
					//application.setBranch(applicationGroupDataM.getBranchName());
					application.setDecision(applicationGroupDataM.getApplicationStatus());
					application.setUserField1(CacheControl.getName(CACHE_APPLY_CHANNEL, applicationGroupDataM.getApplyChannel()));
					application.setUserField5(CacheControl.getName(CACHE_TEMPLATE, applicationGroupDataM.getApplicationTemplate()));
					application.setUserField16(FULL_FRAUD_APPLICATION_USERFEILD16);	
					application.setUserField17(applicationGroupDataM.getBranchName());
					application.setUserField21(FULL_FRAUD_APPLICATION_USERFEILD21);
					application.setUserField22(applicationGroupDataM.getCoverpageType());
					application.setUserField23(CacheControl.displayName(FIELD_ID_PRIORITY, applicationGroupDataM.getPriority()+""));
					application.setUserField25(ServiceUtil.replaceAll(applicationGroupDataM.getApplicationGroupNo(), "-"));
					ArrayList<ApplicationDataM> applicationDatas = applicationGroupDataM.getApplications();
					
					
					String appType=FULL_FRAUD_APPTYPE_CARD;
					Pattern p = Pattern.compile(FULL_FRAUD_APPTYPE_KPL+".*");
					Matcher m = null;
					BigDecimal loanAmt=null;
					
					for(ApplicationDataM applicationData:applicationDatas){
				
						//if(applicationData.getApplicationRecordId().equals(applicationData.getMaincardRecordId())){
							
							m = p.matcher(applicationData.getBusinessClassId());
							if(m.find()){
								appType=FULL_FRAUD_APPTYPE_KPL;
							}
							if(applicationData.getLoan()!=null&&applicationData.getLoan().getLoanAmt()!=null){
								loanAmt=applicationData.getLoan().getLoanAmt();
								//logger.debug("m2-"+applicationData.getLoan().getLoanAmt());
								//logger.debug("loanAmt-"+loanAmt);
							}
							
							
						//}
					}
					//logger.debug("loanAmt-"+loanAmt);
					application.setApplicationType(appType);
					application.setAmountLimit(ServiceUtil.empty(loanAmt)?"":loanAmt.toString());
					
					List<Applicant> applicants=new ArrayList<>();
					List<FinancialsPerApplicant> financialsPerApplicants=new ArrayList<>();
					List<ApplicantCont> applicantConts=new ArrayList<>();
					List<Valuer> valuers=new ArrayList<>();
					
					
					ArrayList<PersonalInfoDataM> personalInfoDatas =applicationGroupDataM.getPersonalInfos();
					for(PersonalInfoDataM personalInfoData:personalInfoDatas){
						
						AddressDataM home= personalInfoData.getAddress(ADDRESS_TYPE_CURRENT);
						AddressDataM office= personalInfoData.getAddress(ADDRESS_TYPE_WORK);
						//AddressDataM regisAddress= personalInfoData.getAddress(ADDRESS_TYPE_DOCUMENT);
						
						PaymentMethodDataM paymentMethodDataM=applicationGroupDataM.getPaymentMethodLifeCycleByPersonalId(personalInfoData.getPersonalId());
						IncomeInfoDataM incomeInfoDataM=personalInfoData.getIncomeByType(INC_TYPE_KVI2_INCOME);
						//incomeInfoDataM.get
						ArrayList<IncomeCategoryDataM> incomeList=new ArrayList<>();
						if(incomeInfoDataM!=null){
							incomeList = incomeInfoDataM.getAllIncomes();
						}
						KVIIncomeDataM kviM = null;
						if(incomeList!=null&&incomeList.size() > 0){
							kviM = (KVIIncomeDataM)incomeList.get(0);
						}
						LoanDataM loan=new LoanDataM(); 
						ArrayList<ApplicationDataM> applications= applicationGroupDataM.getApplications(personalInfoData.getPersonalId(), personalInfoData.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
						for(ApplicationDataM applicationData:applications){
							if(applicationData.getLoan()!=null){
								loan=applicationData.getLoan();
							}
							
						}
						
						String kbankFlag=(FLAG_YES.equals(personalInfoData.getkGroupFlag())?FULL_FRAUD_KBLANK_FLAG_YES:FULL_FRAUD_KBLANK_FLAG_NO);
						
						BigDecimal totWorkYear = !ServiceUtil.empty(personalInfoData.getTotWorkYear())?personalInfoData.getTotWorkYear():null;
						BigDecimal totWorkMonth = !ServiceUtil.empty(personalInfoData.getTotWorkMonth())?personalInfoData.getTotWorkMonth():null;
						BigDecimal totalwork = BigDecimal.ZERO;
						if(ServiceUtil.empty(totWorkYear) && ServiceUtil.empty(totWorkMonth)){
							totalwork = null;
						}else{
							totWorkYear = ServiceUtil.toBigDecimal(personalInfoData.getTotWorkYear(),false);
							totWorkMonth = ServiceUtil.toBigDecimal(personalInfoData.getTotWorkMonth(),false);
							totalwork=totWorkYear.multiply(new BigDecimal(12)).add(totWorkMonth);
						}
						
						BigDecimal residey = !ServiceUtil.empty(home.getResidey())?home.getResidey():null;
						BigDecimal residem = !ServiceUtil.empty(home.getResidem())?home.getResidem():null;
						BigDecimal reside = BigDecimal.ZERO;
						if(ServiceUtil.empty(residey) && ServiceUtil.empty(residem)){
							reside = null;
						}else{
							residey = ServiceUtil.toBigDecimal(home.getResidey(),false);
							residem = ServiceUtil.toBigDecimal(home.getResidem(),false);
							reside = residey.multiply(new BigDecimal(12)).add(residem);
						}
						BigDecimal salary= !ServiceUtil.empty(personalInfoData.getSalary())?personalInfoData.getSalary():null;
						BigDecimal otherIncome= !ServiceUtil.empty(personalInfoData.getOtherIncome())?personalInfoData.getOtherIncome():null;
						BigDecimal circulationIncome=ServiceUtil.toBigDecimal(personalInfoData.getCirculationIncome(),false);
						BigDecimal freelanceIncome=ServiceUtil.toBigDecimal(personalInfoData.getFreelanceIncome(),false);
						BigDecimal netProfitIncome=ServiceUtil.toBigDecimal(personalInfoData.getNetProfitIncome(),false);
						
						//change summary field
//						BigDecimal sumsalaryother=circulationIncome.add(freelanceIncome).add(netProfitIncome).add(otherIncome);
						BigDecimal sumsalaryother = null;
						if(!ServiceUtil.empty(salary) && !ServiceUtil.empty(otherIncome)){
							sumsalaryother = salary.add(otherIncome);
						}else if(!ServiceUtil.empty(salary) && ServiceUtil.empty(otherIncome)){
							sumsalaryother = salary;
						}else if(ServiceUtil.empty(salary) && !ServiceUtil.empty(otherIncome)){
							sumsalaryother = otherIncome;
						}
						BigDecimal totVerifiedIncome= !ServiceUtil.empty(personalInfoData.getTotVerifiedIncome())?personalInfoData.getTotVerifiedIncome():null;				
						BigDecimal rent= !ServiceUtil.empty(home.getRents())?home.getRents():null;			
						BigDecimal debtBurden = !ServiceUtil.empty(loan.getDebtBurden())?loan.getDebtBurden():null;	
						
					
						
						Applicant applicant=new Applicant();
						applicant.setFixVariable(FULL_FRAUD_APPLICANT_PREFIX);
						applicant.setIdNumber1(personalInfoData.getIdno());
						applicant.setIdNumber2(CacheControl.displayName(FIELD_ID_CID_TYPE, personalInfoData.getCidType()));
						applicant.setIdNumber3(personalInfoData.getPersonalType());
						applicant.setFirstName(personalInfoData.getThFirstName());
						applicant.setSurname(personalInfoData.getThLastName());
						applicant.setSex(personalInfoData.getGender());
						applicant.setDateOfBirth(ServiceUtil.display(personalInfoData.getBirthDate(),ServiceUtil.EN,"dd/MM/yyyy"));
						applicant.setHomeAddress1(home.getAddress());
						applicant.setHomeAddress2( ServiceUtil.toNumericStr(home.getMoo()));
						applicant.setHomeAddress3(home.getSoi());
						applicant.setHomeAddress4(home.getRoad());
						applicant.setHomeAddress5(home.getTambol());
						applicant.setHomeAddress6(home.getAmphur());
						applicant.setHomePostcode(home.getZipcode());
						applicant.setHomePhoneNumber(home.getPhone1());
						applicant.setMobilePhoneNumber(ServiceUtil.replaceAll(personalInfoData.getMobileNo(), "-"));
						applicant.setCompanyName(office.getCompanyName());
						applicant.setCompanyAddress1(office.getAddress());
						applicant.setCompanyAddress2( ServiceUtil.toNumericStr(office.getMoo()));
						applicant.setCompanyAddress3(office.getSoi());
						applicant.setCompanyAddress4(office.getRoad());
						applicant.setCompanyAddress5(office.getTambol());
						applicant.setCompanyAddress6(office.getAmphur());
						applicant.setCompanyPostcode(office.getZipcode());
						applicant.setCompanyPhoneNumber(office.getPhone1());
						applicant.setUserField1(CacheControl.displayName(FIELD_ID_NATIONALITY, personalInfoData.getNationality()));
						applicant.setUserField2(CacheControl.displayName(FIELD_ID_OCCUPATION, personalInfoData.getOccupation()));
						applicant.setUserField3(ServiceUtil.empty(totalwork)?"":totalwork.toString());
						applicant.setUserField4(CacheControl.displayName(FIELD_ID_MARRIED, personalInfoData.getMarried()));
						applicant.setUserField5(CatchReadUtil.displayName(FIELD_ID_FRAUD_KBANK_REGION, home.getProvinceDesc(), "MAPPING1", "MAPPING2"));
						applicant.setUserField6(CatchReadUtil.displayName(FIELD_ID_FRAUD_KBANK_REGION, office.getProvinceDesc(), "MAPPING1", "MAPPING2"));
						applicant.setUserField8(personalInfoData.getEnLastName());
						applicant.setUserField9(personalInfoData.getEnFirstName());
						applicant.setUserField10(salary);
						applicant.setUserField11(rent);
						applicant.setUserField12(ServiceUtil.display(personalInfoData.getCidExpDate(),ServiceUtil.EN,"dd/MM/yyyy"));
						applicant.setUserField13(kbankFlag);
						applicant.setUserField16(home.getProvinceDesc());
						applicant.setUserField17(office.getProvinceDesc());
						applicant.setUserField18(personalInfoData.getEmailPrimary());
						applicant.setUserField19(CacheControl.displayName(FIELD_ID_ADRSTS, home.getAdrsts()));
						applicant.setUserField20(ServiceUtil.empty(reside)?"":reside.toString());
			
						applicants.add(applicant);
						
	//					ArrayList<ApplicationDataM> applications= applicationGroupDataM.getApplications(personalInfoData.getPersonalId(), personalInfoData.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
	//					ApplicationDataM mainApplication=new ApplicationDataM();
	//					for(ApplicationDataM applicationData:applications){
		//
	//						if(applicationData.getMaincardRecordId().equals(applicationData.getApplicationRecordId())){
	//							mainApplication=applicationData;
	//						}
	//					}
	//					LoanDataM loan=new LoanDataM(); 
	//					CashTransferDataM cashTransferDataM=new CashTransferDataM();
	//					CardDataM cardDataM=new CardDataM();
	//					if(mainApplication!=null){
	//						loan=mainApplication.getLoan();
	//					}
	//					if(loan!=null){
	//						cashTransferDataM=loan.getCashTransfer();
	//						cardDataM=loan.getCard();
	//					}
	//					
	//					if(loan==null){
	//						loan=new LoanDataM(); 
	//					}
	//					if(cashTransferDataM==null){
	//						cashTransferDataM=new CashTransferDataM(); 
	//					}
	//					if(cardDataM==null){
	//						cardDataM=new CardDataM(); 
	//					}
						
	//					
	//					SpecialAdditionalServiceDataM creaditSheild = applicationGroupDataM.getSpecialAdditionalServiceLifeCycleByPersonalId(personalInfoData.getPersonalId(), mainApplication.getProduct(), SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD);
	//					
	//					String creaditSheildFlag=(creaditSheild!=null?FULL_FRAUD_KBLANK_FLAG_YES:FULL_FRAUD_KBLANK_FLAG_NO);
	//					String CASH_DAY_1Flag=(cashTransferDataM!=null&&CASH_DAY_1.equals(cashTransferDataM.getCashTransferType())?FULL_FRAUD_KBLANK_FLAG_YES:FULL_FRAUD_KBLANK_FLAG_NO);
						
						
						FinancialsPerApplicant financialsPerApplicant=new FinancialsPerApplicant();
						financialsPerApplicant.setFixVariable(FULL_FRAUD_FIANANCIAL_PREFIX);
						financialsPerApplicant.setUserField5(personalInfoData.getThLastName());
						financialsPerApplicant.setUserField6(personalInfoData.getThFirstName());
						financialsPerApplicant.setUserField7(CacheControl.displayName(FIELD_ID_PROFESSION, personalInfoData.getProfession()));
						
						financialsPerApplicant.setUserField13(CacheControl.displayName(FIELD_ID_BUSINESS_NATURE, personalInfoData.getBusinessNature()));
						financialsPerApplicant.setUserField14(CacheControl.displayName(FIELD_ID_BUSINESS_TYPE, personalInfoData.getBusinessType()));
						financialsPerApplicant.setUserField15(CacheControl.displayName(FIELD_ID_TYPE_OF_FIN, personalInfoData.getTypeOfFin()));
						financialsPerApplicant.setUserField16(personalInfoData.getPositionDesc());
						financialsPerApplicant.setUserField17(CacheControl.displayName(FIELD_ID_POSITION_LEVEL, personalInfoData.getPositionLevel()));
						financialsPerApplicant.setUserField18(CacheControl.displayName(FIELD_ID_DEGREE, personalInfoData.getDegree()));
						
						financialsPerApplicant.setUserField23(CacheControl.displayName(FIELD_ID_PLACE_RECEIVE_CARD, personalInfoData.getPlaceReceiveCard()));
						financialsPerApplicant.setUserField24(personalInfoData.getBranchReceiveCard());
						financialsPerApplicant.setUserField25(CacheControl.displayName(FIELD_ID_PAYMENT_RATIO, String.valueOf(paymentMethodDataM.getPaymentRatio())));
						financialsPerApplicant.setUserField26(CacheControl.displayName(FIELD_ID_PAYMENT_METHOD, paymentMethodDataM.getPaymentMethod()));
						financialsPerApplicant.setUserField27(Util.empty(personalInfoData.getCirculationIncome())?"":ServiceUtil.display(personalInfoData.getCirculationIncome(),false));
						financialsPerApplicant.setUserField28(CacheControl.displayName(FIELD_ID_RELATION_WITH_APPLICANT, personalInfoData.getRelationshipType()));
						
						if(kviM!=null){
							financialsPerApplicant.setUserField33(ServiceUtil.display(kviM.getPercentChequeReturn(),false));
						}
						
						financialsPerApplicant.setUserField35(personalInfoData.getSorceOfIncomeOther());
						
						financialsPerApplicant.setUserField40(otherIncome);
						financialsPerApplicant.setUserField41(sumsalaryother);
						financialsPerApplicant.setUserField42(totVerifiedIncome);
						financialsPerApplicant.setUserField45(debtBurden);
						
						financialsPerApplicants.add(financialsPerApplicant);
						
						
						
					}
					
					SaleInfoDataM saleInfoData= applicationGroupDataM.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
					
					Introducer introducer=new Introducer();	
					introducer.setFixVariable(FULL_FRAUD_INTRODUCER_PREFIX);
					if(saleInfoData!=null){
						//DIHQueryResult<KbankBranchInfoDataM> dIHQueryResult=DIHProxy.getBranchInfoByRC_CD(saleInfoData.getSalesRCCode());
						//KbankBranchInfoDataM  kKbankBranchInfoDataM=dIHQueryResult.getResult();
	//					if(kKbankBranchInfoDataM==null){
	//						kKbankBranchInfoDataM=new KbankBranchInfoDataM();
	//					}
						
						String provinceName= CatchReadUtil.displayName(FIELD_ID_FRAUD_KBANK_BRANCHPROVINCE, saleInfoData.getSalesBranchName(), "MAPPING1", "MAPPING2");
						
						introducer.setIdNumber1(saleInfoData.getSalesBranchName());
						introducer.setIdNumber2(saleInfoData.getSalesId());
						//introducer.setSurname(saleInfoData.getSalesName());
						introducer.setCompanyAddress6(provinceName);
						
						introducer.setUserField1(applicationGroupDataM.getBranchRegion());
						introducer.setUserField2(applicationGroupDataM.getBranchZone());
						introducer.setUserField3(CatchReadUtil.displayName(FIELD_ID_FRAUD_KBANK_REGION, provinceName, "MAPPING1", "MAPPING2"));
					
						introducer.setUserField7(saleInfoData.getSalesName());
						introducer.setUserField8(saleInfoData.getRegion());
						introducer.setUserField9(saleInfoData.getZone());
						introducer.setUserField10(saleInfoData.getSalesDeptName());
					}
					
					ArrayList<ApplicationDataM> applications= applicationGroupDataM.getApplicationLifeCycle(ServiceUtil.toBigDecimal(applicationGroupDataM.getLifeCycle(),false));
					logger.debug("applications.size : "+applications.size());
					for(ApplicationDataM applicationDataM:applications){
						PersonalInfoDataM personalInfoData = applicationGroupDataM.getPersonalInfoApplication(applicationDataM.getApplicationRecordId(), PERSONAL_RELATION_APPLICATION_LEVEL);				
						AddressDataM regisAddress= personalInfoData.getAddress(ADDRESS_TYPE_DOCUMENT);
						LoanDataM loan=new LoanDataM(); 
						CashTransferDataM cashTransferDataM=new CashTransferDataM();
						CardDataM cardDataM=new CardDataM();
						if(applicationDataM!=null){
							loan=applicationDataM.getLoan();
						}
						if(loan!=null){
							cashTransferDataM=loan.getCashTransfer();
							cardDataM=loan.getCard();
						}
						
						if(loan==null){
							loan=new LoanDataM(); 
						}
						if(cashTransferDataM==null){
							cashTransferDataM=new CashTransferDataM(); 
						}
						if(cardDataM==null){
							cardDataM=new CardDataM(); 
						}
						
						
						SpecialAdditionalServiceDataM creaditSheild = applicationGroupDataM.getSpecialAdditionalServiceLifeCycleByPersonalId(personalInfoData.getPersonalId(), applicationDataM.getProduct(), SPECIAL_ADDITIONAL_SERVICE_CREDIT_SHIELD);
						
						String creaditSheildFlag=(creaditSheild!=null?FULL_FRAUD_KBLANK_FLAG_YES:FULL_FRAUD_KBLANK_FLAG_NO);
						String CASH_DAY_1Flag=(cashTransferDataM!=null&&CASH_DAY_1.equals(cashTransferDataM.getCashTransferType())?FULL_FRAUD_KBLANK_FLAG_YES:FULL_FRAUD_KBLANK_FLAG_NO);
						BigDecimal kBankRegion = ServiceUtil.empty(CatchReadUtil.displayName(FIELD_ID_FRAUD_KBANK_REGION, regisAddress.getProvinceDesc(), "MAPPING1", "MAPPING2"))?ServiceUtil.toBigDecimal(CatchReadUtil.displayName(FIELD_ID_FRAUD_KBANK_REGION, regisAddress.getProvinceDesc(), "MAPPING1", "MAPPING2"),true):null;
						BigDecimal finalCreditLimit = !ServiceUtil.empty(loan.getFinalCreditLimit())?loan.getFinalCreditLimit():null;
						BigDecimal requestLoanAmt = !ServiceUtil.empty(loan.getRequestLoanAmt())?loan.getRequestLoanAmt():null;
						BigDecimal term = !ServiceUtil.empty(loan.getTerm())?loan.getTerm():null;
						BigDecimal requestTerm = !ServiceUtil.empty(loan.getRequestTerm())?loan.getRequestTerm():null;
						
						
						ApplicantCont applicantCont=new ApplicantCont();
						applicantCont.setFixVariable(FULL_FRAUD_APPLICANT_COUNT_PREFIX);
						
						applicantCont.setIdNumber1(applicationDataM.getProjectCode());
						applicantCont.setIdNumber2(ListBoxControl.getName(FIELD_ID_APPLICATION_TYPE, "CHOICE_NO", applicationDataM.getApplicationType(), "DISPLAY_NAME"));
						applicantCont.setIdNumber3(applicationDataM.getProduct());
						logger.debug("applicantCont.getIdNumber2() : " + applicantCont.getIdNumber2());
						logger.debug("applicationDataM.getApplicationType() :" + applicationDataM.getApplicationType());
						
						applicantCont.setSurname(personalInfoData.getThLastName());
						applicantCont.setFirstName(personalInfoData.getThFirstName());
						applicantCont.setMiddleName(applicationDataM.getPolicyProgramId());
						
						applicantCont.setHomeAddress1(regisAddress.getAddress());
						applicantCont.setHomeAddress2( ServiceUtil.toNumericStr(regisAddress.getMoo()));
						applicantCont.setHomeAddress3(regisAddress.getSoi());
						applicantCont.setHomeAddress4(regisAddress.getRoad());
						applicantCont.setHomeAddress5(regisAddress.getTambol());
						applicantCont.setHomeAddress6(regisAddress.getAmphur());
						applicantCont.setHomePostcode(regisAddress.getZipcode());
						
						applicantCont.setCompanyName(regisAddress.getCompanyName());
						
						applicantCont.setUserField1(regisAddress.getProvinceDesc());
						applicantCont.setUserField2(applicationDataM.getApplicationNo());
						applicantCont.setUserField3( kBankRegion );
						
						applicantCont.setUserField6(FULL_FRAUD_APPLICATIONCOUNT_USERFEILD6);
						applicantCont.setUserField7(CacheControl.getName(CACHE_BUSINESS_CLASS, "BUS_CLASS_ID", applicationDataM.getBusinessClassId(), "BUS_CLASS_DESC"));
						//CACHE_BUSINESS_CLASS
						
						
						applicantCont.setUserField8(creaditSheildFlag);
						applicantCont.setUserField9(CASH_DAY_1Flag);
						applicantCont.setUserField10(CacheControl.displayName(FIELD_ID_APPLICATION_CARD_TYPE, cardDataM.getApplicationType()));
						applicantCont.setUserField11(finalCreditLimit);
						applicantCont.setUserField12(requestLoanAmt);
						applicantCont.setUserField13(term);
						applicantCont.setUserField14(requestTerm);
						
						applicantCont.setUserField16(cardDataM.getCardNoMark());
						//DF826 change value of cardtype send to fraud
						String cardCode = CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",cardDataM.getCardType(), "CARD_CODE");
						applicantCont.setUserField17(CacheControl.displayName(FIELD_ID_CARD_TYPE, cardCode));
						applicantCont.setUserField18(CacheControl.displayName(FIELD_ID_CARD_LEVEL, cardDataM.getCardLevel()));
						applicantCont.setUserField19(ServiceUtil.display(applicationDataM.getFinalAppDecisionDate(),ServiceUtil.EN,"dd/MM/yyyy"));
	
						
						applicantConts.add(applicantCont);
						
						
						
						Valuer valuer=new Valuer();
						valuer.setFixVariable(FULL_FRAUD_VALUER_PREFIX);
						valuer.setIdNumber2(applicationDataM.getFinalAppDecisionBy());
						valuer.setUserField7(CacheControl.getName(CACHE_NAME_USER, "USER_NAME", applicationDataM.getFinalAppDecisionBy(), "VALUE"));
						valuer.setUserField10(applicationDataM.getFinalAppDecision());
						valuers.add(valuer);
					}
	
					
					submitApplicationObject.setApplication(application);
					submitApplicationObject.setApplicants(applicants);
					submitApplicationObject.setIntroducer(introducer);
					submitApplicationObject.setValuers(valuers);
					submitApplicationObject.setFinancialsPerApplicants(financialsPerApplicants);
					submitApplicationObject.setApplicantConts(applicantConts);		
					
					CsvConfiguration config = new CsvConfiguration();
					config.setFieldDelimiter('|');
					
					
					config.getSimpleTypeConverterProvider().registerConverterType(Applicant.class, ApplicantTypeConverter.class);
					config.getSimpleTypeConverterProvider().registerConverterType(FinancialsPerApplicant.class, FinancialsPerApplicantTypeConverter.class);
					config.getSimpleTypeConverterProvider().registerConverterType(ApplicantCont.class, ApplicantContTypeConverter.class);
					config.getSimpleTypeConverterProvider().registerConverterType(Valuer.class, ValuerTypeConverter.class);
					
					Serializer serializer = CsvIOFactory.createFactory(config,SubmitApplicationObject.class).createSerializer();
					
					
	
					StringWriter writer = new StringWriter();
					serializer.open(writer);
					serializer.write(submitApplicationObject);
					serializer.close(true);
					String result=writer.toString();
					result=result.replace("\\|", "|");
					result = result.replace("\n", "").replace("\r", "");
					//result=result.replace(",", "|");
					logger.debug("main-> " + result);
					
					
					ServiceCenterProxy proxy = new ServiceCenterProxy();
	
					
					ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
					serviceRequest.setEndpointUrl(FRAUD_ONLINE_CHECK_URL);
					serviceRequest.setServiceId(FraudonlinecheckProxy.serviceId);
					serviceRequest.setUniqueId(applicationGroupDataM.getApplicationGroupId());
					serviceRequest.setRefId(applicationGroupDataM.getApplicationGroupNo());
					serviceRequest.setUserId(SYSTEM_USER);
					KBKInstinctFraudCheckRequestDataM kBKInstinctFraudCheckRequestDataM = new KBKInstinctFraudCheckRequestDataM();
					kBKInstinctFraudCheckRequestDataM.setInputString(result);
					kBKInstinctFraudCheckRequestDataM.setApplicationNo(applicationGroupDataM.getApplicationGroupNo());
					kBKInstinctFraudCheckRequestDataM.setApplicationType(appType);
					
					serviceRequest.setObjectData(kBKInstinctFraudCheckRequestDataM);
					serviceResponseDataM = proxy.requestService(serviceRequest);
					logger.debug("StatusCode-> "+serviceResponseDataM.getStatusCode());
			
				
					
					if(ServiceResponse.Status.SUCCESS.equals(serviceResponseDataM.getStatusCode())){
						response.setStatusCode(ServiceConstant.Status.SUCCESS);
					}else{
						
						response.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
						
						String errorDesc="";
						
						if(serviceResponseDataM!=null&&serviceResponseDataM.getErrorInfo()!=null){
							errorDesc=serviceResponseDataM.getErrorInfo().getErrorDesc();
						}
						ErrorData errorData=new ErrorData();
						errorData.setErrorDesc(errorDesc);
						response.setErrorData(errorData);
					}
				}
				else{
					response.setStatusCode(decisionResultCode);
					response.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, decisionResponse.getResultCode(), decisionResponse.getErrorMsg()));
				}
			}
			
		}catch(Exception e){
			
			response.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			
			
			ErrorData errorData=new ErrorData();
			errorData.setErrorDesc(e.getMessage());
			response.setErrorData(errorData);
			
			logger.fatal("ERROR",e);
			
		}
		
		
		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
		serviceLogResponse.setServiceReqRespId(serviceReqRespId);
		serviceLogResponse.setRefCode(applicationGroupId);
		serviceLogResponse.setActivityType(ServiceConstant.OUT);
		serviceLogResponse.setServiceDataObject(response);
		serviceLogResponse.setServiceId(ServiceConstant.ServiceId.FullFraudCheck);
		serviceLogResponse.setUniqueId(applicationGroupId);
		serviceLogResponse.setRespCode(response.getStatusCode());
		serviceLogResponse.setUserId(SYSTEM_USER);
		
		String errorDesc="";
		
		if(response!=null&&response.getErrorData()!=null){
			errorDesc=response.getErrorData().getErrorDesc();
		}
		
		serviceLogResponse.setErrorMessage(errorDesc);
		serviceController.createLog(serviceLogResponse);
		//new ResponseEntity<String>(response,HttpStatus.OK)
		return new ResponseEntity<ProcessResponse>(response,HttpStatus.OK);		
	}

}
