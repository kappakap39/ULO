package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.app.utility.ServiceReqRespTool;
import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
//import com.eaf.ulo.pl.eai.service.EAIModuleService;
//import com.eaf.ulo.pl.eai.util.EAIConstant;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.moduleservice.model.EAIDataM;

public class ValidateEAIAccount implements AjaxDisplayGenerateInf{	
	
	static Logger logger = Logger.getLogger(ValidateEAIAccount.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		PLOrigFormHandler PLORIGForm = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = PLORIGForm.getAppForm();
		
		UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");
		
		String transactionID = request.getParameter("transactionId");

		String accountNo = request.getParameter("accountNo");
		String identification_type = request.getParameter("cardtype");
		String identification_no = request.getParameter("identification_no");
		String serviceType = request.getParameter("serviceType");
		
		logger.debug("AccountNo >> " + accountNo);
		logger.debug("ID No >> " + identification_no);
		logger.debug("serviceType >> " + serviceType);
		
		PLPersonalInfoDataM personDataM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		if(OrigUtil.isEmptyString(personDataM.getCidType())){
			personDataM.setCidType(identification_type);
		}

		JsonObjectUtil json = new JsonObjectUtil();

		try{
			
			ORIGXRulesTool tool = new ORIGXRulesTool();
			XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(applicationM, null, userM);

			if(OrigUtil.isEmptyString(transactionID)){
				ServiceReqRespTool requestResponse = new ServiceReqRespTool();
				transactionID = requestResponse.GenerateTransectionId();
			}
			
			requestM.setTransId(transactionID);
			
//			EAIModuleService service = new EAIModuleService();			
//				service.RequestServiceCIS0367I01(requestM, ORIGConfig.EAI_CIS0367I01 ,accountNo,identification_no);
			
			EAIDataM eaiM = requestM.getEaiM();			
			if(null == eaiM){
				eaiM = new EAIDataM();
			}		
			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
				personalM.setCisNo(eaiM.getCisNo());
			
			json.CreateJsonObject("element_cisNo",HTMLRenderUtil.replaceNull(eaiM.getCisNo()));
			json.CreateJsonObject("transactionId",HTMLRenderUtil.replaceNull(requestM.getTransId()));
			
//			if(EAIConstant.CIS0367I01Status.SUCCESS.equals(eaiM.getMsgCIS0367I01())){
//				if("FOUND".equals(eaiM.getResultCIS0367I01()) && !OrigUtil.isEmptyString(eaiM.getCisNo())){
//					json.CreateJsonObject("validateResult",HTMLRenderUtil.replaceNull(eaiM.getResultCIS0367I01()));	
//				}else{
//					if("ACCOUNT".equals(serviceType)){
//						String message = eaiM.getResultCIS0367I01();
//						if(OrigUtil.isEmptyString(message)){
//							json.CreateJsonObject("ERROR","CIS0367I01 : Not found CIS No.");
//						}else{
//							json.CreateJsonObject("validateResult",message);
//						}
//					}else{
//						json.CreateJsonObject("ERROR","CIS0367I01 : Not found CIS No.");
//					}
//				}
//			}else{
//				if(PLXrulesConstant.WebServiceCode.CONNECTION_ERROR_CIS0367I01.equals(eaiM.getMsgCIS0367I01())){
//					json.CreateJsonObject("ERROR","CIS0367I01 : "+ErrorUtil.getShortErrorMessage(request, "CAN_NOT_CONNECT_EAI_CIS0367I01"));
//				}else{
//					String message = eaiM.getMsgCIS0367I01();
//					if(OrigUtil.isEmptyString(message)){
////						message = ErrorUtil.getShortErrorMessage(request,"INTERNAL_SERVER_ERROR");
//						message = Message.error();
//					}
//					json.CreateJsonObject("ERROR","CIS0367I01 : "+message);
//				}
//			}
			
		}catch(Exception e){
			logger.fatal("ERROR CIS0367I01 >> ",e);
			json = new JsonObjectUtil();
			String message = Message.error();
			json.CreateJsonObject("ERROR","CIS0367I01 : "+message);
		}
		return json.returnJson();
	}

}
