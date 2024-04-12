package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
//import com.eaf.orig.ulo.pl.model.app.personal.PLPersonalInfoDataM;

public class ProductFutureSubForm extends ORIGSubForm {

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PLOrigFormHandler plorigform = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		PLApplicationDataM plappdataM = plorigform.getAppForm();
		PLPersonalInfoDataM plpersonalinfodatam = plappdataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		String car_owner = request.getParameter("p_car_owner");
		String end_pay_month = request.getParameter("p_payment_month");
		String end_pay_year = request.getParameter("p_payment_year");
		String payment_with = request.getParameter("p_payment_with");
		String payment_amount = request.getParameter("p_payment_amount");
		String car_type = request.getParameter("p_car_type");
		String insurance_end = request.getParameter("p_month_insurance_end");
		String insurance = request.getParameter("p_insurance");
		
		plpersonalinfodatam.setCarOwner(car_owner);
		plpersonalinfodatam.setCarLastInst_month(DataFormatUtility.StringToInt(end_pay_month));
		plpersonalinfodatam.setCarLastInst_year(DataFormatUtility.StringToInt(end_pay_year));
		plpersonalinfodatam.setCarLoanWith(payment_with);
		plpersonalinfodatam.setCarFinancialAmount(DataFormatUtility.StringToBigDecimal(payment_amount));
		plpersonalinfodatam.setCarType(car_type);
		plpersonalinfodatam.setCarInsureExpMonth(DataFormatUtility.StringToInt(insurance_end));
		plpersonalinfodatam.setInsurance(insurance);
		
		
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
