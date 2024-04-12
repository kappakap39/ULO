package com.eaf.orig.ulo.app.view.form.subform.verifyhr.manual;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.HRVerificationDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class ContactCustomerHRSubForm implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ContactCustomerHRSubForm.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		logger.debug("Create contract customer..");
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_CONTRACT_HR_RESULT);
		try{
			UserDetailM	userM =(UserDetailM)request.getSession().getAttribute("ORIGUser");
			EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();		
			String PHONE_NO = request.getParameter("PHONE_NO");
			String CONTACT_TYPE =request.getParameter("CONTACT_TYPE");
			String PHONE_VER_STATUS = request.getParameter("PHONE_VER_STATUS");
			String REMARK = request.getParameter("REMARK");
			String EXT = request.getParameter("EXT");		
			if(!Util.empty(personalInfo)){
				VerificationResultDataM  verificationResult = personalInfo.getVerificationResult();
				if(Util.empty(verificationResult)){
					verificationResult = new VerificationResultDataM();
					personalInfo.setVerificationResult(verificationResult);
				}
				HRVerificationDataM	 hrVerification = new HRVerificationDataM();
				hrVerification.setRemark(REMARK);
				StringBuilder phoneTo = new StringBuilder(PHONE_NO);
				if(!Util.empty(EXT)){
					phoneTo.append("  "+MessageUtil.getText(request, "TO"));
					phoneTo.append("  "+EXT);
				}
				logger.debug("phoneNo >> "+phoneTo);
				hrVerification.setPhoneNo(phoneTo.toString());
				hrVerification.setPhoneVerStatus(PHONE_VER_STATUS);
				hrVerification.setContactType(CONTACT_TYPE);
				hrVerification.setCreateDate(ApplicationDate.getTimestamp());
				hrVerification.setCreateBy(userM.getUserName());
				verificationResult.addHRVerification(hrVerification);
			}
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
