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

public class SubConditionOfNoDocument09 extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(SubConditionOfNoDocument09.class);
	private String CONSTANNT_AMOUNT =SystemConstant.getConstant("PRVLG_COND09_NO_DOCUMENT_WISDOM");
	private ArrayList<String> PROFESSION_COND =new ArrayList<String>(Arrays.asList(SystemConstant.getArrayConstant("PRVLG_PROFESSION")));
	@Override
	public Object processAction() {
		String cardType=request.getParameter("CARD_TYPE");
		String[] PROJECT_CODE = SystemConstant.getArrayConstant("PROJECT_CODE_OF_NO_DOC_09_"+PrivilegeUtil.getCardTypeDesc(cardType).toUpperCase());
		String PROFESSION =request.getParameter("PROFESSION");
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectForm;			
		try {
			BigDecimal wisdomInsuranceAmount =FormatUtil.toBigDecimal(CONSTANNT_AMOUNT);
			BigDecimal invesmentAmount = CalculatePrivilegeProjectCode.getSumPrivilegeProjectCodeProductSavingAmount(privilegeProjectCode.getPrivilegeProjectCodeProductSavings());
			int compareResult = Util.compareBigDecimal(invesmentAmount, wisdomInsuranceAmount);
			if((compareResult==1||compareResult==0) && PROFESSION_COND.contains(PROFESSION)){
				return PROJECT_CODE;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
}
