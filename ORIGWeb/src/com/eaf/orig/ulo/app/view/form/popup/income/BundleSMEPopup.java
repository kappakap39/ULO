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
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;

@SuppressWarnings("serial")
public class BundleSMEPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BundleSMEPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		BundleSMEDataM bundleSMEM = (BundleSMEDataM)appForm;
		String DEFALUT_INDIVIDUAL_RATIO = SystemConstant.getConstant("DEFALUT_INDIVIDUAL_RATIO");
		String LOAN_TYPE_SINGLE = SystemConstant.getConstant("LOAN_TYPE_SINGLE");
		String BORROWING_TYPE = request.getParameter("BORROWING_TYPE");
		String APPLICANT_QUALITY = request.getParameter("APPLICANT_QUALITY");
		String BUSINESS_CODE = request.getParameter("BUSINESS_CODE");
		String APPROVAL_DATE = request.getParameter("APPROVAL_DATE");
		String APPROVAL_LIMIT = request.getParameter("APPROVAL_LIMIT");
		String INDIVIDUAL_RATIO = request.getParameter("INDIVIDUAL_RATIO");
		String CORPORATE_RATIO = request.getParameter("CORPORATE_RATIO");
		String G_TOTAL_EXIST_PAYMENT = request.getParameter("G_TOTAL_EXIST_PAYMENT");
		String G_TOTAL_NEW_PAY_REQ = request.getParameter("G_TOTAL_NEW_PAY_REQ");
		String G_DSCR_REQ = request.getParameter("G_DSCR_REQ");
		String ACCOUNT_DATE = request.getParameter("ACCOUNT_DATE");
		String ACCOUNT_NAME = request.getParameter("ACCOUNT_NAME");
		String BUNDLE_ACCOUNT_NO = request.getParameter("BUNDLE_ACCOUNT_NO");
		String TYPE_LIMIT = request.getParameter("TYPE_LIMIT");
		String VERIFIED_INCOME = request.getParameter("VERIFIED_INCOME");
		
		
		logger.debug("BORROWING_TYPE >>"+BORROWING_TYPE);	
		logger.debug("APPLICANT_QUALITY >>"+APPLICANT_QUALITY);	
		logger.debug("BUSINESS_CODE >>"+BUSINESS_CODE);
		logger.debug("APPROVAL_DATE >>"+APPROVAL_DATE);
		logger.debug("APPROVAL_LIMIT >>"+APPROVAL_LIMIT);
		logger.debug("INDIVIDUAL_RATIO >>"+INDIVIDUAL_RATIO);
		logger.debug("CORPORATE_RATIO >>"+CORPORATE_RATIO);
		logger.debug("G_TOTAL_EXIST_PAYMENT >>"+G_TOTAL_EXIST_PAYMENT);
		logger.debug("G_TOTAL_NEW_PAY_REQ >>"+G_TOTAL_NEW_PAY_REQ);
		logger.debug("G_DSCR_REQ >>"+G_DSCR_REQ);
		logger.debug("ACCOUNT_DATE >>"+ACCOUNT_DATE);
		logger.debug("ACCOUNT_NAME >>"+ACCOUNT_NAME);
		logger.debug("ACCOUNT_NO >>"+BUNDLE_ACCOUNT_NO);
		logger.debug("TYPE_LIMIT >>"+TYPE_LIMIT);
		logger.debug("VERIFIED_INCOME >>"+VERIFIED_INCOME);
		if(LOAN_TYPE_SINGLE.equals(BORROWING_TYPE)) {
			INDIVIDUAL_RATIO = DEFALUT_INDIVIDUAL_RATIO;
		}
		bundleSMEM.setBorrowingType(BORROWING_TYPE);
		bundleSMEM.setApplicantQuality(APPLICANT_QUALITY);
		bundleSMEM.setBusinessCode(BUSINESS_CODE);
		bundleSMEM.setApprovalDate(FormatUtil.toDate(APPROVAL_DATE,FormatUtil.TH));
		bundleSMEM.setApprovalLimit(FormatUtil.toBigDecimal(APPROVAL_LIMIT,true));	
		bundleSMEM.setIndividualRatio(FormatUtil.toBigDecimal(INDIVIDUAL_RATIO,true));	
		bundleSMEM.setCorporateRatio(FormatUtil.toBigDecimal(CORPORATE_RATIO,true));	
		bundleSMEM.setgTotExistPayment(FormatUtil.toBigDecimal(G_TOTAL_EXIST_PAYMENT,true));	
		bundleSMEM.setgTotNewPayReq(FormatUtil.toBigDecimal(G_TOTAL_NEW_PAY_REQ,true));	
		bundleSMEM.setgDscrReq(FormatUtil.toBigDecimal(G_DSCR_REQ,true));
		bundleSMEM.setAccountName(ACCOUNT_NAME);
		bundleSMEM.setAccountNo(FormatUtil.removeDash(BUNDLE_ACCOUNT_NO));
		bundleSMEM.setTypeLimit(TYPE_LIMIT);
		bundleSMEM.setAccountDate(FormatUtil.toDate(ACCOUNT_DATE, FormatUtil.TH));
		bundleSMEM.setVerifiedIncome(FormatUtil.toBigDecimal(VERIFIED_INCOME, true));
		
		//to check if final value from screen match with previous or not
		if(MConstant.FLAG.TEMP.equals(bundleSMEM.getCompareFlag())) {
			FormErrorUtil formError = new FormErrorUtil();
			formError.addAll(IncomeTypeUtility.executeBundleSMECompareScreen(request, bundleSMEM));
			if(Util.empty(formError.getFormError())) {
				bundleSMEM.setCompareFlag(MConstant.FLAG.YES);
			} else {
				bundleSMEM.setCompareFlag(MConstant.FLAG.WRONG);
			}
		}
	}

		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String APPLICATION_TEMPLATE_KEC_BUNDLE_SME = SystemConstant.getConstant("APPLICATION_TEMPLATE_KEC_BUNDLE_SME");
		int BUNDLE_APPROVE_DAYS = SystemConstant.getIntConstant("BUNDLE_APPROVE_DAYS");
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		
		BundleSMEDataM bundleSMEM = (BundleSMEDataM)appForm;	
		if(!Util.empty(bundleSMEM)) {
			ApplicationGroupDataM appGroupM = FormControl.getOrigObjectForm(request);
			String subformId = "BUNDLING_SME_POPUP";
			if(APPLICATION_TEMPLATE_KEC_BUNDLE_SME.equals(appGroupM.getApplicationTemplate())) {
				subformId = "BUNDLING_SME_KEC_POPUP";
			}
			
			logger.debug("subformId >>"+subformId);
			formError.mandatory(subformId,"BORROWING_TYPE","BORROWING_TYPE", bundleSMEM.getBorrowingType(),request);
			formError.mandatory(subformId,"APPLICANT_QUALITY","APPLICANT_QUALITY", bundleSMEM,request);
			formError.mandatory(subformId,"BUSINESS_CODE","BUSINESS_CODE", bundleSMEM,request);
			formError.mandatoryDate(subformId,"APPROVAL_DATE","APPROVAL_DATE", bundleSMEM.getApprovalDate(),request);
			formError.mandatory(subformId,"APPROVAL_LIMIT","APPROVAL_LIMIT", bundleSMEM,request);
//			formError.mandatory(subformId,"BORROWING_RATIO","BORROWING_RATIO", bundleSMEM,request);
			formError.mandatory(subformId,"INDIVIDUAL_RATIO","INDIVIDUAL_RATIO", bundleSMEM,request);
			formError.mandatory(subformId,"CORPORATE_RATIO","CORPORATE_RATIO", bundleSMEM,request);
			formError.mandatory(subformId,"G_TOTAL_EXIST_PAYMENT","G_TOTAL_EXIST_PAYMENT", bundleSMEM,request);
			formError.mandatory(subformId,"G_TOTAL_NEW_PAY_REQ","G_TOTAL_NEW_PAY_REQ", bundleSMEM,request);
			formError.mandatory(subformId,"G_DSCR_REQ","G_DSCR_REQ", bundleSMEM,request);
//			formError.mandatory(subformId,"ACCOUNT_DATE","ACCOUNT_DATE", bundleSMEM.getAccountDate(),request);
//			formError.mandatory(subformId,"ACCOUNT_NAME","ACCOUNT_NAME", bundleSMEM.getAccountName(),request);
			formError.mandatory(subformId,"BUNDLE_ACCOUNT_NO", bundleSMEM,request);
			formError.mandatory(subformId,"TYPE_LIMIT", bundleSMEM,request);
			formError.mandatory(subformId,"VERIFIED_INCOME", bundleSMEM,request);
	
//			#rawi comment validate account no for bundle on screen
//			if(!Util.empty(bundleSMEM.getAccountNo())){
//				FormUtil formUtil = new FormUtil(subformId, request);
//				String mode = FormDisplayModeUtil.getDisplayMode("BUNDLE_ACCOUNT_NO", bundleSMEM.getAccountNo(), formUtil);
//				ValidateBundleAccountNo bundleSmeAjax = new ValidateBundleAccountNo();	
//				String  errorTextPropertiesName = bundleSmeAjax.getErrorAccountNo(request);
//				logger.debug("errorTextPropertiesName >> "+errorTextPropertiesName);
//				if(!Util.empty(errorTextPropertiesName) && HtmlUtil.EDIT.equals(mode)){
//					HashMap<String, String> hErrorData = new Gson().fromJson(response.getData(), HashMap.class);
//					String errorTextPropertiesName = hErrorData.get(BundleSMEPopUpAccountNo.ERROR);
//					formError.error("BUNDLE_ACCOUNT_NO", MessageErrorUtil.getText(request,errorTextPropertiesName));
//				}
//			}
			
		}
		if(Util.empty(formError.getFormError())) {
			//Check source e-app for ignore compare field
/*			if(ApplicationUtil.eApp(source)) {
				bundleSMEM.setCompareFlag(MConstant.FLAG.YES);
			}else{*/
				String roleId = FormControl.getFormRoleId(request);
				if(MConstant.ROLE.DV.equals(roleId) && !IncomeTypeUtility.isBundleSMEComplete(bundleSMEM, roleId)) {
					formError.addAll(IncomeTypeUtility.executeBundleSMECompareScreen(request, bundleSMEM));
					if(Util.empty(formError.getFormError())) {
						bundleSMEM.setCompareFlag(MConstant.FLAG.YES);
					} else {
						bundleSMEM.setCompareFlag(MConstant.FLAG.WRONG);
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
