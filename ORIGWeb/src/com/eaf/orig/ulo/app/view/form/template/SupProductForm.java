package com.eaf.orig.ulo.app.view.form.template;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class SupProductForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(SupProductForm.class);
	String NORMAL_APPLICATION_FORM = SystemConstant.getConstant("NORMAL_APPLICATION_FORM");
	String SUP_CARD_SEPARATELY_FORM = SystemConstant.getConstant("SUP_CARD_SEPARATELY_FORM");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SUB_FORM_ID = "SUP_PRODUCT_FORM";
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("SupProductForm()..");
		PersonalApplicationInfoDataM personalAppInfo = (PersonalApplicationInfoDataM) appForm;
		ArrayList<String> products = personalAppInfo.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
		String roleId = FormControl.getFormRoleId(request);
		if (!Util.empty(products)) {
			for (String product : products) {
				logger.debug("product >> " + product);
				String formId = FormControl.getFormId("LIST_SUP_PRODUCT_FORM", product);
				ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(formId,roleId);
				if (!Util.empty(productSubForms)) {
					for (ORIGSubForm subForm : productSubForms) {
						if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,appForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
							subForm.setProperties(request, appForm);
						}
					}
				}
				formId = FormControl.getFormId("SUP_PRODUCT_FORM", product);
				productSubForms = FormControl.getSubForm(formId,roleId);
				if (!Util.empty(productSubForms)) {
					for (ORIGSubForm subForm : productSubForms) {
						if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,appForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
							subForm.setProperties(request, appForm);
						}
					}
				}
			}
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String roleId = FormControl.getFormRoleId(request);
		PersonalApplicationInfoDataM personalAppInfo = (PersonalApplicationInfoDataM) objectForm;
		ArrayList<String> products = personalAppInfo.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
		if (!Util.empty(products)) {
			for (String product : products) {
				logger.debug("product >> " + product);
				String formId = FormControl.getFormId("LIST_SUP_PRODUCT_FORM", product);
				ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(formId,roleId);
				if (!Util.empty(productSubForms)) {
					for (ORIGSubForm subForm : productSubForms) {
						if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
							HashMap validateFormErrors = subForm.validateForm(request, objectForm);
							if (null != validateFormErrors) {
								formError.addAll(validateFormErrors);
							}
						}
					}
				}
				formId = FormControl.getFormId("SUP_PRODUCT_FORM", product);
				productSubForms = FormControl.getSubForm(formId,roleId);
				if (!Util.empty(productSubForms)) {
					for (ORIGSubForm subForm : productSubForms) {
						if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
							HashMap validateFormErrors = subForm.validateForm(request, objectForm);
							if (null != validateFormErrors) {
								formError.addAll(validateFormErrors);
							}
						}
					}
				}
			}
		}else if(!APPLICATION_TYPE_ADD_SUP.equals(personalAppInfo.getApplicationType())){
			formError.error(MessageErrorUtil.getText(request, "ERROR_SUP_SAVE_ADD_CARD"));
		}
		return formError.getFormError();
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;		
		String roleId = FormControl.getFormRoleId(request);
		String personalId = getSubformData("PERSONAL_ID");
		ArrayList<String> products = applicationGroup.filterProductPersonal(personalId,PERSONAL_RELATION_APPLICATION_LEVEL);
		if (!Util.empty(products)) {
			for (String product : products) {
				logger.debug("product >> " + product);
				String formId = FormControl.getFormId("LIST_SUP_PRODUCT_FORM", product);
				ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(formId,roleId);
				if (!Util.empty(productSubForms)) {
					for (ORIGSubForm subForm : productSubForms) {
						if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
							subForm.setFormId(formId);
							subForm.setFormLevel(formLevel);
							subForm.setRoleId(roleId);
							subForm.addSubformData("PERSONAL_ID",personalId);
							if(Util.empty(applicationGroup.getReprocessFlag())){
								subForm.displayValueForm(request, applicationGroup);
							}
						}
					}
				}
			}
			
			String formId = FormControl.getFormId("SUP_PRODUCT_FORM_INFO");
			ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(formId,roleId);
			if (!Util.empty(productSubForms)) {
				for (ORIGSubForm subForm : productSubForms) {
					if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
						subForm.setFormId(formId);
						subForm.setFormLevel(formLevel);
						subForm.setRoleId(roleId);
						subForm.addSubformData("PERSONAL_ID",personalId);
						if(Util.empty(applicationGroup.getReprocessFlag())){
							subForm.displayValueForm(request, applicationGroup);
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

	@Override
	public String renderSubformFlag(HttpServletRequest request, Object objectForm) {
		String formId = FormControl.getOrigFormId(request);
		logger.debug("formId >> "+formId);
		logger.debug("NORMAL_APPLICATION_FORM >> "+NORMAL_APPLICATION_FORM);
		if(Util.empty(formId) || NORMAL_APPLICATION_FORM.equals(formId)){
			return MConstant.FLAG_Y;
		}
		return MConstant.FLAG_N;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement>  fieldElementList = new ArrayList<FieldElement>();
		String productElementField = "PRODUCT_ELEMENT";
		try {
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			
			ArrayList<String> products = new ArrayList<String>();
			if(objectForm instanceof PersonalInfoDataM){
				PersonalInfoDataM personalInfo  =(PersonalInfoDataM)objectForm;
				ArrayList<String> applicationIds = applicationGroup.filterPersonalApplicationIdLifeCycle(personalInfo.getPersonalId(),PERSONAL_RELATION_APPLICATION_LEVEL);
				products = applicationGroup.getProductApplicationRecordId(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"),applicationIds);
			}else if(objectForm instanceof ApplicationGroupDataM){
				applicationGroup = ((ApplicationGroupDataM)objectForm);	
				products =applicationGroup.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
			}
			if(!Util.empty(products)){
				 for(String product:products){
					 logger.debug("product>>"+product);
					 FieldElement fieldElement = new FieldElement();
					 fieldElement.setElementId(productElementField);
					 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
					 fieldElement.setElementGroupLevel(CompareDataM.UniqueLevel.APPLICATION);
					 fieldElementList.add(fieldElement);
					 if(PRODUCT_CRADIT_CARD.equals(product)){
						 fieldElement = new FieldElement();
						 fieldElement.setElementId("PERCENT_LIMIT_MAINCARD");
						 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
						 fieldElement.setElementGroupLevel(CompareDataM.UniqueLevel.APPLICATION);
						 fieldElementList.add(fieldElement);
						 
						 fieldElement = new FieldElement();
						 fieldElement.setElementId("PERCENT_LIMIT");
						 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
						 fieldElement.setElementGroupLevel(CompareDataM.UniqueLevel.APPLICATION);
						 fieldElementList.add(fieldElement);
					 }
				 }
			}			
//			ArrayList<ApplicationDataM> appInfoList = applicationGroup.getApplicationLifeCycle(new BigDecimal(applicationGroup.getMaxLifeCycle()));
			String roleId = FormControl.getFormRoleId(request);
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		for(PersonalInfoDataM personalInfo:personalInfos){
			ArrayList<ApplicationDataM> appInfoList = applicationGroup.getApplications(personalInfo.getPersonalId(), PERSONAL_TYPE_SUPPLEMENTARY, PERSONAL_RELATION_APPLICATION_LEVEL);
			if(!Util.empty(appInfoList)){
				for(ApplicationDataM appInfo : appInfoList){
					LoanDataM loanDataM =  appInfo.getLoan();
					if(!Util.empty(loanDataM)){
						CardDataM card = loanDataM.getCard();
						if(card != null){
							HashMap<String, Object> borrowerCardInfo = CardInfoUtil.getCardInfo(card.getCardType());
							String cardCodeborrower = (String) borrowerCardInfo.get("CARD_CODE");
							String productFormId = ListBoxControl.getName(FIELD_ID_CARD_TYPE, cardCodeborrower, "SYSTEM_ID2");
						 	logger.debug("productFormId>>"+productFormId);
						 	logger.debug("roleId>>"+roleId);
							ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(productFormId,roleId);
							logger.debug("subForm size sub:"+productSubForms.size());
							if (!Util.empty(productSubForms)) {
								for (ORIGSubForm subForm : productSubForms) {
									if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
										ArrayList<FieldElement>  subFormFieldElements =  subForm.elementForm(request, appInfo);
										if(!Util.empty(subFormFieldElements)){
											fieldElementList.addAll(subFormFieldElements);
										}
									}
								}
							}
						}
					}
					 
				}
			}
		}

		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElementList;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		logger.debug("SupProductForm.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<>();
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		try{
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
			ApplicationGroupDataM lastApplicationGroup = ((ApplicationGroupDataM)lastObjectForm);
			ArrayList<String> products = applicationGroup.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
			String roleId = FormControl.getFormRoleId(request);
			if(!Util.empty(products)){
				for(String product : products){
					String formId = FormControl.getFormId("SUP_PRODUCT_FORM",product);
					ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(formId,roleId,applicationGroup,request);
					for(ORIGSubForm subForm : productSubForms){
						if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
							ArrayList<AuditDataM> auditForms = subForm.auditForm(request, applicationGroup, lastApplicationGroup);
							if(!Util.empty(auditForms)){
								audits.addAll(auditForms);
							}
						}
					}
				}
				auditFormUtil.auditForm(SUB_FORM_ID,"CARD_TYPE",applicationGroup,lastApplicationGroup,request);
				audits.addAll(auditFormUtil.getAuditForm());
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return audits;
	}
}
