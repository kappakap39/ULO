package com.eaf.inf.batch.ulo.letter.reject.template.generate.inf;

import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;

public abstract class  GenerateRejectLetterHelper implements GenerateRejectLetterInf{
	protected String getConfigTemplateCondition(TemplateMasterDataM templateMaster){
		return (templateMaster.isSendSellerNoCust()) ? "REJECT_LETTER_CONDITION_TEMPLATE_SELLER_NO_CUSTOMER" : "REJECT_LETTER_CONDITION_TEMPLATE_GENERAL" ;
	}
	protected String getConfigAllAppTemplateCondition(TemplateMasterDataM templateMaster){
		return "REJECT_LETTER_ALL_APP_CONDITION_TEMPLATE_GENERAL";
	}
}
