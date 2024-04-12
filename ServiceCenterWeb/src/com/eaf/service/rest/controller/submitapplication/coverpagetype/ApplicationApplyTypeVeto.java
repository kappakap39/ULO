package com.eaf.service.rest.controller.submitapplication.coverpagetype;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.docset.model.InquireDocSetResponse;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.IncomeSourceDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.LoanFeeDataM;
import com.eaf.orig.ulo.model.app.LoanTierDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.PhoneVerificationDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.submitapplication.model.SubmitApplicationObjectDataM;
import com.eaf.orig.ulo.service.app.submit.dao.SubmitApplicationManager;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.application.model.SubmitApplicationRequest;
import com.eaf.service.rest.controller.submitapplication.SubmitApplication;
import com.eaf.service.rest.model.ServiceResponse;
//import com.eaf.core.ulo.common.date.ApplicationDate;
//import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ApplicationApplyTypeVeto extends ProcessActionHelper{
	private static Logger logger = Logger.getLogger(ApplicationApplyTypeVeto.class);
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");	
	String MAX_LIFE_CYCLE_CONFIG = SystemConstant.getConstant("MAX_LIFE_CYCLE");	
	String CACHE_WORKFLOW_PARAM = SystemConstant.getConstant("CACHE_WORKFLOW_PARAM");	
	String VETO_DAY = SystemConstant.getConstant("WORK_FLOW_PARAM_VETO_DAY");	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String APPLICATION_TYPE_NEW = SystemConstant.getConstant("APPLICATION_TYPE_NEW");
	String APPLICATION_TYPE_ADD = SystemConstant.getConstant("APPLICATION_TYPE_ADD");
	String APPLICATION_TYPE_UPGRADE = SystemConstant.getConstant("APPLICATION_TYPE_UPGRADE");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String[] VETO_APPLICATION_DECISION = SystemConstant.getArrayConstant("VETO_APPLICATION_DECISION");	
	String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");	
	@Override
	public Object processAction(){
		ProcessResponse processResponse = new ProcessResponse();
			processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		try{
			SubmitApplicationObjectDataM submitApplicationObject = (SubmitApplicationObjectDataM)objectForm;
			InquireDocSetResponse inquireDocSetResponse = (InquireDocSetResponse)submitApplicationObject.getInquireDocSetResponse();
			SubmitApplicationRequest submitApplicationRequest = (SubmitApplicationRequest)submitApplicationObject.getSubmitApplicationRequest();
			String qr2 = submitApplicationRequest.getQr2();
			String qr1 = submitApplicationRequest.getQr1();
			String userId = submitApplicationObject.getUserId();
			String applicationGroupId = SubmitApplicationManager.selectApplicationGroupId(qr2);
			Timestamp scanDate = ApplicationDate.getTimestamp();
			String coverPageType = submitApplicationObject.getCoverPageType();			
			logger.debug("qr2 >> "+qr2);
			logger.debug("qr1 >> "+qr1);
			logger.debug("applicationGroupId >> "+applicationGroupId);
			logger.debug("coverPageType >> "+coverPageType);
			logger.debug("scanDate >>"+scanDate);
			SubmitApplication submitApplication = new SubmitApplication();
			if(!Util.empty(applicationGroupId)){
				ApplicationGroupDataM applicationGroup = SubmitApplicationManager.loadApplicationGroup(applicationGroupId);
				applicationGroup.setPegaEventFlag(submitApplicationRequest.getPegaEventFlag());
				clearPolicyRulePersonalLevel(applicationGroup);
				applicationGroup.setApplicationDate(Util.toSqlDate(scanDate));
				applicationGroup.setReprocessFlag("");
//				applicationGroup.setApplyDate(Util.toSqlDate(scanDate));
				String jobstate = applicationGroup.getJobState();
				logger.debug("jobstate : "+jobstate);
				if(SystemConfig.containsGeneralParam("JOB_STATE_REJECTED",jobstate)){
					applicationGroup.setUserId(submitApplicationObject.getUserId());
					boolean isVetoEligible = isVetoEligible(applicationGroup);
					logger.debug("isVetoEligible >> "+isVetoEligible);
					if(isVetoEligible){
						updateVerificationResultCustomer(applicationGroup);
						ArrayList<String> docTypeCheckLists = submitApplication.getDocTypeCheckLists(inquireDocSetResponse);
						logger.debug("docTypeCheckLists >> "+docTypeCheckLists.size());
						if(!Util.empty(docTypeCheckLists)){
							//send pega followup
							ServiceResponseDataM serviceResponse = submitApplication.sendFollowUpToPega(applicationGroup,submitApplicationObject,docTypeCheckLists,false);
							String followupToPegaResult = serviceResponse.getStatusCode();
							logger.debug("followupToPegaResult >> "+followupToPegaResult);
							if(!ServiceResponse.Status.SUCCESS.equals(followupToPegaResult)){
								processResponse =  SubmitApplication.error(serviceResponse);
							}
						}else{		
							Integer instantId = applicationGroup.getInstantId();
							logger.debug("instantId : "+instantId);
							if(SubmitApplication.errorInstant(instantId)){
								//update image
								submitApplication.mapImageIndex(inquireDocSetResponse, applicationGroup);
								LookupServiceCenter.getServiceCenterManager().updateImageDocument(applicationGroup,userId,true);		
								submitApplication.workflowTaskProcessAction(applicationGroup,userId);
							}else{
								//reopen application
								reOpenExistingApplication(applicationGroup,inquireDocSetResponse,coverPageType,userId);
								//call pega dummy
								int lifeCycle = applicationGroup.getMaxLifeCycle();
								boolean sendDummyToPega = SubmitApplication.sendDummyToPegaFlag(applicationGroupId,lifeCycle);
								logger.debug("sendDummyToPega : "+sendDummyToPega);
								if(sendDummyToPega){
									ServiceResponseDataM serviceResponse = submitApplication.sendDummyToPega(applicationGroup,submitApplicationObject);
									String followupToPegaResult = serviceResponse.getStatusCode();
									logger.debug("followupToPegaResult : "+followupToPegaResult);
									if(!ServiceResponse.Status.SUCCESS.equals(followupToPegaResult)){
										processResponse =  SubmitApplication.error(serviceResponse);
									}
								}
								//update workflow task
								String processResult = processResponse.getStatusCode();		
								logger.debug("processResult : "+processResult);
								if(ServiceResponse.Status.SUCCESS.equals(processResult)){
									submitApplication.createWorkflowTask(applicationGroup);
								}
							}
						}
					}else{
						processResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
						processResponse.setErrorData(SubmitApplication.error(ErrorData.ErrorType.SERVICE_RESPONSE
								, ProcessResponse.SubmitApplicationErrorCode.SUBMIT_APPLICATION_ERROR_NOT_FOUND_APPLICATION_VETO
								, MessageErrorUtil.getText("SUBMIT_APPLICATION_ERROR_NOT_FOUND_APPLICATION_VETO")));
					}
//					Incident no. 719548, 731441, 736475 : In case "isVetoEligible = false", DO NOT submit follow up.
//					else{
//						//send pega followup
//						ServiceResponseDataM serviceResponse = submitApplication.sendFollowUpToPega(applicationGroup,submitApplicationObject,new ArrayList<String>(),false);
//						String followupToPegaResult = serviceResponse.getStatusCode();
//						logger.debug("followupToPegaResult : "+followupToPegaResult);
//						if(!ServiceResponse.Status.SUCCESS.equals(followupToPegaResult)){
//							processResponse =  SubmitApplication.error(serviceResponse);
//						}
//					}	
				}else{
					processResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					processResponse.setErrorData(SubmitApplication.error(ErrorData.ErrorType.SERVICE_RESPONSE
							, ProcessResponse.SubmitApplicationErrorCode.SUBMIT_APPLICATION_ERROR_NOT_FOUND_APPLICATION_VETO
							, MessageErrorUtil.getText("SUBMIT_APPLICATION_ERROR_NOT_FOUND_APPLICATION_VETO")));
				}
			}else{
				processResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				processResponse.setErrorData(SubmitApplication.error(ErrorData.ErrorType.SERVICE_RESPONSE
						, ProcessResponse.SubmitApplicationErrorCode.SUBMIT_APPLICATION_ERROR_NOT_FOUND_APPLICATION_VETO
						, MessageErrorUtil.getText("SUBMIT_APPLICATION_ERROR_NOT_FOUND_APPLICATION_VETO")));
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(SubmitApplication.error(e));
		}
		return processResponse;
	}
	
	private boolean isVetoEligible(ApplicationGroupDataM applicationGroup){
		int VETO_DAYS =FormatUtil.getInt(CacheControl.getName(CACHE_WORKFLOW_PARAM,VETO_DAY));
		int LIFE_CYCLE_TIMES = FormatUtil.getInt(MAX_LIFE_CYCLE_CONFIG);
		int MAX_LIFE_CYCLE = applicationGroup.getMaxLifeCycle();
		boolean isVetoEligible = false;
//		ArrayList<String> finalAppDecions = applicationGroup.getFinalDecisionLifeCycle();
		ArrayList<ApplicationDataM> applicationList = applicationGroup.filterApplicationLifeCycle();
//		ArrayList<String> vetoEligibleFlags = applicationGroup.getVetoEligibleFlagLifeCycle();	
		logger.debug("VETO_DAYS >> "+VETO_DAYS);
		logger.debug("LIFE_CYCLE_TIMES >> "+LIFE_CYCLE_TIMES);
		logger.debug("MAX_LIFE_CYCLE >> "+MAX_LIFE_CYCLE);
		if(null!=applicationList){
			for(ApplicationDataM application :applicationList){	
//				#rawi comment check diff day on noticomplete to pega and close app to im
//				ApplicationDataM firstApplication = applicationGroup.findApplication(application.getApplicationRecordId(),1);
//				if(null==firstApplication){
//					firstApplication = new ApplicationDataM();
//				}
//				long DIFF_DAY = getDiffDay(firstApplication.getFinalAppDecisionDate(),ApplicationDate.getDate());
//				logger.debug("DIFF_DAY>>"+DIFF_DAY);
				if(DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){
					if(MConstant.FLAG_Y.equals(application.getIsVetoEligibleFlag())&& MAX_LIFE_CYCLE<LIFE_CYCLE_TIMES){
						isVetoEligible = true;
					}else{
						return false;
					}
				}
			}
		}
		Integer instantId = applicationGroup.getInstantId();
		logger.debug("instantId : "+instantId);
		if(SubmitApplication.errorInstant(instantId)){
			isVetoEligible = true;
		}
		return isVetoEligible;
	}
	
	private void reOpenExistingApplication(ApplicationGroupDataM applicationGroup,InquireDocSetResponse inquireDocSetResponse,String coverpageType,String userId) throws Exception{
		SubmitApplication submitApplication = new SubmitApplication();		
		applicationGroup.setCoverpageType(SubmitApplication.getCoverageType(coverpageType));	
//		#rawi comment not clear compare data
//		applicationGroup.setClearCompareData(true);
		int maxLifeCycle = applicationGroup.getMaxLifeCycle();
		int vetoLifeCycle = maxLifeCycle+1;
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		ArrayList<PersonalInfoDataM> usingPersonalInfo = applicationGroup.getUsingPersonalInfo();
		logger.debug("maxLifeCycle >> "+maxLifeCycle);
		logger.debug("vetoLifeCycle >> "+vetoLifeCycle);
		logger.debug("applicationGroupId >> "+applicationGroupId);
		ArrayList<String> products = applicationGroup.getProducts(maxLifeCycle);
		String applicationType = applicationGroup.getApplicationType();
		logger.debug("products : "+products);
		logger.debug("applicationType : "+applicationType);
		if(!Util.empty(usingPersonalInfo)){
			for(PersonalInfoDataM personalInfo : usingPersonalInfo){
				if(!Util.empty(personalInfo)){
					personalInfo.setBureauRequiredFlag(null);
				}
			}
		}
		if(!Util.empty(products)){
			for (String product : products) {
				logger.debug("Product >> "+product);
				if(PRODUCT_CRADIT_CARD.equals(product)&& getNormalApplicationType().contains(applicationType)){
					@SuppressWarnings("unchecked")
					ArrayList<ApplicationDataM> borrowers = (ArrayList<ApplicationDataM>)SerializeUtil.clone(
							(Serializable)filter(product,maxLifeCycle,BORROWER,applicationGroup));
					logger.debug("borrowers.size >> "+borrowers.size());
					if(!Util.empty(borrowers)){
						for (ApplicationDataM borrower : borrowers) {
							createItem(vetoLifeCycle,borrower,applicationGroup);
							String mainCardId = borrower.getApplicationRecordId();
							String cloneApplicationRecordId = borrower.getRefApplicationRecordId();
							@SuppressWarnings("unchecked")
							ArrayList<ApplicationDataM> supplemantarys = (ArrayList<ApplicationDataM>)SerializeUtil.clone(
									(Serializable)filter(cloneApplicationRecordId,SUPPLEMENTARY,applicationGroup));
							logger.debug("supplemantarys.size >> "+supplemantarys.size());
							if(!Util.empty(supplemantarys)){
								for (ApplicationDataM supplemantary : supplemantarys) {
									supplemantary.setMaincardRecordId(mainCardId);
									createItem(vetoLifeCycle,supplemantary,applicationGroup);
								}
							}
						}
					}
				}else{
					@SuppressWarnings("unchecked")
					ArrayList<ApplicationDataM> applications = (ArrayList<ApplicationDataM>)SerializeUtil.clone(
							(Serializable)filter(product,maxLifeCycle,applicationGroup));
					logger.debug("applications.size >> "+applications.size());
					if(!Util.empty(applications)){
						for (ApplicationDataM application: applications) {
							createItem(vetoLifeCycle,application,applicationGroup);
						}
					}
					
				}
			}		
			ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
			if(null != applications){
				for (ApplicationDataM application : applications) {
					LoanDataM loan = application.getLoan();
					String genPaymentMethod = loan.getGenPaymentMethod();
					String genSpecialAdditionalService = loan.getGenSpecialAdditionalService();					
					logger.debug("genPaymentMethod >> "+genPaymentMethod);				
					if(MConstant.FLAG_Y.equals(genPaymentMethod)){
						String lastPaymentMethodId = loan.getPaymentMethodId();	
						logger.debug("lastPaymentMethodId >> "+lastPaymentMethodId);
						if(!Util.empty(lastPaymentMethodId)){
							PaymentMethodDataM paymentMethod = (PaymentMethodDataM)SerializeUtil.clone(applicationGroup.getPaymentMethodById(lastPaymentMethodId));
							logger.debug("paymentMethod >> "+paymentMethod);
							if(null != paymentMethod){								
								String paymentMethodId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
								logger.debug("paymentMethodId >> "+paymentMethodId);
								paymentMethod.init(paymentMethodId);
								applicationGroup.addPaymentMethod(paymentMethod);
								loan.setPaymentMethodId(paymentMethodId);
							}else{
								loan.setPaymentMethodId(null);
							}
						}
					}else{
						loan.setPaymentMethodId(null);
					}

					logger.debug("genSpecialAdditionalService >> "+genSpecialAdditionalService);	
					if(MConstant.FLAG_Y.equals(genPaymentMethod)){
						ArrayList<String> specialAdditionalServiceMapIds = new ArrayList<String>();
						ArrayList<String> lastSpecialAdditionalServiceIds = loan.getSpecialAdditionalServiceIds();
						if(!Util.empty(lastSpecialAdditionalServiceIds)){
							for (String lastSpecialAdditionalServiceId : lastSpecialAdditionalServiceIds) {
								SpecialAdditionalServiceDataM specialAdditionalService = (SpecialAdditionalServiceDataM)SerializeUtil
										.clone(applicationGroup.getSpecialAdditionalService(lastSpecialAdditionalServiceId));
								logger.debug("specialAdditionalService >> "+specialAdditionalService);
								if(null != specialAdditionalService){
									String serviceId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
									logger.debug("serviceId >> "+serviceId);
									specialAdditionalService.init(serviceId);
									specialAdditionalServiceMapIds.add(serviceId);
									applicationGroup.addSpecialAdditionalService(specialAdditionalService);
								}
							}
						}
						loan.setSpecialAdditionalServiceIds(specialAdditionalServiceMapIds);
					}else{
						loan.setSpecialAdditionalServiceIds(new ArrayList<String>());
					}
				}
			}
			
			ArrayList<PersonalInfoDataM> perosnals= applicationGroup.getPersonalInfos();
			if(!Util.empty(perosnals)){
				for(PersonalInfoDataM uloPersonal : perosnals){
					uloPersonal.setIncomeSources(new ArrayList<IncomeSourceDataM>());
				}
			}
			
			
			submitApplication.mapImageIndex(inquireDocSetResponse,applicationGroup);
			LookupServiceCenter.getServiceCenterManager().saveApplication(applicationGroup,userId,true);
			ORIGDAOFactory.getApplicationGroupDAO(userId).clearInstantId(applicationGroupId);
		}
	}
		
	private void createItem(int vetoLifeCycle,ApplicationDataM application,ApplicationGroupDataM applicationGroup) throws Exception{
		String IsVetoEligibleFlag = application.getIsVetoEligibleFlag();
		logger.debug("IsVetoEligibleFlag >> "+IsVetoEligibleFlag);	
		if(MConstant.FLAG_Y.equals(IsVetoEligibleFlag)){
			String refId = application.getApplicationRecordId();
			String applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK);

			String lastApplicationNo = application.getApplicationNo();
			String currentApplicationNo = GenerateUnique.generateApplicationNo(lastApplicationNo,vetoLifeCycle);
			
			logger.debug("refId >> "+refId);
			logger.debug("applicationRecordId >> "+applicationRecordId);	
			logger.debug("lastApplicationNo >> "+lastApplicationNo);
			logger.debug("currentApplicationNo >> "+currentApplicationNo);
			
			application.clearValue();
			
			application.setApplicationRecordId(applicationRecordId);
			application.setApplicationNo(currentApplicationNo);
			application.setRefApplicationRecordId(refId);
			application.setLifeCycle(vetoLifeCycle);
			application.setApplicationDate(applicationGroup.getApplicationDate());
			
			if(null != application.getBundleHL()){
				application.getBundleHL().init(applicationRecordId);
			}		
			if(null != application.getBundleKL()){
				application.getBundleKL().init(applicationRecordId);
			}
			if(null != application.getBundleSME()){
				application.getBundleSME().init(applicationRecordId);
			}
			if(!Util.empty(application.getLoans())){
				for(LoanDataM loan:application.getLoans()){
					String loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK);
					loan.init(applicationRecordId,loanId);
//					loan.setPaymentMethodId(null);
//					loan.setSpecialAdditionalServiceIds(new ArrayList<String>());
					if(!Util.empty(loan.getPaymentMethodId())){
						loan.setGenPaymentMethod(MConstant.FLAG_Y);
					}
					if(!Util.empty(loan.getSpecialAdditionalServiceIds())){
						loan.setGenSpecialAdditionalService(MConstant.FLAG_Y);
					}
					if(null != loan.getCard()){
						String cardId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARD_PK);
						loan.getCard().init(loanId, cardId);
						CardDataM card = loan.getCard();
						if(!ServiceUtil.empty(card)){
							String cardTypeRequest = card.getRequestCardType();
							String cardLevel = ServiceCache.getName(ServiceCache.getConstant("CACHE_NAME_CARDTYPE"), "CODE", cardTypeRequest, "CARD_LEVEL");
							logger.debug("cardLevelRequest veto >> "+cardLevel);
							logger.debug("cardRequestType veto>> "+cardTypeRequest);
							logger.debug("cardType veto>> "+card.getCardType());
							logger.debug("cardLevel veto>> "+card.getCardType());
							card.setCardLevel(cardLevel);
							card.setCardType(cardTypeRequest);
						}
						if(null!=loan.getCard().getWisdom()){
							loan.getCard().getWisdom().init(cardId);
						}

					}
					if(null != loan.getCashTransfers()){
						for(CashTransferDataM cashTransfer :loan.getCashTransfers()){
							String cashTransferId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CASH_TRANSFER_PK);
							cashTransfer.init(loanId,cashTransferId);
						}						 
					}
					if(null != loan.getLoanTiers()){
						for (LoanTierDataM loanTier : loan.getLoanTiers()) {
							String tierId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_TIER_PK); 
							loanTier.init(loanId,tierId);
						}
					}
					if(null != loan.getLoanFees()){
						for (LoanFeeDataM loanFee : loan.getLoanFees()) {
							String loanFeeId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_FEE_PK); 
							loanFee.init(loanId,loanFeeId);
						}
					}		
//					#rawi comment change logic cloan SpecialAdditionalService,PaymentMethod
//					if(!Util.empty(loan.getSpecialAdditionalServiceIDs())){
//						ArrayList<String> additionalServiceIdList = new ArrayList<String>();
//						for(String serviceId : loan.getSpecialAdditionalServiceIDs()){
//							SpecialAdditionalServiceDataM  specialAdditionalService = applicationGroup.getSpecialAdditionalServiceById(serviceId);
//							if(!Util.empty(specialAdditionalService)){
//								String newServiceId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_ADDITIONAL_SERVICE_PK);
//								specialAdditionalService.init(newServiceId);
//								additionalServiceIdList.add(newServiceId);
//							}
//						}
//						loan.setSpecialAdditionalServiceIDs(additionalServiceIdList);
//					}
//					if(!Util.empty(loan.getPaymentMethodId())){
//						PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodByID(loan.getPaymentMethodId());
//						if(!Util.empty(paymentMethod)){
//							String paymentMethodId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
//							paymentMethod.init(paymentMethodId);
//							loan.setPaymentMethodId(paymentMethodId);
//						}
//					}
				}
			}
			
			if(null!=application.getVerificationResult()){
				String verResultId  = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_VERIFICATION_RESULT_PK);
				application.getVerificationResult().init(applicationRecordId, verResultId);
				if(!Util.empty(application.getVerificationResult().getPolicyRules())){
					for(PolicyRulesDataM policyRulesDataM : application.getVerificationResult().getPolicyRules()){
						String policyRuleId  = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_POLICY_RULES_PK);
						policyRulesDataM.init(verResultId, policyRuleId);
						if(!Util.empty(policyRulesDataM.getOrPolicyRules())){
							for(ORPolicyRulesDataM orPolicyRulesDataM :policyRulesDataM.getOrPolicyRules()){
								String orPolicyRuleId  = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_OR_POLICY_RULES_PK);
								orPolicyRulesDataM.init(policyRuleId, orPolicyRuleId);
								if(!Util.empty(orPolicyRulesDataM.getOrPolicyRulesDetails())){
									for(ORPolicyRulesDetailDataM orPolicyRulesDetailDataM: orPolicyRulesDataM.getOrPolicyRulesDetails()){
										String orPolicyRuleDetailId  = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_OR_POLICY_RULES_DTL_PK);
										orPolicyRulesDetailDataM.init(orPolicyRuleId, orPolicyRuleDetailId);
									}
								}
							}
						}
					}
				}
			}
			
			PersonalRelationDataM personalRelation = applicationGroup.getPersonalReation(refId,APPLICATION_LEVEL);
			String personalId = personalRelation.getPersonalId();
			String personalType = personalRelation.getPersonalType();
			logger.debug("personalId >> "+personalId);
			logger.debug("personalType >> "+personalType);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);
			ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
			if (null == personalRelations) {
				personalRelations = new ArrayList<PersonalRelationDataM>();
				personalInfo.setPersonalRelations(personalRelations);
			}
			personalInfo.addPersonalRelation(applicationRecordId,personalType,APPLICATION_LEVEL);
			if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
				application.setMaincardRecordId(applicationRecordId);
			}
			applicationGroup.addApplications(application);
		}			
	}
		
	private ArrayList<String> getNormalApplicationType(){
		ArrayList<String> applicationTypes = new ArrayList<String>();
			applicationTypes.add(APPLICATION_TYPE_NEW);
			applicationTypes.add(APPLICATION_TYPE_ADD);
			applicationTypes.add(APPLICATION_TYPE_UPGRADE);
		return applicationTypes;
	}
	private ArrayList<ApplicationDataM> filter(String product,int maxLifeCycle,ApplicationGroupDataM applicationGroup){
		return applicationGroup.filterApplicationDecision(product,maxLifeCycle,VETO_APPLICATION_DECISION);
	}
	private ArrayList<ApplicationDataM> filter(String product,int maxLifeCycle,String applicationCardType,ApplicationGroupDataM applicationGroup){
		return applicationGroup.filterApplicationDecision(product,maxLifeCycle,VETO_APPLICATION_DECISION,applicationCardType);
	}
	private ArrayList<ApplicationDataM> filter(String applicationRecordId,String applicationCardType,ApplicationGroupDataM applicationGroup){
		return applicationGroup.filterLinkApplicationDecision(applicationRecordId,VETO_APPLICATION_DECISION,applicationCardType);
	}	
	private long getDiffDay(Date firstDate , Date lastDate){
		if(null!=firstDate && null!=lastDate){
			long DIFF_TIME = firstDate.getTime() - lastDate.getTime();
			long DIFF_DAY = DIFF_TIME/(1000*60*60*24);
			return DIFF_DAY;
		}
		return 0;
	}
	private void clearPolicyRulePersonalLevel(ApplicationGroupDataM applicationGroup){
		if(!Util.empty(applicationGroup)){
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) && !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
				VerificationResultDataM verification = personalInfo.getVerificationResult();
				if(!Util.empty(verification)){
					verification.setPolicyRules(new ArrayList<PolicyRulesDataM>());
				}
			}
			
		}
	}
	private void updateVerificationResultCustomer(ApplicationGroupDataM applicationGroup){
		String VER_CUS_RESULT_COMPLETE_OVER_SLA = SystemConstant.getConstant("VER_CUS_RESULT_COMPLETE_OVER_SLA");
		String VALIDATION_STATUS_REQUIRED = SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED");
		String FIELD_ID_DATA_VALIDATION_STATUS = SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS");
		String cusResultDesc = CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS,VALIDATION_STATUS_REQUIRED,"DISPLAY_NAME");
		if(!Util.empty(applicationGroup)){
			ArrayList<PersonalInfoDataM> usingPersonalInfos =  applicationGroup.getUsingPersonalInfo();
			if(!Util.empty(usingPersonalInfos)){
				for(PersonalInfoDataM personalInfo : usingPersonalInfos){
					if(!Util.empty(personalInfo)){
						VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
						if(!Util.empty(verificationResult)){
							String verCustResultCode = verificationResult.getVerCusResultCode();
							logger.debug("verCustResultCode : "+verCustResultCode);
							if(!Util.empty(verCustResultCode) && VER_CUS_RESULT_COMPLETE_OVER_SLA.equals(verCustResultCode)){
								ArrayList<PhoneVerificationDataM> phoneVerifications = new ArrayList<PhoneVerificationDataM>(); 
								// verificationResult.setPhoneVerifications(phoneVerifications); // Clear PhoneVerification
								verificationResult.setVerCusComplete("");
								verificationResult.setVerCusResult(cusResultDesc);
								verificationResult.setVerCusResultCode(VALIDATION_STATUS_REQUIRED);
							}
						}
					}
				}
			}
		}
	}
}
