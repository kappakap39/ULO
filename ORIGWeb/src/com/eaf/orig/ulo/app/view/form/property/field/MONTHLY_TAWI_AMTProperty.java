package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DetailDataM;

public class MONTHLY_TAWI_AMTProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(MONTHLY_TAWI_AMTProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("MONTHLY_TAWI_AMTProperty.validateForm");
		MonthlyTawi50DataM monthlyData = (MonthlyTawi50DataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		ArrayList<MonthlyTawi50DetailDataM> detailList = monthlyData.getMonthlyTaxi50Details();
		if(!Util.empty(detailList)) {
			boolean incomeFound = false;
			for(MonthlyTawi50DetailDataM monthlyDetail : detailList) {
				if(!Util.empty(monthlyDetail.getAmount())) {
					incomeFound = true;
					break;
				}
			}
			if(!incomeFound) {
				logger.info("MONTHLY_TAWI_AMT not found");
				formError.error("AMOUNT_"+monthlyData.getSeq()+"_1",MessageErrorUtil.getText(request,"ERROR_ID_MONTHLY_TAWI_AMT"));
			}
		} else {
			formError.error("AMOUNT_"+monthlyData.getSeq()+"_1",MessageErrorUtil.getText(request,"ERROR_ID_MONTHLY_TAWI_AMT"));
		}
		return formError.getFormError();
	}
}
