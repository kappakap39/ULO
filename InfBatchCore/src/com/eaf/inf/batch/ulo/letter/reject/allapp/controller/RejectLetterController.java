package com.eaf.inf.batch.ulo.letter.reject.allapp.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterApplicationDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterBuildTemplateEntity;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterConfigDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterInterfaceResponse;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterProcessDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateMasterRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateRejectProductRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.generate.GenerateRejectLetterAllAppTemplate;
import com.eaf.inf.batch.ulo.letter.reject.template.product.BundleAllAppTemplateRejectReason;
import com.eaf.inf.batch.ulo.letter.reject.template.product.inf.RejectTemplateReasonProductInf;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;


public class RejectLetterController {
	private static transient Logger logger = Logger.getLogger(RejectLetterController.class);
	public TaskExecuteDataM create(RejectLetterDataM rejectLetter,RejectLetterConfigDataM config){
		TaskExecuteDataM taskExecuteDataM = new TaskExecuteDataM();
		try {
			HashMap<String,RejectTemplateMasterRequestDataM> hRejectTemplateMasterRequests = new HashMap<String,RejectTemplateMasterRequestDataM>();
			ArrayList<String> products = rejectLetter.getProducts();
			logger.debug("products.size() >> "+products.size());
			if(!InfBatchUtil.empty(products)){
				boolean isBundle = products.size()>1;
				if(isBundle){
					createBundle(rejectLetter,config,products,hRejectTemplateMasterRequests,taskExecuteDataM);
				}
				else{
					createSingle(rejectLetter,config,products,hRejectTemplateMasterRequests,taskExecuteDataM);
				}
			}else{
				String errorMsg = "No product";
				taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.WARNING);
				taskExecuteDataM.setResultDesc(errorMsg);
				//taskExecuteDataM.setFailObject(rejectLetter);
				ArrayList<InfBatchLogDataM> InfBatchLogs = generateErrorProcess(hRejectTemplateMasterRequests,rejectLetter,errorMsg);
				taskExecuteDataM.setFailObject(InfBatchLogs);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecuteDataM.setResultDesc(e.getLocalizedMessage());
			//taskExecuteDataM.setFailObject(rejectLetter);
			ArrayList<InfBatchLogDataM> InfBatchLogs = new ArrayList<InfBatchLogDataM>(); 
			try{
				InfBatchLogs = generateErrorProcess(rejectLetter,e.getLocalizedMessage());
			}catch(Exception e2){
				logger.fatal("ERROR ",e2);
			}finally{
				taskExecuteDataM.setFailObject(InfBatchLogs);
			}
		}		
		return taskExecuteDataM;
	}
	private ArrayList<RejectLetterInterfaceResponse> generateTemplateProcess(HashMap<String,RejectTemplateMasterRequestDataM> rejectTemplateMasterRequests) throws Exception{
		ArrayList<RejectLetterInterfaceResponse> rejectLetterInterfaceList = new ArrayList<RejectLetterInterfaceResponse>();
		if(!InfBatchUtil.empty(rejectTemplateMasterRequests)){
			ArrayList<String> keyMappings = new ArrayList<>(rejectTemplateMasterRequests.keySet());
			for(String keyName :keyMappings){
				RejectTemplateMasterRequestDataM rejectTemplateMasterReq = rejectTemplateMasterRequests.get(keyName);
				//GenerateRejectLetetrTemplate generateRejectLetetrTemplate = new GenerateRejectLetetrTemplate();
				GenerateRejectLetterAllAppTemplate template = new GenerateRejectLetterAllAppTemplate();
				TemplateBuilderResponse templateBuilderResp = template.buildTemplate(rejectTemplateMasterReq);
				RejectLetterInterfaceResponse rejectInterfaceRes = template.generateInterfaceTemplate(templateBuilderResp, rejectTemplateMasterReq);
				if(!InfBatchUtil.empty(rejectInterfaceRes)){
					rejectLetterInterfaceList.add(rejectInterfaceRes);
				}
			}
		}
		return rejectLetterInterfaceList;
	}
	private ArrayList<InfBatchLogDataM> generateErrorProcess(RejectLetterDataM rejectLetter,String errorMsg)throws Exception{
		return generateErrorProcess(null,rejectLetter,errorMsg);
	}
	private ArrayList<InfBatchLogDataM> generateErrorProcess(HashMap<String,RejectTemplateMasterRequestDataM> rejectTemplateMasterRequests,RejectLetterDataM rejectLetter,String errorMsg)throws Exception{
		String REJECT_LETTER_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_CODE");
		String INTERFACE_STATUS_NOT_SEND = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_NOT_SEND");
		ArrayList<InfBatchLogDataM> infBatchLogs = new ArrayList<InfBatchLogDataM>();
		ArrayList<String> applicationRecordIds = new ArrayList<String>();
		if(!InfBatchUtil.empty(rejectLetter)){
			ArrayList<RejectLetterApplicationDataM> rejectLetterApplications = rejectLetter.getRejectLetterApplications();
			if(!InfBatchUtil.empty(rejectLetterApplications)){
				for(RejectLetterApplicationDataM rejectLetterApplication : rejectLetterApplications){
					if(!InfBatchUtil.empty(rejectLetterApplication)){
						String APPLICATION_RECORD_ID = rejectLetterApplication.getApplicationRecordId();
						if(!applicationRecordIds.contains(APPLICATION_RECORD_ID)){
							applicationRecordIds.add(APPLICATION_RECORD_ID);
						}
					}
				}
			}
		}
		if(!InfBatchUtil.empty(rejectTemplateMasterRequests)){
			ArrayList<String> keyMappings = new ArrayList<>(rejectTemplateMasterRequests.keySet());
			for(String keyName :keyMappings){
				RejectTemplateMasterRequestDataM rejectTemplateMasterReq = rejectTemplateMasterRequests.get(keyName);
				RejectLetterProcessDataM rejectLetterProcess = (RejectLetterProcessDataM)rejectTemplateMasterReq.getTemplateBuilderRequest().getRequestObject();
				if(!InfBatchUtil.empty(rejectLetterProcess)){
					String applicationgroupId = rejectLetterProcess.getApplicationGroupId();
					String language = rejectLetterProcess.getLanguage();
					//String sendTo = rejectLetterProcess.getSendTo();
					int lifeCycle = rejectLetterProcess.getLifeCycle();
					for(String appReordId : applicationRecordIds){
						InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
							infBatchLog.setApplicationRecordId(appReordId);
							infBatchLog.setInterfaceCode(REJECT_LETTER_INTERFACE_CODE);
							infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND);
							infBatchLog.setApplicationGroupId(applicationgroupId);
							infBatchLog.setLifeCycle(lifeCycle);
							infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
							infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
							infBatchLog.setLogMessage(errorMsg);
							infBatchLog.setSystem02(language);
						infBatchLogs.add(infBatchLog);
					}
				}
			}
		}else{
			if(!InfBatchUtil.empty(rejectLetter)){
				String applicationgroupId = rejectLetter.getApplicationGroupId();
				String language = "";
				//String sendTo = rejectLetterProcess.getSendTo();
				int lifeCycle = rejectLetter.getLifeCycle();
				for(String appReordId : applicationRecordIds){
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setApplicationRecordId(appReordId);
						infBatchLog.setInterfaceCode(REJECT_LETTER_INTERFACE_CODE);
						infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND);
						infBatchLog.setApplicationGroupId(applicationgroupId);
						infBatchLog.setLifeCycle(lifeCycle);
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						infBatchLog.setLogMessage(errorMsg);
						infBatchLog.setSystem02(language);
					infBatchLogs.add(infBatchLog);
				}
			}
		}
		return infBatchLogs;
	}
	private TemplateBuilderRequest getTemplateBuilderRequest(RejectLetterProcessDataM rejectLetterProcessDataM) {
		TemplateBuilderRequest templateBuilder = new TemplateBuilderRequest();
		templateBuilder.setTemplateId("");
		templateBuilder.setTemplateType(TemplateBuilderConstant.TemplateType.REJECT_LETTER);
		templateBuilder.setRequestObject(rejectLetterProcessDataM);
		return templateBuilder;
	}
	public RejectTemplateReasonProductInf getRejectLetterProcessData(String product) throws Exception{
		RejectTemplateReasonProductInf rejectTemplateReasonProductInf = null;
		logger.debug("product >> "+product);
		if(!InfBatchUtil.empty(product)){
			String PROCESS_CLASS_NAME = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_ALL_APP_"+product);
			logger.debug("PROCESS_CLASS_NAME >> "+PROCESS_CLASS_NAME);	
			rejectTemplateReasonProductInf = (RejectTemplateReasonProductInf)Class.forName(PROCESS_CLASS_NAME).newInstance();
		}
		return rejectTemplateReasonProductInf;
	}
	
	private void createSingle(RejectLetterDataM rejectLetter,RejectLetterConfigDataM config, ArrayList<String> products,HashMap<String,RejectTemplateMasterRequestDataM> hRejectTemplateMasterRequests,TaskExecuteDataM taskExecuteDataM) throws Exception{
		for(String product : products){
			RejectTemplateReasonProductInf templateRejectReasonInf = getRejectLetterProcessData(product);
			if(!InfBatchUtil.empty(templateRejectReasonInf)){
				TemplateRejectProductRequestDataM templateRequest = new TemplateRejectProductRequestDataM();
					templateRequest.setRequestObject(rejectLetter);
					templateRequest.setConfig(config);
				ArrayList<RejectLetterProcessDataM>   rejectLetterProcessList = templateRejectReasonInf.getTemplateRejectProduct(templateRequest);
				if(null!=rejectLetterProcessList){
					for(RejectLetterProcessDataM rejectLetterProcessDataM : rejectLetterProcessList){
						templateRejectReasonInf.setTemplateBuilderRequest(getTemplateBuilderRequest(rejectLetterProcessDataM));
						TemplateMasterDataM templateMasterDataM = templateRejectReasonInf.getTemplateMaster();
						String templateId = templateMasterDataM.getTemplateId();
						String templateCode = templateMasterDataM.getTemplateCode();
						String rejectProduct = templateMasterDataM.getRejectLetterProduct();
						String applyType = templateMasterDataM.getApplyType();
						String rejectTemplateType = templateMasterDataM.getRejectLetterTemplateType();
						String keyMapping = templateId+"_"+templateCode+"_"+rejectProduct+"_"+applyType+"_"+rejectTemplateType;
//						logger.debug("keyMapping >> "+keyMapping);
//						logger.debug("templateId >> "+templateId);
//						logger.debug("templateCode >> "+templateCode);
//						logger.debug("rejectProduct >> "+rejectProduct);
//						logger.debug("applyType >> "+applyType);
//						logger.debug("rejectTemplateType >> "+rejectTemplateType);
						if(rejectLetterProcessDataM.isEmail()){
							templateMasterDataM.setGeneratePaperOnly(false);
						}else {
							templateMasterDataM.setGeneratePaperOnly(true);
						}
						if(rejectLetterProcessDataM.isEmailAllAfp()){
							templateMasterDataM.setEmailAllAfp(true);
						}else{
							templateMasterDataM.setEmailAllAfp(false);
						}
						if(hRejectTemplateMasterRequests.containsKey(keyMapping)){
							RejectTemplateMasterRequestDataM rejectTemplateMasterReq = hRejectTemplateMasterRequests.get(keyMapping);
							TemplateMasterDataM templateMaster= rejectTemplateMasterReq.getTemplateMasterDataM();
							ArrayList<String> productList = templateMaster.getProducts();
							productList.add(rejectLetterProcessDataM.getProduct());
						}else{
							ArrayList<String> productList  = new ArrayList<String>();
							productList.add(rejectLetterProcessDataM.getProduct());
							templateMasterDataM.setProducts(productList);
							RejectTemplateMasterRequestDataM rejectTemplateMasterReq = new RejectTemplateMasterRequestDataM();
								rejectTemplateMasterReq.setTemplateBuilderRequest(templateRejectReasonInf.getTemplateBuilderRequest());
								rejectTemplateMasterReq.setTemplateMasterDataM(templateMasterDataM);
								rejectTemplateMasterReq.setSendTo(rejectLetterProcessDataM.getSendTo());
							hRejectTemplateMasterRequests.put(keyMapping, rejectTemplateMasterReq);
						}
						
					}
				}

			}else{
				taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.WARNING);
				taskExecuteDataM.setResultDesc("No template reject");
			}
		}
		ArrayList<RejectLetterInterfaceResponse> rejectResponseList = this.generateTemplateProcess(hRejectTemplateMasterRequests);
		if(InfBatchUtil.empty(rejectResponseList)){
			String errorMsg = "Content could not be generated";
			taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.WARNING);
			taskExecuteDataM.setResultDesc(errorMsg);
			//taskExecuteDataM.setFailObject(rejectLetter);
			ArrayList<InfBatchLogDataM> InfBatchLogs = generateErrorProcess(hRejectTemplateMasterRequests,rejectLetter,errorMsg);
			taskExecuteDataM.setFailObject(InfBatchLogs);
		}else{
			taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			taskExecuteDataM.setResponseObject(rejectResponseList);
		}
	}
	
	private void createBundle(RejectLetterDataM rejectLetter,RejectLetterConfigDataM config,ArrayList<String> products,HashMap<String,RejectTemplateMasterRequestDataM> hRejectTemplateMasterRequests,TaskExecuteDataM taskExecuteDataM) throws Exception{
		boolean isBundle = true;
		for(String product : products ){
			BundleAllAppTemplateRejectReason templateRejectReasonInf = getRejectLetterProcessData(product)==null ? null : new BundleAllAppTemplateRejectReason();
			if(!InfBatchUtil.empty(templateRejectReasonInf)){
				templateRejectReasonInf.setProduct(product);
				TemplateRejectProductRequestDataM templateRequest = new TemplateRejectProductRequestDataM();
					templateRequest.setRequestObject(rejectLetter);
					templateRequest.setConfig(config);
					templateRequest.setBundle(isBundle);
				ArrayList<RejectLetterProcessDataM>   rejectLetterProcessList = templateRejectReasonInf.getTemplateRejectProduct(templateRequest);
				if(null!=rejectLetterProcessList){
					for(RejectLetterProcessDataM rejectLetterProcessDataM : rejectLetterProcessList){
						//add flag is bundle
						TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest(rejectLetterProcessDataM);
						templateBuilderRequest.setBungle(true);
						templateRejectReasonInf.setTemplateBuilderRequest(templateBuilderRequest);
						//end add flag is bundle
						//get template
						TemplateMasterDataM templateMasterDataM = templateRejectReasonInf.getTemplateMaster();
//						String templateId = templateMasterDataM.getTemplateId();
//						String templateCode = templateMasterDataM.getTemplateCode();
						String rejectProduct = templateMasterDataM.getRejectLetterProduct();
//						String rejectTemplateType = templateMasterDataM.getRejectLetterTemplateType();
//						String keyMapping = templateId+"_"+templateCode+"_"+rejectProduct+"_"+rejectTemplateType;
						String keyMapping = rejectProduct;
						if(rejectLetterProcessDataM.isEmail()){
							templateMasterDataM.setGeneratePaperOnly(false);
						}else {
							templateMasterDataM.setGeneratePaperOnly(true);
						}
						if(rejectLetterProcessDataM.isEmailAllAfp()){
							templateMasterDataM.setEmailAllAfp(true);
						}else{
							templateMasterDataM.setEmailAllAfp(false);
						}
						if(hRejectTemplateMasterRequests.containsKey(keyMapping)){
							RejectTemplateMasterRequestDataM rejectTemplateMasterReq = hRejectTemplateMasterRequests.get(keyMapping);
							TemplateMasterDataM templateMaster= rejectTemplateMasterReq.getTemplateMasterDataM();
							ArrayList<String> productList = templateMaster.getProducts();
							productList.add(rejectLetterProcessDataM.getProduct());
						}else{
							ArrayList<String> productList  = new ArrayList<String>();
							productList.add(rejectLetterProcessDataM.getProduct());
							templateMasterDataM.setProducts(productList);
							RejectTemplateMasterRequestDataM rejectTemplateMasterReq = new RejectTemplateMasterRequestDataM();
								rejectTemplateMasterReq.setTemplateBuilderRequest(templateRejectReasonInf.getTemplateBuilderRequest());
								rejectTemplateMasterReq.setTemplateMasterDataM(templateMasterDataM);
								rejectTemplateMasterReq.setSendTo(rejectLetterProcessDataM.getSendTo());
							hRejectTemplateMasterRequests.put(keyMapping, rejectTemplateMasterReq);
						}
					}
				}
			}else{
				taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.WARNING);
				taskExecuteDataM.setResultDesc("No template reject");
			}
		}
		ArrayList<RejectLetterInterfaceResponse> rejectResponseList = this.generateBundleTemplateProcess(hRejectTemplateMasterRequests);
		if(InfBatchUtil.empty(rejectResponseList)){
			String errorMsg = "Content could not be generated";
			taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.WARNING);
			taskExecuteDataM.setResultDesc(errorMsg);
			//taskExecuteDataM.setFailObject(rejectLetter);
			ArrayList<InfBatchLogDataM> InfBatchLogs = generateErrorProcess(hRejectTemplateMasterRequests,rejectLetter,errorMsg);
			taskExecuteDataM.setFailObject(InfBatchLogs);
		}else{
			taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			taskExecuteDataM.setResponseObject(rejectResponseList);
		}
	}
	private ArrayList<RejectLetterInterfaceResponse> generateBundleTemplateProcess(HashMap<String,RejectTemplateMasterRequestDataM> rejectTemplateMasterRequests) throws Exception{
		ArrayList<String> REJECT_LETTER_PRODUCT_ORDERD = InfBatchProperty.getListInfBatchConfig("REJECT_LETTER_PRODUCT_ORDERD");
		ArrayList<RejectLetterInterfaceResponse> rejectLetterInterfaceList = new ArrayList<RejectLetterInterfaceResponse>();
		if(!InfBatchUtil.empty(rejectTemplateMasterRequests)){
			ArrayList<RejectTemplateMasterRequestDataM> rejectTemplateMasterRequestList = new ArrayList<>();//ArrayList<RejectTemplateMasterRequestDataM>(rejectTemplateMasterRequests.values());
			for(String productType : REJECT_LETTER_PRODUCT_ORDERD){
				RejectTemplateMasterRequestDataM rejectTemplateMasterRequest = rejectTemplateMasterRequests.get(productType);
				if(rejectTemplateMasterRequest!=null){
					rejectTemplateMasterRequestList.add(rejectTemplateMasterRequest);
				}
			}
			if(!InfBatchUtil.empty(rejectTemplateMasterRequestList))
			{
				RejectLetterBuildTemplateEntity appGroup = new RejectLetterBuildTemplateEntity(rejectTemplateMasterRequestList);
				GenerateRejectLetterAllAppTemplate template = new GenerateRejectLetterAllAppTemplate();
				TemplateBuilderResponse templateBuilderResp = template.buildTemplateBundle(appGroup);
				RejectLetterInterfaceResponse rejectInterfaceRes = template.generateInterfaceTemplateBundle(templateBuilderResp, appGroup);
				if(!InfBatchUtil.empty(rejectInterfaceRes)){
					rejectLetterInterfaceList.add(rejectInterfaceRes);
				}
			}
		}
		return rejectLetterInterfaceList;
	}
}
