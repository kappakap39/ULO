package com.eaf.orig.ulo.app.view.form.subform.product.kec;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

public class KExpressCashProductPopup2 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(KExpressCashProductPopup2.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		
		ApplicationDataM applicationItem =(ApplicationDataM)appForm;
		CardDataM card = applicationItem.getCard();
		String PRODUCT_K_EXPRESS_CASH =SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		String GROUP_NO =request.getParameter("GROUP_NO_"+PRODUCT_K_EXPRESS_CASH);
		String NO_APP_IN_GANG = request.getParameter("NO_APP_IN_GANG_"+PRODUCT_K_EXPRESS_CASH);
		String CAMPAIGN_CODE =  request.getParameter("CAMPAIGN_CODE_"+PRODUCT_K_EXPRESS_CASH);
		logger.debug("GROUP_NO >>> "+GROUP_NO);
		logger.debug("NO_APP_IN_GANG >>> "+NO_APP_IN_GANG);
		
		logger.debug("card >> "+card);
		if(Util.empty(card)){
			
			card = new CardDataM();
			card.setCardType(CardDataM.ApplicationCardType.BORROWER);
		}
		
		card.setGangNo(NO_APP_IN_GANG);
		card.setNoAppInGang(FormatUtil.toBigDecimal(NO_APP_IN_GANG));
		card.setCampaignCode(CAMPAIGN_CODE);
		
	/*	
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		ArrayList<ApplicationDataM> appListM = applicationGroup.getApplicationsProduct(MConstant.Product.K_EXPRESS_CASH);
		String		savingAccNo =request.getParameter("KEC_SAVING_ACC_NO");
		String		savingAccName =request.getParameter("KEC_SAVING_ACC_NAME");
		String		currentAccNo =request.getParameter("KEC_CURRENT_ACC_NO");
		String		currentAccName =request.getParameter("KEC_CURRENT_ACC_NAME");
		String		CAMPAIGN_CODE =request.getParameter("CAMPAIGN_CODE");

		logger.debug("savingAccNo :"+savingAccNo);
		logger.debug("savingAccName :"+savingAccName);
		logger.debug("currentAccNo :"+currentAccNo);
		logger.debug("currentAccName :"+currentAccName);
		logger.debug("CAMPAIGN_CODE :"+CAMPAIGN_CODE);
		
		if (!Util.empty(appListM)) {
			for(ApplicationDataM appM : appListM){
		
			if(Util.empty(appM)){
				appM  = new ApplicationDataM();
				appM.setBusinessClassId(MConstant.Product.K_EXPRESS_CASH);
	
				}
					ArrayList<LoanDataM> loanListM=	appM.getLoans();
					if(Util.empty(loanListM)){
						logger.debug(" new case ");
						loanListM = new ArrayList<LoanDataM>();
						LoanDataM newloanM = new LoanDataM();
						SpecialAdditionalServiceDataM specialM = new SpecialAdditionalServiceDataM();
						specialM.setServiceType("05");
						specialM.setSavingAccNo(savingAccNo);
						specialM.setSavingAccName(savingAccName);
						specialM.setCurrentAccNo(currentAccNo);
						specialM.setCurrentAccName(currentAccName);
						newloanM.addSpecialAdditionalService(specialM);
						loanListM.add(newloanM);
						appM.setLoans(loanListM);
						applicationGroup.addApplications(appM);
					}
					else{
						logger.debug(" Old case ");
						for(LoanDataM loanM:loanListM){
							SpecialAdditionalServiceDataM specialM =loanM.getSpecialAdditionalService("05"); // ATM
							if(Util.empty(specialM)){
								specialM = new SpecialAdditionalServiceDataM();	
								logger.debug(" new specialM ");
							}
							specialM.setServiceType("05");
							specialM.setSavingAccNo(savingAccNo);
							specialM.setSavingAccName(savingAccName);
							specialM.setCurrentAccNo(currentAccNo);
							specialM.setCurrentAccName(currentAccName);
							loanM.addSpecialAdditionalService(specialM);
							
						}
						
					}
				}
		}*/
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
