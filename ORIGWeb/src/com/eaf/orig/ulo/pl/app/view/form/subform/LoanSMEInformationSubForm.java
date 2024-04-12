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
import com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility.DateFormatType;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLBundleSMEDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesExistCustDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class LoanSMEInformationSubForm extends ORIGSubForm {
	static Logger logger = Logger.getLogger(LoanSMEInformationSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
		PLOrigFormHandler origForm = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		PLApplicationDataM applicationM = origForm.getAppForm();		
		if(OrigUtil.isEmptyObject(applicationM))  applicationM = new PLApplicationDataM();
		
		PLBundleSMEDataM bundleSMEM = applicationM.getBundleSMEM();
		if(OrigUtil.isEmptyObject(bundleSMEM)){
			bundleSMEM = new PLBundleSMEDataM();
			bundleSMEM.setAppRecId(applicationM.getAppRecordID());
			applicationM.setBundleSMEM(bundleSMEM);
		}
		
		String cash_define_date = request.getParameter("cash_define_date");
		String sme_number = request.getParameter("sme_number");
		String gross_income = request.getParameter("gross_income");
		String risk_grade = request.getParameter("risk_grade");
		String total_sme_limit = request.getParameter("total_sme_limit");
		String kec_permit_result = request.getParameter("kec_permit_result");
		String kec_permit_cash = request.getParameter("kec_permit_cash");
		
		try{
			if(!OrigUtil.isEmptyString(cash_define_date)){
				bundleSMEM.setSmeApproveDate(DataFormatUtility.StringThToDateEn(cash_define_date, DateFormatType.FORMAT_DDMMYYY_S));
			}
			
			bundleSMEM.setSmeAppNo(sme_number);
			if(!OrigUtil.isEmptyString(gross_income))bundleSMEM.setGrossIncome(DataFormatUtility.StringToBigDecimal(gross_income));
			if(!OrigUtil.isEmptyString(risk_grade))bundleSMEM.setRiskGrade(risk_grade);
			if(!OrigUtil.isEmptyString(total_sme_limit))bundleSMEM.setTotalSMELimit(DataFormatUtility.StringToBigDecimal(total_sme_limit));
			bundleSMEM.setApproveStatus(kec_permit_result);
			
			if(!OrigUtil.isEmptyString(kec_permit_cash))bundleSMEM.setApproveCreditLine(DataFormatUtility.StringToBigDecimal(kec_permit_cash));
			String userName = userM.getUserName();
			if(null==bundleSMEM.getCreateBy()){
				bundleSMEM.setCreateBy(userM.getUserName());
			}
			
			bundleSMEM.setUpdateBy(userName);
			bundleSMEM.setUpdateDate(new Timestamp((new Date()).getTime()));
			
			//Sankom Set ICDC FLAG
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVer = personalM.getXrulesVerification();
			if(null == xrulesVer){
				xrulesVer = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVer);
			}
			PLXRulesExistCustDataM xrulesExistCustM = xrulesVer.getxRulesExistCustM();
			if( bundleSMEM.getApproveCreditLine()!=null && xrulesExistCustM!=null 
					&& xrulesExistCustM.getLastTemporaryCreditLimitAmount()!=null){			
				if(bundleSMEM.getApproveCreditLine().compareTo(xrulesExistCustM.getLastTemporaryCreditLimitAmount())==1){
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
		try{
			PLOrigFormHandler formHandler = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
			UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
			String errorMsg="";
			String currentRole = userM.getCurrentRole();
			if(OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType()) 
					&&!OrigConstant.ROLE_DF_REJECT.equalsIgnoreCase(currentRole) &&
						(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType())){//Praisan 20121218 validate only type submit (1)
				String gross_income = request.getParameter("gross_income");
				String risk_grade = request.getParameter("risk_grade");
				String total_sme_limit = request.getParameter("total_sme_limit");
				
//				#septemwi modify field kec_permit_cash mandatory
//				String kec_permit_cash = request.getParameter("kec_permit_cash");
//				BigDecimal kec_permit_cash_big = new BigDecimal(0.0);			
//				if(!OrigUtil.isEmptyString(kec_permit_cash)){
//					kec_permit_cash_big = DataFormatUtility.StringToBigDecimal(kec_permit_cash);
//				}				
//				if(OrigUtil.isEmptyString(kec_permit_cash)||kec_permit_cash_big.compareTo(new BigDecimal(0.0))<=0){
//					errorMsg = PLMessageResourceUtil.getTextDescription(request, "LOAN_SME_WARNING");
//					formHandler.PushErrorMessage("kec_permit_cash", errorMsg);
//				}				
				
				String kec_permit_result = request.getParameter("kec_permit_result");			
				BigDecimal kecPermitCash = DataFormatUtility.StringToBigDecimal(request.getParameter("kec_permit_cash"));
				if ("Pass".equals(kec_permit_result) && kecPermitCash.compareTo(BigDecimal.ZERO) <= 0) {
					errorMsg = ErrorUtil.getShortErrorMessage(request, "LOAN_SME_WARNING");
					formHandler.PushErrorMessage("kec_permit_cash", errorMsg);
				}	
				
				BigDecimal gross_income_big = new BigDecimal(0.0);			
				if(!OrigUtil.isEmptyString(gross_income)){gross_income_big = DataFormatUtility.StringToBigDecimal(gross_income);}
				
				if(OrigUtil.isEmptyString(gross_income)||gross_income_big.compareTo(new BigDecimal(0.0))<=0){
					errorMsg = PLMessageResourceUtil.getTextDescription(request, "GROSS_INCOME_WARNING");
					formHandler.PushErrorMessage("gross_income", errorMsg);
				}
				
//				#septemwi comment change to validate String 
//				BigDecimal risk_grade_big = new BigDecimal(0.0);			
//				if(!OrigUtil.isEmptyString(risk_grade)){risk_grade_big = DataFormatUtility.StringToBigDecimal(risk_grade);}
//				
//				if(OrigUtil.isEmptyString(risk_grade)||risk_grade_big.compareTo(new BigDecimal(0.0))<0){
//					errorMsg = PLMessageResourceUtil.getTextDescription(request, "RISK_GRADE_WARNING");
//					formHandler.PushErrorMessage("risk_grade", errorMsg);
//				}
				
				if(OrigUtil.isEmptyString(risk_grade)){
					errorMsg = PLMessageResourceUtil.getTextDescription(request, "RISK_GRADE_WARNING");
					formHandler.PushErrorMessage("risk_grade", errorMsg);
				}
				
				BigDecimal total_sme_limit_big = new BigDecimal(0.0);			
				if(!OrigUtil.isEmptyString(total_sme_limit)){total_sme_limit_big = DataFormatUtility.StringToBigDecimal(total_sme_limit);}
				
				if(OrigUtil.isEmptyString(total_sme_limit)||total_sme_limit_big.compareTo(new BigDecimal(0.0))<=0){
					errorMsg = PLMessageResourceUtil.getTextDescription(request, "TOTAL_SME_WARNING");
					formHandler.PushErrorMessage("total_sme_limit", errorMsg);
				}
				
				if(errorMsg.length()>0){return true;}
			}
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}
		return false;
	}

}
