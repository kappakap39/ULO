package com.eaf.orig.ulo.app.validation;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class AccountValidationHelper implements AccountValidationInf {
	private static transient Logger logger = Logger.getLogger(AccountValidationHelper.class);	
	private String accountNo;
	private String accountName;
	private String additionalAccountNo;
	private String errorCode;
	private ErrorData errorData;
	@Override
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAdditionalAccountNo() {
		return additionalAccountNo;
	}
	public void setAdditionalAccountNo(String additionalAccountNo) {
		this.additionalAccountNo = additionalAccountNo;
	}
	@Override
	public String validateField(HttpServletRequest request, Object objectForm) {
		String DIH_ACCT_STATUS_ACTIVE = SystemConstant.getConstant("DIH_ACCT_STATUS_ACTIVE");
		logger.info("Validate acct");
		DIHProxy proxy = new DIHProxy();	
		DIHQueryResult<String> cisResult = proxy.getCisNoByAccountNo(accountNo);
		if(!ResponseData.SUCCESS.equals(cisResult.getStatusCode())){
			setErrorData(cisResult.getErrorData());
			setErrorCode(cisResult.getStatusCode());
		}
		String cisNo = cisResult.getResult();
		if(!Util.empty(cisNo)) {
			DIHQueryResult<String> accountStatusResult = proxy.getAccountInfo(accountNo,"AR_LCS_TP_CD");
			if(!ResponseData.SUCCESS.equals(accountStatusResult.getStatusCode())){
				setErrorData(accountStatusResult.getErrorData());
				setErrorCode(accountStatusResult.getStatusCode());
			}
			String status = accountStatusResult.getResult();
			if(Util.empty(status) || !DIH_ACCT_STATUS_ACTIVE.equals(status)) {
				cisNo = null;
			}else{
				DIHQueryResult<String> accountNameResult = proxy.getAccountInfo(accountNo,"AR_NM_TH");
				if(!ResponseData.SUCCESS.equals(accountNameResult.getStatusCode())){
					setErrorData(accountNameResult.getErrorData());
					setErrorCode(accountNameResult.getStatusCode());
				}
				accountName = accountNameResult.getResult();
			}
		}
		return cisNo;
	}	
	public static AccountValidationInf retrieveAcctValidation(String product) {
		String ACCT_TYPE_ATM_SAVING = SystemConstant.getConstant("ACCT_TYPE_ATM_SAVING");
		String ACCT_TYPE_ATM_CURRENT = SystemConstant.getConstant("ACCT_TYPE_ATM_CURRENT");
		String ACCT_TYPE_CASH_TRANSFER = SystemConstant.getConstant("ACCT_TYPE_CASH_TRANSFER");
		String ACCT_TYPE_CALL_FOR_CASH = SystemConstant.getConstant("ACCT_TYPE_CALL_FOR_CASH");
		String ACCT_TYPE_CC_MAIN = SystemConstant.getConstant("ACCT_TYPE_CC_MAIN");
		String ACCT_TYPE_CC_SUP = SystemConstant.getConstant("ACCT_TYPE_CC_SUP");
		String ACCT_TYPE_KEC = SystemConstant.getConstant("ACCT_TYPE_KEC");
		String ACCT_TYPE_KPL = SystemConstant.getConstant("ACCT_TYPE_KPL");
		AccountValidationInf validator = null;
		if(ACCT_TYPE_CALL_FOR_CASH.equals(product)) {
			logger.debug("CallForCashAccountValidation");
			validator = new CallForCashAccountValidation();
		}else if(ACCT_TYPE_CASH_TRANSFER.equals(product)) {
			logger.debug("CashDay1AccountValidation");
			validator = new CashDay1AccountValidation();
		}else if(ACCT_TYPE_ATM_SAVING.equals(product)) {
			logger.debug("ATMSavingAccountValidation");
			validator = new ATMSavingAccountValidation();
		}else if(ACCT_TYPE_ATM_CURRENT.equals(product)) {
			logger.debug("ATMCurrentAccountValidation");
			validator = new ATMCurrentAccountValidation();
		}else if(ACCT_TYPE_CC_MAIN.equals(product)) {
			logger.debug("CCMainAccountValidation");
			validator = new CCMainAccountValidation();
		}else if(ACCT_TYPE_CC_SUP.equals(product)) {
			logger.debug("CCSupAccountValidation");
			validator = new CCSupAccountValidation();
		}else if(ACCT_TYPE_KEC.equals(product)) {
			logger.debug("KECAccountValidation");
			validator = new KECAccountValidation();
		}else if(ACCT_TYPE_KPL.equals(product)) {
			logger.debug("KPLAccountValidation");
			validator = new KPLAccountValidation();
		}
		return validator;
	}	
	public static boolean isJointAccount(PersonalInfoDataM personalInfo, PaymentMethodDataM paymentMethod) {
		if(Util.empty(paymentMethod)) {
			return false;
		}
		String PERSONAL_TYPE_APPLICANT =SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN"); 		
		String ACCT_VALIDATION_PASS = SystemConstant.getConstant("ACCT_VALIDATION_PASS");
	 	String ERR_CODE_ACCT_SINGLE_JOINT_MAIN =SystemConstant.getConstant("ERR_CODE_ACCT_SINGLE_JOINT_MAIN");
		String ERR_CODE_ACCT_SINGLE_JOINT_SUP =SystemConstant.getConstant("ERR_CODE_ACCT_SINGLE_JOINT_SUP");
		String ERR_CODE_ACCT_SINGLE_ACCT_KEC =SystemConstant.getConstant("ERR_CODE_ACCT_SINGLE_ACCT_KEC");
		boolean isJoint = false;
		if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
			if(PRODUCT_CRADIT_CARD.equals(paymentMethod.getProductType())){
				if(!Util.empty(paymentMethod.getCompleteData())
						&& paymentMethod.getCompleteData().equals(ERR_CODE_ACCT_SINGLE_JOINT_MAIN)){
					isJoint = true;
				}
			}else if(PRODUCT_K_EXPRESS_CASH.equals(paymentMethod.getProductType())) {
				if(!Util.empty(paymentMethod.getCompleteData())
						&& paymentMethod.getCompleteData().equals(ERR_CODE_ACCT_SINGLE_ACCT_KEC)){
					isJoint = true;
				}
			}else if(PRODUCT_K_PERSONAL_LOAN.equals(paymentMethod.getProductType())) {
				if(!Util.empty(paymentMethod) && !Util.empty(paymentMethod.getCompleteData())
						&& !paymentMethod.getCompleteData().equals(ACCT_VALIDATION_PASS)){
					isJoint = true;
				}
			}
		}else{
			if(!Util.empty(paymentMethod.getCompleteData())
					&& paymentMethod.getCompleteData().equals(ERR_CODE_ACCT_SINGLE_JOINT_SUP)){
				isJoint = true;
			}
		}
		return isJoint;
	}
	public static boolean isAccountValid(PaymentMethodDataM paymentMethod) {
		if(Util.empty(paymentMethod)) {
			return false;
		}
		String ACCT_VALIDATION_PASS = SystemConstant.getConstant("ACCT_VALIDATION_PASS");
		boolean isValid = false;
		if(Util.empty(paymentMethod.getCompleteData())
					|| paymentMethod.getCompleteData().equals(ACCT_VALIDATION_PASS)){
			isValid = true;
		}
		return isValid;
	}
	@Override
	public String getErrorCode() {
		return errorCode;
	}
	@Override
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	public ErrorData getErrorData() {
		return errorData;
	}
	@Override
	public void setErrorData(ErrorData errorData) {
		this.errorData = errorData;
	}
}
