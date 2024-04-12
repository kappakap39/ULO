package com.eaf.orig.ulo.app.view.form.property.field;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.SavingAccountDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDetailDataM;

public class SAVING_ACCT_AMTProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(SAVING_ACCT_AMTProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("SAVING_ACCT_AMTProperty.validateForm");
		SavingAccountDataM othSavingAcctM = (SavingAccountDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		ArrayList<SavingAccountDetailDataM> detailList = othSavingAcctM.getSavingAccountDetails();
		if(!Util.empty(detailList)) {
			int consecutiveCounter = 0;
			for(SavingAccountDetailDataM monthlyDetail : detailList) {
				if(consecutiveCounter == SavingAccountDetailDataM.CONSECUTIVE_COUNT ) {
					break;
				}
				if(!Util.empty(monthlyDetail.getAmount()) && Util.compareBigDecimal(BigDecimal.ZERO, monthlyDetail.getAmount()) != 0) {
					consecutiveCounter++;
				} else {
					consecutiveCounter = 0;
				}
			}
			if(consecutiveCounter < SavingAccountDetailDataM.CONSECUTIVE_COUNT) {
				formError.error("AMOUNT_"+othSavingAcctM.getSeq()+"_1",MessageErrorUtil.getText(request,"ERROR_ID_OPEN_FUND_AMT"));
			}
		} else {
			formError.error("AMOUNT_"+othSavingAcctM.getSeq()+"_1",MessageErrorUtil.getText(request,"ERROR_ID_OPEN_FUND_AMT"));
		}
		return formError.getFormError();
	}
}
