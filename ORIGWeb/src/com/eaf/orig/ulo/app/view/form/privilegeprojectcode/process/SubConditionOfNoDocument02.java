package com.eaf.orig.ulo.app.view.form.privilegeprojectcode.process;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.privilegeprojectcode.util.PrivilegeUtil;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;

public class SubConditionOfNoDocument02 extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(SubConditionOfNoDocument02.class);
	private String CONSTANNT_AMOUNT =SystemConstant.getConstant("PRVLG_COND02_NO_DOCUMENT_PREMIER");
	@Override
	public Object processAction() {
		String cardType=request.getParameter("CARD_TYPE");
		String[] PROJECT_CODE = SystemConstant.getArrayConstant("PROJECT_CODE_OF_NO_DOC_02_"+PrivilegeUtil.getCardTypeDesc(cardType).toUpperCase());
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectForm;		
		try {
			BigDecimal invesmentAmount= CalculatePrivilegeProjectCode.getSumPrivilegeProjectCodeProductSavingAmount(privilegeProjectCode.getPrivilegeProjectCodeProductSavings());
			BigDecimal premierInvesmentAmount =  FormatUtil.toBigDecimal(CONSTANNT_AMOUNT);
			int compareResult =Util.compareBigDecimal(invesmentAmount, premierInvesmentAmount);
			if(compareResult==0 ||compareResult==1){
				return PROJECT_CODE;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}	
		return null;
	}

}
