package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.math.BigDecimal;
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
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesSpecialCustomerDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SpecialCustomerSubForm extends ORIGSubForm {

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PLOrigFormHandler origForm = (PLOrigFormHandler) request.getSession().getAttribute("PLORIGForm");

		PLApplicationDataM applicationM = origForm.getAppForm();
		if (OrigUtil.isEmptyObject(applicationM)){
			applicationM = new PLApplicationDataM();
		}

		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if (OrigUtil.isEmptyObject(xrulesVerM)) {
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}

		PLXRulesSpecialCustomerDataM specialCusM = xrulesVerM.getXrulesSpecialCusM();
		if (OrigUtil.isEmptyObject(specialCusM)) {
			specialCusM = new PLXRulesSpecialCustomerDataM();
			xrulesVerM.setXrulesSpecialCusM(specialCusM);
		}

		String customer_group_name = request.getParameter("customer_group_name");
		String csa_risk_name = request.getParameter("csa_risk_name");
		String aum_avg_month = request.getParameter("aum_avg_month");
		String payroll_type = request.getParameter("payroll_type");
		String final_proxy_income = request.getParameter("final_proxy_income");
		String credit_card_line = request.getParameter("credit_card_line");
		String contact_type = request.getParameter("contact_type");
		String premium_on_top = request.getParameter("premium_on_top");

		specialCusM.setCustomerGroupName(customer_group_name);
		if(!OrigUtil.isEmptyString(csa_risk_name)){
			specialCusM.setCsaRiskGrade(DataFormatUtility.StringToBigDecimal(csa_risk_name));
		}else{
			specialCusM.setCsaRiskGrade(new BigDecimal(0));
		}
		if(!OrigUtil.isEmptyString(aum_avg_month)){
			specialCusM.setAumAve(DataFormatUtility.StringToBigDecimal(aum_avg_month));
		}else{
			specialCusM.setAumAve(new BigDecimal(0));
		}
		specialCusM.setPayrollType(payroll_type);
		if(!OrigUtil.isEmptyString(final_proxy_income)){
			specialCusM.setFinalProxyIncome(DataFormatUtility.StringToBigDecimal(final_proxy_income));
		}else{
			specialCusM.setFinalProxyIncome(new BigDecimal(0));
		}
		if(!OrigUtil.isEmptyString(credit_card_line)){
			specialCusM.setCreditCardCreditLine(DataFormatUtility.StringToBigDecimal(credit_card_line));
		}else{
			specialCusM.setCreditCardCreditLine(new BigDecimal(0));
		}
		specialCusM.setContractChannel(contact_type);
		specialCusM.setPremiumOnTop(premium_on_top);

	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		logger.debug("validateSubFormSpecial Customer ");
		boolean result = false;
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String currentRole = userM.getCurrentRole();
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		/** New Logic Manual Validate Subform #Sankom */
		String errorMsg = "";
		String decision = request.getParameter("decision_option");
		if (OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType())
				&& ((OrigConstant.ROLE_DC.equals(currentRole) && OrigConstant.wfProcessState.SEND.equals(decision)) || !OrigConstant.ROLE_DC
						.equals(currentRole))) {
			if((OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType())){ //Praisan 20121218 validate only type submit (1)
				PLApplicationDataM applicationM = formHandler.getAppForm();
				PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	
				PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
				if (OrigUtil.isEmptyObject(xrulesVerM)) {
					xrulesVerM = new PLXRulesVerificationResultDataM();
				}
	
				PLXRulesSpecialCustomerDataM specialCusM = xrulesVerM.getXrulesSpecialCusM();
				if (OrigUtil.isEmptyObject(specialCusM)) {
					specialCusM = new PLXRulesSpecialCustomerDataM();
				}
				String final_proxy_income = request.getParameter("final_proxy_income");
				if (!OrigUtil.isEmptyString(final_proxy_income)) {
					specialCusM.setFinalProxyIncome(DataFormatUtility.StringToBigDecimal(final_proxy_income));
				} else {
					specialCusM.setFinalProxyIncome(new BigDecimal(0));
				}
				
				if (specialCusM.getFinalProxyIncome() != null && specialCusM.getFinalProxyIncome().compareTo(new BigDecimal(0)) < 1) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "FINAL_PRROXY_INCOME");
					formHandler.PushErrorMessage("final_proxy_income", errorMsg);
					result = true;
				}
			}
		}
		return result;
	}

}
