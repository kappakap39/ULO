package com.eaf.service.rest.controller.submitapplication.coverpagetype;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.docset.model.InquireDocSetResponse;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.submitapplication.model.SubmitApplicationObjectDataM;
import com.eaf.orig.ulo.service.app.submit.dao.SubmitApplicationManager;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.rest.application.model.SubmitApplicationRequest;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.controller.submitapplication.SubmitApplication;
import com.eaf.service.rest.controller.submitapplication.model.CreateApplicationRequest;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;

public class ApplicationAppyTypeFollowProcessCJD implements ApplicationAppyTypeProcess {
	
	private static Logger logger = Logger.getLogger(ApplicationAppyTypeFollowProcessCJD.class);	
	String WIP_JOBSTATE_WAIT_FOLLOW_DV = SystemConstant.getConstant("PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_DV");
	String WIP_JOBSTATE_WAIT_FOLLOW_FU = SystemConstant.getConstant("PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_FU");
	String DECISION_SERVICE_POINT_DOC_COMPLETE =SystemConstant.getConstant("DECISION_SERVICE_POINT_DOC_COMPLETE");
	String DECISION_SERVICE_POINT_BUREAU_DOC =SystemConstant.getConstant("DECISION_SERVICE_POINT_BUREAU_DOC");
	String DECISION_SERVICE_POINT_INCOME =SystemConstant.getConstant("DECISION_SERVICE_POINT_INCOME");
	String JOBSTATE_FOLLOW_SAVING_PLUS = SystemConstant.getConstant("JOBSTATE_FOLLOW_SAVING_PLUS");
	@Override
	public ProcessResponse processAction(SubmitApplicationObjectDataM objectForm){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		try{
			SubmitApplicationObjectDataM submitApplicationObject = objectForm;
			InquireDocSetResponse inquireDocSetResponse = (InquireDocSetResponse)submitApplicationObject.getInquireDocSetResponse();
			SubmitApplicationRequest submitApplicationRequest = (SubmitApplicationRequest)submitApplicationObject.getSubmitApplicationRequest();
			String qr2 = submitApplicationRequest.getQr2();
			String applicationGroupId =SubmitApplicationManager.selectApplicationGroupId(qr2);
			String coverPageType = submitApplicationObject.getCoverPageType();
			String userId = submitApplicationObject.getUserId();
			SubmitApplication submitApplication = new SubmitApplication();
			logger.debug("qr2 : "+qr2);
			logger.debug("coverPageType : "+coverPageType);
			logger.debug("applicationGroupId : "+applicationGroupId);
			ArrayList<String> docTypeCheckLists = submitApplication.getDocTypeCheckLists(inquireDocSetResponse);
			logger.debug("docTypeCheckLists : "+docTypeCheckLists);
			if(!Util.empty(applicationGroupId)){
				ApplicationGroupDataM applicationGroup = SubmitApplicationManager.loadApplicationGroup(applicationGroupId);
				applicationGroup.setPegaEventFlag(submitApplicationRequest.getPegaEventFlag());
				String jobState = applicationGroup.getJobState();
				logger.debug("jobState : "+jobState);				

				if(SystemConfig.containsGeneralParam("WIP_JOBSTATE_WAIT_FOLLOW_DV",jobState)){

					submitApplication.mapImageIndex(inquireDocSetResponse, applicationGroup);
					
					applicationGroup.setUserId(applicationGroup.getSourceUserId());
					ProcessActionResponse decisionResponse = EAppAction.requestDecision(applicationGroup, DecisionServiceUtil.DECISION_POINT.DECISION_POINT_VERIFICATION, userId
							, DecisionServiceUtil.FROM_FOLLOW_ACTION);
					
					
					LookupServiceCenter.getServiceCenterManager().saveApplication(applicationGroup,userId,true);
					
					String decisionResultCode = decisionResponse.getResultCode();						
						
					if(ServiceResponse.Status.SUCCESS.equals(decisionResultCode)){
						
						
						String docCompletedFlag = applicationGroup.getDocCompletedFlag();		
						logger.debug("docCompletedFlag : "+docCompletedFlag);
						if(MConstant.FLAG_N.equals(docCompletedFlag) || !Util.empty(docTypeCheckLists)){
							//send pega followup
							ServiceResponseDataM serviceResponse = submitApplication.sendFollowUpToPega(applicationGroup,submitApplicationObject);
							String followupToPegaResult = serviceResponse.getStatusCode();
							logger.debug("followupToPegaResult : "+followupToPegaResult);
							if(!ServiceResponse.Status.SUCCESS.equals(followupToPegaResult)){
								processResponse =  SubmitApplication.error(serviceResponse);
							}
						}else{
							
								ServiceResponseDataM serviceResponse = submitApplication.sendDummyToPega(applicationGroup,submitApplicationObject);
								String followupToPegaResult = serviceResponse.getStatusCode();
								logger.debug("followupToPegaResult : "+followupToPegaResult);
								if(!ServiceResponse.Status.SUCCESS.equals(followupToPegaResult)){
									processResponse =  SubmitApplication.error(serviceResponse);
								}
						}
					}else{
						processResponse.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, decisionResponse.getResultCode(), decisionResponse.getErrorMsg()));
					}
					
					
				}else{
					//update image
					submitApplication.mapImageIndex(inquireDocSetResponse, applicationGroup);
					LookupServiceCenter.getServiceCenterManager().updateImageDocument(applicationGroup,userId,true);
					applicationGroup.setPegaEventFlag(submitApplicationRequest.getPegaEventFlag());
					//call pega dummy
					int lifeCycle = applicationGroup.getMaxLifeCycle();
					boolean sendDummyToPega = SubmitApplication.sendDummyToPegaFlag(applicationGroupId,lifeCycle);
					logger.debug("sendDummyToPega : "+sendDummyToPega);
					if(sendDummyToPega){
						ServiceResponseDataM serviceResponse = submitApplication.sendDummyToPega(applicationGroup,submitApplicationObject);
						String followupToPegaResult = serviceResponse.getStatusCode();
						logger.debug("followupToPegaResult : "+followupToPegaResult);
						if(!ServiceResponse.Status.SUCCESS.equals(followupToPegaResult)){
							processResponse =  SubmitApplication.error(serviceResponse);
						}
					}
					//update workflow task
					String processResult = processResponse.getStatusCode();		
					logger.debug("processResult : "+processResult);

				}				
			}else{
				processResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				processResponse.setErrorData(SubmitApplication.error(ErrorData.ErrorType.SERVICE_RESPONSE
						, ProcessResponse.SubmitApplicationErrorCode.SUBMIT_APPLICATION_ERROR_NOT_FOUND_APPLICATION_FOLLOW
						, MessageErrorUtil.getText("SUBMIT_APPLICATION_ERROR_NOT_FOUND_APPLICATION_FOLLOW")));
				
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(SubmitApplication.error(e));
		}
		return processResponse;
	}

	
}