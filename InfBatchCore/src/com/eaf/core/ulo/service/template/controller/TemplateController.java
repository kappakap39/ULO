package com.eaf.core.ulo.service.template.controller;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.dao.TemplateBuilderFactory;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderInf;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.core.ulo.service.template.model.TemplateResultDataM;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;

public class TemplateController {
	private static transient Logger logger = Logger.getLogger(TemplateController.class);
	
	public TemplateBuilderResponse build(TemplateBuilderRequest templateBuilderRequest){
		logger.debug("build..");
		String templateId = templateBuilderRequest.getTemplateId();
		String templateType = templateBuilderRequest.getTemplateType();	
		String uniqueId = templateBuilderRequest.getUniqueId().getId();
		logger.debug("templateId >> "+templateId);
		logger.debug("templateType >> "+templateType);
		logger.debug("uniqueId >> "+uniqueId);
		logger.debug("lifeCycle >> "+templateBuilderRequest.getUniqueId().getLifeCycle());
		TemplateBuilderResponse templateBuilderResponse = new TemplateBuilderResponse();
		try{
			TemplateMasterDataM templateMaster = TemplateBuilderFactory.getTemplate(templateBuilderRequest);
			templateBuilderResponse.setTemplateId(templateMaster.getTemplateId());
			templateBuilderResponse.setTemplateName(templateMaster.getTemplateName());
			logger.debug("templateMaster >> "+templateMaster);
			TemplateBuilderInf templateBuilder = getTemplateBuilder(templateBuilderRequest);
			if(null != templateBuilder){
				templateBuilder.init(templateBuilderRequest,templateMaster);
				TemplateVariableDataM templateVariable = templateBuilder.getTemplateVariable();
				if(!InfBatchUtil.empty(templateVariable)){
					logger.debug("templateVariable >> "+templateVariable);
					TemplateResultDataM templateResult = templateBuilder.bulidTemplateResult(templateVariable);
					templateBuilderResponse.setBodyMsgEn(templateResult.getBodyMsgEn());
					templateBuilderResponse.setBodyMsgTh(templateResult.getBodyMsgTh());
					templateBuilderResponse.setBodyMsg(templateResult.getBodyMsg());
					templateBuilderResponse.setHeaderMsg(templateResult.getHeaderMsg());
					templateBuilderResponse.setDetail1(templateResult.getDetail1());
					templateBuilderResponse.setDetail2(templateResult.getDetail2());
					templateBuilderResponse.setDetail3(templateResult.getDetail3());
					templateBuilderResponse.setPostScript1(templateResult.getPostScript1());
					templateBuilderResponse.setPostScript2(templateResult.getPostScript2());
					templateBuilderResponse.setRemark1(templateResult.getRemark1());
					templateBuilderResponse.setTemplateVariable(templateVariable);
					templateBuilderResponse.setAttachments(templateVariable.getAttachments());	
					logger.debug("AlertMessageTh : " + templateResult.getAlertMessageTh());
					logger.debug("AlertMessageEn : " + templateResult.getAlertMessageEn());
					templateBuilderResponse.setAlertMessageTh(templateResult.getAlertMessageTh());
					templateBuilderResponse.setAlertMessageEn(templateResult.getAlertMessageEn());
				}				
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return templateBuilderResponse;
	}
	
	public TemplateBuilderInf getTemplateBuilder(TemplateBuilderRequest templateBuilderRequest){
		TemplateBuilderInf templateBuilder = null;
		String templateType =templateBuilderRequest.getTemplateType();
		String templateId =templateBuilderRequest.getTemplateId();
		try{
			if(!InfBatchUtil.empty(templateType) && !InfBatchUtil.empty(templateId)){
				String classTemplateId = templateType+"_"+templateId;
				logger.debug("classTemplateId >> "+classTemplateId);
				String className = InfBatchProperty.getInfBatchConfig(classTemplateId);
				logger.debug("className >> "+className);
				if(!InfBatchUtil.empty(className)){
					templateBuilder = (TemplateBuilderInf)Class.forName(className).newInstance();
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return templateBuilder;
	}
}
