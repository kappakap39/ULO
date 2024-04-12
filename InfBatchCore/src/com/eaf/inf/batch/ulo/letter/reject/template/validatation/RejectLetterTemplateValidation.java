package com.eaf.inf.batch.ulo.letter.reject.template.validatation;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.letter.reject.inf.NotifyRejectLetterHelper;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectTemplateVariableDataM;

public class RejectLetterTemplateValidation extends NotifyRejectLetterHelper{
	private static transient Logger logger = Logger.getLogger(RejectLetterTemplateValidation.class);
	@Override
	public boolean validateTaskTransaction(Object object)throws InfBatchException{
		boolean valid = true;
		RejectTemplateVariableDataM rejectTemplateVariable = (RejectTemplateVariableDataM)object;
		if(!InfBatchUtil.empty(rejectTemplateVariable)){
			if(InfBatchUtil.empty(rejectTemplateVariable.getAddressLine1()) || InfBatchUtil.empty(rejectTemplateVariable.getAddressLine2())){
				valid = false;
			}else if(InfBatchUtil.empty(rejectTemplateVariable.getFinalDecisionDate())){
				valid = false;
			}
		}else{
			valid = false;
		}
		logger.debug("valid  : "+valid);
		return valid;
	}
}
