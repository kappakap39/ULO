package com.eaf.orig.ulo.app.view.util.ajax;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.app.validation.AccountValidationHelper;
import com.eaf.orig.ulo.app.validation.AccountValidationInf;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.json.ElementFormDataM;
import com.google.gson.Gson;

public class ValidateAccount implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ValidateAccount.class);
	ArrayList<String> PRODUCT_CODE_LIST = SystemConstant.getArrayListConstant("PRODUCT_CODE_LIST");
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.VALIDATE_ACCOUNT);
		ElementFormDataM elementForm = new ElementFormDataM();
		try{
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getPersonalInfoObjectForm(request);
			String productType = null;
			String elementID = request.getParameter("elementID");
			String name = request.getParameter("name");
			String acct_no = request.getParameter("acct_no");
			String isRekey = request.getParameter("isRekey");
			acct_no = FormatUtil.removeDash(acct_no);
			String additionalAcct = request.getParameter("additionalAcct");
			additionalAcct = FormatUtil.removeDash(additionalAcct);
			String returnElementId = "ACCOUNT_NAME";
			logger.info("isRekey "+isRekey);
			logger.info("elementID "+elementID);
			logger.info("name "+name);
			logger.info("acct_no "+acct_no);
			logger.info("additionalAcct "+additionalAcct);
			String roleId = FormControl.getFormRoleId(request);
			
			String ROLE_DV = SystemConstant.getConstant("ROLE_DV");	
			String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
			String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
			String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
			String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
			String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
			String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");	
			String FIELD_ID_ACCT_VALIDATION_ERROR = SystemConstant.getConstant("FIELD_ID_ACCT_VALIDATION_ERROR");	
			String SUFFIX_CASH_TRANSFER = PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER";
			String SUFFIX_CALL_FOR_CASH = PRODUCT_K_EXPRESS_CASH+"_"+CALL_FOR_CASH;
			String SAVING_ACC_NO = "SAVING_ACC_NO";
			String CURRENT_ACC_NO = "CURRENT_ACC_NO";
			
			String ACCT_TYPE_ATM_SAVING = SystemConstant.getConstant("ACCT_TYPE_ATM_SAVING");
			String ACCT_TYPE_ATM_CURRENT = SystemConstant.getConstant("ACCT_TYPE_ATM_CURRENT");
			String ACCT_TYPE_CASH_TRANSFER = SystemConstant.getConstant("ACCT_TYPE_CASH_TRANSFER");
			String ACCT_TYPE_CALL_FOR_CASH = SystemConstant.getConstant("ACCT_TYPE_CALL_FOR_CASH");
			String ACCT_TYPE_CC_MAIN = SystemConstant.getConstant("ACCT_TYPE_CC_MAIN");
			String ACCT_TYPE_CC_SUP = SystemConstant.getConstant("ACCT_TYPE_CC_SUP");
			String ACCT_TYPE_KEC = SystemConstant.getConstant("ACCT_TYPE_KEC");
			String ACCT_TYPE_KPL = SystemConstant.getConstant("ACCT_TYPE_KPL");
			String productName = "";
			if(MConstant.FLAG.YES.equals(isRekey)) {
				productName = request.getParameter("productName");
				logger.debug("elementID>>"+elementID);
				logger.debug("rekey-productName>>"+productName);
			}else{
				String[] elementList = elementID.split("_");
				if(elementList.length > elementList.length-1) {
					productName=elementList[elementList.length-1];
				}
				if(!PRODUCT_CODE_LIST.contains(productName)){
					productName=elementID.replace(name+"_", "");
				}
			}			
			logger.info("productName "+productName);
			if(SUFFIX_CALL_FOR_CASH.equals(productName)) {
				productType = ACCT_TYPE_CALL_FOR_CASH;
			}else if(SUFFIX_CASH_TRANSFER.equals(productName)) {
				productType = ACCT_TYPE_CASH_TRANSFER;
			}else if(SAVING_ACC_NO.equals(name)) {
				returnElementId = "SAVING_ACC_NAME";
				productType = ACCT_TYPE_ATM_SAVING;
			}else if(CURRENT_ACC_NO.equals(name)) {
				returnElementId = "CURRENT_ACC_NAME";
				productType = ACCT_TYPE_ATM_CURRENT;
			}else if(PRODUCT_CRADIT_CARD.equals(productName) && PERSONAL_TYPE.equals(personalInfo.getPersonalType())) {
				productType = ACCT_TYPE_CC_MAIN;
			}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType())) {
				productType = ACCT_TYPE_CC_SUP;
			}else if(PRODUCT_K_EXPRESS_CASH.equals(productName)) {
				productType = ACCT_TYPE_KEC;
			}else if(PRODUCT_K_PERSONAL_LOAN.equals(productName)) {
				productType = ACCT_TYPE_KPL;
			}
			logger.info("productType "+productType);			
			AccountValidationInf validationHelper = AccountValidationHelper.retrieveAcctValidation(productType);
			if(!Util.empty(validationHelper)) { 
				validationHelper.setAccountNo(acct_no);
				validationHelper.setAdditionalAccountNo(additionalAcct);
				String CIS_INFO = validationHelper.validateField(request, personalInfo);
				String acctName = validationHelper.getAccountName();
				elementForm.setElementValue(acctName);
				elementForm.setElementId(returnElementId);
				String errorCode = validationHelper.getErrorCode();
				ErrorData errorData = validationHelper.getErrorData();
				if(!Util.empty(errorCode)) {
					if(ResponseData.SYSTEM_EXCEPTION.equals(errorCode) && !Util.empty(errorData)){
						elementForm.setMessageType(errorCode);
						elementForm.setErrorCode(errorData.getErrorInformation());
					}else{
					if(ROLE_DV.equals(roleId)) {
						elementForm.setErrorCode(ListBoxControl.getName(FIELD_ID_ACCT_VALIDATION_ERROR,errorCode));
						elementForm.setElementValue(null);
					} else {
						if(Util.empty(CIS_INFO)) {
							elementForm.setErrorCode(MessageErrorUtil.getText(request, "ERROR_NO_ACCT_FOUND"));
						} else {
							elementForm.setErrorCode(MessageErrorUtil.getText(request, "ERROR_ACCT_INFO_NOT_MATCH"));
						}
					}
					}
					elementForm.setElementParam(errorCode);
				}else {
					elementForm.setMessageType(ResponseData.SUCCESS);
				}
			}else{
	//			#rawi comment not validate ERROR_RETRIEVING_ACCT for non kbank account
				elementForm.setErrorCode(MessageErrorUtil.getText(request, "ERROR_RETRIEVING_ACCT"));
			}
			return responseData.success(new Gson().toJson(elementForm));
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
