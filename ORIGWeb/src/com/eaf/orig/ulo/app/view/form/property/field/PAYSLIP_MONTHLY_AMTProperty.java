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
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDetailDataM;

public class PAYSLIP_MONTHLY_AMTProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(PAYSLIP_MONTHLY_AMTProperty.class);
	String PAYSLIP_INCOME_FIXED = SystemConstant.getConstant("PAYSLIP_INCOME_FIXED");
	String PAYSLIP_INCOME_VARIABLE = SystemConstant.getConstant("PAYSLIP_INCOME_VARIABLE");
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("PAYSLIP_MONTHLY_AMTProperty.validateForm");
		PayslipMonthlyDataM monthlyData = (PayslipMonthlyDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		ArrayList<PayslipMonthlyDetailDataM> detailList = monthlyData.getPayslipMonthlyDetails();
		if(PAYSLIP_INCOME_FIXED.equals(monthlyData.getIncomeType())){
			if(!Util.empty(detailList)) {
				
					PayslipMonthlyDetailDataM monthlyDetail1 = detailList.get(0);
					PayslipMonthlyDetailDataM monthlyDetail2 = detailList.get(1);
					PayslipMonthlyDetailDataM monthlyDetail3 = detailList.get(2);
					PayslipMonthlyDetailDataM monthlyDetail4 = detailList.get(3);
					
					if(Util.empty(monthlyDetail1.getAmount()) && Util.empty(monthlyDetail2.getAmount())  && Util.empty(monthlyDetail3.getAmount())
							 && Util.empty(monthlyDetail4.getAmount())){
						for(int i = 0 ; i < 4 ; i++){
							formError.error("AMOUNT_"+monthlyData.getSeq()+"_" + (i+1),MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_MONTHLY_AMOUNT"));
						}
					}
					
			} else {
				formError.error("AMOUNT_"+monthlyData.getSeq()+"_1",MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_MONTHLY_FIX"));
			}
		} else if(PAYSLIP_INCOME_VARIABLE.equals(monthlyData.getIncomeType())){
			if(!Util.empty(detailList)) {
				
				PayslipMonthlyDetailDataM monthlyDetail1 = detailList.get(0);
				PayslipMonthlyDetailDataM monthlyDetail2 = detailList.get(1);
				PayslipMonthlyDetailDataM monthlyDetail3 = detailList.get(2);
				PayslipMonthlyDetailDataM monthlyDetail4 = detailList.get(3);
				PayslipMonthlyDetailDataM monthlyDetail5 = detailList.get(4);
				PayslipMonthlyDetailDataM monthlyDetail6 = detailList.get(5);
				PayslipMonthlyDetailDataM monthlyDetail7 = detailList.get(6);
				
				if(Util.empty(monthlyDetail1.getAmount()) && Util.empty(monthlyDetail2.getAmount())  && Util.empty(monthlyDetail3.getAmount())
						 && Util.empty(monthlyDetail4.getAmount()) && Util.empty(monthlyDetail5.getAmount()) && Util.empty(monthlyDetail6.getAmount())
						 && Util.empty(monthlyDetail7.getAmount())){
					for(int i = 0 ; i < 7 ; i++){
						formError.error("AMOUNT_"+monthlyData.getSeq()+"_" + (i+1),MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_MONTHLY_VAR_AMOUNT"));
					}
				}
			} else {
				formError.error("AMT_"+monthlyData.getSeq()+"_1",MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_MONTHLY_VAR"));
			}
		} 
		return formError.getFormError();
	}
}
