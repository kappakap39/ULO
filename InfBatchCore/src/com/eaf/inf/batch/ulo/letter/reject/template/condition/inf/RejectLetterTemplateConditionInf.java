package com.eaf.inf.batch.ulo.letter.reject.template.condition.inf;

import com.eaf.inf.batch.ulo.letter.reject.model.TemplateConditionRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.TemplateConditionResponseDataM;

public interface RejectLetterTemplateConditionInf {
	public TemplateConditionResponseDataM processTemplateCondition(TemplateConditionRequestDataM conditionRequest)throws Exception;
	public TemplateConditionResponseDataM processTemplateConditionKPL(TemplateConditionRequestDataM conditionRequest)throws Exception;
}
