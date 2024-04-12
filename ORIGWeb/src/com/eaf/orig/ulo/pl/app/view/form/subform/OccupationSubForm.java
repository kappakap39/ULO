package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.math.BigDecimal;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
//import com.eaf.orig.shared.utility.OrigBusinessClassUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;

public class OccupationSubForm extends ORIGSubForm {
	
	Logger logger = Logger.getLogger(OccupationSubForm.class);
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
		PLOrigFormHandler origFrom = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		PLApplicationDataM applicationM = origFrom.getAppForm();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		/* Box 1 Present work*/
		String occupation = request.getParameter("occ_occupation");
		String occupation_type = request.getParameter("occ_occupation_type");
		
//		#septem comment change to main_applicant subform
//		String workplaceTitle = request.getParameter("occ_workplace_Title");		
//		String workplacename = request.getParameter("occ_name_th");
		
//		String department = request.getParameter("occ_department");
		
		String position = request.getParameter("occ_position");
		String service_years = request.getParameter("occ_service_years");
		String service_month = request.getParameter("occ_service_month");
		String position_level = request.getParameter("occ_position_level");
		String available_time = request.getParameter("occ_available_time");
		String business_group = request.getParameter("occ_business_group");
		String business_type = request.getParameter("occ_business_type");
		String other_business_type = request.getParameter("occ_other_business_type");
		String type_of_company = request.getParameter("occ_type_of_company");
		String size_of_company = request.getParameter("occ_size_of_company");
		
		/*Box 2 old workplace
		 * */
		String old_workplace_title = request.getParameter("occ_old_workplace_Title");
		String old_workplace = request.getParameter("occ_old_workplacename");
		String old_workplace_tel = request.getParameter("occ_old_workplace_tel");
		String old_service_years = request.getParameter("occ_old_service_years");
		String old_service_month = request.getParameter("occ_old_service_month");
		
		/*String company_type = request.getParameter("occ_company_type");
		String company_size = request.getParameter("occ_company_size");
		String emp_status = request.getParameter("occ_emp_status");*/
		

		/*Box 3 Salary*/
		
		String radio = request.getParameter("applicant_radio");
		String salary_month = request.getParameter("occ_salary_month");
		String business_turnover = request.getParameter("occ_business_turnover");
		String business_net_profit = request.getParameter("occ_business_net_profit");
		String savingIncome = request.getParameter("occ_savings");
		String other_income = request.getParameter("occ_other_income");
		String source_other_income = request.getParameter("occ_source_other_income");
		String other_income_per_month =request.getParameter("occ_other_income_per_month");
		String other_income_text = request.getParameter("occ_other_income_text");
		
		/*Box 4 Process Income*/
		String process_income = request.getParameter("occ_process_income");
		String bank_name = request.getParameter("occ_bank_name");
		String expenditure_month = request.getParameter("occ_expenditure_month");
		
		/*Box 5 Wealth*/
		String wealth_k_bank = request.getParameter("occ_wealth_bank");
		String wealth_ext_bank = request.getParameter("occ_wealth_ext_bank");
		String summary_wealth= request.getParameter("occ_summary_wealth");
		String spFlag = request.getParameter("occ_sp_flag");
		
		//#Vikrom 20121123
		String assetAmount = request.getParameter("asset_amount");
		String otherIncome2 = request.getParameter("occ_other_income_per_month2");
		String Profession_Other = request.getParameter("occ_occupation_type_text");
		String BusTypeOther = request.getParameter("occ_business_type_text");
		String employmentStatus = request.getParameter("occ_emp_style");
		
		String solo_view = request.getParameter("solo_view");
		String lec = request.getParameter("lec");				
		
		/*Set Box1*/
		personalM.setOccupation(occupation);
		personalM.setProfession(occupation_type);
		
//		personalM.setCompanyTitle(workplaceTitle);
//		personalM.setCompanyName(workplacename);
		
//		personalM.setDepartment(department);
		
		personalM.setPositionDesc(position);
		personalM.setCurrentWorkYear(DataFormatUtility.StringToInt(service_years));
		personalM.setCurrentWorkMonth(DataFormatUtility.StringToInt(service_month));
		personalM.setPositionLevel(position_level);
		personalM.setContactTime(available_time);
		personalM.setBusNature(business_group);
		personalM.setBusinessType(business_type);
		personalM.setOther_business_type(other_business_type);
		personalM.setCompanyType(type_of_company);
		personalM.setBusinessSize(size_of_company);
		
		/*Set Box 2*/
		personalM.setPrevCompanyTitle(old_workplace_title);
		personalM.setPrevCompany(old_workplace);
		personalM.setPrevCompanyPhoneNo(old_workplace_tel);
		personalM.setPrevWorkYear(DataFormatUtility.StringToInt(old_service_years));
		personalM.setPrevWorkMonth(DataFormatUtility.StringToInt(old_service_month));
		
		/*Set Box 3*/
		//plpersonal set radio
		personalM.setApplicationIncomeType(radio);
		personalM.setSalary(DataFormatUtility.replaceCommaForBigDecimal(salary_month));
		personalM.setCirculationIncome(DataFormatUtility.StringToBigDecimal(business_turnover));
		personalM.setNetProfitIncome(DataFormatUtility.StringToBigDecimal(business_net_profit));
		personalM.setSavingIncome(DataFormatUtility.replaceCommaForBigDecimal(savingIncome));
		personalM.setOtherIncome(DataFormatUtility.replaceCommaForBigDecimal(other_income));
		personalM.setFreelanceIncome(DataFormatUtility.replaceCommaForBigDecimal(other_income_per_month));
		personalM.setSourceOfIncome(source_other_income);
		personalM.setSourceOfOtherIncome(other_income_text);
		
		/*Set Box 4*/
		personalM.setReceiveIncomeMethod(process_income);
		personalM.setReceiveIncomeBank(bank_name);
		personalM.setMonthlyIncome(DataFormatUtility.replaceCommaForBigDecimal(expenditure_month));
		
		/*Set Box 5*/
		personalM.setWealthKBank(DataFormatUtility.replaceCommaForBigDecimal(wealth_k_bank));
		personalM.setWealthNonKBank(DataFormatUtility.replaceCommaForBigDecimal(wealth_ext_bank));
		
//		BigDecimal wealth_kbank = DataFormatUtility.replaceCommaForBigDecimal(wealth_k_bank);
//		BigDecimal wealth_nonbank =	DataFormatUtility.replaceCommaForBigDecimal(wealth_ext_bank);
//		BigDecimal summary_wealth = wealth_kbank.add(wealth_nonbank);
		personalM.setSummaryWealth(DataFormatUtility.replaceCommaForBigDecimal(summary_wealth));
		
		//#Vikrom 20121123
		personalM.setAssetAmount(DataFormatUtility.replaceCommaForBigDecimal(assetAmount));
		personalM.setOtherIncome(DataFormatUtility.replaceCommaForBigDecimal(otherIncome2));
		personalM.setProfessionOther(Profession_Other);
		personalM.setBusTypeOther(BusTypeOther);
		personalM.setEmploymentStatus(employmentStatus);
				
		personalM.setSpFlag(spFlag);
		
		personalM.setSoloView(solo_view);
		personalM.setLec(lec);
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request){		
		boolean result = false;
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);		
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		
		
		if(PLXrulesConstant.ButtonID.BUTTON_7002.equals(formHandler.getSaveType())
			|| PLXrulesConstant.ButtonID.BUTTON_7001.equals(formHandler.getSaveType())) {
			return false;
		}
		
		String decision = request.getParameter("decision_option");
//		logger.debug("decision_option = "+decision);
		
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		String currentRole = userM.getCurrentRole();
		String errorMsg="";
		
		if(OrigConstant.ROLE_DF_REJECT.equalsIgnoreCase(currentRole) ||
				OrigConstant.Mandatory.MANDATORY_TYPE_DRAFT == formHandler.getMandatoryType() ||
					OrigConstant.ROLE_FU.equalsIgnoreCase(currentRole) 
						|| OrigConstant.Mandatory.MANDATORY_TYPE_SEND_BACK == formHandler.getMandatoryType()){
			return false;
		}
		
//		#septemwi modify 
//		Mandatory when choose occ_occupation_type = Other.		
//		String occ_occupation_type = request.getParameter("occ_occupation_type");
//		String occ_occupation_type_text = request.getParameter("occ_occupation_type_text");		
//		if("18".equals(occ_occupation_type) && OrigUtil.isEmptyString(occ_occupation_type_text)){
//			errorMsg = MessageResourceUtil.getTextDescription(request, "WARNING_OCCUPATION_TYPE");
//			formHandler.PushErrorMessage("occ_occupation_type_text", errorMsg);
//		}
		
//		Mandatory when choose occ_business_type = Other.
		String occ_business_type = request.getParameter("occ_business_type");
		String occ_business_type_text = request.getParameter("occ_business_type_text");		
		if("25".equals(occ_business_type) && OrigUtil.isEmptyString(occ_business_type_text)){
			errorMsg = MessageResourceUtil.getTextDescription(request, "WARNING_BUSINESS_TYPE_OTHER");
			formHandler.PushErrorMessage("occ_business_type_text", errorMsg);
		}
		
//		Mandatory when choose applicant_radio not used.
		String applicant_radio = request.getParameter("applicant_radio");
		if(!OrigUtil.isEmptyString(applicant_radio)){
			if("01".equalsIgnoreCase(applicant_radio)){
				String occ_salary_month = request.getParameter("occ_salary_month");
				if(OrigUtil.isEmptyString(occ_salary_month) || occ_salary_month.equalsIgnoreCase("0.00")){
					errorMsg = MessageResourceUtil.getTextDescription(request, "WARNING_SALARY");
		           	formHandler.PushErrorMessage("occ_salary_month", errorMsg);
		           	result = true;
				}      	
			}else if("02".equalsIgnoreCase(applicant_radio)){
				String occ_business_turnover = request.getParameter("occ_business_turnover");
				String occ_business_net_profit = request.getParameter("occ_business_net_profit");
				if(OrigUtil.isEmptyString(occ_business_turnover) || occ_business_turnover.equalsIgnoreCase("0.00")){
					errorMsg = MessageResourceUtil.getTextDescription(request, "WARNING_BUSINESS_TURNOVER");
		           	formHandler.PushErrorMessage("occ_business_turnover", errorMsg);
					errorMsg = MessageResourceUtil.getTextDescription(request, "WARNING_BUSINESS_NET_PROFIT");
		           	formHandler.PushErrorMessage("occ_business_net_profit", errorMsg);
		           	result = true;
				}else if(OrigUtil.isEmptyString(occ_business_net_profit) || occ_business_net_profit.equalsIgnoreCase("0.00")){
					errorMsg = MessageResourceUtil.getTextDescription(request, "WARNING_BUSINESS_NET_PROFIT");
		           	formHandler.PushErrorMessage("occ_business_net_profit", errorMsg);
		           	result = true;
				}
			}else if("03".equalsIgnoreCase(applicant_radio)){
				String occ_other_income_per_month = request.getParameter("occ_other_income_per_month");
				if(OrigUtil.isEmptyString(occ_other_income_per_month) || occ_other_income_per_month.equalsIgnoreCase("0.00")){
					errorMsg = MessageResourceUtil.getTextDescription(request, "WARNING_OTHER_INCOME_PER_MONTH");
		           	formHandler.PushErrorMessage("occ_other_income_per_month", errorMsg);
		           	result = true;
				}
			}else if("04".equalsIgnoreCase(applicant_radio)){
				String occ_savings = request.getParameter("occ_savings");
				if(OrigUtil.isEmptyString(occ_savings) || occ_savings.equalsIgnoreCase("0.00")){
					errorMsg = MessageResourceUtil.getTextDescription(request, "WARNING_SAVINGS");
		           	formHandler.PushErrorMessage("occ_savings", errorMsg);
		           	result = true;
				}
			}
		}
		
//		Mandatory when choose source of salary = Other.
		String occ_source_other_income = request.getParameter("occ_source_other_income");
		String occ_other_income_text = request.getParameter("occ_other_income_text");
		if("04".equals(occ_source_other_income) && OrigUtil.isEmptyString(occ_other_income_text)){
			errorMsg = MessageResourceUtil.getTextDescription(request, "WARNING_OCCUPATION_SALARY_INCOME");
			formHandler.PushErrorMessage("occ_other_income_text", errorMsg);
			result = true;
		}
		
		if (OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType())
				&& (OrigConstant.wfProcessState.SEND.equals(decision)
						|| OrigConstant.wfProcessState.SENDX.equals(decision))
			){
			if(ORIGLogic.MandatoryYearOfService(applicationM.getBusinessClassId(), currentRole)){
				String occ_service_years = request.getParameter("occ_service_years");
				String occ_service_month = request.getParameter("occ_service_month");
				if(OrigUtil.isEmptyString(occ_service_years) && OrigUtil.isEmptyString(occ_service_month)){
					errorMsg = MessageResourceUtil.getTextDescription(request, "CHECK_WORK_ALERT");
					formHandler.PushErrorMessage("occ_service_years", errorMsg);
					formHandler.PushErrorMessage("occ_service_month", "");
					result = true;
				}else{
					if(DataFormatUtility.StringToInt(occ_service_years) == 0 && DataFormatUtility.StringToInt(occ_service_month) == 0){
						errorMsg = MessageResourceUtil.getTextDescription(request, "CHECK_WORK_ALERT");
						formHandler.PushErrorMessage("occ_service_years", errorMsg);
						formHandler.PushErrorMessage("occ_service_month", "");
						result = true;
					}
				}
			}
		}
		
//		Mandatory when key working time.
		if (OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType())
				&& ((OrigConstant.ROLE_DC.equals(currentRole) && OrigConstant.wfProcessState.SEND.equals(decision)) 
						|| !OrigConstant.ROLE_DC.equals(currentRole))){
		
//			#SeptemWi Comment
//			String occ_service_years = request.getParameter("occ_service_years");
//			String occ_service_month = request.getParameter("occ_service_month");
//			boolean yearsFlag = ORIGUtility.isEmptyString(occ_service_years);
//			boolean monthFlag = ORIGUtility.isEmptyString(occ_service_month);
//			int work_year=0;
//			int work_month=0;
//			
//			if(!yearsFlag){
//				work_year = DataFormatUtility.StringToInt(occ_service_years);
//			}	
//			if(!monthFlag){
//				work_month = DataFormatUtility.StringToInt(occ_service_month);
//			}
//			
//			if(work_year==0 && work_month==0){
//				errorMsg = PLMessageResourceUtil.getTextDescription(request, "CHECK_WORK_ALERT");
//				formHandler.PushErrorMessage("occ_service_years", errorMsg);
//				formHandler.PushErrorMessage("occ_service_month", "");			
//				result = true;
//			}
			
			String occ_other_income_per_month2 = request.getParameter("occ_other_income_per_month2");
			String occ_source_other = request.getParameter("occ_source_other_income");
			BigDecimal occ_other_income_per_month2Val = DataFormatUtility.replaceCommaForBigDecimal(occ_other_income_per_month2);
			BigDecimal value = new BigDecimal("0.00");
			
			if(occ_other_income_per_month2Val.compareTo(value) > 0 && OrigUtil.isEmptyString(occ_source_other)){
				errorMsg = MessageResourceUtil.getTextDescription(request, "OCC_INCOME_TYPE");
				formHandler.PushErrorMessage("occ_source_other_income", errorMsg);
				result = true;
			}
			
//			#septemwi change to ManualMandatory
//			String spFlag = request.getParameter("occ_sp_flag");
//			OrigBusinessClassUtil busclass = new OrigBusinessClassUtil();			
//			if(!busclass.isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW)){
//				if(OrigConstant.ROLE_DC.equals(currentRole) || OrigConstant.ROLE_DC_SUP.equals(currentRole) ||
//						OrigConstant.ROLE_VC.equals(currentRole) || OrigConstant.ROLE_VC_SUP.equals(currentRole)){
//					if(OrigUtil.isEmptyString(spFlag)){
//						errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_SP_FLAG");
//						formHandler.PushErrorMessage("occ_sp_flag", errorMsg);
//						result = true;
//					}
//				}
//			}
			
		}
		
		return result;
		
	}

}
