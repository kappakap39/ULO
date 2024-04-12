package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class KECCardInfoSubForm extends ORIGSubForm {

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
	String	referralWisdomNo = request.getParameter("REFERRAL_WISDOM_NO");
	String	groupNo = request.getParameter("GROUP_NO");
	String	noAppInGang = request.getParameter("NO_APP_IN_GANG");
	String	campaignCode = request.getParameter("CAMPAIGN_CODE");
	
	ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
	ApplicationDataM appM=	applicationGroup.getApplicationProduct(MConstant.Product.K_EXPRESS_CASH);
	if(Util.empty(appM)){
		appM  = new ApplicationDataM();

	}
			ArrayList<LoanDataM> loanListM=	appM.getLoans();
			if(Util.empty(loanListM)){
				loanListM = new ArrayList<LoanDataM>();
				LoanDataM newloanM = new LoanDataM();
				CardDataM cardM = new CardDataM();
				cardM.setReferralCardNo(referralWisdomNo);
				cardM.setGangNo(groupNo);
				cardM.setNoAppInGang(FormatUtil.toBigDecimal(noAppInGang));
		//*****	groupNo
				newloanM.setCard(cardM);
				loanListM.add(newloanM);
				appM.setLoans(loanListM);
			
			}
			else{
				for(LoanDataM loanM:loanListM){
					CardDataM cardM = loanM.getCardInfo(CardDataM.ApplicationCardType.SUPPLEMENTARY);
					if(Util.empty(cardM)){
						 cardM = new CardDataM();
					}
					cardM.setReferralCardNo(referralWisdomNo);
					cardM.setGangNo(groupNo);
					cardM.setNoAppInGang(FormatUtil.toBigDecimal(noAppInGang));
					
				}
				
			}
			applicationGroup.addApplications(appM);
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
