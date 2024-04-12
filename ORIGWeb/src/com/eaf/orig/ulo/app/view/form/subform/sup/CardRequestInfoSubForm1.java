package com.eaf.orig.ulo.app.view.form.subform.sup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.model.FormControlDataM;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class CardRequestInfoSubForm1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CardRequestInfoSubForm1.class);
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");
	String SUB_FORM_ID = "SUP_CARD_REQUEST_INFO_SUBFROM_1";
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;	
		ArrayList<ApplicationDataM> supplemantaryApplications = applicationGroup.filterApplicationLifeCycle();
		String subformId = "SUP_CARD_REQUEST_INFO_SUBFROM_1";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		if (!Util.empty(supplemantaryApplications)) {
			PersonalInfoDataM personalInfoI = applicationGroup.getPersonalInfo(PERSONAL_TYPE_INFO);
			PersonalInfoDataM personalInfoS = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			if(!Util.empty(personalInfoI) && !Util.empty(personalInfoS)){
				String personalIndoTypeI = personalInfoI.getIdno();
				String personalInfoTypeS = personalInfoS.getIdno();
				if(!Util.empty(personalIndoTypeI) && personalIndoTypeI.equals(personalInfoTypeS)){
					formError.error(MessageErrorUtil.getText(request,"ERROR_PERSONALSUPPLEMENT_EQUAL_PERSONAL_INFO"));
				}
			}
			for (ApplicationDataM supplemantaryItem : supplemantaryApplications) {
				String uniqueId = supplemantaryItem.getApplicationRecordId();
				CardDataM supplemantaryCard = supplemantaryItem.getCard();
				String supplemantaryCardTypeId = supplemantaryCard.getCardType();

				HashMap<String, Object> supplemantaryCardInfo = CardInfoUtil.getCardInfo(supplemantaryCardTypeId);
				String cardCode = (String) supplemantaryCardInfo.get("CARD_CODE");
				String supplemantaryCardCode = SQLQueryEngine.display(supplemantaryCardInfo, "CARD_CODE");
				String formId = ListBoxControl.getName(FIELD_ID_CARD_TYPE, cardCode, "SYSTEM_ID2");
				logger.debug(">>>>>formId>>>>"+formId);
				logger.debug(">>>>>uniqueId>>>>"+uniqueId);
				
				FormControlDataM formControlM = new FormControlDataM();
				formControlM.setFormId(formId);
				formControlM.setRoleId(roleId);
				formControlM.setRequest(request);
				HashMap<String,String> requestData = new HashMap<String, String>();
				requestData.put("uniqueId",uniqueId);
				formControlM.setRequestData(requestData);
				boolean errorForm = formError.mandatoryForm(formControlM);
				logger.debug("errorForm >> "+errorForm);				
				String MESSAGE_ERROR = MessageErrorUtil.getText(request, "PREFIX_POPUP_PRODUCT_KCC_ERROR");
				String cardDesc =CacheControl.getName(FIELD_ID_CARD_TYPE, supplemantaryCardCode, "DISPLAY_NAME");		
				if(!errorForm){
					formError.error(MESSAGE_ERROR+cardDesc);
				}
 
			}
		}
		formError.mandatory(subformId,"MAIN_CARD_NUMBER",applicationGroup.getMainCardNo(),request);		
		String applicationType = applicationGroup.getApplicationType();
		logger.debug("applicationType : "+applicationType);
		logger.debug("APPLICATION_CARD_TYPE_SUPPLEMENTARY : "+APPLICATION_CARD_TYPE_SUPPLEMENTARY);
		if(APPLICATION_TYPE_ADD_SUP.equals(applicationGroup.getApplicationType())) {
			ArrayList<ApplicationDataM> addSupApplications = applicationGroup.filterApplicationLifeCycle();
			//validate Main card number same application is not null case submit
			formError.mandatory(subformId, "APPLICATION_NO", "", MessageErrorUtil.getText(request,"ERROR_NO_APPLICATIONS"), addSupApplications, request);
			logger.debug("addSupApplications "+addSupApplications);
			if (addSupApplications.size() == 0) {
				logger.debug("No applications in APPLICATION_TYPE_ADD_SUP");
//				formError.error("ERROR_NO_APPLICATIONS");
			}
		}
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		ArrayList<String> filedNames = new ArrayList<String>();
		filedNames.add("SUP_CARD_INFO");
		filedNames.add("PERCENT_LIMIT_MAINCARD");
		filedNames.add("PERCENT_LIMIT");
		try {		
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
				 fieldElement.setElementGroupId("");
				 fieldElement.setElementGroupLevel(CompareDataM.GroupDataLevel.PERSONAL_SUPPLEMENTATY);
				 fieldElements.add(fieldElement);
			 }
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastapplicationGroup = ((ApplicationGroupDataM)lastObjectForm);
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		auditFormUtil.auditForm(SUB_FORM_ID, "CARD_TYPE_SUP", applicationGroup,lastapplicationGroup, request);
		return auditFormUtil.getAuditForm();
	}

}
