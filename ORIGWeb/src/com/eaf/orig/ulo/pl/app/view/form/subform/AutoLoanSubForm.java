package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLBundleALDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesExistCustDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class AutoLoanSubForm extends ORIGSubForm {
	static Logger logger = Logger.getLogger(AutoLoanSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {

		PLOrigFormHandler origForm = (PLOrigFormHandler) request.getSession().getAttribute("PLORIGForm");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		PLApplicationDataM applicationM = origForm.getAppForm();
		if (OrigUtil.isEmptyObject(applicationM))
			applicationM = new PLApplicationDataM();

		PLBundleALDataM bundleALM = applicationM.getBundleALM();
		if (OrigUtil.isEmptyObject(bundleALM)){
			bundleALM = new PLBundleALDataM();
			bundleALM.setAppRecId(applicationM.getAppRecordID());
			applicationM.setBundleALM(bundleALM);
		}

		String car_brand = request.getParameter("car_brand");
		String car_series = request.getParameter("car_series");
		String car_contact_no = request.getParameter("car_contact_no");
		String buyer_true_salary = request.getParameter("buyer_true_salary");
		String eveluate_salary = request.getParameter("eveluate_salary");
		String car_fico_score = request.getParameter("car_fico_score");
		String debt_burden_score = request.getParameter("debt_burden_score");
		String percent_down_payment = request.getParameter("percent_down_payment");
		String loan_car_cash = request.getParameter("loan_car_cash");
		String payment_per_month = request.getParameter("payment_per_month");
		String permit_date = request.getParameter("permit_date");
		String officer_contact_name = request.getParameter("officer_contact_name");
		String officer_credit_limit = request.getParameter("officer_credit_limit");
		String kec_cash_payment = request.getParameter("kec_cash_payment");
		
		try {
			bundleALM.setCarType(car_brand);
			bundleALM.setCarModel(car_series);
			bundleALM.setContractNo(car_contact_no);
			if(!OrigUtil.isEmptyString(buyer_true_salary)){
				bundleALM.setAppSalary(DataFormatUtility.StringToBigDecimal(buyer_true_salary));
			}else{
				bundleALM.setAppSalary(new BigDecimal(0));
			}
			if(!OrigUtil.isEmptyString(eveluate_salary)) {
				bundleALM.setEstSalary(DataFormatUtility.StringToBigDecimal(eveluate_salary));
			}else{
				bundleALM.setEstSalary(new BigDecimal(0));
			}
			
			bundleALM.setFicoScore(car_fico_score);
			bundleALM.setDebtBurdenScore(debt_burden_score);
			
			if(!OrigUtil.isEmptyString(percent_down_payment)){
				bundleALM.setDownPayment(DataFormatUtility.StringToBigDecimal(percent_down_payment));
			}else{
				bundleALM.setDownPayment(new BigDecimal(0));
			}
			if(!OrigUtil.isEmptyString(loan_car_cash)){
				bundleALM.setHpAmount(DataFormatUtility.StringToBigDecimal(loan_car_cash));
			}else{
				bundleALM.setHpAmount(new BigDecimal(0));
			}
			if(!OrigUtil.isEmptyString(payment_per_month)){
				bundleALM.setMonthlyInstallment(DataFormatUtility.StringToBigDecimal(payment_per_month));
			} else {
				bundleALM.setMonthlyInstallment(new BigDecimal(0));
			}
			if(!OrigUtil.isEmptyString(permit_date)){
				bundleALM.setApproveDate(DataFormatUtility.StringThToDateEn(permit_date, DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S));
			}
			bundleALM.setContractCreateBy(officer_contact_name);
			bundleALM.setCreditApproveBy(officer_credit_limit);
			if (!OrigUtil.isEmptyString(kec_cash_payment)) {
				bundleALM.setApproveCreditLine(DataFormatUtility.StringToBigDecimal(kec_cash_payment));
			} else {
				bundleALM.setApproveCreditLine(new BigDecimal(0));
			}

			if(null==bundleALM.getCreateBy()){
				bundleALM.setCreateBy(userM.getUserName());
			}
			bundleALM.setUpdateDate( new Timestamp(new Date().getTime()));
			//Sankom Set ICDC FLAG
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVer = personalM.getXrulesVerification();
			if (null == xrulesVer) {
				xrulesVer = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVer);
			}
			PLXRulesExistCustDataM xrulesExistCustM = xrulesVer.getxRulesExistCustM();
			if(bundleALM.getApproveCreditLine()!=null && xrulesExistCustM!=null
					&& xrulesExistCustM.getLastTemporaryCreditLimitAmount()!=null){
				if(bundleALM.getApproveCreditLine().compareTo(xrulesExistCustM.getLastTemporaryCreditLimitAmount())==1){
					applicationM.setICDCFlag(OrigConstant.ORIG_FLAG_Y);
				}				
			}
			//END set ICDC			
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		boolean result = false;
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		/** New Logic Manual Validate Subform #Sankom */

		String currentRole = userM.getCurrentRole();
		String errorMsg = "";
        BigDecimal zero=new BigDecimal(0);
		/** Add Condition Not Button Execute1 and Execute2 #SeptemWi */         
		if (OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType())
				&&!OrigConstant.ROLE_DF_REJECT.equalsIgnoreCase(currentRole) &&
					(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType())){
			String carBrand = request.getParameter("car_brand");
			if (carBrand == null && "".equals(carBrand)) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_CAR_BRAND");
				formHandler.PushErrorMessage("car_brand", errorMsg);
				result = true;
			}

			// ==========================================================
			String car_series = request.getParameter("car_series");
			if (car_series == null || "".equals(car_series)) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_CAR_SERIES");
				formHandler.PushErrorMessage("car_series", errorMsg);
				result = true;
			}
			// ==========================================================
			String car_contact_no = request.getParameter("car_contact_no");
			if (car_contact_no == null || "".equals(car_contact_no)) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_CAR_CONTRACT_NO");
				formHandler.PushErrorMessage("car_contact_no", errorMsg);
				result = true;
			}
			// ==========================================================
			BigDecimal buyer_true_salary = null;
			try {
				buyer_true_salary = DataFormatUtility.StringToBigDecimal( request.getParameter("buyer_true_salary"));
			} catch (Exception e) {				 
				logger.error("Error,",e);
			}
			
			if (buyer_true_salary == null || zero.compareTo(buyer_true_salary)==0) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_CAR_TRUE_SALARY");
				formHandler.PushErrorMessage("buyer_true_salary", errorMsg);
				result = true;
			}
			// ==========================================================
			BigDecimal eveluate_salary = null;
			try {
				eveluate_salary = DataFormatUtility.StringToBigDecimal(request.getParameter("eveluate_salary"));
			} catch (Exception e) {
				 logger.error("Error",e);
			}
			if (eveluate_salary == null ||zero.compareTo(eveluate_salary)==0) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_EVELUATE_SALARY");
				formHandler.PushErrorMessage("eveluate_salary", errorMsg);
				result = true;
			}
			// ==========================================================
			BigDecimal car_fico_score = null;
			try {
				car_fico_score = DataFormatUtility.StringToBigDecimal(request.getParameter("car_fico_score"));
			} catch (Exception e) {
				 logger.error("error",e);
			}
			/*if (car_fico_score == null || zero.compareTo(car_fico_score)==0) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_CAR_FICO_SCORE");
				formHandler.PushErrorMessage("car_fico_score", errorMsg);
				result = true;
			}*/
			// ==========================================================
			BigDecimal debt_burden_score = null;
			try {
				debt_burden_score = DataFormatUtility.StringToBigDecimal(request.getParameter("debt_burden_score"));
			} catch (Exception e) {
				logger.error("error",e);
			}
			
			/*if (debt_burden_score == null ||zero.compareTo(debt_burden_score)==0) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_DEBT_BURDEN_SCORE");
				formHandler.PushErrorMessage("debt_burden_score", errorMsg);
				result = true;
			}*/
			// ==========================================================
			BigDecimal percent_down_payment = null;
			try {
				percent_down_payment = DataFormatUtility.StringToBigDecimal(request.getParameter("percent_down_payment"));
			} catch (Exception e) {
				logger.error("error",e);
			}
			
			if (percent_down_payment == null || zero.compareTo(percent_down_payment)==0) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_PERCENT_DOWN_PAYMENT");
				formHandler.PushErrorMessage("percent_down_payment", errorMsg);
				result = true;
			}
			// ==========================================================
			BigDecimal loan_car_cash = null;
			try {
				loan_car_cash = DataFormatUtility.StringToBigDecimal(request.getParameter("loan_car_cash"));
			} catch (Exception e) {
				logger.error("error",e);
			}
			
			if (loan_car_cash == null || zero.compareTo(loan_car_cash)==0) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_LOAN_CAR_CASH");
				formHandler.PushErrorMessage("loan_car_cash", errorMsg);
				result = true;
			}
			
			// ==========================================================
			BigDecimal payment_per_month = null;
			try {
				payment_per_month = DataFormatUtility.StringToBigDecimal(request.getParameter("payment_per_month"));
			} catch (Exception e) {
				logger.error("error",e);
			}
			if (payment_per_month == null || zero.compareTo(payment_per_month)==0) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_PAYMENT_PER_MONTH");
				formHandler.PushErrorMessage("payment_per_month", errorMsg);
				result = true;
			}
			// ==========================================================
			
			String permit_date = request.getParameter("permit_date");
			if (permit_date == null || "".equals(permit_date)) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_PERMIT_DATE");
				formHandler.PushErrorMessage("permit_date", errorMsg);
				result = true;
			}
			// ==========================================================
			
			String officer_contact_name = request.getParameter("officer_contact_name");
			if (officer_contact_name == null || "".equals(officer_contact_name)) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_OFFICER_CONTACT_NAME");
				formHandler.PushErrorMessage("officer_contact_name", errorMsg);
				result = true;
			}
			// ==========================================================
			String officer_credit_limit = request.getParameter("officer_credit_limit");
			if (officer_credit_limit == null || "".equals(officer_credit_limit)) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_OFFICER_CREDIT_LIMIT");
				formHandler.PushErrorMessage("officer_credit_limit", errorMsg);
				result = true;
			}
			// ==========================================================
			
//			#septemwi modify
//			BigDecimal kec_cash_payment = null;
//			try {
//				kec_cash_payment = DataFormatUtility.StringToBigDecimal(request.getParameter("kec_cash_payment"));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//			if (kec_cash_payment == null || zero.compareTo(kec_cash_payment) == 0) {
//				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_KEC_CASH_PAYMENT");
//				formHandler.PushErrorMessage("kec_cash_payment", errorMsg);
//				result = true;
//			}
//			// ==========================================================
						
			BigDecimal kecCashPayment = DataFormatUtility.StringToBigDecimal(request.getParameter("kec_cash_payment"));
			if(null == kecCashPayment){
				errorMsg = ErrorUtil.getShortErrorMessage(request, "AUTO_LOAN_KEC_CASH_PAYMENT");
				formHandler.PushErrorMessage("kec_cash_payment", errorMsg);
				result = true;
			}	
			
			
		}
		return result;
	} 

}
