package com.eaf.orig.ulo.app.view.util.dih;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.hash.Hasher;
import com.eaf.core.ulo.security.hash.HashingFactory;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationIncreaseDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;
import com.google.gson.Gson;

public class IncreaseCardRequestInformation implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(IncreaseCardRequestInformation.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CACHE_ORGANIZATION = SystemConstant.getConstant("CACHE_ORGANIZATION");
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String ERROR_CIS = SystemConstant.getConstant("ERROR_CIS");
	String ERROR_MAXIMUM_CARD= SystemConstant.getConstant("ERROR_MAXIMUM_CARD");
	String APPLICATION_TYPE_INCREASE= SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	int MAX_CARD_REQUEST_IA = Integer.parseInt(SystemConfig.getGeneralParam("MAX_CARD_REQUEST_IA"));
	int MAX_CARD_REQUEST_INC = Integer.parseInt(SystemConfig.getGeneralParam("CARD_REQUEST_LIMIT"));
	String DEFAULT_REC_DECISION = SystemConstant.getConstant("DEFAULT_REC_DECISION");	
	String ERROR_SUP_CARD_REQUEST_INCREASE = SystemConstant.getConstant("ERROR_SUP_CARD_REQUEST");
	String ROLE_IA = SystemConstant.getConstant("ROLE_IA");
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		logger.debug("CustomerInformation.....");
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.INCREASE_CARD_REQ_INFO);
		String data = null;
		JSONUtil json = new JSONUtil();	
		try{
			String CARD_NUMBER = FormatUtil.removeDash(request.getParameter("CARD_NO"));	
			String CIS_NUMBER = request.getParameter("CIS_NUMBER");		
			String ENCRYPTION_CARD_NUMBER = "";
			String HASHED_CARD_NUMBER = "";
			String ENCRYPTION_KM_CARDNO = "";
			try {
				 Hasher hash = HashingFactory.getSHA256Hasher();
				 HASHED_CARD_NUMBER = hash.getHashCode(CARD_NUMBER);			 
				 Encryptor enc = EncryptorFactory.getDIHEncryptor();
				 ENCRYPTION_CARD_NUMBER = enc.encrypt(CARD_NUMBER);
				 Encryptor kmEnc = EncryptorFactory.getKmAlertEncryptor();
				 ENCRYPTION_KM_CARDNO = kmEnc.encrypt(CARD_NUMBER);
				 logger.debug("MAIN_CARD_NUMBER >> "+CARD_NUMBER);
				 logger.debug("HASHED_CARD_NUMBER >> "+HASHED_CARD_NUMBER);
				 logger.debug("ENCRYPTION_CARD_NUMBER >> "+ENCRYPTION_CARD_NUMBER);
				 logger.debug("ENCRYPTION_KM_CARDNO >> "+ENCRYPTION_KM_CARDNO);
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
			
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
			String applicationGroupId = applicationGroup.getApplicationGroupId();
			logger.debug("applicationGroupId >> "+applicationGroupId);
			
			ArrayList<ApplicationDataM> applicationsAll = applicationGroup.getApplications();
			if(Util.empty(applicationsAll)){
				applicationsAll = new ArrayList<ApplicationDataM>();
			}
			PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE_APPLICANT);	
			
			if(APPLICATION_TYPE_INCREASE.equals(applicationGroup.getApplicationType())) {
				MAX_CARD_REQUEST_IA = MAX_CARD_REQUEST_INC;
			}
			boolean equalCisCustomer = false;
			boolean supplementaryFlag = false;
			String MAIN_CARD_NUMBER = "";		
			CardLinkDataM cardLink = null;
			DIHProxy dihProxy = new DIHProxy();
			int maxLifeCycle = applicationGroup.getMaxLifeCycle();
			int sizeApplications = applicationGroup.filterApplicationLifeCycle().size();
			if(sizeApplications < MAX_CARD_REQUEST_IA){
				if(!Util.empty(CARD_NUMBER)){
					
					DIHQueryResult<CardLinkDataM> dihCardLink = dihProxy.getCardLinkInfoENCPT(ENCRYPTION_CARD_NUMBER);
					if(!ResponseData.SUCCESS.equals(dihCardLink.getStatusCode())){
						return responseData.error(dihCardLink);
					}
					cardLink = dihCardLink.getResult();
				}
//				logger.debug("cardLink >> "+new Gson().toJson(cardLink));		
				if(!Util.empty(cardLink)){
					if(BORROWER.equals(cardLink.getApplicationType())){
						if(CIS_NUMBER.equals(cardLink.getCisNo())){
							equalCisCustomer = true;
						}				
					}else if(SUPPLEMENTARY.equals(cardLink.getApplicationType())){
	//					CardLinkDataM checkcardLink = cardLink.getMainCis();
						String roleId = FormControl.getFormRoleId(request);
						if(ROLE_IA.equals(roleId)){
						String CISMain = cardLink.getMainCis();
						if(CIS_NUMBER.equals(CISMain)){
							equalCisCustomer = true;
							supplementaryFlag = true;
							MAIN_CARD_NUMBER = cardLink.getMainCardNo();
							String applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK);
							ApplicationIncreaseDataM applicationIncrease = new ApplicationIncreaseDataM(applicationRecordId);
							applicationIncrease.setApplicationGroupId(applicationGroupId);
							applicationIncrease.setCardNoMark(cardLink.getCardNoMark());
							applicationIncrease.setCardNoEncrypted(ENCRYPTION_CARD_NUMBER);
							applicationIncrease.setThName(cardLink.getMainCardHolderName());
							applicationIncrease.setCardLinkCustNo(cardLink.getCardLinkCustNo());
							applicationIncrease.setMainCardNo(cardLink.getMainCardNo());
							applicationGroup.setApplicationIncrease(applicationIncrease);
						}
						}else{
							equalCisCustomer = true;
							supplementaryFlag = true;
							data = ERROR_SUP_CARD_REQUEST_INCREASE;
						}
					}
					logger.debug("equalCisCustomer >> "+equalCisCustomer);	
					if(equalCisCustomer && !supplementaryFlag){
						String applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK);			
						ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
						if (null == personalRelations) {
							personalRelations = new ArrayList<PersonalRelationDataM>();
							personalInfo.setPersonalRelations(personalRelations);
						}
						personalInfo.addPersonalRelation(applicationRecordId, PERSONAL_TYPE_APPLICANT, APPLICATION_LEVEL);
						
						String loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK);
						String cardId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARD_PK);				
						logger.debug("applicationRecordId >> "+applicationRecordId);
						logger.debug("loanId >> "+loanId);
						logger.debug("cardId >> "+cardId);	
						String cardOrgNo = cardLink.getCardOrgNo();
						String orgId = CacheControl.getName(CACHE_ORGANIZATION, "ORG_NO", cardOrgNo, "ORG_ID");
						String businessClassId = CacheControl.getName(CACHE_BUSINESS_CLASS, "ORG_ID", orgId, "BUS_CLASS_ID");
						logger.debug("cardOrgNo >> "+cardOrgNo);
						logger.debug("orgId >> "+orgId);	
						logger.debug("businessClassId >> "+businessClassId);	
							
						ApplicationDataM application = new ApplicationDataM(applicationRecordId);
						application.setBusinessClassId(businessClassId);
						application.setLifeCycle(maxLifeCycle);
						application.setApplicationNo(GenerateUnique.generateApplicationNo(applicationGroup.getApplicationGroupNo()
								, applicationGroup.itemLifeCycle()+1,applicationGroup.getMaxLifeCycle()));
						application.setApplicationDate(applicationGroup.getApplicationDate());
						LoanDataM loan = new LoanDataM(applicationRecordId,loanId);
						CardDataM card = new CardDataM(loanId,cardId);
							card.setMainCardNo(MAIN_CARD_NUMBER);
							card.setCardNo(ENCRYPTION_CARD_NUMBER);
							card.setCardNoEncrypted(ENCRYPTION_KM_CARDNO);
							card.setMainCardHolderName(cardLink.getMainCardHolderName());
							card.setApplicationType(cardLink.getApplicationType());
							card.setHashingCardNo(HASHED_CARD_NUMBER);
							card.setCardType(ApplicationUtil.getLastCardType(applicationGroupId, HASHED_CARD_NUMBER));
							card.setCardNoMark(cardLink.getCardNoMark());
						personalInfo.setMobileNo(cardLink.getPhoneNo());
						loan.setCard(card);
						application.addLoan(loan);
						
						if(Util.empty(application.getRecommendDecision())){
							application.setRecommendDecision(DEFAULT_REC_DECISION);
						}
						
						HashMap<String, CompareDataM> comparisonFields = getComparisonField(request);
						if(null == comparisonFields){
							comparisonFields = new HashMap<String, CompareDataM>();
						}
						String refId = CompareSensitiveFieldUtil.getApplicationRefId(application); 	
						String refLevel = CompareDataM.RefLevel.PERSONAL; 
						logger.debug("refId >> "+refId); 
						logger.debug("refLevel >> "+refLevel); 
						createComparisonField("PHONE_NUMBER_INCREASE",refId,refLevel,cardLink.getPhoneNo(),comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo);
						String roleId = FormControl.getFormRoleId(request);
						UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
						String userId = userM.getUserName();
						int index = 1;
						for (String elementId : comparisonFields.keySet()) {
							CompareDataM comparisonField = comparisonFields.get(elementId);
							comparisonField.setRole(roleId);
							comparisonField.setCreateBy(userId);
							comparisonField.setUpdateBy(userId);
							comparisonField.setSeq(index++);
						}
						setComparisonField(comparisonFields,request);
						
						ApplicationUtil.setPolicyRuleToApplication(applicationGroup,application,FormControl.getFormRoleId(request),personalInfo);
						applicationsAll.add(application);		
						applicationGroup.setApplications(applicationsAll);
//						logger.debug("Applicatin data>>"+ new Gson().toJson(applicationsAll));
						json.put("PHONE_NUMBER",cardLink.getPhoneNo());
					}else if(!equalCisCustomer){
						data = ERROR_CIS;
					}
				}
			}else{
				data = ERROR_MAXIMUM_CARD;
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
		if(Util.empty(data)){
			data = json.getJSON();
		}
		return responseData.success(data);
	}
	
	private HashMap<String, CompareDataM> getComparisonField(HttpServletRequest request){
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		return applicationGroup.getComparisonField(CompareDataM.SoruceOfData.CARD_LINK);
	}
	
	private void  setComparisonField(HashMap<String, CompareDataM> comparisonFields,HttpServletRequest request){
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<ComparisonGroupDataM> comparisonGroups = applicationGroup.getComparisonGroups();
		if(null == comparisonGroups){
			comparisonGroups = new ArrayList<ComparisonGroupDataM>();
			applicationGroup.setComparisonGroups(comparisonGroups);
		}
		ComparisonGroupDataM comprisionGroup = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.CARD_LINK);
		if(null == comprisionGroup){
			comprisionGroup = new ComparisonGroupDataM();
			comprisionGroup.setSrcOfData(CompareDataM.SoruceOfData.CARD_LINK);
			comparisonGroups.add(comprisionGroup);
		}
		comprisionGroup.setComparisonFields(comparisonFields);
	}
	
	public void createComparisonField(String fieldNameType,String refId,String refLevel,Object value,HashMap<String, CompareDataM> comparisonFields,String uniqueId, String uniqueLevel,PersonalInfoDataM personalInfo){
		String fieldName = fieldNameType+"_"+refId; 
		logger.debug("fieldNameType >> "+fieldNameType); 
		logger.debug("refId >> "+refId); 
		logger.debug("refLevel >> "+refLevel); 
		logger.debug("fieldName >> "+fieldName); 
		CompareDataM compareData = comparisonFields.get(fieldName); 
		String setValue = null; 
		try{
			if(value instanceof String){
				setValue = (String)value; 
			}else if(value instanceof Date){
				setValue = FormatUtil.display((Date)value,FormatUtil.EN,FormatUtil.Format.ddMMyyyy); 
			}else if(value instanceof BigDecimal){
				setValue = FormatUtil.display((BigDecimal)value); 
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e); 
		}
		if(null == compareData){
			compareData = new CompareDataM(); 
			compareData.setFieldNameType(fieldNameType); 
			compareData.setValue(setValue); 
			compareData.setRefId(refId); 
			compareData.setRefLevel(refLevel); 
			compareData.setSrcOfData(CompareDataM.SoruceOfData.CARD_LINK); 
			compareData.setUniqueId(uniqueId); 
			compareData.setUniqueLevel(uniqueLevel); 
			compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData)); 
			compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfo,"")); 
//			compareData.setFieldName(fieldName); 
			comparisonFields.put(fieldName,compareData); 
		}else{
			compareData.setValue(setValue); 
		}
	}
		
}
