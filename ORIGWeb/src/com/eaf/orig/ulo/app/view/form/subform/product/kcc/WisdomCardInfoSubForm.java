package com.eaf.orig.ulo.app.view.form.subform.product.kcc;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.WisdomDataM;

public class WisdomCardInfoSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(PaymentMethodSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		ArrayList<ApplicationDataM> appListM = applicationGroup.filterApplicationLifeCycle(MConstant.Product.CREDIT_CARD);
		String		insuranceType = request.getParameter("KCC_INSURANCE_TYPE");
		String		premiumAmt = request.getParameter("KCC_PREMIUM_AMT");
		String		transferFrom = request.getParameter("KCC_TRANSFER_FROM");
		String		qoutaOf = request.getParameter("KCC_QOUTA_OF");
		
		logger.debug("insuranceType :"+insuranceType);
		logger.debug("premiumAmt :"+premiumAmt);
		logger.debug("transferFrom :"+transferFrom);
		logger.debug("qoutaOf :"+qoutaOf);
		if(!Util.empty(appListM)){
			for(ApplicationDataM appM :appListM){
				if(Util.empty(appM)){
					appM  = new ApplicationDataM();
					appM.setBusinessClassId(MConstant.Product.CREDIT_CARD);
				}	
					ArrayList<LoanDataM> loanListM =appM.getLoans();	
					if(Util.empty(loanListM)){
						loanListM = new ArrayList<LoanDataM>();		
					}
					if(!Util.empty(loanListM)){
						for(LoanDataM loan:loanListM){
							CardDataM card = loan.getCard();
							if(Util.empty(card)){
								card = new CardDataM();
								loan.setCard(card);
							}
							if(!Util.empty(card)){
								WisdomDataM wisdom = card.getWisdom();
								if(Util.empty(wisdom)){
									wisdom = new WisdomDataM();
									card.setWisdom(wisdom);
								}
								wisdom.setInsuranceType(insuranceType);
								wisdom.setPremiumAmt(FormatUtil.toBigDecimal(premiumAmt, false));
								wisdom.setTransferFrom(transferFrom);
								wisdom.setQuotaOf(qoutaOf);
							}
						}
					}
			}
		}
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
