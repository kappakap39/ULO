package com.eaf.inf.batch.ulo.letter.reject.template.generate;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateResultDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterBuildTemplateEntity;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterInterfaceResponse;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateMasterRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.BundleTemplate;
import com.eaf.inf.batch.ulo.letter.reject.template.generate.inf.GenerateRejectLetterInf;


public class GenerateRejectLetterAllAppTemplate extends GenerateRejectLetetrTemplate{
	private static transient Logger logger = Logger.getLogger(GenerateRejectLetterAllAppTemplate.class);
	@Override
	public TemplateBuilderResponse buildTemplate(RejectTemplateMasterRequestDataM rejectTemplateMasterRequest){
		return super.buildTemplate(rejectTemplateMasterRequest);
	}
	@Override
	public RejectLetterInterfaceResponse  generateInterfaceTemplate(TemplateBuilderResponse templateBuider,RejectTemplateMasterRequestDataM  rejectTemplateReq) throws Exception{
		return super.generateInterfaceTemplate(templateBuider, rejectTemplateReq);
	}
	@Override
	public GenerateRejectLetterInf getTemplateInterfaceFormat(String rejectTemplateType) throws Exception{
		GenerateRejectLetterInf generateRejectLetterInf = null;
		if(!InfBatchUtil.empty(rejectTemplateType)){
			String classInterfaceTemplateId = "REJECT_LETTER_ALL_APP_INTERFACE_TEMPLATE_TYPE_"+rejectTemplateType;
			logger.debug("classInterfaceTemplateId >> "+classInterfaceTemplateId);
			String className = InfBatchProperty.getInfBatchConfig(classInterfaceTemplateId);
			logger.debug("className >> "+className);
			generateRejectLetterInf = (GenerateRejectLetterInf)Class.forName(className).newInstance();
		}			
		return generateRejectLetterInf;
	}
	
	public TemplateBuilderResponse buildTemplateBundle(RejectLetterBuildTemplateEntity rejectLetterBuildTemplate){
		TemplateBuilderResponse templateBuilderResponse = new TemplateBuilderResponse();
		try {
			RejectTemplateMasterRequestDataM rejectTemplateMasterRequest = rejectLetterBuildTemplate.getRejectTemplateMasterRequest();
			if(!InfBatchUtil.empty(rejectTemplateMasterRequest)){
				TemplateMasterDataM templateMaster =rejectTemplateMasterRequest.getTemplateMasterDataM();
				TemplateBuilderRequest templateBuilderReq= rejectTemplateMasterRequest.getTemplateBuilderRequest();
				templateBuilderResponse.setTemplateId(templateMaster.getTemplateId());
				templateBuilderResponse.setTemplateName(templateMaster.getTemplateName());
				BundleTemplate templateBuilder = new BundleTemplate();
					templateBuilderReq.setRequestObject(rejectLetterBuildTemplate);
				
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
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return templateBuilderResponse;
	}
	public RejectLetterInterfaceResponse  generateInterfaceTemplateBundle(TemplateBuilderResponse templateBuider,RejectLetterBuildTemplateEntity rejectLetterBuildTemplate) throws Exception{
		RejectTemplateMasterRequestDataM rejectTemplateMasterRequest = rejectLetterBuildTemplate.getRejectTemplateMasterRequest();
		return generateInterfaceTemplate(templateBuider, rejectTemplateMasterRequest);
	}
}
