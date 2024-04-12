package com.eaf.orig.ulo.app.view.form.subform.product.kec;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

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

public class KExpressCashSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(KExpressCashSubForm.class);
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
		 ArrayList<ApplicationDataM> applications  = applicationGroup.filterDisplayApplicationsProduct(PRODUCT_K_EXPRESS_CASH,null);
		 String roleId = FormControl.getFormRoleId(request);
		 FormErrorUtil formError = new FormErrorUtil();	
		 if (!Util.empty(applications)) {
				for (ApplicationDataM applicationItem : applications) {
					CardDataM card = applicationItem.getCard();
					String uniqueId = applicationItem.getApplicationRecordId();
					String cardTypeId = card.getCardType();
					HashMap<String, Object> cardInfo = CardInfoUtil.getCardInfo(cardTypeId);				
					 
					String cardCode = (String)cardInfo.get("CARD_CODE");
					logger.debug("cardCode " + cardCode	);
					String formId = ListBoxControl.getName(FIELD_ID_CARD_TYPE,cardCode, "SYSTEM_ID1");
					logger.debug("formId " + formId);
					logger.debug("uniqueId " + uniqueId);
					
					FormControlDataM formControlM = new FormControlDataM();
					formControlM.setFormId(formId);
					formControlM.setRoleId(roleId);
					formControlM.setRequest(request);
					HashMap<String,String> requestData = new HashMap<String, String>();
					requestData.put("uniqueId",uniqueId);
					formControlM.setRequestData(requestData);
					boolean errorForm = formError.mandatoryForm(formControlM);
					logger.debug("errorForm >> "+errorForm);				
					String MESSAGE_ERROR = MessageErrorUtil.getText(request, "PREFIX_POPUP_PRODUCT_KEC_ERROR");
					String cardDesc =CacheControl.getName(FIELD_ID_CARD_TYPE,cardCode, "DISPLAY_NAME");	
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
