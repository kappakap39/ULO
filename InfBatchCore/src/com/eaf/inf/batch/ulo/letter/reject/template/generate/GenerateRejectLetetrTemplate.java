package com.eaf.inf.batch.ulo.letter.reject.template.generate;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderInf;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateResultDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.inf.NotifyRejectLetterInf;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterInterfaceResponse;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateMasterRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.generate.inf.GenerateRejectLetterInf;

public class GenerateRejectLetetrTemplate extends TemplateController{
	private static transient Logger logger = Logger.getLogger(GenerateRejectLetetrTemplate.class);
	
	public TemplateBuilderResponse buildTemplate(RejectTemplateMasterRequestDataM rejectTemplateMasterRequest){
		TemplateBuilderResponse templateBuilderResponse = new TemplateBuilderResponse();
		try {
			if(!InfBatchUtil.empty(rejectTemplateMasterRequest)){
				TemplateMasterDataM templateMaster =rejectTemplateMasterRequest.getTemplateMasterDataM();
				TemplateBuilderRequest templateBuilderReq= rejectTemplateMasterRequest.getTemplateBuilderRequest();
				templateBuilderResponse.setTemplateId(templateMaster.getTemplateId());
				templateBuilderResponse.setTemplateName(templateMaster.getTemplateName());
				TemplateBuilderInf templateBuilder = getTemplateBuilder(templateBuilderReq);
				if(null != templateBuilder){
					templateBuilder.init(templateBuilderReq,templateMaster);
					TemplateVariableDataM templateVariable = templateBuilder.getTemplateVariable();
					logger.debug("templateVariable >> "+templateVariable);
					TemplateResultDataM templateResult = templateBuilder.bulidTemplateResult(templateVariable);
					templateBuilderResponse.setBodyMsg(templateResult.getBodyMsg());
					templateBuilderResponse.setHeaderMsg(templateResult.getHeaderMsg());
					templateBuilderResponse.setDetail1(templateResult.getDetail1());
					templateBuilderResponse.setDetail2(templateResult.getDetail2());
					templateBuilderResponse.setDetail3(templateResult.getDetail3());
					templateBuilderResponse.setPostScript1(templateResult.getPostScript1());
					templateBuilderResponse.setPostScript2(templateResult.getPostScript2());
					templateBuilderResponse.setRemark1(templateResult.getRemark1());
					templateBuilderResponse.setTemplateVariable(templateVariable);
				}
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return templateBuilderResponse;
	}
	
	
	public RejectLetterInterfaceResponse  generateInterfaceTemplate(TemplateBuilderResponse templateBuider,RejectTemplateMasterRequestDataM  rejectTemplateReq) throws Exception{
		String className = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_BUILDER_VALIDATION");
		logger.debug("className : "+className);
		NotifyRejectLetterInf notify = (NotifyRejectLetterInf)Class.forName(className).newInstance();
		if(notify.validateTaskTransaction(templateBuider)){
			TemplateMasterDataM templateMasterDataM  =rejectTemplateReq.getTemplateMasterDataM();
			String rejectTemplateType = templateMasterDataM.getRejectLetterTemplateType();
			logger.debug("rejectTemplateType>>"+rejectTemplateType);
			GenerateRejectLetterInf generateRejectLetterInf = getTemplateInterfaceFormat(rejectTemplateType);
			if(!InfBatchUtil.empty(generateRejectLetterInf)){
				RejectLetterInterfaceResponse rejectLetterInterfaceResponse= (RejectLetterInterfaceResponse)generateRejectLetterInf.generateTemplate(templateBuider,rejectTemplateReq);
				return rejectLetterInterfaceResponse;
			}
		}
		return null;
	}

	public GenerateRejectLetterInf getTemplateInterfaceFormat(String rejectTemplateType) throws Exception{
		GenerateRejectLetterInf generateRejectLetterInf = null;
		if(!InfBatchUtil.empty(rejectTemplateType)){
			String classInterfaceTemplateId = "REJECT_LETTER_INTERFACE_TEMPLATE_TYPE_"+rejectTemplateType;
			logger.debug("classInterfaceTemplateId >> "+classInterfaceTemplateId);
			String className = InfBatchProperty.getInfBatchConfig(classInterfaceTemplateId);
			logger.debug("className >> "+className);
			generateRejectLetterInf = (GenerateRejectLetterInf)Class.forName(className).newInstance();
		}			
		return generateRejectLetterInf;
	}
}
