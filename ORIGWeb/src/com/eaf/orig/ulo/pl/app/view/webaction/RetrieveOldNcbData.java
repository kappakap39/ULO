package com.eaf.orig.ulo.pl.app.view.webaction;

//import java.sql.Timestamp;
//import java.util.Date;

//import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
//import org.tempuri.RequestOldData;
//import org.tempuri.RequestOldDataResult;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.ncb.service.ejb.NCBServiceManager;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.ncb.util.KCBSServiceLOG;
//import com.eaf.ncb.util.NCBConstant;
//import com.eaf.orig.inf.log.model.ServiceReqRespDataM;
//import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.util.OrigUtil;
//import com.eaf.orig.ulo.pl.app.ejb.ORIGLogManager;
//import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
//import com.eaf.orig.ulo.pl.app.rule.utility.ServiceReqRespTool;
//import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
//import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
//import com.eaf.orig.ulo.pl.config.ORIGConfig;
//import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
//import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import com.eaf.orig.ulo.pl.model.app.personal.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
//import com.eaf.ulo.pl.kcbs.proxy.KCBSMapping;
//import com.eaf.ulo.pl.kcbs.proxy.KCBSRequestServiceProxy;
//import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
//import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
//import com.eaf.xrules.ulo.pl.tool.display.DisplayMatrixTool;

public class RetrieveOldNcbData extends WebActionHelper implements WebAction{
	static Logger logger = Logger.getLogger(RetrieveOldNcbData.class);
	@Override
	public Event toEvent() {		
		return null;
	}

	@Override
	public boolean requiredModelRequest() {		
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {		
		return false;
	}

	@Override
	public boolean preModelRequest(){	
//		
//		ServiceReqRespTool ServiceReqResp = new ServiceReqRespTool();
//		String transId = ServiceReqResp.GenerateTransectionId();
//		String reqRespNo = ServiceReqResp.GenerateReqResNo();
//		
//		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
//		PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
//		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}
//		String trackingCode = xrulesVerM.getNCBTrackingCode();
//		
//		if(!OrigUtil.isEmptyString(trackingCode)){
//			CancleLastNcbTrackingCode(trackingCode);
//		}
//		
//		this.ClearDataNcb(xrulesVerM);
//		
//		xrulesVerM.setNCBRequester(userM.getUserName());
//		RequestOldDataResult response = null;
//		JsonObjectUtil jObj = new JsonObjectUtil();
//		
//		try{						
//			NCBServiceManager ncbBean = PLORIGEJBService.getNCBServiceManager();
//					
//			PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();	
//			
//			String tranNo = ncbBean.getTranNo();
//			
//			xrulesVerM.setNCBTrackingCode(tranNo);
//			
//			logger.debug("NCB TrackingCode (tranNo) ... "+xrulesVerM.getNCBTrackingCode());	
//			
//			origBean.SaveApplication(appM, userM);
//			
//			ncbBean.saveUpdateNCBDataRequest(xrulesVerM.getNCBTrackingCode(), NCBConstant.LOG_ACTIVITY_IN, appM.getApplicationNo(),
//													userM.getUserName(), null);	
//			
//			KCBSMapping kcbsMap = new KCBSMapping();
//			
//			RequestOldData request = kcbsMap.MapModelRequestOldData(userM,appM,xrulesVerM.getNCBTrackingCode());
//			
//				response = this.CallRetrieveOldNCBDataWebservice(request, xrulesVerM.getNCBTrackingCode(), transId, reqRespNo, appM, userM);
//			
//			if(null != response && OrigConstant.KCBSStatusCode.SUCCESS.equals(response.getStatusCode())){
//				xrulesVerM.setNCBCode(NCBConstant.ncbResultCode.WAITING_NCB_DATA);				
//				xrulesVerM.setNCBResult(NCBConstant.NcbResultDesc.IN_PROCESS_NCB);
//				xrulesVerM.setNCBConsentRefNo(response.getConsentRefNo());
//				xrulesVerM.setNcbUpdateBy(userM.getUserName());
//				xrulesVerM.setNCBConsentRefNoDate(new Timestamp(new Date().getTime()));
//				xrulesVerM.setNcbUpdateDate(new Timestamp(new Date().getTime()));
//				xrulesVerM.setNcbRQapprover(userM.getUserName());					
//				
//				origBean.SaveRetrieveOldNcbData(appM, userM,xrulesVerM.getNCBConsentRefNo()
//									,response.getStatusCode(),xrulesVerM.getNCBTrackingCode());
//			}else{				
//				xrulesVerM.setNCBCode(null);
//				xrulesVerM.setNCBResult(GetMessageKCBS(response, getRequest()));
//				xrulesVerM.setNCBRequester(null);
//				xrulesVerM.setNCBTrackingCode(null);
//				xrulesVerM.setNcbUpdateBy(null);
//				xrulesVerM.setNCBConsentRefNoDate(null);
//				xrulesVerM.setNcbUpdateDate(null);
//				xrulesVerM.setNcbRQapprover(null);				
//			}
//								
//			jObj.CreateJsonObject("result_1040", xrulesVerM.getNCBResult());
//			jObj.CreateJsonObject("code_1040", xrulesVerM.getNCBCode());
//			DisplayMatrixTool matrixTool = new DisplayMatrixTool();
//			jObj.CreateJsonObject("ncb-color",matrixTool.NcbStyleTextBox(xrulesVerM.getNCBCode()));
//			
//			PLXRulesDebtBdDataM debtDataM = xrulesVerM.getXRulesDebtBdDataM();
//			if(null == debtDataM){
//				debtDataM = new PLXRulesDebtBdDataM();
//				xrulesVerM.setXRulesDebtBdDataM(debtDataM);
//			}
//			
//			jObj.CreateJsonObject("ncb-consumer",DataFormatUtility.displayCommaNumber(debtDataM.getConsumerLoanDebtTotal(),false));
//			jObj.CreateJsonObject("ncb-commercial",DataFormatUtility.displayCommaNumber(debtDataM.getCommercialLoanDebtTotal(),false));
//			
//			jObj.ResponseJsonArray(getResponse());
//			
//		}catch (Exception e) {
//			logger.fatal("Exception ",e);
//			xrulesVerM.setNCBCode(null);
//			xrulesVerM.setNCBResult(GetMessageKCBS(response, getRequest()));
//			xrulesVerM.setNCBRequester(null);
//			xrulesVerM.setNCBTrackingCode(null);
//			xrulesVerM.setNcbUpdateBy(null);
//			xrulesVerM.setNCBConsentRefNoDate(null);
//			xrulesVerM.setNcbUpdateDate(null);
//			xrulesVerM.setNcbRQapprover(null);			
//
//			jObj.CreateJsonObject("result_1040", xrulesVerM.getNCBResult());
//			jObj.CreateJsonObject("code_1040", xrulesVerM.getNCBCode());
//			DisplayMatrixTool matrixTool = new DisplayMatrixTool();
//			jObj.CreateJsonObject("ncb-color",matrixTool.NcbStyleTextBox(xrulesVerM.getNCBCode()));
//			
//			PLXRulesDebtBdDataM debtDataM = xrulesVerM.getXRulesDebtBdDataM();
//			if(null == debtDataM){
//				debtDataM = new PLXRulesDebtBdDataM();
//				xrulesVerM.setXRulesDebtBdDataM(debtDataM);
//			}
//			try{
//				jObj.CreateJsonObject("ncb-consumer",DataFormatUtility.displayCommaNumber(debtDataM.getConsumerLoanDebtTotal(),false));
//				jObj.CreateJsonObject("ncb-commercial",DataFormatUtility.displayCommaNumber(debtDataM.getCommercialLoanDebtTotal(),false));
//			}catch(Exception ex1){
//				
//			}
//			
//			jObj.ResponseJsonArray(getResponse());
//			
//		}
		return true;
	}

	@Override
	public int getNextActivityType(){	
		return FrontController.NOTFORWARD;
	}
	
//	public RequestOldDataResult CallRetrieveOldNCBDataWebservice(RequestOldData request ,String tranNo
//							,String transId ,String reqRespNo ,PLApplicationDataM appM ,UserDetailM userM) throws Exception{
//		
//		logger.debug("CallRetrieveOldNCBData transId >> "+transId);
//		logger.debug("CallRetrieveOldNCBData reqRespNo >> "+reqRespNo);
//		
//		ORIGLogManager logBean = PLORIGEJBService.getORIGLogManager();
//		RequestOldDataResult response = new RequestOldDataResult();
//		try{
//			ServiceReqRespDataM servRequestM = new ServiceReqRespDataM(transId,appM.getAppRecordID()
//					,tranNo,OrigConstant.ServiceActivityType.OUT,
//							OrigConstant.ServiceLogID.CB003);
//				servRequestM.setReqRespId(reqRespNo);
//				servRequestM.setCreateBy(userM.getUserName());	
//				
//				if("Y".equalsIgnoreCase(KCBSServiceLOG.CB003_DATA)){
//					servRequestM.setContentMsg(request.toString());
//				}
//				if("Y".equalsIgnoreCase(KCBSServiceLOG.CB003_LOG)){
//					logBean.SaveLogServiceReqResp(servRequestM);
//				}
//				
//			KCBSRequestServiceProxy proxy = new KCBSRequestServiceProxy();
//				response = proxy.CallRetrieveOldNCBData(request, ORIGConfig.KCBS_URL);	
//				
//				if(null == response) response = new RequestOldDataResult();		
//				
//				logger.debug("StatusCode >> "+response.getStatusCode());
//				logger.debug("StatusDesc >> "+response.getStatusDesc());
//				
//				ServiceReqRespDataM servResponseM = new ServiceReqRespDataM(transId,appM.getAppRecordID()
//						,tranNo,OrigConstant.ServiceActivityType.IN,
//								OrigConstant.ServiceLogID.CB003);
//				servResponseM.setReqRespId(reqRespNo);
//				if("Y".equalsIgnoreCase(KCBSServiceLOG.CB003_DATA)){
//					servResponseM.setContentMsg(response.toString());
//				}
//				servResponseM.setCreateBy(userM.getUserName());
//				servResponseM.setRespCode(response.getStatusCode());
//				servResponseM.setRespDesc(response.getStatusDesc());
//				if("Y".equalsIgnoreCase(KCBSServiceLOG.CB003_LOG)){
//					logBean.SaveLogServiceReqResp(servResponseM);
//				}
//				
//		}catch (Exception e) {
//			logger.fatal("CallRetrieveOldNCBData Exception ",e);
//			ServiceReqRespDataM servResponseM = new ServiceReqRespDataM(transId,appM.getAppRecordID()
//					,tranNo,OrigConstant.ServiceActivityType.IN,
//							OrigConstant.ServiceLogID.CB003);
//			servResponseM.setReqRespId(reqRespNo);
//			servResponseM.setCreateBy(userM.getUserName());
//			servResponseM.setRespCode(null);
//			servResponseM.setRespDesc(null);
//			servResponseM.setErrorMessage(e.getMessage());
//			if("Y".equalsIgnoreCase(KCBSServiceLOG.CB003_LOG)){
//				logBean.SaveLogServiceReqResp(servResponseM);
//			}
//			throw new Exception(e.getMessage());
//		}	
//		return response;
//	}
//	public String GetMessageKCBS(RequestOldDataResult response ,HttpServletRequest request){
//		if(null != response){
//			if(OrigConstant.KCBSStatusCode.NO_DATA_FOUND.equals(response.getStatusCode())){
//				return ErrorUtil.getShortErrorMessage(getRequest(), "KCBS_STATUS_CODE_02");
//			}
//		}
//		return ErrorUtil.getShortErrorMessage(getRequest(), "CONNECTION_ERROR_KCBS");
//	}
//	public void ClearDataNcb(PLXRulesVerificationResultDataM xrulesVerM){
//		xrulesVerM.setNcbOutPutM(null);
//		xrulesVerM.setNCBResult(null);
//		xrulesVerM.setNCBCode(null);
//		
//		xrulesVerM.setNcbFicoCode(null);
//		xrulesVerM.setNcbFicoResult(null);	
////		#septemwi comment change to NcbFicoCode
////		xrulesVerM.setFicoCode(null);
////		xrulesVerM.setFicoResult(null);	
//		xrulesVerM.setNcbRestructureCode(null);
//		xrulesVerM.setNcbFailHistoryCode(null);
//		xrulesVerM.setNcbFailHistoryReason(null);
//		xrulesVerM.setNcbOverdueHistoryCode(null);
//		xrulesVerM.setNcbOverdueHistoryReason(null);
//		xrulesVerM.setXRulesDebtBdDataM(null);
//		xrulesVerM.setVXRulesNCBDataM(null);
//		xrulesVerM.setVNCBAdjust(null);
//		xrulesVerM.setXrulesFICODataM(null);
//		xrulesVerM.setvXrulesNCBRestructureDebtVect(null);
//		xrulesVerM.setvXrulesNCBOverdueHistoryVect(null);
//		xrulesVerM.setvXrulesNCBVerifyVect(null);
//		xrulesVerM.setNCBRequester(null);
//		xrulesVerM.setNCBTrackingCode(null);
//		xrulesVerM.setNcbUpdateBy(null);
//		xrulesVerM.setNCBConsentRefNoDate(null);
//		xrulesVerM.setNcbUpdateDate(null);
//		xrulesVerM.setNcbRQapprover(null);	
//		
//	}
	
	public void CancleLastNcbTrackingCode(String trackingCode){
		NCBServiceManager ncbBean = PLORIGEJBService.getNCBServiceManager();
			ncbBean.updateNcbReqRespStatus(trackingCode, OrigConstant.FLAG_C);
	}
	
	@Override
	public boolean getCSRFToken(){
		return false;
	}
}
