package com.eaf.inf.batch.ulo.letter.reject.template.product.inf;

import java.util.ArrayList;

import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterProcessDataM;

public interface RejectTemplateReasonProductInf {
	public  ArrayList<RejectLetterProcessDataM>  getTemplateRejectProduct(Object object) throws Exception;
	public TemplateMasterDataM getTemplateMaster();
	public TemplateBuilderRequest getTemplateBuilderRequest();
	public void setTemplateBuilderRequest(TemplateBuilderRequest templateBuilderRequest);
}
