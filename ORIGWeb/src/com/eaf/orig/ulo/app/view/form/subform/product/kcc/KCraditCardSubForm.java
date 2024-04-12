package com.eaf.orig.ulo.app.view.form.subform.product.kcc;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.FormControlDataM;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class KCraditCardSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(KCraditCardSubForm.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
		ArrayList<ApplicationDataM> borrowerApplications = applicationGroup.filterDisplayApplicationsProduct(PRODUCT_CRADIT_CARD,APPLICATION_CARD_TYPE_BORROWER);
		String roleId = FormControl.getFormRoleId(request);
		FormErrorUtil formError = new FormErrorUtil();	
		if (!Util.empty(borrowerApplications)) {
			for (ApplicationDataM borrowerItem : borrowerApplications) {
				CardDataM borrowerCard = borrowerItem.getCard();
				String borrowerUniqueId = borrowerItem.getApplicationRecordId();
				String borrowerCardTypeId = borrowerCard.getCardType();
				PersonalInfoDataM borrowerPersonalInfo = applicationGroup.getPersonalInfoRelation(borrowerUniqueId,PERSONAL_TYPE_APPLICANT, APPLICATION_LEVEL);
				if (null == borrowerPersonalInfo) {
					borrowerPersonalInfo = new PersonalInfoDataM();
				}
				logger.debug("borrowerCardTypeId : "+borrowerCardTypeId);
				HashMap<String, Object> borrowerCardInfo = CardInfoUtil.getCardInfo(borrowerCardTypeId);
				logger.debug("borrowerCardInfo : " + borrowerCardInfo);
				String cardCodeborrower = (String) borrowerCardInfo.get("CARD_CODE");
				String formId = ListBoxControl.getName(FIELD_ID_CARD_TYPE, cardCodeborrower, "SYSTEM_ID1");
				String borrowerCardCode = SQLQueryEngine.display(borrowerCardInfo, "CARD_CODE");
				logger.debug("formId : "+formId);
				logger.debug("borrowerCardCode : "+borrowerUniqueId);
				if(!Util.empty(formId)){
					FormControlDataM formControlM = new FormControlDataM();
					formControlM.setFormId(formId);
					formControlM.setRoleId(roleId);
					formControlM.setRequest(request);
					HashMap<String,String> requestData = new HashMap<String, String>();
					requestData.put("uniqueId",borrowerUniqueId);
					formControlM.setRequestData(requestData);
					boolean errorForm = formError.mandatoryForm(formControlM);
					logger.debug("errorForm >> "+errorForm);				
					String MESSAGE_ERROR = MessageErrorUtil.getText(request, "PREFIX_POPUP_PRODUCT_KCC_ERROR");
					String cardDesc =CacheControl.getName(FIELD_ID_CARD_TYPE, borrowerCardCode, "DISPLAY_NAME");		
					if(!errorForm){
						formError.error(MESSAGE_ERROR+cardDesc);
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
}
