package com.eaf.orig.ulo.app.view.form.template;

import java.math.BigDecimal;
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
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class ProductForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(ProductForm.class);
	private final String APPLICATION_TYPE_NEW = SystemConstant.getConstant("APPLICATION_TYPE_NEW");
//	private final String APPLICATION_TYPE_ADD = SystemConstant.getConstant("APPLICATION_TYPE_ADD");
//	private final String APPLICATION_TYPE_UPGRADE = SystemConstant.getConstant("APPLICATION_TYPE_UPGRADE");
	private final String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	private final String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	private final String SUB_FORM_ID = "PRODUCT_FORM";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		try {
			logger.debug("Call ProductForm ....");
			String roleId = FormControl.getFormRoleId(request);
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) appForm;
			ArrayList<String> products = applicationGroup.getProducts();
			if (!Util.empty(products)) {
				for (String product : products) {
					logger.debug("product >> " + product);
					String formId = FormControl.getFormId("PRODUCT_FORM", product);
					ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(formId,roleId);
					if (!Util.empty(productSubForms)) {
						for (ORIGSubForm subForm : productSubForms) {
							if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,appForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
								subForm.setProperties(request, appForm);
							}
						}
					}
					formId = FormControl.getFormId("LIST_PRODUCT_FORM", product);
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
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}	
	}
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) appForm;
		ArrayList<String> products = applicationGroup.getProducts();
		if (!Util.empty(products)) {
			for (String product : products) {
				logger.debug("product >> " + product);
				String formId = FormControl.getFormId("PRODUCT_FORM", product);
				ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(formId, roleId);
				if (!Util.empty(productSubForms)) {
					for (ORIGSubForm subForm : productSubForms) {
						if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,appForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
							HashMap validateFormErrors = subForm.validateForm(request, appForm);
							if (null != validateFormErrors) {
								formError.addAll(validateFormErrors);
							}
						}
					}
				}
				formId = FormControl.getFormId("LIST_PRODUCT_FORM", product);
				productSubForms = FormControl.getSubForm(formId, roleId);
				if (!Util.empty(productSubForms)) {
					for (ORIGSubForm subForm : productSubForms) {
						if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,appForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
							HashMap validateFormErrors = subForm.validateForm(request, appForm);
							if (null != validateFormErrors) {
								formError.addAll(validateFormErrors);
							}
						}
					}
				}
				
			}
		}
		
		// Validation
		String ERROR_NO_APPLICATIONS = MessageErrorUtil.getText(request, "ERROR_NO_APPLICATIONS");
		// Get Application Group
		if (null != applicationGroup) {
			// Get list of applications
			ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
			if (null != applications) {
				if(APPLICATION_TYPE_NEW.equals(applicationGroup.getApplicationType())) {
					if (applications.size() == 0) {
						logger.debug("No applications in APPLICATION_TYPE_NEW");
						formError.mandatory(SUB_FORM_ID, "APPLICATION_ITEM","PRODUCTS_CARD_TYPE","","", request);
						//formError.error(ERROR_NO_APPLICATIONS);
					}
				} else if(APPLICATION_TYPE_ADD_SUP.equals(applicationGroup.getApplicationType())) {
					ArrayList<ApplicationDataM> addSupApplications = applicationGroup.filterApplicationCardTypeLifeCycle(APPLICATION_TYPE_ADD_SUP);
					if (addSupApplications.size() == 0) {
						logger.debug("No applications in APPLICATION_TYPE_ADD_SUP");
						formError.mandatory(SUB_FORM_ID, "APPLICATION_ITEM","PRODUCTS_CARD_TYPE","","", request);
						//formError.error(ERROR_NO_APPLICATIONS);
					}
				} else if (APPLICATION_TYPE_INCREASE.equals(applicationGroup.getApplicationType())) {
					ArrayList<ApplicationDataM> addSupApplications = applicationGroup.filterApplicationCardTypeLifeCycle(APPLICATION_TYPE_INCREASE);
					if (addSupApplications.size() == 0) {
						logger.debug("No applications in APPLICATION_TYPE_INCREASE");
						formError.mandatory(SUB_FORM_ID, "APPLICATION_ITEM","PRODUCTS_CARD_TYPE","","", request);
						//formError.error(ERROR_NO_APPLICATIONS);
					}
				} else {
					if(applications.size() == 0) {
						logger.debug("No applications in APPLICATION_TYPE");
						formError.mandatory(SUB_FORM_ID, "APPLICATION_ITEM","PRODUCTS_CARD_TYPE","","", request);
						//formError.error(ERROR_NO_APPLICATIONS);
					}
				}
			}
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		String subformId = "PRODUCT_FORM";
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		HashMap data = new HashMap<>();
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		formValue.clearValue("LIST_PRODUCT",applicationGroup);		
		String roleId = FormControl.getFormRoleId(request);
		ArrayList<String> products = applicationGroup.getProducts();
		if (!Util.empty(products)) {
			for (String product : products) {
				logger.debug("product >> " + product);
				String formId = FormControl.getFormId("PRODUCT_FORM", product);
				ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(formId,roleId);
				if (!Util.empty(productSubForms)) {
					for (ORIGSubForm subForm : productSubForms) {
						if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
							subForm.setFormId(formId);
							subForm.setFormLevel(formLevel);
							subForm.setRoleId(roleId);
							if(Util.empty(applicationGroup.getReprocessFlag())){
								subForm.displayValueForm(request, applicationGroup);
							}
						}
					}
				}
			}
		}
		
		ArrayList<ApplicationDataM> appInfoList = applicationGroup.getApplicationLifeCycle(new BigDecimal(applicationGroup.getMaxLifeCycle()));
		if(!Util.empty(appInfoList)){
			for(ApplicationDataM appInfo : appInfoList){
				LoanDataM loanDataM =  appInfo.getLoan();
				if(!Util.empty(loanDataM)){
					CardDataM card = loanDataM.getCard();
					 //Add Handle NullPointer for KPL null card
					ArrayList<ORIGSubForm> productSubForms = null;
					String productFormId = null;
					if(card != null)
					{
						HashMap<String, Object> borrowerCardInfo = CardInfoUtil.getCardInfo(card.getCardType());
						String cardCodeborrower = (String) borrowerCardInfo.get("CARD_CODE");
						productFormId = ListBoxControl.getName(FIELD_ID_CARD_TYPE, cardCodeborrower, "SYSTEM_ID1");
						logger.debug("productFormId>>"+productFormId);
						productSubForms = FormControl.getSubForm(productFormId,roleId);
						logger.debug("subForm.size():"+productSubForms.size());
					}
					if (!Util.empty(productSubForms)) {
						for (ORIGSubForm subForm : productSubForms) {
							if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
								data.put("ApplicationRecordId", appInfo.getApplicationRecordId());
								subForm.setSubformData(data);
								subForm.setFormId(productFormId);
								subForm.setRoleId(roleId);
								subForm.setFormLevel(formLevel);
								if(Util.empty(applicationGroup.getReprocessFlag())){
									subForm.displayValueForm(request, applicationGroup);
								}
							}
						}
					}
				}
				 
			}
		}
	
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		ArrayList<AuditDataM> audits = new ArrayList<>();
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		try{
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
			ApplicationGroupDataM lastApplicationGroup = ((ApplicationGroupDataM)lastObjectForm);
			ArrayList<String> products = applicationGroup.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
			if(!Util.empty(products)){
				for(String product : products){
					String formId = FormControl.getFormId("PRODUCT_FORM",product);
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
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		logger.debug("Load Element Form ..");
		ArrayList<FieldElement>  fieldElementList = new ArrayList<FieldElement>();
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) objectForm;
		ArrayList<String> elementProducts =  applicationGroup.getProductCompareData(CompareDataM.SoruceOfData.PREV_ROLE, SystemConstant.getArrayListConstant("DISPLAY_PRODUCT"));				
		ArrayList<String> existingProducts = applicationGroup.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
		
		try {
			String productElementField = "PRODUCT_ELEMENT";
			ArrayList<String> products = new ArrayList<String>();
			products =applicationGroup.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
			if(!Util.empty(products)){
				 for(String product:products){
					 logger.debug("product>>"+product);
					 FieldElement fieldElement = new FieldElement();
					 fieldElement.setElementId(productElementField);
					 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
					 fieldElement.setElementGroupLevel(CompareDataM.UniqueLevel.APPLICATION);
					 fieldElementList.add(fieldElement);
				 }
			}
			
			
			if(!Util.empty(existingProducts)){
				 for(String product:existingProducts){
					 if(!elementProducts.contains(product)){
						 elementProducts.add(product);
					 }
					 logger.debug("product>>"+product);
					 	String formId = FormControl.getFormId("PRODUCT_FORM", product);
					 	logger.debug("formId>>"+formId);
						ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(formId,roleId);
						if (!Util.empty(productSubForms)) {
							for (ORIGSubForm subForm : productSubForms) {
								if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
									ArrayList<FieldElement>  subFormFieldElements =  subForm.elementForm(request, objectForm);
									if(!Util.empty(subFormFieldElements)){
										fieldElementList.addAll(subFormFieldElements);
									}
								}
							}
						}
				 }
			}
			
			String roleId = FormControl.getFormRoleId(request);
//			ArrayList<ApplicationDataM> appInfoList = applicationGroup.getApplicationLifeCycle(new BigDecimal(applicationGroup.getMaxLifeCycle()));
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			ArrayList<ApplicationDataM> appInfoList = applicationGroup.getApplications(personalInfo.getPersonalId(), PERSONAL_TYPE, PERSONAL_RELATION_APPLICATION_LEVEL);
			if(!Util.empty(appInfoList)){
				for(ApplicationDataM appInfo : appInfoList){
					LoanDataM loanDataM =  appInfo.getLoan();
					if(!Util.empty(loanDataM)){
						CardDataM card = loanDataM.getCard();
						if(card != null)
						{
							HashMap<String, Object> borrowerCardInfo = CardInfoUtil.getCardInfo(card.getCardType());
							String cardCodeborrower = (String) borrowerCardInfo.get("CARD_CODE");
							String productFormId = ListBoxControl.getName(FIELD_ID_CARD_TYPE, cardCodeborrower, "SYSTEM_ID1");
						 	logger.debug("productFormId>>"+productFormId);
							ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(productFormId,roleId);
							logger.debug("subForm.size():"+productSubForms.size());
							if (!Util.empty(productSubForms)) {
								for (ORIGSubForm subForm : productSubForms) {
									if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm))){
										logger.debug("subForm productForm>>"+subForm);
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
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElementList;
	}
}
