package com.eaf.orig.ulo.pl.ajax;


import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.inf.log.model.ServiceReqRespDataM;
import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.ServiceReqRespTool;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/*
 * 
 * 
 * 
 * 
 * Not Use
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class GetEAIAccountCust implements AjaxDisplayGenerateInf {
	private  Logger log = Logger.getLogger(GetEAIAccountCust.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		ServiceReqRespTool serviceTool = new ServiceReqRespTool();
		UserDetailM userdetailM	= (UserDetailM)request.getSession().getAttribute("ORIGUser");
		PLOrigFormHandler PLORIGForm = (PLOrigFormHandler)request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM plApplicationDataM = PLORIGForm.getAppForm();
		
		
		ServiceReqRespTool ServiceReqResp = new ServiceReqRespTool();
		
		//Begin prepare data for insert service interface log
		String transId = ServiceReqResp.GenerateTransectionId();
		String busClass = plApplicationDataM.getBusinessClassId();
		GeneralParamM generalparam = PLORIGEJBService.getORIGDAOUtilLocal().getGeneralParamvalueByBussClass("KBANK_APP_ID",busClass);
		String paramValue = generalparam.getParamValue();
		String apprecordID = plApplicationDataM.getAppRecordID();
		String rqUIDResult = serviceTool.GeneraterqUID(paramValue, apprecordID);	
		String rqDtResult = serviceTool.GeneraterqDt();
		String rqrsNo = serviceTool.GenerateReqResNo();
		log.debug("rqUIDResult= "+rqUIDResult);
		log.debug("rqDtResult= "+rqDtResult);
		log.debug("rqrsNo= "+rqrsNo);

		ServiceReqRespDataM servReqRespM = new ServiceReqRespDataM();
		servReqRespM.setTransId(transId);
		servReqRespM.setServiceId(OrigConstant.ServiceLogID.EAI003);
		servReqRespM.setRefCode(rqUIDResult);
		servReqRespM.setReqRespId(ServiceReqResp.GenerateReqResNo());
		servReqRespM.setCreateBy(userdetailM.getUserName());
		servReqRespM.setAppId(plApplicationDataM.getAppRecordID());
		//End prepare data for insert service interface log
		JsonObjectUtil jsonObject = new JsonObjectUtil(); 
/**		
		
		try{
			PLPersonalInfoDataM personDataM = plApplicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			
			String cardNo = request.getParameter("card_info_card_no");
			String endpointCP0148I01 = ORIGConfig.EAI_CP0148I01;
			
			com.kasikornbank.eai.CP0148I01.CP0148I01SoapProxy proxy = new com.kasikornbank.eai.CP0148I01.CP0148I01SoapProxy();			
			proxy.setEndpoint(endpointCP0148I01);
		
			EAIModelElementMapping eaiElement = new EAIModelElementMapping();
			com.kasikornbank.eai.CP0148I01.CP0148I01_Element  requestEai = eaiElement.MapCP0148I01_Element(cardNo,null,null);
			
			//insert log service out
			servReqRespM.setActivityType(OrigConstant.ServiceActivityType.OUT);
			PLORIGEJBService.getORIGLogManager().SaveLogServiceReqResp(servReqRespM);
			
			com.kasikornbank.eai.CP0148I01.CP0148I01Response  response = proxy.doService(requestEai);
			
			if(response != null){
				com.kasikornbank.eai.CP0148I01.EAIHeader eaiHeader = response.getEAIHeader(); //get response header for check response status		
				if(null == eaiHeader) eaiHeader = new com.kasikornbank.eai.CP0148I01.EAIHeader();
				log.debug("@@@@@ eaiHeader.getStatus(): " + eaiHeader.getStatus());
				
				//insert log service in
				servReqRespM.setActivityType(OrigConstant.ServiceActivityType.IN);
				servReqRespM.setReqRespId(ServiceReqResp.GenerateReqResNo());
				servReqRespM.setRespCode(String.valueOf(eaiHeader.getStatus())); //0 is success;
				PLORIGEJBService.getORIGLogManager().SaveLogServiceReqResp(servReqRespM);
				
				com.kasikornbank.eai.CP0148I01.CPACCustomer cPACCustomer = response.getCPACCustomer();
				if(cPACCustomer != null){
					log.debug("@@@ cPACCustomer.getCreditLine():" + cPACCustomer.getCreditLine());
					PLCardDataM cardM = personDataM.getCardInformation();
					if(cardM == null){
						cardM = new PLCardDataM();
						personDataM.setCardInformation(cardM);
					}
					cardM.setCurCreditLine(new BigDecimal(cPACCustomer.getCreditLine()));
					jsonObject.CreateJsonObject("card_info_current_credit", DataFormatUtility.displayCommaNumber(cardM.getCurCreditLine()));
				}
			}else{
				log.error("##### Response model is null");
				//insert log service in with null response model
				servReqRespM.setActivityType(OrigConstant.ServiceActivityType.IN);
				servReqRespM.setReqRespId(ServiceReqResp.GenerateReqResNo());
				servReqRespM.setRespCode("Error");
				servReqRespM.setRespDesc("Response model is null");
				PLORIGEJBService.getORIGLogManager().SaveLogServiceReqResp(servReqRespM);
			}

		}catch (RemoteException e) {
			jsonObject = new JsonObjectUtil();
			jsonObject.CreateJsonObject("ERROR",ErrorUtil.getShortErrorMessage(request,"CAN_NOT_CONNECT_EAI_CP0148I01"));
			
			log.fatal("RemoteException",e);
			//insert log service in with remote exception
			servReqRespM.setActivityType(OrigConstant.ServiceActivityType.IN);
			servReqRespM.setReqRespId(ServiceReqResp.GenerateReqResNo());
			servReqRespM.setRespCode("Error");
			servReqRespM.setRespDesc(e.getMessage());
			PLORIGEJBService.getORIGLogManager().SaveLogServiceReqResp(servReqRespM);
		}catch (Exception e) {
			log.fatal("error",e);
		}		
*/
		return jsonObject.returnJson();
	}
	

}
