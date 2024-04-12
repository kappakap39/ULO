package com.eaf.orig.ulo.app.view.form.privilegeprojectcode.process;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductInsuranceDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductSavingDataM;

public class CalculatePrivilegeProjectCode {
	private static transient Logger logger = Logger.getLogger(CalculatePrivilegeProjectCode.class);	
	public static BigDecimal getSumPrivilegeProjectCodeProductSavingAmount(ArrayList<PrivilegeProjectCodeProductSavingDataM> privilegeProjectCodeProductSavings){
		BigDecimal totalInvesmentBalanceAmount= BigDecimal.ZERO;
		if(null != privilegeProjectCodeProductSavings){
			for(PrivilegeProjectCodeProductSavingDataM privilegeProjectCodeProductSaving :privilegeProjectCodeProductSavings){
				totalInvesmentBalanceAmount = totalInvesmentBalanceAmount.add(privilegeProjectCodeProductSaving.getAccountBalance().divide(privilegeProjectCodeProductSaving.getHoldingRatio()));
				logger.debug("totalInvesmentBalanceAmount>>>"+totalInvesmentBalanceAmount);
			}
		}
		return totalInvesmentBalanceAmount;
	}
	
	public static BigDecimal getSumPrivilegeProjectCodeProductInsuranceAmount(ArrayList<PrivilegeProjectCodeProductInsuranceDataM> privilegeProjectCodeProductInsurances) {
		BigDecimal totalInsuranceBalanceAmount= BigDecimal.ZERO;
		BigDecimal insuranceYear= BigDecimal.ZERO;
		if(null!=privilegeProjectCodeProductInsurances){
			for(PrivilegeProjectCodeProductInsuranceDataM prvlgProjectCodeProductInsurance :privilegeProjectCodeProductInsurances){
				insuranceYear =FormatUtil.toBigDecimal(CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PRVLG_INSURANCE_TYPE"),prvlgProjectCodeProductInsurance.getInsuranceType(), "SYSTEM_ID1"));
				totalInsuranceBalanceAmount =totalInsuranceBalanceAmount.add(prvlgProjectCodeProductInsurance.getPremium().multiply(insuranceYear));
				logger.debug("insuranceYear>>"+insuranceYear);
				logger.debug("totalInsuranceBalanceAmount>>"+totalInsuranceBalanceAmount);
			}
		}
		return totalInsuranceBalanceAmount;
	}
	
	public static BigDecimal getTotalSavingAmountAndInsuranceAmount(BigDecimal totalSaving,BigDecimal totalInsurance) {
		return totalSaving.add(totalInsurance);
	}
}
