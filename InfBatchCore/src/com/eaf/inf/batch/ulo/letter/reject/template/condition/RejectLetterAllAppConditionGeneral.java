package com.eaf.inf.batch.ulo.letter.reject.template.condition;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateConditionRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateConditionResponseDataM;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;

public class RejectLetterAllAppConditionGeneral extends RejectLetterTemplateConditionHelper{
	private static transient Logger logger = Logger.getLogger(RejectLetterAllAppConditionGeneral.class);
	@Override
	public TemplateConditionResponseDataM processTemplateCondition(TemplateConditionRequestDataM conditionRequest) throws Exception {
		TemplateMasterDataM templateMaster = conditionRequest.getTemplateMasterDataM();
		String eMailPrimary = conditionRequest.getEmailPrimary();
		//String letterType = (!templateMaster.isGeneratePaperOnly() && !InfBatchUtil.empty(eMailPrimary)) ? RejectLetterUtil.EMAIL : RejectLetterUtil.PAPER ;
		logger.debug("isEmailAllAfp : "+templateMaster.isEmailAllAfp());
		logger.debug("isGeneratePaperOnly : "+templateMaster.isGeneratePaperOnly());
		String letterType = "";
		if(templateMaster.isEmailAllAfp()){
			letterType = RejectLetterUtil.EMAIL;
		}else if(!templateMaster.isGeneratePaperOnly() && !InfBatchUtil.empty(eMailPrimary)){
			letterType = RejectLetterUtil.EMAIL;
		}else{
			letterType = RejectLetterUtil.PAPER;
		}
		logger.debug("letterType : "+letterType);
		TemplateConditionResponseDataM conditionResponse = new TemplateConditionResponseDataM();
			conditionResponse.setLetterType(letterType);
		return conditionResponse;
	}
	@Override
	public TemplateConditionResponseDataM processTemplateConditionKPL(TemplateConditionRequestDataM conditionRequest) throws Exception {
		TemplateMasterDataM templateMaster = conditionRequest.getTemplateMasterDataM();
		String eMailPrimary = conditionRequest.getEmailPrimary();
		logger.debug("isEmailAllAfp : "+templateMaster.isEmailAllAfp());
		logger.debug("isGeneratePaperOnly : "+templateMaster.isGeneratePaperOnly());
		String letterType = "";
		if(templateMaster.isEmailAllAfp()){
			letterType = "";
		}else if(!templateMaster.isGeneratePaperOnly() && !InfBatchUtil.empty(eMailPrimary)){
			letterType = RejectLetterUtil.EMAIL;
		}else{
			letterType = RejectLetterUtil.PAPER;
		}
		logger.debug("letterType : "+letterType);
		TemplateConditionResponseDataM conditionResponse = new TemplateConditionResponseDataM();
			conditionResponse.setLetterType(letterType);
		return conditionResponse;
	}
}
