package com.eaf.orig.ulo.app.view.form.subform.product.kpl;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class LoanFinanceSubForm extends ORIGSubForm{
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");
	String subformId = "KPL_INSTALLMENT_SUBFORM";
	String SAVING_PLUS_ACCOUNT_TYPE = SystemConstant.getConstant("SAVING_PLUS_ACCOUNT_TYPE");
	String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
	String ROLE_DV = SystemConstant.getConstant("ROLE_DV");
	private static transient Logger logger = Logger.getLogger(LoanFinanceSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm){
		DIHProxy dihService = new DIHProxy();
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);

		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();
		logger.debug("personalId >> "+personalId);		
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_PERSONAL_LOAN); 
		if(Util.empty(paymentMethod)){
			paymentMethod = new PaymentMethodDataM();
			String paymentMethodId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
			paymentMethod.setPaymentMethodId(paymentMethodId);
			paymentMethod.setPersonalId(personalId);
			paymentMethod.setProductType(PRODUCT_K_PERSONAL_LOAN);
			paymentMethod.setPaymentMethod(PAYMENT_METHOD_DEPOSIT_ACCOUNT);
			applicationGroup.addPaymentMethod(paymentMethod);
		}
		
		if(KPLUtil.isKPL_MARKET(applicationGroup))
		{
			//MARKET TEMPLATE - [CANCELLED]
		}
		else
		{
			//Non-Market KPL
			String SAVING_PLUS = request.getParameter("SAVING_PLUS");
			String ACCOUNT_NO = FormatUtil.removeDash(request.getParameter("ACCOUNT_NO_"+PRODUCT_K_PERSONAL_LOAN));
			String COMPLETE_DATA = request.getParameter("COMPLETE_DATA_"+PRODUCT_K_PERSONAL_LOAN);
			
			logger.debug("SAVING_PLUS : "+SAVING_PLUS);
			logger.debug("ACCOUNT_NO : "+ACCOUNT_NO);
			logger.debug("COMPLETE_DATA : "+COMPLETE_DATA);	
			
			DIHQueryResult<String> queryResult = dihService.getAccountName(ACCOUNT_NO);
			String ACCOUNT_NAME = queryResult.getResult();
			if(Util.empty(COMPLETE_DATA)) {
				COMPLETE_DATA = INFO_IS_CORRECT;
			}
				
			paymentMethod.setAccountNo(ACCOUNT_NO);
			paymentMethod.setAccountName(ACCOUNT_NAME);
			paymentMethod.setCompleteData(COMPLETE_DATA);
			
			ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_PERSONAL_LOAN);
			if(!Util.empty(applications))
			{
				for (ApplicationDataM application : applications) 
				{
					application.setSavingPlusFlag(SAVING_PLUS);
				}
			}
		}
		
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		if(!Util.empty(applications)){
			for (ApplicationDataM application : applications) 
			{
				ArrayList<LoanDataM> loans = application.getLoans();
				if(!Util.empty(loans)){
					for(LoanDataM loan : loans){
						loan.setPaymentMethodId(paymentMethod.getPaymentMethodId());
					}
				}
			}
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm){
		FormErrorUtil formError = new FormErrorUtil();
		logger.debug("subformId >> "+subformId);
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		String roleId = ORIGForm.getRoleId();
		String SAVING_PLUS = request.getParameter("SAVING_PLUS");
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		boolean haveKbankSavingDoc = KPLUtil.haveKbankSavingDoc(applicationGroup);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();
		logger.debug("personalId >> "+personalId);		
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_PERSONAL_LOAN); 
		if(null != paymentMethod && (Util.empty(SAVING_PLUS) || (!Util.empty(SAVING_PLUS) && ROLE_DV.equals(roleId) && haveKbankSavingDoc))){
			formError.mandatory(subformId,"ACCOUNT_NO","ACCOUNT_NO_"+PRODUCT_K_PERSONAL_LOAN,paymentMethod.getAccountNo(),request);
		}		
		return formError.getFormError();
	}
	
	@Override
	public boolean validateSubForm(HttpServletRequest request){
		return false;
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_PERSONAL_LOAN);
		if(Util.empty(paymentMethod)){
			paymentMethod = new PaymentMethodDataM();
		}
		paymentMethod.setAccountNo(formValue.getValue("ACCOUNT_NO_"+PRODUCT_K_PERSONAL_LOAN,"ACCOUNT_NO_"+PRODUCT_K_PERSONAL_LOAN,paymentMethod.getAccountNo()));
		paymentMethod.setAccountName(formValue.getValue("ACCOUNT_NAME_"+PRODUCT_K_PERSONAL_LOAN,"ACCOUNT_NO_"+PRODUCT_K_PERSONAL_LOAN,paymentMethod.getAccountName()));
		
		//KPL Additional
		ApplicationDataM application = KPLUtil.getKPLApplication(applicationGroup);
		application.setSavingPlusFlag(formValue.getValue("SAVING_PLUS","SAVING_PLUS",application.getSavingPlusFlag()));
	};
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastApplicationGroup = ((ApplicationGroupDataM)lastObjectForm);
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();		
		logger.debug("subformId >> "+subformId);
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_PERSONAL_LOAN);
		
		PersonalInfoDataM lastPersonalInfo = lastApplicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String lastPersonalId = lastPersonalInfo.getPersonalId();		
		PaymentMethodDataM lastPaymentMethod = lastApplicationGroup.getPaymentMethodLifeCycleByPersonalId(lastPersonalId,PRODUCT_K_PERSONAL_LOAN);
		
		auditFormUtil.auditForm(subformId,"ACCOUNT_NO",paymentMethod,lastPaymentMethod,request);
		
		return auditFormUtil.getAuditForm();
	}	
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		try 
		{
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
			String[] filedNames = {"ACCOUNT_NO_KPL"};
			if(KPLUtil.isKPL_MARKET(applicationGroup))
			{
				filedNames = new String[] {"ACCOUNT_NO_KPL"};
			}
		
			ArrayList<ApplicationDataM> applicationItems = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_PERSONAL_LOAN);
			if(!Util.empty(applicationItems)){
				for(ApplicationDataM applicationDataM : applicationItems){
					PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(applicationDataM.getApplicationRecordId());
					 for(String elementId:filedNames){
						 FieldElement fieldElement = new FieldElement();
						 fieldElement.setElementId(elementId);
						 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
						 fieldElement.setElementGroupId(personalInfoM.getPersonalId());
						 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfoM));
						 fieldElements.add(fieldElement);
					 }
				}
			}

		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm)
	{
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		//This subform will only render if application template is KPL
		logger.debug("renderSubformFlag - LoanFinanceSubForm");
		if(KPLUtil.isKPL(applicationGroup))
		{
			return MConstant.FLAG_Y;
		}		
		return MConstant.FLAG_N;
	}
	
}
