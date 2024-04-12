package com.eaf.orig.ulo.app.view.form.privilegeprojectcode.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.privilegeprojectcode.util.PrivilegeUtil;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;

public class SubConditionOfNoDocument10 extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(SubConditionOfNoDocument10.class);
	private String CONSTANNT_AMOUNT =SystemConstant.getConstant("PRVLG_COND10_NO_DOCUMENT_PREMIER");
	private ArrayList<String> PROFESSION_COND =new ArrayList<String>(Arrays.asList(SystemConstant.getArrayConstant("PRVLG_PROFESSION")));
	@Override
	public Object processAction() {
		String cardType=request.getParameter("CARD_TYPE");
		String[] PROJECT_CODE = SystemConstant.getArrayConstant("PROJECT_CODE_OF_NO_DOC_10_"+PrivilegeUtil.getCardTypeDesc(cardType).toUpperCase());
		String PROFESSION =request.getParameter("PROFESSION");
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectForm;			
		try {
			
			BigDecimal invesmentAmount = CalculatePrivilegeProjectCode.getSumPrivilegeProjectCodeProductSavingAmount(privilegeProjectCode.getPrivilegeProjectCodeProductSavings());
			BigDecimal premierInsuranceAmount =FormatUtil.toBigDecimal(CONSTANNT_AMOUNT);
			int compareResult = Util.compareBigDecimal(invesmentAmount, premierInsuranceAmount);
			if((compareResult==1 ||compareResult==0) && PROFESSION_COND.contains(PROFESSION)){
				return PROJECT_CODE;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
}
