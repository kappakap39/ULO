package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.app.utility.ServiceReqRespTool;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;
import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
//import com.eaf.ulo.pl.eai.service.EAIModuleService;
//import com.eaf.ulo.pl.eai.util.EAIConstant;
//import com.eaf.xrules.ulo.pl.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.moduleservice.model.EAIDataM;

public class GetEAIaddressData implements AjaxDisplayGenerateInf {
	private  Logger log = Logger.getLogger(GetEAIaddressData.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");
		PLOrigFormHandler formHandler = (PLOrigFormHandler)request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		
		PLApplicationDataM applicationM = formHandler.getAppForm();
		String endpoint1015 = ORIGConfig.EAI_CIS1015I01;
	
		String transactionID = request.getParameter("transactionId"); 
		
		JsonObjectUtil json = new JsonObjectUtil();
		
		try{			
			ORIGXRulesTool tool = new ORIGXRulesTool();
			XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(applicationM, null, null);
			if(OrigUtil.isEmptyString(transactionID)){
				ServiceReqRespTool requestResponse=new ServiceReqRespTool();
				transactionID = requestResponse.GenerateTransectionId();
			}
			requestM.setTransId(transactionID);
			requestM.setUserM(userM);
			
//			EAIModuleService service = new EAIModuleService();
//				service.RequestServiceCIS1015I01(requestM, endpoint1015);
						
			EAIDataM eaiM = requestM.getEaiM();			
			if(null == eaiM){
				eaiM = new EAIDataM();
			}	            		
				
//			if(EAIConstant.CIS1015I01Status.SUCCESS.equals(eaiM.getMsgCIS1015I01())) {				
//				MapResponse(applicationM, json, request);
//			}else{
//				if(PLXrulesConstant.WebServiceCode.CONNECTION_ERROR_CIS1015I01.equals(eaiM.getMsgCIS1015I01())){
//					json.CreateJsonObject("ERROR","CIS1015I01 : "+ErrorUtil.getShortErrorMessage(request,"CAN_NOT_CONNECT_EAI_CIS1015I01"));	
//				}else{
//					String message = eaiM.getMsgCIS1015I01();
//					if(null == message){
////						message = ErrorUtil.getShortErrorMessage(request,"INTERNAL_SERVER_ERROR");
//						message = Message.error();
//					}
//					json.CreateJsonObject("ERROR","CIS1015I01 : "+message);
//				}
//			}
		}catch(Exception e){
//			log.fatal("ERROR ",e);
			json = new JsonObjectUtil();
			String message = Message.error(e);
			json.CreateJsonObject("ERROR","CIS1015I01 : "+message);
		}
		return json.returnJson();
	}
	
	private void MapResponse(PLApplicationDataM applicationM ,JsonObjectUtil json,HttpServletRequest request){
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);	
		Vector<PLAddressDataM> addressVect = personalM.getAddressVect();
		AddressUtil addressUtil =  new AddressUtil();
		if(!OrigUtil.isEmptyVector(addressVect)){	
			StringBuilder addressStr = new StringBuilder("");
			for(PLAddressDataM addrM :addressVect){
				addressStr.append(addressUtil.CreatePLAddressM(addrM,personalM.getPersonalType(),"EDIT",request, personalM.getDepartment()));					
			}
			json.CreateJsonObject("addressResult", addressStr.toString());
		}else{
			json.CreateJsonObject("addressResult",addressUtil.CreateNorecPLAddressM());
		}
	}
	
}
