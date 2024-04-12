package com.eaf.orig.ulo.app.view.util.compare;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.QueryResultController;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.model.SQLDataM;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.callIM.model.CallIMRequest;
import com.eaf.im.rest.callIM.model.CallIMResponse;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.CompareSignatureDAO;
import com.eaf.orig.ulo.app.dao.CompareSignatureFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.comparesignature.CompareSignatureDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.CallIMControlProxy;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class CompareSignature implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(CompareSignature.class);
	String COMPARE_SIGNATURE_YES = SystemConstant.getConstant("COMPARE_SIGNATURE_YES");
	String COMPARE_SIGNATURE_NO = SystemConstant.getConstant("COMPARE_SIGNATURE_NO");
	String DOCUMENT_CODE_CONSENT = SystemConstant.getConstant("DOCUMENT_CODE_CONSENT");
	String IM_COMPARE_SIGNATURE_PART = SystemConfig.getProperty("IM_COMPARE_SIGNATURE_PART");
	String[] ADDITIONAL_DOC_COMPARE = SystemConstant.getArrayConstant("ADDITIONAL_DOC_COMPARE");
	String ADDITIONAL_DOC_COMPARE_FLAG = SystemConstant.getConstant("ADDITIONAL_DOC_COMPARE_FLAG");
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	String transactionId;
	@Override
	public ResponseData processAction(HttpServletRequest request){	
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.COMPARE_SIGNATURE);
		CompareSignatureDataM  compareSignatureDataM = new CompareSignatureDataM();
		transactionId = (String)request.getSession().getAttribute("transactionId");
		try{
			logger.debug("CompareSignature.....");	
			String compareSignatureResult = COMPARE_SIGNATURE_NO;
			UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			String docCompleteFlag = applicationGroup.getDocCompletedFlag();
			logger.debug("docCompleteFlag >> "+docCompleteFlag);

			if(!Util.empty(docCompleteFlag)){
				compareSignatureResult = COMPARE_SIGNATURE_YES;
				ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
				for (PersonalInfoDataM personalInfo : personalInfos) {
					VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
					if(null != verificationResult){
						verificationResult.setCompareSignatureResult(compareSignatureResult);
					}
				}
//				#chatmongkol DF000000000406 EX-IM : Call IM error for Compare Signature: No display error on IM screen#
//				CallIMResponse callIMResponse = callIMService(applicationGroup,userM); 
				ServiceResponseDataM serviceResponse = callIMService(applicationGroup,userM);
				if(ServiceResponse.Status.SUCCESS.equals(serviceResponse.getStatusCode())){
					CallIMResponse callIMResponse = (CallIMResponse)serviceResponse.getObjectData();
					compareSignatureDataM = mappingCompareSignatureData(applicationGroup,callIMResponse);
				}else{
					return responseData.error(serviceResponse.getErrorInfo());
				}
				
			}
			logger.debug("compareSignatureResult >> "+compareSignatureResult);
			compareSignatureDataM.setCompareSignatureFlag(compareSignatureResult);
			compareSignatureDataM.setDefaultAppformPage(findDefaultAppformPage(applicationGroup.getApplicationTemplate()));
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		} 
		Gson gson = new Gson();
 		return responseData.success(gson.toJson(compareSignatureDataM));
	}
	
	private String findDefaultAppformPage(String templateId) throws Exception{
		String page = "1";
		ArrayList<String> SMART_DATA_APPLY_DATE_FIELD = SystemConstant.getArrayListConstant("SMART_DATA_APPLY_DATE_FIELD");
		logger.debug("SMART_DATA_APPLY_DATE_FIELD : "+SMART_DATA_APPLY_DATE_FIELD);	
		if(!Util.empty(SMART_DATA_APPLY_DATE_FIELD)){
			QueryResultController queryController = new QueryResultController();
			String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
			String smartId = CacheControl.getName(CACHE_TEMPLATE,templateId,"SM_TEMPLATE_CODE");
			SQLDataM sqlM = new SQLDataM();
			StringBuilder SQL = new StringBuilder("");
			SQL.append("SELECT PAGENUMBER FROM SM_MAIN WHERE SM_MAIN.TEMPLATEID = ? AND SM_MAIN.FIELDID IN (");
			String COMMA = "";
			for(String FIELD : SMART_DATA_APPLY_DATE_FIELD){
				SQL.append(COMMA+"?");
				COMMA = ",";
			}
			SQL.append(")");
			sqlM.setSQL(SQL.toString());
			int index = 1;
			sqlM.setString(index++,smartId);
			for(String FIELD : SMART_DATA_APPLY_DATE_FIELD){
				sqlM.setString(index++,FIELD);
			}
			HashMap result = queryController.loadResult(sqlM);
			page = QueryResultController.display(result,"PAGENUMBER");
		}
		return page;
	}
	
	private ServiceResponseDataM callIMService(ApplicationGroupDataM applicationGroup ,UserDetailM userM ) throws Exception{
		CallIMResponse callIMResponse = new CallIMResponse();
		ServiceResponseDataM serivceResponse = new ServiceResponseDataM();
		try{
			CallIMRequest callIMRequest  = new CallIMRequest();
				callIMRequest.setBranchCode(applicationGroup.getBranchNo());
				callIMRequest.setRequestTokenOnly(MConstant.FLAG_N);
				callIMRequest.setSetID(applicationGroup.getApplicationGroupNo());
				callIMRequest.setRCCode(applicationGroup.getRcCode());
				callIMRequest.setScanChannel(applicationGroup.getApplyChannel());
			
			String URL = SystemConfig.getProperty("IM_TOKEN_URL");
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(CallIMControlProxy.serviceId);
				serviceRequest.setUserId(userM.getUserName());
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				serviceRequest.setObjectData(callIMRequest);
				
				ServiceCenterProxy proxy = new ServiceCenterProxy();
				serivceResponse = proxy.requestService(serviceRequest,transactionId);
//				
//				callIMResponse = (CallIMResponse)serivceResponse.getObjectData();
//			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
//				callIMResponse = (CallIMResponse)serivceResponse.getObjectData();
//			}else{
//				callIMResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
//			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
//			serivceResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			
		}
		return serivceResponse;
	}
	
	private CompareSignatureDataM mappingCompareSignatureData(ApplicationGroupDataM applicationGroup,CallIMResponse callIMResponse){
		CompareSignatureDataM  compareSignatureDataM = new CompareSignatureDataM();	
		try{
			if(null != callIMResponse){
				String URL = callIMResponse.getIMURL()+IM_COMPARE_SIGNATURE_PART;
				logger.debug("URL>>"+URL);
				compareSignatureDataM.setUrl(URL);
				compareSignatureDataM.setTokenID(callIMResponse.getTokenID());
			}
			//compareSignatureDataM.setOldDocTypeCode(DOCUMENT_CODE_CONSENT);
			compareSignatureDataM.setCurrentSetID(applicationGroup.getApplicationGroupNo());
			compareSignatureDataM.setCurrentDocTypeCodes(currentDocumentTypecodes(applicationGroup));			
			CompareSignatureDAO dao = CompareSignatureFactory.getCompareSignatureDAO();
			
			//Ignore searching for previous app in case of only Sup.
			String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			if(personalInfo != null){
				dao.selectOldApplicationInfo(compareSignatureDataM, applicationGroup);
			}			
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
		}
		return compareSignatureDataM;
	}
	
	private ArrayList<String> currentDocumentTypecodes(ApplicationGroupDataM applicationGroup){
		ArrayList<String> documenTypeCodes = new ArrayList<String>();
//		PersonalInfoDataM personalInfoDataM = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
		if(!Util.empty(personalInfos)){
		for(PersonalInfoDataM personalInfoDataM:personalInfos){
			VerificationResultDataM verificationResultDataM = personalInfoDataM.getVerificationResult();
			if(!Util.empty(verificationResultDataM)){
				ArrayList<RequiredDocDataM>  requiredDocs =verificationResultDataM.getRequiredDocs();
				if(!Util.empty(requiredDocs)){
					for (RequiredDocDataM requiredDoc : requiredDocs) {
						ArrayList<RequiredDocDetailDataM> requiredDocDetails = requiredDoc.getRequiredDocDetails();
						if(!Util.empty(requiredDocDetails)){
							for(RequiredDocDetailDataM requiredDocDetailDataM :requiredDocDetails){
								//if(MConstant.FLAG_Y.equals(requiredDocDetailDataM.getMandatoryFlag()) || SystemConstant.lookup("COMPARE_SIGNATURE_OPTIONAL_DOCUMENT_TYPE", requiredDocDetailDataM.getDocumentCode())){
								if(MConstant.FLAG_Y.equals(requiredDocDetailDataM.getMandatoryFlag())){
									String docCode = requiredDocDetailDataM.getDocumentCode();
									logger.debug(">>docCode>>"+docCode);
									if(!documenTypeCodes.contains(docCode)){
										documenTypeCodes.add(docCode);
									}
									
								}
							}
						}
					}
				}
				if(!Util.empty(ADDITIONAL_DOC_COMPARE)){
					for (String additionalDoc : ADDITIONAL_DOC_COMPARE) {
						documenTypeCodes.add(additionalDoc);
					}
				}
			}
		}
		}
		return documenTypeCodes;
	}
}
