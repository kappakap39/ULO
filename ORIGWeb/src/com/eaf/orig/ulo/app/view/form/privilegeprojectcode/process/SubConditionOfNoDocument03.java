package com.eaf.orig.ulo.app.view.form.privilegeprojectcode.process;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.privilegeprojectcode.util.PrivilegeUtil;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;

public class SubConditionOfNoDocument03 extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(SubConditionOfNoDocument03.class);
	private String CONSTANNT_AMOUNT =SystemConstant.getConstant("PRVLG_COND03_NO_DOCUMENT_WISDOM");
	@Override
	public Object processAction() {
		String cardType=request.getParameter("CARD_TYPE");
		String[]  PROJECT_CODE = SystemConstant.getArrayConstant("PROJECT_CODE_OF_NO_DOC_03_"+PrivilegeUtil.getCardTypeDesc(cardType).toUpperCase());
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectForm;			
		try {
			BigDecimal insuranceBalanceAmount = CalculatePrivilegeProjectCode.getSumPrivilegeProjectCodeProductInsuranceAmount(privilegeProjectCode.getPrivilegeProjectCodeProductInsurances());
			BigDecimal wisdomInsuranceAmount =FormatUtil.toBigDecimal(CONSTANNT_AMOUNT);
			int compareResult = Util.compareBigDecimal(insuranceBalanceAmount, wisdomInsuranceAmount);
			if(compareResult==1||compareResult==0){
				return PROJECT_CODE;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
}
