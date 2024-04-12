package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.model.app.DIHAccountResult;
import com.google.gson.Gson;

public class ValidateBundleAccountNo implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ValidateBundleAccountNo.class);
	String ACCOUNT_TYPE_LOAN = SystemConstant.getConstant("ACCOUNT_TYPE_LOAN");
	String ACCOUNT_TYPE_CURRENT = SystemConstant.getConstant("ACCOUNT_TYPE_CURRENT");
	String ACCOUNT_STATUS_ACTIVE = SystemConstant.getConstant("ACCOUNT_STATUS_ACTIVE");
	String ACCOUNT_STATUS_SUSPENDED = SystemConstant.getConstant("ACCOUNT_STATUS_SUSPENDED");
	String ACCOUNT_STATUS_APPROVED = SystemConstant.getConstant("ACCOUNT_STATUS_APPROVED");
	String INC_TYPE_BUNDLE_HL = SystemConstant.getConstant("INC_TYPE_BUNDLE_HL");
	String ACCOUNT_TYPE_SAFE = SystemConstant.getConstant("ACCOUNT_TYPE_SAFE");
	public class ResponseObject{
		String errorMsg = "";
		String accountName = "";
		String accountDate = "";
		String accountStatus = "";
	}
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.VALIDATE_ACCOUNT_NO_FOR_BUNDLE);		
		String ACCOUNT_NO = request.getParameter("BUNDLE_ACCOUNT_NO");
		String TYPE_LIMIT = request.getParameter("TYPE_LIMIT");
		String BUNDLE_TYPE = request.getParameter("BUNDLE_TYPE");
		String elementFieldId = request.getParameter("elementFieldId");
		
		if(!Util.empty(BUNDLE_TYPE) && INC_TYPE_BUNDLE_HL.equals(BUNDLE_TYPE)){
			ACCOUNT_NO = request.getParameter("BUNDLING_HL_ACCOUNT_NO");
		}
		ACCOUNT_NO = FormatUtil.removeDash(ACCOUNT_NO);		
		logger.debug("BUNDLE_TYPE : "+BUNDLE_TYPE);
		logger.debug("ACCOUNT_NO : "+ACCOUNT_NO);
		logger.debug("TYPE_LIMIT : "+TYPE_LIMIT);	
		logger.debug("elementFieldId : "+elementFieldId);
		if(Util.empty(ACCOUNT_NO)){
			ResponseObject responseObject = new ResponseObject();
			responseObject.accountName = "";
			responseObject.accountDate = "";
			String responseJson = new Gson().toJson(responseObject);
			logger.debug("responseJson : "+responseJson);				
			return responseData.success(responseJson);
		}
		try{
			DIHProxy proxy = new DIHProxy();
			ResponseObject responseObject = new ResponseObject();
			DIHQueryResult<DIHAccountResult> queryResult = proxy.getAccountData(FormatUtil.displayText(ACCOUNT_NO),FormatUtil.displayText(TYPE_LIMIT));
			DIHAccountResult dihResult = queryResult.getResult();
			queryResult.setErrorData(dihResult.getErrorData());
			String statusCode = dihResult.getStatusCode();
			if(Util.empty(statusCode))statusCode=queryResult.getStatusCode();
			logger.debug("isFoundAccount : "+dihResult.isFoundAccount());
			logger.debug("AccountStatus : "+dihResult.getAccountStatus());
			logger.debug("sourceAccount : "+dihResult.getSourceAccount());
			logger.debug("statusCode : "+statusCode);
			logger.debug("getErrorData : "+dihResult.getErrorData());
			if(ResponseData.SUCCESS.equals(statusCode)&&null!=dihResult){
				if(dihResult.isFoundAccount()){
					if(ACCOUNT_TYPE_LOAN.equals(TYPE_LIMIT)){
						if(!(ACCOUNT_STATUS_APPROVED.equals(dihResult.getAccountStatus())
								||ACCOUNT_STATUS_ACTIVE.equals(dihResult.getAccountStatus())
									||ACCOUNT_STATUS_SUSPENDED.equals(dihResult.getAccountStatus())
										)){
							if(!ACCOUNT_TYPE_SAFE.equals(dihResult.getSourceAccount())){
								responseObject.errorMsg = "ERROR_" + elementFieldId;
								responseObject.accountStatus = dihResult.getAccountStatus();
							}
						}
					}else if(ACCOUNT_TYPE_CURRENT.equals(TYPE_LIMIT)&&!ACCOUNT_STATUS_ACTIVE.equals(dihResult.getAccountStatus())){
							responseObject.errorMsg = "ERROR_" + elementFieldId;
							responseObject.accountStatus = dihResult.getAccountStatus();
					}
				}else{
					responseObject.errorMsg = "ERROR_" + elementFieldId;
				}
				responseObject.accountName = FormatUtil.replece(dihResult.getAccountName());
				responseObject.accountDate = FormatUtil.display(dihResult.getAccountDate(), HtmlUtil.TH);
				String responseJson = new Gson().toJson(responseObject);
				logger.debug("responseJson : "+responseJson);				
				return responseData.success(responseJson);
			}else{
				return responseData.error(queryResult);
			}			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			return responseData.error(e);
		}
	}
}
