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
import com.eaf.orig.ulo.model.app.OpenedEndFundDataM;
import com.eaf.orig.ulo.model.app.OpenedEndFundDetailDataM;

public class OPEN_FUND_AMTProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(OPEN_FUND_AMTProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("OPEN_FUND_AMTProperty.validateForm");
		OpenedEndFundDataM othOpenFundM = (OpenedEndFundDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		ArrayList<OpenedEndFundDetailDataM> detailList = othOpenFundM.getOpenEndFundDetails();
		if(!Util.empty(detailList)) {
			int consecutiveCounter = 0;
			int monthCount = 0;
			for(OpenedEndFundDetailDataM monthlyDetail : detailList) {
				monthCount++;
				if(consecutiveCounter == OpenedEndFundDetailDataM.CONSECUTIVE_COUNT ) {
					break;
				}
				if(!Util.empty(monthlyDetail.getAmount()) && Util.compareBigDecimal(BigDecimal.ZERO, monthlyDetail.getAmount()) != 0) {
					consecutiveCounter++;
				} else {
					if(monthCount != 7 && monthCount != 1){
					formError.error("AMOUNT_"+othOpenFundM.getSeq()+"_"+monthCount,MessageErrorUtil.getText(request,"ERROR_ID_OPEN_FUND_AMT"));
					}
					consecutiveCounter = 0;
				}
			}
			if(consecutiveCounter < OpenedEndFundDetailDataM.CONSECUTIVE_COUNT) {
				formError.error("AMOUNT_"+othOpenFundM.getSeq()+"_1",MessageErrorUtil.getText(request,"ERROR_ID_OPEN_FUND_AMT"));
			}
		} else {
			formError.error("AMOUNT_"+othOpenFundM.getSeq()+"_1",MessageErrorUtil.getText(request,"ERROR_ID_OPEN_FUND_AMT"));
		}
		return formError.getFormError();
	}
}
