package com.eaf.orig.ulo.pl.app.view.webaction;

//import java.util.Vector;

import java.io.File;
import java.util.Vector;

import org.apache.log4j.Logger;
//import org.tempuri.NewRequest;
//import org.tempuri.NewRequestResult;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.ncb.service.ejb.NCBServiceManager;
//import com.eaf.ncb.util.KCBSServiceLOG;
//import com.eaf.ncb.util.NCBConstant;
import com.eaf.orig.inf.log.model.ServiceReqRespDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.utility.ObjectDAOFactory;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.ajax.ImageUtil;
import com.eaf.orig.ulo.pl.app.utility.Performance;
//import com.eaf.orig.ulo.pl.app.ejb.ORIGLogManager;
//import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
//import com.eaf.orig.ulo.pl.app.rule.utility.ServiceReqRespTool;
//import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.formcontrol.view.form.ErrorDataM;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
//import com.eaf.orig.ulo.pl.model.app.personal.PLNCBDocDataM;
//import com.eaf.orig.ulo.pl.model.app.personal.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
//import com.eaf.ulo.pl.kcbs.proxy.KCBSMapping;
//import com.eaf.ulo.pl.kcbs.proxy.KCBSRequestServiceProxy;
//import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SendToKBankBureauWebAction extends WebActionHelper implements WebAction{	
	static Logger log = Logger.getLogger(SendToKBankBureauWebAction.class);	
	Performance perf = new Performance("SendToKBankBureauWebAction",Performance.Module.SEND_KCBS);	
	String transactionID = perf.GenTrancationID();	
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
		
		String application[] = getRequest().getParameterValues("checkAppRecId");		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");		
		
		// set message error
		SearchHandler HandlerM = (SearchHandler) getRequest().getSession(true).getAttribute("SEARCH_DATAM");
		if(null == HandlerM){
			HandlerM = new SearchHandler();
		}
		HandlerM.setErrorMSG(null);
		HandlerM.setErrorList(null);
		ORIGDAOUtilLocal daoBean = PLORIGEJBService.getORIGDAOUtilLocal();	
		NCBServiceManager ncbBean =  PLORIGEJBService.getNCBServiceManager();

		try{
			if(null != application && application.length > 0){	
				
				userM = daoBean.getUserProfile(userM.getUserName());
				userM.setRoles( userM.getRoles());	
				
				String appRecID = null;
				String applicationNO = null;
				ErrorDataM errorM = null;
				for(String object :application){	
					try{
						
						String obj[] = object.split("\\|");
						appRecID = obj[0];	
						applicationNO = obj[1];
						
						perf.init("preModelRequest", appRecID, transactionID, userM.getUserName());
						
						log.debug("Load Application KCBS AppRecID >> "+appRecID);	
						log.debug("Load Application KCBS ApplicationNo >> "+applicationNO);	
						
						perf.track(Performance.Action.LOAD_APPLICATION, Performance.START);	
						
//						PLApplicationDataM applicationM = daoBean.loadOrigApplicationForNCB(appRecID);						
						PLApplicationDataM applicationM = daoBean.loadAppInFo(appRecID);
						
						perf.track(Performance.Action.LOAD_APPLICATION, Performance.END);
						
						if(null != applicationM.getJobState() && !OrigConstant.BLOCK_FLAG.equals(applicationM.getBlockFlag())
								&& applicationM.getJobState().lastIndexOf(OrigConstant.BLOCK_FLAG) == -1){
							
							String tranNo = ncbBean.getTranNo();							
							log.debug("Process Send Request To KCBS Begin >> "+tranNo);
							perf.track(Performance.Action.SEND_TO_KCBS, Performance.START);	
							errorM = this.SendToKCBSProcess(appRecID, userM, applicationM, tranNo);
							perf.track(Performance.Action.SEND_TO_KCBS, Performance.END);	
							if(null !=errorM && !OrigUtil.isEmptyString(errorM.getMessage())){
								HandlerM.add(errorM);
							}
							
							log.debug("Process Send Request To KCBS Success >> "+tranNo);
						}else{
							log.debug("Cannot Send To KCBS Data, Application Block !!");
							errorM = new ErrorDataM(applicationNO,ErrorUtil.getShortErrorMessage(getRequest(), "DUP_SUF"));
								HandlerM.add(errorM);
						}
					}catch(Exception e){
//						log.error("error application_no >> "+applicationNO);
//						log.fatal("exception >> ",e);
						String MSG = Message.error(e);
						errorM =  new ErrorDataM(applicationNO,MSG);
							HandlerM.add(errorM);
					}					
				}	
				HandlerM.GenarateErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(), "APP_NO"));
			}
		}catch(Exception e){
//			log.fatal("Exception ",e);		
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);	
			return true;
		}		
		return true;
	}	
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}	
	@Override
	public String getNextActionParameter(){
		return "action=FristPLApp";
	}
	@Override
	public boolean getCSRFToken(){
		return true;
	}	
	private ErrorDataM SendToKCBSProcess(String appRecID,UserDetailM userM,PLApplicationDataM applicationM,String tranNo){
		ErrorDataM errorM = new ErrorDataM();;
//		try{
//			KCBSMapping mappingM = new KCBSMapping();
//			NewRequest requestM = mappingM.mapping(appRecID, userM, applicationM, tranNo);	
//			
//			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);	
//						
//			PLXRulesVerificationResultDataM xVerifyM = personalM.getXrulesVerification();
//			if(OrigUtil.isEmptyObject(xVerifyM)){
//				xVerifyM = new PLXRulesVerificationResultDataM();
//				personalM.setXrulesVerification(xVerifyM);
//			}
//			xVerifyM.setNCBCode(NCBConstant.ncbResultCode.REQUEST_NCB_DATA);	
//			
//			errorM.setId(applicationM.getApplicationNo());
//			
//			NewRequestResult resultM = CallKCBSService(requestM, tranNo, userM, appRecID,xVerifyM, errorM);
//			
//			if(errorM != null && !OrigUtil.isEmptyString(errorM.getMessage())){
//				return errorM;
//			}
//			
//			if(!OrigUtil.isEmptyObject(resultM) && OrigConstant.ncbConnectionStatus.COMPLETED.equals(resultM.getStatusCode())){		
//				log.debug("Process Send Request To KCBS ...Success!! Next Step Save Application ");
//				applicationM.setApplicationStatus(null);
//								
////				copy select image to consent path	#CR Consent Image Alignment			
//				Vector<PLNCBDocDataM> ncbDocVect = personalM.getNcbDocVect();	
//				if(null != ncbDocVect){
//					cloneImage(ncbDocVect,resultM.getConsentRefNo());
//				}
//				
//				log.debug("Save Update Application For NCB !! ConsentRefNo >> "+resultM.getConsentRefNo());
//				PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();
//					origBean.updateAppClaimCompleteForNCB(applicationM,userM,resultM.getConsentRefNo(),resultM.getTranNo());
//				
////				#septem comment move to updateAppClaimCompleteForNCB() #CR Consent Image Alignment
////				if(!OrigUtil.isEmptyObject(personalM)){
////					Vector<PLNCBDocDataM> ncbDocVect = personalM.getNcbDocVect();
////					if(!OrigUtil.isEmptyVector(ncbDocVect)){
////						log.debug("Save Log NCB Doc History ...");
////						ORIGDAOUtilLocal daoBean = PLORIGEJBService.getORIGDAOUtilLocal();
////						daoBean.saveNcbDochistVect(ncbDocVect, userM, resultM.getConsentRefNo());
////					}
////				}
//			
//			}			
//		}catch(Exception e){
////			log.fatal("Exception ",e);
//			String MSG = Message.error(e);
//			return new ErrorDataM(applicationM.getApplicationNo(),MSG);
//		}
		return errorM;
	}	
	
	private void cloneImage(Vector<PLNCBDocDataM> ncbDocVect,String consentRefNo) throws Exception{
		log.debug("cloneImage()..ConsentRefNo>> "+consentRefNo);
		UtilityDAO utilDAO = ObjectDAOFactory.getUtilityDAO();
		Vector<PLApplicationImageSplitDataM> imageVect = utilDAO.getApplicationImageM(ncbDocVect);
		if(null != imageVect){
			for (PLApplicationImageSplitDataM splitM : imageVect) {
				String PATH_INPUT = ImageUtil.IMAGEPath(OrigConstant.FoderImage.FODER_IMAGE_L, splitM);
				String PATH_OUTPUT = ImageUtil.IMAGEPath(OrigConstant.FoderImage.FODER_IMAGE_CONSENT,consentRefNo,splitM);
				log.debug("PATH_INPUT >> "+PATH_INPUT);
				log.debug("PATH_OUTPUT >> "+PATH_OUTPUT);
				ImageUtil.copyFile(new File(PATH_INPUT), new File(PATH_OUTPUT));
			}
		}
	}
	
//	private NewRequestResult CallKCBSService(NewRequest requestM,String tranNo ,UserDetailM userM ,String appRecID 
//									,PLXRulesVerificationResultDataM xVerifyM ,ErrorDataM errorM){		
//		
////		com.eaf.orig.ulo.pl.app.utility.NewRequest requestMString = new com.eaf.orig.ulo.pl.app.utility.NewRequest();
////			requestMString.setDataToModel(requestM);
////		
////		ServiceReqRespTool serviceTool = new ServiceReqRespTool();
////		String transId = serviceTool.GenerateTransectionId();
////		String reqRespNo = serviceTool.GenerateReqResNo();
////		
////		log.debug("TransectionId >> "+transId);		
////		log.debug("Save Log OUT Service ReqResp ... "+reqRespNo);			
////		ORIGLogManager daoLog = PLORIGEJBService.getORIGLogManager();
////		
////		if("Y".equalsIgnoreCase(KCBSServiceLOG.CB001_LOG)){
////			daoLog.SaveLogServiceReqResp(this.mappingModelServReqResp(transId, tranNo, reqRespNo, userM.getUserName()
////												, appRecID, requestMString.toString(),
////														OrigConstant.ServiceActivityType.OUT, "", "", ""));
////		}
////		
//		NewRequestResult resultM = null;
////		String respCode = null;
////		String respDesc = null;
////		String errorMessage = null;
////		try{			
////
////			log.debug("Begin Call KCBSRequestService New Request !! ");			
////			KCBSRequestServiceProxy proxy = new KCBSRequestServiceProxy();			
////			resultM = proxy.callNewRequest(requestM, ORIGConfig.KCBS_URL);
////			log.debug("End Call KCBSRequestService New Request !!");
////			
////			respCode = resultM.getStatusCode();
////			respDesc = resultM.getStatusDesc();
////										
////			log.debug("respCode >> "+respCode);
////			log.debug("respDesc >> "+respDesc);
////			
////			if(!OrigUtil.isEmptyObject(resultM) && !OrigUtil.isEmptyString(respCode) 
////							&& OrigConstant.ncbConnectionStatus.COMPLETED.equals(respCode)){	
////				xVerifyM.setNCBResult(NCBConstant.NcbResultDesc.WAITING_NCB_DATA);
////				xVerifyM.setNCBCode(NCBConstant.ncbResultCode.WAITING_NCB_DATA);				
////			}else if(OrigUtil.isEmptyObject(resultM)){	
////				xVerifyM.setNCBResult(NCBConstant.NcbResultDesc.FAIL);
////				xVerifyM.setNCBCode(NCBConstant.ncbResultCode.FAIL);	
////				errorM.setMessage(ErrorUtil.getShortErrorMessage(getRequest(),"SERVICE_DOWN"));
////			}else{			
////				xVerifyM.setNCBResult(NCBConstant.NcbResultDesc.FAIL);
////				xVerifyM.setNCBCode(NCBConstant.ncbResultCode.FAIL);	
////				errorM.setMessage(ErrorUtil.getShortErrorMessage(getRequest(), "DATA_NCB_INCORRECT"));
////			}
////			
////		}catch(Exception e){
////			log.fatal("Exception >> "+e.getMessage());
////			errorMessage = e.getMessage();
////			respCode = "99";
////			respDesc = "Error";			
////			errorM.setMessage(ErrorUtil.getShortErrorMessage(getRequest(),"SERVICE_DOWN"));
////		}finally{
////			log.debug("Save Log IN Service ReqResp ... "+reqRespNo);
////			com.eaf.orig.ulo.pl.app.utility.NewRequestResult newRequestResult = new com.eaf.orig.ulo.pl.app.utility.NewRequestResult();
////			newRequestResult.setDataToModel(resultM);	
////			if("Y".equalsIgnoreCase(KCBSServiceLOG.CB001_LOG)){
////				daoLog.SaveLogServiceReqResp(this.mappingModelServReqResp(transId, tranNo, reqRespNo, userM.getUserName(), appRecID,
////					newRequestResult.toString(), OrigConstant.ServiceActivityType.IN, respCode, respDesc, errorMessage));
////			}
////		}		
//		return resultM;
//	}	
	private ServiceReqRespDataM mappingModelServReqResp(String transId, String tranNo,
										String reqRespNo, String userName, String appRecId,
													String ContentMsg, String activityType, String respCode,
															String respDesc, String errorMessage){
			ServiceReqRespDataM servReqRespM = new ServiceReqRespDataM();
				servReqRespM.setTransId(transId);
				servReqRespM.setServiceId(OrigConstant.ServiceLogID.CB001);
				servReqRespM.setActivityType(activityType);
				servReqRespM.setRefCode(tranNo);
				servReqRespM.setReqRespId(reqRespNo);
				servReqRespM.setCreateBy(userName);
				servReqRespM.setAppId(appRecId);
//				if("Y".equalsIgnoreCase(KCBSServiceLOG.CB001_DATA)){
//					servReqRespM.setContentMsg(ContentMsg);
//				}
				servReqRespM.setRespCode(respCode);
				servReqRespM.setRespDesc(respDesc);
				servReqRespM.setErrorMessage(errorMessage);				
			return servReqRespM;
	}
}
