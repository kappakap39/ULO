package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class MainApplicantSubForm extends ORIGSubForm {
	
	Logger logger = Logger.getLogger(MainApplicantSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
		PLOrigFormHandler plorigform = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		PLApplicationDataM applicationM = plorigform.getAppForm();
		
		if(applicationM == null){
			applicationM = new PLApplicationDataM();
		}
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		String customerType = request.getParameter("customertype");
//		String cisID = request.getParameter("cis");
		String cardtype = request.getParameter("cardtype");
		String id_no = request.getParameter("identification_no");
		String card_expire_date = request.getParameter("card_expire_date");
		String titleThai = request.getParameter("title_thai");
		String name_th = request.getParameter("name_th");
		String surname_th = request.getParameter("surname_th");
		String middlename_th = request.getParameter("middlename_th");
		String old_surname_th = request.getParameter("old_surname_th");
		String title_eng = request.getParameter("title_eng");
		String name_eng = request.getParameter("name_eng");
		String surname_eng = request.getParameter("surname_eng");
		String middlename_eng = request.getParameter("middlename_eng");
		String birth_date = request.getParameter("birth_date");
		String age = request.getParameter("age_desc");
		String gender = request.getParameter("gender");
		String education = request.getParameter("education");
		String nationality = request.getParameter("nationality");
		String race = request.getParameter("race");
		String marriage_status = request.getParameter("marriage_status");
		String no_of_children = request.getParameter("no_of_children");
		String email = request.getParameter("email1");
		String sub_email = request.getParameter("sub_email");
		String loanObjective = request.getParameter("loan_objective");
		String loanObjective_Desc = request.getParameter("other_loan_objective");
		String main_workplace_Title = request.getParameter("main_workplace_Title");
		String occ_name_th = request.getParameter("occ_name_th");
		
		personalM.setCustomerType(customerType);
		personalM.setCidType(cardtype);
		personalM.setThaiTitleName(titleThai);
		personalM.setThaiFirstName(name_th);
		personalM.setThaiLastName(surname_th);
		personalM.setThaiMidName(middlename_th);
		personalM.setThaiOldLastName(old_surname_th);
		personalM.setEngTitleName(title_eng);
		personalM.setEngFirstName(name_eng);
		personalM.setEngLastName(surname_eng);
		personalM.setEngMidName(middlename_eng);
		personalM.setGender(gender);
		personalM.setDegree(education);
		personalM.setNationality(nationality);
		personalM.setRace(race);
		personalM.setMaritalStatus(marriage_status);
		personalM.setEmail1(email);
		personalM.setEmail2(sub_email);
		personalM.setLoanObjective(loanObjective);
		try {
//			logger.debug("personalM.getLoanObjective()= "+personalM.getLoanObjective());
//			logger.debug("loanObjective_Desc= "+loanObjective_Desc);
			if(!ORIGUtility.isEmptyString(loanObjective_Desc) && personalM.getLoanObjective().equals("10")){
				personalM.setLoanObjectiveDesc(loanObjective_Desc);
			}
			
			if(!ORIGUtility.isEmptyString(id_no)){
				personalM.setIdNo(id_no);
			}
//			logger.debug("[setProperties] .. IdNo "+ personalM.getIdNo());
			if(!ORIGUtility.isEmptyString(age)){
				personalM.setAge(DataFormatUtility.StringToInt(age));
//				logger.debug("personalM.getAge"+personalM.getAge());
			}
			if(!ORIGUtility.isEmptyString(no_of_children)){
				personalM.setNoOfchild(DataFormatUtility.StringToInt(no_of_children));
			}
//			#septem comment
//			if(!ORIGUtility.isEmptyString(main_workplace_Title)){personalM.setCompanyTitle(main_workplace_Title);}
//			if(!ORIGUtility.isEmptyString(occ_name_th)){personalM.setCompanyName(occ_name_th);}
			
/***		Fix Error String Date to Date Format #SeptemWi*/
			personalM.setCidExpDate(DataFormatUtility.StringThToDateEn(card_expire_date, DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S));					
			personalM.setBirthDate(DataFormatUtility.stringToDate(birth_date, "dd/mm/yyyy"));
		} catch (Exception e) {
			logger.fatal("Error ",e);
		}
		
//		#septem set Company
		personalM.setCompanyTitle(main_workplace_Title);
		personalM.setCompanyName(occ_name_th);
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		String errorMsg="";
		if(OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType()) &&
				(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType())){//Praisan 20121218 validate only type submit (1)
			String loan_objective = request.getParameter("loan_objective");
			String other_loan_objective = request.getParameter("other_loan_objective");
			if(loan_objective.equalsIgnoreCase("10")&&OrigUtil.isEmptyString(other_loan_objective)){
				errorMsg = ErrorUtil.getShortErrorMessage(request,"LOAN_INPUT_MSG");
				formHandler.PushErrorMessage("other_loan_objective", errorMsg);
			}
			return true;
		}
		return false;
	}

}
