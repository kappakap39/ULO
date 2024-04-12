package com.eaf.orig.ulo.app.view.form.popup.product.kcc.sup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.CardDataM;

public class SupplementaryCardPopup1 extends ORIGSubForm {
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		String	CARD_LEVEL= request.getParameter("CARD_LEVEL_"+PRODUCT_CRADIT_CARD);			
		ApplicationDataM applicationItem = null;
		if(appForm instanceof HashMap){
			HashMap<String,Object> hObjectModule = (HashMap<String,Object>)appForm;
				applicationItem = (ApplicationDataM)hObjectModule.get("APPLICATION");
			}else{
				applicationItem = (ApplicationDataM)appForm;
			}
		
		CardDataM cardM = applicationItem.getCard();
		cardM.setCardLevel(CARD_LEVEL);
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
