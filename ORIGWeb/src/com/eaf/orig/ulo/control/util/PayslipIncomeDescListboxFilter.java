package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;

public class PayslipIncomeDescListboxFilter implements ListBoxFilterInf{
	private static transient Logger logger = Logger.getLogger(PayslipIncomeDescListboxFilter.class);
	private PayslipMonthlyDataM payslipMonthlyM = null;
	private String PAYSLIP_INCOME_FIX = SystemConstant.getConstant("PAYSLIP_INCOME_FIXED");
	private String PAYSLIP_INCOME_VARIABLE = SystemConstant.getConstant("PAYSLIP_INCOME_VARIABLE");
	private String FIELD_ID_PAYSLIP_INCOME_TYPE_FIX = SystemConstant.getConstant("FIELD_ID_PAYSLIP_INCOME_TYPE_FIX");
	private String FIELD_ID_PAYSLIP_INCOME_TYPE_VARIABLE = SystemConstant.getConstant("FIELD_ID_PAYSLIP_INCOME_TYPE_VARIABLE");
	
	
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId, String cacheId, String typeId, HttpServletRequest request) {
		logger.debug("INCOME_TYPE : "+payslipMonthlyM.getIncomeType());
		ArrayList<HashMap<String,Object>> filterListBoxList = new ArrayList<>();
		if(!Util.empty(payslipMonthlyM)){
			if(PAYSLIP_INCOME_FIX.equals(payslipMonthlyM.getIncomeType())){
				filterListBoxList = ListBoxControl.getListBox(FIELD_ID_PAYSLIP_INCOME_TYPE_FIX,"ACTIVE");
			}else if(PAYSLIP_INCOME_VARIABLE.equals(payslipMonthlyM.getIncomeType())){
				filterListBoxList = ListBoxControl.getListBox(FIELD_ID_PAYSLIP_INCOME_TYPE_VARIABLE,"ACTIVE");
			}
		}
		return filterListBoxList;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId, HttpServletRequest request) {
	}

	@Override
	public void init(Object objectForm) {
		this.payslipMonthlyM = (PayslipMonthlyDataM)objectForm;
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
