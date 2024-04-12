package com.eaf.inf.batch.ulo.letter.reject.template.sendto;

import org.apache.log4j.Logger;

import com.eaf.inf.batch.ulo.letter.reject.model.SendingConditionRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.SendingConditionResponseDataM;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;

public class RejectLetterAllAppSendtoSeller extends RejectLetterSendingHelper{
	private static transient Logger logger = Logger.getLogger(RejectLetterAllAppSendtoSeller.class);
	@Override
	public Object processSendingCondition(SendingConditionRequestDataM sendingConditionRequest)throws Exception{
		 boolean isGenPaper =false;
		 boolean isGenEmail =false;
		 String letterType = RejectLetterUtil.EMAIL_ALL_AFP;
		 SendingConditionResponseDataM response = new SendingConditionResponseDataM();
		 	 response.setGenEmail(isGenEmail);
			 response.setGenPaper(isGenPaper);
			 response.setLetterType(letterType);
		 logger.debug("lettetType : "+letterType);
		 return response;
	}
}
