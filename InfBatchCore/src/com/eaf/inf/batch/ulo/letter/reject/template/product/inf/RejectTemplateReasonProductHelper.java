package com.eaf.inf.batch.ulo.letter.reject.template.product.inf;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.service.template.dao.TemplateBuilderFactory;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;

public abstract class  RejectTemplateReasonProductHelper implements RejectTemplateReasonProductInf {
	private static transient Logger logger = Logger.getLogger(RejectTemplateReasonProductHelper.class);
	private TemplateBuilderRequest templateBuilderRequest;

	@Override
	public TemplateMasterDataM getTemplateMaster() {
		TemplateMasterDataM templateMasterDataM = new TemplateMasterDataM();
		try {			
			templateMasterDataM = TemplateBuilderFactory.getTemplate(templateBuilderRequest);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return templateMasterDataM;
	}

	@Override
	public TemplateBuilderRequest getTemplateBuilderRequest() {
		return templateBuilderRequest;
	}

	@Override
	public void setTemplateBuilderRequest(TemplateBuilderRequest templateBuilderRequest) {
		this.templateBuilderRequest = templateBuilderRequest;
	}

}
