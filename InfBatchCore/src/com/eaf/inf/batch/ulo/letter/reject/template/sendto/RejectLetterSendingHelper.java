package com.eaf.inf.batch.ulo.letter.reject.template.sendto;

import com.eaf.inf.batch.ulo.letter.reject.model.SendingConditionRequestDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.sendto.inf.RejectLetterSendingInf;

public class RejectLetterSendingHelper implements RejectLetterSendingInf {
	@Override
	public Object processSendingCondition(SendingConditionRequestDataM sendingConditionRequest)throws Exception{
		return null;
	}
	@Override
	public Object postCondition(
			SendingConditionRequestDataM sendingConditionRequest, Object object)throws Exception{
		return null;
	}
	protected String generateKeyPriorityRejectLetter(String notificationType,String sendTime,String applicationTemplate,String reasonCode,String cashTransferType){
		 return notificationType+"_"+sendTime+"_"+applicationTemplate+"_"+reasonCode+"_"+cashTransferType;
	}
	protected String generateKeyMakePdfSeller(String notificationType,String sendTime,String applicationTemplate,String reasonCode){
		 return notificationType+"_"+sendTime+"_"+applicationTemplate+"_"+reasonCode;
	 }
}
