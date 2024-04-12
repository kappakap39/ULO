package com.eaf.orig.ulo.app.view.form.subform.product.kcc;

import java.sql.Timestamp;

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
import com.eaf.orig.ulo.model.app.BundleKLDataM;

public class BundlingKLInfoSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BundlingKLInfoSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		ArrayList<ApplicationDataM> appListM = applicationGroup.filterApplicationLifeCycle(MConstant.Product.CREDIT_CARD);
	
		String	 approvalLimit = request.getParameter("KCC_APPROVAL_LIMIT");
		String	 verifiedIncome = request.getParameter("KCC_VERFIED_INCOME");
		String	 verifiedDate = request.getParameter("KCC_VERFIED_DATE");
		String	 estimatedIncome = request.getParameter("KCC_ESTIMATED_INCOME");
		
		if (!Util.empty(appListM)) {
			for (ApplicationDataM appM : appListM) {
				if (Util.empty(appM)) {
					appM = new ApplicationDataM();
					applicationGroup.addApplications(appM);
				}
				appM.setBusinessClassId("KCC");
				BundleKLDataM bundleKLM = appM.getBundleKL();
				if (Util.empty(bundleKLM)) {
					bundleKLM = new BundleKLDataM();
					appM.setBundleKL(bundleKLM);
				
				} 
//					bundleKLM.setApprovalLimit(FormatUtil.toBigDecimal(approvalLimit, false));
					bundleKLM.setVerifiedIncome(FormatUtil.toBigDecimal(verifiedIncome, false));
					if (!Util.empty(verifiedDate)) {
						bundleKLM.setVerifiedDate(FormatUtil.toDate(verifiedDate, FormatUtil.TH));
					}
					bundleKLM.setEstimated_income(FormatUtil.toBigDecimal(estimatedIncome, false));

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
