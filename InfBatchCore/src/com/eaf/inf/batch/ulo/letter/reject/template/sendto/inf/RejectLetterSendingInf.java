package com.eaf.inf.batch.ulo.letter.reject.template.sendto.inf;

import com.eaf.inf.batch.ulo.letter.reject.model.SendingConditionRequestDataM;

public interface RejectLetterSendingInf {
	public Object processSendingCondition(SendingConditionRequestDataM sendingConditionRequest) throws Exception;
	public Object postCondition(SendingConditionRequestDataM sendingConditionRequest,Object object) throws Exception;
}
