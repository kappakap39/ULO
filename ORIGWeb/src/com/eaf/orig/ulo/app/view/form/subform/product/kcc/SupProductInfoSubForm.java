package com.eaf.orig.ulo.app.view.form.subform.product.kcc;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SupProductInfoSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(SupProductInfoSubForm.class);
	private String PERSONAL_TYPE_APPLICANT =SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		logger.debug("ProductInfoSubForm ..... ");
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM) appForm);

		String cardType = request.getParameter("PRODUCTS_CARD_TYPE");
		String cardId = request.getParameter("CARD_ID");
		if (Util.empty(applicationGroup)) {
			applicationGroup = new ApplicationGroupDataM();

		}
		ArrayList<ApplicationDataM> appListM = applicationGroup.filterApplicationLifeCycle(PRODUCT_CRADIT_CARD);
		if (!Util.empty(appListM)) {
			for (ApplicationDataM appM : appListM) {
				if (Util.empty(appM)) {
					appM = new ApplicationDataM();
					appM.setBusinessClassId(PRODUCT_CRADIT_CARD);
				}
				ArrayList<LoanDataM> loanListM = appM.getLoans();

				if (Util.empty(loanListM)) {
					loanListM = new ArrayList<LoanDataM>();
					appM.setLoans(loanListM);
				}
				if (!Util.empty(loanListM)) {
					for (LoanDataM loan : loanListM) {
						CardDataM card = loan.getCard();
						if (Util.empty(card)) {
							card = new CardDataM();
						}
						card.setCardType(cardType);
						card.setCardId(cardId);
						// cardM.setCardLevel(cardLevel); *****
					}
				}
			}
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM) appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		ApplicationDataM applicationM = applicationGroup.getApplicationProduct(MConstant.Product.CREDIT_CARD);
		ArrayList<LoanDataM> loans = applicationM.getLoans();
		if (Util.empty(loans)) {
			loans = new ArrayList<LoanDataM>();
		}
		CardDataM card = new CardDataM();
		for (LoanDataM loan : loans) {
			card = loan.getCard();
		}
		if (Util.empty(card)) {
			card = new CardDataM();
		}
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "SUP_PRODUCT_INFO_SUBFORM";
		logger.debug("subformId >> " + subformId);

		formError.mandatory(subformId, "PRODUCTS_CARD_TYPE", applicationM.getBusinessClassId(), request);
		formError.mandatory(subformId, "CARD_TYPE", card.getCardId(), request);

		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
