package com.eaf.service.rest.controller.submitapplication.coverpagetype;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.im.rest.docset.model.InquireDocSetResponse;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.submitapplication.model.SubmitApplicationObjectDataM;
import com.eaf.orig.ulo.service.app.submit.dao.SubmitApplicationManager;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.WorkManagerProxy;
import com.eaf.service.module.model.WorkManagerRequestDataM;
import com.eaf.service.rest.application.model.SubmitApplicationRequest;
import com.eaf.service.rest.controller.submitapplication.SubmitApplication;
import com.eaf.service.rest.model.ServiceResponse;

public class ApplicationAppyTypeNewProcessCJD implements ApplicationAppyTypeProcess {
	
	private static Logger logger = Logger.getLogger(ApplicationAppyTypeNewProcessCJD.class);	
	String PARAM_CODE_JOB_STATE_WAIT_IM = SystemConstant.getConstant("PARAM_CODE_JOB_STATE_WAIT_IM");
	static String WORK_MANAGER_URL = SystemConfig.getProperty("WORK_MANAGER_URL");
	static String WM_ACTION_PRE_APPROVED= SystemConstant.getConstant("WM_ACTION_PRE_APPROVED");
	String WIP_JOBSTATE_DUPLICATE_APP = SystemConfig.getGeneralParam("WIP_JOBSTATE_DUPLICATE_APP");
	static String WM_ACTION_CJD_INCOME = SystemConstant.getConstant("WM_ACTION_CJD_INCOME");
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
				String [] jobStateWaitIMFlow = {PARAM_CODE_JOB_STATE_WAIT_IM};
				logger.debug("jobStateWaitIAFlow : "+jobStateWaitIMFlow);
				//logger.debug("getGeneralParamList : "+SystemConfig.containsGeneralParam(PARAM_CODE_JOB_STATE_WAIT_IM,jobState));
				if(SystemConfig.containsGeneralParam(PARAM_CODE_JOB_STATE_WAIT_IM,jobState) || WIP_JOBSTATE_DUPLICATE_APP.contains(jobState)){
					//call pega dummy
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
						
						requestWorkmanager(applicationGroup,userId);
						logger.debug("workflow task >> XX");
					}
				}
				else{
					//update image
					submitApplication.mapImageIndex(inquireDocSetResponse,applicationGroup);
					LookupServiceCenter.getServiceCenterManager().updateImageDocument(applicationGroup,userId,true);
				}
			}
//			else{
//				logger.debug("createNewApplication..");
//				ArrayList<String> docTypeCheckLists = submitApplication.getDocTypeCheckLists(inquireDocSetResponse);
//				logger.debug("docTypeCheckLists : "+docTypeCheckLists);
//				if(!Util.empty(docTypeCheckLists)){
//					//send pega followup
//					ServiceResponseDataM serviceResponse = submitApplication.sendFollowUpToPega(submitApplicationObject,docTypeCheckLists);
//					String followupToPegaResult = serviceResponse.getStatusCode();
//					logger.debug("followupToPegaResult : "+followupToPegaResult);
//					if(!ServiceResponse.Status.SUCCESS.equals(followupToPegaResult)){
//						processResponse = SubmitApplication.error(serviceResponse);
//					}
//				}else{
//					//create application
//					CreateApplicationRequest createApplicationRequest = new CreateApplicationRequest();
//						createApplicationRequest.setSubmitApplicationRequest(submitApplicationRequest);
//						createApplicationRequest.setInquireDocSetResponse(inquireDocSetResponse);
//						createApplicationRequest.setApplicationGroupId(applicationGroupId);
//						createApplicationRequest.setCoverPageType(coverPageType);
//						createApplicationRequest.setUserId(userId);
//					ApplicationGroupDataM applicationGroup = submitApplication.createNewApplication(createApplicationRequest);
//					applicationGroup.setPegaEventFlag(submitApplicationRequest.getPegaEventFlag());
//					//call pega dummy
//					int lifeCycle = applicationGroup.getMaxLifeCycle();
//					boolean sendDummyToPega = SubmitApplication.sendDummyToPegaFlag(applicationGroupId,lifeCycle);
//					logger.debug("sendDummyToPega : "+sendDummyToPega);
//					if(sendDummyToPega){
//						ServiceResponseDataM serviceResponse = submitApplication.sendDummyToPega(applicationGroup,submitApplicationObject);
//						String followupToPegaResult = serviceResponse.getStatusCode();
//						logger.debug("followupToPegaResult : "+followupToPegaResult);
//						if(!ServiceResponse.Status.SUCCESS.equals(followupToPegaResult)){
//							processResponse = SubmitApplication.error(serviceResponse);
//						}	
//					}
//					//create workflow task
//					String processResult = processResponse.getStatusCode();		
//					logger.debug("processResult : "+processResult);
//					if(ServiceResponse.Status.SUCCESS.equals(processResult)){
//						submitApplication.createWorkflowTask(applicationGroup);
//					}
//				}
//			}				
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(SubmitApplication.error(e));
		}
		return processResponse;
	}
	
	
	
	
	
	public static ProcessActionResponse requestWorkmanager(ApplicationGroupDataM applicationGroup,String userId){
		
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			WorkManagerRequestDataM workManagerRequestDataM = new WorkManagerRequestDataM();
			workManagerRequestDataM.setRefId(applicationGroup.getApplicationGroupId());
			workManagerRequestDataM.setWmFn(WM_ACTION_CJD_INCOME);
			workManagerRequestDataM.setRefCode(applicationGroup.getApplicationGroupNo());
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(WorkManagerProxy.serviceId);
				serviceRequest.setUserId(userId);
				serviceRequest.setEndpointUrl(WORK_MANAGER_URL);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setObjectData(workManagerRequestDataM);
				
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
				//WorkManagerResponse workManagerResponse =(WorkManagerResponse)serivceResponse.getObjectData();
				if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
					processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
				}else{
					processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR+":when  get object data");
				}
			}else{
				processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR);
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
	
	
}
