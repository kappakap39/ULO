package com.eaf.inf.batch.ulo.letter.reject.template.condition;

import com.eaf.inf.batch.ulo.letter.reject.model.TemplateConditionRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateConditionResponseDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.condition.inf.RejectLetterTemplateConditionInf;

public class RejectLetterTemplateConditionHelper implements RejectLetterTemplateConditionInf{
	@Override
	public TemplateConditionResponseDataM processTemplateCondition(TemplateConditionRequestDataM conditionRequest) throws Exception {
		TemplateConditionResponseDataM conditionResponse = new TemplateConditionResponseDataM();
		return conditionResponse;
	}

	@Override
	public TemplateConditionResponseDataM processTemplateConditionKPL(TemplateConditionRequestDataM conditionRequest) throws Exception {
		TemplateConditionResponseDataM conditionResponse = new TemplateConditionResponseDataM();
		return conditionResponse;
	}
}
