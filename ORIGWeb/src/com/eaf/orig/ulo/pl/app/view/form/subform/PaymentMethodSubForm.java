package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPaymentMethodDataM;

public class PaymentMethodSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(PaymentMethodSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {

		  PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
	      PLApplicationDataM  applicationM = formHandler.getAppForm();
	      PLPaymentMethodDataM  paymentMethodM = applicationM.getPaymentMethod();
	      
	      if(null == paymentMethodM){
	    	  paymentMethodM = new PLPaymentMethodDataM();
	    	  applicationM.setPaymentMethod(paymentMethodM);
	      }
	      
          String payment_method_pay = request.getParameter("payment_method_pay");
          String payment_method_ratio = request.getParameter("payment_method_ratio");
          String payment_method_bankAccountNo = request.getParameter("payment_method_bankAccountNo");
          String payment_method_bankAccountName = request.getParameter("payment_method_bankAccountName");
          String payment_method_dueCycle = request.getParameter("payment_method_dueCycle");
          String payment_method_smsAletFlag = request.getParameter("payment_method_smsAletFlag");
          
          paymentMethodM.setPayMethod(payment_method_pay);
          paymentMethodM.setPayRatio(DataFormatUtility.replaceCommaForBigDecimal(payment_method_ratio));
          paymentMethodM.setAccNo(payment_method_bankAccountNo);
          paymentMethodM.setAccName(payment_method_bankAccountName);
          paymentMethodM.setDueCycle(payment_method_dueCycle);
          paymentMethodM.setSmsDueAlert(payment_method_smsAletFlag);
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		
		logger.debug("validate Payment Method SubForm ");
		boolean result = false;
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		/** New Logic Manual Validate Subform #Sankom */
		
		/** Condition Logic Manual Validate Subform #Pipe */
		
		String currentRole = userM.getCurrentRole();
		String errorMsg = "";
		
		/**Add Condition Not Button Execute1 and Execute2 #SeptemWi*/
		
		if (OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType())
				&&!currentRole.equalsIgnoreCase(OrigConstant.ROLE_DF_REJECT) && 
					(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType())){//Praisan 20121218 validate only type submit (1)
			
			 String payment_method_pay=request.getParameter("payment_method_pay"); 
			 
			 if( payment_method_pay!=null&&"02".equals(payment_method_pay)){
				 String payment_method_ratio=request.getParameter("payment_method_ratio");
				 if(payment_method_ratio==null||"".equals(payment_method_ratio)){
					 errorMsg = ErrorUtil.getShortErrorMessage(request,"PAYMENT_METHOD_RATIO");
					formHandler.PushErrorMessage("payment_method_ratio", errorMsg);
					result = true;
				 }
				 String payment_method_bankAccountNo=request.getParameter("payment_method_bankAccountNo");
				 if(payment_method_bankAccountNo==null||"".equals(payment_method_bankAccountNo)){
					 errorMsg = ErrorUtil.getShortErrorMessage(request,"PAYMENT_METHOD_ACCOUNT_NO");
					formHandler.PushErrorMessage("payment_method_bankAccountNo", errorMsg);
					result = true;
				 }
			 }
		
		}
		return result;
	}

}
