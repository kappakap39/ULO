package com.eaf.orig.ulo.app.view.form.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.postapproval.CardLinkAction;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.pega.UpdateApprovalStatus;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.inf.batch.ulo.pega.InfBatchUpdateApprovalStatus;
import com.eaf.inf.batch.ulo.pega.model.CSVContentDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.postapproval.action.PostApprovalAction;
import com.eaf.orig.ulo.app.postapproval.action.PostApprovalAction.PostApprovalActionResult;
import com.eaf.orig.ulo.app.view.form.cis.compare.CISCompareAction;
import com.eaf.orig.ulo.app.view.util.capport.CapPortTransactionUtil;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusData;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequest;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.util.ServiceErrorResponseUtil;
import com.eaf.service.module.manual.UpdateApprovalStatusServiceProxy;

@SuppressWarnings("serial")
public class _ProcessActionDE2 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(ProcessActionDE2.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
 	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
 	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
 	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
 	String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
 	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	@Override
	public HashMap<String, Object> validateAction(HttpServletRequest request,Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		return formError.getFormError();
	}
	@Override
	public Object preProcessAction() {
		return null;
	}
	@Override
	public Object processAction() {
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
		try{
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
			String roleId = FormControl.getFormRoleId(request);
			logger.debug("roleId : "+roleId);
			for(PersonalInfoDataM personalInfo : personalInfos){
				if(!Util.empty(personalInfo)){
					 HashMap<String, CompareDataM> comparisonFields = applicationGroup.getComparisonField(CompareDataM.SoruceOfData.CIS);
					 ProcessResponse processResponse = PersonalInfoUtil.updateComparisonsProcess(personalInfo,comparisonFields,CompareDataM.SoruceOfData.CIS,applicationGroup.getUserId(),roleId);
					 logger.debug("modifyComparisons resultCode : "+processResponse.getStatusCode());
					 if(!ServiceResponse.Status.SUCCESS.equals(processResponse.getStatusCode())){
						 processActionResponse.setResultCode(processResponse.getStatusCode());
						 processActionResponse.setResultDesc(processResponse.getData());
						 return processActionResponse;
					 }
					 PersonalInfoUtil.setComparisonField(applicationGroup, comparisonFields);
					 CISCompareAction cisCompareProcessor = new CISCompareAction();			
						HashMap<String,CompareDataM> cisCompareFields = cisCompareProcessor.processAction(applicationGroup, roleId);
						if(!Util.empty(cisCompareFields)){
							ComparisonGroupDataM comparisonGroup = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.CIS);
							if(null == comparisonGroup){
								logger.debug(">>>>>comparisonGroup is null >>>");
								comparisonGroup = new ComparisonGroupDataM();
								comparisonGroup.setSrcOfData(CompareDataM.SoruceOfData.CIS);
								applicationGroup.addComparisonGroups(comparisonGroup);
								comparisonGroup.setComparisonFields(cisCompareFields);
							}					
						}
				}
			}
			//CIS
			if(!Util.empty(personalInfos)){
				for(PersonalInfoDataM personalInfo : personalInfos){
					String resultCode = this.postApprovalProcess(null, personalInfo,processActionResponse, applicationGroup,true);
					if(!ServiceResponse.Status.SUCCESS.equals(resultCode)){
						return processActionResponse;
					}
				}
			}
			PersonalInfoDataM applicantSort = new PersonalInfoDataM();
			applicantSort.setSortType(PersonalInfoDataM.SORT_TYPE.ASC);
			if(!Util.empty(personalInfos)){
				Collections.sort(personalInfos,applicantSort);
				for(PersonalInfoDataM personalInfo : personalInfos){
					ArrayList<ApplicationDataM> aplications = applicationGroup.getApplications(personalInfo.getPersonalId(), personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
					if(!Util.empty(aplications)){
						Collections.sort(aplications,new ApplicationDataM());
						for(ApplicationDataM application: aplications){
							String resultCode = this.postApprovalProcess(application,null, processActionResponse, applicationGroup,false);
							if(!ServiceResponse.Status.SUCCESS.equals(resultCode)){
								return processActionResponse;
							}
							if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
								if(null!=application.getLoans()){
									for(LoanDataM loan : application.getLoans()){ 
										if(null!=loan.getCashTransfers()){
											for(CashTransferDataM cashTransfer: loan.getCashTransfers()){
												if(null!=cashTransfer.getPercentTransfer()&&null!=loan.getLoanAmt()){
													BigDecimal firstTransferAmount = BigDecimal.ZERO;
													try{
														firstTransferAmount = loan.getLoanAmt().multiply(cashTransfer.getPercentTransfer().divide(new BigDecimal(100)));
													}catch(Exception e){
													}
													cashTransfer.setFirstTransferAmount(firstTransferAmount);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			ApplicationUtil.setAdditionalService(applicationGroup);		
			ApplicationUtil.defaultCardLinkCustId(applicationGroup);	
			if(APPLICATION_TYPE_INCREASE.equals(applicationGroup.getApplicationType()) && SystemConstant.lookup("JOBSTATE_REJECT", applicationGroup.getJobState())){
				PersonalAddressUtil.setAddressCardLinkIncrease(applicationGroup);
			}
//			ProcessCardLinkAction processCardLink = new ProcessCardLinkAction();
//				processCardLink.doAction(applicationGroup);
			CardLinkAction processCardLink = new CardLinkAction();
				processCardLink.processCardlinkAction(applicationGroup);
			String postApprovalResult = processActionResponse.getResultCode();
			logger.debug("postApprovalResult : "+postApprovalResult);
			if(ServiceResponse.Status.SUCCESS.equals(postApprovalResult)){			
				UpdateApprovalStatusData updateApprovalStatusData = UpdateApprovalStatus.validateUpdateApprovalStatus(applicationGroup);				
				String isClose = updateApprovalStatusData.getIsClose();
				String isVetoEligible = updateApprovalStatusData.getIsVetoEligible();		
				logger.debug("updateApprovalStatusData : "+updateApprovalStatusData);
				if(MConstant.FLAG.NO.equals(isClose) && MConstant.FLAG.YES.equals(isVetoEligible)){
					//Call pega
					ArrayList<ApplicationGroupDataM> applicationGroups = new ArrayList<ApplicationGroupDataM>();
						applicationGroups.add(applicationGroup);
					ArrayList<CSVContentDataM> csvContents = InfBatchUpdateApprovalStatus.mapCSVContent(applicationGroups,isClose,isVetoEligible);
					UpdateApprovalStatusRequest updateApprovalRequest = new UpdateApprovalStatusRequest();
					updateApprovalRequest.setCSVContent(InfBatchUpdateApprovalStatus.getCSVContent(csvContents));					
					String UPDATE_APPROVAL_STATUS_URL = SystemConfig.getProperty("UPDATE_APPROVAL_STATUS_URL");
					ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
						serviceRequest.setServiceId(UpdateApprovalStatusServiceProxy.serviceId);
						serviceRequest.setUserId(userM.getUserName());
						serviceRequest.setEndpointUrl(UPDATE_APPROVAL_STATUS_URL);
						serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
						serviceRequest.setObjectData(updateApprovalRequest);
						serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());					
					ServiceCenterProxy proxy = new ServiceCenterProxy();
					ServiceResponseDataM serviceResponse = proxy.requestService(serviceRequest,transactionId);
					String updateApprovalStatusResult = serviceResponse.getStatusCode();
					logger.debug("updateApprovalStatusResult >> "+updateApprovalStatusResult);
					processActionResponse.setResultCode(updateApprovalStatusResult);
					if(!ServiceResponse.Status.SUCCESS.equals(updateApprovalStatusResult)){
						return ServiceErrorResponseUtil.fail(serviceResponse);
					}
				}
				
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setResultDesc(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
	
	private  String  postApprovalProcess(ApplicationDataM  aplicationInfo, PersonalInfoDataM personalInfo,ProcessActionResponse processActionResponse, ApplicationGroupDataM applicationGroup ,boolean isPostApprovalCIS){
		String postApprovalResult="";
		try {
			logger.debug("isPostApprovalCIS : "+isPostApprovalCIS);
			PostApprovalAction approvalProcess = new PostApprovalAction(applicationGroup,aplicationInfo,personalInfo);
			PostApprovalActionResult postApprovalActionResult = approvalProcess.processPostApprvalAction(isPostApprovalCIS);
			postApprovalResult = postApprovalActionResult.getStatusCode();
			logger.debug("postApprovalResult : "+postApprovalResult);
			if(!ServiceResponse.Status.SUCCESS.equals(postApprovalResult)){
				processActionResponse.setResultCode(postApprovalResult);
				ErrorData errorData = postApprovalActionResult.getErrorData();
				if(Util.empty(errorData)){
					errorData = new ErrorData();
				}
				processActionResponse.setResultDesc(errorData.getErrorInformation());
				processActionResponse.setErrorMsg(errorData.getErrorInformation());
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setResultDesc(e.getLocalizedMessage());
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		return  postApprovalResult;
	}
	
	@Override
	public Object postProcessAction() {
		return null;
	}
}
