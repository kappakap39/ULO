package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.eaf.orig.ulo.pl.model.app.PLBundleHLDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesExistCustDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class HabitatLoanSubForm extends ORIGSubForm {	
	static Logger logger = Logger.getLogger(HabitatLoanSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {

		PLOrigFormHandler origForm = (PLOrigFormHandler) request.getSession().getAttribute("PLORIGForm");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");

		PLApplicationDataM applicationM = origForm.getAppForm();
		if (OrigUtil.isEmptyObject(applicationM)){
			applicationM = new PLApplicationDataM();
		}
		
		PLBundleHLDataM bundleHLM = applicationM.getBundleHLM();
		if (OrigUtil.isEmptyObject(bundleHLM)){
			bundleHLM = new PLBundleHLDataM();
			bundleHLM.setAppRecId(applicationM.getAppRecordID());
			applicationM.setBundleHLM(bundleHLM);
		}

		String kec_permit = request.getParameter("kec_permit");
		if (!OrigUtil.isEmptyString(kec_permit))
			bundleHLM.setApproveCreditLine(DataFormatUtility.StringToBigDecimal(kec_permit));

		if(null == bundleHLM.getCreateBy()){
			bundleHLM.setCreateBy(userM.getUserName());
		}
		bundleHLM.setUpdateDate(new Timestamp(new java.util.Date().getTime()));
		
		//Sankom Set ICDC FLAG
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVer = personalM.getXrulesVerification();
		if (null == xrulesVer) {
			xrulesVer = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVer);
		}
		PLXRulesExistCustDataM xrulesExistCustM = xrulesVer.getxRulesExistCustM();
		if(bundleHLM.getApproveCreditLine()!=null && xrulesExistCustM!=null
				&& xrulesExistCustM.getLastTemporaryCreditLimitAmount()!=null){
			if(bundleHLM.getApproveCreditLine().compareTo(xrulesExistCustM.getLastTemporaryCreditLimitAmount())==1){
				applicationM.setICDCFlag(OrigConstant.ORIG_FLAG_Y);
			}
			
		}
		//END set ICDC
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm){
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		boolean result = false;
		String errorMsg = "";
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		
//		String kec_permit = request.getParameter("kec_permit");	 
		if (OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType()) &&
				(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType())) { //Praisan 20121218 validate only type submit (1)
//			#septemwi modify
//			BigDecimal bKecHabitat = DataFormatUtility.StringToBigDecimal(kec_permit);
//			if (bKecHabitat == null || (bKecHabitat.compareTo(new BigDecimal(0)) == 0)) {
//				errorMsg = ErrorUtil.getShortErrorMessage(request, "HABITAT_KEC");
//				formHandler.PushErrorMessage("kec_permit", errorMsg);
//				result = true;
//			}
			
			BigDecimal kecPermit = DataFormatUtility.StringToBigDecimalEmptyNull(request.getParameter("kec_permit"));
			if (null == kecPermit) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "HABITAT_KEC");
				formHandler.PushErrorMessage("kec_permit", errorMsg);
				result = true;
			}

		}
		return result;
	}

}
