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
import com.eaf.orig.ulo.model.app.BundleHLDataM;

public class BundlingHLInfoSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(BundlingHLInfoSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		logger.debug("ProductBundlingHLInfoSubForm setProperties...");
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		ArrayList<ApplicationDataM> appListM = applicationGroup.filterApplicationLifeCycle(MConstant.Product.CREDIT_CARD);
		String approveCreditLine = request.getParameter("KCC_APPROVE_CREDIT_LINE");
		String verfiedIncome = request.getParameter("KCC_VERFIED_INCOME");
		if (!Util.empty(appListM)) {
			for (ApplicationDataM appM : appListM) {
				if (Util.empty(appM)) {
					appM = new ApplicationDataM();
					applicationGroup.addApplications(appM);
				}
				appM.setBusinessClassId(MConstant.Product.CREDIT_CARD);
				BundleHLDataM bundleHLM = appM.getBundleHL();
				if (Util.empty(bundleHLM)) {
					bundleHLM = new BundleHLDataM();
					appM.setBundleHL(bundleHLM);
				} 
					bundleHLM.setApproveCreditLine(FormatUtil.toBigDecimal(approveCreditLine, false));
					bundleHLM.setVerifiedIncome(FormatUtil.toBigDecimal(verfiedIncome, false));
				
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
