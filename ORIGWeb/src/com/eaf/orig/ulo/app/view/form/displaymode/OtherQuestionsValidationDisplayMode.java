package com.eaf.orig.ulo.app.view.form.displaymode;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;



public class OtherQuestionsValidationDisplayMode extends FormDisplayModeHelper {
	private static transient Logger logger = Logger.getLogger(OtherQuestionsValidationDisplayMode.class);
	private	 String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
	private	 String ACCT_VALIDATION_PASS = SystemConstant.getConstant("ACCT_VALIDATION_PASS");
	
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String displayMode = HtmlUtil.VIEW;
		EntityFormHandler	EntityForm	=	(EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();		
		if(!Util.empty(applications)){
			for(ApplicationDataM applicationItem:applications){
			 	 	CashTransferDataM cashTransfer = applicationItem.getCashTransfer(CALL_FOR_CASH);
			 		if(!Util.empty(cashTransfer) && !Util.empty( cashTransfer.getCompleteData()) && cashTransfer.getCompleteData().equals(ACCT_VALIDATION_PASS)){
			 			logger.debug("cashTransfer >>>>  "+HtmlUtil.EDIT);
			 			displayMode = HtmlUtil.EDIT;
			 		}
			}
		}
		return displayMode;
	}
}
