package com.eaf.orig.ulo.app.view.form.subform.product.kec;

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
import com.eaf.orig.ulo.model.app.BundleSMEDataM;

public class BundlingKSMEInfoSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BundlingKSMEInfoSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		ArrayList<ApplicationDataM> appListM = applicationGroup.filterApplicationLifeCycle(MConstant.Product.K_EXPRESS_CASH);
		
		String		businessOwnerFlag =request.getParameter("KEC_BUSINESS_OWNER_FLAG");
		String		applicantQuality =request.getParameter("KEC_APPLICANT_QUALITY");
		String		gTotalExistPayment =request.getParameter("KEC_G-TOTAL-EXIST-PAYMENT");
		String		approvalLimit =request.getParameter("KEC_APPROVAL_LIMIT");
		String		gTotalNewPay =request.getParameter("KEC_G-TOTAL-NEW_PAY-REQ");
		String		individual =request.getParameter("KEC_INDIVIDUAL_RATIO");
		String		gDscr =request.getParameter("KEC_G-DSCR-REQ");
		String		corporate =request.getParameter("KEC_CORPORATE_RATIO");
		
		logger.debug("businessOwnerFlag :"+businessOwnerFlag);
		logger.debug("applicantQuality :"+applicantQuality);
		logger.debug("gTotalExistPayment :"+gTotalExistPayment);
		logger.debug("approvalLimit :"+approvalLimit);
		logger.debug("gTotalNewPay :"+gTotalNewPay);
		logger.debug("individual :"+individual);
		logger.debug("gDscr :"+gDscr);
		logger.debug("corporate :"+corporate);
		
		if (!Util.empty(appListM)) {
			for(ApplicationDataM appM : appListM){
				if(Util.empty(appM)){
					 appM  = new ApplicationDataM();
					 appM.setBusinessClassId(MConstant.Product.K_EXPRESS_CASH);
				}
				BundleSMEDataM bundleSMEM =appM.getBundleSME();
						if(Util.empty(bundleSMEM)){
							bundleSMEM = new BundleSMEDataM();
							bundleSMEM.setBusOwnerFlag(businessOwnerFlag);
							bundleSMEM.setApplicantQuality(applicantQuality);
							bundleSMEM.setgTotExistPayment(FormatUtil.toBigDecimal(gTotalExistPayment, false));
							bundleSMEM.setApprovalLimit(FormatUtil.toBigDecimal(approvalLimit,false));
							bundleSMEM.setgTotNewPayReq(FormatUtil.toBigDecimal(gTotalNewPay, false));
							bundleSMEM.setIndividualRatio(FormatUtil.toBigDecimal(individual, false));
							bundleSMEM.setgDscrReq(FormatUtil.toBigDecimal(gDscr, false));
							bundleSMEM.setCorporateRatio(FormatUtil.toBigDecimal(corporate, false));
							appM.setBundleSME(bundleSMEM);
							applicationGroup.addApplications(appM);
						}
						else{
							bundleSMEM.setBusOwnerFlag(businessOwnerFlag);
							bundleSMEM.setApplicantQuality(applicantQuality);
							bundleSMEM.setgTotExistPayment(FormatUtil.toBigDecimal(gTotalExistPayment, false));
							bundleSMEM.setApprovalLimit(FormatUtil.toBigDecimal(approvalLimit,false));
							bundleSMEM.setgTotNewPayReq(FormatUtil.toBigDecimal(gTotalNewPay, false));
							bundleSMEM.setIndividualRatio(FormatUtil.toBigDecimal(individual, false));
							bundleSMEM.setgDscrReq(FormatUtil.toBigDecimal(gDscr, false));
							bundleSMEM.setCorporateRatio(FormatUtil.toBigDecimal(corporate, false));
							
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
