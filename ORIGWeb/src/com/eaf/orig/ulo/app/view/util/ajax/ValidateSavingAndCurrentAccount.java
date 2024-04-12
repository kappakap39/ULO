package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.model.dih.AccountInformationDataM;
import com.google.gson.Gson;

public class ValidateSavingAndCurrentAccount implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ValidateSavingAndCurrentAccount.class);
	
	public class ResponseObject{
		String errorMsg = "";
		String accountName = "";
		String statusCode = "";
	}
	
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.VALIDATE_SAVING_AND_CURRENT_ACCOUNT);
		DIHProxy dihService = new DIHProxy();
		ResponseObject responseObject = new ResponseObject();
		String ACCOUNT_NO = request.getParameter("acctNo");
		String SUB = request.getParameter("sub");
		logger.debug("ACCOUNT_NO >> "+ACCOUNT_NO);
		DIHQueryResult<String> dihCIS_SRC_STM_CD= dihService.getAccountNameGuaranteeValidation(ACCOUNT_NO);
		String result = "";
		responseObject.statusCode = ResponseData.BUSINESS_EXCEPTION;
		if(ResponseData.SUCCESS.equals(dihCIS_SRC_STM_CD.getStatusCode())){
			result = dihCIS_SRC_STM_CD.getResult();
			logger.debug("accountName >> "+result);
			if(!Util.empty(result)){
					responseObject.statusCode = ResponseData.SUCCESS;
			}
		}else if(ResponseData.SYSTEM_EXCEPTION.equals(dihCIS_SRC_STM_CD.getStatusCode())){
			return responseData.error(dihCIS_SRC_STM_CD.getErrorData().getErrorInformation());
		}
		if(ResponseData.BUSINESS_EXCEPTION.equals(responseObject.statusCode)){
			responseObject.errorMsg = "ERROR_VALIDATE_SAVING_CURRENT";
		}
		String responseJson = new Gson().toJson(responseObject);
		return responseData.success(responseJson);
	}

}
