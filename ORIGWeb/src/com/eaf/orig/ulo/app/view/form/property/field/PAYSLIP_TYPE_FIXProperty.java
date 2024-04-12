package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;

public class PAYSLIP_TYPE_FIXProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(PAYSLIP_TYPE_FIXProperty.class);
	String PAYSLIP_INCOME_FIXED = SystemConstant.getConstant("PAYSLIP_INCOME_FIXED");
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("PAYSLIP_TYPE_FIXProperty.validateForm");
		PayslipDataM payslipM = (PayslipDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		boolean hasFixType = false;
		ArrayList<PayslipMonthlyDataM> monthly = payslipM.getPayslipMonthlys();
		if(!Util.empty(monthly) && payslipM.getNoMonth() == 0) {
			for(PayslipMonthlyDataM monthlyData: monthly) {
				if(!Util.empty(monthlyData.getIncomeType()) && PAYSLIP_INCOME_FIXED.equals(monthlyData.getIncomeType())){
					hasFixType = true;
				}
			}
			if(!hasFixType) {
				formError.error("PAYSLIP_TYPE_FIX",MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_FIX_TYPE"));
			}
		}
		return formError.getFormError();
	}
}
