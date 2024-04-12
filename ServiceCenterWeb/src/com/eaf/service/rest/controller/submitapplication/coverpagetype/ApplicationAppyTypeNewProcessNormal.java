package com.eaf.service.rest.controller.submitapplication.coverpagetype;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.docset.model.InquireDocSetResponse;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.submitapplication.model.SubmitApplicationObjectDataM;
import com.eaf.orig.ulo.service.app.submit.dao.SubmitApplicationManager;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.rest.application.model.SubmitApplicationRequest;
import com.eaf.service.rest.controller.submitapplication.SubmitApplication;
import com.eaf.service.rest.controller.submitapplication.model.CreateApplicationRequest;
import com.eaf.service.rest.model.ServiceResponse;

public class ApplicationAppyTypeNewProcessNormal implements ApplicationAppyTypeProcess {
	
	private static Logger logger = Logger.getLogger(ApplicationAppyTypeNewProcessNormal.class);	
	String PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_DV = SystemConstant.getConstant("PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_DV");
	String PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_FU = SystemConstant.getConstant("PARAM_CODE_WIP_JOBSTATE_WAIT_FOLLOW_FU");
	String PARAM_CODE_JOB_STATE_CANCELLED = SystemConstant.getConstant("PARAM_CODE_JOB_STATE_CANCELLED");
	String PARAM_CODE_JOB_STATE_REJECTED = SystemConstant.getConstant("PARAM_CODE_JOB_STATE_REJECTED");
	String PARAM_CODE_JOB_STATE_DE2_APPROVE_SUBMIT = SystemConstant.getConstant("PARAM_CODE_JOB_STATE_DE2_APPROVE_SUBMIT");

	@Override
	public ProcessResponse processAction(SubmitApplicationObjectDataM objectForm){
		ProcessResponse processResponse = new ProcessResponse();
			processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		try{
			SubmitApplicationObjectDataM submitApplicationObject = (SubmitApplicationObjectDataM)objectForm;
			String userId = submitApplicationObject.getUserId();
			String applicationGroupId = submitApplicationObject.getApplicationGroupId();
			boolean existApplication = submitApplicationObject.isExistApplication();
			logger.debug("userId : "+userId);
			logger.debug("applicationGroupId : "+applicationGroupId);
			logger.debug("existApplication : "+existApplication);
			InquireDocSetResponse inquireDocSetResponse = (InquireDocSetResponse)submitApplicationObject.getInquireDocSetResponse();
			SubmitApplicationRequest submitApplicationRequest = (SubmitApplicationRequest)submitApplicationObject.getSubmitApplicationRequest();
			String coverPageType = submitApplicationObject.getCoverPageType();
			logger.debug("coverPageType : "+coverPageType);
			SubmitApplication submitApplication = new SubmitApplication();
			if(existApplication){
				ApplicationGroupDataM applicationGroup = SubmitApplicationManager.loadApplicationGroup(applicationGroupId);	
				String jobState = applicationGroup.getJobState();
				logger.debug("jobState : "+jobState);
				String [] jobStateEndFlow = {PARAM_CODE_JOB_STATE_CANCELLED,PARAM_CODE_JOB_STATE_REJECTED,PARAM_CODE_JOB_STATE_DE2_APPROVE_SUBMIT};
				logger.debug("jobStateEndFlow : "+jobStateEndFlow);
				if(SystemConfig.getGeneralParamList(jobStateEndFlow).contains(jobState)){
					//call pega dummy
					int lifeCycle = applicationGroup.getMaxLifeCycle();
					boolean sendDummyToPega = SubmitApplication.sendDummyToPegaFlag(applicationGroupId,lifeCycle);
					logger.debug("sendDummyToPega : "+sendDummyToPega);
					if(sendDummyToPega){
						ServiceResponseDataM serviceResponse = submitApplication.sendDummyToPega(applicationGroup,submitApplicationObject);
						String followupToPegaResult = serviceResponse.getStatusCode();
						logger.debug("followupToPegaResult : "+followupToPegaResult);
						if(!ServiceResponse.Status.SUCCESS.equals(followupToPegaResult)){
							processResponse = SubmitApplication.error(serviceResponse);
						}
					}
				}else{
					//update image
					submitApplication.mapImageIndex(inquireDocSetResponse,applicationGroup);
					LookupServiceCenter.getServiceCenterManager().updateImageDocument(applicationGroup,userId,true);	
					//call pega dummy
					int lifeCycle = applicationGroup.getMaxLifeCycle();
					boolean sendDummyToPega = SubmitApplication.sendDummyToPegaFlag(applicationGroupId,lifeCycle);
					logger.debug("sendDummyToPega : "+sendDummyToPega);
					if(sendDummyToPega){
						ServiceResponseDataM serviceResponse = submitApplication.sendDummyToPega(applicationGroup,submitApplicationObject);
						String followupToPegaResult = serviceResponse.getStatusCode();
						logger.debug("followupToPegaResult >> "+followupToPegaResult);
						if(!ServiceResponse.Status.SUCCESS.equals(followupToPegaResult)){
							processResponse = SubmitApplication.error(serviceResponse);
						}
					}
					//update workflow task
					String processResult = processResponse.getStatusCode();		
					logger.debug("processResult : "+processResult);
					if(ServiceResponse.Status.SUCCESS.equals(processResult)){
						Integer instantId = applicationGroup.getInstantId();
						logger.debug("instantId : "+instantId);
						if(SubmitApplication.errorInstant(instantId)){
							submitApplication.workflowTaskProcessAction(applicationGroup,userId);
						}
					}
				}
			}else{
				logger.debug("createNewApplication..");
				ArrayList<String> docTypeCheckLists = submitApplication.getDocTypeCheckLists(inquireDocSetResponse);
				logger.debug("docTypeCheckLists : "+docTypeCheckLists);
				if(!Util.empty(docTypeCheckLists)){
					//send pega followup
					ServiceResponseDataM serviceResponse = submitApplication.sendFollowUpToPega(submitApplicationObject,docTypeCheckLists);
					String followupToPegaResult = serviceResponse.getStatusCode();
					logger.debug("followupToPegaResult : "+followupToPegaResult);
					if(!ServiceResponse.Status.SUCCESS.equals(followupToPegaResult)){
						processResponse = SubmitApplication.error(serviceResponse);
					}
				}else{
					//create application
					CreateApplicationRequest createApplicationRequest = new CreateApplicationRequest();
						createApplicationRequest.setSubmitApplicationRequest(submitApplicationRequest);
						createApplicationRequest.setInquireDocSetResponse(inquireDocSetResponse);
						createApplicationRequest.setApplicationGroupId(applicationGroupId);
						createApplicationRequest.setCoverPageType(coverPageType);
						createApplicationRequest.setUserId(userId);
					ApplicationGroupDataM applicationGroup = submitApplication.createNewApplication(createApplicationRequest);
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
							processResponse = SubmitApplication.error(serviceResponse);
						}	
					}
					//create workflow task
					String processResult = processResponse.getStatusCode();		
					logger.debug("processResult : "+processResult);
					if(ServiceResponse.Status.SUCCESS.equals(processResult)){
						submitApplication.createWorkflowTask(applicationGroup);
					}
				}
			}				
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(SubmitApplication.error(e));
		}
		return processResponse;
	}
}
