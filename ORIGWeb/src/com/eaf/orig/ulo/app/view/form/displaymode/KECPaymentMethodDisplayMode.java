package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class KECPaymentMethodDisplayMode extends DE2FormDisplayMode{
	private static transient Logger logger = Logger.getLogger(KECPaymentMethodDisplayMode.class);
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");
	String KEC_PAYROLL_CARD_TYPE = SystemConstant.getConstant("KEC_PAYROLL_CARD_TYPE");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)FormControl.getObjectForm(request);
		String displayMode = HtmlUtil.EDIT;
		logger.debug("roleId >> "+roleId);
		if(ROLE_DE2.equals(roleId)){
			if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
				if(!isHasDataPreviousRole(applicationGroup)){
					displayMode =  HtmlUtil.EDIT;
				}else{
					displayMode =  HtmlUtil.VIEW;
				}
			}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
				displayMode =  HtmlUtil.EDIT;
			}
		}
//		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
//		if(!Util.empty(applications)){
//			for(ApplicationDataM application: applications){
//				String cardType = application.getCard().getCardType();
//				logger.debug("cardType : " + cardType);
//				if(KEC_PAYROLL_CARD_TYPE.equals(cardType)){
//					displayMode = HtmlUtil.VIEW;
//				}
//			}
//		}
		
		return displayMode;
	}
}
