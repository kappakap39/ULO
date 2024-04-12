package com.eaf.orig.ulo.app.view.form.popup.income;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.BundleKLDataM;

public class BundleKLPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BundleKLPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		BundleKLDataM bundleKLM = (BundleKLDataM)appForm;
		
		String VERIFIED_INCOME_KL = request.getParameter("VERIFIED_INCOME_KL");
		String VERIFIED_DATE = request.getParameter("VERIFIED_DATE");
		String ESTIMATED_INCOME = request.getParameter("ESTIMATED_INCOME");
		
		logger.debug("VERIFIED_INCOME_KL >>"+VERIFIED_INCOME_KL);	
		logger.debug("VERIFIED_DATE >>"+VERIFIED_DATE);	
		logger.debug("ESTIMATED_INCOME >>"+ESTIMATED_INCOME);
		
		bundleKLM.setVerifiedIncome(FormatUtil.toBigDecimal(VERIFIED_INCOME_KL, true));
		bundleKLM.setVerifiedDate(FormatUtil.toDate(VERIFIED_DATE,FormatUtil.TH));
		bundleKLM.setEstimated_income(FormatUtil.toBigDecimal(ESTIMATED_INCOME, true));
		
		//to check if final value from screen match with previous or not
		if(MConstant.FLAG.TEMP.equals(bundleKLM.getCompareFlag())) {
			FormErrorUtil formError = new FormErrorUtil();
			formError.addAll(IncomeTypeUtility.executeBundleKLCompareScreen(request, bundleKLM));
			if(Util.empty(formError.getFormError())) {
				bundleKLM.setCompareFlag(MConstant.FLAG.YES);
			} else {
				bundleKLM.setCompareFlag(MConstant.FLAG.WRONG);
			}
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "BUNDLING_KL_POPUP";
		BundleKLDataM bundleKLM = (BundleKLDataM)appForm;
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		
		if(!Util.empty(bundleKLM)) {
			formError.mandatoryDate(subformId,"VERIFIED_DATE","VERIFIED_DATE", bundleKLM.getVerifiedDate(),request);
		}
		if(Util.empty(formError.getFormError())) {
			//Check source e-app for ignore compare field
/*			if(ApplicationUtil.eApp(source)) {
				bundleKLM.setCompareFlag(MConstant.FLAG.YES);
			}else{*/
				String roleId = FormControl.getFormRoleId(request);
				if(MConstant.ROLE.DV.equals(roleId) && !IncomeTypeUtility.isBundleKLComplete(bundleKLM, roleId)) {
					formError.addAll(IncomeTypeUtility.executeBundleKLCompareScreen(request, bundleKLM));
					if(Util.empty(formError.getFormError())) {
						bundleKLM.setCompareFlag(MConstant.FLAG.YES);
					} else {
						bundleKLM.setCompareFlag(MConstant.FLAG.TEMP);
					}
				}
			/*}*/
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
