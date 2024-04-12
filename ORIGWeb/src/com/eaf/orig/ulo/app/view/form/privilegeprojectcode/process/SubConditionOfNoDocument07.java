package com.eaf.orig.ulo.app.view.form.privilegeprojectcode.process;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.privilegeprojectcode.util.PrivilegeUtil;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductCCADataM;

public class SubConditionOfNoDocument07 extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(SubConditionOfNoDocument07.class);
	private int PRVLG_PROJECT_PRODUCT_CCA_INDEX=0;
	@Override
	public Object processAction() {		
		String cardType=request.getParameter("CARD_TYPE");
		String[] PROJECT_CODE = SystemConstant.getArrayConstant("PROJECT_CODE_OF_NO_DOC_07_"+PrivilegeUtil.getCardTypeDesc(cardType).toUpperCase());
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectForm;		
		try {
			PrivilegeProjectCodeProductCCADataM prvlgProjectCodeProduct =	privilegeProjectCode.getPrivilegeProjectCodeProductCCAs(PRVLG_PROJECT_PRODUCT_CCA_INDEX);
			if(!Util.empty(prvlgProjectCodeProduct)){
				String result = prvlgProjectCodeProduct.getResult();
				result = null==result?"":result;
				if(SystemConstant.getConstant("PRVLG_CCA_RESULT_COMPLETE").equals(result)){
					return PROJECT_CODE; 				
				}
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
}
