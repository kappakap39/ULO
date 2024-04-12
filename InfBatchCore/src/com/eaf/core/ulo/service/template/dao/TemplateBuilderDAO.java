package com.eaf.core.ulo.service.template.dao;

import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;


public interface TemplateBuilderDAO {
	public TemplateMasterDataM getEmailTemplate(TemplateBuilderRequest templateBuilderRequest);
	public TemplateMasterDataM getSmsTemplate(TemplateBuilderRequest templateBuilderRequest);
	public TemplateMasterDataM getRejectLetterTemplate(TemplateBuilderRequest templateBuilderRequest);
	public TemplateMasterDataM getKMobileTemplate(TemplateBuilderRequest templateBuilderRequest);
	public TemplateMasterDataM getRejectLetterTemplateBundle(TemplateBuilderRequest templateBuilderRequest, String product);
}
