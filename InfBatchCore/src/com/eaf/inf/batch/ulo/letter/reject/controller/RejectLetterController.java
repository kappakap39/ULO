package com.eaf.inf.batch.ulo.letter.reject.controller;

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
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterConfigDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterInterfaceResponse;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterProcessDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateMasterRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateRejectProductRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.generate.GenerateRejectLetetrTemplate;
import com.eaf.inf.batch.ulo.letter.reject.template.product.inf.RejectTemplateReasonProductInf;
import com.google.gson.Gson;

public class RejectLetterController {
	private static transient Logger logger = Logger.getLogger(RejectLetterController.class);

	public TaskExecuteDataM create(RejectLetterDataM rejectLetterDataM,RejectLetterConfigDataM config){
		TaskExecuteDataM taskExecuteDataM = new TaskExecuteDataM();
		try {
			HashMap<String,RejectTemplateMasterRequestDataM> hRejectTemplateMasterRequests = new HashMap<String,RejectTemplateMasterRequestDataM>();
			ArrayList<String> products = rejectLetterDataM.getProducts();
			//logger.debug("products.size() >> "+products.size());
			if(!InfBatchUtil.empty(products)){
				logger.debug("products : "+products.size());
				for(String product : products ){
					//logger.debug("product >> "+product);
					RejectTemplateReasonProductInf templateRejectReasonInf = getRejectLetterProcessData(product);
					if(!InfBatchUtil.empty(templateRejectReasonInf)){
						TemplateRejectProductRequestDataM templateRequest = new TemplateRejectProductRequestDataM();
							templateRequest.setRequestObject(rejectLetterDataM);
							templateRequest.setConfig(config);
						ArrayList<RejectLetterProcessDataM>   rejectLetterProcessList = templateRejectReasonInf.getTemplateRejectProduct(templateRequest);
//						logger.debug(" info : rejectLetterDataM >> "+new Gson().toJson(rejectLetterDataM)+"==>"+rejectLetterDataM.getApplicationGroupId());
//						logger.debug(" info : rejectProcessList >> "+new Gson().toJson(rejectLetterProcessList)+"==>"+rejectLetterDataM.getApplicationGroupId());
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
//								logger.debug("keyMapping >> "+keyMapping);
//								logger.debug("templateId >> "+templateId);
//								logger.debug("templateCode >> "+templateCode);
//								logger.debug("rejectProduct >> "+rejectProduct);
//								logger.debug("applyType >> "+applyType);
//								logger.debug("rejectTemplateType >> "+rejectTemplateType);
								if(rejectLetterProcessDataM.isEmail()){
									templateMasterDataM.setGeneratePaperOnly(false);
								}else {
									templateMasterDataM.setGeneratePaperOnly(true);
								}
								if(rejectLetterProcessDataM.isSendSellerNoCust()){
									templateMasterDataM.setSendSellerNoCust(true);
								}else{
									templateMasterDataM.setSendSellerNoCust(false);
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
						taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.FAIL);
						taskExecuteDataM.setResultDesc("No template reject");
					}
				}
				ArrayList<RejectLetterInterfaceResponse> rejectResponseList = this.generateTemplateProcess(hRejectTemplateMasterRequests);
				if(InfBatchUtil.empty(rejectResponseList)){
					taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.FAIL);
					taskExecuteDataM.setResultDesc("No reject response list");
				}else{
					taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
					taskExecuteDataM.setResponseObject(rejectResponseList);
				}
			}else{
				taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecuteDataM.setResultDesc("No product");
			}
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecuteDataM.setResultDesc(e.getLocalizedMessage());
		}		
		return taskExecuteDataM;
	}

	private ArrayList<RejectLetterInterfaceResponse> generateTemplateProcess(HashMap<String,RejectTemplateMasterRequestDataM> rejectTemplateMasterRequests) throws Exception{
		ArrayList<RejectLetterInterfaceResponse> rejectLetterInterfaceList = new ArrayList<RejectLetterInterfaceResponse>();
		if(!InfBatchUtil.empty(rejectTemplateMasterRequests)){
			ArrayList<String> keyMappings = new ArrayList<>(rejectTemplateMasterRequests.keySet());
			for(String keyName :keyMappings){
				RejectTemplateMasterRequestDataM rejectTemplateMasterReq = rejectTemplateMasterRequests.get(keyName);
				GenerateRejectLetetrTemplate generateRejectLetetrTemplate = new GenerateRejectLetetrTemplate();
				TemplateBuilderResponse templateBuilderResp = generateRejectLetetrTemplate.buildTemplate(rejectTemplateMasterReq);
				RejectLetterInterfaceResponse rejectInterfaceRes = generateRejectLetetrTemplate.generateInterfaceTemplate(templateBuilderResp, rejectTemplateMasterReq);
				if(!InfBatchUtil.empty(rejectInterfaceRes)){
					rejectLetterInterfaceList.add(rejectInterfaceRes);
				}
			}
		}
		return rejectLetterInterfaceList;
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
			String PROCESS_CLASS_NAME = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_"+product);
			logger.debug("PROCESS_CLASS_NAME >> "+PROCESS_CLASS_NAME);	
			rejectTemplateReasonProductInf = (RejectTemplateReasonProductInf)Class.forName(PROCESS_CLASS_NAME).newInstance();
		}
		return rejectTemplateReasonProductInf;
	}
}
