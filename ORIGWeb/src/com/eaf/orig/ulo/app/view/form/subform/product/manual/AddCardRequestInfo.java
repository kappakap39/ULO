package com.eaf.orig.ulo.app.view.form.subform.product.manual;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

public class AddCardRequestInfo implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AddCardRequestInfo.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String DEFAULT_REC_DECISION = SystemConstant.getConstant("DEFAULT_REC_DECISION");	
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		logger.debug("AddCardRequestInfo...");
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_SUPPLEMENTARY_CARD);
		try{
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();		
			ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
			if(null == applications){
				applications = new ArrayList<ApplicationDataM>();
				applicationGroup.setApplications(applications);
			}
			String PERSONAL_ID = request.getParameter("PERSONAL_SUPPLEMENTARY");
			String PRODUCT_TYPE = PRODUCT_CRADIT_CARD;
			String CARD_TYPE = request.getParameter("CARD_TYPE");
			String CARD_LEVEL = request.getParameter("CARD_LEVEL");		
			String APPLICATION_CARD_TYPE = SUPPLEMENTARY;
			logger.debug("PERSONAL_ID >> "+PERSONAL_ID);
			logger.debug("PRODUCT_TYPE >> "+PRODUCT_TYPE);
			logger.debug("CARD_TYPE >> "+CARD_TYPE);
			logger.debug("CARD_LEVEL >> "+CARD_LEVEL);
			HashMap<String, Object> cardInfo = CardInfoUtil.getCardInfo(CARD_TYPE, CARD_LEVEL);
			if(!Util.empty(cardInfo)){
				String businessClassId = SQLQueryEngine.display(cardInfo, "BUSINESS_CLASS_ID");
				String applicationRecordId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_PK);
				String loanId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PK);
				String cardId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_CARD_PK);
				String cardTypeId = SQLQueryEngine.display(cardInfo, "CARD_TYPE_ID");
				String cardLevel = SQLQueryEngine.display(cardInfo, "CARD_LEVEL");
				logger.debug("businessClassId >> " + businessClassId);
				logger.debug("applicationRecordId >> " + applicationRecordId);
				logger.debug("loanId >> " + loanId);
				logger.debug("cardId >> " + cardId);
				logger.debug("cardTypeId >> " + cardTypeId);
				logger.debug("cardLevel >> " + cardLevel);
				logger.debug("APPLICATION_CARD_TYPE >> " + APPLICATION_CARD_TYPE);
				PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(PERSONAL_ID);
				PersonalInfoDataM personalInfoI = applicationGroup.getPersonalInfo(PERSONAL_TYPE_INFO);
				if(Util.empty(personalInfoI)){
					personalInfoI = new PersonalInfoDataM();
				}
				logger.debug("personalInfo >> " + personalInfo);
				if (null != personalInfo) {
					ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
					if (null == personalRelations) {
						personalRelations = new ArrayList<PersonalRelationDataM>();
						personalInfo.setPersonalRelations(personalRelations);
					}
					personalInfo.addPersonalRelation(applicationRecordId,PERSONAL_TYPE_SUPPLEMENTARY, APPLICATION_LEVEL);
				}
				ApplicationDataM application = new ApplicationDataM(applicationRecordId);
				application.setBusinessClassId(businessClassId);
				LoanDataM loan = new LoanDataM(applicationRecordId, loanId);
				CardDataM card = new CardDataM(loanId, cardId);
				card.setCardType(cardTypeId);
				card.setRequestCardType(cardTypeId);
				card.setApplicationType(SUPPLEMENTARY);
				card.setCardLevel(cardLevel);
				card.setMainCardHolderName(personalInfoI.getMainCardHolderName());
				card.setMainCardNo(personalInfoI.getMainCardNo());
				
				PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(PERSONAL_ID);
				if(null != paymentMethod){
					loan.setPaymentMethodId(paymentMethod.getPaymentMethodId());
				}			
				ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(PERSONAL_ID);
				if(null != specialAdditionalServices){
					for (SpecialAdditionalServiceDataM specialAdditional : specialAdditionalServices) {
						if(null != specialAdditional && !Util.empty(specialAdditional.getServiceId())){
							loan.addAdditionalServiceId(specialAdditional.getServiceId());
						}
					}
				}
				
				loan.setCard(card);
				application.addLoan(loan);
	//			application.setLifeCycle(1);
   //	 		application.setApplicationType(applicationGroup.getApplicationType());
	//			application.setApplicationNo(GenerateUnique.generateApplicationNo(applicationGroup.getApplicationGroupNo()
	//					, applicationGroup.itemLifeCycle()+1,applicationGroup.getMaxLifeCycle()));
				application.setLifeCycle(Math.max(1, applicationGroup.getMaxLifeCycle()));
				application.setApplicationNo(GenerateUnique.generateApplicationNo(applicationGroup.getApplicationGroupNo()
						, applicationGroup.itemLifeCycle()+1,applicationGroup.getMaxLifeCycle()));
				ApplicationUtil.setPolicyRuleToApplication(applicationGroup,application,FormControl.getFormRoleId(request),personalInfo);
				if(Util.empty(application.getRecommendDecision())){
					application.setRecommendDecision(DEFAULT_REC_DECISION);
				}
				application.setApplicationType(APPLICATION_TYPE_ADD_SUP);
				application.setApplicationDate(applicationGroup.getApplicationDate());
				applications.add(application);
			}
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
