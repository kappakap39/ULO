package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

@SuppressWarnings("serial")
public class ReKeySensitiveField extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(ReKeySensitiveField.class);
	private String PERSONAL_TYPE_APPLICANT =SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		//##DE 1.1
		String ID_NO = request.getParameter("ID_NO");
		String TH_FIRST_NAME = request.getParameter("TH_FIRST_NAME");
		String TH_LAST_NAME = request.getParameter("TH_LAST_NAME");
		String TH_BIRTH_DATE = request.getParameter("TH_BIRTH_DATE");
		
		//##DE 1.2
		String EN_FIRST_NAME = request.getParameter("EN_FIRST_NAME");// ENG_FIRST_LAST_NAME
		String EN_LAST_NAME = request.getParameter("EN_LAST_NAME");  //ENG_FIRST_LAST_NAME
		String MARRIED = request.getParameter("MARRIED");  //MARITAL_STATUS
		String DEGREE = request.getParameter("DEGREE");
		String ADDRESS_STYLE = request.getParameter("ADDRESS_STYLE");
		String CURRENT_ZIPCODE = request.getParameter("ZIPCODE");//CURRENT_ZIPCODE
		String HOME_PHONE = request.getParameter("PHONE1");//HOME_PHONE
		String COMPANY_NAME = request.getParameter("COMPANY_NAME");
		String COMPANY_ZIPCODE = request.getParameter("ZIPCODE");//COMPANY_ZIPCODE
		String SEND_DOC = request.getParameter("SEND_DOC");//SEND_DOC_ADDRESS
		String PLACE_RECEIVE_CARD = request.getParameter("PLACE_RECEIVE_CARD");//RECEIVE_CARD_PLACE
		String BRANCH_RECEIVE_CARD = request.getParameter("BRANCH_RECEIVE_CARD");//RECEIVE_CARD_PLACE
		String OCCUPATION = request.getParameter("OCCUPATION");
		String PROFESSION = request.getParameter("PROFESSION");//PROFESSION
		String PROFESSION_OTHER = request.getParameter("PROFESSION_OTHER");//PROFESSION
		String POSITION_CODE = request.getParameter("POSITION_CODE");//POSITION_CODE
		String POSITION_DESC = request.getParameter("POSITION_DESC");//POSITION_CODE
		String TOT_WORK_YEAR = request.getParameter("TOT_WORK_YEAR");//WORK_AGE
		String TOT_WORK_MONTH = request.getParameter("TOT_WORK_MONTH");//WORK_AGE
		String TOT_WORK_PLACE_YEAR = request.getParameter("TOT_WORK_YEAR");//WORK_PLACE_AGE
		String TOT_WORK_PLACE_MONTH = request.getParameter("TOT_WORK_MONTH");//WORK_PLACE_AGE
		String INCOME = request.getParameter("SALARY");
		String PAYMENT_METHOD = request.getParameter("PAYMENT_METHOD");
		
		String CREDIT_SHIELD = request.getParameter("CREDIT_SHIELD");
		String BUNDING_HL_INFO = request.getParameter("BUNDING_HL_INFO");
		String BUNDING_K_SME_INFO = request.getParameter("BUNDING_K_SME_INFO");
		String BUNDING_KL = request.getParameter("BUNDING_KL");
	
		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		AddressDataM currentAddress  = personalInfo.getAddress(ADDRESS_TYPE_CURRENT); 
		AddressDataM workAddress =   personalInfo.getAddress(ADDRESS_TYPE_WORK);
		
		PaymentMethodDataM payment = new PaymentMethodDataM();
//		ArrayList<ApplicationDataM> appMListM = applicationGroup.getApplications();
//		if(!Util.empty(appMListM)){
//			for(ApplicationDataM appM :  appMListM){
//				ArrayList<LoanDataM> loanListM =  appM.getLoans();
//			
//				if(!Util.empty(loanListM)){
//					for(LoanDataM loanM:loanListM){
//						payment=loanM.getPaymentMethod("");
//					}
//				}
//			}
//		}
		
		if(!Util.empty(ID_NO)){
			personalInfo.setIdno(ID_NO);
		}
		
		if(!Util.empty(TH_FIRST_NAME)){
			personalInfo.setThFirstName(TH_FIRST_NAME);
		}
		
		if(!Util.empty(TH_LAST_NAME)){
			personalInfo.setThLastName(TH_LAST_NAME);
		}
		
		if(!Util.empty(TH_BIRTH_DATE)){
			personalInfo.setBirthDate(FormatUtil.toDate(TH_BIRTH_DATE, FormatUtil.EN));
		}
		
		if(!Util.empty(EN_FIRST_NAME)){
			personalInfo.setEnFirstName(EN_FIRST_NAME);
		}
		
		if(!Util.empty(EN_LAST_NAME)){
			personalInfo.setEnLastName(EN_LAST_NAME);
		}
		
		if(!Util.empty(MARRIED)){
			personalInfo.setMarried(MARRIED);
		}
		
		if(!Util.empty(DEGREE)){
			personalInfo.setDegree(DEGREE);
		}
		
		if(!Util.empty(ADDRESS_STYLE)){
//			currentAddress.set
		}
		
		if(!Util.empty(CURRENT_ZIPCODE)){
			currentAddress.setZipcode(CURRENT_ZIPCODE);
		}
		
		if(!Util.empty(HOME_PHONE)){
			currentAddress.setPhone1(HOME_PHONE);
		}
		
		if(!Util.empty(COMPANY_NAME)){
			workAddress.setCompanyName(Util.removeNotAllowSpeacialCharactors(COMPANY_NAME.trim()));
		}
		
		if(!Util.empty(COMPANY_ZIPCODE)){
			workAddress.setZipcode(COMPANY_ZIPCODE);
		}
		
		if(!Util.empty(SEND_DOC)){
			personalInfo.setMailingAddress(SEND_DOC);
		}
		
		if(!Util.empty(PLACE_RECEIVE_CARD)){
			personalInfo.setPlaceReceiveCard(PLACE_RECEIVE_CARD);
		}
		
		if(!Util.empty(BRANCH_RECEIVE_CARD)){
			personalInfo.setBranchReceiveCard(BRANCH_RECEIVE_CARD);
		}
		
		if(!Util.empty(OCCUPATION)){
			personalInfo.setOccupation(OCCUPATION);
		}
		
		if(!Util.empty(PROFESSION)){
			personalInfo.setProfession(PROFESSION);
		}
		
		if(!Util.empty(PROFESSION_OTHER)){
			personalInfo.setProfessionOther(PROFESSION_OTHER);
		}
		
		if(!Util.empty(POSITION_CODE)){
			personalInfo.setPositionCode(POSITION_CODE);
		}
		
		if(!Util.empty(POSITION_DESC)){
			personalInfo.setPositionDesc(POSITION_DESC);
		}
		
		if(!Util.empty(TOT_WORK_YEAR)){
			personalInfo.setTotWorkYear(FormatUtil.toBigDecimal(TOT_WORK_YEAR));
		}
		
		if(!Util.empty(TOT_WORK_MONTH)){
			personalInfo.setTotWorkMonth(FormatUtil.toBigDecimal(TOT_WORK_MONTH));
		}
		
		if(!Util.empty(TOT_WORK_PLACE_YEAR)){
			personalInfo.setTotWorkMonth(FormatUtil.toBigDecimal(TOT_WORK_PLACE_YEAR));
		}
		
		if(!Util.empty(TOT_WORK_PLACE_MONTH)){
			personalInfo.setTotWorkMonth(FormatUtil.toBigDecimal(TOT_WORK_PLACE_MONTH));
		}
		
		if(!Util.empty(INCOME)){
			personalInfo.setmIncome(FormatUtil.toBigDecimal(INCOME));
		}
		
		if(!Util.empty(PAYMENT_METHOD)){
			payment.setPaymentMethod(PAYMENT_METHOD);
		}
		
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("=====validateForm===");
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		logger.debug("=====validateSubForm===");
		return false;
	}

}
