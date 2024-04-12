package com.eaf.inf.batch.ulo.letter.reject.template.sendto;

import org.apache.log4j.Logger;

import com.eaf.inf.batch.ulo.letter.reject.model.SendingConditionRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.SendingConditionResponseDataM;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;

public class RejectLetterSendtoSeller extends RejectLetterSendingHelper {
	private static transient Logger logger = Logger.getLogger(RejectLetterSendtoSeller.class);
	@Override
	public Object processSendingCondition(SendingConditionRequestDataM sendingConditionRequest)throws Exception{
		 boolean isGenPaper =false;
		 boolean isGenEmail =false;
		 SendingConditionResponseDataM response = new SendingConditionResponseDataM();
		 	 response.setGenEmail(isGenEmail);
			 response.setGenPaper(isGenPaper);
			 response.setLetterType(RejectLetterUtil.EMAIL_TO_SELLER);
		 logger.debug("lettetType : "+RejectLetterUtil.EMAIL_TO_SELLER);
		 return response;
	}
}
