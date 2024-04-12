package com.eaf.inf.batch.ulo.letter.reject.template.validatation;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.inf.NotifyRejectLetterHelper;

public class RejectLetterBuilderValidation extends NotifyRejectLetterHelper{
	private static transient Logger logger = Logger.getLogger(RejectLetterBuilderValidation.class);
	@Override
	public boolean validateTaskTransaction(Object object)throws InfBatchException{
		boolean valid = true;
		TemplateBuilderResponse templateBuider = (TemplateBuilderResponse)object;
		if(!InfBatchUtil.empty(templateBuider)){
			 TemplateVariableDataM  templateData = templateBuider.getTemplateVariable();
			 if(!InfBatchUtil.empty(templateData)){
				 HashMap<String, Object> hData = (HashMap<String, Object>)templateData.getTemplateVariable();
				 if(InfBatchUtil.empty(hData)){
					 valid = false;
				 }
			 }else{
				valid = false;
			 }
		}else{
			valid = false;
		}
		logger.debug("valid  : "+valid);
		return valid;
	}
}
