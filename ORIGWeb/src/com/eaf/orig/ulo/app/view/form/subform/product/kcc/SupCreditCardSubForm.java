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
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;



public class SupCreditCardSubForm extends ORIGSubForm{
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	private static transient Logger logger = Logger.getLogger(SupCreditCardSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM) appForm;
		ArrayList<ApplicationDataM> supplemantaryApplications = personalApplicationInfo.getApplicationMaxLifeCycle();
		String roleId = FormControl.getFormRoleId(request);
		FormErrorUtil formError = new FormErrorUtil();	
		if (!Util.empty(supplemantaryApplications)) {
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
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		
		return false;
	}

}
