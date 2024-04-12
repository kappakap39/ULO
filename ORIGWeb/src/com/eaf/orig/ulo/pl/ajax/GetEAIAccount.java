/**
 * Create Date May 10, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 */
package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.app.utility.ServiceReqRespTool;
import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import com.eaf.ulo.pl.eai.service.EAIModuleService;
//import com.eaf.ulo.pl.eai.util.EAIConstant;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.moduleservice.model.EAIDataM;

/**
 * @author Sankom
 * 
 */
public class GetEAIAccount implements AjaxDisplayGenerateInf {
	private Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {

		PLOrigFormHandler PLORIGForm = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = PLORIGForm.getAppForm();		  
				
		String itemName = request.getParameter("itemName");
		String accountNo = request.getParameter("accountNo");
        String transactionID = request.getParameter("transactionId");  
		
		logger.debug("Item Name >> " + itemName);
		logger.debug("Account No >> " + accountNo);   
		logger.debug("Transaction ID >> " + transactionID);		

		JsonObjectUtil json = new JsonObjectUtil();
		
		try{			
			ORIGXRulesTool tool = new ORIGXRulesTool();
			XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(applicationM, null, null);
			
			if(OrigUtil.isEmptyString(transactionID)){
				ServiceReqRespTool requestResponse = new ServiceReqRespTool();
				transactionID = requestResponse.GenerateTransectionId();
			}
			requestM.setTransId(transactionID);
			
			EAIDataM eaiM = requestM.getEaiM();
			
			if(null == eaiM){
				eaiM = new EAIDataM();
				requestM.setEaiM(eaiM);
			}		
			eaiM.setAccountNo(accountNo);
			
//			EAIModuleService service = new EAIModuleService();
//				service.RequestServiceCBS1183I01(requestM, ORIGConfig.EAI_CBS1183I01,itemName);
//							
//			logger.debug("MsgCBS1183I01 >> "+eaiM.getMsgCBS1183I01() );
//			logger.debug("AccountName >> "+eaiM.getAccountName());
//			logger.debug("ResultCBS1183I01 >> "+eaiM.getResultCBS1183I01());
//			
//			if(EAIConstant.CBS1183I01Status.SUCCESS.equals(eaiM.getMsgCBS1183I01())){
//				json.CreateJsonObject(itemName, eaiM.getAccountName());
//				json.CreateJsonObject("cbs_result", eaiM.getResultCBS1183I01());
//				json.CreateJsonObject("transactionId", requestM.getTransId());								 		
//			}else{
//				if(PLXrulesConstant.WebServiceCode.CONNECTION_ERROR_CBS1183I01.equals(eaiM.getMsgCBS1183I01())){
//					json.CreateJsonObject("ERROR","CBS1183I01 : "+ErrorUtil.getShortErrorMessage(request, "CAN_NOT_CONNECT_EAI_CBS1183I01"));
//				}else{
//					String message = eaiM.getMsgCBS1183I01();
//					if(null == message){
////						message = ErrorUtil.getShortErrorMessage(request,"INTERNAL_SERVER_ERROR");
//						message = Message.error();
//					}
//					json.CreateJsonObject("ERROR","CBS1183I01 : "+message);
//				}
//			}
		}catch(Exception e){
			logger.fatal("Exception >> ",e);
			json = new JsonObjectUtil();
			String message = Message.error();
			json.CreateJsonObject("ERROR","CBS1183I01 : "+message);			
		}  
		return json.returnJson();
	}
}
