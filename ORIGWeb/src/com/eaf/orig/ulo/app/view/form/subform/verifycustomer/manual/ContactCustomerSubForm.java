package com.eaf.orig.ulo.app.view.form.subform.verifycustomer.manual;
//import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.core.ulo.common.date.ApplicationDate;
//import com.eaf.core.ulo.common.message.MessageUtil;
//import com.eaf.core.ulo.common.properties.CacheControl;
//import com.eaf.core.ulo.common.util.Util;
//import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.ulo.constant.MConstant;
//import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
//import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
//import com.eaf.orig.ulo.model.app.PhoneVerificationDataM;
//import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class ContactCustomerSubForm implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ContactCustomerSubForm.class);
	String FIELD_ID_DATA_VALIDATION_STATUS = SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS");
	String VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT = SystemConstant.getConstant("VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT");
	String VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER = SystemConstant.getConstant("VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER");
	String VER_CUS_COMPLETE_VERIFY_COMPLETED = SystemConstant.getConstant("VER_CUS_COMPLETE_VERIFY_COMPLETED");
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_CONTRACT_CUSTOMER_RESULT);
		try{
	//		UserDetailM	userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");
			EntityFormHandler EntityForm =(EntityFormHandler)request.getSession().getAttribute("EntityForm");
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();		
			String phoneNo = request.getParameter("PHONE_NO");
			String contactType = request.getParameter("CONTACT_TYPE");
			String phoneVerStatus = request.getParameter("PHONE_VER_STATUS");
			String remark = request.getParameter("REMARK");
			String ext = request.getParameter("EXT");
			logger.debug("ContactCustomerHRSubForm");
			logger.debug("phoneNo :"+phoneNo);
			logger.debug("contactType :"+contactType);
			logger.debug("phoneVerStatus :"+phoneVerStatus);
			logger.debug("remark :"+remark);
			logger.debug("ext :"+ext);
			//Add supplementary result for verify customer
	//		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	//		if(!Util.empty(personalInfo)){
	//			VerificationResultDataM  verificationResult = personalInfo.getVerificationResult();
	//		 	if(Util.empty(verificationResult)){
	//		 		verificationResult = new VerificationResultDataM();
	//		 		personalInfo.setVerificationResult(verificationResult);
	//		 	}
	//		 	logger.debug("phoneVerStatus >>> "+phoneVerStatus);
	//			if(!Util.empty(phoneVerStatus) && MConstant.FLAG_N.equals(phoneVerStatus)){
	//				verificationResult.setVerCusComplete(VER_CUS_COMPLETE_CANNOT_CONTACT_CUSTOMER);
	//				verificationResult.setVerCusResultCode(VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT);
	//				verificationResult.setVerCusResult(CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS,VALIDATION_STATUS_WAIT_FOR_NEXT_CONTACT,"DISPLAY_NAME"));
	//				logger.debug("desc >> "+verificationResult.getVerCusResult());
	//				logger.debug("verificationResult >> "+verificationResult.getVerCusComplete());
	//			 }
	//			if(!Util.empty(phoneVerStatus) && MConstant.FLAG_Y.equals(phoneVerStatus)){
	//				verificationResult.setVerCusComplete(VER_CUS_COMPLETE_VERIFY_COMPLETED);
	//			}
	//			ArrayList<PhoneVerificationDataM> phoneVerifications = verificationResult.getPhoneVerifications();
	//			if(Util.empty(phoneVerifications)){
	//				phoneVerifications = new ArrayList<PhoneVerificationDataM> ();
	//				verificationResult.setPhoneVerifications(phoneVerifications);
	//			}
	//			StringBuilder str = new StringBuilder();
	//			str.append(phoneNo);
	//			if(!Util.empty(ext)){
	//				str.append("  "+MessageUtil.getText(request, "TO"));
	//				str.append("  "+ext);
	//			}
	//			PhoneVerificationDataM phoneVerification = new PhoneVerificationDataM();
	//				phoneVerification.setSeq(1);
	//				phoneVerification.setRemark(remark);
	//				phoneVerification.setTelephoneNumber(str.toString());
	//				phoneVerification.setPhoneVerStatus(phoneVerStatus);
	//				phoneVerification.setContactType(contactType);
	//				phoneVerification.setCreateDate(ApplicationDate.getTimestamp());
	//				phoneVerification.setCreateBy(userM.getFirstName());
	//			phoneVerifications.add(phoneVerification);
	//		}
			VerifyUtil.setVerificationResultByPhoneStatus(applicationGroup, phoneNo, contactType, phoneVerStatus, remark, ext, request);
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
