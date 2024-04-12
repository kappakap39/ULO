package com.eaf.orig.ulo.app.view.form.popup.income;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.BundleHLDataM;

public class BundleHLPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BundleHLPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		BundleHLDataM bundleHLM = (BundleHLDataM)appForm;
		
		String VERIFIED_INCOME = request.getParameter("VERIFIED_INCOME");
		String APPROVAL_DATE = request.getParameter("APPROVAL_DATE");
		String CC_CREDIT_LINE = request.getParameter("CC_CREDIT_LINE");
		String KEC_CREDIT_LINE = request.getParameter("KEC_CREDIT_LINE");
		String BUNDLING_HL_MORTGAGE_DATE = request.getParameter("BUNDLING_HL_MORTGAGE_DATE");
		String BUNDLING_HL_ACCOUNT_NO = request.getParameter("BUNDLING_HL_ACCOUNT_NO");
		String ACCOUNT_DATE = request.getParameter("ACCOUNT_DATE");
		String ACCOUNT_NAME = request.getParameter("ACCOUNT_NAME");
		
		logger.debug("VERIFIED_INCOME >>"+VERIFIED_INCOME);	
		logger.debug("APPROVAL_DATE >>"+APPROVAL_DATE);	
		logger.debug("CC_CREDIT_LINE >>"+CC_CREDIT_LINE);	
		logger.debug("KEC_CREDIT_LINE >>"+KEC_CREDIT_LINE);
		logger.debug("BUNDLING_HL_MORTGAGE_DATE >>"+BUNDLING_HL_MORTGAGE_DATE);	
		logger.debug("BUNDLING_HL_ACCOUNT_NO >>"+BUNDLING_HL_ACCOUNT_NO);
		logger.debug("ACCOUNT_DATE >>"+ACCOUNT_DATE);	
		logger.debug("ACCOUNT_NAME >>"+ACCOUNT_NAME);
		
		bundleHLM.setVerifiedIncome(FormatUtil.toBigDecimal(VERIFIED_INCOME, true));
		bundleHLM.setApprovedDate(FormatUtil.toDate(APPROVAL_DATE,FormatUtil.EN));
		bundleHLM.setCcApprovedAmt(FormatUtil.toBigDecimal(CC_CREDIT_LINE, true));	
		bundleHLM.setKecApprovedAmt(FormatUtil.toBigDecimal(KEC_CREDIT_LINE, true));
		bundleHLM.setMortGageDate(FormatUtil.toDate(BUNDLING_HL_MORTGAGE_DATE,FormatUtil.TH));
		bundleHLM.setAccountNo(BUNDLING_HL_ACCOUNT_NO);
		bundleHLM.setAccountOpenDate(FormatUtil.toDate(ACCOUNT_DATE,FormatUtil.TH));
		bundleHLM.setAccountName(ACCOUNT_NAME);
		
		//to check if final value from screen match with previous or not
		if(MConstant.FLAG.TEMP.equals(bundleHLM.getCompareFlag())) {
			FormErrorUtil formError = new FormErrorUtil();
			formError.addAll(IncomeTypeUtility.executeBundleHLCompareScreen(request, bundleHLM));
			if(Util.empty(formError.getFormError())) {
				bundleHLM.setCompareFlag(MConstant.FLAG.YES);
			} else {
				bundleHLM.setCompareFlag(MConstant.FLAG.WRONG);
			}
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		int BUNDLE_APPROVE_DAYS = SystemConstant.getIntConstant("BUNDLE_APPROVE_DAYS");
		String subformId = "BUNDLING_HL_POPUP";
		BundleHLDataM bundleHLM = (BundleHLDataM)appForm;
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		//ApplicationDate.getDate();
		
		if(!Util.empty(bundleHLM)) {
			formError.mandatory(subformId,"VERIFIED_INCOME","VERIFIED_INCOME", bundleHLM.getVerifiedIncome(),request);
			formError.mandatoryDate(subformId,"APPROVAL_DATE","APPROVAL_DATE", bundleHLM.getApprovedDate(),request);
			formError.mandatory(subformId,"CREDIT_LINE","CC_CREDIT_LINE", bundleHLM,request);
			formError.mandatoryDate(subformId,"BUNDLING_HL_MORTGAGE_DATE","BUNDLING_HL_MORTGAGE_DATE", bundleHLM.getMortGageDate(),request);
			formError.mandatory(subformId,"BUNDLING_HL_ACCOUNT_NO","BUNDLING_HL_ACCOUNT_NO", bundleHLM,request);
			
		}
		
		if(Util.empty(formError.getFormError())) {
//			if(ApplicationUtil.eApp(source)) {
//				bundleHLM.setCompareFlag(MConstant.FLAG.YES);
//			}else{
				String roleId = FormControl.getFormRoleId(request);
				if(MConstant.ROLE.DV.equals(roleId) && !IncomeTypeUtility.isBundleHLComplete(bundleHLM, roleId)) {
					formError.addAll(IncomeTypeUtility.executeBundleHLCompareScreen(request, bundleHLM));
					if(Util.empty(formError.getFormError())) {
						bundleHLM.setCompareFlag(MConstant.FLAG.YES);
					} else {
						bundleHLM.setCompareFlag(MConstant.FLAG.TEMP);
					}
				}
//			}
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
