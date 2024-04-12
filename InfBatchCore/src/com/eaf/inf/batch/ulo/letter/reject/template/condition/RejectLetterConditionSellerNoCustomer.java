package com.eaf.inf.batch.ulo.letter.reject.template.condition;

import org.apache.log4j.Logger;

import com.eaf.inf.batch.ulo.letter.reject.model.TemplateConditionRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateConditionResponseDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.condition.RejectLetterTemplateConditionHelper;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;

public class RejectLetterConditionSellerNoCustomer extends RejectLetterTemplateConditionHelper{
	private static transient Logger logger = Logger.getLogger(RejectLetterConditionSellerNoCustomer.class);
	@Override
	public TemplateConditionResponseDataM processTemplateCondition(TemplateConditionRequestDataM conditionRequest)throws Exception{
		String letterType = RejectLetterUtil.EMAIL;
		logger.debug("letterType : "+letterType);
		TemplateConditionResponseDataM conditionResponse = new TemplateConditionResponseDataM();
			conditionResponse.setLetterType(letterType);
		return conditionResponse;
	}
}
